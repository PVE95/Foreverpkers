package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.presets.Preset;

public class InputField implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int id = player.inStream.readDWord();
		String text = player.inStream.readString();
		System.out.println("id: "+id);
		System.out.println("text"+text);
		switch (id) {
			case 32002:
				Preset preset = player.getPresets().getCurrent();
				if (preset == null) {
					player.sendMessage("You must select a preset before changing the name.");
					return;
				}
				preset.setAlias(text);
				player.getPresets().refreshMenus(preset.getMenuSlot(), preset.getMenuSlot() + 1);
				break;
	
			default:
				break;
		}
	}

}