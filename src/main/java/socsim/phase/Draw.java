package socsim.phase;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import socsim.Group;
import socsim.Match;
import socsim.TeamSelector;
import socsim.io.Fussball_IO;

@Slf4j
public class Draw implements CompetitionPhase {
	
	@Getter protected List<Group> gruppen;
	
	public final int num_groups;
	public final int teamsInGrp;

	public static final Draw DEFAULT = new Draw(8, 4);
	
	public Draw(int num_groups, int teamsInGrp) {
		this.num_groups = num_groups;
		this.teamsInGrp = teamsInGrp;
		var allTeams = Fussball_IO.parseTeams();
		gruppen = new TeamSelector(allTeams).getGroups();
	}
	
	@Override
	public boolean isFinished() {
		int numTeams = (int) gruppen.stream().flatMap(g -> g.getTeams().stream()).count();
		return numTeams == num_groups * teamsInGrp;
	}
	
	@Override
	public Vorrunde createNextRound() {
		assert (isFinished());
		log.info("Ziehung vorbei");
		return new Vorrunde(gruppen);
	}
	
	@Override
	public List<? extends Match> matches() {
		// Same as Vorrunde ?!
		return gruppen.stream().flatMap(g -> g.getMatches().stream()).collect(Collectors.toList());
	}
}
