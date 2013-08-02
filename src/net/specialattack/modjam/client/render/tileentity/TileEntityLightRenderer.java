
package net.specialattack.modjam.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.specialattack.modjam.Assets;
import net.specialattack.modjam.block.TileEntityLight;
import net.specialattack.modjam.client.model.ModelLightParCan;
import net.specialattack.modjam.client.model.ModelLightYoke;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityLightRenderer extends TileEntitySpecialRenderer {

    private ModelLightParCan modelLightParCan = new ModelLightParCan();
    private ModelLightYoke modelLightYoke = new ModelLightYoke();

    public static boolean disableLight = true;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        if (!(tile instanceof TileEntityLight)) {
            return;
        }

        TileEntityLight light = (TileEntityLight) tile;

        GL11.glPushMatrix();
        this.func_110628_a(Assets.LIGHT_YOKE_TEXTURE);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        float pitch = light.pitch + (light.pitch - light.prevPitch) * partialTicks;
        float yaw = light.yaw + (light.yaw - light.prevYaw) * partialTicks;
        this.modelLightYoke.setRotations(pitch, yaw);
        this.modelLightYoke.renderAll();
        this.modelLightParCan.setRotations(pitch, yaw);
        this.modelLightParCan.render();

        if (disableLight)
            Minecraft.getMinecraft().entityRenderer.disableLightmap(0.0D);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int color = light.color;
        float red = (float) ((color >> 16) & 0xFF) / 255.0F;
        float green = (float) ((color >> 8) & 0xFF) / 255.0F;
        float blue = (float) (color & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, 0.5F);

        this.modelLightParCan.renderLens();
        GL11.glDisable(GL11.GL_BLEND);

        Tessellator tess = Tessellator.instance;
        tess.startDrawing(GL11.GL_LINES);
        tess.addVertex(0.0F, 0.0F, 0.0F);
        tess.addVertex(0.0F, -1.0F, 0.0F);
        tess.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        if (disableLight)
            Minecraft.getMinecraft().entityRenderer.enableLightmap(0.0D);

        GL11.glPopMatrix();
    }
}
