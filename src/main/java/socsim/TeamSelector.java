package socsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import socsim.stable.Group;
import socsim.stable.Team;
import socsim.ui.GruppenFactory;

/**
 * Selection routines (drawing, making groups etc.) for various scenarios
 */
@Slf4j
@RequiredArgsConstructor
public class TeamSelector {
	
	@NonNull private Collection<Team> allTeams;
	
	public List<Group> getGroups() {
		return draw(allTeams);
	}
	
	public Collection<Team> getParticipants() {
		var participants = new ArrayList<Team>();
		for (Confederation cf : Confederation.values()) {
			participants.addAll(getParticipantsFrom(cf));
		}
		return participants;
	}
	
	public Collection<? extends Team> getParticipantsFrom(Confederation cf) {
		Collection<Team> fromConf = allTeams.stream().filter(t -> t.getConfed() == cf).collect(Collectors.toList());
		
		Collection<Team> selected = drawByElo(fromConf, cf.noOfParticipants);
		log.info("Teams from {}: {}", cf.name, selected);
		return selected;
	}
	
	private static Collection<Team> drawByElo(Collection<Team> teams, int number) {
		Collection<Team> drawn = new ArrayList<>();
		List<Team> urn = new ArrayList<>();
		for (Team t : teams) {
			for (int i = 0; i < t.getElo(); i++) {
				urn.add(t);
			}
		}
		
		for (int i = 0; i < number; i++) {
			Collections.shuffle(urn);
			Team drawnFromUrn = urn.get(0);
			urn.removeIf(t -> t.equals(drawnFromUrn));
			drawn.add(drawnFromUrn);
		}
		
		return drawn;
	}
	
	private static List<Group> draw(Collection<Team> participants) {
		var num_groups = 8;
		var teamsInGrp = 4;
		List<Team> teamsSorted = participants.stream().sorted(Comparator.comparingInt(Team::getElo).reversed()).collect(Collectors.toList());
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
