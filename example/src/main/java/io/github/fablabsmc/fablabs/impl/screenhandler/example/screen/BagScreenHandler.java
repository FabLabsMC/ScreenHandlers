package io.github.fablabsmc.fablabs.impl.screenhandler.example.screen;

import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.item.BagItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;

public class BagScreenHandler extends Generic3x3ContainerScreenHandler {
	private final ScreenHandlerType<?> type;

	public BagScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new BasicInventory(9));
	}

	public BagScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		this(Example.BAG_SCREEN_HANDLER, syncId, playerInventory, inventory);
	}

	protected BagScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(syncId, playerInventory, inventory);
		this.type = type;
	}

	@Override
	public ScreenHandlerType<?> getType() {
		return type;
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
