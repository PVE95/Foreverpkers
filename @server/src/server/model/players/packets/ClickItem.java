package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;
import server.model.players.PlayerHandler;
import server.Server;
import server.model.items.BookHandler;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();

		c.printPacketLog("Player clicked item " + c.getItems().getItemName(itemId) + " in item slot " + itemSlot);

		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		

			if (c.getFood().isFood(itemId))
				c.getFood().eat(itemId,itemSlot);	
	
		/**
		 *SEA_TURTLE(397,22,"Sea Turtle"),
		 *TUNA_POTATO(7060,22,"Tuna Potato"),
		 *ROCKTAIL(15055,24,"Rocktail");
		 */
   	    if (itemId == 4155) {
   	    	c.sendMessage("Your assignment is to slay "+c.taskAmount+" "+c.getTaskName()+"s.");
		}
		if (itemId == 7509) {
			if (c.playerLevel[3] <= 1) {
				c.sendMessage("You seem to be low health, and you don't risk attemping to eat it.");
			} else {
				c.playerLevel[3]--;
				c.setHitUpdateRequired(true);
				c.setHitDiff(1);
				c.updateRequired = true;
				c.getPA().refreshSkill(3);
			}
		}
		 
		if (itemId == 4447) {//antique
			if(c.lampStart2 <= System.currentTimeMillis() - 3600000) {
				c.lampStart2 = System.currentTimeMillis();
				c.getItems().deleteItem2(4447, 1);
				c.sendMessage("You rub the mysterious lamp.");
				c.sendMessage("You will now have @blu@50% Bonus Drop rates@bla@ from monsters for 1 hour.");
				c.sendMessage("The lamp turns into dust.");
				c.getPA().closeAllWindows();
			} else {
				c.sendMessage("@red@You already have a Drop rate lamp active.");
				c.getPA().closeAllWindows();
			}
		}
		if(itemId == 407) {
			c.getItems().deleteItem(407, itemSlot, 1);
			if(Misc.random(3) == 0) {
				c.getItems().addItem(411, 1);
				c.sendMessage("As you open the oyster, you find a pearl inside!");
			}
			else {
				c.sendMessage("As you open the oyster, you find nothing inside.");
			}
		}
		if (itemId == 2528) {
			if(c.lampStart <= System.currentTimeMillis() - 3600000) {
				c.lampStart = System.currentTimeMillis();
				c.getItems().deleteItem2(2528, 1);
				c.sendMessage("You rub the mysterious lamp.");
				c.sendMessage("You will now gain @blu@50% Bonus experience@bla@ in all skills for 1 hour.");
				c.sendMessage("The lamp turns into dust.");
				c.getPA().closeAllWindows();
			} else {
				c.sendMessage("@red@You already have a Bonus experience lamp active.");
				c.getPA().closeAllWindows();
			}
		}
			
			
		if(itemId == 6199) {
			if(c.getItems().freeSlots() > 2 && c.getItems().playerHasItem(6199,1)){
				c.getItems().deleteItem(6199, 1);
				c.theItem = c.randomVote();
				c.getItems().addItem(c.votePresent[c.theItem],c.voteAmount[c.theItem]);
				c.sendMessage("You open the Vote box.");
				c.sendAll("@dre@"+c.playerName+"@red@ opened a Vote box and received @dre@"+c.voteAmount[c.theItem]+"x "+c.getItems().getItemName(c.votePresent[c.theItem])+"@red@!");
			} else {
				c.sendMessage("Not enough inventory space.");
			}
		}
		if(itemId == 6542){
			int theChance = Misc.random(100);
			int theItem = c.randomDPresent();

			if(c.getItems().freeSlots() > 2 && c.getItems().playerHasItem(6542,1)) {

				c.getItems().deleteItem(6542,itemSlot,1);
				if(theChance <= 30) {
					c.getItems().addItem(theItem, 1);
					c.sendMessage("You open the Present.");
				} else if(theChance > 30 && theChance <= 55) {
					theItem = c.randomDPresent2();
					c.getItems().addItem(theItem, 1);
					c.sendMessage("You open the Present.");
					c.sendAll("@dre@"+c.playerName+"@red@ opened a Present and received a @dre@ "+c.getItems().getItemName(theItem)+"@red@!");
				} else if(theChance > 55 && theChance <= 75) {
					theItem = c.randomDPresent3();
					c.getItems().addItem(theItem, 1);
					c.sendMessage("You open the Present.");
					c.sendAll("@dre@"+c.playerName+"@red@ opened a Present and received a @dre@ "+c.getItems().getItemName(theItem)+"@red@!");
				} else if(theChance > 75 && theChance <= 90) {
					theItem = c.randomDPresent4();
					c.getItems().addItem(theItem, 1);
					c.sendMessage("You open the Present.");
					c.sendAll("@dre@"+c.playerName+"@red@ opened a Present and received a @dre@ "+c.getItems().getItemName(theItem)+"@red@!");
				} else if(theChance > 90 && theChance <= 96) {
					theItem = c.randomDPresent5();
					c.getItems().addItem(theItem, 1);
					c.sendMessage("You open the Present.");
					c.sendAll("@dre@"+c.playerName+"@red@ opened a Present and received a @dre@ "+c.getItems().getItemName(theItem)+"@red@!");
				} else if(theChance > 96 && theChance <= 99) {
					theItem = c.randomDPresent6();
					c.getItems().addItem(theItem, 1);
					c.sendMessage("You open the Present.");
					c.sendAll("@dre@"+c.playerName+"@red@ opened a Present and received a @dre@ "+c.getItems().getItemName(theItem)+"@red@!");
				} else if(theChance == 100) {
					theItem = c.randomDPresent7();
					c.getItems().addItem(theItem, 1);
					c.sendMessage("You open the Present.");
					c.sendAll("@dre@"+c.playerName+"@red@ opened a Present and received a @dre@ "+c.getItems().getItemName(theItem)+"@red@!");
				}
			} else {
				c.sendMessage("Not enough inventory space.");
			}
		}

		if(itemId == 405){
			int theItem = c.randomCasket();
			int theItem2 = c.randomCasket();
			int theItem3 = c.randomCasket2();
			if(c.getItems().freeSlots() >= 12 && c.getItems().playerHasItem(405,1)){
				c.getItems().deleteItem(405,itemSlot,1);
				c.getItems().addItem(theItem,1);
					if(Misc.random(100) >= 35) {
							//c.sendAll("@dre@"+c.playerName+" @red@opened a @dre@Casket@red@ and received 1x @dre@"+c.getItems().getItemName(theItem)+"@red@!");
						} else {
							c.getItems().addItem(theItem2,1);
							//c.sendAll("@dre@"+c.playerName+" @red@opened a @dre@Casket@red@ and received 1x @dre@"+c.getItems().getItemName(theItem)+"@red@ and 1x @dre@"+c.getItems().getItemName(theItem2)+"@red@!");
					}
					for (int i = 0; i < 10; i++) {
						theItem3 = c.randomCasket2();
						c.getItems().addItem(theItem3,1);
					}
				c.sendMessage("@blu@You open the casket which contained a plunder of items!");
				} else {
					c.sendMessage("You need 12 free inventory spaces to open this.");
			}
		}

		if(itemId == 784){
				//c.bookHandler.DROP_GUIDE.openBook(c);
		}
		//old casket with phats
		/*if(itemId == 405){
			int theItem = c.randomPhat();
			c.getItems().deleteItem(405,itemSlot,1);
			c.getItems().addItem(theItem,1);
			c.sendAll("@red@"+c.playerName+" opened a Casket and received a "+c.getItems().getItemName(theItem)+"!");
		}*/
		
		if (itemId == 8007) {
			c.getPA().teleTab("varrock");
		}
		if (itemId == 8008) {
			c.getPA().teleTab("lumbridge");
		}
		if (itemId == 8009) {
			c.getPA().teleTab("falador");
		}
		if (itemId == 8010) {
			c.getPA().teleTab("camelot");
		}
		if (itemId == 8011) {
			c.getPA().teleTab("ardy");
		}

		if(itemId == 2784 && c.getItems().playerHasItem(2784,1)) {
		c.getItems().deleteItem(2784, 1);
		int randomCasket = Misc.random(4);
		if(randomCasket == 0) {
		c.getItems().addItem(995, 25000);
		} else if (randomCasket == 1) {
		c.getItems().addItem(995, 40000);
		} else if (randomCasket == 2) {
		c.getItems().addItem(995, 50000);
		} else if (randomCasket == 3) {
		c.getItems().addItem(995, 60000);
		} else if (randomCasket == 4) {
		c.getItems().addItem(995, 80000);
		}
		}
		/*if(itemId == 6199) {
		c.getItems().deleteItem(6199, 1);
		int randomReward = Misc.random(20);
		if(randomReward == 0) {
		c.getItems().addItem(11694, 1);
		} else if (randomReward == 1) {
		c.getItems().addItem(11696, 1);
		} else if (randomReward == 2) {
		c.getItems().addItem(11698, 1);
		} else if (randomReward == 3) {
		c.getItems().addItem(11700, 1);
		} else if (randomReward == 4) {
		c.getItems().addItem(11724, 1);
		} else if (randomReward == 5) {
		c.getItems().addItem(11726, 1);
		} else if (randomReward == 6) {
		c.getItems().addItem(11728, 1);
		} else if (randomReward == 7) {
		c.getItems().addItem(11718, 1);
		} else if (randomReward == 8) {
		c.getItems().addItem(11720, 1);
		} else if (randomReward == 9) {
		c.getItems().addItem(11722, 1);
		} else if (randomReward == 10) {
		c.getItems().addItem(1053, 1);
		} else if (randomReward == 11) {
		c.getItems().addItem(1055, 1);
		} else if (randomReward == 12) {
		c.getItems().addItem(1057, 1);
		} else if (randomReward == 13) {
		c.getItems().addItem(1050, 1);
		} else if (randomReward == 14) {
		c.getItems().addItem(1037, 1);
		} else if (randomReward == 15) {
		c.getItems().addItem(11284, 1);
		} else if (randomReward == 16) {
		c.getItems().addItem(3140, 1);
		} else if (randomReward == 17) {
		c.getItems().addItem(10887, 1);
		} else if (randomReward == 18) {
		c.getItems().addItem(995, 50000000);
		} else if (randomReward == 19) {
		c.getItems().addItem(11722, 1);
		} else if (randomReward == 20) {
		c.getItems().addItem(4151, 1);
		}
		}*/
 		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		if (c.getHerblore().isUnidHerb(itemId))
			c.getHerblore().handleHerbClick(itemId);
		//ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId,itemSlot);
		if (c.getPrayer().isBone(itemId))
			c.getPrayer().buryBone(itemId, itemSlot);
		/*if (itemId == 952) {
			if(c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			}
		}*/
	}

}
