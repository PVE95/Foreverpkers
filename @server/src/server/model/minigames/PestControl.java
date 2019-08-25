package server.model.minigames;

import server.model.npcs.NPCHandler;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import server.Server;
import java.util.HashMap;
import java.util.Iterator;
import server.util.Misc;
import server.model.players.ActionHandler;
/**
 * @author Harlan
 * Credits to Sanity
 */

public class PestControl {

	/**
	 * how long the game is going for
	 */
	private final int GAME_TIMER = 200;
	/**
	 * how long before were put into the game from lobby
	 */
	private final int WAIT_TIMER = 60;
	/**
	 * How many players we need to start a game
	 */
	private final int PLAYERS_REQUIRED = 1;
	/**
	 * How many points a player should receive each game
	 */
	private final int POINT_REWARD = 8;
	/**
	 * How much health should the knight have
	 */
	private final int KNIGHTS_HEALTH = 200;
	/**
	 * Hashmap for the players in lobby
	 */
	private static HashMap<Client, Integer> waitingBoat = new HashMap<Client, Integer>();
	/**
	 * hashmap for the players in game
	 */
	private static HashMap<Client, Integer> gamePlayers = new HashMap<Client, Integer>();

	private int gameTimer = -1;
	private int waitTimer = 60;
	private boolean gameStarted = false;

	/**
	 * Array used for storing the portals health
	 */
	public static int[] portalHealth = {
		200,
		200,
		200,
		200
	};
	/**
	 * array used for storing the npcs used in the minigame
	 * @order npcId, xSpawn, ySpawn, health
	 */
	private int[][] pcNPCData = {
			{6142,2628,2591}, //portal
			{6143,2680,2588}, //portal
			{6144, 2669,2570}, //portal
			{6145, 2645,2569}, //portal
			{3782,2656,2592} //knight
	};
	/**
	 * Process of the minigame handles game start / end
	 */
	public void process() {
		try {
			setBoatInterface();
			/**
			 * handling the wait time in lobby, if timer is done then attempt to start game
			 */
			if (waitTimer > 0)
				waitTimer--;
			else if (waitTimer == 0)
				startGame();
			if (gameStarted && playersInGame() < 1)
				endGame(false);
			/**
			 * if the game has started handle in game aspects
			 */
			if (gameTimer > 0 && gameStarted) {
				gameTimer--;
				setGameInterface();
				if (allPortalsDead())
					endGame(true);
			} else if (gameTimer <= 0 && gameStarted)
				endGame(false);
		} catch (RuntimeException e) {
			System.out.println("Failed to set process");
			e.printStackTrace();
		}
	}
	/**
	 * Method we use for removing a player from the pc game
	 * @param player
	 */
	public static void removePlayerGame(Client player) {
		if (gamePlayers.containsKey(player)) {
			player.getPA().movePlayer(2657, 2639, 0);
			gamePlayers.remove(player);
		}
	}

	/**
	 * Setting the interfaces for the waiting lobby
	 */
	private void setBoatInterface() {
		try {
			Iterator<Client> iterator = waitingBoat.keySet().iterator();
			while (iterator.hasNext()) {
				Client c = iterator.next();
				if (c != null) {
					try {
						if(gameStarted)
							c.getPA().sendFrame126("Next Departure: "+(waitTimer+gameTimer)+"", 21120);
						else
							c.getPA().sendFrame126("Next Departure: "+waitTimer+"", 21120);
						c.getPA().sendFrame126("Players Ready: "+playersInBoat()+"", 21121);
						c.getPA().sendFrame126("(Need "+PLAYERS_REQUIRED+" to 25 players)", 21122);
						c.getPA().sendFrame126("Points: "+c.pcPoints+"", 21123);

					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (RuntimeException e) {
			System.out.println("Failed to set interfaces");
			e.printStackTrace();
		}
	}


	/**
	 * Setting the interface for in game players
	 */
	private void setGameInterface() {
		Iterator<Client> iterator = gamePlayers.keySet().iterator();
		while (iterator.hasNext()) {
			Client player = iterator.next();
			if (player != null) {
				for (int i = 0; i < portalHealth.length; i++) {
					if (portalHealth[i] > 0) {
						player.getPA().sendFrame126("" +portalHealth[i]+ "", 21111+i);
					} else
						player.getPA().sendFrame126("Dead", 21111+i);

				}
				player.getPA().sendFrame126(""+KNIGHTS_HEALTH, 21115);
				player.getPA().sendFrame126(""+player.pcDamage, 21116);
				player.getPA().sendFrame126("Time remaining: "+gameTimer+"", 21117);

			}
		}
	}
	/***
	 * Moving players to arena if there's enough players
	 */
	private void startGame() {
		//if we dont have
		if (playersInBoat() < PLAYERS_REQUIRED) {
			waitTimer = WAIT_TIMER;
			return;
		}
		for (int i = 0; i < portalHealth.length; i++)
			portalHealth[i] = 200;
		gameTimer = GAME_TIMER;
		waitTimer = -1;
		gameStarted = true;
		Iterator<Client> iterator = waitingBoat.keySet().iterator();
		while (iterator.hasNext()) {
			Client player = iterator.next();
			if (player == null) {
				continue;
			}

			player.getPA().movePlayer(2656+Misc.random3(3),2614-Misc.random3(4),0);
			gamePlayers.put(player, 1);
			player.sendMessage("@red@The Pest Control Game has begun!");
		}
		waitingBoat.clear();
	}

	/**
	 * Checks how many players are in the waiting lobby
	 * @return players waiting
	 */
	private int playersInBoat() {
		int players = 0;
		Iterator<Client> iterator = waitingBoat.keySet().iterator();
		while (iterator.hasNext()) {
			Client player = iterator.next();
			if (player != null) {
				players++;
			}
		}
		return players;
	}
	/**
	 * Checks how many players are in the game
	 * @return players in the game
	 */
	private int playersInGame() {
		int players = 0;
		Iterator<Client> inGamePlayers = gamePlayers.keySet().iterator();
		while (inGamePlayers.hasNext()) {
			Client player = inGamePlayers.next();
			if (player != null) {
				players++;
			}
		}
		return players;
	}
	/**
	 * Ends the game
	 * @param won
	 */
	private void endGame(boolean won) {
		cleanUp();
		Iterator<Client> players = gamePlayers.keySet().iterator();
		while (players.hasNext()) {
			Client player = players.next();
			if (player == null) {
				continue;
			}
			cleanUpPlayer(player);
			player.getPA().movePlayer(2657, 2639, 0);
			if (won && player.pcDamage > 50) {
				player.getDH().sendDialogues(79, 3790);
				player.sendMessage("You have won the pest control game and have been awarded "+POINT_REWARD+" Pest Control points.");
				player.pcPoints += POINT_REWARD;
				player.getItems().addItem(995, player.combatLevel * 1000);
			} else if (won) {
				player.getDH().sendDialogues(77, 3790);
				player.sendMessage("The void knights notice your lack of zeal.");
			} else {
				player.getDH().sendDialogues(78, 3790);
				player.sendMessage("You failed to kill all the portals in 3 minutes and have not been awarded any points.");
			}

		}
	}

	/**
	 * Resets the game variables and map
	 */
	private void cleanUp() {
		gameTimer = -1;
		waitTimer = WAIT_TIMER;
		gameStarted = false;
		gamePlayers.clear();
		/*
		 * Removes the npcs from the game if any left over for whatever reason
		 */
		for (int i = 0; i < pcNPCData.length; i++){
			for (int j = 0; j < NPCHandler.npcs.length; j++) {
				if (NPCHandler.npcs[j] != null) {
					if (NPCHandler.npcs[j].npcType == pcNPCData[i][0])
						NPCHandler.npcs[j] = null;
				}
			}
		}
	}
	/**
	 * Cleans the player of any damage, loss they may of received
	 */
	private void cleanUpPlayer(Client player){
		player.poisonDamage = 0;
		player.getCombat().resetPrayers();
		for (int i = 0; i < 24; i++) {
			player.playerLevel[i] = player.getPA().getLevelForXP(player.playerXP[i]);
			player.getPA().refreshSkill(i);
		}
		player.specAmount = 10;
		player.pcDamage = 0;
		player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
	}
	/**
	 * Checks if the portals are dead
	 * @return players dead
	 */
	private boolean allPortalsDead() {
		int count = 0;
		for (int i = 0; i < portalHealth.length; i++) {
			if (portalHealth[i] <= 0)
				count++;
		}
		return count >= 4;
	}

	/**
	 * Moves a player out of the waiting boat
	 * @param c
	 */
	public static void leaveWaitingBoat(Client c) {
		if (waitingBoat.containsKey(c)) {
			waitingBoat.remove(c);
			c.getPA().movePlayer(2657,2639,0);
		}
	}

	/**
	 * Moves a player into the hash and into the lobby
	 * @param player
	 */
	public static void addToWaitRoom(Client player) {
		if (player != null) {
			waitingBoat.put(player, 1);
			player.sendMessage("You have joined the Pest Control boat.");
			player.getPA().movePlayer(2661,2639,0);
		}
	}

	/**
	 * Checks if a player is in the game
	 * @param player
	 * @return
	 */
	public static boolean isInGame(Client player) {
		return gamePlayers.containsKey(player);
	}
	/**
	 * Checks if a player is in the pc boat (lobby)
	 * @param player
	 * @return
	 */
	public static boolean isInPcBoat(Client player) {
		return waitingBoat.containsKey(player);
	}

	public static boolean npcIsPortal(int type) {
		for (int i = 6142; i < 6146; i++) {
			if (type == i)
				return true;
		}
		return false;
	}
	}