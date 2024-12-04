import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {
  /**
   * Returns {@code true} if {@code item} can be crafted from the {@code
   * inventory}.
   *
   * Assume the following:
   * <ul>
   *   <li>All item names are UPPERCASE and contain NO spaces.
   *   <li>All item names are singular.
   *   <li>Each string in inventory and the values of recipes is formatted as
   *   {@code <number><space><ITEM>}. For example:
   *   {@code "3 STICK", "2 STONE","1 BONE"}
   * </ul>
   *
   * @param item the item to craft
   * @param inventory the items you can use to craft with
   * @param recipes instructions for crafting items
   * @return {@code true} if the item can be crafted, {@code false} otherwise.
   */
  public static boolean canCraft(String item, List<String> inventory,
                                 Map<String, List<String>> recipes) {
    return craftHowMany(item, inventory, recipes) > 0;
  }

  /** Converts a list of items to a lookup table of name -> number. */
  private static Map<String, Integer> mapItems(List<String> items) {
    return items.stream()
        .map(s -> s.split(" "))
        .collect(Collectors.toMap(i -> i[1], i -> Integer.parseInt(i[0])));
  }

  /**
   * Returns the number of the given {@code item} that can be crafted with the
   * specified {@code inventory}.
   */
  private static int craftHowMany(String item, List<String> inventory,
                                  Map<String, List<String>> recipes) {
    // If we don't have a recipe for it, we can't make it!
    if (!recipes.containsKey(item)) {
      return 0;
    }

    // Convert our inventory into items.
    var inventoryItems = mapItems(inventory);

    // Find the recipe and convert into items.
    var recipeItems = mapItems(recipes.get(item));

    // For each item in the recipe, see if we have enough stuff.
    int numCanCraft = -1;

    for (var recipeItem : recipeItems.keySet()) {
      if (!inventoryItems.containsKey(recipeItem)) {
        // Let's see if we can craft what we're missing.
        // If there is a recipe, how many can we make?
        int howMany = craftHowMany(recipeItem, inventory, recipes);
        // Is it enough? If so, update the number we can craft.
        if (numCanCraft < 0 || howMany < numCanCraft) {
          numCanCraft = howMany;
        }
      } else if (inventoryItems.get(recipeItem) < recipeItems.get(recipeItem)) {
        // We have the ingredient, but not enough of it.
        return 0;
      } else {
        // We have enough of THIS ingredient.
        int canCraft =
            inventoryItems.get(recipeItem) / recipeItems.get(recipeItem);
        if (numCanCraft < 0 || canCraft < numCanCraft) {
          numCanCraft = canCraft;
        }
      }
    }
    return Math.max(0, numCanCraft);
  }
}
