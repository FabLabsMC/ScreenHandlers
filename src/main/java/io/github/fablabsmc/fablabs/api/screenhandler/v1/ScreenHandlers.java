package io.github.fablabsmc.fablabs.api.screenhandler.v1;

import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlersImpl;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerInventory;

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
	 * @param <T> the screen handler type
	 * @return the created type object
	 */
	<T extends Container> ContainerType<T> createType(Factory<T> factory);

	/**
	 * A factory for client-sided screen handler instances.
	 *
	 * @param <T> the screen handler type
	 */
	interface Factory<T extends Container> {
		/**
		 * Creates a new client-sided screen handler.
		 *
		 * @param syncId the synchronization ID
		 * @param inventory the player inventory
		 * @return the created screen handler
		 */
		@Environment(EnvType.CLIENT)
		T create(int syncId, PlayerInventory inventory);
	}
}
