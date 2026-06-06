package com.sspvp;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSPVPMod implements ModInitializer {
	public static final String MOD_ID = "sspvp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("SSPVP Mod Initialized!");
	}
}