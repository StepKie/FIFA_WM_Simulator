package socsim;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Table {
	
	public static Comparator<Row> POINTS = Comparator.comparingInt(Row::getPoints);
	public static Comparator<Row> GOAL_DIFFERENCE = Comparator.comparingInt(Row::getGoalsDifference);
	public static Comparator<Row> GOALS_SCORED = Comparator.comparingInt(Row::getGoalsFor);
	public static Comparator<Row> DEFAULT = Comparator
			.nullsFirst(POINTS.thenComparing(GOAL_DIFFERENCE).thenComparing(GOALS_SCORED).thenComparing(r -> r.getTeam().getElo()));
	public static Comparator<Row> WM_2018 = DEFAULT.thenComparingInt(r -> r.getTeam().getElo());
	
	@NonNull private Comparator<Row> comparator = WM_2018;
	private SortedSet<Team> teams = new TreeSet<Team>();
	@Getter private SortedSet<Match> matches = new TreeSet<Match>();
	
	public static Table buildTable(Collection<Team> teams, Collection<Match> matches, @NonNull Comparator<Row> comparator) {
		Table table = new Table();
		table.comparator = comparator;
		table.teams.addAll(teams);
		table.matches.addAll(matches);
		log.info("Matches in: {}, matches in table: {}", matches.size(), table.matches.size());
		
		matches.stream().flatMap(m -> Stream.of(m.getHomeTeam(), m.getGuestTeam())).forEach(table.teams::add);
		return table;
	}
	
	// TODO @Builder?
	public static Table buildTable(Collection<Match> matches, @NonNull Comparator<Row> comparator) {
		return buildTable(Collections.emptyList(), matches, comparator);
	}
	
	public List<Row> getRows() {
		SortedSet<Row> rows = new TreeSet<Row>(comparator.reversed());
		teams.forEach(t -> rows.add(new Row(t)));
		return new ArrayList<>(rows);
	}
	
	public void addMatch(Match match) {
		if (!teams.contains(match.getHomeTeam()))
			throw new IllegalArgumentException("Home team \"" + match.getHomeTeam() + "\" not found in team list");
		if (!teams.contains(match.getGuestTeam()))
			throw new IllegalArgumentException("Guest  team \"" + match.getGuestTeam() + "\" not found in team list");
		matches.add(match);
	}
	
	public void print(PrintStream out) {
		out.println("Pos  Team          Pld    W    D    L   GF   GA   GD   GW  Pts");
		out.println("---  ------------  ---  ---  ---  ---  ---  ---  ---  ---  ---");
		for (Row row : getRows())
			row.print(out);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Table [rows=\n");
		for (Row row : getRows())
			builder.append("  " + row + "\n");
		builder.append("]");
		return builder.toString();
	}
	
	@Data
	public class Row {
		@Getter private Team team;
		
		// Rank calculated from match statistics in main & sub-tables
		private int rank = 0;
		
		// Match statistics
		private int points = 0; // Pts (redundant: 3 * W + D)
		private int matchesPlayed = 0; // Pld
		private int matchesWon = 0; // W
		private int matchesDrawn = 0; // D
		private int matchesLost = 0; // L (redundant: Pld - W - D)
		private int goalsFor = 0; // GF
		private int goalsAgainst = 0; // GA
		private int goalsDifference = 0; // GD (redundant: GF - GA)
		private int goalsAway = 0; // GW (http://en.wikipedia.org/wiki/Away_goal)
		
		private Row(Team team) {
			this.team = team;
			refresh();
		}
		
		public void refresh() {
			points = 0;
			matchesPlayed = 0;
			matchesWon = 0;
			matchesDrawn = 0;
			matchesLost = 0;
			goalsFor = 0;
			goalsAgainst = 0;
			goalsDifference = 0;
			goalsAway = 0;
			
			for (Match match : Table.this.matches) {
				if (!match.isFinished() || (!team.equals(match.getHomeTeam()) && !team.equals(match.getGuestTeam())))
					continue;
				
				matchesPlayed++;
				if (team.equals(match.getHomeTeam())) {
					goalsFor += match.getHomeScore();
					goalsAgainst += match.getGuestScore();
					if (match.getHomeScore() > match.getGuestScore()) {
						matchesWon++;
						points += 3;
					} else if (match.getHomeScore() == match.getGuestScore()) {
						matchesDrawn++;
						points++;
					} else {
						matchesLost++;
					}
				} else {
					goalsFor += match.getGuestScore();
					goalsAway += match.getGuestScore();
					goalsAgainst += match.getHomeScore();
					if (match.getHomeScore() < match.getGuestScore()) {
						matchesWon++;
						points += 3;
					} else if (match.getHomeScore() == match.getGuestScore()) {
						matchesDrawn++;
						points++;
					} else {
						matchesLost++;
					}
				}
				goalsDifference = goalsFor - goalsAgainst;
			}
		}
		
		private void print(PrintStream out) {
			out.println(String.format("%3d  %-12s  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d", rank, team.getName(), matchesPlayed, matchesWon,
					matchesDrawn, matchesLost, goalsFor, goalsAgainst, goalsDifference, goalsAway, points));
		}
	}
}
