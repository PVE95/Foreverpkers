package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Change appearance
 **/
public class ChangeAppearance implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int gender = c.getInStream().readSignedByte();
		int head = c.getInStream().readSignedByte();
		int jaw = c.getInStream().readSignedByte();
		int torso = c.getInStream().readSignedByte();
		int arms = c.getInStream().readSignedByte();
		int hands = c.getInStream().readSignedByte();
		int legs = c.getInStream().readSignedByte();
		int feet = c.getInStream().readSignedByte();
		int hairColour = c.getInStream().readSignedByte();
		int torsoColour = c.getInStream().readSignedByte();
		int legsColour = c.getInStream().readSignedByte();
		int feetColour = c.getInStream().readSignedByte();
		int skinColour = c.getInStream().readSignedByte();
		
		if(gender != 0 && gender != 1){
			c.sendMessage("Invalid Gender");
			return;
		}
			
		if(gender == 0){
			if(head < 0 || head > 8)
				head = 0;
				
			if(jaw < 10 || jaw > 17)
				jaw = 10;
			
			if(torso < 18 || torso > 25)
				torso = 18;
				
			if(arms < 26 || arms > 31)
				arms = 26;
				
			if(hands < 33 || hands > 34)
				hands = 33;
				
			if(legs < 36 || legs > 40)
				legs = 36;
				
			if(feet < 42 || feet > 43)
				feet = 42;
				
		} else if(gender == 1){
			if(head < 45 || head > 54)
				head = 45;
				
			if(jaw != -1)
				jaw = -1;
			
			if(torso < 56 || torso > 60)
				torso = 56;
				
			if(arms < 61 || arms > 65)
				arms = 61;
				
			if(hands < 67 || hands > 68)
				hands = 67;
				
			if(legs < 70 || legs > 77)
				legs = 70;
				
			if(feet < 79 || feet > 80)
				feet = 79;
		}
		
		if(hairColour < 0 || hairColour > 11)
			hairColour = 0;
			
		if(torsoColour < 0 || torsoColour > 15)
			torsoColour = 0;
			
		if(feetColour < 0 || feetColour > 5)
			feetColour = 0;
			
		if(skinColour < 0 || skinColour > 7)
			skinColour = 0;
		
		if (c.canChangeAppearance) { 
			c.playerAppearance[0] = gender; // gender
			c.playerAppearance[1] = head; // head
			c.playerAppearance[2] = torso;// Torso
			c.playerAppearance[3] = arms; // arms
			c.playerAppearance[4] = hands; // hands
			c.playerAppearance[5] = legs; // legs
			c.playerAppearance[6] = feet; // feet
			c.playerAppearance[7] = jaw; // beard
			c.playerAppearance[8] = hairColour; // hair colour
			c.playerAppearance[9] = torsoColour; // torso colour
			c.playerAppearance[10] = legsColour; // legs colour
			c.playerAppearance[11] = feetColour; // feet colour
			c.playerAppearance[12] = skinColour; // skin colour

			c.getPA().removeAllWindows();
			c.getPA().requestUpdates();
			c.canChangeAppearance = false;
			if(c.doingStarter == 1 && c.gameMode != 0) {
				c.getDH().sendStartInfo("The best way to get to know the server", "is by completing the starter tasks.", "Go to your @red@quest tab@bla@ to get started,", "there are rewards waiting for you!", "@dre@Welcome to ForeverPkers, "+c.playerName+"");
			}
			if(c.doingStarter == 0 && c.gameMode != 0) {
				c.getDH().sendStartInfo("Teleport to the @or1@shops@bla@ by using @blu@::shops@bla@.", "Talk to the @or1@Legend's Guard@bla@ he will give you", "limited @or1@free items@bla@.", "type @blu@::commands@bla@ for commands.", "@dre@Welcome to ForeverPkers!");
			}
			//c.getDH().sendDialogues(9024, 1);
		if(c.doingStarter == 1) {
			if(c.sTask7 == 0) {
				c.sTask7 = 1;
				c.sendMessage("You completed a starter task!");
				c.getItems().addItem(995,100000);
			}
		}
		}	
	}	
}
