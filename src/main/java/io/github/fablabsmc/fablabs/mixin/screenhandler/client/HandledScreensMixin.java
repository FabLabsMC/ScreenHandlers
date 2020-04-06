package io.github.fablabsmc.fablabs.mixin.screenhandler.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;
import io.github.fablabsmc.fablabs.impl.screenhandler.client.FabricHandledScreensImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

@Mixin(HandledScreens.class)
public class HandledScreensMixin {
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Inject(method = "open", at = @At("HEAD"), cancellable = true)
	private static <T extends ScreenHandler> void fablabs_onOpen(ScreenHandlerType<T> type, MinecraftClient client, int syncId, Text title, CallbackInfo info) {
		if (type != null) {
			FabricHandledScreens.Factory factory = FabricHandledScreensImpl.getFactory(type);

			if (factory != null) {
				PlayerEntity player = client.player;
				Screen screen = factory.create(type.create(syncId, player.inventory), client.player.inventory, title);
				client.player.currentScreenHandler = ((ScreenHandlerProvider<?>) screen).getScreenHandler();
				client.openScreen(screen);

				// Cancel vanilla logic
				info.cancel();
			}
		}
	}
}
