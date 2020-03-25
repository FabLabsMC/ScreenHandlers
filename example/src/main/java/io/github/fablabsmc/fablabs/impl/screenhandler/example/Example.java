package io.github.fablabsmc.fablabs.impl.screenhandler.example;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.item.BagItem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.item.PositionedBagItem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BagScreenHandler;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedBagScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Example implements ModInitializer {
	public static final Item BAG = new BagItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	public static final Item POSITIONED_BAG = new PositionedBagItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	public static final ContainerType<BagScreenHandler> BAG_SCREEN_HANDLER = ScreenHandlers.INSTANCE.simple(BagScreenHandler::new);
	public static final ContainerType<PositionedBagScreenHandler> POSITIONED_BAG_SCREEN_HANDLER = ScreenHandlers.INSTANCE.extended(PositionedBagScreenHandler::new);

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("fablabs-screen-handlers-example", "bag"), BAG);
		Registry.register(Registry.ITEM, new Identifier("fablabs-screen-handlers-example", "positioned_bag"), POSITIONED_BAG);
		Registry.register(Registry.CONTAINER, new Identifier("fablabs-screen-handlers-example", "bag"), BAG_SCREEN_HANDLER);
		Registry.register(Registry.CONTAINER, new Identifier("fablabs-screen-handlers-example", "positioned_bag"), POSITIONED_BAG_SCREEN_HANDLER);
	}
}
