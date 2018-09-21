package socsim.stable;

import java.util.Calendar;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;

import lombok.Getter;

public class Match implements Comparable<Match> {
	@Getter private String id;
	@Getter private Calendar date;
	@Getter private Team homeTeam;
	@Getter private Team guestTeam;
	@Getter private int homeScore = -1;
	@Getter private int guestScore = -1;

	public Match(Calendar date, Team homeTeam, Team guestTeam, int homeScore, int guestScore) {
		this.date = date;
		this.homeTeam = homeTeam;
		this.guestTeam = guestTeam;
		this.homeScore = homeScore;
		this.guestScore = guestScore;
		this.id = homeTeam.getId() + "-" + guestTeam.getId();
	}

	public Match(Calendar date, Team homeTeam, Team guestTeam, boolean ko) {
		this(date, homeTeam, guestTeam, -1, -1);
		int[] goals = new int[] { 0, 1, 2, 3, 4, 5, 6 };
		// TODO Adjust for team elos
		double[] probabilities = new double[] { 0.21, 0.3, 0.27, 0.15, 0.05, 0.01, 0.01 };
		IntegerDistribution id = new EnumeratedIntegerDistribution(goals, probabilities);
		homeScore = id.sample();
		guestScore = id.sample();
		// TODO Remove hack
		if (ko && homeScore == guestScore) {
			homeScore++;

		}
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
		return String.format("  %tF %tR    %-20s  -  %-20s    %2d : %2d", date, date, homeTeam, guestTeam, homeScore,
				guestScore);
	}

	public Team getWinner() {

		if (homeScore > guestScore)
			return homeTeam;
		if (guestScore > homeScore)
			return guestTeam;
		return null;
	}
}
