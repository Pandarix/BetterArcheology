package net.Pandarix.betterarcheology.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IdentifyingScreen extends HandledScreen<IdentifyingScreenHandler> {


    //saves archeology_table_gui as TEXTURE
    private static final Identifier TEXTURE =
            new Identifier(BetterArcheology.MOD_ID, "textures/gui/archeology_table_gui.png");

    public IdentifyingScreen(IdentifyingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    //displays the Title of the Block in the center of the menu
    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(context, x, y);
    }

    //renders the Progress-Arrow
    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {                                                                                      //TODO: Fix coordinates
            context.drawTexture(TEXTURE, x + 51, y + 48, 176, 0, handler.getScaledProgress(), 17);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}