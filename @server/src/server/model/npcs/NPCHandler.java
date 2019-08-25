package server.model.npcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.util.Misc;
import server.world.map.VirtualWorld;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;
import server.util.Misc;
import server.model.minigames.PestControl;
import server.model.npcs.NPCDumbPathFinder;
import server.clip.region.Region;
import server.world.*;
import server.model.players.*;

public class NPCHandler {
	public static int maxNPCs = 10000;
	public static int maxListedNPCs = 10000;
	public static int maxNPCDrops = 10000;
	public static NPC npcs[] = new NPC[maxNPCs];
	public static NPCList NpcList[] = new NPCList[maxListedNPCs];
	/* Dagannoth Mother ints */
	public int dagColor = 0, dagColor2 = 0;
	public int dagSwitch = 45;
	/* Sea Troll ints */
	public int[][] trollQueenItemDropCoords = {{3382, 3961}, {3381, 3963}, {3380, 3963}, {3377, 3961}};
	public boolean spawnedTrolls = false;
	public boolean canKillQueen = true;
	public int dropCounter = 0;
	public int amountOfTrolls = 0;
	/* Mole ints */
	public int moleTimer = 1200;
	/* Kril ints */
	public int krilTimer = 2500;
	public int krilWeak = 0;
	public int lastWeak = 0;
	public int weakCycle = 0;
	public int weakChanges = 0;
	public int kMinionsDead = 3;
	public int playersInChaos = 0;
	public boolean kMinionsSpawned = false;
	public boolean krilDead = true;
	/* Event ints */
	public String eventWinner = "Nobody, 0 Kills";
	public int eventTimer = 0, eventType = 0, eventId = 0, previousEvent = 0, wonItem = 0;
	public Client winnerClient = null;
	public long eventStart = 0;
	public String[] eventName = {"None", "Edgeville PK'ing", "Multi PK'ing", "Wildy Monster Slaying", "Wildy Boss Slaying"};
	/* Event reward ints */
	/* Common rewards, always given if high/extreme rewards are not */
	public int[] eventCommonRewards = { 15018, 15019, 15020, 15220, //rings
											15126, 19114, 20072 //amulet of ranging, asn, ddef
	};
	/* High rewards 1 in 10 */
	public int[] eventHighRewards = { 14484, 11694, //claws, ags
										11718, 11720, 11728, //armadyl
											11724, 11726 //bandos
	};
	/* Extreme rewards 1 in 25 */
	public int[] eventExtremeRewards = { 13884, 13890, 13902, //statius'
											13887, 13893, 13899, //vesta's
												13858, 13861, 13864, //zuriel's
													13870, 13873, 13876 //morrigan's
	};

	public NPCHandler() {
		for(int i = 0; i < maxNPCs; i++) {
			npcs[i] = null;
		}
		for(int i = 0; i < maxListedNPCs; i++) {
			NpcList[i] = null;
		}
		loadNPCList("./Data/cfg/npc.cfg");
		loadAutoSpawn("./Data/cfg/spawn-config.cfg");
	}
	
	public int[][] randomMole =
					{
						{3235, 3944},
						{2999, 3410}//falador spawns
					};
					
		public String[] moleArea = 
					{
					"@dre@The Giant mole has spawned @red@East@dre@ of the Mage bank!",
					"@dre@The Giant mole has spawned @red@North@dre@ of Falador! Falador is @red@Non-PvP@dre@!"
					
					
					};
					
		
	public void spawnTheMole() {

		int random = Misc.random(randomMole.length - 1);
		newNPC(3340, randomMole[random][0], randomMole[random][1], 0, 3, 2000, 30, 250, 250);
		
		sendAll("[@red@Event@bla@] "+moleArea[random]);
		sendAll("[@red@Event@bla@] @red@Proceed with caution..");

	}

	public void spawnKril(int players) {

		if(krilDead) {

			krilDead = false;
			kMinionsSpawned = false;
			kMinionsDead = 3;
			weakChanges = 0;
			weakCycle = 0;
			krilWeak = 0;
			playersInChaos = 0;

			newNPC(2554, 2382, 4719, 0, 0, 2000, 30, 275+players, 175+players);

			sendAll("[@red@Event@bla@] @red@Zamorak's ancient demon, @dre@K'ril Tsutsaroth@red@, has been awakened!");
			sendAll("[@red@Event@bla@] @red@Tremors come from the @dre@Chaos temple@red@, @dre@West@red@ of the @dre@Hill giants@red@.");

		}

	}

	public void krilMinions(int players) {
		weakChanges = 0;
		weakCycle = 0;
		krilWeak = 0;
		kMinionsDead = 0;
		newNPC(2555, 2382, 4716, 0, 2, 175+(players*25), 22+((int)(players/2)), 150+players, 160+players);
		newNPC(2557, 2380, 4724, 0, 2, 175+(players*15), 20+((int)(players/2)), 115+players, 140+players);
		newNPC(2556, 2387, 4718, 0, 2, 175+(players*20), 15+((int)(players/2)), 150+players, 150+players);
	}

		/*
		Reward the event winner
		*/

		public void eventWinner() { //don't forget the server crash that is happening from events.
			int topScore = 0;
			String winner = "Nobody";
			Client win = null;
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						if(c2 != null) {
							if(c2.eventScore > 0) {
								c2.EP += 5;
								c2.allEP += 5;
								c2.sendMessage("@blu@You receive 5 Event Points for participating in the event.");
							}
							if(c2.eventScore > topScore) {
								topScore = c2.eventScore;
								winner = c2.playerName;
								win = c2;
							}
						}
					}
				}
				if(win != null) {
					winnerClient = win;
					win.EP += 20;
					win.allEP += 20;
					win.sendMessage("@blu@You receive 20 Bonus Event Points for winning the event!");
					if(Misc.random(15) == 0) {
						wonItem = eventExtremeRewards[(int)(Math.random()*eventExtremeRewards.length)];
						win.getItems().addItem(wonItem, 1);
					} else if(Misc.random(5) == 0) {
						wonItem = eventHighRewards[(int)(Math.random()*eventHighRewards.length)];
						win.getItems().addItem(wonItem, 1);
					} else {
						wonItem = eventCommonRewards[(int)(Math.random()*eventCommonRewards.length)];
						win.getItems().addItem(wonItem, 1);
					}
				} else {
					winnerClient = null;
				}
				//boolean 1kill = false;
				//if(topScore == 1) 1kill = true;
				//String kill = (1kill == false) ? "kill" : "kills";
				eventWinner = winner+", "+topScore+" "+((topScore == 1) ? "kill" : "kills")+"";
		}

		/*
		Returns string of the event winner name in the format of Name, # Kills
		*/

		public String eventWinnerName() {
			int topScore = 0;
			String winner = "Nobody";
			Client win = null;
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						if(c2 != null) {
							if(c2.eventScore > topScore) {
								topScore = c2.eventScore;
								winner = c2.playerName;
								win = c2;
							}
						}
						//c2.eventScore = 0;
					}
				}
				return winner+", "+topScore+" "+((topScore == 1) ? "kill" : "kills")+"";
		}

		/*
		Sendall so don't have to use c.sendAll
		*/

		public void sendAll(String message) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage(message);
					}
				}
		}

		/*
		Ending events and giving reward to winner(s)
		*/

		public void endEvent() {
			eventWinner();//do it before setting eventType to 0 so we can reward based on the event
			if(winnerClient != null) {
					sendAll("[@red@Event@bla@] @red@Event has ended! Winner @dre@"+eventWinnerName()+" @red@has been awarded a @dre@"+winnerClient.getItems().getItemName(wonItem)+"@red@!");
				} else {
					sendAll("[@red@Event@bla@] @red@Event has ended! Winner @dre@No-one @red@has been awarded with: @dre@Nothing@red@!");
			}
			sendAll("[@red@Event@bla@] @dre@The next event will begin in 10 minutes.");
			eventTimer = -500;//-500 so it adds an 5 extra minute delay 5 + 5 = 10. ^
			previousEvent = eventType;
			eventType = 0; //event type to 0 so there's no event going on
			//don't set eventId to 0 otherwise it will screw shit up
		}
		
		/*
		Sending time left on events
		*/

		public void eventMessage() {
			if(eventType == 1)
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@: "+(2000 - eventTimer) / 100+" "+(eventTimer < 1900 ? "minutes remain" : "minute remains")+" of Bonus PK Points & Loot!");
			if(eventType == 2) {
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@: "+(2000 - eventTimer) / 100+" "+(eventTimer < 1900 ? "minutes remain" : "minute remains")+" of Bonus PK Points & Loot!");
				sendAll("[@red@Event@bla@] @dre@Type @red@::multi@dre@ for @red@Multi PK'ing@dre@ teleports!");
			}
			if(eventType == 3)
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@: "+(2000 - eventTimer) / 100+" "+(eventTimer < 1900 ? "minutes remain" : "minute remains")+" of Bonus Drop Rates & Loot!");
			if(eventType == 4)
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@: "+(2000 - eventTimer) / 100+" "+(eventTimer < 1900 ? "minutes remain" : "minute remains")+" of Bonus Drop Rates & Loot!");

			sendAll("[@red@Event@bla@] @red@In lead: @dre@"+eventWinnerName()+"@red@!");
		}

		/*
		Starts up an event, resets previous event scores
		*/

		public void startEvent() {

			for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.eventId = eventId;//set event id of all players to the new event id
						c2.eventScore = 0;//reset event scores (also resets upon login if not during the same eventId)
					}
				}

			eventStart = System.currentTimeMillis();//event starting time, for calculating time left in minutes

			if(eventType == 1)
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@: Bonus PK Points & Loot for the next 15 minutes!");
			if(eventType == 2) {
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@: Bonus PK Points & Loot for the next 15 minutes!");
				sendAll("[@red@Event@bla@] @dre@Type @red@::multi@dre@ for @red@Multi PK'ing@dre@ teleports!");
			}
			if(eventType == 3)
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@! Bonus Drop Rates & Loot for the next 15 minutes!");
			if(eventType == 4)
				sendAll("[@red@Event@bla@] @dre@"+eventName[eventType]+"@red@: Bonus Drop Rates & Loot for the next 15 minutes!");
		}
	
	public void multiAttackGfx(int i, int gfx) {
		if (npcs[i].projectileId < 0)
			return;
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.heightLevel != npcs[i].heightLevel)
					continue;
				if (Server.playerHandler.players[j].goodDistance(c.absX, c.absY, npcs[i].absX, npcs[i].absY, 15)) {
					int nX = Server.npcHandler.npcs[i].getX() + offset(i);
					int nY = Server.npcHandler.npcs[i].getY() + offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nY - pY)* -1;
					int offY = (nX - pX)* -1;
					c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50, getProjectileSpeed(i), npcs[i].projectileId, 43, 31, -c.getId() - 1, 65);					
				}
			}		
		}
	}
	
	public boolean switchesAttackers(int i) {
		switch(npcs[i].npcType) {
			case 2555:
			case 2556:
			case 2557:
			case 2551:
			case 2552:
			case 2553:
			case 2559:
			case 2560:
			case 2561:
			case 2563:
			case 2564:
			case 2565:
			case 2892:
			case 2894:
			case 3200:
			case 3340:
			case 3066:
			case 3622:
			case 50:
			case 1351:
			case 3847:
			case 3843:
			case 3842:
			case 3841:
			return true;
		
		}
	
		return false;
	}
	
	/**
	* NPC Multi attack calculations
	**/
	
	public void multiAttackDamage(int i, int size) {
		int max = getMaxHit(i);
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.isDead || c.heightLevel != npcs[i].heightLevel)
					continue;
				int damage = Misc.random(max);
				
				if(c.playerEquipment[c.playerWeapon] == 11716 && (npcs[i].npcType == 2554 || npcs[i].npcType == 2555 || npcs[i].npcType == 2556 || npcs[i].npcType == 2557))
					damage = (int) (damage * 0.75);
				
				//if(c.playerId != npcs[i].oldIndex)
					//continue;
				if (Server.playerHandler.players[j].goodDistance(c.absX, c.absY, npcs[i].absX, npcs[i].absY, size)) {
					c.underAttackBy2 = i;

				if (c.playerIndex <= 0 && c.npcIndex <= 0 && c.autoRet == 1 && !c.walkingToItem)
					c.npcIndex = i;
				
					if (npcs[i].attackType == 2) {
						if (!c.prayerActive[16]) {
							if (Misc.random(500) + 200 > Misc.random(c.getCombat().mageDef())) {
								damage = damage;
							} else {
								damage = 0;						
							}
						} else {
								damage = 0;
						}
					} else if (npcs[i].attackType == 1) {
						if (!c.prayerActive[17]) {
							if (Misc.random(500) + 200 > Misc.random(c.getCombat().calculateRangeDefence())) {
								damage = damage;							
							} else {
								damage = 0;
							}
						} else {
								damage = 0;						
						}
					} else if (npcs[i].attackType == 0) {
						if (!c.prayerActive[18]) {
							if (Misc.random(500) + 200 > Misc.random(c.getCombat().calculateMeleeDefence())) {
								damage = damage;							
							} else {
								damage = 0;
							}
						} else {
								damage = 0;						
						}
					}
					
					if (c.playerEquipment[5] == 13742) {//Elysian Effect
							if (Misc.random(100) < 75) {
								double damages = damage;
								double damageDeduction = ((double)damages)/((double)4);
								damage -= ((int)Math.round(damageDeduction));
							}
						}
						if (c.playerEquipment[5] == 13740) {//Divine Effect
							double damages2 = damage;
							double prayer = c.playerLevel[5];
							double possibleDamageDeduction = ((double)damages2)/((double)3.33);//30% of Damage Inflicted
							double actualDamageDeduction;
							if ((prayer * 2) < possibleDamageDeduction) {
							actualDamageDeduction = (prayer * 2);//Partial Effect(Not enough prayer points)
							} else {
							actualDamageDeduction = possibleDamageDeduction;//Full effect
							}
							double prayerDeduction = ((double)actualDamageDeduction)/((double)2);//Half of the damage deducted
							damage -= ((int)Math.round(actualDamageDeduction));
							c.playerLevel[5] = c.playerLevel[5]-((int)Math.round(prayerDeduction));
							c.getPA().refreshSkill(5);
						}
								c.dealDamage(damage);
								c.handleHitMask(damage);
								c.logoutDelay = System.currentTimeMillis();
								if (c.vengOn && damage > 0)
									c.getCombat().appendVengeanceNPC(i, damage);
					if (npcs[i].endGfx > 0) {
						c.gfx100(npcs[i].endGfx);					
					}
					if (npcs[i].endGfx0 > 0) {
						c.gfx0(npcs[i].endGfx0);					
					}
				}
				c.getPA().refreshSkill(3);
			}		
			
		}
	}
	
	public int getClosePlayer(int i) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (j == npcs[i].spawnedBy)
					return j;
				if (goodDistance(Server.playerHandler.players[j].absX, Server.playerHandler.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || isFightCaveNpc(i)) {
					if ((((Server.playerHandler.players[j].underAttackBy <= 0 && Server.playerHandler.players[j].underAttackBy2 <= 0 && !Server.playerHandler.players[j].inMulti()) && (System.currentTimeMillis() - Server.playerHandler.players[j].singleCombatDelay2 > 7000)) || Server.playerHandler.players[j].inMulti()) || npcs[i].npcType == 2455 || npcs[i].npcType == 2456 || npcs[i].npcType == 2554 || npcs[i].npcType == 2555 || npcs[i].npcType == 2556 || npcs[i].npcType == 2557)
						if (Server.playerHandler.players[j].heightLevel == npcs[i].heightLevel)
							return j;
				}
			}	
		}
		return 0;
	}
	
	public static boolean pathBlockedNPC(NPC attacker, Client victim) {
		
		double offsetX = Math.abs(attacker.absX - victim.absX);
		double offsetY = Math.abs(attacker.absY - victim.absY);
		
		int distance = TileControl.calculateDistance(attacker, victim);
		
		if (distance == 0) {
			return true;
		}
		
		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];
		
		int curX = attacker.absX;
		int curY = attacker.absY;
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;
		
		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while(distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > victim.absX) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;	
					currentTileXCount -= offsetX;
				}		
			} else if (curX < victim.absX) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > victim.absY) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;	
					currentTileYCount -= offsetY;
				}	
			} else if (curY < victim.absY) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = attacker.heightLevel;
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;	
		}
		for (int i = 0; i < path.length; i++) {
			if (!Region.getClipping(path[i][0], path[i][1], path[i][2], path[i][3], path[i][4])) {
				return true;	
			}
		}
		return false;
	}
	
	public int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (goodDistance(Server.playerHandler.players[j].absX, Server.playerHandler.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || isFightCaveNpc(i)) {
					if ((((Server.playerHandler.players[j].underAttackBy <= 0 && Server.playerHandler.players[j].underAttackBy2 <= 0 && !Server.playerHandler.players[j].inMulti()) 
						&& (System.currentTimeMillis() - Server.playerHandler.players[j].singleCombatDelay2 > 7000))
						 || Server.playerHandler.players[j].inMulti()) || npcs[i].npcType == 2455 || npcs[i].npcType == 2456 || npcs[i].npcType == 2554 || npcs[i].npcType == 2555 || npcs[i].npcType == 2556 || npcs[i].npcType == 2557)
						if (Server.playerHandler.players[j].heightLevel == npcs[i].heightLevel)
							players.add(j);
				}
			}	
		}
		if (players.size() > 0)
			return players.get(Misc.random(players.size() -1));
		else
			return 0;
	}
	
	public int npcSize(int i) {
		switch (npcs[i].npcType) {
		case 2554:
			return 2;
		case 110:
		case 117:
			return 2;
		case 2883:
		case 2882:
		case 2881:
			return 3;
		case 1459:
		case 1460:
			return 2;
		}
		return 0;
	}
//2558	Kree 6222

	public boolean isAggressive(int i) {
		switch (npcs[i].npcType) {
		case 53://dragons
		case 54:
		case 55:
		case 1615:
		case 1936:
		case 1582:
		case 119:
		case 1575:
		case 82:
		case 83:
		case 2025:
		case 2026:
		case 2027:
		case 2028:
		case 2029:
		case 2030:
		case 2783:
		case 158:
		case 58:
		case 134:
		case 941:
		if(npcs[i].inWild())
			return true;
		else
		return false;
		case 110:
		case 122:
		case 123:
		case 3847:
		case 3843:
		case 3842:
		case 3841:
		case 127:
		case 182:
		case 183:
		case 184:
		case 185:
		case 221:
		case 178:
		case 1459:
		case 1460:
		case 3066:
		case 3622:
			case 6260:
			case 2551:
			case 2552:
			case 77:
			case 6223:
			case 6225:
			case 6227:
			case 2553:
			case 6222:
			case 2559:
			case 2560:
			case 50:
			case 2455:
			case 2456:
			case 2561:
			case 6247:
			case 2563:
			case 6203:
			case 2565:
			case 2892:
			case 2894:
			case 2881:
			case 2882:
			case 2883:
			case 3200:
			case 2554:
			case 2555:
			case 2556:
			case 2557:
			case 2550:
				return true;		
		}
		/*if (npcs[i].inWild() && npcs[i].MaxHP > 0)
			return true;*/ //not needed
		if (isFightCaveNpc(i))
			return true;
		return false;
	}
	
	public boolean isFightCaveNpc(int i) {
		switch (npcs[i].npcType) {
			case 2627:
			case 2630:
			case 2631:
			case 2741:
			case 2743: 
			case 2745:
			return true;		
		}
		return false;
	}
	
	/**
	* Summon npc, barrows, etc
	**/
	
	
	
	public void spawnNpc(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1) {
			//Misc.println("No Free Slot");
			return;		// no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if(headIcon) 
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if(attackPlayer) {
			newNPC.underAttack = true;
			if(c != null) {
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] != newNPC.npcType) {
					if(newNPC.npcType == 2025 || newNPC.npcType == 2026 || newNPC.npcType == 2027 || newNPC.npcType == 2028 || newNPC.npcType == 2029 || newNPC.npcType == 2030) {
						newNPC.forceChat("You dare disturb my rest!");
					}
				}
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] == newNPC.npcType) {
					newNPC.forceChat("You dare steal from us!");
				}
				
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}
	
	public void spawnInstancedNpc(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1) {
			//Misc.println("No Free Slot");
			return;		// no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.isInstanced = true;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		newNPC.updateRequired = true;
		newNPC.dirUpdateRequired = true;
		newNPC.getNextWalkingDirection();
		if(attackPlayer) {
			newNPC.underAttack = true;
			if(c != null) {
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] != newNPC.npcType) {
					if(newNPC.npcType == 2025 || newNPC.npcType == 2026 || newNPC.npcType == 2027 || newNPC.npcType == 2028 || newNPC.npcType == 2029 || newNPC.npcType == 2030) {
						newNPC.forceChat("You dare disturb my rest!");
					}
				}
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] == newNPC.npcType) {
					newNPC.forceChat("You dare steal from us!");
				}
				
				newNPC.killerId = c.playerId;
			}
		}
		
		if(headIcon) 
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if(npcType == 77)
			newNPC.forceChat("BRAIIIIIINNNSSSS");
		npcs[slot] = newNPC;
	}
	
	public void spawnNpc2(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1) {
			//Misc.println("No Free Slot");
			return;		// no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}
	
	
	
	/**
	* Emotes
	**/
	
	public static int getAttackEmote(int i) {
		switch(Server.npcHandler.npcs[i].npcType) {
		case 2555:
		case 2556:
			return 6945;
		case 2557:
			return 6947;
		case 2554://k'ril kril
		if(npcs[i].attackType == 0)
			return 6945;
		else
			return 6948;
		case 3068:
		case 3069:
		case 3070:
		case 3071:
			if(npcs[i].attackType == 0)
			{
				return 2986;
			}
			if(npcs[i].attackType == 1)
			{
				return 2989;
			}
			if(npcs[i].attackType == 2)
			{
				return 2988;
			}
		case 912:
		case 913:
			return 811;
		case 3843:
		case 3842:
		case 3841:
			return 3985;
		case 3847:
			return 3992;
		case 914:
			return 194;//191
		case 127:
			return 185;
		case 2456://daggs
		case 2455:
			return 1341;
		case 221:
			return 128;
		case 3340:
			return 3312;
		case 1637:
			return 1592;
		case 1633: case 1634:
		return 1582;
		case 1204:
		return 6562;
		case 1973:
			return 260;
			case 1351:
			if(npcs[i].attackType == 1 || npcs[i].attackType == 2)
			return 1340;
			else
			return 1341;
		case 3851:
			return 811;
		case 1575:
			return 1495;
		case 2069:
			return 6007;
		case 2070:
			return 6007;
		case 2071:
			return 6007;
		case 2072:
			return 6007;
		case 2073:
			return 6007;
		case 2074:
			return 6007;
			case 1612:
			return 1523;
		case 77: //Zombie
			return 299;
		case 49:
				return 6579;
		case 1467:
			return 1383;
		case 6247:
		return 6964;
		case 6203:
		return 6944;
		case 6260:
		return 7060;
		case 2044:
		return 359;
		case 1936:
		return 1142;
		case 6223:
		case 6225:
		case 6227:
		return 6953;
		case 6222:
		return 6976;
		case 6267:
		return 359;
		case 6268:
		return 2930;
		case 6269:
		return 4652;
		case 6270:
		return 4652;
		case 6271:
		return 4320;
		case 6272:
		return 4320;
		case 6273:
		return 4320;
		case 6274:
		return 4320;
			case 1459:
				return 1402;
			case 2550:
				if (npcs[i].attackType == 0)
					return 7060;
				else
					return 7063;	
					case 86:
					case 87:
					return 4933;
			case 2892:
			case 2894:
			return 2868;
			case 2627:
			return 2621;
			case 2630:
			return 2625;
			case 2631:
			return 2633;
			case 2741:
			return 2637;
			case 2746:
			return 2637;
			case 2607:
			return 2611;
			case 2743://360
			return 2647;
			//bandos gwd
			case 2551:
			case 2552:
			case 2553:
			return 6154;
			//end of gwd
			//arma gwd
			case 2558:
			return 3505;
			case 2560:
			return 6953;
			case 2559:
			return 6952;
			case 2561:
			return 6954;
			//end of arma gwd
			//sara gwd
			case 2562:
			return 6964;
			case 2563:
			return 6376;
			case 2564:
			return 7018;
			case 2565:
			return 7009;
			//end of sara gwd
			case 5247:
			return 5411;
			case 13: //wizards
			case 172:
			case 174:
			return 711;
			
			case 103:
			case 655:
			return 5532;
			
			case 1624:
			return 1557;
			
			case 1648:
			return 1592;
			
			case 2783: //dark beast
			return 2729;
			
			case 1615: //abby demon
			return 1537;
			
			case 1613: //nech
			return 1528;
			
			case 1610: case 1611: //garg
			return 1515;
			
			case 1616: //basilisk
			return 1546;
			
			//case 459: //skele
			//return 260;
			
			case 50://drags
			case 53:
			case 54:
			case 55:
			case 941:
			case 1590:
			case 1591:
			case 1592:
			return 80;
			
			case 124: //earth warrior
			return 390;
			
			case 803: //monk
			return 422;
			
			case 52: //baby drag
			return 25;			

			case 58: //Shadow Spider
            case 59: //Giant Spider
            case 60: //Giant Spider
            case 61: //Spider
            case 62: //Jungle Spider
            case 63: //Deadly Red Spider
            case 64: //Ice Spider
            case 134:
			return 143;	
			
			case 105: //Bear
            case 106:  //Bear
			case 1326:
			return 41;
			
			case 412:
			case 78:
			return 30;
			
			case 2033: //rat
			return 138;	
			
			case 2031: // bloodworm
			return 2070;
			
			case 101: // goblin
			return 309;	
			

			case 81: // cow
			return 0x03B;
			
			case 21: // hero
			return 451;	
			
			case 41: // chicken
			return 55;	
			
			case 9: // guard
			case 32: // guard
			case 20: // paladin
			return 451;	
			
			case 1338: // dagannoth
			case 1340:
			case 1342:
			return 1341;
		
			case 19: // white knight
			return 406;
			
			case 110:
			case 111: // ice giant
			case 112:
			case 117:
			case 116:
			return 128;
			
			case 2452:
			return 1312;
			
			case 2889:
			return 2859;
			
			case 118:
			case 119:
			return 99;
			
			case 82://Lesser Demon
            case 83://Greater Demon
            case 84://Black Demon
            case 1472://jungle demon
			return 64;
			
			case 1267:
			case 1265:
			return 1312;
			
			case 125: // ice warrior
			case 178:
			return 451;
			
			case 1153: //Kalphite Worker
            case 1154: //Kalphite Soldier
            case 1155: //Kalphite guardian
            case 1156: //Kalphite worker
            case 1157: //Kalphite guardian
			return 1184;
			
			case 123:
			case 122:
			return 164;
			
			case 2028: // karil
			return 2075;
			
			case 1643: // infernal mage
			return 729;
					
			case 2025: // ahrim
			return 729;
			
			case 2026: // dharok
			return 2067;
			
			case 2027: // guthan
			return 2080;
			
			case 2029: // torag
			return 0x814;
			
			case 2030: // verac
			return 2062;
			
			case 2881: //supreme
			return 2855;
			
			case 2882: //prime
			return 2854;
			
			case 2883: //rex
			return 2851;
			case 5666:
			int test = Misc.random(2);
			if(test == 2) {
			return 5895;
			} else if(test == 1) {
			return 5894;
			} else {
			return 5896;
			}
			
			case 3200:
			return 3146;
			case 100:
			return 6184;
			
			case 2745:
			if (npcs[i].attackType == 2)
			return 2656;
			else if (npcs[i].attackType == 1)
			return 2652;
			else if (npcs[i].attackType == 0)
			return 2655;
			
			
			default:
			return 0x326;		
		}
	}	


	
		public int getDeadEmote(int i) {
		switch(npcs[i].npcType) {
			case 1265:
			case 1267:
				return 1314;
		case 2555:
		case 2556:
		case 2557:
			return 6947;
		case 2554:
			return 6946;//k'ril kril
		case 3068: //wyverns
		case 3069:
		case 3070:
		case 3071:
			return 2987;
		case 3843:
		case 3842:
		case 3841:
			return 3988;
		case 914:
			return 196;
		case 3847:
			return 3993;
		case 2456://daggs
		case 2455:
			return 1342;
		case 221:
			return 131;
		case 3340:
			return 3310;
		case 1351:
		return 1342;
		case 1973:
			return 263;
		case 1575:
			return 1497;
case 2069:
	return 6008;
case 2070:
	return 6008;
case 2071:
	return 6008;
case 2072:
	return 6008;
case 2073:
	return 6008;
case 2074:
	return 6008;
	case 1936:
return 1142;
	
	
	case 49:
		return 6576;
		case 2044:
		return 359;
case 6247:
return 6965;
case 6203:
return 6945;
case 6260:
return 7062;
case 6222:
		return 6975;
		case 6267:
		return 357;
		case 6268:
		return 2938;
		case 6269:
		return 4653;
		case 6270:
		return 4653;
		case 6271:
		return 4321;
		case 6272:
		return 4321;
		case 6273:
		return 4321;
		case 6274:
		return 4321;
			case 117:
			case 110:
			case 116:
			return 131;
			case 1459:
			return 1404;
			//sara gwd
			case 5247:
			return 5412;
			case 2562:
			return 6965;
			case 86:
			case 87:
			return 4935;
			case 2563:
			return 6377;
			case 2564:
			return 7016;
			case 2565:
			return 7011;
			//bandos gwd
			case 2551:
			case 2552:
			case 2553:
			return 6156;
			case 2550:
			return 7062;
			case 2892:
			case 2894:
			return 2865;
			case 1612: //banshee
			return 1524;
			case 2558:
			return 3503;
			case 2559:
			case 2560:
			case 2561:
			return 6956;
			case 2607:
			return 2607;
			case 2627:
			return 2620;
			case 2630:
			return 2627;
			case 2631:
			return 2630;
			case 2738:
			return 2627;
			case 2741:
			return 2638;
			case 2746:
			return 2638;
			case 2743:
			return 2646;
			case 2745:
			return 2654;
			
			case 3777:
			case 3778:
			case 3779:
			case 3780:
			return -1;
			case 5666:
			return 5898;
			case 3200:
			return 3147;
			
			case 2035: //spider
			return 146;
			
			case 2033: //rat
			return 141;
			
			case 2031: // bloodvel
			return 2073;
			
			case 101: //goblin
			return 313;
			
			case 81: // cow
			return 0x03E;
			
			case 41: // chicken
			return 57;
			
			case 1338: // dagannoth
			case 1340:
			case 1342:
			return 1342;
			
			case 2881:
			case 2882:
			case 2883:
			return 2856;
			
			case 125: // ice warrior
			return 843;
			
			case 77:
			case 751://Zombies!!
			return 302;
			
			case 1626:
            case 1627:
            case 1628:
            case 1629:
            case 1630:
            case 1631:
            case 1632: //turoth!
            return 1597;
			
			case 1616: //basilisk
            return 1548;
			
			case 1653: //hand
            return 1590;
			
			case 82://demons
			case 83:
			case 84:
			return 67;
			
			case 1605://abby spec
			return 1508;
			
			case 51://baby drags
			case 52:
			case 1589:
			case 3376:
			return 28;
			
			case 1610:
			case 1611:
			return 1518;
			
			case 1618:
			case 1619:
			return 1553;
			
			case 1620: case 1621:
			return 1563;
			
			case 2783:
			return 2732;
			
			case 1615:
			return 1538;
			
			case 1624:
			return 1558;
			
			case 1613:
			return 1530;
			
			case 1637:
			return 1587;
			
			case 1633: case 1634: case 1635: case 1636:
			return 1580;
			
			case 1648: case 1649: case 1650: case 1651: case 1652: case 1654: case 1655: case 1656: case 1657:
			return 1590;
			
			case 100:
			case 102:
			return 6182;
			
			case 105:
			case 106:
			case 1326:
			return 44;
			
			case 412:
			case 78:
			return 36;
			
			case 122:
			case 123:
			return 167;
			
			case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 134:
			return 146;
			
			case 1153: case 1154: case 1155: case 1156: case 1157:
			return 1190;
			
			case 103: case 104:
			return 5534;
			
			case 118: case 119:
			return 102;
			
			
			case 50://drags
			case 53:
			case 54:
			case 55:
			case 941:
			case 1590:
			case 1591:
			case 1592:
			return 92;
			
			
			default:
			return 2304;
		}
	}
	
	/**
	* Attack delays
	**/
	public int getNpcDelay(int i) {
		switch(npcs[i].npcType) {
			case 2554:
				return 7;
			case 1643:
			case 2025:
			case 2028:
				return 7;
			case 2745:
				return 8;
			case 6222:
			case 2559:
			case 2560:
			case 2561:
			case 6260:
			return 6;
			//saradomin gw boss
			case 6247:
case 6203:
//case 6260:
return 5;
			default:
			return 5;
		}
	}
	
	/**
	* Hit delays
	**/
	public int getHitDelay(int i) {
		switch(npcs[i].npcType) {
			case 2554:
				return 6;
		case 3847:
			return 6;
			case 912:
			case 913:
			case 914:
				return 3;
			case 2881:
			case 2882:
			case 2892:
			case 2894:
				return 3;
			case 3200://chaos ele
				return 5;
			case 2743:
			case 2631:
			case 6222:
			case 2559:
			case 2560:
				return 3;
			
			case 1351:
			if(npcs[i].attackType == 2)
			return 5;
			else if(npcs[i].attackType == 1)
			return 3;
			else
			return 2;
			
			case 2745:
				return 6;

			case 6247:
case 6203:
case 6260:
return 2;
			case 2025:
			case 1643:
			return 3;
			case 2028:
			return 3;

			default:
			return 2;
		}
	}
		
	/**
	* Npc respawn time
	**/
	public int getRespawnTime(int i) {
		switch(npcs[i].npcType) {

		case 2067://turtle
		case 2068://karambwan
			return 80;
		case 312://fish
		case 334:
		case 324:
		case 314:
		case 326:
		case 316:
			return 60;
		case 2550:
		case 2551:
		case 2552:
		case 2553:
			return 90;
		case 221://bosses
		case 3066:
		case 3200:
		case 1351:
			return 50;
		case 2881:
		case 2882:
		case 2883:
			return 115;
		case 3847:
			return 100;
		case 3622:
			return 120;
		case 3340:
			return 50000000;//so mole doesn't respawn
		case 3843:
		case 3842:
		case 3841:
		case 2554:
		case 2555:
		case 2556:
		case 2557:
			return 2147000000; //so no respawn trolls
			case 6222:
			case 6247:
			case 6203:
			case 6260:
				case 6223:
		case 6225:
		case 6227:
//			case 6222:
			case 2559:
			case 2560:
			case 2561:
			case 2562:
			case 2563:
			case 2564:
			return 100;
			case 6142:
			case 6143:
			case 6144:
			case 6145:
			return 500;
			case 1549:
				return 10;
			default:
			return 25;
		}
	}
	
	
	
	public void newNPC(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}

		if(slot == -1) return;		// no free slot found

		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;

		if(npcType == 2555)
			npcs[slot].gfx0(754);
	}

	public void newNPCList(int npcType, String npcName, int combat, int HP) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] == null) {
				slot = i;
				break;
			}
		}

		if(slot == -1) return;		// no free slot found

		NPCList newNPCList = new NPCList(npcType);
		newNPCList.npcName = npcName;
		newNPCList.npcCombat = combat;
		newNPCList.npcHealth = HP;
		NpcList[slot] = newNPCList;
	}

	
	int currentX = 0, currentY = 0, counter = 0;

				public void handleDeath(int i) {

					Client c = (Client) Server.playerHandler.players[npcs[i].oldIndex];

					if(npcs[i].npcType == 3847) {
							if(npcs[i].HP < 200 && spawnedTrolls == false && !npcs[i].isDead) {
								spawnedTrolls = true;
								//amountOfTrolls = 3;
								//newNPC(2555, 2382, 4716, 0, 2, 175+(players*25), 22+((int)(players/2)), 150+players, 160+players);
								newNPC(3841, 3383, 3960, 0, 0, 150, 17, 175, 120);	
								newNPC(3842, 3378, 3959, 0, 0, 175, 18, 200, 135);	
								newNPC(3843, 3379, 3958, 0, 0, 200, 22, 250, 150);
							}
							if(npcs[i].isDead) {
								spawnedTrolls = false;
								//canKillQueen = false;
							}
						}
						if(npcs[i].npcType == 3841 || npcs[i].npcType == 3842 || npcs[i].npcType == 3843) {
							if(npcs[i].isDead) {
								amountOfTrolls--;
							}
						}

						if(npcs[i].npcType == 2555 || npcs[i].npcType == 2556 || npcs[i].npcType == 2557) {
								kMinionsDead++;
						} else if(npcs[i].npcType == 2554) {
								krilDead = true;
								krilTimer = 0;
								if(c != null) {
									c.sendMessage("@red@You have defeated the mighty K'ril Tsutsaroth!");
									if(c.countChaosTemple2() > playersInChaos) {
										playersInChaos = c.countChaosTemple2();
									}
								}

								for (int j = 0; j < Server.playerHandler.players.length; j++) {
									if (Server.playerHandler.players[j] != null) {
										Client c2 = (Client)Server.playerHandler.players[j];
										int damageRequired = (int) (2500 / (playersInChaos * 1.4));

										if(c != null && c2 != c) {
											if(c2.damageToKril >= damageRequired) {
												dropItems2(i, c2);
											}
										}
									}
								}
						}
						if(npcs[i].npcType == 3068 || npcs[i].npcType == 3069 || npcs[i].npcType == 3070 || npcs[i].npcType == 3071) {
							if(c != null) {
								c.freezeTimer = 0;
							}
						}
						if(npcs[i].npcType == 221 || npcs[i].npcType == 3066 || npcs[i].npcType == 2281 || npcs[i].npcType == 2882 || npcs[i].npcType == 2883 || npcs[i].npcType == 3200 || npcs[i].npcType == 50 || npcs[i].npcType == 1351 || npcs[i].npcType == 2550 || npcs[i].npcType == 3847 || npcs[i].npcType == 2554) {
							if(eventType == 4 && c != null) {
								if(c.inWild()) {
									c.eventScore++;
									c.sendMessage("[@red@Event@bla@] @red@Your score: @dre@"+c.eventScore+" "+((c.eventScore == 1) ? "kill" : "kills")+"@red@, In lead: @dre@"+eventWinnerName()+"");
									c.getItems().addPVP(false, Server.npcHandler.eventType, i);//potential drops
								}
							}
							appendSlayerOther(i);
						} else {
							if(eventType == 3 && c != null) {
								if(c.inWild()) {
									if(getNpcListHP(npcs[i].npcType) > 100) {
										c.eventScore++;
										c.getItems().addPVP(true, Server.npcHandler.eventType, i);//potential drops
									} else {
										c.getItems().addPVP(false, Server.npcHandler.eventType, i);//potential drops
										if(Misc.random(2) == 0) {
											c.eventScore++;
											if(Misc.random(2) == 0) {
												c.sendMessage("@red@Note:@bla@ You are gaining event score for 1 of every 3 kills.");
												c.sendMessage("Move to a more powerful monster to gain full points and bonus loot.");
											}
										}
									}
									if(Misc.random(2) == 0)
											c.sendMessage("[@red@Event@bla@] @red@Your score: @dre@"+c.eventScore+" "+((c.eventScore == 1) ? "kill" : "kills")+"@red@, In lead: @dre@"+eventWinnerName()+"");
								}
							}
						}
					}

	public void process() {
	dagSwitch--;
	moleTimer++;
	krilTimer++;

	eventTimer++;
	if(eventTimer == 500){// 500 5 min
		eventType = Misc.random(3) + 1;
		eventId = Misc.random(10000);
		startEvent();
	} else if(eventTimer >= 999) {
		if(eventTimer == 1000)//10 minutes remain
			eventMessage();
		if(eventTimer == 1500)//5
			eventMessage();
		if(eventTimer == 1700)//3
			eventMessage();
		if(eventTimer == 1900)//1
			eventMessage();
		if(eventTimer == 2000) {
			endEvent();
		}
	}
	
	if(moleTimer >= 3000){//30 min

		moleTimer = 0;
		if(Server.playerHandler.getPlayerCount() >= 40)
			moleTimer = 1500;//15 min

		spawnTheMole();
	}

	if(krilTimer >= 4000) {

		if(krilTimer == 4000)
			sendAll("[@red@Event@bla@] @red@Zamorak wizards are preparing to perform a ritual at the @dre@Chaos temple@red@..");

		if(krilTimer >= 4500 && krilDead){
			spawnKril(Server.playerHandler.getPlayerCount());
		}
	}
	
	if(dagSwitch <= 0)
		dagSwitch = 50;
	
	
	
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] == null) continue;
			npcs[i].clearUpdateFlags();
		}
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {
				if (npcs[i].actionTimer > 0) {
					npcs[i].actionTimer--;
				}
				
				if (npcs[i].npcType == 1351) {
					if(dagSwitch <= 1) {
						if(dagColor2 == 0){
							npcs[i].requestTransform(1352);
							dagColor2 = 1;
						}
						else if(dagColor2 == 1){
							npcs[i].requestTransform(1355);
							dagColor2 = 2;
						}
						else if(dagColor2 == 2){
							npcs[i].requestTransform(1351);
							dagColor2 = 0;
						}
					}
					if(dagColor2 == 1){
						npcs[i].requestTransform(1352);
					}
					else if(dagColor2 == 2){
						npcs[i].requestTransform(1355);
					}
					else if(dagColor2 == 0){
						npcs[i].requestTransform(1351);
					}
				}
				
				counter++;
				
				if(counter == 2){
					currentX = npcs[i].absX + 1;
					currentY = npcs[i].absY - 1;
				} else if (counter == 4){
					currentX = npcs[i].absX + 1;
					currentY = npcs[i].absY + 1;
				} else if (counter == 5){
					currentX = npcs[i].absX;
					currentY = npcs[i].absY + 1;
				} else if (counter == 3){
					currentX = npcs[i].absX + 1;
					currentY = npcs[i].absY;
				} else if (counter == 1){
					currentX = npcs[i].absX;
					currentY = npcs[i].absY - 1;
				} else if (counter == 6){
					currentX = npcs[i].absX - 1;
					currentY = npcs[i].absY + 1;
				} else if (counter == 7){
					currentX = npcs[i].absX - 1;
					currentY = npcs[i].absY;
				}
				
				
				if(counter == 7)
					counter = 0;
				
				//npcs[i].turnNpc(currentX, currentY);
				//npcs[i].animNumber = 866; // dead emote
				//npcs[i].animUpdateRequired = true;
				//npcs[i].forceChat("DANCE DANCE DANCE DANCE DANCE");

			if(npcs[i].npcType == 316 || npcs[i].npcType == 312 || npcs[i].npcType == 334 || npcs[i].npcType == 324 || npcs[i].npcType == 314 || npcs[i].npcType == 326 ||  npcs[i].npcType == 2067 ||  npcs[i].npcType == 2068){
				if(!npcs[i].isDead) {
					npcs[i].tickRevs++;
					if(npcs[i].tickRevs >= 90 && Misc.random(60) == 0){
						npcs[i].isDead = true;
					}
				}
			}

						if(npcs[i].npcType == 3847) {
							if(npcs[i].HP < 200 && spawnedTrolls == false && !npcs[i].isDead) {
								spawnedTrolls = true;
								//amountOfTrolls = 3;
								//newNPC(2555, 2382, 4716, 0, 2, 175+(players*25), 22+((int)(players/2)), 150+players, 160+players);
								newNPC(3841, 3383, 3960, 0, 0, 150, 17, 175, 120);	
								newNPC(3842, 3378, 3959, 0, 0, 175, 18, 200, 135);	
								newNPC(3843, 3379, 3958, 0, 0, 200, 22, 250, 150);
							}
						}
				
				if(npcs[i].npcType == 77 && !npcs[i].isDead){
					npcs[i].tickRevs++;
					if(npcs[i].tickRevs >= 45){
						if(npcs[i].spawnedBy > 0){
							
							if(Server.playerHandler.players[npcs[i].spawnedBy] != null){
								Client ownedPlayer = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
								if(ownedPlayer.getPand().inMission()){
									if(ownedPlayer.getPand().lastBound <= 0){
										ownedPlayer.getPand().lastBound = 25 + Misc.random(10);
										ownedPlayer.stopMovement();
										ownedPlayer.freezeTimer = 6;
										ownedPlayer.sendMessage("A zombie teleports to you and entagles you!");
										ownedPlayer.gfx0(572);
										ownedPlayer.updateRequired = true;
										npcs[i].isDead = true;
										npcs[i].applyDead = true;
										npcs[i].updateRequired = true;
										ownedPlayer.getPand().spawnEnemyNpc(77, false, ownedPlayer.absX, ownedPlayer.absY);
										continue;
									}
								}
							}
							
						}
					}
				}
				
				
				
				/*if(amountOfTrolls == 0 && spawnedTrolls == true)
				{
					canKillQueen = true;
				}*/
				
				
				
				if (npcs[i].freezeTimer > 0) {
					npcs[i].freezeTimer--;
				}
				
				if (npcs[i].freezeTimer > 20 && npcs[i].npcType == 2883) {
					npcs[i].freezeTimer = 20;
				}
				
				if (npcs[i].freezeTimer > 0 && npcs[i].npcType == 221) {
					npcs[i].freezeTimer = 0;
					 npcs[i].updateRequired = true;
                     npcs[i].forceChat("You cannot freeze me, you fool!");
				}
				
				if (npcs[i].hitDelayTimer > 0) {
					npcs[i].hitDelayTimer--;
				}
				
				if (npcs[i].hitDelayTimer == 1) {
					npcs[i].hitDelayTimer = 0;
					applyDamage(i);
				}
				
				if(npcs[i].attackTimer > 0) {
					npcs[i].attackTimer--;
				}
				if (npcs[i].npcType == 3498){
					if(Misc.random2(30) <= 2 && dagColor == 0){
					NPCHandler.npcs[i].requestTransform(3499);
					dagColor = 1;
					}
					if(Misc.random2(30) <= 2 && dagColor == 1){
					NPCHandler.npcs[i].requestTransform(3501);
					dagColor = 2;
					}
					if(Misc.random2(30) <= 2 && dagColor == 2){
					NPCHandler.npcs[i].requestTransform(3498);
					dagColor = 0;
					}
				}
				
				if (npcs[i].npcType == 1351){
					if(dagSwitch <= 1 && dagColor2 == 0){
					//dagSwitch = 30;
					NPCHandler.npcs[i].requestTransform(1352);
					dagColor2 = 1;
					continue;
					}
					if(dagSwitch <= 1 && dagColor2 == 1){
					//dagSwitch = 30;
					NPCHandler.npcs[i].requestTransform(1355);
					dagColor2 = 2;
					continue;
					}
					if(dagSwitch <= 1 && dagColor2 == 2){
					//dagSwitch = 30;
					NPCHandler.npcs[i].requestTransform(1351);
					dagColor2 = 0;
					continue;
					}
				}

				if (npcs[i].npcType == 1597){ //vanakka lol
								if(eventType != 0) {
                                        if (Misc.random2(100) <= 3) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("The Event is "+eventName[eventType]+"! In lead: "+eventWinnerName()+".");
                                        }
										else if (Misc.random2(100) >= 98) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("The Event is "+eventName[eventType]+"!");
                                        }
                                } else if(eventType == 0 && eventId != 0) {
                                	 if (Misc.random2(100) >= 98) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Previous event, "+eventName[previousEvent]+" winner: "+eventWinner+"!");
                                        }
                                    else if (Misc.random2(100) <= 2) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Next Event will begin in "+(((500 - eventTimer) + 100) / 100)+" "+(Server.npcHandler.eventTimer < 400 ? "minutes" : "minute")+"!");
                                        }
                                } else if(eventType == 0 && eventId == 0) {
                                	if (Misc.random2(100) >= 96) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Next Event will begin in "+(((500 - eventTimer) + 100) / 100)+" "+(Server.npcHandler.eventTimer < 400 ? "minutes" : "minute")+"!");
                                        }
                                }
                            }
				
				if (npcs[i].npcType == 636){ //cheerleader
                                        if (Misc.random2(100) <= 5) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Talk to me if you want to vote! Get free stuff!");
                                        }
										if (Misc.random2(100) >= 95) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Have you voted today?");
                                        }
                                }	
								
							if (npcs[i].npcType == 221){ //black knight titan
                                        if (Misc.random2(250) <= 1) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Come here so I can CRUSH you, puny human!");
                                        }
										if (Misc.random2(250) >= 249) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("You DARE enter the Black Knight Hideout? My knights will destroy you!");
                                        }
                                }	
								
								if (npcs[i].npcType == 399){ //cheerleader
                                        if (Misc.random2(100) <= 5) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Need some free gear?");
                                        }
										if (Misc.random2(100) >= 95) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("I've got plenty of free armour to go around.");
                                        }
                                }	
				if(npcs[i].spawnedBy > 0) { // delete summons npc
					
					if(npcs[i].isInstanced){
						if((Client)Server.playerHandler.players[npcs[i].spawnedBy] != null){
							if(((Client) Server.playerHandler.players[npcs[i].spawnedBy]).getPand().inMission() == false){
								npcs[i] = null;
								continue;
							}
						}
					}
					
					if(Server.playerHandler.players[npcs[i].spawnedBy] == null
					|| Server.playerHandler.players[npcs[i].spawnedBy].heightLevel != npcs[i].heightLevel	
					|| Server.playerHandler.players[npcs[i].spawnedBy].respawnTimer > 0 
					|| !Server.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Server.playerHandler.players[npcs[i].spawnedBy].getX(), Server.playerHandler.players[npcs[i].spawnedBy].getY(), 20)) {
							
						if(Server.playerHandler.players[npcs[i].spawnedBy] != null) {
							for(int o = 0; o < Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs.length; o++){
								if(npcs[i].npcType == Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][0]) {
									if (Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] == 1)
										Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] = 0;
								}
							}
						}
						npcs[i] = null;
					}
				}
				if (npcs[i] == null) continue;
				
				/**
				* Attacking player
				**/

				if (isAggressive(i) && npcs[i].npcType != 7891 && !npcs[i].underAttack && npcs[i].killerId <= 0 && !npcs[i].isDead && !switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				} else if (isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead && switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				}
				
				if (System.currentTimeMillis() - npcs[i].lastDamageTaken > 5000)
					npcs[i].underAttackBy = 0;
				
				if((npcs[i].killerId > 0 || npcs[i].underAttack) && npcs[i].npcType != 7891 && !npcs[i].walkingHome && retaliates(npcs[i].npcType)) {
					if(!npcs[i].isDead) {
						int p = npcs[i].killerId;
						if(Server.playerHandler.players[p] != null) {
							Client c = (Client) Server.playerHandler.players[p];			
								followPlayer(i, c.playerId);
							//NPCDumbPathFinder.follow(npcs[i], c);
							if (npcs[i] == null) continue;
							if(npcs[i].attackTimer == 0) {
								if(c != null) {
									attackPlayer(c, i);
									
									if(npcs[i].npcType == 2554) {
										if(!goodDistance(c.absX, c.absY, npcs[i].absX, npcs[i].absY, 3))
											npcs[i].killerId = getCloseRandomPlayer(i);
									}
								} else {
									npcs[i].killerId = 0;
									npcs[i].underAttack = false;
									npcs[i].facePlayer(0);
								}
							}
						} else {
							npcs[i].killerId = 0;
							npcs[i].underAttack = false;
							npcs[i].facePlayer(0);
						}
					}
				}
				
				
		
				/**
				* Random walking and walking home
				**/
				if (npcs[i] == null) continue;
				if((!npcs[i].underAttack || npcs[i].walkingHome) && npcs[i].randomWalk && !npcs[i].isDead) {
					npcs[i].facePlayer(0);
					npcs[i].killerId = 0;	
					if(npcs[i].spawnedBy == 0) {
						if((npcs[i].absX > npcs[i].makeX + npcs[i].walkingType) || (npcs[i].absX < npcs[i].makeX - npcs[i].walkingType) || (npcs[i].absY > npcs[i].makeY + npcs[i].walkingType) || (npcs[i].absY < npcs[i].makeY - npcs[i].walkingType)) {
							npcs[i].walkingHome = true;
						}
					}

					if (npcs[i].walkingHome && npcs[i].absX == npcs[i].makeX && npcs[i].absY == npcs[i].makeY) {
						npcs[i].walkingHome = false;
					} else if(npcs[i].walkingHome) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[i].makeX);
			      		npcs[i].moveY = GetMove(npcs[i].absY, npcs[i].makeY);
						handleClipping(i);
						npcs[i].getNextNPCMovement(i);
						npcs[i].updateRequired = true;
					}
					if(npcs[i].walkingType >= 1) {
						if(Misc.random(3)== 1 && !npcs[i].walkingHome) {
							int MoveX = 0;
							int MoveY = 0;			
							int Rnd = Misc.random(9);
							if (Rnd == 1) {
								MoveX = 1;
								MoveY = 1;
							} else if (Rnd == 2) {
								MoveX = -1;
							} else if (Rnd == 3) {
								MoveY = -1;
							} else if (Rnd == 4) {
								MoveX = 1;
							} else if (Rnd == 5) {
								MoveY = 1;
							} else if (Rnd == 6) {
								MoveX = -1;
								MoveY = -1;
							} else if (Rnd == 7) {
								MoveX = -1;
								MoveY = 1;
							} else if (Rnd == 8) {
								MoveX = 1;
								MoveY = -1;
							}
										
							if (MoveX == 1) {
								if (npcs[i].absX + MoveX < npcs[i].makeX + 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}
							
							if (MoveX == -1) {
								if (npcs[i].absX - MoveX > npcs[i].makeX - 1)  {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}
							
							if(MoveY == 1) {
								if(npcs[i].absY + MoveY < npcs[i].makeY + 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}
							
							if(MoveY == -1) {
								if(npcs[i].absY - MoveY > npcs[i].makeY - 1)  {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}
								

							int x = (npcs[i].absX + npcs[i].moveX);
							int y = (npcs[i].absY + npcs[i].moveY);
							if (VirtualWorld.I(npcs[i].heightLevel, npcs[i].absX, npcs[i].absY, x, y, 0)){
								handleClipping(i);
								npcs[i].getNextNPCMovement(i);
								//npcs[i].updateRequired = true;
							} else
							{
								npcs[i].moveX = 0;
								npcs[i].moveY = 0;
							} 
							npcs[i].updateRequired = true;
						}
					}
				}

				if (npcs[i].isDead == true) {
					//Client c = (Client) Server.playerHandler.players[npcs[i].oldIndex];
					//Client temp = (Client) Server.playerHandler.players[npcs[i].killerId];
					//if(c == null && temp == null && !(npcs[i].npcType == 316 || npcs[i].npcType == 312 || npcs[i].npcType == 334 || npcs[i].npcType == 324 || npcs[i].npcType == 314 || npcs[i].npcType == 326 ||  npcs[i].npcType == 2067 ||  npcs[i].npcType == 2068))
					//	continue;
					if (npcs[i].actionTimer == 0 && npcs[i].applyDead == false && npcs[i].needRespawn == false) {
						npcs[i].updateRequired = true;
						npcs[i].facePlayer(0);
						npcs[i].killedBy = getNpcKillerId(i);
						npcs[i].animNumber = getDeadEmote(i); // dead emote
						npcs[i].animUpdateRequired = true;
						npcs[i].freezeTimer = 0;
						npcs[i].applyDead = true;
						killedBarrow(i);
						//c.singleCombatDelay2 = System.currentTimeMillis() - 5000;
						npcs[i].actionTimer = 4; // delete time
						resetPlayersInCombat(i);
						
					} else if (npcs[i].actionTimer == 0 && npcs[i].applyDead == true &&  npcs[i].needRespawn == false) {						
						npcs[i].needRespawn = true;
						npcs[i].actionTimer = getRespawnTime(i); // respawn time
						//c.singleCombatDelay2 = System.currentTimeMillis() - 5000;
						dropItems(i); // npc drops items!
						appendSlayerExperience(i);
						handleDeath(i);
						appendSpecial(i);
						//appendPotential(i);
						appendKillCount(i);
						appendJailKc(i);
						
						/*(npcs[i] != null){
							if(npcs[i].isInstanced){
								Client c = (Client) Server.playerHandler.players[npcs[i].oldIndex];
								if(c != null){
									if(c.getPand() != null) {
									  if(c.getPand().inMission() && !npcs[i].forcedDeath){
									   c.getPand().totalNpcs--;
									   c.getPand().killCount++; //all other monsters, add 1 kc
									   if(npcs[i].npcType != 3066 && npcs[i].npcType != 1575 && npcs[i].npcType != 1973)
									   c.getPand().totalPointsEarned += (npcs[i].MaxHP * 4);
										if(npcs[i].npcType == 1575)
									   c.getPand().totalPointsEarned += (npcs[i].MaxHP * 5);
										if(npcs[i].npcType == 1973)
									   c.getPand().totalPointsEarned += (npcs[i].MaxHP * 6);
										if(npcs[i].npcType == 3066)
									   c.getPand().totalPointsEarned += (npcs[i].MaxHP * 10) + 2000;
									   c.currentKC++; 
									   c.currentPPoints += (npcs[i].MaxHP * 4); 
									  } else if(npcs[i].forcedDeath && c.getPand().inMission()){
									   c.getPand().totalNpcs--;
									  }
									 }
								}
							}
						}*/
						
						npcs[i].absX = npcs[i].makeX;
						npcs[i].absY = npcs[i].makeY;				
						npcs[i].HP = npcs[i].MaxHP;
						npcs[i].animNumber = 0x328;
						npcs[i].updateRequired = true;
						npcs[i].animUpdateRequired = true;
						if (npcs[i].npcType >= 2440 && npcs[i].npcType <= 2446) {
							Server.objectManager.removeObject(npcs[i].absX, npcs[i].absY);
						}
					} else if (npcs[i].actionTimer == 0 && npcs[i].needRespawn == true) {					
						if(npcs[i].spawnedBy > 0) {
							npcs[i] = null;
						} else {
							int old1 = npcs[i].npcType;
							int old2 = npcs[i].makeX;
							int old3 = npcs[i].makeY;
							int old4 = npcs[i].heightLevel;
							int old5 = npcs[i].walkingType;
							int old6 = npcs[i].MaxHP;
							int old7 = npcs[i].maxHit;
							int old8 = npcs[i].attack;	
							int old9 = npcs[i].defence;
							
							npcs[i] = null;
							newNPC(old1, old2, old3, old4, old5, old6, old7, old8, old9);
						}
					}
				}
			}
		}
	}
       
	public boolean getsPulled(int i) {
		switch (npcs[i].npcType) {
			case 2550:
				if (npcs[i].firstAttacker > 0)
					return false;
			break;
		}
		return true;
	}

	/**
	* Size of the multi attacks
	**/
	public int multiAttackSize(int i) {
		switch (npcs[i].npcType) {
			case 2554:
			if(npcs[i].attackType == 2)
				return 12;
			else
				return 2;
			default:
			return 8;
		}
	}

	/**
	* Does the NPC attack multiple targets?
	**/  

	public boolean multiAttacks(int i) {
		switch (npcs[i].npcType) {
		case 6223:
		case 6225:
		case 6227:
		case 50:
		case 3200:
		case 6222:
		case 3847:
		case 2554:
		case 3340:
			return true;

		case 3066:
			if(npcs[i].attackType == 2 || npcs[i].attackType == 1)
				return true;

		case 1351:
			if(npcs[i].attackType == 1 || npcs[i].attackType == 2)
				return true;
			
			default:
			return false;
		}
	
	
	}
	
	/**
	* Npc killer id?
	**/
	
	public int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int count = 0;
		int killerId = 0;

		for (int p = 1; p < Config.MAX_PLAYERS; p++)  {
			if (Server.playerHandler.players[p] != null) {
				if(Server.playerHandler.players[p].lastNpcAttacked == npcId) {
					if(Server.playerHandler.players[p].totalDamageDealt > oldDamage) {
						oldDamage = Server.playerHandler.players[p].totalDamageDealt;
						killerId = p;
					}
					Server.playerHandler.players[p].totalDamageDealt = 0;
				}	
			}
		}

		if(killerId == 0) // for multi barrages, will give drop to last person to attack it
			return Server.npcHandler.npcs[npcId].underAttackBy;

		return killerId;
	}
		
	/**
	 * 
	 */
	private void killedBarrow(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			for(int o = 0; o < c.barrowsNpcs.length; o++){
				if(npcs[i].npcType == c.barrowsNpcs[o][0]) {
					c.barrowsNpcs[o][1] = 2; // 2 for dead
					c.barrowsKillCount++;	

				}
			}
		}
	}
	
	/*private void killedTzhaar(int i) {
		final Client c2 = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
		c2.tzhaarKilled++;
		//System.out.println("To kill: " + c2.tzhaarToKill + " killed: " + c2.tzhaarKilled);
		if (c2.tzhaarKilled == c2.tzhaarToKill) {
			//c2.sendMessage("STARTING EVENT");
			c2.waveId++;
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					if (c2 != null) {
						Server.fightCaves.spawnNextWave(c2);
					}	
					c.stop();
				}
			}, 7500);
			
		}
	}*/
	
	public void handleJadDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
		c.getItems().addItem(6570,1);
		c.sendMessage("Congratulations on completing the fight caves minigame!");
		c.sendMessage("You have received 5 Massacred World points for this.");
		c.getPA().resetTzhaar();
		c.waveId = 300;
		c.pcPoints += 5;
c.updateText();
	}
	
	
	/**
	* Dropping Items!
	**/
	
	public boolean rareDrops(int i) {
		return Misc.random(NPCDrops.dropRarity.get(npcs[i].npcType)) == 0;
	}
	
	
	public void dropItems(int i) {
		int npc = 0, theRoll = 0, amountDropped = 0, newDrop = 0, myDrop = 0, playersForDrop = 0;
		double ringBonus = 1, gildedBonus = 1, wildBonus = 1, skullBonus = 1, lampBonus = 1, nerdBonus = 1;
		
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];

		double slayerBonus = 0.0;
		double gameBonus = 1;
		double totalBonus = 1;
		double eventBonus = 1;
		double adjustmentBonus = 1; // adjustment due to drop rate changes
		
		if(c == null) 
			return;

		if(c.inWild())
			wildBonus = 0.75; // 50% bonus drop rate in wild

		//slayerBonus = 1 - ((double)c.playerLevel[18] / 396);
		slayerBonus = 1 - ((double)(c.playerLevel[18]) / 198);

		if(c.playerLevel[18] == 99)
			slayerBonus -= ((double)c.playerXP[18] / 800000000);

		if(c.playerEquipment[c.playerWeapon] == 15998)
				gildedBonus = 0.975;

		gildedBonus -= .00625*c.gildedAmount();

		if(c.playerEquipment[c.playerRing] == 2572)
			ringBonus = 0.9625;
		if(c.playerEquipment[c.playerRing] == 6465)
			ringBonus = 0.975;
		if(c.playerEquipment[c.playerRing] == 4202)
			ringBonus = 0.985;
		if(c.gameMode == 2)//10% bonus drop rate
			gameBonus = 0.95;
		if(c.bonusDrops())
			lampBonus = 0.875;
		if(c.alldp >= 1000)
			nerdBonus = 1;
		
			
		if(c.inWild() && c.isSkulled)
			skullBonus = 0.975;
		if(c.inWild() && c.redSkull == 1)
			skullBonus = 0.925;

		if(npcs[i].npcType == 221 || npcs[i].npcType == 3066 || npcs[i].npcType == 2281 || npcs[i].npcType == 2882 || npcs[i].npcType == 2883 || npcs[i].npcType == 3200 || npcs[i].npcType == 50 || npcs[i].npcType == 1351 || npcs[i].npcType == 2550 || npcs[i].npcType == 3847 || npcs[i].npcType == 2554) {
			if(eventType == 4)
				eventBonus = .875;
			} else if(eventType == 3) {
				eventBonus = .875;
		}
		
			
		/*if(c.inWild() && c.potential > 0){ //potential drop rate bonus is 10% of total potential
			skullBonus = (1 - ((double)c.potential / 4000));
		}*/

		totalBonus = (int)((double)((double)((double)((double)((double)((double)((double)((double)((double)(100 * ringBonus)) * wildBonus) * (slayerBonus)) * gameBonus) * skullBonus) * adjustmentBonus) * lampBonus) * nerdBonus) * gildedBonus);
					
		if(c.playerRights >= 3)
			c.sendMessage("Slayer bonus: "+slayerBonus+" Potential bonus: "+skullBonus+" Total bonus: "+totalBonus);
				
		if(c != null) {
			
			if(npcs[i].npcType == 77 && Misc.random(4) == 0){
				if(Misc.random(1) == 1)
				Server.itemHandler.createGroundItem(c, 391, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
					else
				Server.itemHandler.createGroundItem(c, 385, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
			}
			
			if(npcs[i].npcType == 1575){
				Server.itemHandler.createGroundItem(c, 15272, npcs[i].absX, npcs[i].absY, 1 + Misc.random(1), c.playerId, -1);
			}
			
			if(npcs[i].npcType == 1973){
				Server.itemHandler.createGroundItem(c, 15272, npcs[i].absX, npcs[i].absY, 1 + Misc.random(2), c.playerId, -1);
				if(Misc.random(2) == 0)
					Server.itemHandler.createGroundItem(c, 3024, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
			}
			
			if(c.getPand().inMission() && (npcs[i].npcType == 3066))
			{
				if(Misc.random(1) == 0)
				Server.itemHandler.createGroundItem(c, 391, npcs[i].absX, npcs[i].absY, 3, c.playerId, -1);
					else
				Server.itemHandler.createGroundItem(c, 15272, npcs[i].absX, npcs[i].absY, 3, c.playerId, -1);
				if(Misc.random(1) == 0)
					Server.itemHandler.createGroundItem(c, 3024, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
			}
			if(npcs[i].npcType == 3340){
			
			if (c.clanId >= 0) {
			for (int j = 0; j < Server.clanChat.clans[c.clanId].members.length; j++) {
			if (Server.clanChat.clans[c.clanId].members[j] <= 0)
					continue;
			if(Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]] != null){
				//Client m = (Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]];
				//if (m != null) {
					//if(goodDistance(m.absX, m.absY, npcs[i].absX, npcs[i].absY, 40)){
					//m.sendMessage("@red@Each clan member receives 1M coins for their efforts.");
					if(goodDistance(Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].absX, Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].absY, npcs[i].absX, npcs[i].absY, 40)){
					//Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].sendMessage("@red@Each clan member receives 1M coins for their efforts.");
					//m.sendMessage(""+);
					Server.itemHandler.createGroundItem((Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]], 995, npcs[i].absX, npcs[i].absY, 1000000, Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].playerId);
					}
				}
		}
		
			} else {
			c.sendMessage("@red@You have received 2.5M coins for your effort.");
			Server.itemHandler.createGroundItem(c, 995, npcs[i].absX, npcs[i].absY, 2500000, c.playerId);
			}
		}
			
			
			for(npc = 0; npc < Config.NPC_DROPS.length; npc++){
				if(npcs[i].npcType == Config.NPC_DROPS[npc][0] && !c.getPand().inMission()) {
				int theFirstRoll = (int)((double)((double)((double)((double)((double)((double)((double)((double)((double)((double)Config.NPC_DROPS[npc][3] * ringBonus) * wildBonus) * (slayerBonus)) * gameBonus) * skullBonus) * adjustmentBonus) * lampBonus) * nerdBonus) * gildedBonus) * eventBonus);
				theRoll = Misc.random((int)((double)((double)((double)((double)((double)((double)((double)((double)((double)((double)Config.NPC_DROPS[npc][3] * ringBonus) * wildBonus) * (slayerBonus)) * gameBonus) * skullBonus) * adjustmentBonus) * lampBonus) * nerdBonus) * gildedBonus) * eventBonus));
				
				if((Config.NPC_DROPS[npc][1] == 85 || Config.NPC_DROPS[npc][1] == 1547 || Config.NPC_DROPS[npc][1] == 13045 || Config.NPC_DROPS[npc][1] == 13047) && !c.inWild())//shiny and magic keys only in wild
					continue;

				if(c.playerRights > 2) //admin drop spamerino
					c.sendMessage("[@red@"+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+"@bla@]Chance: @red@"+theFirstRoll+" @bla@Roll: @red@"+theRoll);
					
					if(theRoll == 0) {
					if(Config.NPC_DROPS[npc][2] > 1 && Config.NPC_DROPS[npc][2] <= 3) //random if less than 3
						amountDropped = 1 + Misc.random(Config.NPC_DROPS[npc][2]);
					if(Config.NPC_DROPS[npc][2] > 1 && Config.NPC_DROPS[npc][2] <= 10) // 60% always 40% random
						amountDropped =  (int)((Misc.random((int) (Config.NPC_DROPS[npc][2] * .4)) + ((int) (Config.NPC_DROPS[npc][2] * .6))));
					if(Config.NPC_DROPS[npc][2] > 10)
						amountDropped = ((Misc.random((int) (Config.NPC_DROPS[npc][2] * .4)) + ((int) (Config.NPC_DROPS[npc][2] * .6))));

					newDrop = amountDropped;

						if(npcs[i].npcType == 3847) {
								int myLoot = 0;
								int lastNpc = 0;
								for(int j = 0; j <= 3; j++) {
									int choice = Misc.random(3);
									for(npc = 0; npc < Config.NPC_DROPS.length; npc++){
										if(npcs[i].npcType == Config.NPC_DROPS[npc][0]) {
											lastNpc = npc;
											int theRoll2 = Misc.random((int)((double)((double)((double)((double)((double)((double)((double)((double)((double)((double)Config.NPC_DROPS[npc][3] * ringBonus) * wildBonus) * (slayerBonus)) * gameBonus) * skullBonus) * adjustmentBonus) * lampBonus) * nerdBonus) * gildedBonus) * eventBonus));
											if(theRoll2 == 0) {
												myLoot = npc;
													break;
											}
										} else if(npc == Config.NPC_DROPS.length){
											myLoot = lastNpc;
												break;
										}
									}
									if(dropCounter >= 3)
										break;
									Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[myLoot][1], trollQueenItemDropCoords[choice][0], trollQueenItemDropCoords[choice][1], Misc.random(Config.NPC_DROPS[myLoot][2]), c.playerId);
									dropCounter++;
									for (int j2 = 0; j2 < Server.playerHandler.players.length; j2++) {
										if (Server.playerHandler.players[j2] != null) {
											Client c2 = (Client)Server.playerHandler.players[j2];
											if(c2.announceOn == 0){
												if(c.getShops().getItemShopValue(Config.NPC_DROPS[myLoot][1]) > 20000000) {
													c2.sendMessage("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just received a "+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+" drop!");
													String dropText = c.playerName + " got a rare drop: "+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+"!";
													c.getPA().writeDropLine(dropText);
												}
													
											}
										}
									}
								}

								break;
							}
					
						if(Config.NPC_DROPS[npc][2] == 1){
							amountDropped = 1;
							
							Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, Config.NPC_DROPS[npc][2], c.playerId);
							
							if (c.clanId >= 0) {
								if(Server.clanChat.clans[c.clanId].lootshare) {
									Server.clanChat.handleLootShare(c, Config.NPC_DROPS[npc][1], 1);
								}
							}

							for (int j = 0; j < Server.playerHandler.players.length; j++) {
								if (Server.playerHandler.players[j] != null) {
									Client c2 = (Client)Server.playerHandler.players[j];
									if(c2.announceOn == 0){
										if(c.getShops().getItemShopValue(Config.NPC_DROPS[npc][1]) > 20000000) {
											c2.sendMessage("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just received a "+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+" drop!");
											String dropText = c.playerName + " got a rare drop: "+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+"!";
											c.getPA().writeDropLine(dropText);
										}
									}
								}
							}
							} else if(Config.NPC_DROPS[npc][2] > 1) {
							if (c.clanId >= 0) {
								if(Server.clanChat.clans[c.clanId].lootshare) {
									playersForDrop = 0;
									for (int j = 0; j < Server.clanChat.clans[c.clanId].members.length; j++) {//counting players for drop
											if (Server.clanChat.clans[c.clanId].members[j] <= 0)
												continue;
											if (Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]] != null) {
												Client cc = (Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]];
												if(c.goodDistance(c.getX(), c.getY(), cc.getX(), cc.getY(), 20)) {
													playersForDrop++;
													//c.sendMessage("playersForDrop:"+playersForDrop);
												}
											}
										}
									myDrop = (int) Math.ceil(newDrop / playersForDrop);
									for (int j = 0; j < Server.clanChat.clans[c.clanId].members.length; j++) {
											if (Server.clanChat.clans[c.clanId].members[j] <= 0)
												continue;
											if (Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]] != null) {
												Client cc = (Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]];
												if(c.goodDistance(c.getX(), c.getY(), cc.getX(), cc.getY(), 20)) {
												if(myDrop > newDrop)
													myDrop = newDrop;
												Server.itemHandler.createGroundItem(cc, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, myDrop, cc.playerId);
												Server.clanChat.handleLootShare(cc, Config.NPC_DROPS[npc][1], myDrop);
												newDrop -= myDrop;
												}
											}
										}
									} else {
										Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, Config.NPC_DROPS[npc][2], c.playerId);	
									}
								} else {
									Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, Config.NPC_DROPS[npc][2], c.playerId);
							}
							
						}
						/*if (c.clanId >= 0) {
							if(Server.clanChat.clans[c.clanId].lootshare) {
							Server.clanChat.handleLootShare(c, Config.NPC_DROPS[npc][1], amountDropped);
							}
						}*/
					}
					if(Config.NPC_DROPS[npc][3] > 0 && theRoll == 0)
						break;
				}
			}
			dropCounter = 0;
			if(npcs[i].npcType == 3847)
			{
				c.sendMessage("@red@As you kill the queen, the items dropped wash up to the shore.");
			}
		}
	}
	public void dropItems2(int i, Client c) {
		int npc = 0, theRoll = 0, amountDropped = 0, newDrop = 0, myDrop = 0, playersForDrop = 0;
		double ringBonus = 1, gildedBonus = 1, wildBonus = 1, skullBonus = 1, lampBonus = 1, nerdBonus = 1;
		
		double slayerBonus = 0.0;
		double gameBonus = 1;
		double totalBonus = 1;
		double eventBonus = 1;
		double adjustmentBonus = .75; // adjustment due to drop rate changes
		
		if(c == null) 
			return;

		if(c.inWild())
			wildBonus = 0.75; // 50% bonus drop rate in wild

		//slayerBonus = 1 - ((double)c.playerLevel[18] / 396);
		slayerBonus = 1 - ((double)(c.playerLevel[18]) / 198);

		if(c.playerLevel[18] == 99)
			slayerBonus -= ((double)c.playerXP[18] / 800000000);

		if(c.playerEquipment[c.playerWeapon] == 15998)
				gildedBonus = 0.975;

		gildedBonus -= .00625*c.gildedAmount();

		if(c.playerEquipment[c.playerRing] == 2572)
			ringBonus = 0.9625;
		if(c.playerEquipment[c.playerRing] == 6465)
			ringBonus = 0.975;
		if(c.playerEquipment[c.playerRing] == 4202)
			ringBonus = 0.985;
		if(c.gameMode == 2)//10% bonus drop rate
			gameBonus = 0.95;
		if(c.bonusDrops())
			lampBonus = 0.875;
		if(c.alldp >= 1000)
			nerdBonus = 1;
		
			
		if(c.inWild() && c.isSkulled)
			skullBonus = 0.975;
		if(c.inWild() && c.redSkull == 1)
			skullBonus = 0.925;
		
		if(npcs[i].npcType == 221 || npcs[i].npcType == 3066 || npcs[i].npcType == 2281 || npcs[i].npcType == 2882 || npcs[i].npcType == 2883 || npcs[i].npcType == 3200 || npcs[i].npcType == 50 || npcs[i].npcType == 1351 || npcs[i].npcType == 2550 || npcs[i].npcType == 3847 || npcs[i].npcType == 2554) {
			if(eventType == 4)
				eventBonus = .875;
			} else if(eventType == 3) {
				eventBonus = .875;
		}
		
			
		/*if(c.inWild() && c.potential > 0){ //potential drop rate bonus is 10% of total potential
			skullBonus = (1 - ((double)c.potential / 4000));
		}*/

		if(npcs[i].npcType == 2554) {
			eventBonus = 1.75; // Decreases droprate to 1/4th because this is for people who didn't kill the boss
		}

		totalBonus = (int)((double)((double)((double)((double)((double)((double)((double)((double)((double)(100 * ringBonus)) * wildBonus) * (slayerBonus)) * gameBonus) * skullBonus) * adjustmentBonus) * lampBonus) * nerdBonus) * gildedBonus);
			
		if(c.playerRights >= 3)
			c.sendMessage("Slayer bonus: "+slayerBonus+" Potential bonus: "+skullBonus+" Total bonus: "+totalBonus);
				
		if(c != null) {
			for(int o = 0; o < c.barrowsNpcs.length; o++){
				if(npcs[i].npcType == c.barrowsNpcs[o][0]) {//barrows wilderness drop bonus 25%
					c.barrowsNpcs[o][1] = 2; // 2 for dead
					c.barrowsKillCount++;
					if(c.inWild()){
					wildBonus = .75;
					}
				}
			}
			
			if(npcs[i].npcType == 77 && Misc.random(4) == 0){
				if(Misc.random(1) == 1)
				Server.itemHandler.createGroundItem(c, 391, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
					else
				Server.itemHandler.createGroundItem(c, 385, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
			}
			
			if(npcs[i].npcType == 1575){
				Server.itemHandler.createGroundItem(c, 15272, npcs[i].absX, npcs[i].absY, 1 + Misc.random(1), c.playerId, -1);
			}
			
			if(npcs[i].npcType == 1973){
				Server.itemHandler.createGroundItem(c, 15272, npcs[i].absX, npcs[i].absY, 1 + Misc.random(2), c.playerId, -1);
				if(Misc.random(2) == 0)
					Server.itemHandler.createGroundItem(c, 3024, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
			}
			
			if(c.getPand().inMission() && (npcs[i].npcType == 3066))
			{
				if(Misc.random(1) == 0)
				Server.itemHandler.createGroundItem(c, 391, npcs[i].absX, npcs[i].absY, 3, c.playerId, -1);
					else
				Server.itemHandler.createGroundItem(c, 15272, npcs[i].absX, npcs[i].absY, 3, c.playerId, -1);
				if(Misc.random(1) == 0)
					Server.itemHandler.createGroundItem(c, 3024, npcs[i].absX, npcs[i].absY, 1, c.playerId, -1);
			}
			if(npcs[i].npcType == 3340){
			
			if (c.clanId >= 0) {
			for (int j = 0; j < Server.clanChat.clans[c.clanId].members.length; j++) {
			if (Server.clanChat.clans[c.clanId].members[j] <= 0)
					continue;
			if(Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]] != null){
				//Client m = (Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]];
				//if (m != null) {
					//if(goodDistance(m.absX, m.absY, npcs[i].absX, npcs[i].absY, 40)){
					//m.sendMessage("@red@Each clan member receives 1M coins for their efforts.");
					if(goodDistance(Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].absX, Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].absY, npcs[i].absX, npcs[i].absY, 40)){
					//Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].sendMessage("@red@Each clan member receives 1M coins for their efforts.");
					//m.sendMessage(""+);
					Server.itemHandler.createGroundItem((Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]], 995, npcs[i].absX, npcs[i].absY, 1000000, Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]].playerId);
					}
				}
		}
		
			} else {
			c.sendMessage("@red@You have received 2.5M coins for your effort.");
			Server.itemHandler.createGroundItem(c, 995, npcs[i].absX, npcs[i].absY, 2500000, c.playerId);
			}
		}
			
			
			for(npc = 0; npc < Config.NPC_DROPS.length; npc++){
				if(npcs[i].npcType == Config.NPC_DROPS[npc][0] && !c.getPand().inMission()) {
				int theFirstRoll = (int)((double)((double)((double)((double)((double)((double)((double)((double)((double)((double)Config.NPC_DROPS[npc][3] * ringBonus) * wildBonus) * (slayerBonus)) * gameBonus) * skullBonus) * adjustmentBonus) * lampBonus) * nerdBonus) * gildedBonus) * eventBonus);
				theRoll = Misc.random((int)((double)((double)((double)((double)((double)((double)((double)((double)((double)((double)Config.NPC_DROPS[npc][3] * ringBonus) * wildBonus) * (slayerBonus)) * gameBonus) * skullBonus) * adjustmentBonus) * lampBonus) * nerdBonus) * gildedBonus) * eventBonus));
				
				if((Config.NPC_DROPS[npc][1] == 85 || Config.NPC_DROPS[npc][1] == 1547) && !c.inWild())//shiny and magic keys only in wild
					continue;

				if(c.playerRights > 2) //admin drop spamerino
					c.sendMessage("[@red@"+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+"@bla@]Chance: @red@"+theFirstRoll+" @bla@Roll: @red@"+theRoll);
					
					if(theRoll == 0) {
					if(Config.NPC_DROPS[npc][2] > 1 && Config.NPC_DROPS[npc][2] <= 3) //random if less than 3
						amountDropped = 1 + Misc.random(Config.NPC_DROPS[npc][2]);
					if(Config.NPC_DROPS[npc][2] > 1 && Config.NPC_DROPS[npc][2] <= 10) // 60% always 40% random
						amountDropped =  (int)((Misc.random((int) (Config.NPC_DROPS[npc][2] * .4)) + ((int) (Config.NPC_DROPS[npc][2] * .6))));
					if(Config.NPC_DROPS[npc][2] > 10)
						amountDropped = ((Misc.random((int) (Config.NPC_DROPS[npc][2] * .4)) + ((int) (Config.NPC_DROPS[npc][2] * .6))));

					newDrop = amountDropped;
					
						if(Config.NPC_DROPS[npc][2] == 1){
							amountDropped = 1;
							if(npcs[i].npcType == 3847)
							{
								for(int j = 0; j < 3; j++)
								{
									int choice = Misc.random(3);
									if(dropCounter >= 3)
										continue;
									Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], trollQueenItemDropCoords[choice][0], trollQueenItemDropCoords[choice][1], Config.NPC_DROPS[npc][2], c.playerId);
									dropCounter++;	
								}
							
							}
							else
							{
								Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, Config.NPC_DROPS[npc][2], c.playerId);

							}
							
							if (c.clanId >= 0) {
								if(Server.clanChat.clans[c.clanId].lootshare) {
									Server.clanChat.handleLootShare(c, Config.NPC_DROPS[npc][1], 1);
								}
							}
							for (int j = 0; j < Server.playerHandler.players.length; j++) {
								if (Server.playerHandler.players[j] != null) {
									Client c2 = (Client)Server.playerHandler.players[j];
									if(c2.announceOn == 0){
										if(c.getShops().getItemShopValue(Config.NPC_DROPS[npc][1]) > 20000000) {
											c2.sendMessage("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just received a "+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+" drop!");
											String dropText = c.playerName + " got a rare drop: "+c.getItems().getItemName(Config.NPC_DROPS[npc][1])+"!";
											c.getPA().writeDropLine(dropText);
										}
									}
								}
							}
							} else if(Config.NPC_DROPS[npc][2] > 1) {
							if (c.clanId >= 0) {
								if(Server.clanChat.clans[c.clanId].lootshare) {
									playersForDrop = 0;
									for (int j = 0; j < Server.clanChat.clans[c.clanId].members.length; j++) {//counting players for drop
											if (Server.clanChat.clans[c.clanId].members[j] <= 0)
												continue;
											if (Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]] != null) {
												Client cc = (Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]];
												if(c.goodDistance(c.getX(), c.getY(), cc.getX(), cc.getY(), 20)) {
													playersForDrop++;
													//c.sendMessage("playersForDrop:"+playersForDrop);
												}
											}
										}
									myDrop = (int) Math.ceil(newDrop / playersForDrop);
									for (int j = 0; j < Server.clanChat.clans[c.clanId].members.length; j++) {
											if (Server.clanChat.clans[c.clanId].members[j] <= 0)
												continue;
											if (Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]] != null) {
												Client cc = (Client)Server.playerHandler.players[Server.clanChat.clans[c.clanId].members[j]];
												if(c.goodDistance(c.getX(), c.getY(), cc.getX(), cc.getY(), 20)) {
												if(myDrop > newDrop)
													myDrop = newDrop;
												Server.itemHandler.createGroundItem(cc, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, myDrop, cc.playerId);
												Server.clanChat.handleLootShare(cc, Config.NPC_DROPS[npc][1], myDrop);
												newDrop -= myDrop;
												}
											}
										}
									} else {
										if(npcs[i].npcType == 3847)
										{
											int choice = Misc.random(3);
											Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], trollQueenItemDropCoords[choice][0], trollQueenItemDropCoords[choice][1], Config.NPC_DROPS[npc][2], c.playerId);
										}
										else
										{
											Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, Config.NPC_DROPS[npc][2], c.playerId);

										}
										
								}
								} else {
									if(npcs[i].npcType == 3847)
									{
										int choice = Misc.random(3);
										Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], trollQueenItemDropCoords[choice][0], trollQueenItemDropCoords[choice][1], Config.NPC_DROPS[npc][2], c.playerId);
									}
									else
									{
										Server.itemHandler.createGroundItem(c, Config.NPC_DROPS[npc][1], npcs[i].absX, npcs[i].absY, Config.NPC_DROPS[npc][2], c.playerId);

									}
							}
							
						}
						/*if (c.clanId >= 0) {
							if(Server.clanChat.clans[c.clanId].lootshare) {
							Server.clanChat.handleLootShare(c, Config.NPC_DROPS[npc][1], amountDropped);
							}
						}*/
					}
					if(Config.NPC_DROPS[npc][3] > 20 && theRoll == 0)
						break;
				}
			}
			dropCounter = 0;
			if(npcs[i].npcType == 3847)
			{
				c.sendMessage("@red@As you kill the queen, the items dropped wash up to the shore.");
			}
		}
	}
		public void appendJailKc(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			int[] Jail = {
				132
			};
			for (int j : Jail) {
				if (npcs[i].npcType == j) {
					c.monkeyk0ed++;
					c.sendMessage("You now have "+c.monkeyk0ed+" Monkey kills!");

				}
			}
		}	
	}
	public void appendKillCount(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			int[] kcMonsters = {6267,6268,6269,6270,6271,6272,6273,6274};
			for (int j : kcMonsters) {
				if (npcs[i].npcType == j) {
					if (c.killCount < 19) {
						c.killCount++;
					//	c.sendMessage("Killcount: " + c.killCount);
						c.updateCount();
					} else {
					if (c.killCount == 20) {
				c.getDH().sendOption4("Armadyl", "Zamorak", "Saradomin", "Bandos");
				c.dialogueAction = 20;
				c.killCount = 20;
			} else {
c.killCount = 20;
c.getDH().sendOption4("Armadyl", "Zamorak", "Saradomin", "Bandos");
				c.dialogueAction = 20;
				c.updateCount();
				}
					}
					break;
				}
			}
		}	
	}
	
	
	//id of bones dropped by npcs
	public int boneDrop(int type) {
		switch (type) {
			case 1://normal bones
			case 9:
			//case 19:
			case 100:
			case 12:
			case 17:
			case 803:
			case 18:
			case 81:
			case 101:
			case 41:
			case 19:
			case 459:
			case 75:
			case 86:
			case 78:
			case 912:
			case 913:
			case 914:
			case 1648:
			case 1643:
			case 1618:
			case 1624:
			case 181:
			case 119:
			case 6218:
			case 26:
			case 1341:
			return 526;
			case 117:
			return 532;//big bones
			case 50://drags
			case 53:
			case 54:
			case 55:
			case 941:
			case 1590:
			case 1591:
			case 1592:
			return 536;
			case 84:
			case 1615:
			case 1613:
			case 82:
			case 3200:
			return 592;
			case 2881:
			case 2882:
			case 2883:
			return 6729;
			default:
			return -1;
		}	
	}

	public int getStackedDropAmount(int itemId, int npcId) {
		switch (itemId) {
			case 995:
				switch (npcId) {
					case 1:
					return 50+ Misc.random(50);
					case 9:
					return 133 + Misc.random(100);
					case 1624:
					return 1000 + Misc.random(300);
					case 1618:
					return 1000 + Misc.random(300);
					case 1643:
					return 1000 + Misc.random(300);
					case 1610:
					return 1000 + Misc.random(1000);
					case 1613:
					return 1500 + Misc.random(1250);
					case 1615:
					return 3000;
					case 18:
					return 500;
					case 101:
					return 60;
					case 913:
					case 912:
					case 914:
					return 750 + Misc.random(500);
					case 1612:
					return 250 + Misc.random(500);
					case 1648:
					return 250 + Misc.random(250);
					case 459:
					return 200;
					case 82:
					return 1000 + Misc.random(455);
					case 52:
					return 400 + Misc.random(200);
					case 6218:
					return 1500 + Misc.random(2000);
					case 1341:
					return 1500 + Misc.random(500);
					case 26:
					return 500 + Misc.random(100);
					case 20:
					return 750 + Misc.random(100);
					case 21: 
					return 890 + Misc.random(125);
					case 117:
					return 500 + Misc.random(250);
					case 2607:
					return 500 + Misc.random(350);
				}			
			break;
			case 11212:
			return 10 + Misc.random(4);
			case 565:
			case 561:
			return 10;
			case 560:
			case 563:
			case 562:
			return 15;
			case 555:
			case 554:
			case 556:
			case 557:
			return 20;
			case 892:
			return 40;
			case 886:
			return 100;
			case 6522:
			return 6 + Misc.random(5);
			
		}
	
		return 1;
	}
	
	/**
	* Slayer Experience
	**/	


	public void appendSlayerExperience(int i) {
		
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			int points = 0;
		if(c.slayerTask > 0 && c.taskAmount > 0) {
			if(c.slayerTask == npcs[i].npcType || (c.slayerTask == 2881 && (npcs[i].npcType == 2882 || npcs[i].npcType == 2883)) || (c.slayerTask == 105 && (npcs[i].npcType == 106 || npcs[i].npcType == 1326)) || (c.slayerTask == 1648 && (npcs[i].npcType == 1653)) || (c.slayerTask == 54 && (npcs[i].npcType == 50)) || (c.slayerTask == 2455 && (npcs[i].npcType == 2456 || npcs[i].npcType == 2881 || npcs[i].npcType == 2882 || npcs[i].npcType == 2883 || npcs[i].npcType == 1351))) {
				if(c.slayerTask != 221 && c.slayerTask != 3066 && c.slayerTask != 2881 && c.slayerTask != 3200 && c.slayerTask != 50 && c.slayerTask != 1351)
					c.getPA().addSkillXP(npcs[i].MaxHP * 20, 18);
					else if(c.slayerTask == 221 || c.slayerTask == 3066 || (c.slayerTask == 2881 && (npcs[i].npcType == 2882 || npcs[i].npcType == 2883)) || c.slayerTask == 3200 || c.slayerTask == 50 || c.slayerTask == 1351)
					c.getPA().addSkillXP((int)(npcs[i].MaxHP * 30), 18);
			c.taskAmount--;
			if(c.taskAmount == 10) c.sendMessage("You have 10 "+c.getTaskName()+" left to kill.");
			if(c.taskAmount == 5) c.sendMessage("You have 5 "+c.getTaskName()+" left to kill.");
			if(c.taskAmount == 0) {
				if(getNpcListCombat(npcs[i].npcType) < 70)
					points = 1;
				if(getNpcListCombat(npcs[i].npcType) >= 70 && getNpcListCombat(npcs[i].npcType) < 100)
					points = 2;
				if(getNpcListCombat(npcs[i].npcType) >= 100)
					points = 3;
				if(c.slayerTask == 221 || c.slayerTask == 3066 || c.slayerTask == 2881 || c.slayerTask == 3200 || c.slayerTask == 50 || c.slayerTask == 1351)
					points = 5;
				c.taskPoints += points;
				c.totalTaskPoints += points;
				if(points == 1)
				c.sendMessage("@blu@You have completed your Slayer assignment and earn "+points+" Slayer point. You have @blu@"+c.taskPoints+" total points.");
				if(points > 1 && points < 5)
				c.sendMessage("@blu@You have completed your Slayer assignment and earn "+points+" Slayer points. You have @blu@"+c.taskPoints+" total points.");
				if(points >= 5)
				c.sendMessage("@blu@You have completed your Boss assignment and earn "+points+" Slayer points. You have @blu@"+c.taskPoints+" total points.");
			} else if(c.taskAmount == -3 || c.taskAmount == -5 || c.taskAmount == -10 || c.taskAmount == -15) {
				c.sendMessage("You have already completed your assignment. Return to a Slayer master for a new one.");
			}
			return;
			}
		}
			c.getPA().addSkillXP(npcs[i].MaxHP * 21, 18);//normal slayer xp
		}
	}
	
	public void appendSlayerOther(int i) { //gives boss task to other player.
	Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			int points = 0;
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
					if (goodDistance(npcs[i].getX(), npcs[i].getY(), c2.absX, c2.absY, 10) && c.playerId != c2.playerId) {
							if(c2.slayerTask == npcs[i].npcType || (c2.slayerTask == 2881 && (npcs[i].npcType == 2882 || npcs[i].npcType == 2883))){
								c2.taskAmount--;
							if(c2.taskAmount == 10) c2.sendMessage("You have 10 "+c2.getTaskName()+" left to kill.");
							if(c2.taskAmount == 5) c2.sendMessage("You have 5 "+c2.getTaskName()+" left to kill.");
							if(c2.taskAmount == 0) {
								if(getNpcListCombat(npcs[i].npcType) < 70)
									points = 1;
								if(getNpcListCombat(npcs[i].npcType) >= 70 && getNpcListCombat(npcs[i].npcType) < 100)
									points = 2;
								if(getNpcListCombat(npcs[i].npcType) >= 100)
									points = 3;
								if(c2.slayerTask == 221 || c2.slayerTask == 3066 || c2.slayerTask == 2881 || c2.slayerTask == 3200 || c2.slayerTask == 50 || c2.slayerTask == 1351)
									points = 5;
								c2.taskPoints += points;
								c2.totalTaskPoints += points;
								if(points == 1)
								c2.sendMessage("@blu@You have completed your Slayer assignment and earn "+points+" Slayer point. You have @blu@"+c2.taskPoints+" total points.");
								if(points > 1 && points < 5)
								c2.sendMessage("@blu@You have completed your Slayer assignment and earn "+points+" Slayer points. You have @blu@"+c2.taskPoints+" total points.");
								if(points >= 5)
								c2.sendMessage("@blu@You have completed your Boss assignment and earn "+points+" Slayer points. You have @blu@"+c2.taskPoints+" total points.");
							} else if(c2.taskAmount < 0) {
								c2.sendMessage("You have already completed your assignment. Return to a Slayer master for a new one.");
							}
						}
					}
				}
			}
		}
	}

							public void appendSpecial(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
		if(npcs[i].MaxHP >= 50) {
			if(c.specAmount < 10)
				c.sendMessage("Your special attack is increased by "+npcs[i].MaxHP / 10+"%");
				c.specAmount += (npcs[i].MaxHP / 100);
			} else {
				c.specAmount += .5;
				}
		
					if (c.specAmount > 10){
						c.specAmount = 10;
						if(Misc.random(5) == 0)
							c.sendMessage("You gain no special attack because your special attack is full.");
						}
						
		}
	}
	public void appendPotential(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			if(c.inWild()){
				if (c.potential >= c.maxPotential()){
					if(c.potential != c.maxPotential())
						c.sendMessage("@red@You have reached maximum potential and cannot go any higher.");
					c.potential = c.maxPotential();
				} else {
					c.potential += (int)((npcs[i].MaxHP / 20) + 2);
					if(Misc.random(2) == 0)
						c.sendMessage("Your potential has increased to @red@"+c.potential+"%@bla@/@blu@"+c.maxPotential()+"%@bla@ total. Type @blu@::potential @bla@for more info.");
				}
			}
			c.questTab();
		}
	}

		
	

	
	/**
	 *	Resets players in combat
	 */
	
	public void resetPlayersInCombat(int i) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null)
				if (Server.playerHandler.players[j].underAttackBy2 == i)
					Server.playerHandler.players[j].underAttackBy2 = 0;
		}
	}
	
	
	/**
	* Npc Follow Player
	**/
	
	public int GetMove(int Place1,int Place2) { 
		if ((Place1 - Place2) == 0) {
            return 0;
		} else if ((Place1 - Place2) < 0) {
			return 1;
		} else if ((Place1 - Place2) > 0) {
			return -1;
		}
        	return 0;
   	 }
	
	public boolean followPlayer(int i) {
		switch (npcs[i].npcType) {
			case 2554:
			case 2892:
			case 2894:
			case 2745:
			case 3847:
			case 3340:
			return false;
		}
		return true;
	}
	
	public void followPlayer(int i, int playerId) {

		if (Server.playerHandler.players[playerId] == null) {
			return;
		}

		Client c = (Client)Server.playerHandler.players[playerId];

		if (Server.playerHandler.players[playerId].respawnTimer > 0) {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true; 
	      	npcs[i].underAttack = false;	
			return;
		}
		
		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}

		int playerX = Server.playerHandler.players[playerId].absX;
		int playerY = Server.playerHandler.players[playerId].absY;
		int originalMoveX = npcs[i].moveX;
		int originalMoveY = npcs[i].moveY;
		int o = 0;
		npcs[i].randomWalk = false;

		//if(npcs[i].npcType != 50 && npcs[i].npcType != 3340 && npcs[i].npcType != 3200 && npcs[i].npcType != 3066 && npcs[i].npcType != 3847 && npcs[i].npcType != 2881 && npcs[i].npcType != 2882 && npcs[i].npcType != 2883)			
			if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY, distanceRequired(i)))
				return;

		int npcY = npcs[i].absY;
		int npcX = npcs[i].absX;
		if((npcs[i].spawnedBy > 0) || ((npcs[i].absX < npcs[i].makeX + Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absX > npcs[i].makeX - Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY < npcs[i].makeY + Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY - Config.NPC_FOLLOW_DISTANCE))) {
			if(npcs[i].heightLevel == Server.playerHandler.players[playerId].heightLevel) {
				if(Server.playerHandler.players[playerId] != null && npcs[i] != null) {
					if(playerY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerX > npcs[i].absX)  {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerX == npcs[i].absX || playerY == npcs[i].absY) {
						o = Misc.random(3);
						switch(o) {
							case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY+1);
							break;
							
							case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY-1);
							break;
							
							case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX+1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
							
							case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX-1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
						}	
					}
					int x = (npcs[i].absX + npcs[i].moveX);
					int y = (npcs[i].absY + npcs[i].moveY);
					
					if(npcs[i].npcType == 77 || npcs[i].npcType == 1575 || npcs[i].npcType == 1973 || npcs[i].npcType == 1459 || npcs[i].npcType == 3843 || npcs[i].npcType == 3842 || npcs[i].npcType == 3841){
						for(int j = 0; j < npcs.length; j++){
							if(j != i && npcs[j] != null){
								if(x == npcs[j].absX && y == npcs[j].absY){
									npcs[i].moveX = originalMoveX;
									npcs[i].moveY = originalMoveY;
									j = npcs.length;
								}
							}
						}
					}
					
					npcs[i].facePlayer(playerId);

					handleClipping(i);
					npcs[i].getNextNPCMovement(i);

					npcs[i].facePlayer(playerId);
			      	npcs[i].updateRequired = true;

				}	
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true; 
		   	npcs[i].underAttack = false;	
		}
	}
	
	public void handleClipping(int i) {
		NPC npc = npcs[i];
			if(npc.moveX == 1 && npc.moveY == 1) {
					if((Region.getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
								npc.moveY = 1;
							else 
								npc.moveX = 1; 				
							}
					} else if(npc.moveX == -1 && npc.moveY == -1) {
						if((Region.getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
								npc.moveY = -1;
							else
								npc.moveX = -1; 		
					}
					} else if(npc.moveX == 1 && npc.moveY == -1) {
							if((Region.getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
								npc.moveY = -1;
							else
								npc.moveX = 1; 
							}
					} else if(npc.moveX == -1 && npc.moveY == 1) {
						if((Region.getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
								npc.moveY = 1;
							else
								npc.moveX = -1; 
										}
					} //Checking Diagonal movement. 
					
			if (npc.moveY == -1 ) {
				if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0)
                    npc.moveY = 0;
			} else if( npc.moveY == 1) {
				if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0)
					npc.moveY = 0;
			} //Checking Y movement.
			if(npc.moveX == 1) {
				if((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0) 
					npc.moveX = 0;
				} else if(npc.moveX == -1) {
				 if((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0)
					npc.moveX = 0;
			} //Checking X movement.
	}
	
	
	public boolean checkClipping(int i) {
		NPC npc = npcs[i];
		int size = npcSize(i);
		
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (!VirtualWorld.I(npc.heightLevel, npc.absX + x, npc.absY + y, npc.absX + npc.moveX, npc.absY + npc.moveY, 0))
					return false;				
			}
		}
		return true;
	}
	
	/**
	* load spell
	**/
	public void loadSpell2(int i) {
		npcs[i].attackType = 3;
		int random = Misc.random(3);
		if (random == 0) {
			npcs[i].projectileId = 393; //red
			npcs[i].endGfx = 430;
		} else if (random == 1) {
			npcs[i].projectileId = 394; //green
			npcs[i].endGfx = 429;
		} else if (random == 2) {
			npcs[i].projectileId = 395; //white
			npcs[i].endGfx = 431;
		} else if (random == 3) {
			npcs[i].projectileId = 396; //blue
			npcs[i].endGfx = 428;
		}
	}
	
	public void loadSpell(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killerId];
		switch(npcs[i].npcType) {
		case 2554://k'ril kril
				npcs[i].attackType = 2;
			if(c == null)
				return;
			int r23 = 0;
				r23= Misc.random(2);
			if (r23 <= 1 && c.goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)) {
					npcs[i].attackTimer = 4;
					npcs[i].hitDelayTimer = 2;
					npcs[i].attackType = 0;
					npcs[i].endGfx = -1;
					npcs[i].projectileId = -1;
				} else {
					npcs[i].hitDelayTimer = 6;
					npcs[i].attackType = 2;
					npcs[i].endGfx = 157;
					npcs[i].projectileId = 448;
			}		
		break;
		case 2556:
			npcs[i].attackType = 1;
			npcs[i].endGfx = 1211;
			npcs[i].projectileId = 288;
		break;
		case 2557:
			npcs[i].attackType = 2;
			npcs[i].gfx100(155);
			npcs[i].projectileId = 156;
			npcs[i].endGfx = 157;
		break;
		case 3068:
		case 3069:
		case 3070:
		case 3071:
			int r14 = Misc.random(10);
			npcs[i].endGfx = -1;
			if(c != null) {
				if(r14 > 7){
						npcs[i].projectileId = -1;
						npcs[i].gfx0(501);
						npcs[i].attackType = 2;
						npcs[i].hitDelayTimer = 4;
						if(c.freezeTimer <= -5) {
							npcs[i].endGfx0 = 367;
							c.freezeTimer = 30;
						}
					} else if(r14 > 3 && c.goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 1)) {
						npcs[i].attackType = 0;
						npcs[i].endGfx = 395;
						npcs[i].projectileId = -1;
					} else {
						npcs[i].projectileId = 258;
						npcs[i].endGfx = 441;
						npcs[i].hitDelayTimer = 4;
						npcs[i].attackType = 1;
				}
			}
			break;
		case 2503:
				int r6 = 0;
				r6 = Misc.random(0);
				if(r6 == 0)
				{
					npcs[i].attackType = 2;
					npcs[i].endGfx = 157;
					npcs[i].projectileId = 451;
				}
		break;
		case 2501:
				int r8 = 0;
				r8 = Misc.random(0);
				if(r8 == 0)
				{
					npcs[i].attackType = 1;
					npcs[i].projectileId = 298;
				}
		break;
		case 912:
			npcs[i].attackType = 2;
			npcs[i].endGfx0 = 78;
		break;
		case 913:
			npcs[i].attackType = 2;
			npcs[i].endGfx = 76;
		break;
		case 914:
			npcs[i].attackType = 2;
			npcs[i].endGfx = 77;
		break;
		case 3340:
			int r12 = 0;
			r12= Misc.random(3);
			if (r12 == 0) {
			npcs[i].attackType = 2;
			npcs[i].projectileId = 394; //green
			npcs[i].endGfx = 429;
			} else {
			npcs[i].attackType = 0;
			}
		break;
		case 277:
			int r5 = 0;
				r5= Misc.random(1);
			if (r5 == 0) {
				npcs[i].attackType = 2;
				npcs[i].endGfx = 157;
				npcs[i].projectileId = 448;
			} else if (r5 == 1) {
				npcs[i].attackType = 0;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = -1;
			}		
		break;
		case 2892:
			npcs[i].projectileId = 94;
			npcs[i].attackType = 2;
			npcs[i].endGfx = 95;
		break;
		case 2894:
			npcs[i].projectileId = 298;
			npcs[i].attackType = 1;
		break;

		case 54:
			case 53:
			case 55:
			case 941:
			case 1590:
			case 1591:
			case 1592:
			int random2 = Misc.random(3);
			if (random2 == 1){
				npcs[i].projectileId = 393; //red
				npcs[i].endGfx = 430;
				npcs[i].attackType = 3;
			} else {
				npcs[i].projectileId = -1; //melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;	
			}
		break;
		case 50:
			int random = Misc.random(4);
			if (random == 0) {
				npcs[i].projectileId = 393; //red
				npcs[i].endGfx = 430;
				npcs[i].attackType = 2;
			} else if (random == 1) {
				npcs[i].projectileId = 394; //green
				npcs[i].endGfx = 429;
				npcs[i].attackType = 2;
			} else if (random == 2) {
				npcs[i].projectileId = 395; //white
				npcs[i].endGfx = 431;
				npcs[i].attackType = 2;
			} else if (random == 3) {
				npcs[i].projectileId = 396; //blue
				npcs[i].endGfx = 428;
				npcs[i].attackType = 2;
			} else if (random == 4) {
				npcs[i].projectileId = -1; //melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;				
			}			
			break;
			//arma npcs
			case 2561:
				npcs[i].attackType = 0;
			break;
			case 2560:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1190;
			break;
			case 2559:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1203;
			break;

			case 6222:
				random = Misc.random(1);
				npcs[i].attackType = 1 + random;
				if (npcs[i].attackType == 1) {
					npcs[i].projectileId = 1197;				
				} else {
					npcs[i].attackType = 2;
					npcs[i].projectileId = 1198;
				}	
			break;
			//sara npcs
			case 2562: //sara
				random = Misc.random(1);
				if (random == 0) {
					npcs[i].attackType = 2;
					npcs[i].endGfx = 1224;
					npcs[i].projectileId = -1;
				} else if (random == 1)
					npcs[i].attackType = 0;
			break;
			case 2563: //star
				npcs[i].attackType = 0;
			break;
			case 2564: //growler
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1203;
			break;
			case 2565: //bree
				npcs[i].attackType = 1;
				npcs[i].projectileId = 9;
			break;
			//bandos npcs
			case 2550:
				random = Misc.random(2);
				if (random == 0 || random == 1)
					npcs[i].attackType = 0;
				else {
					npcs[i].attackType = 1;
					npcs[i].endGfx = 1211;
					npcs[i].projectileId = 288;
				}
			break;
			case 2551:
				npcs[i].attackType = 0;
			break;
			case 2552:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1203;
			break;
			case 2553:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1206;
			break;

			case 1643://infernal mage
			npcs[i].attackType = 2;
			npcs[i].gfx100(155);
			npcs[i].projectileId = 156;
			npcs[i].endGfx = 157;
			break;
			
			case 2025:
			npcs[i].attackType = 2;
			int r = Misc.random(3);
			if(r == 0) {
				npcs[i].gfx100(158);
				npcs[i].projectileId = 159;
				npcs[i].endGfx = 160;
			}
			if(r == 1) {
				npcs[i].gfx100(161);
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
			}
			if(r == 2) {
				npcs[i].gfx100(164);
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
			}
			if(r == 3) {
				npcs[i].gfx100(155);
				npcs[i].projectileId = 156;
				npcs[i].endGfx = 157;
			}
			break;
			
			case 2881://supreme
				npcs[i].attackType = 1;
				npcs[i].projectileId = 298;
			break;
			
			case 2882://prime
				npcs[i].attackType = 2;
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 477;
			break;
			
			case 2455://prime
				npcs[i].attackType = 1;
				npcs[i].projectileId = 298;
			break;
			
			case 2028:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 27;
			break;
			
			case 3200:
			int r7 = 0;
				r7 = Misc.random(1)+1;
				if(r7 == 1) {
				npcs[i].attackType = 1;
				npcs[i].gfx100(550);
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
				} else if (r7 == 2) {
				npcs[i].attackType = 2;
				npcs[i].gfx100(553);
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
				}
			break;
			case 3066:
			int r4 = 0;
				r4= Misc.random(1);
			if (r4 == 1) {
				npcs[i].attackType = 2;
				npcs[i].endGfx = 157;
				npcs[i].projectileId = 451;
			} else {
				npcs[i].attackType = 1;
				//npcs[i].endGfx = -1;
				npcs[i].projectileId = 298;
			}	
			break;
			case 3842:
				npcs[i].attackType = 2;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = 298;
				break;
			case 3841:
				npcs[i].attackType = 1;
				npcs[i].endGfx = 163;
				npcs[i].projectileId = 162;
				break;
			case 3847:
				int r9 = 0;
				r9 = Misc.random(1);
				if (r9 == 1) {
					npcs[i].attackType = 1;
					npcs[i].endGfx = -1;
					npcs[i].projectileId = 298;
					int poisonChance = Misc.random(5);
					if(poisonChance == 0)
					{
						c.getPA().appendPoison(6);
					}
				} else {
					npcs[i].attackType = 2;
					npcs[i].endGfx = 163;
					npcs[i].projectileId = 162;
				}	
			break;
			case 1351://dag mother
			if (dagColor2 == 2) {
				npcs[i].attackType = 1;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = 294;
			} else if (dagColor2 == 1) {
				npcs[i].attackType = 2;
				npcs[i].gfx100(161);
				npcs[i].endGfx = 163;
				npcs[i].projectileId = 162;
			} else if (dagColor2 == 0) {
				npcs[i].attackType = 0;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = -1;
			}	
			break;
			
			case 2745:
			int r3 = 0;
				r3= Misc.random(1);
			if (r3 == 0) {
				npcs[i].hitDelayTimer = 6;
				npcs[i].attackType = 2;
				npcs[i].endGfx = 157;
				npcs[i].endGfx0 = -1;
				npcs[i].projectileId = 448;
			} else if (r3 == 1) {
				npcs[i].hitDelayTimer = 7;
				npcs[i].attackType = 1;
				npcs[i].endGfx = 451;
				//npcs[i].endGfx0 = 451;
				npcs[i].projectileId = -1;
			}		
			break;
			case 2743:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 445;
				npcs[i].endGfx = 446;
			break;
			
			case 2631:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 443;
			break;
		}
	}
		
	/**
	* Distanced required to attack
	**/	
	public int distanceRequired(int i) {
		switch(npcs[i].npcType) {
			case 2554:
				return 12;
			case 3842:
			case 3841:
				return 5;
			case 3068:
			case 3069:
			case 3070:
			case 3071:
				return 5;
			case 3847:
				return 8;
			case 2501:
			case 2503:
				return 9;
			case 126:
				return 1;
			case 13:
				return 5;
			case 172:
			case 174:
				return 6;
			case 2025:
			case 2028:
			case 1643:
			return 6;
			case 2455:
			case 50:
				if(npcs[i].attackType == 2)
					return 8;
				else
					return 2;
			case 2562:
			return 2;
			case 2881://dag kings
			case 2882:
			case 3200://chaos ele
			case 2743:
			case 2631:

			case 2745:
			case 3340:
			case 1351:
			return 8;
			case 2883://rex
			return 2;

			case 2552:
			case 2553:
			case 2556:
			case 2557:
			case 6223:
			case 6225:
			case 6227:
			case 6222:
			case 2559:
			case 2560:
			case 2564:
			case 2565:
			return 9;
			//things around dags
			case 2892:
			case 2894:
			return 10;
			case 3066:
			return 15;
			/*case 1351:
			if(npcs[i].attackType != 0){
			return 8;
			} else {
			return 2;
			}*/
			default:
			return 1;
		}
	}
	
	public int followDistance(int i) {
		switch (npcs[i].npcType) {
			case 2025:
			case 2026:
			case 2027:
			case 2028:
			case 2029:
			case 2030:
				return 5;
			case 2550:
			case 2551:
			case 2562:
			case 2563:
			return 8;
			case 2883:
			return 9;
			case 2881:
			case 2882:
			case 2455:
			case 2456:
			return 4;
			case 3340:
			return 5;
			case 3843:
			case 3842:
			case 3841:
				return 6;
		
		}
		return 0;
		
	
	}
	
	public int getProjectileSpeed(int i) {
		switch(npcs[i].npcType) {
			case 2554:
				return 155;
			case 2881:
			case 2882:
				return 85;
			case 3847:
				return 160;
			case 3200:
				return 135;
			case 1351:
			if(npcs[i].attackType == 2)
			return 150;
			else if(npcs[i].attackType == 1)
			return 100;
			else if(npcs[i].attackType == 0)
			return 100;

			case 2745:
			if(npcs[i].attackType == 2)
			return 130;
			else if(npcs[i].attackType == 1)
			return 175;
			
			case 50:
			case 2455:
			case 2456:
			return 90;
			case 1643:
			case 2025:
			return 85;
			
			case 2028:
			return 80;
			
			default:
			return 85;
		}
	}
	
	/**
	*NPC Attacking Player
	**/

	
	public void attackPlayer(Client c, int i) {
		if(npcs[i] != null) {

			if (npcs[i].isDead)
				return;

			/*if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0 && npcs[i].underAttackBy != c.playerId && npcs[i].npcType != 2455 && npcs[i].npcType != 2456) {
				npcs[i].killerId = 0;
				return;
			}*/
			if (!npcs[i].inMulti() && ((c.underAttackBy > 0 || (c.underAttackBy2 > 0 && c.underAttackBy2 != i) || (npcs[i].underAttackBy > 0 && npcs[i].underAttackBy != c.playerId)) && (npcs[i].npcType != 2455 && npcs[i].npcType != 2456 && npcs[i].npcType != 2554 && npcs[i].npcType != 2555 && npcs[i].npcType != 2556 && npcs[i].npcType != 2557))) {
				npcs[i].killerId = 0;
				return;
			}
			if (npcs[i].heightLevel != c.heightLevel) {
				npcs[i].killerId = 0;
				return;
			}
			
			int npcY = npcs[i].absY;
			int npcX = npcs[i].absX;
			int playerX = PlayerHandler.players[c.playerId].absX;
			int playerY = PlayerHandler.players[c.playerId].absY;
			
		int otherX = Server.npcHandler.npcs[i].getX();
		int otherY = Server.npcHandler.npcs[i].getY();
		
		boolean isBehindwall = false;

		if(npcs[i].npcType != 3340 && npcs[i].npcType != 2554 && npcs[i].npcType != 2555 && npcs[i].npcType != 2556 && npcs[i].npcType != 2557 && npcs[i].npcType != 50 && npcs[i].npcType != 3200 && npcs[i].npcType != 3066 && npcs[i].npcType != 3847 && npcs[i].npcType != 2881 && npcs[i].npcType != 2882 && npcs[i].npcType != 2883) {
			if(otherX < c.getX())
				if(Region.blockedWest(c.getX(), c.getY(),c.heightLevel))
					isBehindwall = true;
			
			if(otherX > c.getX())
				if(Region.blockedEast(c.getX(), c.getY(),c.heightLevel))
					isBehindwall = true;
			
			if(otherY < c.getY())
				if(Region.blockedSouth(c.getX(), c.getY(),c.heightLevel))
					isBehindwall = true;
			
			if(otherY > c.getY())
				if(Region.blockedNorth(c.getX(), c.getY(),c.heightLevel))
					isBehindwall = true;
		}
		
			if (isBehindwall)
				return;

				npcs[i].facePlayer(c.playerId);

			boolean special = false;//specialCase(c,i);
			if(goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), distanceRequired(i)) || special) {
				if(c.respawnTimer <= 0) {
					npcs[i].facePlayer(c.playerId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;
					
					if (special)
						loadSpell2(i);
					else
						loadSpell(i);
						
					if (npcs[i].attackType == 3)
						npcs[i].hitDelayTimer += 2;
					if (multiAttacks(i)) {
						multiAttackGfx(i, npcs[i].projectileId);
						startAnimation(getAttackEmote(i), i);
						npcs[i].oldIndex = c.playerId;
						return;
					}
					if(npcs[i].projectileId > 0) {
						int nX = Server.npcHandler.npcs[i].getX() + offset(i);
						int nY = Server.npcHandler.npcs[i].getY() + offset(i);
						int pX = c.getX();
						int pY = c.getY();
						int offX = (nY - pY)* -1;
						int offY = (nX - pX)* -1;
						c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50, getProjectileSpeed(i), npcs[i].projectileId, 43, 31, -c.getId() - 1, 65);
					}
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					npcs[i].oldIndex = c.playerId;
					startAnimation(getAttackEmote(i), i);
					c.getPA().removeAllWindows();
					npcs[i].updateRequired = true;
				} 
			}			
		}
	}
	
	public int offset(int i) {
		switch (npcs[i].npcType) {
			case 3068:
			case 3069:
			case 3070:
			case 3071:
				return 1;
			case 2554: // Kril
				return 2;
			case 2555: // Kril minions
			case 2556:
			case 2557:
				return 1;
			case 50:
			return 2;
			case 2881:
			case 2882:
			return 1;
			case 2745:
			case 2743:
			return 1;		
			case 3340:
			return 2;
			case 105:
			case 106:
			return 1;
			case 110:
			case 117:
			return 2;
		}
		return 0;
	}
	
	public boolean specialCase(Client c, int i) { //responsible for npcs that much 
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 8) && !goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), distanceRequired(i)))
			return true;
		return false;
	}
	
	public boolean retaliates(int npcType) {

		return npcType < 6142 || npcType > 6145 && !(npcType >= 2440 && npcType <= 2446);
	}

	public void applyDamage(int i) {
		Client c = (Client) Server.playerHandler.players[npcs[i].oldIndex];
		if(npcs[i] != null) {
			if(Server.playerHandler.players[npcs[i].oldIndex] == null) {
				return;
			}
			
			if (npcs[i].isDead)
				return;
			
			if (multiAttacks(i)) {
			
			
			//if (npcs[i].oldIndex != c.playerId)
				//if(c.playerId != npcs[i].underAttackBy)
					multiAttackDamage(i, multiAttackSize(i));
				return;
			}
			
			if (c.playerIndex <= 0 && c.npcIndex <= 0 && c.autoRet == 1 && !c.walkingToItem)
					c.npcIndex = i;
					
			if(c.attackTimer <= 3 || c.attackTimer == 0 && c.npcIndex == 0 && c.oldNpcIndex == 0) {
				c.startAnimation(c.getCombat().getBlockEmote());
			}

			if(npcs[i].npcType == 2497 || npcs[i].npcType == 191)
			{
				if(Misc.random(6) == 0)
				{
					if(c.playerEquipment[c.playerShield] == 6215 || c.playerEquipment[c.playerShield] == 6217 || c.playerEquipment[c.playerShield] == 6219 || c.playerEquipment[c.playerShield] == 6221 || c.playerEquipment[c.playerShield] == 6223 || c.playerEquipment[c.playerShield] == 6225 || c.playerEquipment[c.playerShield] == 6227 || c.playerEquipment[c.playerShield] == 6229 || c.playerEquipment[c.playerShield] == 6231 || c.playerEquipment[c.playerShield] == 6233 || c.playerEquipment[c.playerShield] == 6235)
					{
						c.sendMessage("Your shield drains the poison from you.");
					}
					else
					{
						c.getPA().appendPoison(22);
					}
				}	
			}
			
			if(npcs[i].npcType == 3847 && npcs[i].attackType == 1)
			{
				if(Misc.random(0) == 0)
				{
					if(c.playerEquipment[c.playerShield] == 6215 || c.playerEquipment[c.playerShield] == 6217 || c.playerEquipment[c.playerShield] == 6219 || c.playerEquipment[c.playerShield] == 6221 || c.playerEquipment[c.playerShield] == 6223 || c.playerEquipment[c.playerShield] == 6225 || c.playerEquipment[c.playerShield] == 6227 || c.playerEquipment[c.playerShield] == 6229 || c.playerEquipment[c.playerShield] == 6231 || c.playerEquipment[c.playerShield] == 6233 || c.playerEquipment[c.playerShield] == 6235)
					{
						c.sendMessage("Your shield drains the poison from you.");
					}
					else
					{
						c.getPA().appendPoison(24);
					}
				}
			}

			if(npcs[i].npcType == 2499) //green broodoo brother boss
			{
				if(Misc.random(5) == 0) //has 20% chance of doing poison damage
				{
					if(c.playerEquipment[c.playerShield] == 6215 || c.playerEquipment[c.playerShield] == 6217 || c.playerEquipment[c.playerShield] == 6219 || c.playerEquipment[c.playerShield] == 6221 || c.playerEquipment[c.playerShield] == 6223 || c.playerEquipment[c.playerShield] == 6225 || c.playerEquipment[c.playerShield] == 6227 || c.playerEquipment[c.playerShield] == 6229 || c.playerEquipment[c.playerShield] == 6231 || c.playerEquipment[c.playerShield] == 6233 || c.playerEquipment[c.playerShield] == 6235)
					{
						c.sendMessage("Your shield drains the poison from you.");
					} else {
						c.getPA().appendPoison(40); //does 20 damage
					}
				}
			}
			
				if(c.respawnTimer <= 0) {	
					int damage = 0;
					if(npcs[i].attackType == 0) {
						
						damage = Misc.random(npcs[i].maxHit);

						if (10 + Misc.random(c.getCombat().calculateMeleeDefence()) > Misc.random(Server.npcHandler.npcs[i].attack)) {
							damage = 0;
						}

						if(c.playerEquipment[c.playerWeapon] == 11716 && (npcs[i].npcType == 2554 || npcs[i].npcType == 2555 || npcs[i].npcType == 2556 || npcs[i].npcType == 2557))
							damage = (int) (damage * 0.75);

						if(c.prayerActive[18]) { // protect from melee
							if(npcs[i].npcType != 77) {
								damage = 0;
							}
						}				
						if (c.playerLevel[3] - damage < 0) { 
							damage = c.playerLevel[3];
						}
					}
					
					if(npcs[i].attackType == 1) { // range
						
						damage = Misc.random(npcs[i].maxHit);
						
						if (10 + Misc.random(c.getCombat().calculateRangeDefence()) > Misc.random(Server.npcHandler.npcs[i].attack)) {
							damage = 0;
						}					
						if(c.prayerActive[17]) { // protect from range
							damage = 0;
						}				
						if (c.playerLevel[3] - damage < 0) { 
							damage = c.playerLevel[3];
						}
						if(npcs[i].endGfx > 0) {
							c.gfx100(npcs[i].endGfx);
						} else if(npcs[i].endGfx0 > 0) {
							c.gfx0(npcs[i].endGfx0);
						}
					}
					
					if(npcs[i].attackType == 2) { // magic
						
						damage = Misc.random(npcs[i].maxHit);

						boolean magicFailed = false;
						if (10 + Misc.random(c.getCombat().mageDef()) > Misc.random(Server.npcHandler.npcs[i].attack)) {
							damage = 0;
							magicFailed = true;
						}
						//int damageCap = 50;

						if(c.playerEquipment[c.playerShield] == 6259 || c.playerEquipment[c.playerShield] == 6261 || 
					      c.playerEquipment[c.playerShield] == 6263 || c.playerEquipment[c.playerShield] == 6265 || 
					      c.playerEquipment[c.playerShield] == 6267 || c.playerEquipment[c.playerShield] == 6269 || 
					      c.playerEquipment[c.playerShield] == 6271 || c.playerEquipment[c.playerShield] == 6273 || 
					      c.playerEquipment[c.playerShield] == 6275 || c.playerEquipment[c.playerShield] == 6277 || 
					      c.playerEquipment[c.playerShield] == 6279)
					     {
					       int damageCap = 25;
					       if(damage > damageCap)
					     	damage = damageCap;
					     }

						 if((npcs[i].npcType == 3068 || npcs[i].npcType == 3069 || npcs[i].npcType == 3070 || npcs[i].npcType == 3071) && c.playerEquipment[c.playerShield] != 11284) {
							damage = Misc.random(60);
							c.sendMessage("You are badly burnt by the dragon's breath!");
						}

						if(c.prayerActive[16]) { // protect from magic
							damage = 0;
							if(damage == 0) {
								magicFailed = true;
							}
						}

						if((npcs[i].npcType == 3068 || npcs[i].npcType == 3069 || npcs[i].npcType == 3070 || npcs[i].npcType == 3071) && c.playerEquipment[c.playerShield] == 11284) {
							if(damage == 0)
								damage = Misc.random(14);
							magicFailed = false;
						}
										
						if (c.playerLevel[3] - damage < 0) { 
							damage = c.playerLevel[3];
						}
						if(npcs[i].endGfx > 0 && (!magicFailed || isFightCaveNpc(i))) {
							c.gfx100(npcs[i].endGfx);
						} else if(npcs[i].endGfx0 > 0 && (!magicFailed || isFightCaveNpc(i))) {
							c.gfx0(npcs[i].endGfx0);					
						} else {
							c.gfx100(85);
						}
					}
					
					if (c.playerEquipment[5] == 13742) {//Elysian Effect
							if (Misc.random(100) < 75) {
								double damages = damage;
								double damageDeduction = ((double)damages)/((double)4);
								damage -= ((int)Math.round(damageDeduction));
							}
						}
						if (c.playerEquipment[5] == 13740) {//Divine Effect
							double damages2 = damage;
							double prayer = c.playerLevel[5];
							double possibleDamageDeduction = ((double)damages2)/((double)3.33);//30% of Damage Inflicted
							double actualDamageDeduction;
							if ((prayer * 2) < possibleDamageDeduction) {
							actualDamageDeduction = (prayer * 2);//Partial Effect(Not enough prayer points)
							} else {
							actualDamageDeduction = possibleDamageDeduction;//Full effect
							}
							double prayerDeduction = ((double)actualDamageDeduction)/((double)2);//Half of the damage deducted
							damage -= ((int)Math.round(actualDamageDeduction));
							c.playerLevel[5] = c.playerLevel[5]-((int)Math.round(prayerDeduction));
							c.getPA().refreshSkill(5);
						}
					
					
					
					if (npcs[i].attackType == 3) { //fire breath
						int anti = c.getPA().antiFire();
						if(c.playerEquipment[c.playerShield] == 6237 || c.playerEquipment[c.playerShield] == 6239 || c.playerEquipment[c.playerShield] == 6241 || c.playerEquipment[c.playerShield] == 6243 || c.playerEquipment[c.playerShield] == 6245 || c.playerEquipment[c.playerShield] == 6247 || c.playerEquipment[c.playerShield] == 6249 || c.playerEquipment[c.playerShield] == 6251 || c.playerEquipment[c.playerShield] == 6253 || c.playerEquipment[c.playerShield] == 6255 || c.playerEquipment[c.playerShield] == 6257)
					      {
					       damage = 0;
					       c.sendMessage("Your shield gives you complete immunity to the dragonfire.");
					      }
					      else
					      {
					       if (anti == 0) {
					        damage = Misc.random(30) + 10;
					        c.sendMessage("You are badly burnt by the dragon fire!");
					       } else if (anti == 1)
					        damage = Misc.random(20);
					       else if (anti == 2)
					        damage = Misc.random(10);
					       if (c.playerLevel[3] - damage < 0)
					        damage = c.playerLevel[3];
					      }
						c.gfx100(npcs[i].endGfx);
					}
					handleSpecialEffects(c, i, damage);
						if (c.vengOn && damage > 0) {
						c.getCombat().appendVengeanceNPC(i, damage);
					}
					c.logoutDelay = System.currentTimeMillis(); // logout delay
					//c.setHitDiff(damage);
					c.handleHitMask(damage);
					c.playerLevel[3] -= damage;
					c.getPA().refreshSkill(3);
					c.updateRequired = true;
					//c.setHitUpdateRequired(true);	
				}
		}
	}
	
	public void handleSpecialEffects(Client c, int i, int damage) {
	
	if (npcs[i].npcType == 277 && npcs[i].attackType == 2){
		if(damage > 19){
			if(c != null) {
				if(c.getItems().playerHasItem(1929,1)){
				 c.getItems().deleteItem(1929,c.getItems().getItemSlot(1929),1);
				 c.getItems().addItem(1925,1);
				 c.sendMessage("One of your buckets of water boil and turn into steam from the heat.");
				} else {
				 damage = damage * 3;
				 c.sendMessage("@red@Ouch! You are burnt by the Fire Warrior's fist. Get some water!");
				}
			}
		}
	}
	
		if (npcs[i].npcType == 2892 || npcs[i].npcType == 2894) {
			if (damage > 0) {
				if (c != null) {
					if (c.playerLevel[5] > 0) {
						c.playerLevel[5]--;
						c.getPA().refreshSkill(5);
						c.getPA().appendPoison(12);
					}
				}			
			}	
		}
	
	}
		
		

	public void startAnimation(int animId, int i) {
		npcs[i].animNumber = animId;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
	}
	
public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
    return ((objectX-playerX <= distance && objectX-playerX >= -distance) && (objectY-playerY <= distance && objectY-playerY >= -distance));
}
	
	public int getMaxHit(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killerId];
		switch (npcs[i].npcType) {
		case 2554:
			if(npcs[i].attackType == 2)
				return 45;
			else
				return 30;
		case 2550:
			if(npcs[i].attackType == 1)
				return 45;
			else
				return 65;
		case 2551:
		case 2552:
		case 2553:
			return 25;
		case 3066: //zombie champ
			return 20;
		case 3847: //sea troll q
			if(npcs[i].attackType == 1) {
				return 40;
			}
			else if(npcs[i].attackType == 2)
			{
				return 35;
			}
		case 3068: //wyvs
		case 3069:
		case 3070:
		case 3071:
			if(npcs[i].attackType == 2 && c.playerEquipment[c.playerShield] != 11284) {
				return 60;
			} else {
				return 14;
			}
		case 110:
			return 8;
		case 117:
			return 2;
		case 1351: //dagg mother
			return 20;

		case 6222: //godwars
		case 6247:
		case 6203:
		case 6260:
			if (npcs[i].attackType == 2)
				return 28;
			else
				return 68;
			case 277:
				if (npcs[i].attackType == 2)
					return 25;
				else
					return 30;
			case 3200: //chaos ele
				return 16;
			case 50://kbd
				return 20;
			case 3340://mole
				return 25;
		case 6223:
		case 6225:
		case 6227:
			return 10;
		}
		return 1;
	}
	
	
	public boolean loadAutoSpawn(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./"+FileName));
		} catch(FileNotFoundException fileex) {
			Misc.println(FileName+": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(FileName+": error loading file.");
			return false;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("spawn")) {
					newNPC(Integer.parseInt(token3[0]), Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]), getNpcListHP(Integer.parseInt(token3[0])), Integer.parseInt(token3[5]), Integer.parseInt(token3[6]), Integer.parseInt(token3[7]));
				
				}
			} else {
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try { characterfile.close(); } catch(IOException ioexception) { }
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return false;
	}

	public int getNpcListHP(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcHealth;
				}
			}
		}
		return 0;
	}
	
	public int getNpcListCombat(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcCombat;
				}
			}
		}
		return 0;
	}
	

	public String getNpcListName(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcName;
				}
			}
		}
		return "nothing";
	}

	public boolean loadNPCList(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./"+FileName));
		} catch(FileNotFoundException fileex) {
			Misc.println(FileName+": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(FileName+": error loading file.");
			return false;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("npc")) {
					newNPCList(Integer.parseInt(token3[0]), token3[1], Integer.parseInt(token3[2]), Integer.parseInt(token3[3]));
				}
			} else {
				if (line.equals("[ENDOFNPCLIST]")) {
					try { characterfile.close(); } catch(IOException ioexception) { }
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return false;
	}
	

}
