package org.wecancodeit.virtualpetsfullstack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class VirtualPetShelter {

	@OneToMany(mappedBy = "shelter")
	private Collection<VirtualPet> roster;

	@OneToMany(mappedBy = "shelter")
	private Collection<Cage> cages;

	private int foodBowlLevel;
	private int waterBowlLevel;
	private int litterBoxLevel;
	private Map<VirtualPet, Cage> cageList = new HashMap<>();

	private boolean floorIsDirty;
	private boolean petIsDead;

	public VirtualPetShelter() {
	}

	public VirtualPetShelter(int litterBoxLevel) {
		this.litterBoxLevel = litterBoxLevel;
		// admitNewPet(defaultOrganicDog);
		// admitNewPet(defaultOrganicCat);
		// admitNewPet(defaultRobotPet);
	}

	public int getFoodBowlLevel() {
		return foodBowlLevel;
	}

	public int getWaterBowlLevel() {
		return waterBowlLevel;
	}

	public int getLitterBoxLevel() {
		return litterBoxLevel;
	}

	public boolean checkIfLitterBoxesAreFull() {
		return litterBoxLevel >= getOrganicCatCount() * 2;
	}

	public boolean checkIfFloorIsDirty() {
		return floorIsDirty;
	}

	public boolean checkIfPetIsDead() {
		return petIsDead;
	}

	public void admitNewPet(VirtualPet petInput) {
		roster.put(petInput.getName(), petInput);
		if (petInput instanceof Cageable) {
			cageList.put(petInput, new Cage());
		}
	}

	public void admitNewDogWithDirtyCage(VirtualPet petInput, int dirtiness) {
		roster.put(petInput.getName(), petInput);
		cageList.put(petInput, new Cage(dirtiness));
	}

	public boolean checkIfPetExists(String name) {
		return roster.containsKey(name);
	}

	public VirtualPet getPet(String name) {
		return roster.get(name);
	}

	public Collection<VirtualPet> getAllPets() {
		return roster;
	}

	public Collection<VirtualPet> getAllOrganicPets() {
		Collection<VirtualPet> organics = new ArrayList<>();

		for (VirtualPet currentPet : roster.values()) {
			if (currentPet instanceof OrganicPet) {
				organics.add(currentPet);
			}
		}
		return organics;
	}

	public Collection<VirtualPet> getAllRobotPets() {
		Collection<VirtualPet> robots = new ArrayList<>();

		for (VirtualPet currentPet : roster.values()) {
			if (currentPet instanceof RobotPet) {
				robots.add(currentPet);
			}
		}
		return robots;
	}

	public int getOrganicPetCount() {
		int count = 0;
		for (VirtualPet i : roster.values()) {
			if (i instanceof OrganicPet) {
				count++;
			}
		}
		return count;
	}

	public int getOrganicCatCount() {
		int count = 0;
		for (VirtualPet i : roster.values()) {
			if (i instanceof OrganicCat) {
				count++;
			}
		}
		return count;
	}

	public Map<VirtualPet, Cage> getAllCages() {
		return cageList;
	}

	public void adoptOutPet(String name) {
		VirtualPet pet = roster.get(name);
		if (cageList.containsKey(pet)) {
			cageList.remove(pet);
		}
		roster.remove(name);
	}

	public String putOutFood() {
		if (foodBowlLevel < getOrganicPetCount() * 2) {
			foodBowlLevel = getOrganicPetCount() * 2;
			return "success";
		}
		return "no need";
	}

	public String putOutWater() {
		if (waterBowlLevel < getOrganicPetCount() * 2) {
			waterBowlLevel = getOrganicPetCount() * 2;
			return "success";
		}
		return "no need";
	}

	public String scoopLitterBox() {
		if (litterBoxLevel == 0) {
			return "no need";
		} else {
			litterBoxLevel = 0;
			return "success";
		}
	}

	public int getCageWasteLevel(VirtualPet input) {
		return cageList.get(input).getWasteLevel();
	}

	public void cleanAllCages() {
		// cageList.values().forEach(cage -> cage.cleanCage());

		for (Cage currentCage : cageList.values()) {
			currentCage.cleanCage();
		}
	}

	public void cleanFloor() {
		floorIsDirty = false;
	}

	public void walkAllDogs() {
		for (VirtualPet currentPet : roster.values()) {
			if (currentPet instanceof Walkable) {
				((Walkable) currentPet).goForWalk();
			}
		}
	}

	// public void walkAllDogs() {
	// roster.values().forEach(pet -> {
	// if (pet instanceof Walkable) {
	// ((Walkable) pet).goForWalk();
	// }
	// });
	// }

	public void oilAllRobots() {
		for (VirtualPet currentPet : roster.values()) {
			if (currentPet instanceof RobotPet) {
				((RobotPet) currentPet).oil();
			}
		}
	}

	// public void oilAllRobots() {
	// roster.values().forEach(pet -> {
	// if (pet instanceof RobotPet) {
	// ((RobotPet) pet).oil();
	// }
	// });
	// }

	public void chargeAllRobots() {
		for (VirtualPet currentPet : roster.values()) {
			if (currentPet instanceof RobotPet) {
				((RobotPet) currentPet).recharge();
			}
		}
	}

	// public void chargeAllRobots() {
	// roster.values().forEach(pet -> {
	// if (pet instanceof RobotPet) {
	// ((RobotPet) pet).recharge();
	// }
	// });
	// }

	public String playWithPet(String name) {
		VirtualPet pet = getPet(name);
		if (pet.getHealthLevel() <= 20) {
			return "too unhealthy";
		}

		if (pet instanceof OrganicPet) {
			OrganicPet orgPet = (OrganicPet) pet;
			if (orgPet.getTirednessLevel() >= 80) {
				return "too tired";
			} else if (orgPet.getHungerLevel() >= 80) {
				return "too hungry";
			}
		}

		if (pet instanceof RobotPet) {
			RobotPet roboPet = (RobotPet) pet;
			if (roboPet.getOilLevel() <= 20) {
				return "oil too low";
			} else if (roboPet.getChargeLevel() <= 20) {
				return "charge too low";
			}
		}
		pet.play();
		return "success";
	}

	public void petsTakeCareOfSelves() {
		for (VirtualPet currentPet : getAllPets()) {
			currentPet.tick();

			if (currentPet instanceof OrganicPet) {
				OrganicPet orgPet = (OrganicPet) currentPet;

				if (orgPet.getHungerLevel() >= 50 && foodBowlLevel > 0) {
					orgPet.eat();
					foodBowlLevel--;
				}

				if (orgPet.getThirstLevel() >= 50 && waterBowlLevel > 0) {
					orgPet.drink();
					waterBowlLevel--;
				}

				if (orgPet instanceof OrganicDog) {
					OrganicDog orgDog = (OrganicDog) orgPet;
					if (orgDog.getWasteLevel() == 100 && getCageWasteLevel(orgDog) > 2) {
						orgDog.useBathroom();
						floorIsDirty = true;
					} else if (orgPet.getWasteLevel() >= 70 && getCageWasteLevel(orgDog) < 3) {
						orgDog.useBathroom();
						cageList.get(orgDog).addWaste();
					}
				}

				if (orgPet instanceof OrganicCat) {
					OrganicCat orgCat = (OrganicCat) orgPet;
					if (orgCat.getWasteLevel() == 100 && checkIfLitterBoxesAreFull()) {
						orgCat.useBathroom();
						floorIsDirty = true;
					} else if (orgPet.getWasteLevel() >= 70 && !checkIfLitterBoxesAreFull()) {
						orgCat.useBathroom();
						litterBoxLevel++;
					}
				}

				if (orgPet.getTirednessLevel() >= 80) {
					orgPet.sleep();
				}
			}
		}
	}

	public void checkForHealthProblems() {
		boolean healthDropped;

		for (VirtualPet currentPet : roster.values()) {
			healthDropped = false;

			if (currentPet.getHealthLevel() <= 30) {
				currentPet.lowerHappinessLevel(10);
			}

			if (currentPet.getHappinessLevel() == 0) {
				currentPet.lowerHealthLevel(10);
				healthDropped = true;
			}

			if (currentPet instanceof OrganicPet) {
				OrganicPet orgPet = (OrganicPet) currentPet;
				if (orgPet.getHungerLevel() == 100 || orgPet.getThirstLevel() == 100) {
					orgPet.lowerHealthLevel(10);
					healthDropped = true;
				}
				if (floorIsDirty) {
					orgPet.lowerHealthLevel(10);
					healthDropped = true;
				}
				if (orgPet instanceof OrganicDog && getCageWasteLevel(orgPet) >= 2) {
					orgPet.lowerHealthLevel(10);
					healthDropped = true;
				}
			} else if (currentPet instanceof RobotPet) {
				RobotPet roboPet = (RobotPet) currentPet;
				if (roboPet.getOilLevel() == 0 || roboPet.getChargeLevel() == 0) {
					roboPet.lowerHealthLevel(10);
					healthDropped = true;
				}
			}

			if (currentPet.getHealthLevel() <= 0) {
				petIsDead = true;
				break;
			}

			if (!healthDropped) {
				currentPet.raiseHealthLevel(20);
			}
		}

	}
}
