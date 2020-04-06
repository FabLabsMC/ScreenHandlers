package io.github.fablabsmc.fablabs.mixin.screenhandler.client;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ScreenHandlers;
import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlerTypeBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(ScreenHandlerType.class)
public abstract class ScreenHandlerTypeMixin<T extends ScreenHandler> implements ScreenHandlerTypeBridge<T> {
	@Inject(method = "create", at = @At("HEAD"), cancellable = true)
	private void fablabs_onCreate(int syncId, PlayerInventory inventory, CallbackInfoReturnable<T> info) {
		if (fablabs_hasExtraData()) {
			Identifier id = Registry.SCREEN_HANDLER.getId((ScreenHandlerType<?>) (Object) this);
			throw new IllegalStateException("[FabLabs] Screen handler " + id + " with extra data cannot be constructed using its type!");
		}

		ScreenHandlers.ExtendedFactory<T> factory = fablabs_getFactory();

		if (factory != null) {
			info.setReturnValue(factory.create(syncId, inventory, null));
		}
	}
}
