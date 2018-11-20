package socsim.phase;

public interface CompetitionPhase {
	// assert (isFinished == false...)
	void step();
	
	default CompetitionPhase jump() {
		while (!isFinished()) {
			step();
		}
		return createNextRound();
		
	};
	
	boolean isFinished();
	
	// TODO Similar structure in implementation classes -> also default?
	// assert (isFinished)
	// update UI
	// determineDate
	CompetitionPhase createNextRound();
}
