package socsim.phase;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import socsim.KOMatch;
import socsim.Team;
import socsim.ui.C_KOMatch;
import socsim.ui.C_KOPhase;
import socsim.ui.FussballWM;

@Slf4j
public class KORound implements CompetitionPhase {
	
	Map<KOMatch, C_KOMatch> map;
	C_KOPhase gui;
	
	@Getter List<KOMatch> matches = new ArrayList<>();
	Instant startDate;
	
	public KORound(List<Team> teams, Instant date, C_KOPhase gui) {
		this.gui = gui;
		this.startDate = date;
		KOMatch tree = generateTree(4);
		// TODO May use pairing strategy here
		for (int i = 0; i < teams.size(); i = i + 2) {
			matches.add(new KOMatch(date, teams.get(i), teams.get(i + 1)));
			date = (i % 2 == 0) ? date.plus(4, HOURS) : date.plus(20, HOURS);
		}
	}
	
	private static KOMatch generateTree(int depth) {
		return (depth <= 0) ? null : new KOMatch(null, generateTree(depth - 1), generateTree(depth - 1));
	}
	
	public KOMatch nextMatch() {
		return matches.stream().filter(m -> !m.isFinished()).findFirst().orElse(null);
	}
	
	@Override
	public boolean isFinished() {
		return (nextMatch() == null);
	}
	
	@Override
	public KORound createNextRound() {
		if (matches.size() == 1) {
			return null;
		}
		
		assert (isFinished()) : "Round not finished!";
		Instant date = matches.get(matches.size() - 1).getDate().plus(1, DAYS);
		List<Team> nextRound = new ArrayList<>();
		
		for (int i = 0; i < matches.size(); i = i + 2) {
			nextRound.add(matches.get(i).getWinner());
			nextRound.add(matches.get(i + 1).getWinner());
		}
		// TODO Crosstable hack
		// Match vf1 = new Match(date, af1.getWinner(), af2.getWinner(), true);
		// Match vf2 = new Match(date, af5.getWinner(), af6.getWinner(), true);
		// Match vf3 = new Match(date, af3.getWinner(), af4.getWinner(), true);
		// Match vf4 = new Match(date, af7.getWinner(), af8.getWinner(), true);
		if (nextRound.size() == 8) {
			Collections.swap(nextRound, 2, 4);
			Collections.swap(nextRound, 3, 5);
		}
		
		return new KORound(nextRound, date, gui);
		
	}
	
	public static String getRoundName(int size) {
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
	
	@Override
	public void step() {
		if (isFinished()) {
			gui.getShell().close();
			new FussballWM().open();
			return;
		}
		KOMatch upNext = nextMatch();
		upNext.play();
		log.info("Game: {}", upNext.toString());
		map.get(upNext).updateMatch(upNext);
	}
}
