package server.model.players.packets;

/**
 * @author Ryan / Lmctruck30
 */

import server.model.items.UseItem;
import server.model.players.Client;
import server.model.players.PacketType;
import server.Server;

public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		/*
		 * a = ?
		 * b = ?
		 */
		
		int a = c.getInStream().readUnsignedWord();
		int objectId = c.getInStream().readSignedWordBigEndian();
		int objectY = c.getInStream().readSignedWordBigEndianA();
		int b = c.getInStream().readUnsignedWord();
		int objectX = c.getInStream().readSignedWordBigEndianA();
		int itemId = c.getInStream().readUnsignedWord();
		c.printPacketLog("Player used a " + c.getItems().getItemName(itemId) + " on object id: " + objectId + " X:" + objectX + " Y:" +objectY);
		UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);

		switch(objectId)
		{
		
		case 406:
		if(!c.goodDistance(c.absX, c.absY, 2518, 4777, 2))
			return;
		
			if(itemId == 6335 && c.getItems().playerHasItem(6341) && c.getItems().playerHasItem(6343))
			{
				c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
				//client, npcId, x, y, height, walk, health, maxhit, atk, defense, atkplayer, headicon
				Server.npcHandler.spawnNpc(c, 2499, 2522, 4775, c.heightLevel, 0, 450, 35, 225, 275, true, false); //green
				Server.npcHandler.spawnNpc(c, 2501, 2524, 4777, c.heightLevel, 0, 475, 45, 200, 375, true, false); //range
				Server.npcHandler.spawnNpc(c, 2503, 2522, 4779, c.heightLevel, 0, 350, 40, 225, 325, true, false); //mage
				
				c.getItems().deleteItem(6335, 1);
				c.getItems().deleteItem(6341, 1);
				c.getItems().deleteItem(6343, 1);
			}
			if(itemId == 6341 && c.getItems().playerHasItem(6335) && c.getItems().playerHasItem(6343))
			{
				c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
				Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
				Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
				Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
				
				c.getItems().deleteItem(6335, 1);
				c.getItems().deleteItem(6341, 1);
				c.getItems().deleteItem(6343, 1);
			}
			if(itemId == 6343 && c.getItems().playerHasItem(6341) && c.getItems().playerHasItem(6335))
			{
				c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
				Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
				Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
				Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
				
				c.getItems().deleteItem(6335, 1);
				c.getItems().deleteItem(6341, 1);
				c.getItems().deleteItem(6343, 1);
			}
			
			//white broodoo outfit
			else if(itemId == 6339 && c.getItems().playerHasItem(6351) && c.getItems().playerHasItem(6353))
			{
					c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
					Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
					Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
					Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
					
					c.getItems().deleteItem(6339, 1);
					c.getItems().deleteItem(6351, 1);
					c.getItems().deleteItem(6353, 1);
			}
			else if(itemId == 6351 && c.getItems().playerHasItem(6339) && c.getItems().playerHasItem(6353))
			{
					c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
					Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
					Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
					Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
					
					c.getItems().deleteItem(6339, 1);
					c.getItems().deleteItem(6351, 1);
					c.getItems().deleteItem(6353, 1);
			}
			else if(itemId == 6353 && c.getItems().playerHasItem(6351) && c.getItems().playerHasItem(6339))
			{
					c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
					Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
					Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
					Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
					
					c.getItems().deleteItem(6339, 1);
					c.getItems().deleteItem(6351, 1);
					c.getItems().deleteItem(6353, 1);
			}
			
			//orange broodoo outfit
			else if(itemId == 6337 && c.getItems().playerHasItem(6361) && c.getItems().playerHasItem(6363))
			{
					c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
					Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
					Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
					Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
					
					c.getItems().deleteItem(6337, 1);
					c.getItems().deleteItem(6361, 1);
					c.getItems().deleteItem(6363, 1);
			}
			else if(itemId == 6361 && c.getItems().playerHasItem(6337) && c.getItems().playerHasItem(6363))
			{
					c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
					Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
					Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
					Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
					
					c.getItems().deleteItem(6337, 1);
					c.getItems().deleteItem(6361, 1);
					c.getItems().deleteItem(6363, 1);
			}
			else if(itemId == 6363 && c.getItems().playerHasItem(6361) && c.getItems().playerHasItem(6337))
			{
					c.sendMessage("As you use the mask on the grave, 3 broodoo brothers come to life!");
					Server.npcHandler.spawnNpc(c, 2499, 2523, 4772, c.heightLevel, 0, 350, 35, 225, 200, true, false);
					Server.npcHandler.spawnNpc(c, 2501, 2529, 4777, c.heightLevel, 0, 375, 45, 200, 225, true, false);
					Server.npcHandler.spawnNpc(c, 2503, 2525, 4784, c.heightLevel, 0, 250, 40, 225, 225, true, false);
					
					c.getItems().deleteItem(6337, 1);
					c.getItems().deleteItem(6361, 1);
					c.getItems().deleteItem(6363, 1);
			}
			else
			{
				c.sendMessage("You don't have the correct items to interact with this grave.");
			}
			break;
			
			
			/*default:
				c.sendMessage("Nothing interesting happens");
				break;*/

			}
		
	}

}
