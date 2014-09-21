package com.iconmaster.aec.item;

import com.iconmaster.aec.AetherCraft;
import com.iconmaster.aec.aether.AVRegistry;
import com.iconmaster.aec.aether.IAetherStorageItem;
import com.iconmaster.aec.aether.IConsumeBehavior;
import com.iconmaster.aec.aether.IProduceBehavior;
import com.iconmaster.aec.util.NumberUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemAetherBattery extends Item implements IAetherStorageItem, IProduceBehavior, IConsumeBehavior {
	
	public IIcon[] icons;
	
	public ItemAetherBattery() {
		super();
        this.setUnlocalizedName("aec.aetherBattery");
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setCreativeTab(AetherCraft.tabAetherCraft);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "aec.aetherBattery."+stack.getItemDamage();
	}
	
	@Override
	public void getSubItems(Item par1,CreativeTabs tab,List list) {
		list.add(new ItemStack(this,1,0));
		list.add(new ItemStack(this,1,1));
		list.add(new ItemStack(this,1,2));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[3];
		icons[0] = iconRegister.registerIcon("aec:itemAetherBattery");
		icons[1] = iconRegister.registerIcon("aec:itemAetherCell");
		icons[2] = iconRegister.registerIcon("aec:itemLesserStorageCrystal");
	}
	
	@Override
    public IIcon getIconFromDamage(int par1) {
		return icons[par1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack,
			EntityPlayer entityPlayer, List list, boolean advancedTooltip) {
		float ev = 0;
		if(stack.hasTagCompound()){
			NBTTagCompound tag = stack.getTagCompound();
			if(tag.hasKey("AV")){
				ev = tag.getFloat("AV");
			}
		}
		list.add("\u00a72" + "STORED AV: " + NumberUtils.display(ev));
	}

	@Override
	public float addAether(ItemStack stack, float av) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		float max = (float) (AetherCraft.options.getFloat("abatterymaxstorage")*Math.pow(2,stack.getItemDamage()*2));
		float has = stack.getTagCompound().getFloat("AV");
		if (has + av > max) {
			this.setAether(stack,max);
			return (has+av)-max;
		}
		this.setAether(stack,has+av);
		return 0;
	}

	@Override
	public float extractAether(ItemStack stack, float av) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		float has = stack.getTagCompound().getFloat("AV");
		if (has - av < 0) {
			this.setAether(stack,0);
			return has;
		}
		this.setAether(stack,has-av);
		return av;
	}

	public void setAether(ItemStack stack, float av) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setFloat("AV", av);
	}

	@Override
	public float getAether(ItemStack stack) {
		if (!stack.hasTagCompound()) {return 0;}
		return stack.getTagCompound().getFloat("AV");
	}

	@Override
	public float getConsumeAV(ItemStack stack) {
		//System.out.println(AVRegistry.getAV(stack)+getAether(stack));
		return AVRegistry.getAV(stack)+getAether(stack);
	}

	@Override
	public ItemStack consume(ItemStack stack, ItemStack[] inv) {
		return null;
	}

	@Override
	public float getProduceAV(ItemStack stack) {
		//System.out.println(AVRegistry.getAV(stack)+getAether(stack));
		return AVRegistry.getAV(stack)+getAether(stack);
	}

	@Override
	public ItemStack produce(ItemStack stack, ItemStack[] inv) {
		ItemStack copy = stack.copy();
		setAether(copy, getAether(stack));
		return copy;
	}

	@Override
	public float tryAddAether(ItemStack stack, float av) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		float max = (float) (AetherCraft.options.getFloat("abatterymaxstorage")*Math.pow(2,stack.getItemDamage()*2));
		float has = stack.getTagCompound().getFloat("AV");
		if (has + av > max) {
			//this.setAether(stack,max);
			return (has+av)-max;
		}
		//this.setAether(stack,has+av);
		return 0;
	}

	@Override
	public float tryExtractAether(ItemStack stack, float av) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		float has = stack.getTagCompound().getFloat("AV");
		if (has - av < 0) {
			//this.setAether(stack,0);
			return has;
		}
		//this.setAether(stack,has-av);
		return av;
	}
}
