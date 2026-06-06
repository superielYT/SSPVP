package com.sspvp.client.hud;

import com.sspvp.client.keybind.HudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeystrokesHud {
	private static final int PANEL_COLOR = 0xCC171717;
	private static final int ACCENT_COLOR = 0xFF7C5CFF;
	private static final int KEY_COLOR = 0xFF2A2A2A;
	private static final int KEY_ACTIVE_COLOR = 0xFF7C5CFF;
	private static final int TEXT_COLOR = 0xFFFFFFFF;
	private static final int PADDING = 6;
	private static final int KEY_SIZE = 20;
	private static final int SPACING = 3;

	public void render(DrawContext drawContext) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) return;

		HudConfig.HudModule keystrokesModule = HudConfig.getModule("keystrokes");
		if (keystrokesModule == null) return;

		int x = keystrokesModule.x;
		int y = keystrokesModule.y;
		int panelWidth = 65;
		int panelHeight = 70;

		// Draw background
		drawContext.fill(x, y, x + panelWidth, y + panelHeight, PANEL_COLOR);

		// Draw accent top border
		drawContext.fill(x, y, x + panelWidth, y + 3, ACCENT_COLOR);

		// Draw title
		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal("KEYS"),
			x + PADDING, y + 6,
			TEXT_COLOR, 0xFF000000
		);

		// Get key states
		boolean w = isKeyPressed(GLFW.GLFW_KEY_W);
		boolean a = isKeyPressed(GLFW.GLFW_KEY_A);
		boolean s = isKeyPressed(GLFW.GLFW_KEY_S);
		boolean d = isKeyPressed(GLFW.GLFW_KEY_D);

		// Draw key layout (WASD)
		int keyStartX = x + PADDING;
		int keyStartY = y + 20;

		// W key (top center)
		drawKey(drawContext, client, keyStartX + KEY_SIZE + SPACING, keyStartY, "W", w);

		// A key (bottom left)
		drawKey(drawContext, client, keyStartX, keyStartY + KEY_SIZE + SPACING, "A", a);

		// S key (bottom center)
		drawKey(drawContext, client, keyStartX + KEY_SIZE + SPACING, keyStartY + KEY_SIZE + SPACING, "S", s);

		// D key (bottom right)
		drawKey(drawContext, client, keyStartX + (KEY_SIZE + SPACING) * 2, keyStartY + KEY_SIZE + SPACING, "D", d);
	}

	private void drawKey(DrawContext context, MinecraftClient client, int x, int y, String key, boolean pressed) {
		int keyColor = pressed ? KEY_ACTIVE_COLOR : KEY_COLOR;
		int textColor = pressed ? ACCENT_COLOR : 0xFFAAAAAA;

		// Draw key background
		context.fill(x, y, x + KEY_SIZE, y + KEY_SIZE, keyColor);

		// Draw border
		int borderColor = pressed ? ACCENT_COLOR : 0xFF555555;
		context.fill(x - 1, y - 1, x + KEY_SIZE + 1, y + 1, borderColor);
		context.fill(x - 1, y + KEY_SIZE - 1, x + KEY_SIZE + 1, y + KEY_SIZE + 1, borderColor);
		context.fill(x - 1, y - 1, x + 1, y + KEY_SIZE + 1, borderColor);
		context.fill(x + KEY_SIZE - 1, y - 1, x + KEY_SIZE + 1, y + KEY_SIZE + 1, borderColor);

		// Draw key label
		int textX = x + (KEY_SIZE / 2) - 2;
		int textY = y + (KEY_SIZE / 2) - 4;
		context.drawTextWithBackground(
			client.textRenderer,
			Text.literal(key),
			textX, textY,
			textColor, 0xFF000000
		);
	}

	private boolean isKeyPressed(int key) {
		long window = MinecraftClient.getInstance().getWindow().getHandle();
		return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
	}
}