package io.github.fablabsmc.fablabs.impl.screenhandler.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FabricHandledScreensImpl implements FabricHandledScreens {
	public static final FabricHandledScreens INSTANCE = new FabricHandledScreensImpl();

	private FabricHandledScreensImpl() {
	}

	@Override
	public <T extends ScreenHandler, U extends Screen & ScreenHandlerProvider<T>> void register(ScreenHandlerType<? extends T> type, Factory<? super T, ? extends U> screenFactory) {
		// Convert our factory to the vanilla provider here as it won't be available to modders.
		HandledScreens.register(type, screenFactory::create);
	}
}
