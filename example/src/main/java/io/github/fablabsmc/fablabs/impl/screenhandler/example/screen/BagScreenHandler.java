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

public class BagScreenHandler extends Generic3x3Container {
	private final ContainerType<?> type;

	public BagScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new BasicInventory(9));
	}

	public BagScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		this(Example.BAG_SCREEN_HANDLER, syncId, playerInventory, inventory);
	}

	protected BagScreenHandler(ContainerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(syncId, playerInventory, inventory);
		this.type = type;
	}

	@Override
	public ContainerType<?> getType() {
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
