package io.github.fablabsmc.fablabs.impl.screenhandler;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;

import net.minecraft.container.Container;

public interface ScreenHandlerTypeBridge<T extends Container> {
	ScreenHandlers.Factory<T> fablabs_getFactory();
	void fablabs_setFactory(ScreenHandlers.Factory<T> factory);

	boolean fablabs_hasExtraData();
	void fablabs_setHasExtraData(boolean hasExtraData);
}
