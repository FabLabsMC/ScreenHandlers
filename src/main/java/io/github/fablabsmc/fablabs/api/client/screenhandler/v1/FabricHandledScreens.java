package io.github.fablabsmc.fablabs.api.client.screenhandler.v1;

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
 * An API for registering screens that represent screen handlers on the client.
 * Exposes vanilla's private {@link HandledScreens#register HandledScreens.register()} to modders as {@link #register FabricHandledScreens.register()}.
 *
 * <h2>Example</h2>
 * <pre>
 * {@code
 * // In a client-side initialization method:
 * FabricHandledScreens.register(MyScreenHandlers.OVEN, OvenScreen::new);
 *
 * // Screen class
 * public class OvenScreen extends HandledScreen<OvenScreenHandler> {
 * 	public OvenScreen(OvenScreenHandler handler, PlayerInventory inventory, Text title) {
 * 		super(handler, inventory, title);
 * 	}
 * }
 * }
 * </pre>
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
