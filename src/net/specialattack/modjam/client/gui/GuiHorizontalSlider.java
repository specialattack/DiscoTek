
package net.specialattack.modjam.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiHorizontalSlider extends ModJamSlider {

    private String baseDisplayString;

    public GuiHorizontalSlider(int par1, int par2, int par3, String par5Str, float par6, GuiSliderCompat parent) {
        super(par1, par2, par3, 150, 20, par5Str);
        this.sliderValue = par6;
        this.parent = parent;
        this.baseDisplayString = par5Str;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
     * this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1) {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of
     * MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
        if (this.drawButton) {
            if (this.dragging) {
                this.sliderValue = (float) (par2 - (this.xPosition + 4)) / (float) (this.width - 8);

                if (this.sliderValue < 0.0F) {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F) {
                    this.sliderValue = 1.0F;
                }

            }
            
            this.displayString = this.baseDisplayString + (int)(this.sliderValue * 100);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
            parent.slideActionPerformed(this);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of
     * MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.sliderValue = (float) (par2 - (this.xPosition + 4)) / (float) (this.width - 8);

            if (this.sliderValue < 0.0F) {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F) {
                this.sliderValue = 1.0F;
            }

            this.dragging = true;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of
     * MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2) {
        this.dragging = false;
    }
}