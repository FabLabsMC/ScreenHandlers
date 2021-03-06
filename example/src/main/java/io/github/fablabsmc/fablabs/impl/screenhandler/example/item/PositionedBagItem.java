package io.github.fablabsmc.fablabs.impl.screenhandler.example.item;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedBagScreenHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PositionedBagItem extends BagItem {
	public PositionedBagItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		user.openHandledScreen(createScreenHandlerFactory(stack, null));
		return TypedActionResult.success(stack);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity user = context.getPlayer();
		ItemStack stack = user.getStackInHand(context.getHand());
		BlockPos pos = context.getBlockPos();
		user.openHandledScreen(createScreenHandlerFactory(stack, pos));
		return ActionResult.SUCCESS;
	}

	private ExtendedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack, BlockPos pos) {
		return new ExtendedScreenHandlerFactory() {
			@Override
			public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
				return new PositionedBagScreenHandler(syncId, inventory, new BagInventory(stack), pos);
			}

			@Override
			public Text getDisplayName() {
				return stack.getName();
			}

			@Override
			public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
				buf.writeBoolean(pos != null);
				buf.writeBlockPos(pos != null ? pos : BlockPos.ORIGIN);
			}
		};
	}
}
