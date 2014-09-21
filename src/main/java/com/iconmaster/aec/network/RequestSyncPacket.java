package com.iconmaster.aec.network;

import com.iconmaster.aec.tileentity.AetherCraftTileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class RequestSyncPacket implements IMessage, IMessageHandler<RequestSyncPacket, IMessage> {
	private int x,y,z = 0;
	
	public RequestSyncPacket() {
		
	}
	
	public RequestSyncPacket(int x,int y,int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
	}

	@Override
	public IMessage onMessage(RequestSyncPacket message, MessageContext ctx) {
		AetherCraftTileEntity te = (AetherCraftTileEntity) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
		if (te == null) {
			//System.out.println("[AEC PACKET]SERVER ERROR: TE was null!");
			return null;
		}
		AetherCraftPacketHandler.HANDLER.sendToAll(new DeviceSyncPacket(message.x,message.y,message.z,te.getAether()));
		return null;
	}
	
}