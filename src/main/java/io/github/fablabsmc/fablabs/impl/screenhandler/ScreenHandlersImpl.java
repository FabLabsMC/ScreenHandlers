package io.github.fablabsmc.fablabs.impl.screenhandler;

import java.util.Objects;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.netty.buffer.Unpooled;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

public final class ScreenHandlersImpl implements ScreenHandlers {
	public static final ScreenHandlers INSTANCE = new ScreenHandlersImpl();

	private ScreenHandlersImpl() {
	}

	@Override
	public <T extends ScreenHandler> ScreenHandlerType<T> simple(SimpleFactory<T> factory) {
		// Wrap our factory in vanilla's factory; it will not be public for users.
		return new ScreenHandlerType<>(factory::create);
	}

	@Override
	public <T extends ScreenHandler> ScreenHandlerType<T> extended(ExtendedFactory<T> factory) {
		return new ExtendedScreenHandlerType<>(factory);
	}

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

		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(Registry.SCREEN_HANDLER.getRawId(handler.getType()));
		buf.writeVarInt(syncId);
		buf.writeText(factory.getDisplayName());
		factory.writeScreenData(buf);

		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, Packets.OPEN_ID, buf);
	}
}
