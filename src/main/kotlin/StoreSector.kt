enum class StoreSector(val storeGridXValue: Int, val storeGridYValue: Int) {
    ENTRANCE(2,0),
    CENTER(2,2),
    PASTRY(4,1),
    DELI(4, 2),
    MEAT(4,4),
    PRODUCE(2, 4),
    FROZEN(1, 3),
    PANTRY(1,2),
    MISCELLANEOUS(0,1),
    CHECKOUT(2,1)
}
