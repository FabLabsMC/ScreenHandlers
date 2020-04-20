package io.github.fablabsmc.fablabs.impl.screenhandler.example.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BoxScreenHandler;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedBagScreenHandler;

import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;

import net.fabricmc.api.ClientModInitializer;

public class ExampleClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricHandledScreens.register(Example.BAG_SCREEN_HANDLER, Generic3x3ContainerScreen::new);
		FabricHandledScreens.<PositionedBagScreenHandler, PositionedScreen<PositionedBagScreenHandler>>register(
				Example.POSITIONED_BAG_SCREEN_HANDLER,
				PositionedScreen::new
		);
		FabricHandledScreens.<BoxScreenHandler, PositionedScreen<BoxScreenHandler>>register(
				Example.BOX_SCREEN_HANDLER,
				PositionedScreen::new
		);
	}
}
