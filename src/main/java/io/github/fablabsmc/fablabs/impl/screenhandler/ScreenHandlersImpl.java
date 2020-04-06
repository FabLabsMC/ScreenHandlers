package io.github.fablabsmc.fablabs.impl.screenhandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.OptionalInt;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.mixin.screenhandler.ServerPlayerEntityAccessor;
import io.netty.buffer.Unpooled;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

public final class ScreenHandlersImpl implements ScreenHandlers {
	public static final ScreenHandlersImpl INSTANCE = new ScreenHandlersImpl();

	private static final Lazy<MethodHandle> CONSTRUCTOR = new Lazy<>(() -> {
		try {
			Constructor<?> ctor = ScreenHandlerType.class.getDeclaredConstructors()[0];
			ctor.setAccessible(true);
			return MethodHandles.lookup().unreflectConstructor(ctor);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Could not find private ScreenHandlerType constructor!", e);
		}
	});

	private ScreenHandlersImpl() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ScreenHandler> ScreenHandlerType<T> simple(SimpleFactory<T> factory) {
		try {
			return new ScreenHandlerType<>(factory::create);
		} catch (Throwable t) {
			throw new RuntimeException("Could not construct ScreenHandlerType!", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ScreenHandler> ScreenHandlerType<T> extended(ExtendedFactory<T> factory) {
		try {
			return new ExtendedScreenHandlerType<>(factory);
		} catch (Throwable t) {
			throw new RuntimeException("Could not construct ScreenHandlerType!", t);
		}
	}

	public OptionalInt open(ServerPlayerEntity player, ExtendedScreenHandlerFactory factory) {
		Objects.requireNonNull(player, "player is null");
		Objects.requireNonNull(factory, "factory is null");
		ServerPlayerEntityAccessor bridge = (ServerPlayerEntityAccessor) player;

		if (player.currentScreenHandler != player.playerScreenHandler) {
			player.closeHandledScreen();
		}

		bridge.callIncrementScreenHandlerSyncId();
		int syncId = bridge.getScreenHandlerSyncId();
		ScreenHandler handler = factory.createMenu(syncId, player.inventory, player);

		if (handler == null) {
			if (player.isSpectator()) {
				player.addMessage((new TranslatableText("container.spectatorCantOpen")).formatted(Formatting.RED), true);
			}

			return OptionalInt.empty();
		}

		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(Registry.SCREEN_HANDLER.getRawId(handler.getType()));
		buf.writeVarInt(syncId);
		buf.writeText(factory.getDisplayName());
		factory.writeScreenData(buf);

		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, Packets.OPEN_ID, buf);
		handler.addListener(player);
		player.currentScreenHandler = handler;
		return OptionalInt.of(syncId);
	}
}
