package io.github.fablabsmc.fablabs.impl.screenhandler.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.api.screenhandler.v1.client.FabricHandledScreens;
import io.github.fablabsmc.fablabs.impl.screenhandler.Networking;
import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlerTypeBridge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.PacketByteBuf;
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
		ClientSidePacketRegistry.INSTANCE.register(Networking.OPEN_ID, (ctx, buf) -> openScreen(buf));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void openScreen(PacketByteBuf buf) {
		int typeId = buf.readVarInt();
		int syncId = buf.readVarInt();
		Text title = buf.readText();
		ContainerType<?> type = Registry.CONTAINER.get(typeId);

		if (type == null) {
			LOGGER.warn("[FabLabs] Unknown screen handler ID: {}", typeId);
			return;
		}

		ScreenHandlerTypeBridge<?> bridge = (ScreenHandlerTypeBridge<?>) type;

		if (!bridge.fablabs_hasExtraData()) {
			LOGGER.warn("[FabLabs] Screen handler {} does not have extra data", Registry.CONTAINER.getId(type));
			return;
		}

		FabricHandledScreens.Factory screenFactory = FabricHandledScreensImpl.getFactory(type);

		if (screenFactory != null) {
			MinecraftClient client = MinecraftClient.getInstance();
			PlayerEntity player = client.player;

			ScreenHandlers.Factory<?> factory = bridge.fablabs_getFactory();
			Screen screen = screenFactory.create(
					factory.create(syncId, player.inventory, buf),
					player.inventory,
					title
			);

			player.container = ((ContainerProvider<?>) screen).getContainer();
			client.openScreen(screen);
		} else {
			LOGGER.warn("[FabLabs] Screen not registered for screen handler {}!", title);
		}
	}
}
