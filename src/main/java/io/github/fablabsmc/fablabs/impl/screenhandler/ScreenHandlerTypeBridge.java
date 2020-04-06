package io.github.fablabsmc.fablabs.impl.screenhandler;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;

import net.minecraft.screen.ScreenHandler;

public interface ScreenHandlerTypeBridge<T extends ScreenHandler> {
	ScreenHandlers.ExtendedFactory<T> fablabs_getFactory();
	void fablabs_setFactory(ScreenHandlers.ExtendedFactory<T> factory);

	boolean fablabs_hasExtraData();
	void fablabs_setHasExtraData(boolean hasExtraData);
}
