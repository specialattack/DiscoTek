
package net.specialattack.discotek.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabIcon extends CreativeTabs {

    private ItemStack iconItemStack;

    public CreativeTabIcon(String name) {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        return this.iconItemStack;
    }

    public void setIconItemStack(ItemStack iconItemStack) {
        this.iconItemStack = iconItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return this.iconItemStack.getItem();
    }

}
