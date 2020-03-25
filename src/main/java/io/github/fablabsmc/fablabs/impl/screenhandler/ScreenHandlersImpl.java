package io.github.fablabsmc.fablabs.impl.screenhandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.OptionalInt;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.NetworkedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.mixin.screenhandler.ServerPlayerEntityAccessor;
import io.netty.buffer.Unpooled;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Lazy;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

public final class ScreenHandlersImpl implements ScreenHandlers {
	public static final ScreenHandlersImpl INSTANCE = new ScreenHandlersImpl();

	private static final Lazy<MethodHandle> CONSTRUCTOR = new Lazy<>(() -> {
		try {
			Constructor<?> ctor = ContainerType.class.getDeclaredConstructors()[0];
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
	public <T extends Container> ContainerType<T>  simple(SimpleFactory<T> factory) {
		try {
			ContainerType<T> result = (ContainerType<T>) CONSTRUCTOR.get().invoke(null);
			((ScreenHandlerTypeBridge<T>) result).fablabs_setFactory(((syncId, inventory, buf) -> factory.create(syncId, inventory)));
			return result;
		} catch (Throwable t) {
			throw new RuntimeException("Could not construct ScreenHandlerType!", t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Container> ContainerType<T> extended(ExtendedFactory<T> factory) {
		try {
			ContainerType<T> result = (ContainerType<T>) CONSTRUCTOR.get().invoke(null);
			ScreenHandlerTypeBridge<T> bridge = (ScreenHandlerTypeBridge<T>) result;
			bridge.fablabs_setFactory(factory);
			bridge.fablabs_setHasExtraData(true);
			return result;
		} catch (Throwable t) {
			throw new RuntimeException("Could not construct ScreenHandlerType!", t);
		}
	}

	public OptionalInt open(ServerPlayerEntity player, NetworkedScreenHandlerFactory factory) {
		Objects.requireNonNull(player, "player is null");
		Objects.requireNonNull(factory, "factory is null");
		ServerPlayerEntityAccessor bridge = (ServerPlayerEntityAccessor) player;

		if (player.container != player.playerContainer) {
			player.closeContainer();
		}

		bridge.callIncrementContainerSyncId();
		int syncId = bridge.getContainerSyncId();
		Container handler = factory.createMenu(syncId, player.inventory, player);

		if (handler == null) {
			if (player.isSpectator()) {
				player.addChatMessage((new TranslatableText("container.spectatorCantOpen")).formatted(Formatting.RED), true);
			}

			return OptionalInt.empty();
		}

		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(Registry.CONTAINER.getRawId(handler.getType()));
		buf.writeVarInt(syncId);
		buf.writeText(factory.getDisplayName());
		factory.writeExtraData(buf);

		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, Packets.OPEN_ID, buf);
		handler.addListener(player);
		player.container = handler;
		return OptionalInt.of(syncId);
	}
}
