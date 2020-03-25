package io.github.fablabsmc.fablabs.impl.screenhandler.example.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import net.fabricmc.api.ClientModInitializer;

public class ExampleClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricHandledScreens.INSTANCE.register(Example.BAG_SCREEN_HANDLER, BagScreen::new);
	}
}
