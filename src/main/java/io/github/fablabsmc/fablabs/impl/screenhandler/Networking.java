package io.github.fablabsmc.fablabs.impl.screenhandler;

import java.util.Objects;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.netty.buffer.Unpooled;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

public final class Networking {
	// [Packet format]
	// typeId: identifier
	// syncId: varInt
	// title: text
	// customData: buf
	public static final Identifier OPEN_ID = new Identifier("fablabs-screen-handlers-v1", "open_screen");

	/**
	 * Opens an extended screen handler by sending a custom packet to the client.
	 *
	 * @param player  the player
	 * @param factory the screen handler factory
	 * @param handler the screen handler instance
	 * @param syncId  the synchronization ID
	 */
	public static void sendOpenPacket(ServerPlayerEntity player, ExtendedScreenHandlerFactory factory, ScreenHandler handler, int syncId) {
		Objects.requireNonNull(player, "player is null");
		Objects.requireNonNull(factory, "factory is null");
		Objects.requireNonNull(handler, "handler is null");

		Identifier typeId = Registry.SCREEN_HANDLER.getId(handler.getType());

		if (typeId == null) {
			throw new IllegalArgumentException("Trying to open unregistered screen handler " + handler);
		}

		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeIdentifier(typeId);
		buf.writeVarInt(syncId);
		buf.writeText(factory.getDisplayName());
		factory.writeScreenData(buf);

		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, OPEN_ID, buf);
	}
}
