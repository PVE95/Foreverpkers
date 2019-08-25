package server.model.players.packets;

import server.Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import server.model.players.*;
import server.Config;
import server.util.Misc;
import server.Connection;
import server.model.players.Client;
import server.model.players.PlayerHandler;

public class ConsoleInputHandler implements Runnable {
	private Client c;
	
	public void run(){
		BufferedReader inStream = new BufferedReader(new InputStreamReader(System.in));
		Scanner scan = new Scanner(System.in);
		
		while(true){
			try {
				
				String playerCommand = scan.nextLine();


				if(playerCommand.startsWith("restart")) {
					System.out.println("Restarting Server!");
					for(Player players : PlayerHandler.players) {
						if(players == null)
						continue;	
						PlayerSave.saveGame((Client)players);
					}
					System.exit(0);
				}
				
				if (playerCommand.startsWith("ban")) {
					try {	
						String playerToBan = playerCommand.substring(4);
						Connection.addNameToBanList(playerToBan);
						Connection.addNameToFile(playerToBan);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
									Server.playerHandler.players[i].disconnected = true;
									Client c2 = (Client)Server.playerHandler.players[i];
									c2.sendMessage(" " +c2.playerName+ " Got Banned By CONSOLE.");
									System.out.println("You banned " + c2.playerName + " from the server.");
								} 
							}
						}
					} catch(Exception e) {
						System.out.println("Player must be offline.");
					}
				}
				
				if(playerCommand.startsWith("update")) {
					String[] args = playerCommand.split(" ");
					int a = Integer.parseInt(args[1]);
					PlayerHandler.updateSeconds = a;
					PlayerHandler.updateAnnounced = false;
					PlayerHandler.updateRunning = true;
					PlayerHandler.updateStartTime = System.currentTimeMillis();
					
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("Server update started by [@red@CONSOLE@bla@]");
						}
					}
					
				}
				
				
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}