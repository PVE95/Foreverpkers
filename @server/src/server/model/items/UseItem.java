package server.model.items;

import server.model.players.Client;
import server.util.Misc;
import server.Config;

/**
 * 
 * @author Ryan / Lmctruck30
 *
 */

public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX, int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch(objectID) {
			case 2783:
				c.getSmithingInt().showSmithInterface(itemId);
			break;
			case 8151:
			case 8389:
				c.getFarming().checkItemOnObject(itemId);
			break;
			case 2728:
            case 2732:
			case 12269:
				c.getCooking().itemOnObject(itemId);
			break;
			case 409:
			case 411:
				if (c.getPrayer().isBone(itemId))
					c.getPrayer().bonesOnAltar(itemId);
			break;
			case 12093:
				if(c.getItems().playerHasItem(2138, 1) && c.memberStatus >= 1) {
				c.getItems().deleteItem(2138,1);
				c.getPA().startTeleport(2461, 4356, 0, "modern");
				c.sendMessage("As you offer the raw chicken you are teleported to the Evil Chicken's lair.");
				}
			break;
            case 12253:
                if(c.getItems().playerHasItem(954, 1)) {
                  c.getPA().movePlayer(2271, 4680, 4);
             }
            break;
		default:
			if(c.playerRights == 3)
				Misc.println("Player At Object id: "+objectID+" X:" + objectX + " Y:" +objectY+" with Item id: "+itemId);
			break;
		}
		
	}

	public static void ItemonItem(Client c, int itemUsed, int useWith) {
	
				//papyrus
				int total = 0;
				if (itemUsed == 970) {
					for(int i = 0; i < c.playerItems.length; i++) {
						if(c.playerItems[i] - 1 == useWith) {
							if(c.getItems().getItemName(useWith).equals(c.getItems().getItemName(useWith + 1))) {
								if(c.getItems().playerHasItem(useWith, 1)) {
									total = total + 1;
									c.getItems().deleteItem(useWith, c.getItems().getItemSlot(useWith), 1);
									c.getItems().deleteItem(970, c.getItems().getItemSlot(970), 1);
									c.getItems().addItem(useWith+1, 1);
									}
							} else {
								c.sendMessage("This item cannot be noted.");
								return;
							}
						}
						}
				}
				if (useWith == 970) {
					for(int i = 0; i < c.playerItems.length; i++) {
						if(c.playerItems[i] - 1 == itemUsed) {
							if(c.getItems().getItemName(itemUsed).equals(c.getItems().getItemName(itemUsed + 1))) {
								if(c.getItems().playerHasItem(itemUsed, 1)) {
									total = total + 1;
									c.getItems().deleteItem(itemUsed, c.getItems().getItemSlot(itemUsed), 1);
									c.getItems().deleteItem(970, c.getItems().getItemSlot(970), 1);
									c.getItems().addItem(itemUsed+1, 1);
									}
							} else {
								c.sendMessage("This item cannot be noted.");
								return;
							}
						}
						}
				}
				if (itemUsed == 3025 && useWith == 3024 || itemUsed == 3024
                                && useWith == 3025) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3027 && useWith == 3025 || itemUsed == 3025
                                && useWith == 3027) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3029 && useWith == 3025 || itemUsed == 3025
                                && useWith == 3029) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3031 && useWith == 3025 || itemUsed == 3025
                                && useWith == 3031) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3031 && useWith == 3027 || itemUsed == 3027
                                && useWith == 3031) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3031 && useWith == 3029 || itemUsed == 3029
                                && useWith == 3031) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3027 && useWith == 3029 || itemUsed == 3029
                                && useWith == 3027) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3027 && useWith == 3024 || itemUsed == 3024
                                && useWith == 3027) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3027 && useWith == 3026 || itemUsed == 3026
                                && useWith == 3027) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3027 && useWith == 3028 || itemUsed == 3028
                                && useWith == 3027) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 10926 && useWith == 10925 || itemUsed == 10925
                                && useWith == 10926) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 10926 && useWith == 10926 || itemUsed == 10925
                                && useWith == 10925) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 10927 && useWith == 10928 || itemUsed == 10928
                                && useWith == 10927) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 10927 && useWith == 10927 || itemUsed == 10928
                                && useWith == 10928) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 10929 && useWith == 10930 || itemUsed == 10930
                                && useWith == 10929) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 10929 && useWith == 10929 || itemUsed == 10930
                                && useWith == 10930) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 10931 && useWith == 10932 || itemUsed == 10931
                                && useWith == 10932) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 10931 && useWith == 10931 || itemUsed == 10932
                                && useWith == 10932) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
		if (itemUsed == 3027 && useWith == 3030 || itemUsed == 3030
                                && useWith == 3027) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3029 && useWith == 3027 || itemUsed == 3027
                                && useWith == 3029) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3025 && useWith == 3026 || itemUsed == 3026
                                && useWith == 3025) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3025 && useWith == 3028 || itemUsed == 3028
                                && useWith == 3025) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3025 && useWith == 3030 || itemUsed == 3030
                                && useWith == 3025) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }//
                if (itemUsed == 3029 && useWith == 3024 || itemUsed == 3024
                                && useWith == 3029) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3029 && useWith == 3026 || itemUsed == 3026
                                && useWith == 3029) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3029 && useWith == 3028 || itemUsed == 3028
                                && useWith == 3029) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3029 && useWith == 3030 || itemUsed == 3030
                                && useWith == 3029) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3031 && useWith == 3024 || itemUsed == 3024
                                && useWith == 3031) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3031 && useWith == 3026 || itemUsed == 3026
                                && useWith == 3031) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3031 && useWith == 3028 || itemUsed == 3028
                                && useWith == 3031) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 3031 && useWith == 3030 || itemUsed == 3030
                                && useWith == 3031) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
	
	if (itemUsed == 6686 && useWith == 6685 || itemUsed == 6685
                                && useWith == 6686) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6686 && useWith == 6689 || itemUsed == 6689
                                && useWith == 6686) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6686 && useWith == 6687 || itemUsed == 6687
                                && useWith == 6686) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6686 && useWith == 6691 || itemUsed == 6691
                                && useWith == 6686) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6688 && useWith == 6686 || itemUsed == 6686
                                && useWith == 6688) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6688 && useWith == 6687 || itemUsed == 6687
                                && useWith == 6688) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6690 && useWith == 6688 || itemUsed == 6688
                                && useWith == 6690) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6692 && useWith == 6688 || itemUsed == 6688
                                && useWith == 6692) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6692 && useWith == 6685 || itemUsed == 6685
                                && useWith == 6692) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6692 && useWith == 6690 || itemUsed == 6690
                                && useWith == 6692) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6692 && useWith == 6689 || itemUsed == 6689
                                && useWith == 6692) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6692 && useWith == 6691 || itemUsed == 6691
                                && useWith == 6692) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6692 && useWith == 6687 || itemUsed == 6687
                                && useWith == 6692) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6688 && useWith == 6689 || itemUsed == 6689
                                && useWith == 6688) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6688 && useWith == 6685 || itemUsed == 6685
                                && useWith == 6688) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6688 && useWith == 6691 || itemUsed == 6691
                                && useWith == 6688) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6686 && useWith == 6692 || itemUsed == 6692
                                && useWith == 6686) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6690 && useWith == 6685 || itemUsed == 6685
                                && useWith == 6690) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6690 && useWith == 6689 || itemUsed == 6689
                                && useWith == 6690) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6690 && useWith == 6691 || itemUsed == 6691
                                && useWith == 6690) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 6690 && useWith == 6687 || itemUsed == 6687
                                && useWith == 6690) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                /**
                 *** End of saradomin brew fix ( Using noted sara brew on non noted to get
                 * non noted)
                 **/
                /**
                 *** Start of PRAYER POT fix ( Using noted sara brew on non noted to get
                 * non noted)
                 **/
                if (itemUsed == 2434 && useWith == 2435 || itemUsed == 2435
                                && useWith == 2434) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2435 && useWith == 139 || itemUsed == 139
                                && useWith == 2435) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2435 && useWith == 141 || itemUsed == 141
                                && useWith == 2435) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2435 && useWith == 143 || itemUsed == 143
                                && useWith == 2435) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2434 && useWith == 139 || itemUsed == 139
                                && useWith == 2434) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2434 && useWith == 141 || itemUsed == 141
                                && useWith == 2434) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2434 && useWith == 140 || itemUsed == 140
                                && useWith == 2434) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 142 && useWith == 140 || itemUsed == 140
                                && useWith == 142) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 141 && useWith == 140 || itemUsed == 140
                                && useWith == 141) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 143 && useWith == 140 || itemUsed == 140
                                && useWith == 143) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 142 && useWith == 2435 || itemUsed == 2435
                                && useWith == 142) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 144 && useWith == 2435 || itemUsed == 2435
                                && useWith == 144) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 144 && useWith == 140 || itemUsed == 140
                                && useWith == 144) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 143 && useWith == 140 || itemUsed == 140
                                && useWith == 143) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 139 && useWith == 140 || itemUsed == 140
                                && useWith == 139) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 140 && useWith == 2435 || itemUsed == 2435
                                && useWith == 140) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 144 && useWith == 143 || itemUsed == 143
                                && useWith == 144) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2434 && useWith == 143 || itemUsed == 143
                                && useWith == 2434) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2434 && useWith == 142 || itemUsed == 142
                                && useWith == 2434) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 2434 && useWith == 144 || itemUsed == 144
                                && useWith == 2434) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 139 && useWith == 142 || itemUsed == 142
                                && useWith == 139) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 141 && useWith == 142 || itemUsed == 142
                                && useWith == 141) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 142 && useWith == 143 || itemUsed == 143
                                && useWith == 142) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 142 && useWith == 139 || itemUsed == 139
                                && useWith == 142) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 142 && useWith == 143 || itemUsed == 143
                                && useWith == 142) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 144 && useWith == 142 || itemUsed == 142
                                && useWith == 144) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 142 && useWith == 141 || itemUsed == 141
                                && useWith == 142) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 144 && useWith == 139 || itemUsed == 139
                                && useWith == 144) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
                if (itemUsed == 144 && useWith == 141 || itemUsed == 141
                                && useWith == 144) {
                        c.sendMessage("Nothing interesting happens.");
                        return;
                }
	
		if ((itemUsed == 2355 && useWith == 1161) || (itemUsed == 1161 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1161, c.getItems().getItemSlot(1161), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2605,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2355 && useWith == 1123) || (itemUsed == 1123 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1123, c.getItems().getItemSlot(1123), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2599,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2355 && useWith == 1073) || (itemUsed == 1073 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1073, c.getItems().getItemSlot(1073), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2601,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2355 && useWith == 1199) || (itemUsed == 1199 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1199, c.getItems().getItemSlot(1199), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2603,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2355 && useWith == 1165) || (itemUsed == 1165 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1165, c.getItems().getItemSlot(1165), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2587,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2355 && useWith == 1125) || (itemUsed == 1125 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1125, c.getItems().getItemSlot(1125), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2583,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2355 && useWith == 1077) || (itemUsed == 1077 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1077, c.getItems().getItemSlot(1077), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2585,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2355 && useWith == 1195) || (itemUsed == 1195 && useWith == 2355)) {
			if (c.playerLevel[c.playerSmithing] >= 70) {
				c.getItems().deleteItem(1195, c.getItems().getItemSlot(1195), 1);
				c.getItems().deleteItem(2355, c.getItems().getItemSlot(2355), 1);
				c.getItems().addItem(2589,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1199) || (itemUsed == 1199 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1199, c.getItems().getItemSlot(1199), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2611,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1161) || (itemUsed == 1161 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1161, c.getItems().getItemSlot(1161), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2613,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1123) || (itemUsed == 1123 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1123, c.getItems().getItemSlot(1123), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2607,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1073) || (itemUsed == 1073 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1073, c.getItems().getItemSlot(1073), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2609,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1165) || (itemUsed == 1165 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1165, c.getItems().getItemSlot(1165), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2595,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1125) || (itemUsed == 1125 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1125, c.getItems().getItemSlot(1125), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2591,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1077) || (itemUsed == 1077 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1077, c.getItems().getItemSlot(1077), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2593,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
		if ((itemUsed == 2357 && useWith == 1195) || (itemUsed == 1195 && useWith == 2357)) {
			if (c.playerLevel[c.playerSmithing] >= 75) {
				c.getItems().deleteItem(1195, c.getItems().getItemSlot(1195), 1);
				c.getItems().deleteItem(2357, c.getItems().getItemSlot(2357), 1);
				c.getItems().addItem(2597,1);
			} else {
				c.sendMessage("You need a smithing level of 75 to create this.");
			}
		}
	
		if (itemUsed == 227 || useWith == 227)
			c.getHerblore().handlePotMaking(itemUsed,useWith);

		if (c.getItems().getItemName(itemUsed).contains("(") && c.getItems().getItemName(useWith).contains("(") && !c.getItems().getItemName(useWith).contains("shield") && !c.getItems().getItemName(itemUsed).contains("shield")){
			if(itemUsed == 6686 || useWith == 6688 || useWith == 6690 || useWith == 6692 || useWith == 3025 || useWith == 3027 || useWith == 3029 || useWith == 3031) {
			return;
			} else {
			c.getPotMixing().mixPotion2(itemUsed, useWith);
			}
		}
		if (itemUsed == 1733 || useWith == 1733)
			c.getCrafting().handleLeather(itemUsed, useWith);
		if (itemUsed == 1755 || useWith == 1755)
			c.getCrafting().handleChisel(itemUsed,useWith);
		if (itemUsed == 946 || useWith == 946)
			c.getFletching().handleLog(itemUsed,useWith);
		if (itemUsed == 53 || useWith == 53 || itemUsed == 52 || useWith == 52 || useWith == 9144) {
			c.getFletching().makeArrows(itemUsed, useWith);
            return;
        }
		if ((itemUsed == 1540 && useWith == 11286) || (itemUsed == 11286 && useWith == 1540)) {
			if (c.playerLevel[c.playerSmithing] >= 95) {
				c.getItems().deleteItem(1540, c.getItems().getItemSlot(1540), 1);
				c.getItems().deleteItem(11286, c.getItems().getItemSlot(11286), 1);
				c.getItems().addItem(11284,1);
				c.sendMessage("You combine the two materials to create a dragonfire shield.");
				c.getPA().addSkillXP(500 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
			} else {
				c.sendMessage("You need a smithing level of 95 to create a dragonfire shield.");
			}
		}
		if (itemUsed == 9142 && useWith == 9190 || itemUsed == 9190 && useWith == 9142) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c.getItems().getItemAmount(useWith) ? c.getItems().getItemAmount(useWith) : c.getItems().getItemAmount(itemUsed);
				c.getItems().deleteItem(useWith, c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed, c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9241, boltsMade);
				c.getPA().addSkillXP(boltsMade * 6 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9191 || itemUsed == 9191 && useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c.getItems().getItemAmount(useWith) ? c.getItems().getItemAmount(useWith) : c.getItems().getItemAmount(itemUsed);
				c.getItems().deleteItem(useWith, c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed, c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9242, boltsMade);
				c.getPA().addSkillXP(boltsMade * 7 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}		
		}
		if (itemUsed == 9143 && useWith == 9192 || itemUsed == 9192 && useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c.getItems().getItemAmount(useWith) ? c.getItems().getItemAmount(useWith) : c.getItems().getItemAmount(itemUsed);
				c.getItems().deleteItem(useWith, c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed, c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9243, boltsMade);
				c.getPA().addSkillXP(boltsMade * 7 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}		
		}
		if (itemUsed == 9144 && useWith == 9193 || itemUsed == 9193 && useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c.getItems().getItemAmount(useWith) ? c.getItems().getItemAmount(useWith) : c.getItems().getItemAmount(itemUsed);
				c.getItems().deleteItem(useWith, c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed, c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9244, boltsMade);
				c.getPA().addSkillXP(boltsMade * 10 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}		
		}
		if (itemUsed == 9144 && useWith == 9194 || itemUsed == 9194 && useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c.getItems().getItemAmount(useWith) ? c.getItems().getItemAmount(useWith) : c.getItems().getItemAmount(itemUsed);
				c.getItems().deleteItem(useWith, c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed, c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9245, boltsMade);
				c.getPA().addSkillXP(boltsMade * 13 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}		
		}
		if (itemUsed == 1601 && useWith == 1755 || itemUsed == 1755 && useWith == 1601) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				c.getItems().deleteItem(1601, c.getItems().getItemSlot(1601), 1);
				c.getItems().addItem(9192, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 1607 && useWith == 1755 || itemUsed == 1755 && useWith == 1607) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				c.getItems().deleteItem(1607, c.getItems().getItemSlot(1607), 1);
				c.getItems().addItem(9189, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 1605 && useWith == 1755 || itemUsed == 1755 && useWith == 1605) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				c.getItems().deleteItem(1605, c.getItems().getItemSlot(1605), 1);
				c.getItems().addItem(9190, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 1603 && useWith == 1755 || itemUsed == 1755 && useWith == 1603) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems().deleteItem(1603, c.getItems().getItemSlot(1603), 1);
				c.getItems().addItem(9191, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed == 1615 && useWith == 1755 || itemUsed == 1755 && useWith == 1615) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems().deleteItem(1615, c.getItems().getItemSlot(1615), 1);
				c.getItems().addItem(9193, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE, c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed >= 11710 && itemUsed <= 11714 && useWith >= 11710 && useWith <= 11714) {
			if (c.getItems().hasAllShards()) {
				c.getItems().makeBlade();
			}		
		}
		if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366 && useWith == 2368) {
			c.getItems().deleteItem(2368, c.getItems().getItemSlot(2368),1);
			c.getItems().deleteItem(2366, c.getItems().getItemSlot(2366),1);
			c.getItems().addItem(1187,1);
		}
		
		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
			int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (blade == 11690) {
				c.getItems().makeGodsword(hilt);
			}
		}
		
		switch(itemUsed) {
			case 1511:
			case 1521:
			case 1519:
			case 1517:
			case 1515:
			case 1513:
			case 590:
				c.getFiremaking().checkLogType(itemUsed, useWith);
				//c.sendMessage("Firemaking is disabled.");
			break;
			
		default:
			if(c.playerRights == 3)
				Misc.println("Player used Item id: "+itemUsed+" with Item id: "+useWith);
			break;
		}	
	}
	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		switch(itemId) {
		
		default:
			if(c.playerRights == 3)
				Misc.println("Player used Item id: "+itemId+" with Npc id: "+npcId+" With Slot : "+slot);
			break;
		}
		
	}


}
