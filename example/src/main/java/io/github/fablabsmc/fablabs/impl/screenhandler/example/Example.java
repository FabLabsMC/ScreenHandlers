package io.github.fablabsmc.fablabs.impl.screenhandler.example;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
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
	public static final ScreenHandlerType<BagScreenHandler> BAG_SCREEN_HANDLER = ScreenHandlers.simple(BagScreenHandler::new);
	public static final ScreenHandlerType<PositionedBagScreenHandler> POSITIONED_BAG_SCREEN_HANDLER = ScreenHandlers.extended(PositionedBagScreenHandler::new);
	public static final ScreenHandlerType<BoxScreenHandler> BOX_SCREEN_HANDLER = ScreenHandlers.extended(BoxScreenHandler::new);

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(ID, "bag"), BAG);
		Registry.register(Registry.ITEM, new Identifier(ID, "positioned_bag"), POSITIONED_BAG);
		Registry.register(Registry.BLOCK, new Identifier(ID, "box"), BOX);
		Registry.register(Registry.ITEM, new Identifier(ID, "box"), BOX_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ID, "box"), BOX_ENTITY);
		Registry.register(Registry.SCREEN_HANDLER, new Identifier(ID, "bag"), BAG_SCREEN_HANDLER);
		Registry.register(Registry.SCREEN_HANDLER, new Identifier(ID, "positioned_bag"), POSITIONED_BAG_SCREEN_HANDLER);
		Registry.register(Registry.SCREEN_HANDLER, new Identifier(ID, "box"), BOX_SCREEN_HANDLER);
	}
}
