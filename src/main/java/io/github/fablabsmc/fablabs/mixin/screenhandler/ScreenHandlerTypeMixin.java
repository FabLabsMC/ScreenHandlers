package io.github.fablabsmc.fablabs.mixin.screenhandler;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlerTypeBridge;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;

@Mixin(ContainerType.class)
public class ScreenHandlerTypeMixin<T extends Container> implements ScreenHandlerTypeBridge<T> {
	private ScreenHandlers.ExtendedFactory<T> fablabs_factory = null;
	private boolean fablabs_hasExtraData = false;

	@Override
	public ScreenHandlers.ExtendedFactory<T> fablabs_getFactory() {
		return fablabs_factory;
	}

	@Override
	public void fablabs_setFactory(ScreenHandlers.ExtendedFactory<T> factory) {
		this.fablabs_factory = factory;
	}

	@Override
	public boolean fablabs_hasExtraData() {
		return fablabs_hasExtraData;
	}

	@Override
	public void fablabs_setHasExtraData(boolean hasExtraData) {
		this.fablabs_hasExtraData = hasExtraData;
	}
}
