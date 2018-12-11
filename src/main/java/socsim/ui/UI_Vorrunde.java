package socsim.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import lombok.NonNull;
import socsim.phase.KORound;
import socsim.phase.Vorrunde;

public class UI_Vorrunde extends UI_Phase<Vorrunde> {
	
	@NonNull private List<C_Gruppe> gruppenComps;
	
	public UI_Vorrunde(Vorrunde delegate, List<C_Gruppe> ui_elements) {
		super(delegate);
		this.gruppenComps = ui_elements;
	}
	
	@Override
	public void refresh() {
		var lastUpdate = isFinished() ? null : lastMatch();
		gruppenComps.stream().forEach(cgruppe -> cgruppe.refresh(lastUpdate));
		
	}
	
	private Shell getShell() {
		return gruppenComps.get(0).getShell();
	}
	
	@Override
	public UI_Phase<KORound> createNextRound() {
		return new C_KOPhase(getShell(), SWT.NONE, delegate.createNextRound());
	}
}
