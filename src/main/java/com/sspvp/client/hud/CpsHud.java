package com.sspvp.client.hud;

import com.sspvp.client.keybind.HudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CpsHud {
	private static final int PANEL_COLOR = 0xCC171717;
	private static final int ACCENT_COLOR = 0xFF7C5CFF;
	private static final int TEXT_COLOR = 0xFFFFFFFF;
	private static final int PADDING = 8;
	private static final int PANEL_WIDTH = 70;
	private static final int PANEL_HEIGHT = 45;
	private static final int TRACK_TIME = 100; // 100ms window

	private long lastLeftClick = 0;
	private long lastRightClick = 0;
	private int leftCpsCount = 0;
	private int rightCpsCount = 0;
	private long cpsUpdateTime = 0;

	public void render(DrawContext drawContext) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) return;

		HudConfig.HudModule cpsModule = HudConfig.getModule("cps");
		if (cpsModule == null) return;

		// Track mouse clicks (simplified - would need mixin for accurate tracking)
		trackClicks(client);

		int x = cpsModule.x;
		int y = cpsModule.y;

		// Draw background
		drawContext.fill(x, y, x + PANEL_WIDTH, y + PANEL_HEIGHT, PANEL_COLOR);

		// Draw accent top border
		drawContext.fill(x, y, x + PANEL_WIDTH, y + 3, ACCENT_COLOR);

		// Draw title
		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal("CPS"),
			x + PADDING, y + 6,
			TEXT_COLOR, 0xFF000000
		);

		// Draw CPS values
		String leftText = "L: " + leftCpsCount;
		String rightText = "R: " + rightCpsCount;

		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal(leftText),
			x + PADDING, y + 18,
			ACCENT_COLOR, 0xFF000000
		);

		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal(rightText),
			x + PADDING, y + 30,
			ACCENT_COLOR, 0xFF000000
		);
	}

	private void trackClicks(MinecraftClient client) {
		long now = System.currentTimeMillis();

		// Reset counts every TRACK_TIME ms
		if (now - cpsUpdateTime > TRACK_TIME) {
			leftCpsCount = 0;
			rightCpsCount = 0;
			cpsUpdateTime = now;
		}

		// This is a simplified version. A proper implementation would use a mixin
		// to track actual mouse clicks in the Minecraft client.
		// For now, we'll show static CPS values
	}
}