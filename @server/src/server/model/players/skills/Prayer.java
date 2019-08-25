package server.model.players.skills;


import server.Config;
import server.model.players.Client;
public class Prayer {

	Client c;
	
	public int[][] bonesExp = {{526,5},{532,15},{534,80},{536,190},{6729,230}, {6812, 276}, {4834, 750}};	
	
	public Prayer(Client c) {
		this.c = c;
	}
	
	public void buryBone(int id, int slot) {
		if(System.currentTimeMillis() - c.buryDelay > 1500) {
			c.getItems().deleteItem(id, slot, 1);
			c.sendMessage("You bury the bones.");
			if(c.worshippedGod == 1) {
				c.getPA().addSkillXP(getExp(id)*Config.PRAYER_EXPERIENCE*2,5);
			} else {
				c.getPA().addSkillXP(getExp(id)*Config.PRAYER_EXPERIENCE,5);
			}
			c.buryDelay = System.currentTimeMillis();
			c.startAnimation(827);
		}	
	}
	
	public void bonesOnAltar(int id) {
		c.getItems().deleteItem(id, c.getItems().getItemSlot(id), 1);
		c.sendMessage("The gods are pleased with your offering.");
		c.getPA().addSkillXP(getExp(id)*4*Config.PRAYER_EXPERIENCE, 5);
		c.startAnimation(896);
		c.gfx0(247);
	}
	
	public boolean isBone(int id) {
		for (int j = 0; j < bonesExp.length; j++)
			if (bonesExp[j][0] == id)
				return true;
		return false;
	}
	
	public int getExp(int id) {
		for (int j = 0; j < bonesExp.length; j++) {
			if (bonesExp[j][0] == id)
				return bonesExp[j][1];
		}
		return 0;
	}
}