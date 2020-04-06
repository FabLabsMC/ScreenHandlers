package io.github.fablabsmc.fablabs.api.screenhandler.v1;

import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlersImpl;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * An utility for creating screen handler types.
 */
public interface ScreenHandlers {
	ScreenHandlers INSTANCE = ScreenHandlersImpl.INSTANCE;

	/**
	 * Creates a new {@code ScreenHandlerType} that creates client-sided screen handlers using the factory.
	 *
	 * @param factory the client-sided screen handler factory
	 * @param <T>     the screen handler type
	 * @return the created type object
	 */
	<T extends ScreenHandler> ScreenHandlerType<T> simple(SimpleFactory<T> factory);

	/**
	 * Creates a new {@code ScreenHandlerType} that creates client-sided screen handlers with additional
	 * networked opening data.
	 *
	 * <p>These screen handlers must be opened with a {@link ExtendedScreenHandlerFactory}.
	 *
	 * @param factory the client-sided screen handler factory
	 * @param <T>     the screen handler type
	 * @return the created type object
	 */
	<T extends ScreenHandler> ScreenHandlerType<T> extended(ExtendedFactory<T> factory);

	/**
	 * A factory for client-sided screen handler instances.
	 *
	 * @param <T> the screen handler type
	 */
	interface SimpleFactory<T extends ScreenHandler> {
		/**
		 * Creates a new client-sided screen handler.
		 *
		 * @param syncId    the synchronization ID
		 * @param inventory the player inventory
		 * @return the created screen handler
		 */
		@Environment(EnvType.CLIENT)
		T create(int syncId, PlayerInventory inventory);
	}

	/**
	 * A factory for client-sided screen handler instances
	 * with additional synced opening data.
	 *
	 * @param <T> the screen handler type
	 * @see ExtendedScreenHandlerFactory
	 */
	interface ExtendedFactory<T extends ScreenHandler> {
		/**
		 * Creates a new client-sided screen handler with additional opening data.
		 *
		 * @param syncId    the synchronization ID
		 * @param inventory the player inventory
		 * @param buf       the packet buffer
		 * @return the created screen handler
		 */
		@Environment(EnvType.CLIENT)
		T create(int syncId, PlayerInventory inventory, PacketByteBuf buf);
	}
}
