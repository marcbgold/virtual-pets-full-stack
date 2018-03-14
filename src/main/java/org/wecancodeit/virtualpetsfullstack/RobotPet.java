package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;

@Entity
public abstract class RobotPet extends VirtualPet {

	protected int oilLevel;
	protected int chargeLevel;

	public RobotPet(String name, String description, int oil, int happiness, int charge, int health) {
		super(name, description, happiness, health);
		oilLevel = oil;
		chargeLevel = charge;
	}

	public int getOilLevel() {
		return oilLevel;
	}

	public int getChargeLevel() {
		return chargeLevel;
	}

	public void oil() {
		oilLevel = 100;
		chargeLevel -= 10;

		keepValuesInBounds();
	}

	@Override
	public void play() {
		oilLevel -= 20;
		happinessLevel = 100;
		chargeLevel -= 30;

		keepValuesInBounds();
	}

	public void recharge() {
		happinessLevel -= 10;
		chargeLevel = 100;
	}

	@Override
	public void tick() {
		oilLevel -= 10;
		happinessLevel -= 10;
		chargeLevel -= 10;

		keepValuesInBounds();
	}

	public void keepValuesInBounds() {
		if (oilLevel < 0)
			oilLevel = 0;

		if (happinessLevel < 0)
			happinessLevel = 0;

		if (happinessLevel > 100)
			happinessLevel = 100;

		if (chargeLevel < 0)
			chargeLevel = 0;

		if (chargeLevel > 100)
			chargeLevel = 100;

		if (healthLevel > 100)
			healthLevel = 100;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
