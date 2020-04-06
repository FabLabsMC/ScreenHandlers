package io.github.fablabsmc.fablabs.mixin.screenhandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public interface ServerPlayerEntityAccessor {
	@Invoker
	void callIncrementScreenHandlerSyncId();

	@Accessor
	int getScreenHandlerSyncId();
}
