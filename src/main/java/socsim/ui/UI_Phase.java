package socsim.ui;

import java.util.List;

import socsim.Match;
import socsim.phase.CompetitionPhase;

public abstract class UI_Phase<T extends CompetitionPhase> implements CompetitionPhase {
	
	protected boolean updateUI = true;
	protected T delegate;
	
	public abstract void refresh();
	
	public UI_Phase(T delegate) {
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
	public List<? extends Match> matches() {
		return delegate.matches();
	}
}
