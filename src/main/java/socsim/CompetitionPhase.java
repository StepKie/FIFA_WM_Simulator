package socsim;

public interface CompetitionPhase {
	void step();
	
	CompetitionPhase jump();
	
	boolean isFinished();
	
	CompetitionPhase createNextRound();
}
