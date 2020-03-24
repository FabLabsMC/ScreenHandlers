package io.github.fablabsmc.fablabs.impl.screenhandler;

import java.util.function.BiFunction;

import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;

public interface ExtendedScreenHandlerType<T extends Container> {
	BiFunction<? super Integer, ? super PlayerInventory, ? extends T> fablabs_getFactory();
	void fablabs_setFactory(BiFunction<? super Integer, ? super PlayerInventory, ? extends T> factory);
}
