package socsim;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.function.BinaryOperator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KOMatch extends Match {
	
	@Getter KOMatch previousHome;
	@Getter KOMatch previousGuest;
	
	public static final BinaryOperator<KOMatch> NEXT_ROUND = (m1, m2) -> new KOMatch(m2.date.plus(1, ChronoUnit.DAYS),
			"NONAME", m1, m2);
	
	@Getter private boolean verlängerung = false;
	
	public KOMatch(Instant date, String name, Team homeTeam, Team guestTeam) {
		super(date, name, homeTeam, guestTeam);
		log.info("Created new KO match {} - {}", homeTeam.getId(), guestTeam.getId());
	}
	
	public KOMatch(Instant date, String name, KOMatch previousHome, KOMatch previousGuest) {
		super(date, name, previousHome.getWinner(), previousGuest.getWinner());
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
	}
	
	public boolean isFirstRound() {
		return previousHome == null && previousGuest == null;
	}
	
	public static String getRoundName(int size) {
		switch (size) {
		case 16:
			return "Achtelfinale";
		case 8:
			return "Viertelfinale";
		case 4:
			return "Halbfinale";
		case 2:
			return "Finale";
		default:
			return "Unbekannte Runde ( " + size + " Teilnehmer)";
		}
		
	}
	
	@Override
	public String toString() {
		return super.toString() + (isVerlängerung() ? " (n.V.)" : "");
	}
}
