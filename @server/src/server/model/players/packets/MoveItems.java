package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

public class MoveItems implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
	
		c.getItems().resetItems(3214);
		if(c.inTrade) {
			c.getTradeAndDuel().declineTrade();
                             		return;
                        	}
		if(c.tradeStatus == 1) {
			c.getTradeAndDuel().declineTrade();
                             		return;
                        	}
		if(c.duelStatus == 1) {
			c.getTradeAndDuel().declineDuel();
			return;
		}
	
		int interfaceId = c.getInStream().readSignedWordBigEndianA();
		boolean insertMode = c.getInStream().readSignedByteC() == 1;
		int from = c.getInStream().readSignedWordBigEndianA();
		int to = c.getInStream().readSignedWordBigEndian();
		
		c.getItems().moveItems(from, to, interfaceId, insertMode);
		
	}
}