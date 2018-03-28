package org.wecancodeit.virtualpetsfullstack;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class VirtualPetShelter {

	@Id
	@GeneratedValue
	private long id;

	@OneToMany(mappedBy = "shelter")
	private Collection<VirtualPet> roster;

	@OneToMany(mappedBy = "shelter")
	private Collection<Cage> cageList;

	private int foodBowlLevel;
	private int waterBowlLevel;
	private int litterBoxLevel;

	private boolean floorIsDirty;
	private boolean petIsDead;

	public VirtualPetShelter() {
	}

	public VirtualPetShelter(int litterBoxLevel) {
		this.litterBoxLevel = litterBoxLevel;
	}

	public long getId() {
		return id;
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

	public Collection<VirtualPet> getAllPets() {
		return roster;
	}

	public Collection<VirtualPet> getAllOrganicPets() {
		Collection<VirtualPet> organics = new ArrayList<>();

		for (VirtualPet currentPet : roster) {
			if (currentPet instanceof OrganicPet) {
				organics.add(currentPet);
			}
		}
		return organics;
	}

	public Collection<VirtualPet> getAllRobotPets() {
		Collection<VirtualPet> robots = new ArrayList<>();

		for (VirtualPet currentPet : roster) {
			if (currentPet instanceof RobotPet) {
				robots.add(currentPet);
			}
		}
		return robots;
	}

	public Collection<Cage> getAllCages() {
		return cageList;
	}

	public int getOrganicPetCount() {
		int count = 0;
		for (VirtualPet i : roster) {
			if (i instanceof OrganicPet) {
				count++;
			}
		}
		return count;
	}

	public int getOrganicCatCount() {
		int count = 0;
		for (VirtualPet i : roster) {
			if (i instanceof OrganicCat) {
				count++;
			}
		}
		return count;
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

	public String scoopOutLitterBoxes() {
		if (litterBoxLevel == 0) {
			return "no need";
		} else {
			litterBoxLevel = 0;
			return "success";
		}
	}

	public void cleanAllCages() {
		for (Cage currentCage : cageList) {
			currentCage.cleanCage();
		}
	}

	public void cleanFloor() {
		floorIsDirty = false;
	}

	public void walkAllDogs() {
		for (VirtualPet currentPet : roster) {
			if (currentPet instanceof Walkable) {
				((Walkable) currentPet).goForWalk();
			}
		}
	}

	public void oilAllRobots() {
		for (VirtualPet currentPet : roster) {
			if (currentPet instanceof RobotPet) {
				((RobotPet) currentPet).oil();
			}
		}
	}

	public void chargeAllRobots() {
		for (VirtualPet currentPet : roster) {
			if (currentPet instanceof RobotPet) {
				((RobotPet) currentPet).recharge();
			}
		}
	}

	public String playWithPet(VirtualPet pet) {
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
					if (orgDog.getWasteLevel() == 100 && orgDog.getCageWasteLevel() > 2) {
						orgDog.useBathroom();
						floorIsDirty = true;
					} else if (orgPet.getWasteLevel() >= 70 && orgDog.getCageWasteLevel() < 3) {
						orgDog.useBathroom();
						orgDog.addWasteToCage();
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

		for (VirtualPet currentPet : roster) {
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
				if (orgPet instanceof OrganicDog) {
					OrganicDog orgDog = (OrganicDog) orgPet;
					if (orgDog.getCageWasteLevel() >= 2) {
						orgDog.lowerHealthLevel(10);
						healthDropped = true;
					}
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
