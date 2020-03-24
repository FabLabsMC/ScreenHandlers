package io.github.fablabsmc.fablabs.api.screenhandler.v1.client;

import io.github.fablabsmc.fablabs.impl.screenhandler.client.FabricHandledScreensImpl;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * An utility for registering screens with screen handlers.
 */
@Environment(EnvType.CLIENT)
public interface FabricHandledScreens {
	FabricHandledScreens INSTANCE = FabricHandledScreensImpl.INSTANCE;

	/**
	 * Registers a new screen factory for a screen handler type.
	 *
	 * @param type          the screen handler type object
	 * @param screenFactory the screen handler factory
	 * @param <T>           the screen handler type
	 * @param <U>           the screen type
	 */
	<T extends Container, U extends Screen & ContainerProvider<T>> void register(ContainerType<T> type, Factory<T, U> screenFactory);

	/**
	 * A factory for handled screens.
	 *
	 * @param <T> the screen handler type
	 * @param <U> the screen type
	 */
	@FunctionalInterface
	interface Factory<T extends Container, U extends Screen & ContainerProvider<T>> {
		/**
		 * Creates a new handled screen.
		 *
		 * @param handler   the screen handler
		 * @param inventory the player inventory
		 * @param title     the title of the screen
		 * @return the created screen
		 */
		U create(T handler, PlayerInventory inventory, Text title);
	}
}
