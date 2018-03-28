package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;

@Entity
public class OrganicCat extends OrganicPet {

	private String species = "Cat";

	@SuppressWarnings("unused")
	private OrganicCat() {
	}

	public OrganicCat(VirtualPetShelter shelter, String name, String description) {
		this(shelter, name, description, 20, 20, 70, 10, 0, 100);
	}

	public OrganicCat(VirtualPetShelter shelter, String name, String description, int hunger, int thirst, int happiness, int tiredness, int waste, int health) {
		super(shelter, name, description, hunger, thirst, happiness, tiredness, waste, health);
	}

	public String getSpecies() {
		return species;
	}

	@Override
	public String toString() {
		return super.toString() + "Cat\t\t|" + hungerLevel + "\t\t|" + thirstLevel + "\t\t|" + happinessLevel + "\t\t|" + tirednessLevel + "\t\t|" + wasteLevel + "\t\t|"
				+ healthLevel;
	}

	@Override
	public void tick() {
		hungerLevel += 5;
		thirstLevel += 5;
		happinessLevel -= 5;
		tirednessLevel += 5;
		wasteLevel += 5;

		keepValuesInBounds();
	}

}
