package server.model.players.skills;

import server.model.players.*;
import server.Config;
import server.util.Misc;
import server.model.objects.Object;
import server.event.*;

/**
* @Author Demise
*/

public class Mining {
	
	Client c;
	
	private final int VALID_PICK[] = {1265,1267,1269,1273,1271,1275};
	private final int[] PICK_REQS = {1,1,6,6,21,31,41,61};
	private final int[] RANDOM_GEMS = {1623,1621,1619,1617};
	private final int[] RARE_GEMS = {6571,1631,1631};
	private int oreType;
	private int exp;
	private int levelReq;
	private int pickType;
	private final int EMOTE = 625;
	
	public Mining(Client c) {
		this.c = c;
	}
	
	public void startMining(int oreType, int levelReq, int exp) {
		c.turnPlayerTo(c.objectX, c.objectY);
		if(oreType != 451 && c.inWild()) {
			c.sendMessage("You can only mine Runite in the wilderness.");
			return;
		}
		if(c.getItems().freeSlots() < 1){
			c.sendMessage("Not enough space in your inventory to do this.");
			return;
		}
		if(c.miningTimer >= 0)
			return;
		this.pickType = goodPick();
		c.miningTimer = getMiningTimer(oreType, pickType);
		if (goodPick() > 0) {
			if (c.playerLevel[c.playerMining] >= levelReq) {
				this.oreType = oreType;
				this.exp = exp;
				this.levelReq = levelReq;
				
				c.startAnimation(EMOTE);
				c.sendMessage("You swing your pick at the rock.");
				
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			int count = c.miningTimer;
			
			@Override
			public void execute(CycleEventContainer container) {
				if(count-- > 0) {
					
				} else {
					container.stop();
				}
			}

			@Override
			public void stop() {
			mineOre();
			}}, 1);
			
				
			} else {
				resetMining();
				c.sendMessage("You need a mining level of " + levelReq + " to mine this rock.");
				c.startAnimation(65535);
			}		
		} else {
			resetMining();
			c.sendMessage("You need a pickaxe to mine this rock.");
			c.startAnimation(65535);
			c.getPA().resetVariables();
		}
	}
	
	public void mineOre() {
		if (c.getItems().addItem(oreType,1)) {
			c.sendMessage("You manage to mine some ore.");
			c.getPA().addSkillXP(exp * Config.MINING_EXPERIENCE, c.playerMining);
			c.getPA().refreshSkill(c.playerMining);
			new Object(450, c.objectX, c.objectY, c.heightLevel, c.face, 10, c.objectId, getRespawnTimer(oreType));
			if (Misc.random(100) == 1) {
				if(oreType == 451 || oreType == 444){
				c.getItems().addItem(RARE_GEMS[(int)(RARE_GEMS.length * Math.random())], 1);
				c.sendMessage("You find a rare gem from the rock!");
				} else {
				c.getItems().addItem(RANDOM_GEMS[(int)(RANDOM_GEMS.length * Math.random())], 1);
				c.sendMessage("You find an uncommon gem from the rock.");
				}
			}
			/*if(c.inWild()){
			c.potential++;
			
			if(Misc.random(3) == 0)
				c.potential++;//random bonus
			
				if (c.potential >= c.maxPotential()){
						c.potential = c.maxPotential();
						c.sendMessage("Your potential is at @red@"+c.maxPotential()+"%@bla@ and cannot go any higher.");
					} else {
					if(Misc.random(2) == 0)
						c.sendMessage("Your potential has increased to @red@"+c.potential+"%@bla@/@blu@"+c.maxPotential()+"%@bla@ total. Type @blu@::potential @bla@for more info.");
				}
			}*/
			resetMining();
			c.getPA().resetVariables();
			c.startAnimation(65535);
		} else {
			c.getPA().resetVariables();
			c.startAnimation(65535);
		}
	}
	
	public void resetMining() {
		c.miningTimer = -1;
		this.oreType = -1;
		this.exp = -1;
		this.levelReq = -1;
		this.pickType = -1;
		c.startAnimation(65535);
	}
	
	public int goodPick() {
		for (int j = VALID_PICK.length - 1; j >= 0; j--) {
			if (c.playerEquipment[c.playerWeapon] == VALID_PICK[j]) {
				if (c.playerLevel[c.playerMining] >= PICK_REQS[j])
					return VALID_PICK[j];
			}		
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			for (int j = VALID_PICK.length - 1; j >= 0; j--) {
				if (c.playerItems[i] == VALID_PICK[j] + 1) {
					if (c.playerLevel[c.playerMining] >= PICK_REQS[j])
						return VALID_PICK[j];
				}
			}		
		}
		return - 1;
	}
	
	public int getMiningTimer(int ore, int pick) {
		int time = Misc.random(5);
		if (ore == 436 || ore == 438) {
			time += 3;
		}
		if (ore == 440) {
			time += 4;
		}
		if (ore == 453) {
			time += 5;
		}
		if (ore == 447) {
			time += 6;
		}
		if (ore == 449) {
			time += 9;
		}
		if (ore == 444) {
			time += 11;
		}
		if (ore == 451) {
			time += 15;
		}
		
		//{1265,1267,1269,1273,1271,1275};
		if(pick == 1267)
			time -= 1;
		if(pick == 1269)
			time -= 2;
		if(pick == 1273)
			time -= 3;
		if(pick == 1271)
			time -= 4;
		if(pick == 1275)
			time -= 5;
		
		return time;
	}
	
	public int getRespawnTimer(int ore) {
		int time = Misc.random(5);
		if (ore == 436 || ore == 438) {
			time += 5;
		}
		if (ore == 440) {
			time += 7;
		}
		if (ore == 453) {
			time += 25;
		}
		if (ore == 447) {
			time += 35;
		}
		if (ore == 449) {
			time += 50;
		}
		if (ore == 444) {
			time += 120;
		}
		if (ore == 451) {
			time += 80;
		}
		return time;
	}
	
}