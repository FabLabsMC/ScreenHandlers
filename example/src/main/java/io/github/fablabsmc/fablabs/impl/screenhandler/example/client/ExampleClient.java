package io.github.fablabsmc.fablabs.impl.screenhandler.example.client;

import io.github.fablabsmc.fablabs.api.client.screenhandler.v1.ScreenRegistry;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.Example;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BoxScreenHandler;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedBagScreenHandler;

import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;

import net.fabricmc.api.ClientModInitializer;

public class ExampleClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(Example.BAG_SCREEN_HANDLER, Generic3x3ContainerScreen::new);
		ScreenRegistry.<PositionedBagScreenHandler, PositionedScreen<PositionedBagScreenHandler>>register(
				Example.POSITIONED_BAG_SCREEN_HANDLER,
				PositionedScreen::new
		);
		ScreenRegistry.<BoxScreenHandler, PositionedScreen<BoxScreenHandler>>register(
				Example.BOX_SCREEN_HANDLER,
				PositionedScreen::new
		);
	}
}
