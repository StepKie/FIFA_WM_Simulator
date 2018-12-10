package socsim.phase;

import java.util.List;

import socsim.Match;

public abstract class UI_Phase implements CompetitionPhase {
	
	protected boolean updateUI = true;
	protected CompetitionPhase delegate;
	
	public abstract void refresh();
	
	public UI_Phase(CompetitionPhase delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public CompetitionPhase jump() {
		updateUI = false;
		CompetitionPhase nextPhase = CompetitionPhase.super.jump();
		updateUI = true;
		refresh();
		return nextPhase;
	}
	
	@Override
	public void step() {
		CompetitionPhase.super.step();
		if (updateUI)
			refresh();
	}
	
	@Override
	public abstract UI_Phase createNextRound();
	
	@Override
	public List<? extends Match> matches() {
		// TODO Auto-generated method stub
		return delegate.matches();
	}
}
