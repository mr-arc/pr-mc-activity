import java.util.List;
import java.util.Map;

public class CanCraft {
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
    // Your code here.
    return false;
  }
}