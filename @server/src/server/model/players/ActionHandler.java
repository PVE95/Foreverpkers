package server.model.players;

import server.Config;
import server.Server;
import server.model.objects.Object;
import server.util.Misc;
import server.util.ScriptManager;

public class ActionHandler {
	
	private Client c;
	
	public ActionHandler(Client Client) {
		this.c = Client;
	}
	
	public void appendChestHit(Client c, int damage) {
		Server.playerHandler.players[c.playerId].setHitDiff(damage);
		Server.playerHandler.players[c.playerId].playerLevel[3] -= damage;
		c.getPA().refreshSkill(3);
		Server.playerHandler.players[c.playerId].setHitUpdateRequired(true);	
		Server.playerHandler.players[c.playerId].updateRequired = true;		
	}	
	
	
	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;

		if(!c.goodDistance(c.getX(), c.getY(), obX, obY, 4))//prevent cheat clients maybe?
			return;
		//c.sendMessage("Object type: " + objectType);

		switch(objectType){

		case 12045:
		case 12047:
		if(c.absX == 2469)
			c.getPA().walkTo2(1,0);
		else if(c.absX == 2470)
			c.getPA().walkTo2(-1,0);

		if(c.absY == 4434)
			c.getPA().walkTo2(0,-1);
		else if(c.absY == 4433)
			c.getPA().walkTo2(0,1);
		break;

		case 3726:
			c.getThieving().stealFromStall(995,1000 +  Misc.random(600), 240, 99);
		break;
		case 6162:
			c.getThieving().stealFromStall(c.randomGem(),1, 150, 1);
		break;
		case 4707:
			c.getThieving().stealFromStall(c.randomFish(),1, 150, 1);
		break;
		case 6164:
			c.getThieving().stealFromStall(995,5000+Misc.random(10000), 150, 1);
		break;

		case 12127:
				if(c.absX == 2408 && c.absY == 4394) {
					if(c.memberStatus >= 3)
						c.getPA().walkTo2(0,2);
					else
						c.sendMessage("You must be an Elite Donator to pass this obstacle.");
				}
				if(c.absX == 2408 && c.absY == 4396) {
					if(c.memberStatus >= 3)
						c.getPA().walkTo2(0,-2);
					else
						c.sendMessage("You must be an Elite Donator to pass this obstacle.");
				}
				if(c.absX == 2415 && c.absY == 4401) {
					if(c.memberStatus >= 3)
						c.getPA().walkTo2(0,2);
					else
						c.sendMessage("You must be an Elite Donator to pass this obstacle.");
				}
				if(c.absX == 2415 && c.absY == 4403) {
					if(c.memberStatus >= 3)
						c.getPA().walkTo2(0,-2);
					else
						c.sendMessage("You must be an Elite Donator to pass this obstacle.");
				}
				if(c.absX == 2400 && c.absY == 4402) {
					if(c.memberStatus >= 2)
						c.getPA().walkTo2(0,2);
					else
						c.sendMessage("You must be a Super Donator to pass this obstacle.");
				}
				if(c.absX == 2400 && c.absY == 4404) {
					if(c.memberStatus >= 2)
						c.getPA().walkTo2(0,-2);
					else
						c.sendMessage("You must be a Super Donator to pass this obstacle.");
				}
		break;
		
		case 2513://target at edge
			if(c.gameMode == 2) {
				if (System.currentTimeMillis() - c.dummyTimer >= 2000) {
					if (c.playerEquipment[c.playerWeapon] == 841) {
						c.getPA().addSkillXP(30, 4);//range
						c.getPA().addSkillXP(15, 3);//hp
						c.startAnimation(426);
						c.sendMessage("You hit the target!");
						c.dummyTimer = System.currentTimeMillis();
					} else {
						c.sendMessage("You must have a normal shortbow equipped to train on this target!");
					}
				}
			} else {
				c.sendMessage("Only trained accounts can hit the targets!");
			}
		break;
		
		case 823://dummy at edge
		if(c.gameMode == 2) {
			if (System.currentTimeMillis() - c.dummyTimer >= 2000) {
				if (c.playerEquipment[c.playerWeapon] == -1) {
					c.sendMessage("You hit the dummy.");
					if(c.fightMode == 0) {//atk
						if(c.playerLevel[0] <= 24) {
							c.getPA().addSkillXP(30, 0);
						} else {
							c.sendMessage("You've practiced enough of this skill, time to change it up!");
						}
					}
					if(c.fightMode == 2) {//str
						if(c.playerLevel[2] <= 24) {
							c.getPA().addSkillXP(30, 2);
						} else {
							c.sendMessage("You've practiced enough of this skill, time to change it up!");
						}
					}
					if(c.fightMode == 1) {//def
						if(c.playerLevel[1] <= 24) {
							c.getPA().addSkillXP(30, 1);
						} else {
							c.sendMessage("You've practiced enough of this skill, time to change it up!");
						}
					}


					c.getPA().addSkillXP(15, 3);//hp
					c.startAnimation(422);
					c.dummyTimer = System.currentTimeMillis();
				} else if (c.playerEquipment[c.playerWeapon] != -1) {
					c.sendMessage("You can't use weapons on a dummy!");
				}
			}
		} else {
			c.sendMessage("Only trained accounts can hit the dummies!");
		}
		break;
		case 2646://flax
			if((System.currentTimeMillis() - c.bananaDelay >= 3000) && c.getItems().freeSlots() >= 1){
				c.bananaDelay = System.currentTimeMillis();
				c.getItems().addItem(1779, 1);
				c.sendMessage("You pick up some flax");
				c.startAnimation(832);
				c.getPA().addSkillXP(Config.FARMING_EXPERIENCE*5, 19);
			}
		break;
		case 4309: //spinning wheel
			if((System.currentTimeMillis() - c.bananaDelay >= 3000)){
				if(c.getItems().playerHasItem(1779,1)) {
					c.bananaDelay = System.currentTimeMillis();
					c.getItems().addItem(1777, 1);
					c.getItems().deleteItem(1779, 1);
					c.sendMessage("You make a bow string out of flax");
					c.startAnimation(832);
					c.getPA().addSkillXP(Config.CRAFTING_EXPERIENCE*5, 12);
				} else {
					c.sendMessage("You need flax to use the spinning wheel!");
				}
			}
		break;
		case 13291://magic chest
			/*if (c.getItems().playerHasItem(4067,20) && c.getItems().freeSlots() >= 3){
					c.getDH().sendDialogues(9041, 0);
			} else */
			if (c.getItems().playerHasItem(1547,1) && c.getItems().freeSlots() >= 3){
				c.getItems().deleteItem(1547,c.getItems().getItemSlot(1547), 1);
				int theItem = 0;
				if(Misc.random(100) == 0)
					theItem = 22494;
				else
					theItem = c.randomMagicChest();
				c.getItems().addItem(theItem, 1);
				c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has received a @dre@"+c.getItems().getItemName(theItem)+"@red@ from a Magic chest key.");
				c.sendMessage("@blu@You discover the treasure inside the chest.");
			} else if (c.getItems().playerHasItem(85,1) && c.getItems().freeSlots() >= 1){
				c.getItems().deleteItem(85,c.getItems().getItemSlot(85), 1);
				//c.getItems().addItem(7937, Misc.random(20) + 10);
				c.getItems().addItem(995, Misc.random(5000000) + 5000000);
				int theItem = 0;

				if(Misc.random(100) <= 1){
					theItem = c.randomChest();
				} else {
					theItem = c.randomBadChest();
				}
				c.getItems().addItem(theItem, 1);
				c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has received a @dre@"+c.getItems().getItemName(theItem)+"@red@ from a Shiny key.");
				c.sendMessage("@blu@You discover the treasure inside the chest.");

				} else if (c.getItems().freeSlots() > 2) {
					c.sendMessage("@blu@You must have a Shiny or Magic key to open this chest.");
				} else {
					c.sendMessage("You need at least 3 free inventory spaces.");
				}
		break;
		case 4126:
		if(obX == 2962 && obY == 3377){
			if (c.getItems().playerHasItem(989,1) && c.getItems().freeSlots() >= 4){
					c.getItems().deleteItem(989,c.getItems().getItemSlot(989), 1);
					c.getItems().addItem(6571, Misc.random(2) + 1);
					c.getItems().addItem(995, 250000);
					if(Misc.random(1) == 1){
						c.getItems().addItem(995, 250000);
						if(Misc.random(2) == 1){
							c.getItems().addItem(995, 500000);
							if(Misc.random(3) == 1){
								c.getItems().addItem(995, 1500000);
							}
						}
					}
					c.sendMessage("@blu@You discover the treasure in the chest.");
				} else {
					c.sendMessage("@blu@You must have a Crystal key and 4 inventory spaces to open the chest.");
			}
		}
		break;
		case 13617:
		if(obX == 3107 && obY == 3514){
			c.getPA().movePlayer(2965,3380,c.heightLevel);
		}
		break;
		case 3742:
		if(obX == 3099 && obY == 3518){
			c.sendMessage("@red@Single PK on the west side, Multi PK on the east.@blu@ You have been warned");
		}
		break;
		case 2465:
		if(obX == 3088 && obY == 3487){
			c.getDH().sendDialogues(9033, 1);
		}
		if(obX == 3100 && obY == 3498){
			c.getDH().sendDialogues(24, 1);
		}
		if(obX == 3073 && obY == 3866){
			c.getPA().movePlayer(3096,3503,c.heightLevel);
		}
		break;
		case 8966:
		if (!c.inWild()) {
			c.sendMessage("You climb up the stairs and magically appear at home.");
			c.getPA().movePlayer(Config.HOME_X,Config.HOME_Y,0);
		}
		break;
		case 3608:
			if(obX == 3098 && obY == 3499){
				//c.getPA().sendFrame126("www.foreverpkers-ps.com/forum/index.php?board=33.0", 12000);
			}
		break;
		case 3045:
		if(c.KC < 50) {
				c.sendMessage("You need a killcount of 50 to use this bank.");
			} else {
				c.getPA().openUpBank();
		}
		break;
		case 2466:
		if(obX == 3073 && obY == 3866){
			c.getPA().movePlayer(3096,3503,c.heightLevel);
		}
		if(obX == 3084 && obY == 3493){
			c.getPA().movePlayer(2757,3502,c.heightLevel);
		}
		break;
		case 2561:
			c.getThieving().stealFromStall(995, 2500, 10, 1);
		break;
		case 170://wildy chest
		int multiplier = 0;
		if (c.heightLevel != 0 || !(c.objectX == 3106 && c.objectY == 3933))
			return;
		if (System.currentTimeMillis() - c.lastThieve < 2500)
			return;
		if(c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You cannot thieve while in combat.");
			return;
		}
		int playerAmount = PlayerHandler.getPlayerCount();
		int keyChance = 1000;
		boolean closeEnough = c.goodDistance(c.getX(), c.getY(), 3106, 3933, 1);
			if(!closeEnough)
				return;
			if(playerAmount < 20) {
					multiplier = 1;
				} else if(playerAmount >= 20 && playerAmount < 50) {
					multiplier = 2;
				} else if(playerAmount >= 50 && playerAmount < 100) {
					multiplier = 3;
				} else if(playerAmount >= 100) {
					multiplier = 4;
				} else {
					multiplier = 1;
			}
			if(c.getItems().playerHasItem(995,1000000000)){
				if(Misc.random(10) == 0) {
					appendChestHit(c, Misc.random(3) + 8);
					c.sendMessage("You fail to disarm the trap, you hurt yourself.");
				}
				else {
					c.getThieving().stealFromStall(995, Misc.random(50000 * multiplier), 125, 1);
				}
					keyChance = 175;
			} else if(c.getItems().playerHasItem(995,100000000)) {
					if(Misc.random(10) == 0) {
						appendChestHit(c, Misc.random(3) + 8);
						c.sendMessage("You fail to disarm the trap, you hurt yourself.");
					}
					else {
						c.getThieving().stealFromStall(995, Misc.random(25000 * multiplier), 100, 1);
					}
					keyChance = 225;
			} else if(c.getItems().playerHasItem(995,10000000)) {
					if(Misc.random(10) == 0) {
						appendChestHit(c, Misc.random(3) + 8);
						c.sendMessage("You fail to disarm the trap, you hurt yourself.");
					}
					else {
						c.getThieving().stealFromStall(995, Misc.random(15000 * multiplier), 80, 1);
					}
					keyChance = 300;
			} else if(c.getItems().playerHasItem(995,1000000)) {
					if(Misc.random(10) == 0) {
						appendChestHit(c, Misc.random(3) + 8);
						c.sendMessage("You fail to disarm the trap, you hurt yourself.");
					}
					else {
						c.getThieving().stealFromStall(995, Misc.random(10000 * multiplier), 60, 1);
					}
					keyChance = 500;
			} else if(c.getItems().playerHasItem(995,100000)) {
					if(Misc.random(10) == 0) {
						appendChestHit(c, Misc.random(3) + 8);
						c.sendMessage("You fail to disarm the trap, you hurt yourself.");
					}
					else {
						c.getThieving().stealFromStall(995, Misc.random(5000 * multiplier), 40, 1);
					}
					keyChance = 1000;
				} else {
					c.sendMessage("@red@You must have 100,000 coins to open this.");
					return;
			}
		if(Misc.random(200) <= 1)
			c.sendMessage("[@red@Note@bla@] @red@Carrying @dre@1M@red@, @dre@10M@red@, @dre@100M@red@, or @dre@1B coins@red@ will increase your reward.");
		
				if(Misc.random(keyChance) == 0){
					if(c.getItems().freeSlots() > 0){
						c.sendMessage("You find a shiny key!");
						c.getItems().addItem(85, 1);
						} else {
						Server.itemHandler.createGroundItem(c, 85, c.getX(), c.getY(), 1, c.playerId);
						c.sendMessage("You find a shiny key! The key is dropped because you have no inventory space.");
					}
				}
		break;
		case 2560:
			//c.getThieving().stealFromStall(995, 5000, 40, 40);
		break;
		case 2475:
                //c.sendMessage("You are back home.");
                //c.getPA().movePlayer(3096, 3503, 0);
		break;
		case 2564:
			//c.getThieving().stealFromStall(995, 150000, 60, 60);
		break;
		case 2562:
			//c.getThieving().stealFromStall(995, 200000, 80, 80);
		break;
		case 8972:
		if(c.objectX == 2450 && c.objectY == 4471) {
			c.getDH().sendDialogues(19000, 1337);
			} else {
		        if (c.memberStatus == 0) {
		        		c.sendMessage("@blu@You must be a Member to enter this portal.");
		        	} else {
						if(!c.inWild() && !c.isInHighRiskPK() && !c.inFaladorPvP())
							c.getPA().movePlayer(2455, 4466, 0);
		        }
    	}
		break;
		case 26792:
			c.getDH().sendDialogues(956, 1337);
		break;
		case 75:
			c.getDH().sendDialogues(1000, 494);
		break;
		case 2477:
            c.getDH().sendDialogues(600, 1);
		break;
		case 2470:
			if(c.objectX == 3240 && c.objectY == 3606)
                c.getPA().movePlayer(2421, 4691, 0);
		break;
		case 2971:
			if(c.objectY == 4690)
                c.getPA().movePlayer(3240, 3607, 0);
		break;
		case 2882:
		case 2883:
			if (c.objectX == 3268) {
				if (c.absX < c.objectX) {
					c.getPA().walkTo2(1,0);
				} else {
					c.getPA().walkTo2(-1,0);
				}
			}
		break;

		/*                 Wilderness Teleports start                     */

		case 272: //downstairs boat ladder
		if(c.objectX == 3018 && c.objectY == 3957 && c.heightLevel == 0)
			c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		case 273:
		if(c.objectX == 3018 && c.objectY == 3957 && c.heightLevel == 1)
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;
		case 245:
		if((c.objectX == 3017 || c.objectX == 3019) && c.objectY == 3959 && c.heightLevel == 1)
			c.getPA().movePlayer(c.absX, c.absY + 2, 2);
		break;
		case 246:
		if((c.objectX == 3017 || c.objectX == 3019) && c.objectY == 3959 && c.heightLevel == 2)
			c.getPA().movePlayer(c.absX, c.absY - 2, 1);
		break;
		case 1733: //mage bank dungeon
			if(c.objectX == 3187 && c.objectY == 3433){
				c.getPA().movePlayer(3190, 9834, 0);
			}
			if(c.objectX == 3044 && c.objectY == 3924){
				if((c.absX == 3044 || c.absX == 3045) && c.absY == 3927)
					c.getPA().movePlayer(3044, 10323, 0);
			}
		break;
		case 1734: //mage bank dungeon
			if(c.objectX == 3187 && c.objectY == 9833){
				c.getPA().movePlayer(3186, 3434, 0);
			}
			if(c.objectX == 3044 && c.objectY == 10324){
				if((c.absX == 3044 || c.absX == 3045) && c.absY == 10323)
					c.getPA().movePlayer(3044, 3927, 0);
			}
		break;
		case 1765:
		if(c.objectX == 3017 && c.objectY == 3849)
			c.getPA().movePlayer(3069, 10255, 0);
		break;
		case 1766:
		if(c.objectX == 3069 && c.objectY == 10256)
			c.getPA().movePlayer(3016, 3849, 0);
		break;
		case 410:
			if(c.inWild() || c.safeTimer > 0)
				break;
			if (c.playerMagicBook == 0) {
					c.playerMagicBook = 2;
					c.setSidebarInterface(6, 16640);
					c.sendMessage("You feel a lunar wisdom fill your mind...");
					c.getPA().resetAutocast();
				} else {
					c.setSidebarInterface(6, 1151); //modern
					c.playerMagicBook = 0;
					c.sendMessage("You feel a strange drain upon your memory...");
					c.autocastId = -1;
					c.getPA().resetAutocast();
			}
		break; 
		case 6552:
			if(c.inWild() || c.safeTimer > 0)
				break;
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("You feel a strange wisdom fill your mind...");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151); //modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a strange drain upon your memory...");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
		break;
		
		case 9356:
		if(c.objectX == 2437 && c.objectY == 5166)
			c.getPA().enterCaves();
		break;
		case 9357:
			c.getPA().resetTzhaar();
		break;
		
		case 1590:
		case 1589:
		if ((c.absX == 3192 || c.absX == 3191) && c.absY == 9825)
			c.getPA().movePlayer(c.absX, c.absY-1, 0);
		else if ((c.absX == 2757 || c.absX == 2758) && c.absY == 3482)
			c.getPA().movePlayer(c.absX, c.absY+1, 0);
		else if ((c.absY == 9909 || c.absY == 9910) && c.absX == 3104)
			c.getPA().movePlayer(c.absX-1, c.absY, 0);
		else if ((c.absY == 9909 || c.absY == 9910) && c.absX == 3103)
			c.getPA().movePlayer(c.absX+1, c.absY, 0);
		else if ((c.absX == 3106 || c.absX == 3105) && c.absY == 9944)
			c.getPA().movePlayer(c.absX, c.absY+1, 0);
		else if ((c.absX == 3106 || c.absX == 3105) && c.absY == 9945)
			c.getPA().movePlayer(c.absX, c.absY-1, 0);
		break;
		
		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				new Object(6951, c.objectX, c.objectY, c.heightLevel, 1, 10, 8959, 15);			
			}
		break;
		
		case 2213:
		case 6084:
		case 14367:
		case 11758:
		case 3193:
			c.getPA().openUpBank();
		break;
		
		case 10177://first ladder
		if(!c.inWild())
			c.getPA().movePlayer(1863, 4373, 2);
		break;
		case 10212://second
		if(!c.inWild())
			c.getPA().movePlayer(2545, 10143, 0);
		break;
		case 10213://2nd room 2nd ladder goin down
		if(!c.inWild())
			c.getPA().movePlayer(1827, 4362, 1);
		break;
		case 10211://3rd room 2nd ladder goin up
		if(!c.inWild())
			c.getPA().movePlayer(1864, 4389, 1);
		break;
		case 10210://3rd room 1st ladder goin up
		if(!c.inWild())
			c.getPA().movePlayer(1864, 4387, 2);
		break;
		case 10214://4th room 1st ladder goin up
		if(!c.inWild())
			c.getPA().movePlayer(1863, 4370, 1);
		break;
		case 10215://4th room final ladder down
		if(!c.inWild())
			c.getPA().movePlayer(1890, 4409, 0);
		break;
		case 1749://4th room final ladder down
		if(c.objectX == 3278 && c.objectY == 3952)
			c.getPA().movePlayer(1890, 4409, 4);
		break;
		case 10216://final room ladder up
		//old
		//if(!c.inWild())
			//c.getPA().movePlayer(1890, 4409, 1);

		//new
		if(c.objectX == 1890 && c.objectY == 4408)
			c.getPA().movePlayer(3278, 3953, 0);
		break;
		case 10217://go to dag mother
		if(c.objectX == 1957 && c.objectY == 4371)
			c.getPA().movePlayer(2515, 4632, c.heightLevel);
		break;
		case 4413://leave dag mother
		if(c.objectX == 2515 && c.objectY == 4631)
			c.getPA().movePlayer(1957, 4371, c.heightLevel);
		break;
		case 10230:
		if(c.objectX == 1911 && c.objectY == 4367)
			c.getPA().movePlayer(2900, 4449, c.heightLevel);
		break;
		case 10229:
		if(c.objectX == 2899 && c.objectY == 4449)
			c.getPA().movePlayer(1912, 4367, c.heightLevel);
		break;
		case 2623:
			if (c.absX >= c.objectX)
				c.getPA().walkTo2(-1,0);
			else
				c.getPA().walkTo2(1,0);
		break;
		//pc boat
		case 14315:
		if(!c.inWild())
			c.getPA().movePlayer(2660,2639,0);
		break;
		case 14314:
		if(!c.inWild())
			c.getPA().movePlayer(2657,2639,0);
		break;
		case 1596:
		case 1597:
		if(c.absY == 9917) {
				c.getPA().walkTo2(0,1);
			} else {
				if(c.inWild() && c.objectY != 3896 && c.objectY != 3856 && c.objectY != 3904)
					break;
				if (c.getY() >= c.objectY)
					c.getPA().walkTo2(0,-1);
				else
					c.getPA().walkTo2(0,1);
		}
		break;
	
		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
				else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
				else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else c.absY -= 1;
		if(!c.inWild())
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;
		
		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			Server.objectManager.startObelisk(objectType);
		break;
		
		case 9369:
			if(c.objectX != 2399 || c.objectY != 5176)
				break;

			if(c.teleTimer <= 0){
				if (c.getY() > 5175)
					c.getPA().movePlayer(2399, 5175, 0);
				else
					c.getPA().movePlayer(2399, 5177, 0);
			}
		break;
		
		case 9368:
			if(c.objectX != 2399 || c.objectY != 5168)
				break;

			if (c.getY() < 5169) {
				Server.fightPits.removePlayerFromPits(c.playerId);
				c.getPA().movePlayer(2399, 5169, 0);
			}	
		break;

		case 2286:
		case 154:
		case 4058:
		case 2295:
		case 2285:
		case 2313:
		case 2312:
		case 2314:
			c.getAgility().handleGnomeCourse(objectType,obX,obY);
		break;
		
		//barrows
		//Chest
		case 10284:
			if(c.barrowsKillCount < 5) {
				c.sendMessage("You haven't killed all the brothers.");
			}
			if(c.barrowsKillCount == 5 && c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already summoned this npc.");
			}
			if(c.barrowsNpcs[c.randomCoffin][1] == 0 && c.barrowsKillCount >= 5) {
				Server.npcHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0], 3551, 9694-1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2) && c.getItems().freeSlots() >= 2) {
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(), Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if(c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need at least 2 inventory slot opened.");
			}
			break;
		//doors
		/*case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
		break;
		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
		break;
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
		break;
		case 6730:
			if(obX == 3558 && obY == 9677) {
					c.getPA().object(3562, 9678, 6749, -3, 0);
					c.getPA().object(3562, 9677, 6730, -1, 0);
				} else if(obX == 3558 && obY == 9678) {
					c.getPA().object(3558, 9677, 6749, -1, 0);
					c.getPA().object(3558, 9678, 6730, -3, 0);
			}
		break;
		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
		break;
		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
		break;
		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
		break;*/
		//end doors
		//coffins
		case 6707: // verac
		if(!c.inWild())
			c.getPA().movePlayer(3556, 3298, 0);
		break;
		case 6823:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY()-1, -1, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;

		case 6706: // torag 
		if(!c.inWild())
			c.getPA().movePlayer(3553, 3283, 0);
		break;
		case 6772:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX()+1, c.getY(), -1, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
		case 6705: // karil stairs
		if(!c.inWild())
			c.getPA().movePlayer(3565, 3276, 0);
		break;
		case 6822:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY()-1, -1, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
		case 6704: // guthan stairs
		if(!c.inWild())
			c.getPA().movePlayer(3578, 3284, 0);
		break;
		case 6773:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY()-1, -1, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;

		case 6703: // dharok stairs
		if(!c.inWild())
			c.getPA().movePlayer(3574, 3298, 0);
		break;
		case 6771:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY()-1, -1, 0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
		case 6702: // ahrim stairs
		if(!c.inWild())
			c.getPA().movePlayer(3565, 3290, 0);
		break;
		case 6821:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY()-1, -1, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
		case 1276:
		case 1278://trees
			c.woodcut[0] = 1511;
			c.woodcut[1] = 1;
			c.woodcut[2] = 25;
			c.getWoodcutting().startWoodcutting(c.woodcut[0], c.woodcut[1], c.woodcut[2]);
		break;
		
		case 1281: //oak
			c.woodcut[0] = 1521;
			c.woodcut[1] = 15;
			c.woodcut[2] = 37;
			c.getWoodcutting().startWoodcutting(c.woodcut[0], c.woodcut[1], c.woodcut[2]);
		break;
		
		case 1308: //willow
		case 5551:
		case 5553:
			c.woodcut[0] = 1519;
			c.woodcut[1] = 30;
			c.woodcut[2] = 68;
			c.getWoodcutting().startWoodcutting(c.woodcut[0], c.woodcut[1], c.woodcut[2]);
		break;
		
		case 1307: //maple
			c.woodcut[0] = 1517;
			c.woodcut[1] = 45;
			c.woodcut[2] = 100;
			c.getWoodcutting().startWoodcutting(c.woodcut[0], c.woodcut[1], c.woodcut[2]);
		break;
		
		case 1309: //yew
			c.woodcut[0] = 1515;
			c.woodcut[1] = 60;
			c.woodcut[2] = 175;
			c.getWoodcutting().startWoodcutting(c.woodcut[0], c.woodcut[1], c.woodcut[2]);
		break;
		
		case 1306: //yew
			c.woodcut[0] = 1513;
			c.woodcut[1] = 75;
			c.woodcut[2] = 250;
			c.getWoodcutting().startWoodcutting(c.woodcut[0], c.woodcut[1], c.woodcut[2]);
		break;

		
		case 2090://copper
		case 2091:
			c.mining[0] = 436;
			c.mining[1] = 1;
			c.mining[2] = 18;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;
		
		case 2094://tin
			c.mining[0] = 438;
			c.mining[1] = 1;
			c.mining[2] = 18;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;		
		
		case 145856:
		case 2092:
		case 2093: //iron
			c.mining[0] = 440;
			c.mining[1] = 15;
			c.mining[2] = 35;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;
		
		case 14850:
		case 14851:
		case 14852:
		case 2096:
		case 2097: //coal
			c.mining[0] = 453;
			c.mining[1] = 30;
			c.mining[2] = 50;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;		
		
		case 2100:
		case 2101:
			c.mining[0] = 442;
			c.mining[1] = 20;
			c.mining[2] = 40;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;
		
		case 2098:
		case 2099:
			c.mining[0] = 444;
			c.mining[1] = 40;
			c.mining[2] = 65;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;
		
		case 2102:
		case 2103:
		case 14853:
		case 14854:
		case 14855: //mith ore
			c.mining[0] = 447;
			c.mining[1] = 55;
			c.mining[2] = 80;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;
		
		case 2105:
		case 2104:
		case 14862: //addy ore
			c.mining[0] = 449;
			c.mining[1] = 70;
			c.mining[2] = 95;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;
		
		case 14859:
		case 14860: //rune ore
			c.mining[0] = 451;
			c.mining[1] = 85;
			c.mining[2] = 125;
			c.getMining().startMining(c.mining[0], c.mining[1], c.mining[2]);
		break;
		
		case 8143:
			if (c.farm[0] > 0 && c.farm[1] > 0) {
				c.getFarming().pickHerb();
			}
		break;

		case 1517:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo2(0,-1);
				else
					c.getPA().walkTo2(0,1);
			}
		break;
		case 2113://falador mining guild entrance
				if(c.getPA().getLevelForXP(c.playerXP[c.playerMining]) >= 65){
						c.getPA().movePlayer(3021, 9739, 0);
						c.sendMessage("You enter the mining guild.");
					} else {
						c.sendMessage("Your mining level must be at least @blu@65@bla@ to enter the mining guild.");
				}
		break;
		case 2112://falador mining guild door
				if(c.absX == 3046 && c.absY == 9757){
					if(c.getPA().getLevelForXP(c.playerXP[c.playerMining]) >= 65){
							c.getPA().walkTo2(0,-1);
							c.sendMessage("You enter the mining guild.");
							} else {
							c.sendMessage("Your mining level must be at least @blu@65@bla@ to enter the mining guild.");
						}
					}
				if(c.absX == 3046 && c.absY == 9756){
							c.getPA().walkTo2(0,1);
							c.sendMessage("You leave the mining guild.");
				}
		break;
		case 9319:

		if(c.inWild())
			break;

			if (c.heightLevel == 0) {
					if(c.getPA().getLevelForXP(c.playerXP[18]) >= 50){
							c.getPA().movePlayer(c.absX, c.absY, 1);
						} else {
							c.sendMessage("You must have a Slayer level of @blu@50@bla@ to climb this.");
					}
				} else if (c.heightLevel == 1) {
					if(c.getPA().getLevelForXP(c.playerXP[18]) >= 75){
							c.getPA().movePlayer(c.absX, c.absY, 2);
						} else {
							c.sendMessage("You must have a Slayer level of @blu@75@bla@ to climb this.");
					}
			}
		break;
		
		case 9320:
		if(c.inWild())
			break;
		if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX, c.absY, 0);
			} else if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX, c.absY, 1);
		}
		break;
		case 4496:
			if(c.inWild())
				break;
			if (c.heightLevel == 2)
				c.getPA().movePlayer(3412, 3540+Misc.random(1), 1);
		break;
		case 4494:
			if(c.inWild())
				break;
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(3412, 3540+Misc.random(1), 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(3438, 3537+Misc.random(1), 0);
			}
		break;
		/*case 2958:
			if(c.objectX == 2377 && c.objectY == 4711 && c.onLedge3()) {
				c.getPA().movePlayer(2378+Misc.random(2), 4708, 0);
				c.sendMessage("You swing to the floor using the rope.");
			}
		break;*/
		case 4493:
		if(c.inWild())
			break;
			if (c.heightLevel == 0) {
				if(c.absX > 3431 && c.absY <= 3540)
					c.getPA().movePlayer(3433, 3537+Misc.random(1), 1);
				} else if (c.heightLevel == 1) {
					c.getPA().movePlayer(c.absX, c.absY, 2);
			}
		break;
		case 4495:
		if(c.inWild())
			break;
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(3417, 3540+Misc.random(1), 2);
			}
		break;
		case 5126:
			if (c.absX == 3445 && c.absY == 3554) {
					c.getPA().movePlayer(3445, 3555, 2);
				} else if(c.absX == 3445 && c.absY == 3555) {
					c.getPA().movePlayer(3445, 3554, 2);
			}
		break;
		case 11707:
			if (c.absX == 2949 && c.absY == 3379)
				c.getPA().walkTo2(-1,0);
			if (c.absX == 2948 && c.absY == 3379)
				c.getPA().walkTo2(1,0);
		break;
		case 1568:
		if (c.objectX == 3097 && c.objectY == 3468)
			c.getPA().movePlayer(3096, 9867, 0);
		break;
		case 1755:
			if (c.objectX == 2884 && c.objectY == 9797) {
				c.getPA().movePlayer(2884, 3396, 0);
				c.sendMessage("You climb the ladder.");
			} else if (c.objectX == 3097 && c.objectY == 9867) {
				c.getPA().movePlayer(3096, 3468, 0);
				c.sendMessage("You climb the ladder.");
			} else if (c.objectX == 3020 && c.objectY == 9739 || c.objectX == 3019 && c.objectY == 9740 || c.objectX == 3018 && c.objectY == 9739 || c.objectX == 3019 && c.objectY == 9738){
				c.getPA().movePlayer(3021, 3339, 0);
				c.sendMessage("You leave the mining guild.");
			}
		break;
		case 1759:
		if (c.objectX == 2884 && c.objectY == 3397)
			c.getPA().movePlayer(2884, 9798, 0);		
		break;
		case 9359:
		if(!c.inWild())
			c.getPA().movePlayer(2400, 5179, 0);
		break;
		case 1558:
		case 1557:
			if ((c.absX == 2757 || c.absX == 2758) && c.absY == 3483)
				c.getPA().movePlayer(c.absX, c.absY-1, 0);
			else if ((c.absX == 2757 || c.absX == 2758) && c.absY == 3482)
				c.getPA().movePlayer(c.absX, c.absY+1, 0);
			else if ((c.absY == 9909 || c.absY == 9910) && c.absX == 3104)
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
			else if ((c.absY == 9909 || c.absY == 9910) && c.absX == 3103)
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
			else if ((c.absX == 3106 || c.absX == 3105) && c.absY == 9944)
				c.getPA().movePlayer(c.absX, c.absY+1, 0);
			else if ((c.absX == 3106 || c.absX == 3105) && c.absY == 9945)
				c.getPA().movePlayer(c.absX, c.absY-1, 0);
		break;

		case 2962:
		case 2960:
		case 2959:
		case 2964:
		case 2963:
		case 2961:
			if((c.objectX == 2377 && c.objectY == 4717) || (c.objectX == 2377 && c.objectY == 4728) || (c.objectX == 2390 && c.objectY == 4718) || (c.objectX == 2390 && c.objectY == 4724) || (c.objectX == 2387 && c.objectY == 4728) || (c.objectX == 2382 && c.objectY == 4729)) {

				c.turnPlayerTo(c.objectX, c.objectY);
				c.startAnimation(839);

				if(c.goodDistance(c.absX, c.absY, 2378, 4717, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2376, 4717);
						c.postProcessing();
					}
				if(c.goodDistance(c.absX, c.absY, 2376, 4718, 1)) {
						c.stopMovement();
						c.getPA().walkTo3(2378, 4717);
						c.postProcessing();
				}
				if(c.goodDistance(c.absX, c.absY, 2376, 4727, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2378, 4729);
						c.postProcessing();
					} else if(c.goodDistance(c.absX, c.absY, 2378, 4729, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2376, 4727);
						c.postProcessing();
				}
				if(c.goodDistance(c.absX, c.absY, 2390, 4716, 2)) {
						c.stopMovement();
						c.getPA().movePlayer(2391, 4719, 0);
						c.postProcessing();
					} else if(c.goodDistance(c.absX, c.absY, 2391, 4719, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2390, 4717);
						c.postProcessing();
				}
				if(c.goodDistance(c.absX, c.absY, 2390, 4723, 1)) {
						c.stopMovement();
						c.getPA().walkTo3(2390, 4725);
						c.postProcessing();
					} else if(c.goodDistance(c.absX, c.absY, 2390, 4725, 1)) {
						c.stopMovement();
						c.getPA().walkTo3(2390, 4723);
						c.postProcessing();
				}
				if(c.goodDistance(c.absX, c.absY, 2388, 4728, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2386, 4727);
						c.postProcessing();
					} else if(c.goodDistance(c.absX, c.absY, 2386, 4727, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2388, 4728);
						c.postProcessing();
				}
				 if(c.goodDistance(c.absX, c.absY, 2382, 4730, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2383, 4728);
						c.postProcessing();
					} else if(c.goodDistance(c.absX, c.absY, 2383, 4728, 2)) {
						c.stopMovement();
						c.getPA().walkTo3(2382, 4730);
						c.postProcessing();
				}
			}
		break;

		case 409: //Prayer altars
		case 411:
		case 4859:		
		case 24343:
		case 26289:
		case 26288:
		case 26287:
		case 26286:
			if(c.underAttackBy > 0) {
				c.sendMessage("The Gods would not appreciate your prayers during combat.");
				break;
			}
			if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
					c.startAnimation(645);
					c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
					c.sendMessage("You recharge your prayer points.");
					c.getPA().refreshSkill(5);
				} else {
					c.sendMessage("You already have full prayer points.");
			}
		break;
		case 2873: //Saradomin Statue	
			c.getDH().sendDialogues(10000, -1);
		break;
		case 2874: //Zammy statue
			c.getDH().sendDialogues(10004, -1);
		break;

		//levers
		case 2879:
		if(c.objectX == 2508 && c.objectY == 4686)
			c.getPA().movePlayer(2538, 4716, 0);
		break;
		case 2878:
		if(c.objectX == 2541 && c.objectY == 4719)
			c.getPA().movePlayer(2509, 4689, 0);
		break;
		case 1814: //edgeville monster teleport
			c.getDH().sendDialogues(9047, 1);
		break;
		case 1815: //black drag wildy
		if(c.objectX == 3153 && c.objectY == 3923)
			c.getPA().startTeleport2(2717, 9801, 0);
		break;
		case 1816:
		if(c.objectX == 3067 && c.objectY == 10253)
			c.getPA().startTeleport2(2271, 4680, 0);			
		break;
		case 1817:
		if(c.objectX == 2717 && c.objectY == 9801)
				c.getPA().startTeleport2(3153, 3923, 0);
			else if(c.objectX == 2271 && c.objectY == 4680 && c.heightLevel == 0) {
				c.getPA().startTeleport2(3067, 10253, 0);
			} else {
				c.sendMessage("This lever appears to be stuck.");
			}
		break;
		case 5959: //outside mage bank
		if(c.objectX == 3090 && c.objectY == 3956)
			c.getPA().startTeleport2(2539, 4712, 0);
		break;
		case 5960: //inside mage bank
		if(c.objectX == 2539 && c.objectY == 4712)
			c.getPA().startTeleport2(3090, 3956, 0);
		break;
		case 9706:
		if(c.objectX == 3104 && c.objectY == 3956)
			c.getPA().startTeleport2(3105, 3951, 0);
		break;
		case 9707:
		if(c.objectX == 3105 && c.objectY == 3952)
			c.getPA().startTeleport2(3105, 3956, 0);
		break;

		case 2558:
			c.sendMessage("This door is locked.");	
		break;

		//some agility shit
		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2880, 9813, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(2878, 9813, 0);
			}
		break;
		
		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
		break;

		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo2(0,1);
			else
				c.getPA().walkTo2(0,-1);
		break;

		case 3044://furnaces
		case 2781:
		case 2785:
		case 2966:
		case 3294:
		case 3413:
		case 3994:
		case 4304:
		case 4305:
		case 6189:
		case 6190:
			c.getSmithing().sendSmelting();
		break;

		case 733:

		if(!c.inWild())
			break;

			c.startAnimation(451);
			c.getPA().removeObject(c.objectX, c.objectY);
			c.sendMessage("You slash the web.");
			if (c.objectX == 3158 && c.objectY == 3951) {
					new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10, 733, 50);
				} else {
					new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10, 733, 50);
			}
		break;
		
		default:
			ScriptManager.callFunc("objectClick1_"+objectType, c, objectType, obX, obY);
			break;

		}
	}
	
	public void secondClickObject(int objectType, int obX, int obY) {
		int randomMsg = Misc.random(100);
		c.clickObjectType = 0;
		if(!c.goodDistance(c.getX(), c.getY(), obX, obY, 4))//prevent cheat clients maybe?
			return;
		//c.sendMessage("Object type: " + objectType);
		switch(objectType) {
		
		case 6162:
			if(c.memberStatus >= 1)
			c.getThieving().stealFromStall(c.randomGem(),1, 150, 1);
		break;
		case 4707:
			if(c.memberStatus >= 1)
			c.getThieving().stealFromStall(c.randomFish(),1, 150, 1);
		break;
		case 6164:
			if(c.memberStatus >= 1)
			c.getThieving().stealFromStall(995,5000+Misc.random(10000), 150, 1);
		break;
		
		case 2513://target at edge
			if(c.gameMode == 2) {
				if (System.currentTimeMillis() - c.dummyTimer >= 2000) {
					if (c.playerEquipment[c.playerWeapon] == 841) {
						c.getPA().addSkillXP(30, 4);//range
						c.getPA().addSkillXP(15, 3);//hp
						c.startAnimation(426);
						c.sendMessage("You hit the target!");
						c.dummyTimer = System.currentTimeMillis();
					} else {
						c.sendMessage("You must have a normal shortbow equipped to train on this target!");
					}
				}
			} else {
				c.sendMessage("Only trained accounts can hit the targets!");
			}
		break;
		
		case 823://dummy at edge
		if(c.gameMode == 2) {
			if (System.currentTimeMillis() - c.dummyTimer >= 2000) {
				if (c.playerEquipment[c.playerWeapon] == -1) {
					c.sendMessage("You hit the dummy.");
					if(c.fightMode == 0) {//atk
						if(c.playerLevel[0] <= 24) {
							c.getPA().addSkillXP(30, 0);
						} else {
							c.sendMessage("You've practiced enough of this skill, time to change it up!");
						}
					}
					if(c.fightMode == 2) {//str
						if(c.playerLevel[2] <= 24) {
							c.getPA().addSkillXP(30, 2);
						} else {
							c.sendMessage("You've practiced enough of this skill, time to change it up!");
						}
					}
					if(c.fightMode == 1) {//def
						if(c.playerLevel[1] <= 24) {
							c.getPA().addSkillXP(30, 1);
						} else {
							c.sendMessage("You've practiced enough of this skill, time to change it up!");
						}
					}


					c.getPA().addSkillXP(15, 3);//hp
					c.startAnimation(422);
					c.dummyTimer = System.currentTimeMillis();
				} else if (c.playerEquipment[c.playerWeapon] != -1) {
					c.sendMessage("You can't use weapons on a dummy!");
				}
			}
		} else {
			c.sendMessage("Only trained accounts can hit the dummies!");
		}
		break;
		
		case 2646://flax
			if((System.currentTimeMillis() - c.bananaDelay >= 3000) && c.getItems().freeSlots() >= 1){
				c.bananaDelay = System.currentTimeMillis();
				c.getItems().addItem(1779, 1);
				c.sendMessage("You pick up some flax");
				c.startAnimation(832);
				c.getPA().addSkillXP(Config.FARMING_EXPERIENCE*5, 19);
			}
		break;
		case 8987://boxing island teleport
			if(obX == 2523 || obX == 2524 && obY == 4777){
				if(c.canUsePortal == 1) {
				//if(c.safeTimer == 0) {
					c.getPA().movePlayer(3182 + Misc.random(1), 3443 + Misc.random(1), 0);
					c.canUsePortal = 0;
				} else {
					c.sendMessage("You can't teleport during a fight!");
				}
			}
		break;
		case 1734:
			//c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
		if(obX == 3187 && obY == 9833){
			c.getPA().movePlayer(3186, 3434, 0);
		}
		break;
		case 4309: //spinning wheel
			if((System.currentTimeMillis() - c.bananaDelay >= 3000)){
				if(c.getItems().playerHasItem(1779,1)) {
					c.bananaDelay = System.currentTimeMillis();
					c.getItems().addItem(1777, 1);
					c.getItems().deleteItem(1779, 1);
					c.sendMessage("You make a bow string out of flax");
					c.startAnimation(832);
					c.getPA().addSkillXP(Config.CRAFTING_EXPERIENCE*5, 12);
				} else {
					c.sendMessage("You need flax to use the spinning wheel!");
				}
			}
		break;
		
		case 17010:
if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 6914 || c.playerEquipment[c.playerWeapon] == 1381 || c.playerEquipment[c.playerWeapon] == 1383 || c.playerEquipment[c.playerWeapon] == 1385 || c.playerEquipment[c.playerWeapon] == 1387 || c.playerEquipment[c.playerWeapon] == 6562) {
c.sendMessage("Please remove your staff to change your mage.");
return;
}
if (c.playerMagicBook == 0) {
c.playerMagicBook = 2;
c.setSidebarInterface(6, 29999);
c.sendMessage("You feel a lunar wisdom fill your mind...");
c.getPA().resetAutocast();
} else {
c.setSidebarInterface(6, 1151); //modern
c.playerMagicBook = 0;
c.sendMessage("You feel a strange drain upon your memory...");
c.autocastId = -1;
c.getPA().resetAutocast();
}
break; 
case 26289:
case 26288:
case 26287:
case 26286:
c.getPA().spellTeleport(2881, 5309, 6);
break;
			case 11666:
			case 3044:
				c.getSmithing().sendSmelting();
			break;
			case 2213:
			case 14367:
			case 11758:
				if(c.inWild() || c.isInHighRiskPK() || c.inFaladorPvP()) {
					c.sendMessage("You can't do this in the wilderness!");
				} else {
					c.getPA().openUpBank();
				}
			break;
			case 4874:
				c.getThieving().stealFromStall(995,2500 + Misc.random(2500), 25, 1);
				if(randomMsg == 1)
					c.sendMessage("[@red@Note@bla@] Want to make more money from Thieving? Try the Wilderness chest at Mage bank!");
				if(c.doingStarter == 1) {
					if(c.sTask2 == 0) {
						c.sTask2 = 1;
						c.getItems().addItem(995,100000);
						c.sendMessage("You completed a starter task!");
					}
				}
			break;
			case 4875:
				c.getThieving().stealFromStall(995,2750 + Misc.random(2750), 40, 25);
				if(randomMsg == 1)
					c.sendMessage("[@red@Note@bla@] Want to make more money from Thieving? Try the Wilderness chest at Mage bank!");
			break;
			case 4876:
				c.getThieving().stealFromStall(995,3000 + Misc.random(3000), 75, 50);
				if(randomMsg == 1)
					c.sendMessage("[@red@Note@bla@] Want to make more money from Thieving? Try the Wilderness chest at Mage bank!");
			break;
			case 4877:
				c.getThieving().stealFromStall(995,3500 +  Misc.random(3500), 100, 75);
				if(randomMsg == 1)
					c.sendMessage("[@red@Note@bla@] Want to make more money from Thieving? Try the Wilderness chest at Mage bank!");
			break;
			case 4878:
				c.getThieving().stealFromStall(995,5500 + Misc.random(5500), 125, 90);
				if(randomMsg == 1)
					c.sendMessage("[@red@Note@bla@] Want to make more money from Thieving? Try the Wilderness chest at Mage bank!");
			break;

			case 2557:
				if (System.currentTimeMillis() - c.lastLockPick < 3000 || c.freezeTimer > 0)
					break;
				if (c.getItems().playerHasItem(1523,1)) {
						c.lastLockPick = System.currentTimeMillis();
						if (Misc.random(10) <= 3){
							c.sendMessage("You fail to pick the lock.");
							break;
						}
					 if (c.objectX == 3191 && c.objectY == 3963) {
						if (c.absY == 3963) {
							c.getPA().walkTo2(0,-1);
						} else if (c.absY == 3962) {
							c.getPA().walkTo2(0,1);
						}					
					} else if (c.objectX == 3190 && c.objectY == 3957) {
						if (c.absY == 3957) {
							c.getPA().walkTo2(0,1);
						} else if (c.absY == 3958) {
							c.getPA().walkTo2(0,-1);
						}					
					}
				} else {
					c.sendMessage("I need a lockpick to pick this lock.");
				}
			break;
	
			case 2558:
				if (System.currentTimeMillis() - c.lastLockPick < 3000 || c.freezeTimer > 0)
					break;
				if (c.getItems().playerHasItem(1523,1)) {
						c.lastLockPick = System.currentTimeMillis();
						if (Misc.random(10) <= 3){
							c.sendMessage("You fail to pick the lock.");
							break;
						}
					if (c.objectX == 3044 && c.objectY == 3956) {
						if (c.absX == 3045) {
							c.getPA().walkTo2(-1,0);
						} else if (c.absX == 3044) {
							c.getPA().walkTo2(1,0);
						}
					
					} else if (c.objectX == 3038 && c.objectY == 3956) {
						if (c.absX == 3037) {
							c.getPA().walkTo2(1,0);
						} else if (c.absX == 3038) {
							c.getPA().walkTo2(-1,0);
						}				
					} else if (c.objectX == 3041 && c.objectY == 3959) {
						if (c.absY == 3960) {
							c.getPA().walkTo2(0,-1);
						} else if (c.absY == 3959) {
							c.getPA().walkTo2(0,1);
						}					
					}
				} else {
					c.sendMessage("I need a lockpick to pick this lock.");
				}
			break;
		default:
			ScriptManager.callFunc("objectClick2_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch(objectType) {
		default:
			ScriptManager.callFunc("objectClick3_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	public void firstClickNpc(int npcType) {
		if(npcType != 316 && npcType != 312 && npcType != 334 && npcType != 324 && npcType != 314 && npcType != 326 && npcType != 2067 && npcType != 2068)
			c.npcClickIndex = 0;
		c.clickNpcType = 0;
		c.getPA().resetFollow();
		switch(npcType) {
			case 1702:
				c.getDH().sendDialogues(10016, npcType);
			break;
			case 1:
			case 2:
			case 3:
				c.getDH().sendDialogues(13337, npcType);
			break;
			case 4:
				c.getDH().sendDialogues(13340, npcType);
			break;
			case 220:
				c.getShops().openShop(5);
			break;
			case 992:
				c.getShops().openShop(54);
			break;
			
			case 597:
				c.getShops().openShop(47);
			break;
			
			case 537:
				//if(c.memberStatus == 0) {
					c.getShops().openShop(49);
				/*}
				if(c.memberStatus == 1) {
					c.getShops().openShop(50);
				}
				if(c.memberStatus == 2) {
					c.getShops().openShop(51);
				}
				if(c.memberStatus == 3) {
					c.getShops().openShop(52);
				}*/
			break;
			
			case 2548:
				c.getDH().sendDialogues(15000, npcType);
			break;
			
			case 945:
				c.getDH().sendDialogues(18004, npcType);
			break;
			
			case 2790:
				c.getDH().sendDialogues(10021, npcType);
			break;
			
			case 599:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			break;

			case 1001:
				c.getDH().sendDialogues(9045, npcType);
			break;

			case 547:
				c.getShops().openShop(38);
			break;
			case 516:
				c.getDH().sendDialogues(20003, npcType);
				//c.getShops().openShop(42);
				//c.checkVotes(c.playerName); CAUSES CRASH WHEN SPAMMED!
				c.sendMessage("You currently have @blu@"+c.VP+"@bla@ vote points, and @blu@"+c.allVP+"@bla@ total vote points.");
			break;
			case 410:
				c.getDH().sendDialogues(9999, npcType);
			break;
			case 1867:
				c.getShops().openShop(40);
			break;
			case 636:
				c.getPA().sendFrame126("www.foreverpkers-ps.com/vote.php", 12000);
			break;
			case 731:
				c.getItems().addItem(1917,1);
				c.sendMessage("It's beer!");
			break;
			case 1039:
				c.getShops().openShop(30);
			break;
			case 535:
				c.getShops().openShop(37);
			break;
			case 1783:
				c.getShops().openShop(36);
			break;
			case 212:
				c.getShops().openShop(39);
			break;
			case 2270:
				c.getShops().openShop(20);
			break;
			case 37:
				c.getShops().openShop(31);
				c.sendMessage("@red@Info about Donating and Membership at @blu@www.ForeverPkers-PS.com@red@!");
				c.sendMessage("@red@Donations keep this server running, and maintaining frequent updates!");
			break;
			case 399:
				c.getShops().openShop(33);
				c.sendMessage("@red@Gear points@bla@ are gained every @blu@5 minutes@bla@. Donate if you want to increase the amount!");
				c.sendMessage("@blu@Did you know, you can type ::food, ::veng, ::barrage, and ::pots, to spawn them?");
				if(c.doingStarter == 1) {
					if(c.sTask2 == 0) {
						c.sTask2 = 1;
						c.getItems().addItem(995,100000);
						c.sendMessage("You completed a starter task!");
					}
				}
			break;
			case 251:
				c.getShops().openShop(34);
			break;
			case 398:
				c.getDH().sendDialogues(9036, npcType);
			break;
			case 2253:
				c.getDH().sendDialogues(23, npcType);
			break;

			case 1597:
				c.getDH().sendDialogues(9037, npcType);
			break;
			case 2402:
				c.getShops().openShop(31);
			break;
			case 651:
				c.getShops().openShop(29);
			break;
			case 1294:
				c.getShops().openShop(23);
			break;
			case 587:
				c.getShops().openShop(22);
			break;
			case 3792://void squire
				//c.getPA().movePlayer(2659, 2676, 0);
			break;
			case 2244:
				c.getDH().sendDialogues(9000, npcType);
			break;
			case 70:
				//if(c.slayerTask <= 0 || c.taskAmount <= 0)
				c.getDH().sendDialogues(19010, npcType);
				//else
				//c.getDH().sendDialogues(19013, npcType);
			break;
			case 3020:
			c.getDH().sendDialogues(20, npcType);
			/*if (c.getItems().playerHasItem(995,200)) {
				c.getItems().deleteItem(995,c.getItems().getItemSlot(995), 200);
				c.itemBeforeCarpet = c.playerEquipment[c.playerWeapon];
				c.playerEquipment[c.playerWeapon] = 5614;
				c.getPA().movePlayer(3308, 3109, 0);
				c.startAnimation(2262);
				c.isRunning2 = true;
				c.usingCarpet = true;
				c.getPA().walkTo3(-130, -64);
				c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.updateRequired = true; 
				c.setAppearanceUpdateRequired(true);
				} else {
				c.sendMessage("You need 200 coins to ride the carpet.");
			}	
			break;*/
			break;
			case 1918:
			if (c.getItems().playerHasItem(995,200)) {
				c.getItems().deleteItem(995,c.getItems().getItemSlot(995), 200);
				c.getPA().movePlayer(3308, 3108, 0);
				c.sendMessage("You pay 200 coins and are flown to the Shanty pass.");
			} else {
				c.sendMessage("You need 200 coins to ride the carpet.");
				}
			break;
			case 3791://void squire
				//c.getPA().movePlayer(3049, 3235, 0);
			break;
			case 2825:
				if(!c.inWild())
					c.getPA().startTeleport(2665,3161, 0, "modern");
				c.sendMessage("Ready to go fishin'?");
			break;
			case 534:
				c.getShops().openShop(21);
			break;
			case 1071:
				c.getShops().openShop(19);
			break;

			case 706:
				c.getDH().sendDialogues(9, npcType);
			break;
			case 2258:
				c.getDH().sendDialogues(17, npcType);
			break;
			case 1599:
				if (c.slayerTask <= 0) {
					c.getDH().sendDialogues(11,npcType);
				} else {
					c.getDH().sendDialogues(13,npcType);
				}
			case 500:
			if (c.monkeyk0ed >= 20) {
					c.getDH().sendDialogues(33,npcType);
				} else {
					c.getDH().sendDialogues(35,npcType);
				}			
			break;

			case 461:
				c.getShops().openShop(2);
			break;
			
			case 683:
				c.getShops().openShop(3);
			break;
			
			case 549:
				c.getShops().openShop(28);
			break;
			
			case 519:
				c.getShops().openShop(8);
			break;
			case 1282:
				c.getDH().sendDialogues(10012,npcType);
			break;
			case 1152:
				c.getDH().sendDialogues(16,npcType);
			break;
			case 494:
			case 57:
			if(c.inWild() || c.isInHighRiskPK() || c.inFaladorPvP()) {
					c.sendMessage("You can't do this in the wilderness!");
				} else {
				c.getPA().openUpBank();
			}
			break;
			case 2566:
				c.getShops().openSkillCape();
			break;
			case 3789:
				c.sendMessage("You currently have "+c.rangeKills+" ranged kill points.");
			break;
			case 3787:
				c.sendMessage("You currently have "+c.meleeKills+" melee kill points.");
			break;
			case 3788:
				c.sendMessage("You currently have "+c.mageKills+" magic kill points.");
			break;
			case 905:
				c.getDH().sendDialogues(5, npcType);
			break;
			case 460:
				c.getDH().sendDialogues(3, npcType);
			break;
			case 462:
				c.getDH().sendDialogues(7, npcType);
			break;
			
			/* FISH STARTS HERE */
			
			case 312://lobsters
				c.getFishing().setupFishing(377);
			break;
			case 316: //shrimp
				c.getFishing().setupFishing(317);
			break;
			case 334: //mantas
				c.getFishing().setupFishing(389);
			break;
			case 324: //tuna
				c.getFishing().setupFishing(359);
			break;
			case 314: //salmon
				c.getFishing().setupFishing(335);
			break;
			case 326: //monks
				c.getFishing().setupFishing(7944);
			break;
			case 2067: //manta
				c.getFishing().setupFishing(389);
			break;
			case 2068: //karambwan
				c.getFishing().setupFishing(3142);
			break;
			case 546:
				c.getShops().openShop(27);
			break;
			case 541:
				c.getShops().openShop(26);
			break;
			case 522:
			case 523:
			case 520:
				c.getShops().openShop(1);
			break;
			case 904:
				c.sendMessage("He seems to not want to talk.");
			break;
			case 1596:
				c.getDH().sendDialogues(28, npcType);
			break;
			case 553:
				c.getShops().openShop(2);
			break;
		default:
			ScriptManager.callFunc("npcClick1_"+npcType, c, npcType);
			if(c.playerRights == 3) 
				c.sendMessage("First Click Npc : "+npcType);
			break;
		}
	}

	public void secondClickNpc(int npcType) {
		if(npcType != 316 && npcType != 312 && npcType != 334 && npcType != 324 && npcType != 314 && npcType != 326 && npcType != 2067 && npcType != 2068)
			c.npcClickIndex = 0;
		c.clickNpcType = 0;
		
		switch(npcType) {
			case 1702:
				c.getPA().openUpBank();
			break;
			case 316://bait
				c.getFishing().setupFishing(335);
			break;
			case 312://harpoon
				c.getFishing().setupFishing(359);
			break;
			case 334: //sharks
				c.getFishing().setupFishing(383);
			break;
			case 324: //lobs
				c.getFishing().setupFishing(359);
			break;
			case 2067: //sea turtle
				c.getFishing().setupFishing(395);
			break;
			case 537:
				if(c.memberStatus == 0) {
					c.getShops().openShop(49);
				}
				if(c.memberStatus == 1) {
					c.getShops().openShop(50);
				}
				if(c.memberStatus == 2) {
					c.getShops().openShop(51);
				}
				if(c.memberStatus == 3) {
					c.getShops().openShop(52);
				}
			break;
			case 522:
			case 523:
			case 520:
				c.getShops().openShop(1);
			break;
			case 540:
				c.getShops().openShop(48);
			break;
			case 945:
				c.getDH().sendDialogues(18004, npcType);
			break;
			case 70:
				c.getShops().openShop(22);
			break;
			case 553:
				c.getShops().openShop(2);
			break;
			case 1597:
				c.getShops().openShop(53);
			break;
			case 1039:
				c.getShops().openShop(30);
			break;
			case 546:
				c.getShops().openShop(27);
			break;
			case 541:
				c.getShops().openShop(26);
			break;
			case 2824:
				c.getShops().openShop(25);
			break;
			case 570:
				c.getShops().openShop(24);
			break;
			case 587:
				c.getShops().openShop(22);
			break;
			case 534:
				c.getShops().openShop(21);
			break;
			case 2270:
				c.getShops().openShop(20);
			break;
			case 1282:
				c.getDH().sendDialogues(10012,npcType);
			break;
			case 3788:
				c.getShops().openShop(44);
			break;
			case 3789:
				c.getShops().openShop(45);
			break;
			case 3787:
				c.getShops().openShop(46);
			break;
			case 494:
			if(c.inWild() || c.isInHighRiskPK() || c.inFaladorPvP()) {
					c.sendMessage("You can't do this in the wilderness!");
				} else {
				c.getPA().openUpBank();
			}
			break;
			/*case 904:
				c.getShops().openShop(17);
			break;*/
			case 461:
				c.getShops().openShop(2);
			break;
			
			case 683:
				c.getShops().openShop(3);
			break;
			
			case 549:
				c.getShops().openShop(28);
			break;
			
			case 519:
				c.getShops().openShop(8);
			break;

			case 1:
			case 9:
			case 18:
			case 20:
			case 26:
			case 21:
			case 187:
				c.getThieving().stealFromNPC(npcType);
			break;
			default:
				ScriptManager.callFunc("npcClick2_"+npcType, c, npcType);
				if(c.playerRights == 3) 
					Misc.println("Second Click Npc : "+npcType);
				break;
			
		}
	}
	
	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch(npcType) {
			case 70:
				//c.getDH().sendDialogues(31, npcType);
				c.getShops().openShop(22);
			break;
			case 2575:
				c.getDH().sendDialogues(18004, npcType);
			break;
			case 1596:
				c.getShops().openShop(18);
			break;
			case 1597:
				c.getShops().openShop(6);
			break;
			default:
				ScriptManager.callFunc("npcClick3_"+npcType, c, npcType);
				if(c.playerRights == 3) 
					Misc.println("Third Click NPC : "+npcType);
				break;

		}
	}
	

}