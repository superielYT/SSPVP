# SSPVP - Clean PvP Mod

A clean Minecraft PvP mod for version 1.21.1 with customizable HUD and GUI.

## Features

- **InfoHUD**: Display FPS, Ping, and BPS in real-time
- **TargetHUD**: Show health bars for targeted players with smooth interpolation
- **HUD Editor**: Drag-and-drop interface to customize HUD positions
- **Keybinds**: 
  - **Right Shift**: Open HUD Editor
  - **F**: Toggle FPS display
  - **H**: Toggle Target HUD

## Building

```bash
./gradlew build
```

The mod JAR will be in `build/libs/`

## Installation

1. Install Fabric Loader for 1.21.1
2. Place the mod JAR in your mods folder
3. Launch Minecraft

## Usage

1. Press **Right Shift** to open the HUD Editor
2. Drag modules to reposition them
3. Close with **Right Shift** or **ESC**
4. Use **F** and **H** to toggle HUD elements

## Colors

- **Background**: #171717
- **Panel**: #222222
- **Accent**: #7C5CFF (Purple)

## Keybinds Configuration

All keybinds can be customized in the Minecraft controls menu under "SSPVP" category.
