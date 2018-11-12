package socsim.stable;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import lombok.Getter;

public class KORound {
	@Getter List<Match> matches = new ArrayList<>();
	Instant startDate = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();

	public KORound(List<Team> teams, Instant date) {
		// TODO May use pairing strategy here
		for (int i = 0; i < teams.size(); i = i + 2) {
			matches.add(new Match(date, teams.get(i), teams.get(i + 1), true));
			date = (i % 2 == 0) ? date.plus(4, HOURS) : date.plus(20, HOURS);
		}
	}

	public Match nextMatch() {
		return matches.stream().filter(m -> !m.isFinished()).findFirst().orElse(null);
	}

	public boolean isFinished() {
		return (nextMatch() == null);
	}

	public KORound createNextRound() {
		assert (isFinished()) : "Round not finished!";
		Instant date = matches.get(matches.size() - 1).getDate().plus(1, DAYS);
		List<Team> nextRound = new ArrayList<>();

		for (int i = 0; i < matches.size(); i = i + 2) {
			nextRound.add(matches.get(i).getWinner());
			nextRound.add(matches.get(i + 1).getWinner());
		}
		// TODO Crosstable hack
//		Match vf1 = new Match(date, af1.getWinner(), af2.getWinner(), true);
//		Match vf2 = new Match(date, af5.getWinner(), af6.getWinner(), true);
//		Match vf3 = new Match(date, af3.getWinner(), af4.getWinner(), true);
//		Match vf4 = new Match(date, af7.getWinner(), af8.getWinner(), true);
		if (nextRound.size() == 8) {
			Collections.swap(nextRound, 2, 4);
			Collections.swap(nextRound, 3, 5);
		}

		return new KORound(nextRound, date);

	}

	public String getRoundName(int size) {
		switch (size) {
		case 16:
			return "Achtelfinale";
		case 8:
			return "Viertelfinale";
		case 4:
			return "Halbfinale";
		case 2:
			return "Finale";
		default:
			return "Unbekannte Runde ( " + size + " Teilnehmer)";
		}

	}

	public static List<Team> getAF(List<Group> gruppen) {
		return Arrays.asList(
				gruppen.get(0).getTeam(1), gruppen.get(1).getTeam(2), // A1-B2
				gruppen.get(2).getTeam(1), gruppen.get(3).getTeam(2), // C1-D2 
				gruppen.get(1).getTeam(1), gruppen.get(0).getTeam(2), // B1-A2
				gruppen.get(3).getTeam(1), gruppen.get(2).getTeam(2), // D1-C2
				gruppen.get(4).getTeam(1), gruppen.get(5).getTeam(2), // E1-F2
				gruppen.get(6).getTeam(1), gruppen.get(7).getTeam(2), // G1-H2
				gruppen.get(5).getTeam(1), gruppen.get(4).getTeam(2), // F1-E2
				gruppen.get(7).getTeam(1), gruppen.get(6).getTeam(2) // H1-G2
				);
	}
}
