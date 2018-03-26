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
	// private VirtualPet orgCat;

	@Before
	public void setup() {
		underTest = new VirtualPetShelter(4);
		orgDog = new OrganicDog(underTest, NAME, DESCRIPTION);
		// orgCat = new OrganicCat(underTest, NAME, DESCRIPTION);
	}

	@Test
	public void putOutFoodShouldRaiseFoodBowlLevelToOrganicPetCountTimes2() {
		underTest.putOutFood();

		assertThat(underTest.getFoodBowlLevel(), is(6));
	}

	@Test
	public void putOutWaterShouldRaiseWaterBowlLevelToOrganicPetCountTimes2() {
		underTest.putOutWater();

		assertThat(underTest.getWaterBowlLevel(), is(6));
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
		OrganicDog extraDog = new OrganicDog(underTest, "Extra", DESCRIPTION, 60, 60, 60, 60, 100, 60);
		underTest.admitNewDogWithDirtyCage(extraDog, 3);
		underTest.petsTakeCareOfSelves();

		boolean result = underTest.checkIfFloorIsDirty();
		assertThat(result, is(true));
	}

	@Test
	public void shouldCleanAllCages() {
		OrganicDog extraDog = new OrganicDog(underTest, "Extra", DESCRIPTION, 60, 60, 60, 60, 100, 60);
		underTest.admitNewPet(extraDog);
		underTest.petsTakeCareOfSelves();
		underTest.cleanAllCages();

		VirtualPet test = underTest.getPet("Extra");

		int result = underTest.getCageWasteLevel(test);
		assertThat(result, is(0));
	}

	@Test
	public void shouldOilAllRobots() {
		RobotDog extraRobot = new RobotDog(underTest, "extra", DESCRIPTION);
		underTest.admitNewPet(extraRobot);
		underTest.oilAllRobots();

		RobotCat glados = (RobotCat) underTest.getPet("GLaDOS");
		extraRobot = (RobotDog) underTest.getPet("extra");

		assertThat(glados.getOilLevel(), is(100));
		assertThat(extraRobot.getOilLevel(), is(100));

	}

	@Test
	public void shouldChargeAllRobots() {
		RobotDog extraRobot = new RobotDog(underTest, "extra", DESCRIPTION);
		underTest.admitNewPet(extraRobot);
		underTest.chargeAllRobots();

		RobotCat glados = (RobotCat) underTest.getPet("GLaDOS");
		extraRobot = (RobotDog) underTest.getPet("extra");

		assertThat(glados.getChargeLevel(), is(100));
		assertThat(extraRobot.getChargeLevel(), is(100));

	}

	@Test
	public void shouldWalkAllDogs() {
		RobotDog extraRobot = new RobotDog(underTest, "extra", DESCRIPTION);
		underTest.admitNewPet(extraRobot);
		underTest.walkAllDogs();

		OrganicDog crono = (OrganicDog) underTest.getPet("Crono");
		extraRobot = (RobotDog) underTest.getPet("extra");

		assertThat(crono.getHappinessLevel(), is(90));
		assertThat(extraRobot.getHappinessLevel(), is(90));
	}

}
