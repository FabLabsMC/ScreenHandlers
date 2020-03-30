package io.github.fablabsmc.fablabs.impl.screenhandler;

import net.minecraft.util.Identifier;

public final class Packets {
	// [Packet format]
	// typeId: varInt
	// syncId: varInt
	// title: text
	// customData: buf
	public static final Identifier OPEN_ID = new Identifier("fablabs-screen-handlers-v1", "open_screen");
}
