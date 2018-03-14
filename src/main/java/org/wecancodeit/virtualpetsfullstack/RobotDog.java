package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;

@Entity
public class RobotDog extends RobotPet implements Walkable, Cageable {

	public RobotDog(String name, String description) {
		this(name, description, 80, 70, 90, 100);
	}

	public RobotDog(String name, String description, int oil, int happiness, int charge, int health) {
		super(name, description, oil, happiness, charge, health);
	}

	@Override
	public void goForWalk() {
		oilLevel -= 10;
		happinessLevel += 20;
		chargeLevel -= 10;
	}

	@Override
	public String toString() {
		return super.toString() + "Dog\t\t|" + oilLevel + "\t\t|" + happinessLevel + "\t\t|" + chargeLevel + "\t\t|" + healthLevel;
	}

}
