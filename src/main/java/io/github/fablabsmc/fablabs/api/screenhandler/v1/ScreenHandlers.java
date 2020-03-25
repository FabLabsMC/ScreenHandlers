package io.github.fablabsmc.fablabs.api.screenhandler.v1;

import java.util.function.Consumer;

import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlersImpl;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.PacketByteBuf;

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
	<T extends Container> ContainerType<T> createType(SimpleFactory<T> factory);

	/**
	 * Creates a new {@code ScreenHandlerType} that creates client-sided screen handlers using the factory.
	 *
	 * <p>These screen handlers must be opened through {@link #open} instead of {@link PlayerEntity#openContainer}.
	 *
	 * @param factory the client-sided screen handler factory
	 * @param <T>     the screen handler type
	 * @return the created type object
	 */
	<T extends Container> ContainerType<T> createExtendedType(Factory<T> factory);

	/**
	 * Opens a screen handler with additional networked data.
	 * The {@code packetWriter} is used to write the data to the packet.
	 *
	 * <p>This is a no-op method on the client.
	 *
	 * @param player       the player
	 * @param factory      the screen handler factory
	 * @param packetWriter the packet writer
	 */
	void open(PlayerEntity player, NameableContainerFactory factory, Consumer<PacketByteBuf> packetWriter);

	/**
	 * A factory for client-sided screen handler instances.
	 *
	 * @param <T> the screen handler type
	 */
	interface SimpleFactory<T extends Container> {
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
	 * with additional synced data.
	 *
	 * @param <T> the screen handler type
	 */
	interface Factory<T extends Container> {
		/**
		 * Creates a new client-sided screen handler with custom data.
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
