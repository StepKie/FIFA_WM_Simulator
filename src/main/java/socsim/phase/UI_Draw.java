package socsim.phase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.widgets.Shell;

import lombok.extern.slf4j.Slf4j;
import socsim.Group;
import socsim.Match;
import socsim.TeamSelector;
import socsim.io.Fussball_IO;
import socsim.ui.C_Gruppe;

@Slf4j
public class UI_Draw implements CompetitionPhase {
	
	private int revealed = 0;
	private int total;
	
	List<Group> gruppen;
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	Shell parent;
	
	public UI_Draw(Shell parent) {
		this.parent = parent;
		
		var allTeams = Fussball_IO.parseTeams();
		gruppen = new TeamSelector(allTeams).getGroups();
		total = (int) gruppen.stream().flatMap(g -> g.getTeams().stream()).count();
		gruppen.forEach(g -> gruppenComps.add(C_Gruppe.createWMGruppe(parent, g)));
	}
	
	@Override
	public void step() {
		int gruppe = revealed % 8;
		int index = revealed / 8;
		gruppenComps.get(gruppe).reveal(index);
		
		revealed++;
		return;
	}
	
	@Override
	public boolean isFinished() {
		return revealed == total;
	}
	
	@Override
	public CompetitionPhase createNextRound() {
		assert (isFinished());
		log.info("Ziehung vorbei");
		gruppenComps.forEach(cgruppe -> cgruppe.refresh(null));
		return new UI_Vorrunde(gruppen, gruppenComps);
	}
	
	@Override
	public List<? extends Match> matches() {
		// Same as Vorrunde ?!
		return gruppen.stream().flatMap(g -> g.getMatches().stream()).collect(Collectors.toList());
	}
}
