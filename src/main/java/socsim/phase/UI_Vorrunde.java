package socsim.phase;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import lombok.NonNull;
import socsim.Group;
import socsim.ui.C_Gruppe;
import socsim.ui.C_KOPhase;

public class UI_Vorrunde extends UI_Phase {
	
	@NonNull private List<C_Gruppe> gruppenComps;
	
	public UI_Vorrunde(List<Group> gruppen, List<C_Gruppe> ui_elements) {
		super(new Vorrunde(gruppen));
		this.gruppenComps = ui_elements;
	}
	
	@Override
	public void refresh() {
		gruppenComps.stream().forEach(cgruppe -> cgruppe.refresh(lastMatch()));
		
	}
	
	private Shell getShell() {
		return gruppenComps.get(0).getShell();
	}
	
	@Override
	public UI_Phase createNextRound() {
		return new C_KOPhase(getShell(), SWT.NONE, (KORound) delegate.createNextRound());
	}
}
