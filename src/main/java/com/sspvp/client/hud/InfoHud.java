package com.sspvp.client.hud;

import com.sspvp.client.keybind.HudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class InfoHud {
	private static final int PANEL_WIDTH = 85;
	private static final int PANEL_HEIGHT = 65;
	private static final int PANEL_COLOR = 0xCC171717;
	private static final int ACCENT_COLOR = 0xFF7C5CFF;
	private static final int TEXT_COLOR = 0xFFFFFFFF;
	private static final int PADDING = 6;

	public void render(DrawContext drawContext) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) return;

		HudConfig.HudModule fpsModule = HudConfig.getModule("fps");
		HudConfig.HudModule pingModule = HudConfig.getModule("ping");
		HudConfig.HudModule bpsModule = HudConfig.getModule("bps");

		if (fpsModule == null || pingModule == null || bpsModule == null) return;

		// Draw FPS
		drawPanel(drawContext, fpsModule.x, fpsModule.y, "FPS", String.valueOf(client.getCurrentFps()));

		// Draw Ping
		int ping = getPing(client);
		drawPanel(drawContext, pingModule.x, pingModule.y, "PING", ping + "ms");

		// Draw BPS (Block Break Speed - placeholder)
		drawPanel(drawContext, bpsModule.x, bpsModule.y, "BPS", "N/A");
	}

	private void drawPanel(DrawContext context, int x, int y, String label, String value) {
		// Draw background
		context.fill(x, y, x + PANEL_WIDTH, y + PANEL_HEIGHT, PANEL_COLOR);

		// Draw accent top border
		context.fill(x, y, x + PANEL_WIDTH, y + 3, ACCENT_COLOR);

		// Draw label
		context.drawTextWithBackground(
			MinecraftClient.getInstance().textRenderer,
			Text.literal(label),
			x + PADDING, y + 8,
			TEXT_COLOR, 0xFF000000
		);

		// Draw value (larger)
		context.drawTextWithBackground(
			MinecraftClient.getInstance().textRenderer,
			Text.literal(value),
			x + PADDING, y + 28,
			ACCENT_COLOR, 0xFF000000
		);
	}

	private int getPing(MinecraftClient client) {
		if (client.getNetworkHandler() != null && client.getNetworkHandler().getPlayerListEntry(client.player.getUuid()) != null) {
			return client.getNetworkHandler().getPlayerListEntry(client.player.getUuid()).getLatency();
		}
		return 0;
	}
}