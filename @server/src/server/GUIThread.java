package server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import server.util.ScriptManager;
import server.event.EventManager;
import server.model.npcs.NPCHandler;
import server.model.npcs.NPCDrops;
import server.model.players.PlayerHandler;
import server.model.players.Player;
import server.model.players.Client;
import server.model.players.PlayerSave;
import server.model.minigames.*;
import server.model.npcs.*;
import server.net.ConnectionHandler;
import server.net.ConnectionThrottleFilter;
import server.util.ShutDownHook;
import server.util.SimpleTimer;
import server.util.log.Logger;
import server.world.ItemHandler;
import server.world.ObjectHandler;
import server.world.ObjectManager;
import server.world.ShopHandler;
import server.world.map.VirtualWorld;
import server.world.ClanChatHandler;
import server.world.WorldMap;

public class GUIThread implements Runnable{

	public static ServerGUI sGUI = new ServerGUI();
	public static int currentMessage;
	public static String messageFromPlayer[] =  {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
												 null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
	public boolean newSuggestion = false;
	
	public void run(){
		
		sGUI.handleGUI();
	
	}


}