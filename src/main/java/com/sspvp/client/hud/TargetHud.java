package com.sspvp.client.hud;

import com.sspvp.client.keybind.HudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class TargetHud {
	private static final int TARGET_WIDTH = 150;
	private static final int TARGET_HEIGHT = 50;
	private static final int PANEL_COLOR = 0xCC171717;
	private static final int ACCENT_COLOR = 0xFF7C5CFF;
	private static final int TEXT_COLOR = 0xFFFFFFFF;
	private static final int HEALTH_COLOR = 0xFFFF5555;
	private static final int PADDING = 8;

	private float displayHealth = 20f;

	public void render(DrawContext drawContext) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) return;

		PlayerEntity target = getTarget(client);
		if (target == null || target == client.player) return;

		HudConfig.HudModule targetModule = HudConfig.getModule("target");
		if (targetModule == null) return;

		int x = targetModule.x;
		int y = targetModule.y;

		// Smooth health interpolation
		float realHealth = target.getHealth();
		displayHealth += (realHealth - displayHealth) * 0.15f;

		// Draw background
		drawContext.fill(x, y, x + TARGET_WIDTH, y + TARGET_HEIGHT, PANEL_COLOR);

		// Draw accent top border
		drawContext.fill(x, y, x + TARGET_WIDTH, y + 3, ACCENT_COLOR);

		// Draw player name
		String playerName = target.getName().getString();
		if (playerName.length() > 18) {
			playerName = playerName.substring(0, 18) + "...";
		}
		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal(playerName),
			x + PADDING, y + 6,
			TEXT_COLOR, 0xFF000000
		);

		// Draw health bar
		int barY = y + 20;
		drawHealthBar(drawContext, x + PADDING, barY, 134, 8, (int) displayHealth, 20);

		// Draw health text
		String healthText = String.format("%.1f ❤", displayHealth);
		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal(healthText),
			x + PADDING, y + 32,
			HEALTH_COLOR, 0xFF000000
		);

		// Draw distance
		double distance = client.player.distanceTo(target);
		String distanceText = String.format("%.1fm", distance);
		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal(distanceText),
			x + TARGET_WIDTH - PADDING - 40, y + 32,
			TEXT_COLOR, 0xFF000000
		);
	}

	private void drawHealthBar(DrawContext context, int x, int y, int width, int height, int health, int maxHealth) {
		// Background (darker)
		context.fill(x, y, x + width, y + height, 0xFF1A1A1A);

		// Health bar (red to purple gradient effect)
		int healthWidth = (int) (width * ((float) health / maxHealth));
		context.fill(x, y, x + healthWidth, y + height, 0xFF7C5CFF);

		// Border
		context.fill(x - 1, y - 1, x + width + 1, y + 1, 0xFF7C5CFF);
		context.fill(x - 1, y + height, x + width + 1, y + height + 1, 0xFF7C5CFF);
		context.fill(x - 1, y - 1, x + 1, y + height + 1, 0xFF7C5CFF);
		context.fill(x + width - 1, y - 1, x + width + 1, y + height + 1, 0xFF7C5CFF);
	}

	private PlayerEntity getTarget(MinecraftClient client) {
		if (client.targetedEntity instanceof PlayerEntity) {
			return (PlayerEntity) client.targetedEntity;
		}
		return null;
	}
}