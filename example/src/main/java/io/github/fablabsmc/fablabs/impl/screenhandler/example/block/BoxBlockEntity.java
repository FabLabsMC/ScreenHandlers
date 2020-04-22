package io.github.fablabsmc.fablabs.impl.screenhandler.example.block;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BoxScreenHandler;

import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class BoxBlockEntity extends LootableContainerBlockEntity implements ExtendedScreenHandlerFactory {
	private DefaultedList<ItemStack> items = DefaultedList.ofSize(size(), ItemStack.EMPTY);

	public BoxBlockEntity() {
		super(Example.BOX_ENTITY);
	}

	@Override
	protected DefaultedList<ItemStack> getInvStackList() {
		return items;
	}

	@Override
	protected void setInvStackList(DefaultedList<ItemStack> list) {
		this.items = list;
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	protected ScreenHandler createContainer(int syncId, PlayerInventory playerInventory) {
		return new BoxScreenHandler(syncId, playerInventory, this);
	}

	@Override
	public int size() {
		return 3 * 3;
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
		buf.writeBlockPos(pos);
	}
}
