package socsim.stable;

import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Getter;
import socsim.stable.ranking.TableRowComparator;

public class Group implements Comparable<Group> {
	private String id;
	@Getter private String name;
	@Getter private SortedSet<Team> teams = new TreeSet<Team>();
	@Getter private List<Match> matches = new ArrayList<Match>();
	@Getter private Table table;
	
	public Group(String id, TableRowComparator comparator) {
		this.id = id;
		this.name = "Gruppe " + id;
		table = new Table(comparator);
	}
	
	/**
	 * Create a Group from raw text data representing a cross-table of each team's match results against each other
	 * team, i.e. the usual two matches per pair, one home and one guest match.
	 *
	 * @param id unique group/league ID
	 * @param name full group/league name
	 * @param rawData cross-table raw data (comma-separated) with one row/line per team. The first table column is the
	 *        team name, the remaining ones are the home match results, e.g.
	 * 
	 *        <pre>
	 * Team A,?,3,0,2,1
	 * Team B,1,2,?,2,2
	 * Team C,0,1,2,3,?
	 *        </pre>
	 * 
	 *        Each "?" is a dummy value because no team plays against itself. In the above example Team A beat B at home
	 *        by 3-0 and as guest team by 1-2. Team C lost against A at home by 0-1 and also 2-1 as guest team. Team B
	 *        tied C at home by 2-2.
	 *        <p>
	 *        <b>Please note:</b> The simplified cross-table reformat does <i>not</i> contain any match dates, so this
	 *        method creates synthetic, identical ones without any real-world significance.
	 * @param comparator table row comparator to use for ranking the resulting table
	 * @return the Group generated from the cross-table
	 */
	static Group parseCrossTable(String id, TableRowComparator comparator) {
		Group group = new Group(id, comparator);
		
		// Determine teams
		List<Team> teams = new ArrayList<Team>();
		
		Instant dummyMatchDate = Calendar.getInstance().toInstant();
		for (Team homeTeam : teams) {
			
			for (Team guestTeam : teams) {
				if (homeTeam.equals(guestTeam)) {
					continue;
				}
				group.addMatch(new Match(dummyMatchDate, homeTeam, guestTeam, 0, 0));
			}
		}
		return group;
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
}
