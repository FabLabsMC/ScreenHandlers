package io.github.fablabsmc.fablabs.impl.screenhandler.example.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedScreenHandler;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PositionedScreen<T extends ScreenHandler & PositionedScreenHandler> extends HandledScreen<T> {
	private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

	public PositionedScreen(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	public void render(int mouseX, int mouseY, float delta) {
		renderBackground();
		super.render(mouseX, mouseY, delta);
		drawMouseoverTooltip(mouseX, mouseY);
	}

	protected void drawForeground(int mouseX, int mouseY) {
		BlockPos pos = handler.getPos();
		String position = pos != null ? "(" + pos.toShortString() + ")" : title.asFormattedString();
		textRenderer.draw(position, (float) (backgroundWidth / 2 - textRenderer.getStringWidth(position) / 2), 6.0F, 0x404040);
		textRenderer.draw(playerInventory.getDisplayName().asFormattedString(), 8.0F, backgroundHeight - 96 + 2, 0x404040);
	}

	protected void drawBackground(float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		client.getTextureManager().bindTexture(TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawTexture(x, y, 0, 0, backgroundWidth, backgroundHeight);
	}
}
