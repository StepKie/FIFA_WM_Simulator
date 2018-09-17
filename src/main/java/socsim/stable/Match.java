package socsim.stable;

import java.util.Calendar;

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
