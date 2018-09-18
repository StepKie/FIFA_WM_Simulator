package socsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		int num_groups = 2;
		List<Group> groups = new ArrayList<>();
		// TODO Logic here
		for (int i = 0; i < num_groups; i++) {
			Group grp = GruppenFactory.create_WM_Group(i, participants);
			groups.add(grp);
		}

		return groups;
	}

}
