package org.wecancodeit.virtualpetsfullstack;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
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
public class VirtualPetsFullStackJpaMappingsTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private VirtualPetShelterRepository shelterRepo;

	@Resource
	private VirtualPetRepository petRepo;

	@Resource
	private CageRepository cageRepo;

	private static final String DESC = "description";

	VirtualPetShelter shelter;
	OrganicDog firstDog;
	RobotDog secondDog;
	Cage firstCage;
	Cage secondCage;

	@Before
	public void setup() {
		shelter = new VirtualPetShelter();
		firstDog = new OrganicDog(shelter, "first", DESC);
		secondDog = new RobotDog(shelter, "second", DESC);
		firstCage = new Cage(shelter, firstDog);
		secondCage = new Cage(shelter, secondDog);
	}

	@Test
	public void shouldSaveAndLoadPet() {
		shelter = shelterRepo.save(shelter);
		firstDog = petRepo.save(firstDog);
		long dogId = firstDog.getId();

		entityManager.flush();
		entityManager.clear();

		firstDog = (OrganicDog) petRepo.findOne(dogId);
		assertThat(firstDog.getName(), is("first"));
	}

	@Test
	public void shouldEstablishManyPetsToOneShelterRelationship() {
		shelter = shelterRepo.save(shelter);
		long shelterId = shelter.getId();

		firstDog = petRepo.save(firstDog);
		secondDog = petRepo.save(secondDog);

		entityManager.flush();
		entityManager.clear();

		shelter = shelterRepo.findOne(shelterId);
		assertThat(shelter.getAllPets(), containsInAnyOrder(firstDog, secondDog));
	}

	@Test
	public void shouldSaveAndLoadCageWithMatchingPet() {
		shelter = shelterRepo.save(shelter);
		firstDog = petRepo.save(firstDog);
		firstCage = cageRepo.save(firstCage);
		long cageId = firstCage.getId();

		entityManager.flush();
		entityManager.clear();

		firstCage = cageRepo.findOne(cageId);
		assertThat(firstCage.getPet(), is(firstDog));
	}

	// @Test
	// public void shouldHaveSameIdForPetAndMatchingCage() {
	// shelter = shelterRepo.save(shelter);
	// firstDog = petRepo.save(firstDog);
	// firstCage = cageRepo.save(firstCage);
	// long petId = firstDog.getId();
	// long cageId = firstCage.getId();
	//
	// entityManager.flush();
	// entityManager.clear();
	//
	// assertThat(petId, is(cageId));
	// }

	@Test
	public void shouldEstablishManyCagesToOneShelterRelationship() {
		shelter = shelterRepo.save(shelter);
		firstDog = petRepo.save(firstDog);
		secondDog = petRepo.save(secondDog);
		firstCage = cageRepo.save(firstCage);
		secondCage = cageRepo.save(secondCage);
		long shelterId = shelter.getId();

		entityManager.flush();
		entityManager.clear();

		shelter = shelterRepo.findOne(shelterId);
		assertThat(shelter.getAllCages(), containsInAnyOrder(firstCage, secondCage));
	}
}