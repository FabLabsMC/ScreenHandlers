package io.github.fablabsmc.fablabs.api.screenhandler.v1;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * An extension of {@code NamedScreenHandlerFactory} that can write additional
 * networking data to a screen opening packet.
 *
 * @see FabricScreenHandlerTypes#extended(net.minecraft.util.Identifier, FabricScreenHandlerTypes.ExtendedClientHandlerFactory)
 */
public interface ExtendedScreenHandlerFactory extends NamedScreenHandlerFactory {
	/**
	 * Writes additional server -> client screen opening data to the buffer.
	 *
	 * @param player the player that is opening the screen
	 * @param buf    the packet buffer
	 */
	void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf);
}
