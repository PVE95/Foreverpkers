package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.Connection;
import server.util.Misc;
import server.Server;

/**
 * Chat : Modified by Coder Alex. 29.10.2010
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte)(c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
		c.getPA().writeChatLog(Misc.textUnpack(c.getChatText(), packetSize-2));
		c.getPA().writeChatLine(Misc.textUnpack(c.getChatText(), packetSize-2));
		int fuckyou = 0;

		String[] stuffz = {".c0m", "luzo","l u z o", "luzox", "luzo x", "xpk", "luz", "all join luzo","forumotion","co.uk","c0.uk","co . uk",".net" , ".com" , ".info" , ".org", ".tk", ". tk", ". com", ". org", ". net", ". info", "www.", "selling rs", "sell rs", "buying rs", "this server suck", "this server is gay", "ryans gay", "james is gay", "sell runescape", "selling runescape", "buy runescape", "buying runescape", "sell don", "sellin don", "selling don", "buying don", "buy don", "sell acc", "sellin acc", "buyin acc", "buy acc", "pkrss", "p k r s s", "com", "www", "pkrs s"};					
		String term = Misc.textUnpack(c.getChatText(), c.packetSize - 2).toLowerCase();
		for(int i = 0; i < stuffz.length; i++) {
			if(term.contains(stuffz[i])) {
				if(c.playerRights < 1)
						return;
			}
		}

		for(int i2 = 0; i2 < c.badwords.length; i2++) {
			if(term.contains(c.badwords[i2])) {
				if(c.playerRights < 1){
					c.sendMessage("@red@Please refrain from using foul language. Your message was blocked.");
					c.sendMessage("@red@Passing the filter will result in a permanent mute.");
					return;
				}
			}
		}
		
		if(term.contains("@dre@on the percentile dice") || term.contains("@dre@ just rolled") || term.contains("@dre@just rolled")){
			c.sendMessage("@red@Your message was blocked because it is similar to the ::dice message.");
			return;
		}
		
		if (!Connection.isMuted(c))
			c.setChatTextUpdateRequired(true);

	
	}	
}