package server.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import server.model.objects.Object;
import server.util.Misc;
import server.model.players.Client;
import server.Server;
import server.world.ObjectHandler;

/**
 * @author Demise
 */

public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();
	public void process() {
		for (Object o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}		
		}
		for (Object o : toRemove) {
			if (isObelisk(o.newId)) {
				int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);	
		}
		toRemove.clear();
	}
	
	    public void removeObject(int x, int y) {
        for (int j = 0; j < Server.playerHandler.players.length; j++) {
            if (Server.playerHandler.players[j] != null) {
                Client c = (Client)Server.playerHandler.players[j];
                c.getPA().object(-1, x, y, 0, 10);            
                                                                           c.getPA().object(158, 3097, 3493, 0, 10);
            }    
        }    
    }
	
	public void updateObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);			
			}	
		}	
	}
	
	public void placeObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}	
		}
	}
	
	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}	
		return null;
	}


	
	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o,c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		loadCustomSpawns(c);
		if (c.distanceToPoint(2813, 3463) <= 60) {
			c.getFarming().updateHerbPatch();
		}
	}
	private int[][] customObjects = {{}};
	public void loadCustomSpawns(Client c) {

	c.getPA().checkObjectSpawn(-1, 2387, 9491, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2388, 9486, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2388, 9487, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2389, 9486, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2389, 9487, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2373, 9485, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2374, 9490, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2375, 9490, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2374, 9491, 0, 10); //stake rocks
	c.getPA().checkObjectSpawn(-1, 2375, 9491, 0, 10); //stake rocks

	c.getPA().checkObjectSpawn(1749, 3278, 3952, 0, 10);//wildy dag

	c.getPA().checkObjectSpawn(2213, 2654, 3162, 0, 10);//khazard bank
	c.getPA().checkObjectSpawn(2213, 2653, 3162, 0, 10);//khazard bank
	c.getPA().checkObjectSpawn(2213, 2923, 3171, 0, 10);//karamja bank
	c.getPA().checkObjectSpawn(170, 3106, 3933, 3, 10);//wildy chest
	c.getPA().checkObjectSpawn(3044, 3107, 3513, 0, 10);//furnace
	c.getPA().checkObjectSpawn(2783, 3110, 3511, 0, 10);//anvil
	c.getPA().checkObjectSpawn(2783, 3111, 3511, 0, 10);//anvil
	
	c.getPA().checkObjectSpawn(6162, 2456, 4467, 3, 10);
	c.getPA().checkObjectSpawn(4707, 2456, 4469, 3, 10);
	c.getPA().checkObjectSpawn(6164, 2456, 4471, 3, 10);
	
	c.getPA().checkObjectSpawn(8972, 2450, 4471, 3, 10);

	//c.getPA().checkObjectSpawn(-1, 3090, 3503, -1, 10); // remove tree edge
	
		/*c.getPA().checkObjectSpawn(823, 3093, 3506, 3, 10);
		c.getPA().checkObjectSpawn(823, 3094, 3506, 3, 10);
		c.getPA().checkObjectSpawn(823, 3095, 3506, 3, 10);
		c.getPA().checkObjectSpawn(823, 3096, 3506, 3, 10);
		c.getPA().checkObjectSpawn(823, 3097, 3506, 3, 10);
		c.getPA().checkObjectSpawn(823, 3098, 3506, 3, 10);
		c.getPA().checkObjectSpawn(2513, 3095, 3500, 0, 10);
		c.getPA().checkObjectSpawn(2513, 3096, 3500, 0, 10);
		c.getPA().checkObjectSpawn(2513, 3097, 3500, 0, 10);*/
	
		c.getPA().checkObjectSpawn(1814, 3086, 3483, 3, 5);

		c.getPA().checkObjectSpawn(3644, 3083, 3498, 0, 10);
		c.getPA().checkObjectSpawn(13291, 3084, 3499, 1, 10);//magic chest

		c.getPA().checkObjectSpawn(13291, 2447, 4469, 1, 10);//magic chest dz
		//c.getPA().checkObjectSpawn(4150, 3090, 3487, 0, 10);
		
		c.getPA().checkObjectSpawn(8987, 2524, 4777, 0, 10);
		c.getPA().checkObjectSpawn(4309, 2752, 3499, 0, 10);

		c.getPA().checkObjectSpawn(-1, 2523, 4777, -1, 10);

		 c.getPA().checkObjectSpawn(406, 2518, 4777, 2, 10);//broodoo grave
		c.getPA().checkObjectSpawn(8987, 2531, 4777, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2524, 4777, -1, 10);

		c.getPA().checkObjectSpawn(849, 2790, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2791, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2792, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2793, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2794, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2795, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2796, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2797, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2798, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2799, 4728, -1, 10);
		c.getPA().checkObjectSpawn(849, 2800, 4728, -1, 10);

		c.getPA().checkObjectSpawn(-1, 2958, 3820, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2958, 3821, -1, 0);

		c.getPA().checkObjectSpawn(-1, 2390, 4718, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2387, 4728, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2382, 4729, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2377, 4728, -1, 10);

		c.getPA().checkObjectSpawn(-1, 2407, 4376, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2407, 4376, -1, 0);
		
		//c.getPA().checkObjectSpawn(13617, 3107, 3513, 1, 10);//nice portal tho
		c.getPA().checkObjectSpawn(-1, 3112, 3514, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3111, 3514, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3112, 3515, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3111, 3515, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2543, 10143, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2545, 10145, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2545, 10141, 0, 10);

		c.getPA().checkObjectSpawn(-1, 2390, 4724, 0, 10);//rocks at kril
		c.getPA().checkObjectSpawn(-1, 2377, 4717, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2377, 4711, 0, 10);
		
		c.getPA().checkObjectSpawn(2213, 2751, 3512, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2750, 3512, 0, 10);
		
		c.getPA().checkObjectSpawn(2213, 3070, 3510, -1, 10);
		
		c.getPA().checkObjectSpawn(2213, 2601, 4774, 0, 10);
		c.getPA().checkObjectSpawn(3608, 3098, 3499, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2761, 3503, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2764, 3503, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2763, 3503, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2762, 3503, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2752, 3503, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2753, 3503, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2754, 3503, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2759, 3513, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2750, 3510, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2756, 3508, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2759, 3507, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2761, 3509, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2761, 3511, 0, 10);

		c.getPA().checkObjectSpawn(-1, 2758, 3482, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2757, 3482, 0, 0);

		c.getPA().checkObjectSpawn(-1, 2736, 3477, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2737, 3477, 0, 0);

		c.getPA().checkObjectSpawn(-1, 2758, 3513, 0, 10);

		c.getPA().checkObjectSpawn(-1, 2757, 3513, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2755, 3511, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2755, 3509, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2757, 3507, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2757, 3499, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2758, 3499, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2758, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2757, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2763, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2763, 3500, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2762, 3499, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2753, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2753, 3499, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2753, 3500, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2757, 3504, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2758, 3504, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2757, 3503, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2758, 3503, 0, 0);
		c.getPA().checkObjectSpawn(-1, 3279, 3382, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3278, 3382, 0, 10);
		//c.getPA().checkObjectSpawn(2477, 3086, 3487, 0, 10);
		//c.getPA().checkObjectSpawn(3192, 3084, 3487, 0, 10);
		c.getPA().checkObjectSpawn(2470, 3240, 3606, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3098, 3513, 0, 10);
    		c.getPA().checkObjectSpawn(-1, 3097, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3096, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3093, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3091, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3092, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3098, 3507, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3097, 3507, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3096, 3507, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3093, 3507, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3091, 3507, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3091, 3508, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3091, 3510, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3094, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3093, 3509, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3096, 3511, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3099, 3507, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3100, 3508, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3100, 3507, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3099, 3512, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3099, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3100, 3512, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3100, 3513, -1, 10);
    		c.getPA().checkObjectSpawn(-1, 3101, 3510, -1, 0);
    		c.getPA().checkObjectSpawn(-1, 3101, 3509, -1, 0);
    		c.getPA().checkObjectSpawn(-1, 3100, 3509, -1, 0);
    		c.getPA().checkObjectSpawn(-1, 3100, 3510, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3088, 3511, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3488, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3098, 3496, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3499, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3498, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3091, 3495, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3494, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3496, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3496, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3235, 9315, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3231, 9320, 0, 10);		
		c.getPA().checkObjectSpawn(-1, 3233, 9324, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3234, 9324, 0, 10);
		c.getPA().checkObjectSpawn(1, 3219, 9301, 0, 10);
		c.getPA().checkObjectSpawn(1, 3220, 9301, 0, 10);
		c.getPA().checkObjectSpawn(10284, 3203, 9305, 0, 10);
		c.getPA().checkObjectSpawn(8972, 3087, 3487, 0, 10);
		c.getPA().checkObjectSpawn(2213, 3233, 9315, 0, 10);
		/* banks in Edgeville house */
		c.getPA().checkObjectSpawn(2213, 3091, 3507, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3091, 3508, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3091, 3509, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3091, 3510, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3091, 3511, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3091, 3512, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3091, 3513, 1, 10);
		/* end banks in Edgeville House */
		c.getPA().checkObjectSpawn(4874, 3098, 3500, 0, 10);
		c.getPA().checkObjectSpawn(4875, 3097, 3500, 0, 10);
		c.getPA().checkObjectSpawn(4876, 3096, 3500, 0, 10);
		c.getPA().checkObjectSpawn(4877, 3095, 3500, 0, 10);
		c.getPA().checkObjectSpawn(4878, 3094, 3500, 0, 10);
		c.getPA().checkObjectSpawn(1755, 3055, 9774, 0, 0);
		c.getPA().checkObjectSpawn(8987, 3098, 3497, 0, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3850, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3849, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10307, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10308, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10311, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10312, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10341, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10342, 1, 0);
		c.getPA().checkObjectSpawn(2213, 3080, 9502, 1, 10);
		c.getPA().checkObjectSpawn(2475, 3233, 9312, 1, 10);
		c.getPA().checkObjectSpawn(4551, 2522, 3595, 1, 10);
		c.getPA().checkObjectSpawn(409, 3090, 3494, 1, 10);
		//c.getPA().checkObjectSpawn(410, 3087, 3486, 1, 10);
		c.getPA().checkObjectSpawn(409, 2539, 4718, 1, 10);
		c.getPA().checkObjectSpawn(2465, 3073, 3866, 1, 10); //wildchest portal :D
		//c.getPA().checkObjectSpawn(2465, 3084, 3493, 1, 10); //shops portal
		c.getPA().checkObjectSpawn(2213, 3273, 3392, 2, 10);
		c.getPA().checkObjectSpawn(2213, 3274, 3392, 2, 10);
		
		c.getPA().checkObjectSpawn(2873, 3090, 3492, 1, 10); //saradomin statue
		c.getPA().checkObjectSpawn(2874, 3090, 3498, 1, 10); //zamorak statue

		c.getPA().checkObjectSpawn(2213, 2400, 5181, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2401, 5181, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2402, 5181, 0, 10);
		
		
		
		c.getPA().checkObjectSpawn(409, 2765, 3486, 3, 10);

		//c.getPA().checkObjectSpawn(2465, 3088, 3487, 1, 10);//boss portal

		c.getPA().checkObjectSpawn(-1, 3089, 3059, 1, 10);//test
		c.getPA().checkObjectSpawn(-1, 2415, 3703, 1, 10);//test
c.getPA().checkObjectSpawn(-1, 2399, 5172, 1, 10);//test

c.getPA().checkObjectSpawn(-1, 3023, 3636, 1, 0);//test

//castle
c.getPA().checkObjectSpawn(-1, 3021, 3632, -1, 0);
c.getPA().checkObjectSpawn(-1, 3021, 3631, -1, 0);
c.getPA().checkObjectSpawn(-1, 3023, 3627, -1, 0);
c.getPA().checkObjectSpawn(-1, 3024, 3636, -1, 0);
c.getPA().checkObjectSpawn(-1, 3024, 3626, -1, 0);
c.getPA().checkObjectSpawn(-1, 3024, 3637, -1, 0);
c.getPA().checkObjectSpawn(-1, 3034, 3637, -1, 0);
c.getPA().checkObjectSpawn(-1, 3035, 3636, -1, 0);
c.getPA().checkObjectSpawn(-1, 3033, 3632, -1, 0);
c.getPA().checkObjectSpawn(-1, 3035, 3637, -1, 0);
c.getPA().checkObjectSpawn(-1, 3035, 3627, -1, 0);
c.getPA().checkObjectSpawn(-1, 3034, 3626, -1, 0);
c.getPA().checkObjectSpawn(2213, 3024, 3630, 1, 10);
c.getPA().checkObjectSpawn(2213, 3024, 3631, 1, 10);
c.getPA().checkObjectSpawn(2213, 3024, 3632, 1, 10);
c.getPA().checkObjectSpawn(2213, 3024, 3633, 1, 10);

c.getPA().checkObjectSpawn(2213, 3136, 9914, 1, 10);
c.getPA().checkObjectSpawn(2213, 3136, 9916, 1, 10);

c.getPA().checkObjectSpawn(4126, 2962, 3377, 0, 10);

//13291
c.getPA().checkObjectSpawn(13291, 3120, 9960, 1, 10);
//c.getPA().checkObjectSpawn(2465, 3100, 3498, 1, 10);//monster portal
c.getPA().checkObjectSpawn(411, 3281, 3885, 1, 10);//chaos altar
c.getPA().checkObjectSpawn(-1, 2374, 9486, -1, 10);

		/*if (c.heightLevel == 0)
			c.getPA().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		else
			c.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);*/
	}
	
	public final int IN_USE_ID = 14825;
	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}
	public int[] obeliskIds = {14829,14830,14827,14828,14826,14831};
	public int[][] obeliskCoords = {{3154,3618},{3225,3665},{3033,3730},{3104,3792},{2978,3864},{3305,3914}};
	public boolean[] activated = {false,false,false,false,false,false};
	
	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
			}
		}	
	}
	
	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}
	
	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1)) {
					c.getPA().startTeleport2(obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0);
				}
			}		
		}
	}
	
	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}
	
	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}	
	}




}