package server.model.minigames;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.util.Misc;

/*
 * 
 * @author Pez
 *  desc: basically a new Jad for newer rewards.
 *  
 */

public class ElvenForest 
{
	
	private Client c;
	
	public boolean isPlaying = false;
	public int missionId = -1;

	private final int[][] WAVES = 
		{{71}, {71, 71}, {71, 1184}, {71, 71, 1184},					   
		{1184, 1184}, {1183}, {1183, 71}, {1183, 71, 71},
		{1183, 1184}, {1183, 1184, 71}, {1183, 1184, 71, 71},
		{1183, 1184, 1184}, {1183, 1183}, {2359}, {2359, 71},
		{2359, 71, 71}, {2359, 1184}, {2359, 1184, 71},
		{2359, 1184, 71, 71}, {2359, 1184, 1183},
		{2359, 1184, 1183, 71}, {2359, 1184, 1183, 71, 71, 71},
		{2359, 1184, 1183, 71, 71, 71, 71, 71}, {2359, 2361},
		{2359, 2361, 71}, {2359, 2361, 71, 71, 71},
		{2359, 2361, 1184, 1183}, {2359, 2361, 1184, 1183, 71, 71},
		{2359, 2361, 1184, 1184, 1183}, {2359, 2361, 1184, 1184, 1183, 71},
		{2359, 2361, 1184, 1184, 1183, 1183, 71}}; //NEEDS BOSS STILL.
	
	private final static int [][] COORDINATES = {{2240, 3271}, {2244, 3270}, {2251, 3266}, {2242, 3262}};
	
	public static int[][][] validPlayerCoords = 
		{
			{
				{2270, 3275}, {2220, 3250}, {2242, 3267}
			}
		};
	
	public ElvenForest(Client c)
	{
		this.c = c;
	}
	
	public void reset()
	{
		c.elveWaveId = 0;
	}
	
	public void initialize()
	{
		reset();
	}
	
	public boolean validCoords()
	{
		if(missionId == -1)
		{
			return false;
		}
		int x1 = validPlayerCoords[missionId][0][0], x2 = validPlayerCoords[missionId][1][0];
		int y1 = validPlayerCoords[missionId][0][1], y2 = validPlayerCoords[missionId][1][1];
		
		if((c.absX <= x1) && (c.absX >= x2) && c.absY <= y1 && (c.absY >= y2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean inMissionRegion()
	{
		for(int i = 0; i < validPlayerCoords.length; i++)
		{
			int x1 = validPlayerCoords[missionId][0][0], x2 = validPlayerCoords[missionId][1][0];
			int y1 = validPlayerCoords[missionId][0][1], y2 = validPlayerCoords[missionId][1][1];
			if((c.absX <= x1) && (c.absX >= x2) && c.absY <= y1 && (c.absY >= y2))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean inMission()
	{
		return (!isPlaying || missionId == -1 || !validCoords()) ? false : true;
	}
	
	public void startup(int startWave)
	{
		setHeightLevel();
	}
	
	private void setHeightLevel()
	{
		int potentialValue = c.playerId * 100;
		for(int i = 0; i < Config.MAX_PLAYERS; i++){
			if(Server.playerHandler.players[i] != null){
				Client otherPlayer = (Client)Server.playerHandler.players[i];
				if(otherPlayer.instancedHeightLevel == potentialValue || otherPlayer.heightLevel == potentialValue){
					potentialValue+=100;
				}
			}
		}
		
		c.instancedHeightLevel = potentialValue;
	}
	
	public void spawnWave()
	{
		if(c != null)
		{
			if(c.elveWaveId >= WAVES.length)
			{
				if(c.waveId >= WAVES.length)
				{
					c.elveWaveId = 0;
					return;
				}
				if(c.elveWaveId < 0)
				{
					return;
				}
				int elves = WAVES[c.elveWaveId].length;
				
				for(int i = 0; i < elves; i++)
				{
					int npc = WAVES[c.elveWaveId][i];
					int X = COORDINATES[i][0];
					int Y = COORDINATES[i][1];
					int H = c.instancedHeightLevel;
					//int hp = getHp(npc);
					//int max = getMax(npc);
					//int attack = getAttack(npc);
					//int defence = getDefence(npc);
					//Server.npcHandler.spawnNpc(c, npc, X, Y, H, 0, hp, max, attack, defence, false, true);
				}
			}
		}
	}
	
	/*public static int getHp(int npc) {
		switch (npc) {
			case 71:
			return 80;
			case 1184:
			return 200;
			case 1183:
			return 175;
			case 2359:
			return 350;
			case 2361: 
			return 325;	
		}
		return 100;
	}*/
	
}
