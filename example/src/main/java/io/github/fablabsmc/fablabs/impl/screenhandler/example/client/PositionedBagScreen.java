package io.github.fablabsmc.fablabs.impl.screenhandler.example.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.BagScreenHandler;
import io.github.fablabsmc.fablabs.impl.screenhandler.example.screen.PositionedBagScreenHandler;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PositionedBagScreen extends ContainerScreen<PositionedBagScreenHandler> {
	private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

	public PositionedBagScreen(PositionedBagScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	public void render(int mouseX, int mouseY, float delta) {
		renderBackground();
		super.render(mouseX, mouseY, delta);
		drawMouseoverTooltip(mouseX, mouseY);
	}

	protected void drawForeground(int mouseX, int mouseY) {
		BlockPos pos = container.getPos();
		String position = pos != null ? "(" + pos.toShortString() + ")" : title.asFormattedString();
		font.draw(position, (float) (containerWidth / 2 - font.getStringWidth(position) / 2), 6.0F, 0x404040);
		font.draw(playerInventory.getDisplayName().asFormattedString(), 8.0F, containerHeight - 96 + 2, 0x404040);
	}

	protected void drawBackground(float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		minecraft.getTextureManager().bindTexture(TEXTURE);
		int x = (width - containerWidth) / 2;
		int y = (height - containerHeight) / 2;
		blit(x, y, 0, 0, containerWidth, containerHeight);
	}
}
