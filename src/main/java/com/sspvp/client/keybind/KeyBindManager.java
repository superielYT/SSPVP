package com.sspvp.client.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindManager {
	public static KeyBinding openHudEditor;
	public static KeyBinding toggleFPS;
	public static KeyBinding toggleTargetHud;
	public static KeyBinding toggleArmorHud;
	public static KeyBinding toggleCPS;
	public static KeyBinding toggleKeystrokes;

	public static void register() {
		// Open HUD Editor with Right Shift (customizable)
		openHudEditor = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.sspvp.openHudEditor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.sspvp")
		);

		// Toggle FPS Display (customizable)
		toggleFPS = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.sspvp.toggleFps", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F, "category.sspvp")
		);

		// Toggle Target HUD (customizable)
		toggleTargetHud = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.sspvp.toggleTargetHud", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.sspvp")
		);

		// Toggle Armor HUD (customizable)
		toggleArmorHud = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.sspvp.toggleArmorHud", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_A, "category.sspvp")
		);

		// Toggle CPS (customizable)
		toggleCPS = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.sspvp.toggleCps", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.sspvp")
		);

		// Toggle Keystrokes (customizable)
		toggleKeystrokes = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.sspvp.toggleKeystrokes", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "category.sspvp")
		);
	}

	public static void tick() {
		// Handle keybind presses
		while (openHudEditor.wasPressed()) {
			HudEditorScreen.open();
		}

		while (toggleFPS.wasPressed()) {
			HudConfig.toggleFPS();
		}

		while (toggleTargetHud.wasPressed()) {
			HudConfig.toggleTargetHud();
		}

		while (toggleArmorHud.wasPressed()) {
			HudConfig.toggleArmorHud();
		}

		while (toggleCPS.wasPressed()) {
			HudConfig.toggleCPS();
		}

		while (toggleKeystrokes.wasPressed()) {
			HudConfig.toggleKeystrokes();
		}
	}
}