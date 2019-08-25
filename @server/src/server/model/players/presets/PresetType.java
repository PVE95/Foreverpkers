package server.model.players.presets;

public enum PresetType {
	
	INVENTORY, EQUIPMENT;
	
	/**
	 * Determines if the type is an inventory preset
	 * @return	true if the type is an inventory preset
	 */
	public boolean isInventory() {
		return equals(INVENTORY);
	}
	
	/**
	 * Determines if the type is an equipment preset
	 * @return	true if the type is an equipment preset
	 */
	public boolean isEquipment() {
		return equals(EQUIPMENT);
	}

}