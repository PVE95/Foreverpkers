package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;


/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();
		c.printPacketLog("Player wore a " + c.getItems().getItemName(c.wearId) + ".");
		c.alchDelay = System.currentTimeMillis();
		int oldCombatTimer = c.attackTimer;
		if ((c.playerIndex > 0 || c.npcIndex > 0) && c.wearId != 4153)
			c.getCombat().resetPlayerAttack();
		if(c.playerEquipment[c.playerWeapon] == 15042 && c.bloodDamage > 0) {
			c.bloodDamage = 0;
			c.forcedText = "Ahhh... much better.";
			c.updateRequired = true;
			c.forcedChatUpdateRequired = true;
		}
		if (c.wearId >= 5509 && c.wearId <= 5515) {
			int pouch = -1;
			int a = c.wearId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().emptyPouch(pouch);
			return;
		}
			//c.attackTimer = oldCombatTimer;
		if (!c.inTrade) {
            c.getItems().wearItem(c.wearId, c.wearSlot);
        }
	}

}
