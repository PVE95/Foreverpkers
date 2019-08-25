package server.model.players.packets;

import server.model.items.Item;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.presets.Preset;
import server.model.players.presets.PresetSlotAction;
import server.model.players.presets.PresetType;

public class SelectItemOnInterface implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int interfaceId = player.getInStream().readDWord();
		@SuppressWarnings("unused")
		int slot = player.getInStream().readDWord();
		int itemId = player.getInStream().readDWord();
		int itemAmount = player.getInStream().readDWord();
		switch (interfaceId) {
		case 32011:
			PresetType type = player.getPresets().getCurrent().getEditingType();
			Preset preset = player.getPresets().getCurrent();
			Item item = new Item(itemId, itemAmount);
			if (type.isEquipment()) {
				int equipmentSlot = PresetSlotAction.getEquipmentSlot(type, preset.getSelectedSlot());
				if (player.getItems().getSlot(itemId) != equipmentSlot) {
					player.sendMessage("This item cannot be inserted into this equipment slot.");
					return;
				}
				preset.getEquipment().add(player, preset.getSelectedSlot(), item);
			} else if (type.isInventory()) {
				preset.getInventory().add(player, preset.getSelectedSlot(), item);
			}
			player.getPresets().hideSearch();
			break;
		}
	}

}