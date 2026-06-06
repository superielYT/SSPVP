package com.sspvp.client.keybind;

import java.util.HashMap;
import java.util.Map;

public class HudConfig {
	public static class HudModule {
		public int x;
		public int y;
		public String name;
		public boolean enabled;

		public HudModule(String name, int x, int y, boolean enabled) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.enabled = enabled;
		}
	}

	private static final Map<String, HudModule> modules = new HashMap<>();
	private static boolean fpsEnabled = true;
	private static boolean targetHudEnabled = true;
	private static boolean armorHudEnabled = true;
	private static boolean cpsEnabled = true;
	private static boolean keystrokesEnabled = true;
	private static boolean editMode = false;

	static {
		// Default positions - Top Left
		modules.put("fps", new HudModule("FPS", 10, 10, true));
		modules.put("ping", new HudModule("Ping", 10, 25, true));
		modules.put("bps", new HudModule("BPS", 10, 40, true));
		
		// Center
		modules.put("keystrokes", new HudModule("Keystrokes", 250, 200, true));
		modules.put("cps", new HudModule("CPS", 250, 230, true));
		
		// Bottom Left
		modules.put("armor", new HudModule("Armor", 10, 250, true));
		
		// Bottom Right
		modules.put("potions", new HudModule("Potions", 380, 250, true));
		
		// Near Crosshair
		modules.put("target", new HudModule("Target", 320, 180, true));
	}

	public static HudModule getModule(String name) {
		return modules.getOrDefault(name, null);
	}

	public static Map<String, HudModule> getAllModules() {
		return modules;
	}

	public static void setModulePosition(String name, int x, int y) {
		HudModule module = modules.get(name);
		if (module != null) {
			module.x = x;
			module.y = y;
		}
	}

	public static void toggleFPS() {
		fpsEnabled = !fpsEnabled;
	}

	public static void toggleTargetHud() {
		targetHudEnabled = !targetHudEnabled;
	}

	public static void toggleArmorHud() {
		armorHudEnabled = !armorHudEnabled;
	}

	public static void toggleCPS() {
		cpsEnabled = !cpsEnabled;
	}

	public static void toggleKeystrokes() {
		keystrokesEnabled = !keystrokesEnabled;
	}

	public static boolean isFpsEnabled() {
		return fpsEnabled;
	}

	public static boolean isTargetHudEnabled() {
		return targetHudEnabled;
	}

	public static boolean isArmorHudEnabled() {
		return armorHudEnabled;
	}

	public static boolean isCpsEnabled() {
		return cpsEnabled;
	}

	public static boolean isKeystrokesEnabled() {
		return keystrokesEnabled;
	}

	public static void setEditMode(boolean editMode) {
		HudConfig.editMode = editMode;
	}

	public static boolean isEditMode() {
		return editMode;
	}
}