package socsim.phase;

import java.time.Instant;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import socsim.Group;
import socsim.Match;
import socsim.ui.C_Gruppe;
import socsim.ui.C_KOPhase;

@Slf4j
@RequiredArgsConstructor
public class Vorrunde implements CompetitionPhase {
	
	@NonNull List<Group> gruppen;
	@NonNull List<C_Gruppe> gruppenComps;
	
	@Override
	public void step() {
		Match vorrunde_Next = nextMatch();
		log.info("SPIELEN VORRUNDE");
		vorrunde_Next.play();
		gruppenComps.forEach(cgruppe -> cgruppe.refresh(vorrunde_Next));
		
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
	
	@Override
	public CompetitionPhase createNextRound() {
		assert (isFinished());
		gruppenComps.forEach(cgruppe -> cgruppe.refresh(null));
		log.info("Vorrunde vorbei");
		getShell().setSize(1700, 800);
		C_KOPhase koPhase = new C_KOPhase(getShell(), SWT.NONE);
		koPhase.setLayoutData(new RowData(1400, SWT.DEFAULT));
		koPhase.setVisible(true);
		getShell().layout();
		
		Instant date = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
		return new KORound(KORound.getAF(gruppen), date, koPhase);
	}
	
	private Shell getShell() {
		return gruppenComps.get(0).getShell();
	}
	
}
