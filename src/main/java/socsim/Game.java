package socsim;

import java.util.Random;

import socsim.stable.Team;

public class Game {

	public static Random rng = new Random();
	// Instant when;

	Team home;
	Team away;

	int goals_home;
	int goals_away;

	public void simulate() {
		goals_home = rng.nextInt(6);
		goals_away = rng.nextInt(6);
	}
}
