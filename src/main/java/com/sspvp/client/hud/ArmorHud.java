package com.sspvp.client.hud;

import com.sspvp.client.keybind.HudConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ArmorHud {
	private static final int PANEL_COLOR = 0xCC171717;
	private static final int ACCENT_COLOR = 0xFF7C5CFF;
	private static final int TEXT_COLOR = 0xFFFFFFFF;
	private static final int PADDING = 6;
	private static final int ITEM_SIZE = 16;
	private static final int SPACING = 4;

	public void render(DrawContext drawContext) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) return;

		HudConfig.HudModule armorModule = HudConfig.getModule("armor");
		if (armorModule == null) return;

		int x = armorModule.x;
		int y = armorModule.y;
		int panelWidth = 120;
		int panelHeight = 60;

		// Draw background
		drawContext.fill(x, y, x + panelWidth, y + panelHeight, PANEL_COLOR);

		// Draw accent top border
		drawContext.fill(x, y, x + panelWidth, y + 3, ACCENT_COLOR);

		// Draw title
		drawContext.drawTextWithBackground(
			client.textRenderer,
			Text.literal("ARMOR"),
			x + PADDING, y + 6,
			TEXT_COLOR, 0xFF000000
		);

		// Draw armor pieces
		int armorY = y + 20;
		int armorX = x + PADDING;

		// Head
		ItemStack head = client.player.getEquippedStack(EquipmentSlot.HEAD);
		drawArmorPiece(drawContext, armorX, armorY, head, "H");

		// Chest
		ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
		drawArmorPiece(drawContext, armorX + ITEM_SIZE + SPACING + 8, armorY, chest, "C");

		// Legs
		ItemStack legs = client.player.getEquippedStack(EquipmentSlot.LEGS);
		drawArmorPiece(drawContext, armorX + (ITEM_SIZE + SPACING + 8) * 2, armorY, legs, "L");

		// Feet
		ItemStack feet = client.player.getEquippedStack(EquipmentSlot.FEET);
		drawArmorPiece(drawContext, armorX + (ITEM_SIZE + SPACING + 8) * 3, armorY, feet, "F");

		// Draw durability percentages
		int durY = armorY + ITEM_SIZE + SPACING;
		drawDurability(drawContext, armorX, durY, head);
		drawDurability(drawContext, armorX + ITEM_SIZE + SPACING + 8, durY, chest);
		drawDurability(drawContext, armorX + (ITEM_SIZE + SPACING + 8) * 2, durY, legs);
		drawDurability(drawContext, armorX + (ITEM_SIZE + SPACING + 8) * 3, durY, feet);
	}

	private void drawArmorPiece(DrawContext context, int x, int y, ItemStack stack, String label) {
		// Draw background for slot
		context.fill(x, y, x + ITEM_SIZE, y + ITEM_SIZE, 0xFF2A2A2A);

		// Draw border
		context.fill(x - 1, y - 1, x + ITEM_SIZE + 1, y + 1, 0xFF7C5CFF);
		context.fill(x - 1, y + ITEM_SIZE - 1, x + ITEM_SIZE + 1, y + ITEM_SIZE + 1, 0xFF7C5CFF);
		context.fill(x - 1, y - 1, x + 1, y + ITEM_SIZE + 1, 0xFF7C5CFF);
		context.fill(x + ITEM_SIZE - 1, y - 1, x + ITEM_SIZE + 1, y + ITEM_SIZE + 1, 0xFF7C5CFF);

		if (!stack.isEmpty()) {
			// Draw the item
			context.drawItem(stack, x + 2, y + 2);
			
			// Get durability for coloring
			if (stack.isDamageable()) {
				int durability = stack.getMaxDamage() - stack.getDamage();
				int maxDurability = stack.getMaxDamage();
				float durPercent = (float) durability / maxDurability;
				
				int color = (int) (0xFF00AA00 * durPercent) | 0xFFAA0000;
				
				// Draw durability bar below item
				int barWidth = ITEM_SIZE - 2;
				int barHeight = 2;
				context.fill(x + 1, y + ITEM_SIZE - 2, x + 1 + (int)(barWidth * durPercent), y + ITEM_SIZE - 1, color);
			}
		}

		// Draw label
		context.drawTextWithBackground(
			MinecraftClient.getInstance().textRenderer,
			Text.literal(label),
			x + 6, y + ITEM_SIZE + 3,
			0xFFFFFFFF, 0xFF000000
		);
	}

	private void drawDurability(DrawContext context, int x, int y, ItemStack stack) {
		if (!stack.isEmpty() && stack.isDamageable()) {
			int durability = stack.getMaxDamage() - stack.getDamage();
			int maxDurability = stack.getMaxDamage();
			float durPercent = (float) durability / maxDurability * 100;
			
			String durText = String.format("%.0f%%", durPercent);
			context.drawTextWithBackground(
				MinecraftClient.getInstance().textRenderer,
				Text.literal(durText),
				x + 2, y,
				0xFFFFFFFF, 0xFF000000
			);
		}
	}
}