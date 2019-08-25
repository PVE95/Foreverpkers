package server.model.players.skills;

import server.Config;
import server.util.Misc;
import server.model.players.Client;
import server.Server;
/**
 * Thieving.java
 *
 * @author Demise
 *
 **/
 
public class Thieving {
	
	private Client c;
		
	public Thieving(Client c) {
		this.c = c;
	}
	
	public void stealFromNPC(int id) {
		if (System.currentTimeMillis() - c.lastThieve < 2000)
			return;
		for (int j = 0; j < npcThieving.length; j++) {
			if (npcThieving[j][0] == id) {
				if (c.playerLevel[c.playerThieving] >= npcThieving[j][1]) {
					if (Misc.random(c.playerLevel[c.playerThieving] + 2 - npcThieving[j][1]) != 1) {
						int cash = Misc.random(npcThieving[j][3] / 2) + (npcThieving[j][3] / 2);
						double thieveBonus =  1 - (c.playerXP[c.playerThieving] / 400000000);
						int thieveChance = 0;

						cash = (int) (cash * (1 + thieveBonus));
						c.getPA().addSkillXP(npcThieving[j][2] * Config.THIEVING_EXPERIENCE, c.playerThieving);
						c.getItems().addItem(995, cash);
						c.startAnimation(881);
						c.lastThieve = System.currentTimeMillis();
						c.sendMessage("You thieve some money...");
						thieveChance = (int) (60 * thieveBonus);
						if(npcThieving[j][0] == 187 && Misc.random(thieveChance) == 0){
							if(c.getItems().freeSlots() > 0){
							c.sendMessage("You find a crystal key!");
							c.getItems().addItem(989, 1);
							} else {
							Server.itemHandler.createGroundItem(c, 989, c.getX(), c.getY(), 1, c.playerId);
							c.sendMessage("You find a crystal key! The key is dropped because you have no inventory space.");
							}
						}
						thieveChance = (int) (800 * thieveBonus);
						if(npcThieving[j][0] == 187 && Misc.random(thieveChance) <= 1){
							if(c.getItems().freeSlots() > 0){
							c.sendMessage("You find a shiny key!");
							c.getItems().addItem(85, 1);
							} else {
							Server.itemHandler.createGroundItem(c, 85, c.getX(), c.getY(), 1, c.playerId);
							c.sendMessage("You find a shiny key! The key is dropped because you have no inventory space.");
							}
						}
						thieveChance = (int) (100 * thieveBonus);
						if(npcThieving[j][0] == 187 && Misc.random(thieveChance) == 0){
							if(c.getItems().freeSlots() > 0){
							c.sendMessage("You find a lockpick!");
							c.getItems().addItem(1523, 1);
							} else {
							Server.itemHandler.createGroundItem(c, 1523, c.getX(), c.getY(), 1, c.playerId);
							c.sendMessage("You find a lockpick! The lockpick is dropped because you have no inventory space.");
							}
						}
							/*if(c.inWild()){
								c.potential++;
								
								if(Misc.random(2) == 0)
									c.potential++;//random bonus
								
								if (c.potential >= c.maxPotential()){
									c.potential = c.maxPotential();
									c.sendMessage("Your potential is at @red@"+c.maxPotential()+"%@bla@ and cannot go any higher.");
								} else {
									c.sendMessage("Your potential has increased to @red@"+c.potential+"%@bla@/@blu@"+c.maxPotential()+"%@bla@ total. Type @blu@::potential @bla@for more info.");
								}
							}*/
						break;
					} else {
						c.setHitDiff(npcThieving[j][4]);
						c.setHitUpdateRequired(true);
						c.playerLevel[3] -= npcThieving[j][4];
						c.getPA().refreshSkill(3);
						c.lastThieve = System.currentTimeMillis() + 2000;
						c.sendMessage("You fail to thieve the NPC.");
						break;
					}
				} else {
					c.sendMessage("You need a thieving level of at least" + npcThieving[j][1] + ".");
				}
			}		
		}
	}
	
	public void stealFromStall(int id, int amount, int xp, int level) {
		if (System.currentTimeMillis() - c.lastThieve < 2500)
			return;
		if (Misc.random(100) == 0) {
			c.randomEvent();
			return;
		}

		double thieveBonus =  1 - (c.playerXP[c.playerThieving] / 400000000);
		if(amount != 1)
			amount = (int) (amount * (1 + thieveBonus));
		
		if (c.playerLevel[c.playerThieving] >= level) {
			if (c.getItems().addItem(id, amount)) {
				c.startAnimation(832);
				c.getPA().addSkillXP(xp * Config.THIEVING_EXPERIENCE, c.playerThieving);
				c.lastThieve = System.currentTimeMillis();
				if(id == 995) {
				c.sendMessage("You take "+amount+" coins.");
				} else {
				c.sendMessage("You take a "+c.getItems().getItemName(id)+".");
				}
			}
		} else {
			c.sendMessage("You must have a thieving level of " + level + " to thieve from this stall.");
		}
	}
	//npc, level, exp, coin amount
	public int[][] npcThieving = {{1,1,8,200,1},{18,25,26,500,1},{9,40,47,1000,2},{26,55,85,1500,3},{20,70,152,750 +  Misc.random(600),4},{21,80,273,3000,5},{187,85,300,10000,6}};

}