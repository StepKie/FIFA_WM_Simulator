package socsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import socsim.stable.Group;
import socsim.stable.Team;
import ui.FussballWM;
import ui.GruppenFactory;

@Slf4j
public class SoccerApplication {

	private static Collection<Team> allTeams;

	public static void main(String[] args) {

		allTeams = GruppenFactory.parseTeams("fifa_ranking_small.csv");
		log.info("-----------------------------");
		log.info("Participants:");
		List<Team> participants = new ArrayList<>();
		for (Confederation cf : Confederation.values()) {
			participants.addAll(new TeamSelector(allTeams).getParticipantsFrom(cf));
		}
		List<Group> groups = draw(participants);

		try {
			FussballWM window = new FussballWM(groups);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<Group> draw(List<Team> participants) {
		int num_groups = 2;
		List<Group> groups = new ArrayList<>();
		for (int i = 0; i < num_groups; i++) {
			Group grp = GruppenFactory.create_WM_Group(i, participants);
			groups.add(grp);
		}

		return groups;
	}

}
