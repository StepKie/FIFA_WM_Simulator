package socsim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import socsim.stable.Group;
import socsim.stable.Match;
import socsim.stable.Team;
import socsim.ui.GruppenFactory;

@Slf4j
public class SoccerApplication {

	// Print options
	private static final boolean SHOW_TEAMS = true;
	private static final boolean SHOW_MATCHES = true;
	private static final boolean SHOW_SUBTABLES = false;

	public static void main(String[] args) {

		Collection<Team> allTeams = TeamSelector.parseTeams("fifa_ranking_small.csv");
		log.info("-----------------------------");
		log.info("Participants:");
		Collection<Team> participants = new TeamSelector(allTeams).getParticipants();
		List<Group> groups = draw(participants);

		groups.forEach(g -> g.print(System.out, SHOW_TEAMS, SHOW_MATCHES, SHOW_SUBTABLES));
		makeKOPhase(groups);
		}
//		try {
//			FussballWM window = new FussballWM(groups);
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	private static void makeKOPhase(List<Group> groups) {
		Calendar date = new GregorianCalendar(2012, 6, 30, 16, 0);
//		Instant when = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
		Match af1 = new Match(date, groups.get(0).getTeam(1), groups.get(1).getTeam(2)); // A1-B2
		Match af2 = new Match(date, groups.get(2).getTeam(1), groups.get(3).getTeam(2)); // C1-D2
		Match af3 = new Match(date, groups.get(1).getTeam(1), groups.get(0).getTeam(2)); // B1-A2
		Match af4 = new Match(date, groups.get(3).getTeam(1), groups.get(2).getTeam(2)); // D1-C2
		Match af5 = new Match(date, groups.get(4).getTeam(1), groups.get(5).getTeam(2)); // E1-F2
		Match af6 = new Match(date, groups.get(6).getTeam(1), groups.get(7).getTeam(2)); // G1-H2
		Match af7 = new Match(date, groups.get(5).getTeam(1), groups.get(4).getTeam(2)); // F1-E2
		Match af8 = new Match(date, groups.get(7).getTeam(1), groups.get(6).getTeam(2)); // H1-G2
		
		// TODO K-o matches (new subclass?)
		Match vf1 = new Match(date, groups.get(0).getTeam(1), groups.get(1).getTeam(2)); // A1-B2
		Match vf2 = new Match(date, groups.get(2).getTeam(1), groups.get(3).getTeam(2)); // C1-D2
		Match vf3 = new Match(date, groups.get(1).getTeam(1), groups.get(0).getTeam(2)); // B1-A2
		Match vf4 = new Match(date, groups.get(3).getTeam(1), groups.get(2).getTeam(2)); // D1-C2

	}

	private static List<Group> draw(Collection<Team> participants) {
		int num_groups = 8;
		int teamsInGrp = 4;
		List<Team> teamsSorted = participants.stream().sorted(Comparator.comparingInt(Team::getElo).reversed())
				.collect(Collectors.toList());

		List<Team>[] topf = new List[teamsInGrp];
		for (int i = 0; i < teamsInGrp; i++) {
			topf[i] = teamsSorted.subList(i * num_groups, i * num_groups + 8);
			Collections.shuffle(topf[i]);
			log.info("Topf {}: {}", i, topf[i]);
		}
		List<Team>[] gruppe = new List[num_groups];
		for (int i = 0; i < num_groups; i++) {
			gruppe[i] = new ArrayList<>();
		}

		// TODO: Ensure that we can add confederation rule is not violated
		for (int i = 0; i < num_groups; i++) {
			for (int j = 0; j < teamsInGrp; j++) {
				gruppe[i].add(topf[j].get(i));
			}
		}

		List<Group> grps = new ArrayList<>();
		for (int i = 0; i < num_groups; i++) {
			Group grp = GruppenFactory.create_WM_Group(i, gruppe[i]);
			grps.add(grp);
		}

		return grps;
	}

	private static boolean canAdd(Collection<Team> teams, Team toAdd) {
		if (teams.contains(toAdd))
			return false;
		Confederation cf = toAdd.getConfed();
		long same_cf = teams.stream().filter(t -> t.getConfed() == cf).count();
		// There may be no team from the same Confederation in the Group (except UEFA,
		// where 1 may be in there for a max total of 2)
		return (cf == Confederation.UEFA) ? same_cf <= 1 : same_cf == 0;
	}

}
