package io.github.fablabsmc.fablabs.impl.screenhandler.example;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.item.BagItem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BagScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Example implements ModInitializer {
	public static final Item BAG = new BagItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	public static final ContainerType<BagScreenHandler> BAG_SCREEN_HANDLER = ScreenHandlers.INSTANCE.createType(BagScreenHandler::new);

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("fablabs-screen-handlers-example", "bag"), BAG);
		Registry.register(Registry.CONTAINER, new Identifier("fablabs-screen-handlers-example", "bag"), BAG_SCREEN_HANDLER);
	}
}
