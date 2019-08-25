package server.model.shops;

import server.Config;
import server.Server;
import server.model.items.Item;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import server.world.ShopHandler;

public class ShopAssistant {

	private Client c;

	public ShopAssistant(Client client) {
		this.c = client;
	}

	/**
	*Shops
	**/

	public void updatePlayerShop() {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping == true
						&& PlayerHandler.players[i].myShopId == c.myShopId
						&& i != c.playerId) {
					Client sh = (Client) PlayerHandler.players[i];
					//sh.getShops().openShop(sh.myShopId);
					PlayerHandler.players[i].updateShop = true;
				}
			}
		}
	}

	public void openShop(int ShopID){
		c.getItems().resetItems(3823);
		resetShop(ShopID);
		c.isShopping = true;
		c.myShopId = ShopID;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126(Server.shopHandler.ShopName[ShopID], 3901);
	}



	public void updateshop(int i){
		resetShop(i);
	}




	public void resetShop(int ShopID) {
		//synchronized(c) {
			int TotalItems = 0;
			for (int i = 0; i < Server.shopHandler.MaxShopItems; i++) {
				if (Server.shopHandler.ShopItems[ShopID][i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > Server.shopHandler.MaxShopItems) {
				TotalItems = Server.shopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			for (int i = 0; i < Server.shopHandler.ShopItems.length; i++) {
				if (Server.shopHandler.ShopItems[ShopID][i] > 0 || i <= Server.shopHandler.ShopItemsStandard[ShopID]) {
					if (Server.shopHandler.ShopItemsN[ShopID][i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(Server.shopHandler.ShopItemsN[ShopID][i]);
					} else {
						c.getOutStream().writeByte(Server.shopHandler.ShopItemsN[ShopID][i]);
					}
					if (Server.shopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT || Server.shopHandler.ShopItems[ShopID][i] < 0) {
						Server.shopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
					}
					c.getOutStream().writeWordBigEndianA(Server.shopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		//}
	}


	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		double ShopValue = 1;
		double Overstock = 0;
		double TotPrice = 0;

		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					ShopValue = Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}

		TotPrice = ShopValue;

		if (Server.shopHandler.ShopBModifier[c.myShopId] == 1) {
			TotPrice *= 1;
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1;
			}
		} else if (Type == 1) {
			TotPrice *= 1;
		}
		return TotPrice;
	}

	public int getItemShopValue(int itemId) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == itemId) {
					return (int)Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		return 0;
	}



	/**
	*buy item from shop (Shop Price)
	**/

	public void buyFromShopPrice(int removeId, int removeSlot){
		int ShopValue = (int)Math.floor(getItemShopValue(removeId, 0, removeSlot));
		ShopValue *= 1;
		String ShopAdd = "";
		if (c.myShopId == 29) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue(removeId) + " PK Points.");
			return;
		}

		if (c.myShopId == 18 || c.myShopId == 32) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue7(removeId) + " PK Points.");
			return;
		}

		if (c.myShopId == 22) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue16(removeId) + " Slayer points.");
			if(removeId == 970)
				c.sendMessage("@blu@You will receive 25 Papyrus for 1 Slayer Point. 1 Papyrus will note 1 item, anywhere.");
			return;
		}

		if (c.myShopId == 16) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue22(removeId) + " Pandemonium points.");
			return;
		}
		if (c.myShopId == 6) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue20(removeId) + " Event points.");
			return;
		}
		if (c.myShopId == 37) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue(removeId) + " Target Points.");
			if(removeId == 995)
				c.sendMessage("@blu@You will receive 2.5M Coins for 1 Target Point.");
			return;
		}
		if (c.myShopId == 40) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue5(removeId) + " Last Man Standing points.");
			return;
		}
		if (c.myShopId == 42) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue10(removeId) + " Vote points.");
			if(removeId == 970)
				c.sendMessage("@blu@You will receive 50 Papyrus for 50 Vote Points. 1 Papyrus will note 1 item, anywhere.");
			return;
		}
		if (c.myShopId == 44) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue11(removeId) + " mage kill points.");
			return;
		}
		if (c.myShopId == 45) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue12(removeId) + " ranged kill points.");
			return;
		}
		if (c.myShopId == 46) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue13(removeId) + " melee kill points.");
			return;
		}
		if (c.myShopId == 49 || c.myShopId == 50 || c.myShopId == 51 || c.myShopId == 52 || c.myShopId == 39) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue14(removeId) + " coins.");
			return;
		}
		if (c.myShopId == 43) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue10(removeId) + " Vote points.");
			return;
		}
		if (c.myShopId == 33) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue3(removeId) + " Gear Points.");
			return;
		}

		if (c.myShopId == 41) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue6(removeId) + " ForeverPkers Points.");
			return;
		}

		if (c.myShopId == 54) {
			c.sendMessage("You need a slayer level of " + getSpecialItemValue15(removeId) + " to buy "+c.getItems().getItemName(removeId)+"");
			return;
		}

		if (c.myShopId == 17) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue3(removeId) + " Mage Points.");
			return;
		}

		if (c.myShopId == 31) {
			c.sendMessage(c.getItems().getItemName(removeId)+"@bla@ costs " + getSpecialItemValue2(removeId) + " Donator Tickets.");
			if(removeId == 970)
				c.sendMessage("@blu@You will receive 100 Papyrus per Ticket. 1 Papyrus will note 1 item, anywhere.");
			else if(removeId == 405)
				c.sendMessage("@blu@Contains 1-2 random Donator shop items and 10 random uncommon items!");
			return;
		}

		if (c.myShopId == 15) {
			c.sendMessage("c.getItems().getItemName(removeId)+"+ c.getItems().getUntradePrice(removeId) + " coins.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(c.getItems().getItemName(removeId)+" costs@bla@ "+ShopValue+" coins"+ShopAdd);
	}

		public int getSpecialItemValue6(int id) {//ForeverPkers Points for voting, donating, staying online, all that shit
		switch (id) {
		case 4151://whip
			return 500;
		case 11694://ags!
			return 25000;
		case 14484://dclaws!
			return 25000;
		case 773://perfect ring
			return 10000;
		}
		return 100000;
		}
		public int getSpecialItemValue15(int id) {//Slayer level req
		switch (id) {
		case 4170:
			return 90;
		}
		return 90;
		}
		public int getSpecialItemValue22(int id) {//Pandemonium points
			switch (id) {
				case 4716://dharok
				case 4718:
				case 4720:
				case 4722:
					return 7500;
				case 4708://ahrim
					return 5000;
				case 4710:
				case 4712:
				case 4714:
					return 7500;
				case 4724:
				case 4726:
				case 4728:
				case 4730:
					return 5000;
				case 4732:
				case 4734:
				case 4736:
				case 4738:
					return 5000;
				case 4745:
				case 4747:
				case 4749:
				case 4751:
					return 5000;
				case 4753:
				case 4755:
				case 4757:
				case 4759:
					return 5000;
				case 7461:
					return 7500;
				case 7462:
					return 75000;
				case 8850:
					return 7500;
				case 20072:
					return 25000;
				case 11732:
					return 12500;
				case 4151:
					return 15000;
				case 4153:
					return 50000;
				case 11730:
					return 75000;
				case 6731:
				case 6733:
					return 7500;
				case 6737:
					return 10000;
				case 15018:
				case 15019:
					return 45000;
				case 15220:
					return 75000;
				case 85:
					return 125000;
				case 19111:
					return 500000;
			}
		return 1000000;
		}
		public int getSpecialItemValue16(int id) {//Slayer points, task points
		switch (id) {
		case 970:
			return 1;
		case 4155://enchanted gem
			return 1;
		case 85:
			return 25;
		case 7461:
			return 3;
		case 7462://barrows gloves
			return 25;
		case 2572://ring of wealth
			return 25;
		case 4447://drop lamp
		case 2528://xp lamp
			return 25;
		case 15037:
		case 15038:
		case 15039:
		case 15040://chaotics
		case 15041:
			return 75;
		case 11728://heroics
		case 10362:
			return 50;
		}
		return 1000;
		}

		public int getSpecialItemValue7(int id) {//PK Points
		switch (id) {
			case 6585:
				return 125;
			case 626:
				return 750;
			case 11694: //ags
			return 7500;
			case 11696://bgs
			return 500;
			case 11698://sgs
			return 500;
			case 11700://zgs
			return 500;
			case 11235://dbow
				return 150;
			case 11720://arma plate
				return 1250;
			case 11722://arma skirt
				return 1250;
			case 11718://arma helm
				return 500;
		case 11730://sara sword
			return 1000;
		case 4153://gmaul
			return 500;
		case 15001://staff of light
			return 500;
		case 15000://arcane stream
		case 19114://occult
			return 500;
		case 15126://amulet of ranging
			return 500;
		case 11732:
			return 60;
		case 10828:
			return 75;

		case 13858:
			return 7500;
		case 15220: //zerker ring i
			return 750;
		case 15020: //other rings
		case 15019:
		case 15018:
			return 500;
		case 11090:
			return 10;
		case 4151://whip
			return 125;
		case 7462: //b gloves
			return 600;
		case 7461: //d gloves
			return 250;
		case 6731://seers ring
			return 175;
		case 6733://archers ring
			return 175;
		case 6735://warrior ring
			return 175;
		case 6737://berserker ring
			return 250;
		case 13899:
			return 5000;
		case 13902:
			return 5000;
		case 14484:
			return 2500;
		case 6570:
			return 250;
		case 19111:
			return 25000;
		case 13896:
		   return 5000;
		case 13887:
			return 17500;
		case 13893:
			return 17500;
		case 13870:
			return 7500;
		case 13873:
			return 7500;
		case 10551://torso
			return 450;
		case 10548://fighter hat
			return 225;
		case 13876:
			return 4000;
		case 13884:
			return 12500;
		case 3748:
			return 5000;
		case 13890:
			return 12500;
		case 13864:
			return 4000;
		case 13861:
			return 7500;
		case 15041:
			return 5000;
		case 11284: //dragonfireshield
			return 250;
		case 13399:
			return 9999;
		case 10926:
			return 45;
		case 20072://d boots
			return 275;
		case 4716: //dh head
			return 45;
		case 4720: //dh bod
			return 45;
		case 4722: //dh leg
			return 45;
		case 4718: //dh axe
			return 55;
		case 4745: //torag helm
			return 30;
		case 4749: //torag plate
			return 30;
		case 4751: //torage legs
			return 30;
		case 4747: //torag hammers
			return 30;
		case 4724: //guthan's helm
			return 50;
		case 4728: //guthan's plate
			return 50;
		case 4730 : //guthan's legs
			return 50;
		case 4726: //guthan's spear
			return 50;
		case 4708: //ahrim hood
			return 50;
		case 4712: //ahrim top
			return 75;
		case 4714: //ahrim legs
			return 75;
		case 4710: //ahrim staff
			return 35;
		case 4732: //karil's helm
			return 35;
		case 4736: //karil's top
			return 65;
		case 4738: //karils bottom
			return 65;
		case 4734: //karils xbow
			return 75;
		case 4740: //boltrack
			return 1;
		case 4753: //verac helm
			return 50;
		case 4757: //verac bod
			return 50;
		case 4759: //verac leg
			return 50;
		case 4755: //verac flail
			return 50;
		case 11724: //bandos chest
			return 1500;
		case 11726: //bandos tassets
			return 1250;
		case 11665: //mele helm
			return 500;
		case 11664: //range helm
			return 500;
		case 11663: //mage helm
			return 500;
		case 8839: //robe top
			return 750;
		case 8840: //robe bottom
			return 750;
		case 8842: //void gloves
			return 275;
		case 4212: //crystal bow
			return 2500;
		case 13022: //hand cannon
			return 3500;
		case 13740: //divine shield
			return 4000;
		case 13738: //arcane shield
			return 2500;
		case 13742: //ely shield
			return 3500;
		case 13744: //spectral shield
			return 1250;
		case 16018: //lava whip
			return 2250;
		case 22494: //poly
			return 1500;
		case 1547: //magic key
			return 2500;
		case 85: //shiny key
			return 1000;

		case 8921:
			return 1250;
		case 13263:
			return 5000;
		case 15060:
			return 2500;
		//case : //
			//return 100;
		}
		return 10000;
		}

	public int getSpecialItemValue(int id) {//Target points
		switch (id) {
		case 19114:
			return 5;
		case 995:
			return 1;
		case 3757:
			return 225;
		case 15126:
			return 5;
		case 19111: //tokhaar
			return 25;
		case 3758:
			return 60;
		case 20072:
			return 10;
			case 6570:
			return 1;
		case 14484:
			return 50;
		case 13399:
			return 250;
		case 11091:
		case 11090:
			return 3;
		case 11235://dark bow
			return 25;
		case 10887://anchor
			return 175;
		case 4212:
			return 75;
		case 4151://whip
			return 1;
		case 15040://c staff
			return 50;
		case 15037://chaotics
		case 15038:
		case 15039:
		case 15041:
			return 75;
		case 13738:
			return 30;
		case 13744:
			return 20;
		case 13740://divine
			return 250;
		case 13742://ely
			return 50;
		case 4153://maul
			return 5;
		case 11128:	//beserker neck
			return 5;
		case 11694:
			return 75;
		case 11696:
			return 35;
		case 11698:
			return 35;
		case 11700:
			return 35;
		}
		return 999;
	}

	public int getSpecialItemValue2(int id) {//DONATOR TICKETS
		switch (id) {

		case 19111:
			return 50;

		case 3481:
		case 3483:
		case 3485:
		case 3486:
		case 3488:
			return 25;

		case 10362:
		case 11728:
			return 5;
		case 11724:
			return 15;
		case 11726:
			return 15;
		//case 4212:
		//case 4224:
			//return 10;
		case 13290:
			return 50;
		case 8921:
			return 30;
		case 2572:
			return 10;
		case 15998://gilded whip
			return 30;
		case 16018://lava whip
			return 100;
		case 6542://gift
			return 50;
		case 405://casket
			return 10;
		case 10887://anchor
			return 35;
		case 11235:
			return 5;
		case 10330:
			return 10;
		case 10332:
			return 10;
		case 2383:
			return 15;
		case 4067:
			return 1;
		case 19780:
			return 150;
		case 13022:
			return 25;
		case 22494:
			return 25;
		case 13896:
			return 5;

			case 13870:
			case 13873:
			case 13876:
			return 5;

			case 13858:
			case 13861:
			case 13864:
			return 5;


		case 15009://zuriel
		case 15011:
		case 15008:
		return 10;

		case 13399://primal
		case 15574:
		case 15662:
		return 25;

		case 13887://morrigan
		case 13893:
		case 13884:
		case 13890:
		return 10;

		case 13899://vls
		return 25;
		case 13902:
		return 10;
		case 10548:
			return 5;
		case 10551:
			return 5;
		case 20072:
			return 8;

		case 15001://sol
		return 3;
		case 19114:
		case 15000://arcane stream
		return 10;
		case 15126: //amulet of ranging
		return 10;
		case 14484: //dragonclaws
		return 25;
		case 970:
		return 1;
		case 11694: //ags
		return 75;
		case 11696://bgs
		return 10;
		case 11698://sgs
		return 10;
		case 11700://zgs
		return 10;

		case 4151:
		return 1;

		case 626:
		return 1;

		case 11284://dfs
		return 10;

		case 11820:
		return 10;
		case 11821:
		return 25;
		case 11822:
		return 15;

		case 15018:
		return 5;
		case 15019:
		return 5;
		case 15020:
		return 5;
		case 15220:
		return 10;
		case 15017:
		return 5;
		case 4084:
		return 35;

		case 15005:
		return 5;
		case 15004:
		return 5;

		case 11732:
			return 5;
		case 7462:
			return 8;


		case 1037:
		case 1038://red phat
		case 1040://yellow partyha
		case 1042: //blue
		case 1044://green partyhat
		case 1046://purple partyhat phat
		case 1048: //white
			return 150;
		case 6666:
			return 25;
		case 1949:
			return 40000000;
		case 747:
			return 1337;

		case 1050://santa
			return 75;
		case 1053://masks
		case 1055:
		case 1057:
			return 50;
		case 1547: //magic key
			return 35;
		case 85: //shiny key
			return 10;

		case 15037: // chaotics
		case 15038:
		case 15039:
		case 15041:
		case 15040:
			return 50;
		case 773: // p ring
			return 50;
		case 2415:
		case 2416:
		case 2417:
			return 50;
		case 4212:
			return 50;

			}
		return 10000;
	}
	public int getSpecialItemValue3(int id) {//FREE POINTS
		switch (id) {

		case 1323:
		return 25;
		case 7458: //mith glove
		return 30;
		case 7459: //addy glove
		return 35;
		case 7460: //rune glove
		return 40;
		case 9185://rune crossbow
		return 75;
		case 9244://d bolts e
		return 25;
		case 10499:
		return 25;
		case 9243://diamond bolts e
		return 20;
		case 7461: //d glove
		return 50;
		case 1305: //d long
		return 100;
		case 4587: //d scim
		return 100;
		case 1434: //dmace
		return 100;
		case 1215: //dd
		return 20;
		case 5698: //dds
		return 25;
		case 2550: //recoil ring
		return 10;
		case 1127: //r00n pl8
		return 100;
		case 1079: //r00n legs
		return 75;
		case 1163: //r00n helm
		return 40;
		case 1201: //r00n kite
		return 50;
		case 4131: //r00b b000tz dickbutt
		return 90;
		case 1725: //str ammy
		return 25;
		case 1712: //glory ammy
		return 35;
		case 3105: //climbing boots
		return 50;
		case 2497: //dhide chaps
		return 35;
		case 2491: //dhide vambs
		return 25;
		case 2503: //dhide body
		return 50;
		case 861: // mage bow
			return 50;
		case 892: // rune arrows
			return 10;
		case 4089: //Blue mystic hat
		return 40;
		case 4091: //Blue myst top
		return 90;
		case 4093: //Blue myst bott
		return 70;
		case 10828: //nezzy
		return 60;
		case 5574:
		return 25;
		case 5575:
		return 25;
		case 5576:
		return 25;
		case 3122:
		return 75;
		case 1275: // Strength amulet
			return 25;
		case 1731: // Power amulet
			return 30;




			}
			return 100;
	}
	public int getSpecialItemValue4(int id) {//MAGE POINTS
		switch (id) {
			case 6916: //infinity top
			return 50;
			case 6918: //infinity hat
			return 30;
			case 6920: //infinity boots
			return 20;
			case 6922: //infinity gloves
			return 15;
			case 6924: //infinity robes
			return 40;
			case 6889: //magebook
			return 45;
			case 6914: //masterwand
			return 60;




			}
			return 100;
	}
	public int getSpecialItemValue5(int id) {//LMS POINTS
		switch (id) {
			case 15037: //chaotic rapier
			case 15038: //chaotic long
			case 15039: //chaotic maul
			case 15040: //chaotic staff
			case 7053: //lit bug lantern
			case 4037://banner
			case 4039://banner
			return 1;

			}
			return 5;
	}

	public int getSpecialItemValue11(int id) {//mage kill points
		switch (id) {
			case 11663:
				return 50;
			case 8839:
				return 100;
			case 8840:
				return 100;
			case 8842:
				return 80;
		}
		return 5;
	}
	public int getSpecialItemValue12(int id) {//range kill points
		switch (id) {
			case 11664:
				return 50;
			case 8839:
				return 100;
			case 8840:
				return 100;
			case 8842:
				return 80;
		}
		return 5;
	}
	public int getSpecialItemValue13(int id) {//melee kill points
		switch (id) {
			case 11665:
				return 50;
			case 8839:
				return 100;
			case 8840:
				return 100;
			case 8842:
				return 80;
		}
		return 5;
	}

	public int getSpecialItemValue14(int id) {//cash??
		switch (id) {
			case 11714://godsword shard 3
				return 50000000;
			case 10370://guthix zamorak saradomin chaps body
			case 10372:
			case 10378:
			case 10380:
			case 10386:
			case 10388:
				return 11000000;
			case 4447:
			case 2528:
				return 250000000;
			case 13899:
					return 1500000000;
			case 13902:
					return 1100000000;
			case 13887:
					return 750000000;
			case 13893:
					return 750000000;
			case 13896:
					return 575000000;
			case 13884:
					return 650000000;
			case 13890:
					return 650000000;
			case 13864:
					return 475000000;
			case 13858:
					return 650000000;
			case 13861:
					return 650000000;
			case 13876:
					return 475000000;
			case 13870:
					return 650000000;
			case 13873:
					return 650000000;
		}
		return 2000000000;
	}
	public int getSpecialItemValue20(int id) {//Event points
		switch (id) {
			case 13290: //leaf bladed sword
				return 6000;
			case 19111://tokhaar
				return 800;
			case 773://pring
				return 800;
			case 19780://korasi
				return 10000;
			case 13022://hc
				return 5000;
			case 22494://poly
				return 1250;
			case 970://papyrus
				return 5;
			case 995:
				return 10;
			case 6542: //present
				return 500;
			case 2572: //row
				return 150;
			case 3757: //fremmy blade
				return 3000;
			case 16018: // Lava whip
				return 2750;
			case 15998: // gilded whip
				return 1000;
			case 85://shiny key
				return 350;
			case 1547://magic key
				return 750;
			case 4212://cbow
				return 1500;
			case 11694: //ags
				return 1500;
			case 14484: //claws
				return 750;
			case 11284: //dfs
				return 500;
			case 3748: //fremmy helm
				return 750;
			case 3758: //fremmy shield
				return 750;
			case 13744: //spectral shield
				return 600;
			case 13738: //arcane shield
				return 750;
			case 13742: //ely shield
				return 1000;
			case 13740: //divine
				return 5000;
			case 4224://cshield
				return 700;
			case 13899://vls
					return 600;
			case 13902://warham
					return 450;
			case 13887://vesta body
					return 850;
			case 13893: //vesta legs
					return 850;
			case 13896://statius helm
					return 500;
			case 13884: //stat body
					return 550;
			case 13890: //stat legs
					return 500;
			case 13864://zuriels hood
					return 350;
			case 13858: //zuriels top
					return 500;
			case 13861: //zuriels bottom
					return 450;
			case 13876://morg coif
					return 350;
			case 13870: //morg body
					return 500;
			case 13873: //morg chaps
					return 450;
			case 7462://b gloves
				return 150;
			case 10362://hero amulet
			case 11728://hero boots
				return 200;
			case 13045:
				return 2250;
			case 13047:
				return 3750;
			case 2415:
			case 2416:
			case 2417:
				return 1000;
		}
		return 10000;
	}

	public int getSpecialItemValue10(int id) {//Vote points
		switch (id) {
		case 6570:
			return 25;
		case 3486: //gilded set
			return 125;
		case 3481:
			return 250;
		case 3483:
		case 3485:
			return 175;
		case 3488:
			return 150;
		case 4151:
			return 25;
		case 11128:
			return 75;
		case 13290:
			return 1750;
		case 8850:
			return 20;
		case 19114:
		case 15000:
			return 125;
		case 11730:
			return 300;
		case 4153:
			return 50;
		case 15998: //gilded whip
			return 400;
		case 4084: //sled
			return 1000;
		case 970:
			return 50;
		case 6542:
		case 15037:
		case 15038:
		case 15039:
		case 15040:
		case 15041:
			return 500;
		case 3757:
			return 1500;
			case 6180:
			return 18;
			case 6181:
			return 18;
			case 6182:
			return 18;
			case 19780:
			return 1000;
			case 15049:
			return 300;
			case 20072:
			return 75;
			case 19111: //tokhaar
			return 750;
			case 15126:
			return 100;
			case 773: //p ring
			return 550;
			case 11090: //p neck
			return 25;
			case 10926:
			return 15;
			case 2572:
				return 200;
			case 775:
			return 250;
			case 1837:
			return 25;
			case 2637:
			return 50;
			case 2635:
			return 50;
			case 2633:
			return 50;
			case 2460:
			return 15;
			case 2462:
			return 15;
			case 2464:
			return 15;
			case 2466:
			return 15;
			case 2468:
			return 15;
			case 2470:
			return 15;
			case 2472:
			return 15;
			case 2474:
			return 15;
			case 2476:
			return 15;
			case 1419:
			return 100;
			case 2639:
			return 10;
			case 2641:
			return 10;
			case 2643:
			return 10;
			case 2922:
			return 10;
			case 2914:
			return 10;
			case 6666:
			return 50;
			case 6665:
			return 35;
			case 7534:
			return 40;

			case 4566:
			return 100;
			case 1037:
			return 50;
			case 6856:
			case 6857:
			case 6858:
			case 6859:
			case 6862:
			case 6863:
			case 6654:
			case 6655:
			case 6656:
			case 1949:
			return 10;
			case 7594:
			return 25;
			case 7668:
			return 100;
			case 2997:
			return 100;

			case 16018:
				return 750;

			}
			return 1000;
	}

	/**
	*Sell item to shop (Shop Price)
	**/
	public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Config.ITEM_SELLABLE) {
			if (i == removeId) {
				c.sendMessage("You can't sell "+c.getItems().getItemName(removeId).toLowerCase()+".");
				return;
			}
		}
		if(c.inTrade) {
			c.sendMessage("You can't sell items while trading!");
			return;
		}
		boolean IsIn = false;
		if (Server.shopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= Server.shopHandler.ShopItemsStandard[c.myShopId]; j++) {
				//if (removeId == (Server.shopHandler.ShopItems[c.myShopId][j] - 1) || removeId == (Server.shopHandler.ShopItems[c.myShopId][j]) || removeId == (Server.shopHandler.ShopItems[c.myShopId][j] + 1)) {
					//c.sendMessage(c.getItems().getItemName(removeId)+""+c.getItems().getItemName(Server.shopHandler.ShopItems[c.myShopId][j] - 1));
					if(c.getItems().getItemName(removeId).equals(c.getItems().getItemName(Server.shopHandler.ShopItems[c.myShopId][j] - 1))) {
						IsIn = true;
						break;
					}
				//}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell "+c.getItems().getItemName(removeId).toLowerCase()+" to this store.");
		} else {
			int ShopValue = (int) (Math.floor(getItemShopValue(removeId, 1, removeSlot)) * .4);
			if((removeId >= 14876 && removeId <= 14887) || removeId == 2365)
				ShopValue = (int) Math.floor(getItemShopValue(removeId, 1, removeSlot));
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			if(ShopValue >= 40000000){
					if((removeId < 14876 || removeId > 14887) && removeId != 2365) {
						c.sendMessage("This item is too expensive to sell to the shop.");
						return;
					}
				}
			c.sendMessage("The shop will buy "+c.getItems().getItemName(removeId)+" for "+ShopValue+" coins"+ShopAdd);
		}
	}


	public int theSlot;
	public boolean sellItem(int itemID, int fromSlot, int amount) {

	if(c.inWild() && (!c.safeZone() || c.safeTimer > 0)) {
			c.sendMessage("You can't do that.");
			return false;
		}
		if (c.myShopId == 14)
			return false;
		for (int i : Config.ITEM_SELLABLE) {
			if (i == itemID) {
				c.sendMessage("You can't sell "+c.getItems().getItemName(itemID).toLowerCase()+".");
				return false;
			}
		}

		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if (Server.shopHandler.ShopSModifier[c.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= Server.shopHandler.ShopItemsStandard[c.myShopId]; i++) {
					//if (itemID == (Server.shopHandler.ShopItems[c.myShopId][i] - 1) || itemID == (Server.shopHandler.ShopItems[c.myShopId][i]) || itemID == (Server.shopHandler.ShopItems[c.myShopId][i] + 1)) {
					if(c.getItems().getItemName(itemID).equals(c.getItems().getItemName(Server.shopHandler.ShopItems[c.myShopId][i] - 1))) {
						theSlot = i;
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					c.sendMessage("You can't sell "+c.getItems().getItemName(itemID).toLowerCase()+" to this store.");
					return false;
				}
			}

		/*for (int i = 0; i <= Server.shopHandler.ShopItems.length; i++) {
			if(c.getItems().getItemName(itemID).equals(c.getItems().getItemName(Server.shopHandler.ShopItems[c.myShopId][i] - 1))) {
				theSlot = i;
			}
		}*/

			if (amount > c.playerItemsN[fromSlot] && (Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == true || Item.itemStackable[(c.playerItems[fromSlot] - 1)] == true)) {
				amount = c.playerItemsN[fromSlot];
			} else if (amount > c.getItems().getItemAmount(itemID) && Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == false && Item.itemStackable[(c.playerItems[fromSlot] - 1)] == false) {
				amount = c.getItems().getItemAmount(itemID);
			}
			//double ShopValue;
			//double TotPrice;
			int TotPrice2 = 0;
			//int Overstock;
			for (int i = amount; i > 0; i--) {

				if((itemID < 14876 || itemID > 14887) && itemID != 2365) //artifacts and gold bar
					TotPrice2 = (int) (Math.floor(getItemShopValue(itemID, 1, fromSlot)) * .4);
				else
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 1, fromSlot));

				if(TotPrice2 >= 40000000){
					if((itemID < 14876 || itemID > 14887) && itemID != 2365) {
						c.sendMessage("This item is too expensive to sell to the shop.");
						break;
					}
				}
				if(c.myShopId == 33){
				c.sendMessage("You can't sell items to this shop.");
				break;
				}
				if (c.getItems().freeSlots() > 0 || c.getItems().playerHasItem(995)) {
					if (Item.itemIsNote[itemID] == false) {
						c.getItems().deleteItem(itemID, c.getItems().getItemSlot(itemID), 1);
					} else {
						c.getItems().deleteItem(itemID, fromSlot, 1);
					}
					c.getItems().addItem(995, TotPrice2);
					//addShopItem(itemID, 1);
				} else {
					c.sendMessage("You don't have enough space in your inventory.");
					break;
				}
				Server.shopHandler.ShopItemsN[c.myShopId][theSlot] += 1;
			}
			c.logSales(c.playerName+" sold "+amount+" "+c.getItems().getItemName(itemID)+" for "+TotPrice2);
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}

	public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		if (Item.itemIsNote[itemID] == true) {
			itemID = c.getItems().getUnnotedItem(itemID);
		}
		for (int i = 0; i < Server.shopHandler.ShopItems.length; i++) {
			if ((Server.shopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				Server.shopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < Server.shopHandler.ShopItems.length; i++) {
				if (Server.shopHandler.ShopItems[c.myShopId][i] == 0) {
					Server.shopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					Server.shopHandler.ShopItemsN[c.myShopId][i] = amount;
					Server.shopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}

	public boolean buyItem(int itemID, int fromSlot, int amount) {
		int myAmount = amount;
	if(c.inWild() && (!c.safeZone() || c.safeTimer > 0)) {
			c.sendMessage("You can't do that.");
			return false;
		}
		if (c.myShopId == 14) {
			skillBuy(itemID);
			return false;
		} else if (c.myShopId == 15) {
			buyVoid(itemID);
			return false;
		}
		if(c.myShopId == 30){
			if(c.memberStatus == 0){
				c.sendMessage("@blu@Only Donators can buy from this shop! Donate $10 for access.");
				c.sendMessage("@blu@Type ::donate!");
				return false;
			}
		}
		if(c.inTrade) {
			c.sendMessage("You can't buy items while trading!");
			return false;
		}
		if (amount > 0) {
			if (amount > Server.shopHandler.ShopItemsN[c.myShopId][fromSlot]) {
				amount = Server.shopHandler.ShopItemsN[c.myShopId][fromSlot];
			}
			if (Server.shopHandler.ShopItems[c.myShopId][fromSlot] != itemID+1) {
				return false;
			}

			//double ShopValue;
			//double TotPrice;
			int TotPrice2 = 0;
			//int Overstock;
			int Slot = 0;
			int Slot1 = 0;//Tokkul
			int Slot2 = 0;//Pking Points
			if (c.myShopId >= 17 && c.myShopId <= 18 || c.myShopId == 6 || c.myShopId == 22  || c.myShopId == 16 || c.myShopId == 29 || c.myShopId == 31 || c.myShopId == 32 || c.myShopId == 33 || c.myShopId == 37 || c.myShopId == 40 || c.myShopId == 41 || c.myShopId == 42 || c.myShopId == 44 || c.myShopId == 45 || c.myShopId == 46 || c.myShopId == 43 || c.myShopId == 49 || c.myShopId == 50 || c.myShopId == 51 || c.myShopId == 52 || c.myShopId == 54 || c.myShopId == 39) {
				handleOtherShop(itemID, myAmount);
				return false;
			}
			if(amount <= 100) {
				for (int i = amount; i > 0; i--) {
					TotPrice2 = (int)Math.floor(getItemShopValue(itemID, 0, fromSlot));
					Slot = c.getItems().getItemSlot(995);
					Slot1 = c.getItems().getItemSlot(6529);
					if (Slot == -1 && c.myShopId != 101 && c.myShopId != 102 && c.myShopId != 103 && c.myShopId != 104) {
						c.sendMessage("You don't have enough coins.");
						break;
					}
					if(Slot1 == -1 && c.myShopId == 100 || c.myShopId == 101 || c.myShopId == 102) {
						c.sendMessage("You don't have enough tokkul.");
						break;
					}
	                if(TotPrice2 <= 1) {
	                	TotPrice2 = (int)Math.floor(getItemShopValue(itemID, 0, fromSlot));
	                	TotPrice2 *= 1.66;
	                }
	                if(c.myShopId != 100 || c.myShopId != 101 || c.myShopId != 102 || c.myShopId != 103) {
						if (c.playerItemsN[Slot] >= TotPrice2) {
							if (c.getItems().freeSlots() > 0) {
								c.getItems().deleteItem(995, c.getItems().getItemSlot(995), TotPrice2);
								c.getItems().addItem(itemID, 1);
								Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
								Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
								Server.shopHandler.ShopItemsRestock[c.myShopId][fromSlot] = System.currentTimeMillis();
								if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
									Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
								}
							} else {
								c.sendMessage("You don't have enough space in your inventory.");
								break;
							}
						} else {
							c.sendMessage("You don't have enough coins.");
							break;
						}
	                }
	                if(c.myShopId == 101 || c.myShopId == 102 || c.myShopId == 103) {
	                	if (c.playerItemsN[Slot1] >= TotPrice2) {
							if (c.getItems().freeSlots() > 0) {
								c.getItems().deleteItem(6529, c.getItems().getItemSlot(6529), TotPrice2);
									c.getItems().addItem(itemID, 1);
								Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
								Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
								Server.shopHandler.ShopItemsRestock[c.myShopId][fromSlot] = System.currentTimeMillis();
								if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
									Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
								}
							} else {
								c.sendMessage("You don't have enough space in your inventory.");
								break;
							}
						} else {
							c.sendMessage("You don't have enough tokkul.");
							break;
						}
	                		}
				}
			} else {
					TotPrice2 = (int) (Math.floor(getItemShopValue(itemID, 0, fromSlot)) * 100);
					Slot = c.getItems().getItemSlot(995);
					Slot1 = c.getItems().getItemSlot(6529);
					if (Slot == -1 && c.myShopId != 101 && c.myShopId != 102 && c.myShopId != 103 && c.myShopId != 104) {
						c.sendMessage("You don't have enough coins.");
						//break;
					}
					if(Slot1 == -1 && c.myShopId == 100 || c.myShopId == 101 || c.myShopId == 102) {
						c.sendMessage("You don't have enough tokkul.");
						//break;
					}
	                if(TotPrice2 <= 1) {
	                	TotPrice2 = (int) (Math.floor(getItemShopValue(itemID, 0, fromSlot)) * 100);
	                	TotPrice2 *= 1.66;
	                }
	                if(c.myShopId != 100 || c.myShopId != 101 || c.myShopId != 102 || c.myShopId != 103) {
						if (c.playerItemsN[Slot] >= TotPrice2) {
							if (c.getItems().freeSlots() > 0) {
								if(Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] >= 100) {
								c.getItems().deleteItem(995, c.getItems().getItemSlot(995), TotPrice2);
								c.getItems().addItem(itemID, 100);
								Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 100;
								Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
								Server.shopHandler.ShopItemsRestock[c.myShopId][fromSlot] = System.currentTimeMillis();
									if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
										Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
									}
								} else {
									c.sendMessage("Shop does not have that much to sell you.");
									//break;
								}
							} else {
								c.sendMessage("You don't have enough space in your inventory.");
								//break;
							}
						} else {
							c.sendMessage("You don't have enough coins.");
							//break;
						}
	                }
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return false;
	}

		public void handleOtherShop(int itemID, int amount) {
			if (c.myShopId == 17) {
				if (c.magePoints >= getSpecialItemValue4(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.magePoints -= getSpecialItemValue4(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough points to buy this item.");
				}
			} else if (c.myShopId == 41) {
				if (c.fpp >= getSpecialItemValue6(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.fpp -= getSpecialItemValue6(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough ForeverPkers Points to buy this item.");
				}
			} else if (c.myShopId == 54) {
				if (c.playerLevel[18] >= getSpecialItemValue15(itemID)) {
					if (c.getItems().freeSlots() > 0){
						//c.fpp -= getSpecialItemValue6(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have high enough Slayer level to get this item.");
				}
			} else if (c.myShopId == 40) {
				if (c.lmsPoints >= getSpecialItemValue5(itemID)) {
					if (c.getItems().freeSlots() > 0) {
						c.lmsPoints -= getSpecialItemValue5(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Last Man Standing points to buy this item.");
				}
			} else if (c.myShopId == 42) {
				if (c.VP >= getSpecialItemValue10(itemID)) {
					if (c.getItems().freeSlots() > 0) {
						c.VP -= getSpecialItemValue10(itemID);
						if(itemID != 970) {
								c.getItems().addItem(itemID, 1);
							} else {
								c.getItems().addItem(970, 50);
							}
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough vote points to buy this item.");
				}
			} else if (c.myShopId == 44) {
				if (c.mageKills >= getSpecialItemValue11(itemID)) {
					if (c.getItems().freeSlots() > 0) {
						c.mageKills -= getSpecialItemValue11(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough mage kill points to buy this item.");
				}
			} else if (c.myShopId == 45) {
				if (c.rangeKills >= getSpecialItemValue12(itemID)) {
					if (c.getItems().freeSlots() > 0) {
						c.rangeKills -= getSpecialItemValue12(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough ranged kill points to buy this item.");
				}
			} else if (c.myShopId == 46) {
				if (c.meleeKills >= getSpecialItemValue13(itemID)) {
					if (c.getItems().freeSlots() > 0) {
						c.meleeKills -= getSpecialItemValue13(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough melee kill points to buy this item.");
				}
			} else if (c.myShopId == 49 || c.myShopId == 50 || c.myShopId == 51 || c.myShopId == 52 || c.myShopId == 39) {
				if (c.getItems().playerHasItem(995, getSpecialItemValue14(itemID))) {
					if (c.getItems().freeSlots() > 0){
						c.getItems().deleteItem(995, c.getItems().getItemSlot(995), getSpecialItemValue14(itemID));
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough coins to buy this item.");
				}
			} else if (c.myShopId == 43) {
				if (c.VP >= getSpecialItemValue10(itemID)) {
					if (c.getItems().freeSlots() > 0) {
						c.VP -= getSpecialItemValue10(itemID);
								c.getItems().addItem(itemID, 1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough vote points to buy this item.");
				}
			} else if (c.myShopId == 37) {
				if (c.targetPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.targetPoints -= getSpecialItemValue(itemID);
						if(itemID != 995) {
								c.getItems().addItem(itemID, 1);
							} else {
								c.getItems().addItem(995, 2500000);
							}
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough target points to buy this item.");
				}
			} else if (c.myShopId == 31) {
				if (c.getItems().playerHasItem(4067, getSpecialItemValue2(itemID))) {
					if (c.getItems().freeSlots() > 0){
						c.getItems().deleteItem(4067, c.getItems().getItemSlot(4067), getSpecialItemValue2(itemID));
						if(itemID != 970) {
								c.getItems().addItem(itemID, 1);
							} else {
								c.getItems().addItem(970, 100);
							}
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Donator Tickets to buy this item.");
				}

			} else if (c.myShopId == 29) {
				if (c.pkPoints >= getSpecialItemValue(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pkPoints -= getSpecialItemValue(itemID);
						c.getItems().addItem(itemID,1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough PK Points to buy this item.");
				}

				} else if (c.myShopId == 18 || c.myShopId == 32) {
				if (c.pkPoints >= getSpecialItemValue7(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pkPoints -= getSpecialItemValue7(itemID);
						if(itemID != 4740) {
						c.getItems().addItem(itemID,1);
						} else {
						c.getItems().addItem(4740,10);
						}
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough PK Points to buy this item.");
				}
			} else if (c.myShopId == 22) {
				if (c.taskPoints >= getSpecialItemValue16(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.taskPoints -= getSpecialItemValue16(itemID);
						if(itemID != 970) {
								c.getItems().addItem(itemID, 1);
							} else {
								c.getItems().addItem(970, 25);
							}
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Slayer Points to buy this item.");
				}
			} else if (c.myShopId == 6) {
				if (c.EP >= getSpecialItemValue20(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.EP -= getSpecialItemValue20(itemID);
						if(itemID != 995) {
								c.getItems().addItem(itemID, 1);
							} else {
								c.getItems().addItem(995, 1000000);
							}
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Event Points to buy this item.");
				}
			} else if (c.myShopId == 16) {
				if (c.pandPoints >= getSpecialItemValue22(itemID)) {
					if (c.getItems().freeSlots() > 0){
						c.pandPoints -= getSpecialItemValue22(itemID);
						c.getItems().addItem(itemID, 1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough Pandemonium Points to buy this item.");
				}
			}	else if (c.myShopId == 33) {
				if (c.SP >= (getSpecialItemValue3(itemID) * amount)) {
					if (c.getItems().freeSlots() > 0){
						c.SP -= (getSpecialItemValue3(itemID) * amount);
						c.getItems().addItem(itemID,amount);
						c.getItems().resetItems(3823);
					}
				} else {
					String messageAdd = (amount > 1) ? " amount." : " item.";
					c.sendMessage("You do not have enough Gear Points to buy this"+messageAdd);
				}

			}
			c.questTab();
		}

		public void openSkillCape() {
			int capes = get99Count();
			if (capes > 1)
				capes = 1;
			else
				capes = 0;
			c.myShopId = 14;
			setupSkillCapes(capes, get99Count());
		}



		/*public int[][] skillCapes = {{0,9747,4319,2679},{1,2683,4329,2685},{2,2680,4359,2682},{3,2701,4341,2703},{4,2686,4351,2688},{5,2689,4347,2691},{6,2692,4343,2691},
									{7,2737,4325,2733},{8,2734,4353,2736},{9,2716,4337,2718},{1-,2728,4335,2730},{11,2695,4321,2697},{12,2713,4327,2715},{13,2725,4357,2727},
									{14,2722,4345,2724},{15,2707,4339,2709},{16,2704,4317,2706},{17,2710,4361,2712},{18,2719,4355,2721},{19,2737,4331,2739},{20,2698,4333,2700}};*/
		public int[] skillCapes = {9747,9753,9750,9768,9756,9759,9762,9801,9807,9783,9798,9804,9780,9795,9792,9774,9771,9777,9786,9810,9765};
		public int get99Count() {
			int count = 0;
			for (int j = 0; j < c.playerLevel.length; j++) {
				if (c.getLevelForXP(c.playerXP[j]) >= 99) {
					count++;
				}
			}
			return count;
		}

		public void setupSkillCapes(int capes, int capes2) {
			//synchronized(c) {
				c.getItems().resetItems(3823);
				c.isShopping = true;
				c.myShopId = 14;
				c.getPA().sendFrame248(3824, 3822);
				c.getPA().sendFrame126("Skillcape Shop", 3901);

				int TotalItems = 0;
				TotalItems = capes2;
				if (TotalItems > Server.shopHandler.MaxShopItems) {
					TotalItems = Server.shopHandler.MaxShopItems;
				}
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(3900);
				c.getOutStream().writeWord(TotalItems);
				int TotalCount = 0;
				for (int i = 0; i < 21; i++) {
					if (c.getLevelForXP(c.playerXP[i]) < 99)
						continue;
					c.getOutStream().writeByte(1);
					c.getOutStream().writeWordBigEndianA(skillCapes[i] + 2);
					TotalCount++;
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			//}
		}

		public void skillBuy(int item) {
			int nn = get99Count();
			if (nn > 1)
				nn = 1;
			else
				nn = 0;
			for (int j = 0; j < skillCapes.length; j++) {
				if (skillCapes[j] == item || skillCapes[j]+1 == item) {
					if (c.getItems().freeSlots() > 1) {
						if (c.getItems().playerHasItem(995,99000)) {
							if (c.getLevelForXP(c.playerXP[j]) >= 99) {
								c.getItems().deleteItem(995, c.getItems().getItemSlot(995), 99000);
								c.getItems().addItem(skillCapes[j] + nn,1);
								c.getItems().addItem(skillCapes[j] + 2,1);
							} else {
								c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
							}
						} else {
							c.sendMessage("You need 99k to buy this item.");
						}
					} else {
						c.sendMessage("You must have at least 1 inventory spaces to buy this item.");
					}
				}
				/*if (skillCapes[j][1 + nn] == item) {
					if (c.getItems().freeSlots() >= 1) {
						if (c.getItems().playerHasItem(995,99000)) {
							if (c.getLevelForXP(c.playerXP[j]) >= 99) {
								c.getItems().deleteItem(995, c.getItems().getItemSlot(995), 99000);
								c.getItems().addItem(skillCapes[j] + nn,1);
								c.getItems().addItem(skillCapes[j] + 2,1);
							} else {
								c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
							}
						} else {
							c.sendMessage("You need 99k to buy this item.");
						}
					} else {
						c.sendMessage("You must have at least 1 inventory spaces to buy this item.");
					}
					break;
				}*/
			}
			c.getItems().resetItems(3823);
		}

		public void openVoid() {
			/*synchronized(c) {
				c.getItems().resetItems(3823);
				c.isShopping = true;
				c.myShopId = 15;
				c.getPA().sendFrame248(3824, 3822);
				c.getPA().sendFrame126("Void Recovery", 3901);

				int TotalItems = 5;
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(3900);
				c.getOutStream().writeWord(TotalItems);
				for (int i = 0; i < c.voidStatus.length; i++) {
					c.getOutStream().writeByte(c.voidStatus[i]);
					c.getOutStream().writeWordBigEndianA(2519 + i * 2);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}*/
		}

		public void buyVoid(int item) {
			/*if (item > 2527 || item < 2518)
				return;
			//c.sendMessage("" + item);
			if (c.voidStatus[(item-2518)/2] > 0) {
				if (c.getItems().freeSlots() >= 1) {
					if (c.getItems().playerHasItem(995,c.getItems().getUntradePrice(item))) {
						c.voidStatus[(item-2518)/2]--;
						c.getItems().deleteItem(995,c.getItems().getItemSlot(995), c.getItems().getUntradePrice(item));
						c.getItems().addItem(item,1);
						openVoid();
					} else {
						c.sendMessage("This item costs " + c.getItems().getUntradePrice(item) + " coins to rebuy.");
					}
				} else {
					c.sendMessage("I should have a free inventory space.");
				}
			} else {
				c.sendMessage("I don't need to recover this item from the void knights.");
			}*/
		}


}
