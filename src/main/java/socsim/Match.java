package socsim;

import java.time.Instant;
import java.util.Comparator;
import java.util.Random;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Match implements Comparable<Match> {
	@Getter @NonNull protected Instant date;
	@Getter protected Team homeTeam;
	@Getter protected Team guestTeam;
	@Getter protected int homeScore = -1;
	@Getter protected int guestScore = -1;
	
	@Getter protected boolean finished = false;
	
	// Base goal scoring probabilities
	public static final double[] base_probabilities = new double[] { 0.21, 0.3, 0.27, 0.15, 0.05, 0.01, 0.01 };
	
	public Match(Instant date, Team homeTeam, Team guestTeam) {
		this(date);
		this.homeTeam = homeTeam;
		this.guestTeam = guestTeam;
	}
	
	public Match(Instant date, Team homeTeam, Team guestTeam, int homeScore, int guestScore) {
		this(date, homeTeam, guestTeam);
		this.homeScore = homeScore;
		this.guestScore = guestScore;
		finished = true;
	}
	
	public void play() {
		assert (homeTeam != null && guestTeam != null && !finished) : "Attempted to play uninitialized match";
		int eloDiff = homeTeam.getElo() - guestTeam.getElo();
		double eloScaleFactor = 0.001 * eloDiff;
		
		IntegerDistribution dist = new PoissonDistribution(2.5 + eloScaleFactor);
		int totalGoals = dist.sample();
		// Original formula - produces high values, because it is applied to each goal.
		// Playing around below, fix eventually ...
		// double expectedScoreElo = 1 / (1 + (Math.pow(10, ((double) homeTeam.getElo()
		// - (double) guestTeam.getElo()) /
		// 400)));
		
		double expectedScoreElo = 1 / (1 + (Math.pow(10, ((double) homeTeam.getElo() - (double) guestTeam.getElo()) / 400)));
		
		homeScore = 0;
		guestScore = 0;
		while (totalGoals > 0) {
			double rd = new Random().nextDouble();
			if (rd > expectedScoreElo) {
				homeScore++;
			} else {
				guestScore++;
			}
			totalGoals--;
		}
		
		finished = true;
	}
	
	@Override
	public String toString() {
		return String.format("  %s    %-20s  -  %-20s    %2d : %2d", date.toString(), homeTeam, guestTeam, homeScore, guestScore);
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
		return Comparator.comparing(Match::getDate) //
				.thenComparing(m -> m.getHomeTeam().getId()) //
				.thenComparing(m -> m.getHomeTeam().getId()) //
				.compare(this, o);
	}
}
