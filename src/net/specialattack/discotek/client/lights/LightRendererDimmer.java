
package net.specialattack.discotek.client.lights;

import me.heldplayer.util.HeldCore.client.RenderHelper;
import net.minecraft.client.Minecraft;
import net.specialattack.discotek.Assets;
import net.specialattack.discotek.client.model.ModelDMXRedstone;
import net.specialattack.discotek.lights.ILightRenderHandler;
import net.specialattack.discotek.tileentity.TileEntityLight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LightRendererDimmer implements ILightRenderHandler {

    private ModelDMXRedstone modelDMXRedstone = new ModelDMXRedstone();

    @Override
    public void renderSolid(TileEntityLight light, float partialTicks, boolean disableLightmap) {
        Minecraft.getMinecraft().mcProfiler.startSection("model");
        RenderHelper.bindTexture(Assets.LIGHT_YOKE_TEXTURE);
        this.modelDMXRedstone.renderAll();
        Minecraft.getMinecraft().mcProfiler.endSection();
    }

    @Override
    public void renderLight(TileEntityLight light, float partialTicks) {}

}