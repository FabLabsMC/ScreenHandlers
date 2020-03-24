package io.github.fablabsmc.fablabs.impl.screenhandler;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;

import net.minecraft.container.Container;

public interface ExtendedScreenHandlerType<T extends Container> {
	ScreenHandlers.Factory<T> fablabs_getFactory();
	void fablabs_setFactory(ScreenHandlers.Factory<T> factory);
}
