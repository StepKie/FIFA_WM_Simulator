package socsim.phase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import socsim.BinaryTree;
import socsim.KOMatch;
import socsim.Team;
import socsim.ui.C_KOPhase;
import socsim.ui.FussballWM;

@Slf4j
public class KORound implements CompetitionPhase {
	
	@Setter C_KOPhase gui;
	
	// Entire bracket (finale is the root)
	@Getter private List<KOMatch> matches;
	
	public KORound(List<Team> teams, Instant date) {
		
		log.info("Creating KORound with {}", teams);
		List<KOMatch> firstRound = makeFirstRoundFromQualifiedTeams(teams, date);
		matches = BinaryTree.generateTree(firstRound, KOMatch.NEXT_ROUND).getAll(Comparator.comparing(KOMatch::getDate));
		log.info("Teams: {}, Matches created: {}", teams.size(), matches.size());
		
	}
	
	private static List<KOMatch> makeFirstRoundFromQualifiedTeams(List<Team> teams, Instant start) {
		int noOfTeams = teams.size();
		assert (noOfTeams % 2 == 0);
		var matches = new ArrayList<KOMatch>();
		for (int i = 0; i < noOfTeams; i += 2) {
			
			String matchName = KOMatch.getRoundName(noOfTeams) + " " + Integer.toString(i / 2 + 1);
			matches.add(new KOMatch(start, matchName, teams.get(i), teams.get(i + 1)));
			start = start.plus(1, ChronoUnit.HOURS);
		}
		return matches;
	}
	
	public KOMatch nextMatch() {
		return matches.stream().filter(m -> m.isSetup()).findFirst().orElse(null);
	}
	
	@Override
	public boolean isFinished() {
		return (nextMatch() == null);
	}
	
	@Override
	public KORound createNextRound() {
		// TODO Could split in multiple subbrackets, or implement jump() to use
		// subbrackets ...
		return null;
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
		
		gui.refresh();
		
	}
}
