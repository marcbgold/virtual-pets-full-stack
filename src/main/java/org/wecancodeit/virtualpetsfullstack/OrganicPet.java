package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;

@Entity
public abstract class OrganicPet extends VirtualPet {

	protected String type = "Organic";

	protected int hungerLevel;
	protected int thirstLevel;
	protected int tirednessLevel;
	protected int wasteLevel;

	public OrganicPet() {
	}

	public OrganicPet(VirtualPetShelter shelter, String name, String description, int hunger, int thirst, int happiness, int tiredness, int waste, int health) {
		super(shelter, name, description, happiness, health);
		hungerLevel = hunger;
		thirstLevel = thirst;
		tirednessLevel = tiredness;
		wasteLevel = waste;
	}

	public String getType() {
		return type;
	}

	public int getHungerLevel() {
		return hungerLevel;
	}

	public int getThirstLevel() {
		return thirstLevel;
	}

	public int getTirednessLevel() {
		return tirednessLevel;
	}

	public int getWasteLevel() {
		return wasteLevel;
	}

	public String getPlayStatus() {
		if (healthLevel <= 20) {
			return "Too Unhealthy";
		} else if (tirednessLevel >= 80) {
			return "Too Tired";
		} else if (hungerLevel >= 80) {
			return "Too Hungry";
		} else {
			return "Play";
		}
	}

	public void eat() {
		hungerLevel -= 50;
		thirstLevel += 10;
		tirednessLevel += 10;
		wasteLevel += 20;

		keepValuesInBounds();
	}

	public void drink() {
		thirstLevel -= 50;
		wasteLevel += 20;

		keepValuesInBounds();
	}

	@Override
	public void play() {
		hungerLevel += 10;
		thirstLevel += 10;
		happinessLevel = 100;
		tirednessLevel += 30;

		keepValuesInBounds();
	}

	public void sleep() {
		hungerLevel += 20;
		thirstLevel += 20;
		tirednessLevel = 0;

		keepValuesInBounds();
	}

	public void useBathroom() {
		wasteLevel = 0;
	}

	@Override
	public void tick() {
		hungerLevel += 10;
		thirstLevel += 10;
		happinessLevel -= 10;
		tirednessLevel += 10;
		wasteLevel += 10;

		keepValuesInBounds();
	}

	@Override
	public void keepValuesInBounds() {
		if (hungerLevel > 100)
			hungerLevel = 100;

		if (thirstLevel > 100)
			thirstLevel = 100;

		if (happinessLevel < 0)
			happinessLevel = 0;

		if (happinessLevel > 100)
			happinessLevel = 100;

		if (tirednessLevel > 100)
			tirednessLevel = 100;

		if (wasteLevel > 100)
			wasteLevel = 100;

		if (healthLevel > 100)
			healthLevel = 100;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}