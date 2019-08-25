import java.io.*;

public final class ItemDef {

	public static void nullLoader() {
		mruNodes2 = null;
		mruNodes1 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public boolean method192(int j) {
		int k = anInt175;
		int l = anInt166;
		if(j == 1)
		{
			k = anInt197;
			l = anInt173;
		}
		if(k == -1)
			return true;
		boolean flag = true;
		if(!Model.method463(k))
			flag = false;
		if(l != -1 && !Model.method463(l))
			flag = false;
		return flag;
	}
	
	/*public static void dumpStacks() {
		try {
			FileOutputStream out = new FileOutputStream(new File("stackable.dat"));
			for (int j = 0; j < 12000; j++) {
				ItemDef item = ItemDef.forID(j);
				out.write(item.stackable ? 1 : 0);
			}
			out.write(-1);
			out.close();
		} catch (IOException ioe) {ioe.printStackTrace();}	
	}
	
	public static void dumpNotes() {
		try {
			FileOutputStream out = new FileOutputStream(new File("notes.dat"));
			for (int j = 0; j < 12000; j++) {
				ItemDef item = ItemDef.forID(j);
				out.write(item.certTemplateID != -1 ? 0 : 1);
			}
			out.write(-1);
			out.close();
		} catch (IOException ioe) {ioe.printStackTrace();}
	
	
	}*/
	
/*	public static void dumpPrices() {
		try {
			//FileOutputStream out = new FileOutputStream(new File("prices.dat"));
			FileWriter fw = new FileWriter("prices.txt");
			for (int j = 0; j < 12000; j++) {
				ItemDef item = ItemDef.forID(j);
				fw.write(item.id + " " + item.value + "\r\n");
			}
			fw.close();
		} catch (IOException ioe) {ioe.printStackTrace();}
	
	
	}*/
	
	public static void unpackConfig(StreamLoader streamLoader)	 {
		stream = new Stream(streamLoader.getDataForName("obj.dat"));
		Stream stream = new Stream(streamLoader.getDataForName("obj.idx"));
		totalItems = stream.readUnsignedWord();
		streamIndices = new int[totalItems + 400000];
		int i = 2;
		for(int j = 0; j < totalItems; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}
		cache = new ItemDef[10];
		for(int k = 0; k < 10; k++)
			cache[k] = new ItemDef();
		//dumpStacks();
		//dumpNotes();
		//dumpPrices();
	}

	public Model method194(int j) {
		int k = anInt175;
		int l = anInt166;
		if(j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if(k == -1)
			return null;
		Model model = Model.method462(k);
		if(l != -1) {
			Model model_1 = Model.method462(l);
			Model aclass30_sub2_sub4_sub6s[] = {
					model, model_1
			};
			model = new Model(2, aclass30_sub2_sub4_sub6s);
		}
	   if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++)
				model.method476(originalModelColors[i1], modifiedModelColors[i1]);

		}
		return model;
	}

	public boolean method195(int j) {
		int k = maleEquip1;
		int l = maleEquip2;
		int i1 = anInt185;
		if(j == 1) {
			k = femaleEquip1;
			l = femaleEquip2;
			i1 = anInt162;
		}
		if(k == -1)
			return true;
		boolean flag = true;
		if(!Model.method463(k))
			flag = false;
		if(l != -1 && !Model.method463(l))
			flag = false;
		if(i1 != -1 && !Model.method463(i1))
			flag = false;
		return flag;
	}

	public Model method196(int i) {
		int j = maleEquip1;
		int k = maleEquip2;
		int l = anInt185;
		if(i == 1) {
			j = femaleEquip1;
			k = femaleEquip2;
			l = anInt162;
		}
		if(j == -1)
			return null;
		Model model = Model.method462(j);
		if(k != -1)
			if(l != -1) {
				Model model_1 = Model.method462(k);
				Model model_3 = Model.method462(l);
				Model aclass30_sub2_sub4_sub6_1s[] = {
						model, model_1, model_3
				};
				model = new Model(3, aclass30_sub2_sub4_sub6_1s);
			} else {
				Model model_2 = Model.method462(k);
				Model aclass30_sub2_sub4_sub6s[] = {
						model, model_2
				};
				model = new Model(2, aclass30_sub2_sub4_sub6s);
			}
		if(i == 0 && aByte205 != 0)
			model.method475(0, aByte205, 0);
		if(i == 1 && aByte154 != 0)
			model.method475(0, aByte154, 0);
		if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++)
				model.method476(originalModelColors[i1], modifiedModelColors[i1]);

		}
		return model;
	}

	
	public void setDefaults() {
		modelID = 0;
		name = null;
		description = null;
		originalModelColors = null;
		modifiedModelColors = null;
		modelZoom = 2000;
		modelRotation1 = 0;
		modelRotation2 = 0;
		anInt204 = 0;
		modelOffset1 = 0;
		modelOffset2 = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = null;
		actions = null;
		maleEquip1 = -1;
		maleEquip2 = -1;
		aByte205 = 0;
		femaleEquip1 = -1;
		femaleEquip2 = -1;
		aByte154 = 0;
		anInt185 = -1;
		anInt162 = -1;
		anInt175 = -1;
		anInt166 = -1;
		anInt197 = -1;
		anInt173 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		anInt167 = 128;
		anInt192 = 128;
		anInt191 = 128;
		anInt196 = 0;
		anInt184 = 0;
		team = 0;
	}

	public static ItemDef forID(int i) {
		for(int j = 0; j < 10; j++)
			if(cache[j].id == i)
				return cache[j];
		cacheIndex = (cacheIndex + 1) % 10;
		ItemDef itemDef = cache[cacheIndex];
		stream.currentOffset = streamIndices[i];
		itemDef.id = i;
		itemDef.setDefaults();
		itemDef.readValues(stream);
		if(itemDef.certTemplateID != -1)
			itemDef.toNote();
		if(!isMembers && itemDef.membersObject) {
			itemDef.name = "Members Object";
			itemDef.description = "Login to a members' server to use this object.".getBytes();
			itemDef.groundActions = null;
			itemDef.actions = null;
			itemDef.team = 0;
		}

if (i == 2438) {
      itemDef.actions = new String[5];
      itemDef.actions[0] = "Drink";
      itemDef.name = "Overload (4)";
      itemDef.description = "4 doses of overload potion.".getBytes();
      itemDef.originalModelColors = new int[1];
      itemDef.modifiedModelColors = new int[1];
      itemDef.originalModelColors[0] = 61;
      itemDef.modifiedModelColors[0] = 0;
      itemDef.modelID = 2789;
      itemDef.modelZoom = 550;
      itemDef.modelRotation1 = 84;
      itemDef.modelRotation2 = 1996;
      itemDef.anInt204 = 0;
      itemDef.modelOffset1 = 0;
      itemDef.modelOffset2 = -1;
    }
    if (i == 151) {
      itemDef.actions = new String[5];
      itemDef.actions[0] = "Drink";
      itemDef.name = "Overload (3)";
      itemDef.description = "3 doses of overload potion.".getBytes();
      itemDef.originalModelColors = new int[1];
      itemDef.modifiedModelColors = new int[1];
      itemDef.originalModelColors[0] = 61;
      itemDef.modifiedModelColors[0] = 0;
      itemDef.modelID = 2697;
      itemDef.modelZoom = 550;
      itemDef.modelRotation1 = 84;
      itemDef.modelRotation2 = 1996;
      itemDef.anInt204 = 0;
      itemDef.modelOffset1 = 0;
      itemDef.modelOffset2 = -1;
    }
    if (i == 153) {
      itemDef.actions = new String[5];
      itemDef.actions[0] = "Drink";
      itemDef.name = "Overload (2)";
      itemDef.description = "2 doses of overload potion.".getBytes();
      itemDef.originalModelColors = new int[1];
      itemDef.modifiedModelColors = new int[1];
      itemDef.originalModelColors[0] = 61;
      itemDef.modifiedModelColors[0] = 0;
      itemDef.modelID = 2384;
      itemDef.modelZoom = 550;
      itemDef.modelRotation1 = 84;
      itemDef.modelRotation2 = 1996;
      itemDef.anInt204 = 0;
      itemDef.modelOffset1 = 0;
      itemDef.modelOffset2 = -1;
    }
    if (i == 155) {
      itemDef.actions = new String[5];
      itemDef.actions[0] = "Drink";
      itemDef.name = "Overload (1)";
      itemDef.description = "1 dose of overload potion.".getBytes();
      itemDef.originalModelColors = new int[1];
      itemDef.modifiedModelColors = new int[1];
      itemDef.originalModelColors[0] = 61;
      itemDef.modifiedModelColors[0] = 0;
      itemDef.modelID = 2621;
      itemDef.modelZoom = 550;
      itemDef.modelRotation1 = 84;
      itemDef.modelRotation2 = 1996;
      itemDef.anInt204 = 0;
      itemDef.modelOffset1 = 0;
      itemDef.modelOffset2 = -1;
    }


			switch (itemDef.id) {
				case 19112:
       itemDef.name = "Trident of the seas";
       itemDef.description = "A weapon from the deep."
       .getBytes();
       itemDef.maleEquip1 = 1052;
       itemDef.femaleEquip1 = 1052;
       itemDef.modelID = 1051;
       itemDef.modelRotation2 = 420;
       itemDef.modelRotation1 = 1130;
       itemDef.modelZoom = 2755;
       itemDef.modelOffset2 = 0;
       itemDef.modelOffset1 = 0;
     //  itemDef.itemActions = new String[5];
    //   itemDef.itemActions[1] = "Wield";
     //  itemDef.itemActions[3] = "Check";
       itemDef.actions = new String[] { null, "Wield", null, "Check", "Drop" };
       break;
       case 19113:
	itemDef.name = "Abyssal tentacle";
	itemDef.description = "A weapon from the abyss, embedded in a slimy tentacle."
        .getBytes();
	itemDef.modelID = 4185;
        itemDef.modelZoom = 913;
        itemDef.modelRotation1 = 304;
        itemDef.modelRotation2 = 148;
        itemDef.modelOffset1 = 0;
        itemDef.modelOffset2 = 3;		
	itemDef.maleEquip1 = 28446;
	itemDef.femaleEquip1 = 4186;
	itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
        break;
       case 19114:
		itemDef.name = "Occult necklace";
		itemDef.description = "A satanic evil embodies this amulet."
		.getBytes();
		itemDef.maleEquip1 = 7824;
		itemDef.femaleEquip1 = 7824;
		itemDef.modelID = 7825;
		itemDef.modelZoom = 240;
		itemDef.modelRotation1 = 265;
		itemDef.modelRotation2 = 50;
		itemDef.modelOffset1 = 2;
		itemDef.modelOffset2 = 230;
		itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
       break;
       case 19115:
		itemDef.name = "Staff of the dead";
		itemDef.actions = new String[5];
		itemDef.actions[1] = "Wield";
		itemDef.description = "A ghastly weapon with evil origins.".getBytes();
		itemDef.modelID = 2810;
		itemDef.maleEquip1 = 5232;
		itemDef.femaleEquip1 = 5232;
		itemDef.modelRotation2 = 148;
		itemDef.modelRotation1 = 1300;
		itemDef.modelZoom = 1420;
		itemDef.modelOffset1 = -5;
		itemDef.modelOffset2 = 2;
	break;
		case 19116:
                        itemDef.name = "Bronze platebody (g)";
                        itemDef.modelZoom = 1180;
                        itemDef.modelRotation2 = 452;
                        //itemDef.offsetX = -1;
                       // itemDef.offsetY = -1;
                        itemDef.modifiedModelColors = new int[] { 5652, 7050, 7114 };
                        itemDef.originalModelColors = new int[] { 61, 41, 24 };
                        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
                        itemDef.modelID = 2378;
                        itemDef.maleEquip1 = 3379;
                        itemDef.femaleEquip1 = 3383;
                        break;
		case 3481:
		case 3482:
		case 3483:
		case 3484:
		case 3485:
		case 3486:
		case 3487:
		case 3488:
		case 3489:
			itemDef.name = "@or1@" + itemDef.name;
		break;
		case 1053:
		case 1054:
		case 1055:
		case 1056:
		case 1057:
		case 1058:
			itemDef.name = "@gr3@" + itemDef.name;
		break;
		case 7453:
		   itemDef.name = "Gloves";
		break;
		case 7454:
		   itemDef.name = "Bronze gloves";
		break;
		case 7455:
		   itemDef.name = "Iron gloves";
		break;
		case 7456:
		   itemDef.name = "Steel gloves";
		break;
		case 7457:
		   itemDef.name = "Black gloves";
		break;
		case 7458:
		   itemDef.name = "Mithril gloves";
		break;
		case 7459:
		   itemDef.name = "Adamant gloves";
		break;
		case 7460:
		   itemDef.name = "Rune gloves";
		break;
		case 7461:
		   itemDef.name = "@or2@Dragon gloves";
		break;
		case 7462:
		   itemDef.name = "@or1@Barrows gloves";
		break;
		case 6570:
		   itemDef.name = "@or2@Fire cape";
		break;


		case 12708:
			itemDef.name = "@gre@Pegasian boots";
			itemDef.modelID = 29396;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 165;
			itemDef.modelRotation2 = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 =-7;
			itemDef.maleEquip1 = 29252;
			itemDef.femaleEquip1 = 29253;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;
		case 12710:
			itemDef.name = "@gre@Primordial Boots";
			itemDef.modelID = 29397;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 165;
			itemDef.modelRotation2 = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 =-7;
			itemDef.maleEquip1 = 29250;
			itemDef.femaleEquip1 = 29255;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;

			case 12712:
			itemDef.name = "@gre@Eternal boots";
			itemDef.modelID = 29394;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 165;
			itemDef.modelRotation2 = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 =-7;
			itemDef.maleEquip1 = 29249;
			itemDef.femaleEquip1 = 29254;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;
case 13576:
				itemDef.name = "@gre@Dragon Warhammer";
				itemDef.modelID = 4041;
				itemDef.modelRotation1 = 1510;
				itemDef.modelRotation2 = 1785;
				itemDef.modelZoom = 1600;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.maleEquip1 = 4037;
				itemDef.femaleEquip1 = 4038;
				break;
			case 13045:
				itemDef.name = "@gre@Abyssal bludgeon";
				itemDef.modelZoom = 1400;
				itemDef.modelRotation1 = 1549;
				itemDef.modelRotation2 = 1818;
				itemDef.modelOffset2 = 9;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
				itemDef.modelID = 29597;
				itemDef.maleEquip1 = 29424;
				itemDef.femaleEquip1 = 29424;
			break;
			
			
			case 13047:
				itemDef.name = "@gre@Abyssal dagger";
				itemDef.modelZoom = 1275;
				itemDef.modelRotation1 = 1549;
				itemDef.modelRotation2 = 1818;
				itemDef.modelOffset2 = 9;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
				itemDef.modelID = 29598;
				itemDef.maleEquip1 = 29425;
				itemDef.femaleEquip1 = 29425;
			break;
			case 12904:
				   itemDef.name = "@gre@Toxic staff of the dead";
				   itemDef.modelZoom = 2150;
				   itemDef.modelRotation1 = 512;
				   itemDef.modelRotation2 = 1010;
				   itemDef.modifiedModelColors = new int[] { 21947 };
				   itemDef.originalModelColors = new int[] { 17467 };
				   itemDef.groundActions = new String[] { null, null, "Take", null, null };
					itemDef.actions = new String[] { null, "Wield", "Check", null, "Uncharge" };
				   itemDef.modelID = 19224;
					itemDef.maleEquip1 = 14402;
					itemDef.femaleEquip1 = 14402;
				   break;
case 15395:
				itemDef.name = "@gre@Tanzanite helm";
				itemDef.modelZoom = 700;
				itemDef.modelRotation1 = 215;
				itemDef.modelRotation2 = 1724;
				itemDef.modelOffset1 = -17;
				itemDef.modelOffset2 = 0;
				itemDef.modelID = 19220;
			      itemDef.maleEquip1 = 23994;
			      itemDef.femaleEquip1 = 23994;
			        itemDef.actions = new String[5];
			        itemDef.actions[1] = "Wear"; // What it says,eg Wear, Weild ect...
			        break;

			case 15396:
				itemDef.name = "@gre@Magma helm";
				itemDef.modelZoom = 700;
				itemDef.modelRotation1 = 215;
				itemDef.modelRotation2 = 1724;
				itemDef.modelOffset1 = -17;
				itemDef.modelOffset2 = 0;
				itemDef.modelID = 29205;
			      itemDef.maleEquip1 = 14424;
			      itemDef.femaleEquip1 = 14426;
			        itemDef.actions = new String[5];
			        itemDef.actions[1] = "Wear"; // What it says,eg Wear, Weild ect...
break;
			case 15397:
				itemDef.name = "@gre@Serpentine helm";
				itemDef.modelZoom = 700;
				itemDef.modelRotation1 = 215;
				itemDef.modelRotation2 = 1724;
				itemDef.modelOffset2 = -17;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wear", "Check", null, "Drop" };
				itemDef.modelID = 19220;
			    itemDef.maleEquip1 = 23994;
			    itemDef.femaleEquip1 = 23994;
			    /*itemDef.femaleEquip1 = 14398;
				itemDef.maleEquip1 = 14395;*/
			break;
			case 12424:
				itemDef.name = "@gre@3rd age bow";
				itemDef.modelZoom = 1979;
				itemDef.modelRotation1 = 537;
				itemDef.modelRotation2 = 256;
				itemDef.modelOffset1 = -15;
				itemDef.modelOffset2 = 10;
				itemDef.groundActions = new String[] { null, null, "Take", null, null };
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 28678;
			     itemDef.maleEquip1 = 28622;
			     itemDef.femaleEquip1 = 28622;
				break;
			case 12422:
				itemDef.name = "@gre@3rd age wand";
				itemDef.modelZoom = 1347;
				itemDef.modelRotation1 = 1468;
				itemDef.modelRotation2 = 1805;
				itemDef.modelOffset2 = 1;
				itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
				itemDef.modelID = 28654;
			    itemDef.maleEquip1 = 28619;
			    itemDef.femaleEquip1 = 28619;
			break;
		case 15098:
			itemDef.name = "Dice (up to 100)";
			itemDef.modelZoom = 1104;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.anInt204 = 1;
			itemDef.actions = new String[] { null, null, "Take", null, null };
			itemDef.actions[0] = "Use ::roll";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 47852;
			break;
		 case 19000:
		    itemDef.name = "@gre@Abyssal dagger(p++)";
		    itemDef.modelZoom = 1275;
		    itemDef.modelRotation1 = 1549;
		    itemDef.modelRotation2 = 1818;
		    itemDef.modelOffset2 = 9;
		    itemDef.groundActions = new String[] { null, null, "Take", null, null };
		    itemDef.actions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
		    itemDef.modelID = 29596;
		    itemDef.maleEquip1 = 29426;
		    itemDef.femaleEquip1 = 29426;
		   break;
		/*case 13271:
		   itemDef.name = "Abyssal dagger(p++)";
		   itemDef.groundOptions = new String[] { null, null, "Take", null, null };
		   itemDef.inventoryOptions = new String[] { null, "Wield", null, null, "Drop" };
		   itemDef.modelZoom = 1347;
		   itemDef.rotationY = 1589;
		   itemDef.rotationX = 781;
		   itemDef.offsetX = -5;
		   itemDef.offsetY = 3;
		   itemDef.maleModel = 29426;
		   itemDef.femaleModel = 29426;
		   itemDef.groundModel = 29596;
		break;*/
case 13677:
					itemDef.actions = new String[5];
					itemDef.actions[1] = "Wear";
					itemDef.modelID = 14125; // Inv & Ground
					itemDef.modelZoom = 2000; // Zoom
					itemDef.modelRotation1 = 572;
					itemDef.modelRotation2 = 0;
					itemDef.anInt204 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 1;
					itemDef.maleEquip1 = 14126; // Male
					itemDef.femaleEquip1 = 14126; // Female
					itemDef.anInt175 = -1;
					itemDef.anInt197 = -1;
					itemDef.name = "Moderator cape";
					itemDef.description = "Its A Moderator Cape".getBytes();
					break;


				case 13678:
					itemDef.actions = new String[5];
					itemDef.actions[1] = "Wear";
					itemDef.modelID = 14127; // Inv & Ground
					itemDef.modelZoom = 2000; // Zoom
					itemDef.modelRotation1 = 572;
					itemDef.modelRotation2 = 0;
					itemDef.anInt204 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 1;
					itemDef.maleEquip1 = 14128; // Male
					itemDef.femaleEquip1 = 14128; // Female
					itemDef.anInt175 = -1;
					itemDef.anInt197 = -1;
					itemDef.name = "Administrator cape";
					itemDef.description = "Its an Administrator cape".getBytes();
					break;

				case 13679:
					itemDef.actions = new String[5];
					itemDef.actions[1] = "Wear";
					itemDef.modelID = 14129; // Inv & Ground
					itemDef.modelZoom = 2000; // Zoom
					itemDef.modelRotation1 = 572;
					itemDef.modelRotation2 = 0;
					itemDef.anInt204 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 1;
					itemDef.maleEquip1 = 14130; // Male
					itemDef.femaleEquip1 = 14130; // Female
					itemDef.anInt175 = -1;
					itemDef.anInt197 = -1;
					itemDef.name = "Owner cape";
					itemDef.description = "Its an Owner cape".getBytes();
					break;
				  case 12926:
				    	itemDef.modelID = 25000;
				    	itemDef.name = "Toxic blowpipe";
				    	itemDef.description = "It's a Toxic blowpipe".getBytes();
				    	itemDef.modelZoom = 1158;
				    	itemDef.modelRotation1 = 768;
				    	itemDef.modelRotation2 = 189;
				    	itemDef.modelOffset1 = -7;
				    	itemDef.modelOffset2 = 4;
				    	itemDef.maleEquip1 = 14403;
				    	itemDef.femaleEquip1 = 14403;
				    	itemDef.aByte154 = 6;
				    	itemDef.actions = new String[] { null, "Wield", "Check", "Unload", "Drop" };
				    	itemDef.groundActions = new String[] { null, null, "Take", null, null };
				    break;
			  		case 12848:
						itemDef.name = "@gre@ForeverPkers Bond";
					       itemDef.description = "Gives your account Donator status until the next reset.".getBytes();
						itemDef.modelZoom = 2500;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 =  512;
						itemDef.modelOffset2 = 3;
						itemDef.groundActions = new String[] { null, null, "Take", null, null };
						itemDef.actions = new String[] { "Claim", null, null, null, "Destroy" };
						itemDef.modelID = 28210;
					break;
		 case 13871:
		   itemDef.name = "Dragon javelin";
		   itemDef.description = "Rawr xD".getBytes();
		   itemDef.modelZoom = 1872;
		   itemDef.modelRotation1 = 500;
		   itemDef.modelRotation2 = 282;
		   itemDef.modelOffset1 = -1;
		   itemDef.modelOffset2 = -1;
		   itemDef.anInt204 = 0;
		   itemDef.groundActions = new String[] { null, null, "Take", null, null };
		   itemDef.actions = new String[] { null, "Wear", null, null, "Drop", };
		   itemDef.modelID = 31511;
		   break;
		case 19481:
			itemDef.name = "Heavy ballista";
			itemDef.description = "It's a Heavy ballista.".getBytes();
			itemDef.modelZoom = 1284;
			itemDef.modelRotation1 = 189;
			itemDef.modelRotation2 = 148;
			itemDef.modelOffset1 = 8;
			itemDef.modelOffset2 = -18;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.modelID = 31523;
			itemDef.maleEquip1 = 31236;
			itemDef.femaleEquip1 = 31236;
			break;
		case 11728:
		case 11729:
		   itemDef.name = "@or1@Bandos boots";
		break;
        case 8921:
		   itemDef.name = "@or2@Black mask";
		   itemDef.maleEquip1 = 10071;
		   itemDef.femaleEquip1 = 10072;
		   itemDef.modelID = 10070;
		   itemDef.modelOffset2 = 0;
		   itemDef.modelOffset1 = 0;
		   itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
        break;
		case 11722:
			itemDef.femaleEquip1 = itemDef.maleEquip1;
			itemDef.femaleEquip2 = itemDef.maleEquip2;
			itemDef.name = "@or2@" + itemDef.name;
		break;
		case 19111: 
			itemDef.name = "@or2@TokHaar-Kal";
			itemDef.modelZoom = 1616;
			itemDef.modelRotation2 = 339;
			itemDef.modelRotation1 = 192;
			itemDef.modelOffset2 = -4;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.modelID = 62592;
			itemDef.maleEquip1 = 62575;
			itemDef.femaleEquip1 = 62582;
         break;
		case 15126: 
            itemDef.name = "@or2@Amulet of ranging";
            itemDef.modelZoom = 848;
            itemDef.modelRotation1 = 310;
            itemDef.modelRotation2 = 175;
            itemDef.groundActions = (new String[] { null, null, "Take", null, null });
            itemDef.actions = (new String[] { null, "Wear", null, null, "Drop" });
            itemDef.modelID = 48185;
            itemDef.maleEquip1 = 48183;
            itemDef.femaleEquip1 = 48184;
            itemDef.anInt196 = 25;
            itemDef.anInt184 = 125;
        break;
		case 13263:
			itemDef.name = "@or1@Advanced slayer helmet";
			itemDef.modelZoom = 789;
			itemDef.modelRotation2 = 1743;
			itemDef.modelRotation1 = 69;
			itemDef.modelOffset2 = -3;
			itemDef.modelOffset1 = -7;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, "Disassemble", "Drop" };
			itemDef.modelID = 67433;
			itemDef.maleEquip1 = 66469;
			itemDef.femaleEquip1 = 66850;
		break;
        case 19780:
                itemDef.name = "@bar@Korasi's sword";
                itemDef.modelZoom = 1744;
                itemDef.modelRotation2 = 330;
                itemDef.modelRotation1 = 1505;
                itemDef.groundActions = new String[] { null, null, "Take", null, null };
                itemDef.actions = new String[] { null, "Wield", null, null, "Destroy" };
                itemDef.modelID = 60831;       
                itemDef.maleEquip1 = 57780;
                itemDef.femaleEquip1 = 57784;
        break;
        case 14479:
			itemDef.name = "@or2@Dragon platebody";
			itemDef.modelZoom = 1614;
			itemDef.modelRotation2 = 526;
			itemDef.modelRotation1 = 0;
			itemDef.modelOffset2 = 1;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.modelID = 67237;
			itemDef.maleEquip1 = 66631;
			itemDef.femaleEquip1 = 67020;
			itemDef.anInt196 = 15;
		break;
        case 19785:
        itemDef.name = "@or2@Elite void knight top";
        itemDef.modelZoom = 1450;
        itemDef.modelRotation2 = 470;
        itemDef.modelRotation1 = 0;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 67449;
        itemDef.maleEquip1 = 66685;
        itemDef.femaleEquip1 = 67078;
break;
                case 19786:
        itemDef.name = "@or2@Elite void knight robe";
        itemDef.modelZoom = 1824;
        itemDef.modelRotation2 = 443;
        itemDef.modelRotation1 = 0;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 67452;
        itemDef.maleEquip1 = 66597;
        itemDef.femaleEquip1 = 66986;
break;
			case 10887:
				itemDef.name = "@bar@Barrelchest anchor"; //Name
			break;
			case 11284:
			case 11283:
				itemDef.name = "@or2@Dragonfire shield";
			break;
			case 1038:
				itemDef.name = "@red@Red partyhat";
			break;
			case 1040:
				itemDef.name = "@yel@Yellow partyhat";
			break;
			case 1042:
				itemDef.name = "@blu@Blue partyhat";
			break;
			case 1044:
				itemDef.name = "@gr3@Green partyhat";
			break;
			case 1046:
				itemDef.name = "@mag@Purple partyhat";
			break;
			case 1048:
				itemDef.name = "@whi@White partyhat";
			break;
			case 11235:
				itemDef.name = "@or2@Dark bow";
			break;
			case 11694:
			case 11695:
				itemDef.name = "@or1@Armadyl godsword";
			break;
			case 11696://godswords
			case 11697:
			case 11698:
			case 11699:
			case 11700:
			case 11701:
			case 11732://dboots
			case 11733:
			case 4151://whip
			case 4152:
			case 4153://gmaul
			case 4154:
			case 6585://fury
			case 6586:
			case 6731://rings
			case 6732:
			case 6733:
			case 6734:
			case 6735:
			case 6736:
			case 6737:
			case 6738:
			case 10828://nez
			case 10548:
			case 10551:
				itemDef.name = "@or2@" + itemDef.name;
			break;
			
			case 2415://god staffs/staves
			case 2416:
			case 2417:
				itemDef.name = "@or1@" + itemDef.name;
			break;
			
			case 2572://row ring of wealth
				itemDef.name = "@or1@" + itemDef.name;
			break;
			case 11702://arma hilt
			case 11703:
				itemDef.name = "@or1@" + itemDef.name;
			break;
			case 11704:
			case 11705:
			case 11706:
			case 11707:
			case 11708:
			case 11709:
			case 11710:
			case 11711:
			case 11712:
			case 11713:
			case 11714:
			case 11715:
			case 11716:
			case 11717:
				itemDef.name = "@or2@" + itemDef.name;
			break;
			case 8850://rune def and dark orange items
			
			case 11718:
			case 11719:
			case 11720:
			case 11721:
			case 11723:
			case 11724:
			case 11725:
			case 11726:
			case 11727:
			case 11730:
			case 11731:
				itemDef.name = "@or2@" + itemDef.name;
			break;
			
			case 10330:
			case 10331:
			case 10332:
			case 10333:
			case 10334:
			case 10335:
			case 10336:
			case 10337:
			case 10338:
			case 10339:
			case 10340:
			case 10341:
			case 10342:
			case 10343:
			case 10344:
			case 10345:
			case 10346:
			case 10347:
			case 10348:
			case 10349:
			case 10350:
			case 10351:
			case 10352:
			case 10353:
				itemDef.name = "@or1@" + itemDef.name;
			break;
			
			
			case 15998:
				itemDef.actions = new String[5];
				itemDef.actions[1] = "Wield";
				itemDef.name = "@gr3@Gilded whip"; //Name
				itemDef.description = "A gilded whip that increases drop rates.".getBytes();
				itemDef.originalModelColors = new int[2];
				itemDef.modifiedModelColors = new int[2];
				itemDef.originalModelColors[0] = 528;
				itemDef.modifiedModelColors[0] = 7114;
	            itemDef.modelRotation1 = 280;
	            itemDef.modelRotation2 = 0;
	            itemDef.modelOffset1 = -1;
	            itemDef.modelOffset2 = 56;
				itemDef.modelID = 5412;
				itemDef.maleEquip1 = 5409;
				itemDef.femaleEquip1 = 5409;
				itemDef.modelZoom = 840;
			break;
		case 16000:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "White whip"; //Name
			itemDef.description = "A white whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 100;
            itemDef.modelRotation1 = 280;
            itemDef.modelRotation2 = 0;
            itemDef.modelOffset1 = -1;
            itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
		break;
	case 16002:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Iron whip"; //Name
			itemDef.description = "A iron whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 20;
            itemDef.modelRotation1 = 280;
            itemDef.modelRotation2 = 0;
            itemDef.modelOffset1 = -1;
            itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;

		case 16004:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Steel whip"; //Name
			itemDef.description = "A steel whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 70;
                	                itemDef.modelRotation1 = 280;
                	                itemDef.modelRotation2 = 0;
                	                itemDef.modelOffset1 = -1;
                	                itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;

		case 16006:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Black whip"; //Name
			itemDef.description = "A black whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 0;
                	                itemDef.modelRotation1 = 280;
                	                itemDef.modelRotation2 = 0;
                	                itemDef.modelOffset1 = -1;
                	                itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;

		case 16008:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Mithril whip"; //Name
			itemDef.description = "A mith whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 43297;
                	                itemDef.modelRotation1 = 280;
                	                itemDef.modelRotation2 = 0;
                	                itemDef.modelOffset1 = -1;
                	                itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;

		case 16010:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Adamant whip"; //Name
			itemDef.description = "A addy whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 21662;
                	itemDef.modelRotation1 = 280;
                	itemDef.modelRotation2 = 0;
                	itemDef.modelOffset1 = -1;
                	itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;

		case 16012:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Rune whip"; //Name
			itemDef.description = "A rune whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 36133;
                	itemDef.modelRotation1 = 280;
                	itemDef.modelRotation2 = 0;
                	itemDef.modelOffset1 = -1;
                	itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;

		case 16014:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Dragon whip"; //Name
			itemDef.description = "A dragon whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 926;
                	itemDef.modelRotation1 = 280;
                	itemDef.modelRotation2 = 0;
                	itemDef.modelOffset1 = -1;
                	itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;

		case 16016:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Barrows whip"; //Name
			itemDef.description = "A barrows whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 5652;
                	itemDef.modelRotation1 = 280;
                	itemDef.modelRotation2 = 0;
                	itemDef.modelOffset1 = -1;
                	itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;
		
		case 16018:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "@gr3@Lava whip"; //Name
			itemDef.description = "A lava whip.".getBytes();
			itemDef.originalModelColors = new int[2];
			itemDef.modifiedModelColors = new int[2];
			itemDef.originalModelColors[0] = 528;
			itemDef.modifiedModelColors[0] = 6073;
                	itemDef.modelRotation1 = 280;
                	itemDef.modelRotation2 = 0;
                	itemDef.modelOffset1 = -1;
                	itemDef.modelOffset2 = 56;
			itemDef.modelID = 5412;
			itemDef.maleEquip1 = 5409;
			itemDef.femaleEquip1 = 5409;
			itemDef.modelZoom = 840;
			break;
		case 16022:
            		itemDef.actions = new String[5];
            		itemDef.modifiedModelColors = new int[1];
            		itemDef.originalModelColors = new int[1];
            		itemDef.actions[1] = "Wield";
            		itemDef.modelID = 5412;
            		itemDef.maleEquip1 = 5409;
            		itemDef.femaleEquip1 = 5409;
            		itemDef.modelZoom = 840;
            		itemDef.modelRotation1 = 280;
            		itemDef.modelRotation2 = 0;
            		itemDef.modelOffset1 = -2;
            		itemDef.modelOffset2 = 56;
            		itemDef.anInt204 = 0;
            		itemDef.name = "@gr3@Lime Whip";
            		itemDef.description = "A Lime Abyssal Whip".getBytes();
            		itemDef.modifiedModelColors[0] = 17350;
            		itemDef.originalModelColors[0] = 528;
        		break;
        	
		case 16024:
            		itemDef.actions = new String[5];
            		itemDef.modifiedModelColors = new int[1];
           		itemDef.originalModelColors = new int[1];
            		itemDef.actions[1] = "Wield";
           		itemDef.modelID = 5412;
           		itemDef.maleEquip1 = 5409;
           		itemDef.femaleEquip1 = 5409;
           		itemDef.modelZoom = 840;
            		itemDef.modelRotation1 = 280;
            		itemDef.modelRotation2 = 0;
            		itemDef.modelOffset1 = -2;
            		itemDef.modelOffset2 = 56;
            		itemDef.anInt204 = 0;
            		itemDef.name = "Pink Whip";
            		itemDef.description = "A Pink Abyssal Whip".getBytes();
            		itemDef.modifiedModelColors[0] = 350;
            		itemDef.originalModelColors[0] = 528;
        		break;
		
		case 16026:
       	    		itemDef.actions = new String[5];
           		itemDef.modifiedModelColors = new int[1];
           		itemDef.originalModelColors = new int[1];
           		itemDef.actions[1] = "Wield";
           		itemDef.modelID = 5412;
           		itemDef.maleEquip1 = 5409;
            		itemDef.femaleEquip1 = 5409;
            		itemDef.modelZoom = 840;
           		itemDef.modelRotation1 = 280;
           		itemDef.modelRotation2 = 0;
           		itemDef.modelOffset1 = -2;
           		itemDef.modelOffset2 = 56;
           		itemDef.anInt204 = 0;
           		itemDef.name = "Elemental Whip";
           		itemDef.description = "An Elemental Abyssal Whip".getBytes();
           		itemDef.modifiedModelColors[0] = 51120;
            		itemDef.originalModelColors[0] = 528;
        		break;
case 19787:
        itemDef.name = "@or2@Elite void knight top";
        itemDef.modelZoom = 1450;
        itemDef.modelRotation2 = 470;
        itemDef.modelRotation1 = 0;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 67463;
        itemDef.maleEquip1 = 66688;
        itemDef.femaleEquip1 = 67080;
break;
case 19788:
        itemDef.name = "@or2@Elite void knight robe";
        itemDef.modelZoom = 1824;
        itemDef.modelRotation2 = 443;
        itemDef.modelRotation1 = 0;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 67448;
        itemDef.maleEquip1 = 66594;
        itemDef.femaleEquip1 = 66985;
break;
case 19789:
        itemDef.name = "@or2@Elite void knight top";
        itemDef.modelZoom = 1450;
        itemDef.modelRotation2 = 270;
        itemDef.modelRotation1 = 200;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 67457;
        itemDef.maleEquip1 = 66687;
        itemDef.femaleEquip1 = 67079;
break;
case 19790:
        itemDef.name = "@or2@Elite void knight robe";
        itemDef.modelZoom = 1824;
        itemDef.modelRotation2 = 243;
        itemDef.modelRotation1 = 150;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 67450;
        itemDef.maleEquip1 = 66596;
        itemDef.femaleEquip1 = 66987;
break;
case 20752:
        itemDef.name = "@or2@Completionist hood";
        itemDef.modelZoom = 760;
        itemDef.modelRotation2 = 11;
        itemDef.modelRotation1 = 81;
        itemDef.modelOffset2 = 1;
        itemDef.modelOffset1 = -3;
        //itemDef.newModelColor = new int[] { 65214, 65200, 65186, 62995 };
        //itemDef.editedModelColor  = new int[] { 65214, 65200, 65186, 62995 };
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 65271;
        itemDef.maleEquip1 = 65292;
        itemDef.femaleEquip1 = 65310;
break;
case 20753:
        itemDef.name = "@or2@Completionist hood";
        itemDef.modelZoom = 760;
        itemDef.modelRotation2 = 11;
        itemDef.modelRotation1 = 81;
        itemDef.modelOffset2 = 1;
        itemDef.modelOffset1 = -3;
        //itemDef.newModelColor = new int[] { 65214, 65200, 65186, 62995 };
        //itemDef.editedModelColor  = new int[] { 65214, 65200, 65186, 62995 };
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 65271;
        itemDef.maleEquip1 = 65288;
        itemDef.femaleEquip1 = 65312;
break;
case 20763:
        itemDef.name = "@or2@Veteran cape";
        itemDef.modelZoom = 1513;
        itemDef.modelRotation2 = 279;
        itemDef.modelRotation1 = 948;
        itemDef.modelOffset2 = -3;
        itemDef.modelOffset1 = 24;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 65261;
        itemDef.maleEquip1 = 65305;
        itemDef.femaleEquip1 = 65318;
break;
case 20764:
        itemDef.name = "@or2@Veteran hood";
        itemDef.modelZoom = 760;
        itemDef.modelRotation2 = 11;
        itemDef.modelRotation1 = 81;
        itemDef.modelOffset2 = 1;
        itemDef.modelOffset1 = -3;
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 65271;
        itemDef.maleEquip1 = 65289;
        itemDef.femaleEquip1 = 65314;
break;
case 20769:
        itemDef.name = "@or1@Completionist cape";
        itemDef.modelZoom = 1316;
        itemDef.modelRotation2 = 252;
        itemDef.modelRotation1 = 1020;
        itemDef.modelOffset2 = -1;
        itemDef.modelOffset1 = 24;
        ////itemDef.newModelColor = new int[] { 65214, 65200, 65186, 62995 };
        ////itemDef.editedModelColor  = new int[] { 65214, 65200, 65186, 62995 };
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", "Customise", "Features", "Destroy" };
        itemDef.modelID = 65270;
        itemDef.maleEquip1 = 65297;
        itemDef.femaleEquip1 = 65316;
break;
case 20770:
        itemDef.name = "@or1@Completionist hood";
        itemDef.modelZoom = 760;
        itemDef.modelRotation2 = 11;
        itemDef.modelRotation1 = 81;
        itemDef.modelOffset2 = 1;
        itemDef.modelOffset1 = -3;
        ////itemDef.newModelColor = new int[] { 65214, 65200, 65186, 62995 };
        ////itemDef.editedModelColor  = new int[] { 65214, 65200, 65186, 62995 };
        itemDef.groundActions = new String[] { null, null, "Take", null, null };
        itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
        itemDef.modelID = 65273;
        itemDef.maleEquip1 = 65292;
        itemDef.femaleEquip1 = 65310;
break;
case 10555:
	itemDef.name = "@or2@Penance skirt";
	itemDef.modelRotation2 = 456;
	itemDef.modelRotation1 = 1798;
	itemDef.modelOffset1 = 1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
	itemDef.modelID = 20602;
	itemDef.maleEquip1 = 19252;
	itemDef.femaleEquip1 = 20510;
break;
case 21736:
	itemDef.name = "@or2@Akrisae's hood";
	itemDef.modelZoom = 622;
	//itemDef.modelRotation2 = 297;
	//itemDef.modelRotation1 = 156;
	itemDef.modelOffset2 = 5;
	itemDef.modelOffset1 = 46;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
	itemDef.modelID = 28354;
	itemDef.maleEquip1 = 28574;
	itemDef.femaleEquip1 = 28721;
break;

case 21752:
	itemDef.name = "@or2@Akrisae's robe top";
	itemDef.modelZoom = 1400;
	itemDef.modelRotation2 = 470;
	itemDef.modelRotation1 = 0;
	itemDef.modelOffset2 = 3;
	itemDef.modelOffset1 = 1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
	itemDef.modelID = 28441;
	itemDef.maleEquip1 = 28632;
	itemDef.femaleEquip1 = 28725;
break;

case 11846:
	itemDef.name = "@or2@Barrows - ahrim's set";
	itemDef.modelZoom = 2414;
	itemDef.modelRotation2 = 199;
	itemDef.modelRotation1 = 1795;
	itemDef.modelOffset2 = 6;
	itemDef.modelOffset1 = -22;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 30041;
	itemDef.anInt196 = 25;
	itemDef.anInt184 = 125;
break;
case 11848:
	itemDef.name = "@or2@Barrows - dharok's set";
	itemDef.modelZoom = 2414;
	itemDef.modelRotation2 = 199;
	itemDef.modelRotation1 = 1795;
	itemDef.modelOffset2 = 6;
	itemDef.modelOffset1 = -22;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 30051;
	itemDef.anInt196 = 25;
	itemDef.anInt184 = 125;
break;
case 11850:
	itemDef.name = "@or2@Barrows - guthan's set";
	itemDef.modelZoom = 2414;
	itemDef.modelRotation2 = 199;
	itemDef.modelRotation1 = 1795;
	itemDef.modelOffset2 = 6;
	itemDef.modelOffset1 = -22;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 30052;
	itemDef.anInt196 = 25;
	itemDef.anInt184 = 125;
break;
case 11852:
	itemDef.name = "@or2@Barrows - karil's set";
	itemDef.modelZoom = 2414;
	itemDef.modelRotation2 = 199;
	itemDef.modelRotation1 = 1795;
	itemDef.modelOffset2 = 6;
	itemDef.modelOffset1 = -22;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 30050;
	itemDef.anInt196 = 25;
	itemDef.anInt184 = 125;
break;
case 11854:
	itemDef.name = "@or2@Barrows - torag's set";
	itemDef.modelZoom = 2414;
	itemDef.modelRotation2 = 199;
	itemDef.modelRotation1 = 1795;
	itemDef.modelOffset2 = 6;
	itemDef.modelOffset1 = -22;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 30042;
	itemDef.anInt196 = 25;
	itemDef.anInt184 = 125;
break;
case 11856:
	itemDef.name = "@or2@Barrows - verac's set";
	itemDef.modelZoom = 2414;
	itemDef.modelRotation2 = 199;
	itemDef.modelRotation1 = 1795;
	itemDef.modelOffset2 = 6;
	itemDef.modelOffset1 = -22;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 30032;
	itemDef.anInt196 = 25;
	itemDef.anInt184 = 125;
break;

case 21760:
	itemDef.name = "@or2@Akrisae's robe skirt";
	itemDef.modelZoom = 1382;
	itemDef.modelRotation2 = 277;
	itemDef.modelRotation1 = 124;
	itemDef.modelOffset2 = 7;
	itemDef.modelOffset1 = 46;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
	itemDef.modelID = 28351;
	itemDef.maleEquip1 = 28620;
	itemDef.femaleEquip1 = 28724;
break;

case 21768:
	itemDef.name = "@or2@Barrows - akrisae's set";
	itemDef.modelZoom = 2414;
	itemDef.modelRotation2 = 199;
	itemDef.modelRotation1 = 1795;
	itemDef.modelOffset2 = 6;
	itemDef.modelOffset1 = -22;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 30041;
	itemDef.anInt196 = 25;
	itemDef.anInt184 = 125;
break;

case 21744:
	itemDef.name = "@or2@Akrisae's war mace";
	itemDef.modelZoom = 1447;
	itemDef.modelRotation2 = 189;
	itemDef.modelRotation1 = 673;
	itemDef.modelOffset2 = 11;
	itemDef.modelOffset1 = 7;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
	itemDef.modelID = 29341;
	itemDef.maleEquip1 = 29453;
	itemDef.femaleEquip1 = 29453;
break;

case 23114:
	itemDef.name = "@or2@Dragon full helm (or)";
	itemDef.modelZoom = 789;
	itemDef.modelRotation2 = 135;
	itemDef.modelRotation1 = 123;
	itemDef.modelOffset2 = -1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
	itemDef.modelID = 61414;
break;

case 23113:
	itemDef.name = "@or2@Dragon platebody (or)";
	itemDef.modelZoom = 1486;
	itemDef.modelRotation2 = 526;
	itemDef.modelRotation1 = 0;
	itemDef.modelOffset2 = 1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
	itemDef.modelID = 61395;
	itemDef.maleEquip1 = 61356;
break;

case 23117:
	itemDef.name = "@or2@Dragon platelegs (or)";
	itemDef.modelZoom = 1689;
	itemDef.modelRotation2 = 345;
	itemDef.modelRotation1 = 252;
	itemDef.modelOffset1 = 4;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
	itemDef.modelID = 61394;
break;

case 19340:
	itemDef.name = "@or2@Dragon square shield (or)";
	itemDef.modelZoom = 1360;
	itemDef.modelRotation2 = 295;
	itemDef.modelRotation1 = 106;
	itemDef.modelOffset2 = 3;
	itemDef.modelOffset1 = 47;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wield", "Split", null, "Drop" };
	itemDef.modelID = 67549;
	itemDef.maleEquip1 = 67809;
	itemDef.femaleEquip1 = 67812;
break;
case 19341:
	itemDef.name = "@or2@Dragon full helm (sp)";
	itemDef.modelZoom = 871;
	itemDef.modelRotation2 = 96;
	itemDef.modelRotation1 = 240;
	itemDef.modelOffset2 = -9;
	itemDef.modelOffset1 = 3;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
	itemDef.modelID = 67225;
	itemDef.maleEquip1 = 66422;
	itemDef.femaleEquip1 = 66806;
break;
case 19342:
	itemDef.name = "@or2@Dragon platebody (sp)";
	itemDef.modelZoom = 1614;
	itemDef.modelRotation2 = 526;
	itemDef.modelRotation1 = 0;
	itemDef.modelOffset2 = 1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
	itemDef.modelID = 67242;
	itemDef.maleEquip1 = 66635;
	itemDef.femaleEquip1 = 67024;
	itemDef.anInt196 = 15;
	itemDef.anInt184 = 75;
break;
case 19343:
	itemDef.name = "@or2@Dragon platelegs (sp)";
	itemDef.modelZoom = 1689;
	itemDef.modelRotation2 = 405;
	itemDef.modelRotation1 = 249;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
	itemDef.modelID = 67232;
	itemDef.maleEquip1 = 66533;
	itemDef.femaleEquip1 = 66919;
break;
case 19344:
	itemDef.name = "@or2@Dragon plateskirt (sp)";
	itemDef.modelZoom = 1689;
	itemDef.modelRotation2 = 405;
	itemDef.modelRotation1 = 249;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", "Split", null, "Drop" };
	itemDef.modelID = 67236;
	itemDef.maleEquip1 = 66528;
	itemDef.femaleEquip1 = 66920;
break;
case 19345:
	itemDef.name = "@or2@Dragon sq shield (sp)";
	itemDef.modelZoom = 1616;
	itemDef.modelRotation2 = 280;
	itemDef.modelRotation1 = 106;
	itemDef.modelOffset2 = 3;
	itemDef.modelOffset1 = 47;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wield", "Split", null, "Drop" };
	itemDef.modelID = 67551;
	itemDef.maleEquip1 = 67811;
	itemDef.femaleEquip1 = 67815;
break;
case 19346:
	itemDef.name = "@or2@Dragon full helm ornament kit (or)";
	itemDef.modelZoom = 2134;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 1978;
	itemDef.modelOffset2 = 3;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58255;
break;
case 19348:
	itemDef.name = "@or2@Dragon platelegs/skirt ornament kit (or)";
	itemDef.modelZoom = 2134;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 1978;
	itemDef.modelOffset2 = 3;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58251;
break;
case 19350:
	itemDef.name = "@or2@Dragon platebody ornament kit (or)";
	itemDef.modelZoom = 2134;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 1978;
	itemDef.modelOffset2 = 3;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58210;
break;
case 19352:
	itemDef.name = "@or2@Dragon sq shield ornament kit (or)";
	itemDef.modelZoom = 2134;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 1978;
	itemDef.modelOffset2 = 3;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58193;
break;
case 19354:
	itemDef.name = "@or2@Dragon full helm ornament kit (sp)";
	itemDef.modelZoom = 2095;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 124;
	itemDef.modelOffset2 = 4;
	itemDef.modelOffset1 = -1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58264;
break;
case 19356:
	itemDef.name = "@or2@Dragon platelegs/skirt ornament kit (sp)";
	itemDef.modelZoom = 2095;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 124;
	itemDef.modelOffset2 = 4;
	itemDef.modelOffset1 = -1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58196;
break;
case 19358:
	itemDef.name = "@or2@Dragon platebody ornament kit (sp)";
	itemDef.modelZoom = 2095;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 124;
	itemDef.modelOffset2 = 4;
	itemDef.modelOffset1 = -1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58190;
break;
case 19360:
	itemDef.name = "@or2@Dragon sq shield ornament kit (sp)";
	itemDef.modelZoom = 2095;
	itemDef.modelRotation2 = 500;
	itemDef.modelRotation1 = 124;
	itemDef.modelOffset2 = 4;
	itemDef.modelOffset1 = -1;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, null, null, null, "Drop" };
	itemDef.modelID = 58239;
break;
	case 20072:
        itemDef.actions = new String[5];
        itemDef.actions[1] = "Wear";
        itemDef.originalModelColors = new int[1];
        itemDef.modifiedModelColors = new int[1];
        itemDef.originalModelColors[0] = 28; // colors
        itemDef.originalModelColors[0] = 74; // colors
        itemDef.modifiedModelColors[0] = 38676; // colors
        itemDef.modifiedModelColors[0] = 924; // colors 
        itemDef.anInt204 = 0;        
        itemDef.modelID = 15335;//inventory/drop model
        itemDef.maleEquip1 = 15413;
        itemDef.femaleEquip1 = 15413;
        itemDef.modelZoom = 490;//Model Zoom
        itemDef.modelRotation1 = 344;//Model Rotation1
        itemDef.modelRotation2 = 192;//Model Rotation2
        itemDef.modelOffset1 = 1;//Model Offset 1
        itemDef.modelOffset2 = 20;// model Offset 2
        itemDef.anInt188 = -1;
        itemDef.anInt164 = -1;
        itemDef.anInt175 = -1;
        itemDef.anInt197 = -1;
        itemDef.name = "@or2@Dragon defender";
        itemDef.description = "A pointy off-hand knife.".getBytes();
	break;
		case 15041: 
            itemDef.actions = new String[5];
            itemDef.actions[1] = "Wield";
			/*itemDef.femaleEquip1 = 56307;
			itemDef.maleEquip1 = 56307;*/
			itemDef.femaleEquip1 = 35093; //male wield model
			itemDef.maleEquip1 = 35093; //femArmModel
            itemDef.modelOffset1 = -1;
            itemDef.modelOffset2 = 0;
            itemDef.modelZoom = 898;
            itemDef.modelRotation2 = 1049;
            itemDef.modelRotation1 = 1221;
            itemDef.modelID = 35092;
            itemDef.name = "@or1@Chaotic crossbow";
            itemDef.anInt188 = -1;
            itemDef.anInt164 = -1;
            itemDef.aByte205 = -26;
            itemDef.aByte154 = -14;
			itemDef.originalModelColors = new int[] { 33904 };
			itemDef.modifiedModelColors = new int[] { 8613 };
            itemDef.description = "A destructive crossbow. (It doesnt look in good condition.)".getBytes();
            break;
	case 13399:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wield";
      itemDef.femaleEquip1 = 56042;
      itemDef.modelZoom = 1400;
      itemDef.modelRotation2 = 337;
      itemDef.maleEquip1 = 56042;
      itemDef.modelID = 54520;
      itemDef.anInt164 = -1;
      itemDef.name = "@or2@Primal maul";
      itemDef.description = "A maul used to claim life from those who don't deserve it.".getBytes();
      break;

    case 13022:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wield";
      itemDef.modelID = 48512;
      itemDef.modelZoom = 1744;
      itemDef.modelRotation2 = 738;
      itemDef.modelRotation1 = 1985;
      itemDef.modelOffset1 = 0;
      itemDef.modelOffset2 = 0;
      itemDef.maleEquip1 = 48465;
      itemDef.femaleEquip1 = 48465;
      itemDef.name = "@bar@Hand cannon";
      itemDef.description = "A miniature dwarven cannon. ".getBytes();
      break;
    case 15590:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wear";
      itemDef.femaleEquip1 = 56460;
      itemDef.modelOffset2 = 6;
      itemDef.modelZoom = 1720;
      itemDef.modelRotation2 = 0;
      itemDef.maleEquip1 = 55804;
      itemDef.modelID = 54068;
      itemDef.name = "@bar@Primal plateskirt";
      itemDef.description = "Designer leg protection.".getBytes();
      break;
			case 15595:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 32799;
			itemDef.name = "Twisted bow";
			itemDef.modelZoom = 2000;
			itemDef.modelRotation2 = 720;
			itemDef.modelRotation1 = 1500;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.femaleEquip1 = 32674;
			itemDef.maleEquip1 = 32674;
			itemDef.description = "A mystical bow carved from the twisted remains of the Great Olm.".getBytes();
			break;
    case 15592:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wear";
      itemDef.femaleEquip1 = 55770;
      itemDef.modelOffset2 = 1;
      itemDef.modelZoom = 921;
      itemDef.modelRotation2 = 121;
      itemDef.maleEquip1 = 55770;
      itemDef.modelID = 54164;
      itemDef.name = "@bar@Primal full helm";
      itemDef.description = "A full face helmet".getBytes();
      break;
    case 15593:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wear";
      itemDef.femaleEquip1 = 56515;
      itemDef.modelOffset2 = 10;
      itemDef.modelZoom = 1447;
      itemDef.modelRotation2 = 0;
      itemDef.modelRotation1 = 504;
      itemDef.maleEquip1 = 55851;
      itemDef.modelID = 54126;
      itemDef.name = "@bar@Primal platebody";
      itemDef.description = "An epic platebody".getBytes();
      break;
    case 15594:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wear";
      itemDef.femaleEquip1 = 55991;
      itemDef.modelOffset2 = 0;
      itemDef.modelZoom = 1776;
      itemDef.modelRotation2 = 0;
      itemDef.modelRotation1 = 344;
      itemDef.maleEquip1 = 55991;
      itemDef.modelID = 54253;
      itemDef.anInt188 = -1;
      itemDef.anInt164 = -1;
      itemDef.aByte154 = -10;
      itemDef.aByte205 = -10;
      itemDef.name = "@bar@Primal kiteshield";
      itemDef.description = "A large metal shield.".getBytes();
      break;
    case 15574:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wield";
      itemDef.femaleEquip1 = 56163;
      itemDef.modelOffset1 = 3;
      itemDef.modelOffset2 = 0;
      itemDef.modelZoom = 1650;
      itemDef.modelRotation2 = 1300;
      itemDef.modelRotation1 = 498;
      itemDef.maleEquip1 = 56100;
      itemDef.modelID = 54437;
      itemDef.anInt164 = -1;
      itemDef.name = "@bar@Primal longsword";
      itemDef.description = "A razor-sharp longsword. (Tier 11)".getBytes();
      break;
      case 18391:
		itemDef.actions = new String[5];
		itemDef.actions[1] = "Wield";
		itemDef.modelOffset1 = -1;
		itemDef.femaleEquip1 = 56046;
		itemDef.modelOffset2 = 0;
		itemDef.modelZoom = 1776;
		itemDef.modelRotation1 = 1576;
		itemDef.modelRotation2 = 1589;
		//itemDef.anInt169 = -1;
		itemDef.maleEquip1 = 56046;
		itemDef.modelID = 54373;                
		itemDef.anInt164 = -1;
		itemDef.name = "@bar@Primal 2h sword";
		itemDef.description = "It's a Primal 2h sword yo".getBytes();
	break;
	 /* case 18363:
			case 15044:
			itemDef.itemActions = new String[5];
			itemDef.itemActions[1] = "Wear";
			itemDef.modelID = 54313; //drop model
			itemDef.anInt165 = 55947; //male wield model
			itemDef.anInt200 = 55947; //femArmModel
			itemDef.modelZoom = 1670;
			itemDef.modelRotationY = 316;
			itemDef.modelRotationX = 64;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 14;
			itemDef.name = "Farseer Shield";
			itemDef.description = "A reward from Dungeoneering".getBytes();
			break;*/
    case 15662:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wield";
      itemDef.femaleEquip1 = 56227;
      itemDef.modelOffset1 = 9;
      itemDef.modelOffset2 = 13;
      itemDef.modelZoom = 1425;
      itemDef.modelRotation2 = 1300;
      itemDef.modelRotation1 = 700;
      itemDef.maleEquip1 = 56227;
      itemDef.modelID = 54202;
      itemDef.anInt164 = -1;
      itemDef.name = "@or2@Primal rapier";
      itemDef.description = "A razor-sharp rapier. (Tier 11)".getBytes();
      break;

    case 15060:
      itemDef.actions = new String[5];
      itemDef.actions[1] = "Wear";
      itemDef.modelID = 35554;
      itemDef.modelZoom = 789;
      itemDef.modelRotation1 = 69;
      itemDef.modelRotation2 = 1743;
      itemDef.modelOffset1 = -4;
      itemDef.modelOffset2 = 0;
      itemDef.maleEquip1 = 35553;
      itemDef.femaleEquip1 = 35552;
	  itemDef.actions = new String[] { null, "Wear", null, "Disassemble", "Drop" };
      itemDef.name = "@or2@Slayer helmet";
      break;

    case 15049:
      itemDef.actions[1] = "Wield";
      itemDef.modelID = 44576;
      itemDef.modelZoom = 1300;
      itemDef.modelRotation1 = 400;
      itemDef.modelRotation2 = 0;
      itemDef.modelOffset1 = 0;
      itemDef.modelOffset2 = 10;
      itemDef.maleEquip1 = 40207;
      itemDef.femaleEquip1 = 40207;
      itemDef.aByte154 = -10;
      itemDef.anInt188 = -1;
      itemDef.anInt164 = -1;
      itemDef.name = "@or2@Dragon Platebody";
	break;
		case 3758:
			itemDef.name = "@bar@Fremennik shield";
		break;
		case 13887:
			itemDef.name = "@bar@Vesta's chainbody";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42593;
			itemDef.modelZoom = 1440;
			itemDef.modelRotation1 = 545;
			itemDef.modelRotation2 = 2;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 4;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42624;
			itemDef.femaleEquip1 = 42644;
			itemDef.description = "Vesta's chainbody, a reward from PVP.".getBytes();
			break;

		case 13893:
			itemDef.name = "@bar@Vesta's plateskirt";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42581;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset2 = 11;
			itemDef.modelOffset1 = -3;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42633;
			itemDef.femaleEquip1 = 42649;
			itemDef.description = "Vesta's plateskirt, a reward from PVP.".getBytes();
			break;
		case 13899:
			itemDef.name = "@bar@Vesta's longsword";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 42597;
			itemDef.modelZoom = 1744;
			itemDef.modelRotation1 = 738;
			itemDef.modelRotation2 = 1985;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42615;
			itemDef.femaleEquip1 = 42615;
			itemDef.description = "Vesta's longsword, a reward from PVP.".getBytes();
			break;
		case 15006:
			itemDef.name = "@bar@Corrupt Vesta's longsword";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 42597;
			itemDef.modelZoom = 1744;
			itemDef.modelRotation1 = 738;
			itemDef.modelRotation2 = 1985;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42615;
			itemDef.femaleEquip1 = 42615;
			itemDef.description = "Vesta's longsword, a reward from PVP.".getBytes();
			break;

		case 13905:
			itemDef.name = "@bar@Vesta's spear";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Equip";
			itemDef.modelID = 42599;
			itemDef.modelZoom = 2022;
			itemDef.modelRotation1 = 480;
			itemDef.modelRotation2 = 15;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 0;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42614;
			itemDef.femaleEquip1 = 42614;
			itemDef.description = "Vesta's spear, a reward from PVP.".getBytes();
		break;

		case 13858:
			itemDef.name = "@bar@Zuriel's robe top";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42591;
			itemDef.modelZoom = 1373;
			itemDef.modelRotation1 = 373;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = -7;
			itemDef.modelOffset1 = 0;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42627;
			itemDef.femaleEquip1 = 42642;
			itemDef.description = "Zuriel's robe top, a reward from PVP.".getBytes();
			break;
		case 13861:
			itemDef.name = "@bar@Zuriel's robe bottom";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42588;
			itemDef.modelZoom = 1697;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = -9;
			itemDef.modelOffset1 = 2;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42634;
			itemDef.femaleEquip1 = 42645;
			itemDef.description = "Zuriel's robe bottom, a reward from PVP.".getBytes();
			break;
		case 13864:
			itemDef.name = "@bar@Zuriel's hood";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42604;
			itemDef.modelZoom = 720;
			itemDef.modelRotation1 = 28;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = 1;
			itemDef.modelOffset1 = 1;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42638;
			itemDef.femaleEquip1 = 42653;
			itemDef.description = "Zuriel's hood, a reward from PVP.".getBytes();
			break;
		case 13884:
			itemDef.name = "@bar@Statius's platebody";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42602;
			itemDef.modelZoom = 1312;//1312
			itemDef.modelRotation1 = 272;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset2 = 39;
			itemDef.modelOffset1 = -2;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42625;
			itemDef.femaleEquip1 = 42641;
			itemDef.description = "Statius's platebody, a reward from PVP.".getBytes();
			break;
		case 13890:
			itemDef.name = "@bar@Statius's platelegs";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42590;
			itemDef.modelZoom = 1625;
			itemDef.modelRotation1 = 355;
			itemDef.modelRotation2 = 2046;
			itemDef.modelOffset2 = -11;
			itemDef.modelOffset1 = 0;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42632;
			itemDef.femaleEquip1 = 42647;
			itemDef.description = "Statius's platelegs, a reward from PVP.".getBytes();
			break;

		case 13896:
			itemDef.name = "@bar@Statius's full helm";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42596;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 96;
			itemDef.modelRotation2 = 2039;
			itemDef.modelOffset2 = -7;
			itemDef.modelOffset1 = 2;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42639;
			itemDef.femaleEquip1 = 42655;
			itemDef.description = "Statius's full helm, a reward from PVP.".getBytes();
			break;
		case 13902:
			itemDef.name = "@bar@Statius's warhammer";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 42577;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 507;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset2 = 6;
			itemDef.modelOffset1 = 7;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42623;
			itemDef.femaleEquip1 = 42623;
			itemDef.description = "Statius's warhammer, a reward from PVP.".getBytes();
			break;
		case 13870:
			itemDef.name = "@bar@Morrigan's leather body";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42578;
			itemDef.modelZoom = 1184;
			itemDef.modelRotation1 = 545;
			itemDef.modelRotation2 = 2;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 4;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42626;
			itemDef.femaleEquip1 = 42643;
			itemDef.description = "Morrigan's leather body, a reward from PVP.".getBytes();
			break;

		case 13873:
			itemDef.name = "@bar@Morrigan's leather chaps";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42603;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 482;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset2 = 11;
			itemDef.modelOffset1 = -3;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42631;
			itemDef.femaleEquip1 = 42646;
			itemDef.description = "Morrigan's leather chaps, a reward from PVP.".getBytes();
			break;

		case 13876:
			itemDef.name = "@bar@Morrigan's coif";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42583;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 537;
			itemDef.modelRotation2 = 5;
			itemDef.modelOffset2 = 6;
			itemDef.modelOffset1 = -3;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42636;
			itemDef.femaleEquip1 = 42652;
			itemDef.description = "Morrigan's coif, a reward from PVP.".getBytes();
			break;
		case 13879:
			itemDef.name = "@bar@Morrigan's javelin";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 42592;
			itemDef.modelZoom = 1872;
			itemDef.modelRotation1 = 282;
			itemDef.modelRotation2 = 2009;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.aByte205 = -24;
			itemDef.aByte154 = -24;
			itemDef.maleEquip1 = 42613;
			itemDef.femaleEquip1 = 42613;
			itemDef.description = "Morrigan's javelin, a reward from PVP.".getBytes();
			break;
		case 22358:
			itemDef.name = "Goliath gloves (black)";
			itemDef.modelZoom = 592;
			itemDef.modelRotation2 = 539;
			itemDef.modelRotation1 = 40;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -4;
			itemDef.modelID = 3831;
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.maleEquip1 = 2301;
			itemDef.femaleEquip1 = 2323;
		break;
		case 22494:
			itemDef.name = "@bar@Polypore staff";
			itemDef.modelZoom = 3750;
			itemDef.modelRotation2 = 1454;
			itemDef.modelRotation1 = 997;
			itemDef.modelOffset1 = 8;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.aByte205 = -13;
			itemDef.aByte154 = -13;
			itemDef.modelID = 13426;
			itemDef.maleEquip1 = 13417;
			itemDef.femaleEquip1 = 13417;
		break;
		case 15550:
			itemDef.modelID = 62103;
			itemDef.name = "Witchdoctor mask";
			itemDef.modelZoom = 811;
			itemDef.modelRotation1 = 500;
			itemDef.modelRotation2 = 221;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 3;
			itemDef.maleEquip1 = 62088;
			itemDef.femaleEquip1 = 62095;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
		break;
		case 22359:
			itemDef.name = "Goliath gloves (white)";
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 40;
			itemDef.modelOffset2 = 1;
			itemDef.modelOffset1 = -4;
			itemDef.modelID = 3831;
			itemDef.originalModelColors = new int[] { 64585, 64590, 64595 };
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.maleEquip1 = 8726;
			itemDef.femaleEquip1 = 8750;
		break;
		case 22360:
			itemDef.name = "Goliath gloves (yellow)";
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 40;
			itemDef.modelOffset2 = 1;
			itemDef.modelOffset1 = -4;
			itemDef.modelID = 3831;
			itemDef.originalModelColors = new int[] { 9767, 9772, 9777 };
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.maleEquip1 = 8726;
			itemDef.femaleEquip1 = 8750;
			itemDef.anInt196 = 30;
		break;
		case 13883:
			itemDef.name = "@bar@Morrigan's throwing axe";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 42582;
			itemDef.modelZoom = 976;
			itemDef.modelRotation1 = 672;
			itemDef.modelRotation2 = 2024;
			itemDef.modelOffset2 = 4;
			itemDef.modelOffset1 = -5;
			itemDef.anInt204 = 0;
			itemDef.maleEquip1 = 42611;
			itemDef.femaleEquip1 = 42611;
			itemDef.description = "Morrigan's throwing axe, a reward from PVP.".getBytes();
			itemDef.anInt196 = 30;
			break;
		case 13738:
			itemDef.actions = new String[5];//menu
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 40922;//inventory/drop model
			itemDef.modelZoom = 1616;//Model Zoom
			itemDef.modelRotation1 = 396;//rotation 1
			itemDef.modelRotation2 = 1050;//rotation 2
			itemDef.modelOffset1 = -3;//model offset 1
			itemDef.modelOffset2 = 4;//model offset 2
			itemDef.maleEquip1 = 40944;//male wield ModelId
			itemDef.femaleEquip1 = 40944;//female wield ModelId
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
			itemDef.aByte154 = -10;
			itemDef.aByte205 = -10;
			itemDef.name = "@bar@Arcane spirit shield";//name
			itemDef.description = "It's a Arcane spirit shield.".getBytes();//name
			itemDef.anInt196 = 30;
		break;
		case 13744:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 40920;
			itemDef.modelZoom = 1616; 
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 4;
			itemDef.maleEquip1 = 40940;
			itemDef.femaleEquip1 = 40940;
			itemDef.anInt188 = -1;
			itemDef.anInt164 = -1;
			itemDef.aByte154 = -10;
			itemDef.aByte205 = -10;
			itemDef.name = "@bar@Spectral spirit shield";
			itemDef.description = "It's a Spectral spirit shield.".getBytes();
		break;
		case 13740:
			itemDef.actions = new String[5];//menu
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 40921;//inventory/drop model
			itemDef.modelZoom = 1616;//Model Zoom
			itemDef.modelRotation1 = 396;//rotation 1
			itemDef.modelRotation2 = 1050;//rotation 2
			itemDef.modelOffset1 = -3;//model offset 1
			itemDef.modelOffset2 = 4;//model offset 2
			itemDef.maleEquip1 = 40939;//male wield ModelId
			itemDef.femaleEquip1 = 40939;//female wield ModelId
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
			itemDef.aByte154 = -10;
			itemDef.aByte205 = -10;
			itemDef.name = "@bar@Divine spirit shield";//name
			itemDef.description = "It's a Divine spirit shield.".getBytes();//name
		break;
		case 13742:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 40915;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 4;
			itemDef.maleEquip1 = 40942;
			itemDef.femaleEquip1 = 40942;
			itemDef.anInt188 = -1;
			itemDef.anInt164 = -1;
			itemDef.aByte154 = -10;
			itemDef.aByte205 = -10;
			itemDef.name = "@bar@Elysian spirit shield";
			itemDef.description = "It's an Elysian spirit shield.".getBytes();
		break;
		case 13734:
			itemDef.actions = new String[5];//menu
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 40919;//inventory/drop model
			itemDef.modelZoom = 1616;//Model Zoom
			itemDef.modelRotation1 = 396;//rotation 1
			itemDef.modelRotation2 = 1050;//rotation 2
			itemDef.modelOffset1 = -3;//model offset 1
			itemDef.modelOffset2 = 4;//model offset 2
			itemDef.maleEquip1 = 40943;//male wield ModelId
			itemDef.femaleEquip1 = 40943;//female wield ModelId
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
			itemDef.aByte154 = -10;
			itemDef.aByte205 = -10;
			itemDef.name = "Spirit shield";//name
			itemDef.description = "It's a Spirit shield.".getBytes();//name
		break;
case 13290:
	itemDef.name = "@or1@Leaf-bladed sword";
	itemDef.modelZoom = 1104;
	itemDef.modelRotation2 = 242;
	itemDef.modelRotation1 = 175;
	itemDef.modelOffset1 = -18;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
	itemDef.modelID = 34603;
	itemDef.maleEquip1 = 34602;
	itemDef.femaleEquip1 = 34602;
break;
case 19669:
	itemDef.name = "Ring of vigour";
	itemDef.modelZoom = 720;
	itemDef.modelRotation2 = 165;
	itemDef.modelRotation1 = 1865;
	itemDef.modelOffset2 = -7;
	itemDef.modelOffset1 = -12;
	itemDef.groundActions = new String[] { null, null, "Take", null, null };
	itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
	itemDef.modelID = 59615;
break;
case 7285:
	itemDef.name = "@cya@Update Log";
break;
		case 13736:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 40913;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 396;
			itemDef.modelRotation2 = 1050;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 4;
			itemDef.maleEquip1 = 40941;
			itemDef.femaleEquip1 = 40941;
			itemDef.anInt188 = -1;
			itemDef.anInt164 = -1;
			itemDef.aByte154 = -10;
			itemDef.aByte205 = -10;
			itemDef.name = "Blessed spirit shield";
			itemDef.description = "It's a Blessed spirit shield.".getBytes();
		break;
		
		case 15272:
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Eat";
			itemDef.modelID = 48728;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1926;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Rocktail";
			itemDef.description = "Some nicely cooked rocktail.".getBytes();
		break;
		case 15273:
			itemDef.modelID = 48728;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1926;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Rocktail";
			itemDef.stackable = true;
			itemDef.description = "Exchange this at any bank for Rocktail.".getBytes();
		break;
		case 15271:
			itemDef.actions = new String[5];
			itemDef.modelID = 48722;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1926;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Raw rocktail";
			itemDef.description = "I should try cooking this.".getBytes();
		break;
		case 15274:
			itemDef.actions = new String[5];
			itemDef.modelID = 48725;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1926;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.name = "Burnt rocktail";
			itemDef.description = "Oops! Maybe a little less heat next time.".getBytes();
		break;
		case 14876:
			itemDef.actions = new String[5];
			itemDef.modelID = 47258;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 202;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 43;
			itemDef.stackable = false;
			itemDef.name = "@or1@Ancient statuette";
			itemDef.description = "A mysterious statuette of ancient times.".getBytes();
		break;
		case 14877:
			itemDef.actions = new String[5];
			itemDef.modelID = 47257;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 81;
			itemDef.modelRotation2 = 337;
			itemDef.modelOffset2 = -27;
			itemDef.modelOffset1 = 0;
			itemDef.stackable = false;
			itemDef.name = "@or2@Seren statuette";
			itemDef.description = "A small statuette that appears to be entirely made of crystal.".getBytes();
		break;
		case 14878:
			itemDef.actions = new String[5];
			itemDef.modelID = 47256;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 148;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = -30;
			itemDef.stackable = false;
			itemDef.name = "@or2@Armadyl statuette";
			itemDef.description = "A dedication to Armadyl, carved from the wing bones of his fallen warriors.".getBytes();
		break;
		case 14879:
			itemDef.actions = new String[5];
			itemDef.modelID = 47250;
			itemDef.modelZoom = 976;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 75;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 33;
			itemDef.stackable = false;
			itemDef.name = "@or2@Zamorak statuette";
			itemDef.description = "A small obsidian statuette in the shape of a black demon. ".getBytes();
		break;
		case 14880:
			itemDef.actions = new String[5];
			itemDef.modelID = 47248;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 75;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 43;
			itemDef.stackable = false;
			itemDef.name = "@or2@Saradomin statuette";
			itemDef.description = "An angel statuette dedicated to Saradomin.".getBytes();
		break;
		case 14881:
			itemDef.actions = new String[5];
			itemDef.modelID = 47244;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 153;
			itemDef.modelRotation2 = 1841;
			itemDef.modelOffset2 = -32;
			itemDef.modelOffset1 = 0;
			itemDef.stackable = false;
			itemDef.name = "@or2@Bandos statuette";
			itemDef.description = "A statuette resembling an ork-like creature.".getBytes();
		break;
		case 14882:
			itemDef.actions = new String[5];
			itemDef.modelID = 47247;
			itemDef.modelZoom = 720;
			itemDef.modelRotation1 = 105;
			itemDef.modelRotation2 = 1653;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 57;
			itemDef.stackable = false;
			itemDef.name = "@or2@Ruby chalice";
			itemDef.description = "A small obsidian challice with a finely cut ruby in it.".getBytes();
		break;
		case 14883:
			itemDef.actions = new String[5];
			itemDef.modelID = 47252;
			itemDef.modelZoom = 1300;
			itemDef.modelRotation1 = 141;
			itemDef.modelRotation2 = 1949;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.stackable = false;
			itemDef.name = "@or2@Guthixian brazier";
			itemDef.description = "A bronze ritual brazier, trimmed with jade and emeralds.".getBytes();
		break;
		case 14884:
			itemDef.actions = new String[5];
			itemDef.modelID = 47251;
			itemDef.modelZoom = 1032;
			itemDef.modelRotation1 = 364;
			itemDef.modelRotation2 = 1872;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 5;
			itemDef.stackable = false;
			itemDef.name = "@or2@Armadyl totem";
			itemDef.description = "A painted, wooden eagle in fine condition.".getBytes();
		break;
		case 14885:
			itemDef.actions = new String[5];
			itemDef.modelID = 47259;
			itemDef.modelZoom = 724;
			itemDef.modelRotation1 = 377;
			itemDef.modelRotation2 = 916;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = -1;
			itemDef.stackable = false;
			itemDef.name = "@or2@Zamorak medallion";
			itemDef.description = "A black, metal symbol decorated with three blood red rubies.".getBytes();
		break;
		case 14886:
			itemDef.actions = new String[5];
			itemDef.modelID = 47246;
			itemDef.modelZoom = 1744;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = -32;
			itemDef.stackable = false;
			itemDef.name = "@or2@Saradomin carving";
			itemDef.description = "A wooden angel icon dedicated to Saradomin. ".getBytes();
		break;
		case 14887:
			itemDef.actions = new String[5];
			itemDef.modelID = 47245;
			itemDef.modelZoom = 921;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.stackable = false;
			itemDef.name = "@or2@Bandos scrimshaw";
			itemDef.description = "A stylised ogre face, crafted out of bone.".getBytes();
		break;

	case 15888:
		itemDef.actions = new String[5];
		itemDef.actions[1] = "Wear";
		itemDef.modelID = 56821;
		itemDef.modelZoom = 2128;
		itemDef.modelRotation1 = 504;
		itemDef.modelRotation2 = 0;
		itemDef.modelOffset1 = 0;
		itemDef.modelOffset2 = 1;
		itemDef.maleEquip1 = 56555;
		itemDef.femaleEquip1 = 55903;
		itemDef.name = "Dungeoneering cape";
		itemDef.description = "Worn by the master's in the dungeoneering skill.".getBytes();
		break;
	case 15889:
		itemDef.actions = new String[5];
		itemDef.actions[1] = "Wear";
		itemDef.modelID = 56820;
		itemDef.modelZoom = 2128;
		itemDef.modelRotation1 = 504;
		itemDef.modelRotation2 = 0;
		itemDef.modelOffset1 = 0;
		itemDef.modelOffset2 = 1;
		itemDef.maleEquip1 = 56556;
		itemDef.femaleEquip1 = 56902;
		itemDef.name = "Dungeoneering cape";
		itemDef.description = "Worn by the master's in the dungeoneering skill.".getBytes();
	break;

		case 14888:
			itemDef.actions = new String[5];
			itemDef.modelID = 47254;
			itemDef.modelZoom = 1347;
			itemDef.modelRotation1 = 81;
			itemDef.modelRotation2 = 1670;
			itemDef.modelOffset2 = 15;
			itemDef.modelOffset1 = 3;
			itemDef.stackable = false;
			itemDef.name = "Saradomin amphora";
			itemDef.description = "A ceramic vase with a Saradomin symbol painted on it.".getBytes();
		break;
		case 14889:
			itemDef.actions = new String[5];
			itemDef.modelID = 47249;
			itemDef.modelZoom = 848;
			itemDef.modelRotation1 = 111;
			itemDef.modelRotation2 = 1347;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = -5;
			itemDef.stackable = false;
			itemDef.name = "Ancient psaltery bridge";
			itemDef.description = "A part of an old instrument.".getBytes();
		break;
		case 14890:
			itemDef.actions = new String[5];
			itemDef.modelID = 47255;
			itemDef.modelZoom = 835;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 13;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = -1;
			itemDef.stackable = false;
			itemDef.name = "@or2@Bronzed dragon claw";
			itemDef.description = "This big claw was once part of a ceremonial necklace.".getBytes();
		break;
		case 14891:
			itemDef.actions = new String[5];
			itemDef.modelID = 47243;
			itemDef.modelZoom = 1104;
			itemDef.modelRotation1 = 130;
			itemDef.modelRotation2 = 1820;
			itemDef.modelOffset2 = 20;
			itemDef.modelOffset1 = 0;
			itemDef.stackable = false;
			itemDef.name = "@or2@Third age carafe";
			itemDef.description = "A very old clay carafe.".getBytes();
		break;
		case 14892:
			itemDef.actions = new String[5];
			itemDef.modelID = 47253;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 417;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = -9;
			itemDef.stackable = false;
			itemDef.name = "@or2@Broken statue headdress";
			itemDef.description = "This was once part of a big statue.".getBytes();
		break;
		case 15000:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.femaleEquip1 = 56505;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -12;
			itemDef.modelZoom = 976;
			itemDef.modelRotation2 = 51;
			itemDef.modelRotation1 = 510;
			itemDef.maleEquip1 = 55825;
			itemDef.modelID = 56779;
			itemDef.name = "@or2@Arcane stream necklace";
			itemDef.description = "The energy from this necklace is unlike anything you have ever felt.".getBytes();
		break;
		case 15001:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 51802;//female
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 2;
			itemDef.modelZoom = 1490;
			itemDef.modelRotation2 = 1400;
			itemDef.modelRotation1 = 148;
			itemDef.maleEquip1 = 51800;
			itemDef.modelID = 51799;
			itemDef.name = "@or2@Staff of light";
			itemDef.description = "Humming with power.".getBytes();
		break;
		
		case 4067:
			itemDef.name = "@or1@Donator Ticket";
			itemDef.description = "I got a whole lot of money..".getBytes();
		break;
		
		case 626:
		case 627:
			itemDef.name = "@or2@Epic boots";
			itemDef.description = "They're like Fire capes.. On my feet!".getBytes();
		break;
		
		case 15018:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -15;
			itemDef.modelZoom = 620;
			itemDef.modelRotation2 = 1940;
			itemDef.modelRotation1 = 340;
			itemDef.modelID = 9932;
			itemDef.name = "@or2@Seers' ring (i)";
			itemDef.description = "A mysterious ring that has been imbued.".getBytes();
		break;
		case 15019:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -15;
			itemDef.modelZoom = 630;
			itemDef.modelRotation2 = 1904;
			itemDef.modelRotation1 = 332;
			itemDef.modelID = 9930;
			itemDef.name = "@or2@Archers' ring (i)";
			itemDef.description = "Improves the wearer's skill with a bow. This has been imbued.".getBytes();
		break;
		case 15020:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -7;
			itemDef.modelZoom = 570;
			itemDef.modelRotation2 = 1776;
			itemDef.modelRotation1 = 348;
			itemDef.modelID = 9933;
			itemDef.name = "@or2@Warrior ring (i)";
			itemDef.description = "A legendary ring once worn by Fremennik warriors. This has been imbued.".getBytes();
		break;
		case 15220:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -15;
			itemDef.modelZoom = 600;
			itemDef.modelRotation2 = 1916;
			itemDef.modelRotation1 = 324;
			itemDef.modelID = 9931;
			itemDef.name = "@or2@Berserker ring (i)";
			itemDef.description = "A ring reputed to bring out a berserk fury in its wearer. This has been imbued".getBytes();
		break;
		case 14484:
                	itemDef.actions = new String[5];
                	itemDef.actions[1] = "Wield";
                	itemDef.modelID = 44590;
                	itemDef.maleEquip1 = 43660;//maleEquip1
                	itemDef.femaleEquip1 = 43660;//femaleEquip1
                	itemDef.modelZoom = 789;
                	itemDef.modelRotation1 = 240;
                	itemDef.modelRotation2 = 60;
                	itemDef.modelOffset1 = -1;
                	itemDef.modelOffset2 = -23;
                	itemDef.name = "@or1@Dragon claws";
                	itemDef.description = "A set of fighting claws.".getBytes();
        break;
		case 15042:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 35083;//female
			itemDef.modelOffset1 = 9;
			itemDef.modelOffset2 = 13;
			itemDef.modelZoom = 1425;
			itemDef.modelRotation2 = 1300;
			itemDef.modelRotation1 = 700;
			itemDef.maleEquip1 = 35083;
			itemDef.modelID = 35082;
			itemDef.name = "@re1@Blood rapier";
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
			itemDef.description = "A razor-sharp rapier. (It doesnt look in good condition.)".getBytes();
		break;
		
		case 15037:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 35085;//female
			itemDef.modelOffset1 = 9;
			itemDef.modelOffset2 = 13;
			itemDef.modelZoom = 1425;
			itemDef.modelRotation2 = 1300;
			itemDef.modelRotation1 = 700;
			itemDef.maleEquip1 = 35085;
			itemDef.modelID = 35084;
			itemDef.name = "@or1@Chaotic rapier";
			itemDef.aByte205 = -12;
			itemDef.aByte154 = -12;
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
			itemDef.description = "A razor-sharp rapier. (It doesnt look in good condition.)".getBytes();
		break;


		case 15038:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 35087;//female
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 1650;
			itemDef.modelRotation2 = 1300;
			itemDef.modelRotation1 = 498;
			itemDef.maleEquip1 = 35087;
			itemDef.modelID = 35086;
			itemDef.name = "@or1@Chaotic longsword";
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
			itemDef.description = "A dangerously-sharp longsword. (It doesnt look in good condition.)".getBytes();
		break;
		case 15039:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 35089;
			itemDef.maleEquip1 = 35089;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 2;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation2 = 354;
			itemDef.modelRotation1 = 498;
			itemDef.modelID = 35088;
			itemDef.name = "@or1@Chaotic maul";
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
itemDef.aByte205 = -10;            itemDef.aByte154 = -10;
			itemDef.description = "A dangerously-blunt maul. (It doesnt look in good condition.)".getBytes();
		break;
		case 15040:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.femaleEquip1 = 35091;//female
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 0;
			itemDef.modelZoom = 1711;
			itemDef.modelRotation2 = 365;
			itemDef.modelRotation1 = 350;
			itemDef.maleEquip1 = 35091;
			itemDef.modelID = 35090;
			itemDef.name = "@or1@Chaotic staff";
			itemDef.aByte205 = -10;
			itemDef.aByte154 = -10;
			itemDef.anInt188 = -1;//female sleeve
			itemDef.anInt164 = -1;//male sleeve
			itemDef.description = "A staff used by the greatest of wizards. (It doesnt look in good condition.)".getBytes();
		break;

				
			
				
				
			}
		/* Hardcoded items start here */
		/**/
		return itemDef;
	}

	public void actionData(int a, String b) {
		actions = new String[5];
		actions[a] = b;
	}
	
	public void totalColors(int total) {
	   originalModelColors = new int[total];	   
	   modifiedModelColors = new int[total];
	}
	
	public void colors(int id, int original, int modified) {
		originalModelColors[id] = original;
		modifiedModelColors[id] = modified;
	}
	public void itemData(String n, String d) {
		name = n;
		description = d.getBytes();
	}
	public void models(int mID, int mE, int fE, int mE2, int fE2) {
		modelID = mID;
		maleEquip1 = mE;
		femaleEquip1 = fE;
		maleEquip2 = mE2;
		femaleEquip2 = fE2;
	}
	public void modelData(int mZ, int mR1, int mR2, int mO1, int mO2) {
		modelZoom = mZ;
		modelRotation1 = mR1;
		modelRotation2 = mR2;
		modelOffset1 = mO1;
		modelOffset2 = mO2;
	}

	public void toNote() {
		ItemDef itemDef = forID(certTemplateID);
		modelID = itemDef.modelID;
		modelZoom = itemDef.modelZoom;
		modelRotation1 = itemDef.modelRotation1;
		modelRotation2 = itemDef.modelRotation2;
		anInt204 = itemDef.anInt204;
		modelOffset1 = itemDef.modelOffset1;
		modelOffset2 = itemDef.modelOffset2;
		originalModelColors = itemDef.originalModelColors;
		modifiedModelColors = itemDef.modifiedModelColors;
		ItemDef itemDef_1 = forID(certID);
		name = itemDef_1.name;
		membersObject = itemDef_1.membersObject;
		value = itemDef_1.value;
		String s = "a";
		char c = itemDef_1.name.charAt(0);
		if(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			s = "an";
		description = ("Swap this note at any bank for " + s + " " + itemDef_1.name + ".").getBytes();
		stackable = true;
	}

	public static Sprite getSprite(int i, int j, int k) {
		if(k == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);
			if(sprite != null && sprite.anInt1445 != j && sprite.anInt1445 != -1) {
				sprite.unlink();
				sprite = null;
			}
			if(sprite != null)
				return sprite;
		}
		ItemDef itemDef = forID(i);
		if(itemDef.stackIDs == null)
			j = -1;
		if(j > 1) {
			int i1 = -1;
			for(int j1 = 0; j1 < 10; j1++)
				if(j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];
			if(i1 != -1)
				itemDef = forID(i1);
		}
		Model model = itemDef.method201(1);
		if(model == null)
			return null;
		Sprite sprite = null;
		if(itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if(sprite == null)
				return null;
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Texture.textureInt1;
		int l1 = Texture.textureInt2;
		int ai[] = Texture.anIntArray1472;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Texture.aBoolean1464 = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Texture.method364();
		int k3 = itemDef.modelZoom;
		if(k == -1)
			k3 = (int)((double)k3 * 1.5D);
		if(k > 0)
			k3 = (int)((double)k3 * 1.04D);
		int l3 = Texture.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Texture.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.anInt204, itemDef.modelRotation1, itemDef.modelOffset1, l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		for(int i5 = 31; i5 >= 0; i5--) {
			for(int j4 = 31; j4 >= 0; j4--)
				if(sprite2.myPixels[i5 + j4 * 32] == 0)
					if(i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if(j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if(i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if(j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
		}
		if(k > 0) {
			for(int j5 = 31; j5 >= 0; j5--) {
				for(int k4 = 31; k4 >= 0; k4--)
					if(sprite2.myPixels[j5 + k4 * 32] == 0)
						if(j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if(k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if(j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if(k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
			}
		} else if(k == 0) {
			for(int k5 = 31; k5 >= 0; k5--) {
				for(int l4 = 31; l4 >= 0; l4--)
					if(sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
			}
		}
		if(itemDef.certTemplateID != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if(k == 0)
			mruNodes1.removeFromCache(sprite2, i);
		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Texture.textureInt1 = k1;
		Texture.textureInt2 = l1;
		Texture.anIntArray1472 = ai;
		Texture.aBoolean1464 = true;
		if(itemDef.stackable)
			sprite2.anInt1444 = 33;
		else
			sprite2.anInt1444 = 32;
		sprite2.anInt1445 = j;
		return sprite2;
	}

	public Model method201(int i) {
		if(stackIDs != null && i > 1) {
			int j = -1;
			for(int k = 0; k < 10; k++)
				if(i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];
			if(j != -1)
				return forID(j).method201(1);
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		if(model != null)
			return model;
		model = Model.method462(modelID);
		if(model == null)
			return null;
		if(anInt167 != 128 || anInt192 != 128 || anInt191 != 128)
			model.method478(anInt167, anInt191, anInt192);
		if (originalModelColors != null) {
			for (int l = 0; l < originalModelColors.length; l++)
				model.method476(originalModelColors[l], modifiedModelColors[l]);

		}
		model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.aBoolean1659 = true;
		mruNodes2.removeFromCache(model, id);
		return model;
	}

	public Model method202(int i) {
		if(stackIDs != null && i > 1) {
			int j = -1;
			for(int k = 0; k < 10; k++)
				if(i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];
			if(j != -1)
				return forID(j).method202(1);
		}
		Model model = Model.method462(modelID);
		if(model == null)
			return null;
		if (originalModelColors != null) {
			for (int l = 0; l < originalModelColors.length; l++)
				model.method476(originalModelColors[l], modifiedModelColors[l]);

		}
		return model;
	}

	public void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if(i == 0)
				return;
			if(i == 1)
				modelID = stream.readUnsignedWord();
			else if(i == 2)
				name = stream.readString();
			else if(i == 3)
				description = stream.readBytes();
			else if(i == 4)
				modelZoom = stream.readUnsignedWord();
			else if(i == 5)
				modelRotation1 = stream.readUnsignedWord();
			else if(i == 6)
				modelRotation2 = stream.readUnsignedWord();
			else if(i == 7) {
				modelOffset1 = stream.readUnsignedWord();
				if(modelOffset1 > 32767)
					modelOffset1 -= 0x10000;
			} else if(i == 8) {
				modelOffset2 = stream.readUnsignedWord();
				if(modelOffset2 > 32767)
					modelOffset2 -= 0x10000;
			} else if(i == 10)
				stream.readUnsignedWord();
			else if(i == 11)
				stackable = true;
			else if(i == 12)
				value = stream.readDWord();
			else if(i == 16)
				membersObject = true;
			else if(i == 23) {
				maleEquip1 = stream.readUnsignedWord();
				aByte205 = stream.readSignedByte();
			} else if(i == 24)
				maleEquip2 = stream.readUnsignedWord();
			else if(i == 25) {
				femaleEquip1 = stream.readUnsignedWord();
				aByte154 = stream.readSignedByte();
			} else if(i == 26)
				femaleEquip2 = stream.readUnsignedWord();
			else if(i >= 30 && i < 35) {
				if(groundActions == null)
					groundActions = new String[5];
				groundActions[i - 30] = stream.readString();
				if(groundActions[i - 30].equalsIgnoreCase("hidden"))
					groundActions[i - 30] = null;
			} else if(i >= 35 && i < 40) {
				if(actions == null)
					actions = new String[5];
				actions[i - 35] = stream.readString();
			} else if(i == 40) {
				int j = stream.readUnsignedByte();
				originalModelColors = new int[j];
				modifiedModelColors = new int[j];
				for(int k = 0; k < j; k++) {
					originalModelColors[k] = stream.readUnsignedWord();
					modifiedModelColors[k] = stream.readUnsignedWord();
				}
			} else if(i == 78)
				anInt185 = stream.readUnsignedWord();
			else if(i == 79)
				anInt162 = stream.readUnsignedWord();
			else if(i == 90)
				anInt175 = stream.readUnsignedWord();
			else if(i == 91)
				anInt197 = stream.readUnsignedWord();
			else if(i == 92)
				anInt166 = stream.readUnsignedWord();
			else if(i == 93)
				anInt173 = stream.readUnsignedWord();
			else if(i == 95)
				anInt204 = stream.readUnsignedWord();
			else if(i == 97)
				certID = stream.readUnsignedWord();
			else if(i == 98)
				certTemplateID = stream.readUnsignedWord();
			else if(i >= 100 && i < 110) {
				if(stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}
				stackIDs[i - 100] = stream.readUnsignedWord();
				stackAmounts[i - 100] = stream.readUnsignedWord();
			} else if(i == 110)
				anInt167 = stream.readUnsignedWord();
			else if(i == 111)
				anInt192 = stream.readUnsignedWord();
			else if(i == 112)
				anInt191 = stream.readUnsignedWord();
			else if(i == 113)
				anInt196 = stream.readSignedByte();
			else if(i == 114)
				anInt184 = stream.readSignedByte() * 5;
			else if(i == 115)
				team = stream.readUnsignedByte();
		} while(true);
	}

	public ItemDef() {
		id = -1;
	}

	public byte aByte154;
	public int value;
	public int[] modifiedModelColors;
	public int id;
	static MRUNodes mruNodes1 = new MRUNodes(100);
	public static MRUNodes mruNodes2 = new MRUNodes(50);
	public int[] originalModelColors;
	public boolean membersObject;
	public int anInt162;
	public int certTemplateID;
	public int femaleEquip2;
	public int maleEquip1;
	public int anInt166;
	public int anInt167;
	public String groundActions[];
	public int modelOffset1;
	public String name;
	public static ItemDef[] cache;
	public int anInt173;
	public int modelID;
	public int anInt175;
	public boolean stackable;
	public byte description[];
	public int certID;
	public static int cacheIndex;
	public int modelZoom;
	public static boolean isMembers = true;
	public static Stream stream;
	public int anInt184;
	public int anInt185;
	public int maleEquip2;
	public String actions[];
	public int modelRotation1;
	public int anInt191;
	public int anInt192;
	public int[] stackIDs;
	public int modelOffset2;
	public static int[] streamIndices;
	public int anInt196;
	public int anInt197;
	public int modelRotation2;
	public int femaleEquip1;
	public int[] stackAmounts;
	public int team;
	public static int totalItems;
	public int anInt204;
	public byte aByte205;
	public int anInt164;
	public int anInt199;
	public int anInt188;
}