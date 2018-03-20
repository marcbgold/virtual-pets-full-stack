package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class OrganicDog extends OrganicPet implements Walkable, Cageable {

	@OneToOne(mappedBy = "pet")
	private Cage cage;

	public OrganicDog() {
	}

	public OrganicDog(VirtualPetShelter shelter, String name, String description) {
		this(shelter, name, description, 20, 20, 70, 10, 0, 100);
	}

	public OrganicDog(VirtualPetShelter shelter, String name, String description, int hunger, int thirst, int happiness, int tiredness, int waste, int health) {
		super(shelter, name, description, hunger, thirst, happiness, tiredness, waste, health);
	}

	public long getCageId() {
		return cage.getId();
	}

	public int getCageWasteLevel() {
		return cage.getWasteLevel();
	}

	public void addWasteToCage() {
		cage.addWaste();
	}

	@Override
	public void goForWalk() {
		happinessLevel += 20;
		tirednessLevel += 30;
		useBathroom();
	}

	@Override
	public String toString() {
		return super.toString() + "Dog\t\t|" + hungerLevel + "\t\t|" + thirstLevel + "\t\t|" + happinessLevel + "\t\t|" + tirednessLevel + "\t\t|" + wasteLevel + "\t\t|"
				+ healthLevel;
	}

}
