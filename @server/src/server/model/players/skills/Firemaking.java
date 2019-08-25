package server.model.players.skills;

import server.model.objects.*;
import server.model.players.*;
import server.*;
import server.clip.region.*;

/**
 * Firemaking.java
 * 
 * @author Sanity
 * 
 **/
public class Firemaking {

    private Client c;

    private int[] logs = { 1511, 1521, 1519, 1517, 1515, 1513 };
    private int[] level = { 1, 15, 30, 45, 60, 75 };
    public long lastLight;
    private int DELAY = 1250;
    public boolean resetAnim = false;

    public Firemaking(Client c) {
	this.c = c;
    }

    public void checkLogType(int logType, int otherItem) {
	for (int j = 0; j < logs.length; j++) {
	    if (logs[j] == logType || logs[j] == otherItem) {
		lightFire(j);
		return;
	    }
	}
    }

    public void lightFire(int slot) {
    	if (c.duelStatus >= 5) {
	    return;
	}
	
	if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			c.getPA().walkTo(-1, 0);
		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
			c.getPA().walkTo(1, 0);
		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
			c.getPA().walkTo(0, -1);
		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
			c.getPA().walkTo(0, 1);
		}
	
	if (c.playerLevel[c.playerFiremaking] >= level[slot]) {
			if (c.getItems().playerHasItem(590) && c.getItems().playerHasItem(logs[slot])) {
				if (System.currentTimeMillis() - lastLight > DELAY) {
					c.startAnimation(733,0);
					c.getItems().deleteItem(logs[slot], c.getItems().getItemSlot(logs[slot]), 1);
					c.getPA().addSkillXP(logs[slot] * Config.FIREMAKING_EXPERIENCE, c.playerFiremaking);
					Server.objectHandler.setFire(c, 2732, c.getX(),c.getY());
					c.sendMessage("You light the fire.");
					c.turnPlayerTo(c.getX() + 1, c.getY());
					this.lastLight = System.currentTimeMillis();
					resetAnim = true;
				}
			}
		}	
	}
	
}