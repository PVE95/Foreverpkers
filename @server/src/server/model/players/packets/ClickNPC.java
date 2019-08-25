package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Click NPC
 */
public class ClickNPC implements PacketType {
	public static final int ATTACK_NPC = 72, MAGE_NPC = 131, FIRST_CLICK = 155, SECOND_CLICK = 17, THIRD_CLICK = 21;
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.npcIndex = 0;
		c.npcClickIndex = 0;
		c.playerIndex = 0;
		c.clickNpcType = 0;
		//int castingSpellId = c.getInStream().readSignedWordA();
		c.getPA().resetFollow();
		switch(packetType) {
			
			/**
			* Attack npc melee or range
			**/
			case ATTACK_NPC:
			if (!c.mageAllowed) {
				c.mageAllowed = true;
				c.sendMessage("I can't reach that.");
				break;
			}
			boolean usingMageWep = c.playerEquipment[c.playerWeapon] == 22494 || c.playerEquipment[c.playerWeapon] == 19112 || c.playerEquipment[c.playerWeapon] == 2415 || c.playerEquipment[c.playerWeapon] == 2416 || c.playerEquipment[c.playerWeapon] == 2417;
			if(c.inMageArena()) {
				if(c.spellId <= 0 && !usingMageWep && !c.autocasting) {
				c.sendMessage("You can only use magic in the Mage Arena.");
				c.getCombat().resetPlayerAttack();
				break;
				}
			}
			c.npcIndex = c.getInStream().readUnsignedWordA();
			if (Server.npcHandler.npcs[c.npcIndex] == null) {
				c.npcIndex = 0;
				break;
			}	
			if (Server.npcHandler.npcs[c.npcIndex].MaxHP == 0) {
				c.npcIndex = 0;
				break;
			}			
			
			if(Server.npcHandler.npcs[c.npcIndex] == null){
				break;
			}
			if (c.autocastId > 0)
				c.autocasting = true;			
			if (!c.autocasting && c.spellId > 0) {
				c.spellId = 0;
			}
			
			c.faceUpdate(c.npcIndex);			
			c.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = false;
					usingCross = c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 15041;
			if (c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223)
				usingBow = true;
			for (int bowId : c.BOWS) {
				if(c.playerEquipment[c.playerWeapon] == bowId) {
					usingBow = true;
					for (int arrowId : c.ARROWS) {
						if(c.playerEquipment[c.playerArrows] == arrowId) {
							usingArrows = true;
						}
					}
				}
			}
			for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
				if(c.playerEquipment[c.playerWeapon] == otherRangeId) {
					usingOtherRangeWeapons = true;
				}
			}
			if((usingBow || c.autocasting) && c.goodDistance(c.getX(), c.getY(), Server.npcHandler.npcs[c.npcIndex].getX(), Server.npcHandler.npcs[c.npcIndex].getY(), 7)) {
				c.stopMovement();
			}
			
			if(usingOtherRangeWeapons && c.goodDistance(c.getX(), c.getY(), Server.npcHandler.npcs[c.npcIndex].getX(), Server.npcHandler.npcs[c.npcIndex].getY(), 4)) {
				c.stopMovement();
			}
			if(!usingCross && !usingArrows && usingBow && c.playerEquipment[c.playerWeapon] < 4212 && c.playerEquipment[c.playerWeapon] > 4223 && !usingCross) {
				c.sendMessage("You have run out of arrows!");
				break;
			} 
			if(c.getCombat().correctBowAndArrows() < c.playerEquipment[c.playerArrows] && Config.CORRECT_ARROWS && usingBow && !c.getCombat().usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 9185 && c.playerEquipment[c.playerWeapon] != 15041) {
				c.sendMessage("You can't use "+c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase()+"s with a "+c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()+".");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;
			}
			if ((c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 15041) && !c.getCombat().properBolts()) {
				c.sendMessage("You must use bolts with a crossbow.");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;				
			}
			
			if (c.followId > 0) {
				c.getPA().resetFollow();			
			}
			if (c.attackTimer <= 0) {
				c.getCombat().attackNpc(c.npcIndex);
				c.attackTimer++;
			}	
			
			break;
			
			/**
			* Attack npc with magic
			**/
			case MAGE_NPC:
			if (!c.mageAllowed) {
				c.mageAllowed = true;
				c.sendMessage("I can't reach that.");
				break;
			}
			//c.usingSpecial = false;
			//c.getItems().updateSpecialBar();
			
			c.npcIndex = c.getInStream().readSignedWordBigEndianA();
			int castingSpellId = c.getInStream().readSignedWordA();
			c.usingMagic = false;
			
			if(Server.npcHandler.npcs[c.npcIndex] == null ){
				break;
			}
			
			if(Server.npcHandler.npcs[c.npcIndex].MaxHP == 0 || Server.npcHandler.npcs[c.npcIndex].npcType == 944){
				c.sendMessage("You can't attack this npc.");
				break;
			}
			
			
			for(int i = 0; i < c.MAGIC_SPELLS.length; i++){
				if(castingSpellId == c.MAGIC_SPELLS[i][0]) {
					c.spellId = i;
					c.usingMagic = true;
					c.magicDamage = c.getCombat().calculateMagicDamage(c, c.npcIndex);
					break;
				}
			}
			
			if(castingSpellId == 1171) { // crumble undead
				for (int npc : Config.UNDEAD_NPCS) {
					if(Server.npcHandler.npcs[c.npcIndex].npcType != npc) {
					 c.sendMessage("You can only attack undead enemies with this spell.");
					 c.usingMagic = false;
					 c.spellId = 0;
					 c.stopMovement();
					 c.npcIndex = 0;
					 break;
					}
				}
			}
			
			/*if(!c.getCombat().checkMagicReqs(c.spellId)) {
				c.stopMovement();
				break;
			}*/
			/*if (c.autocasting)
				c.autocasting = false;*/
				
			
			
			if(c.usingMagic) {
				if(c.goodDistance(c.getX(), c.getY(), Server.npcHandler.npcs[c.npcIndex].getX(), Server.npcHandler.npcs[c.npcIndex].getY(), 8)) {
					c.stopMovement();
				}
				if (c.attackTimer <= 0) {
					c.getCombat().attackNpc(c.npcIndex);
					c.attackTimer++;
				}	
			}
	
			break;
			
			case FIRST_CLICK:
				c.npcClickIndex = c.inStream.readSignedWordBigEndian();
				c.npcType = Server.npcHandler.npcs[c.npcClickIndex].npcType;
				
				c.printPacketLog("Player first-clicked npc id " + c.npcType);
				
				if(c.goodDistance(Server.npcHandler.npcs[c.npcClickIndex].getX(), Server.npcHandler.npcs[c.npcClickIndex].getY(), c.getX(), c.getY(), 1)) {
					c.turnPlayerTo(Server.npcHandler.npcs[c.npcClickIndex].getX(), Server.npcHandler.npcs[c.npcClickIndex].getY());
					Server.npcHandler.npcs[c.npcClickIndex].facePlayer(c.playerId);
					c.getActions().firstClickNpc(c.npcType);	
				} else {
					c.clickNpcType = 1;
				}
				break;
			
			case SECOND_CLICK:
				c.npcClickIndex = c.inStream.readUnsignedWordBigEndianA();
				c.npcType = Server.npcHandler.npcs[c.npcClickIndex].npcType;
				
				c.printPacketLog("Player second-clicked npc id " + c.npcType);
				if(c.goodDistance(Server.npcHandler.npcs[c.npcClickIndex].getX(), Server.npcHandler.npcs[c.npcClickIndex].getY(), c.getX(), c.getY(), 1)) {
					c.turnPlayerTo(Server.npcHandler.npcs[c.npcClickIndex].getX(), Server.npcHandler.npcs[c.npcClickIndex].getY());
					Server.npcHandler.npcs[c.npcClickIndex].facePlayer(c.playerId);
					c.getActions().secondClickNpc(c.npcType);	
				} else {
					c.clickNpcType = 2;
				}
				break;
			
			case THIRD_CLICK:
				c.npcClickIndex = c.inStream.readSignedWord();
				c.npcType = Server.npcHandler.npcs[c.npcClickIndex].npcType;
				
				c.printPacketLog("Player third-clicked npc id " + c.npcType);
				if(c.goodDistance(Server.npcHandler.npcs[c.npcClickIndex].getX(), Server.npcHandler.npcs[c.npcClickIndex].getY(), c.getX(), c.getY(), 1)) {
					c.turnPlayerTo(Server.npcHandler.npcs[c.npcClickIndex].getX(), Server.npcHandler.npcs[c.npcClickIndex].getY());
					Server.npcHandler.npcs[c.npcClickIndex].facePlayer(c.playerId);
					c.getActions().thirdClickNpc(c.npcType);	
				} else {
					c.clickNpcType = 3;
				}
				break;
		}

	}
}
