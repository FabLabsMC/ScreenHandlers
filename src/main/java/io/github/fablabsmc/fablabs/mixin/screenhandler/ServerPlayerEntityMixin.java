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

import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Inject(method = "openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;", at = @At("HEAD"), cancellable = true)
	private void fablabs_onOpenHandledScreen(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> info) {
		if (factory instanceof ExtendedScreenHandlerFactory) {
			OptionalInt result = ScreenHandlersImpl.INSTANCE.open((ServerPlayerEntity) (Object) this, (ExtendedScreenHandlerFactory) factory);
			info.setReturnValue(result);
		}
	}

	@Inject(
			method = "openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/screen/NamedScreenHandlerFactory;createMenu(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/screen/ScreenHandler;"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void fablabs_checkForIncorrectHandlers(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> info, ScreenHandler handler) {
		if (((ScreenHandlerTypeBridge<?>) handler.getType()).fablabs_hasExtraData()) {
			Identifier id = Registry.SCREEN_HANDLER.getId(handler.getType());
			throw new IllegalArgumentException("[FabLabs] Screen handler " + id + " with extra data must be opened with an ExtendedScreenHandlerFactory!");
		}
	}
}
