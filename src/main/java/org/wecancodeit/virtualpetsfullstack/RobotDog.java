package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class RobotDog extends RobotPet implements Walkable, Cageable {

	private String species = "Dog";

	@OneToOne(mappedBy = "pet")
	private Cage cage;

	@SuppressWarnings("unused")
	private RobotDog() {
	}

	public RobotDog(VirtualPetShelter shelter, String name, String description) {
		this(shelter, name, description, 80, 70, 90, 100);
	}

	public RobotDog(VirtualPetShelter shelter, String name, String description, int oil, int happiness, int charge, int health) {
		super(shelter, name, description, oil, happiness, charge, health);
	}

	public String getSpecies() {
		return species;
	}

	public long getCageId() {
		return cage.getId();
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
