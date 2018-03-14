package org.wecancodeit.virtualpetsfullstack;

public class Cage {

	private int wasteLevel;

	public Cage() {
		this(0);
	}

	public Cage(int dirtiness) {
		wasteLevel = dirtiness;
	}

	public int getWasteLevel() {
		return wasteLevel;
	}

	public void addWaste() {
		wasteLevel++;
	}

	public void cleanCage() {
		wasteLevel = 0;
	}
}
