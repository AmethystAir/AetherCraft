package com.iconmaster.aec.client.gui;

import com.iconmaster.aec.aether.InfuserRegistry;
import com.iconmaster.aec.inventory.ContainerAetherInfuser;
import com.iconmaster.aec.tileentity.TileEntityAetherInfuser;
import com.iconmaster.aec.util.NumberUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAetherInfuser extends AetherCraftGui<TileEntityAetherInfuser> {

	private static final ResourceLocation gui_texture = new ResourceLocation(
			"aec", "textures/gui/aetherInfuserGui.png");

	public GuiAetherInfuser(InventoryPlayer player,TileEntityAetherInfuser tileEntity) {
		super(new ContainerAetherInfuser(player, tileEntity));
		
		this.xSize = 176;
		this.ySize = 166;
		this.te = tileEntity;
		this.requestSync();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glDisable(GL11.GL_LIGHTING);
		this.fontRendererObj.drawString("Aether Infuser", 9, 4, 0x404040);

		this.fontRendererObj.drawStringWithShadow("AV: "+NumberUtils.display(te.getAether())+"/"+NumberUtils.display(te.getMax()), 30, 58,0x00FF00);
		
		//te.calcLimit();
		//this.fontRendererObj.drawStringWithShadow("Limit: "+NumberUtils.display(te.limit), 30, 70,0x00FF00);
		
		if (InfuserRegistry.getOutputAV(this.te.getStackInSlot(0)) != 0) {
			int percent = Math.min((int) ((te.infused / InfuserRegistry.getOutputAV(this.te.getStackInSlot(0)))*100),100);
			this.drawRect(41, 16, 41 + percent, 24,0xFF32FF00);
		}
		
		this.drawCenteredString(
				this.fontRendererObj,
				NumberUtils.display(te.infused)
						+ " / "
						+ NumberUtils.display(InfuserRegistry.getOutputAV(this.te.getStackInSlot(0))), 41+50, 16, 0x55FF55);

		
		int barHeight = 68 - 16;
		int progress = (int) Math.min(100,(te.getAether()/te.getMax())*100);
		float barPercent = (100 - progress) / 100.0f;
		//this.drawGradientRect(10, (int) (16+barHeight*barPercent), 26, 68,
		//		0xFF16FF00,0x990EA600);
		this.drawGradientRect(10, (int) (16.0f + (barHeight * barPercent)), 26, 68,
				0xFF16FF00,0x990EA600);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float metaTicks, int mouseX,
			int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.gui_texture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}