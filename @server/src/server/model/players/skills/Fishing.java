package server.model.players.skills;

import server.Config;
import server.util.Misc;
import server.Server;
import server.model.players.Client;
import server.model.npcs.NPCHandler;
import server.model.npcs.NPC;
/**
 * Fishing.java
 *
 * @author Demise
 *
 **/
 
public class Fishing {
	
	private Client c;
	private int fishType;
	private int exp;
	private int req;
	private int thisSlot;
	private int equipmentType;
	private int index;
	private final int SALMON_EXP = 70;
	private final int SWORD_EXP = 100;
	private final int SALMON_ID = 331;
	private final int SWORD_ID = 371;
	public boolean fishing = false;

	
	private final int[] REQS = {1,20,40,35,62,76,81,90,85};
	private final int[] FISH_TYPES = {317,335,377,359,7944,383,389,395,3142};
	private final int[] EXP = {10,50,80,90,120,110,46,135,120};
	private final int[] FISH_EMOTE = {621,2575,619,618,621,618,621,2575,621};
	
	public Fishing(Client c) {
		this.c = c;
	}
	
	public void setupFishing(int fishType) {
		if (c.getItems().playerHasItem(getEquipment(fishType))) {
			if (c.playerLevel[c.playerFishing] >= req) {
				int slot = getSlot(fishType);
					if (slot > -1) {
						if(c.getItems().freeSlots() < 1){
							c.sendMessage("Not enough space in your inventory to do this.");
							resetFishing();
							return;
						}
						this.req = REQS[slot];
						this.fishType = FISH_TYPES[slot];
						this.equipmentType = getEquipment(fishType);
						this.exp = EXP[slot];
						this.thisSlot = slot;
						this.index = c.npcClickIndex;
						c.fishing = true;
						c.fishTimer = 3 + Misc.random(2);
						c.startAnimation(FISH_EMOTE[slot]);
					}
			} else {
				c.sendMessage("You need a fishing level of " + req + " to fish here.");
				resetFishing();
			}
		} else {
			c.sendMessage("You do not have the correct equipment to use this fishing spot.");
			resetFishing();
		}
	}
	
	public void catchFish() {
		int index = this.index;
		if (c.getItems().playerHasItem(getEquipment(fishType))) {
			if (c.playerLevel[c.playerFishing] >= req) {
				if (c.getItems().freeSlots() > 0) {
					if(fishType == 395) {
						if(Misc.random(1) == 0) {
							c.getItems().addItem(fishType,1);
							c.getPA().addSkillXP(exp * Config.FISHING_EXPERIENCE,c.playerFishing);
							c.sendMessage("You catch a fish.");
						} else {
							c.sendMessage("You fail to catch anything.");
						}
					} else {
						if (canFishOther(fishType)) {
							c.getItems().addItem(otherFishId(fishType),1);
							c.getPA().addSkillXP(otherFishXP(fishType) * Config.FISHING_EXPERIENCE,c.playerFishing);
						} else {
							c.getItems().addItem(fishType,1);
							c.getPA().addSkillXP(exp * Config.FISHING_EXPERIENCE,c.playerFishing);
						}
						c.sendMessage("You catch a fish.");
					}

					if(fishType == 395)
					c.fishTimer = 2 + Misc.random(5 + ((99 - c.playerLevel[c.playerFishing]) / 2));
						else
					c.fishTimer = 2 + Misc.random(10 - ((c.playerLevel[c.playerFishing] - req) / 2));

					if(c.fishTimer <= 1)
						c.fishTimer = 2;

					c.startAnimation(FISH_EMOTE[this.thisSlot]);
					
					if(Server.npcHandler.npcs[c.npcClickIndex].isDead)
						resetFishing();
				} else {
					c.sendMessage("Not enough space in your inventory to do this.");
					resetFishing();
				}
			} else {
				c.sendMessage("You need a fishing level of " + req + " to fish here.");
				resetFishing();
			}
		} else {
			c.sendMessage("You do not have the correct equipment to use this fishing spot.");
			resetFishing();
		}
	}
	
	private int getSlot(int fishType) {
		for (int j = 0; j < REQS.length; j++)
			if (FISH_TYPES[j] == fishType)
				return j;
		return -1;
	}
	
	private int getEquipment(int fish) {
		if (fish == 317) //shrimp
			return 303;
		if (fish == 335) //trout + salmon
			return 309;
		if (fish == 377) //lobs
			return 301;
		if (fish == 359)//tuna
			return 311;
		if (fish == 7944)//monks
			return 303;
		if (fish == 383)//sharks
			return 311;
		if (fish == 389)//mantas
			return 303;
		if (fish == 3142)//karambwan
			return 303;
		if (fish == 395)//turtle
			return 307;
		return -1;
	}
	
	private boolean canFishOther(int fishType) {			
		if (fishType == 335 && c.playerLevel[c.playerFishing] >= 30)
			return true;
		if (fishType == 359 && c.playerLevel[c.playerFishing] >= 50)
			return true;
		return false;
	}
	
	private int otherFishId(int fishType) {
		if (fishType == 335)
			return SALMON_ID;
		else if (fishType == 359)
			return SWORD_ID;
		return -1;
	}
	
	private int otherFishXP(int fishType) {
		if (fishType == 335)
			return SALMON_EXP;
		else if (fishType == 359)
			return SWORD_EXP;
		return 0;
	}
	
	public void resetFishing() {
		this.exp = 0;
		this.fishType = -1;
		this.equipmentType = -1;
		this.req = 0;
		c.fishTimer = -1;
		c.fishing = false;
		c.startAnimation(65535);
	}
}