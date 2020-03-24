package io.github.fablabsmc.fablabs.mixin.screenhandler.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.impl.screenhandler.ExtendedScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerInventory;

@Mixin(ContainerType.class)
public abstract class ScreenHandlerTypeMixin<T extends Container> implements ExtendedScreenHandlerType<T> {
	@Inject(method = "create", at = @At("HEAD"), cancellable = true)
	private void fablabs_onCreate(int syncId, PlayerInventory inventory, CallbackInfoReturnable<T> info) {
		ScreenHandlers.Factory<T> factory = fablabs_getFactory();

		if (factory != null) {
			info.setReturnValue(factory.create(syncId, inventory));
		}
	}
}
