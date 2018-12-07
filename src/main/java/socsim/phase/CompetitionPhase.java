package socsim.phase;

public interface CompetitionPhase {
	
	// assert (isFinished == false...)
	void step();
	
	CompetitionPhase jump();
	
	boolean isFinished();
	
	// TODO Similar structure in implementation classes -> also default?
	// assert (isFinished)
	// update UI
	// determineDate
	CompetitionPhase createNextRound();
}
