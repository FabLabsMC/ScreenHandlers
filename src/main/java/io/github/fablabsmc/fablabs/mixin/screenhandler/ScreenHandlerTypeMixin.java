package io.github.fablabsmc.fablabs.mixin.screenhandler;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.impl.screenhandler.ExtendedScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;

@Mixin(ContainerType.class)
public class ScreenHandlerTypeMixin<T extends Container> implements ExtendedScreenHandlerType<T> {
	private ScreenHandlers.Factory<T> fablabs_factory = null;

	@Override
	public ScreenHandlers.Factory<T> fablabs_getFactory() {
		return fablabs_factory;
	}

	@Override
	public void fablabs_setFactory(ScreenHandlers.Factory<T> factory) {
		this.fablabs_factory = factory;
	}
}
