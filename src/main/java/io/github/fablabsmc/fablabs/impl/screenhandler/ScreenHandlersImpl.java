package io.github.fablabsmc.fablabs.impl.screenhandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.function.BiFunction;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Lazy;

public final class ScreenHandlersImpl implements ScreenHandlers {
	public static final ScreenHandlersImpl INSTANCE = new ScreenHandlersImpl();

	private static final Lazy<MethodHandle> CONSTRUCTOR = new Lazy<>(() -> {
		try {
			Constructor<?> ctor = ContainerType.class.getDeclaredConstructors()[0];
			ctor.setAccessible(true);
			return MethodHandles.lookup().unreflectConstructor(ctor);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Could not find private ScreenHandlerType constructor!", e);
		}
	});

	private ScreenHandlersImpl() {}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Container> ContainerType<T> createType(BiFunction<? super Integer, ? super PlayerInventory, ? extends T> factory) {
		try {
			ContainerType<T> result = (ContainerType<T>) CONSTRUCTOR.get().invoke(null);
			((ExtendedScreenHandlerType<T>) result).fablabs_setFactory(factory);
			return result;
		} catch (Throwable t) {
			throw new RuntimeException("Could not construct ScreenHandlerType!", t);
		}
	}
}
