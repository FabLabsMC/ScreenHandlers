package io.github.fablabsmc.fablabs.api.screenhandler.v1;

import java.util.function.Consumer;

import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.PacketByteBuf;

/**
 * An extension of {@code NamedScreenHandlerFactory} that can write additional
 * networking data to a screen opening packet.
 *
 * @see ScreenHandlers#createExtendedType(ScreenHandlers.Factory)
 */
public interface NetworkedScreenHandlerFactory extends NameableContainerFactory {
	/**
	 * Writes additional server -> client networked data to the buffer.
	 */
	void writeExtraData(PacketByteBuf buf);

	/**
	 * Creates a networked screen handler factory from a base factory and a packet writer.
	 *
	 * <p>All {@link NameableContainerFactory} operations are delegated to the base factory,
	 * and {@link #writeExtraData(PacketByteBuf)} is delegated to the writer.
	 *
	 * @param factory      the base factory
	 * @param packetWriter the packet writer
	 * @return the created factory
	 */
	static NetworkedScreenHandlerFactory of(NameableContainerFactory factory, Consumer<PacketByteBuf> packetWriter) {
		return new NetworkedScreenHandlerFactory() {
			@Override
			public Container createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
				return factory.createMenu(syncId, inventory, player);
			}

			@Override
			public Text getDisplayName() {
				return factory.getDisplayName();
			}

			@Override
			public void writeExtraData(PacketByteBuf buf) {
				packetWriter.accept(buf);
			}
		};
	}
}
