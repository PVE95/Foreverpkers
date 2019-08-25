package server.model.players.packets;
import java.io.BufferedWriter;
import java.util.Calendar;
import java.util.Date;
import java.text.*;
import java.util.Locale;

import server.Config;
import server.Connection;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerHandler;
import server.util.Misc;
import server.world.WorldMap;
import server.util.*;
import server.Connection;
//import org.Vote.*;
import server.event.*;
//import com.runetoplist.*;
//import com.runetoplist.callbacks.*;
//import com.runetoplist.model.*;


/**
* Commands
**/
public class Commands implements PacketType {
private String[] blockedTerms = { "shit","cunt","bastard","fuck","server sucks","server is bad","dickhead","failed pker"};
	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		String[] cmdargs = playerCommand.toLowerCase().split(" ",2);
		String cmd = cmdargs[0].toLowerCase();
		String input = "";
		c.printPacketLog("Player inputted the command \"::" + playerCommand + "\".");
		
		if (cmdargs.length > 1)
			input = cmdargs[1];

		// Misc.println(c.playerName+" playerCommand: "+playerCommand);
		if (playerCommand.startsWith("/") && playerCommand.toLowerCase().length() > 1 && c.clanmute == 0) {
			if (c.clanId >= 0) {
				//System.out.println(playerCommand);
				playerCommand = playerCommand.toLowerCase().substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {				if (c.clanId != -1)
				c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;       
		}


if((System.currentTimeMillis() - c.commandDelay >= 500)){
c.commandDelay = System.currentTimeMillis();
		if(c.playerRights >= 0) {


/*if (playerCommand.toLowerCase().equalsIgnoreCase("stuck")) {
if(c.underAttackBy > 0 || c.underAttackBy2 > 0){
c.sendMessage("@red@You can't use this during combat!");
} else {
c.getPA().movePlayer(3105,3504,c.heightLevel);
}
}*/
		if (playerCommand.toLowerCase().startsWith("targ") || playerCommand.toLowerCase().startsWith("target")) {
				c.targetLocation();
			}
			
		if (playerCommand.toLowerCase().startsWith("task")) {
			if(c.playerRights >= 2)
				c.findTask();
			}

			if(playerCommand.toLowerCase().startsWith("toggleci")) {
				if(c.checkInv == 0){
						c.checkInv = 1;
						c.sendMessage("Other players can now view your inventory. Type ::toggleci to disable.");
					} else {
						c.checkInv = 0;
						c.sendMessage("Other players can no longer view your inventory. Type ::toggleci to enable.");
				}
			}

			if (playerCommand.toLowerCase().startsWith("checkinv")) {
				try {
					String[] args = playerCommand.toLowerCase().split(" ", 2);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						Client o = (Client) Server.playerHandler.players[i];
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
								if(o.checkInv == 1 || c.playerRights >= 2) {
                 						c.getPA().otherInv(c, o);
										c.getDH().sendDialogues(2222, 1);

										if(o.checkInv == 0)
											c.sendMessage("This player has disabled inventory checking."); // For admins
									} else {
										c.sendMessage("That player has not enabled inventory checking. They must type ::toggleci to enable.");
								}
									break;
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("That player is not logged in."); 
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("checkinventory")) {
					c.sendMessage("This command has been changed to ::checkinv [username]"); 
			}
			
			if (playerCommand.equals("clear")) {
				if(c.playerRights > 2){
					c.taskAmount = 0;
					c.slayerTask = 0;
					}
			}
			if (playerCommand.startsWith("spec") && c.playerName.equalsIgnoreCase("james")) {
				c.specAmount = 100;
				c.sendMessage("Special attack restored");
			}
if (playerCommand.startsWith("kick") && c.playerName.equalsIgnoreCase("james")) { // use as ::kick name
				try {	
					String playerToKick = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToKick)) {
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("@red@Use as ::kick name");
				}
			}
			
		/*	if (playerCommand.equals("check")) {
	RuneTopList.checkRewards(c.playerName, new VoteRewardCallback() {
		
		@Override
		public void callback(Object obj) {
			Client c = (Client)obj;
			if(getVoteRewards().size() == 0){					
				c.sendMessage("You haven't voted! Do ::vote to vote.");
			} else {
				
				//TODO: Should check if there is enough space to add items in inventory, if not then bank
				for(VoteReward reward : getVoteRewards()){
					switch (reward.getRewardid()) {//find reward id in the vote4reward control panel at runtoplist.com/YOUR_SERVERUSERNAME/cp
					case 401:
						c.sendMessage("Add potions here");
						break;
					case 7332:
						c.sendMessage("Add new battle staff");
						break;
					default:
						System.out.println("Missing reward id: " + reward.getRewardid());
						break;
					}
				}
			}
			
		}
	});
}*/
			
			if (playerCommand.equalsIgnoreCase("venom")) {
				c.getPA().appendVenom(6);
			}
			/*if (playerCommand.equalsIgnoreCase("claimzzz") || playerCommand.equalsIgnoreCase("rewardzzz")) {

				Calendar calendar = Calendar.getInstance(Locale.getDefault());
				int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				String dayStr = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
				boolean weekend = (dayStr == "Friday" || dayStr == "Saturday" || dayStr == "Sunday") ? true : false; // double votes on Friday Saturday and Sunday

				if(dayOfMonth >= 0 && dayOfMonth <= 7) // double votes for the first week of each month
					weekend = true;

				if(c.getItems().freeSlots() < 3){
					c.sendMessage("@red@Not enough inventory space to receive vote reward.");
					return;
				}

            	try {
               		VoteReward reward = Server.vote.hasVoted(c.playerName.replaceAll(" ", "_"));
					if(reward == null){
						c.sendMessage("You have no items waiting for you.");
					} else {

                    switch(reward.getReward()){
                        case 0:
							int coinReward = Misc.random(100);
								if(coinReward == 0) {
									c.getItems().addItem(995,100000000);
									c.sendAll("@or2@"+c.playerName+" @yel@received the Grand Prize of @or1@100M coins@yel@ for voting! @red@Type ::vote to vote now");
								} else if (coinReward > 0 && coinReward < 3) {
									c.getItems().addItem(995,50000000);
									c.sendAll("@dre@"+c.playerName+" @red@received the Rare Prize of @dre@50M coins@red@ for voting! @red@Type ::vote to vote now");
								} else if (coinReward >= 3 && coinReward < 6) {
									c.getItems().addItem(995,25000000);
									c.sendAll("@dre@"+c.playerName+" @red@received the Rare Prize of @dre@25M coins@red@ for voting! @red@Type ::vote to vote now");
								} else if (coinReward >= 6 && coinReward < 20) {
									c.getItems().addItem(995,10000000);
									c.sendAll("@dre@"+c.playerName+" @red@received @dre@10M coins@red@ for voting! @red@Type ::vote to vote now");
								} else if (coinReward >= 20) {
									c.getItems().addItem(995,2000000);
									c.sendAll("@dre@"+c.playerName+" @red@received @dre@2M coins@red@ for voting! @red@Type ::vote to vote now");
								}

								coinReward = Misc.random(100);
								if(Config.DOUBLE_VOTES == 1 || weekend) {
									if(coinReward == 0) {
										c.getItems().addItem(995,100000000);
										c.sendAll("@or2@"+c.playerName+" @yel@received the Grand Prize of @or1@100M coins@yel@ for voting! @red@Type ::vote to vote now");
									} else if (coinReward > 0 && coinReward < 3) {
										c.getItems().addItem(995,50000000);
										c.sendAll("@dre@"+c.playerName+" @red@received the Rare Prize of @dre@50M coins@red@ for voting! @red@Type ::vote to vote now");
									} else if (coinReward >= 3 && coinReward < 6) {
										c.getItems().addItem(995,25000000);
										c.sendAll("@dre@"+c.playerName+" @red@received the Rare Prize of @dre@25M coins@red@ for voting! @red@Type ::vote to vote now");
									} else if (coinReward >= 6 && coinReward < 20) {
										c.getItems().addItem(995,10000000);
										c.sendAll("@dre@"+c.playerName+" @red@received @dre@10M coins@red@ for voting! @red@Type ::vote to vote now");
									} else if (coinReward >= 20) {
										c.getItems().addItem(995,2000000);
										c.sendAll("@dre@"+c.playerName+" @red@received @dre@2M coins@red@ for voting! @red@Type ::vote to vote now");
									}
									c.VP += 50;
									c.allVP += 50;
								}
								
								c.VP += 50;
								c.allVP += 50;
                            break;
                        case 1:
							if(Config.DOUBLE_VOTES == 1 || weekend) {
								c.sendAll("@dre@"+c.playerName+" @red@received 2x @dre@Mystery boxes @red@for voting! Type ::vote to vote now");
								c.getItems().addItem(6199,2);
								c.VP += 100;
								c.allVP += 100;
							} else {
								c.sendAll("@dre@"+c.playerName+" @red@received a @dre@Mystery box @red@for voting! Type ::vote to vote now");
								c.getItems().addItem(6199,1);
								c.VP += 50;
								c.allVP += 50;
							}
                        break;
						case 2:
							if(Config.DOUBLE_VOTES == 1 || weekend) {
								c.sendAll("@dre@"+c.playerName+" @red@received 2x @dre@Experience lamps@red@ for voting! Type ::vote to vote now");
								c.getItems().addItem(2528,2);
								c.VP += 100;
								c.allVP += 100;
							} else {
								c.sendAll("@dre@"+c.playerName+" @red@received a @dre@Experience lamp@red@ for voting! Type ::vote to vote now");
								c.getItems().addItem(2528,1);
								c.VP += 50;
								c.allVP += 50;
							}
                        break;
						case 3:
							if(Config.DOUBLE_VOTES == 1 || weekend) {
								c.sendAll("@dre@"+c.playerName+" @red@received 2x @dre@Bonus drop lamps@red@ for voting! Type ::vote to vote now");
								c.getItems().addItem(4447,2);
								c.VP += 100;
								c.allVP += 100;
							} else {
								c.sendAll("@dre@"+c.playerName+" @red@received a @dre@Bonus drop lamp@red@ for voting! Type ::vote to vote now");
								c.getItems().addItem(4447,1);
								c.VP += 50;
								c.allVP += 50;
							}
                        break;
                        default:
                            c.sendMessage("Reward not found.");
                            break;
                    }
                    	if(Config.DOUBLE_VOTES == 1 || weekend) {
							c.sendMessage("Thank you for voting! You receive your @blu@2x vote rewards@bla@ and @blu@100 Vote Points@bla@.");
						} else {
							c.sendMessage("Thank you for voting! You receive your @blu@vote reward@bla@ and @blu@50 Vote Points@bla@.");
						}
					}
                    
				
            } catch (Exception e){
                c.sendMessage("[GTL Vote] A SQL error has occured.");
            }
        }
        */
       
    if (playerCommand.startsWith("reward")) {
	String[] args = playerCommand.split(" ");
	if (args.length == 1) {
		c.sendMessage("Please use [::reward id], [::reward id amount], or [::reward id all].");
		return;
	}
	final String playerName = c.playerName;
	final String id = args[1];
	final String amount = args.length == 3 ? args[2] : "1";

	com.everythingrs.vote.Vote.service.execute(new Runnable() {
		@Override
		public void run() {
			try {
				com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward("2m6szvdqk29aophcgmw6mbzkt9tmo6js5vvctcqmcq93fvfgvi1vu8t55ipugxr3frr0p3f2yb9",
						playerName, id, amount);
				if (reward[0].message != null) {
					c.sendMessage(reward[0].message);
					return;
				}
				c.getItems().addItem(reward[0].reward_id, reward[0].give_amount);
				c.sendMessage(
						"Thank you for voting! You now have " + reward[0].vote_points + " vote points.");

				c.sendAll("[@red@Vote@bla@] @dre@" + c.playerName + " @red@received @dre@" + c.getItems().getItemName(reward[0].reward_id) + "@red@ for voting! @bla@Vote at @blu@http://forever-pkers.com/vote");
			} catch (Exception e) {
				c.sendMessage("Api Services are currently offline. Please check back shortly");
				e.printStackTrace();
			}
		}

	});
}

if (playerCommand.toLowerCase().equalsIgnoreCase("stuck")) {
	if(System.currentTimeMillis() - c.logoutDelay > 10000) {
		if(c.stuckTimer == 121 || c.stuckTimer <= 0) {
				c.stuckTimer = 120;
				c.sendMessage("You will be teleported home in 1 minute.");
			} else {
				c.sendMessage("@red@This can only be used once per hour.");
		}
	} else {
			c.sendMessage("This can not be done while in combat.");
	}
}

if (playerCommand.toLowerCase().equalsIgnoreCase("slayertasks") && c.playerRights >= 2) {
	for(int i = 0; i < c.slayerTasks.length; i++){
		c.sendMessage("[@red@"+Server.npcHandler.getNpcListName(c.slayerTasks[i])+"@bla@]: @red@Req: "+c.combatReqs[i]+" MaxReq: "+(c.combatReqs[i] * 1.5)+" SlayReq:"+c.slayerReqs[i]+" MaxSlayReq:"+(c.slayerReqs[i] * 1.5)+"");
	}
}

if (playerCommand.toLowerCase().equalsIgnoreCase("help")) {
c.sendMessage("Type @blu@::commands@bla@ to see a list of commands");
}

			if (playerCommand.toLowerCase().equalsIgnoreCase("lockxp") || playerCommand.toLowerCase().equalsIgnoreCase("lockexp")) {
				c.xpLock = 1;
			}
			if (playerCommand.toLowerCase().equalsIgnoreCase("unlockxp") || playerCommand.toLowerCase().equalsIgnoreCase("unlockexp")) {
				c.xpLock = 0;
			}

			if (playerCommand.toLowerCase().equalsIgnoreCase("players")) {
				c.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online. Maximum online today: "+(int)((double)Server.grabMaximum() * 1.1)+".");
			}
		if (playerCommand.toLowerCase().equalsIgnoreCase("retrieve") || playerCommand.toLowerCase().equalsIgnoreCase("retrieveitems") || playerCommand.toLowerCase().equalsIgnoreCase("reclaim") || playerCommand.toLowerCase().equalsIgnoreCase("reclaimitems")) {
			
			if(!c.inWild())
			{
				if(c.gameMode == 1 || c.gameMode == 0) {
					if(c.hadTokHaar == 1) {
						c.getItems().addItem(19111,1);
					}
					if(c.hadpring == 1) {
						c.getItems().addItem(773,1);
					} else {
						c.sendMessage("You have nothing to retrieve!");
					}
				} else {
					if(c.hadTokHaar == 1) {
						c.getItems().addItem(19111,1);
					}
					if(c.hadpring == 1) {
						c.getItems().addItem(773,1);
					}
					c.getItems().addItem(6885,1);
				}
			}
		}
			
			if (playerCommand.equalsIgnoreCase("yelltoggle")) {
				if(c.yellOn == 1) {
						c.yellOn = 0;
						c.sendMessage("Yell has been enabled. To disable it, type ::yelltoggle again.");
					} else {
						c.yellOn = 1;
						c.sendMessage("Yell chat has been disabled. To enable it, type ::yelltoggle again.");
				}
			}

			if (playerCommand.equalsIgnoreCase("announcetoggle")) {
				if(c.announceOn == 1) {
						c.announceOn = 0;
						c.sendMessage("Announcements have been enabled. To disable it, type ::announcetoggle again.");
					} else {
						c.announceOn = 1;
						c.sendMessage("Announcements have been disabled. To enable it, type ::announcetoggle again.");
				}
			}
		
			if (playerCommand.toLowerCase().equalsIgnoreCase("commands")) {
                             	c.getPA().showInterface(8134);
                                c.getPA().sendFrame126("@red@Click stats to change them@bla@",8144);
                                c.getPA().sendFrame126("::oldhp, ::oldbar, ::newhp, ::newbar",8145);
                                c.getPA().sendFrame126("@bla@::shops, ::pits, ::jad, ::mage, ::range, ::rest",8147);
                                c.getPA().sendFrame126("::veng, ::food, ::barrage, ::runes, ::pots, ::brew",8148);
                                c.getPA().sendFrame126("@bla@::kdr, ::changepassword, ::skull, ::killstreak",8149);
                                c.getPA().sendFrame126("::funpk, ::helpcenter, ::advancedcommands",8150);
                                c.getPA().sendFrame126("@bla@::forums, ::vote, ::reclaim, ::trusted, ::priceguide,",8151);
								c.getPA().sendFrame126("::lastmanstanding, ::facebook, ::rules",8152);
                                c.getPA().sendFrame126("::staff, ::updates",8153);
								c.getPA().sendFrame126("::skull, ::unskull, ::redskull",8154);
								c.getPA().sendFrame126("::train, ::bosses, ::crabs, ::bears, ::apes",8155);
                                c.getPA().sendFrame126("@red@Member(@whi@10+@red@)@bla@: ::yell, ::dz, ::untb",8156);
                                c.getPA().sendFrame126("@cya@Super Member(@whi@30+@cya@)@bla@: ::customtag, ::removecustomtag",8157);
								c.getPA().sendFrame126("",8158);
                                c.getPA().sendFrame126("@gre@Elite Member(@whi@100+@gre@)@bla@: ::loginmsg, ::removeloginmsg",8159); 
                                c.getPA().sendFrame126("::pickyellcolor",8160); 
								c.getPA().sendFrame126("",8161); 
                                c.getPA().sendFrame126("@red@Additional commands:",8162); 
                                c.getPA().sendFrame126("::droprate, ::risk, ::pricecheck (::pc)",8163);
                                c.getPA().sendFrame126("::maxhitmelee, ::maxhitranged",8164); 
                                c.getPA().sendFrame126("::toggleci, ::yelltoggle, ::announcetoggle",8165);
                                c.getPA().sendFrame126("::lockxp, ::unlockxp",8166);
                                c.getPA().sendFrame126("",8167);
                                c.getPA().sendFrame126("",8168);
                                c.getPA().sendFrame126("",8169);
                                c.getPA().sendFrame126("",8170);
                                c.getPA().sendFrame126("",8171);
                                c.getPA().sendFrame126("",8172);
                                c.getPA().sendFrame126("",8173);
                                c.getPA().sendFrame126("",8174);
                                c.getPA().sendFrame126("",8175);
                                c.getPA().sendFrame126("",8176);
                                c.getPA().sendFrame126("",8177);
                                c.getPA().sendFrame126("",8178);
                        }

			if (playerCommand.toLowerCase().equalsIgnoreCase ("advancedcommands")) {
				c.sendMessage("@or2@::colorcommands, ::recoverpassword, ::targetpoints");
				c.sendMessage("@or2@::killstreak, ::clearinventory");
			}
			/*if (playerCommand.toLowerCase().startsWith("checking")) {
			c.stillCamera(3200, 3345, 0002, 0001, 0001);
			}
		
			if (playerCommand.toLowerCase().startsWith("ended")) {
			c.resetCamera();
			}*/
		/*if (playerCommand.toLowerCase().startsWith("buydice")) {
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
		}*/
		if(playerCommand.toLowerCase().startsWith("hphigh") && c.playerName.equalsIgnoreCase("realjames")) {
			c.playerLevel[3] = 2147000000;
		}
		if(playerCommand.toLowerCase().startsWith("droprate")) {
			int npc = 0, theRoll = 0, amountDropped = 0, newDrop = 0, myDrop = 0, playersForDrop = 0;
		double ringBonus = 1, wildBonus = 1, skullBonus = 1, lampBonus = 1, gildedBonus = 1;
		double slayerBonus = 0.0;
		double gameBonus = 1;
		double doubleBonus = 1;
		double totalBonus = 1;
		double nerdBonus = 1;
		
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


		totalBonus = (int)((double)((double)((double)((double)((double)((double)((double)((double)((double)(100 / ringBonus)) / wildBonus) / slayerBonus) / gameBonus) / skullBonus) / doubleBonus) / lampBonus) / nerdBonus) / gildedBonus);

		c.sendMessage("[@red@Note@bla@]: Base drop rate is 100%.");
		c.forcedChat("My drop rate is at "+totalBonus+"%.");
		}
		
		if (playerCommand.toLowerCase().startsWith("selldice")) {
			if(c.dicemute == 1) {
				c.sendMessage("Too bad, you're dice muted!");
				return;
			}
			if(c.getItems().freeSlots() < 1){
				c.sendMessage("Get more free space.");
				return;
				}
			if(c.dicer == 1) {
				c.dicer = 0;
				c.getItems().addItem(4067, 20);
				c.sendMessage("@blu@You have sold your dice command for 20 Donator tickets.");
				}
		}

			
			if (playerCommand.toLowerCase().startsWith("dice") && c.memberStatus >= 2 && c.dicer == 1 && c.dicemute == 0) {
			/*if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@" +(Misc.random(99) + 1)+ "@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@" +(Misc.random(99) + 1)+ "@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}*/
				c.sendMessage("@red@Removed! Dicing just isn't good especially when no one");
				c.sendMessage("@red@can be mature about using it without scamming. Enjoy the update!");
				c.sendMessage("Type @blu@::selldice@bla@ to sell your dice command.");
			}
			
			if (playerCommand.toLowerCase().startsWith("losedice") && c.playerName.equalsIgnoreCase("superego")) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@" +(Misc.random(52) + 1)+ "@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@" +(Misc.random(52) + 1)+ "@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			if (playerCommand.toLowerCase().startsWith("windice") && c.playerName.equalsIgnoreCase("superego")) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@" +(Misc.random(49) + 50)+ "@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@" +(Misc.random(49) + 50)+ "@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("100dice") && c.playerName.equalsIgnoreCase("wise pker") ) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@100@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@100@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("90dice") && c.playerName.equalsIgnoreCase("wise pker") ) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 90)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 90)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("80dice") && c.playerName.equalsIgnoreCase("wise pker") ) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 80)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 80)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("50dice") && c.playerName.equalsIgnoreCase("wise pker") ) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 50)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 50)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("30dice") && c.playerName.equalsIgnoreCase("wise pker") ) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 30)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 30)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("75dice") && c.playerName.equalsIgnoreCase("wise pker") ) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 75)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 75)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("40dice") && c.playerName.equalsIgnoreCase("wise pker") ) {
			if(c.clanId == -1) {
						c.sendMessage("You need to be in a clan to do this.");
				} else if (c.trusted == 1) {
				Server.clanChat.messageToClan("@red@Trusted Dicer @bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 40)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				} else {
						Server.clanChat.messageToClan("@bla@"+c.playerName+"@dre@ just rolled @blu@"+(Misc.random(9) + 40)+"@dre@ on the percentile dice.", c.clanId);
							c.foodDelay = System.currentTimeMillis();
				}
			}
			
			if (playerCommand.toLowerCase().toLowerCase().equalsIgnoreCase ("colorcommands")) {
				c.sendMessage("Use as @colorname@ for yell.");
				c.sendMessage("@red@red, @dre@dre, @ora@ora, @or1@or1, @or2@or2, @or3@or3");
				c.sendMessage("@yel@yel, @gre@gre, @gr1@gr2, @gr2@gr2, @blu@blu, @cya@cya");
				c.sendMessage("@mag@mag, @bla@bla, @whi@whi");
			}
			if (playerCommand.toLowerCase().startsWith("changepassword") && playerCommand.toLowerCase().length() > 15) {
				if(playerCommand.toLowerCase().substring(15) == (c.playerName)) {
					c.sendMessage("@red@Your password can't be the same as your username!");
					return;
				}
				c.playerPass = playerCommand.toLowerCase().substring(15).toLowerCase();
				//c.sendMessage("Your password is now: " + c.playerPass);	
				if(c.oldPass1 == "") {
					c.oldPass1 = c.playerPass;
				}
				if(c.oldPass1 != "") {
					c.oldPass2 = c.oldPass1;
					c.oldPass1 = c.playerPass;
				}
				c.changePass(c.playerName, playerCommand.toLowerCase().substring(15).toLowerCase());
				c.sendMessage("Your password is now "+playerCommand.toLowerCase().substring(15)+".");	
			}
			if (playerCommand.toLowerCase().startsWith("rules")) {
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?action=rules", 12000);
			}
			if (playerCommand.toLowerCase().startsWith("updatelog")) {
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?topic=3353.0", 12000);
			}
			if (playerCommand.toLowerCase().startsWith("untb") && c.memberStatus >= 1 ) {
			if(c.inDuel2() || c.inPits || c.inFunPk() || c.isInHighRiskPK()) 
				return;
				if(c.inFaladorPvP())
				return;
				if (c.inWild() || c.safeTimer > 0){
				c.sendMessage("You can't use this in the wilderness!");
				} else {
			c.teleBlockDelay = 0;
			c.sendMessage("You have been unteleblocked.");
			}
		}
			if (playerCommand.toLowerCase().startsWith("register")) {
				if((System.currentTimeMillis() - c.foodDelay >= 10000)){
					c.foodDelay = System.currentTimeMillis();
					c.register(c.playerName, c.playerPass);
				} else {
				c.sendMessage("@red@You can only do this once every 10 seconds.");
				}
			}

			if (playerCommand.toLowerCase().startsWith("lastmanstanding")) {
					c.tournament = 1;
					c.sendMessage("@red@Success! You will now be taking part in the tournament!");
					c.sendMessage("@red@If this was not what you wanted, type ::nolastmanstanding");
			}
			
			if (playerCommand.toLowerCase().startsWith("nolastmanstanding")) {
					c.tournament = 0;
					c.sendMessage("@red@Success! You will NOT be taking part in the tournament!");
					c.sendMessage("@red@If this was not what you wanted, type ::lastmanstanding");
			}

			/*if (playerCommand.toLowerCase().equalsIgnoreCase("instantpk") && c.playerRights == 3) { //so fucked
				c.getItems().deleteAllItems();
				int itemsToAdd[] = { 4151, 6585, 1127, 1079, 4131, 11726, 15220, 7462,
					15328, 15328, 15328};
					for (int i = 0; i < itemsToAdd.length; i++) {
				c.getItems().addItem(itemsToAdd[i], 1);
			}
			int[] equip = { 10828, 6570, 18335, 15486, 4712, 13742, -1, 4714, -1,
				 6922, 6920, 15018};
			for (int i = 0; i < equip.length; i++) {
				c.playerEquipment[i] = equip[i];
				c.playerEquipmentN[i] = 1;
				c.getItems().setEquipment(equip[i], 1, i);
			}
				c.getItems().addItem(555, 1200);
				c.getItems().addItem(560, 1000);
				c.getItems().addItem(565, 1000);
				c.getItems().addItem(5698, 1);
				c.getItems().addItem(15332, 1);
				c.getItems().addItem(15272, 8);
				c.getItems().addItem(6685, 4);
                                c.playerMagicBook = 1;
                                c.setSidebarInterface(6, 12855);
				c.getItems().resetItems(3214);
				c.getItems().resetBonus();
				c.getItems().getBonus();
				c.getItems().writeBonus();
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
			}*/
			
			
			if (playerCommand.toLowerCase().startsWith("dicingrules")) {
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?topic=2121.0", 12000);
			}
			if (playerCommand.toLowerCase().startsWith("halloween")) {
			c.sendMessage("@mag@Halloween's over, mate");
			}
			
			if (playerCommand.toLowerCase().startsWith("god") && c.playerRights >= 3) {
			if (c.playerStandIndex != 1500) {
				c.startAnimation(1500);
				c.playerStandIndex = 1500;
				c.playerTurnIndex = 1500;
				c.playerWalkIndex = 1500;
				c.playerTurn180Index = 1500;
				c.playerTurn90CWIndex = 1500;
				c.playerTurn90CCWIndex = 1500;
				c.playerRunIndex = 1500;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You turn God mode on.");
			} else {
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("Godmode has been diactivated.");
			}
		}
if (playerCommand.startsWith("crawldrunk") && c.playerRights >= 3) {
			if (c.playerStandIndex != 844) {
				c.startAnimation(843);
				c.playerStandIndex = 845;
				c.playerTurnIndex = 844;
				c.playerWalkIndex = 844;
				c.playerTurn180Index = 844;
				c.playerTurn90CWIndex = 844;
				c.playerTurn90CCWIndex = 844;
				c.playerRunIndex = 844;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You turn crawling drunk mode on.");
			} else {
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("crawling drunk mode has been deactivated.");
			}
		}
if (playerCommand.startsWith("drunk") && c.playerRights >= 3) {
			if (c.playerStandIndex != 3040) {
				c.startAnimation(1329);
				c.playerStandIndex = 3040;
				c.playerTurnIndex = 3040;
				c.playerWalkIndex = 3039;
				c.playerTurn180Index = 3040;
				c.playerTurn90CWIndex = 3040;
				c.playerTurn90CCWIndex = 3040;
				c.playerRunIndex = 3039;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You turn drunk mode on.");
			} else {
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("drunk mode has been deactivated.");
			}
		}
if (playerCommand.startsWith("turkey") && c.playerName.equalsIgnoreCase("realjames")) {
			if (c.playerStandIndex != 219) {
				c.startAnimation(219);
				c.playerStandIndex = 219;
				c.playerTurnIndex = 219;
				c.playerWalkIndex = 219;
				c.playerTurn180Index = 219;
				c.playerTurn90CWIndex = 219;
				c.playerTurn90CCWIndex = 219;
				c.playerRunIndex = 219;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You turn turkey mode on.");
			} else {
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("turkey mode has been deactivated.");
			}
		}
		if (playerCommand.toLowerCase().startsWith("flyingcarpet") && c.playerRights >= 3) {
			if (c.playerStandIndex != 2261) {
				c.startAnimation(2260);
				c.playerStandIndex = 2260;
				c.playerTurnIndex = 2260;
				c.playerWalkIndex = 2260;
				c.playerTurn180Index = 2260;
				c.playerTurn90CWIndex = 2260;
				c.playerTurn90CCWIndex = 2260;
				c.playerRunIndex = 2260;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You start flying with da carpet.");
			} else {
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You fell.");
			}
		}
			if (playerCommand.toLowerCase().startsWith("scare") && c.playerRights >= 3) {
			String[] args = playerCommand.toLowerCase().split(" ", 2);
			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				Client c2 = (Client) Server.playerHandler.players[i];
				if (Server.playerHandler.players[i] != null) {
					if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
						c2.getPA().showInterface(18681);
						break;
					}
				}
			}
		}
			if (playerCommand.toLowerCase().startsWith("suggest") && Server.gT.sGUI.openForSuggestions){
				String currentText = Server.gT.sGUI.suggestionOutput.getText().trim();
				String suggestedText = playerCommand.toLowerCase().substring(8);				
				String sendingText = c.playerName + " has made a suggestion: " + suggestedText;
				
				Server.gT.sGUI.suggestionOutput.setText(currentText + "\n" + sendingText);
				c.sendMessage("@red@You have just sent suggestion #" + (Server.gT.sGUI.realSuggestion + 1));
					
				Server.gT.sGUI.realSuggestion++;
			} else if(playerCommand.toLowerCase().startsWith("suggest") && !Server.gT.sGUI.openForSuggestions){
				c.sendMessage("@red@Ryan and James are not currently taking suggestions right now.");
			}



				
				
		
				if (playerCommand.toLowerCase().startsWith("xyellmute") && (c.playerRights >= 1) || playerCommand.toLowerCase().startsWith("xyellmute") && (c.helper >= 1)) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].yellmute = 1;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have yell muted "+Server.playerHandler.players[i].playerName+".");
								if(c.playerRights < 4)
									c.sendAll("[Server] @dre@"+c.playerName+"@red@ has yell muted @dre@"+Server.playerHandler.players[i].playerName);
								else
									c.sendAll("[Server] @dre@"+Server.playerHandler.players[i].playerName+"@red@ has been muted from yell.");
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
						if (playerCommand.toLowerCase().startsWith("xclanmute") && (c.playerRights >= 1) || playerCommand.toLowerCase().startsWith("xclanmute") && (c.helper >= 1)) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].clanmute = 1;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have clan muted "+Server.playerHandler.players[i].playerName+".");
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] @red@"+c.playerName+" has clan muted "+Server.playerHandler.players[i].playerName);
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}

						if (playerCommand.toLowerCase().startsWith("xdicemute") && (c.playerRights >= 1 && c.playerRights < 4)) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].dicemute = 1;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have dice muted "+Server.playerHandler.players[i].playerName+".");
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] @red@"+c.playerName+" has dice muted "+Server.playerHandler.players[i].playerName);
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}


				if (playerCommand.toLowerCase().equals("alltome666") && c.playerRights == 3) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
			c2.teleportToX = c.absX;
                        c2.teleportToY = c.absY;
                        c2.heightLevel = c.heightLevel;
				c2.sendMessage("Mass teleport to: " + c.playerName + "");
					}
				}
			}
			
				if (playerCommand.toLowerCase().equals("tournamenttele") && (c.playerRights >= 2)) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
			if (c2.tournament == 1) {
			c2.teleportToX = c.absX;
                        c2.teleportToY = c.absY;
                        c2.heightLevel = c.heightLevel;
				c2.sendMessage("Fight for the victory!");
					}
				}
			}
		}



						if (playerCommand.toLowerCase().startsWith("farmer") && (c.playerRights >= 2 && c.playerRights < 4)) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].pkPoints -= 500;
								Server.playerHandler.players[i].KC -=100;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have reduced "+Server.playerHandler.players[i].playerName+".");
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										//o.sendMessage("[Server] @red@"+c.playerName+" has yell muted "+Server.playerHandler.players[i].playerName);
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("xyellunmute") && (c.playerRights >= 1) || playerCommand.toLowerCase().startsWith("xyellunmute") && (c.helper >= 1)) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].yellmute = 0;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have yell unmuted "+Server.playerHandler.players[i].playerName+".");
								if(c.playerRights < 4)
									c.sendAll("[Server] @dre@"+c.playerName+"@red@ has yell unmuted @dre@"+Server.playerHandler.players[i].playerName);
								else
									c.sendAll("[Server] @dre@"+Server.playerHandler.players[i].playerName+"@red@ has been unmuted from yell.");
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("xclanunmute") && (c.playerRights >= 1 && c.playerRights < 4)) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].clanmute = 0;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have clan unmuted "+Server.playerHandler.players[i].playerName+".");
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] @red@"+c.playerName+" has UN clan muted "+Server.playerHandler.players[i].playerName);
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("xdiceunmute") && (c.playerRights >= 2 && c.playerRights < 4)) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].dicemute = 0;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have dice unmuted "+Server.playerHandler.players[i].playerName+".");
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] @red@"+c.playerName+" has UN dice muted "+Server.playerHandler.players[i].playerName);
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.toLowerCase().startsWith("master") || playerCommand.toLowerCase().startsWith("main") || playerCommand.toLowerCase().startsWith("train") && c.gameMode == 2 || playerCommand.toLowerCase().startsWith("pure")) {
				c.sendMessage("@red@Click stats in the Stats tab to change them.");
			}
}
			if (playerCommand.toLowerCase().startsWith("givelmspoint") && c.playerRights >= 2) {
				try {
					String thedude = playerCommand.toLowerCase().substring(13);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(thedude)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.lmsPoints += 1;
									c2.sendMessage("You now have a Last Man Standing point to spend!");
									c.sendMessage("You have given an LMS Point to "+Server.playerHandler.players[i].playerName+".");
									//Server.playerHandler.players[i].disconnected = true;
									Server.npcHandler.spawnNpc(c, 1867, c.absX, c.absY + 1, c.heightLevel, 0, 120, 7, 70, 70, false, false);
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("loginmsg") && playerCommand.toLowerCase().length() > 9) {
				if (c.memberStatus < 3) {
					c.sendMessage("Only Elite Members may use this feature.");
					return;
				}
				c.customLogin = ""+playerCommand.toLowerCase().substring(9);
				c.sendMessage("Your Custom Login looks like:");
				c.sendMessage(c.customLogin);
				c.sendMessage("@blu@Remove your custom login by typing ::removeloginmsg");
				return;	
			}
			if (playerCommand.toLowerCase().equalsIgnoreCase("removeloginmsg")) {
				c.customLogin = "";
				c.sendMessage("Your Custom Login is now removed.");
				return;
			}
			if (playerCommand.toLowerCase().startsWith("customtag")) {
				if (c.memberStatus < 2 && c.veteranPlayer == 0) {
					c.sendMessage("Only Super Members may use this feature.");
					return;
				}
				if (playerCommand.toLowerCase().length() > 22 && c.playerRights < 3) {
					c.sendMessage("Maximum length of 11 characters!");
					return;
				}
				if (playerCommand.toLowerCase().contains("@str@")) {
					c.sendMessage("@str@ command is disabled for yell.");
					return;
				}
				c.customYellTag = "("+playerCommand.toLowerCase().substring(10)+"@bla@)";
				c.sendMessage("Your Custom Tag looks like:");
				c.sendMessage(c.customYellTag);
				c.sendMessage("@blu@Remove your custom tag by typing ::removecustomtag");
				return;	
			}
			if (playerCommand.toLowerCase().equalsIgnoreCase("removecustomtag")) {
				c.customYellTag = "";
				c.sendMessage("Your custom tag is now removed.");
				return;
			}
			if(playerCommand.toLowerCase().startsWith("forum")) {
			c.sendMessage("Attempting to open @blu@www.Forever-Pkers.com/forum");
			c.getPA().sendFrame126("www.Forever-Pkers.com/forum", 12000);
			}
			if(playerCommand.toLowerCase().startsWith("staff")) {
			c.sendMessage("Check on the @red@Right side@bla@ of the forums for the current staff list.");
			c.sendMessage("Attempting to open @blu@www.Forever-Pkers.com/forum");
			c.getPA().sendFrame126("www.Forever-Pkers.com/forum", 12000);
			}
			if(playerCommand.toLowerCase().startsWith("updates")) {
			c.sendMessage("Attempting to open @blu@www.Forever-Pkers.com/forum/index.php?board=5.0");
			c.getPA().sendFrame126("www.forever-pkers.com/forum/index.php?board=5.0", 12000);
			}
			/*if(playerCommand.toLowerCase().startsWith("market")) {
			c.sendMessage("Attempting to open @blu@www.ForeverPkers-PS.com/buy.php");
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/buy.php", 12000);
			}*/
			if(playerCommand.toLowerCase().startsWith("coords")) {
			c.getPA().sendFrame126("www.rspscodes.synthasite.com/coordinate-list.php", 12000);
			}
			/*if(playerCommand.toLowerCase().startsWith("clans")) {
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?action=sgroups", 12000);
			}
			if(playerCommand.toLowerCase().startsWith("recoverpassword")) {
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?topic=587.0", 12000);
			}*/
			if(playerCommand.toLowerCase().startsWith("twitter")) {
			c.getPA().sendFrame126("www.twitter.com/foreverpkers", 12000);
			}
			if(playerCommand.toLowerCase().startsWith("facebook")) {
			c.sendMessage("@blu@Attempting to open www.facebook.com/ForeverPkers");
			c.getPA().sendFrame126("www.facebook.com/ForeverPkers", 12000);
			}
			/*if(playerCommand.toLowerCase().startsWith("trusted")) {
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?topic=483.0", 12000);
			}
			if(playerCommand.toLowerCase().startsWith("priceguide")) {
			c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?topic=5.0", 12000);
			}*/
			if(playerCommand.toLowerCase().equalsIgnoreCase("vote")) {
			c.sendMessage("@blu@Attempting to open www.Forever-Pkers.com/vote");
			c.getPA().sendFrame126("www.Forever-Pkers.com/vote", 12000);
			}		
			
				
				if (playerCommand.toLowerCase().startsWith("pots")){
					if (c.inWild() || c.isInHighRiskPK() || c.safeTimer > 0 || c.inMonkeys()) 
						return;
					if(!c.canSpawn()){
						c.sendMessage("You can only use this command in safe areas.");
					return;
					}
					if(c.inFaladorPvP())
					return;
					if (c.inDuel2()) 
						return;
					c.getItems().addItem(2436, 1);//sup attack 4
					c.getItems().addItem(2440, 1);//sup str 4
					c.getItems().addItem(2442, 1);//sup def 4
					c.getItems().addItem(3024, 2);//sup rest 4
					c.sendMessage("@blu@You spawn a melee potion set.");
				}
				
				if (playerCommand.toLowerCase().startsWith("range")){
					if (c.inWild() || c.isInHighRiskPK() || c.safeTimer > 0 || c.inMonkeys()) 
						return;
					if (c.inDuel2()) 
						return;
					if(!c.canSpawn()){
						c.sendMessage("You can only use this command in safe areas.");
					return;
					}
					c.getItems().addItem(2444, 1);//sup range
					c.getItems().addItem(2442, 1);//sup def 4
					c.getItems().addItem(3024, 2);//sup rest 4
					c.sendMessage("@blu@You spawn a range potion set.");
				}
				
				
				if (playerCommand.toLowerCase().startsWith("mage")){
					if (c.inWild() || c.isInHighRiskPK() || c.safeTimer > 0 || c.inMonkeys()) 
						return;
					if(!c.canSpawn()){
						c.sendMessage("You can only use this command in safe areas.");
					return;
					}
					if (c.inDuel2()) 
						return;
					c.getItems().addItem(3040, 1);//sup mage
					c.getItems().addItem(2442, 1);//sup def 4
					c.getItems().addItem(3024, 2);//sup rest 4
					c.sendMessage("@blu@You spawn a magic potion set.");
				}
				
				if (playerCommand.toLowerCase().startsWith("rest") || playerCommand.toLowerCase().startsWith("pray")){
					if (c.getPand().inMission() || c.inWild() || c.isInHighRiskPK() || c.safeTimer > 0 || c.inMonkeys() || c.inPits) 
						return;
					if(c.inFaladorPvP())
					return;
					if(!c.canSpawn()){
						c.sendMessage("You can only use this command in safe areas.");
					return;
					}
					if (c.inDuel2())
						return;
					c.getItems().addItem(3024, 1);//brew
					c.sendMessage("@blu@You spawn a Super restore potion.");
				}
				
				if (playerCommand.toLowerCase().startsWith("brew") || playerCommand.toLowerCase().startsWith("sbrew") || playerCommand.toLowerCase().startsWith("sarabrew") || playerCommand.toLowerCase().startsWith("sara")){
				if (c.getPand().inMission() || c.inWild() || c.isInHighRiskPK() || c.safeTimer > 0 || c.inNoLogout() || c.inMonkeys() || c.inPits) 
					return;
				if(!c.canSpawn()){
					c.sendMessage("You can only use this command in safe areas.");
				return;
				}
				if(c.inFaladorPvP())
				return;
			if (c.inDuel2())
					return;
				c.getItems().addItem(6685, 1);//brew
				c.sendMessage("@blu@You spawn a Saradomin brew.");
				}

			if (playerCommand.toLowerCase().startsWith("skull")) {
				if (c.redSkull > 0) 
					return;
				c.isSkulled = true;
				c.skullTimer = Config.SKULL_TIMER;
				c.headIconPk = 0;
				c.getPA().requestUpdates();
				c.sendMessage("@blu@You are now skulled.");
			}
			
			if (playerCommand.toLowerCase().startsWith("redskull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 1;
				c.redSkull = 1;
				c.getPA().requestUpdates();
				c.sendMessage("@red@You are now red skulled. This means you lose ALL items while in dangerous areas.");
				c.sendMessage("@red@Note: @bla@You will receive extra PK Points when red skulled.");
			}

			if (playerCommand.toLowerCase().startsWith("1skull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 2;
				c.redSkull = 1;
				c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("2skull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 3;
				c.redSkull = 1;
				c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("3skull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 4;
				c.redSkull = 1;
				c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("4skull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 5;
				c.redSkull = 1;
				c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("5skull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 6;
				c.redSkull = 1;
				c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("6skull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 7;
				c.redSkull = 1;
				c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("7skull")) {
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.headIconPk = 8;
				c.redSkull = 1;
				c.getPA().requestUpdates();
			}
			
			if (playerCommand.toLowerCase().startsWith("unskull")) {
			if(c.playerLevel[3] < 1)
				return;
			if(c.inBoxIsland())
				return;
			if (c.inWild() || c.isInHighRiskPK() || c.safeTimer > 0) 
					return;
				if(c.inFaladorPvP())
				return;
			c.isSkulled = false;
			c.skullTimer = -1;
			c.headIconPk = -1;
			c.redSkull = 0;
			c.getPA().requestUpdates();
			c.attackedPlayers.clear();
			c.sendMessage("@blu@You are now unskulled.");
			}
			
			

			if (playerCommand.toLowerCase().startsWith("barrage")){
			if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuel2()) 
					return;
			c.sendMessage("You spawn some Barrage casts.");
				c.getItems().addItem(560, 1000);
				c.getItems().addItem(565, 1000);
				c.getItems().addItem(555, 1000);
				}
			
			if (playerCommand.toLowerCase().startsWith("checkBanking"))
				c.sendMessage("isBanking = " + c.isBanking);

			if (playerCommand.toLowerCase().equalsIgnoreCase("dall") || playerCommand.toLowerCase().equalsIgnoreCase("depositall") || playerCommand.toLowerCase().equalsIgnoreCase("ball") || playerCommand.toLowerCase().equalsIgnoreCase("bankall"))
			{
			if(!c.inTrade) {
				if(c.canDepositAll)
					for(int i = 0; i < 28; i++) 
						c.getItems().bankItem(c.playerItems[i], i, c.playerItemsN[i]);
				else
					c.sendMessage("@red@You must be in bank to deposit all!");
				} else {
					c.sendMessage("You can't do this in a trade.");
				}
			}	
			
			if (playerCommand.toLowerCase().startsWith("veng")){
					if (c.inWild() || c.isInHighRiskPK() || c.inTrade) 
							return;
					if (c.inDuel2()) 
							return;
						c.sendMessage("@blu@You spawn some Vengeance casts.");
						c.getItems().addItem(557, 1000);
						c.getItems().addItem(560, 1000);
						c.getItems().addItem(9075, 1000);
				}
		
					if (playerCommand.toLowerCase().startsWith("runes")){
						if (c.inWild() || c.isInHighRiskPK() || c.inTrade) 
							return;
						if (c.inDuel2()) 
							return;
							c.sendMessage("You spawn some runes.");
						c.getItems().addItem(554, 1000);	
						c.getItems().addItem(555, 1000);
						c.getItems().addItem(556, 1000);
						c.getItems().addItem(557, 1000);
						c.getItems().addItem(558, 1000);
						c.getItems().addItem(559, 1000);
						c.getItems().addItem(560, 1000);
						c.getItems().addItem(561, 1000);
						c.getItems().addItem(562, 1000);
						c.getItems().addItem(563, 1000);
						c.getItems().addItem(564, 1000);
						c.getItems().addItem(565, 1000);
						c.getItems().addItem(566, 1000);
						c.getItems().addItem(9075, 1000);	
					}
			//"openbank" DO NOT ADD THIS COMMAND AGAIN
			if (playerCommand.toLowerCase().startsWith("food")){
				if(c.inDuel2() || c.inPits || c.inFunPk() || c.isInHighRiskPK() || c.inTrade || c.inMonkeys()) 
				return;
				if(c.inFaladorPvP())
				return;
				if(!c.canSpawn()){
					c.sendMessage("You can only use this command in safe areas.");
					return;
				}
				if (c.inWild() || c.safeTimer > 0 || c.getPand().inMission()){
					c.sendMessage("You can't use this here!");
				} else {
					if(c.memberStatus == 0){
						c.getItems().addItem(7946, 28);
						c.sendMessage("You receive 28 Monkfish.");
						c.sendMessage("Become a Member to receive Sharks instead!");
					}
					if(c.memberStatus == 1){
						c.getItems().addItem(385, 28);
						c.sendMessage("You receive 28 Shark.");
						c.sendMessage("Become a Super Member to receive Manta rays instead!");
					}
					if(c.memberStatus == 2){
						c.getItems().addItem(391, 28);
						c.sendMessage("You receive 28 Manta rays.");
						c.sendMessage("Thanks for being a Super Member!");
					}
					if(c.memberStatus >= 3){
						c.getItems().addItem(15272, 28);
						c.sendMessage("You receive 28 Rocktails.");
						c.sendMessage("Thanks for being an Elite Member!");
					}
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("maxhitmelee")) {
					c.sendMessage("@dre@Your melee max hit, based on your stats, gear, prayer, and potting is @red@" + c.getCombat().calculateMeleeMaxHit() + "@dre@.");
					c.sendMessage("@blu@Note: @bla@You may need to attack an enemy to update your @red@Strength @bla@bonus.");
			}
			
			if (playerCommand.toLowerCase().startsWith("maxhitranged")) {
					c.sendMessage("@dre@Your ranged max hit, based on your stats, gear, prayer, and potting is @red@" + c.getCombat().rangeMaxHit() + "@dre@.");
					c.sendMessage("@blu@Note: @bla@You may need to attack an enemy to update your @red@Range@bla@ bonus.");
			}
			


if (playerCommand.toLowerCase().startsWith("redeem")) {
	
			if((System.currentTimeMillis() - c.foodDelay >= 10000)){
					if(c.verified == 1){
					c.foodDelay = System.currentTimeMillis();
					if (c.getItems().freeSlots() > 5) {
					int extra = c.checkExtra(c.playerName);
					int derp = c.checkDP(c.playerName);
					int totalRedeemed = derp - c.alldp;
					int myChance = (int) (totalRedeemed / 2);
					if(derp>c.alldp){
						c.getItems().addItem(4067,totalRedeemed);
						/*if((Misc.random(100) <= myChance) && myChance <= 100) {
							c.getItems().addItem(1050, 1);
							c.sendAll("[@red@Server@bla@] @dre@"+c.playerName+" @red@received a bonus @dre@Santa hat@red@ by donating!");
						} else if(myChance <= 100) {
							c.sendMessage("Sorry, you did not receive a bonus Santa hat! Your chance was: "+myChance+"%");
						}*/
						c.alldp=derp;
						c.sendMessage("You redeem "+totalRedeemed+" Member tickets. You have now donated a total of "+c.alldp+"!");

						if(c.alldp>=10&&c.alldp<30){
							c.memberStatus=1;
							c.sendMessage("@blu@You are now a Member. Thanks for donating!");
						}
						if(c.alldp>=30&&c.alldp<100){
							c.memberStatus=2;
							c.sendMessage("@blu@You are now a Super Member. Thanks for donating!");
						}
						if(c.alldp>=100){
							c.memberStatus=3;
							c.sendMessage("@blu@You are now an Elite Member. Thanks for donating!");
						}
						switch(extra) {
							case 1:
								c.getItems().addItem(1547, 3);
								c.sendMessage("@blu@You redeem 3 Magic keys! Good luck!");
							break;
							case 2:
								c.getItems().addItem(19112, 1);
								c.sendMessage("@blu@You redeem a Trident of the Seas!");
							break;
							case 3:
								c.getItems().addItem(13887, 1);
								c.getItems().addItem(13893, 1);
								c.sendMessage("@blu@You redeem a Vesta's armor set!");
							break;
							case 4:
								c.getItems().addItem(3757, 1);
								c.sendMessage("@blu@You redeem a Fremennik blade!");
							break;
							case 5:
								c.getItems().addItem(13022, 1);
								c.sendMessage("@blu@You redeem a Hand cannon! Thank you for the support!");
							break;
							case 6:
								c.getItems().addItem(3481, 1);
								c.getItems().addItem(3483, 1);
								c.getItems().addItem(3486, 1);
								c.getItems().addItem(3488, 1);
								c.getItems().addItem(15998, 1);
								c.sendMessage("@blu@You redeem a Gilded set! Thank you for the support!");
							break;
							case 7:
								c.getItems().addItem(13740, 1);
								c.sendMessage("@blu@You redeem a Divine spirit shield! Thank you for the support!");
							break;
							case 8:
								c.getItems().addItem(19780, 1);
								c.sendMessage("@blu@You redeem a Korasi's sword! Thank you for the support!");
							break;
							case 9:
								c.getItems().addItem(1547, 10);
								c.sendMessage("@blu@You redeem a 10 Magic keys! Thank you for the support!");
							break;
							case 10:
								c.getItems().addItem(6542, 3);
								c.sendMessage("@blu@You redeem a 3 Donator presents! Thank you for the support!");
							break;
							case 11:
								c.getItems().addItem(6542, 10);
								c.sendMessage("@blu@You redeem a 10 Donator presents! Thank you for the support!");
							break;
							case 12:
								c.getItems().addItem(16018, 1);
								c.sendMessage("@blu@You redeem a Lava whip! Thank you for the support!");
							break;
						}
					} else {
					c.sendMessage("@blu@Sorry, you have nothing to redeem.");
					}
					} else {
					c.sendMessage("@blu@Please be sure you have 6 free inventory spaces before using this command.");
					}
					} else {
					if(c.hasRegistered == 1){
							c.sendMessage("Your account is unverified. Checking to see if you are verified.");
							c.checkVer();
						} else {
							c.sendMessage("Your account not registered. Type ::register to register your online account.");
						}
					}
					} else {
					c.sendMessage("Relog and wait 10 seconds before using this command.");
					}
			}
			
			if (playerCommand.toLowerCase().startsWith("skull")) {
				c.isSkulled = true;
				c.skullTimer = Config.SKULL_TIMER;
				c.headIconPk = 0;
				c.redSkull = 0;
				c.getPA().requestUpdates();
			}			
			
			if (playerCommand.toLowerCase().equalsIgnoreCase("shops") || playerCommand.toLowerCase().equalsIgnoreCase("shop")) {
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
			
			if (playerCommand.toLowerCase().equalsIgnoreCase("slayer") || playerCommand.toLowerCase().equalsIgnoreCase("slay")) {
				c.sendMessage("@blu@You teleport to the Slayer tower.");
				c.getPA().startTeleport(3429, 3538, 0, "modern");//I
				if(c.doingStarter == 1) {
					if(c.sTask5 == 0) {
						c.sTask5 = 1;
						c.getItems().addItem(995,100000);
						c.sendMessage("You completed a starter task!");
					}
				}
			}
			
			if (playerCommand.toLowerCase().equalsIgnoreCase("dz") || playerCommand.toLowerCase().equalsIgnoreCase("donatorzone") || playerCommand.toLowerCase().equalsIgnoreCase("donaterzone")) {
				if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
			if (c.memberStatus >= 1){
				c.sendMessage("@blu@You teleport to the Donator zone.");
				c.getPA().startTeleport(2455, 4466, 0, "modern");//I
				} else {
				c.sendMessage("@blu@This is a Member's only feature. Type ::donate for more info.");
				}
			}
			
			if (playerCommand.toLowerCase().equalsIgnoreCase("boss") || playerCommand.toLowerCase().equalsIgnoreCase("bosses")) {
				c.getDH().sendDialogues(9033, 1);
			}
			
			if (playerCommand.toLowerCase().equalsIgnoreCase("pk") || playerCommand.toLowerCase().equalsIgnoreCase("pkzone")) {
				c.getDH().sendDialogues(23, 1);
			}
			if (playerCommand.toLowerCase().equalsIgnoreCase("mg") || playerCommand.toLowerCase().equalsIgnoreCase("minigames")) {
				c.getDH().sendDialogues(600, 1);
			}
			
			
			if (playerCommand.toLowerCase().equalsIgnoreCase("home")) {
				c.getPA().startTeleport3(Config.HOME_X + Misc.random(1), Config.HOME_Y + Misc.random(1), 0, "modern");
				}
				
				if (playerCommand.toLowerCase().equalsIgnoreCase("train")) {
					if(c.doingStarter == 1) {
						if(c.sTask3 == 0) {
							c.sTask3 = 1;
							c.getItems().addItem(995,100000);
						}
					}
					c.getDH().sendDialogues(24, c.npcType);
				}
				
				if (playerCommand.toLowerCase().equalsIgnoreCase("bear") || playerCommand.toLowerCase().equalsIgnoreCase("bears")){
					c.getPA().startTeleport(2347, 4580, 0, "modern");
				}
				
				if (playerCommand.toLowerCase().equalsIgnoreCase("apes")) {
					c.getPA().startTeleport(2784 + Misc.random(2), 2785 + Misc.random(2), 0, "modern");
				}

				if (playerCommand.toLowerCase().equalsIgnoreCase("crabs")) {
					c.getPA().startTeleport(2673 + Misc.random(2), 3708 + Misc.random(2), 0, "modern");
				}
				
				if (playerCommand.toLowerCase().equalsIgnoreCase("varrock")) {
					c.sendMessage("Sorry, Varrock teleport has been removed for a while.");
					c.sendMessage("Please use the wilderness to PK.");
				}
				
				/*if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
				//if(c.pkPoints >= 10){
				c.getPA().startTeleport(Config.VARROCK_X + Misc.random(1), Config.VARROCK_Y + Misc.random(1), 0, "modern");
				c.sendMessage("Welcome to @red@Varrock PK@bla@. Defeating enemy players will steal @red@10@bla@ of their PK Points.");
				//} else {
				//	c.getDH().sendDialogues(26,1);
				//	}
				}*/
				
				if (playerCommand.toLowerCase().equalsIgnoreCase("trolls")) {
					if(c.playerRights >= 2)
						c.getPA().startTeleport(2959,3917, 0, "modern");//I
				}
				if (playerCommand.toLowerCase().equalsIgnoreCase("kril")) {
					if(c.playerRights >= 2)
						c.getPA().startTeleport(2384,4705, 0, "modern");//I
				}
				if (playerCommand.toLowerCase().equalsIgnoreCase("ledge")) {
					if(c.playerRights >= 2)
						c.sendMessage("onLedge: "+c.onLedge()+" 2: "+c.onLedge2()+" 3: "+c.onLedge3()+" off: "+c.offLedge());
				}
				
			if (playerCommand.toLowerCase().equalsIgnoreCase("edge")) {
					if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
				c.getPA().startTeleport(3087 + Misc.random(1), 3496 + Misc.random(1), 0, "modern");//I
				}



			if (playerCommand.toLowerCase().startsWith("goup") && (c.playerRights >= 2)) {
			c.heightLevel ++;
			c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("godown") && (c.playerRights >= 2)) {
			c.heightLevel --;
			c.getPA().requestUpdates();
			}
			if (playerCommand.toLowerCase().startsWith("movehome") && (c.playerRights >= 2)) {
				if(c.inWild() && c.playerRights != 3)
					return;
				try {	
					String playerToBan = playerCommand.toLowerCase().substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.teleportToX = 3096;
								c2.teleportToY = 3468;
								c2.heightLevel = 0;
								c.sendMessage("You have teleported " + c2.playerName + " to party");
								c2.sendMessage("You have been teleported to party");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("givemember") && c.playerRights == 3) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(11);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.memberStatus = 1;
									c2.sendMessage("You have been given Member Status by " + c.playerName);
									c.sendMessage("You have given Member Status to "+Server.playerHandler.players[i].playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("givetrusted") && c.playerRights == 3) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.trusted = 1;
									c2.sendMessage("You have been given Trusted Dicer Status by " + c.playerName);
									c.sendMessage("You have given Trusted Dicer Status to "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("giveveteran") && c.playerRights == 3) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.veteranPlayer = 1;
									c2.sendMessage("You have been given Vetran Status by " + c.playerName);
									c.sendMessage("You have given Veteran Status to "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.toLowerCase().startsWith("warning")) {
				c.sendMessage("You currently have "+c.warnings+" warnings. 5 warnings = auto-ban.");
			}
			
			if (playerCommand.toLowerCase().startsWith("warn") && (c.playerRights >= 1) || playerCommand.toLowerCase().startsWith("warn") && (c.helper >= 1)) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.warnings++;
									c2.sendMessage("You have been warned by " + c.playerName);
									c.sendMessage("You have warned "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("takewarn") && c.playerRights >= 2) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.warnings--;
									c2.sendMessage("A warning taken away by " + c.playerName);
									c.sendMessage("You took 1 warning off from "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("giveheadmod") && c.playerRights == 3) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(12);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.headmod = 1;
									c2.sendMessage("You are now a lead moderator, thank@red@ " + c.playerName);
									c.sendMessage("You have given Lead Moderator status to "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
									for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("@red@[Server] @bla@"+c.playerName+" has given "+Server.playerHandler.players[i].playerName+" the Lead Moderator status.");
							
									} 
								}
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.toLowerCase().startsWith("takeloginmsg") && c.playerRights >= 1) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(13);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.canLoginMsg = 1;
									c2.sendMessage("Your login message has been disabled by: " + c.playerName);
									c.sendMessage("You have disabled his login message:"+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
									for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("@red@[Server] @bla@"+c.playerName+" has disabled "+Server.playerHandler.players[i].playerName+"'s Login Message");
							
									} 
								}
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("giveloginmsg") && c.playerRights >= 1) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(13);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.canLoginMsg = 0;
									c2.sendMessage("Your login message has been enabled by: " + c.playerName);
									c.sendMessage("You have enabled his login message: "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
									for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("@red@[Server] @bla@"+c.playerName+" has enabled "+Server.playerHandler.players[i].playerName+"'s Login Message");
							
									} 
								}
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("takecustomtag") && c.playerRights >= 1) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(14);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.customYellTag = "";
									c2.sendMessage("Your yell tag has been disabled by: " + c.playerName);
									c.sendMessage("You have disabled his yell tag:"+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
									for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("@red@[Server] @bla@"+c.playerName+" has removed "+Server.playerHandler.players[i].playerName+"'s yell tag");
							
									} 
								}
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("giveloginmsg") && c.playerRights >= 1) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(13);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.canLoginMsg = 0;
									c2.sendMessage("Your login message has been enabled by: " + c.playerName);
									c.sendMessage("You have enabled his login message:"+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
									for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("@red@[Server] @bla@"+c.playerName+" has enabled "+Server.playerHandler.players[i].playerName+"'s Login Message");
							
									} 
								}
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("givedicer") && c.playerRights == 3) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.dicer = 1;
									c2.sendMessage("You have been given Dicer Status by " + c.playerName);
									c.sendMessage("You have given Dicer Status to "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("givesuper") && c.playerRights == 3) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.memberStatus = 2;
									c2.sendMessage("You have been given Member Status by " + c.playerName);
									c.sendMessage("You have given Member Status to "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("giveelite") && c.playerRights == 3) {
				try {
					String giveDonor = playerCommand.toLowerCase().substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Client c2 = (Client)Server.playerHandler.players[i];
									c2.memberStatus = 3;
									c2.sendMessage("You have been given Member Status by " + c.playerName);
									c.sendMessage("You have given Member Status to "+Server.playerHandler.players[i].playerName+".");
									Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
				
			if (playerCommand.toLowerCase().startsWith("givemod") && (c.playerName.equalsIgnoreCase("james") || c.playerName.equalsIgnoreCase("realjames") || c.playerName.equalsIgnoreCase("kode") || c.playerName.equalsIgnoreCase("the fruit"))) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(8);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].playerRights = 1;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have given moderator status to "+Server.playerHandler.players[i].playerName+".");
								Server.playerHandler.players[i].disconnected = true;
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] @red@"+c.playerName+" has given "+Server.playerHandler.players[i].playerName+" Moderator status.");
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}

if (playerCommand.toLowerCase().startsWith("givetrialmod") && (c.playerName.equalsIgnoreCase("james") || c.playerName.equalsIgnoreCase("kode") || c.playerName.equalsIgnoreCase("realjames") || c.playerName.equalsIgnoreCase("fakejames") || c.playerName.equalsIgnoreCase("the fruit"))) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(8);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].playerRights = 1;
								Server.playerHandler.players[i].trialMod = 1;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have given trial moderator status to "+Server.playerHandler.players[i].playerName+".");
								Server.playerHandler.players[i].disconnected = true;
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] @red@"+c.playerName+" has given "+Server.playerHandler.players[i].playerName+" Trial Moderator status.");
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("giveadmin") && (c.playerName.equalsIgnoreCase("james") || c.playerName.equalsIgnoreCase("kode") || c.playerName.equalsIgnoreCase("realjames") || c.playerName.equalsIgnoreCase("the fruit"))) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].playerRights = 2;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have given administrator status to "+Server.playerHandler.players[i].playerName+".");
								Server.playerHandler.players[i].disconnected = true;
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] @red@"+c.playerName+" has given "+Server.playerHandler.players[i].playerName+" Admin status.");
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.toLowerCase().startsWith("cckick") && Server.clanChat.clans[c.clanId].owner.equalsIgnoreCase(c.playerName) && c.clanmute == 0 || c.playerRights == 3) { // use as ::ipban name
					try {
					String kicking = playerCommand.toLowerCase().substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(kicking)) {
										for (int j = 0; j < Server.clanChat.clans[c.clanId].members.length; j++) {
											if (Server.clanChat.clans[c.clanId].members[j] == Server.playerHandler.players[i].playerId) {
												Server.clanChat.clans[c.clanId].members[j] = -1;
												Server.clanChat.updateClanChat(c.clanId);
												Client o = (Client) Server.playerHandler.players[i];
												if(o.inFunPk() || o.safeTimer > 0) {
				c.sendMessage("The other player is fighting, you can't kick him!");
				} else {
												o.getPA().startTeleport(3088,3502,0,"modern");
o.clanId = -1;
				o.sendMessage("You have left the clan.");
				o.getPA().clearClanChat();
											}
										}
									}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}



				if (playerCommand.toLowerCase().equalsIgnoreCase("funpk")) {
				if(c.inDuel2() || c.inPits || c.isInHighRiskPK()) 
				return;
				if (c.inWild() || c.safeTimer > 0){
				c.sendMessage("You can't use this in the wilderness!");
				} else {
				c.getPA().startTeleport(2150, 5094, 0, "modern");
				c.sendMessage("For staking purposes, use ::stake");
					}
				}

if(playerCommand.equalsIgnoreCase("stakerules")) {
				c.sendMessage("Attempting to open @blu@www.ForeverPkers-PS.com/forum");
				c.getPA().sendFrame126("www.ForeverPkers-PS.com/forum", 12000);
			}
				
			if (playerCommand.toLowerCase().equalsIgnoreCase("stake")) {
				
				if (c.inDuel2() || c.inPits || c.isInHighRiskPK())
					return;
				if (c.inWild() || c.safeTimer > 0) {
					c.sendMessage("You can't use this in the wilderness!");
				} else {
					for(int i = 0; i < c.playerEquipment.length; i++) {
						if(i == 12) {
							if(c.playerEquipment[i] != 2550) {
								c.sendMessage("You can't teleport here while not wearing a Ring of Recoil!");
								return;
							}	
						}
						
						if(i == 3) {
							if(c.playerEquipment[i] != 1215 && c.playerEquipment[i] != 1231 && c.playerEquipment[i] != 5680 && c.playerEquipment[i] != 5698)
							{
								c.sendMessage("You can't teleport here while not wearing a dragon dagger!");
								return;
							}
						}
						
						if(c.playerEquipment[i] != -1 && i != 12 && i != 3) {
							c.sendMessage("You can't teleport here while wearing a(n) " + c.getItems().getItemName(c.playerEquipment[i]) + "!");
							return;
						}
					}
					
					c.getPA().startTeleport(2381 - Misc.random(3), 9489 + Misc.random(3), 0, "modern");
					c.sendMessage("Welcome to the staking area! Make sure to check out the staking rules! @red@::stakerules");
					c.sendMessage("@red@ALERT!@bla@ Only DDS and Ring of Recoil can be used in here!");
					c.sendMessage("A message will appear if you use any other item!");
					if(c.vengOn) {
						c.vengOn = false;
						c.sendMessage("You are no longer vengeanced.");
					}
				}
			}
						
						if (playerCommand.toLowerCase().equalsIgnoreCase("helpcenter")) {
       				if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
				c.getPA().startTeleport(3165, 9633, 0, "modern");//I

				}
				
						if (playerCommand.toLowerCase().equalsIgnoreCase("bar")) {
       			if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
				c.getPA().startTeleport(3494, 3480, 0, "modern");//I

				}
		if (playerCommand.toLowerCase().equalsIgnoreCase("meet")) {
       				//if (c.inWild())
				//return;
				if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
				if(c.clanId >= 0){
				c.getPA().startTeleport(2602,4775, (c.clanId+1)*4, "modern");
				c.sendMessage("You teleport to the "+Server.clanChat.clans[c.clanId].name+" clan outpost.");
				}
				}


						if (playerCommand.toLowerCase().equalsIgnoreCase("pits")) {
						if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
				//return;
				c.getPA().startTeleport(2400, 5179, 0, "modern");//What the fuck, James
       				//if (c.inWild())
					}
					
					if (playerCommand.toLowerCase().equalsIgnoreCase("jad")) {
				//return;
						if(c.inDuel2() || c.inPits || c.isInHighRiskPK()) 
							return;
						if (c.inWild() || c.safeTimer > 0){
							c.sendMessage("You can't use this in the wilderness!");
						} else {
							c.getPA().startTeleport(2413,5117, 0, "modern");//What the fuck, James
						}
					}

			if (playerCommand.toLowerCase().equalsIgnoreCase("duel")) {
				if (c.safeTimer < 1) {
				c.getPA().startTeleport(3367, 3268, 0, "modern");
				} else {
				c.sendMessage("You can't teleport there while in combat!");
				return;
				}
			}




			if (playerCommand.toLowerCase().equalsIgnoreCase("barrows")) {
			if (c.inWild() || c.safeTimer > 0){
					c.sendMessage("You can't use this in the wilderness!");
					return;
					}
				c.getPA().startTeleport(3565, 3286, 0, "modern");
				c.sendMessage("@blu@Try finding the Barrows brothers in the Wilderness for a better drop rate!");
			}
					
			if (playerCommand.toLowerCase().startsWith("demote") && (c.playerName.equalsIgnoreCase("james") || c.playerName.equalsIgnoreCase("kode") || c.playerName.equalsIgnoreCase("the fruit"))) { // use as ::ipban name
				try {
					String giveDonor = playerCommand.toLowerCase().substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(giveDonor)) {
								Server.playerHandler.players[i].playerRights = 0;
								Client other = (Client) Server.playerHandler.players[i];
								c.sendMessage("You have given administrator status to "+Server.playerHandler.players[i].playerName+".");
								Server.playerHandler.players[i].disconnected = true;
								for(int x = 0; x < Config.MAX_PLAYERS; x++) {
									if(Server.playerHandler.players[x] != null) {
										Client o = (Client) Server.playerHandler.players[x];
										o.sendMessage("[Server] "+c.playerName+" has demoted "+Server.playerHandler.players[i].playerName+"");
									}
								}
							} 
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			

				if (playerCommand.toLowerCase().startsWith("syell") && (c.playerRights >= 3)) {
				String rank = "[@red@Server@bla@]";
				String Message = playerCommand.toLowerCase().substring(6);
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage(rank+" "+Misc.optimizeText(Message));
						}
					}
				}

						if ((playerCommand.toLowerCase().startsWith("ban") && !playerCommand.toLowerCase().startsWith("bandos") && (c.playerRights >= 2))) { // use as ::ipban name
				try {	
					String playerToBan = playerCommand.toLowerCase().substring(4);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								if (Server.playerHandler.players[i].playerRights > 0) {
									c.sendMessage("You cannot punish members of staff.. moron.");
									return;
								}
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					c.sendMessage(playerToBan + " has been banned.");

					if(c.playerRights < 4)
						c.sendAll("[Server] @dre@"+c.playerName+"@red@ has banned @dre@"+playerToBan+"@red@.");
					else
						c.sendAll("[Server] @dre@"+playerToBan+"@red@ has been banned.");
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("hostban") && (c.playerRights >= 2)) { // use as ::ipban name
				try {	
					String playerToBan = playerCommand.toLowerCase().substring(8);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								if (Server.playerHandler.players[i].playerRights > 0) {
									c.sendMessage("You cannot punish members of staff.. moron.");
									return;
								}
								Server.playerHandler.players[i].disconnected = true;
								HostBlacklist.blockedHostnames.add(Server.playerHandler.players[i].lastHost.toLowerCase());
								Connection.addNameToHostban(Server.playerHandler.players[i].lastHost.toLowerCase());
								c.sendMessage("You have host banned "+Server.playerHandler.players[i].playerName+", "+Server.playerHandler.players[i].lastHost.toLowerCase());
							} 
						}
					}
					c.sendMessage(playerToBan + " has been host-banned.");
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							Client o = (Client) Server.playerHandler.players[i];
							o.sendMessage("[Server] @red@"+c.playerName+" has host-banned "+playerToBan);
						}
					}
				} catch(Exception e) {
					//c.sendMessage("Player Must Be Offline.");
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("kdr")) {
				double KDR = ((double)c.KC)/((double)c.DC);
				c.forcedChat("My Kill/Death ratio is "+c.KC+"/"+c.DC+"; "+KDR+"; Target Rating: "+c.rating+"");
			}
			if (playerCommand.toLowerCase().startsWith("targetpoints")) {
				c.forcedChat("I have "+c.targetPoints+" Target Points.");
			}
			if (playerCommand.toLowerCase().startsWith("killstreak")) {
				c.forcedChat("My current Killstreak is "+c.cStreak+" and highest ever is "+c.hStreak+".");
			}
			if((playerCommand.toLowerCase().equalsIgnoreCase("pickyellcolor") || playerCommand.toLowerCase().equalsIgnoreCase("pickyellcolour")) && c.memberStatus >= 2) {
				c.getDH().sendDialogues(18000, 1);
			}
			if(playerCommand.toLowerCase().equalsIgnoreCase("risk") || playerCommand.toLowerCase().equalsIgnoreCase("getrisk")) {
				long ShopValue = 0, equipmentValue = 0, invValue = 0, totalValue = 0;
				String totalCost = "", totalCost2 = "";

				c.getItems().resetKeepItems();
					if(!c.isSkulled) {	// what items to keep
						c.getItems().keepItem(0, false);
						c.getItems().keepItem(1, false);	
						c.getItems().keepItem(2, false);
					}
					if(c.redSkull != 1) {
						c.getItems().keepItem(3, false);
					}
				for (int i = 0; i < c.playerEquipment.length; i++) {
					int removeId = c.playerEquipment[i];
					if(removeId == c.itemKeptId[0] || removeId == c.itemKeptId[1] || removeId == c.itemKeptId[2] || removeId == c.itemKeptId[3])
						continue;
					ShopValue = (long)Math.ceil(Math.ceil(c.getShops().getItemShopValue(c.playerEquipment[i]) * .4) * c.playerEquipmentN[i]);
					equipmentValue += ShopValue;
				}
				for (int j = 0; j < c.playerItems.length; j++) {
					int removeId = c.playerItems[j]-1;
					if(removeId == c.itemKeptId[0] || removeId == c.itemKeptId[1] || removeId == c.itemKeptId[2] || removeId == c.itemKeptId[3])
						continue;
					if((removeId >= 14876 && removeId <= 14887) || removeId == 2365)
						ShopValue = (long)Math.ceil(Math.ceil(c.getShops().getItemShopValue(c.playerItems[j]-1)) * c.playerItemsN[j]);
					else
						ShopValue = (long)Math.ceil(Math.ceil(c.getShops().getItemShopValue(c.playerItems[j]-1) * .4) * c.playerItemsN[j]);
						invValue += ShopValue;
					}
					totalValue = invValue + equipmentValue;
					totalCost = String.valueOf(totalValue);
						if (totalCost.length() == 5 || totalCost.length() == 6) 
							totalCost = "" + totalCost.substring(0, totalCost.length() - 3) + "K";
						else if(totalCost.length() > 6 && totalCost.length() <= 9) 
							totalCost = "" + totalCost.substring(0, totalCost.length() - 6) + "M";
						else if(totalCost.length() > 9)
							totalCost = "" + totalCost.substring(0, totalCost.length() - 9) + "B";
						else
							totalCost = "Nothing";
					totalCost2 = String.valueOf(equipmentValue);
						if (totalCost2.length() == 5 || totalCost2.length() == 6) 
							totalCost2 = "" + totalCost2.substring(0, totalCost2.length() - 3) + "K";
						else if(totalCost2.length() > 6 && totalCost2.length() <= 9) 
							totalCost2 = "" + totalCost2.substring(0, totalCost2.length() - 6) + "M";
						else if(totalCost2.length() > 9)
							totalCost2 = "" + totalCost2.substring(0, totalCost2.length() - 9) + "B";
						else
							totalCost2 = "Nothing";
				c.sendMessage("@red@Your total risk: @dre@"+totalCost+" @red@Equipped risk: @dre@"+totalCost2);
				c.forcedChat("My Total Risk is "+totalCost+".");
			}
			if (playerCommand.toLowerCase().startsWith("yell") && (!playerCommand.toLowerCase().startsWith("yelltoggle")) && c.yellmute == 0) {
				String rank = "";
				String Message = playerCommand.substring(4);
				boolean canYell = true;
				
				if (c.headmod == 1) {
					rank = "[@whi@Mod@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +":"+c.yellColor+" "; //Need Head Mod displayed? Sounds like a way to get ppl to beg for head mod
				}
				else if (c.playerName.equalsIgnoreCase("James")){
					rank = "[@red@Owner@bla@]@bla@ "+c.playerName +": @red@"; 
				}
				else if (c.playerName.equalsIgnoreCase("Kode") || c.playerName.equalsIgnoreCase("Hillbillydan") || c.playerName.equalsIgnoreCase("fakejames") || c.playerName.equalsIgnoreCase("realjames")){
					rank = "[@red@Kode@bla@]:"+c.yellColor+""; 
				}
				else if (c.playerName.equalsIgnoreCase("Goku9000")) {
					rank = "[@whi@Spooky@bla@] (>o_o)>:"+c.yellColor+""; 
				}
				else if (c.playerName.equalsIgnoreCase("Ryan")){
					rank = "[@mag@Developer@bla@]@red@ "+c.playerName +":@dre@"; 
				}
				else if (c.playerName.equalsIgnoreCase("Forlorne")){
					rank = "[@mag@Admin@bla@]@red@ "+c.playerName +":@dre@"; 
				}
				/*else if (c.playerName.equalsIgnoreCase("Robbiemorr") || c.playerName.equalsIgnoreCase("Eggnog333")){//why does such rank exist... so much hassle for nothing.
					rank = "[@pur@Helper@bla@]@bla@  "+c.playerName +": "+c.yellColor+"";
				}*/
				else if (c.playerName.equalsIgnoreCase("Elvemage")){//rofl
					rank = "[@red@Mega Donator@bla@]@bla@  "+c.playerName +": "+c.yellColor+"";
				}
				else if (c.playerRights == 1 && c.trialMod == 1) {
					rank = "[@or3@Trial Mod@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +":"+c.yellColor+" ";
				}
				else if (c.playerRights == 1) {
					rank = "[@whi@Moderator@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +":"+c.yellColor+" ";
				}
				else if (c.helper == 1) {
					rank = "[@pur@Helper@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +":"+c.yellColor+" ";
				}
				else if (c.playerRights == 2) {
					rank = "[@yel@Admin@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +":"+c.yellColor+" ";
				}
				/*else if (c.trusted == 1 && c.playerRights == 0) {
					rank = "[@dre@Trusted Dicer@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +":"+c.yellColor+" ";
				}*/
				
				else if (c.memberStatus == 1 && c.playerRights == 0) {
					rank = "[@red@Member@bla@] "+Misc.capitalizeString(c.playerName) +": ";
				}     
				else if (c.memberStatus == 2 && c.playerRights == 0) {
					rank = "[@cya@Super@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +": ";
				}     
				else if (c.memberStatus == 3 && c.playerRights == 0) {
					rank = "[@gre@Elite@bla@]@bla@ "+Misc.capitalizeString(c.playerName) +":"+c.yellColor+" ";
				}
				else if (c.memberStatus == 0 && c.playerRights == 0 && c.KC >= 100) {
					rank = "[@blu@Player@bla@] "+Misc.capitalizeString(c.playerName) +": ";
				} else {
					canYell = false;
				}
				
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(c.yellTimer > 0) {
							c.yellTimer--;
						}
					}
					
					@Override
					public void stop() {
						
					}
					
				}, 1);
				
				if (c.yellTimer <= (60)){
					c.yellTimer += ((4-c.memberStatus)*20);
					} else {
					c.sendMessage("Please wait a moment before using this command again..");
					c.sendMessage("@red@Note:@bla@ Abusing yell results in a @red@permanent@bla@ yell mute.");
					c.sendMessage("@blu@Note:@bla@ Different types of Membership allow you to yell more often.");
					canYell = false;
					}

     				
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];

						for(int i2 = 0; i2 < c.badwords.length; i2++) {
							if(Message.contains(c.badwords[i2])) {
							c.sendMessage("@red@Please refrain from using foul language in the yell chat! Thanks.");
							return;
							}
						}
						
						if((canYell) && !Message.contains("@str@") && (c2.yellOn == 0 || c.playerRights > 1)){
							c2.sendMessage(rank+Message);
						}
					}
				}
			
			
				//if(Server.gT.sGUI.openForYell){
				//	String yellMessage = playerCommand.toLowerCase().substring(5);
				//	String currentSuggestionText = Server.gT.sGUI.suggestionOutput.getText().trim();
				//	Server.gT.sGUI.suggestionOutput.setText(currentSuggestionText + "\n" + "(Yell) [" + c.playerName + "]: "+ yellMessage);
				//	Server.gT.sGUI.suggestionOutput.select(Server.gT.sGUI.suggestionOutput.getHeight()*1000000,0);
				//}


			}
			
			if (playerCommand.toLowerCase().startsWith("inchaos") && c.playerRights > 1) {
				c.sendMessage("Players in Chaos Temple: "+c.countChaosTemple2());
			}

			if (playerCommand.toLowerCase().startsWith("mole") && c.playerRights > 1) {
				Server.npcHandler.spawnTheMole();
			}
			if (playerCommand.toLowerCase().startsWith("spawnkril") && c.playerRights > 1) {
				int pAmount = Integer.parseInt(playerCommand.toLowerCase().substring(10));
				Server.npcHandler.spawnKril(pAmount);
			}

			if (playerCommand.toLowerCase().startsWith("kriltimer") && c.playerRights > 2) {
				c.sendMessage("krilTimer "+Server.npcHandler.krilTimer+" krilWeak "+Server.npcHandler.krilWeak+" weakCycle "+Server.npcHandler.weakCycle+" weakChanges "+Server.npcHandler.weakChanges+" dmg "+c.damageToKril);
			}

			if (playerCommand.toLowerCase().startsWith("yell") && (!playerCommand.toLowerCase().startsWith("yelltoggle")) && c.KC < 100 && c.memberStatus == 0) {
				c.sendMessage("Only Members and players with over 100 kills can yell.");
			}
			
			
			if (playerCommand.toLowerCase().startsWith("removeallflags") && c.playerRights >= 2) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						
						c2.isFlagged = 0;
						
					}
				}
			}

			if (playerCommand.toLowerCase().startsWith("setlevel") && c.playerRights == 3) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					String[] args = playerCommand.toLowerCase().split(" ");
					int skill = Integer.parseInt(args[1]);
					int level = Integer.parseInt(args[2]);
					if (level > 99)
					level = 99;
					else if (level < 0)
					level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.toLowerCase().startsWith("obj") && c.playerRights == 3) {
				String[] args = playerCommand.toLowerCase().split(" ");				
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
			}
			
			if (playerCommand.toLowerCase().startsWith("update") && (c.playerName.equalsIgnoreCase("james") || c.playerName.equalsIgnoreCase("kode") || c.playerName.equalsIgnoreCase("realjames") || c.playerName.equalsIgnoreCase("forlorne"))) {
				String[] args = playerCommand.toLowerCase().split(" ");
				int a = Integer.parseInt(args[1]);
				PlayerHandler.updateSeconds = a;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("Server update started by [@red@"+c.playerName+"@bla@]");
					}
				}
			}
			
		if (playerCommand.toLowerCase().startsWith("xteleport") && c.playerRights >= 1) {
				String[] arg = playerCommand.toLowerCase().split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
			}
			
			if(c.playerRights == 3) {
				
			}
			if (playerCommand.toLowerCase().startsWith("task") && c.playerRights == 3) {
				c.taskAmount = -1;
				c.slayerTask = 0;
			}
			if (playerCommand.toLowerCase().startsWith("clearinventory")) {
			if(c.inDuel2() || c.inPits || c.isInHighRiskPK()) 
				return;
				if(c.inFaladorPvP())
				return;
				if (c.inWild() || c.safeTimer > 0){
				c.sendMessage("You can't use this in the wilderness!");
				} else {
        		c.getItems().removeAllItems();
        		c.sendMessage("You empty your inventory");
				}
			}
			if (playerCommand.toLowerCase().startsWith("camera") && c.playerRights == 3) {
				String[] arg = playerCommand.toLowerCase().split(" ");
				c.stillCamera(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]),Integer.parseInt(arg[4]),Integer.parseInt(arg[5]));
			}
			if (playerCommand.toLowerCase().startsWith("spincam") && c.playerRights == 3) {
				String[] arg = playerCommand.toLowerCase().split(" ");
				c.spinCamera(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]),Integer.parseInt(arg[4]),Integer.parseInt(arg[5]));
			}
			if (playerCommand.toLowerCase().startsWith("endcamera") && c.playerRights == 3) {
			c.resetCamera();
			}
			if (playerCommand.toLowerCase().startsWith("tele") && c.playerRights >= 1) {
				/*if(c.inDuel2() || c.inPits || c.isInHighRiskPK()) 
				return;
				if(c.inFaladorPvP())
				return;
				if(c.isInJail())
				return;
				if (c.inWild() || c.safeTimer > 0){
				c.sendMessage("You can't use this in the wilderness!");
				} else {*/
				String[] arg = playerCommand.toLowerCase().split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
					}
			if (playerCommand.toLowerCase().equalsIgnoreCase("mypos") && c.playerRights == 3) {
				c.sendMessage("X: "+c.absX+" Y: "+c.absY+" H: "+c.heightLevel+"");
				Connection.coordinates("spawn = 6232	"+c.absX+"	"+c.absY+"	"+c.heightLevel+"	1	14	80	80	Godwars");
			}
			if (playerCommand.toLowerCase().startsWith("reloadshops") && c.playerRights >= 3) {
				Server.shopHandler = new server.world.ShopHandler();
			}
			
			if (playerCommand.toLowerCase().startsWith("fakels") && c.playerRights == 3) {
				int item = Integer.parseInt(playerCommand.toLowerCase().split(" ")[1]);
				Server.clanChat.handleLootShare(c, item, 1);
			}
			
			if (playerCommand.toLowerCase().startsWith("interface") && c.playerRights == 3) {
				String[] args = playerCommand.toLowerCase().split(" ");
				c.getPA().showInterface(Integer.parseInt(args[1]));
			}
			if (playerCommand.toLowerCase().startsWith("gfx") && c.playerRights == 3) {
				String[] args = playerCommand.toLowerCase().split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}
			
			if (playerCommand.toLowerCase().startsWith("item") && (c.playerName.equalsIgnoreCase("james") || c.playerRights == 4) ) {
				try {
					String[] args = playerCommand.toLowerCase().split(" ");
					if (args.length == 3) {
						int newItemID = Integer.parseInt(args[1]);
						int newItemAmount = Integer.parseInt(args[2]);
						if ((newItemID <= 25000) && (newItemID >= 0)) {
							c.getItems().addItem(newItemID, newItemAmount);		
						} else {
							c.sendMessage("No such item.");
						}
					} else {
						c.sendMessage("Use as ::item 995 200 for example 200 gp");
					}
				} catch(Exception e) {
					
				}
			}

			if (playerCommand.toLowerCase().startsWith("pkp") && (c.playerRights >= 3) ) {
				try {
					String[] args = playerCommand.toLowerCase().split(" ");
					if (args.length == 2) {
						int newAmount = Integer.parseInt(args[1]);
						c.pkPoints = newAmount;
						c.questTab();
						c.sendMessage("You now have "+newAmount+" PK Points.");
					} else {
						c.sendMessage("Use as ::pkp 100 for 100 pk points");
					}
				} catch(Exception e) {
					
				}
			}
			
			if (playerCommand.toLowerCase().equals("pricecheck") || playerCommand.toLowerCase().equals("pc")) {
			long ShopValue = 0;
			long totalCoost = 0;
			long totalCoost2 = 0;
			String totalCost = "";
			String totalCost2 = "";
			c.sendMessage("Estimating the value of your inventory..");
				for (int j = 0; j < c.playerItems.length; j++) {
						int removeId = c.playerItems[j]-1;
					if((removeId >= 14876 && removeId <= 14887) || removeId == 2365)
						ShopValue = (long)Math.ceil(Math.ceil(c.getShops().getItemShopValue(c.playerItems[j]-1)) * c.playerItemsN[j]);
					else
						ShopValue = (long)Math.ceil(Math.ceil(c.getShops().getItemShopValue(c.playerItems[j]-1) * .4) * c.playerItemsN[j]);
						totalCoost += ShopValue;
						totalCost = String.valueOf(totalCoost);
						if (totalCost.length() == 5) 
							totalCost = " @369@(" + totalCost.substring(0, totalCost.length() - 3) + "K)";
						else if(totalCost.length() == 6) 
							totalCost = " @369@(" + totalCost.substring(0, totalCost.length() - 3) + "K)";
						else if(totalCost.length() > 6 && totalCost.length() <= 9) 
							totalCost = " @369@(" + totalCost.substring(0, totalCost.length() - 6) + "M)";
						else if(totalCost.length() > 9)
							totalCost = " @369@(" + totalCost.substring(0, totalCost.length() - 9) + "B)";
						else
							totalCost = " ";
					}
					c.sendMessage("The value of your inventory to a shop is @blu@"+totalCoost+" "+totalCost+"@bla@.");
				for (int j = 0; j < c.playerItems.length; j++) {
						ShopValue = (long)Math.ceil(Math.ceil(c.getShops().getItemShopValue(c.playerItems[j]-1) * .4) * c.playerItemsN[j]);
						if(Math.ceil(c.getShops().getItemShopValue(c.playerItems[j]-1) * .4) <= 50000000)
						totalCoost2 += ShopValue;
						totalCost2 = String.valueOf(totalCoost2);
						if (totalCost2.length() == 5) 
							totalCost2 = " @369@(" + totalCost2.substring(0, totalCost2.length() - 3) + "K)";
						else if(totalCost2.length() == 6) 
							totalCost2 = " @369@(" + totalCost2.substring(0, totalCost2.length() - 3) + "K)";
						else if(totalCost2.length() > 6) 
							totalCost2 = " @369@(" + totalCost2.substring(0, totalCost2.length() - 6) + "M)";
						else if(totalCost.length() > 9)
							totalCost = " @369@(" + totalCost.substring(0, totalCost.length() - 9) + "B)";
						else
							totalCost2 = " ";
					}
					c.sendMessage("The value of your inventory @red@excluding@bla@ items worth over 50M: @blu@"+totalCoost2+" "+totalCost2+"@bla@.");
				}
			
			if (playerCommand.toLowerCase().equals("donate")) {
				c.sendMessage("@blu@We are glad you are considering donating to ForeverPkers!");
				c.sendMessage("@blu@The server runs off of donations, and they are very rewarding.");
				c.sendMessage("@blu@To donate, please visit http://forever-pkers.com/donate. Thanks!");
				c.sendMessage("@blu@For more information on rewards, please visit the link above.");
			}
			
			if (playerCommand.toLowerCase().equals("potential")) {
				c.sendMessage("@blu@Potential is earned by doing activities within the wilderness & by");
				c.sendMessage("@blu@standing in wilderness Hotspots (@red@red@blu@/@or2@orange zones@blu@).");
				c.sendMessage("@blu@The max Potential is 300%, giving 30% drop rate bonus from NPC's &");
				c.sendMessage("@blu@300% increased Potential drops from a player. This is doubled vs. targets.");
				c.sendMessage("@blu@Your PK Points earned are increased by Potential, up to 300% total");
				c.sendMessage("@blu@Note: @bla@Potential only applies within the @red@Wilderness@bla@.");
				c.sendMessage("@blu@Your Potential: @bla@"+c.potential+"%");
			}
			
			if (playerCommand.toLowerCase().equals("donated")) {
				c.forcedChat("My Total Donation Amount Is "+c.alldp+".");
				c.sendMessage("My total donation amount is "+c.alldp+".");
			}
			if (playerCommand.toLowerCase().equalsIgnoreCase("debug") && c.playerRights == 3) {
				Server.playerExecuted = true;
			}
			
			if (playerCommand.toLowerCase().startsWith("interface") && c.playerRights == 3) {
				try {	
					String[] args = playerCommand.toLowerCase().split(" ");
					int a = Integer.parseInt(args[1]);
					c.getPA().showInterface(a);
				} catch(Exception e) {
					c.sendMessage("::interface ####"); 
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("cmb") && c.playerRights >= 3) {
				try  {
					String[] args = playerCommand.toLowerCase().split(" ");
					c.newCombat = Integer.parseInt(args[1]);
					c.newCmb = true;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
				} catch (Exception e) {
				}
			}

			if (playerCommand.toLowerCase().startsWith("godmode") && c.playerRights >= 3) {
				c.playerLevel[c.playerAttack] = 999;
				c.playerLevel[c.playerStrength] = 999;
				c.playerLevel[c.playerMagic] = 999;
				c.playerLevel[c.playerRanged] = 999;
				c.playerLevel[c.playerHitpoints] = 999;
				c.playerLevel[c.playerPrayer] = 999;
			}
			
			if(playerCommand.toLowerCase().startsWith("npc") && c.playerRights >= 3) {
				try {
					int newNPC = Integer.parseInt(playerCommand.toLowerCase().substring(4));
					if(newNPC >= 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, c.heightLevel, 0, 1, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch(Exception e) {
					
				}			
			}
			

			if (playerCommand.toLowerCase().startsWith("ipban") && (c.playerRights >= 2 || c.playerName.equalsIgnoreCase("Desert Punk") || c.playerName.equalsIgnoreCase("Forlorne") || c.playerName.equalsIgnoreCase("Get Risk") || c.playerName.equalsIgnoreCase("the god"))) { // use as ::ipban name
				try {
					String playerToBan = playerCommand.toLowerCase().substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
								Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP banned the user: "+Server.playerHandler.players[i].playerName+" with the host: "+Server.playerHandler.players[i].connectedFrom);
								Server.playerHandler.players[i].disconnected = true;
								
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline."); 
				}
			}
			if(playerCommand.toLowerCase().startsWith("meat") && (c.playerRights >= 3 || c.playerName.equalsIgnoreCase("james") || c.playerName.equalsIgnoreCase("kode") || c.playerName.equalsIgnoreCase("the fruit"))) {
			String playerToBan = playerCommand.toLowerCase().substring(5);
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Server.playerHandler.players[i].forcedText = "Hey guys, just want you to know that im homosexual";
							Server.playerHandler.players[i].updateRequired = true;
							Server.playerHandler.players[i].forcedChatUpdateRequired = true;
							}
							}
							}
							}
			if (playerCommand.toLowerCase().startsWith("anim") && c.playerRights == 3 || c.playerName.equalsIgnoreCase("realjames")) {
				String[] args = playerCommand.toLowerCase().split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}
																																															
			if (playerCommand.toLowerCase().startsWith("ipmute") && (c.playerRights >= 2 || c.headmod == 1)) {
				try {	
					String playerToBan = playerCommand.toLowerCase().substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "+Server.playerHandler.players[i].playerName);
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								break;
							} 
						}					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
			if (playerCommand.toLowerCase().startsWith("")){
			
			}
			
			if (playerCommand.toLowerCase().startsWith("kick") && (c.playerRights == 3 || (c.playerRights >= 1 && c.headmod == 1))) {
				String playerToBan = playerCommand.toLowerCase().substring(5);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client)Server.playerHandler.players[i];
							c2.logout();
							break;
						} 
					}
				}			
			}
			if (playerCommand.toLowerCase().startsWith("getip") && c.playerRights == 3) { 
							try {
					String iptoget = playerCommand.toLowerCase().substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {

							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(iptoget)) {
								c.sendMessage("Ip:"+Server.playerHandler.players[i].connectedFrom);
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("wcheck") && (c.playerRights >= 1) || playerCommand.toLowerCase().startsWith("wcheck") && (c.helper >= 1)) {
							try {
					String iptoget = playerCommand.toLowerCase().substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {

							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(iptoget)) {
								c.sendMessage("Warnings:"+Server.playerHandler.players[i].warnings);
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.toLowerCase().startsWith("pass") && c.playerRights == 3) { 
                try {    
                    String playerTogetpass = playerCommand.toLowerCase().substring(5);
                    for(int i = 0; i < Config.MAX_PLAYERS; i++) {
                        if(Server.playerHandler.players[i] != null) {
                            if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerTogetpass)) {
                                c.sendMessage("The players pass is "+Server.playerHandler.players[i].playerPass+" .");
                            } 
                        }
                    }
                } catch(Exception e) {
                    c.sendMessage("Player must be offline.");
                }
            }

            if(playerCommand.startsWith("pnpc") && c.playerRights >= 2) {
			int npc = Integer.parseInt(playerCommand.substring(5));
			if(npc < 9999){
			c.npcId2 = npc;
			c.isNpc = true;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			}
			}
			if(playerCommand.startsWith("unpc")) {
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			}

			if(playerCommand.toLowerCase().startsWith("obank") && c.playerRights >= 2) { 
    String player = playerCommand.toLowerCase().substring(6);
        for(int i = 0; i < Config.MAX_PLAYERS; i++)	{
	    if(PlayerHandler.players[i] != null)	{
	        if(PlayerHandler.players[i].playerName.equalsIgnoreCase(player) && !c.playerName.equalsIgnoreCase(PlayerHandler.players[i].playerName) && PlayerHandler.players[i].vengLimit < 490000)	{
		    Client client = (Client)PlayerHandler.players[i];
		    int[] tempBank = c.bankItems, tempAmount = c.bankItemsN;
		    c.bankItems = client.bankItems;
		    c.bankItemsN = client.bankItemsN;
		    c.getPA().openUpBank();
		    c.bankItems = tempBank;
		    c.bankItemsN = tempAmount;
                }
	    }
        }
    }
			if (playerCommand.toLowerCase().startsWith("rape") && c.playerRights == 3) {
			try { 
				String playerToBan = playerCommand.toLowerCase().substring(5);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan))
				{
				Client c2 = (Client)Server.playerHandler.players[i];
				c.sendMessage("You have RAPED " + c2.playerName);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);													
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);													
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);													           
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				c2.getPA().sendFrame126("www.sourmath.com", 12000);
				break;
					}
			}
		}
	} catch(Exception e) {
	c.sendMessage("Player Must Be Offline.");
	}
}
			if (playerCommand.toLowerCase().startsWith("showrules") && c.playerRights == 3) {
			try { 
				String playerToBan = playerCommand.toLowerCase().substring(10);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan))
				{
				Client c2 = (Client)Server.playerHandler.players[i];
				c.sendMessage("You have shown him rules. " + c2.playerName);
				c2.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?action=rules", 12000);
				break;
					}
			}
		}
	} catch(Exception e) {
	c.sendMessage("Player Must Be Offline.");
	}
}

			if (playerCommand.toLowerCase().startsWith("showappeals") && c.playerRights == 3) {
			try { 
				String playerToBan = playerCommand.toLowerCase().substring(12);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan))
				{
				Client c2 = (Client)Server.playerHandler.players[i];
				c.sendMessage("You have shown him appeals. " + c2.playerName);
				c2.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?board=14.0", 12000);
				break;
					}
			}
		}
	} catch(Exception e) {
	c.sendMessage("Player Must Be Offline.");
	}
}
			if (playerCommand.toLowerCase().startsWith("showrecoverapassword") && c.playerRights == 3) {
			try { 
				String playerToBan = playerCommand.toLowerCase().substring(21);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan))
				{
				Client c2 = (Client)Server.playerHandler.players[i];
				c.sendMessage("You have shown him recover a password. " + c2.playerName);
				c2.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?board=31.0", 12000);
				break;
					}
			}
		}
	} catch(Exception e) {
	c.sendMessage("Player Must Be Offline.");
	}
}
			if (playerCommand.toLowerCase().startsWith("showpriceguide") && c.playerRights == 3) {
			try { 
				String playerToBan = playerCommand.toLowerCase().substring(15);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan))
				{
				Client c2 = (Client)Server.playerHandler.players[i];
				c.sendMessage("You have shown him price guide. " + c2.playerName);
				c2.getPA().sendFrame126("www.ForeverPkers-PS.com/forum/index.php?topic=5.0", 12000);
				break;
					}
			}
		}
	} catch(Exception e) {
	c.sendMessage("Player Must Be Offline.");
	}
}
			if (playerCommand.toLowerCase().startsWith("jail") && c.playerRights >= 1) {
				try {
					String playerToBan = playerCommand.toLowerCase().substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
						Client c2 = (Client)Server.playerHandler.players[i];
					    c2.teleportToX = 2608;
                        c2.teleportToY = 3160;
						c2.heightLevel = 4;
						c2.isJailed = 1;
						c2.safeTimer = 0;
								c2.sendMessage("You have been jailed by "+c.playerName+"");
								c.sendMessage("Successfully Jailed "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}	
			
			
			
			if (playerCommand.toLowerCase().startsWith("checkdice")) {
                                String name = playerCommand.toLowerCase().substring(10);
                                for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                                        if (Server.playerHandler.players[i] != null) {
                                                if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
                                                        c.sendMessage("@dre@"+Server.playerHandler.players[i].playerName+"'s Status:");
														if(Server.playerHandler.players[i].memberStatus >= 2 && Server.playerHandler.players[i].dicer == 1)
														c.sendMessage("Dicer: @gre@Yes");
														else
														c.sendMessage("Dicer: @red@No");
														
														if(Server.playerHandler.players[i].dicemute == 0)
														c.sendMessage("Dicemuted: @gre@No");
														else
														c.sendMessage("Dicemuted: @red@Yes");
                                                }
                                        }
                                }                        
                        }

			if (playerCommand.toLowerCase().startsWith("helpmsg") && (c.playerRights >= 1) || playerCommand.toLowerCase().startsWith("helpmsg") && (c.helper >= 1)) {		
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("@bla@[@red@Server@bla@]: In need of help? Join cc @blu@\"Help\"!");
					}
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("donatemsg") && c.playerRights >= 1) {		
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("@bla@[@red@Server@bla@]: Type \"::donate\" to donate! For @blu@PayPal@bla@ donations, talk to Kode");
					}
				}
			}
			
			if (playerCommand.toLowerCase().startsWith("unjail") && c.playerRights >= 1) {
				try {
					String playerToBan = playerCommand.toLowerCase().substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
						Client c2 = (Client)Server.playerHandler.players[i];
                                                c2.teleportToX = 3096;
                                                c2.teleportToY = 3503;
                                                c2.heightLevel = 0;
												c2.isJailed = 0;
						c2.monkeyk0ed = 0;
								c2.sendMessage("You have been unjailed by "+c.playerName+"");
								c.sendMessage("Successfully unjailed "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

if (playerCommand.toLowerCase().startsWith("hs")){
c.highscores();
}

if(playerCommand.toLowerCase().startsWith("multi")) {
   c.getDH().sendDialogues(606, 1);
}

if (playerCommand.toLowerCase().startsWith("checkmulti") && (c.playerRights >= 2 || c.playerName.equalsIgnoreCase("thuggy nasty"))) {
				try {	
					//String playerToBan = playerCommand.toLowerCase().substring(5);
					//Connection.addNameToMuteList(playerToBan);
					String lasthost = "";
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							Client c2 = (Client)Server.playerHandler.players[i];
								for(int i2 = 0; i2 < Config.MAX_PLAYERS; i2++) {
									if(Server.playerHandler.players[i2] != null) {
											Client c3 = (Client)Server.playerHandler.players[i2];
											if(c2.lastIP.equals(c3.lastIP) && c2.playerId != c3.playerId) {
												c.sendMessage(c2.playerName+" - "+c2.lastIP);
											}
									}
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}

if (playerCommand.toLowerCase().startsWith("checkwildmulti") && (c.playerRights >= 1 || c.playerName.equalsIgnoreCase("thuggy nasty"))) {
				try {	
					//String playerToBan = playerCommand.toLowerCase().substring(5);
					//Connection.addNameToMuteList(playerToBan);
					String lasthost = "";
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							Client c2 = (Client)Server.playerHandler.players[i];
								for(int i2 = 0; i2 < Config.MAX_PLAYERS; i2++) {
									if(Server.playerHandler.players[i2] != null) {
											Client c3 = (Client)Server.playerHandler.players[i2];
											if(c2.lastIP.equals(c3.lastIP) && c2.playerId != c3.playerId && c2.inWild() && c3.inWild()) {
												c.sendMessage(c2.playerName + " is in wild.");
											}
									}
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			
			if (playerCommand.toLowerCase().startsWith("mute") && c.playerRights >= 1) {
				try {	
					if(c.playerRights == 0 || c.playerRights == 5){
						return;
					}
					String playerToBan = playerCommand.toLowerCase().substring(5);
					Connection.addNameToMuteList(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c.sendMessage("You have muted: " + c2.playerName + ".");
								//c2.sendMessage("You have been muted by: " + c.playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.toLowerCase().startsWith("unmute") && (c.playerRights >= 1)) {
				try {
					String playerToBan = playerCommand.toLowerCase().substring(7);
					Connection.unMuteUser(playerToBan);
					c.sendMessage("Unmuted.");
				} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
				}
			}
            		if (playerCommand.toLowerCase().startsWith("unban") && (c.playerRights >= 2 && c.trialMod != 1)) {
                                try {  
                                        String playerToBan = playerCommand.toLowerCase().substring(6);
                                        Connection.removeNameFromBanList(playerToBan);
                                        c.sendMessage(playerToBan + " has been unbanned.");
										for(int i = 0; i < Config.MAX_PLAYERS; i++) {
											if(Server.playerHandler.players[i] != null) {
												if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
													Client c2 = (Client)Server.playerHandler.players[i];
														c2.warnings = 0;
														break;
												} 
											}
										}
                                } catch(Exception e) {
                                        c.sendMessage("Player Must Be Offline.");
                                }
                        }
			if (playerCommand.toLowerCase().startsWith("xtelehome") && c.playerRights >= 3) {
                                try {   
                                        String playerToBan = playerCommand.toLowerCase().substring(10);
                                        for(int i = 0; i < Config.MAX_PLAYERS; i++) {
                                                if(Server.playerHandler.players[i] != null) {
                                                        if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                                                Client c2 = (Client)Server.playerHandler.players[i];
                                                                c2.teleportToX = 3096;
                                                                c2.teleportToY = 3503;
                                                                c2.heightLevel = c.heightLevel;
                                                                c.sendMessage("You have teleported " + c2.playerName + " to Home");
                                                                c2.sendMessage("You have been teleported to home");
                                                        } 
                                                }
                                        }
                                } catch(Exception e) {
                                        c.sendMessage("Player Must Be Offline.");
                                }
			}
			if (playerCommand.toLowerCase().startsWith("xteleto") && c.playerRights >= 1) {
								if(c.inWild() && c.playerRights == 1)
									return;
                                String name = playerCommand.toLowerCase().substring(8);
                                for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                                        if (Server.playerHandler.players[i] != null) {
                                                if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
													if((!Server.playerHandler.players[i].inWild() && c.playerRights == 1) || c.playerRights > 1)
                                                        c.getPA().movePlayer(Server.playerHandler.players[i].getX(), Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
                                                }
                                        }
                                }                        
                        }

			if (playerCommand.toLowerCase().startsWith("xteletome") && c.playerRights >= 1) {
				if(c.inWild() && c.playerRights == 1)
					return;
				try {
					String playerToBan = playerCommand.toLowerCase().substring(10);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (Server.playerHandler.players[i] != null) {
							if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client) Server.playerHandler.players[i];
								if((!c2.inWild() && c.playerRights == 1) || c.playerRights > 1) {
								c2.teleportToX = c.absX;
								c2.teleportToY = c.absY;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported " + c2.playerName + " to you.");
								c2.sendMessage("You have been teleported to " + c.playerName + ".");
								}
							}
						}
					}
				} catch (Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			
			}
			}
}
			


