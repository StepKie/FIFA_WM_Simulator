package socsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Selection routines (drawing, making groups etc.) for various scenarios
 */
@Slf4j
@RequiredArgsConstructor
public class TeamSelector {
	
	int num_groups = 8;
	int teamsInGrp = 4;
	
	private List<List<Team>> topf = new ArrayList<>();
	private List<List<Team>> gruppe = new ArrayList<>();
	
	private static Predicate<Team> isUefa = t -> t.getConfed() == Confederation.UEFA;
	
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
	
	private List<Group> draw(Collection<Team> participants) {
		
		List<Team> teamsSorted = participants.stream().sorted(Comparator.comparingInt(Team::getElo).reversed()).collect(Collectors.toList());
		for (int i = 0; i < teamsInGrp; i++) {
			topf.add(teamsSorted.subList(i * num_groups, i * num_groups + 8));
			if (i < 2) {
				Collections.shuffle(topf.get(i));
			} else {
				Collections.sort(topf.get(i), Team.BY_NO_OF_PARTICIPANTS);
			}
			log.info("Topf {}: {}", i, topf.get(i));
		}
		for (int i = 0; i < num_groups; i++) {
			gruppe.add(new ArrayList<>());
		}
		
		while (!teamsSorted.isEmpty()) {
			Team next = teamsSorted.remove(0);
			getValidGroup(next).add(next);
		}
		
		List<Group> grps = new ArrayList<>();
		for (int i = 0; i < num_groups; i++) {
			Group grp = GruppenFactory.create_WM_Group(i, gruppe.get(i));
			grps.add(grp);
		}
		
		return grps;
	}
	
	private static int uefaTeams(List<Team> teams) {
		return (int) teams.stream().filter(isUefa).count();
	}
	
	private List<Team> getValidGroup(Team t) {
		Comparator<List<?>> c = Comparator.comparingInt(List::size);
		c.thenComparing(c);
		return gruppe.stream() //
				.sorted(Comparator.comparingInt(List::size)) //
				.sorted(Comparator.comparingInt(g -> uefaTeams(g))) //
				.findFirst().orElseThrow();
	}
}
