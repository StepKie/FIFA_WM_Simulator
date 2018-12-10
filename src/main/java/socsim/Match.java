package socsim;

import java.io.Serializable;
import java.time.Instant;
import java.util.Comparator;
import java.util.Random;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import socsim.io.Fussball_IO;

@Slf4j
public class Match implements Comparable<Match>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum State {
		SCHEDULED,
		SETUP,
		FINISHED
	}
	
	private int id;
	@Getter protected Instant date;
	@Getter protected String name;
	@Getter protected Team homeTeam;
	@Getter protected Team guestTeam;
	@Getter protected int homeScore;
	@Getter protected int guestScore;
	@Getter State state;
	
	public Match(Instant date, String name, Team homeTeam, Team guestTeam) {
		this.date = date;
		this.name = name;
		this.homeTeam = homeTeam;
		this.guestTeam = guestTeam;
	}
	
	public void play() {
		homeTeam = getHomeTeam();
		guestTeam = getGuestTeam();
		
		assert (isSetup() && !isFinished()) : "Attempted to play uninitialized match";
		int eloDiff = homeTeam.getElo() - guestTeam.getElo();
		double eloScaleFactor = 0.001 * eloDiff;
		
		IntegerDistribution dist = new PoissonDistribution(2.5 + eloScaleFactor);
		int totalGoals = dist.sample();
		
		double expectedScoreElo = 1 / (1 + (Math.pow(10, ((double) homeTeam.getElo() - (double) guestTeam.getElo()) / 400)));
		
		while (totalGoals > 0) {
			double rd = new Random().nextDouble();
			if (rd > expectedScoreElo) {
				homeScore++;
			} else {
				guestScore++;
			}
			totalGoals--;
		}
		state = State.FINISHED;
		
		log.info("Match finished: {}", toString());
		Fussball_IO.saveHistory(this);
	}
	
	public boolean isFinished() {
		return state == State.FINISHED;
	}
	
	public boolean isSetup() {
		return getHomeTeam() != null && getGuestTeam() != null && !isFinished();
	}
	
	@Override
	public String toString() {
		String ht = homeTeam == null ? "null" : homeTeam.getName();
		String gt = guestTeam == null ? "null" : guestTeam.getName();
		return String.format("%s %-15s %-15s - %-15s %2d : %2d", date, name, ht, gt, homeScore, guestScore);
	}
	
	public Team getWinner() {
		if (homeScore > guestScore)
			return homeTeam;
		if (guestScore > homeScore)
			return guestTeam;
		return null;
	}
	
	@Override
	public int compareTo(Match o) {
		return Comparator.nullsLast(Comparator.comparing(Match::getDate).thenComparing(Match::hashCode)).compare(this, o);
	}
}
