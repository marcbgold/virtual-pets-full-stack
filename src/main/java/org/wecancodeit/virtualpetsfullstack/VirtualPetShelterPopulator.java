package org.wecancodeit.virtualpetsfullstack;

import java.util.Date;

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
		Category rpg = shelterRepo.save(new Category("RPG", "Pretend to be some dudes, read lots of dialogue, and go save the world or something."));
		Category platformer = shelterRepo.save(new Category("Platformer", "Jump on lots of platforms, and maybe stomp on bad guys or solve some puzzles."));

		Tag snes = cageRepo.save(new Tag("SNES", "Game originally released on Super Nintendo"));
		Tag psx = cageRepo.save(new Tag("PSX", "Game originally released on Sony Playstation"));
		Tag pc = cageRepo.save(new Tag("PC", "Game originally released on PC"));
		Tag twoD = cageRepo.save(new Tag("2D", "Game has 2D graphics"));
		Tag threeD = cageRepo.save(new Tag("3D", "Game has 3D graphics"));
		Tag puzzle = cageRepo.save(new Tag("Puzzle", "Game involves puzzle solving"));
		Tag storyDriven = cageRepo.save(new Tag("Story-Driven", "Game centers around a continuous story"));

		petRepo.save(new Review(rpg, "Chrono Trigger", new Date(), 1995, "Time-hopping shenanigans.", "./images/chrono-trigger.jpg", "Mute, spiky-haired kid",
				"Saves the world from space monster", "By messing with time", snes, twoD, storyDriven));
		petRepo.save(new Review(platformer, "Crash Bandicoot", new Date(), 1996, "Rodent thing with pants vs. midget mad scientist.", "./images/crash-bandicoot.jpg",
				"Running towards the screen", "Falling into unseen holes", "The gamedevs hate you", psx, threeD));
		petRepo.save(new Review(platformer, "Portal 2", new Date(), 2011, "Free cake.  So delicious and moist.", "./images/portal.jpg", "Here's the test result:",
				"\"You're a horrible person.\"", "See? That's what it says.", pc, threeD, puzzle, storyDriven));
		petRepo.save(new Review(platformer, "Super Mario World", new Date(), 1990, "Turtle boss must die. Again.", "./images/super-mario-world.jpg", "Fat Italian guy",
				"Eats shrooms and murders turtles", "Bowser never learns", snes, twoD));
		petRepo.save(new Review(rpg, "Undertale", new Date(), 2015, "Kill everybody... or don't.  Up to you.", "./images/undertale.jpg", "Here, I made you this",
				"Cinnamon-butterscotch pie", "Now be good, won't you?", pc, twoD, puzzle, storyDriven));
	}

}
