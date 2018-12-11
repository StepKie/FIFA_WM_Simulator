package socsim.phase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import socsim.BinaryTree;
import socsim.KOMatch;
import socsim.Team;

@Slf4j
public class KORound implements CompetitionPhase {
	
	// Entire bracket (finale is the root)
	private List<KOMatch> matches;
	
	public KORound(List<Team> teams, Instant date) {
		
		teams.forEach(t -> log.info("Qualified: {}", t.getName()));
		List<KOMatch> firstRound = makeFirstRoundFromQualifiedTeams(teams, date);
		matches = BinaryTree.generateTree(firstRound, KOMatch.NEXT_ROUND).getAll(Comparator.comparing(KOMatch::getDate));
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
	
	@Override
	public CompetitionPhase createNextRound() {
		// TODO Could split in multiple subbrackets, or implement jump() to use
		// subbrackets ...
		return null;
	}
	
	@Override
	public List<KOMatch> matches() {
		return matches;
	}
}
