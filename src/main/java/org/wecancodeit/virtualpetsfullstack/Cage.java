package org.wecancodeit.virtualpetsfullstack;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Cage {

	@Id
	private long id;

	@ManyToOne
	VirtualPetShelter shelter;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private VirtualPet pet;

	private int wasteLevel;

	public Cage() {
	}

	public Cage(VirtualPetShelter shelter, VirtualPet pet) {
		this(shelter, pet, 0);
	}

	public Cage(VirtualPetShelter shelter, VirtualPet pet, int dirtiness) {
		this.shelter = shelter;
		this.pet = pet;
		wasteLevel = dirtiness;
	}

	public long getId() {
		return id;
	}

	public VirtualPetShelter getShelter() {
		return shelter;
	}

	public VirtualPet getPet() {
		return pet;
	}

	public int getWasteLevel() {
		return wasteLevel;
	}

	public void addWaste() {
		wasteLevel++;
	}

	public void cleanCage() {
		wasteLevel = 0;
	}
}
