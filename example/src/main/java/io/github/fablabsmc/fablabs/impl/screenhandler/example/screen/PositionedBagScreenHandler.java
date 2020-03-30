package io.github.fablabsmc.fablabs.impl.screenhandler.example.screen;

import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class PositionedBagScreenHandler extends BagScreenHandler {
	private final BlockPos pos;

	public PositionedBagScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		this(syncId, playerInventory, new BasicInventory(9), readOptionalPos(buf));
	}

	private static BlockPos readOptionalPos(PacketByteBuf buf) {
		boolean hasPos = buf.readBoolean();
		BlockPos pos = buf.readBlockPos();
		return hasPos ? pos : null;
	}

	public PositionedBagScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, BlockPos pos) {
		super(Example.POSITIONED_BAG_SCREEN_HANDLER, syncId, playerInventory, inventory);
		this.pos = pos;
	}

	public BlockPos getPos() {
		return pos;
	}
}
