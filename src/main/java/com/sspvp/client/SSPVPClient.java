package com.sspvp.client;

import com.sspvp.SSPVPMod;
import com.sspvp.client.hud.HudManager;
import com.sspvp.client.keybind.KeyBindManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@Environment(EnvType.CLIENT)
public class SSPVPClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		SSPVPMod.LOGGER.info("SSPVP Client Initialized!");
		
		// Initialize keybinds
		KeyBindManager.register();
		
		// Register HUD render
		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			HudManager.render(drawContext, tickDelta);
		});
		
		// Register tick event
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			KeyBindManager.tick();
		});
	}
}