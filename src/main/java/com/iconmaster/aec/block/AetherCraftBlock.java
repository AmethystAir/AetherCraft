package com.iconmaster.aec.block;

import com.iconmaster.aec.AetherCraft;
import com.iconmaster.aec.aether.IAetherTransfer;
import com.iconmaster.aec.util.BlockTextureData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class AetherCraftBlock extends BlockContainer implements IAetherTransfer {
	private BlockTextureData[] icon;
	private String blockName;

	public AetherCraftBlock(Material material,String name) {
		super(material);
		this.setHardness(1.5f);
        //this.setUnlocalizedName("aec.aether"+name);
        blockName = name;
        this.setCreativeTab(AetherCraft.tabAetherCraft);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "aec.aether"+blockName;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (!(tileEntity instanceof IInventory)) {
			return;
		}

		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z
						+ rz, new ItemStack(item.getItem(), item.stackSize,
						item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound(
							(NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = new BlockTextureData[3];
		icon[0] = new BlockTextureData(iconRegister.registerIcon("aec:aether"+blockName+"Top"),iconRegister.registerIcon("aec:aether"+blockName+"Side"),iconRegister.registerIcon("aec:deviceBottom"));
		icon[1] = new BlockTextureData(iconRegister.registerIcon("aec:aether"+blockName+"InfusedTop"),iconRegister.registerIcon("aec:aether"+blockName+"InfusedSide"),iconRegister.registerIcon("aec:deviceInfusedBottom"));
		icon[2] = new BlockTextureData(iconRegister.registerIcon("aec:aether"+blockName+"GildedTop"),iconRegister.registerIcon("aec:aether"+blockName+"GildedSide"),iconRegister.registerIcon("aec:deviceGildedBottom"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (metadata >= icon.length || icon[metadata]==null) {return null;}
		return side == 0 ? icon[metadata].getBottom() : side == 1 ? icon[metadata].getTop() : icon[metadata].getSide();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2) {
		return null;
	}
	
	@Override
	public boolean canTransferAV(World world, int x, int y, int z, int sideFrom) {
		return true;
	}
	
	@Override
	public float getMaxTransferAV(World world, int x,int y,int z,int side) {
		int meta = world.getBlockMetadata(x, y, z);
		return (float) ((AetherCraft.options.getFloat("flowrate"))*(Math.pow(2,meta*2)));
	}
	
    @Override
    public int damageDropped(int par1)
    {
        return par1;
    }
    
    @Override
    public void getSubBlocks(Item par1, CreativeTabs tab, List list) {
    	list.add(new ItemStack(this,1,0));
    	list.add(new ItemStack(this,1,1));
    	list.add(new ItemStack(this,1,2));
    }
    
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(this,1,world.getBlockMetadata(x, y, z));
	}
    
    
}