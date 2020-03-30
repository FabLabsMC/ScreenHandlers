package io.github.fablabsmc.fablabs.mixin.screenhandler;

import java.util.OptionalInt;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlerTypeBridge;
import io.github.fablabsmc.fablabs.impl.screenhandler.ScreenHandlersImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Inject(method = "openContainer(Lnet/minecraft/container/NameableContainerFactory;)Ljava/util/OptionalInt;", at = @At("HEAD"), cancellable = true)
	private void fablabs_onOpenHandledScreen(NameableContainerFactory factory, CallbackInfoReturnable<OptionalInt> info) {
		if (factory instanceof ExtendedScreenHandlerFactory) {
			OptionalInt result = ScreenHandlersImpl.INSTANCE.open((ServerPlayerEntity) (Object) this, (ExtendedScreenHandlerFactory) factory);
			info.setReturnValue(result);
		}
	}

	@Inject(
			method = "openContainer(Lnet/minecraft/container/NameableContainerFactory;)Ljava/util/OptionalInt;",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/container/NameableContainerFactory;createMenu(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/container/Container;"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void fablabs_checkForIncorrectHandlers(NameableContainerFactory factory, CallbackInfoReturnable<OptionalInt> info, Container handler) {
		if (((ScreenHandlerTypeBridge<?>) handler.getType()).fablabs_hasExtraData()) {
			Identifier id = Registry.CONTAINER.getId(handler.getType());
			throw new IllegalArgumentException("[FabLabs] Screen handler " + id + " with extra data must be opened with an ExtendedScreenHandlerFactory!");
		}
	}
}
