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

    # Your code goes here!

    return False
