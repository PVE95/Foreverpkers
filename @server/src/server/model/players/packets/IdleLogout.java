package server.model.players.packets;


import server.model.players.Client;
import server.model.players.PacketType;


public class IdleLogout implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.memberStatus < 3 && c.logoutTimes == 71) {//10 mins
			c.logout();
		} else if(c.memberStatus == 3 && c.logoutTimes == 141) {//20mins
			c.logout();
		} else if (c.memberStatus < 3 && c.logoutTimes == 64) {
			c.sendMessage("@red@You have not moved in a while, you will idle logout soon.");
			c.logoutTimes++;
		} else if(c.memberStatus == 3 && c.logoutTimes == 134) {
			c.sendMessage("@red@You have not moved in a while, you will idle logout soon.");
			c.logoutTimes++;
		} else {
			c.logoutTimes++;
		}
	}
}