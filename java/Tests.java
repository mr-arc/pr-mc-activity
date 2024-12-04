import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class Tests {
  private static final List<String> INVENTORY =
      Arrays.asList("4 STICK", "5 STONE", "3 CLAY", "5 WOOD");

  private static final Map<String, List<String>> RECIPES = new HashMap<>();
  static {
    RECIPES.put("PICKAXE", Arrays.asList("3 STONE", "2 STICK"));
    RECIPES.put("PAPER", Arrays.asList("3 SUGAR"));
    RECIPES.put("LADDER", Arrays.asList("7 STICK"));
    RECIPES.put("STICK", Arrays.asList("2 WOOD"));
    RECIPES.put("TNT", Arrays.asList("4 SAND", "5 GUNPOWDER"));
    RECIPES.put("BOOK", Arrays.asList("3 PAPER", "1 LEATHER"));
    RECIPES.put("LEATHER", Arrays.asList("4 HIDE"));
  }

  private static final String BLUE = "\033[1;34m";
  private static final String RESET = "\033[0m";
  private static final String GREEN = "\033[1;32m";
  private static final String RED = "\033[1;31m";

  private static final List<TestCase> ALL_TESTS = Arrays.asList(
      runTest("Cannot craft OBSIDIAN (no recipe for it).", false, "OBSIDIAN"),
      runTest("Can craft STICK", true, "STICK"),
      runTest("Can craft PICKAXE using STICKS", true, "PICKAXE"),
      runTest("Can craft PICKAXE using WOOD", true, "PICKAXE",
              Arrays.asList("8 WOOD", "5 STONE", "3 OBSIDIAN")),
      runTest("Cannot craft PICKAXE without enough STONE", false, "PICKAXE",
              Arrays.asList("1 STONE", "8 STICKS")),
      runTest(
          "Cannot craft BOOK with LEATHER, but not enough SUGAR to make PAPER.",
          false, "BOOK", Arrays.asList("2 LEATHER", "2 SUGAR")),
      runTest("[HARD!] Can craft BOOK with only SUGAR and HIDEs", true, "BOOK",
              Arrays.asList("20 HIDE", "16 SUGAR"))

  );

  private static TestCase runTest(String name, boolean expected, String item) {
    return runTest(name, expected, item, INVENTORY);
  }

  private static TestCase runTest(String name, boolean expected, String item,
                                  List<String> inventory) {
    return runTest(name, expected, item, inventory, RECIPES);
  }

  private static TestCase runTest(String name, boolean expected, String item,
                                  List<String> inventory,
                                  Map<String, List<String>> recipes) {
    return new TestCase(name, () -> {
      boolean passed = Solution.canCraft(item, inventory, recipes) == expected;
      String result = passed ? "✅ PASSED" : "⛔ FAILED";
      String color = passed ? GREEN : RED;
      System.out.printf("  %s%s %s%s %s\n", color, result, BLUE, name, RESET);
      return passed;
    });
  }

  private static void runAllTests() {
    System.out.println("Running " + ALL_TESTS.size() + " tests.");
    int passed = 0;

    for (var test : ALL_TESTS) {
      try {
        boolean result = test.test.get();
        if (result) {
          ++passed;
        }
      } catch (Exception ex) {
        System.out.println(RED + "Exception in test " + BLUE + test.name + RED);
        ex.printStackTrace();
        System.out.println(RESET);
      }
    }

    String message =
        passed != ALL_TESTS.size()
            ? String.format("%d of %d passed.", passed, ALL_TESTS.size())
            : "All tests passed!";
    System.out.println("Done. " + message);
  }

  private static class TestCase {
    String name;
    Supplier<Boolean> test;

    TestCase(String name, Supplier<Boolean> test) {
      this.name = name;
      this.test = test;
    }
  }

  public static void main(String[] args) { runAllTests(); }
}
