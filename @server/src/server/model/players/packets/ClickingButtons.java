package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.items.GameItem;
import server.model.players.Client;
import server.model.players.SkillMenu;
import server.model.players.PacketType;
import server.model.players.PlayerHandler;
import server.util.Misc;
import server.model.players.SkillGuides;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {
public int gonext;
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0, packetSize);
		c.printPacketLog("Player clicked action button id " + actionButtonId);
		c.sendMessage("button: "+actionButtonId);
		//int actionButtonId = c.getInStream().readShort();
		if (c.isDead)
			return;
		/*if((System.currentTimeMillis() - c.foodDelay >= 10000)){
			c.foodDelay = System.currentTimeMillis();
			c.sendMessage("Please wait 2 seconds...");
			System.out.println("test");*/
		if(c.playerRights >= 3)	
			System.out.println(c.playerName+ " - actionbutton: "+actionButtonId);
		if (c.getPresets().clickButton(actionButtonId)) {
			return;
		}
		switch (actionButtonId){
			//crafting + fletching interface:
			case 150:
				if (c.autoRet == 0)
					c.autoRet = 1;
				else 
					c.autoRet = 0;
			break;
			//1st tele option
			
		case 33206:
			if(c.gameMode == 1){
c.outStream.createFrame(27);
c.attackSkill = true;
c.strengthSkill = false;
c.mageSkill = false;
c.rangeSkill = false;
c.defenceSkill = false;
c.prayerSkill = false;
c.healthSkill = false;
} else if(c.gameMode == 2){
c.forcedText = "[SkillChat] My Attack level is "+c.playerLevel[0]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
}
break;
case 33209:
			if(c.gameMode == 1){
c.outStream.createFrame(27);
c.strengthSkill = true;
c.attackSkill = false;
c.mageSkill = false;
c.rangeSkill = false;
c.defenceSkill = false;
c.prayerSkill = false;
c.healthSkill = false;
} else if(c.gameMode == 2){
c.forcedText = "[SkillChat] My Strength level is "+c.playerLevel[2]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
}
break;
case 33212:
if(c.gameMode == 1){
c.outStream.createFrame(27);
c.defenceSkill = true;
c.attackSkill = false;
c.strengthSkill = false;
c.mageSkill = false;
c.rangeSkill = false;
c.prayerSkill = false;
c.healthSkill = false;
} else if(c.gameMode == 2){
c.forcedText = "[SkillChat] My Defence level is "+c.playerLevel[1]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
}
break;
case 33215:
if(c.gameMode == 1){
c.outStream.createFrame(27);
c.rangeSkill = true;
c.attackSkill = false;
c.strengthSkill = false;
c.mageSkill = false;
c.defenceSkill = false;
c.prayerSkill = false;
c.healthSkill = false;
} else if(c.gameMode == 2){
c.forcedText = "[SkillChat] My Ranged level is "+c.playerLevel[4]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
}
break;
case 33218:
if(c.gameMode == 1){
c.outStream.createFrame(27);
c.prayerSkill = true;
c.attackSkill = false;
c.strengthSkill = false;
c.mageSkill = false;
c.rangeSkill = false;
c.defenceSkill = false;
c.healthSkill = false;
} else if(c.gameMode == 2){
c.forcedText = "[SkillChat] My Prayer level is "+c.playerLevel[5]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
}
break;
case 33221:
if(c.gameMode == 1){
c.outStream.createFrame(27);
c.mageSkill = true;
c.attackSkill = false;
c.strengthSkill = false;
c.rangeSkill = false;
c.defenceSkill = false;
c.prayerSkill = false;
c.healthSkill = false;
} else if(c.gameMode == 2){
c.forcedText = "[SkillChat] My Magic level is "+c.playerLevel[6]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
}
break;
case 33207:
if(c.gameMode == 1){
c.outStream.createFrame(27);
c.healthSkill = true;
c.attackSkill = false;
c.strengthSkill = false;
c.mageSkill = false;
c.rangeSkill = false;
c.defenceSkill = false;
c.prayerSkill = false;
} else if(c.gameMode == 2){
c.forcedText = "[SkillChat] My Hitpoints level is "+c.playerLevel[3]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
}
break;

		case 28215:
			c.targetLocation();
		break;

			/*case 30298:
				c.getPA().vengOther();
			break;*/
			/*case 7338:
			c.getPA().sendFrame126("www.foreverpkers.netai.net", 12000);
			break;*/
			
			case 4135:
				if(c.getItems().playerHasItem(561,1) && c.getItems().playerHasItem(555,2) && c.getItems().playerHasItem(557,2)) {
					if (c.getItems().playerHasItem(1963, 1)) {
						c.getItems().deleteItem(1963, 1);
						c.getItems().addItem(526, 1);
					} else {
						c.sendMessage("You don't have any bananas!");
					}
				} else {
					c.sendMessage("You don't have the runes required for this spell!");
				}
			break;
			
			case 9190:
				if (c.dialogueAction == 82)
				{
					c.getDH().sendDialogues(568,1);
				}					


				if(c.dialogueAction == 9002) { //shopping - gear
					c.getDH().sendDialogues(9003, c.npcType);
					}
				if(c.dialogueAction == 9003) { //shoppinggear - Pure
					c.getDH().sendDialogues(9004, c.npcType);
					}
				if(c.dialogueAction == 9008) { //shoppingrunes - AllRunes
					c.getDH().sendDialogues(9009, c.npcType);
					}
				if(c.dialogueAction == 9012) { //shoppingpotions - SuperSet
					c.getDH().sendDialogues(9013, c.npcType);
					}
			if(c.dialogueAction == 956) {
			c.betAmount = 5000;
			c.getDH().sendDialogues(957, 0);
			}
				if (c.teleAction == 1) {
					//rock crabs
					c.getPA().spellTeleport(2676, 3715, 0);
				} else if (c.teleAction == 3) {
					
				} else if (c.teleAction == 4) {
					c.getPA().spellTeleport(3244, 3518, 0);
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(3046,9779,0);
				} else if (c.teleAction == 20) {
					//lum
					c.getPA().spellTeleport(3222, 3218, 0);//3222 3218 
				}
				
				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2845, 4832, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 11) {
					c.getPA().spellTeleport(2786, 4839, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 12) {
					c.getPA().spellTeleport(2398, 4841, 0);
					c.dialogueAction = -1;
				}
				break;
				//mining - 3046,9779,0
			//smithing - 3079,9502,0


			//2nd tele option
			case 9191:
			if(c.dialogueAction == 956) {
			c.betAmount = 25000;
			c.getDH().sendDialogues(957, 0);
			}
			if (c.dialogueAction == 82)
			{
				c.getDH().sendDialogues(577,1);
			}
				if (c.teleAction == 1) {
					//tav dungeon
					c.getPA().spellTeleport(2884, 9798, 0);
				}				else if (c.teleAction == 3) {
					//kbd
					
				} else if (c.teleAction == 4) {
					//graveyard
					c.getPA().spellTeleport(3087, 3513, 0);
					
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(3079,9502,0);
				
				} else if (c.teleAction == 20) {
					c.getPA().spellTeleport(3210,3424,0);//3210 3424
				}
				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2796, 4818, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 11) {
					c.getPA().spellTeleport(2527, 4833, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 12) {
					c.getPA().spellTeleport(2464, 4834, 0);
					c.dialogueAction = -1;
				}
				break;
			//3rd tele option	



			case 9192:
			if(c.dialogueAction == 956) {
			c.betAmount = 100000;
			c.getDH().sendDialogues(957, 0);
			}
				if (c.teleAction == 1) {
					//slayer tower
					c.getPA().spellTeleport(3428, 3537, 0);
				} else if (c.teleAction == 3) {
					//dag kings
					//chaos elemental
					
				} else if (c.teleAction == 4) {
				
								//varrock wildy
					c.getPA().spellTeleport(2539, 4716, 0);	
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(2813,3436,0);
				}
				 else if (c.teleAction == 20) {
					c.getPA().spellTeleport(2757,3477,0);
				}

				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2713, 4836, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 11) {
					c.getPA().spellTeleport(2162, 4833, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 12) {
					c.getPA().spellTeleport(2207, 4836, 0);
					c.dialogueAction = -1;
				}
			else if (c.dialogueAction == 82)
				{
					c.getDH().sendDialogues(579,1);
				}
				break;
			//4th tele option
			case 2470:
			if(c.dialogueAction == 555) {
				c.getPA().spellTeleport(2660, 4839, 0);
			}
			break;
			case 9193:
			if(c.dialogueAction == 956) {
			c.betAmount = 250000;
			c.getDH().sendDialogues(957, 0);
			}
			
				if (c.teleAction == 1) {
					//brimhaven dungeon
					c.getPA().spellTeleport(2710, 9466, 0);
				} else if (c.teleAction == 3) {
					
				} else if (c.teleAction == 4) {
					//Fala
				
	//Hillz
	c.getPA().spellTeleport(3351, 3659, 0);
				} else if (c.teleAction == 5) {
					c.getPA().spellTeleport(2724,3484,0);
					c.sendMessage("For magic logs, try north of the duel arena.");
				}
				if (c.dialogueAction == 10) {
					c.getPA().spellTeleport(2660, 4839, 0);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 11) {
					//c.getPA().spellTeleport(2527, 4833, 0); astrals here
					c.getRunecrafting().craftRunes(2489);
					c.dialogueAction = -1;
				} else if (c.dialogueAction == 12) {
					//c.getPA().spellTeleport(2464, 4834, 0); bloods here
					c.getRunecrafting().craftRunes(2489);
					c.dialogueAction = -1;
				
				} else if (c.teleAction == 20) {
					c.getPA().spellTeleport(2964,3378,0);
				}
				break;
			//5th tele option
			case 9194:
				c.getPA().removeAllWindows();
				switch(c.dialogueAction){
				//in case I forget.. Add case here for dialogue action 'case ID:', break;

				}
				break;
			
			case 71074:
				if (c.clanId >= 0) {
					if (Server.clanChat.clans[c.clanId].owner.equalsIgnoreCase(c.playerName)) {
						Server.clanChat.sendLootShareMessage(c.clanId, "Lootshare has been toggled to " + (!Server.clanChat.clans[c.clanId].lootshare ? "on" : "off") + " by the clan leader.");
						Server.clanChat.clans[c.clanId].lootshare = !Server.clanChat.clans[c.clanId].lootshare;
					} else {
						c.sendMessage("Only the owner of the clan has the power to do that.");
						if(Server.clanChat.clans[c.clanId].name.equalsIgnoreCase("Help"))
						c.sendMessage("Lootshare is currently on.");
						else
						c.sendMessage("Lootshare is currently " + (Server.clanChat.clans[c.clanId].lootshare ? "on" : "off") + ".");
					}
				}	
			break;
			case 34185: case 34184: case 34183: case 34182: case 34189: case 34188: case 34187: case 34186: case 34193: case 34192: case 34191: case 34190:
				if (c.craftingLeather)
					c.getCrafting().handleCraftingClick(actionButtonId);
				if (c.getFletching().fletching)
					c.getFletching().handleFletchingClick(actionButtonId);
			break;
			
			case 15147:
				if (c.smeltInterface) {
					c.smeltType = 2349;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 15151:
				if (c.smeltInterface) {
					c.smeltType = 2351;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			
			case 15159:
				if (c.smeltInterface) {
					c.smeltType = 2353;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 15163://gold
				if(c.smeltInterface) {
					c.smeltType = 2357;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			case 15155://silver
				if(c.smeltInterface) {
					c.smeltType = 2355;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 29017:
				if (c.smeltInterface) {
					c.smeltType = 2359;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 29022:
				if (c.smeltInterface) {
					c.smeltType = 2361;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			
			case 29026:
				if (c.smeltInterface) {
					c.smeltType = 2363;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;
			case 58253:
			//c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;
			
			case 59004:
			c.getPA().removeAllWindows();
			break;
			
			case 70212:
				if (c.clanId > -1)
					Server.clanChat.leaveClan(c.playerId, c.clanId);
				else
					c.sendMessage("You are not in a clan.");
			break;
			case 62137:
				if (c.clanId >= 0) {
					c.sendMessage("You are already in a clan.");
					break;
				} else 
				if (c.getOutStream() != null) {
					c.getOutStream().createFrame(187);
					c.flushOutStream();
				}	
			break;
			

			case 9178://first click sendOption4
			if(c.dialogueAction == 606) {
				c.getPA().startTeleport(3015, 3631, 0, "modern"); //black warriors fortress ::multi teleport
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 2223) {
				String type = c.playerMagicBook == 0 ? "modern" : "ancient";
				c.getPA().startTeleport(Config.HOME_X, Config.HOME_Y, 0, type);
			}
			if (c.dialogueAction == 2224) {
				if(c.chatClickDelay <= 0) {
					c.chatClickDelay = 3;
					c.getDH().sendDialogues(9033, 1);//bosses
				}
			}
			if (c.dialogueAction == 9046){
			if (c.chatClickDelay <= 0) {
						c.sendMessage("@red@Travel through the cave to find the Dagannoth Kings.. Beware!");
						c.getPA().startTeleport(2442, 10147, 0, "modern");
					}
			}
			if (c.dialogueAction == 19011){
					c.getShops().openShop(22);
					c.sendMessage("You have @blu@"+c.taskPoints+"@bla@ Slayer points.");
					}
			if (c.dialogueAction == 2225) {
				String type = c.playerMagicBook == 0 ? "modern" : "ancient";
				c.getPA().startTeleport(2965, 3379, 0, type);
			}
			if (c.dialogueAction == 18000) {
				c.yellColor = "@blu@@blu@";
				c.getPA().removeAllWindows();
				
			}
			if (c.dialogueAction == 19000) {
					if(c.dPortalDelay <= System.currentTimeMillis()) {
						c.dPortalDelay = System.currentTimeMillis() + (1000*60*(((4-c.memberStatus)*5)+5));
						c.getPA().startTeleport(3003, 3407, 0, "modern");
					} else {
						int timeLeft = (int) ((c.dPortalDelay - System.currentTimeMillis()) / 60000);
						c.sendMessage("You can use this portal again in "+timeLeft+" minutes.");
						c.getPA().closeAllWindows();
					}
			}
			if (c.dialogueAction == 18002) {
				c.gameMode = 1;
				c.sendMessage("You're now ready to go! Click your stats to change them, go to ::shops for items");
				c.getPA().removeAllWindows();
				
			}
			if (c.dialogueAction == 18003) {
				c.getPA().startTeleport(2347, 4580, 0, "modern");
				c.getPA().removeAllWindows();
				
			}
			
			if (c.dialogueAction == 10500) {
						c.getDH().sendDialogues(10501, c.npcType);
			}
			
			if (c.dialogueAction == 10504) {
			if (c.pkChallenge >= 10){
					c.pkChallenge = (c.pkChallenge - 10);
					c.getItems().addItem(c.randomWeapons2(), 1);
					} else {
					c.sendMessage("You need at least 10 Challenge points to do this gamble.");
				}
			}
			
			if (c.dialogueAction == 10505) {
			if (c.pkChallenge >= 40){
					c.pkChallenge = (c.pkChallenge - 40);
					c.getItems().addItem(c.randomWeapons3(), 1);
					} else {
					c.sendMessage("You need at least 40 Challenge points to do this gamble.");
				}
			}
			
				if (c.dialogueAction == 19999) {
					if(c.KC > 499) {
							c.getDH().sendDialogues(10018, c.npcType);
							c.getItems().addItem(20763, 1);
							c.getItems().addItem(20764, 1);
						} else {
							c.getDH().sendDialogues(20002, c.npcType);
					}
				}
				
				if( c.dialogueAction == 10023){
					c.getDH().sendDialogues(10024, c.npcType);
				}
				
				if (c.dialogueAction == 10022){
					c.sendMessage("@dre@You have @red@"+c.pandPoints+"@dre@ Pandemonium points to spend of @red@"+c.totalPandPoints+"@dre@ total.");
					c.getShops().openShop(16);
				}
	
				if (c.dialogueAction == 10004 || c.dialogueAction == 10000){
					c.getDH().sendDialogues(10003, 0);
				}
				
				if (c.dialogueAction == 10012){
					c.getShops().openShop(28);
				}
				
				if (c.dialogueAction == 10013){
					c.getShops().openShop(2);
				}

				if (c.usingGlory)
					c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0, "modern");
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(3428, 3538, 0, "modern");
				if (c.dialogueAction == 3)		
					c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0, "modern");
				if (c.dialogueAction == 4)
					c.getPA().startTeleport(3565, 3314, 0, "modern");
				if (c.dialogueAction == 20) {
					c.getPA().startTeleport(2897, 3618, 4, "modern");
					c.killCount = 0;
				}
				if(c.dialogueAction == 18011) {
					c.gameMode = 1;
					c.sendMessage("@gre@Success! @bla@You are now an instant PK account!");
					c.sendMessage("@red@You can now change your stats by clicking them in your stats tab.");
					c.getPA().removeAllWindows();
					
				}
				/*if (c.dialogueAction == 14) { //fortress
					c.getPA().startTeleport(3023, 3631, 0, "modern");
				}*/
				if (c.dialogueAction == 15) { //varrock pk
					if (c.pkPoints >= 10){
						c.getPA().startTeleport(Config.VARROCK_X + Misc.random(1), Config.VARROCK_Y + Misc.random(1), 0, "modern");
						c.sendMessage("Welcome to @red@Varrock PK@bla@. Defeating enemy players will steal @red@10@bla@ of their PK Points.");
					} else {
						c.getDH().sendDialogues(26,0);
					}
				}
				if (c.dialogueAction == 14) { //Hills
					c.getPA().startTeleport(3296, 3650, 0, "modern");
				}
				if (c.dialogueAction == 24) { 
					if(c.chatClickDelay <= 0){ //bear
						c.getPA().startTeleport(2347, 4580, 0, "modern");
						c.getPA().removeAllWindows();
					}
				}
				if (c.dialogueAction == 31) { //varrock mine
					c.getPA().startTeleport(3286, 3366, 0, "modern");
				}

				if (c.dialogueAction == 32 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 10){
						c.pkPoints = (c.pkPoints - 10);
						c.getItems().addItem(995, 250000);
				} else {
					c.sendMessage("You need at least 10 PK Points to do this gamble.");
				}
				}

				if (c.dialogueAction == 35 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 25 && c.memberStatus > 0){
					c.pkPoints = (c.pkPoints - 25);
					c.getItems().addItem(10926, 1);
					} else {
					c.sendMessage("You need at least 25 PK Points to do this gamble.");
				}
				}


				if (c.dialogueAction == 34 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 250){
					c.pkPoints = (c.pkPoints - 250);
					c.getItems().addItem(c.randomVoid(), 1);
					} else {
					c.sendMessage("You need at least 250 PK Points to do this gamble.");
				}
				}

				if (c.dialogueAction == 33 && c.getItems().freeSlots() > 0 && c.chatClickDelay <= 0) {
					if (c.pkPoints >= 3500){
						c.pkPoints = (c.pkPoints - 3500);
						c.getItems().addItem(c.randomGodswords(), 1);
						} else {
						c.sendMessage("You need at least 3500 PK Points to do this gamble.");
					}
				}	
				
				if (c.dialogueAction == 9001) {
					c.getDH().sendDialogues(9022,c.npcType);
				}	
				if (c.dialogueAction == 9033) {//black knight titan
					if (c.chatClickDelay <= 0) {
					c.getPA().startTeleport(2884, 9798, 0, "modern");
					c.sendMessage("@blu@Continue through the dungeon to find the Black Knight Titan.");
					}
				}	
				if (c.dialogueAction == 600) {
					if (c.inWild() || c.safeTimer > 0){
						c.sendMessage("You can't use this in the wilderness!");
					} else {
						c.getPA().startTeleport(2150, 5094, 0, "modern");
						c.sendMessage("You teleport to Fun PK.");
					}
				}
				break;
			case 9179://second click sendOption4
			if(c.dialogueAction == 606) {
					c.getPA().startTeleport(2958, 3821, 0, "modern"); //chaos temple ::multi teleport
					c.getPA().removeAllWindows();
			}
				if(c.dialogueAction == 2223) {
					if(c.inDuel2() || c.inPits || c.isInHighRiskPK()) 
						return;
					if (c.inWild() || c.safeTimer > 0){
						c.sendMessage("You can't use this in the wilderness!");
					} else {
						c.sendMessage("@blu@You teleport to the shop area.");
						c.getPA().startTeleport(2757, 3502, 0, "modern");//I
						if(c.doingStarter == 1) {
							if(c.sTask1 == 0) {
								c.sTask1 = 1;
								c.sendMessage("You completed a starter task!");
								c.getItems().addItem(995,100000);
							}
						}
					}
				}
				if (c.dialogueAction == 9046){
				if (c.chatClickDelay <= 0) {
						c.getBroodoo().startUp();
  						c.getPA().removeAllWindows();
						}
				}
				if (c.dialogueAction == 19011){
					if(c.taskAmount <= 0)
						c.findTask();
					else {
						c.getDH().sendDialogues(19013, 70);
						c.sendMessage("Your assignment is to slay "+c.taskAmount+" "+c.getTaskName()+"s.");
						}
				}
				if (c.dialogueAction == 19000) {
					if(c.dPortalDelay <= System.currentTimeMillis()) {
						c.dPortalDelay = System.currentTimeMillis() + (1000*60*(((4-c.memberStatus)*5)+5));
						c.getPA().startTeleport(1913, 4367, 0, "modern");
					} else {
						int timeLeft = (int) ((c.dPortalDelay - System.currentTimeMillis()) / 60000);
						c.sendMessage("You can use this portal again in "+timeLeft+" minutes.");
						c.getPA().closeAllWindows();
					}
				}
				if (c.dialogueAction == 18005) {
					c.getDH().sendDialogues(18006, c.npcType);
				}
				if(c.dialogueAction == 18011) {
					c.gameMode = 1;
					c.sendMessage("@gre@Success! @bla@You are now an instant PK account!");
					c.sendMessage("@red@You can now change your stats by clicking them in your stats tab.");
					c.getPA().removeAllWindows();
					
				}
				if(c.dialogueAction == 10500) {
					c.getShops().openShop(53);
				}
				if (c.dialogueAction == 10504) {
					if (c.pkChallenge >= 10){
					c.pkChallenge = (c.pkChallenge - 10);
					c.getItems().addItem(c.randomArmor2(), 1);
					} else {
					c.sendMessage("You need at least 10 Challenge points to do this gamble.");
				}
			}
				if (c.dialogueAction == 10505) {
					if (c.pkChallenge >= 40){
					c.pkChallenge = (c.pkChallenge - 40);
					c.getItems().addItem(c.randomArmor3(), 1);
					} else {
					c.sendMessage("You need at least 40 Challenge points to do this gamble.");
				}
			}
				if(c.dialogueAction == 2224) {
				if(c.chatClickDelay == 0){
					c.getPA().startTeleport(2400, 5179, 0, "modern");
					}
					
				}
				if (c.dialogueAction == 18000) {
					c.yellColor = "@red@@red@";
					c.getPA().removeAllWindows();
					
				}
				if(c.dialogueAction == 18008) {
				for (int j = 0; j < c.playerEquipment.length; j++) {
						if (c.playerEquipment[j] > 0) {
							c.sendMessage("@red@Please remove all your equipment before using this feature.");
							c.getPA().removeAllWindows();
							return;
						}
					}
					for (int x = 0; x < 7; x++) {
					c.playerXP[x] = c.getPA().getXPForLevel(1);
					c.playerLevel[x] = c.getPA().getLevelForXP(c.playerXP[x]);
					c.getPA().refreshSkill(x);
					}
					c.playerXP[3] = c.getPA().getXPForLevel(10);
					c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]+1);
					c.getPA().refreshSkill(3);
					c.getPA().requestUpdates();
					c.gameMode = 2;
					c.sendMessage("@gre@Success! @bla@You are now a trained account!");
					c.getPA().removeAllWindows();
					
				}
				if(c.dialogueAction == 18009) {
					c.getDH().sendDialogues(18011,-1);
					
				}
				if (c.dialogueAction == 18002) {
					c.gameMode = 1;
					c.sendMessage("You're now ready to go! Click your stats to change them, go to ::shops for items");
					c.dialogueAction = -1;
					c.getPA().removeAllWindows();
					
				}
				if (c.dialogueAction == 18003) {
					c.getPA().startTeleport(2074 + Misc.random(1), 3160 + Misc.random(1), 0, "modern");
					c.getPA().removeAllWindows();
					
				}
				if (c.dialogueAction == 19999) {
					if(c.KC >= 1000 && c.playerLevel[18] == 99 && c.hStreak >= 50) {
						if(c.daFirstTime != 0) {
								c.getDH().sendDialogues(20001,c.npcType);
							} else {
								c.getDH().sendDialogues(20000, c.npcType);
						}
						} else {
							c.getDH().sendDialogues(20000, c.npcType);
					}
				}
				if (c.usingGlory) {
					c.getPA().startTeleport(Config.AL_KHARID_X, Config.AL_KHARID_Y, 0, "modern");
				}
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(2884, 3395, 0, "modern");
				if (c.dialogueAction == 3)
					c.getPA().startTeleport(3243, 3513, 0, "modern");
				if (c.dialogueAction == 4)
					c.getPA().startTeleport(2444, 5170, 0, "modern");
				if (c.dialogueAction == 20) {
					c.getPA().startTeleport(2897, 3618, 12, "modern");
					c.killCount = 0;
				}
				
				if (c.dialogueAction == 10022){
					c.getDH().sendDialogues(0, 0);
					c.getPA().showInterface(8134);
					c.getPA().sendFrame126("@red@Pandemonium Mission Guide@bla@",8144);
					c.getPA().sendFrame126("-- @red@Undead Standoff@bla@ --",8145);
					c.getPA().sendFrame126("The main objective of the mission is to get as many kills as",8147);
					c.getPA().sendFrame126("possible before quitting or dieing. Note: You keep items.",8148);
					c.getPA().sendFrame126("In the mission, you fight @red@Summoned Zombies@bla@ primarily,",8149);
					c.getPA().sendFrame126("and other undead creatures such as @red@Skeleton Hellhounds@bla@",8150);
					c.getPA().sendFrame126("and @red@Giant Skeletons@bla@. As the mission progresses and",8151);
					c.getPA().sendFrame126("your killcount gets higher, the monsters you fight",8152);
					c.getPA().sendFrame126("hit harder, and have more health than before.",8153);
					c.getPA().sendFrame126("Utilize tactics to confuse your enemies",8154);
					c.getPA().sendFrame126("and keep yourself on the battlefield longer. Good luck.",8155);
					for(int i = 0; i < 100; i++ )c.getPA().sendFrame126(" ",8156 + i);
				}
				
				
				if (c.dialogueAction == 10012){
					c.getShops().openShop(3);
				}
				
				if (c.dialogueAction == 10013){
					c.getShops().openShop(27);
				}

				if (c.dialogueAction == 10000 || c.dialogueAction == 10004){
					c.getDH().sendDialogues(10007, 0);
				}
				
				if (c.dialogueAction == 24) {
					c.getPA().startTeleport(2884, 9798, 0, "modern"); //taverly dragon dungeon
					c.sendMessage("@blu@Go through the pipe to access the Black dragons.");
				}
				
				if (c.dialogueAction == 15) { //fortress
					c.getPA().startTeleport(3023, 3631, 0, "modern");
				}
				if (c.dialogueAction == 14) { //chest
					//c.getPA().startTeleport(3093, 3868, 0, "modern");
					c.getPA().startTeleport(2541 + Misc.random(1), 4714 + Misc.random(1), 0, "modern");
					c.sendMessage("@red@The Wilderness chest is now located inside the Mage arena in the wilderness!");	
				}
				if (c.dialogueAction == 31) { //mining guild
					c.getPA().startTeleport(3040, 9741, 0, "modern");
				}	
				if (c.dialogueAction == 32) {
					//c.getShops().openShop(32);
					/*int reward = c.randomNewPKPGamble();
					if(c.pkPoints > 249) {
						if(c.getItems().freeSlots() > 0) {
						c.getItems().addItem(reward,1);
						c.pkPoints -= 250;
						if((c.getShops().getItemShopValue(reward)) >= 20000000)
							c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+"@red@ just received a @dre@"+c.getItems().getItemName(reward)+"@red@ from the PK Point gamble!");
						} else {
							c.sendMessage("You need more inventory space.");
							c.getPA().closeAllWindows();
						}
					} else {
						c.sendMessage("You don't have enough PK Points to do this gamble!");
						c.getPA().closeAllWindows();
					}*/
					c.getDH().sendDialogues(29,c.npcType);
					c.chatClickDelay = 3;
				}

				if (c.dialogueAction == 34 && c.getItems().freeSlots() > 0) {
				int reward = c.randomNewPKPGamble();
					if(c.pkPoints >= 1000) {
						if(c.getItems().freeSlots() > 0) {
						c.getItems().addItem(reward,1);
						c.pkPoints -= 1000;
						if((c.getShops().getItemShopValue(reward)) >= 20000000)
							c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+"@red@ just received a @dre@"+c.getItems().getItemName(reward)+"@red@ from the PK Shop gamble!");
						} else {
							c.sendMessage("You need more inventory space.");
							c.getPA().closeAllWindows();
						}
					} else {
						c.sendMessage("You need 1000 PK Points to do this gamble!");
						c.getPA().closeAllWindows();
					}
				/*if (c.pkPoints >= 10){
					c.pkPoints = (c.pkPoints - 10);
					c.getItems().addItem(10925, 1);
					} else {
					c.sendMessage("You need at least 10 PK Points to do this gamble.");
				}*/
				}


				if (c.dialogueAction == 33 && c.getItems().freeSlots() > 0 && c.chatClickDelay <= 0) {
					if (c.pkPoints >= 850){
							c.pkPoints = (c.pkPoints - 850);
							c.getItems().addItem(c.randomArmor(), 1);
						} else {
							c.sendMessage("You need at least 850 PK Points to do this gamble.");
					}
				}

				if (c.dialogueAction == 35 && c.getItems().freeSlots() > 0) {
				if (c.getItems().playerHasItem(4067, 1)){
					c.getItems().deleteItem(4067, c.getItems().getItemSlot(4067), 1);
					c.getItems().addItem(10926, 10);
					} else {
					c.sendMessage("You need at least 1 Donator Ticket to do this gamble.");
				}
				}
				
					if(c.dialogueAction == 9001) {//exchange
					c.getDH().sendDialogues(9002, c.npcType);
					}
				if (c.dialogueAction == 9033) {
					if (c.chatClickDelay <= 0)
						c.getPA().startTeleport(3005, 3850, 0, "modern");
				}
				
				if (c.dialogueAction == 600) {
					c.getPA().startTeleport(2400, 5179, 0, "modern");
					c.sendMessage("You teleport to Fight Pits.");
				}	
				
			break;
			
			case 9180://third click sendOption4
			if(c.dialogueAction == 606) {
					c.getPA().startTeleport(3287, 3883, 0, "modern"); //demonic ruins ::multi teleport
					c.getPA().removeAllWindows();
			}
			if(c.dialogueAction == 2223) {
				c.getDH().sendDialogues(24, c.npcType);
				c.chatClickDelay = 3;
				if(c.doingStarter == 1) {
					if(c.sTask3 == 0) {
						c.sTask3 = 1;
						c.getItems().addItem(995,100000);
					}
				}
			}
			if(c.dialogueAction == 18011) {
				c.getPA().removeAllWindows();
			}

			if(c.dialogueAction == 34) {
				c.getPA().removeAllWindows();
			}
				
			if (c.dialogueAction == 19011){
					if(c.taskAmount <= 0)
						c.findBossTask();
					else {
						c.getDH().sendDialogues(19013, 70);
						c.sendMessage("Your assignment is to slay "+c.taskAmount+" "+c.getTaskName()+"s.");
						}
				}
			if(c.dialogueAction == 18008) {
			
					c.getPA().removeAllWindows();
					
				}
				if (c.dialogueAction == 19001) {
					c.getPA().startTeleport(3232, 3940, 0, "modern");
					c.sendMessage("@red@Go kill that mole!");
				}
				if (c.dialogueAction == 19000) {
					if(c.dPortalDelay <= System.currentTimeMillis()) {
						c.dPortalDelay = System.currentTimeMillis() + (1000*60*(((4-c.memberStatus)*5)+5));
						c.getPA().startTeleport(2405, 4392, 0, "modern");
					} else {
						int timeLeft = (int) ((c.dPortalDelay - System.currentTimeMillis()) / 60000);
						c.sendMessage("You can use this portal again in "+timeLeft+" minutes.");
						c.getPA().closeAllWindows();
					}
				}
				if(c.dialogueAction == 18009) {
					for (int j = 0; j < c.playerEquipment.length; j++) {
						if (c.playerEquipment[j] > 0) {
							c.sendMessage("@red@Please remove all your equipment before using this feature.");
							c.getPA().removeAllWindows();
							return;
						}
					}
					c.sendMessage("@gre@Success!@bla@ Defence level reset to 1.");
					c.getPA().requestUpdates();
					c.playerXP[1] = c.getPA().getXPForLevel(1);
					c.playerLevel[1] = c.getPA().getLevelForXP(c.playerXP[1]);
					c.getPA().refreshSkill(1);
					c.getPA().removeAllWindows();
					
				}
				if(c.dialogueAction == 18005) {
				
					c.getPA().removeAllWindows();
					
				}
				
			if(c.dialogueAction == 10500) {
				c.getDH().sendDialogues(10504, 0);
				c.chatClickDelay = 3;
			}
			if (c.dialogueAction == 10504) {
			if(c.chatClickDelay <= 0){
			if (c.pkChallenge >= 5){
					c.pkChallenge = (c.pkChallenge - 5);
					c.getItems().addItem(c.randomAccessories(), 1);
					} else {
					c.sendMessage("You need at least 5 Challenge points to do this gamble.");
				}
				}
			}
			if (c.dialogueAction == 10505) {
			if(c.chatClickDelay <= 0){
			if (c.pkChallenge >= 30){
					c.pkChallenge = (c.pkChallenge - 30);
					c.getItems().addItem(c.randomAccessories2(), 1);
					} else {
					c.sendMessage("You need at least 30 Challenge points to do this gamble.");
				}
				}
			}
			if(c.dialogueAction == 2224) {
			if(c.chatClickDelay == 0){
				c.getPA().startTeleport(2945, 3146, 0, "modern");
			}
			}
			if(c.dialogueAction == 10022){
				c.getDH().sendDialogues(10023, 0);
			}
			if(c.dialogueAction == 24) { //3rd
				c.getPA().startTeleport(2784 + Misc.random(2), 2785 + Misc.random(2), 0, "modern");
			}
			if (c.dialogueAction == 18002) {
				c.gameMode = 2;
				c.sendMessage("You choose to go the hard way... Go to ::shops to get started!");
				c.dialogueAction = -1;
				c.getPA().removeAllWindows();
				
			}
			if(c.dialogueAction == 18003) {
				c.getPA().startTeleport(2884, 9798, 0, "modern");
				c.sendMessage("@blu@Go through the pipe to access the Black dragons.");
				c.getPA().removeAllWindows();
				
			}
			if (c.dialogueAction == 18000) {
				c.yellColor = "@whi@@whi@";
				c.getPA().removeAllWindows();
				
			}
			if (c.dialogueAction == 19999) {
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if(c.dialogueAction == 10000 || c.dialogueAction == 10004){
				c.getDH().sendDialogues(10008, 0);
			}
			
			if(c.dialogueAction == 10012){
				c.getShops().openShop(26);
			}
			
			if(c.dialogueAction == 10013){
				c.getShops().openShop(21);
			}
				
			if(c.dialogueAction == 9001) {//exchange
					c.getDH().sendDialogues(9017, c.npcType);
					}
			if(c.dialogueAction == 9002) {
					c.getDH().sendDialogues(9003, c.npcType);
					}
			if(c.dialogueAction == 9003) { //shoppinggear - range
					c.getDH().sendDialogues(9006, c.npcType);
					}
			if(c.dialogueAction == 9008) { //shoppingrunes - VengRunes
					c.getDH().sendDialogues(9011, c.npcType);
					}
			if(c.dialogueAction == 9012) { //shoppingpotions - Magic
					c.getDH().sendDialogues(9015, c.npcType);
					}
				
				if (c.usingGlory)
					c.getPA().startTeleport(2916, 3168, 0, "modern");
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(2471,10137, 0, "modern");	
				if (c.dialogueAction == 3)
					c.getPA().startTeleport(3363, 3676, 0, "modern");
				if (c.dialogueAction == 4)
					c.getPA().startTeleport(2659, 2676, 0, "modern");
				if (c.dialogueAction == 20) {
					c.getPA().startTeleport(2897, 3618, 8, "modern");
					c.killCount = 0;
				}
				if (c.dialogueAction == 600) { // Duel
				c.getPA().startTeleport(3367, 3268, 0, "modern");
				}
				if (c.dialogueAction == 15) { //Mage bank
					c.getPA().startTeleport(2541 + Misc.random(1), 4714 + Misc.random(1), 0, "modern");
				}
				if (c.dialogueAction == 14) { //easts
					c.getPA().startTeleport(2986 + Misc.random(2), 3597 + Misc.random(2), 0, "modern");
				}
				if (c.dialogueAction == 31) { //falador mine
					c.getPA().startTeleport(3040, 9769, 0, "modern");
				}
				if (c.dialogueAction == 32) {
					c.getShops().openShop(18);
				}

				if (c.dialogueAction == 33 && c.getItems().freeSlots() > 0 && c.chatClickDelay <= 0) {
					if (c.pkPoints >= 1250){
						c.pkPoints = (c.pkPoints - 1250);
						c.getItems().addItem(c.randomWeapons(), 1);
						} else {
						c.sendMessage("You need at least 1250 PK Points to do this gamble.");
					}
				}
		
				if (c.dialogueAction == 34 && c.getItems().freeSlots() > 0) {
				/*if (c.pkPoints >= 50){
					c.pkPoints = (c.pkPoints - 50);
					c.getItems().addItem(995, Misc.random(25000000));
					} else {
					c.sendMessage("You need at least 50 PK Points to do this gamble.");
					}*/
				}
				
				if (c.dialogueAction == 9033) {
					if (c.chatClickDelay <= 0) {
						c.getPA().startTeleport(2442, 10147, 0, "modern");
						c.sendMessage("@blu@Travel through the cave to find the Dagannoth kings and Mother. Beware!");
					}
				}


				if (c.dialogueAction == 35 && c.getItems().freeSlots() > 0) {
				if (c.getItems().playerHasItem(4067, 1)){
					c.getItems().deleteItem(4067, c.getItems().getItemSlot(4067), 1);
					c.getItems().addItem(627, 2);
					} else {
					c.sendMessage("You need at least 1 Donator Ticket to do this gamble.");
				}
				}	
			break;
			
			case 9181://fourth click sendOption4
			if(c.dialogueAction == 606) {
					c.getPA().startTeleport(3080, 3871, 0, "modern");
					c.getPA().removeAllWindows();
			}
			if(c.dialogueAction == 2223) {
				if(c.chatClickDelay <= 0) {
					c.getDH().sendDialogues(2224, 1);
					c.chatClickDelay = 3;
				}
			}
			if(c.dialogueAction == 2224) {
				if(c.chatClickDelay <= 0)
					c.getDH().sendDialogues(2225, 1);
			}
			if(c.dialogueAction == 24) {
				if(c.chatClickDelay <= 0){
				c.getPA().startTeleport(3429 + Misc.random(2), 3538 + Misc.random(2), 0, "modern");
				c.getPA().removeAllWindows();
				}
			}
			if (c.dialogueAction == 19011){
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if (c.dialogueAction == 10022){
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if (c.dialogueAction == 18000) {
				c.yellColor = "@bla@@bla@";
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 10500) {
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 10504) {
				c.getDH().sendDialogues(10505, 1);
			}
			if (c.dialogueAction == 10505) {
				//c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 18002) {
				c.gameMode = 2;
				c.sendMessage("You choose to go the hard way... Go to ::shops to get started!");
				c.getPA().removeAllWindows();
				
			}
				if (c.dialogueAction == 19999) {
					c.talkingNpc = -1;
					c.getPA().removeAllWindows();
					c.nextChat = 0;
				}
				if(c.dialogueAction == 10012){
					c.getDH().sendDialogues(10013, c.npcType);
					c.chatClickDelay = 3;
				}
				
				if(c.dialogueAction == 10013){
					if(c.chatClickDelay == 0){
						c.talkingNpc = -1;
						c.getPA().removeAllWindows();
						c.nextChat = 0;
					}
				}
				
				if (c.dialogueAction == 32) {
					c.getShops().openShop(32);//barrow shop
				}
				
				if (c.dialogueAction == 9033){
					c.getDH().sendDialogues(9046, c.npcType);
					c.chatClickDelay = 2;
				}

				if(c.dialogueAction == 9001) {
				c.getPA().closeAllWindows();
				}
				if(c.dialogueAction == 9003) { //shoppinggear - mage
					c.getDH().sendDialogues(9007, c.npcType);
					}		
				if (c.usingGlory)
					c.getPA().startTeleport(3103, 3249, 0, "modern");
				if (c.dialogueAction == 2)
					c.getPA().startTeleport(2669,3714, 0, "modern");
				if (c.dialogueAction == 3)	
					c.getPA().startTeleport(2540, 4716, 0, "modern");
				if (c.dialogueAction == 4) {
					c.getPA().startTeleport(3366, 3266, 0, "modern");
					c.sendMessage("Dueling is at your own risk. Refunds will not be given for items lost due to glitches.");
				}
				if (c.dialogueAction == 20) {
					//c.getPA().startTeleport(3366, 3266, 0, "modern");
					//c.killCount = 0;
					c.sendMessage("This will be added shortly");
				}
				if (c.dialogueAction == 14) {
					c.getDH().sendDialogues(25, 0);
					c.packetTimer = 2;
				}
				if (c.dialogueAction == 15) {
					if(c.packetTimer <= 0)
					c.getPA().closeAllWindows();
				}
				if (c.dialogueAction == 31) { //runite ore
					c.getPA().startTeleport(3060, 3887, 0, "modern");
				}
				if (c.dialogueAction == 600) {// barrows
					c.getPA().startTeleport(3564, 3288, 0, "modern");
					c.getItems().addItem(952, 1);
				}
				if (c.dialogueAction == 32) {
					//c.getDH().sendDialogues(29, c.npcType);
					gonext = 0;
				}
				if (c.dialogueAction == 33) {
					if (gonext == 1){
					c.getDH().sendDialogues(30, c.npcType);
					c.chatClickDelay = 3;
					} else {
					gonext = gonext + 1;
					c.chatClickDelay = 3;
					}
				}
			break;
			
			case 1093:
			case 1094:
			case 1097: //Autocast Interface
				if (c.autocastId > 0) {
					c.getPA().resetAutocast();
				} else {
					if (c.playerMagicBook == 1) {
							c.setSidebarInterface(0, 1689);
					} else if (c.playerMagicBook == 0) {
						if (c.playerEquipment[c.playerWeapon] == 4170) {
							c.setSidebarInterface(0, 12050);
						} else {
							c.setSidebarInterface(0, 1829);
						}
					} else {
							c.sendMessage("@red@You cannot autocast while using the Lunar spellbook.");
					}
						
				}		
			break;
			
			case 9157://barrows tele to tunnels
				if(c.dialogueAction == 1) {
					int r = 4;
					//int r = Misc.random(3);
					switch(r) {
						case 0:
							c.getPA().movePlayer(3534, 9677, 0);
							break;
						
						case 1:
							c.getPA().movePlayer(3534, 9712, 0);
							break;
						
						case 2:
							c.getPA().movePlayer(3568, 9712, 0);
							break;
						
						case 3:
							c.getPA().movePlayer(3568, 9677, 0);
							break;
						case 4:
							c.getPA().movePlayer(3551, 9694, 0);
							break;
					}
				} else if (c.dialogueAction == 10025) {
					if (c.getItems().playerHasItem(995,250000)) {
							c.getItems().deleteItem(995,c.getItems().getItemSlot(995), 250000);
							c.getDH().sendDialogues(0, 0);
							c.getPand().startUp(0, 0, 0);
						} else {
							c.sendMessage("@red@You need 250,000 coins to start the minigame.");
							c.getDH().sendDialogues(0, 0);
					}
				} else if (c.dialogueAction == 2) {
					c.getPA().movePlayer2(2507, 4717, 0);	
				} else if (c.dialogueAction == 9048) {
					if(!c.inWild()) {
						int random = Misc.random(c.randomTeleport.length);
						c.getPA().startTeleport(c.randomTeleport[random][0],c.randomTeleport[random][1],0,"modern");
						c.sendMessage("@red@You are teleported to a random area!");
					}
				} else if(c.dialogueAction == 2222) {
					c.getItems().resetItems(3214);
					c.getPA().removeAllWindows();
				} else if (c.dialogueAction == 5) {
					c.getSlayer().giveTask();
				} else if (c.dialogueAction == 6) {
					c.getSlayer().giveTask2();
				} else if (c.dialogueAction == 7) {
					if(!c.inWild()) {
						c.getPA().startTeleport(3088,3933,0,"modern");
						c.sendMessage("NOTE: You are now in the wilderness...");
					}
				} else if (c.dialogueAction == 19014) {
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
				} else if (c.dialogueAction == 19018) {
				if(c.doingStarter == 1) {
					c.sendMessage("@blu@You've skipped the starter tasks! Good luck!");
					c.getItems().addItem(995,250000);
					c.doingStarter = 0;
				}
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
				} else if(c.dialogueAction == 10002){
					c.worshippedGod = 1;
					c.startAnimation(812);
					c.gfx0(247);
					c.sendMessage("@dre@You are now a worshipper of @blu@Saradomin");
					if(c.doingStarter == 1) {
						if(c.sTask8 == 0) {
							c.sTask8 = 1;
							c.getItems().addItem(995,100000);
							c.sendMessage("You completed a starter task!");
						}
					}
					c.talkingNpc = -1;
					c.getPA().removeAllWindows();
					c.nextChat = 0;			
				} else if(c.dialogueAction == 10006){
					c.worshippedGod = 2;
					c.startAnimation(812);
					c.gfx0(246);
					c.sendMessage("@dre@You are now a worshipper of @red@Zamorak");
					if(c.doingStarter == 1) {
						if(c.sTask8 == 0) {
							c.sTask8 = 1;
							c.getItems().addItem(995,100000);
							c.sendMessage("You completed a starter task!");
						}
					}
					c.talkingNpc = -1;
					c.getPA().removeAllWindows();
					c.nextChat = 0;
				} else if (c.dialogueAction == 27) {
					c.getPA().movePlayer2(3096, 3503, 0);
					c.monkeyk0ed = 0;
					c.forcedText = "Freedom!";
					c.forcedChatUpdateRequired = true;
					c.updateRequired = true;
				} else if(c.dialogueAction == 20001) {
					if(c.KC >= 1000 && c.playerLevel[18] == 99 && c.hStreak >= 50) {
							c.getDH().sendDialogues(10018, c.npcType);
							c.getItems().addItem(20769, 1);
							c.getItems().addItem(20770, 1);
							if(c.daFirstTime == 0) {
								c.sendAll("@red@"+c.playerName+" @yel@has just claimed the Completionist cape for the first time!");
								c.daFirstTime = 1;
							}
						} else {
							c.getDH().sendDialogues(20002, c.npcType);
					}
				} else if(c.dialogueAction == 20003){
					c.getShops().openShop(42);
				} else if(c.dialogueAction == 10009){
					c.getDH().sendDialogues(10010, 0);
				} else if (c.dialogueAction == 8) {
					c.getPA().fixAllBarrows();
					c.getPA().closeAllWindows();
				} else if (c.dialogueAction == 9040) {
					if(!c.inWild()) {
						c.getPA().startTeleport(2600+Misc.random(3), 3157+Misc.random(3), 4, "modern");
						c.inPits = false;
						c.duelStatus = 0;
						c.sendMessage("@red@You are now in high-risk PK. You cannot protect item.");
						c.getCombat().resetPrayers();
						c.getPA().resetDamageDone();
					}
				} else if (c.dialogueAction == 15002) {
					if(c.KC < 100) {
						c.sendMessage("You need to have a killcount of at least 100 to buy this!");
						return;
					}
					if(c.memberStatus < 1) {
						c.sendMessage("You need to be a Member to buy this command!");
					} else if(c.dicer == 0) {
					if (c.getItems().playerHasItem(4067,20)) {
						if(c.memberStatus >= 2){
						c.getItems().deleteItem(4067,c.getItems().getItemSlot(4067), 20);
						c.dicer = 1;
						c.sendMessage("@blu@You are now a dicer!");
					} else {
						c.sendMessage("@red@Only Super Members can buy dicing. @blu@::Donate");
					}
					} else {
						c.sendMessage("@red@You need 20 Member tickets to become a dicer. @blu@::Donate");
					}
					} else {
						c.sendMessage("@red@You are already a dicer!");
					}
				} else if (c.dialogueAction == 9042) {
					if(c.getItems().playerHasItem(4067,25)){
						if (c.getItems().freeSlots() > 0){
						int itemGat = c.randomDonator();
							c.getItems().deleteItem(4067,c.getItems().getItemSlot(4067), 25);
							c.getItems().addItem(itemGat, 1);
							c.sendMessage("@blu@The chest takes your Donator Tickets and you pull out the treasure.");
							c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@opened the Magic chest and received a @dre@"+c.getItems().getItemName(itemGat)+"@red@!");
						} else {
							c.sendMessage("@red@You need at least 1 free inventory space.");
						}
					}
				} else if (c.dialogueAction == 9044) {
					if(!c.inWild())
						c.getPA().startTeleport(2524, 4777, 0, "modern");
				} else if (c.dialogueAction == 9017) {
					c.getDH().sendDialogues(9018, c.npcType);
				} else if (c.dialogueAction == 9002) {
					c.getDH().sendDialogues(9034, c.npcType);
				} else if (c.dialogueAction == 10016){
					if(c.bankPin.equalsIgnoreCase("")){
						c.getBankPin().open();
					} else if(!c.bankPin.equalsIgnoreCase("")){
						c.getDH().sendDialogues(10017, c.npcType);
					}
				} else if (c.dialogueAction == 10020){
					c.getBankPin().changePin();
				}
				
				break;
				/**Prayers**/
			case 97168: // thick skin
			c.getCombat().activatePrayer(0);
			break;	
			case 97170: // burst of str
			c.getCombat().activatePrayer(1);
			break;	
			case 97172: // charity of thought
			c.getCombat().activatePrayer(2);
			break;	
			case 97174: // range
			c.getCombat().activatePrayer(3);
			break;
			case 97176: // mage
			c.getCombat().activatePrayer(4);
			break;
			case 97178: // rockskin
			c.getCombat().activatePrayer(5);
			break;
			case 97180: // super human
			c.getCombat().activatePrayer(6);
			break;
			case 97182:	// improved reflexes
			c.getCombat().activatePrayer(7);
			break;
			case 97184: //hawk eye
			c.getCombat().activatePrayer(8);
			break;
			case 97186:
			c.getCombat().activatePrayer(9);
			break;
			case 97188: // protect Item
			c.getCombat().activatePrayer(10);
			break;			
			case 97190: // 26 range
			c.getCombat().activatePrayer(11);
			break;
			case 97192: // 27 mage
			c.getCombat().activatePrayer(12);
			break;	
			case 97194: // steel skin
			c.getCombat().activatePrayer(13);
			break;
			case 97196: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
			case 97198: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;	
			case 97200: // protect from magic
			c.getCombat().activatePrayer(16);
			break;					
			case 97202: // protect from range
			c.getCombat().activatePrayer(17);
			break;
			case 97204: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
			case 97206: // 44 range
			c.getCombat().activatePrayer(19);
			break;	
			case 97208: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;				
			case 97210: // retrui
			c.getCombat().activatePrayer(21);
			break;					
			case 97212: // redem
			c.getCombat().activatePrayer(22);
			break;					
			case 97214: // smite
			c.getCombat().activatePrayer(23);
			break;
			case 97216: // chiv
			c.getCombat().activatePrayer(24);
			break;
			case 97218: // piety
			c.getCombat().activatePrayer(25);
			break;
			case 108005:
				c.getPA().showInterface(19148);
				c.getItems().writeBonus();
			break;
			case 28165:
			if(c.doingStarter == 0) {
					c.getPA().resetAutocast();			
				if (c.inWild() || c.isInHighRiskPK() || c.inFaladorPvP() || c.safeTimer > 0){
						c.sendMessage("You can't use this in the wilderness!");
					} else {
						if(c.playerMagicBook == 0) {
							c.playerMagicBook = 1;
							c.setSidebarInterface(6, 12855);
							c.sendMessage("You feel an ancient wisdom fill your mind...");
							c.getPA().resetAutocast();
							c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
							break;
						}
						if(c.playerMagicBook == 1) {
							c.playerMagicBook = 2;
							c.setSidebarInterface(6, 16640); // lunar
							c.sendMessage("You feel a lunar wisdom fill your mind...");
							c.autocastId = -1;
							c.getPA().resetAutocast();
							c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
							break;
						}
						if(c.playerMagicBook == 2) {
							c.setSidebarInterface(6, 1151); //modern
							c.playerMagicBook = 0;
							c.sendMessage("You feel a strange drain upon your memory...");
							c.autocastId = -1;
							c.getPA().resetAutocast();
							c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
							break;
						}
				}
			}
			break;
			
			case 28179:
				if(c.doingStarter == 1) {
					c.getDH().sendDialogues(19017, 1);
				}
			break;
			
			case 28169://shops
			if (c.inWild() || c.safeTimer > 0 || c.inPits){
					c.sendMessage("You can't use this in the wilderness!");
				} else {
					c.sendMessage("@blu@You teleport to the shop area.");
					c.getPA().startTeleport(2757, 3502, 0, "modern");//I
						if(c.doingStarter == 1) {
							if(c.sTask1 == 0) {
								c.sTask1 = 1;
								c.sendMessage("You completed a starter task!");
								c.getItems().addItem(995,100000);
							}
						}
			}
			break;
			case 28175://bosses
			c.getDH().sendDialogues(9033, 1);
			break;
			case 28167://pk
			c.getDH().sendDialogues(23, 1);
			break;
			case 28176://minigames
			c.getDH().sendDialogues(600, 1);
			break;
			case 28177://slayer tower
			if(c.inWild())
				break;
				c.getPA().startTeleport(3429, 3538, 0, "modern");
				c.sendMessage("@blu@You teleport to the slayer tower");
				if(c.doingStarter == 1) {
					if(c.sTask5 == 0) {
						c.sTask5 = 1;
						c.getItems().addItem(995,100000);
						c.sendMessage("You completed a starter task!");
					}
				}
			break;

			case 9158: 
			if (c.dialogueAction == 20003) {
				c.getShops().openShop(43);
			}
			if (c.dialogueAction == 10025) {
				c.getDH().sendDialogues(10026, c.npcType);
			}
			if (c.dialogueAction == 9048) {
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if (c.dialogueAction == 20001) {
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if (c.dialogueAction == 19014) {
			if(c.slayerTask == 221 || c.slayerTask == 3066 || c.slayerTask == 2881 || c.slayerTask == 3200 || c.slayerTask == 50 || c.slayerTask == 1351){
				c.slayerTask = 0;
				c.taskAmount = 0;
				c.findTask();
			} else {
				c.getDH().sendDialogues(19015, 70);
				c.sendMessage("Your assignment is to slay "+c.taskAmount+" "+c.getTaskName()+"s.");
				}
			}
			if (c.dialogueAction == 19018) {
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if (c.dialogueAction == 10002 || c.dialogueAction == 10006 || c.dialogueAction == 10009){
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if (c.dialogueAction == 9040) {
				if(c.inWild())
					break;
				c.getPA().startTeleport(3486+Misc.random(7), 9489+Misc.random(7), 0, "modern");
				c.getCombat().resetPrayers();
			}
			break;

			//begin random event
			case 63013:
			if(c.pieSelect == 1) {
			c.getPA().closeAllWindows();
			//c.getItems().addItem(995,1000);
			c.sendMessage("Congratulations, you have completed the random event!");
			}
			break;
			case 100237:
			if(c.aid == 1) {
			c.aid = 0;
			c.sendMessage("@red@Accept aid is now OFF.");
			c.sendMessage("@red@This means you cannot accept aiding spells.");
			break;
			}
			if(c.aid == 0) {
			c.aid = 1;
			c.sendMessage("@red@Accept aid is now ON.");
			c.sendMessage("@red@This means you can accept aiding spells.");
			}
			break;
			case 63014:
			if(c.kebabSelect == 1) {
			c.getPA().closeAllWindows();
			//c.getItems().addItem(995,10000);
			c.sendMessage("Congratulations, you have completed the random event!");
			}
			break;
			case 63015:
			if(c.chocSelect == 1) {
			c.getPA().closeAllWindows();
			//c.getItems().addItem(995,10000);
			c.sendMessage("Congratulations, you have completed the random event!");
			}
			break;
			case 63009:
			if(c.bagelSelect == 1) {
			c.getPA().closeAllWindows();
			//c.getItems().addItem(995,10000);
			c.sendMessage("Congratulations, you have completed the random event!");
			}
			break;
			case 63010:
			if(c.triangleSandwich == 1) {
			c.getPA().closeAllWindows();
			//c.getItems().addItem(995,10000);
			c.sendMessage("Congratulations, you have completed the random event!");
			}
			break;
			case 63011:
			if(c.squareSandwich == 1) {
			c.getPA().closeAllWindows();
			//c.getItems().addItem(995,10000);
			c.sendMessage("Congratulations, you have completed the random event!");
			}
			break;
			case 63012:
			if(c.breadSelect == 1) {
			c.getPA().closeAllWindows();
			//c.getItems().addItem(995,10000);
			c.sendMessage("Congratulations, you have completed the random event!");
			}
			break;
			/**Specials**/
			case 29188:
			if (c.playerEquipment[c.playerWeapon] != 4153) {
			c.specBarId = 7636; // the special attack text - sendframe126(S P E C I A L  A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			}
			break;
			
			case 30108:
			if (c.playerEquipment[c.playerWeapon] != 4153) {
			c.specBarId = 7812; // the special attack text - sendframe126(S P E C I A L  A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			}
			break;
			
			case 29163:
			if (c.playerEquipment[c.playerWeapon] != 4153) {
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			}
			break;
			
			case 33033:
			if (c.playerEquipment[c.playerWeapon] != 4153) {
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			}
			break;
			
			case 29038:
			//Client ox = (Client)Server.playerHandler.players[c.playerIndex];
			
			if (c.playerEquipment[c.playerWeapon] == 4153) {
			c.getCombat().handleGmaulPlayer();
			} else {
			c.specBarId = 7486;
			c.usingSpecial = !c.usingSpecial;
			}
			/*if ((c.playerEquipment[c.playerWeapon] == 10887 || c.playerEquipment[c.playerWeapon] == 13902) && c.playerIndex > 0) {
				if(c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])){
					c.getCombat().activateSpecial(c.playerEquipment[c.playerWeapon], ox.playerId);
				}
			}*/
			c.getItems().updateSpecialBar();
			break;
			
			case 29063:
			if (c.playerEquipment[c.playerWeapon] == 4153) 
				c.getCombat().handleGmaulPlayer();	
			
			if(c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
				c.gfx0(246);
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.startAnimation(1056);
				c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]) + (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;
			
			case 48023:
			if (c.playerEquipment[c.playerWeapon] == 4153) 
				c.getCombat().handleGmaulPlayer();	
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29138:
			if (c.playerEquipment[c.playerWeapon] == 4153) 
				c.getCombat().handleGmaulPlayer();	
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29113:
			if (c.playerEquipment[c.playerWeapon] == 4153) 
				c.getCombat().handleGmaulPlayer();	
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			case 29238:
			if (c.playerEquipment[c.playerWeapon] == 4153) 
				c.getCombat().handleGmaulPlayer();	
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			
			/**Dueling**/			
			case 26065: // no forfeit
			case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;
			
			case 26066: // no movement
			case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;
			
			case 26069: // no range
			case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;
			
			case 26070: // no melee
			case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;				
			
			case 26071: // no mage
			case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;
				
			case 26072: // no drinks
			case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;
			
			case 26073: // no food
			case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;
			
			case 26074: // no prayer
			case 26047:	
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;
			
			case 26076: // obsticals
			case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;
			
			case 2158: // fun weapons
			case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;
			
			case 30136: // sp attack
			case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;	

			case 53245: //no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;
			
			case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;
			
			case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;
			
			case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;
			
			case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;
			
			case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;
			
			case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;
			
			case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;
			
			case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;
			
			case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;
			
			case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;
			
			case 26018:	
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			if(o == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}
			
			if(c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
				c.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			c.duelStatus = 2;
			if(c.duelStatus == 2) {
				c.getPA().sendFrame126("Waiting for other player...", 6684);
				o.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			if(o.duelStatus == 2) {
				o.getPA().sendFrame126("Waiting for other player...", 6684);
				c.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			
			if(c.duelStatus == 2 && o.duelStatus == 2) {
				c.canOffer = false;
				o.canOffer = false;
				c.duelStatus = 3;
				o.duelStatus = 3;
				c.getTradeAndDuel().confirmDuel();
				o.getTradeAndDuel().confirmDuel();
			}
			break;
			
			case 25120:
			if(c.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) Server.playerHandler.players[c.duelingWith];
			if(o1 == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			c.duelStatus = 4;
			if(o1.duelStatus == 4 && c.duelStatus == 4) {
c.stopMovement();
o1.stopMovement();	
o1.freezeTimer = 3;	
c.freezeTimer = 3;		
				c.getTradeAndDuel().startDuel();
				o1.getTradeAndDuel().startDuel();
				o1.duelCount = 4;
				c.duelCount = 4;
				c.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				c.getPA().sendFrame126("Waiting for other player...", 6571);
				o1.getPA().sendFrame126("Other player has accepted", 6571);
			}
			break;
	
			
			case 4169: // god spell charge
			c.usingMagic = true;
			if(!c.getCombat().checkMagicReqs(48)) {
				break;
			}
				
			if(System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay	= System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
	        break;
				case 28164:
					if(!c.isSkulled) {
						c.getItems().resetKeepItems();
						c.getItems().keepItem(0, false);
						c.getItems().keepItem(1, false);
						c.getItems().keepItem(2, false);
						c.getItems().keepItem(3, false);
						c.sendMessage("You can keep three items and a fourth if you use the protect item prayer.");
					} else {
						c.getItems().resetKeepItems();
						c.getItems().keepItem(0, false);
						c.sendMessage("You are skulled and will only keep one item if you use the protect item prayer.");
					}
					c.getItems().sendItemsKept();
					c.getPA().showInterface(6960);
					c.getItems().resetKeepItems();
				break;
			case 28172:
			if(c.doingStarter == 0) {
				c.forcedChat("I'm currently on a "+c.cStreak+" Killstreak");
			}
			if(c.doingStarter == 1) {
				if(c.sTask6 == 0) {
					c.sTask6 = 1;
					c.getItems().addItem(995,100000);
					c.sendMessage("You completed a starter task!");
				}
				c.getPA().resetAutocast();			
			if (c.inWild() || c.isInHighRiskPK() || c.inFaladorPvP() || c.safeTimer > 0){
				c.sendMessage("You can't use this in the wilderness!");
				} else {
			if(c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("You feel an ancient wisdom fill your mind...");
				c.getPA().resetAutocast();
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				break;
			}
			if(c.playerMagicBook == 1) {
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 16640); // lunar
				c.sendMessage("You feel a lunar wisdom fill your mind...");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				break;
			}
			if(c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 1151); //modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a strange drain upon your memory...");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				break;
			}
			}
			}
				
			break;
			case 28178:
			if(c.doingStarter == 0) {
				c.forcedChat("My highest ever Killstreak is "+c.hStreak+"");
				break;
			}
			case 28166:
			if(c.doingStarter == 0) {
				double KDR = ((double)c.KC)/((double)c.DC);
				c.forcedChat("My Kill/Death ratio is "+c.KC+"/"+c.DC+"; "+KDR+".");
				break;
			}
				
			/*case 28215:
			if(c.doingStarter == 0) {
				c.forcedChat("I Have "+c.potential+"% Potential");
				c.sendMessage("Type @blu@::potential@bla@ for info.");
				break;
			}*/
				
			case 28168:
			if(c.doingStarter == 0) {
				c.forcedChat("I currently have "+c.pkPoints+" PK Points");
				break;
			}
				
			case 28171:
			if(c.doingStarter == 0) {
				c.forcedChat("I have "+c.targetPoints+" Target Points, but I've killed a total of "+c.totalTargetPoints+" Targets.");
				break;
			}

			case 28170:
			if(c.doingStarter == 0) {
				if(c.warnings <= 0) {
					c.sendMessage("@gre@You don't have any warnings. @bla@Good job!");
				} else if(c.warnings == 1) {
					c.sendMessage("You have @or1@"+c.warnings+" @bla@warning. Try to behave better.");
				} else if(c.warnings > 1 && c.warnings < 3) {
					c.sendMessage("You have @or1@"+c.warnings+"@bla@ warnings. Try to behave better.");
				} else {
					c.sendMessage("@red@You have "+c.warnings+" warnings. You are close to getting banned!");
				}
			}
			break;			

			case 28173:
			if(c.doingStarter == 0) {
				if(c.worshippedGod == 0) {
					c.forcedChat("I don't believe in gods! ...yet");
				}
				if(c.worshippedGod == 1) {
					c.forcedChat("I've pledged my loyalty to Saradomin, and have a Reputation of "+c.godReputation+"");
				} else if (c.worshippedGod == 2) {
					c.forcedChat("I've pledged my loyalty to Zamorak, and have a Reputation of "+c.godReputation2+"");
				}
			}
			break;
			case 28174:
				c.forcedChat("My Target Rating is "+c.rating+".");
			break;

			case 5971:
			case 5982:
			case 5972:
			case 5973:
			case 5974:
			case 5975:
			case 5976:
			case 5977:
			case 5978:
			case 5979:
			case 5980:
			case 5981:
			c.getPA().sendFrame126("www.foreverpkers-ps.com/forum/index.php?action=collapse;c=3;sa=expand;e23d0dc21f04=363fb965f8ec427616086c36a68718ab#c3", 12000);
			break;

			
			
			case 9154:
			c.logout();
			break;
			
			case 21010:
			c.takeAsNote = true;
			break;

			case 21011:
			c.takeAsNote = false;
			break;
			
			
			case 4171:
			case 50056:
			case 117048:
			String type = c.playerMagicBook == 0 ? "modern" : "ancient";
			//c.getPA().startTeleport(Config.HOME_X, Config.HOME_Y, 0, type);
				c.getDH().sendDialogues(2223, 1);
			break;
			
			
			//lunar home teleport
			/*case 117048:
			c.getPA().startTeleport(Config.HOME_X, Config.HOME_Y, 0, "modern");	
			break;*/
			
			case 50235: //paddewa
			break;

			case 4140: //varrock
			if (c.getItems().playerHasItem(563,1) && c.getItems().playerHasItem(554,1) && c.getItems().playerHasItem(556,3)) {
				c.getPA().startTeleport(Config.VARROCK_X + Misc.random(1), Config.VARROCK_Y + Misc.random(1), 0, "modern");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),1);
				c.getItems().deleteItem(554,c.getItems().getItemSlot(554),1);
				c.getItems().deleteItem(556,c.getItems().getItemSlot(556),3);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;

			case 50245: //senntisen 
			break;

			case 4143: //lumb
			if (c.getItems().playerHasItem(563,1) && c.getItems().playerHasItem(557,1) && c.getItems().playerHasItem(556,3)) {
				c.getPA().startTeleport(3223, 3218, 0, "modern");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),1);
				c.getItems().deleteItem(557,c.getItems().getItemSlot(557),1);
				c.getItems().deleteItem(556,c.getItems().getItemSlot(556),3);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;
			
			case 50253://kharyrll
			if (c.getItems().playerHasItem(563,2) && c.getItems().playerHasItem(565,1)) {
				c.getPA().startTeleport(3494, 3472, 0, "ancient");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),2);
				c.getItems().deleteItem(565,c.getItems().getItemSlot(565),1);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;

			case 4146://fally
			if (c.getItems().playerHasItem(563,1) && c.getItems().playerHasItem(555,1) && c.getItems().playerHasItem(556,3)) {
				c.getPA().startTeleport(2965, 3380, 0, "modern");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),1);
				c.getItems().deleteItem(555,c.getItems().getItemSlot(555),1);
				c.getItems().deleteItem(556,c.getItems().getItemSlot(556),3);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;
			

			case 51005:
			break;

			case 4150: //cammy
			if (c.getItems().playerHasItem(563,1) && c.getItems().playerHasItem(556,5)) {
				c.getPA().startTeleport(2757, 3477, 0, "modern");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),1);
				c.getItems().deleteItem(556,c.getItems().getItemSlot(556),5);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;			
			
			case 51013: //dareyak tele
			break;

			case 6004: //ardy
			if (c.getItems().playerHasItem(563,2) && c.getItems().playerHasItem(555,2)) {
				c.getPA().startTeleport(2662, 3307, 0, "modern");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),2);
				c.getItems().deleteItem(555,c.getItems().getItemSlot(555),2);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break; 
			
			
			case 51023: //carralangar
			if (c.getItems().playerHasItem(563,2) && c.getItems().playerHasItem(566,2)) {
				c.getPA().startTeleport(3140, 3676, 0, "ancient");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),2);
				c.getItems().deleteItem(566,c.getItems().getItemSlot(566),2);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;

			case 6005:
			c.getDH().sendOption5("Option 16", "Option 2", "Option 3", "Option 4", "Option 5");
			c.teleAction = 6;
			break; 
			
			
			case 51031://annakarl
			if (c.getItems().playerHasItem(563,2) && c.getItems().playerHasItem(565,2)) {
				c.getPA().startTeleport(3289, 3887, 0, "ancient");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),2);
				c.getItems().deleteItem(565,c.getItems().getItemSlot(565),2);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;
			
			case 29031:
			c.getDH().sendOption5("Option 17", "Option 2", "Option 3", "Option 4", "Option 5");
			c.teleAction = 7;
			break; 		
			
			
			case 51039: //ghorrock tele
			if (c.getItems().playerHasItem(563,2) && c.getItems().playerHasItem(555,8)) {
				c.getPA().startTeleport(2977, 3925, 0, "ancient");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),2);
				c.getItems().deleteItem(555,c.getItems().getItemSlot(555),8);
			} else {
				c.sendMessage("You do not have the required runes for this spell.");	
			}
			break;

			case 72038:
			if (c.getItems().playerHasItem(563,2) && c.getItems().playerHasItem(554,2)  && c.getItems().playerHasItem(555,2) && c.getItems().playerHasItem(1963,1)) {
				c.getPA().startTeleport(2787, 2786, 0, "modern");
				c.getItems().deleteItem(563,c.getItems().getItemSlot(563),2);
				c.getItems().deleteItem(557,c.getItems().getItemSlot(555),2);
				c.getItems().deleteItem(556,c.getItems().getItemSlot(554),2);
				c.getItems().deleteItem(1963,c.getItems().getItemSlot(1963),1);
			} else {
				c.sendMessage("You do not have the required runes for this spell (You may need a banana).");	
			}
			break;
			
      		case 9125: //Accurate
			case 22230://punch
			case 48010://flick (Abyssal Whip)
			case 14218://pound (Mace)
			case 33020://jab (Halberd)
			case 21200: //spike (Pickaxe)
			case 6168: //chop (Hatchet)
			case 8234: //stab (Daggers)
			case 17102: //accurate (All Darts)
			case 6236: //accurate (Long/Short-bow)
			case 1080: //bash (Staffs/Battlestaffs)
			case 6221: // range accurate
			case 30088: //claws (Chop)
			case 1177: //hammer (Pound)
			case 18077: //lunge (Spear)
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			//Defence
		case 9126: //Defensive
			case 22228: //block (Unarmed)
			case 48008: //deflect (Abyssal Whip)
			case 1175: //block (Hammer)
			case 21201: //block (Pickaxe)
			case 14219: //block (Mace)
			case 1078: //focus - block (Staffs/Battlestaffs)
			case 33018: //fend (Hally)
			case 6169: //block (Hatechet)
			case 8235: //block (Daggers)
			case 18078: //block (Spear)
			case 30089: //block (Claws)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			//All
			case 9127: // Controlled
			case 14220: //Spike (Mace)
			case 6234: //longrange (Long/Short-bow)
			case 6219: //longrange
			case 18079: //pound (Spear)
			case 17100: //longrange (Darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			//Strength
			case 9128: //Aggressive
			case 14221: //Pummel(Mace)
			case 33019: //Swipe(Halberd)
			case 21203: //impale (Pickaxe)
			case 21202: //smash (Pickaxe)
			case 6171: //hack (Hatchet)
			case 6170: //smash (Axe)
			case 6220: // range rapid
			case 8236: //slash (Daggers)
			case 8237: //lunge (Daggers)
			case 30090: //claws (Lunge)
			case 30091: //claws (Slash)
			case 1176: //stat hammer
			case 22229: //block (unarmed)
			case 1079: //pound (Staffs/Battlestaffs)
			case 6235: //rapid (Long/Short-bow)
			case 17101: //repid (Darts)
			case 18080: //swipe (Spear)
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
			
			
			/**Prayers**/
			case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;	
			case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;	
			case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;	
			case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
			case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
			case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
			case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
			case 21238:	// improved reflexes
			c.getCombat().activatePrayer(7);
			break;
			case 21239: //hawk eye
			c.getCombat().activatePrayer(8);
			break;
			case 21240:
			c.getCombat().activatePrayer(9);
			break;
			case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;			
			case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
			case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;	
			case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
			case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
			case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;	
			case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;					
			case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
			case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
			case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;	
			case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;				
			case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;					
			case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;					
			case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
			case 70092: // piety
			c.getCombat().activatePrayer(24);
			break;
			case 70094: // turmoil
			c.getCombat().activatePrayer(25);

			break;
			
			case 13092:
			if(c.packetTimer > 0)
			 break;

			c.packetTimer = 2;
            if (System.nanoTime() - c.lastButton < 1000000000) {
					c.lastButton = System.nanoTime();
					break;
				} else {
					c.lastButton = System.nanoTime();
			}
			Client ot = (Client) Server.playerHandler.players[c.tradeWith];
			if(ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			if(ot.tradeWith != c.playerId){
				ot.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);	
			c.goodTrade= true;
			ot.goodTrade= true;
			
			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if(ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems.size()) {					
						c.sendMessage(ot.playerName +" only has "+ot.getItems().freeSlots()+" free slots, please remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						ot.sendMessage(c.playerName +" has to remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items or you could offer them "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						c.goodTrade= false;
						ot.goodTrade= false;
						c.getPA().sendFrame126("Not enough inventory space...", 3431);
						ot.getPA().sendFrame126("Not enough inventory space...", 3431);
							break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...", 3431);				
						ot.getPA().sendFrame126("Other player has accepted", 3431);
						c.goodTrade= true;
						ot.goodTrade= true;
						}
					}	
				}	
				if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
					c.tradeConfirmed = true;
					if(ot.tradeConfirmed) {
						c.inSecondWindow = true;
						ot.inSecondWindow = true;
						c.inFirstWindow = false;
						ot.inFirstWindow = false;
						c.getTradeAndDuel().confirmScreen();
						ot.getTradeAndDuel().confirmScreen();
							break;
					}		  
				}
			break;
					
			case 13218:

			if(c.packetTimer > 0)
			 break;
			c.packetTimer = 2;

              if (System.nanoTime() - c.lastButton < 1000000000) {
						c.lastButton = System.nanoTime();
							break;
					} else {
						c.lastButton = System.nanoTime();
				}
			c.tradeAccepted = true;
			Client ot1 = (Client) Server.playerHandler.players[c.tradeWith];
				if (ot1 == null) {
					c.getTradeAndDuel().declineTrade();
					c.sendMessage("Trade declined as the other player has disconnected.");
					break;
				}
				if (ot1.tradeWith != c.playerId) {
					c.getTradeAndDuel().declineTrade();
					c.sendMessage("Trade declined as the other player has disconnected.");
					break;
				}
				
				if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed && !c.tradeConfirmed2) {
					c.tradeConfirmed2 = true;
					if(ot1.tradeConfirmed2) {	
						c.acceptedTrade = true;
						ot1.acceptedTrade = true;
						c.getTradeAndDuel().giveItems();
						ot1.getTradeAndDuel().giveItems();
						c.inSecondWindow = false;
						ot1.inSecondWindow = false;
						break;
					}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
				}
				
			break;			
			/* Rules Interface Buttons */
			case 125011: //Click agree
				if(!c.ruleAgreeButton) {
					c.ruleAgreeButton = true;
					c.getPA().sendFrame36(701, 1);
				} else {
					c.ruleAgreeButton = false;
					c.getPA().sendFrame36(701, 0);
				}
				break;
			case 125003://Accept
				if(c.ruleAgreeButton) {
					//c.getPA().showInterface(3559);
					c.newPlayer = false;
				} else if(!c.ruleAgreeButton) {
					c.sendMessage("You need to click on you agree before you can continue on.");
				}
				break;
			case 125006://Decline
				c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
				break;
			/* End Rules Interface Buttons */
			/* Player Options */
			case 74176:
				if(!c.mouseButton) {
					c.mouseButton = true;
					c.getPA().sendFrame36(500, 1);
					c.getPA().sendFrame36(170,1);
				} else if(c.mouseButton) {
					c.mouseButton = false;
					c.getPA().sendFrame36(500, 0);
					c.getPA().sendFrame36(170,0);					
				}
				break;
			case 74184:
				if(!c.splitChat) {
					c.splitChat = true;
					c.getPA().sendFrame36(502, 1);
					c.getPA().sendFrame36(287, 1);
				} else {
					c.splitChat = false;
					c.getPA().sendFrame36(502, 0);
					c.getPA().sendFrame36(287, 0);
				}
				break;
			case 74180:
				if(!c.chatEffects) {
					c.chatEffects = true;
					c.getPA().sendFrame36(501, 1);
					c.getPA().sendFrame36(171, 0);
				} else {
					c.chatEffects = false;
					c.getPA().sendFrame36(501, 0);
					c.getPA().sendFrame36(171, 1);
				}
				break;
			case 74188:
				if(!c.acceptAid) {
					c.acceptAid = true;
					c.getPA().sendFrame36(503, 1);
					c.getPA().sendFrame36(427, 1);
				} else {
					c.acceptAid = false;
					c.getPA().sendFrame36(503, 0);
					c.getPA().sendFrame36(427, 0);
				}
				break;
			case 74192:
				if(!c.isRunning2) {
					c.isRunning2 = true;
					c.getPA().sendFrame36(504, 1);
					c.getPA().sendFrame36(173, 1);
				} else {
					c.isRunning2 = false;
					c.getPA().sendFrame36(504, 0);
					c.getPA().sendFrame36(173, 0);
				}
				break;
			
			case 58074:
				c.getBankPin().close();
				break;

			case 58025:
			case 58026:
			case 58027:
			case 58028:
			case 58029:
			case 58030:
			case 58031:
			case 58032:
			case 58033:
			case 58034:
				c.getBankPin().pinEnter(actionButtonId);
				break;
				
			case 74201://brightness1
			c.brightness = 1;
				c.getPA().sendFrame36(505, 1);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166, 1);
				break;
			case 74203://brightness2
			c.brightness = 2;
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 1);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,2);
				break;

			case 74204://brightness3
			c.brightness = 3;
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 1);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,3);
				break;

			case 74205://brightness4
			c.brightness = 4;
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 1);
				c.getPA().sendFrame36(166,4);
				break;
			case 3138://brightness1
			c.brightness = 1;
				c.getPA().sendFrame36(505, 1);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166, 1);
				break;
			case 3140://brightness2
			c.brightness = 2;
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 1);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,2);
				break;

			case 3142://brightness3
			c.brightness = 3;
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 1);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,3);
				break;

			case 3144://brightness4
			c.brightness = 4;
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 1);
				c.getPA().sendFrame36(166,4);
				break;
			case 74206://area1
				c.getPA().sendFrame36(509, 1);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74207://area2
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 1);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74208://area3
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 1);
				c.getPA().sendFrame36(512, 0);
				break;
			case 74209://area4
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 1);
				break;
			case 168:
                c.startAnimation(855);
            break;
			case 107230:
if (c.playerRights >= 1) {
if (System.currentTimeMillis() - c.lastSpec > 40000) {
				
				if (!c.inWild() || !c.isInHighRiskPK()) {
				if (c.attackTimer <= 0) {
					c.specAmount = 10.0;
		c.sendMessage("You feel yourself growing stronger..");
		c.sendMessage("Your special bar increases to maximum capacity!");
		if(c.playerRights <= 4) {
		c.gfx100(738);
		}
		c.lastSpec = System.currentTimeMillis();
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
c.getItems().updateSpecialBar();
					} else {
			c.sendMessage("You can't use that during combat!");
			}
			} else {
			c.sendMessage("You can't use that in the wilderness!");
			}
} else {
c.sendMessage("Please wait before refilling your special.");
}
} else {
c.sendMessage("Sorry, but you need to be a Premium Member to use instant special regain!");
c.sendMessage("Please type ::donate for more information on donating for status/items!");
}
break;
case 108006:
c.sendMessage("This feature is coming soon!");
break;
case 107243:
case 83093:
c.setSidebarInterface(4, 1644);
break;

case 107215:
c.setSidebarInterface(11, 904);
break;
			case 152:
			c.isRunning2 = !c.isRunning2;
			int frame = c.isRunning2 == true ? 1 : 0;
			c.getPA().sendFrame36(173,frame);
			break;
	case 107229:
if (c.playerRights >= 1) {
				if (!c.inWild() || !c.isInHighRiskPK()) {
				if (c.attackTimer <= 0) {
if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += 99;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			}
		c.playerLevel[0] += c.getPotions().getBoostedStat(0, true);
		c.getPA().refreshSkill(0);
		c.playerLevel[1] += c.getPotions().getBoostedStat(1, true);
		c.getPA().refreshSkill(1);
		c.playerLevel[2] += c.getPotions().getBoostedStat(2, true);
		c.getPA().refreshSkill(2);
		c.playerLevel[6] += c.getPotions().getBoostedStat(6, true);
		c.getPA().refreshSkill(6);
		c.playerLevel[4] += c.getPotions().getBoostedStat(4, true);
		c.getPA().refreshSkill(4);
c.getPotions().curePoison(1);
		c.sendMessage("You feel yourself growing stronger..");
		c.sendMessage("You increase your stats, heal yourself and cure all poison!");
		if(c.playerRights <= 4) {
		c.gfx100(738);
		}
					} else {
			c.sendMessage("You can't use that during combat!");
			}
			} else {
			c.sendMessage("You can't use that in the wilderness!");
			}
} else {
c.sendMessage("Sorry, but you need to be a Premium Member to use total restore!");
c.sendMessage("Please type ::donate for more information on donating for status/items!");
}
			break;
            case 169:
                c.startAnimation(856);
            break;
            case 162:
                c.startAnimation(857);
            break;
            case 164:
                c.startAnimation(858);
            break;
            case 165:
                c.startAnimation(859);
            break;
            case 161:
                c.startAnimation(860);
            break;
            case 170:
                c.startAnimation(861);
            break;
            case 171:
                c.startAnimation(862);
            break;
            case 163:
                c.startAnimation(863);
            break;
            case 167:
                c.startAnimation(864);
            break;
            case 172:
                c.startAnimation(865);
            break;
            case 166:

                c.startAnimation(866);

            break;
            case 52050:
                c.startAnimation(2105);
            break;
            case 52051:
                c.startAnimation(2106);
            break;
            case 52052:
                c.startAnimation(2107);
            break;
            case 52053:
			c.startAnimation(2108);

		
            break;
            case 52054:
                c.startAnimation(2109);
            break;
            case 52055:
                c.startAnimation(2110);
            break;
            case 52056:
                c.startAnimation(2111);
            break;
            case 52057:
                c.startAnimation(2112);
            break;
            case 52058:
                c.startAnimation(2113);
            break;
            case 43092:
            	c.stopMovement();
                c.startAnimation(0x558);
            break;
            case 2155:
                c.startAnimation(0x46B);
            break;
            case 25103:
                c.startAnimation(0x46A);
            break;
            case 25106:
                c.startAnimation(0x469);
            break;
            case 2154:
                c.startAnimation(0x468);
            break;
            case 52071:
                c.startAnimation(0x84F);
            break;
            case 52072:
                c.startAnimation(0x850);
            break;
			case 72032:
				//c.startAnimation(4276);
				//c.gfx0(712);
            break;
			case 72033:
				//c.startAnimation(4278);
            break;
			case 59062:
				//c.startAnimation(4280);
            break;
			case 72254:
				//c.startAnimation(4275);
            break;
			case 73004:
				c.startAnimation(7272);
				c.gfx0(1244);
            break;
			case 72255:
				c.startAnimation(6111);
			break;
						case 154:
			if(c.getPA().wearingCape(c.playerEquipment[c.playerCape])) {
				c.stopMovement();
				c.gfx0(c.getPA().skillcapeGfx(c.playerEquipment[c.playerCape]));
				c.startAnimation(c.getPA().skillcapeEmote(c.playerEquipment[c.playerCape]));
			} else {
				c.sendMessage("You must be wearing a Skillcape to do this emote.");
			}
			break;
			/* END OF EMOTES */
			
			case 3071: // Go back a page, BOOK
				if(c.bookPage > 0) {
					c.bookPage--;
					c.getPA().sendBook(c.bookName, c.bookPages[c.bookPage]);
				}
				break;
			case 3073: // Go forward a page, BOOK
				if(c.bookPage < c.maxPages) {
					c.bookPage++;
					c.getPA().sendBook(c.bookName, c.bookPages[c.bookPage]);
				}
				break;
			case 39178:
				c.getPA().closeAllWindows();
			break;
			
case 118098:
if(c.playerMagicBook == 2) {
	c.getPA().castVeng();
	c.vengLimit++;
} else {
	c.sendMessage("Nope");
}
break; 
			case 33208: //mining
				c.forcedText = "[SkillChat] My Mining level is "+c.playerLevel[14]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				SkillGuides.miningInterface(c);
			break;
			case 33211: //smithing
				c.forcedText = "[SkillChat] My Smithing level is "+c.playerLevel[13]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				SkillGuides.smithingInterface(c);
			break;
			case 33214: //fish
				c.forcedText = "[SkillChat] My Fishing level is "+c.playerLevel[10]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 33217: //cookie
				c.forcedText = "[SkillChat] My Cooking level is "+c.playerLevel[7]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 33220: //fm
				c.forcedText = "[SkillChat] My Firemaking level is "+c.playerLevel[11]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 33223: //wc
				c.forcedText = "[SkillChat] My Woodcutting level is "+c.playerLevel[8]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 33210: //agility
				c.forcedText = "[SkillChat] My Agility level is "+c.playerLevel[16]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 33213: //herblore
				c.forcedText = "[SkillChat] My Herblore level is "+c.playerLevel[15]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 33216: //thieving
				c.forcedText = "[SkillChat] My Thieving level is "+c.playerLevel[17]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				SkillGuides.thievingInterface(c);
			break;
			case 33219: //crafting
				c.forcedText = "[SkillChat] My Crafting level is "+c.playerLevel[12]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				SkillGuides.craftingInterface(c);
			break;
			case 54104: //farming
				c.forcedText = "[SkillChat] My Farming level is "+c.playerLevel[19]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				SkillGuides.farmingInterface(c);
			break;
			case 33222: //fletching
				c.forcedText = "[SkillChat] My Fletching level is "+c.playerLevel[9]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 33224: //runecrafting
				c.forcedText = "[SkillChat] My Runecrafting level is "+c.playerLevel[20]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			break;
			case 47130://slayer
				c.forcedText = "[SkillChat] My Slayer level is "+c.playerLevel[18]+".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				SkillGuides.slayerInterface(c);
			break;
			
			case 24017:
				c.getPA().resetAutocast();
				//c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				//c.setSidebarInterface(0, 328);
				//c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 : c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
		/*} else {
				c.sendMessage("@red@You can only do this once every 2 seconds.");
		}*/
		c.questTab();
	}
	

}
