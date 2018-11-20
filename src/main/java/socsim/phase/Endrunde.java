package socsim.phase;

import static java.time.temporal.ChronoUnit.HOURS;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import socsim.KOMatch;
import socsim.Match;
import socsim.Team;
import socsim.ui.C_KOPhase;

@Slf4j
public class Endrunde implements CompetitionPhase {
	
	List<KORound> runden;
	C_KOPhase gui;
	
	@Getter List<Match> matches = new ArrayList<>();
	Instant startDate;
	
	public Endrunde(List<Team> teams, Instant date, C_KOPhase gui) {
		this.gui = gui;
		this.startDate = date;
		// TODO May use pairing strategy here
		for (int i = 0; i < teams.size(); i = i + 2) {
			matches.add(new KOMatch(date, teams.get(i), teams.get(i + 1)));
			date = (i % 2 == 0) ? date.plus(4, HOURS) : date.plus(20, HOURS);
		}
	}
	
	@Override
	public void step() {
		runden.iterator().next();
		
	}
	
	@Override
	public boolean isFinished() {
		return runden.stream().allMatch(KORound::isFinished);
	}
	
	@Override
	public CompetitionPhase createNextRound() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
