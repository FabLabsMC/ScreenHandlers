package io.github.fablabsmc.fablabs.impl.screenhandler.example.item;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BagScreenHandler;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.container.SimpleNamedContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BagItem extends Item {
	public BagItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		ScreenHandlers.INSTANCE.open(user, createScreenHandlerFactory(stack, null), buf -> {
			buf.writeBoolean(false);
			buf.writeBlockPos(BlockPos.ORIGIN);
		});
		return TypedActionResult.success(stack);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity user = context.getPlayer();
		ItemStack stack = user.getStackInHand(context.getHand());
		BlockPos pos = context.getBlockPos();
		ScreenHandlers.INSTANCE.open(user, createScreenHandlerFactory(stack, pos), buf -> {
			buf.writeBoolean(true);
			buf.writeBlockPos(pos);
		});
		return ActionResult.SUCCESS;
	}

	private NameableContainerFactory createScreenHandlerFactory(ItemStack stack, BlockPos pos) {
		return new SimpleNamedContainerFactory((syncId, inventory, player) -> {
			return new BagScreenHandler(syncId, inventory, new BagInventory(stack), pos);
		}, stack.getName());
	}
}
