//@author Ryan (Rune-server: DigitalKilbas)

package server.model.minigames;
import server.Config;
import server.Server;
import server.model.players.Client;
import server.util.Misc;
import server.event.*;


public class BroodooBrothers
{
	
	private Client c;
	
	public boolean gaveItems = false;
	
	public BroodooBrothers(Client c)
	{
		this.c = c;
	}
	
	public void startUp()
	{
		setHeightLevel();
		//c.getPA().movePlayer(2523, 4777, c.instancedHeightLevel);
		c.getPA().spellTeleport(2523, 4777, c.instancedHeightLevel);
		c.sendMessage("Use a tribal outfit on the grave to summon the Broodoo brothers.");
	}
	
	public void reset()
	{
		gaveItems = false;
		c.instancedHeightLevel = 0;
	}
	
	public void spawnEnemyNpc(int npcId, int health, int attack, int maxHit, int defence, boolean headIcon, int xCoord, int yCoord)
	{
		Server.npcHandler.spawnInstancedNpc(c, npcId, xCoord, yCoord, c.heightLevel, 0, health, maxHit, attack, defence, true, headIcon);
	}
	
	private void setHeightLevel()
	{
		int potentialValue = c.playerId * 100;
		
		for(int i = 0; i < Config.MAX_PLAYERS; i++)
		{
			if(Server.playerHandler.players[i] != null)
			{
				Client otherPlayer = (Client)Server.playerHandler.players[i];
				if(otherPlayer.instancedHeightLevel == potentialValue || otherPlayer.heightLevel == potentialValue)
				{
					potentialValue += 4;
				}
			}
		}
		
		c.instancedHeightLevel = potentialValue;
	}
}
		