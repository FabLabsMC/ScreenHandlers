package io.github.fablabsmc.fablabs.mixin.screenhandler;

import java.util.OptionalInt;

import io.github.fablabsmc.fablabs.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.github.fablabsmc.fablabs.impl.screenhandler.ExtendedScreenHandlerType;
import io.github.fablabsmc.fablabs.impl.screenhandler.Networking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.network.Packet;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Shadow
	private int screenHandlerSyncId;

	@Unique
	private final ThreadLocal<ScreenHandler> fablabs_openedScreenHandler = new ThreadLocal<>();

	@Inject(method = "openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void fablabs_storeOpenedScreenHandler(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> info, ScreenHandler handler) {
		if (factory instanceof ExtendedScreenHandlerFactory) {
			fablabs_openedScreenHandler.set(handler);
		} else if (handler.getType() instanceof ExtendedScreenHandlerType<?>) {
			Identifier id = Registry.SCREEN_HANDLER.getId(handler.getType());
			throw new IllegalArgumentException("[FabLabs] Extended screen handler " + id + " must be opened with an ExtendedScreenHandlerFactory!");
		}
	}

	@Redirect(method = "openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
	private void fablabs_replaceVanillaScreenPacket(ServerPlayNetworkHandler networkHandler, Packet<?> packet, NamedScreenHandlerFactory factory) {
		if (factory instanceof ExtendedScreenHandlerFactory) {
			ScreenHandler handler = fablabs_openedScreenHandler.get();

			if (handler.getType() instanceof ExtendedScreenHandlerType<?>) {
				Networking.sendOpenPacket((ServerPlayerEntity) (Object) this, (ExtendedScreenHandlerFactory) factory, handler, screenHandlerSyncId);
			} else {
				Identifier id = Registry.SCREEN_HANDLER.getId(handler.getType());
				throw new IllegalArgumentException("[FabLabs] Non-extended screen handler " + id + " must not be opened with an ExtendedScreenHandlerFactory!");
			}
		} else {
			// Use with vanilla logic
			networkHandler.sendPacket(packet);
		}
	}

	@Inject(method = "openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;", at = @At("RETURN"))
	private void fablabs_clearStoredScreenHandler(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> info) {
		fablabs_openedScreenHandler.remove();
	}
}
