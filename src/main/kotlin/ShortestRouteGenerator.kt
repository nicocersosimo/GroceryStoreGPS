class ShortestRouteGenerator {
    fun generate(selectedItems: Set<GroceryItem>): Set<GroceryItem> {
        val orderToBuyItems = mutableSetOf<GroceryItem>()
        var currentSector = StoreSector.ENTRANCE
        val selectedItemsUnvisitedSectors = selectedItems.map { it.storeSector }.toMutableSet()

        while (currentSector != StoreSector.CHECKOUT) {
            val closestSectors = getClosestSectors(currentSector, selectedItemsUnvisitedSectors)
            //handle tiebreaker
            currentSector = if (closestSectors.size > 1) {
                //take first if multiple because any sector you choose in the list will yield the same distance traveled
                //in the end
                val farthestSectorFromCenter = getFarthestSectors(StoreSector.CENTER, closestSectors).first()
                farthestSectorFromCenter
            } else {
                closestSectors.single()
            }
            orderToBuyItems.addAll(selectedItems.filter { it.storeSector == currentSector })
            selectedItemsUnvisitedSectors.remove(currentSector)
            if (selectedItemsUnvisitedSectors.isEmpty()) { currentSector = StoreSector.CHECKOUT }
        }

        return orderToBuyItems
    }

    private fun getClosestSectors(currentSector: StoreSector, sectorsToCompare: Set<StoreSector>): Set<StoreSector> {
        val firstClosestSector =
            sectorsToCompare.minByOrNull { otherSector -> findDistanceBetweenSectors(currentSector, otherSector) }
        val shortestDistance = findDistanceBetweenSectors(currentSector, firstClosestSector!!)
        val allOtherSectorsWithShortestDistance = sectorsToCompare.filter { otherSector ->
            findDistanceBetweenSectors(currentSector, otherSector) == shortestDistance
        }.toSet()
        return allOtherSectorsWithShortestDistance
    }

    private fun getFarthestSectors(currentSector: StoreSector, sectorsToCompare: Set<StoreSector>): Set<StoreSector> {
        val firstFarthestSector =
            sectorsToCompare.maxByOrNull { otherSector -> findDistanceBetweenSectors(currentSector, otherSector) }
        val longestDistance = findDistanceBetweenSectors(currentSector, firstFarthestSector!!)
        val allOtherSectorsWithLongestDistance = sectorsToCompare.filter { otherSector ->
            findDistanceBetweenSectors(currentSector, otherSector) == longestDistance
        }.toSet()
        return allOtherSectorsWithLongestDistance
    }

    //bound to horizontal and vertical movements along the x,y gridlines to account for aisles in the store and allow
    //for the problem to be solved in polynomial time (no longer NP-Hard / able to reach most optimal solution each time
    //without having to calculate and compare each possible route / can scale up without destroying performance)
    private fun findDistanceBetweenSectors(currentSector: StoreSector, offsetSector: StoreSector): Int {
        val xOffset = kotlin.math.abs(offsetSector.storeGridXValue - currentSector.storeGridXValue)
        val yOffset = kotlin.math.abs(offsetSector.storeGridYValue - currentSector.storeGridYValue)

        return xOffset + yOffset
    }
}
