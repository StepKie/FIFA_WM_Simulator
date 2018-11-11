package socsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import socsim.stable.Group;
import socsim.stable.Team;
import socsim.ui.FussballWM;
import socsim.ui.GruppenFactory;

@Slf4j
public class SoccerApplication {
	
	public static void main(String[] args) {
		
		Collection<Team> allTeams = TeamSelector.parseTeams("fifa_ranking_small.csv");
		log.info("-----------------------------");
		log.info("Participants:");
		Collection<Team> participants = new TeamSelector(allTeams).getParticipants();
		List<Group> groups = draw(participants);
		
		try {
			FussballWM window = new FussballWM(groups);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
