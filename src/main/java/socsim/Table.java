package socsim;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Table {
	
	public static Comparator<Row> POINTS = Comparator.comparingInt(Row::getPoints);
	public static Comparator<Row> GOAL_DIFFERENCE = Comparator.comparingInt(Row::getPoints);
	public static Comparator<Row> GOALS_SCORED = Comparator.comparingInt(Row::getGoalsFor);
	// public static Comparator<Row> DIRECT_COMPARISON = (r1, r2) ->
	// GOAL_DIFFERENCE.compare(r1.getOuterTable().filter(t), r2);
	public static Comparator<Row> DEFAULT = Comparator.nullsFirst(POINTS.thenComparing(GOAL_DIFFERENCE).thenComparing(GOALS_SCORED));
	public static Comparator<Row> WM_2018 = DEFAULT.thenComparingInt(r -> r.getTeam().getElo());
	
	private List<Row> rows = new ArrayList<Row>();
	@NonNull private Comparator<Row> comparator;
	private SortedSet<Team> teams = new TreeSet<Team>();
	private SortedSet<Match> matches = new TreeSet<Match>();
	private boolean showSubTables = false;
	private Set<String> dumpedSubTables = new LinkedHashSet<String>();
	private String indent = "";
	
	public Table(Comparator<Row> comparator, Table parent) {
		this(comparator);
		indent = parent.indent + "    ";
	}
	
	public Table(Stream<Team> teams, Stream<Match> matches, Comparator<Row> comparator) {
		this(comparator);
		teams.forEach(this.teams::add);
		matches.forEach(this.matches::add);
	}
	
	public void addTeam(Team team) {
		teams.add(team);
		if (getRow(team) == null)
			rows.add(new Row(team));
	}
	
	public void addMatch(Match match) {
		if (!teams.contains(match.getHomeTeam()))
			throw new IllegalArgumentException("Home team \"" + match.getHomeTeam() + "\" not found in team list");
		if (!teams.contains(match.getGuestTeam()))
			throw new IllegalArgumentException("Guest  team \"" + match.getGuestTeam() + "\" not found in team list");
		matches.add(match);
	}
	
	public void print(PrintStream out, boolean showSubTables) {
		this.showSubTables = showSubTables;
		refresh();
		for (String dumpedSubTable : dumpedSubTables)
			out.println(dumpedSubTable);
		out.println(indent + "Pos  Team          Pld    W    D    L   GF   GA   GD   GW  Pts");
		out.println(indent + "---  ------------  ---  ---  ---  ---  ---  ---  ---  ---  ---");
		for (Row row : rows)
			row.print(out, indent);
	}
	
	public void refresh() {
		for (Row row : rows)
			row.refresh();
		dumpedSubTables.clear();
		// Update table ranking. Make sure to maintain alphabetical order by team ID for
		// mathematically equal rows.
		// Otherwise there might be duplicate subtables in member dumpedSubTables, just
		// with identical teams in
		// different order.
		Collections.sort(rows, Table.WM_2018.reversed());
		Row previousRow = null;
		int currentRank = 0;
		int nextRank = 0;
		for (Row row : rows) {
			nextRank++;
			if (comparator.compare(previousRow, row) < 0)
				currentRank = nextRank;
			row.rank = currentRank;
			previousRow = row;
		}
	}
	
	public Table filter(Predicate<Team> t) {
		return new Table(teams.stream().filter(t), matches.stream().filter(m -> t.test(m.getGuestTeam()) || t.test(m.getHomeTeam())), comparator);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Table [rows=\n");
		for (Row row : rows)
			builder.append("  " + row + "\n");
		builder.append("]");
		return builder.toString();
	}
	
	public Row getRow(Team team) {
		for (Row row : rows) {
			if (row.team.equals(team))
				return row;
		}
		return null;
	}
	
	public List<Row> getRows() {
		return rows;
	}
	
	public SortedSet<Match> getMatches() {
		return matches;
	}
	
	public boolean isShowSubTables() {
		return showSubTables;
	}
	
	public void addDumpedSubTable(String dumpedSubTable) {
		dumpedSubTables.add(dumpedSubTable);
	}
	
	public class Row {
		@Getter private Team team;
		
		// Rank calculated from match statistics in main & sub-tables
		private int rank = 0;
		
		// Match statistics
		@Getter private int points = 0; // Pts (redundant: 3 * W + D)
		@Getter private int matchesPlayed = 0; // Pld
		@Getter private int matchesWon = 0; // W
		@Getter private int matchesDrawn = 0; // D
		@Getter private int matchesLost = 0; // L (redundant: Pld - W - D)
		@Getter private int goalsFor = 0; // GF
		@Getter private int goalsAgainst = 0; // GA
		@Getter private int goalsDifference = 0; // GD (redundant: GF - GA)
		@Getter private int goalsAway = 0; // GW (http://en.wikipedia.org/wiki/Away_goal)
		
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
				if (match.getHomeScore() < 0 || match.getGuestScore() < 0
						|| (!team.equals(match.getHomeTeam()) && !team.equals(match.getGuestTeam())))
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
		
		private void print(PrintStream out, String indent) {
			out.println(String.format(indent + "%3d  %-12s  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d", rank, team.getName(), matchesPlayed,
					matchesWon, matchesDrawn, matchesLost, goalsFor, goalsAgainst, goalsDifference, goalsAway, points));
		}
		
		@Override
		public int hashCode() {
			return team.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Row other = (Row) obj;
			if (!getOuterTable().equals(other.getOuterTable()))
				return false;
			if (team.equals(other.team))
				return true;
			return false;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Row [").append("team=").append(team).append(", rank=").append(rank).append(", points=").append(points)
					.append(", matchesPlayed=").append(matchesPlayed).append(", matchesWon=").append(matchesWon).append(", matchesDrawn=")
					.append(matchesDrawn).append(", matchesLost=").append(matchesLost).append(", goalsFor=").append(goalsFor)
					.append(", goalsAgainst=").append(goalsAgainst).append(", goalsDifference=").append(goalsDifference).append(", goalsAway=")
					.append(goalsAway).append("]");
			return builder.toString();
		}
		
		public Table getOuterTable() {
			return Table.this;
		}
	}
}
