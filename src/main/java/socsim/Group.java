package socsim;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.Getter;
import socsim.Table.Row;

public class Group implements Comparable<Group> {
	private String id;
	@Getter private String name;
	@Getter private SortedSet<Team> teams = new TreeSet<Team>();
	@Getter private List<Match> matches = new ArrayList<Match>();
	@Getter private Table table;
	
	public Group(String id, Comparator<Row> comparator) {
		this.id = id;
		this.name = "Gruppe " + id;
		table = new Table(comparator);
	}
	
	public Group(String id, Comparator<Row> comparator, Collection<Team> teams) {
		this(id, comparator);
		teams.forEach(this::addTeam);
	}
	
	@Override
	public int compareTo(Group group) {
		return id.compareTo(group.id);
	}
	
	public void addTeam(Team team) {
		teams.add(team);
		table.addTeam(team);
	}
	
	public void addMatch(Match match) {
		matches.add(match);
		table.addMatch(match);
	}
	
	public Team getTeam(String id) {
		return teams.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
	}
	
	/**
	 * @return Team at position x (1-number of places)
	 */
	public Team getTeam(int position) {
		return getTable().getRows().get(position - 1).getTeam();
	}
	
	public void print(PrintStream out, boolean showTeams, boolean showMatches, boolean showSubTables) {
		out.println(name);
		out.println();
		if (showTeams) {
			teams.stream().sorted(Comparator.comparingInt(Team::getElo).reversed())
					.forEach(t -> out.println("  " + t + " (" + t.getId() + ", " + t.getElo() + ")"));
			out.println();
		}
		if (showMatches) {
			for (Match match : matches) {
				out.println(match.toString());
			}
			out.println();
		}
		table.print(out, showSubTables);
		out.println();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Create a schedule for this group.
	 * 
	 * TODO May use a Strategy object (double-round-robin, etc.)
	 */
	public void createSchedule(Instant start) {
		List<Team> byElo = teams.stream().sorted(Comparator.comparingInt(Team::getElo).reversed()).collect(Collectors.toList());
		addMatch(new Match(start, byElo.get(0), byElo.get(1), false));
		addMatch(new Match(start.plus(4, HOURS), byElo.get(2), byElo.get(3), false));
		addMatch(new Match(start.plus(8, DAYS), byElo.get(0), byElo.get(2), false));
		addMatch(new Match(start.plus(8, DAYS).plus(4, HOURS), byElo.get(1), byElo.get(3), false));
		addMatch(new Match(start.plus(16, DAYS), byElo.get(0), byElo.get(3), false));
		addMatch(new Match(start.plus(16, DAYS).plus(4, HOURS), byElo.get(1), byElo.get(2), false));
		
	}
}