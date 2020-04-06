package io.github.fablabsmc.fablabs.impl.screenhandler.example.item;

import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BagScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BagItem extends Item {
	public BagItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		user.openHandledScreen(createScreenHandlerFactory(stack));
		return TypedActionResult.success(stack);
	}

	private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
			return new BagScreenHandler(syncId, inventory, new BagInventory(stack));
		}, stack.getName());
	}
}
