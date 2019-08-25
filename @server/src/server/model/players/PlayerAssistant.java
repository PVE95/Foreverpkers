package server.model.players;

import server.Config;
import server.Server;
import server.model.npcs.NPCHandler;
import server.util.Misc;
import server.world.map.*;

import java.util.Properties;

import server.model.players.PlayerSave;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.Calendar;

import server.Connection;
import server.clip.region.ObjectDef;
import server.clip.region.Region;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.world.*;
import server.model.shops.ShopAssistant;

public class PlayerAssistant{
	private Client c;
	public PlayerAssistant(Client Client) {
		this.c = Client;
	}
	
	private static final String[] killMessage = {"wrecked", "destroyed", "ended", "cleared", "ruined"};
	public void playerWalk(int x, int y) {
			PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);
	}
	public void otherInv(Client c, Client o) {
        if(o == c || o == null || c == null) 
            return;
         int[] backupItems = c.playerItems;
         int[] backupItemsN = c.playerItemsN;
        c.playerItems = o.playerItems;
        c.playerItemsN = o.playerItemsN;
       
        c.getItems().resetItems(3214);
         
       c.playerItems = backupItems;
       c.playerItemsN = backupItemsN;
    }
	
	public String lastIP = "";

	public void vengOther() {
		Client c2 = (Client)PlayerHandler.players[c.playerIndex];
		if (System.currentTimeMillis() - c.lastVeng > 30000) {
			if (c.getItems().playerHasItem(557,10) && c.getItems().playerHasItem(9075,4) && c.getItems().playerHasItem(560,2)) {
				c2.vengOn = true;
				c2.lastVeng = System.currentTimeMillis();
				c.startAnimation(4411);
				c2.sendMessage("You now have the power of Vengeance!");
				c.getItems().deleteItem(557,c.getItems().getItemSlot(557),10);
				c.getItems().deleteItem(560,c.getItems().getItemSlot(560),2);
				c.getItems().deleteItem(9075,c.getItems().getItemSlot(9075),4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell.");
			}
		} else {
			c.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}

public void writePMLog(String data)
	{
		checkDateAndTime();	
		String filePath = "./Data/ChatLogs/" + c.playerName + ".txt";
		BufferedWriter bw = null;
		
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[PM][" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "[" + c.connectedFrom + "]: " + "" + data + " ");
			bw.newLine();
			bw.flush();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}

	public void teleTab(String teleportType) {
		if(c.inPits || inPitsWait()){
			c.sendMessage("You can't teleport during fight pits!");
			return;
		}
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(c.isInJail()) {
			c.sendMessage("You cannot teleport out of jail.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();	
			c.usingSpecial = false;		
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = 0;
			c.startAnimation(714);
				c.teleTimer = 11;
				c.teleGfx = 308;
				c.teleEndAnimation = 715;
			if(teleportType.equalsIgnoreCase("varrock")) {
				c.teleX = Config.VARROCK_X;
				c.teleY = Config.VARROCK_Y;
				c.getItems().deleteItem(8007 ,c.getItems().getItemSlot(8007), 1);
			} 
			if(teleportType.equalsIgnoreCase("lumbridge")) {
				c.teleX = 3222;
				c.teleY = 3218;
				c.getItems().deleteItem(8008,c.getItems().getItemSlot(8008), 1);	
			}	
			if(teleportType.equalsIgnoreCase("falador")) {
				c.teleX = 2965;
				c.teleY = 3380;
				c.getItems().deleteItem(8009 ,c.getItems().getItemSlot(8009), 1);	
			}
			if(teleportType.equalsIgnoreCase("camelot")) {
				c.teleX = 2757;
				c.teleY = 3477;
				c.getItems().deleteItem(8010 ,c.getItems().getItemSlot(8010), 1);	
			}
			if(teleportType.equalsIgnoreCase("ardy")) {
				c.teleX = 2659;
				c.teleY = 3308;
				c.getItems().deleteItem(8011 ,c.getItems().getItemSlot(8011), 1);	
			}
			
		}
	}

public boolean wearingCape(int cape) {
		int capes[] = {
			9747, 9748, 9750, 9751, 
			9753, 9754, 9756, 9757, 
			9759, 9760, 9762, 9763,
			9765, 9766, 9768, 9769,
			9771, 9772, 9774, 9775,
			9777, 9778, 9780, 9781,
			9783, 9784, 9786, 9787,
			9789, 9790, 9792, 9793,
			9795, 9796, 9798, 9799,
			9801, 9802, 9804, 9805,
			9807, 9808, 9810, 9811,
			10662 
		};
		for(int i = 0; i < capes.length; i++) {
			if(capes[i] == cape) {
				return true;
			}
		}
		return false;
	}
	
	public int skillcapeGfx(int cape) {
		int capeGfx[][] = {
			{9747, 823}, {9748, 823},
			{9750, 828}, {9751, 828},
			{9753, 824}, {9754, 824},
			{9756, 832}, {9757, 832},
			{9759, 829}, {9760, 829},
			{9762, 813}, {9763, 813},
			{9765, 817}, {9766, 817},
			{9768, 833}, {9769, 833},
			{9771, 830}, {9772, 830},
			{9774, 835}, {9775, 835},
			{9777, 826}, {9778, 826},
			{9780, 818}, {9781, 818},
			{9783, 812}, {9784, 812},
			{9786, 827}, {9787, 827},
			{9789, 820}, {9790, 820},
			{9792, 814}, {9793, 814},
			{9795, 815}, {9796, 815},
			{9798, 819}, {9799, 819},
			{9801, 821}, {9802, 821},
			{9804, 831}, {9805, 831},
			{9807, 822}, {9808, 822},
			{9810, 825}, {9811, 825},
			{10662, 816}
		};
		for(int i = 0; i < capeGfx.length; i++) {
			if(capeGfx[i][0] == cape) {
				return capeGfx[i][1];
			}
		}
		return -1;
	}
	
	public int skillcapeEmote(int cape) {
		int capeEmote[][] = {
			{9747, 4959}, {9748, 4959},
			{9750, 4981}, {9751, 4981},
			{9753, 4961}, {9754, 4961},
			{9756, 4973}, {9757, 4973},
			{9759, 4979}, {9760, 4979},
			{9762, 4939}, {9763, 4939},
			{9765, 4947}, {9766, 4947},
			{9768, 4971}, {9769, 4971},
			{9771, 4977}, {9772, 4977},
			{9774, 4969}, {9775, 4969},
			{9777, 4965}, {9778, 4965},
			{9780, 4949}, {9781, 4949},
			{9783, 4937}, {9784, 4937},
			{9786, 4967}, {9787, 4967},
			{9789, 4953}, {9790, 4953},
			{9792, 4941}, {9793, 4941},
			{9795, 4943}, {9796, 4943},
			{9798, 4951}, {9799, 4951},
			{9801, 4955}, {9802, 4955},
			{9804, 4975}, {9805, 4975},
			{9807, 4957}, {9808, 4957},
			{9810, 4963}, {9811, 4963},
			{10662, 4945}
		};
		for(int i = 0; i < capeEmote.length; i++) {
			if(capeEmote[i][0] == cape) {
				return capeEmote[i][1];
			}
		}
		return -1;
	}
	public void writeCommandLog(String command)
	{
		checkDateAndTime();	
		String filePath = "./Data/Commands2.txt";
		BufferedWriter bw = null;
		
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " 
				+ "[" + c.playerName + "]: " + "[" + c.connectedFrom + "] "
				 +  "::" + command);
			bw.newLine();
			bw.flush();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}

	public String getTotalAmount(Client c, int j) {
		if(j >= 10000 && j < 10000000) {
			return j / 1000 + "K";
		} else if(j >= 10000000 && j  <= 2147483647) {
			return j / 1000000 + "M";
		} else {
			return ""+ j +" gp";
		}
	}


	public String checkTimeOfDay()
	{	
		Calendar cal = new GregorianCalendar();	
		int TIME_OF_DAY = cal.get(Calendar.AM_PM);		
		if (TIME_OF_DAY > 0)
			return "PM";
		else
			return "AM";
	}
	
	public void checkDateAndTime()
	{
		Calendar cal = new GregorianCalendar();	
		
		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DAY = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR = cal.get(Calendar.HOUR_OF_DAY);
		int MIN = cal.get(Calendar.MINUTE);
		int SECOND = cal.get(Calendar.SECOND);
		
		String day = "";
		String month = "";
		String hour = "";
		String minute = "";
		String second = "";
		
		if (DAY < 10)
			day = "0" + DAY;
		else 
			day = "" + DAY;
		if (MONTH < 10)
			month = "0" + MONTH;	
		else
			month = "" + MONTH;
		if (HOUR < 10)
			hour = "0" + HOUR;
		else 
			hour = "" + HOUR;
		if (MIN < 10)
			minute = "0" + MIN;
		else
			minute = "" + MIN;
		if (SECOND < 10)
			second = "0" + SECOND;
		else
			second = "" + SECOND;
			
		c.date = day + "/" + month + "/" + YEAR;	
		c.currentTime = hour + ":" + minute + ":" + second;
	}	
	Properties p = new Properties();
	
	public void loadAnnouncements()
	{
		try
		{
			loadIni();
		
			if (p.getProperty("announcement1").length() > 0) {
				c.sendMessage(p.getProperty("announcement1"));
			}
			if (p.getProperty("announcement2").length() > 0) {
				c.sendMessage(p.getProperty("announcement2"));
			}
			if (p.getProperty("announcement3").length() > 0) {
				c.sendMessage(p.getProperty("announcement3"));
			}
		}
		catch (Exception e)
		{
		}
	}
	
	private void loadIni()
	{		
		try 
		{
			p.load(new FileInputStream("./Announcements.ini"));
		}
		catch (Exception e)
		{
		}
	}
	
	public int CraftInt, Dcolor, FletchInt;
	
	/**
	 * MulitCombat icon
	 * @param i1 0 = off 1 = on
	 */
	public void multiWay(int i1) {
		//synchronized(c) {
			c.outStream.createFrame(61);
			c.outStream.writeByte(i1);
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		//}
	}
	
	public void clearClanChat() {
		c.clanId = -1;
		c.getPA().sendFrame126("Talking in: ", 18139);
		c.getPA().sendFrame126("Owner: ", 18140);
		for (int j = 18144; j < 18244; j++)
			c.getPA().sendFrame126("", j);
	}
	
	public void resetAutocast() {
		c.autocastId = 0;
		c.autocasting = false;
		c.getPA().sendFrame36(108, 0);
	}
	
	public void sendFrame126(String s, int id) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null ) {
				c.getOutStream().createFrameVarSizeWord(126);
				c.getOutStream().writeString(s);
				c.getOutStream().writeWordA(id);
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		//}
	}
	
	
	public void sendLink(String s) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null ) {
				c.getOutStream().createFrameVarSizeWord(187);
				c.getOutStream().writeString(s);
			}
		//}	
	}
	
	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(134);
				c.getOutStream().writeByte(skillNum);
				c.getOutStream().writeDWord_v1(XP);
				c.getOutStream().writeByte(currentLevel);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame106(int sideIcon) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(106);
				c.getOutStream().writeByteC(sideIcon);
				c.flushOutStream();
				requestUpdates();
			}
		//}
	}
	
	public void sendFrame107() {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(107);
				c.flushOutStream();
			}
		//}
	}
	public void sendFrame36(int id, int state) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(36);
				c.getOutStream().writeWordBigEndian(id);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame185(int Frame) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(185);
				c.getOutStream().writeWordBigEndianA(Frame);
			}
		//}
	}
	
	public void showInterface(int interfaceid) {
		//synchronized(c) {
		if (c.inTrade || c.inDuel)
			return;
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(97);
				c.getOutStream().writeWord(interfaceid);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame248(int MainFrame, int SubFrame) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(248);
				c.getOutStream().writeWordA(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(246);
				c.getOutStream().writeWordBigEndian(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.getOutStream().writeWord(SubFrame2);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame171(int MainFrame, int SubFrame) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(171);
				c.getOutStream().writeByte(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame200(int MainFrame, int SubFrame) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(200);
				c.getOutStream().writeWord(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame70(int i, int o, int id) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(70);
				c.getOutStream().writeWord(i);
				c.getOutStream().writeWordBigEndian(o);
				c.getOutStream().writeWordBigEndian(id);
				c.flushOutStream();
			}
		//}
	}

	public void sendFrame75(int MainFrame, int SubFrame) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(75);
				c.getOutStream().writeWordBigEndianA(MainFrame);
				c.getOutStream().writeWordBigEndianA(SubFrame);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame164(int Frame) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(164);
				c.getOutStream().writeWordBigEndian_dup(Frame);
				c.flushOutStream();
			}
		//}
	}
	
	public void setPrivateMessaging(int i) { // friends and ignore list status
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
		        c.getOutStream().createFrame(221);
		        c.getOutStream().writeByte(i);
				c.flushOutStream();
			}
		//}
    }
	
	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(206);
				c.getOutStream().writeByte(publicChat);	
				c.getOutStream().writeByte(privateChat);	
				c.getOutStream().writeByte(tradeBlock);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendFrame87(int id, int state) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(87);
				c.getOutStream().writeWordBigEndian_dup(id);	
				c.getOutStream().writeDWord_v1(state);
				c.flushOutStream();
			}
		//}
	}
	
	public void sendPM(long name, int rights, byte[] chatmessage, int messagesize) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSize(196);
				c.getOutStream().writeQWord(name);
				c.getOutStream().writeDWord(c.lastChatId++);
				c.getOutStream().writeByte(rights);
				c.getOutStream().writeBytes(chatmessage, messagesize, 0);
				c.getOutStream().endFrameVarSize();
				c.flushOutStream();
				String chatmessagegot = Misc.textUnpack(chatmessage, messagesize);
				String target = Misc.longToPlayerName(name);
			}	
		//}
	}
	
	public void createPlayerHints(int type, int id) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(254);
				c.getOutStream().writeByte(type);
				c.getOutStream().writeWord(id); 
				c.getOutStream().write3Byte(0);
				c.flushOutStream();
			}
		//}
	}

	public void createObjectHints(int x, int y, int height, int pos) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(254);
				c.getOutStream().writeByte(pos);
				c.getOutStream().writeWord(x);
				c.getOutStream().writeWord(y);
				c.getOutStream().writeByte(height);
				c.flushOutStream();
			}
		//}
	}
	
	public void loadPM(long playerName, int world) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				if(world != 0) {
		            world += 9;
				} else if(!Config.WORLD_LIST_FIX) {
					world += 1;
				}	
				c.getOutStream().createFrame(50);
				c.getOutStream().writeQWord(playerName);
				c.getOutStream().writeByte(world);
				c.flushOutStream();
			}
		//}
	}

	public void loadPM2(long playerName, int world) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(2);
				c.getOutStream().writeQWord(playerName);
				c.getOutStream().writeByte(world);
				c.flushOutStream();
			}
		//}
	}
	
	public void removeAllWindows() {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getPA().resetVariables();
				c.getOutStream().createFrame(219);
				c.flushOutStream();
			}
		//}
	}
	
	public void closeAllWindows() {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(219);
				c.flushOutStream();
				//c.isBanking = false;
				c.canDepositAll = false;
			}
		//}
	}
	
	public void sendFrame34(int id, int slot, int column, int amount) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.outStream.createFrameVarSizeWord(34); // init item to smith screen
				c.outStream.writeWord(column); // Column Across Smith Screen
				c.outStream.writeByte(4); // Total Rows?
				c.outStream.writeDWord(slot); // Row Down The Smith Screen
				c.outStream.writeWord(id+1); // item
				c.outStream.writeByte(amount); // how many there are?
				c.outStream.endFrameVarSizeWord();
			}
		//}
	}	
	
	public void sendFrame34a(int frame, int item, int slot, int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}
	
	public void walkableInterface(int id) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(208);
		        c.getOutStream().writeWordBigEndian_dup(id);
				c.flushOutStream();
			}
		//}
	}
	
	public int mapStatus = 0;
	public void sendFrame99(int state) { // used for disabling map
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				if(mapStatus != state) {
					mapStatus = state;
					c.getOutStream().createFrame(99);
			        c.getOutStream().writeByte(state);
					c.flushOutStream();
				}
			}
		//}
	}
	
	/*public void sendCrashFrame() { // used for crashing cheat clients ????
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(123);
				c.flushOutStream();
			}
		//}
	}*/
	
	/**
	* Reseting animations for everyone
	**/

	public void frame1() {
		//synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
					Client person = (Client)Server.playerHandler.players[i];
					if(person != null) {
						if(person.getOutStream() != null && !person.disconnected) {
							if(c.distanceToPoint(person.getX(), person.getY()) <= 25){	
								person.getOutStream().createFrame(1);
								person.flushOutStream();
								person.getPA().requestUpdates();
							}
						}
					}
				}
			}
		//}
	}
	
	/**
	* Creating projectile
	**/
	public void createProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {      
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
		        c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
		        c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
		        c.getOutStream().createFrame(117);
		        c.getOutStream().writeByte(angle);
		        c.getOutStream().writeByte(offY);
		        c.getOutStream().writeByte(offX);
		        c.getOutStream().writeWord(lockon);
		        c.getOutStream().writeWord(gfxMoving);
		        c.getOutStream().writeByte(startHeight);
		        c.getOutStream().writeByte(endHeight);
		        c.getOutStream().writeWord(time);
			    c.getOutStream().writeWord(speed);
				c.getOutStream().writeByte(16);
				c.getOutStream().writeByte(64);
				c.flushOutStream();
			}
		//}
    }
	
	public void createProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {      
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
		        c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
		        c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
		        c.getOutStream().createFrame(117);
		        c.getOutStream().writeByte(angle);
		        c.getOutStream().writeByte(offY);
		        c.getOutStream().writeByte(offX);
		        c.getOutStream().writeWord(lockon);
		        c.getOutStream().writeWord(gfxMoving);
		        c.getOutStream().writeByte(startHeight);
		        c.getOutStream().writeByte(endHeight);
		        c.getOutStream().writeWord(time);
			    c.getOutStream().writeWord(speed);
				c.getOutStream().writeByte(slope);
				c.getOutStream().writeByte(64);
				c.flushOutStream();
			}
		//}
    }
	
	// projectiles for everyone within 25 squares
	public void createPlayersProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
		//synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				Player p = Server.playerHandler.players[i];
				if(p != null) {
					Client person = (Client)p;
					if(person != null) {
						if(person.getOutStream() != null) {
							if(person.distanceToPoint(x, y) <= 25){
								if (p.heightLevel == c.heightLevel)
									person.getPA().createProjectile(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time);
							}
						}
					}	
				}
			}
		//}
	}
	
	public void createPlayersProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
		//synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				Player p = Server.playerHandler.players[i];
				if(p != null) {
					Client person = (Client)p;
					if(person != null) {
						if(person.getOutStream() != null) {
							if(person.distanceToPoint(x, y) <= 25){	
								person.getPA().createProjectile2(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time, slope);	
							}
						}
					}	
				}
			}
		//}
	}
	

	/**
	** GFX
	**/
	public void stillGfx(int id, int x, int y, int height, int time) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(4);
				c.getOutStream().writeByte(0);
				c.getOutStream().writeWord(id);
				c.getOutStream().writeByte(height);
				c.getOutStream().writeWord(time);
				c.flushOutStream();
			}
		//}
	}
	
	//creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		//synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				Player p = Server.playerHandler.players[i];
				if(p != null) {
					Client person = (Client)p;
					if(person != null) {
						if(person.getOutStream() != null) {
							if(person.distanceToPoint(x, y) <= 25){	
								person.getPA().stillGfx(id, x, y, height, time);
							}
						}
					}	
				}
			}
		//}
	}
	
	/**
	* Objects, add and remove
	**/
	public void object(int objectId, int objectX, int objectY, int face, int objectType) {

			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(101);
				c.getOutStream().writeByteC((objectType<<2) + (face&3));
				c.getOutStream().writeByte(0);
			
				if (objectId != -1) { // removing
					c.getOutStream().createFrame(151);
					c.getOutStream().writeByteS(0);
					c.getOutStream().writeWordBigEndian(objectId);
					c.getOutStream().writeByteS((objectType<<2) + (face&3));
				}
				c.flushOutStream();
			}	
		
	}
	
	public void checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
		if (c.distanceToPoint(objectX, objectY) > 60)
			return;
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(101);
				c.getOutStream().writeByteC((objectType<<2) + (face&3));
				c.getOutStream().writeByte(0);
			
				if (objectId != -1) { // removing
					c.getOutStream().createFrame(151);
					c.getOutStream().writeByteS(0);
					c.getOutStream().writeWordBigEndian(objectId);
					c.getOutStream().writeByteS((objectType<<2) + (face&3));
				}
				c.flushOutStream();
			}	
		}
	

	/**
	* Show option, attack, trade, follow etc
	**/
	public String optionType = "null";
	public void showOption(int i, int l, String s, int a) {
		//synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				if(!optionType.equalsIgnoreCase(s)) {
					optionType = s;
					c.getOutStream().createFrameVarSize(104);
					c.getOutStream().writeByteC(i);
					c.getOutStream().writeByteA(l);
					c.getOutStream().writeString(s);
					c.getOutStream().endFrameVarSize();
					c.flushOutStream();
				}
			}
		//}
	}
	
	/**
	* Open bank
	**/
	
	public void openUpBank(){
		if(!c.canSpawn()) {
			c.sendMessage("@dre@You can only bank in safezones.");
			return;
		}
		if(c.inWild() || c.safeTimer > 0 || c.getPand().inMission()) {
			c.sendMessage("You cannot bank during combat.");
			return;
		}
		if(!c.lastIP.equals(c.firstIP)) {
			if(c.getBankPin().getFullPin().equalsIgnoreCase("") && !c.bankPin.equalsIgnoreCase("")){
				c.getBankPin().open();
				return;
			}
		}
			
		if(c.inTrade || c.tradeStatus == 1) {
			Client o = (Client) Server.playerHandler.players[c.tradeWith];
			if(o != null) {
				o.getTradeAndDuel().declineTrade();
			}
		}
		if(c.duelStatus == 1) {
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			if(o != null) {
				o.getTradeAndDuel().resetDuel();
			}
		}
		if(c.getOutStream() != null && c != null) {
			//Client o = (Client) Server.playerHandler.players[c.boxWith];
			c.sendMessage("@red@Type @dre@::dall@red@ while banking to deposit your inventory.");
			c.isBanking = true;
			c.canDepositAll = true;
			c.getItems().resetItems(5064);
			c.getItems().rearrangeBank();
			c.boxRequested = false;
			c.getItems().resetBank();
			c.getItems().resetTempItems();
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(5292);
			c.getOutStream().writeWord(5063);
			c.flushOutStream();
		}
	
	}
	
	/**
	* Private Messaging
	**/	
	public void logIntoPM() {
		setPrivateMessaging(2);
		for(int i1 = 0; i1 < Config.MAX_PLAYERS; i1++) {
			Player p = Server.playerHandler.players[i1];
			if(p != null && p.isActive) {
				Client o = (Client)p;
				if(o != null) {
					o.getPA().updatePM(c.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for(int i = 0; i < c.ignores.length; i++) {
			if(c.ignores[i] != 0)  {
				//loadPM2(c.ignores[i], 0);
			}
		}

		for(int i = 0; i < c.friends.length; i++) {
			if(c.friends[i] != 0)  {
				for(int i2 = 1; i2 < Config.MAX_PLAYERS; i2++) {
					Player p = Server.playerHandler.players[i2];
					if (p != null && p.isActive && Misc.playerNameToInt64(p.playerName) == c.friends[i])  {
						Client o = (Client)p;
						if(o != null) {
							if (c.playerRights >= 2 || p.privateChat == 0 || (p.privateChat == 1 && o.getPA().isInPM(Misc.playerNameToInt64(c.playerName)))) {
			 		 			loadPM(c.friends[i], 1);
			 		 			pmLoaded = true;
							}
							break;
						}
					}
				}
				if(!pmLoaded) {	
					loadPM(c.friends[i], 0);
				}
				pmLoaded = false;
			}
			for(int i1 = 1; i1 < Config.MAX_PLAYERS; i1++) {
				Player p = Server.playerHandler.players[i1];
    			if(p != null && p.isActive) {
					Client o = (Client)p;
					if(o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
		}
	}
	
	
	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = Server.playerHandler.players[pID];
		if(p == null || p.playerName == null || p.playerName.equals("null")){
			return;
		}
		Client o = (Client)p;
		if(o == null) {
			return;
		}
        long l = Misc.playerNameToInt64(Server.playerHandler.players[pID].playerName);

        if (p.privateChat == 0) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i]) {
                        loadPM(l, world);
                        return;
                    }
                }
            }
        } else if (p.privateChat == 1) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i]) {
                        if (o.getPA().isInPM(Misc.playerNameToInt64(c.playerName))) {
                            loadPM(l, world);
                            return;
                        } else {
                            loadPM(l, 0);
                            return;
                        }
                    }
                }
            }
        } else if (p.privateChat == 2) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i] && c.playerRights < 2) {
                        loadPM(l, 0);
                        return;
                    }
                }
            }
        }
    }
	
	public boolean isInPM(long l) {
        for (int i = 0; i < c.friends.length; i++) {
            if (c.friends[i] != 0) {
                if (l == c.friends[i]) {
                    return true;
                }
            }
        }
        return false;
    }
	
	
	/**
	 * Drink AntiPosion Potions
	 * @param itemId The itemId
	 * @param itemSlot The itemSlot
	 * @param newItemId The new item After Drinking
	 * @param healType The type of poison it heals
	 */
	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId, int healType) {
		c.attackTimer = c.getCombat().getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		if(c.duelRule[5]) {
			c.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if(!c.isDead && System.currentTimeMillis() - c.foodDelay > 2000) {
			if(c.getItems().playerHasItem(itemId, 1, itemSlot)) {
				c.sendMessage("You drink the "+c.getItems().getItemName(itemId).toLowerCase()+".");
				c.foodDelay = System.currentTimeMillis();
				// Actions
				if(healType == 1) {
					//Cures The Poison
				} else if(healType == 2) {
					//Cures The Poison + protects from getting poison again
				}
				c.startAnimation(0x33D);
				c.getItems().deleteItem(itemId, itemSlot, 1);
				c.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}
	
	
	/**
	* Magic on items
	**/
	
	public void magicOnItems(int slot, int itemId, int spellId) {
		
		switch(spellId) {
			case 1162: // low alch
			if(System.currentTimeMillis() - c.alchDelay > 2000) {	
				if(c.getItems().playerHasItem(itemId, 1, slot)){
				if(!c.getCombat().checkMagicReqs(49)) {
					break;
				}
				
				if(c.getItems().playerHasItem(itemId, 1, slot)){
				int TotPrice2 = 0;
				boolean canAlch = true;
				for (int i : Config.ITEM_SELLABLE) {
					if (i == itemId) {
						c.sendMessage("You can't alch this.");
						canAlch = false;
						break;
					} 
				}
				if(itemId == 995) {
					c.sendMessage("You can't alch coins.");
					canAlch = false;
					break;
				}
					TotPrice2 = (int) (Math.floor(c.getShops().getItemShopValue(itemId, 1, slot)) * .3);
					if(TotPrice2 >= 40000000){//This really just brought too much cash into the economy.
						c.sendMessage("This item is too expensive to be alched.");
						canAlch = false;
						break;
					}
					if(canAlch) {
						c.getItems().deleteItem(itemId, slot, 1);
						c.getItems().addItem(995, TotPrice2);
						c.startAnimation(c.MAGIC_SPELLS[49][2]);
						c.gfx100(c.MAGIC_SPELLS[49][3]);
						c.alchDelay = System.currentTimeMillis();
						sendFrame106(6);
						addSkillXP(c.MAGIC_SPELLS[49][7] * Config.MAGIC_EXP_RATE, 6);
						refreshSkill(6);
					}
				}
				}
			}
			break;
			case 1178: // high alch
			if(System.currentTimeMillis() - c.alchDelay > 2000) {	
				if(c.getItems().playerHasItem(itemId, 1, slot)){
				if(!c.getCombat().checkMagicReqs(50)) {
					break;
				}
				boolean canAlch = true;
				if(itemId == 995) {
					c.sendMessage("You can't alch coins.");
					canAlch = false;
					break;
				}				
				if(c.getItems().playerHasItem(itemId, 1, slot)){
				int TotPrice2 = 0;
				for (int i : Config.ITEM_SELLABLE) {
					if (i == itemId) {
						c.sendMessage("You can't alch this.");
						canAlch = false;
						break;
					} 
				}
				TotPrice2 = (int) (Math.floor(c.getShops().getItemShopValue(itemId)) * .5);
				if(TotPrice2 >= 40000000){//This really just brought too much cash into the economy.
					c.sendMessage("This item is too expensive to be alched.");
					canAlch = false;
					break;
				}
					if(canAlch) {
						c.getItems().deleteItem(itemId, slot, 1);
						c.getItems().addItem(995, TotPrice2);
						c.startAnimation(c.MAGIC_SPELLS[50][2]);
						c.gfx100(c.MAGIC_SPELLS[50][3]);
						c.alchDelay = System.currentTimeMillis();
						sendFrame106(6);
						addSkillXP(c.MAGIC_SPELLS[50][7] * Config.MAGIC_EXP_RATE, 6);
						refreshSkill(6);
					}
				}
				}
			}
			break;
		}
	}
	
	/**
	* Dieing
	**/
	public double memberBonus;
	double potentialBonus = 1;
	public void applyDead() {
		
		int totalPoints = 0;
		int[] MagicWeps = {2415,2416,2417,4710,4675,22494};
		int losesPVP = Misc.random(40);
		c.respawnTimer = 15;
		c.isDead = false;
		c.antiFirePot = false;
		c.getCombat().resetPlayerAttack();
		
		if(c.duelStatus != 6) {
	
			c.killerId = findKiller();
			Client o = (Client) Server.playerHandler.players[c.killerId];
			if(o != null && (c.killerId != c.playerId)) {	
			
		if(c.lastIP.equals(o.lastIP) && !c.lastIP.equals("127.0.0.1")) {
			c.sendMessage("This fight doesn't have an effect on your PK points");
			return;
		}
		
		if(c.prayerActive[21])
			appendRetribution(o);
		
		c.damageTimer = 6;
		c.boxRequested = false;
		o.boxRequested = true;
			
			
			
			if(o.doingStarter == 1) {
				if(o.sTask4 == 0) {
					o.sTask4 = 1;
					o.sendMessage("You completed a starter task!");
					o.getItems().addItem(995,1000000);
				}
			}
			if(o.inBoxIsland()) {
				o.canUsePortal = (o.canUsePortal + 1);
				o.sendMessage("@red@You can now use the portal.");
				o.getBoxing().resetBox();
			}
			o.specAmount = 10;
			o.sendMessage("Your special attack refills completely!");
					
			if(!o.inPits){
				if(o.killsThisMinute <= 2){
					if(o.cantGetKills == 0){
						if(c.playerId != o.playerId){
							if(!c.inPits && !c.inFunPk() && !c.inStakeArena() && !c.getPand().inMission()){
							c.specAmount = 8.00;
							c.DC++;
							} else if(c.inFunPk() || c.inStakeArena()) {
									c.sendMessage("@blu@You receive no penalty for dying in a safe area.");
									c.specAmount = 10.0;
							} else if(c.inPits) {
									c.specAmount = 10.0;
									c.sendMessage("@blu@You receive no penalty for dying in Fight pits.");
							}
				
							if(/*o.lastKilled != Server.playerHandler.players[c.playerId].connectedFrom */!c.inFunPk() && !c.inStakeArena() && !c.getPand().inMission() && !c.isInDuelArea() && !c.inPits){
								o.KC++;
								
								
								
								o.killsThisMinute++;
								o.playerKilled = c.playerId;
								if (!o.inFunPk() && !o.inPits && !o.inStakeArena()){
								
									totalPoints = 100;

									if(o.inMulti() && Server.npcHandler.eventType == 2) { //for events
											o.EP += 5;
											o.allEP += 5;
											o.eventScore++;
											o.sendMessage("[@red@Event@bla@] @red@Your score: @dre@"+o.eventScore+" "+((o.eventScore == 1) ? "kill" : "kills")+"@red@, In lead: @dre@"+Server.npcHandler.eventWinnerName()+"");
											totalPoints += 5;
										} else if(Server.npcHandler.eventType == 1 && o.isInEdge()) {
											o.EP += 10;
											o.allEP += 10;
											o.eventScore++;
											o.sendMessage("[@red@Event@bla@] @red@Your score: @dre@"+o.eventScore+" "+((o.eventScore == 1) ? "kill" : "kills")+"@red@, In lead: @dre@"+Server.npcHandler.eventWinnerName()+"");
											totalPoints += 5;
									}
								
									if(c.playerId == o.myTarget){
										totalPoints += 5;
										if(o.gameMode == 2) {
											o.targetPoints += 2;
											o.totalTargetPoints += 2;
											o.sendMessage("(@red@+1@bla@) Extra target point received! (Trained account bonus)");
										} else {
											o.targetPoints += 1;
											o.totalTargetPoints += 1;
										}
										c.specAmount = 10.0;
										if(o.worshippedGod == 1 && o.godReputation >= 250 && Misc.random(3) == 0){
											o.targetPoints += 1;
											o.totalTargetPoints += 1;
											o.sendMessage("You now have @blu@"+o.targetPoints+"@bla@ target points. (@red@+2@bla@)");
										} else {
											o.sendMessage("You now have @blu@"+o.targetPoints+"@bla@ target points. (@red@+1@bla@)");
										}
										int winAmount, loseAmount, ratingAmount1, ratingAmount2;
										double percentage = (1 / ((double)(1 + (Math.pow(10,(double)((double)(o.rating - c.rating)/400))))));
										ratingAmount1 = 32;
										ratingAmount2 = 32;
										if(o.targFights <= 10 && o.rating < 1750)
										ratingAmount1 = 50;
										if(c.targFights <= 10 && c.rating < 1750)
										ratingAmount2 = 0;
										if(o.rating >= 1750)
									 	ratingAmount1 = 25;
										if(c.rating >= 1750)
										ratingAmount2 = 25;
										if(o.rating >= 2250)
										ratingAmount1 = 15;
										if(c.rating >= 2250)
										ratingAmount2 = 15;
										winAmount = (int)((double)ratingAmount1 * ((double)1 - percentage));
										loseAmount = (int)((double)ratingAmount2 * ((double)1 - percentage));
										o.rating += winAmount;
										c.rating = c.rating - loseAmount;
										o.targFights++;
										c.targFights++;
										o.sendMessage("Your rating is now "+o.rating+". (@red@+"+winAmount+"@bla@)");
										c.sendMessage("Your rating is now "+c.rating+". (@red@-"+loseAmount+"@bla@)");
										
										o.lastTargetName = c.playerName;
										c.lastTargetName = o.playerName;
										c.myTarget = -1;
										o.myTarget = -1;
										c.target = 0;
										o.target = 0;
										o.getPA().createPlayerHints(10, -1);
										c.getPA().createPlayerHints(10, -1);
										o.targetName = "None";
										c.targetName = "None";
										o.findTarget();
										c.findTarget();
										c.getItems().addPVP(true, Server.npcHandler.eventType, -1);//add pvp drop for target fight
									} else {
										c.getItems().addPVP(false, Server.npcHandler.eventType, -1);//add pvp drop for non-target
									}
									o.cStreak += 1;
									o.wStreak += 1;
									if(o.cStreak > 2) {
										if(o.wStreak == o.cStreak){
											c.sendAll("[@red@Server@bla@]: @dre@"+o.playerName+"@red@ has killed @dre@"+c.playerName+"@red@ and is on a @dre@"+o.cStreak+"@red@ Wilderness killstreak!");
										} else {
											c.sendAll("[@red@Server@bla@]: @dre@"+o.playerName+"@red@ has killed @dre@"+c.playerName+"@red@ and is on a @dre@"+o.cStreak+"@red@ killstreak! Wildy Streak: @dre@"+o.wStreak);
										}
										if(o.cStreak < 6) {
											o.sendMessage("@bla@You gain @red@"+o.cStreak+" @bla@extra PK Points because of your @red@"+o.cStreak+" @bla@killstreak.");
											totalPoints += o.cStreak;
										} else {
											o.sendMessage("@bla@You gain @red@ 5 @bla@extra PK Points because of your @red@"+o.cStreak+" @bla@killstreak.");
											totalPoints += 5;
										}
									}
									
									
			String killText = o.playerName + " killed " + c.playerName + " with " + o.getItems().getItemName(o.playerEquipment[o.playerWeapon]);
			c.getPA().writeKillLine(killText);

			if(o.cStreak > o.hStreak) {
				o.sendMessage("Congratulations, your highest kill streak has increased!");								
				o.hStreak = o.cStreak;
			}

			//if(o.inBoxIsland()) {
				//o.playerLevel[3] = o.getLevelForXP(o.playerXP[3]);
				//o.canUsePortal = 1;
			//}
			
		
			
			if(!o.usingBow && !o.usingMagic && !(o.playerEquipment[c.playerWeapon] == 19780 || o.playerEquipment[c.playerWeapon] == 22494 || o.playerEquipment[c.playerWeapon] == 19112 || o.playerEquipment[c.playerWeapon] == 2415 || o.playerEquipment[c.playerWeapon] == 2416 || o.playerEquipment[c.playerWeapon] == 2417)/* && o.playerEquipment[c.playerWeapon] != MagicWeps[]*/) {
				o.meleeKills++;
				o.sendMessage("Melee Kill points increased. You now have @blu@"+o.meleeKills+"@bla@ Melee Kill points.");
			}
			if(o.usingBow) {
				o.rangeKills++;
				o.sendMessage("Ranged Kill points increased. You now have @blu@"+o.rangeKills+"@bla@ Ranged Kill points.");
			}
			if(o.castingMagic || (o.playerEquipment[c.playerWeapon] == 19780 ||  o.playerEquipment[c.playerWeapon] == 19112 || o.playerEquipment[c.playerWeapon] == 22494 || o.playerEquipment[c.playerWeapon] == 2415 || o.playerEquipment[c.playerWeapon] == 2416 || o.playerEquipment[c.playerWeapon] == 2417 || o.playerEquipment[c.playerWeapon] == 15050)/* || o.playerEquipment[c.playerWeapon] == MagicWeps[]*/) {
				o.mageKills++;
				o.sendMessage("Mage Kill points increased. You now have @blu@"+o.mageKills+"@bla@ Mage Kill points.");
			}
	
			//PK Challenge weapons
			/*if(o.playerEquipment[c.playerWeapon] == 1377 || o.playerEquipment[c.playerWeapon] == 4587 || 
			 o.playerEquipment[c.playerWeapon] == 1305 ||  o.playerEquipment[c.playerWeapon] == 1434 ||  o.playerEquipment[c.playerWeapon] == 7158 || 
			  o.playerEquipment[c.playerWeapon] == 6528 || o.playerEquipment[c.playerWeapon] == 6527 ||  o.playerEquipment[c.playerWeapon] == 6523 || 
			   o.playerEquipment[c.playerWeapon] == 861
			){
				o.pkChallenge++;
				o.sendMessage("PK Challenge points increased! You now have @blu@"+o.pkChallenge+"@bla@ points.");
			}*/
								int pkStolen = 0;
									if(c.inVarrock() || c.inVarrock2()){
										if(c.pkPoints >= 10) {
											c.pkPoints -= 10;
											c.sendMessage(o.playerName+" defeats you and takes @red@10@bla@ of your PK Points.");
										} else {
											pkStolen = c.pkPoints;
											c.pkPoints = 0;
											c.sendMessage(o.playerName+" defeats you and takes @red@"+pkStolen+"@bla@ of your PK Points.");
										}
									}
									
									if(o.inVarrock() || o.inVarrock2()){
										if(c.pkPoints >= 10) {
											o.pkPoints += 10;
											o.sendMessage("You take @red@10@bla@ of "+c.playerName+"'s PK Points.");
										} else {
											o.pkPoints += pkStolen;
											o.sendMessage("You take @red@"+pkStolen+"@bla@ of "+c.playerName+"'s PK Points.");
										}
									}
									
									/*if(o.inBoxIsland()) {
									o.canUsePortal++;
									o.sendMessage("You can now use the portal.");
									}*/
									
									double riskBonus = 1;
									double memberBonus = 1;
									
									if(o.worshippedGod == 2 && o.godReputation2 >= 200 && Misc.random(19) <= 3){//Zamorak Bonus
										totalPoints += 5;
									}
									
									/*if(o.playerLevel[3] <= 15 && o.playerLevel[3] > 10) {
									riskBonus = 1.25;
									 o.sendMessage("@red@Risked it! You killed your opponent with under 15 HP, earning you 25% Bonus PK Points!");
									}
									if(o.playerLevel[3] <= 10 && o.playerLevel[3] > 5) {
									riskBonus = 1.5;
									 o.sendMessage("@red@Risked it! You killed your opponent with under 10 HP, earning you 50% Bonus PK Points!");
									}
									if(o.playerLevel[3] <= 5 && o.playerLevel[3] > 0) {
									riskBonus = 1.75;
									 o.sendMessage("@red@Risked it! You killed your opponent with under 5 HP, earning you 75% Bonus PK Points!");
									}*/
										
									
									if(c.redSkull == 1)
										totalPoints += 2;
									if(o.redSkull == 1)
										totalPoints += 2;
									
									if(o.gameMode == 2)
										totalPoints = (int)(totalPoints*1.25); //increases PKP for Trained accs
										
									if(o.memberStatus >= 1 && o.memberStatus <= 4){//Donator Bonus
										
										if(o.memberStatus == 1)
											memberBonus = 1.1;
										if(o.memberStatus == 2)
											memberBonus = 1.2;
										if(o.memberStatus == 3)
											memberBonus = 1.3;
										
									}

									/*if(o.potential >= 1)
										potentialBonus = ((double)(o.potential/100)) + 1;*/

									totalPoints = (int)((double)totalPoints * memberBonus);
									
										o.pkPoints += totalPoints;
										o.sendMessage("You now have @blu@"+o.pkPoints+" @red@PK Points@bla@. (@blu@+"+totalPoints+"@bla@)");
								
								if(o.gameMode == 1 || o.gameMode == 0) {
									if(o.worshippingGod()){
										if(c.worshippingGod() && o.worshippedGod != c.worshippedGod){
										o.addGodReputation(1);
										o.sendMessage("You have gained @blu@" + (o.worshippedGod == 1 ? o.godReputation : o.godReputation2) + "@bla@ reputation with " + o.worshippedGodString() + " (@red@+1@bla@)");
										}
									}
								}
								if(o.gameMode == 2) {
									if(o.worshippingGod()){
										if(c.worshippingGod() && o.worshippedGod != c.worshippedGod){
										o.addGodReputation(2);
										o.sendMessage("You have gained @blu@" + (o.worshippedGod == 1 ? o.godReputation : o.godReputation2) + "@bla@ reputation with " + o.worshippedGodString() + " (@red@+2@bla@)(@red@Train Mode Bonus@bla@)");
										}
									}
								}
								
								/*if(o.potential > 0) {
									o.potential = (int) (o.potential * .7);
									o.sendMessage("Your potential is decreased because you killed a player. Potential: @blu@"+o.potential+"%");
								}*/
									totalPoints = 0;
									//o.updateAchieves(c.playerName,o.playerEquipment[o.playerWeapon]);
										if(o.hasRegistered != 1) 
											o.register(o.playerName, "o");
								} 
						
								if (o.inFunPk() || o.inPitsGameArea() || o.inPits || o.inStakeArena()){
										o.pkPoints = (o.pkPoints + 10);
									if(o.memberStatus < 1){
										o.sendMessage("@red@You now have "+o.pkPoints+" PK Points. (@red@+1@bla@)");
									} else if(o.memberStatus >= 1){
										o.pkPoints = (o.pkPoints + 10);
										o.sendMessage("@red@You now have "+o.pkPoints+" PK Points. (@red@+2@bla@)");
									}
								}
								/*if(o.inBoxIsland()) {
									o.canUsePortal = (o.canUsePortal +1);
									o.sendMessage("@red@You can now use the portal.");
								}*/
								o.lastKilled = Server.playerHandler.players[c.playerId].connectedFrom;
								if(!c.inPits && !c.inFightCaves() && !c.inPitsGameArea() && !c.inFunPk() && !c.inStakeArena() && !c.safe() && !c.inDuel2() && c.cStreak >= 5) {
									c.sendAll("[@red@Server@bla@]: @dre@"+o.playerName+"@red@ just "+killMessage[new java.util.Random().nextInt(killMessage.length)]+" @dre@"+c.playerName+"'s@red@ "+c.cStreak+" killstreak!");
								}
								
							} else {
							o.sendMessage("@red@You gain no PK points.");
							}
						}
				} else {
					o.sendMessage("@red@You have been blocked from receiving PK Points for 1 hour.");
					c.playerKilled = c.playerId;
					c.DC++;
				}
				} else {
					c.DC++;
					o.cantGetKills = 1;
					o.cantGetKillsTimer = 3600;
					o.sendMessage("@red@You have been blocked from receiving PK Points for 1 hour.");
				}
				} else {
					o.sendMessage("@red@You now have "+o.pkPoints+" PK Points. (+3)");
					o.pkPoints = (o.pkPoints + 3);
					//o.sendMessage("@red@Fight pits PK point bonus is temporarily disabled!");
				}
				
				if(o.duelStatus == 5) {
					o.duelStatus++;
				}
					o.questTab();
			}
		}
		c.faceUpdate(0);
		c.npcIndex = 0;
		c.playerIndex = 0;
		c.stopMovement();
		if(c.duelStatus <= 4) {
			c.sendMessage("Oh dear, you are dead!");
			if(!c.inPits && !c.inFightCaves() && !c.inPitsGameArea() && !c.inFunPk() && !c.inStakeArena() && !c.safe() && !c.inDuel2()) {
				c.cStreak = 0;
			}
		} else if(c.duelStatus != 6) {
			c.sendMessage("You have lost the duel!");
		}
		/*if(!c.inPits && !c.inFightCaves() && !c.inPitsGameArea() && !c.inFunPk() && !c.inStakeArena() && !c.safe() && !c.inDuel2() && c.potential > 0) {
			c.sendMessage("Your potential bonus has been reset to 0 percent.");
			c.potential = 0;
		}*/
		c.questTab();
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.venomDamage = 0;
		c.lastVeng = 0;
		c.vengOn = false;
		c.doubleHit = false;
		c.usingClaws = false;
		resetFollowers();
		c.attackTimer = 0;
		//c.safeTimer = 0;
		//c.getBoxing().resetBox();
		requestUpdates();
		c.dbowSpec = false;
	}
	
	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[c.playerId] = 0;			
			}		
		}	
	}
	/*
*Vengeance
*/
public void castVeng() {
	if(c.duelRule[4]) {
        	c.sendMessage("Magic is disabled in this duel!");
       		return;
    	}
	if(c.playerLevel[6] < 94) {
		c.sendMessage("You need a magic level of 94 to cast this spell.");
		return;
	}
	if(c.playerLevel[1] < 40) {
		c.sendMessage("You need a defence level of 40 to cast this spell.");
		return;
	}
	if(!c.getItems().playerHasItem(9075, 4) || !c.getItems().playerHasItem(557, 10) || !c.getItems().playerHasItem(560, 2)) {
		c.sendMessage("You don't have the required runes to cast this spell.");
		return;
	}
	if(System.currentTimeMillis() - c.lastCast < 30000) {
		c.sendMessage("You can only cast vengeance every 30 seconds.");
		return;
	}
	if(c.vengOn) {
		c.sendMessage("You already have vengeance cast.");
		return;
	}
	c.startAnimation(4410);
	c.forcedText = c.playerName+" casts Vengeance";
	c.updateRequired = true;
	c.forcedChatUpdateRequired = true;
	//c.gfx100(726);
	//c.gfx100(604);//Just use c.gfx100
	c.getItems().deleteItem2(9075, 4);
	c.getItems().deleteItem2(557, 10);//For these you need to change to deleteItem(item, itemslot, amount);.
	c.getItems().deleteItem2(560, 2);
	addSkillXP(10000, 6);
	refreshSkill(6);
	c.vengOn = true;
	c.lastCast = System.currentTimeMillis();
}
	public void vengMe() {
		if (System.currentTimeMillis() - c.lastVeng > 30000) {
			if (c.getItems().playerHasItem(557,10) && c.getItems().playerHasItem(9075,4) && c.getItems().playerHasItem(560,2)) {
				c.vengOn = true;
				c.lastVeng = System.currentTimeMillis();
				c.startAnimation(4410);
				c.gfx100(726);
				c.getItems().deleteItem(557,c.getItems().getItemSlot(557),10);
				c.getItems().deleteItem(560,c.getItems().getItemSlot(560),2);
				c.getItems().deleteItem(9075,c.getItems().getItemSlot(9075),4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell.");
			}
		} else {
			c.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}
	
	public void resetTb() {
		c.teleBlockLength = 0;
		c.teleBlockDelay = 0;	
	}
	
	public void handleStatus(int i, int i2, int i3) {
	}
	
	public void resetFollowers() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (Server.playerHandler.players[j].followId == c.playerId) {
					Client c = (Client)Server.playerHandler.players[j];
					c.getPA().resetFollow();
				}			
			}		
		}	
	}
	
	public void fremmyHelm() {
		if (c.playerEquipment[c.playerHat] == 3748) {
			if (c.playerLevel[3] > 0 && c.playerLevel[3] <= 10 && c.underAttackBy > 0) {
				c.playerLevel[3] += 10;
			}
		}
	}
	
	public void giveLife() {
		c.isDead = false;
		c.faceUpdate(-1);
		c.freezeTimer = 0;

						boolean failed = true;

							for(int i=0;i<c.PrimalArmor.length;i++) {
								int chance = Misc.random(5000);
						       	if(c.playerEquipment[c.playerHat] == c.PrimalArmor[i] && chance <= 25 && failed) {
						       		c.getItems().deleteEquipment(0, c.playerHat);
						       		failed = false;
						       	} else if(c.playerEquipment[c.playerChest] == c.PrimalArmor[i] && chance <= 25 && failed) {
						       		c.getItems().deleteEquipment(0, c.playerChest);
						       		failed = false;
						       	} else if(c.playerEquipment[c.playerLegs] == c.PrimalArmor[i] && chance <= 25 && failed) {
						       		c.getItems().deleteEquipment(0,c.playerLegs);
						       		failed = false;
						       	} else if(c.getItems().playerHasItem(c.PrimalArmor[i],1) && failed) {
						      		for(int j=0;j<c.getItems().getItemCount(c.PrimalArmor[i]);j++) {
						      			if(Misc.random(10000) <= 25 && failed) {
						        			c.getItems().deleteItem(c.PrimalArmor[i], 1);
						        			failed = false;
						      			}
						      		}
						        }
					   		}

					   		if(!failed)
						   		c.sendMessage("@dre@A piece of your Primal equipment fades away...");

						   	failed = true;

						   	int[] PvpItems = { 13864,13861,13858,13896,13902,13899,13890,13884,13893,13887,13883,13879,13876,13873,13870 };
						   	
					   		for(int i=0;i<PvpItems.length;i++) {
								int chance = Misc.random(100);
						       	if(c.playerEquipment[c.playerHat] == PvpItems[i] && chance <= 1) {
						       		c.getItems().deleteEquipment(0, c.playerHat);
						       		failed = false;
						       	} else if(c.playerEquipment[c.playerChest] == PvpItems[i] && chance <= 1) {
						       		c.getItems().deleteEquipment(0, c.playerChest);
						       		failed = false;
						       	} else if(c.playerEquipment[c.playerLegs] == PvpItems[i] && chance <= 1) {
						       		c.getItems().deleteEquipment(0,c.playerLegs);
						       		failed = false;
						       	} else if(c.playerEquipment[c.playerWeapon] == PvpItems[i] && chance <= 1) {
						       		c.getItems().deleteEquipment(0,c.playerWeapon);
						       		failed = false;
						       	} else if(c.getItems().playerHasItem(PvpItems[i],1)) {
						      		for(int j=0;j<c.getItems().getItemCount(PvpItems[i]);j++) {
						      			if(Misc.random(100) <= 1) {
						        			c.getItems().deleteItem(PvpItems[i], 1);
						        			failed = false;
						      			}
						      		}
						        }
					   		}

					   		if(!failed)
						   		c.sendMessage("@dre@Some of your Ancient warrior's equipment fades away...");

			if (!c.inFightCaves() && !c.inPitsGameArea() && !c.getPand().inMission() && !c.inFunPk() && !c.inStakeArena() && !c.safe() && !c.inDuel2()) {
				c.getItems().resetKeepItems();
					if(c.playerRights != 3 && c.safeTimer >= -8) {
						if(!c.isSkulled && !c.isInFala() && !c.isInArd()) {	// what items to keep
							c.getItems().keepItem(0, true);
							c.getItems().keepItem(1, true);	
							c.getItems().keepItem(2, true);
						}	
						if((c.prayerActive[10]) && c.redSkull != 1) {
							c.getItems().keepItem(3, true);
						}
						if(c.playerRights != 3) {
							if(!c.isInDuelArea() && !c.inPitsGameArea() && !c.inFunPk() && !c.inStakeArena() && !c.getPand().inMission()){
								c.getItems().dropAllItems(); // drop all items
								c.getItems().deleteAllItems(); // delete all items
							}
						}
						if(!c.isSkulled && !c.isInFala() && !c.isInArd()) { // add the kept items once we finish deleting and dropping them	
							for (int i1 = 0; i1 < 3; i1++) {
								if(c.itemKeptId[i1] > 0) {
									c.getItems().addItem(c.itemKeptId[i1], 1);
								}
							}
						}	
						if(c.prayerActive[10] || c.isInArd() && c.redSkull != 1) { // if we have protect items 
							if(c.itemKeptId[3] > 0) {
								c.getItems().addItem(c.itemKeptId[3], 1);
							}
						}
					}
					c.getItems().resetKeepItems();
				} else if (c.inPitsGameArea()) {
					Server.fightPits.removePlayerFromPits(c.playerId);
					c.pitsStatus = 1;
			}

			if (c.pitsStatus == 1) {
					movePlayer(2399, 5173, 0);
				} else if(c.duelStatus <= 4) { // if we are not in a duel repawn to wildy
					movePlayer(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
					c.isSkulled = false;
					c.skullTimer = 0;
					c.lastVeng = 0;
					c.vengOn = false;
					c.attackedPlayers.clear();
				} else { // we are in a duel, respawn outside of arena
					Client o = (Client) Server.playerHandler.players[c.duelingWith];
					if(o != null) {
						o.getPA().createPlayerHints(10, -1);
						if(o.duelStatus == 6) {
							o.getTradeAndDuel().duelVictory();
						}
					}
					movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
					if(c.duelStatus != 6) { // if we have won but have died, don't reset the duel status.
						c.getTradeAndDuel().resetDuel();
					}
			}

		PlayerSave.saveGame(c);
		c.getCombat().resetPrayers();
		for (int i = 0; i < 20; i++) {
			c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
			c.getPA().refreshSkill(i);
		}
		c.getCombat().resetPlayerAttack();
		resetAnimation();
		frame1();
		resetTb();
		c.isSkulled = false;
		c.attackedPlayers.clear();
		c.headIconPk = -1;
		c.skullTimer = -1;
		c.redSkull = 0;
		c.damageTaken = new int[Config.MAX_PLAYERS];
		c.getPA().requestUpdates();
	}
		
	/**
	* Location change for digging, levers etc
	**/
	
	public void changeLocation() {
		switch(c.newLocation) {
			case 1:
			movePlayer(3578,9706,-1);
			break;
			case 2:
			movePlayer(3568,9683,-1);
			break;
			case 3:
			movePlayer(3557,9703,-1);
			break;
			case 4:
			movePlayer(3556,9718,-1);
			break;
			case 5:
			movePlayer(3534,9704,-1);
			break;
			case 6:
			movePlayer(3546,9684,-1);
			break;
		}
		c.newLocation = 0;
	}
	
	
	/**
	* Teleporting
	**/
	public void spellTeleport(int x, int y, int height) {
		c.getPA().startTeleport(x, y, height, c.playerMagicBook == 1 ? "ancient" : "modern");
	}
public void startMovement(int x, int y, int height) {
	
	/*if (c.playerLevel[3] < 41 && c.inWild() || c.playerLevel[3] < 41 && c.isInHighRiskPK() || c.playerLevel[3] < 41 && c.inFaladorPvP()) {
		c.sendMessage("@red@Your hitpoints are too low, you can't teleport!");
		return;
	} 
	if (c.playerLevel[3] < 41) {
		c.sendMessage("@red@Your hitpoints are too low, you can't teleport!");
		return;
	} */
	if (c.inNoTele()) {
		c.sendMessage("You can't teleport here!");
		return;
	}
	/*if (c.isInStartingSquare()) {
		c.sendMessage("You can't teleport here!");
		return;
	}*/
	/*if (c.playerLevel[3] < 41 && c.inDuel2()) {
		c.getItems().addItem(391, 1);
		c.sendMessage("Your hitpoints are too low, you can't teleport!");
	}*/
	if(c.inPits || inPitsWait()){
	c.sendMessage("You can't teleport during fight pits!");
	return;
	}
		if((c.inFunPk() || c.inStakeArena()) && c.safeTimer > 0) {
			c.sendMessage("You can't teleport during a safe fight!");
			return;
		}
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;			
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;

			}
		
		
	}	
	public void startTeleport(int x, int y, int height, String teleportType) {
		c.canDepositAll = false;

		/*if (c.isInStartingSquare()) {
			c.sendMessage("You can't teleport here!");
			return;
		}*/
		/*if((c.inFunPk() || c.inStakeArena()) && (c.underAttackBy > 0 || c.underAttackBy2 > 0)){
			c.sendMessage("You can't teleport during a safe fight!");
			return;
		}*/
		if(c.inPits || inPitsWait()){
			c.sendMessage("@red@You can't teleport during fight pits!");
				return;
		}
		if (c.inNoTele()) {
			c.sendMessage("You can't teleport here!");
				return;
		}
		if(c.inTrade){
			c.sendMessage("@red@You can't teleport in a trade.");
			return;
		}
		if(c.duelStatus == 5) {
			c.sendMessage("@red@You can't teleport during a duel!");
			return;
		}
		if(c.isInJail()){
			c.sendMessage("@red@You can't escape from here! Be patient.");
			return;
		}
		if(c.inWild() || c.safeTimer > 0) {
			c.sendMessage("You may only teleport using teleport tabs or ::home in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			c.sendMessage("@blu@Donators can use ::untb.");
				return;
		}
		if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;	
			c.getCombat().resetPlayerAttack();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if(c.underAttackBy > 0 && !c.inFunPk() && !c.inStakeArena()){
				if(c.KC < 50) {
					c.pkPoints = c.pkPoints - 3;
					c.sendMessage("@red@You lose 3 PK Points for teleporting in combat.");
				} else {
					if(!c.getPand().inMission()){
					if(c.pkPoints*0.05 <= 50) {
							c.sendMessage("@red@You lose "+(c.pkPoints*0.05)+" (5%) of your PK Points for teleporting in combat.");
							c.pkPoints -= c.pkPoints*0.05;
						} else {
							c.sendMessage("@red@You lose 50 of your PK Points for teleporting in combat.");
							c.pkPoints -= 50;
						}
					}
				}
			}
			if(teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(714);
				c.teleTimer = 11;
				c.teleGfx = 308;
				c.teleEndAnimation = 715;
			} 
			if(teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(1979);
				c.teleGfx = 0;
				c.teleTimer = 9;
				c.teleEndAnimation = 0;
				c.gfx0(392);
			}
			
		}
	}

	public void startTeleport3(int x, int y, int height, String teleportType) {
		c.canDepositAll = false;

		/*if (c.isInStartingSquare()) {
			c.sendMessage("You can't teleport here!");
			return;
		}*/
		/*if((c.inFunPk() || c.inStakeArena()) && (c.underAttackBy > 0 || c.underAttackBy2 > 0)){
			c.sendMessage("You can't teleport during a safe fight!");
			return;
		}*/
		if(c.inPits || inPitsWait()){
			c.sendMessage("@red@You can't teleport during fight pits!");
				return;
		}
		if (c.inNoTele()) {
			c.sendMessage("You can't teleport here!");
				return;
		}
		if(c.inTrade){
			c.sendMessage("@red@You can't teleport in a trade.");
			return;
		}
		if(c.duelStatus == 5) {
			c.sendMessage("@red@You can't teleport during a duel!");
			return;
		}
		if(c.isInJail()){
			c.sendMessage("@red@You can't escape from here! Be patient.");
			return;
		}
		if((c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL)) {
			c.sendMessage("@red@You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			c.sendMessage("@blu@Donators can use ::untb.");
				return;
		}
		if(c.ladderTimer > 0) {
			return;
		}
		if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;	
			c.getCombat().resetPlayerAttack();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if(c.underAttackBy > 0 && !c.inFunPk() && !c.inStakeArena()){
				if(c.KC < 50) {
					c.pkPoints = c.pkPoints - 3;
					c.sendMessage("@red@You lose 3 PK Points for teleporting in combat.");
				} else {
					if(!c.getPand().inMission()){
					if(c.pkPoints*0.05 <= 50) {
							c.sendMessage("@red@You lose "+(c.pkPoints*0.05)+" (5%) of your PK Points for teleporting in combat.");
							c.pkPoints -= c.pkPoints*0.05;
						} else {
							c.sendMessage("@red@You lose 50 of your PK Points for teleporting in combat.");
							c.pkPoints -= 50;
						}
					}
				}
			}
			if(teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(714);
				c.teleTimer = 11;
				c.teleGfx = 308;
				c.teleEndAnimation = 715;
			} 
			if(teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(1979);
				c.teleGfx = 0;
				c.teleTimer = 9;
				c.teleEndAnimation = 0;
				c.gfx0(392);
			}
			
		}
	}

	public void startTeleportGreen(int x, int y, int height, String teleportType) {
		c.canDepositAll = false;

		if(c.inPits || inPitsWait()){
			c.sendMessage("@red@You can't teleport during fight pits!");
			return;
		}
		if (c.inNoTele()) {
			c.sendMessage("You can't teleport here!");
			return;
		}
		/*if (c.isInStartingSquare()) {
			c.sendMessage("You can't teleport here!");
			return;
		}*/
		if(c.inTrade){
			c.sendMessage("@red@You can't teleport in a trade.");
			return;
		}
		if(c.duelStatus == 5) {
			c.sendMessage("@red@You can't teleport during a duel!");
			return;
		}
		if(c.isInJail()){
			c.sendMessage("@red@You can't escape from here! Be patient.");
			return;
		}
		if((c.inWild() && c.wildLevel > 35)) {
			c.sendMessage("@red@You can't teleport above level 35 in the wilderness.");
			return;
		}
		if((c.inWild() && c.wildLevel > 20)) {
			c.getCombat().greenCharges(c);
		} else {
			c.sendMessage("Your shield loses no charges, because you were below 20 wilderness.");
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("@red@You are teleblocked and can't teleport. Donators can use ::untb!");
			return;
		}
		if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;	
			c.getCombat().resetPlayerAttack();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if(c.underAttackBy > 0 && !c.inFunPk() && !c.inStakeArena()){
				if(c.KC < 50) {
					c.pkPoints = c.pkPoints - 3;
					c.sendMessage("@red@You lose 3 PK Points for teleporting in combat.");
				} else {
					if(!c.getPand().inMission()){
					if(c.pkPoints*0.05 <= 50) {
							c.sendMessage("@red@You lose @dre@"+(c.pkPoints*0.05)+" (5%)@red@ of your PK Points for teleporting in combat.");
							c.pkPoints -= c.pkPoints*0.05;
						} else {
							c.sendMessage("@red@You lose @dre@"+(c.pkPoints*0.01)+" (1%)@red@ of your PK Points for teleporting in combat.");
							c.pkPoints -= c.pkPoints*0.01;
						}
					}
				}
			}
			if(teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(714);
				c.teleTimer = 11;
				c.teleGfx = 308;
				c.teleEndAnimation = 715;
			} 
			if(teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(1979);
				c.teleGfx = 0;
				c.teleTimer = 9;
				c.teleEndAnimation = 0;
				c.gfx0(392);
			}
			
		}
	}

	public void startTeleport2(int x, int y, int height) {
		c.canDepositAll = false;
	/*if (c.playerLevel[3] < 41 && c.inWild() || c.playerLevel[3] < 41 && c.isInHighRiskPK() || c.playerLevel[3] < 41 && c.inFaladorPvP()) {
		c.sendMessage("@red@Your hitpoints are too low, you can't teleport!");
		return;
	} 
	
	if (c.playerLevel[3] < 41) {
		c.sendMessage("@red@Your hitpoints are too low, you can't teleport!");
		return;
	} 
	if (c.playerLevel[3] < 41 && c.inDuel2()) {
		c.getItems().addItem(391, 1);
		c.sendMessage("Your hitpoints are too low, you can't teleport!");
	}*/
	if (c.inNoTele()) {
		c.sendMessage("You can't teleport here!");
		return;
	}
	if(c.ladderTimer > 0)
		return;
	/*if (c.isInStartingSquare()) {
		c.sendMessage("You can't teleport here!");
		return;
	}*/
	if(c.inPits || inPitsWait()){
	c.sendMessage("You can't teleport during fight pits!");
	return;
	}
if(c.inTrade){
	c.sendMessage("You can't teleport in a trade.");
	return;
	}
		
		if((c.inFunPk() || c.inStakeArena()) && c.safeTimer > 0) {
			c.sendMessage("You can't teleport during a safe match!");
			return;
		}
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(!c.isDead && c.teleTimer == 0) {			
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;			
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.startAnimation(714);
			c.teleTimer = 11;
			c.teleGfx = 308;
			c.teleEndAnimation = 715;
			c.canDepositAll = false;
		}
	} 

	public void processTeleport() {
		c.teleportToX = c.teleX;
		c.teleportToY = c.teleY;
		c.heightLevel = c.teleHeight;
		if(c.teleEndAnimation > 0) {
			c.startAnimation(c.teleEndAnimation);
		}
	}
		
	public void movePlayer(int x, int y, int h) {
		c.ladderTimer = 2;
		c.homeTimer = 0;
		c.resetWalkingQueue();
		c.teleportToX = x;
        c.teleportToY = y;
		c.heightLevel = h;
		c.canDepositAll = false;
		requestUpdates();
	}

	public void movePlayer2(int x, int y, int h) {
		if(c.inWild() || c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You can't do this right now.");
			return;
		}
		c.homeTimer = 0;
		c.resetWalkingQueue();
		c.teleportToX = x;
        c.teleportToY = y;
		c.heightLevel = h;
		c.canDepositAll = false;
		requestUpdates();
	}
	
	/**
	* Following
	**/
	
	/*public void Player() {
		if(c.isInHighRiskPK() {
			return;
		}
		if(Server.playerHandler.players[c.followId] == null || Server.playerHandler.players[c.followId].isDead) {
			c.getPA().resetFollow();
			return;
		}		
		if(c.freezeTimer > 0) {
			return;
		}
		int otherX = Server.playerHandler.players[c.followId].getX();
		int otherY = Server.playerHandler.players[c.followId].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 6);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean sameSpot = (c.absX == otherX && c.absY == otherY);
		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			c.getPA().resetFollow();
			return;
		}
		c.faceUpdate(c.followId+32768);
		if ((c.usingBow || c.mageFollow || c.autocastId > 0 && (c.npcIndex > 0 || c.playerIndex > 0)) && bowDistance && !sameSpot) {
			c.stopMovement();
			return;
		}	
		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot && (c.npcIndex > 0 || c.playerIndex > 0)) {
			c.stopMovement();
			return;
		}	
		if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1) && !sameSpot) {
			return;
		}
		c.outStream.createFrame(174);
		boolean followPlayer = c.followId > 0;
		if (c.freezeTimer <= 0)
			if (followPlayer)
				c.outStream.writeWord(c.followId);
			else 
				c.outStream.writeWord(c.followId2);
		else
			c.outStream.writeWord(0);
		
		if (followPlayer)
			c.outStream.writeByte(1);
		else
			c.outStream.writeByte(0);
		if (c.usingBow && c.playerIndex > 0)
			c.followDistance = 5;
		else if (c.usingRangeWeapon && c.playerIndex > 0)
			c.followDistance = 3;
		else if (c.spellId > 0 && c.playerIndex > 0)
			c.followDistance = 5;
		else
			c.followDistance = 1;
		c.outStream.writeWord(c.followDistance);
	}*/
	public void writeChatLine(String data) {

	BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("./Data/lastChat.txt"));
			bw.write(c.playerName + ": "+ data);
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		finally
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}

	public void writePlayers(String data) {

	BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("./Data/playerCount.txt"));
			bw.write("There are currently " +data+ " players online.");
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		finally
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}

	public void writeDropLine(String data) {

	BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("./Data/lastDrop.txt"));
			bw.write(data);
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		finally
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}

	public void writeKillLine(String data) {

	BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("./Data/lastKill.txt"));
			bw.write(data);
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		finally
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}

	public void writeChatLog(String data)
	{
		checkDateAndTime();	
		String filePath = "./Data/ChatLogs/" + c.playerName + ".txt";
		BufferedWriter bw = null;
		
		try 
		{				
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "[" + c.connectedFrom + "]: " + "" + data + " ");
			bw.newLine();
			bw.flush();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		} 
		finally 
		{
			if (bw != null)
			{
				try 
				{
					bw.close();
				} 
				catch (IOException ioe2) 
				{
				}
			}
		}
	}
	
	public void sendBook(String title, String[] pages) {
		if (pages == null) {
			c.sendMessage("This book has no pages...");
			return;
		}
		c.startAnimation(1350);
		sendFrame126(title, 903);
		for(int i = 0; i < pages.length; i++)
			sendFrame126(pages[i], 843+i);
		sendFrame126("- " + (c.bookPage+1) + " -", 14165);
		sendFrame126("", 14166);
		showInterface(837);
		c.flushOutStream();
	}
	
	public void followPlayer() {
		if(Server.playerHandler.players[c.followId] == null || Server.playerHandler.players[c.followId].isDead) {
			c.followId = 0;
			return;
		}		
		if(c.freezeTimer > 0) {
			resetFollow();
			c.stopMovement();
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;
		
        int otherX = PlayerHandler.players[c.followId].getX();
        int otherY = PlayerHandler.players[c.followId].getY();
		boolean sameSpot = (c.absX == otherX && c.absY == otherY);

		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);

		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 4);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 6);
		boolean mageDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 7);

		boolean castingMagic = (c.usingMagic || c.mageFollow || c.autocasting || c.spellId > 0)
				&& mageDistance;
		boolean playerRanging = (c.usingRangeWeapon)
				&& rangeWeaponDistance;
		boolean playerBowOrCross = (c.usingBow) && bowDistance;
		boolean otherRangeWeapon = c.playerEquipment[c.playerWeapon] == 4212;

		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			resetFollow();
			return;
		}
		c.faceUpdate(c.followId + 32768);
		if (!sameSpot) {

			if(c.usingSpecial && c.goodDistance(otherX, otherY, c.absX, c.absY, 6) && c.playerEquipment[c.playerWeapon] == 13022) {
				c.stopMovement();
				return;
			}
			if(castingMagic) {
				c.stopMovement();
				return;
			}
        	if (!c.usingSpecial && c.playerIndex > 0/*c.inWild()*/) {
				/*if (c.usingSpecial && (playerRanging || playerBowOrCross)) {
					c.stopMovement();
					return;
				}*/
				if (playerRanging || playerBowOrCross || otherRangeWeapon) {
					c.stopMovement();
					return;
				}
				if (c.getCombat().usingHally() && hallyDistance) {
					c.stopMovement();
					return;
				}
			}
		}
                if (otherX == c.absX && otherY == c.absY) {
                        c.getPA().stepAway();
                } else if (c.isRunning2) {
                        if (otherY > c.getY() && otherX == c.getX()) {
                                playerWalk(otherX, otherY - 1);
                        } else if (otherY < c.getY() && otherX == c.getX()) {
                                playerWalk(otherX, otherY + 1);
                        } else if (otherX > c.getX() && otherY == c.getY()) {
                                playerWalk(otherX - 1, otherY);
                        } else if (otherX < c.getX() && otherY == c.getY()) {
                                playerWalk(otherX + 1, otherY);
                        } else if (otherX < c.getX() && otherY < c.getY()) {
                                playerWalk(otherX + 1, otherY + 1);
                        } else if (otherX > c.getX() && otherY > c.getY()) {
                                playerWalk(otherX - 1, otherY - 1);
                        } else if (otherX < c.getX() && otherY > c.getY()) {
                                playerWalk(otherX + 1, otherY - 1);
                        } else if (otherX > c.getX() && otherY < c.getY()) {
                                playerWalk(otherX + 1, otherY - 1);
                        }
                } else {
                        if (otherY > c.getY() && otherX == c.getX()) {
                                playerWalk(otherX, otherY - 1);
                        } else if (otherY < c.getY() && otherX == c.getX()) {
                                playerWalk(otherX, otherY + 1);
                        } else if (otherX > c.getX() && otherY == c.getY()) {
                                playerWalk(otherX - 1, otherY);
                        } else if (otherX < c.getX() && otherY == c.getY()) {
                                playerWalk(otherX + 1, otherY);
                        } else if (otherX < c.getX() && otherY < c.getY()) {
                                playerWalk(otherX + 1, otherY + 1);
                        } else if (otherX > c.getX() && otherY > c.getY()) {
                                playerWalk(otherX - 1, otherY - 1);
                        } else if (otherX < c.getX() && otherY > c.getY()) {
                                playerWalk(otherX + 1, otherY - 1);
                        } else if (otherX > c.getX() && otherY < c.getY()) {
                                playerWalk(otherX - 1, otherY + 1);
                        }
                }
                c.faceUpdate(c.followId+32768);
        }
	
	// Clipping
	public static boolean pathBlocked(Client attacker, Client victim) {
		
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
			path[next][2] = attacker.heightLevel;//getHeightLevel();
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;	
		}
		for (int i = 0; i < path.length; i++) {
			if (!Region./*getSingleton().*/getClipping(path[i][0], path[i][1], path[i][2], path[i][3], path[i][4])) {
				return true;	
			}
		}
		return false;
	}
	
	public void followNpc() {
		if(Server.npcHandler.npcs[c.followId2] == null || Server.npcHandler.npcs[c.followId2].isDead) {
			c.followId2 = 0;
			return;
		}		
		if(c.freezeTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;
		
		int otherX = Server.npcHandler.npcs[c.followId2].getX();
		int otherY = Server.npcHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 10);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 10);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		boolean mageDistance = c.goodDistance(otherX, otherY, c.getX(),c.getY(), 7);
		boolean castingMagic = false, playerRanging = false;
		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		/*if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}*/
		if((c.playerEquipment[c.playerWeapon] == 13022 || c.playerEquipment[c.playerWeapon] == 15041 || c.playerEquipment[c.playerWeapon] == 9185) && rangeWeaponDistance)
			playerRanging = true;
		if((c.playerEquipment[c.playerWeapon] == 22494 || c.playerEquipment[c.playerWeapon] == 19112 || c.playerEquipment[c.playerWeapon] == 2415 || c.playerEquipment[c.playerWeapon] == 2416 || c.playerEquipment[c.playerWeapon] == 2417) && mageDistance)
			castingMagic = true;
		
		if((c.usingBow || playerRanging) && bowDistance && !sameSpot) {
			return;
		}
		if((castingMagic || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0)) && mageDistance && !sameSpot) {
			return;
		}
		if(c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if(c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}
		
		c.faceUpdate(c.followId2);
		if (otherX == c.absX && otherY == c.absY) {
			c.getPA().stepAway();	
		} else if(c.isRunning2 && !withinDistance) {
			if(otherY > c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} 
		} else {
			if(otherY > c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1));
			} 
		}
		c.faceUpdate(c.followId2);
	}
	
	private static void walkClipped(Client c) {
		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			c.getPA().walkTo(-1, 0);
			return;
		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
			c.getPA().walkTo(1, 0);
			return;
		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
			c.getPA().walkTo(0, -1);
			return;
		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
			c.getPA().walkTo(0, 1);
			return;
		}
		c.getPA().walkTo(-1, 0);
	}
	
	/*public void followNpc() {
		if(Server.npcHandler.npcs[c.followId2] == null || Server.npcHandler.npcs[c.followId2].isDead) {
			c.followId2 = 0;
			return;
		}		
		if(c.freezeTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;
		
		int otherX = Server.npcHandler.npcs[c.followId2].getX();
		int otherY = Server.npcHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);

		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 4);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 6);
		boolean mageDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 7);

		boolean castingMagic = (c.usingMagic || c.mageFollow || c.autocasting || c.spellId > 0)
				&& mageDistance;
		boolean playerRanging = (c.usingRangeWeapon)
				&& rangeWeaponDistance;
		boolean playerBowOrCross = (c.usingBow) && bowDistance;
		
		boolean isBehindwall = false;
		if(Server.npcHandler.npcs[c.followId2].npcType != 3200) {
		if(otherX < c.getX())
		if(!Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0))
		isBehindwall = true;
		
		if(otherX > c.getX())
		if(!Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0))
		isBehindwall = true;
		
		if(otherY < c.getY())
		if(!Region.getClipping(c.getX(), c.getY()-1, c.heightLevel, 0, -1))
		isBehindwall = true;
		
		if(otherY > c.getY())
		if(!Region.getClipping(c.getX(), c.getY()+1, c.heightLevel, 0, 1))
		isBehindwall = true;
		}
		
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		/*if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}
		
		if((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}
		
		/*if(isBehindwall){
		c.stopMovement();
					return;
					}
		
		if (!sameSpot) {
			//if (!c.usingSpecial && (!playerRanging || !playerBowOrCross)) {
				if (c.usingSpecial && (playerRanging || playerBowOrCross)) {
					c.stopMovement();
					return;
				}
				if (castingMagic || playerRanging || playerBowOrCross) {
					c.stopMovement();
					return;
				}
				if (c.getCombat().usingHally() && hallyDistance) {
					c.stopMovement();
					return;
				}
			//}
		}
		
		c.faceUpdate(c.followId2);
		if (otherX == c.absX && otherY == c.absY) {
			c.getPA().stepAway();
			/*int r = Misc.random(3);
			switch (r) {
			case 0:
				playerWalk(c.absX, c.absY-1);
				break;
			case 1:
				playerWalk(c.absX, c.absY+1);
				break;
			case 2:
				playerWalk(c.absX+1, c.absY);
				break;
			case 3:
				playerWalk(c.absX-1, c.absY);
				break;
			}
		} else if (c.isRunning2 && !withinDistance) {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		c.faceUpdate(c.followId2);
	}*/
	
	public void stepAway() {
		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
		c.getPA().walkTo2(-1, 0);
	         } else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
		c.getPA().walkTo2(1, 0);
	        } else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
		c.getPA().walkTo2(0, -1);
	        } else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
		c.getPA().walkTo2(0, 1);
	        }
	}
	

	

	
	
	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else return j-i;
	}
	
	public void resetFollow() {
		c.followId = 0;
		c.followId2 = 0;
		c.mageFollow = false;
		c.outStream.createFrame(174);
		c.outStream.writeWord(0);
		c.outStream.writeByte(0);
		c.outStream.writeWord(1);
	}
	
	public void walkTo(int x, int y) {
        PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);
 }
	
	public void walkTo2(int i, int j) {
c.homeTimer = 0;
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
        if(++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;

        for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }

    public void walkTo3(int i, int j) {
    	int i2 = i - c.getX();
    	int j2 = j - c.getY();
		c.homeTimer = 0;
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
        if(++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
		int k = c.getX() + i2;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j2;
        l -= c.mapRegionY * 8;

        for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }
	
	public void stopDiagonal(int otherX, int otherY) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 1;
		int xMove = otherX - c.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - c.getY();
		/*if (!clipHor) {
			yMove = 0;
		} else if (!clipVer) {
			xMove = 0;	
		}*/
		
		int k = c.getX() + xMove;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + yMove;
        l -= c.mapRegionY * 8;
		
		for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
		
	}
	
		
	
	public void walkToCheck(int i, int j) {
c.homeTimer = 0;
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
        if(++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;
		
		for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
	}
	

	public int getMove(int place1,int place2) {
c.homeTimer = 0;
		if (System.currentTimeMillis() - c.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
            return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
        return 0;
   	}
	
	public boolean fullVeracs() {
		return c.playerEquipment[c.playerHat] == 4753 && c.playerEquipment[c.playerChest] == 4757 && c.playerEquipment[c.playerLegs] == 4759 && c.playerEquipment[c.playerWeapon] == 4755;
	}
	public boolean fullGuthans() {
		return c.playerEquipment[c.playerHat] == 4724 && c.playerEquipment[c.playerChest] == 4728 && c.playerEquipment[c.playerLegs] == 4730 && c.playerEquipment[c.playerWeapon] == 4726;
	}
	
	/**
	* reseting animation
	**/
	public void resetAnimation() {
		c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		c.startAnimation(c.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}
	
	public void levelUp(int skill) {
		int totalLevel = (getLevelForXP(c.playerXP[0]) + getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2]) + getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4]) + getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6]) + getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8]) + getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10]) + getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12]) + getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14]) + getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16]) + getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18]) + getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20]));
		sendFrame126("Total Lvl: "+totalLevel, 3984);
		switch(skill) {
			case 0:
			sendFrame126("Congratulations, you just advanced an attack level!", 6248);
			sendFrame126("Your attack level is now "+getLevelForXP(c.playerXP[skill])+".", 6249);
			c.sendMessage("Congratulations, you just advanced an attack level.");	
			sendFrame164(6247);
			if(c.gameMode == 2) {
				if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 "+c.skillName[skill]+" on a trained account!");
				}
			}
			break;
			
			case 1:
            sendFrame126("Congratulations, you just advanced a defence level!", 6254);
            sendFrame126("Your defence level is now "+getLevelForXP(c.playerXP[skill])+".", 6255);
            c.sendMessage("Congratulations, you just advanced a defence level.");
			sendFrame164(6253);
			if(c.gameMode == 2) {
				if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 "+c.skillName[skill]+" on a trained account!");
				}
			}
			break;
			
			case 2:
            sendFrame126("Congratulations, you just advanced a strength level!", 6207);
            sendFrame126("Your strength level is now "+getLevelForXP(c.playerXP[skill])+".", 6208);
            c.sendMessage("Congratulations, you just advanced a strength level.");
			sendFrame164(6206);
			if(c.gameMode == 2) {
				if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 "+c.skillName[skill]+" on a trained account!");
				}
			}
			break;
			
			case 3:
            sendFrame126("Congratulations, you just advanced a hitpoints level!", 6217);
            sendFrame126("Your hitpoints level is now "+getLevelForXP(c.playerXP[skill])+".", 6218);
            c.sendMessage("Congratulations, you just advanced a hitpoints level.");
			sendFrame164(6216);
			if(c.gameMode == 2) {
				if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 "+c.skillName[skill]+" on a trained account!");
				}
			}
			break;
			
			case 4:
            sendFrame126("Congratulations, you just advanced a ranged level!", 5453);
            sendFrame126("Your ranged level is now "+getLevelForXP(c.playerXP[skill])+".", 6114);
            c.sendMessage("Congratulations, you just advanced a ranging level.");
			sendFrame164(4443);
			if(c.gameMode == 2) {
				if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 "+c.skillName[skill]+" on a trained account!");
				}
			}
			break;
			
			case 5:
            sendFrame126("Congratulations, you just advanced a prayer level!", 6243);
            sendFrame126("Your prayer level is now "+getLevelForXP(c.playerXP[skill])+".", 6244);
            c.sendMessage("Congratulations, you just advanced a prayer level.");
			sendFrame164(6242);
			if(c.gameMode == 2) {
				if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 "+c.skillName[skill]+" on a trained account!");
				}
			}
			break;
			
			case 6:
            sendFrame126("Congratulations, you just advanced a magic level!", 6212);
            sendFrame126("Your magic level is now "+getLevelForXP(c.playerXP[skill])+".", 6213);
            c.sendMessage("Congratulations, you just advanced a magic level.");
			sendFrame164(6211);
			if(c.gameMode == 2) {
				if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 "+c.skillName[skill]+" on a trained account!");
				}
			}
			break;
			
			case 7:
            sendFrame126("Congratulations, you just advanced a cooking level!", 6227);
            sendFrame126("Your cooking level is now "+getLevelForXP(c.playerXP[skill])+".", 6228);
            c.sendMessage("Congratulations, you just advanced a cooking level.");
			sendFrame164(6226);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
			break;
			
			case 8:
			sendFrame126("Congratulations, you just advanced a woodcutting level!", 4273);
			sendFrame126("Your woodcutting level is now "+getLevelForXP(c.playerXP[skill])+".", 4274);
			c.sendMessage("Congratulations, you just advanced a woodcutting level.");
			sendFrame164(4272);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
			}
            break;
			
                                               case 9:
            sendFrame126("Congratulations, you just advanced a fletching level!", 6232);
            sendFrame126("Your fletching level is now "+getLevelForXP(c.playerXP[skill])+".", 6233);
            c.sendMessage("Congratulations, you just advanced a fletching level.");
			sendFrame164(6231);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
			
			case 10:
            sendFrame126("Congratulations, you just advanced a fishing level!", 6259);
            sendFrame126("Your fishing level is now "+getLevelForXP(c.playerXP[skill])+".", 6260);
            c.sendMessage("Congratulations, you just advanced a fishing level.");
			sendFrame164(6258);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
			break;
			
			case 11:
			sendFrame126("Congratulations, you just advanced a fire making level!", 4283);
			sendFrame126("Your firemaking level is now "+getLevelForXP(c.playerXP[skill])+".", 4284);
			c.sendMessage("Congratulations, you just advanced a fire making level.");
			sendFrame164(4282);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
			
                                                case 12:
			sendFrame126("Congratulations, you just advanced a crafting level!", 6264);
			sendFrame126("Your crafting level is now "+getLevelForXP(c.playerXP[skill])+".", 6265);
			c.sendMessage("Congratulations, you just advanced a crafting level.");
			sendFrame164(6263);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
			
			case 13:
			sendFrame126("Congratulations, you just advanced a smithing level!", 6222);
			sendFrame126("Your smithing level is now "+getLevelForXP(c.playerXP[skill])+".", 6223);
			c.sendMessage("Congratulations, you just advanced a smithing level.");
			sendFrame164(6221);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
			break;
			
			case 14:
			sendFrame126("Congratulations, you just advanced a mining level!", 4417);
			sendFrame126("Your mining level is now "+getLevelForXP(c.playerXP[skill])+".", 4438);
			c.sendMessage("Congratulations, you just advanced a mining level.");
			sendFrame164(4416);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
			
			case 15:
            sendFrame126("Congratulations, you just advanced a herblore level!", 6238);
            sendFrame126("Your herblore level is now "+getLevelForXP(c.playerXP[skill])+".", 6239);
            c.sendMessage("Congratulations, you just advanced a herblore level.");
			sendFrame164(6237);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
			
			case 16:
			sendFrame126("Congratulations, you just advanced a agility level!", 4278);
			sendFrame126("Your agility level is now "+getLevelForXP(c.playerXP[skill])+".", 4279);
			c.sendMessage("Congratulations, you just advanced an agility level.");
			sendFrame164(4277);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
			
			case 17:
			sendFrame126("Congratulations, you just advanced a thieving level!", 4263);
			sendFrame126("Your theiving level is now "+getLevelForXP(c.playerXP[skill])+".", 4264);
            c.sendMessage("Congratulations, you just advanced a thieving level.");
			sendFrame164(4261);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
			break;
			
			case 18:
			sendFrame126("Congratulations, you just advanced a slayer level!", 12123);
			sendFrame126("Your slayer level is now "+getLevelForXP(c.playerXP[skill])+".", 12124);
			c.sendMessage("Congratulations, you just advanced a slayer level.");
			sendFrame164(12122);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
            case 19:
			sendFrame126("Congratulations, you just advanced a farming level!", 12123);
			sendFrame126("Your farming level is now "+getLevelForXP(c.playerXP[skill])+".", 12124);
			c.sendMessage("Congratulations, you just advanced a farming level.");
			sendFrame164(12122);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;

            case 20:
			sendFrame126("Congratulations, you just advanced a runecrafting level!", 4268);
			sendFrame126("Your runecrafting level is now "+getLevelForXP(c.playerXP[skill])+".", 4269);
			c.sendMessage("Congratulations, you just advanced a runecrafting level.");
			sendFrame164(4267);
			if(getLevelForXP(c.playerXP[skill]) == 99) {
					c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+" @red@has just reached 99 @dre@"+c.skillName[skill]+"@red@!");
				}
            break;
		}
		c.dialogueAction = 0;
		c.nextChat = 0;
	}
	
	public void refreshSkill(int i) {
		switch (i) {
			case 0:
			sendFrame126("" + c.playerLevel[0] + "", 4004);
			sendFrame126("" + getLevelForXP(c.playerXP[0]) + "", 4005);
			sendFrame126("" + c.playerXP[0] + "", 4044);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[0]) + 1) + "", 4045);
			break;
			
			case 1:
			sendFrame126("" + c.playerLevel[1] + "", 4008);
			sendFrame126("" + getLevelForXP(c.playerXP[1]) + "", 4009);
			sendFrame126("" + c.playerXP[1] + "", 4056);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[1]) + 1) + "", 4057);
			break;
			
			case 2:
			sendFrame126("" + c.playerLevel[2] + "", 4006);
			sendFrame126("" + getLevelForXP(c.playerXP[2]) + "", 4007);
			sendFrame126("" + c.playerXP[2] + "", 4050);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[2]) + 1) + "", 4051);
			break;
			
			case 3:
			sendFrame126("" + c.playerLevel[3] + "", 4016);
			sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 4017);
			sendFrame126("" + c.playerXP[3] + "", 4080);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3])+1) + "", 4081);
			break;
			
			case 4:
			sendFrame126("" + c.playerLevel[4] + "", 4010);
			sendFrame126("" + getLevelForXP(c.playerXP[4]) + "", 4011);
			sendFrame126("" + c.playerXP[4] + "", 4062);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[4]) + 1) + "", 4063);
			break;
			
			case 5:
			sendFrame126("" + c.playerLevel[5] + "", 4012);
			sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
			sendFrame126("" + c.playerXP[5] + "", 4068);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1) + "", 4069);
			sendFrame126("" +c.playerLevel[5]+"/"+getLevelForXP(c.playerXP[5])+"", 687);//Prayer frame
			break;
			
			case 6:
			sendFrame126("" + c.playerLevel[6] + "", 4014);
			sendFrame126("" + getLevelForXP(c.playerXP[6]) + "", 4015);
			sendFrame126("" + c.playerXP[6] + "", 4074);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[6]) + 1) + "", 4075);
			break;
			
			case 7:
			sendFrame126("" + c.playerLevel[7] + "", 4034);
			sendFrame126("" + getLevelForXP(c.playerXP[7]) + "", 4035);
			sendFrame126("" + c.playerXP[7] + "", 4134);
			sendFrame126("" +getXPForLevel(getLevelForXP(c.playerXP[7]) + 1) + "", 4135);
			break;
			
			case 8:
			sendFrame126("" + c.playerLevel[8] + "", 4038);
			sendFrame126("" + getLevelForXP(c.playerXP[8]) + "", 4039);
			sendFrame126("" + c.playerXP[8] + "", 4146);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[8]) + 1) + "", 4147);
			break;
			
			case 9:
			sendFrame126("" + c.playerLevel[9] + "", 4026);
			sendFrame126("" + getLevelForXP(c.playerXP[9]) + "", 4027);
			sendFrame126("" + c.playerXP[9] + "", 4110);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[9]) + 1) + "", 4111);
			break;
			
			case 10:
			sendFrame126("" + c.playerLevel[10] + "", 4032);
			sendFrame126("" + getLevelForXP(c.playerXP[10]) + "", 4033);
			sendFrame126("" + c.playerXP[10] + "", 4128);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[10]) + 1) + "", 4129);
			break;
			
			case 11:
			sendFrame126("" + c.playerLevel[11] + "", 4036);
			sendFrame126("" + getLevelForXP(c.playerXP[11]) + "", 4037);
			sendFrame126("" + c.playerXP[11] + "", 4140);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[11]) + 1) + "", 4141);
			break;
			
			case 12:
			sendFrame126("" + c.playerLevel[12] + "", 4024);
			sendFrame126("" + getLevelForXP(c.playerXP[12]) + "", 4025);
			sendFrame126("" + c.playerXP[12] + "", 4104);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[12]) + 1) + "", 4105);
			break;
			
			case 13:
			sendFrame126("" + c.playerLevel[13] + "", 4030);
			sendFrame126("" + getLevelForXP(c.playerXP[13]) + "", 4031);
			sendFrame126("" + c.playerXP[13] + "", 4122);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[13]) + 1) + "", 4123);
			break;
			
			case 14:
			sendFrame126("" + c.playerLevel[14] + "", 4028);
			sendFrame126("" + getLevelForXP(c.playerXP[14]) + "", 4029);
			sendFrame126("" + c.playerXP[14] + "", 4116);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[14]) + 1)+ "", 4117);
			break;
			
			case 15:
			sendFrame126("" + c.playerLevel[15] + "", 4020);
			sendFrame126("" + getLevelForXP(c.playerXP[15]) + "", 4021);
			sendFrame126("" + c.playerXP[15] + "", 4092);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[15]) + 1) + "", 4093);
			break;
			
			case 16:
			sendFrame126("" + c.playerLevel[16] + "", 4018);
			sendFrame126("" + getLevelForXP(c.playerXP[16]) + "", 4019);
			sendFrame126("" + c.playerXP[16] + "", 4086);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[16]) + 1) + "", 4087);
			break;
			
			case 17:
			sendFrame126("" + c.playerLevel[17] + "", 4022);
			sendFrame126("" + getLevelForXP(c.playerXP[17]) + "", 4023);
			sendFrame126("" + c.playerXP[17] + "", 4098);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[17]) + 1) + "", 4099);
			break;
			
			case 18:
			sendFrame126("" + c.playerLevel[18] + "", 12166);
			sendFrame126("" + getLevelForXP(c.playerXP[18]) + "", 12167);
			sendFrame126("" + c.playerXP[18] + "", 12171);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[18]) + 1) + "", 12172);
			break;
			
			case 19:
			sendFrame126("" + c.playerLevel[19] + "", 13926);
			sendFrame126("" + getLevelForXP(c.playerXP[19]) + "", 13927);
			sendFrame126("" + c.playerXP[19] + "", 13921);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[19]) + 1) + "", 13922);
			break;
			
			case 20:
			sendFrame126("" + c.playerLevel[20] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[20]) + "", 4153);
			sendFrame126("" + c.playerXP[20] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[20]) + 1) + "", 4158);
			break;
		}
	}
	
	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor((double)lvl + 300.0 * Math.pow(2.0, (double)lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int)Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double) lvl + 300.0
					* Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}
	
	public boolean checkRetributionReqsSingle(int i) {
		if(Server.playerHandler.players[i] == null) 
			return false;
		
		if (i == c.playerId)
			return false;
			
		if (c.inPits && Server.playerHandler.players[i].inPits)
			return true;
			
		if(!Server.playerHandler.players[i].inWild()) 
			return false;
	
		if(Config.COMBAT_LEVEL_DIFFERENCE) {
			int combatDif1 = c.getCombat().getCombatDifference(c.combatLevel, Server.playerHandler.players[i].combatLevel);
			if(combatDif1 > c.wildLevel || combatDif1 > Server.playerHandler.players[i].wildLevel)
				return false;
		}
	
	if(Config.SINGLE_AND_MULTI_ZONES) {
		if(!Server.playerHandler.players[i].inMulti()) {	// single combat zones
			if(Server.playerHandler.players[i].underAttackBy != c.playerId  && Server.playerHandler.players[i].underAttackBy != 0) {
				return false;
			}
			if(Server.playerHandler.players[i].playerId != c.underAttackBy && c.underAttackBy != 0) {
				return false;
			}
		}
	}
	return true;
}

public boolean checkRetributionReqsMulti(int i) {

			
		if(!Server.playerHandler.players[i].inWild()) 
			return false;
	
		if(Config.COMBAT_LEVEL_DIFFERENCE) {
			int combatDif1 = c.getCombat().getCombatDifference(c.combatLevel, Server.playerHandler.players[i].combatLevel);
			if(combatDif1 > c.wildLevel || combatDif1 > Server.playerHandler.players[i].wildLevel)
			return false;
		}
	return true;
}

	
	public void appendRetribution(Client o) {
		if (!c.inMulti()){
			if (o != null) {
				//Client c2 = (Client)Server.playerHandler.players[o];
				//if (c2.isDead || c2.respawnTimer > 0)
				//	return;
				if (checkRetributionReqsSingle(c.playerId)) {
					if (!o.goodDistance(o.getX(), o.getY(), c.getX(), c.getY(), 1))
						return;
					int damage = (int)(c.playerLevel[5] * 2.50/10);
					if (o.playerLevel[3] - damage < 0) 
						damage = o.playerLevel[3];					
					c.gfx0(437);
					o.handleHitMask(damage);
					o.dealDamage(damage);
					o.damageTaken[c.killerId] += damage;
					o.getPA().refreshSkill(3);
					c.totalPlayerDamageDealt += damage;
				
				} 		
			}
		} else {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
					Client players = (Client)Server.playerHandler.players[i];
					if (checkRetributionReqsMulti(c.playerId)) {
						if (!players.goodDistance(players.getX(), players.getY(), c.getX(), c.getY(), 1))
							return;
						int damage = (int)(c.playerLevel[5] * 2.50/10);
						if (players.playerLevel[3] - damage < 0) 
							damage = players.playerLevel[3];					
						c.gfx0(437);
						players.handleHitMask(damage);
						players.dealDamage(damage);
						players.damageTaken[c.killerId] += damage;
						players.getPA().refreshSkill(3);
						c.totalPlayerDamageDealt += damage;
					}
				}
			}
		}
	}
	
	public boolean addSkillXP(int amount, int skill){
		if(c.xpLock == 1){
			return false;
		}
		if (amount+c.playerXP[skill] < 0 || c.playerXP[skill] > 200000000) {
			if(c.playerXP[skill] > 200000000) {
				c.playerXP[skill] = 200000000;
			}
			return false;
		}
		amount *= Config.SERVER_EXP_BONUS;
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		int oldXP = 0;
		double ringBonus = 1;
		double xpBonus = 1;
		if(c.playerEquipment[c.playerRing] == 6465)
			ringBonus = 1.2;
		if(c.playerEquipment[c.playerRing] == 4202)
			ringBonus = 1.1;
		if(c.bonusXP())
			xpBonus = 1.5;
		if(c.inWild())
			xpBonus *= 2;
			
			oldXP = c.playerXP[skill];
			c.playerXP[skill] += (int)((double)amount*ringBonus*xpBonus);
		if(oldXP < 50000000 && c.playerXP[skill] >= 50000000)
			c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+"@red@ has just reached @dre@50M @red@total experience in @dre@"+c.skillName[skill]+"@red@!");
		if(oldXP < 100000000 && c.playerXP[skill] >= 100000000)
			c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+"@red@ has just reached @dre@100M @red@total experience in @dre@"+c.skillName[skill]+"@red@!");
		if(oldXP < 150000000 && c.playerXP[skill] >= 150000000)
			c.sendAll("[@red@Server@bla@]: @dre@"+c.playerName+"@red@ has just reached @dre@150M @red@total experience in @dre@"+c.skillName[skill]+"@red@!");
		if(oldXP < 200000000 && c.playerXP[skill] >= 200000000)
			c.sendAll("[@red@Server@bla@]: @red@"+c.playerName+"@or2@ has just reached @red@200M @or2@total experience in @red@"+c.skillName[skill]+"@or2@!");
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill]) && skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			c.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}


	public void resetBarrows() {
		c.barrowsNpcs[0][1] = 0;
		c.barrowsNpcs[1][1] = 0;
		c.barrowsNpcs[2][1] = 0;
		c.barrowsNpcs[3][1] = 0;
		c.barrowsNpcs[4][1] = 0;
		c.barrowsNpcs[5][1] = 0;
		c.barrowsKillCount = 0;
		c.randomCoffin = Misc.random(3) + 1;
	}
	
	public static int Barrows[] = {4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759};
	public static int Runes[] = {4740,558,560,565};
	public static int Pots[] = {};
	
	public int randomBarrows() {
		return Barrows[(int)(Math.random()*Barrows.length)];
	}

	public int randomRunes() {
		return Runes[(int) (Math.random()*Runes.length)];
	}
	
	public int randomPots() {
		return Pots[(int) (Math.random()*Pots.length)];
	}
	/**
	 * Show an arrow icon on the selected player.
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public void drawHeadicon(int i, int j, int k, int l) {
		//synchronized(c) {
			c.outStream.createFrame(254);
			c.outStream.writeByte(i);
	
			if (i == 1 || i == 10) {
				c.outStream.writeWord(j);
				c.outStream.writeWord(k);
				c.outStream.writeByte(l);
			} else {
				c.outStream.writeWord(k);
				c.outStream.writeWord(l);
				c.outStream.writeByte(j);
			}
		//}
	}
	
	public int getNpcId(int id) {
		for(int i = 0; i < NPCHandler.maxNPCs; i++) {
			if(NPCHandler.npcs[i] != null) {
				if(Server.npcHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}
	
	private void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}
	
	public void removeObjects() {
		objectToRemove(2638, 4688);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
		objectToRemove2(3234, 9324);
		objectToRemove2(3233, 9324);
	}
	
	
	public void handleGlory(int gloryId) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Mage Bank");
		c.usingGlory = true;
		c.dialogueAction = -1;
	}
	
	public void resetVariables() {
		c.getFishing().resetFishing();
		c.getCrafting().resetCrafting();
		c.usingGlory = false;
		c.smeltInterface = false;
		c.smeltType = 0;
		c.smeltAmount = 0;
		c.woodcut[0] = c.woodcut[1] = c.woodcut[2] = 0;
		c.mining[0] = c.mining[1] = c.mining[2] = 0;
	}
	
	public boolean inPitsWait() {
		return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175 && c.getY() >= 5169;
	}
	
	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}
	
	public void removeFromCW() {
		if (c.castleWarsTeam == 1) {
			if (c.inCwWait) {
				Server.castleWars.saradominWait.remove(Server.castleWars.saradominWait.indexOf(c.playerId));
			} else {
				Server.castleWars.saradomin.remove(Server.castleWars.saradomin.indexOf(c.playerId));
			}
		} else if (c.castleWarsTeam == 2) {
			if (c.inCwWait) {
				Server.castleWars.zamorakWait.remove(Server.castleWars.zamorakWait.indexOf(c.playerId));
			} else {
				Server.castleWars.zamorak.remove(Server.castleWars.zamorak.indexOf(c.playerId));
			}		
		}
	}
	
	public int antiFire() {
		int toReturn = 0;
		if (c.antiFirePot)
			toReturn++;
		if (c.playerEquipment[c.playerShield] == 1540 || c.playerEquipment[c.playerShield] == 11284)
			toReturn++;
		if (c.prayerActive[12])
			toReturn++;
		if(c.playerEquipment[c.playerShield] == 6237 || c.playerEquipment[c.playerShield] == 6239 || c.playerEquipment[c.playerShield] == 6241 || c.playerEquipment[c.playerShield] == 6243 || c.playerEquipment[c.playerShield] == 6245 || c.playerEquipment[c.playerShield] == 6247 || c.playerEquipment[c.playerShield] == 6249 || c.playerEquipment[c.playerShield] == 6251 || c.playerEquipment[c.playerShield] == 6253 || c.playerEquipment[c.playerShield] == 6255 || c.playerEquipment[c.playerShield] == 6257)
		{
		  toReturn = 3;
		}
		return toReturn;	
	}
	
	public boolean checkForFlags() {
		int[][] itemsToCheck = {{995,100000000},{35,5},{667,5},{2402,5},{746,5},{4151,150},{565,100000},{560,100000},{555,300000},{11235,10}};
		for (int j = 0; j < itemsToCheck.length; j++) {
			if (itemsToCheck[j][1] < c.getItems().getTotalCount(itemsToCheck[j][0]))
				return true;		
		}
		return false;
	}
	
	public void addStarter() {
		//c.getItems().addItem(995,100000);
		c.getItems().addItem(1731,1);
		c.getItems().addItem(554,200);
		c.getItems().addItem(555,3000);
		c.getItems().addItem(556,200);
		c.getItems().addItem(557,1000);
		c.getItems().addItem(558,600);
		c.getItems().addItem(560,2000);
		c.getItems().addItem(565,1000);
		c.getItems().addItem(9075,400);
		c.getItems().addItem(1381,1);
		c.getItems().addItem(1323,1);
		c.getItems().addItem(841,1);
		c.getItems().addItem(882,500);
		c.getItems().addItem(392,1000);
		c.getDH().sendDialogues(33, 945);
		c.getPA().addSkillXP((15000000), 3);
		c.playerXP[3] = c.getPA().getXPForLevel(99)+5;
		c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
		c.getPA().refreshSkill(3);	
	}
	
	public int getWearingAmount() {
		int count = 0;
		for (int j = 0; j < c.playerEquipment.length; j++) {
			if (c.playerEquipment[j] > 0)
				count++;		
		}
		return count;
	}
	
	public void useOperate(int itemId) {
		switch (itemId) {
			case 1712:
			case 1710:
			case 1708:
			case 1706:
			case 1704:
			handleGlory(itemId);
			break;
			
			case 3758:
			c.getCombat().handleFremmyShield();
			break;
			
			case 11284:
				c.getCombat().handleDfs();
			break;
			case 6215: //green
		      case 6217:
		      case 6219:
		      case 6221:
		      case 6223:
		      case 6225:
		      case 6227:
		      case 6229:
		      case 6231:
		      case 6233:
		       c.itemUsing = itemId;
		       c.getCombat().handleGreenBroodooShield();
		       break;
			case 6237:
			case 6239:
			case 6241:
			case 6243:
			case 6245:
			case 6247:
			case 6249:
			case 6251:
			case 6253:
			case 6255:
				c.itemUsing = itemId;
				c.getCombat().handleOrangeBroodooShield();
				break;
			case 6259:
			case 6261:
			case 6263:
			case 6265:
			case 6267:
			case 6269:
			case 6271:
			case 6273:
			case 6275:
			case 6277:
				c.itemUsing = itemId;
				c.getCombat().handleWhiteBroodooShield();
				break;
		}
	}
	
	public void getSpeared(int otherX, int otherY) {
		int x = c.absX - otherX;
		int y = c.absY - otherY;
		if (x > 0) {
		if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
			x = 1;
		}
		}
		else if (x < 0) {
		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			x = -1;
		}
		}
		if (y > 0) {
		if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
			y = 1;
		}
		}
		else if (y < 0) {
		if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
			y = -1;
		}
		}
		moveCheck(x,y);
		c.lastSpear = System.currentTimeMillis();
	}
	
	public void moveCheck(int xMove, int yMove) {	
	if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1))
		movePlayer(c.absX + xMove, c.absY + yMove, c.heightLevel);
	}
	
	public int findKiller() {
		int killer = c.playerId;
		int damage = 0;
		for (int j = 0; j < Config.MAX_PLAYERS; j++) {
			if (PlayerHandler.players[j] == null)
				continue;
			if (j == c.playerId)
				continue;
			if (c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40) 
				|| c.goodDistance(c.absX, c.absY + 9400, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40)
				|| c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY + 9400, 40))
				if (c.damageTaken[j] > damage) {
					damage = c.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}
	
	public void resetTzhaar() {
		c.waveId = -1;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;	
		c.getPA().movePlayer(2438,5168,0);
	}
	
	public void enterCaves() {
		c.getPA().movePlayer(2413,5117,0);
	}
	
	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage("You have been poisoned.");
			c.poisonDamage = damage;
		}	
		
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (System.currentTimeMillis() - c.lastPoison > 20000 && c.poisonDamage > 0) {
					int damage = c.poisonDamage/2;
					if (damage > 0) {
						if (!c.getHitUpdateRequired()) {
							c.setHitUpdateRequired(true);
							c.setHitDiff(damage);
							c.updateRequired = true;

							c.poisonMask = 1;
						} else if (!c.getHitUpdateRequired2()) {
							c.setHitUpdateRequired2(true);
							c.setHitDiff2(damage);
							c.updateRequired = true;
							c.poisonMask = 2;
						}
						c.lastPoison = System.currentTimeMillis();
						c.poisonDamage--;
						c.dealDamage(damage);
						refreshSkill(3);
					} else {
						c.poisonDamage = -1;
						c.sendMessage("You are no longer poisoned.");
					}	
				}
			}
			
			@Override
			public void stop() {
				
			}
		}, 1);
	}

	public void appendVenom(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage("You have been poisoned.");
			c.venomDamage = damage;
		}	
		
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (System.currentTimeMillis() - c.lastVenom > 20000 && c.venomDamage > 0) {
					int damage = c.venomDamage;
					if (damage > 0) {
						if (!c.getHitUpdateRequired()) {
							c.setHitUpdateRequired(true);
							c.setHitDiff(damage);
							c.updateRequired = true;

							c.venomMask = 1;
						} else if (!c.getHitUpdateRequired2()) {
							c.setHitUpdateRequired2(true);
							c.setHitDiff2(damage);
							c.updateRequired = true;
							c.venomMask = 2;
						}
						c.lastVenom = System.currentTimeMillis();
						if(c.venomDamage <= 18)
							c.venomDamage += 2;
						c.dealDamage(damage);
						refreshSkill(3);
					} else {
						c.venomDamage = -1;
						c.sendMessage("You are no longer poisoned.");
					}	
				}
			}
			
			@Override
			public void stop() {
				
			}
		}, 1);
	}
	
	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}	
		}
		return false;	
	}
	
	public void checkPouch(int i) {
		if (i < 0)
			return;
		c.sendMessage("This pouch has " + c.pouches[i] + " rune ess in it.");		
	}
	
	public void fillPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > c.getItems().getItemAmount(1436)) {
			toAdd = c.getItems().getItemAmount(1436);
		}
		if (toAdd > c.POUCH_SIZE[i] - c.pouches[i])
			toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > 0) {
			c.getItems().deleteItem(1436, toAdd);
			c.pouches[i] += toAdd;
		}		
	}
	
	public void emptyPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.pouches[i];
		if (toAdd > c.getItems().freeSlots()) {
			toAdd = c.getItems().freeSlots();
		}
		if (toAdd > 0) {
			c.getItems().addItem(1436, toAdd);
			c.pouches[i] -= toAdd;
		}		
	}
	
	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = c.getItems().getItemAmount(995);
		for (int j = 0; j < c.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
				if (c.playerItems[j]-1 == c.getItems().brokenBarrows[i][1]) {					
					if (totalCost + 50000 > cashAmount) {
						breakOut = true;
						c.sendMessage("You need 50 000 coins to repair a piece of Barrows' armour!");
						break;
					} else {
						totalCost += 50000;
					}
					c.playerItems[j] = c.getItems().brokenBarrows[i][0]+1;
					c.sendMessage("You repair your "+c.getItems().getItemName(c.getItems().brokenBarrows[i][0]+1)+".");
				}		
			}
			if (breakOut)		
				break;
		}
		if (totalCost > 0)
			c.getItems().deleteItem(995, c.getItems().getItemSlot(995), totalCost);		
	}
	
	public void handleLoginText() {
		c.getPA().sendFrame126("When you are ready to leave", 2450);
		c.getPA().sendFrame126("ForeverPkers, use the", 2451);
		c.getPA().sendFrame126("button below to logout safely.", 2452);
		c.getPA().sendFrame126("The Bank of ForeverPkers - Deposit Box", 7421);
		c.getPA().sendFrame126("The Bank of ForeverPkers", 5383);
		c.getPA().sendFrame126("Level 15 : Bananas to Bones", 1259);
		c.getPA().sendFrame126("Various Teleports", 1195);
		/*c.getPA().sendFrame126("Monster Teleport", 13037);
		c.getPA().sendFrame126("Minigame Teleport", 13047);
		c.getPA().sendFrame126("Boss Teleport", 13055);
		c.getPA().sendFrame126("Pking Teleport", 13063);
		c.getPA().sendFrame126("Skill Teleport", 13071);
		c.getPA().sendFrame126("Monster Teleport", 1300);
		c.getPA().sendFrame126("Minigame Teleport", 1325);
		c.getPA().sendFrame126("Boss Teleport", 1350);
		c.getPA().sendFrame126("Pking Teleport", 1382);
		c.getPA().sendFrame126("Skill Teleport", 1415);
		c.getPA().sendFrame126("City Teleport", 1454);	
		c.getPA().sendFrame126("Coming Soon (2)", 7457);
		c.getPA().sendFrame126("Coming Soon (3)", 13097);
		c.getPA().sendFrame126("Coming Soon (4)", 13089);
		c.getPA().sendFrame126("City Teleport", 13081);*/
	
	}

	public void homeTeleport() {
		if(c.inTrade) {
			Client o = (Client) Server.playerHandler.players[c.tradeWith];
			if(o != null) {
				o.getTradeAndDuel().declineTrade();
			}
		}
		if(c.homeTeleMinutes > 0) {
			c.sendMessage("You must wait another "+c.homeTeleMinutes+" minute"+(c.homeTeleMinutes == 1 ? "" : "s")+" to cast the spell.");
			return;
		}
		//resetSkills();
		c.stopMovement();
		if(!c.isDead && c.homeTimer == 0) {
			removeAllWindows();	
			c.usingSpecial = false;	
			//c.startAnimation(4850);
			c.teleX = Config.LUMBY_X;
			c.teleY = Config.LUMBY_Y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = 0;
			c.homeTimer = 13;
		}
	}

	public void handleWeaponStyle() {
		if (c.fightMode == 0) {
			c.getPA().sendFrame36(43, c.fightMode);
		} else if (c.fightMode == 1) {
			c.getPA().sendFrame36(43, 3);
		} else if (c.fightMode == 2) {
			c.getPA().sendFrame36(43, 1);
		} else if (c.fightMode == 3) {
			c.getPA().sendFrame36(43, 2);
		}
	}
	
	
	
}
