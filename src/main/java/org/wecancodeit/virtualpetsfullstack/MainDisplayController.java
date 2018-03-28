package org.wecancodeit.virtualpetsfullstack;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainDisplayController {

	@Resource
	VirtualPetShelterRepository shelterRepo;

	@Resource
	VirtualPetRepository petRepo;

	@Resource
	CageRepository cageRepo;

	@RequestMapping("/")
	public String showStartPage() {
		return "start";
	}

	@RequestMapping(path = "/main/{playerName}", method = RequestMethod.GET)
	public String showMainGamePage(Model model, @PathVariable String playerName) {
		model.addAttribute("playerName", playerName);
		model.addAttribute("shelter", shelterRepo.findOne(1L));
		model.addAttribute("roster", petRepo.findAll());
		return "main";
	}

}
