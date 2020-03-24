package io.github.fablabsmc.fablabs.mixin.screenhandler;

import java.util.function.BiFunction;

import io.github.fablabsmc.fablabs.impl.screenhandler.ExtendedScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerInventory;

@Mixin(ContainerType.class)
public class ScreenHandlerTypeMixin<T extends Container> implements ExtendedScreenHandlerType<T> {
	private BiFunction<? super Integer, ? super PlayerInventory, ? extends T> fablabs_factory = null;

	@Override
	public BiFunction<? super Integer, ? super PlayerInventory, ? extends T> fablabs_getFactory() {
		return fablabs_factory;
	}

	@Override
	public void fablabs_setFactory(BiFunction<? super Integer, ? super PlayerInventory, ? extends T> factory) {
		this.fablabs_factory = factory;
	}
}
