package org.wecancodeit.virtualpetsfullstack;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GameUpdatesController {

	@Resource
	VirtualPetShelterRepository shelterRepo;

	@Resource
	VirtualPetRepository petRepo;

	@Resource
	CageRepository cageRepo;

	@RequestMapping(path = "/end-of-round", method = RequestMethod.PUT)
	public String endOfRound(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.petsTakeCareOfSelves();
		shelter.checkForHealthProblems();
		return "refresh-roster";
	}

	@RequestMapping(path = "/put-out-food", method = RequestMethod.PUT)
	public String putOutFood(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.putOutFood();
		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/put-out-food", method = RequestMethod.PUT)
	public String putOutWater(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.putOutWater();
		return "redirect:/end-of-round";
	}

}
