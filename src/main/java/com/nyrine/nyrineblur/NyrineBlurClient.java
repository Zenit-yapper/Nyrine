package com.nyrine.nyrineblur;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class NyrineBlurClient implements ClientModInitializer {
    public static float blurStrength = 0.65f; // Default strength (0.0 - 1.0+)
    private static KeyBinding strengthUp;
    private static KeyBinding strengthDown;

    @Override
    public void onInitializeClient() {
        System.out.println("Nyrine Blur loaded! Use F8/F9 to adjust blur.");

        strengthUp = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.nyrineblur.strength_up", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F8, "category.nyrineblur"
        ));
        strengthDown = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.nyrineblur.strength_down", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F9, "category.nyrineblur"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (strengthUp.wasPressed()) {
                blurStrength = Math.min(blurStrength + 0.05f, 2.0f);
                client.player.sendMessage(net.minecraft.text.Text.literal("§aNyrine Blur Strength: " + String.format("%.2f", blurStrength)), true);
            }
            while (strengthDown.wasPressed()) {
                blurStrength = Math.max(blurStrength - 0.05f, 0.0f);
                client.player.sendMessage(net.minecraft.text.Text.literal("§cNyrine Blur Strength: " + String.format("%.2f", blurStrength)), true);
            }
        });
    }
}
