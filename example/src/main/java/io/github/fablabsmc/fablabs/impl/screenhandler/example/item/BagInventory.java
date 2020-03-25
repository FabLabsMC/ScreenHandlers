package io.github.fablabsmc.fablabs.impl.screenhandler.example.item;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

final class BagInventory implements ImplementedInventory {
	private final ItemStack stack;
	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);

	BagInventory(ItemStack stack) {
		this.stack = stack;
		CompoundTag tag = stack.getSubTag("Items");
		if (tag != null) {
			Inventories.fromTag(tag, items);
		}
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	@Override
	public void markDirty() {
		CompoundTag tag = stack.getOrCreateSubTag("Items");
		Inventories.toTag(tag, items);
	}
}
