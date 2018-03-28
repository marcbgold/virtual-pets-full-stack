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
		shelter = shelterRepo.save(shelter);
		model.addAttribute("shelter", shelter);
		model.addAttribute("roster", petRepo.findAll());
		return "refresh-roster";
	}

	@RequestMapping(path = "/put-out-food", method = RequestMethod.PUT)
	public String putOutFood(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.putOutFood();
		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/put-out-water", method = RequestMethod.PUT)
	public String putOutWater(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.putOutWater();
		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/clean-all-cages", method = RequestMethod.PUT)
	public String cleanAllCages(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.cleanAllCages();
		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/scoop-out-litter-boxes", method = RequestMethod.PUT)
	public String scoopOutLitterBoxes(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.scoopOutLitterBoxes();
		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/walk-all-dogs", method = RequestMethod.PUT)
	public String walkAllDogs(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.walkAllDogs();
		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/oil-all-robots", method = RequestMethod.PUT)
	public String oilAllRobots(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.oilAllRobots();
		return "redirect:/end-of-round";
	}

	@RequestMapping(path = "/charge-all-robots", method = RequestMethod.PUT)
	public String chargeAllRobots(Model model) {
		VirtualPetShelter shelter = shelterRepo.findOne(1L);
		shelter.chargeAllRobots();
		return "redirect:/end-of-round";
	}
}
