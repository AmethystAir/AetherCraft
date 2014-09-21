package com.iconmaster.aec.client;

import com.iconmaster.aec.AetherCraft;
import com.iconmaster.aec.CommonProxy;
import com.iconmaster.aec.client.render.RenderAetherConduit;
import com.iconmaster.aec.client.render.RenderAetherFlame;
import com.iconmaster.aec.client.render.RenderAetherPump;
import com.iconmaster.aec.event.*;
import com.iconmaster.aec.tileentity.TileEntityAetherPump;
import com.iconmaster.aec.util.InventoryUtils;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	public static int renderPass;
	
    @Override
    public void setCustomRenderers()
    {
    		AetherCraft.proxy.conduitRenderType = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(new RenderAetherConduit());
            
            AetherCraft.proxy.pumpRenderType = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(new RenderAetherPump());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAetherPump.class, new RenderAetherPump());
            
            AetherCraft.proxy.flameRenderType = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(new RenderAetherFlame());
    }
	
	@Override
	public void registerRenderInformation() {
	}

	@Override
	public void registerHandlers() {

	}

	@Override
	public void registerEventHandlers() {
		if (AetherCraft.options.getBoolean("enableflyring")) {
			MinecraftForge.EVENT_BUS.register(new FallDamageEvent());
		}
		MinecraftForge.EVENT_BUS.register(new AetherSwordEvent());
		MinecraftForge.EVENT_BUS.register(new DisableRingInContainerEvent());
		MinecraftForge.EVENT_BUS.register(new TooltipEvent());
		MinecraftForge.EVENT_BUS.register(new AetherArmorDamageEvent());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void activateRings(Object ctx) {
		InventoryUtils.activateRings(Minecraft.getMinecraft().thePlayer);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void deactivateRings(Object ctx) {
		InventoryUtils.deactivateRings(Minecraft.getMinecraft().thePlayer);
	}
}