package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerSave;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		c.alchDelay = System.currentTimeMillis();

		c.printPacketLog("Player dropped a " + c.getItems().getItemName(itemId) + ".");

		/*if(c.KC < 3) {
			c.sendMessage("@blu@You can't drop items until you kill 3 players.");
			return;
		}*/
		if((c.underAttackBy > 0) && c.getShops().getItemShopValue(itemId) > 5000) {
		c.sendMessage("You can't drop items worth over 5k while in combat.");
		return;
		}
		/*if(c.inUndeadStandoff()) {
			c.sendMessage("You can't drop items here!");
			return;
		}*/
if (c.inTrade) {
			c.sendMessage("@blu@You can't drop items while trading!");
			return;
		}
		if (Server.playerHandler.players[c.playerId].underAttackBy != 0) {
			if ((c.getShops().getItemShopValue(itemId)*.75) > 10000) {
			c.sendMessage("@blu@You can't drop items worth over 10k in combat.");
			return;
			}
		}
		if(c.inTrade) {
			c.sendMessage("@blu@You can't drop items while trading!");
			return;
		}

		if(!c.getItems().playerHasItem(itemId,1,slot)) {
			return;
		}
	//	if(c.tradeTimer > 0) {
	//		c.sendMessage("You must wait until your starter timer is up to drop items.");
	//		return;
	//	}
		if(c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}

		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}
		if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
			if(droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 10000) {
						c.sendMessage("@blu@You may not drop items worth more than 10000 coins while in combat.");
						return;
					}
				}
				if (c.underAttackBy > 0) {
					if (itemId == 995) {
						c.sendMessage("@blu@You may not drop cash while in combat.");
						return;
					}
				}
				c.getWoodcutting().resetWoodcut();
				c.getMining().resetMining();
				c.getFishing().resetFishing();
				c.isFlagged = c.isFlaggable();
				//if(c.isFlagged != 2 && c.playerRights != 4){
				Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
				//} else {
				//c.sendMessage("Poof. The item disappears as it touches the ground.");
				//}
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				PlayerSave.saveGame(c);
			} else {
				c.sendMessage("This item cannot be dropped.");
			}
		}

	}
}
