package socsim.ui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.Getter;
import socsim.phase.KORound;

public class C_KOPhase {
	
	@Getter List<C_KOMatch> matches;
	private Button btnAgain;
	
	public C_KOPhase(Composite parent, int style, KORound koRound) {
		
		C_KOMatch c_af1 = C_KOMatch.createCompositeKoMatch(parent, false, 1, 1);
		C_KOMatch c_vf1 = C_KOMatch.createCompositeKoMatch(parent, false, 2, 9);
		// Invisible button instead of 4 labels ...
		createButton(parent).setVisible(false);
		C_KOMatch c_vf2 = C_KOMatch.createCompositeKoMatch(parent, true, 2, 10);
		C_KOMatch c_af3 = C_KOMatch.createCompositeKoMatch(parent, true, 1, 3);
		
		C_KOMatch c_af2 = C_KOMatch.createCompositeKoMatch(parent, false, 1, 2);
		C_KOMatch c_hf1 = C_KOMatch.createCompositeKoMatch(parent, false, 2, 13);
		C_KOMatch c_f = new C_Finale(parent, SWT.BORDER, 2, 15);
		C_KOMatch c_hf2 = C_KOMatch.createCompositeKoMatch(parent, true, 2, 14);
		C_KOMatch c_af4 = C_KOMatch.createCompositeKoMatch(parent, true, 1, 4);
		
		C_KOMatch c_af5 = C_KOMatch.createCompositeKoMatch(parent, false, 1, 5);
		C_KOMatch c_vf3 = C_KOMatch.createCompositeKoMatch(parent, false, 2, 11);
		
		C_KOMatch c_vf4 = C_KOMatch.createCompositeKoMatch(parent, true, 2, 12);
		C_KOMatch c_af7 = C_KOMatch.createCompositeKoMatch(parent, true, 1, 7);
		
		C_KOMatch c_af6 = C_KOMatch.createCompositeKoMatch(parent, false, 1, 6);
		
		btnAgain = createButton(parent);
		
		C_KOMatch c_af8 = C_KOMatch.createCompositeKoMatch(parent, true, 1, 8);
		
		new Label(parent, SWT.NONE);
		parent.layout();
		
		// FIXME Worst hack (why is there one more, stupid iterator )...
		matches = Arrays.asList(c_af1, c_af2, c_af3, c_af4, c_af5, c_af6, c_af7, c_af8, c_vf1, c_vf2, c_vf3, c_vf4, c_hf1, c_hf2, c_f);
		matches.forEach(m -> m.setMatch(koRound.getMatches().get(m.getOrder() - 1)));
	}
	
	private Button createButton(Composite parent) {
		Button btnAgain = new Button(parent, SWT.CENTER);
		btnAgain.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnAgain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FussballWM.start(parent.getShell());
			}
		});
		GridData gd_btnAgain = new GridData(SWT.CENTER, SWT.TOP, false, false, 4, 1);
		gd_btnAgain.widthHint = 90;
		btnAgain.setLayoutData(gd_btnAgain);
		btnAgain.setText("Nochmal!");
		btnAgain.setVisible(false);
		
		return btnAgain;
	}
	
	public void refresh() {
		getMatches().forEach(C_KOMatch::refresh);
		btnAgain.setVisible(getMatches().stream().allMatch(m -> m.getMatch().isFinished()));
	}
}
