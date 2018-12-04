package socsim;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

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

public class Group {
	
	private String id;
	@Getter private String name;
	@Getter private SortedSet<Team> teams = new TreeSet<Team>();
	@Getter private List<Match> matches = new ArrayList<Match>();
	@Getter private Table table;
	
	public Group(String id, Comparator<Row> comparator, Collection<Team> teams, Instant start) {
		this.id = id;
		this.name = "Gruppe " + id;
		this.teams.addAll(teams);
		
		createSchedule(start);
		table = Table.buildTable(matches, comparator);
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
	
	/**
	 * Create a schedule for this group.
	 * 
	 * TODO May use a Strategy object (double-round-robin, etc.)
	 */
	private void createSchedule(Instant start) {
		List<Team> byElo = teams.stream().sorted(Comparator.comparingInt(Team::getElo).reversed()).collect(Collectors.toList());
		matches.add(new Match(start, id + "1-" + id + "2", byElo.get(0), byElo.get(1)));
		matches.add(new Match(start.plus(4, HOURS), id + "3-" + id + "4", byElo.get(2), byElo.get(3)));
		matches.add(new Match(start.plus(8, DAYS), id + "1-" + id + "3", byElo.get(0), byElo.get(2)));
		matches.add(new Match(start.plus(8, DAYS).plus(4, HOURS), id + "2-" + id + "4", byElo.get(1), byElo.get(3)));
		matches.add(new Match(start.plus(16, DAYS), id + "1-" + id + "4", byElo.get(0), byElo.get(3)));
		matches.add(new Match(start.plus(16, DAYS).plus(4, HOURS), id + "2-" + id + "3", byElo.get(1), byElo.get(2)));
		
	}
}
