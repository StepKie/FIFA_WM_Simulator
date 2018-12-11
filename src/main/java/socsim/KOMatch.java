package socsim;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.function.BinaryOperator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KOMatch extends Match {
	
	private static final long serialVersionUID = 1L;
	
	@Getter private int round;
	@Getter private int noInRound;
	@Getter KOMatch previousHome;
	@Getter KOMatch previousGuest;
	
	public static final BinaryOperator<KOMatch> NEXT_ROUND = (m1, m2) -> new KOMatch(m2.date.plus(1, ChronoUnit.DAYS), m2.round + 1, m2.noInRound / 2,
			m1, m2);
	
	@Getter private boolean verlängerung = false;
	
	public KOMatch(Instant date, int round, int noInRound, Team homeTeam, Team guestTeam) {
		super(date, getRoundName(round, noInRound), homeTeam, guestTeam);
		this.round = round;
		this.noInRound = noInRound;
		log.info("Created new KO match {} - {}", toString());
	}
	
	public KOMatch(Instant date, int round, int noInRound, KOMatch previousHome, KOMatch previousGuest) {
		super(date, getRoundName(round, noInRound), previousHome.getWinner(), previousGuest.getWinner());
		this.round = round;
		this.noInRound = noInRound;
		this.previousHome = previousHome;
		this.previousGuest = previousGuest;
	}
	
	public KOMatch(String name, Team homeTeam, Team guestTeam) {
		super(null, name, homeTeam, guestTeam);
		log.info("Created new KO match {} - {}", homeTeam.getId(), guestTeam.getId());
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
		log.info(this.toString());
	}
	
	public boolean isFirstRound() {
		return previousHome == null && previousGuest == null;
	}
	
	public static String getRoundName(int round, int noInRound) {
		String name = "NONAME";
		if (round == 1)
			name = "Achtelfinale";
		if (round == 2)
			name = "Viertelfinale";
		if (round == 3)
			name = "Halbfinale";
		if (round == 4)
			return name = "Finale";
		
		return name.concat(" " + noInRound);
	}
	
	@Override
	public String toString() {
		return super.toString() + (isVerlängerung() ? " (n.V.)" : "");
	}
}
