package io.github.fablabsmc.fablabs.impl.screenhandler.example.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;

public class ExampleClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricHandledScreens.INSTANCE.register(Example.BAG_SCREEN_HANDLER, Generic3x3ContainerScreen::new);
	}
}
