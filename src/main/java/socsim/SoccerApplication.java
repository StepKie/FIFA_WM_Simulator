package socsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import socsim.stable.Group;
import socsim.stable.Team;
import socsim.ui.GruppenFactory;

@Slf4j
public class SoccerApplication {

	// Print options
	private static final boolean SHOW_TEAMS = true;
	private static final boolean SHOW_MATCHES = true;
	private static final boolean SHOW_SUBTABLES = true;

	public static void main(String[] args) {

		Collection<Team> allTeams = TeamSelector.parseTeams("fifa_ranking_small.csv");
		log.info("-----------------------------");
		log.info("Participants:");
		Collection<Team> participants = new TeamSelector(allTeams).getParticipants();
		List<Group> groups = draw(participants);

		groups.forEach(g -> g.print(System.out, SHOW_TEAMS, SHOW_MATCHES, SHOW_SUBTABLES));
//		try {
//			FussballWM window = new FussballWM(groups);
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private static List<Group> draw(Collection<Team> participants) {
		List<Team> teamsSorted = participants.stream().sorted(Comparator.comparingInt(Team::getElo).reversed())
				.collect(Collectors.toList());
		List<Team>[] topf = new List[4];
		for (int i = 0; i < 4; i++) {
			topf[i] = teamsSorted.subList(i * 8, i * 8 + 7);
			log.info("Topf {}: {}", i, topf[i]);
		}
		
//		Collection<Team> topf1 = teamsSorted.subList(0, 7);
//		Collection<Team> topf2 = teamsSorted.subList(8, 15);
//		Collection<Team> topf3 = teamsSorted.subList(16, 23);
//		Collection<Team> topf4 = teamsSorted.subList(24, 31);

		List<Group> groups = new ArrayList<>();

		// TODO Logic here
		int num_groups = 8;
		for (int i = 0; i < num_groups; i++) {
			Group grp = GruppenFactory.create_WM_Group(i, participants);
			groups.add(grp);
		}

		return groups;
	}

}
