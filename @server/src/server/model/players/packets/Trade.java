package server.model.players.packets;

import server.Config;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Trading
 */
public class Trade implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPA().resetFollow();
		if(c.playerRights == 4)
		return;
		if(c.inWild()) {  
			c.sendMessage("You can't trade in the wilderness!");
			return;
		}

		if(c.isBanking)
		return;

		if(c.arenas()) {
			c.sendMessage("You can't trade inside the arena!");
			return;
		}
			if (tradeId != c.playerId && !c.isBanking) {
				c.getTradeAndDuel().requestTrade(tradeId);
			}
	}
		
}

