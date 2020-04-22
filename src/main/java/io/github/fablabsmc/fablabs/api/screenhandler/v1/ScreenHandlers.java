package io.github.fablabsmc.fablabs.api.screenhandler.v1;

import io.github.fablabsmc.fablabs.impl.screenhandler.ExtendedScreenHandlerType;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * An API for creating {@linkplain ScreenHandlerType screen handler types}.
 *
 * <p>This class exposes the private {@link ScreenHandlerType} constructor,
 * as well as adds support for creating types using Fabric's extended screen handler API.
 *
 * <p>Screen handlers types are used to synchronize {@linkplain ScreenHandler screen handlers}
 * between the server and the client. Screen handlers manage the items and integer properties that are
 * needed to show on screens, such as the items in a chest or the progress of a furnace.
 *
 * <h2>Simple and extended screen handlers</h2>
 * Simple screen handlers are the type of screen handlers used in vanilla.
 * They can automatically synchronize items and integer properties between the server and the client,
 * but they don't support having custom data sent in the opening packet.
 *
 * <p>This module adds <i>extended screen handlers</i> that can synchronize their own custom data
 * when they are opened, which can be useful for defining additional properties of a screen on the server.
 * For example, a mod can synchronize text that will show up as a label.
 *
 * <h2>Example</h2>
 * <pre>
 * {@code
 * // Creating the screen handler type
 * public static final ScreenHandlerType<OvenScreenHandler> OVEN = ScreenHandlers.simple(OvenScreenHandler::new);
 *
 * // Registering the type
 * Registry.register(Registry.SCREEN_HANDLER, new Identifier("my_mod", "oven"), OVEN);
 *
 * // Screen handler class
 * public class OvenScreenHandler extends ScreenHandler {
 * 	public OvenScreenHandler(int syncId) {
 * 		super(MyScreenHandlers.OVEN, syncId);
 * 	}
 * }
 * }
 * </pre>
 *
 * @see io.github.fablabsmc.fablabs.api.client.screenhandler.v1.FabricHandledScreens registering screens for screen handlers
 */
public final class ScreenHandlers {
	private ScreenHandlers() {
	}

	/**
	 * Creates a new {@code ScreenHandlerType} that creates client-sided screen handlers using the factory.
	 *
	 * @param factory the client-sided screen handler factory
	 * @param <T>     the screen handler type
	 * @return the created type object
	 */
	public static <T extends ScreenHandler> ScreenHandlerType<T> simple(SimpleClientHandlerFactory<T> factory) {
		// Wrap our factory in vanilla's factory; it will not be public for users.
		return new ScreenHandlerType<>(factory::create);
	}

	/**
	 * Creates a new {@code ScreenHandlerType} that creates client-sided screen handlers with additional
	 * networked opening data.
	 *
	 * <p>These screen handlers must be opened with a {@link ExtendedScreenHandlerFactory}.
	 *
	 * @param factory the client-sided screen handler factory
	 * @param <T>     the screen handler type
	 * @return the created type object
	 */
	public static <T extends ScreenHandler> ScreenHandlerType<T> extended(ExtendedClientHandlerFactory<T> factory) {
		return new ExtendedScreenHandlerType<>(factory);
	}

	/**
	 * A factory for client-sided screen handler instances.
	 *
	 * @param <T> the screen handler type
	 */
	public interface SimpleClientHandlerFactory<T extends ScreenHandler> {
		/**
		 * Creates a new client-sided screen handler.
		 *
		 * @param syncId    the synchronization ID
		 * @param inventory the player inventory
		 * @return the created screen handler
		 */
		@Environment(EnvType.CLIENT)
		T create(int syncId, PlayerInventory inventory);
	}

	/**
	 * A factory for client-sided screen handler instances
	 * with additional screen opening data.
	 *
	 * @param <T> the screen handler type
	 * @see ExtendedScreenHandlerFactory
	 */
	public interface ExtendedClientHandlerFactory<T extends ScreenHandler> {
		/**
		 * Creates a new client-sided screen handler with additional screen opening data.
		 *
		 * @param syncId    the synchronization ID
		 * @param inventory the player inventory
		 * @param buf       the packet buffer
		 * @return the created screen handler
		 */
		@Environment(EnvType.CLIENT)
		T create(int syncId, PlayerInventory inventory, PacketByteBuf buf);
	}
}
