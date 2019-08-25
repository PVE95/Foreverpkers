package server.model.players.skills;

import server.model.players.Client;
import server.util.Misc;
import server.Config;

public class Cooking {
	
	Client c;
	private int slot;
	private int fishType;
	private int req;
	private int exp;

	public Cooking(Client c) {
		this.c = c;
	}

	
	private int[][] cookingItems = {{317,315,7954,1,20},{2138,2140,2144,1,10},{335,333,323,20,30},{331,329,323,30,40},{359,361,363,35,50},{377,379,381,40,60},{371,373,375,50,70},{7944,7946,7948,62,80},{383,385,387,80,90},{3142,3144,3148,85,100},{395,397,399,90,110},{389,391,393,91,115}};
	
	public void itemOnObject(int id) {
		for (int j = 0; j < cookingItems.length; j++) {
			if (cookingItems[j][0] == id) {
				//cookFish(cookingItems[j][0],j);

				this.slot = j;
				c.iscooking = true;
				setupCooking(id);
			}
		}
	}

	/*public int fishSlot() {
		for (int j = 0; j < cookingItems.length; j++) {
			if (cookingItems[j][0] == id)
		}
	}*/
	
	public void setupCooking(int fishType) {
		int slot = this.slot;
		if (c.getItems().playerHasItem(fishType, 1)) {
			this.req = cookingItems[slot][3];
			if (c.playerLevel[c.playerCooking] >= cookingItems[slot][3]) {
					if (slot > -1) {
						//this.req = cookingItems[slot][3];
						this.fishType = cookingItems[slot][0];
						this.exp = cookingItems[slot][4];
						c.iscooking = true;
						c.cookTimer = 4;
						c.startAnimation(883);
					}
			} else {
				c.sendMessage("You need a cooking level of " + req + " to cook this.");
				resetCooking();
			}
		} else {
			c.sendMessage("You do not have anything to cook.");
			resetCooking();
		}
	}
	public void resetCooking() {
		c.cookTimer = 0;
		c.iscooking = false;
	}
	public int cookingBonus() {
		if(c.playerEquipment[c.playerHands] == 775)
			return 10;

		return 20;
	}
	public void cookFish() {
		//for (int j = 0; j < 28; j++) {
		boolean noBurn = false;
			if (c.getItems().playerHasItem(this.fishType,1)) {
				if (c.playerLevel[c.playerCooking] >= cookingItems[this.slot][3]) {
					if((c.playerLevel[c.playerCooking] - cookingItems[this.slot][3]) - cookingBonus() > 0)//10 higher w/ gauntlets or 20 higher levels for no burn
						noBurn = true;
					if (Misc.random(c.playerLevel[c.playerCooking] + 5 - cookingItems[this.slot][3]) <= 1 && !noBurn) {
						c.sendMessage("You accidently burn the fish.");
						c.getItems().deleteItem(this.fishType, c.getItems().getItemSlot(this.fishType), 1);
						c.getItems().addItem(cookingItems[this.slot][2], 1);
					} else {
						c.getItems().deleteItem(this.fishType, c.getItems().getItemSlot(this.fishType), 1);
						c.getItems().addItem(cookingItems[this.slot][1], 1);
						c.getPA().addSkillXP(cookingItems[this.slot][4] * Config.COOKING_EXPERIENCE, c.playerCooking);
					}
					c.cookTimer = 4;
					c.startAnimation(883);
				} else {
					c.sendMessage("You need a cooking level of " + cookingItems[slot][3] + " to cook this fish.");
					resetCooking();
				}
			} else {
				resetCooking();
			}
		//}
	}
	
	
}