package socsim;

import java.time.Instant;
import java.util.Random;

import lombok.Getter;

public class KOMatch extends Match {
	
	@Getter KOMatch previousHome;
	@Getter KOMatch previousGuest;
	
	@Getter private boolean ko = false;
	@Getter private boolean verlängerung = false;
	
	// Base goal scoring probabilities
	public static final double[] base_probabilities = new double[] { 0.21, 0.3, 0.27, 0.15, 0.05, 0.01, 0.01 };
	
	public KOMatch(Instant date, Team homeTeam, Team guestTeam) {
		super(date, homeTeam, guestTeam);
	}
	
	public KOMatch(Instant date, KOMatch previousHome, KOMatch previousGuest) {
		super(date, previousHome.getWinner(), previousGuest.getWinner());
	}
	
	@Override
	public Team getHomeTeam() {
		return isFirstRound() ? super.getHomeTeam() : previousHome.getWinner();
		
	}
	
	@Override
	public Team getGuestTeam() {
		return isFirstRound() ? super.getGuestTeam() : previousGuest.getWinner();
	}
	
	@Override
	public void play() {
		super.play();
		// TODO Remove hack
		if (homeScore == guestScore) {
			verlängerung = true;
			double rd = new Random().nextDouble();
			if (rd < 0.1) {
				homeScore += 2;
			} else if (rd < 0.5) {
				homeScore += 1;
			} else if (rd < 0.9) {
				guestScore += 1;
			} else {
				guestScore += 2;
			}
			
		}
		
		System.out.println(toString() + (isVerlängerung() ? " (n.V.)" : ""));
	}
	
	public boolean isFirstRound() {
		return previousHome == null && previousGuest == null;
	}
	
}
