# Example project

This project includes example code for three different variants of screen handlers:

1. A block with extended screen handler data.
  - [`BoxBlockEntity`](src/main/java/io/github/fablabsmc/fablabs/impl/screenhandler/example/block/BoxBlockEntity.java)
    is a block entity that implements `ExtendedScreenHandlerFactory` for syncing its position.
2. An item with no extra data.
  - A simple [bag](src/main/java/io/github/fablabsmc/fablabs/impl/screenhandler/example/item/BagItem.java).
3. An item with extra screen handler data.
  - A [bag](src/main/java/io/github/fablabsmc/fablabs/impl/screenhandler/example/item/PositionedBagItem.java) that shows the clicked position.

See [the main mod class](src/main/java/io/github/fablabsmc/fablabs/impl/screenhandler/example/Example.java) for registration.
