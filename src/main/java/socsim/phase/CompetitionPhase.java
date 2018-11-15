package socsim.phase;

public interface CompetitionPhase {
	void step();
	
	default CompetitionPhase jump() {
		while (!isFinished()) {
			step();
		}
		return isFinished() ? createNextRound() : this;
		
	};
	
	boolean isFinished();
	
	CompetitionPhase createNextRound();
}
