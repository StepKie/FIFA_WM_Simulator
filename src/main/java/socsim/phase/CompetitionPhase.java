package socsim.phase;

import java.util.Comparator;
import java.util.List;

import socsim.Match;
import socsim.io.Fussball_IO;

public interface CompetitionPhase {
	
	// assert (isFinished == false...)
	default void step() {
		if (isFinished())
			return;
		Match next = nextMatch();
		next.play();
		Fussball_IO.saveHistory(next);
		
	};
	
	default CompetitionPhase jump() {
		while (!isFinished()) {
			step();
		}
		return createNextRound();
	};
	
	default boolean isFinished() {
		return nextMatch() == null;
	};
	
	// TODO Similar structure in implementation classes -> also default?
	// assert (isFinished)
	// update UI
	// determineDate
	CompetitionPhase createNextRound();
	
	default Match nextMatch() {
		return matches().stream() //
				.filter(m -> !m.isFinished()) //
				.sorted() //
				.findFirst().orElse(null);
	}
	
	default Match lastMatch() {
		return matches().stream() //
				.filter(Match::isFinished) //
				.sorted(Comparator.comparing(Match::getDate).reversed()) //
				.findFirst().orElse(null);
	};
	
	List<? extends Match> matches();
}
