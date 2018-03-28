package org.wecancodeit.virtualpetsfullstack;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainDisplayController {

	@Resource
	VirtualPetShelterRepository shelterRepo;

	@Resource
	VirtualPetRepository petRepo;

	@Resource
	CageRepository cageRepo;

	@RequestMapping("/main")
	public String showMainGamePage(Model model) {
		model.addAttribute("shelter", shelterRepo.findOne(1L));
		model.addAttribute("roster", petRepo.findAll());
		return "main";
	}

}
