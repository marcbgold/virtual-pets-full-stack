package org.wecancodeit.virtualpetsfullstack;

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

	// @Test
	// public void shouldEstablishManyReviewsToOneCategoryRelationship() {
	// category = categoryRepo.save(category);
	// long categoryId = category.getId();
	//
	// firstReview = reviewRepo.save(firstReview);
	// secondReview = reviewRepo.save(secondReview);
	//
	// entityManager.flush();
	// entityManager.clear();
	//
	// category = categoryRepo.findOne(categoryId);
	// assertThat(category.getReviews(), containsInAnyOrder(firstReview,
	// secondReview));
	// }
	//
	// @Test
	// public void shouldSaveAndLoadTag() {
	// Tag underTest = tagRepo.save(new Tag("name", "description"));
	// long TagId = underTest.getId();
	//
	// entityManager.flush();
	// entityManager.clear();
	//
	// underTest = tagRepo.findOne(TagId);
	// assertThat(underTest.getName(), is("name"));
	// }
	//
	// @Test
	// public void shouldEstablishManyTagsToOneReviewRelationship() {
	// category = categoryRepo.save(category);
	// firstTag = tagRepo.save(new Tag("first", DESC));
	// secondTag = tagRepo.save(new Tag("second", DESC));
	// Review underTest = reviewRepo.save(new Review(category, "test", REV_DATE,
	// YEAR_PUB, DESC, IMG_URL, HAIKU_LINE1, HAIKU_LINE2, HAIKU_LINE3, firstTag,
	// secondTag));
	// long reviewId = underTest.getId();
	//
	// entityManager.flush();
	// entityManager.clear();
	//
	// underTest = reviewRepo.findOne(reviewId);
	// assertThat(underTest.getTags(), containsInAnyOrder(firstTag, secondTag));
	// }
	//
	// @Test
	// public void shouldEstablishManyReviewsToOneTagRelationship() {
	// category = categoryRepo.save(category);
	// Tag underTest = tagRepo.save(new Tag("test", DESC));
	// firstReview = reviewRepo.save(new Review(category, "first review", REV_DATE,
	// YEAR_PUB, DESC, IMG_URL, HAIKU_LINE1, HAIKU_LINE2, HAIKU_LINE3, underTest));
	// secondReview = reviewRepo.save(new Review(category, "second review",
	// REV_DATE, YEAR_PUB, DESC, IMG_URL, HAIKU_LINE1, HAIKU_LINE2, HAIKU_LINE3,
	// underTest));
	// long tagId = underTest.getId();
	//
	// entityManager.flush();
	// entityManager.clear();
	//
	// underTest = tagRepo.findOne(tagId);
	// assertThat(underTest.getReviews(), containsInAnyOrder(firstReview,
	// secondReview));
	// }
	//
	// @Test
	// public void shouldGetTagSize() {
	// category = categoryRepo.save(category);
	// Tag underTest = tagRepo.save(new Tag("test", DESC));
	// firstReview = reviewRepo.save(new Review(category, "first review", REV_DATE,
	// YEAR_PUB, DESC, IMG_URL, HAIKU_LINE1, HAIKU_LINE2, HAIKU_LINE3, underTest));
	// secondReview = reviewRepo.save(new Review(category, "second review",
	// REV_DATE, YEAR_PUB, DESC, IMG_URL, HAIKU_LINE1, HAIKU_LINE2, HAIKU_LINE3,
	// underTest));
	// long tagId = underTest.getId();
	//
	// entityManager.flush();
	// entityManager.clear();
	//
	// underTest = tagRepo.findOne(tagId);
	// assertThat(underTest.getTagSize(), is("medium-tag"));
	// }
}