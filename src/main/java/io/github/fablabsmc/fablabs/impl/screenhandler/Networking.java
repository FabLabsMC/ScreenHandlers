package io.github.fablabsmc.fablabs.impl.screenhandler;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

public class Networking implements ModInitializer {
	private static final String ID = "fablabs-screen-handlers-v1";

	// [Packet format]
	// typeId: varInt
	// syncId: varInt
	// title: text
	// customData: buf
	public static final Identifier OPEN_ID = new Identifier(ID);

	@Override
	public void onInitialize() {
	}
}
