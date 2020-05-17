package io.github.fablabsmc.fablabs.impl.screenhandler.example;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlerRegistry;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.block.BoxBlock;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.block.BoxBlockEntity;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.item.BagItem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.item.PositionedBagItem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BagScreenHandler;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BoxScreenHandler;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedBagScreenHandler;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.ModInitializer;

public class Example implements ModInitializer {
	public static final String ID = "fablabs-screen-handlers-example";

	public static final Item BAG = new BagItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	public static final Item POSITIONED_BAG = new PositionedBagItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
	public static final Block BOX = new BoxBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD));
	public static final Item BOX_ITEM = new BlockItem(BOX, new Item.Settings().group(ItemGroup.DECORATIONS));
	public static final BlockEntityType<?> BOX_ENTITY = BlockEntityType.Builder.create(BoxBlockEntity::new, BOX).build(null);
	public static final ScreenHandlerType<BagScreenHandler> BAG_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(id("bag"), BagScreenHandler::new);
	public static final ScreenHandlerType<PositionedBagScreenHandler> POSITIONED_BAG_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(id("positioned_bag"), PositionedBagScreenHandler::new);
	public static final ScreenHandlerType<BoxScreenHandler> BOX_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(id("box"), BoxScreenHandler::new);

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, id("bag"), BAG);
		Registry.register(Registry.ITEM, id("positioned_bag"), POSITIONED_BAG);
		Registry.register(Registry.BLOCK, id("box"), BOX);
		Registry.register(Registry.ITEM, id("box"), BOX_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, id("box"), BOX_ENTITY);
	}
}
