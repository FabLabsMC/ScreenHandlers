package io.github.fablabsmc.fablabs.impl.screenhandler.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FabricHandledScreensImpl implements FabricHandledScreens {
	public static final FabricHandledScreensImpl INSTANCE = new FabricHandledScreensImpl();

	private static final Map<ScreenHandlerType<?>, FabricHandledScreens.Factory<?, ?>> FACTORIES = new HashMap<>();

	private FabricHandledScreensImpl() { }

	public static FabricHandledScreens.Factory<?, ?> getFactory(ScreenHandlerType<?> type) {
		Objects.requireNonNull(type, "type is null");

		return FACTORIES.get(type);
	}

	@Override
	public <T extends ScreenHandler, U extends Screen & ScreenHandlerProvider<? extends T>> void register(ScreenHandlerType<? extends T> type, Factory<? super T, ? extends U> screenFactory) {
		FACTORIES.put(type, screenFactory);
	}
}
