package io.github.fablabsmc.fablabs.api.screenhandler.v1.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * An utility for registering screens with screen handlers.
 */
@Environment(EnvType.CLIENT)
public final class FabricHandledScreens {
	private FabricHandledScreens() {
	}

	/**
	 * Registers a new screen factory for a screen handler type.
	 *
	 * @param type          the screen handler type object
	 * @param screenFactory the screen handler factory
	 * @param <H>           the screen handler type
	 * @param <S>           the screen type
	 */
	public static <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> void register(ScreenHandlerType<? extends H> type, Factory<? super H, ? extends S> screenFactory) {
		// Convert our factory to the vanilla provider here as it won't be available to modders.
		HandledScreens.<H, S>register(type, screenFactory::create);
	}

	/**
	 * A factory for handled screens.
	 *
	 * @param <H> the screen handler type
	 * @param <S> the screen type
	 */
	@FunctionalInterface
	public interface Factory<H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> {
		/**
		 * Creates a new handled screen.
		 *
		 * @param handler   the screen handler
		 * @param inventory the player inventory
		 * @param title     the title of the screen
		 * @return the created screen
		 */
		S create(H handler, PlayerInventory inventory, Text title);
	}
}
