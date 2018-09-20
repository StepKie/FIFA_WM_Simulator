package socsim.stable;

import java.util.Calendar;
import java.util.Random;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

public class Match implements Comparable<Match> {
	private String id;
	private Calendar date;
	private Team homeTeam;
	private Team guestTeam;
	private int homeScore = -1;
	private int guestScore = -1;

	public Match(Calendar date, Team homeTeam, Team guestTeam, int homeScore, int guestScore) {
		this.date = date;
		this.homeTeam = homeTeam;
		this.guestTeam = guestTeam;
		this.homeScore = homeScore;
		this.guestScore = guestScore;
		this.id = homeTeam.getId() + "-" + guestTeam.getId();
	}

	public Match(Calendar date, Team homeTeam, Team guestTeam) {
		this(date, homeTeam, guestTeam, -1, -1);
		int[] goals = new int[] { 0, 1, 2, 3, 4, 5, 6 };
		// TODO Adjust for team elos
		double[] probabilities = new double[] { 0.21, 0.3, 0.27, 0.15, 0.05, 0.01, 0.01 };
		IntegerDistribution id = new EnumeratedIntegerDistribution(goals, probabilities);
		homeScore = id.sample();
		guestScore = new Random().nextInt(5);
	}

	@Override
	public int compareTo(Match match) {
		int result = getDate().compareTo(match.getDate());
		if (result != 0)
			return result;
		return getId().compareTo(match.getId());
	}

	@Override
	public String toString() {
		return getId();
	}

	String getId() {
		return id;
	}

	Calendar getDate() {
		return date;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public Team getGuestTeam() {
		return guestTeam;
	}

	int getHomeScore() {
		return homeScore;
	}

	int getGuestScore() {
		return guestScore;
	}
}
