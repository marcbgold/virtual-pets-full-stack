package org.wecancodeit.virtualpetsfullstack;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class VirtualPetShelterTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private VirtualPetShelterRepository shelterRepo;

	@Resource
	private VirtualPetRepository petRepo;

	@Resource
	private CageRepository cageRepo;

	private static final String NAME = "Name";
	private static final String DESCRIPTION = "Description";

	private VirtualPetShelter underTest;
	private VirtualPet orgDog;

	@Before
	public void setup() {
		underTest = new VirtualPetShelter(4);
		orgDog = new OrganicDog(underTest, NAME, DESCRIPTION);
	}

	@Test
	public void putOutFoodShouldRaiseFoodBowlLevelToOrganicPetCountTimes2() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		orgDog = petRepo.save(orgDog);

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.putOutFood();

		assertThat(underTest.getFoodBowlLevel(), is(2));
	}

	@Test
	public void putOutWaterShouldRaiseWaterBowlLevelToOrganicPetCountTimes2() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		orgDog = petRepo.save(orgDog);

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.putOutWater();

		assertThat(underTest.getWaterBowlLevel(), is(2));
	}

	@Test
	public void scoopLitterBoxShouldLowerLitterBoxLevelToZero() {
		underTest.scoopLitterBox();

		assertThat(underTest.getLitterBoxLevel(), is(0));
	}

	@Test
	public void shouldPlayWithBothOrganicAndRobotPets() {
		RobotCat roboCat = new RobotCat(underTest, "robot", "");
		String organicResult = underTest.playWithPet(orgDog);
		String robotResult = underTest.playWithPet(roboCat);

		assertThat(organicResult, is("success"));
		assertThat(robotResult, is("success"));
	}

	@Test
	public void petShouldRefuseToPlayWhenTooUnhealthy() {
		OrganicDog tooUnhealthy = new OrganicDog(underTest, "too unhealthy", "", 50, 50, 50, 50, 50, 10);

		String tooSickResult = underTest.playWithPet(tooUnhealthy);

		assertThat(tooSickResult, is("too unhealthy"));
	}

	@Test
	public void organicPetShouldRefuseToPlayWhenTooHungry() {
		OrganicDog tooHungry = new OrganicDog(underTest, "too hungry", "", 100, 50, 50, 50, 50, 50);

		String tooHungryResult = underTest.playWithPet(tooHungry);

		assertThat(tooHungryResult, is("too hungry"));
	}

	@Test
	public void organicPetShouldRefuseToPlayWhenTooTired() {
		OrganicCat tooTired = new OrganicCat(underTest, "too tired", "", 50, 50, 50, 100, 50, 50);

		String tooTiredResult = underTest.playWithPet(tooTired);

		assertThat(tooTiredResult, is("too tired"));
	}

	@Test
	public void robotPetShouldRefuseToPlayWhenOilTooLow() {
		RobotDog oilTooLow = new RobotDog(underTest, "oil too low", "", 10, 50, 50, 50);

		String oilTooLowResult = underTest.playWithPet(oilTooLow);

		assertThat(oilTooLowResult, is("oil too low"));
	}

	@Test
	public void robotPetShouldRefuseToPlayWhenChargeTooLow() {
		RobotCat chargeTooLow = new RobotCat(underTest, "charge too low", "", 50, 50, 10, 50);

		String chargeTooLowResult = underTest.playWithPet(chargeTooLow);

		assertThat(chargeTooLowResult, is("charge too low"));
	}

	@Test
	public void petsShouldTakeCareOfSelves() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		OrganicCat orgCat = new OrganicCat(underTest, NAME, DESCRIPTION, 60, 60, 60, 60, 60, 60);
		orgCat = petRepo.save(orgCat);

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.putOutFood();
		underTest.putOutWater();
		underTest.scoopLitterBox();
		underTest.petsTakeCareOfSelves();

		assertThat(underTest.getFoodBowlLevel(), is(1));
		assertThat(underTest.getWaterBowlLevel(), is(1));
		assertThat(underTest.getLitterBoxLevel(), is(1));
	}

	@Test
	public void petStatUpdatesShouldPersistWhenPetsTakeCareOfSelves() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		OrganicCat orgCat = new OrganicCat(underTest, NAME, DESCRIPTION, 60, 60, 60, 60, 60, 60);
		orgCat = petRepo.save(orgCat);
		long catId = orgCat.getId();

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.putOutFood();
		underTest.petsTakeCareOfSelves();
		orgCat = (OrganicCat) petRepo.findOne(catId);

		assertThat(orgCat.getHungerLevel(), is(15));
	}

	@Test
	public void organicCatShouldUseBathroomOnFloorWhenLitterBoxesAreFull() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		OrganicCat orgCat = new OrganicCat(underTest, NAME, DESCRIPTION, 60, 60, 60, 60, 100, 60);
		orgCat = petRepo.save(orgCat);

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.petsTakeCareOfSelves();

		assertThat(underTest.checkIfFloorIsDirty(), is(true));
	}

	@Test
	public void organicDogShouldUseBathroomInCage() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		OrganicDog extraDog = new OrganicDog(underTest, NAME, DESCRIPTION, 60, 60, 60, 60, 100, 60);
		extraDog = petRepo.save(extraDog);
		Cage cage = new Cage(underTest, extraDog);
		cage = cageRepo.save(cage);
		long cageId = cage.getId();

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.petsTakeCareOfSelves();

		cage = cageRepo.findOne(cageId);
		int wasteLevel = cage.getWasteLevel();
		assertThat(wasteLevel, is(1));
	}

	@Test
	public void organicDogShouldUseBathroomOnFloorWhenCageIsDirty() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		OrganicDog extraDog = new OrganicDog(underTest, "Extra", DESCRIPTION, 60, 60, 60, 60, 100, 60);
		extraDog = petRepo.save(extraDog);
		Cage cage = new Cage(underTest, extraDog, 3);
		cage = cageRepo.save(cage);

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.petsTakeCareOfSelves();

		boolean floorIsDirty = underTest.checkIfFloorIsDirty();
		assertThat(floorIsDirty, is(true));
	}

	@Test
	public void shouldCleanAllCages() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		OrganicDog extraDog = new OrganicDog(underTest, "Extra", DESCRIPTION, 60, 60, 60, 60, 100, 60);
		extraDog = petRepo.save(extraDog);
		Cage cage = new Cage(underTest, extraDog);
		cage = cageRepo.save(cage);
		long cageId1 = cage.getId();

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.petsTakeCareOfSelves();
		underTest.cleanAllCages();

		cage = cageRepo.findOne(cageId1);
		int result = cage.getWasteLevel();
		assertThat(result, is(0));
	}

	@Test
	public void shouldOilAllRobots() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		RobotDog roboDog = new RobotDog(underTest, NAME, DESCRIPTION);
		roboDog = petRepo.save(roboDog);
		long robotId = roboDog.getId();

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.oilAllRobots();

		roboDog = (RobotDog) petRepo.findOne(robotId);

		assertThat(roboDog.getOilLevel(), is(100));
	}

	@Test
	public void shouldChargeAllRobots() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		RobotDog roboDog = new RobotDog(underTest, NAME, DESCRIPTION);
		roboDog = petRepo.save(roboDog);
		long robotId = roboDog.getId();

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.chargeAllRobots();

		roboDog = (RobotDog) petRepo.findOne(robotId);

		assertThat(roboDog.getChargeLevel(), is(100));
	}

	@Test
	public void shouldWalkAllDogs() {
		underTest = shelterRepo.save(underTest);
		long shelterId = underTest.getId();
		RobotDog roboDog = new RobotDog(underTest, NAME, DESCRIPTION);
		roboDog = petRepo.save(roboDog);
		long robotId = roboDog.getId();

		entityManager.flush();
		entityManager.clear();

		underTest = shelterRepo.findOne(shelterId);
		underTest.walkAllDogs();

		roboDog = (RobotDog) petRepo.findOne(robotId);

		assertThat(roboDog.getHappinessLevel(), is(90));
	}

}
