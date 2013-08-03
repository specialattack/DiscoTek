
package net.specialattack.modjam.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.specialattack.modjam.Assets;
import net.specialattack.modjam.client.model.ModelLightParCan;
import net.specialattack.modjam.client.model.ModelLightYoke;
import net.specialattack.modjam.tileentity.TileEntityLight;

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
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

        switch (light.getBlockMetadata()) {
        case 0:
            this.render1(light, x, y, z, partialTicks);
        break;
        }

        GL11.glPopMatrix();
    }

    public void render1(TileEntityLight light, double x, double y, double z, float partialTicks) {
        this.func_110628_a(Assets.LIGHT_YOKE_TEXTURE);

        float pitch = light.pitch + (light.pitch - light.prevPitch) * partialTicks;
        float yaw = light.yaw + (light.yaw - light.prevYaw) * partialTicks;
        this.modelLightYoke.setRotations(pitch, yaw);
        this.modelLightYoke.renderAll();
        this.modelLightParCan.setRotations(pitch, yaw);
        this.modelLightParCan.render();

        if (disableLight) {
            Minecraft.getMinecraft().entityRenderer.disableLightmap(0.0D);
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_DST_COLOR);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int color = light.color;
        float red = (float) ((color >> 16) & 0xFF) / 255.0F;
        float green = (float) ((color >> 8) & 0xFF) / 255.0F;
        float blue = (float) (color & 0xFF) / 255.0F;
        float brightness = light.brightness + (light.brightness - light.prevBrightness) * partialTicks;
        GL11.glColor4f(red * brightness, green * brightness, blue * brightness, 0.4F);

        this.modelLightParCan.renderLens();

        if (disableLight) {
            int lightLength = 10;
            float alpha = 0.8F * brightness;

            GL11.glRotatef(yaw * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(pitch * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);

            //HUzzah! I'm a wizard
            float lightangle = light.focus + (light.focus - light.prevFocus) * partialTicks;
            float downDiff = (float) (lightLength * Math.tan(Math.toRadians(lightangle)));

            GL11.glShadeModel(GL11.GL_SMOOTH);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(-0.15F, -0.15F, -0.5F);
            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(-0.15F - downDiff, -0.15F - downDiff, -lightLength);
            GL11.glVertex3f(0.15F + downDiff, -0.15F - downDiff, -lightLength);
            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(0.15F, -0.15F, -0.5F);

            GL11.glVertex3f(-0.15F, 0.15F, -0.5F);
            GL11.glVertex3f(0.15F, 0.15F, -0.5F);
            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(0.15F + downDiff, 0.15F + downDiff, -lightLength);
            GL11.glVertex3f(-0.15F - downDiff, 0.15F + downDiff, -lightLength);

            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(0.15F, -0.15F, -0.5F);
            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(0.15F + downDiff, -0.15F - downDiff, -lightLength);
            GL11.glVertex3f(0.15F + downDiff, 0.15F + downDiff, -lightLength);
            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(0.15F, 0.15F, -0.5F);

            GL11.glVertex3f(-0.15F, -0.15F, -0.5F);
            GL11.glVertex3f(-0.15F, 0.15F, -0.5F);
            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(-0.15F - downDiff, 0.15F + downDiff, -lightLength);
            GL11.glVertex3f(-0.15F - downDiff, -0.15F - downDiff, -lightLength);

            // Inside

            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(0.15F, -0.15F, -0.5F);
            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(0.15F + downDiff, -0.15F - downDiff, -lightLength);
            GL11.glVertex3f(-0.15F - downDiff, -0.15F - downDiff, -lightLength);
            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(-0.15F, -0.15F, -0.5F);

            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(-0.15F - downDiff, 0.15F + downDiff, -lightLength);
            GL11.glVertex3f(0.15F + downDiff, 0.15F + downDiff, -lightLength);
            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(0.15F, 0.15F, -0.5F);
            GL11.glVertex3f(-0.15F, 0.15F, -0.5F);

            GL11.glVertex3f(0.15F, 0.15F, -0.5F);
            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(0.15F + downDiff, 0.15F + downDiff, -lightLength);
            GL11.glVertex3f(0.15F + downDiff, -0.15F - downDiff, -lightLength);
            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(0.15F, -0.15F, -0.5F);

            GL11.glColor4f(red, green, blue, 0.0F); // End
            GL11.glVertex3f(-0.15F - downDiff, -0.15F - downDiff, -lightLength);
            GL11.glVertex3f(-0.15F - downDiff, 0.15F + downDiff, -lightLength);
            GL11.glColor4f(red, green, blue, alpha); // Origin
            GL11.glVertex3f(-0.15F, 0.15F, -0.5F);
            GL11.glVertex3f(-0.15F, -0.15F, -0.5F);

            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        if (disableLight) {
            Minecraft.getMinecraft().entityRenderer.enableLightmap(0.0D);
        }
    }

}
