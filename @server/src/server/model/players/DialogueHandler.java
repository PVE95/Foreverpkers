package server.model.players;

import server.Server;

public class DialogueHandler {

	private Client c;
	
	public DialogueHandler(Client client) {
		this.c = client;
	}
	
	/**
	 * Handles all talking
	 * @param dialogue The dialogue you want to use
	 * @param npcId The npc id that the chat will focus on during the chat
	 */
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch(dialogue) {
		case 18004:
		if(c.gameMode == 1){
			sendNpcChat4("Hello there, "+c.playerName+".", "Would you like to become a trained account?", "You'll receive 25% Bonus Drop rate, 25% Bonus PK Points,", "and double Target Points.", 945, "ForeverPkers Guide");
			c.nextChat = 18005;
			} else if(c.gameMode == 2){
			sendNpcChat4("Hello there, "+c.playerName+".", "Would you like to become an Instant PK account?", "You may then change your stats by clicking them, but", "you will lose all of your trained account progress.", 945, "ForeverPkers Guide");
			c.nextChat = 18009;
			}
		break;
		case 19000:
			sendOption4("Giant mole (Falador)", "Dagannoth kings", "Chaos elemental", "");
			c.dialogueAction = 19000;
		break;
		case 19001:
			sendOption4("", "Giant mole (Falador)", "Giant mole (Wilderness)", "");
			c.dialogueAction = 19001;
		break;
		case 19010:
			sendNpcChat2("Good to see you again, "+c.playerName+".", "What do you need from me?",70, "Turael");
			c.nextChat = 19011;
		break;
		case 19011:
			sendOption4("Do you have anything for sale?","I would like a slayer task.","I would like a boss task.","Nevermind");
			c.dialogueAction = 19011;
			c.nextChat = -1;
		break;
		case 19012:
			sendNpcChat1("Your new assignment is to kill "+c.taskAmount+" "+c.myTaskName+"s.",70, "Turael");
			c.dialogueAction = -1;
			c.nextChat = 19013;
		break;
		case 19013:
			if(c.slayerTask == 221 || c.slayerTask == 3066 || c.slayerTask == 2881 || c.slayerTask == 3200 || c.slayerTask == 50 || c.slayerTask == 1351)
			sendNpcChat2(""+c.bossLocation[c.myTask][0],""+c.bossLocation[c.myTask][1],70, "Turael");
			else
			sendNpcChat2(""+c.slayerLocation[c.myTask][0],""+c.slayerLocation[c.myTask][1],70, "Turael");
			c.dialogueAction = -1;
			c.nextChat = 19014;
		break;
		case 19014:
			sendOption2("Great!","I'd like a different task.");
			c.dialogueAction = 19014;
			c.nextChat = -1;
		break;
		case 19015:
			sendNpcChat1("I can only change your task if it is a boss task.",70, "Turael");
			c.dialogueAction = -1;
			c.nextChat = -1;
		break;
		case 19016:
			sendNpcChat1("This option is coming soon!",70, "Turael");
			c.dialogueAction = -1;
			c.nextChat = -1;
		break;
		case 19017:
			sendNpcChat3("@red@Are you sure?", "@red@If you skip the starter tasks, you will not", "@red@receive the starter reward.",945, "ForeverPkers Guide");
			c.nextChat = 19018;
		break;
		case 19018:
			sendOption2("Yes, skip the starter tasks.","I've changed my mind.");
			c.dialogueAction = 19018;
			c.nextChat = -1;
		break;
		case 18005:
			sendOption4("", "What is a trained account?", "Nevermind", "");
			c.dialogueAction = 18005;
		break;
		case 18006:
			sendNpcChat4("Trained accounts are restricted from changing", "their stats instantly, meaning you must level your combat", "abilities each manually to the level you prefer.", "The experience rates are 66x that of RuneScape.", 945, "ForeverPkers Guide");
			c.nextChat = 18007;
		break;
		case 18007:
			sendNpcChat4("However, many great bonuses come with training.", "25% bonus Drop rates, 25% bonus PK points, 2x Target points,", "2x god reputation, and less degrade chance on PvP armor.", "So what do you say?", 945, "ForeverPkers Guide");
			c.nextChat = 18008;
		break;
		case 18008:
			sendOption4("", "Become a trained account", "No thanks", "");
			c.dialogueAction = 18008;
		break;
		case 18009:
			sendOption4("", "Become an Instant PK account", "Reset defence level", "Nevermind");
			c.dialogueAction = 18009;
		break;
		case 18010:
			sendNpcChat2("Are you sure you want to become an Instant PK account?","Your trained combat stats will @red@not@bla@ be saved.",945,"ForeverPkers Guide");
			c.nextChat = 18011;
		break;
		case 18011:
			sendOption4("","Yes, become an Instant PK account.", "No, I like my stats!","");
			c.dialogueAction = 18011;
		break;
		case 2222:
			sendOption2("Quit viewing", "");
			c.dialogueAction = 2222;
			break;
		case 2223:
			sendOption4("Home", "Shops", "Training Areas", "More...");
			c.dialogueAction = 2223;
			break;
		case 2224:
			sendOption4("Bosses", "Fight Pits", "Karamja", "More...");
			c.dialogueAction = 2224;
			break;
		case 18000:
			sendOption4("@blu@Blue", "@red@Red", "@whi@White", "@bla@Black");
			c.dialogueAction = 18000;
			break;
		case 18001:
			sendNpcChat2("Welcome to ForeverPkers, "+c.playerName+"", "First, you need to choose your game mode.", 945, "ForeverPkers Guide");
			c.nextChat = 18002;
		break;
		case 18002:
			sendOption4("@red@Instant PK", "(click-to-change stats, normal point gain)", "@red@Train", "(train your stats; bonus loot/PK points and more!)");
			c.dialogueAction = 18002;
		break;
		case 18003:
			sendOption4("Bears", "Apes", "Black Dragons", "Coming Soon");
		break;
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 1:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 1;
			c.nextChat = 2;
			break;
		case 2:
			sendOption2("Yea! I'm fearless!",  "No way! That looks scary!");
			c.dialogueAction = 1;
			c.nextChat = 0;
			break;
		case 3:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 4;
		break;
		case 5:
			sendNpcChat4("Hello adventurer...", "My name is Kolodion, the master of this mage bank.", "Would you like to play a minigame in order ", 
						"to earn points towards recieving magic related prizes?", c.talkingNpc, "Kolodion");
			c.nextChat = 6;
		break;
		case 6:
			sendNpcChat4("The way the game works is as follows...", "You will be teleported to the wilderness,", 
			"You must kill mages to recieve points,","redeem points with the chamber guardian.", c.talkingNpc, "Kolodion");
			c.nextChat = 15;
		break;
		case 11:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 12;
		break;
		case 12:
			sendOption2("Yes I would like a slayer task.", "No I would not like a slayer task.");
			c.dialogueAction = 5;
		break;
		case 13:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I see I have already assigned you a task to complete.", 
			"Would you like me to give you an easier task?", c.talkingNpc, "Duradel");
			c.nextChat = 14;
		break;
		case 14:
			sendOption2("Yes I would like an easier task.", "No I would like to keep my task.");
			c.dialogueAction = 6;
		break;
		case 15:
			sendOption2("Yes I would like to play", "No, sounds too dangerous for me.");
			c.dialogueAction = 7;
		break;
		case 16:
			sendOption2("I'd like to fix all of my Barrows, please.", "Cancel");
			c.dialogueAction = 8;
		break;
		case 17:
			sendOption5("Air", "Mind", "Water", "Earth", "More");
			c.dialogueAction = 10;
			c.dialogueId = 17;
			c.teleAction = -1;
		break;
		case 18:
			sendOption5("Fire", "Body", "Cosmic", "Astral", "More");
			c.dialogueAction = 11;
			c.dialogueId = 18;
			c.teleAction = -1;
		break;
		case 19:
			sendOption5("Nature", "Law", "Death", "Blood", "More");
			c.dialogueAction = 12;
			c.dialogueId = 19;
			c.teleAction = -1;
		break;
		case 20:
			sendNpcChat1("Would you like to ride the flying carpet for 200 gold?", c.talkingNpc, "Rug Merchant");
			c.nextChat = 21;
		break;
		case 13337:
			sendNpcChat1("Hope you're enjoying your time here at ForeverPkers!", c.talkingNpc, "Man");
			c.nextChat = 13338;
		break;
		case 13338:
			sendPlayerChat1("Why, thank you! I certainly am!");
			c.nextChat = 13339;
		break;
		case 13339:
			sendPlayerChat1("... Well... At least the locals seem pretty nice!");
			c.nextChat = -1;
		break;
		case 13340:
			if(c.getItems().playerHasItem(995, 1000000000)) {
				sendNpcChat3("Well hello there! You look like a well", "composed young fellow, what do you say", "you and I go out sometime?", c.talkingNpc, "Woman");
				c.nextChat = 13341;
			} else {
				sendNpcChat1("Hope you're enjoying your time here at ForeverPkers!", c.talkingNpc, "Woman");
				c.nextChat = 13338;
			}
		break;
		case 13341:
			sendStatement("You decide to walk away... She seemed to take interest in your bank.");
			c.nextChat = -1;
		break;
		case 21:
			sendOption2("Yes",  "No");
			c.dialogueAction = 13;
		break;
		case 22:
			sendNpcChat1("You currently have "+c.pkPoints+" Credits.", c.talkingNpc, "Mazchna");
		break;
		case 25:
			sendOption4("Varrock PK@bla@ (@red@Single @bla@& @gre@Safe@bla@)", "Dark Warriors Fortress @bla@(@red@Multi @bla@& @gre@Safe@bla@)", "Mage Bank @bla@(@red@Single @bla@& @gre@Safe@bla@)", "Nevermind");
			c.dialogueAction = 15;
		break;
		case 23:
			sendOption4("Hills @bla@(@or2@Medium Hotspot@bla@)", "Wilderness Chest @bla@(@red@Multi & Dangerous@bla@)", "East Dragons @bla@(@red@Major Hotspot@bla@)", "More...");
			c.dialogueAction = 14;
		break;
		case 24:
			sendOption4("Bear territory", "Taverly dungeon", "Monkey guards", "Slayer tower");
			c.dialogueAction = 24;
		break;
		case 18012://multi
			sendOption4("Dark Warrior's Fortress", "Land of the apes", "Bear territory @bla@(@gre@Low Level@bla@)", "Slayer Tower");
			c.dialogueAction = 18012;
		break;
		case 26:
			sendStatement("You must have @blu@10@bla@ PK Points to teleport to Varrock.");
			c.nextChat = 0;
		break;
		case 27:
			sendOption4("Varrock", "Mining Guild", "Falador Mines", "Runite Ore @red@Dangerous");
			c.dialogueAction = 31;
		break;
		case 28:
			sendOption4("Coins (250,000) @red@10 PK Points", "PK Point Gambles", "PK Point Shop", "Barrows Shop");
			c.dialogueAction = 32;
		break;
		case 29:
			sendOption4("Godsword Gamble @red@3500 PK Points", "Armour Gamble (Bandos/Armadyl/DFS) @red@850 PK Points", "Weapons Gamble (GS/Claws/Etc) @red@1250 PK Points", "More...");
			c.dialogueAction = 33;
		break;
		case 30:
			sendOption4("Void Gamble (All pieces) @red@250 PK Points", "PK Point Shop Gamble (All items) @red@1000 PK Points", "Nevermind.", "");
			c.dialogueAction = 34;
		break;

		case 31:
			sendOption4("1 Spec. Potion @red@10 PK Points", "10 Spec. Potions @red@1 Donator Ticket", "2 Pink Boots @red@1 Donator Ticket", "More Coming Soon...");
			c.dialogueAction = 35;
		break;

		case 32:
			sendNpcChat1("I have deals for donators only!", c.talkingNpc, "");
		break;
		
		case 33:
			sendNpcChat4("Congratulations!","You have killed 20 chickens, lets hope you learned something", "would you like to leave?","Follow the rules!", c.talkingNpc, "Mosol Rei");
			c.dialogueAction = 26;
			c.nextChat = 22;
			break;
				case 9000:
			sendNpcChat2("Hello, " +c.playerName+ ".","What do you need?",c.talkingNpc, "Lumbridge Guide");
			c.nextChat = 9001;
			break;
			

		case 34:
			sendOption2("Yes get me out of here!",  "Nah man, I'm loving it here");
			c.dialogueAction = 27;
			c.nextChat = 0;	
			break;

		case 35:
			sendNpcChat4("You cannot Escape yet!","You've killed "+c.monkeyk0ed+" out of 20 monkeys!","Come back when you have killed 20","Bye", c.talkingNpc, "Mosol Rei");
			c.dialogueAction = 30;
			c.nextChat = 0;
			break;
		case 36:
			sendOption2("@red@High Risk PK","More Coming Soon...");
			c.dialogueAction = 555;
			c.nextChat = 0;
			break;
		
		case 77:
		sendNpcChat4("" + c.playerName +" you have Failed.", "You did participate enough to take down", "the portals. ", "Try Harder next time.",
			 c.talkingNpc, "Void Knight");
			 break;
		case 78:
		sendNpcChat4("All is Lost!", "You could not take down the portals in time.", " ", "Try Harder next time.",
			 c.talkingNpc, "Void Knight");
			 break;
		case 79:
		sendNpcChat4("Congratulations " + c.playerName +"!", "You took down all the portals whilst keeping", "the void knight alive.", "You been awarded, well done.",
			 c.talkingNpc, "Void Knight");
			 break;
		
		case 600:
			sendOption4("Fun PK", "Fight Pits", "Duel Arena", "Barrows ");
			c.dialogueAction = 600;
		break;
		case 606:
			sendOption4("Dark Warrior Fortress @red@(Teleport is safe)@bla@", "Chaos Temple @red@(Teleport is NOT safe)@bla@", "Demonic Ruins @red@(Teleport is NOT safe)@bla@", "Lava Maze @red@(Teleport is NOT safe)@bla@");
			c.dialogueAction = 606;
		break;
		case 9001:
			sendOption4("I want to know more about 'credits'.", "I'd like to exchange my credits for items.", "I'd like to exchange my coins/credits.", "Nevermind.");
			c.dialogueAction = 9001;
		break;
		
		case 9002: //secks
			sendOption2("Normal Shop", "Barrows Shop");
			//c.getShops().openShop(29);
			//sendOption5("Gear sets", "Runes/Spell casts", "Potion sets", "Special items", "");
			c.dialogueAction = 9002;
		break;
		
		case 9003: //shopping creds gear.
			sendOption5("Pure Set [25]", "Melee Set [20]", "Range Set[20]", "Mage Set[15]", "Back");
			c.dialogueAction = 9003;
		break;
		
		case 9004: //pure gear
			if(c.pkPoints <=24){
				sendNpcChat1("You don't have enough credits for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=25){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 25;			
					c.getItems().addItem(544, 1); //monk
					c.getItems().addItem(542, 1); //monk
					c.getItems().addItem(1731, 1); //power ammy
					c.getItems().addItem(3105, 1); //climbing boots
					c.getItems().addItem(5698, 1); //dscim
					c.getItems().addItem(4587, 1); //dds
					c.getItems().addItem(852, 1); //range magebow
					c.getItems().addItem(892, 250); //range runearrows
					c.getItems().addItem(2498, 1); //range dhideshit
					c.getItems().addItem(2491, 1); //range dhideshit
					c.nextChat = 0;
				}
		c.dialogueAction = 9004;
		break;
		
		case 9005: //melee gear
			if(c.pkPoints <=19){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=20){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 10;			
					c.getItems().addItem(1127, 1); //r00nplate
					c.getItems().addItem(1079, 1); //r00nlegz
					c.getItems().addItem(4131, 1); //r00nb00tz
					c.getItems().addItem(1725, 1); //str ammy
					c.getItems().addItem(5698, 1); //dscim
					c.getItems().addItem(4587, 1); //dds
					c.nextChat = 0;
				}
		c.dialogueAction = 9005;
		break;
		
		case 9006: //range gear
			if(c.pkPoints <=19){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=20){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 10;			
					c.getItems().addItem(852, 1); //range magebow
					c.getItems().addItem(892, 250); //range runearrows
					c.getItems().addItem(2498, 1); //range dhideshit
					c.getItems().addItem(2491, 1); //range dhideshit
					c.getItems().addItem(2503, 1); //range dhideshit
					c.getItems().addItem(3105, 1); //climbing boots					
					c.getItems().addItem(1731, 1); //power ammy
					c.nextChat = 0;
				}
		c.dialogueAction = 9006;
		break;
		
				case 9007: //mage gear
			if(c.pkPoints <=14){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=15){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 10;			
					c.getItems().addItem(4089, 1); //mystic
					c.getItems().addItem(4091, 1); //mystic
					c.getItems().addItem(4093, 1); //mystic
					c.getItems().addItem(1731, 1); //power ammy
					c.getItems().addItem(6563, 1); //MudStaff			
					c.getItems().addItem(3105, 1); //climbing boots		
					c.nextChat = 0;
				}
		c.dialogueAction = 9007;
		break;
		case 9008: //shopping creds runes.
				sendOption5("All Runes x 100[20]", "[5]Barrage Runes", "[5]Vengence Runes", "[10]Tele-Block Runes","Back");
				c.dialogueAction = 9008;
		break;
		
		case 9009: //allrunes
			if(c.pkPoints <=19){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=20){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 20;			
					c.getItems().addItem(554, 100);	
					c.getItems().addItem(555, 100);
					c.getItems().addItem(556, 100);
					c.getItems().addItem(557, 100);
					c.getItems().addItem(558, 100);
					c.getItems().addItem(559, 100);
					c.getItems().addItem(560, 100);
					c.getItems().addItem(561, 100);
					c.getItems().addItem(562, 100);
					c.getItems().addItem(563, 100);
					c.getItems().addItem(564, 100);
					c.getItems().addItem(565, 100);
					c.getItems().addItem(566, 100);
					c.getItems().addItem(9075, 100);	
						c.nextChat = 0;
				}
						c.dialogueAction = 9010;
		break;
		
		case 9010: //BarrageRunes
			if(c.pkPoints <=4){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=5){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 5;			
					c.getItems().addItem(560, 100);
					c.getItems().addItem(565, 100);
					c.getItems().addItem(555, 100);
					c.nextChat = 0;
				}
				c.dialogueAction = 9010;
		break;
		
		case 9011: //VengRunes
			if(c.pkPoints <=4){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=5){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 5;			
				c.getItems().addItem(557, 1000);
				c.getItems().addItem(560, 200);
				c.getItems().addItem(9075, 400);
					c.nextChat = 0;
				}
				c.dialogueAction = 9011;
		break;
		case 9012: //cred shop pots.
				sendOption5("Super Set [15]", "Magic Potion [3]", "Range Potion [3]", "[Overloads - Coming Soon]", "Back");
				c.dialogueAction = 9012;
		break;
		case 9013: //sooperset
			if(c.pkPoints <=14){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=15){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 15;			
				c.getItems().addItem(2436, 1);//sup attack 4
				c.getItems().addItem(2440, 1);//sup str 4
				c.getItems().addItem(2442, 1);//sup def 4
				c.getItems().addItem(3024, 1);//sup rest 4
					c.nextChat = 0;
				}
				c.dialogueAction = 9013;
				break;
		case 9014: //ranging
			if(c.pkPoints <=2){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=3){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 3;			
				c.getItems().addItem(2044, 1);//ranging pot
					c.nextChat = 0;
				}
				c.dialogueAction = 9014;
		break;
		case 9015: //mage
			if(c.pkPoints <=2){
				sendNpcChat1("You don't have enough credit for this!",c.talkingNpc, "Lumbridge Guide");
				c.nextChat = 0;
			}
				if(c.pkPoints >=3){ //EDIT ITEMS LATER IF YOU WANT.
					sendNpcChat1("Here, you are.",c.talkingNpc, "Lumbridge Guide");
					c.pkPoints = c.pkPoints - 3;			
				c.getItems().addItem(3040, 1);//ranging pot
					c.nextChat = 0;
				}
				c.dialogueAction = 9015;
		break;
		case 9016:
			sendNpcChat1("[Dialogue In Progress - Ryan]",c.talkingNpc, "Lumbridge Guide");
			c.nextChat = 0;
		break;
		case 9017:
			sendOption2("Exchange Credits for Coins","Exchange Coins for Credits");
			c.dialogueAction = 9017;
		break;
		case 9018:
			sendNpcChat2("Enter the amount of times you wish to exchange.","1 Credit is worth 2,500 Coins.",c.talkingNpc, "Lumbridge Guide");
			c.nextChat = 9020;
		break;
		case 9019:
			sendNpcChat2("Enter the amount of times you wish to exchange.","10,000 coins is worth 1 Credit.",c.talkingNpc, "Lumbridge Guide");
			c.nextChat = 9021;
		break;
		case 9020:		
		c.getPA().removeAllWindows();
			c.outStream.createFrame(27);
			c.exchange = 1;
			c.mageSkill = false;
			c.attackSkill = false;
			c.strengthSkill = false;
			c.rangeSkill = false;
			c.defenceSkill = false;
			c.prayerSkill = false;
			c.healthSkill = false;
		break;
		
		case 9021:		
		c.getPA().removeAllWindows();
			c.outStream.createFrame(27);
			c.exchange = 2;
			c.mageSkill = false;
			c.attackSkill = false;
			c.strengthSkill = false;
			c.rangeSkill = false;
			c.defenceSkill = false;
			c.prayerSkill = false;
			c.healthSkill = false;
		break;
		
		case 9022:
			sendNpcChat2("Credits are a score system used to purchase","special items in ForeverPkers.",c.talkingNpc, "Lumbridge Guide");
			c.nextChat = 9023;
		break;
		case 9023:
			sendNpcChat3("You gain these 'credits' by Player Killing,","Boss Hunting, and other miscellanious events","in the game.",c.talkingNpc, "Lumbridge Guide");
			c.nextChat = 0;
		break;
		
		case 9024:
			sendNpcChat2("Welcome to ForeverPkers! I'm going to quickly","explain the basics to you and show you around.",945, "Guide");
			c.finishedTutorial = true;
			c.nextChat = 9025;
		break;
		case 9025:
			sendNpcChat2("You will have to do this every time you","login with less than 3 player kills.",945, "Guide");
			c.nextChat = 9026;
		break;
		case 9026:
			sendNpcChat3("ForeverPkers is based on a currency known as 'credits'.","These credits are obtained from Player Killing,","Boss Hunting, and other miscellanious events.",945, "Guide");
			c.nextChat = 9027;
		break;
		case 9027:
			c.getPA().movePlayer(3086, 3490, 0);
			sendNpcChat3("Credits can be exchanged by talking to the Lumbridge Guide.","You can choose to exchange them for coins or items,","and even exchange coins for credits.",945, "Guide");
			c.nextChat = 9028;
		break;
		case 9028:
			sendNpcChat2("You cannot trade until you get 3 player kills.","This is to prevent transferring gold.",945, "Guide");
			c.nextChat = 9029;
		break;
		case 9029:
			sendNpcChat2("You can change your magic in the quest tab, and also","check your risked items there.",945, "Guide");
			c.nextChat = 9030;
		break;
		case 9030:
			c.getPA().movePlayer(3272, 3399, 0);
			sendNpcChat4("I'll leave you here to buy your equipment.","Be sure you check your quest tab before buying","items. There may be cheaper items there.","Alternatively, you can get here by typing ::shops",945, "Guide");
			c.nextChat = 9031;
		break;
		case 9031:
			sendNpcChat3("Oh, I almost forgot! To train your combat skills,","simply click them in your skill tab, and enter","the level you want in it!",945, "Guide");
			c.nextChat = 9032;
		break;
		
		
		case 9032:
			c.sendMessage("Hope you enjoy ForeverPkers!");
			c.sendMessage("Talk to the Legends Guard to spend your Gear Points.");
			c.finishedTutorial = false;
			c.endTutorial();
			c.getPA().sendFrame99(0);
			c.nextChat = 0;
		break;
		
		case 9033:
			sendOption4("Black Knight Titan (Low-Level)", "King Black Dragon @red@(40 Wild)", "Dagannoth mother @red@(Dangerous)", "Next page...");
			c.dialogueAction = 9033;
		break;

		case 9046:
			sendOption4("Dagannoth kings @red@(Dangerous)", "Broodoo brothers @red@(Dangerous)", "", "");
			c.dialogueAction = 9046;
		break;

		case 9047:
			sendStatement("Teleport to a random monster area in the wilderness?");
			c.nextChat = 9048;
		break;
		case 9048:
			sendOption2("Yes.","No thanks, I'm scared.");
			c.dialogueAction = 9048;
		break;
		
		case 9034:
			c.getShops().openShop(29);
			//show me genitals
		break;

		case 9035:
			c.getShops().openShop(32);
			//your genitalia
			// faggot
			break;
		
		case 9036:
			sendNpcChat3("Are you looking for the shops? To get to them, type ::shops.","There you can spend your coins, and your Gear Points.","You gain 1000 Gear Points every 5 minutes.",398, "Legends Guard");
			c.nextChat = 0;
		break;

		case 9999:
			sendNpcChat1("Do you want to claim a Veteran or Completionist cape?",c.talkingNpc,"Mysterious Old Man");
			c.nextChat = 19999;
		break;
		case 19999:
			sendOption4("Veteran cape", "Completionist cape", "No, sir.", "");
			c.dialogueAction = 19999;
		break;
		case 10018:
			sendNpcChat1("Please accept this as a gift of your dedication to ForeverPkers.",c.talkingNpc,"Mysterious Old Man");
		break;
		case 10019:
			sendNpcChat1("I'm sorry, but I don't think you have been with us long enough to be named a Veteran.",c.talkingNpc,"Mysterious Old Man");
		break;
		case 20002:
			sendNpcChat1("You don't meet all of the requirements yet!",c.talkingNpc,"Mysterious Old Man");
			
			if(c.dialogueAction != 20001)
				c.nextChat = 20004;
			else
				c.nextChat = -1;
		break;
		case 20004:
			sendStatement("@red@Requires a minimum of 500 kill count.");
			c.nextChat = -1;
		break;
		case 20000:
			sendNpcChat4("Have a Slayer level of 99, at","least 1,000 kill count and 50 killstreak.","You may claim the cape once you've", "met these requirements.",410,"To have the Completionist cape, you must:");
			c.nextChat = 20001;
		break;
		case 20001:
			sendOption2("Accept gift.", "Decline gift.");
			c.dialogueAction = 20001;
		break;
		case 20003:
			sendOption2("Vote Shop", "Fancy Vote Shop");
			c.dialogueAction = 20003;
		break;

		case 9037:
			sendNpcChat1("You have @blu@"+c.EP+"@bla@ Event points.", c.talkingNpc, "Vannaka");
			c.nextChat = -1;
		break;
		
		case 10500:
			sendOption4("What are PK Challenge points?", "View PK Challenge weapons", "Exchange PK Challenge points", "Nevermind.");
			c.dialogueAction = 10500;
		break;
		
		case 10501:
			sendNpcChat3("PK Challenge points are earned by","using the weapons supplied in my shop","to deal the final blow to your enemy.", c.talkingNpc, "Vannaka");
			c.nextChat = 10502;
		break;
		case 10502:
			sendNpcChat4("It's simple really! Oh yes, and I must mention,","after earning a certain number of points,","you can come back to me and exchange them", "for many types of great rewards.", c.talkingNpc, "Vannaka");
			c.nextChat = 10503;
		break;
		case 10503:
			sendNpcChat1("Is there anything else I can do for you?", c.talkingNpc, "Vannaka");
			c.nextChat = 10500;
		break;
		case 10504:
			sendOption4("Exchange for Weapons @red@(10 Challenge points)", "Exchange for Armor @red@(10 Challenge points)", "Exchange for Accessories @red@(5 Challenge points)", "More...");
			c.dialogueAction = 10504;
		break;
		case 10505:
			sendOption4("Exchange for Better Weapons @red@(40 Challenge points)", "Exchange for Better Armor @red@(40 Challenge points)", "Exchange for Better Accessories @red@(30 Challenge points)", "More...");
			c.dialogueAction = 10505;
		break;
		case 10506:
			sendOption4("Are you sure you would like to box?", "You can see your opponent's risk on the right.", "Yes.", "No thanks.");
			c.dialogueAction = 10506;
		break;
			
			case 9038:
			sendStatement("You must have 60 Donator Tickets to open the mask gamble chest!");
			c.nextChat = 0;
			break;

			case 9039:
			sendNpcChat4("You are about to enter high-risk PK.","@red@Multi combat, protect item disabled","@red@You will not be skulled.","@blu@Double PK Points, always get loot from enemy.",2262,"@red@- WARNING -");
			c.nextChat = 9040;
			break;
			case 9040:
			sendOption2("Single PK", "Multi PK");
			c.dialogueAction = 9040;
			//c.nextChat = 0;
			break;

			case 9041:
			sendStatement("Are you sure you want to use 25 Donator Tickets on the chest?");
			c.nextChat = 9042;
			break;
			case 9042:
			sendOption2("Yes, I want use 25 Donator Tickets to get either a key or a present.", "No thanks.");
			c.dialogueAction = 9042;
			break;
			
			case 15000:
			sendStatement("Are you here to buy the dice command?");
			c.nextChat = 15001;
			break;
			case 15002:
			sendOption2("Yes, I want to use 20 donator tickets to buy it.", "No thanks.");
			c.dialogueAction = 15002;
			break;
			case 15001:
			sendNpcChat4("It costs 20 donator tickets,","you need to be a super donator,","you need to have a killcount of over 100","and you must not abuse it.",410,"The '::dice' command is not free.");
			c.nextChat = 15002;
			break;

			case 9043:
			sendStatement("Old School PK, new items disabled.");
			c.nextChat = 9044;
			break;
			case 9044:
			sendOption2("Let's go!", "No thanks.");
			c.dialogueAction = 9044;
			break;

			case 9045:
			sendOption4("Combat 126, Maxed.","Combat 101, Defence 1.","Combat 86, Pure.","Combat 76, Pure");
			c.dialogueAction = 9045;
			break;


			case 10000:
				if(c.worshippedGod == 0){
					sendStatement("Would you like to pledge your loyalty to Saradomin?");
					c.nextChat = 10002;
				} else if(c.worshippedGod == 2){
					sendStatement("You are a Zamorak worshipper, you shouldn't pray to Saradomin.");
					c.nextChat = 0;
				} else if(c.worshippedGod == 1){
					sendOption4("What is my reputation with Saradomin?",
								"What are Saradomin's blessings?",
								"I would like to renounce my loyalty to Saradomin.",
								"Nevermind.");
					c.dialogueAction = 10000;
				}
			break;
			
			case 10002:
				sendOption2("Yes, I pledge my loyalty to Saradomin!", "I need more time to think");
				c.dialogueAction = 10002;
				c.nextChat = 0;	
			break;
			
			case 10003:
				if(c.worshippedGod == 1)
				sendStatement("Your reputation with " + c.worshippedGodString() + " is " + c.godReputation+".");
				else if(c.worshippedGod == 2)
				sendStatement("Your reputation with " + c.worshippedGodString() + " is " + c.godReputation2+".");
				c.nextChat = 0;
			break;

			case 10004:
				if(c.worshippedGod == 0){
					sendStatement("Would you like to pledge your loyalty to Zamorak?");
					c.nextChat = 10006;
				} else if(c.worshippedGod == 1){
					sendStatement("You are a Saradomin worshipper, you shouldn't pray to Zamorak.");
					c.nextChat = 0;
				} else if(c.worshippedGod == 2){
					sendOption4("What is my reputation with Zamorak?",
								"What are Zamorak's blessings?",
								"I would like to renounce my loyalty to Zamorak.",
								"Nevermind.");
					c.dialogueAction = 10004;
				}
			break;
			
			case 10006:
				sendOption2("Yes, I pledge my loyalty to Zamorak!", "I need more time to think.");
				c.dialogueAction = 10006;
				c.nextChat = 0;			
			break;

			case 10007:
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
				
				if(c.worshippedGod == 1){
					String subMessage = "[LOCKED]";
					
					if(c.godReputation >= 50)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 50]";
					
					c.sendMessage(subMessage + " Saradomin's Defence - All defence bonuses increased by 2%");
					
					if(c.godReputation >= 100)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 100]";
					
					c.sendMessage(subMessage + " Blessing of Protection - Less prayer drained from prot. prayers");
					
					if(c.godReputation >= 200)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 200]";
					
					c.sendMessage(subMessage + " Saradomin's Brewery - 50% chance of not losing stats to brews");
					
					if(c.godReputation >= 250)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 250]";
			
					c.sendMessage(subMessage + " Saradomin's Generosity - 25% chance of an extra target point");
					
					if(c.godReputation >= 500)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 500]";
					
					c.sendMessage(subMessage + " Saradomin's Defence - All defence bonuses increased by 3.5%");
					
					if(c.godReputation >= 2500)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 2500]";
					
					c.sendMessage(subMessage + " Saradomin's Defence - All defence bonuses increased by 5%");
					
				} else if(c.worshippedGod == 2){
					String subMessage = "[LOCKED]";
					
					if(c.godReputation2 >= 50)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 50]";
					
					c.sendMessage(subMessage + " Zamorak's Strength - Attack, ranged and magic increased by 2%");
					
					if(c.godReputation2 >= 100)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 100]";
					
					c.sendMessage(subMessage + " Battle Meditation - Special attack regens faster");
					
					if(c.godReputation2 >= 200)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 200]";
					
					c.sendMessage(subMessage + " Zamorak's Greed - 15% chance to gain 5 extra PK Points");
					
					if(c.godReputation2 >= 250)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 250]";
					
					c.sendMessage(subMessage + " Zamorak's Smite - Stronger Smite prayer");

					if(c.godReputation2 >= 500)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 500]";
					
					c.sendMessage(subMessage + " Zamorak's Strength - Attack, ranged and magic increased by 3.5%");	
		
					if(c.godReputation2 >= 2500)	subMessage = "@blu@[UNLOCKED]";
					else subMessage = "@red@[LOCKED - Req. 2500]";
					
					c.sendMessage(subMessage + " Zamorak's Strength - Attack, ranged and magic increased by 5%");	
				}
				
			break;
			
			case 10008:
				sendStatement("Are you sure?");
				c.nextChat = 10009;
			break;
			
			case 10009: //Renounce comfirm
				sendOption2("Yes, I want to change gods.","No, I want to stay loyal to " + c.worshippedGodString() + ".");
				c.dialogueAction = 10009;
			break;
			
			case 10010:
				c.worshippedGod = 0;
				sendStatement("You may now worship a new god.");
				c.questTab();
				c.nextChat = 0;
			break;
			
			case 10011:
				sendNpcChat1("I got ALL the shops, nigga.",4716,"Trader Sven");
				c.nextChat = 10012;
			break;
			
			case 10012:
				sendOption4("Armour Shop","Archer Shop","Food and Potions","More...");
				c.dialogueAction = 10012;
				c.nextChat = 10012;
			break;
			
			case 10013:
				sendOption4("Wizardly 'Wears'", "Magic Accessories", "Weapons and Accessories", "Nevermind.");
				c.dialogueAction = 10013;
				c.nextChat = 0;
			break;
			
			case 10014:
				sendNpcChat2("Hi. im a faggot and my chin looks like a dick", "want to fight boss with ur @mag@fagget@bla@ clan??//", 470, "fagget gnome");
				c.nextChat = 10015;
			break;
			
			case 10015:
				sendOption2("Okay.","No Thanks.");
				c.dialogueAction = 10015;
			break;

			case 10016:
				String option1 = c.bankPin.equalsIgnoreCase("") ? 
						"I would like to set up a bank PIN." : "I would like to remove my bank pin";
				sendOption2(option1, "Nevermind.");
				c.dialogueAction = 10016;
			break;
			
			case 10017:
				sendStatement("Are you sure you want to remove your bank pin?");
				c.nextChat = 10020;
			break;
			
			case 10020:
				sendOption2("Yes.", "No.");
				c.dialogueAction = 10020;
			break;
			
			case 10021:
				sendNpcChat2("Welcome to pandemonium, soldier.", "What do you request?", 2790, "Sergeant Damien");
				c.nextChat = 10022;
			break;
			
			case 10022:
				sendOption4("What rewards can I get from pandemonium missions?", "What are the pandemonium missions?", "I would like to start a mission.", "Nevermind.");
				c.dialogueAction = 10022;
			break;
			
			case 10023:
				sendOption4("Undead Standoff", "@red@", "@red@", "@red@");
				c.dialogueAction = 10023;
			break;

			case 10027:
				sendNpcChat2("This will cost 250,000 coins. Are you", "willing to pay?", 2790, "Sergeant Damien");
				c.nextChat = 10025;
			break;

			case 10025:
				sendOption2("Of course!", "No, can't afford it sorry.");
				c.dialogueAction = 10025;
			break;

			case 10026:
				sendNpcChat1("Then get yourself in-line, son!", 2790, "Sergeant Damien");
				c.nextChat = -1;
			break;

			case 10024:

				if(c.bonusPand > 0)
					sendNpcChat2("You may still earn "+c.bonusPand+" bonus", "pandemonium points today.", 2790, "Sergeant Damien");
				else
					sendNpcChat3("Since you have used all of your bonus pandemonium", "points, you will receive the normal amount of points", "until tomorrow. Bonus points refill daily.", 2790, "Sergeant Damien");

				c.nextChat = 10027;
			break;
		}


	}
	
	/*
	 * Information Box
	 */
	
	public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}
	
	/*
	 * Options
	 */
	
	private void sendOption(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2470);
	 	c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126("Click here to continue", 2473);
		c.getPA().sendFrame164(13758);
	}	
	
	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	private void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2469);
	}
	
	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}
	
	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */
	
	private void sendStatement(String s) { // 1 line click here to continue chat box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}
	
	/*
	 * Npc Chatting
	 */
	
	private void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}
	
	private void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 591);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}
	
	private void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
		c.getPA().sendFrame200(4894, 591);
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}
	
	private void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}
	
	/*
	 * Player Chating Back
	 */
	
	private void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(c.playerName, 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}
	
	private void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(c.playerName, 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}
	
	private void sendPlayerChat3(String s, String s1, String s2) {
		c.getPA().sendFrame200(980, 591);
		c.getPA().sendFrame126(c.playerName, 981);
		c.getPA().sendFrame126(s, 982);
		c.getPA().sendFrame126(s1, 983);
		c.getPA().sendFrame126(s2, 984);
		c.getPA().sendFrame185(980);
		c.getPA().sendFrame164(979);
	}
	
	private void sendPlayerChat4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(c.playerName, 988);
		c.getPA().sendFrame126(s, 989);
		c.getPA().sendFrame126(s1, 990);
		c.getPA().sendFrame126(s2, 991);
		c.getPA().sendFrame126(s3, 992);
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
	}
}
