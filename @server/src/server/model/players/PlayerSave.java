package server.model.players;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import server.Server;
import server.util.Misc;

public class PlayerSave
{
	
	/**
	*Loading
	**/
	public static int loadGame(Client p, String playerName, String playerPass) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;
		
		
		
		try {
			characterfile = new BufferedReader(new FileReader("./Data/characters/"+playerName+".txt"));
			File1 = true;
		} catch(FileNotFoundException fileex1) {
		}
		
		if (File1) {
			//new File ("./characters/"+playerName+".txt");
		} else {
			Misc.println(playerName+": character file not found.");
			p.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(playerName+": error loading file.");
			return 3;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (ReadMode) {
				case 1:
					 if (token.equals("character-password")) {
						if (playerPass.equalsIgnoreCase(token2) || Misc.basicEncrypt(playerPass).equals(token2)) {
							playerPass = Misc.basicEncrypt(playerPass);
						} else {
							return 3;
						}
					}
					break;
				case 2:
					if (token.equals("character-height")) {
						p.heightLevel = Integer.parseInt(token2);
					} else if (token.equals("character-posx")) {
						p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3222 : Integer.parseInt(token2));
					} else if (token.equals("character-posy")) {
						p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3222 : Integer.parseInt(token2));
					} else if (token.equals("character-rights")) {
						p.playerRights = Integer.parseInt(token2);
					} else if (token.equals("trialMod")) {
						p.trialMod = Integer.parseInt(token2);
					} else if (token.equals("helper")) {
						p.helper = Integer.parseInt(token2);
					} else if (token.equals("oldPassword1")) {
						p.oldPass1 = token2;
					} else if (token.equals("oldPassword2")) {
						p.oldPass2 = token2;
					} else if (token.equals("character-yellTag")) {
						p.customYellTag = token2;
					} else if (token.equals("character-login")) {
						p.customLogin = token2;
					} else if (token.equals("character-yellColor")) {
						p.yellColor = token2;
					} else if (token.equals("character-nameColor")) {
						p.nameColor = token2;
					} else if (token.equals("tutorial-progress")) {
						p.tutorial = Integer.parseInt(token2);	
					} else if (token.equals("crystal-bow-shots")) {
						p.crystalBowArrowCount = Integer.parseInt(token2);
					} else if (token.equals("skull-timer")) {
						p.skullTimer = Integer.parseInt(token2);
					} else if (token.equals("noob-timer")) {
						p.noobTimer = Integer.parseInt(token2);
					} else if (token.equals("play-time")) {
						p.pTime = Integer.parseInt(token2);
					} else if (token.equals("magic-book")) {
						p.playerMagicBook = Integer.parseInt(token2);
					} else if (token.equals("brother-info")) {
						p.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer.parseInt(token3[1]);
					 } else if (token.equals("special-amount")) {
						p.specAmount = Double.parseDouble(token2);	
					 } else if (token.equals("selected-coffin")) {
						p.randomCoffin = Integer.parseInt(token2);
					} else if (token.equals("targetPoints")) {
						p.targetPoints = Integer.parseInt(token2);
					} else if (token.equals("totalTargetPoints")) {
						p.totalTargetPoints = Integer.parseInt(token2);
					} else if (token.equals("weaponTask")) {
						p.weaponTask = Integer.parseInt(token2);
					} else if (token.equals("brightness")) {
						p.brightness = Integer.parseInt(token2);
					} else if (token.equals("teleblock-length")) {
						p.teleBlockDelay = System.currentTimeMillis();
						p.teleBlockLength = Integer.parseInt(token2);							
					} else if (token.equals("pc-points")) {
						p.pcPoints = Integer.parseInt(token2);
					} else if (token.equals("pandPoints")) {
						p.pandPoints = Integer.parseInt(token2);
					} else if (token.equals("totalPandPoints")) {
						p.totalPandPoints = Integer.parseInt(token2);
					} else if (token.equals("bonusPand")) {
						p.bonusPand = Integer.parseInt(token2);
					} else if (token.equals("lastDay")) {
						p.lastDay = Integer.parseInt(token2);
					} else if (token.equals("currentPPoints")) {
						p.currentPPoints = Integer.parseInt(token2);
					} else if (token.equals("currentKC")) {
						p.currentKC = Integer.parseInt(token2);
					} else if (token.equals("bestPand")) {
						p.bestPand = Integer.parseInt(token2);
					} else if (token.equals("bossesKilled")) {
						p.bossesKilled = Integer.parseInt(token2);
					} else if (token.equals("scoreReset")) {
						p.scoreReset = Integer.parseInt(token2);
					} else if (token.equals("lastKilled")) {
						p.lastKilled = token2;
					} else if (token.equals("lmsPoints")) {
						p.lmsPoints = Integer.parseInt(token2);
					} else if (token.equals("pkChallenge")) {
						p.pkChallenge = Integer.parseInt(token2);
					} else if (token.equals("vengLimit")) {
						p.vengLimit = Integer.parseInt(token2);
					} else if (token.equals("KC")) {
						p.KC = Integer.parseInt(token2);	
					} else if (token.equals("DC")) {
						p.DC = Integer.parseInt(token2);
					} else if (token.equals("gotShit")) {
						p.gotShit = Integer.parseInt(token2);
					} else if (token.equals("targFights")) {
						p.targFights = Integer.parseInt(token2);
					} else if (token.equals("rating")) {
						p.rating = Integer.parseInt(token2);
					} else if (token.equals("recoilHits")) {
						p.recoilHits = Integer.parseInt(token2);					
					} else if (token.equals("slayerTask")) {
						p.slayerTask = Integer.parseInt(token2);					
					} else if (token.equals("taskAmount")) {
						p.taskAmount = Integer.parseInt(token2);
					} else if (token.equals("myTask")) {
						p.myTask = Integer.parseInt(token2);
					} else if (token.equals("taskPoints")) {
						p.taskPoints = Integer.parseInt(token2);
					} else if (token.equals("totalTaskPoints")) {
						p.totalTaskPoints = Integer.parseInt(token2);
					} else if (token.equals("magePoints")) {
						p.magePoints = Integer.parseInt(token2);
					} else if(token.equals("CurrentStreak")) {
						p.cStreak = Integer.parseInt(token2);
					} else if (token.equals("bankPin")) {
						p.bankPin = token2;
					} else if (token.equals("setPin")) {
						p.setPin = Boolean.parseBoolean(token2);
					} else if(token.equals("HighestStreak")) {
						p.hStreak = Integer.parseInt(token2);
					} else if(token.equals("wStreak")) {
						p.wStreak = Integer.parseInt(token2);
					} else if(token.equals("potential")) {
						p.potential = Integer.parseInt(token2);
					 } else if(token.equals("ban-start")) {
                   			  p.banStart = Long.parseLong(token2);
                 			  } else if(token.equals("ban-end")) {
                   			  p.banEnd = Long.parseLong(token2);
					} else if(token.equals("lampStart")) {
                   		p.lampStart = Long.parseLong(token2);
					} else if(token.equals("lampStart2")) {
                   		p.lampStart2 = Long.parseLong(token2);
                   	} else if(token.equals("dPortalDelay")) {
                   		p.dPortalDelay = Long.parseLong(token2);
					} else if (token.equals("isflagged2")) {
						p.isFlagged = Integer.parseInt(token2);
					} else if (token.equals("isSevereflagged")) {
						p.isSevereFlagged = Boolean.parseBoolean(token2);		
					} else if (token.equals("checkInv")) {
						p.checkInv = Integer.parseInt(token2);	
					} else if (token.equals("autoRet")) {
						p.autoRet = Integer.parseInt(token2);					
					} else if (token.equals("barrowskillcount")) {
						p.barrowsKillCount = Integer.parseInt(token2);
					} else if (token.equals("flagged")) {
						p.accountFlagged = Boolean.parseBoolean(token2);
					} else if (token.equals("wave")) {
						p.waveId = Integer.parseInt(token2);
					} else if (token.equals("void")) {
						for (int j = 0; j < token3.length; j++) {
							p.voidStatus[j] = Integer.parseInt(token3[j]);						
						}
					} else if (token.equals("gwkc")) {
						p.killCount = Integer.parseInt(token2);
					} else if (token.equals("fightMode")) {
						p.fightMode = Integer.parseInt(token2);
					} else if (token.equals("Monkey Kc")) {
						p.monkeyk0ed = Integer.parseInt(token2);
					} else if (token.equals("tradeTimer")) {
						p.tradeTimer = Integer.parseInt(token2);
					} else if (token.equals("pkPoints")) {
						p.pkPoints = Integer.parseInt(token2);
					} else if (token.equals("eventScore")) {
						p.eventScore = Integer.parseInt(token2);
					} else if (token.equals("eventId")) {
						p.eventId = Integer.parseInt(token2);
					} else if (token.equals("redSkull")) {
						p.redSkull = Integer.parseInt(token2);
					} else if (token.equals("pkpTimer")) {
						p.pkpTimer = Integer.parseInt(token2);
					} else if (token.equals("memberStatus")) {
						p.memberStatus = Integer.parseInt(token2);
					} else if (token.equals("gameMode")) {
						p.gameMode = Integer.parseInt(token2);
					} else if (token.equals("xpLock")) {
						p.xpLock = Integer.parseInt(token2);
					} else if (token.equals("yellmute")) {
						p.yellmute = Integer.parseInt(token2);
					} else if (token.equals("veteranPlayer")) {
						p.veteranPlayer = Integer.parseInt(token2);
					} else if (token.equals("verified")) {
						p.verified = Integer.parseInt(token2);
					} else if (token.equals("yellOn")) {
						p.yellOn = Integer.parseInt(token2);
					} else if (token.equals("announceOn")) {
						p.announceOn = Integer.parseInt(token2);
					} else if (token.equals("clanmute")) {
						p.clanmute = Integer.parseInt(token2);
					} else if (token.equals("stuckTimer")) {
						p.stuckTimer = Integer.parseInt(token2);
					} else if (token.equals("isJailed")) {
						p.isJailed = Integer.parseInt(token2);
					} else if (token.equals("doingStarter")) {
						p.doingStarter = Integer.parseInt(token2);
					} else if (token.equals("sTask1")) {
						p.sTask1 = Integer.parseInt(token2);
					} else if (token.equals("sTask2")) {
						p.sTask2 = Integer.parseInt(token2);
					} else if (token.equals("sTask3")) {
						p.sTask3 = Integer.parseInt(token2);
					} else if (token.equals("sTask4")) {
						p.sTask4 = Integer.parseInt(token2);
					} else if (token.equals("sTask5")) {
						p.sTask5 = Integer.parseInt(token2);
					} else if (token.equals("sTask6")) {
						p.sTask6 = Integer.parseInt(token2);
					} else if (token.equals("sTask7")) {
						p.sTask7 = Integer.parseInt(token2);
					} else if (token.equals("sTask8")) {
						p.sTask8 = Integer.parseInt(token2);
					} else if (token.equals("sTask9")) {
						p.sTask9 = Integer.parseInt(token2);
					} else if (token.equals("sTask10")) {
						p.sTask10 = Integer.parseInt(token2);
					} else if (token.equals("sTask11")) {
						p.sTask11 = Integer.parseInt(token2);
					} else if (token.equals("sTask12")) {
						p.sTask12 = Integer.parseInt(token2);
					} else if (token.equals("allsTasks")) {
						p.allsTasks = Integer.parseInt(token2);
					} else if (token.equals("mageKills")) {
						p.mageKills = Integer.parseInt(token2);
					} else if (token.equals("rangeKills")) {
						p.rangeKills = Integer.parseInt(token2);
					} else if (token.equals("meleeKills")) {
						p.meleeKills = Integer.parseInt(token2);
					} else if (token.equals("hadTokHaar")) {
						p.hadTokHaar = Integer.parseInt(token2);
					} else if (token.equals("hadpring")) {
						p.hadpring = Integer.parseInt(token2);
					} else if (token.equals("daFirstTime")) {
						p.daFirstTime = Integer.parseInt(token2);
					} else if (token.equals("canUsePortal")) {
						p.canUsePortal = Integer.parseInt(token2);
					} else if (token.equals("warnings")) {
						p.warnings = Integer.parseInt(token2);
					} else if (token.equals("canLoginMsg")) {
						p.canLoginMsg = Integer.parseInt(token2);
					} else if (token.equals("headmod")) {
						p.headmod = Integer.parseInt(token2);
					} else if (token.equals("tournament")) {
						p.tournament = Integer.parseInt(token2);
					} else if (token.equals("fightPitsWins")) {
						p.fightPitsWins = Integer.parseInt(token2);
					} else if (token.equals("trusted")) {
						p.trusted = Integer.parseInt(token2);
					} else if (token.equals("dicer")) {
						p.dicer = Integer.parseInt(token2);
					} else if (token.equals("firstIP")) {
						p.firstIP = token2;
					} else if (token.equals("lastIP")) {
						p.lastIP = token2;
					} else if (token.equals("firstHost")) {
						p.firstHost = token2;
					} else if (token.equals("lastHost")) {
						p.lastHost = token2;
					} else if (token.equals("lastClan")) {
						p.lastClan = token2;
					} else if (token.equals("lastTargetName")) {
						p.lastTargetName = token2;
					} else if (token.equals("cantGetKills")) {
						p.cantGetKills = Integer.parseInt(token2);
					} else if (token.equals("cantGetKillsTimer")) {
						p.cantGetKillsTimer = Integer.parseInt(token2);
					} else if (token.equals("killsThisMinute")) {
						p.killsThisMinute = Integer.parseInt(token2);
					} else if (token.equals("killsThisMinuteTimer")) {
						p.killsThisMinuteTimer = Integer.parseInt(token2);
					} else if (token.equals("SP")) {
						p.SP = Integer.parseInt(token2);
					} else if (token.equals("Altar")) {
						p.altarPrayed = Integer.parseInt(token2);
					} else if (token.equals("alldp")) {
						p.alldp = Integer.parseInt(token2);
					} else if (token.equals("dp")) {
						p.dp = Integer.parseInt(token2);
					} else if (token.equals("allVP")) {
						p.allVP = Integer.parseInt(token2);
					} else if (token.equals("VP")) {
						p.VP = Integer.parseInt(token2);
					} else if (token.equals("EP")) {
						p.EP = Integer.parseInt(token2);
					} else if (token.equals("allEP")) {
						p.allEP = Integer.parseInt(token2);
					} else if (token.equals("worshippedGod")) {
						p.worshippedGod = Integer.parseInt(token2);
					} else if (token.equals("godReputation")) {
						p.godReputation = Integer.parseInt(token2);
					} else if (token.equals("godReputation2")) {
						p.godReputation2 = Integer.parseInt(token2);
					} else if (token.equals("aid")) {
						p.aid = Integer.parseInt(token2);
					} else if (token.equals("FPP")) {
						p.fpp = Integer.parseInt(token2);
					} else if (token.equals("hasRegistered")) {
						p.hasRegistered = Integer.parseInt(token2);
					}
					break;
				case 3:
					if (token.equals("character-equip")) {
						p.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 4:
					if (token.equals("character-look")) {
						p.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					} 
					break;
				case 5:
					if (token.equals("character-skill")) {
						p.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 6:
					if (token.equals("character-item")) {
						p.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 7:
					if (token.equals("character-bank")) {
						p.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 8:
					 if (token.equals("character-friend")) {
						p.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					} 
					break;
				case 9:
					if (token.equals("character-ignore")) {
						p.ignores[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					}
					break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {		ReadMode = 1;
				} else if (line.equals("[CHARACTER]")) {	ReadMode = 2;
				} else if (line.equals("[EQUIPMENT]")) {	ReadMode = 3;
				} else if (line.equals("[LOOK]")) {		ReadMode = 4;
				} else if (line.equals("[SKILLS]")) {		ReadMode = 5;
				} else if (line.equals("[ITEMS]")) {		ReadMode = 6;
				} else if (line.equals("[BANK]")) {		ReadMode = 7;
				} else if (line.equals("[FRIENDS]")) {		ReadMode = 8;
				} else if (line.equals("[IGNORES]")) {		ReadMode = 9;
				} else if (line.equals("[SHOP]")) {		ReadMode = 10;
				} else if (line.equals("[EOF]")) {		try { characterfile.close(); } catch(IOException ioexception) { } return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return 13;
	}
	
	
	
	
	/**
	*Saving
	**/
	public static boolean saveGame(Client p) {
		if(!p.saveFile || p.newPlayer || !p.saveCharacter) {
			//System.out.println("first");
			return false;
		}
		if(p.playerName == null || Server.playerHandler.players[p.playerId] == null) {
			//System.out.println("second");
			return false;
		}
		p.playerName = p.playerName2;
		int tbTime = (int)(p.teleBlockDelay - System.currentTimeMillis() + p.teleBlockLength);
		if(tbTime > 300000 || tbTime < 0){
			tbTime = 0;
		}
		
		BufferedWriter characterfile = null;
		try {
			characterfile = new BufferedWriter(new FileWriter("./Data/characters/"+p.playerName+".txt"));
			
			/*ACCOUNT*/
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("character-username = ", 0, 21);
			characterfile.write(p.playerName, 0, p.playerName.length());
			characterfile.newLine();
			characterfile.write("character-password = ", 0, 21);
			characterfile.write(p.playerPass, 0, p.playerPass.length());
			characterfile.newLine();
			characterfile.newLine();
			
			/*CHARACTER*/
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("oldPassword1 = ", 0, 15);
			characterfile.write(p.oldPass1, 0, p.oldPass1.length());
			characterfile.newLine();
			characterfile.write("oldPassword2 = ", 0, 15);
			characterfile.write(p.oldPass2, 0, p.oldPass2.length());
			characterfile.newLine();
			characterfile.write("character-height = ", 0, 19);
			characterfile.write(Integer.toString(p.heightLevel), 0, Integer.toString(p.heightLevel).length());
			characterfile.newLine();
			characterfile.write("character-posx = ", 0, 17);
			characterfile.write(Integer.toString(p.absX), 0, Integer.toString(p.absX).length());
			characterfile.newLine();
			characterfile.write("character-posy = ", 0, 17);
			characterfile.write(Integer.toString(p.absY), 0, Integer.toString(p.absY).length());
			characterfile.newLine();
			characterfile.write("character-rights = ", 0, 19);
			characterfile.write(Integer.toString(p.playerRights), 0, Integer.toString(p.playerRights).length());
			characterfile.newLine();
			characterfile.write("trialMod = ", 0, 11);
			characterfile.write(Integer.toString(p.trialMod), 0, Integer.toString(p.trialMod).length());
			characterfile.newLine();
			characterfile.write("helper = ", 0, 9);
			characterfile.write(Integer.toString(p.helper), 0, Integer.toString(p.helper).length());
			characterfile.newLine();
			characterfile.write("character-yellTag = ", 0, 20);
			characterfile.write(p.customYellTag, 0, p.customYellTag.length());
			characterfile.newLine();
			characterfile.write("character-login = ", 0, 18);
			characterfile.write(p.customLogin, 0, p.customLogin.length());
			characterfile.newLine();
			characterfile.write("character-yellColor = ", 0, 22);
			characterfile.write(p.yellColor, 0, p.yellColor.length());
			characterfile.newLine();
			characterfile.write("character-nameColor = ", 0, 22);
			characterfile.write(p.nameColor, 0, p.nameColor.length());
			characterfile.newLine();
			characterfile.write("Altar = ", 0, 8);
			characterfile.write(Integer.toString(p.altarPrayed), 0, Integer.toString(p.altarPrayed).length());
			characterfile.newLine();
			characterfile.write("crystal-bow-shots = ", 0, 20);
			characterfile.write(Integer.toString(p.crystalBowArrowCount), 0, Integer.toString(p.crystalBowArrowCount).length());
			characterfile.newLine();
			characterfile.write("skull-timer = ", 0, 14);
			characterfile.write(Integer.toString(p.skullTimer), 0, Integer.toString(p.skullTimer).length());
			characterfile.newLine();
			characterfile.write("noob-timer = ", 0, 13);
			characterfile.write(Integer.toString(p.noobTimer), 0, Integer.toString(p.noobTimer).length());
			characterfile.newLine();
			characterfile.write("play-time = ", 0, 12);
			characterfile.write(Integer.toString(p.pTime), 0, Integer.toString(p.pTime).length());
			characterfile.newLine();
			characterfile.write("magic-book = ", 0, 13);
			characterfile.write(Integer.toString(p.playerMagicBook), 0, Integer.toString(p.playerMagicBook).length());
			characterfile.newLine();
			characterfile.write("CurrentStreak = ", 0, 16);
			characterfile.write(Integer.toString(p.cStreak), 0, Integer.toString(p.cStreak).length());
			characterfile.newLine();

			characterfile.write("HighestStreak = ", 0, 16);
			characterfile.write(Integer.toString(p.hStreak), 0, Integer.toString(p.hStreak).length());
			characterfile.newLine();
			
			characterfile.write("wStreak = ", 0, 10);
			characterfile.write(Integer.toString(p.wStreak), 0, Integer.toString(p.wStreak).length());
			characterfile.newLine();
			
			characterfile.write("potential = ", 0, 12);
			characterfile.write(Integer.toString(p.potential), 0, Integer.toString(p.potential).length());
			characterfile.newLine();
			
			characterfile.write("ban-start = ", 0, 12);
            characterfile.write(Long.toString(p.banStart), 0, Long.toString(p.banStart).length());
            characterfile.newLine();
            characterfile.write("ban-end = ", 0, 10);
            characterfile.write(Long.toString(p.banEnd), 0, Long.toString(p.banEnd).length());
            characterfile.newLine();
			characterfile.write("lampStart = ", 0, 12);
            characterfile.write(Long.toString(p.lampStart), 0, Long.toString(p.lampStart).length());
            characterfile.newLine();
			characterfile.write("lampStart2 = ", 0, 13);
            characterfile.write(Long.toString(p.lampStart2), 0, Long.toString(p.lampStart2).length());
            characterfile.newLine();
            characterfile.write("dPortalDelay = ", 0, 15);
            characterfile.write(Long.toString(p.dPortalDelay), 0, Long.toString(p.dPortalDelay).length());
            characterfile.newLine();
			characterfile.write("isflagged2 = ", 0, 13);
			characterfile.write(Integer.toString(p.isFlagged), 0, Integer.toString(p.isFlagged).length());
			characterfile.newLine();
			characterfile.write("isSevereflagged = ", 0, 18);
			characterfile.write(Boolean.toString(p.isSevereFlagged), 0, Boolean.toString(p.isSevereFlagged).length());
			characterfile.newLine();
			
			for (int b = 0; b < p.barrowsNpcs.length; b++) {
				characterfile.write("brother-info = ", 0, 15);
				characterfile.write(Integer.toString(b), 0, Integer.toString(b).length());
				characterfile.write("	", 0, 1);
				characterfile.write(p.barrowsNpcs[b][1] <= 1 ? Integer.toString(0) : Integer.toString(p.barrowsNpcs[b][1]), 0, Integer.toString(p.barrowsNpcs[b][1]).length());
				characterfile.newLine();
			}	
			characterfile.write("special-amount = ", 0, 17);
			characterfile.write(Double.toString(p.specAmount), 0, Double.toString(p.specAmount).length());
			characterfile.newLine();
			characterfile.write("selected-coffin = ", 0, 18);
			characterfile.write(Integer.toString(p.randomCoffin), 0, Integer.toString(p.randomCoffin).length());
			characterfile.newLine();
			characterfile.write("barrows-killcount = ", 0, 20);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("teleblock-length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0, Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("pc-points = ", 0, 12);
			characterfile.write(Integer.toString(p.pcPoints), 0, Integer.toString(p.pcPoints).length());
			characterfile.newLine();
			characterfile.write("pandPoints = ", 0, 13);
			characterfile.write(Integer.toString(p.pandPoints), 0, Integer.toString(p.pandPoints).length());
			characterfile.newLine();
			characterfile.write("totalPandPoints = ", 0, 18);
			characterfile.write(Integer.toString(p.totalPandPoints), 0, Integer.toString(p.totalPandPoints).length());
			characterfile.newLine();
			characterfile.write("currentPPoints = ", 0, 17);
			characterfile.write(Integer.toString(p.currentPPoints), 0, Integer.toString(p.currentPPoints).length());
			characterfile.newLine();
			characterfile.write("bonusPand = ", 0, 12);
			characterfile.write(Integer.toString(p.bonusPand), 0, Integer.toString(p.bonusPand).length());
			characterfile.newLine();
			characterfile.write("lastDay = ", 0, 10);
			characterfile.write(Integer.toString(p.lastDay), 0, Integer.toString(p.lastDay).length());
			characterfile.newLine();
			characterfile.write("currentKC = ", 0, 12);
			characterfile.write(Integer.toString(p.currentKC), 0, Integer.toString(p.currentKC).length());
			characterfile.newLine();
			characterfile.write("bestPand = ", 0, 11);
			characterfile.write(Integer.toString(p.bestPand), 0, Integer.toString(p.bestPand).length());
			characterfile.newLine();
			characterfile.write("bossesKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.bossesKilled), 0, Integer.toString(p.bossesKilled).length());
			characterfile.newLine();
			characterfile.write("scoreReset = ", 0, 13);
			characterfile.write(Integer.toString(p.scoreReset), 0, Integer.toString(p.scoreReset).length());
			characterfile.newLine();
			characterfile.write("lastKilled = ", 0, 11);
			characterfile.write(p.lastKilled, 0, p.lastKilled.length());
			characterfile.newLine();
			characterfile.write("KC = ", 0, 5);
			characterfile.write(Integer.toString(p.KC), 0, Integer.toString(p.KC).length());
			characterfile.newLine();
			characterfile.write("DC = ", 0, 5);
			characterfile.write(Integer.toString(p.DC), 0, Integer.toString(p.DC).length());
			characterfile.newLine();
			characterfile.write("gotShit = ", 0, 10);
			characterfile.write(Integer.toString(p.gotShit), 0, Integer.toString(p.gotShit).length());
			characterfile.newLine();
			characterfile.write("targFights = ", 0, 13);
			characterfile.write(Integer.toString(p.targFights), 0, Integer.toString(p.targFights).length());
			characterfile.newLine();
			characterfile.write("rating = ", 0, 9);
			characterfile.write(Integer.toString(p.rating), 0, Integer.toString(p.rating).length());
			characterfile.newLine();
			characterfile.write("bankPin = ", 0, 10);
			characterfile.write(p.bankPin, 0, p.bankPin.length());
			characterfile.newLine();
			characterfile.write("setPin = ", 0, 9);
			characterfile.write(Boolean.toString(p.setPin), 0, Boolean.toString(p.setPin).length());
			characterfile.newLine();
			characterfile.write("recoilHits = ", 0, 13);
			characterfile.write(Integer.toString(p.recoilHits), 0, Integer.toString(p.recoilHits).length());
			characterfile.newLine();
			characterfile.write("slayerTask = ", 0, 13);
			characterfile.write(Integer.toString(p.slayerTask), 0, Integer.toString(p.slayerTask).length());
			characterfile.newLine();
			characterfile.write("taskAmount = ", 0, 13);
			characterfile.write(Integer.toString(p.taskAmount), 0, Integer.toString(p.taskAmount).length());
			characterfile.newLine();
			characterfile.write("myTask = ", 0, 9);
			characterfile.write(Integer.toString(p.myTask), 0, Integer.toString(p.myTask).length());
			characterfile.newLine();
			characterfile.write("taskPoints = ", 0, 13);
			characterfile.write(Integer.toString(p.taskPoints), 0, Integer.toString(p.taskPoints).length());
			characterfile.newLine();
			characterfile.write("totalTaskPoints = ", 0, 18);
			characterfile.write(Integer.toString(p.totalTaskPoints), 0, Integer.toString(p.totalTaskPoints).length());
			characterfile.newLine();
			characterfile.write("magePoints = ", 0, 13);
			characterfile.write(Integer.toString(p.magePoints), 0, Integer.toString(p.magePoints).length());
			characterfile.newLine();
			characterfile.write("checkInv = ", 0, 11);
			characterfile.write(Integer.toString(p.checkInv), 0, Integer.toString(p.checkInv).length());
			characterfile.newLine();
			characterfile.write("autoRet = ", 0, 10);
			characterfile.write(Integer.toString(p.autoRet), 0, Integer.toString(p.autoRet).length());
			characterfile.newLine();
			characterfile.write("barrowskillcount = ", 0, 19);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("flagged = ", 0, 10);
			characterfile.write(Boolean.toString(p.accountFlagged), 0, Boolean.toString(p.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("wave = ", 0, 7);
			characterfile.write(Integer.toString(p.waveId), 0, Integer.toString(p.waveId).length());
			characterfile.newLine();
			characterfile.write("gwkc = ", 0, 7);
			characterfile.write(Integer.toString(p.killCount), 0, Integer.toString(p.killCount).length());
			characterfile.newLine();
			characterfile.write("fightMode = ", 0, 12);
			characterfile.write(Integer.toString(p.fightMode), 0, Integer.toString(p.fightMode).length());
			characterfile.newLine();
			characterfile.write("Monkey Kc = ", 0, 12);
			characterfile.write(Integer.toString(p.monkeyk0ed), 0, Integer.toString(p.monkeyk0ed).length());
			characterfile.newLine();
			characterfile.write("Monkey Kc = ", 0, 12);
			characterfile.write(Integer.toString(p.monkeyk0ed), 0, Integer.toString(p.monkeyk0ed).length());
			characterfile.newLine();
			characterfile.write("void = ", 0, 7);
			String toWrite = p.voidStatus[0] + "\t" + p.voidStatus[1] + "\t" + p.voidStatus[2] + "\t" + p.voidStatus[3] + "\t" + p.voidStatus[4];
			characterfile.write(toWrite);
			characterfile.newLine();
			characterfile.write("tradeTimer = ", 0, 13);
			characterfile.write(Integer.toString(p.tradeTimer), 0, Integer.toString(p.tradeTimer).length());
			characterfile.newLine();
			characterfile.write("pkPoints = ", 0, 11);
			characterfile.write(Integer.toString(p.pkPoints), 0, Integer.toString(p.pkPoints).length());
			characterfile.newLine();
			characterfile.write("eventScore = ", 0, 13);
			characterfile.write(Integer.toString(p.eventScore), 0, Integer.toString(p.eventScore).length());
			characterfile.newLine();
			characterfile.write("eventId = ", 0, 10);
			characterfile.write(Integer.toString(p.eventId), 0, Integer.toString(p.eventId).length());
			characterfile.newLine();
			characterfile.write("redSkull = ", 0, 11);
			characterfile.write(Integer.toString(p.redSkull), 0, Integer.toString(p.redSkull).length());
			characterfile.newLine();
			characterfile.write("lmsPoints = ", 0, 12);
			characterfile.write(Integer.toString(p.lmsPoints), 0, Integer.toString(p.lmsPoints).length());
			characterfile.newLine();
			characterfile.write("pkChallenge = ", 0, 14);
			characterfile.write(Integer.toString(p.pkChallenge), 0, Integer.toString(p.pkChallenge).length());
			characterfile.newLine();
			characterfile.write("vengLimit = ", 0, 12);
			characterfile.write(Integer.toString(p.vengLimit), 0, Integer.toString(p.vengLimit).length());
			characterfile.newLine();
			characterfile.write("pkpTimer = ", 0, 11);
			characterfile.write(Integer.toString(p.pkpTimer), 0, Integer.toString(p.pkpTimer).length());
			characterfile.newLine();
			characterfile.write("targetPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.targetPoints), 0, Integer.toString(p.targetPoints).length());
			characterfile.newLine();
			characterfile.write("totalTargetPoints = ", 0, 20);
			characterfile.write(Integer.toString(p.totalTargetPoints), 0, Integer.toString(p.totalTargetPoints).length());
			characterfile.newLine();
			characterfile.write("weaponTask = ", 0, 13);
			characterfile.write(Integer.toString(p.weaponTask), 0, Integer.toString(p.weaponTask).length());
			characterfile.newLine();
			characterfile.write("brightness = ", 0, 13);
			characterfile.write(Integer.toString(p.brightness), 0, Integer.toString(p.brightness).length());
			characterfile.newLine();
			characterfile.write("memberStatus = ", 0, 15);
			characterfile.write(Integer.toString(p.memberStatus), 0, Integer.toString(p.memberStatus).length());
			characterfile.newLine();
			characterfile.write("gameMode = ", 0, 11);
			characterfile.write(Integer.toString(p.gameMode), 0, Integer.toString(p.gameMode).length());
			characterfile.newLine();
			characterfile.write("xpLock = ", 0, 9);
			characterfile.write(Integer.toString(p.xpLock), 0, Integer.toString(p.xpLock).length());
			characterfile.newLine();
			characterfile.write("cantGetKills = ", 0, 15);
			characterfile.write(Integer.toString(p.cantGetKills), 0, Integer.toString(p.cantGetKills).length());
			characterfile.newLine();
			characterfile.write("cantGetKillsTimer = ", 0, 20);
			characterfile.write(Integer.toString(p.cantGetKillsTimer), 0, Integer.toString(p.cantGetKillsTimer).length());
			characterfile.newLine();
			characterfile.write("killsThisMinute = ", 0, 18);
			characterfile.write(Integer.toString(p.killsThisMinute), 0, Integer.toString(p.killsThisMinute).length());
			characterfile.newLine();
			characterfile.write("killsThisMinuteTimer = ", 0, 23);
			characterfile.write(Integer.toString(p.killsThisMinuteTimer), 0, Integer.toString(p.killsThisMinuteTimer).length());
			characterfile.newLine();
			characterfile.write("SP = ", 0, 5);
			characterfile.write(Integer.toString(p.SP), 0, Integer.toString(p.SP).length());
			characterfile.newLine();
			characterfile.write("alldp = ", 0, 8);
			characterfile.write(Integer.toString(p.alldp), 0, Integer.toString(p.alldp).length());
			characterfile.newLine();
			characterfile.write("dp = ", 0, 5);
			characterfile.write(Integer.toString(p.dp), 0, Integer.toString(p.dp).length());
			characterfile.newLine();
			characterfile.write("allVP = ", 0, 8);
			characterfile.write(Integer.toString(p.allVP), 0, Integer.toString(p.allVP).length());
			characterfile.newLine();
			characterfile.write("VP = ", 0, 5);
			characterfile.write(Integer.toString(p.VP), 0, Integer.toString(p.VP).length());
			characterfile.newLine();
			characterfile.write("EP = ", 0, 5);
			characterfile.write(Integer.toString(p.EP), 0, Integer.toString(p.EP).length());
			characterfile.newLine();
			characterfile.write("allEP = ", 0, 8);
			characterfile.write(Integer.toString(p.allEP), 0, Integer.toString(p.allEP).length());
			characterfile.newLine();
			characterfile.write("worshippedGod = ", 0, "worshippedGod = ".length());
			characterfile.write(Integer.toString(p.worshippedGod), 0, Integer.toString(p.worshippedGod).length());
			characterfile.newLine();
			characterfile.write("godReputation = ", 0, "godReputation = ".length());
			characterfile.write(Integer.toString(p.godReputation), 0, Integer.toString(p.godReputation).length());
			characterfile.newLine();
			characterfile.write("godReputation2 = ", 0, "godReputation2 = ".length());
			characterfile.write(Integer.toString(p.godReputation2), 0, Integer.toString(p.godReputation2).length());
			characterfile.newLine();
			characterfile.write("aid = ", 0, 5);
			characterfile.write(Integer.toString(p.aid), 0, Integer.toString(p.aid).length());
			characterfile.newLine();
			characterfile.write("FPP = ", 0, 5);
			characterfile.write(Integer.toString(p.fpp), 0, Integer.toString(p.fpp).length());
			characterfile.newLine();
			characterfile.write("hasRegistered = ", 0, 16);
			characterfile.write(Integer.toString(p.hasRegistered), 0, Integer.toString(p.hasRegistered).length());
			characterfile.newLine();
			characterfile.write("yellmute = ", 0, 11);
			characterfile.write(Integer.toString(p.yellmute), 0, Integer.toString(p.yellmute).length());
			characterfile.newLine();
			characterfile.write("veteranPlayer = ", 0, 16);
			characterfile.write(Integer.toString(p.veteranPlayer), 0, Integer.toString(p.veteranPlayer).length());
			characterfile.newLine();
			characterfile.write("verified = ", 0, 11);
			characterfile.write(Integer.toString(p.verified), 0, Integer.toString(p.verified).length());
			characterfile.newLine();
			characterfile.write("yellOn = ", 0, 9);
			characterfile.write(Integer.toString(p.yellOn), 0, Integer.toString(p.yellOn).length());
			characterfile.newLine();
			characterfile.write("announceOn = ", 0, 13);
			characterfile.write(Integer.toString(p.announceOn), 0, Integer.toString(p.announceOn).length());
			characterfile.newLine();
			characterfile.write("clanmute = ", 0, 11);
			characterfile.write(Integer.toString(p.clanmute), 0, Integer.toString(p.clanmute).length());
			characterfile.newLine();
			characterfile.write("tournament = ", 0, 13);
			characterfile.write(Integer.toString(p.tournament), 0, Integer.toString(p.tournament).length());
			characterfile.newLine();
			characterfile.write("fightPitsWins = ", 0, 16);
			characterfile.write(Integer.toString(p.fightPitsWins), 0, Integer.toString(p.fightPitsWins).length());
			characterfile.newLine();
			characterfile.write("doingStarter = ", 0, 15);
			characterfile.write(Integer.toString(p.doingStarter), 0, Integer.toString(p.doingStarter).length());
			characterfile.newLine();
			characterfile.write("sTask1 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask1), 0, Integer.toString(p.sTask1).length());
			characterfile.newLine();
			characterfile.write("sTask2 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask2), 0, Integer.toString(p.sTask2).length());
			characterfile.newLine();
			characterfile.write("sTask3 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask3), 0, Integer.toString(p.sTask3).length());
			characterfile.newLine();
			characterfile.write("sTask4 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask4), 0, Integer.toString(p.sTask4).length());
			characterfile.newLine();
			characterfile.write("sTask5 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask5), 0, Integer.toString(p.sTask5).length());
			characterfile.newLine();
			characterfile.write("sTask6 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask6), 0, Integer.toString(p.sTask6).length());
			characterfile.newLine();
			characterfile.write("sTask7 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask7), 0, Integer.toString(p.sTask7).length());
			characterfile.newLine();
			characterfile.write("sTask8 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask8), 0, Integer.toString(p.sTask8).length());
			characterfile.newLine();
			characterfile.write("sTask9 = ", 0, 9);
			characterfile.write(Integer.toString(p.sTask9), 0, Integer.toString(p.sTask9).length());
			characterfile.newLine();
			characterfile.write("sTask10 = ", 0, 10);
			characterfile.write(Integer.toString(p.sTask10), 0, Integer.toString(p.sTask10).length());
			characterfile.newLine();
			characterfile.write("sTask11 = ", 0, 10);
			characterfile.write(Integer.toString(p.sTask11), 0, Integer.toString(p.sTask11).length());
			characterfile.newLine();
			characterfile.write("sTask12 = ", 0, 10);
			characterfile.write(Integer.toString(p.sTask12), 0, Integer.toString(p.sTask12).length());
			characterfile.newLine();
			characterfile.write("allsTasks = ", 0, 12);
			characterfile.write(Integer.toString(p.allsTasks), 0, Integer.toString(p.allsTasks).length());
			characterfile.newLine();
			characterfile.write("isJailed = ", 0, 11);
			characterfile.write(Integer.toString(p.isJailed), 0, Integer.toString(p.isJailed).length());
			characterfile.newLine();
			characterfile.write("stuckTimer = ", 0, 13);
			characterfile.write(Integer.toString(p.stuckTimer), 0, Integer.toString(p.stuckTimer).length());
			characterfile.newLine();
			characterfile.write("mageKills = ", 0, 12);
			characterfile.write(Integer.toString(p.mageKills), 0, Integer.toString(p.mageKills).length());
			characterfile.newLine();
			characterfile.write("rangeKills = ", 0, 13);
			characterfile.write(Integer.toString(p.rangeKills), 0, Integer.toString(p.rangeKills).length());
			characterfile.newLine();
			characterfile.write("meleeKills = ", 0, 13);
			characterfile.write(Integer.toString(p.meleeKills), 0, Integer.toString(p.meleeKills).length());
			characterfile.newLine();
			characterfile.write("hadTokHaar = ", 0, 13);
			characterfile.write(Integer.toString(p.hadTokHaar), 0, Integer.toString(p.hadTokHaar).length());
			characterfile.newLine();
			characterfile.write("hadpring = ", 0, 11);
			characterfile.write(Integer.toString(p.hadpring), 0, Integer.toString(p.hadpring).length());
			characterfile.newLine();
			characterfile.write("daFirstTime = ", 0, 14);
			characterfile.write(Integer.toString(p.daFirstTime), 0, Integer.toString(p.daFirstTime).length());
			characterfile.newLine();
			characterfile.write("canUsePortal = ", 0, 15);
			characterfile.write(Integer.toString(p.canUsePortal), 0, Integer.toString(p.canUsePortal).length());
			characterfile.newLine();
			characterfile.write("warnings = ", 0, 11);
			characterfile.write(Integer.toString(p.warnings), 0, Integer.toString(p.warnings).length());
			characterfile.newLine();
			characterfile.write("canLoginMsg = ", 0, 14);
			characterfile.write(Integer.toString(p.canLoginMsg), 0, Integer.toString(p.canLoginMsg).length());
			characterfile.newLine();
			characterfile.write("headmod = ", 0, 10);
			characterfile.write(Integer.toString(p.headmod), 0, Integer.toString(p.headmod).length());
			characterfile.newLine();
			characterfile.write("trusted = ", 0, 10);
			characterfile.write(Integer.toString(p.trusted), 0, Integer.toString(p.trusted).length());
			characterfile.newLine();
			characterfile.write("dicer = ", 0, 8);
			characterfile.write(Integer.toString(p.dicer), 0, Integer.toString(p.dicer).length());
			characterfile.newLine();
			characterfile.write("firstIP = ", 0, 10);
			characterfile.write(p.firstIP, 0, p.firstIP.length());
			characterfile.newLine();
			characterfile.write("lastIP = ", 0, 9);
			characterfile.write(p.lastIP, 0, p.lastIP.length());
			characterfile.newLine();
			characterfile.write("firstHost = ", 0, 12);
			characterfile.write(p.firstHost, 0, p.firstHost.length());
			characterfile.newLine();
			characterfile.write("lastHost = ", 0, 11);
			characterfile.write(p.lastHost, 0, p.lastHost.length());
			characterfile.newLine();
			characterfile.write("lastClan = ", 0, 11);
			characterfile.write(p.lastClan, 0, p.lastClan.length());
			characterfile.newLine();
			characterfile.write("lastTargetName = ", 0, 17);
			characterfile.write(p.lastTargetName, 0, p.lastTargetName.length());
			characterfile.newLine();
			characterfile.newLine();
			
			/*EQUIPMENT*/
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < p.playerEquipment.length; i++) {
				characterfile.write("character-equip = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipment[i]), 0, Integer.toString(p.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipmentN[i]), 0, Integer.toString(p.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*LOOK*/
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.playerAppearance.length; i++) {
				characterfile.write("character-look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerAppearance[i]), 0, Integer.toString(p.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*SKILLS*/
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.playerLevel.length; i++) {
				characterfile.write("character-skill = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerLevel[i]), 0, Integer.toString(p.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerXP[i]), 0, Integer.toString(p.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*ITEMS*/
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < p.playerItems.length; i++) {
				if (p.playerItems[i] > 0) {
					characterfile.write("character-item = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItems[i]), 0, Integer.toString(p.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItemsN[i]), 0, Integer.toString(p.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
		/*BANK*/
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.bankItems.length; i++) {
				if (p.bankItems[i] > 0) {
					characterfile.write("character-bank = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItems[i]), 0, Integer.toString(p.bankItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItemsN[i]), 0, Integer.toString(p.bankItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
		/*FRIENDS*/
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < p.friends.length; i++) {
				if (p.friends[i] > 0) {
					characterfile.write("character-friend = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + p.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();
					

		/*IGNORES*/
			characterfile.write("[IGNORES]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < p.ignores.length; i++) {
				if (p.ignores[i] > 0) {
					characterfile.write("character-ignore = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + p.ignores[i]);
					//characterfile.write(Long.toString(p.ignores[i]), 0, Long.toString(p.ignores[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
		/*EOF*/
			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.close();
		} catch(IOException ioexception) {
			Misc.println(p.playerName+": error writing file.");
			return false;
		}
		return true;
	}	
	

}