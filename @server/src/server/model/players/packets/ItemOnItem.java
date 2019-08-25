package server.model.players.packets;

/**
 * @author Ryan / Lmctruck30
 */

import server.model.items.UseItem;
import server.model.players.Client;
import server.model.players.PacketType;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int usedWithSlot = c.getInStream().readUnsignedWord();
		int itemUsedSlot = c.getInStream().readUnsignedWordA();
		int useWith = c.playerItems[usedWithSlot] - 1;
		int itemUsed = c.playerItems[itemUsedSlot] - 1;
		c.printPacketLog("Player used a " + c.getItems().getItemName(itemUsed) + " with a " + c.getItems().getItemName(useWith) + ".");
		UseItem.ItemonItem(c, itemUsed, useWith);

		switch(itemUsed)
		  {
		  case 11788:
		   if(useWith == 6235)
		   {
		    c.getItems().deleteItem(6235, 1); //green shield
		    c.getItems().deleteItem(11788, 1);
		    c.getItems().addItem(6215, 1);
		    c.sendMessage("As you pour the potion onto the shield, you feel the shield regenerate.");
		   }
		   if(useWith == 6257)
		   {
		    c.getItems().deleteItem(6257, 1); //orange shield
		    c.getItems().deleteItem(11788, 1);
		    c.getItems().addItem(6237, 1);
		    c.sendMessage("As you pour the potion onto the shield, you feel the shield regenerate.");
		   }
		   if(useWith == 6279)
		   {
		    c.getItems().deleteItem(6279, 1); //while shield
		    c.getItems().deleteItem(11788, 1);
		    c.getItems().addItem(6259, 1);
		    c.sendMessage("As you pour the potion onto the shield, you feel the shield regenerate.");
		   }
		   break;
		  }

	}

}
