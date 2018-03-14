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

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private VirtualPet pet;

	@ManyToOne
	VirtualPetShelter shelter;
	private int wasteLevel;

	public Cage() {
		this(0);
	}

	public Cage(int dirtiness) {
		wasteLevel = dirtiness;
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
