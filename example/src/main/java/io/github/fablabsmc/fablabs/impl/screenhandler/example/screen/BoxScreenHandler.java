package io.github.fablabsmc.fablabs.impl.screenhandler.example.screen;

import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

public class BoxScreenHandler extends Generic3x3ContainerScreenHandler implements PositionedScreenHandler {
	private final BlockPos pos;

	public BoxScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		super(syncId, playerInventory);
		this.pos = buf.readBlockPos();
	}

	public BoxScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(syncId, playerInventory, inventory);
		this.pos = BlockPos.ORIGIN;
	}

	@Override
	public BlockPos getPos() {
		return pos;
	}

	@Override
	public ScreenHandlerType<?> getType() {
		return Example.BOX_SCREEN_HANDLER;
	}
}
