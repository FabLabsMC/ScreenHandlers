package io.github.fablabsmc.fablabs.impl.screenhandler.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FabricHandledScreensImpl implements FabricHandledScreens {
	public static final FabricHandledScreensImpl INSTANCE = new FabricHandledScreensImpl();

	private static final Map<ContainerType<?>, FabricHandledScreens.Factory<?, ?>> FACTORIES = new HashMap<>();

	private FabricHandledScreensImpl() { }

	public static FabricHandledScreens.Factory<?, ?> getFactory(ContainerType<?> type) {
		Objects.requireNonNull(type, "type is null");

		return FACTORIES.get(type);
	}

	@Override
	public <T extends Container, U extends Screen & ContainerProvider<T>> void register(ContainerType<T> type, Factory<T, U> screenFactory) {
		FACTORIES.put(type, screenFactory);
	}
}
