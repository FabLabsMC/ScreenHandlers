package io.github.fablabsmc.fablabs.mixin.screenhandler.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;
import io.github.fablabsmc.fablabs.impl.screenhandler.client.FabricHandledScreensImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.Screens;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

@Mixin(Screens.class)
public class HandledScreensMixin {
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Inject(method = "open", at = @At("HEAD"), cancellable = true)
	private static <T extends Container> void fablabs_onOpen(ContainerType<T> type, MinecraftClient client, int syncId, Text title, CallbackInfo info) {
		if (type != null) {
			FabricHandledScreens.Factory factory = FabricHandledScreensImpl.getFactory(type);

			if (factory != null) {
				PlayerEntity player = client.player;
				Screen screen = factory.create(type.create(syncId, player.inventory), client.player.inventory, title);
				client.player.container = ((ContainerProvider<?>) screen).getContainer();
				client.openScreen(screen);

				// Cancel vanilla logic
				info.cancel();
			}
		}
	}
}
