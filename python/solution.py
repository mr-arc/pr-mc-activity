def canCraft(item: str, inventory: list[str], recipes: dict[str, list[str]]) -> bool:
    """
    Returns True if item can be crafted from the inventory. False if it cannot.

    Assume the following:
    - All item names are UPPERCASE and contain NO spaces.
    - All item names are singular.
    - Each string in inventory and the values of recipes is formatted as
      <number><space><ITEM>. For example: "3 STICK", "2 STONE", "1 BONE"

    Args:
      item: The item to craft.
      inventory: The items you can use to craft with.
      recipes: Instructions for crafting items.
    """
    return _craftHowMany(item, inventory, recipes) > 0


def _parseItem(item: str) -> tuple[int, str]:
    """Converts an item string into a tuple of [number, name]."""
    split = item.split(" ")
    return [int(split[0]), split[1]]


def _itemsToDict(items: list[str]) -> dict[str, int]:
    """Converts a list of items to a lookup table of name -> number."""
    itemsDict = {}
    for item in [_parseItem(i) for i in items]:
        itemsDict[item[1]] = item[0]
    return itemsDict


def _craftHowMany(
    item: str, inventory: list[str], recipes: dict[str, list[str]]
) -> int:
    """
    Returns the number of the given item that can be crafted with the specified inventory.
    """
    # If we don't have a recipe for it, we can't make it!
    if item not in recipes:
        return 0

    # Convert our inventory into items.
    inventory_items = _itemsToDict(inventory)

    # Find the recipe and convert into items.
    recipe_items = _itemsToDict(recipes[item])

    # For each item in the recipe, see if we have enough stuff.
    num_can_craft = None
    for recipe_item in recipe_items:
        if recipe_item not in inventory_items:
            # Let's see if we can craft what we're missing.
            # If there is a recipe, how many can we make?
            how_many = _craftHowMany(recipe_item, inventory, recipes)
            # Is it enough? If so, update the number we can craft.
            if num_can_craft is None or how_many < num_can_craft:
                num_can_craft = how_many

        elif inventory_items[recipe_item] < recipe_items[recipe_item]:
            # We have the ingredient, but not enough of it.
            return 0

        else:
            # We have enough of THIS ingredient.
            can_craft = inventory_items[recipe_item] / recipe_items[recipe_item]
            if num_can_craft is None or can_craft < num_can_craft:
                num_can_craft = can_craft

    return num_can_craft if num_can_craft else 0
