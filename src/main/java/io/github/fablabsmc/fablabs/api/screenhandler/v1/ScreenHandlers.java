package io.github.fablabsmc.fablabs.api.screenhandler.v1;

import java.util.function.BiFunction;

import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlersImpl;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerInventory;

/**
 * An utility for creating screen handler types.
 */
public interface ScreenHandlers {
	ScreenHandlers INSTANCE = ScreenHandlersImpl.INSTANCE;

	/**
	 * Creates a new {@code ScreenHandlerType} that creates client-sided screen handlers using the factory.
	 *
	 * @param factory the client-sided screen handler factory
	 * @param <T> the screen handler type
	 * @return the created type object
	 */
	<T extends Container> ContainerType<T> createType(BiFunction<? super Integer, ? super PlayerInventory, ? extends T> factory);
}
