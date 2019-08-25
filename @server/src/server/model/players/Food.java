package server.model.players;
import server.model.players.CombatAssistant;


import java.util.HashMap;

/**
 * @author Sanity
 */

public class Food {
	
	
	private Client c;
	
	public Food (Client c) {
		this.c = c;	
	}
	public static enum FoodToEat {
		BANANA(1963,2,"Banana"),
		SHRIMPS(315,3,"Shrimps"),
		COOKED_CHICKEN(2140,2,"Cooked chicken"),
		HERRING(347,5,"Herring"),
		SARDINE(325,4,"Sardine"),	
		TROUT(333,7,"Trout"),
		SALMON(329,9,"Salmon"),
		TUNA(361,10,"Tuna"),
		LOBSTER(379,12,"Lobster"),
		SWORDFISH(373,14,"Swordfish"),
		MONKFISH(7946,16,"Monkfish"),
		SHARK(385,20,"Shark"),
		MANTA(391,21,"Manta Ray"),
		TUNA_POTATO(7060,22,"Tuna Potato"),
		ROCKTAIL(15272,22,"Rocktail"),
		SEA_TURTLE(397,23,"Sea Turtle"),
		PUMPKIN(1959,25,"Pumpkin"),
		COOKED_KARAMBWAN(3144, 18, "Cooked karambwan");
		
		
		
		private int id; private int heal; private String name;
		
		private FoodToEat(int id, int heal, String name) {
			this.id = id;
			this.heal = heal;
			this.name = name;		
		}
		
		public int getId() {
			return id;
		}

		public int getHeal() {
			return heal;
		}
		
		public String getName() {
			return name;
		}
		public static HashMap <Integer,FoodToEat> food = new HashMap<Integer,FoodToEat>();
		
		public static FoodToEat forId(int id) {
			return food.get(id);
		}
		
		static {
		for (FoodToEat f : FoodToEat.values())
			food.put(f.getId(), f);
		}
	}
	
	public void eat(int id, int slot) {
		/*if (c.duelRule[6]) {
			c.sendMessage("You may not eat in this duel.");
			return;
		}*/
		if(id != id) {
			return;
		}
		if ((((System.nanoTime() - c.foodDelay >= 1500000000) || c.foodTimer <= 0) || (((System.nanoTime() - c.foodDelay >= 500000000) || c.foodTimer <= 0) && id == 3144)) && c.playerLevel[3] > 0) {
			c.startAnimation(829);
			c.getItems().deleteItem(id,slot,1);
			FoodToEat f = FoodToEat.food.get(id);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += f.getHeal();
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			}
			if(id != 3144) {
				c.getCombat().resetPlayerAttack();
				c.attackTimer += 2;
				c.foodDelay = System.nanoTime();
				c.foodTimer = 3;
			} else {
				c.foodDelay = System.nanoTime();
				c.foodTimer = 1;
			}
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the " + f.getName() + ".");
		}		
	}

	
	public boolean isFood(int id) {
		return FoodToEat.food.containsKey(id);
	}	
	

}