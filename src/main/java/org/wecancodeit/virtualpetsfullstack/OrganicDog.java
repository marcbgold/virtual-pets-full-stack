package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;

@Entity
public class OrganicDog extends OrganicPet implements Walkable, Cageable {

	public OrganicDog(String name, String description) {
		this(name, description, 20, 20, 70, 10, 0, 100);
	}

	public OrganicDog(String name, String description, int hunger, int thirst, int happiness, int tiredness, int waste, int health) {
		super(name, description, hunger, thirst, happiness, tiredness, waste, health);
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
