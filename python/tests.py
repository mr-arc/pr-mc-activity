from solution import canCraft
import traceback

_RECIPES = {
    "PICKAXE": ["3 STONE", "2 STICK"],
    "PAPER": ["3 SUGAR"],
    "LADDER": ["7 STICK"],
    "STICK": ["2 WOOD"],
    "TNT": ["4 SAND", "5 GUNPOWDER"],
    "BOOK": ["3 PAPER", "1 LEATHER"],
    "LEATHER": ["4 HIDE"],
}

_INVENTORY = [
    "4 STICK",
    "5 STONE",
    "3 CLAY",
    "5 WOOD",
]

_BLUE = "\033[1;34m"
_RESET = "\033[0m"
_GREEN = "\033[1;32m"
_RED = "\033[1;31m"


def _runTest(
    name: str,
    expected: bool,
    item: str,
    inventory: list[str] = _INVENTORY,
    recipes: dict[str, list[str]] = _RECIPES,
):
    def doTest():
        passed = canCraft(item, inventory, recipes) == expected
        result = "✅ PASSED" if passed else "⛔ FAILED"
        color = _GREEN if passed else _RED
        print(f"   {color}{result}  {_BLUE}{name} {_RESET}")
        return passed

    return (name, doTest)


_ALL_TESTS = [
    _runTest(
        "Cannot craft OBSIDIAN (no recipe for it).",
        False,
        "OBSIDIAN",
    ),
    _runTest("Can craft STICK", True, "STICK"),
    _runTest("Can craft PICKAXE using STICKS", True, "PICKAXE"),
    _runTest(
        "Can craft PICKAXE using WOOD",
        True,
        "PICKAXE",
        ["8 WOOD", "5 STONE", "3 OBSIDIAN"],
    ),
    _runTest(
        "Cannot craft PICKAXE without enough STONE",
        False,
        "PICKAXE",
        ["1 STONE", "8 STICKS"],
    ),
    _runTest(
        "Cannot craft BOOK with LEATHER, but not enough SUGAR to make PAPER.",
        False,
        "BOOK",
        ["2 LEATHER", "2 SUGAR"],
    ),
    _runTest(
        "[HARD!] Can craft BOOK with only SUGAR and HIDEs",
        True,
        "BOOK",
        ["20 HIDE", "16 SUGAR"],
    ),
]


def _runAllTests():
    print(f"Running {len(_ALL_TESTS)} tests.")
    passed = 0

    for name, test in _ALL_TESTS:
        try:
            result = test()
            if result:
                passed += 1
        except Exception as ex:
            print(f'{_RED}Exception in test {_BLUE}"{name}"{_RED}: {ex}')
            print(traceback.format_exc())
            print(_RESET)

    message = "All tests passed!"
    if passed != len(_ALL_TESTS):
        message = f"{passed} of {len(_ALL_TESTS)} passed."
    print(f"Done. {message}")


if __name__ == "__main__":
    _runAllTests()
