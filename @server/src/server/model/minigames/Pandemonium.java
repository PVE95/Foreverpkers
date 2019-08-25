//@author Ryan (Rune-server: DigitalKilbas)

package server.model.minigames;
import server.Config;
import server.Server;
import server.model.players.Client;
import server.util.Misc;
import server.event.*;


public class Pandemonium 
{
	
	private Client c;
	
	public boolean isPlaying = false;
	public int ticksPlayed = 0, missionId = -1, killCount = 0, totalNpcs = 0, lastBound = 0, earnedPoints = 0;
	public int totalPointsEarned = 0;
	
	public Pandemonium(Client c)
	{
		this.c = c;
	}
	
	public void reset()
	{
		isPlaying = false;
		ticksPlayed = 0;
		missionId = -1;
		c.instancedHeightLevel = 0;
		killCount = 0;
		totalNpcs = 0;
		lastBound = 0;
		totalPointsEarned = 0;
		earnedPoints = 0;
	}
	
	public void startUp(final int mission, int startKC, int startPoints)
	{
	if(inMission() || missionId != -1)
		{
			return;
		}
		missionId = mission;
		
		setHeightLevel();
		isPlaying = true;
		
		c.getPA().movePlayer(MissionData.validCoords[missionId][2][0], MissionData.validCoords[missionId][2][1], c.instancedHeightLevel);
		killCount = startKC;
		totalPointsEarned = startPoints;
		final Client client = c;
		
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
		{
			int revolutions = 0, outOfBoundRevs = 0, spawnCounter = 0, lastBoss = 60, thisBoss = c.bossesKilled;
			
			@Override
			public void execute(CycleEventContainer container)
			{
				if(client == null || !client.isActive)
				{
					container.stop();
					return;
				}
				
				ticksPlayed++;
				revolutions++;
				
				if(!inMission())
				{
					container.stop();
					return;
				}
				
				handleMission();
				if(client == null || !client.isActive)
				{
					container.stop();
				}
			}
			
			@Override
			public void stop()
			{
				if(client != null && client.isActive)
				{
					int seconds = (ticksPlayed * 600) / 1000;
					sendReward();
					client.sendMessage("You now have a total of @red@"+client.pandPoints+"@bla@ Pandemonium points available.");
					reset();
				}
			}
			
			public void handleMission()
			{
				switch(missionId)
				{
				case 0:
					int[][] randomLocations =
					{
						{2809, 4721},
						{2800, 4712},
						{2802, 4718},
						{2800, 4724},
						{2806, 4723},
						{2795, 4725},
						{2808, 4714}	
					};
					
					int[][] randomBosses = 
					{
							{10, 1575, 20}, //skele hellhound spawn after 10 kills, hits 5 + random of 5
							{20, 1973, 25}, //giant skele spawns after 25 kills, hits 5 + random of 10
							{30, 3066, 33} //zombie champ spawns after 40 kills, hits 15 + random of 20 (hits average of 15-20)
					};
					
					int currentAttack = 350 + (killCount * 4), maxHit = (int) (6 + (killCount * 0.05)), currentHealth = 60 + (int)(killCount * 0.9);
					
					int bossHealth = (killCount * 3) + 50;

					if(bossHealth < 20)
						bossHealth = 20;
     
					 if(bossHealth > 300)
					 {
					  bossHealth = 300;
					 }
					
					if(lastBoss > 0)
					{
						lastBoss--;
					}
					if(lastBound > 0)
					{
						lastBound--;
					}
					
					if(maxHit > 12)
					{
						maxHit = 12;
					}
					
					int randomTimer = Misc.random(40);

					if(totalNpcs > 10 && Misc.random(5) < 4) //remove comment for slower spawn rate
					{
						return;
					}
					if(totalNpcs > 15/* && Misc.random(5) < 4*/) //remove comment for slower spawn rate
					{
						return;
					}
					
					if(/*randomTimer == 0 &&*/ lastBoss <= 0)
					{
						int randomBossIndex = 0;
						
						for(int i = 0; i < randomBosses.length; i++)
						{
							if(killCount >= randomBosses[i][0])
							{
								randomBossIndex = i;
							}
						}
						
								randomBossIndex = Misc.random(randomBossIndex);
								thisBoss = c.lastBoss;
								c.lastBoss++;
							//int randomBoss = randomBosses[thisBoss][1];
							String npcName = Server.npcHandler.getNpcListName(randomBosses[randomBossIndex][1]).replace("_", " ");
							client.sendMessage("@dre@A @red@" + npcName + "@dre@ has entered the battlefield!");
							spawnEnemyNpc(randomBosses[randomBossIndex][1], bossHealth, currentAttack, randomBosses[randomBossIndex][2], 50, false, randomLocations);
							//spawnEnemyNpc(randomBoss, bossHealth, currentAttack, randomBosses[thisBoss][2], 50, false, randomLocations);
							lastBoss = 90;
					}
					
					spawnCounter++;
					
					if(spawnCounter >= 10)
					{
						spawnEnemyNpc(77, currentHealth, currentAttack, maxHit, 1, false, randomLocations);
						spawnCounter = 0;
					}
					
					/*switch(revolutions)
					{
					case 1:
					case 90:
					case 180:
						for(int i = 0; i < 2; i++)
						{
							spawnEnemyNpc(77, currentHealth, currentAttack, maxHit, 1, false, randomLocations);
							earnedPoints = currentHealth/4; //earnedPoints is given if its not a boss monster
						}
						break;
					}*/
				}
			}
		}, 0);

	}
	
	private void spawnEnemyNpc(int npcId, int health, int attack, int maxHit, int defence, boolean headIcon, int[][] randomLocations)
	{
		int random = Misc.random(randomLocations.length - 1);
		totalNpcs++;
		Server.npcHandler.spawnInstancedNpc(c, npcId, randomLocations[random][0], randomLocations[random][1], c.heightLevel, 0, health, maxHit, attack, defence, true, headIcon);
	}
	
	public void spawnEnemyNpc(int npcId, boolean headIcon, int x, int y)
	{
		totalNpcs++;
		int attack = 350 + (killCount * 4), maxHit = (int) (6 + (killCount * 0.05)), health = 30 + (int)(killCount * 0.9), defence = 1;
		
		if(maxHit > 12)
		{
			maxHit = 12;
		}
		
		Server.npcHandler.spawnInstancedNpc(c,  npcId, x, y, c.heightLevel, 0, health, maxHit, attack, defence, true, headIcon);
	}
	
	private void sendReward()
	{
		if(killCount < 0) return;
		
		boolean[] odds = {true, true, true, true};
		boolean reducedOdds = true;
		
		int[] commonItems = {4716,4718,4720,4722,4708,4710,4712,4714,4724,4726,4728,4730,4732,4734,4736,4738,4745,4747,4749,4751,4753,4755,4757,4759};
		int[] rareItems = {4151,7461,11732,8850,6731,6733,6735,6737};
		int[] veryRareItems = {4153,11730,20072,15018,15019,15020,15220,85,7462};
		int[] extremelyRareItems = {19111};//don't want anyone grinding for extreme items except cape, so best rewards are veryRare
		
		int oddsReduction = 0;
		int earnedGold = killCount * (700000 - Misc.random(20000));
		int gotIt = 0;
		
		if(killCount < 10) odds[0] = odds[1] = odds[2] = odds[3] = false;
		if(killCount < 35) odds[2] = odds[3] = false;
		if(killCount < 50) odds[3] = false;
		//if(killCount < 150) reducedOdds = false;
		oddsReduction = (int)(killCount * 0.20);
		
		//earnedGold = earnedGold > 2147000000 || earnedGold < 0 ? 2147000000 : earnedGold;
		oddsReduction = oddsReduction > 55 ? 55 : oddsReduction;

		givePoints(totalPointsEarned);
		
		
		if(odds[0]){
			if(winsOdds(3 - (oddsReduction / 40))){
				giveItem(commonItems[Misc.random(commonItems.length - 1)], 1);
			}
			if(winsOdds(2)) {
					giveItem(commonItems[Misc.random(commonItems.length - 1)], 1);
			}
		}
		
		if(odds[1])
			if(winsOdds(45 - oddsReduction))
				giveItem(rareItems[Misc.random(rareItems.length - 1)], 1);
		
		if(odds[2])
			if(winsOdds(50 - oddsReduction))
				giveItem(veryRareItems[Misc.random(veryRareItems.length - 1)], 1);
		
		if(odds[3])
			if(winsOdds(60 - (oddsReduction / 2)))
				giveItem(extremelyRareItems[Misc.random(extremelyRareItems.length - 1)], 1);

	}

	private void giveItem(int itemId, int amount){
		int inventorySlots = 0;
		
		if(c.getItems().isStackable(itemId)) inventorySlots = 1;
		else inventorySlots = amount;
	
		String alternateQuantity = String.valueOf(amount);
		
		if(alternateQuantity.length() == 6 || alternateQuantity.length() == 5) 
			alternateQuantity = " (" + alternateQuantity.substring(0, alternateQuantity.length() - 4) + "K) ";
		else if(alternateQuantity.length() > 6) 
			alternateQuantity = " (" + alternateQuantity.substring(0, alternateQuantity.length() - 6) + "M) ";
		else
			alternateQuantity = " ";
		
		String message = ("[@red@Reward@bla@]@dre@ You earn bonus item: @mag@" + c.getItems().getItemName(itemId) + "@bla@!");
		if(c.getItems().freeSlots() >= inventorySlots){
			c.getItems().addItem(itemId, amount);
		} else {
			Server.itemHandler.createGroundItem(c, itemId, c.absX, c.absY, 1, c.playerId, -1);
			message += (" @bla@(@red@Inventory full, dropped!@bla@) ");
		}
		
		c.sendMessage(message);
		

	}
	
	private boolean winsOdds(int maximum)
	{
		maximum = maximum < 0 ? 0 : maximum;
		return (Misc.random(maximum) == 0);
	}
	
	private void givePoints(int amount)
	{

		int totalAmount = amount;

		if(amount > c.bestPand) {
			c.sendMessage("[@red@Record@bla@] New personal record: @red@"+amount+"@bla@! Congratulations!");
			c.bestPand = amount;
		}

		if(c.bonusPand > amount)
			totalAmount *= 2;
		else
			totalAmount += c.bonusPand;

		c.bonusPand -= (totalAmount - amount);

		c.pandPoints += totalAmount; //adds points to server's character
		c.totalPandPoints += totalAmount;//adds to total points accumulated

		int seconds = (ticksPlayed * 600) / 1000;
		c.sendMessage("The mission is now over, you lasted @red@" + seconds + " seconds@bla@ and got @red@" + c.currentKC + " kills, @bla@earning @red@" + totalAmount + "@bla@ points."); //shows amount of seconds, kill count, and amount of points earned


		c.currentKC = 0;//resets values since reward is given
		c.currentPPoints = 0;
		c.bossesKilled = 0;

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
	
	private void initialize()
	{
		reset();
	}
	
	public boolean validCoords()
	{
		if(missionId == -1)
		{
			return false;
		}
		int x1 = MissionData.validCoords[missionId][0][0], x2 = MissionData.validCoords[missionId][1][0];
		int y1 = MissionData.validCoords[missionId][0][1], y2 = MissionData.validCoords[missionId][1][1];
		if((c.absX <= x1) && (c.absX >= x2) && (c.absY <= y1) && (c.absY >= y2))
			return true;
		else
			return false;
	}
	
	public boolean inMissionRegion()
	{
		for(int i = 0; i < MissionData.validCoords.length; i++){
			int x1 = MissionData.validCoords[i][0][0], x2 = MissionData.validCoords[i][1][0];
			int y1 = MissionData.validCoords[i][0][1], y2 = MissionData.validCoords[i][1][1];
			if((c.absX <= x1) && (c.absX >= x2) && (c.absY <= y1) && (c.absY >= y2))
				return true;
		}
		
		return false;
	}
	
	public boolean inMission()
	{
		return (!isPlaying || missionId == -1 || !validCoords()) ? false : true;
	}
}
		