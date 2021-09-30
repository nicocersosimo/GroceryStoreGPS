import java.util.*

fun main() {
    println()
    println("Welcome to Nico's Grocery Store GPS, where we always find your shortest route!")
    Thread.sleep(2000)
    println()
    println("Here is a numbered list of all the available grocery items in the store:")
    Thread.sleep(1500)
    println()
    GroceryItem.values().forEach { item -> println("${item.ordinal + 1}.) ${item.name}") }
    println()
    Thread.sleep(1000)
    println("Please type the item number and hit enter for each item you'd like to purchase today.")
    println("When you're done selecting items type \"done\" and hit enter.")
    println("If at any time you need to make a correction to your list, type \"correction\" and hit enter")
    val selectedItems = mutableSetOf<GroceryItem>()
    val groceryItemOrdinals = GroceryItem.values().map { it.ordinal }
    var done = false
    var addOrRemove = "add"

    while (!done) {
        val input = readLine()

        if (input?.lowercase(Locale.getDefault()).equals("done") ||
            input?.lowercase(Locale.getDefault()).equals("correction")
        ) {
            if (selectedItems.isEmpty()) {
                println("Must select at least one grocery item first")
            } else {
                println("Here are your recorded selections:")
                Thread.sleep(1000)
                selectedItems.forEach { println(it.name) }
                Thread.sleep(1000)
                println()
                println("Is this correct?")
                Thread.sleep(1000)
                println()
                println("1.) Yes. On with the routing!")
                println("2.) Yes, but I need to ADD more items to my list")
                println("3.) No. I need to REMOVE some items from my list")
                println("Select an option number and hit enter to proceed")
                when (readLine()) {
                    "1" -> done = true
                    "2" -> {
                        addOrRemove = "add"
                        println("You've selected to add more items to your list. Here is the list again for reference:")
                        Thread.sleep(2000)
                        println()
                        GroceryItem.values().forEach { item -> println("${item.ordinal + 1}.) ${item.name}") }
                        println()
                        println("Select an item number and hit enter to add it.")
                        println("Type \"done\" and hit enter when you're done making additional selections.")
                        println("Type \"correction\" again if you selected the wrong course of action")
                    }
                    "3" -> {
                        addOrRemove = "remove"
                        println("You've selected to remove items from your list. Here is your current list for reference:")
                        Thread.sleep(2000)
                        selectedItems.forEach { item -> println("${item.ordinal + 1}.) ${item.name}") }
                        println()
                        println("Select an item number and hit enter to remove it.")
                        println("Type \"done\" and hit enter when you're done selecting items to remove.")
                        println("Type \"correction\" again if you selected the wrong course of action")
                    }
                }
            }
        } else if (input.isNullOrBlank() || input.toIntOrNull() == null || input.toInt() - 1 !in groceryItemOrdinals) {
            println("Invalid input. Accepts item numbers, \"done\", or \"correction\" only")
        } else {
            if (addOrRemove == "add") {
                println("Adding selected item: ${GroceryItem.values()[input.toInt() - 1].name}")
                selectedItems.add(GroceryItem.values()[input.toInt() - 1])
            } else {
                if (selectedItems.contains(GroceryItem.values()[input.toInt() - 1])) {
                    println("Removing selected item: ${GroceryItem.values()[input.toInt() - 1].name}")
                    selectedItems.remove(GroceryItem.values()[input.toInt() - 1])
                }
                else {
                    println("Cannot remove item: ${GroceryItem.values()[input.toInt() - 1].name}, " +
                            "has not been selected")
                }
            }
        }
    }

    println()
    println("Thank you for your selections. Calculating the shortest route based on the locations of the following " +
            "selected items in the store:")

    Thread.sleep(1500)
    println()
    println("Bleep..")
    Thread.sleep(1000)
    println("..bloop..")
    Thread.sleep(1000)
    println("..*various other cinematic computer noises*..")
    Thread.sleep(1000)
    val shortestRouteGenerator = ShortestRouteGenerator()
    val shortestRouteOrderedSet = shortestRouteGenerator.generate(selectedItems)
    println("...aaaand DONE")
    println()
    Thread.sleep(1000)
    println("Below is your list of selected items along with the sectors they are in, which are now ordered from top " +
            "to bottom to create your shortest route!")
    Thread.sleep(1000)
    println()
    shortestRouteOrderedSet.forEachIndexed { index, item ->
        println("${index + 1}.) ${item.name} in ${item.storeSector.name} sector")
    }
    Thread.sleep(1000)
    println("Enjoy your visit to Nico's Grocery Store today :)")
}
