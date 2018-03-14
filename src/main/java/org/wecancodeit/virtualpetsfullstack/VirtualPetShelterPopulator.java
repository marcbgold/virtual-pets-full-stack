package org.wecancodeit.virtualpetsfullstack;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VirtualPetShelterPopulator implements CommandLineRunner {

	@Resource
	private VirtualPetShelterRepository shelterRepo;

	@Resource
	private VirtualPetRepository petRepo;

	@Resource
	private CageRepository cageRepo;

	@Override
	public void run(String... args) throws Exception {
		VirtualPetShelter shelter = shelterRepo.save(new VirtualPetShelter());

		String organicDogName = "Crono";
		String organicDogDescription = "Reddish-orange male mutt with short, spiky fur.  Very friendly and athletic, but you've never heard him bark.  Ever.";
		OrganicDog defaultOrganicDog = petRepo.save(new OrganicDog(shelter, organicDogName, organicDogDescription));

		String organicCatName = "Lara";
		String organicCatDescription = "Black and gray female tabby.  Extra nimble and slinky.  Inists upon trying to get into every enclosed space she can find.";
		petRepo.save(new OrganicCat(shelter, organicCatName, organicCatDescription));

		String robotCatName = "GLaDOS";
		String robotCatDescription = "Mostly white with large, yellow eyes.  Seems to always be mocking you.  Possibly wants to kill you.  Hates potatoes.";
		petRepo.save(new RobotCat(shelter, robotCatName, robotCatDescription));

		cageRepo.save(new Cage(shelter, defaultOrganicDog));
	}

}
