package io.github.fablabsmc.fablabs.impl.screenhandler;

import java.util.function.BiFunction;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.mixin.screenhandler.ScreenHandlerTypeAccessor;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerInventory;

public enum ScreenHandlersImpl implements ScreenHandlers {
	INSTANCE;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Container> ContainerType<T> createType(BiFunction<? super Integer, ? super PlayerInventory, ? extends T> factory) {
		ContainerType<T> result = ScreenHandlerTypeAccessor.construct(null);
		((ExtendedScreenHandlerType<T>) result).fablabs_setFactory(factory);
		return result;
	}
}
