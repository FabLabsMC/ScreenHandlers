package io.github.fablabsmc.fablabs.impl.screenhandler.example.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedScreenHandler;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PositionedScreen<T extends ScreenHandler & PositionedScreenHandler> extends HandledScreen<T> {
	private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

	public PositionedScreen(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		BlockPos pos = handler.getPos();
		Text usedTitle = pos != null ? new LiteralText("(" + pos.toShortString() + ")") : title;
		textRenderer.draw(matrices, usedTitle, (float) (backgroundWidth / 2 - textRenderer.getWidth(usedTitle) / 2), 6.0F, 0x404040);
		textRenderer.draw(matrices, playerInventory.getDisplayName(), 8.0F, backgroundHeight - 96 + 2, 0x404040);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		client.getTextureManager().bindTexture(TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
	}
}
