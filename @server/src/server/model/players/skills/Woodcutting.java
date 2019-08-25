package server.model.players.skills;

import server.model.players.*;
import server.Config;
import server.util.Misc;
import server.model.objects.Object;
import server.event.*;

/**
* @Author Demise
*/

public class Woodcutting {
	
	Client c;
	
	private final int VALID_AXE[] = {1351,1349,1353,1361,1355,1357,1359,6739};
	private final int[] AXE_REQS = {1,1,6,6,21,31,41,61};
	private int logType;
	private int exp;
	private int levelReq;
	private int axeType;
	
	public Woodcutting(Client c) {
		this.c = c;
	}
	
	public void startWoodcutting(int logType, int levelReq, int exp) {
	c.turnPlayerTo(c.objectX, c.objectY);
	if(c.getItems().freeSlots() < 1){
		c.sendMessage("Not enough space in your inventory to do this.");
		return;
		}
	if(c.wcTimer >= 0)
		return;
		
	c.wcTimer = getWcTimer();
	
		if (goodAxe() > 0) {
			if (c.playerLevel[c.playerWoodcutting] >= levelReq) {
				this.logType = logType;
				this.exp = exp;
				this.levelReq = levelReq;
				this.axeType = goodAxe();
				
				c.sendMessage("You swing your axe at the tree.");
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			int count = c.wcTimer;
			
			@Override
			public void execute(CycleEventContainer container) {
				if(count-- > 0) {
				if(axeType==1351)
				c.startAnimation(879);
				if(axeType==1349)
				c.startAnimation(877);
				if(axeType==1353)
				c.startAnimation(875);
				if(axeType==1361)
				c.startAnimation(873);
				if(axeType==1355)
				c.startAnimation(871);
				if(axeType==1357)
				c.startAnimation(869);
				if(axeType==1359)
				c.startAnimation(867);
				if(axeType==6739)
				c.startAnimation(2846);
				} else {
					container.stop();
				}
			}

			@Override
			public void stop() {
			cutWood();
			}
			
		}, 1);
			} else {
				c.getPA().resetVariables();
				c.startAnimation(65535);
				c.sendMessage("You need a woodcutting level of " + levelReq + " to cut this tree.");
			}		
		} else {
			c.startAnimation(65535);
			c.sendMessage("You must have an axe of your woodcutting level to cut this tree.");
			c.getPA().resetVariables();
		}
	}
	
	public void resetWoodcut() {
		this.logType = -1;
		this.exp = -1;
		this.levelReq = -1;
		this.axeType = -1;
		c.wcTimer = -1;	
		c.startAnimation(65535);
	}
	
	public void cutWood() {
		if (c.getItems().addItem(logType,1)) {
			
			c.sendMessage("You get some logs.");
			c.getPA().addSkillXP(exp * Config.WOODCUTTING_EXPERIENCE, c.playerWoodcutting);
			c.getPA().refreshSkill(c.playerWoodcutting);
			
			/*if(c.inWild()){
			c.potential++;
			
			if(Misc.random(3) == 0)
				c.potential++;//random bonus
			
			if (c.potential >= c.maxPotential()){
				c.potential = c.maxPotential();
				c.sendMessage("Your potential is at @red@"+c.maxPotential()+"%@bla@ and cannot go any higher.");
			} else {
				c.sendMessage("Your potential has increased to @red@"+c.potential+"%@bla@/@blu@"+c.maxPotential()+"%@bla@ total. Type @blu@::potential @bla@for more info.");
			}
			} */
			
			//if(Misc.random(10) == 0) {
				new Object(getStumpId(c.objectId), c.objectX, c.objectY, c.heightLevel, c.face, 10, c.objectId, getRespawnTimer(logType));
				c.getPA().resetVariables();
				resetWoodcut();
				c.startAnimation(65535);
			//}
		} else {
			c.getPA().resetVariables();
			c.startAnimation(65535);
		}
	}
	
	public int goodAxe() {
		for (int j = VALID_AXE.length - 1; j >= 0; j--) {
			if (c.playerEquipment[c.playerWeapon] == VALID_AXE[j]) {
				if (c.playerLevel[c.playerWoodcutting] >= AXE_REQS[j])
					return VALID_AXE[j];
			}		
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			for (int j = VALID_AXE.length - 1; j >= 0; j--) {
				if (c.playerItems[i] == VALID_AXE[j] + 1) {
					if (c.playerLevel[c.playerWoodcutting] >= AXE_REQS[j])
						return VALID_AXE[j];
				}
			}		
		}
		return - 1;
	}
	
	public int getWcTimer() {
		int time = Misc.random(5) + 5;
		if(logType == 1151)
			time += 2;
		if(logType == 1521)
			time += 5;
		if(logType == 1519)
			time += 7;
		if(logType == 1517)
			time += 10;
		if(logType == 1515)
			time += 25;
		if(logType == 1513)
			time += 50;
		return time;
	}
	
	public int getStumpId(int objectId) {
		switch(objectId){
		case 1281: //oak
			return 1356;
		case 1308://willow
			return 4329;
		case 1307://maple
			return 1343;
		case 1309://yew
			return 1356;
		case 1306://magic
			return 7401;
			}
		
		return 1342;//norm
	}
	
	public int getRespawnTimer(int logType) {
		int time = Misc.random(10) + 5;
		if(logType == 1151)
			time += 2;
		if(logType == 1521)
			time += 5;
		if(logType == 1519)
			time += 7;
		if(logType == 1517)
			time += 10;
			if(logType == 1515)
			time += 30;
			if(logType == 1513)
			time += 40;
		return time;
	}

}