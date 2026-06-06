package com.sspvp.client.hud;

import com.sspvp.client.keybind.HudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class HudManager {
	private static final InfoHud infoHud = new InfoHud();
	private static final TargetHud targetHud = new TargetHud();
	private static final ArmorHud armorHud = new ArmorHud();
	private static final CpsHud cpsHud = new CpsHud();
	private static final KeystrokesHud keystrokesHud = new KeystrokesHud();

	public static void render(DrawContext drawContext, float tickDelta) {
		if (HudConfig.isEditMode()) {
			return; // Don't render HUD in edit mode
		}

		ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
		if (player == null) return;

		// Render InfoHUD
		infoHud.render(drawContext);

		// Render Target HUD
		if (HudConfig.isTargetHudEnabled()) {
			targetHud.render(drawContext);
		}

		// Render Armor HUD
		if (HudConfig.isArmorHudEnabled()) {
			armorHud.render(drawContext);
		}

		// Render CPS HUD
		if (HudConfig.isCpsEnabled()) {
			cpsHud.render(drawContext);
		}

		// Render Keystrokes HUD
		if (HudConfig.isKeystrokesEnabled()) {
			keystrokesHud.render(drawContext);
		}
	}
}