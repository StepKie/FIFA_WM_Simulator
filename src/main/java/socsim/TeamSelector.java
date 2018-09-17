package socsim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import socsim.stable.Team;

@Slf4j
public class TeamSelector {
	
	private Collection<? extends Team> allTeams;
	
	public TeamSelector(Collection<Team> teams) {
		allTeams = teams;
	}
	
	public Collection<? extends Team> getParticipantsFrom(Confederation cf) {
		Collection<Team> fromConf = allTeams.stream().filter(t -> t.getConfed() == cf).collect(Collectors.toList());
		log.info("Teams from {}:", cf.name);
		Collection<Team> selected = drawByElo(fromConf, cf.noOfParticipants);
		
		return selected;
	}
	
	private Collection<Team> drawByElo(Collection<Team> teams, int number) {
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
			log.info(drawnFromUrn.toString());
		}
		
		return drawn;
	}
}
