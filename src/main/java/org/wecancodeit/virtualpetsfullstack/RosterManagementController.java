package org.wecancodeit.virtualpetsfullstack;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RosterManagementController {

	@Resource
	VirtualPetShelterRepository shelterRepo;

	@Resource
	VirtualPetRepository petRepo;

	@Resource
	CageRepository cageRepo;

	@RequestMapping(path = "/admit-new-pet/{name}/{description}/{type}", method = RequestMethod.POST)
	public String admitNewPet(@PathVariable String name, @PathVariable String description, @PathVariable String type) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);

		switch (type) {
		case "organic-dog":
			VirtualPet orgDog = petRepo.save(new OrganicDog(shelter, name, description));
			cageRepo.save(new Cage(shelter, orgDog));
			break;
		case "robot-dog":
			VirtualPet roboDog = petRepo.save(new RobotDog(shelter, name, description));
			cageRepo.save(new Cage(shelter, roboDog));
			break;
		case "organic-cat":
			petRepo.save(new OrganicCat(shelter, name, description));
			break;
		case "robot-cat":
			petRepo.save(new RobotCat(shelter, name, description));
			break;
		}

		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/adopt-out-pet/{id}", method = RequestMethod.DELETE)
	public String adoptOutPet(@PathVariable long id) {
		VirtualPet pet = petRepo.findOne(id);

		if (pet instanceof Cageable) {
			Cageable cagedPet = (Cageable) pet;
			long cageId = cagedPet.getCageId();
			cageRepo.delete(cageId);
		}

		petRepo.delete(id);
		return "redirect:/end-of-round";
	}
}
