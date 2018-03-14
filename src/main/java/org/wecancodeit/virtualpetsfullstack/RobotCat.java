package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;

@Entity
public class RobotCat extends RobotPet {

	public RobotCat() {
	}

	public RobotCat(VirtualPetShelter shelter, String name, String description) {
		this(shelter, name, description, 80, 70, 90, 100);
	}

	public RobotCat(VirtualPetShelter shelter, String name, String description, int oil, int happiness, int charge, int health) {
		super(shelter, name, description, oil, happiness, charge, health);
	}

	@Override
	public String toString() {
		return super.toString() + "Cat\t\t|" + oilLevel + "\t\t|" + happinessLevel + "\t\t|" + chargeLevel + "\t\t|" + healthLevel;
	}

	@Override
	public void tick() {
		oilLevel -= 5;
		happinessLevel -= 5;
		chargeLevel -= 5;

		keepValuesInBounds();
	}
}
