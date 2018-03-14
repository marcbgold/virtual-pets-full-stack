package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;

@Entity
public class OrganicCat extends OrganicPet {

	public OrganicCat(String name, String description) {
		this(name, description, 20, 20, 70, 10, 0, 100);
	}

	public OrganicCat(String name, String description, int hunger, int thirst, int happiness, int tiredness, int waste, int health) {
		super(name, description, hunger, thirst, happiness, tiredness, waste, health);
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
