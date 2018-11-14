package socsim;

import java.time.Instant;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Shell;

import lombok.extern.slf4j.Slf4j;
import socsim.ui.C_Gruppe;
import socsim.ui.C_KOPhase;

@Slf4j
public class Vorrunde implements CompetitionPhase {
	
	private int revealed = 0;
	
	List<Group> gruppen;
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	Shell parent;
	
	public Vorrunde(List<Group> gruppen, Shell parent) {
		this.parent = parent;
		this.gruppen = gruppen;
		for (Group gruppe : gruppen) {
			C_Gruppe gruppenComp = C_Gruppe.createWMGruppe(parent, gruppe);
			gruppenComps.add(gruppenComp);
			gruppenComp.setLayoutData(new RowData(SWT.DEFAULT, SWT.DEFAULT));
		}
	}
	
	@Override
	public void step() {
		if (revealed < 32) {
			System.out.println("DRAW");
			int gruppe = revealed % 8;
			int index = revealed / 8;
			gruppenComps.get(gruppe).reveal(index);
			
			revealed++;
			return;
		}
		
		Match vorrunde_Next = nextMatch();
		log.info("SPIELEN VORRUNDE");
		vorrunde_Next.play();
		gruppenComps.forEach(cgruppe -> cgruppe.refresh(vorrunde_Next));
		
	}
	
	@Override
	public CompetitionPhase jump() {
		while (revealed < 32) {
			int gruppe = revealed % 8;
			int index = revealed / 8;
			gruppenComps.get(gruppe).reveal(index);
			
			revealed++;
		}
		gruppenComps.forEach(cgruppe -> cgruppe.refresh(null));
		return isFinished() ? createNextRound() : this;
		
	}
	
	@Override
	public boolean isFinished() {
		return nextMatch() == null;
	}
	
	private Match nextMatch() {
		return gruppen.stream() //
				.flatMap(g -> g.getMatches().stream()) //
				.filter(m -> !m.isFinished()) //
				.sorted() //
				.findFirst().orElse(null);
	}
	
	public CompetitionPhase createNextRound() {
		assert (isFinished());
		parent.setSize(1700, 800);
		C_KOPhase koPhase = new C_KOPhase(parent, SWT.NONE);
		koPhase.setLayoutData(new RowData(1400, SWT.DEFAULT));
		koPhase.setVisible(true);
		parent.layout();
		
		Instant date = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
		return new KORound(KORound.getAF(gruppen), date, koPhase);
	}
	
}
