package org.wecancodeit.virtualpetsfullstack;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class RobotDogTest {

	private static final String NAME = "Test";
	private static final String DESCRIPTION = "Description";
	private static final int OIL = 50;
	private static final int HAPPINESS = 50;
	private static final int CHARGE = 50;
	private static final int HEALTH = 50;

	private VirtualPetShelter shelter;
	private RobotDog underTest;

	@Before
	public void setup() {
		underTest = new RobotDog(shelter, NAME, DESCRIPTION, OIL, HAPPINESS, CHARGE, HEALTH);
	}

	@Test
	public void shouldKeepValuesInBounds() {
		shelter = new VirtualPetShelter(0);
		underTest = new RobotDog(shelter, NAME, DESCRIPTION, OIL, 110, 110, 110);
		RobotDog alsoUnderTest = new RobotDog(shelter, NAME, DESCRIPTION, -10, -10, -10, HEALTH);
		underTest.keepValuesInBounds();
		alsoUnderTest.keepValuesInBounds();

		assertThat(underTest.getHappinessLevel(), is(100));
		assertThat(underTest.getChargeLevel(), is(100));
		assertThat(underTest.getHealthLevel(), is(100));
		assertThat(alsoUnderTest.getOilLevel(), is(0));
		assertThat(alsoUnderTest.getHappinessLevel(), is(0));
		assertThat(alsoUnderTest.getChargeLevel(), is(0));

	}

	@Test
	public void getOiledShouldRaiseOrLowerLevels() {
		underTest.oil();

		assertThat(underTest.getOilLevel(), is(100));
		assertThat(underTest.getChargeLevel(), is(40));
	}

	@Test
	public void playShouldRaiseOrLowerLevels() {
		underTest.play();

		assertThat(underTest.getOilLevel(), is(30));
		assertThat(underTest.getHappinessLevel(), is(100));
		assertThat(underTest.getChargeLevel(), is(20));
	}

	@Test
	public void rechargeShouldRaiseOrLowerLevels() {
		underTest.recharge();

		assertThat(underTest.getHappinessLevel(), is(40));
		assertThat(underTest.getChargeLevel(), is(100));
	}

	@Test
	public void tickShouldRaiseOrLowerValuesBy10() {
		underTest.tick();

		assertThat(underTest.getOilLevel(), is(40));
		assertThat(underTest.getHappinessLevel(), is(40));
		assertThat(underTest.getChargeLevel(), is(40));
	}

	@Test
	public void goForWalkShouldRaiseOrLowerValues() {
		underTest.goForWalk();

		assertThat(underTest.getOilLevel(), is(40));
		assertThat(underTest.getHappinessLevel(), is(70));
		assertThat(underTest.getChargeLevel(), is(40));
	}

	@Test
	public void shouldRaiseHealthLevelBySpecifiedAmount() {
		underTest.raiseHealthLevel(10);

		assertThat(underTest.getHealthLevel(), is(60));
	}

	@Test
	public void shouldLowerHealthLevelBySpecifiedAmount() {
		underTest.lowerHealthLevel(10);

		assertThat(underTest.getHealthLevel(), is(40));
	}

	@Test
	public void shouldLowerHappinessLevelBySpecifiedAmount() {
		underTest.lowerHappinessLevel(10);

		assertThat(underTest.getHappinessLevel(), is(40));
	}

}
