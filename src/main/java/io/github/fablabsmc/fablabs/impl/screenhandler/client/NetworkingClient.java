package io.github.fablabsmc.fablabs.impl.screenhandler.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;
import io.github.fablabsmc.fablabs.impl.screenhandler.ExtendedScreenHandlerType;
import io.github.fablabsmc.fablabs.impl.screenhandler.Packets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

@Environment(EnvType.CLIENT)
public final class NetworkingClient implements ClientModInitializer {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitializeClient() {
		ClientSidePacketRegistry.INSTANCE.register(Packets.OPEN_ID, (ctx, buf) -> openScreen(buf));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void openScreen(PacketByteBuf buf) {
		int typeId = buf.readVarInt();
		int syncId = buf.readVarInt();
		Text title = buf.readText();
		ScreenHandlerType<?> type = Registry.SCREEN_HANDLER.get(typeId);

		if (type == null) {
			LOGGER.warn("[FabLabs] Unknown screen handler ID: {}", typeId);
			return;
		}

		if (!(type instanceof ExtendedScreenHandlerType<?>)) {
			LOGGER.warn("[FabLabs] Received extended opening packet for screen handler {} without extra data", Registry.SCREEN_HANDLER.getId(type));
			return;
		}

		FabricHandledScreens.Factory screenFactory = FabricHandledScreensImpl.getFactory(type);

		if (screenFactory != null) {
			MinecraftClient client = MinecraftClient.getInstance();
			PlayerEntity player = client.player;

			Screen screen = screenFactory.create(
					((ExtendedScreenHandlerType<?>) type).create(syncId, player.inventory, buf),
					player.inventory,
					title
			);

			MinecraftClient.getInstance().execute(() -> {
				player.currentScreenHandler = ((ScreenHandlerProvider<?>) screen).getScreenHandler();
				client.openScreen(screen);
			});
		} else {
			LOGGER.warn("[FabLabs] Screen not registered for screen handler {}!", title);
		}
	}
}
