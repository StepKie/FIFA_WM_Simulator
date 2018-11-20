package socsim.phase;

import java.time.Instant;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Shell;

import lombok.extern.slf4j.Slf4j;
import socsim.Group;
import socsim.TeamSelector;
import socsim.io.Fussball_IO;
import socsim.ui.C_Gruppe;

@Slf4j
public class Draw implements CompetitionPhase {
	
	private int revealed = 0;
	private int total;
	
	List<Group> gruppen;
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	Shell parent;
	
	// TODO Integrate TeamSelector here, do not pass in Groups directly
	public Draw(Shell parent) {
		this.parent = parent;
		
		var allTeams = Fussball_IO.parseTeams();
		gruppen = new TeamSelector(allTeams).getGroups();
		
		for (Group gruppe : gruppen) {
			total = (int) gruppen.stream().flatMap(g -> g.getTeams().stream()).count();
			C_Gruppe gruppenComp = C_Gruppe.createWMGruppe(parent, gruppe);
			gruppenComps.add(gruppenComp);
			gruppenComp.setLayoutData(new RowData(SWT.DEFAULT, SWT.DEFAULT));
		}
	}
	
	@Override
	public void step() {
		log.info("Ziehung");
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
		
		Instant date = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
		return new Vorrunde(gruppen, gruppenComps);
	}
	
}
