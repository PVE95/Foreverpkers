package server.model.minigames;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.ArrayList;

import server.Config;
import server.Server;
import server.model.items.GameItem;
import server.model.items.Item;
import server.util.Misc;
import server.model.players.*;

public class Boxing {

	private Client c;
	public Boxing(Client Client) {
		this.c = Client;
	}

	public void resetBox() {
		c.boxWith = 0;
		c.getPA().requestUpdates();
		c.getCombat().resetPlayerAttack();
		c.boxRequested = false;
		c.sendMessage("Boxing request cancelled.");
	}

	public void requestBox(int id){

		try {
			c.isFlagged = c.isFlaggable();

			Client o = (Client) Server.playerHandler.players[id];
			if (o.inWild()) {
			c.sendMessage("You can't request a boxing match in the wilderness!");
			return;
			}
			if(c.playerRights == 4)
			return;
			if (c.inWild()) {
			c.sendMessage("You can't request a boxing match in the wilderness!");
			return;
			}
			if (c.playerEquipment[c.playerHands] != 7671) {
				c.sendMessage("You need to be wearing boxing gloves to request a boxing match!");
				return;
			}
			if (o.playerEquipment[o.playerHands] != 7671) {
				c.sendMessage("Your opponent needs to be wearing boxing gloves before you can do this!");
				return;
			}
			if (c.KC < 3) {
			c.sendMessage("You need a killcount of 3 to request a boxing match.");
			return;
			}
			if (o.KC < 3) {
			c.sendMessage("Your opponent needs a killcount of 3 to box.");
			return;
			}
			if (o.isBanking)
			{
				c.sendMessage("You can't request a boxing match while the other person is banking.");
				return;
			}
			if (c.isBanking)
			{
				c.sendMessage("No.");
				return;
			}
			if (o.poisonDamage > 0)
			{
				c.sendMessage("Your opponent is poisoned. He needs to drink some anti-poison!");
				//c.getItems().addItem(175, 1);
				return;
			}

			if (c.poisonDamage > 0)
			{
				c.sendMessage("You cant trade poisoned. Try drinking anti-poison.");
				c.getItems().addItem(175, 1);
				return;
			}
			if (o.inTrade) {
			c.sendMessage("That person is trading!");
			//c.getPA().closeAllWindows();
			return;
			}
			
			c.printPacketLog("Player SUCCESSFULLY sent a box request to another player named " + Server.playerHandler.players[id].playerName);
			
			if (id == c.playerId)
				return;
			c.boxWith = id;
			if(!c.inTrade && o.boxRequested && o.boxWith == c.playerId) {
				//c.getTradeAndDuel().openTrade();
				//o.getTradeAndDuel().openTrade();	
				//moveplayerhere;
				//c.getDH().sendDialogues(10506, 1);
				c.getPA().movePlayer(2523, 4778, c.playerId*4);
				o.getPA().movePlayer(2524, 4778, c.playerId*4);
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 1;
				c.redSkull = 1;
				c.getPA().requestUpdates();
				o.isSkulled = true;
				o.skullTimer = 100000;
				o.headIconPk = 1;
				o.redSkull = 1;
				o.getPA().requestUpdates();
				c.getPA().closeAllWindows();
				o.getPA().closeAllWindows();
			} else if(!c.inTrade) {
				c.boxRequested = true;
				c.sendMessage("Sending a boxing match request...(Dangerous)");
				o.sendMessage(""+c.playerName+" wants to box with you. (Dangerous)");
			}
		} 
		catch (Exception e) {
			Misc.println("Error requesting trade.");
		}
	}
}