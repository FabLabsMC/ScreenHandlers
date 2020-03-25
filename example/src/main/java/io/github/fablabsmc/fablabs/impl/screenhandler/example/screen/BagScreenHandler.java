package io.github.fablabsmc.fablabs.impl.screenhandler.example.screen;

import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.item.BagItem;
import net.minecraft.container.ContainerType;
import net.minecraft.container.Generic3x3Container;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class BagScreenHandler extends Generic3x3Container {
	private final BlockPos pos;

	public BagScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		this(syncId, playerInventory, new BasicInventory(9), readOptionalPos(buf));
	}

	private static BlockPos readOptionalPos(PacketByteBuf buf) {
		boolean hasPos = buf.readBoolean();
		BlockPos pos = buf.readBlockPos();
		return hasPos ? pos : null;
	}

	public BagScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, BlockPos pos) {
		super(syncId, playerInventory, inventory);
		this.pos = pos;
	}

	public BlockPos getPos() {
		return pos;
	}

	@Override
	public ContainerType<?> getType() {
		return Example.BAG_SCREEN_HANDLER;
	}

	@Override
	public ItemStack onSlotClick(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity) {
		if (slotId >= 0) { // slotId < 0 are used for networking internals
			ItemStack stack = getSlot(slotId).getStack();
			if (stack.getItem() instanceof BagItem) {
				// Prevent moving bags around
				return stack;
			}
		}

		return super.onSlotClick(slotId, clickData, actionType, playerEntity);
	}
}
