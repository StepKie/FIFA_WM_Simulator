package socsim.phase;

public abstract class UI_Phase implements CompetitionPhase {
	
	protected boolean updateUI = true;
	
	@Override
	public CompetitionPhase jump() {
		updateUI = false;
		while (!isFinished()) {
			step();
		}
		updateUI = true;
		return createNextRound();
		
	};
}
