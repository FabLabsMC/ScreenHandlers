package io.github.fablabsmc.fablabs.mixin.screenhandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.Coerce;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;

@Mixin(ContainerType.class)
public interface ScreenHandlerTypeAccessor {
	@SuppressWarnings("PublicStaticMixinMember") // for mcdev
	@Invoker("<init>")
	static <T extends Container> ContainerType<T> construct(@Coerce Object factory) {
		throw new AssertionError("Mixin dummy body called!");
	}
}
