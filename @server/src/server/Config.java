package server;

import server.util.Misc;


public class Config {

	public static final int DOUBLE_VOTES = 1;//0 for false, 1 for true
	
	public static final boolean SERVER_DEBUG = false;//needs to be false for Real world to work
	
	public static boolean SENDSERVERPACKETS = false;
	
	public static final String SERVER_NAME = "Massacred World";
	public static final String WELCOME_MESSAGE = "Massacred World.com";
	public static final String FORUMS = "Massacred World.com/forum";
	
	public static final int CLIENT_VERSION = 1;
	
	public static int MESSAGE_DELAY = 6000;
	public static final int ITEM_LIMIT = 25000; // item id limit, different clients have more items like silab which goes past 19114
	public static final int MAXITEM_AMOUNT = Integer.MAX_VALUE;
	public static final int BANK_SIZE = 352;
	public static final int MAX_PLAYERS = 1024;

	public static int REGION_SIZE = 0;
	 public static int REGION_AMOUNT = 70;
	public static int REGION_DECREASE = 6;
	public static int REGION_NORMALREGION = 32;
	public static final int CONNECTION_DELAY = 100; // how long one ip can keep connecting
	public static final int IPS_ALLOWED = 3; // how many ips are allowed
		
	public static final boolean WORLD_LIST_FIX = false; // change to true if you want to stop that world--8 thing, but it can cause the screen to freeze on silabsoft client
	
	public static final int[] ITEM_SELLABLE 		=	{2528,4447,4170,4171,6886,6887,6885,19111,10552,10548,10549,4083,4084,9813,9814,3058,3059,6570,1052,391,392,385,386,995,996,15272,15273,773,774,7671,7672,20748,20763,20752,20769,20770}; // what items can't be sold in any store
	public static final int[] ITEM_TRADEABLE 		= 	{2528,4447,4170,4171,6886,6887,6885,20770,20769,19111,1059,20764,20763,1645,1653,4083,9813,3058,6570,1052,773}; // what items can't be traded or staked
	public static final int[] UNDROPPABLE_ITEMS 	= 	{2528,4447,4170,4171,20770,20769,19111,1059,20764,20763,1645,1653,4083,9813,6570, 1052}; // what items can't be dropped
	public static final int[] ITEMS_KEPT_ON_DEATH = {6570,6885,6886,6887,19111,20748,20752,20769,20770};
	
	public static final int[] FUN_WEAPONS	=	{2460,2461,2462,2463,2464,2465,2466,2467,2468,2469,2470,2471,2471,2473,2474,2475,2476,2477}; // fun weapons for dueling
	
	public static final boolean ADMIN_CAN_TRADE = true; //can admins trade?
	public static final boolean ADMIN_CAN_SELL_ITEMS = true; // can admins sell items?
	public static final boolean ADMIN_DROP_ITEMS = true; // can admin drop items?

public static final int[] PVP_ARMOR = { 13876,13864,13896,13861,13858,13902,13899,13890,13884,13893,13887,13873,13870 };
public static final int[] PVP_ARMOR2 = { 13876, 13864, 13896 };
	
	public static final int START_LOCATION_X = 3096; // start here
	public static final int START_LOCATION_Y = 3503;
	public static final int RESPAWN_X = 3096; // when dead respawn here
	public static final int RESPAWN_Y = 3503;
	public static final int HOME_X = 3087; // when dead respawn here
	public static final int HOME_Y = 3496;
	public static final int DUELING_RESPAWN_X = 3362; // when dead in duel area spawn here
	public static final int DUELING_RESPAWN_Y = 3263;
	public static final int RANDOM_DUELING_RESPAWN = 5; // random coords
	
	public static final int NO_TELEPORT_WILD_LEVEL = 20; // level you can't tele on and above
	public static final int SKULL_TIMER = 1200; // how long does the skull last? seconds x 2
	public static final int TELEBLOCK_DELAY = 20000; // how long does teleblock last for.
	public static final boolean SINGLE_AND_MULTI_ZONES = true; // multi and single zones?
	public static final boolean COMBAT_LEVEL_DIFFERENCE = true; // wildy levels and combat level differences matters
	
	public static final boolean itemRequirements = true; // attack, def, str, range or magic levels required to wield weapons or wear items?
	public static final int EXP_GAINER = 1000;
	public static final int MELEE_EXP_RATE = EXP_GAINER; // damage * exp rate
	public static final int RANGE_EXP_RATE = EXP_GAINER;
	public static final int MAGIC_EXP_RATE = EXP_GAINER;
	public static final double SERVER_EXP_BONUS = 1;
	
	public static final int INCREASE_SPECIAL_AMOUNT = 17500; // how fast your special bar refills
	public static final int INCREASE_SPECIAL_AMOUNT_ZAM = 14500;
	public static final boolean PRAYER_POINTS_REQUIRED = true; // you need prayer points to use prayer
	public static final boolean PRAYER_LEVEL_REQUIRED = true; // need prayer level to use different prayers
	public static final boolean MAGIC_LEVEL_REQUIRED = true; // need magic level to cast spell
	public static final int GOD_SPELL_CHARGE = 300000; // how long does god spell charge last?
	public static final boolean RUNES_REQUIRED = true; // magic rune required?
	public static final boolean CORRECT_ARROWS = true; // correct arrows for bows?
	public static final boolean CRYSTAL_BOW_DEGRADES = false; // magic rune required?
	
	public static final int SAVE_TIMER = 5000; // save every 1 minute
	public static final int NPC_RANDOM_WALK_DISTANCE = 5; // the square created , 3x3 so npc can't move out of that box when randomly walking
	public static final int NPC_FOLLOW_DISTANCE = 10; // how far can the npc follow you from it's spawn point,
	public static final int SEA_NPCS_FOLLOW_DISTANCE = 0;
	public static final int[] UNDEAD_NPCS = {90,91,92,93,94,103,104,73,74,75,76,77,3066,3622,2025,2026,2027,2028,2029,2030,1612,1613,1648,1653,126}; // undead npcs

	/**
	 * Barrows Reward
	 */
	
	
	/**
	 * Glory
	 */
	public static final int EDGEVILLE_X = 3094;
	public static final int EDGEVILLE_Y = 3469;
	public static final String EDGEVILLE = "";
	public static final int AL_KHARID_X = 3293;
	public static final int AL_KHARID_Y = 3174;
	public static final String AL_KHARID = "";
	public static final int KARAMJA_X = 3087;
	public static final int KARAMJA_Y = 3500;
	public static final String KARAMJA = "";
	public static final int MAGEBANK_X = 2538;
	public static final int MAGEBANK_Y = 4716;
	public static final String MAGEBANK = "";
	
	/**
	* Teleport Spells
	**/
	// modern
	public static final int VARROCK_X = 3213;
	public static final int VARROCK_Y = 3423;
	public static final String VARROCK = "";
	public static final int LUMBY_X = 3222;
	public static final int LUMBY_Y = 3218;
	public static final String LUMBY = "";
    public static final int FALADOR_X = 2964;
	public static final int FALADOR_Y = 3378;
	public static final String FALADOR = "";
	public static final int CAMELOT_X = 2757;
	public static final int CAMELOT_Y = 3477;
	public static final String CAMELOT = "";
	public static final int ARDOUGNE_X = 2662;
	public static final int ARDOUGNE_Y = 3305;
	public static final String ARDOUGNE = "";
	public static final int WATCHTOWER_X = 3087;
	public static final int WATCHTOWER_Y = 3500;
	public static final String WATCHTOWER = "";
	public static final int TROLLHEIM_X = 3243;
	public static final int TROLLHEIM_Y = 3513;
	public static final String TROLLHEIM = "";
	
	// ancient
	
	public static final int PADDEWWA_X = 3098;
	public static final int PADDEWWA_Y = 9884;
	
	public static final int SENNTISTEN_X = 3322;
	public static final int SENNTISTEN_Y = 3336;

    public static final int KHARYRLL_X = 3492;
	public static final int KHARYRLL_Y = 3471;

	public static final int LASSAR_X = 3006;
	public static final int LASSAR_Y = 3471;
	
	public static final int DAREEYAK_X = 3161;
	public static final int DAREEYAK_Y = 3671;
	
	public static final int CARRALLANGAR_X = 3156;
	public static final int CARRALLANGAR_Y = 3666;
	
	public static final int ANNAKARL_X = 3288;
	public static final int ANNAKARL_Y = 3886;
	
	public static final int GHORROCK_X = 2977;
	public static final int GHORROCK_Y = 3873;
 
	public static final int TIMEOUT = 20;
	public static final int CYCLE_TIME = 600;
	public static final int BUFFER_SIZE = 10000;
	public static final int MAX_PROCESS_PACKETS = 7;
	
	/**
	 * Slayer Variables
	 */
	public static final int[][] SLAYER_TASKS = {{1,87,90,4,5}, //low tasks
												{6,7,8,9,10}, //med tasks
												{11,12,13,14,15}, //high tasks
												{1,1,15,20,25}, //low reqs
												{30,35,40,45,50}, //med reqs
												{60,75,80,85,90}}; //high reqs
	
	/**
	* Skill Experience Multipliers
	*/	
	/*public static final int WOODCUTTING_EXPERIENCE = 10;
	public static final int MINING_EXPERIENCE = 20;
	public static final int SMITHING_EXPERIENCE = 20;
	public static final int FARMING_EXPERIENCE = 100;
	public static final int FIREMAKING_EXPERIENCE = 10;
	public static final int HERBLORE_EXPERIENCE = 40;
	public static final int FISHING_EXPERIENCE = 7;
	public static final int AGILITY_EXPERIENCE = 40;
	public static final int PRAYER_EXPERIENCE = 80;
	public static final int RUNECRAFTING_EXPERIENCE = 40;
	public static final int CRAFTING_EXPERIENCE = 7;
	public static final int THIEVING_EXPERIENCE = 7;
	public static final int SLAYER_EXPERIENCE = 50;
	public static final int COOKING_EXPERIENCE = 7;
	public static final int FLETCHING_EXPERIENCE = 10;*/

	public static final int WOODCUTTING_EXPERIENCE = 10;
	public static final int MINING_EXPERIENCE = 20;
	public static final int SMITHING_EXPERIENCE = 20;
	public static final int FARMING_EXPERIENCE = 10;
	public static final int FIREMAKING_EXPERIENCE = 10;
	public static final int HERBLORE_EXPERIENCE = 40;
	public static final int FISHING_EXPERIENCE = 10;
	public static final int AGILITY_EXPERIENCE = 10;
	public static final int PRAYER_EXPERIENCE = 80;
	public static final int RUNECRAFTING_EXPERIENCE = 40;
	public static final int CRAFTING_EXPERIENCE = 20;
	public static final int THIEVING_EXPERIENCE = 20;
	public static final int SLAYER_EXPERIENCE = 20;
	public static final int COOKING_EXPERIENCE = 40;
	public static final int FLETCHING_EXPERIENCE = 40;

public static final int[][] NPC_DROPS = {//NPC ID, Item ID, Amount, if Misc.random(Lastnumber) == 0, it drops
	
	//{, 5304, 6, 90}//torstol, {, 5303, 6, 70}//dwarf weed, {, 5300, 6, 70}//snapdragon, {, 5302, 6, 65}//lantadyme, {, 5301, 6, 60}//cadantine, {, 5299, 6, 55}//kwuarm, {, 5295, 6, 50}//ranarr, {, 5298, 6, 50}//avantoe, {, 5297, 6, 45}//irit, {, 5296, 6, 40}//toadflax, {, 5294, 6, 35}//harralander, {, 5293, 6, 30}//tarromin, {, 5292, 6, 25}//marrentill, {, 5291, 6, 20}//guam, //herb seeds
	//{, 6571, 1, 75} onyx {, 1632, 30, 80}//dragonstone, {, 1604, 15, 50}//diamond, {, 1604, 15, 35}//ruby, {, 1606, 15, 25}//emerald, {, 1608, 15, 20}//sapphire, //gems
	//{, 398, 15, 80}//sea turtle, {, 385, 15, 80} unnoted shark, {, 3145, 20, 70}//karambwan, {, 7061, 125, 65}//tuna potato, {, 392, 45, 50}//manta ray, //cooked food
	//{, 1516, 10, 75}//yew, {, 1518, 10, 60}//maple, {, 1520, 10, 45}//willow, {, 1522, 10, 30}//oak, {, 1522, 10, 15}//normal, //logs
	//{, 3140, 1, 65}//dchain, {, 4087, 1, 60}//dlegs, {, 4585, 1, 60}//dskirt, {, 1187, 1, 50}//dsqshield,{, 1149, 1, 40}//dmed, //dragon armor items
	//{, 9245, 10, 70}//onyxbolts(e), {, 2365, 5, 40}//perfect bars, {, 9244, 150, 35}//dbolts(e), //misc
	// herbs noted {, 270, 6, 50}// torstol {, 268, 12, 45} //dwarf weed
	// herbs {, 269, 1, 30}// torstol {, 267, 1, 25} //dwarf weed
	
		//wyverns
	{3068, 6812, 1, 0}, {3068, 13740, 1, 10000}, {3068, 10887, 1, 1300}, {3068, 11724, 1, 500}, {3068, 11726, 1, 500}, {3068, 9245, 75, 300}, {3068, 3140, 1, 150}, {3068, 85, 1, 125}, {3068, 5304, 12, 110}, {3068, 2365, 6, 100}, {3068, 6571, 1, 75}, {3068, 1359, 1, 75}, {3068, 1373, 1, 75}, {3068, 560, 75, 75}, {3068, 566, 50, 75}, {3068, 892, 15, 75}, {3068, 995, 25000000, 125}, {3068, 9185, 1, 65}, {3068, 398, 10, 45}, {3068, 9244, 65, 40},
	{3069, 6812, 1, 0}, {3069, 13740, 1, 10000}, {3069, 10887, 1, 1300}, {3069, 11724, 1, 500}, {3069, 11726, 1, 500}, {3069, 9245, 75, 300}, {3069, 3140, 1, 150}, {3069, 85, 1, 125}, {3069, 5304, 12, 110}, {3069, 2365, 6, 100}, {3069, 6571, 1, 75}, {3069, 1359, 1, 75}, {3069, 1373, 1, 75}, {3069, 560, 75, 75}, {3069, 566, 50, 75}, {3069, 892, 15, 75}, {3069, 995, 25000000, 125}, {3069, 9185, 1, 65}, {3069, 398, 10, 45}, {3069, 9244, 65, 40},
	{3070, 6812, 1, 0}, {3070, 13740, 1, 10000}, {3070, 10887, 1, 1300}, {3070, 11724, 1, 500}, {3070, 11726, 1, 500}, {3070, 9245, 75, 300}, {3070, 3140, 1, 150}, {3070, 85, 1, 125}, {3070, 5304, 12, 110}, {3070, 2365, 6, 100}, {3070, 6571, 1, 75}, {3070, 1359, 1, 75}, {3070, 1373, 1, 75}, {3070, 560, 75, 75}, {3070, 566, 50, 75}, {3070, 892, 15, 75}, {3070, 995, 25000000, 125}, {3070, 9185, 1, 65}, {3070, 398, 10, 45}, {3070, 9244, 65, 40},
	{3071, 6812, 1, 0}, {3071, 13740, 1, 10000}, {3071, 10887, 1, 1300}, {3071, 11724, 1, 500}, {3071, 11726, 1, 500}, {3071, 9245, 75, 300}, {3071, 3140, 1, 150}, {3071, 85, 1, 125}, {3071, 5304, 12, 110}, {3071, 2365, 6, 100}, {3071, 6571, 1, 75}, {3071, 1359, 1, 75}, {3071, 1373, 1, 75}, {3071, 560, 75, 75}, {3071, 566, 50, 75}, {3071, 892, 15, 75}, {3071, 995, 25000000, 85}, {3071, 9185, 1, 65}, {3071, 398, 10, 45}, {3071, 9244, 65, 40},

	{191, 526, 1, 0},
	{191, 6335, 1, 25}, {191, 6341, 1, 15}, {191, 6343, 1, 15},
	{191, 6337, 1, 25}, {191, 6361, 1, 15}, {191, 6363, 1, 15},
	{191, 6339, 1, 25}, {191, 6353, 1, 15}, {191, 6353, 1, 15},
			
	{2497, 526, 1, 0},
	{2497, 6335, 1, 25}, {2497, 6341, 1, 15}, {2497, 6343, 1, 15},
	{2497, 6337, 1, 25}, {2497, 6361, 1, 15}, {2497, 6363, 1, 15},
	{2497, 6339, 1, 25}, {2497, 6351, 1, 15}, {2497, 6353, 1, 15},
			
	{2499, 526, 1, 0},{2499, 6215, 1, 75},{2499, 11788, 1, 25},
	{2501, 526, 1, 0},{2501, 6237, 1, 75},{2501, 11788, 1, 25},
	{2503, 526, 1, 0},{2503, 6259, 1, 75},{2503, 11788, 1, 25},
			   
			   
			   
			   
			   
		//General graardor	   
			{2550,4834,1,0},{2550,11696,1,250},{2550,11724,1,175},{2550,11726,1,175},{2550,5304,10,50},{2550,268,15,40},{2550,995,2500000,30},{2550,452,6,25},{2550,1113,1,20},{2550,1123,1,10},{2550,450,15,5},{2550,995,500000,1}, 
		//Bandos minion
			{2551,526,1,0},{2551,11724,1,1275},{2551,11726,1,1275},{2551,5304,10,50},{2551,268,15,40},{2551,995,2500000,30},{2551,452,6,25},{2551,1113,1,20},{2551,1123,1,10},{2551,450,15,5},{2551,995,500000,1}, 
			{2552,526,1,0},{2552,11724,1,1275},{2552,11726,1,1275},{2552,5304,10,50},{2552,268,15,40},{2552,995,2500000,30},{2552,452,6,25},{2552,1113,1,20},{2552,1123,1,10},{2552,450,15,5},{2552,995,500000,1}, 
			{2553,526,1,0},{2553,11724,1,1275},{2553,11726,1,1275},{2553,5304,10,50},{2553,268,15,40},{2553,995,2500000,30},{2553,452,6,25},{2553,1113,1,20},{2553,1123,1,10},{2553,450,15,5},{2553,995,500000,1}, 
		//k'ril tsutsaroth
			{2554,592,1,0},{2554,15042,1,1500},{2554,18391,1,1250},{2554,15590,1,150},{2554,15592,1,150},{2554,15593,1,150},{2554,10364,1,150},{2554,10366,1,150},{2554,2417,1,150},{2554,10362,1,140},{2554,11728,1,140},{2554,14876,1,130},{2554,11708,1,100},{2554,9245,50,70},{2554,1615,3,60},{2554,10368,1,40},{2554,10374,1,40},{2554,1547,3,40},{2554,85,3,21},{2554,15220,1,21},{2554,15020,1,21},{2554,15019,1,21},{2554,15018,1,21},{2554,10370,1,21},{2554,10372,1,21},
		//k'ril minions
			{2555,592,1,0},{2555,14879,1,0},{2555,15590,1,650},{2555,15592,1,650},{2555,15593,1,650},{2555,11728,1,500},{2555,10362,1,400},{2555,11716,1,350},{2555,2417,1,250},{2555,1547,1,75},{2555,1615,1,45},{2555,9245,20,35},{2555,85,1,5},
			{2556,592,1,0},{2556,14879,1,0},{2556,15590,1,650},{2556,15592,1,650},{2556,15593,1,650},{2556,11728,1,500},{2556,10362,1,400},{2556,11716,1,350},{2556,2417,1,250},{2556,1547,1,75},{2556,1615,1,45},{2556,9245,20,35},{2556,85,1,5},
			{2557,592,1,0},{2557,14879,1,0},{2557,15590,1,650},{2557,15592,1,650},{2557,15593,1,650},{2557,11728,1,500},{2557,10362,1,400},{2557,11716,1,350},{2557,2417,1,250},{2557,1547,1,75},{2557,1615,1,45},{2557,9245,20,35},{2557,85,1,5},
		//Soliix
			{3841,526,1,0},{3841,8921,1,1000},{3841,1547,1,500},{3841,1615,1,300},{3841,11710,1,250},{3841,11712,1,250},{3841,11714,1,250},{3841,10551,1,250},{3841,4153,1,250},{3841,1275,1,150},{3841,85,1,125},{3841,11732,1,100},{3841,9185,1,100},
			{3841,1149,1,65},{3841,2365,1,50},{3841,1289,1,50},{3841,268,5,30},{3841,450,15,25},{3841,270,3,10},{3841,995,300000,6},{3841,1147,1,6},{3841,451,1,3},
				//Mezilkree
			{3842,526,1,0},{3842,8921,1,1000},{3842,1547,1,500},{3842,1615,1,300},{3842,11710,1,250},{3842,11712,1,250},{3842,11714,1,250},{3842,10551,1,250},{3842,4153,1,250},{3842,1275,1,150},{3842,85,1,125},{3842,11732,1,100},{3842,9185,1,100},
			{3842,1149,1,65},{3842,2365,1,50},{3842,1289,1,50},{3842,268,5,30},{3842,450,15,25},{3842,270,3,10},{3842,995,300000,6},{3842,1147,1,6},{3842,451,1,3},
		//Ttarmek
			{3843,526,1,0},{3843,8921,1,1000},{3843,1547,1,500},{3843,1615,1,300},{3843,11710,1,250},{3843,11712,1,250},{3843,11714,1,250},{3843,10551,1,250},{3843,4153,1,250},{3843,1275,1,150},{3843,85,1,125},{3843,11732,1,100},{3843,9185,1,100},
			{3843,1149,1,65},{3843,2365,1,50},{3843,1289,1,50},{3843,268,5,30},{3843,450,15,25},{3843,270,3,10},{3843,995,300000,6},{3843,1147,1,6},{3843,451,1,3},
		//sea troll queen
			{3847,13399,1,2500},{3847,19112,1,750},{3847,1547,1,550},{3847,11710,1,250},{3847,11712,1,250},{3847,11714,1,250},{3847,14484,1,250},{3847,1783,1,250},{3847,19113,1,125},{3847,1615,1,125},{3847,5303,6,125},{3847,1187,1,125},{3847,85,1,100},{3847,1249,1,125},{3847, 2365, 15, 75},
			{3847,15220,1,75},{3847,15018,1,75},{3847,15019,1,75},{3847,5698,1,75},{3847,9245,5,70},{3847,1748,15,70},{3847,15020,1,65},{3847,2364,10,50},{3847,1305,1,50},{3847,396,30,50},{3847,4087,1,50},{3847,4585,1,50},
			{3847,15126,1,50},{3847,19114,1,50},{3847,20072,1,50},{3847,10926,5,50},{3847,5304,2,50},{3847,396,30,40},{3847,454,30,30},{3847,1127,1,25},{3847,1213,1,25},{3847,6571,3,15},{3847,995,1000000,10},
			{3847, 3144, 1, 5},{3847, 407, 1, 5},
			{3847, 407, 1, 5}, {3847, 407, 1, 5}, {3847, 401, 2, 5}, {3847, 401, 1, 5}, {3847, 401, 2, 5}, {3847, 401, 1, 5},
			{3847, 401, 2, 5}, {3847, 401, 1, 5}, {3847, 401, 2, 5}, {3847, 401, 1, 5}, {3847, 401, 2, 5}, {3847, 401, 1, 5},
			{3847, 401, 2, 5}, {3847, 401, 1, 5}, {3847, 401, 2, 5}, {3847, 401, 1, 5}, {3847, 401, 1, 5}, {3847, 401, 2, 5},
			{3847, 401, 1, 5}, {3847, 401, 1, 5}, 
		//white knight
			{19,526,1,0},{19,6587,1,500},{19,6589,1,500},{19,6591,1,500},{19,6599,1,500},{19,6601,1,500},{19,6605,1,500},{19,6607,1,500},{19,6609,1,500},{19,6611,1,500},{19,6613,1,500},{19,6615,1,500},{19,6617,1,1000}, {19,6619,1,500}, {19,6621,1,500}, {19,6623,1,750}, {19,6625,1,1000}, {19,6627,1,1000}, {19,6629,1,500},{19,6631,1,500}, {19,6633,1,500}, {19,10420,1,1500}, {19,10422,1,1500},
		//Men
			{1,526,1,0},{1,995,5000,3},
			{2,526,1,0},{2,995,5000,3},
			{3,526,1,0},{3,995,5000,3},
			{4,526,1,0},{4,995,5000,3},
		//guards
			{9,526,1,0},{9,995,7500,2},
		//banshees
			{1612,1333,1,40},{1612,257,1,20},{1612,255,1,17},{1612,253,1,15},{1612,251,1,13},{1612,249,1,10},{1612,995,7500,6},
		//pyrefiend
			{1633,592,1,0},{1633,20072,1,365},{1633,8850,1,75},{1633,995,8000,10},
		//black knight titan
			{221,532,1,0},{221,11730,1,750},{221,1547,1,750},{221,11235,1,425},{221,8850,1,225},{221,85,1,175},{221,11732,1,125}, {221, 3145, 20, 115}, {221,7461,1,100},{221,10926,1,75},{221,11212,35,75}, {221, 5302, 6, 70}, {221, 5295, 6, 65}, {221, 7061, 125, 65}, {221, 5297, 6, 55}, {221, 5296, 6, 50}, {221, 392, 45, 50},{221, 5294, 6, 45}, {221, 5293, 6, 40}, {221,437,12,40},{221,439,12,40},{221,1620,3,40},{221,995,100000,25},{221,385,3,25},
		//Infernal mage
			{1643,526,1,0},{1643,6914,1,300},{1643,6916,1,200},{1643,6918,1,200},{1643,6920,1,200},{1643,6922,1,200},{1643,6924,1,200},{1643,995,19114,12},
		//dustdevil
			{1624,592,1,0},{1624,3140,1,215},{1624,7461,1,125},{1624,267,1,50},{1624,265,1,45},{1624,263,1,40},{1624,261,1,35},{1624,259,1,26},{1624,257,1,20},{1624,995,12000,18},{1624,255,1,17},{1624,253,1,15},{1624,251,1,13},{1624,249,1,10},
		//shadow spider
			{58,2434,1,16}, 
		//jelly
			{1637,13736,1,125},{1637,13734,1,75},{1637,995,19114,15},
		//Chaos Dwarf
			{119,526,1,0},{119,777,1,500},{119,1275,1,350},{119,85,1,300},{119,1271,1,200},{119,1273,1,125}, {119, 7061, 125, 90}, {119, 392, 45, 75}, {119, 5295, 6, 50}, {119, 5298, 6, 40}, {119,995,12500,38}, {119, 5296, 6, 30}, {119, 5293, 6, 25}, {119, 5292, 6, 25}, {119, 5291, 6, 20}, {119,2365,1,18},
		//crawlin hand
			{1648,526,1,0},{1648,1632,1,55},{1648,995,1500,10},{1648,892,15,8},
		//crawlin hand2
			{1653,526,1,0},{1653,1632,1,55},{1653,995,2000,10},{1653,892,20,8},
		//nech
			{1613,592,1,0},{1613,7462,1,1250},{1613,11732,1,125},{1613,7461,1,125},{1613,10928,1,25},{1613,995,75000,25},{1613,4131,1,20},
		//gargoyle
			{1610,532,1,0},{1610,4153,1,400},{1610,5299,3,125},{1610,5304,1,100},{1610,5303,1,90},{1610,5302,1,80},{1610,1079,1,75},{1610,5301,1,70},{1610,261,1,60},{1610,1113,1,55},{1610,6983,1,50},{1610,1631,1,50},{1610,259,1,45},{1610,1163,1,45},{1610,257,1,30},{1610,255,1,25},{1610,257,1,30},{1610,253,1,25},{1610,251,1,20},{1610,1432,1,20},{1610,6981,1,20},{1610,1617,1,15},{1610,995,40000,15},{1610,6979,1,1},
		//Hill giant
			{117,532,1,0},{117, 1163, 1, 75},{117, 261, 1, 55},{117, 1147, 1, 50},{117, 5297, 6, 45}, {117, 5296, 6, 40}, {117, 5294, 6, 35}, {117, 5293, 6, 30},{117, 259, 1, 25},{117, 5292, 6, 25},{117, 257, 1, 25}, {117, 5291, 6, 20}, {117,995,25000,20},{117, 255, 1, 20},{117, 253, 1, 17},{117, 251, 1, 15},{117, 249, 1, 10},
		//ice warrior
			{125,526,1,0}, {125, 7061, 125, 100}, {125, 392, 45, 80},{125, 5302, 6, 80}, {125, 5301, 6, 70}, {125, 5299, 6, 60}, {125, 5295, 6, 50}, {125, 5298, 6, 40}, {125, 5297, 6, 30}, {125, 1518, 10, 30}, {125, 5296, 6, 25}, {125, 5294, 6, 20}, {125, 1520, 10, 18}, {125, 1522, 10, 12}, {125,995,25000,15},
		//Bronze dragon
			{1590,536,1,0},{1590,2350,5,0},{1590,11284,1,3000},{1590,4087,1,300},{1590,4585,1,300},{1590,1377,1,175},{1590,11732,1,175},{1590,1147,1,60},{1590,263,1,25},
		//Iron dragon
			{1591,536,1,0},{1591,2352,5,0},{1591,11284,1,2500},{1591,3204,1,1500},{1591,11732,1,250},{1591,4087,1,250},{1591,4585,1,250},{1591,1377,1,150},{1591,1187,1,1591},{1591,1149,1,100}, {1591,1631,1,40},{1591,265,1,25},
		//Steel dragon
			{1592,536,1,0},{1592,2354,5,0},{1592,11284,1,2000},{1592,3204,1,1000},{1592,11732,1,200},{1592,4087,1,200},{1592,4585,1,200},{1592,1377,1,100},
		//King black dragon
			{50,536,4,0},{50,1747,5,0},{50,11335,1,750},{50,11694,1,100}, {50,11696,1,100},
			{50,11698,1,100}, {50,11284,1,100}, {50,11700,1,650},{50,3204,1,650},{50,1615,1,500},
			{50,1547,1,350},{50,11710,1,50},{50,11712,1,50},{50,11714,1,50},{50,85,1,45},
			{50,11732,1,40},{50,1319,1,35},{50,20072,1,30},{50,1303,1,30},{50,1187,1,30},{50,1377,1,50},{50,452,15,45},{50,5698,1,40},{50,1632,30,35},
			{50,9244,75,35},{50,1305,1,30},{50,385,3,25},{50,246,10,15},{50,3054,1,10},{50,2363,3,5},{50,1392,1,3},{50,1229,1,1},
		//black drag
			{54,536,1,0},{54,1747,1,0},{54,11284,1,3000},{54,14484,1,3000},{54,1615,1,1250},{54,1547,1,1000},{54,3140,1,350},{54,10928,1,125},{54,4151,1,275},{54,85,1,250},{54,7461,1,100},{54,4087,1,80},{54,1149,1,75},{54,9244,35,75}, {54, 5300, 6, 75}, {54, 5303, 6, 70}, {54, 5302, 6, 68}, 
		//red drag
			{53,536,1,0},{53,1749,1,0},{53,11284,1,4000},{53,3204,1,1250},{53,1615,1,900},{53,4151,1,475},{53,4087,1,175},{53,4585,1,175},{53,1149,1,155}, {53,10928,1,125},{53,85,1,250},{53,7461,1,100}, {53, 5300, 6, 95}, {53, 5303, 6, 85}, {53, 5302, 6, 83}, {53,4087,1,80},{53, 5301, 6, 80}, {53,1149,1,75},{53,9244,35,75}, 
		//blue drag
			{55,536,1,0},{55,1751,1,0},{55,11284,1,4500},{55,1615,1,1100},{55,85,1,450},{55,1377,1,225},{55,5299,1,175},{55,243,1,175},{55,1373,1,75},{55,261,1,65},{55,1333,1,55},{55,259,1,45},
		//Green dragon
   			{941,536,1,0},{941,1753,1,0},{941,11284,1,5000},{941,1615,1,1250},{941,85,1,600},{941,2572,1,500},
   			{941,4087,1,350},{941,4585,1,350},{941,1249,1,275},{941,1377,1,225},{941,1127,1,175},{941,5295,6,75},
   			{941,1149,1,70},{941,1201,1,70},{941,1185,1,30},
   			{941,5298,6,60},{941,4131,1,55},{941,1704,1,50},{941,5297,6,50},{941,1462,1,50},{941,5296,6,40},{941,1631,1,40},
   			{941,1432,1,40},{941,5294,6,30},{941,1163,1,30},{941,263,1,30},{941,1303,1,30},
   			{941,995,25000,20},{941,259,1,25},{941,261,1,25},{941,5293,6,20},{941,139,1,20},{941,379,1,20},
   			{941,145,1,20},{941,251,1,20},{941,561,25,15},{941,249,1,10},
		//fire giant
			{110,532,1,0},{110,3204,1,1750},{110,1615,1,750},{110,85,1,400},{110,11732,1,275},{110,1149,1,240},{110,10926,1,150},{110,375,12,140},{110,374,12,140},{110,145,1,120}, {110,5303,1,100},{110,892,100,45},{110,268,5,35}, 
		//ahrim
			{2025,1547,1,750},{2025,85,1,50},{2025,4709,1,15},{2025,4711,1,15},{2025,4713,1,15},{2025,4715,1,15},
		//dharok
			{2026,1547,1,750},{2026,85,1,50},{2026,4717,1,15},{2026,4719,1,15},{2026,4721,1,15},{2026,4723,1,15},
		//guthan
			{2027,1547,1,750},{2027,85,1,50},{2027,4725,1,15},{2027,4727,1,15},{2027,4729,1,15},{2027,4731,1,15},
		//karil
			{2028,1547,1,750},{2028,85,1,50},{2028,4733,1,15},{2028,4735,1,15},{2028,4737,1,15},{2028,4739,1,15},{2028,4740,10,75},
		//torag
			{2029,1547,1,750},{2029,85,1,50},{2029,4746,1,15},{2029,4748,1,15},{2029,4750,1,15},{2029,4752,1,15},
		//verac
			{2030,1547,1,750},{2030,85,1,50},{2030,4754,1,15},{2030,4756,1,15},{2030,4758,1,15},{2030,4760,1,15},
		//abyssal demon
			 {1615,592,1,0},{1615,13047,1,9000},{1615,1547,1,1250},{1615,1615,1,600},{1615,85,1,400},{1615,4151,1,400}, {1615, 3145, 20, 120}, {1615, 5300, 6, 70}, {1615, 5303, 6, 60}, {1615, 5302, 6, 50}, {1615, 5301, 6, 40}, {1615,995,12500,38}, {1615,268,15,35}, {1615, 7061, 125, 30}, {1615, 1602, 100, 30}, {1615,450,10,30}, {1615, 392, 45, 25}, {1615,1632,2,25},{1615,1618,4,25},{1615,995,120000,20},
		//otherworldly being
			 {126,2530,1,0},{126,1547,1,3500},{126,16018,1,3000},{126,15998,1,3000},{126,995,25000000,250},{126,85,1,100},{126,10926,5,100},{126,6585,1,100},{126,11732,1,100},{126,15018,1,100},{126,15019,1,100},{126,15020,1,100},{126,15220,1,100},
		//dark beast
			 {2783,532,1,0},{2783,1547,1,750},{2783,1615,1,600},{2783,11235,1,500},{2783,2572,1,300},{2783,85,1,250},{2783,5304,1,100},{2783,2365,1,50},{2783,11212,30,40},{2783,9244,50,40},{2783,269,1,35},{2783,451,6,35},{2783,1185,1,30},{2783,1147,1,30},{2783,995,191140,25},{2783,1632,5,15},{2783,5302,3,15},{2783,451,1,6},
		//chaos ele`
			{3200,526,1,0},{3200,19780,1,7500},{3200,13740,1,5000},{3200,15037,1,500},{3200,15040,1,500},{3200,15039,1,500},{3200,15041,1,500}, {3200,15038,1,500},{3200,1547,1,250},{3200,85,1,65},{3200,9244,100,55},{3200,5303,6,50},{3200,1187,1,45},{3200,10926,25,100},{3200,2572,1,40}, {3200,1127,1,40},{3200,1632,50,35},{3200,5304,6,30},{3200,5300,6,25},{3200,398,15,25},{3200,2364,50,35},{3200,7946,10,15},{3200,3145,10,15},{3200, 7061,25,15},{3200,246,10,10},{3200,385,2,8},{3200,2434,2,10},{3200,5300,5,7},{3200,7061,10,6},{3200,1347,1,5},{3200,385,1,5},{3200,995,250000,5},{3200,1185,1,6},{3200,258,2,3},{3200,1391,1,1},
		//jad
			{2745,592,1,0}, {2745,6570,1,0},
		//pirates
			{182,526,1,0},{182,2651,1,1500},{182,7110,1,1500},{182,7112,1,1500},{182,7114,1,1500},{182,7116,1,1500},{182,7122,1,1500}, {182,7124,1,1500}, {182,7126,1,1500},{182,7128,1,1500},{182,7130,1,1500}, {182,7132,1,1500},{182,7134,1,1500},{182,7138,1,1500},{182,7136,1,1500},{182,1025,1,500},{182,995,100000,25},
			{183,526,1,0},{183,2651,1,1500},{183,7110,1,1500},{183,7112,1,1500},{183,7114,1,1500},{183,7116,1,1500},{183,7122,1,1500}, {183,7124,1,1500}, {183,7126,1,1500},{183,7128,1,1500},{183,7130,1,1500}, {183,7132,1,1500},{183,7134,1,1500},{183,7138,1,1500},{183,7136,1,1500},{183,1025,1,500},{183,995,100000,25},
			{184,526,1,0},{184,2651,1,1500},{184,7110,1,1500},{184,7112,1,1500},{184,7114,1,1500},{184,7116,1,1500},{184,7122,1,1500}, {184,7124,1,1500}, {184,7126,1,1500},{184,7128,1,1500},{184,7130,1,1500}, {184,7132,1,1500},{184,7134,1,1500},{184,7138,1,1500},{184,7136,1,1500},{184,1025,1,500},{184,995,100000,25},
			{185,526,1,0},{185,2651,1,1500},{185,7110,1,1500},{185,7112,1,1500},{185,7114,1,1500},{185,7116,1,1500},{185,7122,1,1500}, {185,7124,1,1500}, {185,7126,1,1500},{185,7128,1,1500},{185,7130,1,1500}, {185,7132,1,1500},{185,7134,1,1500},{185,7138,1,1500},{185,7136,1,1500},{185,1025,1,500},{185,995,100000,25},
		//bears
			{105,526,1,0}, {105,948,1,0},{105,3101,1,75},{105,995,19114,20},{105,4502,1,20},
		//black bears
			{106,526,1,0}, {106,948,1,0},{106,3101,1,75},{106,995,19114,20},{106,4502,1,20},
		//baby bears
			{1326,526,1,0}, {1326,948,1,0},{1326,3101,1,75},{1326,4502,1,25},
		//apes
			{1459,526,1,0},{1459,3167,1,0},{1459,11732,1,275},{1459,3140,1,250},{1459,4151,1,175},{1459,4087,1,115},{1459,4585,1,115},{1459,995,100000,25},
		//Giant mole
   			{3340,7416,2,0},{3340,7418,2,0}, {3340,15574,1,1500},{3340,13740,1,1000},{3340,16018,1,500}, {3340,11694,1,150},{3340,995,50000000,100}, {3340,3204, 15, 50}, {3340,1547,1,50}, {3340, 5304, 15, 10}, {3340, 5300, 15, 10},{3340,9245,50,5}, {3340, 5303, 15, 12},{3340,85,1,45},{3340,6572,12,8},{3340,452,30,5},{3340, 10926, 7, 6},
   		//zombie champ
   			{3066,532,1,0},{3066,15662,1,3750},{3066,6722,1,2500},{3066,13047,1,900},{3066,13045,1,900},{3066,11694,1,750},{3066,14484,1,500},{3066,11724,1,150},{3066,11726,1,150},
   			{3066,1547,1,350},{3066,85,1,100},{3066,15020,1,85},{3066,15220,1,75},{3066,20072,1,100},
   			{3066,15018,1,100},{3066,15019,1,100},{3066,15126,1,100},{3066,19114,1,100},{3066,9244,250,100},{3066,270,10,75}, {3066,6571,1,75},
   			{3066,10926,10,85},{3066,8850,1,85},{3066,995,5000000,50},{3066,9244,175,45},{3066,4587,1,40},{3066,9245,40,35},{3066,10926,3,30},{3066,398,15,30},
   			{3066,3145,20,25},{3066,7061,125,20},{3066,1215,1,15},{3066,1319,1,10},{3066,1147,1,10},{3066,1147,1,8},{3066,1632,4,5},{3066,451,2,4},
   		//zombie champ guard	
			{3622,526,1,0},{3622,6722,1,75000},{3622,7592,1,350}, {3622,7593,1,350}, {3622,7594,1,350}, {3622,7595,1,350}, {3622, 7061, 125, 180}, {3622, 392, 45, 150},
		//ice troll
			{1936,532,1,0}, {1936,2572,1,400},{1936,85,1,400},{1936,2577,1,200},{1936,2579,1,200},{1936,2619,1,100},{1936,2621,1,100},{1936,3476,1,100},{1936,2615,1,60}, {1936,2617,1,60},{1936,995,75000,40}, {1936, 1516, 10, 35}, {1936, 1518, 10, 30},{1936,2365,5,25},{1936, 1520, 10, 25},{1936,2362,4,10},
		//dagannoth mother
   			{1351,6729,1,0}, {1351,13022,1,1250},{1351,15041,1,350},{1351,15040,1,350},{1351,15037,1,350},{1351,13738,1,150},{1351,13742,1,150},{1351,13744,1,150},{1351,3748,1,150},{1351,3758,1,150},{1351,1547,1,150},{1351,2363,100,50},{1351,3145,1,35},{1351,85,1,15},{1351,10926,15,10},{1351,6056,10,10},
  		//dagannoth rex melee
  			{2883,6729,1,0},{2883,3757,1,1500},{2883,12710,1,750},{2883,3748,1,500},{2883,3758,1,350},{2883,6739,1,75},{2883,1547,1,350},{2883,10926,30,60},{2883,1615,1,60},{2883,19114,1,50},{2883,15126,1,50},{2883,15220,1,30},{2883,15020,1,30},{2883,20072,1,30},{2883,4153,1,50},{2883,85,1,50},{2883,450,60,70},{2883,5303,16,70},{2883,9245,20,50},{2883,5304,6,30},{2883,1516,40,15},{2883,397,6,15},{2883,398,14,8},{2883,270,6,5},{2883,1632,10,5},{2883,451,4,4},
  		//dagannoth prime mage
  			{2882,6729,1,0},{2882,3757,1,1500},{2882,12712,1,750},{2882,3748,1,500},{2882,3758,1,350},{2882,6739,1,75},{2882,1547,1,350},{2882,10926,30,60},{2882,1615,1,60},{2882,19114,1,50},{2882,15126,1,50},{2882,15018,1,30},{2882,20072,1,125},{2882,4153,1,50},{2882,85,1,50},{2882,450,60,70},{2882,5303,16,70},{2882,9245,20,50},{2882,5304,6,30},{2882,1516,40,15},{2882,397,6,15},{2882,398,14,8},{2882,270,6,5},{2882,1632,10,5},{2882,451,4,4},
  		//dagannoth supreme range
   			{2881,6729,1,0},{2881,3757,1,1500},{2881,12708,1,750},{2881,3748,1,500},{2881,3758,1,350},{2881,6739,1,75},{2881,1547,1,350},{2881,10926,30,60},{2881,1615,1,100},{2881,19114,1,50},{2881,15126,1,50},{2881,15019,1,30},{2881,20072,1,125},{2881,4153,1,50},{2881,85,1,50},{2881,450,60,70},{2881,5303,16,70},{2881,9245,20,50},{2881,5304,6,30},{2881,1516,40,15},{2881,397,6,15},{2881,398,14,8},{2881,270,6,5},{2881,1632,10,5},{2881,451,4,4}
};
}
