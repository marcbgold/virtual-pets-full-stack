package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public abstract class VirtualPet {

	@Id
	@GeneratedValue
	protected long id;

	@ManyToOne
	VirtualPetShelter shelter;
	protected String name;

	@Lob
	protected String description;
	protected int happinessLevel;
	protected int healthLevel;

	public VirtualPet(String name, String description, int happiness, int health) {
		this.name = name;
		this.description = description;
		happinessLevel = happiness;
		healthLevel = health;
	}

	public long getId() {
		return id;
	}

	public VirtualPetShelter getShelter() {
		return shelter;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getHappinessLevel() {
		return happinessLevel;
	}

	public void lowerHappinessLevel(int amount) {
		happinessLevel -= amount;
	}

	public int getHealthLevel() {
		return healthLevel;
	}

	public void raiseHealthLevel(int amount) {
		healthLevel += amount;
		if (healthLevel > 100) {
			healthLevel = 100;
		}
	}

	public void lowerHealthLevel(int amount) {
		healthLevel -= amount;
	}

	public abstract void play();

	public abstract void tick();

	public abstract void keepValuesInBounds();

	public boolean equals(VirtualPet input) {
		return name.hashCode() == input.hashCode();
	}

	// @Override
	// public String toString() {
	// return name + ": " + description;
	// }

	@Override
	public String toString() {
		String namePart = "";
		if (name.length() < 9) {
			namePart = name + "\t\t|";
		} else if (name.length() < 15) {
			namePart = name + "\t|";
		} else {
			namePart = name + " |";
		}
		return namePart;
	}

	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		return id == ((VirtualPet) obj).id;
	}
}
