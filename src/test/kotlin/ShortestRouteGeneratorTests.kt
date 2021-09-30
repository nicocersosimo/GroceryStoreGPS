import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ShortestRouteGeneratorTests {

    private val shortestRouteGenerator = ShortestRouteGenerator()

    @Test
    fun `should generate the shortest route when there are no tiebreakers`() {
        val selectedItems = setOf(
            GroceryItem.CHICKEN,
            GroceryItem.BAGELS,
            GroceryItem.BAR_SOAP,
            GroceryItem.BURRITOS
        )

        val result = shortestRouteGenerator.generate(selectedItems)

        val expected = setOf(
            GroceryItem.BAGELS,
            GroceryItem.CHICKEN,
            GroceryItem.BURRITOS,
            GroceryItem.BAR_SOAP
        )

        assertThat(result).containsExactlyElementsOf(expected)
    }

    @Test
    fun `should generate the shortest route when there is a tiebreaker for closestSector but no additional tiebreaker for farthestFromCenter`() {
        val selectedItems = setOf(
            GroceryItem.CHIPS,
            GroceryItem.APPLES,
            GroceryItem.BAR_SOAP,
            GroceryItem.BURRITOS
        )

        val result = shortestRouteGenerator.generate(selectedItems)

        val expected = setOf(
            GroceryItem.BAR_SOAP,
            GroceryItem.CHIPS,
            GroceryItem.BURRITOS,
            GroceryItem.APPLES
        )

        assertThat(result).containsExactlyElementsOf(expected)
    }

    @Test
    fun `should pick any shortest route when there is a tiebreaker for both closestSector AND farthestFromCenter`() {
        val selectedItems = setOf(
            GroceryItem.PIZZA,
            GroceryItem.PEPPERONI,
            GroceryItem.APPLES,
            GroceryItem.FLOWERS
        )

        val result = shortestRouteGenerator.generate(selectedItems)
        assertThat(result.indexOf(GroceryItem.FLOWERS)).isEqualTo(0)
        assertThat(result.indexOf(GroceryItem.APPLES)).isEqualTo(2)
    }
}
