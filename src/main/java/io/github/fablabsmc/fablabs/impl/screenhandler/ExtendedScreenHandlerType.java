package io.github.fablabsmc.fablabs.impl.screenhandler;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public final class ExtendedScreenHandlerType<T extends ScreenHandler> extends ScreenHandlerType<T> {
	private final ScreenHandlers.ExtendedFactory<T> factory;

	public ExtendedScreenHandlerType(ScreenHandlers.ExtendedFactory<T> factory) {
		super(null);
		this.factory = factory;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public T create(int syncId, PlayerInventory inventory) {
		throw new UnsupportedOperationException("Use ExtendedScreenHandlerType.create(int, PlayerInventory, PacketByteBuf)!");
	}

	@Environment(EnvType.CLIENT)
	public T create(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		return factory.create(syncId, inventory, buf);
	}
}
