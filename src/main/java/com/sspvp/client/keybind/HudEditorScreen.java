package com.sspvp.client.keybind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class HudEditorScreen extends Screen {
	private static final int PANEL_COLOR = 0xFF222222;
	private static final int ACCENT_COLOR = 0xFF7C5CFF;
	private static final int GRID_SIZE = 10;
	private static final int MODULE_WIDTH = 120;
	private static final int MODULE_HEIGHT = 35;

	private String selectedModule = null;
	private int dragOffsetX = 0;
	private int dragOffsetY = 0;
	private boolean isDragging = false;

	public HudEditorScreen() {
		super(Text.literal("HUD Editor"));
	}

	public static void open() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.currentScreen == null) {
			client.setScreen(new HudEditorScreen());
			HudConfig.setEditMode(true);
		}
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		// Dark background
		this.fillGradient(context, 0, 0, this.width, this.height, 0xFF171717, 0xFF171717);

		// Draw grid
		drawGrid(context);

		// Draw all modules
		for (HudConfig.HudModule module : HudConfig.getAllModules().values()) {
			drawModule(context, module, mouseX, mouseY);
		}

		// Draw instructions
		context.drawTextWithBackground(
			this.textRenderer,
			Text.literal("Drag modules to move | Right Shift to close | Grid snap enabled"),
			10, this.height - 20,
			0xFFFFFFFF, 0xFF000000
		);

		super.render(context, mouseX, mouseY, delta);
	}

	private void drawGrid(DrawContext context) {
		int gridColor = 0xFF2A2A2A;
		for (int x = 0; x < this.width; x += GRID_SIZE) {
			context.fill(x, 0, x + 1, this.height, gridColor);
		}
		for (int y = 0; y < this.height; y += GRID_SIZE) {
			context.fill(0, y, this.width, y + 1, gridColor);
		}
	}

	private void drawModule(DrawContext context, HudConfig.HudModule module, int mouseX, int mouseY) {
		int x = module.x;
		int y = module.y;
		int width = MODULE_WIDTH;
		int height = MODULE_HEIGHT;

		// Highlight if hovering
		boolean hovering = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;

		// Draw module background
		context.fill(x, y, x + width, y + height, hovering ? 0xFF2A2A2A : PANEL_COLOR);

		// Draw accent border top
		context.fill(x, y, x + width, y + 3, ACCENT_COLOR);

		// Draw text
		String displayText = module.name;
		context.drawTextWithBackground(
			this.textRenderer,
			Text.literal(displayText),
			x + 8, y + 6,
			0xFFFFFFFF, 0xFF000000
		);

		// Draw position info
		String posText = "(" + module.x + ", " + module.y + ")";
		context.drawTextWithBackground(
			this.textRenderer,
			Text.literal(posText),
			x + 8, y + 18,
			0xFFAAAAAA, 0xFF000000
		);

		// Show selection highlight
		if (selectedModule != null && selectedModule.equals(module.name)) {
			for (int i = 0; i < 3; i++) {
				context.fill(x + i, y + i, x + width - i, y + height - i, 0x4400FF00);
			}
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		return false;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) { // Left click
			// Find clicked module
			for (HudConfig.HudModule module : HudConfig.getAllModules().values()) {
				int x = module.x;
				int y = module.y;
				if (mouseX >= x && mouseX < x + MODULE_WIDTH && mouseY >= y && mouseY < y + MODULE_HEIGHT) {
					selectedModule = module.name;
					isDragging = true;
					dragOffsetX = (int) (mouseX - x);
					dragOffsetY = (int) (mouseY - y);
					return true;
				}
			}
			selectedModule = null;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (isDragging && selectedModule != null) {
			HudConfig.HudModule module = HudConfig.getModule(selectedModule);
			if (module != null) {
				int newX = (int) (mouseX - dragOffsetX);
				int newY = (int) (mouseY - dragOffsetY);

				// Snap to grid
				newX = (newX / GRID_SIZE) * GRID_SIZE;
				newY = (newY / GRID_SIZE) * GRID_SIZE;

				// Clamp to screen
				newX = Math.max(0, Math.min(newX, this.width - MODULE_WIDTH));
				newY = Math.max(0, Math.min(newY, this.height - MODULE_HEIGHT));

				HudConfig.setModulePosition(selectedModule, newX, newY);
			}
		}
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == 0) {
			isDragging = false;
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
			this.close();
			return true;
		}
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			this.close();
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public void close() {
		super.close();
		HudConfig.setEditMode(false);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}