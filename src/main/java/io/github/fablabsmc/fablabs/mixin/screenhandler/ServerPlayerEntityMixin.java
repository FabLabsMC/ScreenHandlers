package io.github.fablabsmc.fablabs.mixin.screenhandler;

import java.util.OptionalInt;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.NetworkedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlersImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.container.NameableContainerFactory;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Inject(method = "openContainer(Lnet/minecraft/container/NameableContainerFactory;)Ljava/util/OptionalInt;", at = @At("HEAD"), cancellable = true)
	private void fablabs_onOpenHandledScreen(NameableContainerFactory factory, CallbackInfoReturnable<OptionalInt> info) {
		if (factory instanceof NetworkedScreenHandlerFactory) {
			OptionalInt result = ScreenHandlersImpl.INSTANCE.open((ServerPlayerEntity) (Object) this, (NetworkedScreenHandlerFactory) factory);

			if (result.isPresent()) {
				info.setReturnValue(result);
			}
		}
	}
}
