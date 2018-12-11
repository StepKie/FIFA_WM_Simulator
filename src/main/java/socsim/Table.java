package socsim;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Data
public class Table {
	
	public static Comparator<Row> POINTS = Comparator.comparingInt(Row::getPoints);
	public static Comparator<Row> GOAL_DIFFERENCE = Comparator.comparingInt(Row::getGoalsDifference);
	public static Comparator<Row> GOALS_SCORED = Comparator.comparingInt(Row::getGoalsFor);
	public static Comparator<Row> DEFAULT = POINTS.thenComparing(GOAL_DIFFERENCE).thenComparing(GOALS_SCORED)
			.thenComparing(r -> r.getTeam().getElo());
	
	@NonNull private Comparator<Row> comparator = DEFAULT;
	private SortedSet<Team> teams = new TreeSet<Team>(Team.BY_ID);
	@Getter private SortedSet<Match> matches = new TreeSet<Match>();
	
	// TODO @Builder?
	public static Table buildTable(Collection<Match> matches, @NonNull Comparator<Row> comparator) {
		Table table = new Table();
		table.comparator = comparator;
		table.matches.addAll(matches);
		matches.stream().flatMap(m -> Stream.of(m.getHomeTeam(), m.getGuestTeam())).forEach(table.teams::add);
		return table;
	}
	
	public List<Row> getRows() {
		SortedSet<Row> rows = new TreeSet<Row>(comparator.reversed());
		teams.forEach(t -> rows.add(new Row(t)));
		return new ArrayList<>(rows);
	}
	
	public void print(PrintStream out) {
		out.println("Pos  Team          Pld    W    D    L   GF   GA   GD   GW  Pts");
		out.println("---  ------------  ---  ---  ---  ---  ---  ---  ---  ---  ---");
		getRows().forEach(r -> out.println(r.toString()));
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
			
			for (Match match : Table.this.matches) {
				if (!match.isFinished() || (!team.equals(match.getHomeTeam()) && !team.equals(match.getGuestTeam())))
					continue;
				
				matchesPlayed++;
				boolean isHomeTeam = team.equals(match.getHomeTeam());
				
				int goalsFor = isHomeTeam ? match.getHomeScore() : match.getGuestScore();
				int goalsAgainst = !isHomeTeam ? match.getHomeScore() : match.getGuestScore();
				int goalsDifference = goalsFor - goalsAgainst;
				if (goalsDifference > 0) {
					matchesWon++;
					points += 3;
				} else if (goalsDifference == 0) {
					matchesDrawn++;
					points++;
				} else {
					matchesLost++;
				}
				this.goalsFor += goalsFor;
				this.goalsAgainst += goalsAgainst;
				this.goalsDifference += goalsDifference;
				
			}
		}
		
		@Override
		public String toString() {
			return String.format("%3d  %-12s  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d  %3d", rank, team.getName(), matchesPlayed, matchesWon,
					matchesDrawn, matchesLost, goalsFor, goalsAgainst, goalsDifference, goalsAway, points);
		}
	}
}
