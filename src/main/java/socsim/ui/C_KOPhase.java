package socsim.ui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.Getter;
import socsim.phase.KORound;

public class C_KOPhase extends Composite {
	
	@Getter List<C_KOMatch> matches;
	
	private Button btnAgain;
	
	public C_KOPhase(Composite parent, int style, KORound koRound) {
		this(parent, style);
		matches.forEach(m -> m.setMatch(koRound.getMatches().get(m.getOrder() - 1)));
		setLayoutData(new RowData(FussballWM.WIDTH - 100, SWT.DEFAULT));
		setVisible(true);
		getShell().layout();
	}
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_KOPhase(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(7, false));
		
		C_KOMatch c_af1 = C_KOMatch.createCompositeKoMatch(this, false, 1, 1);
		C_KOMatch c_vf1 = C_KOMatch.createCompositeKoMatch(this, false, 2, 9);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		C_KOMatch c_vf2 = C_KOMatch.createCompositeKoMatch(this, true, 2, 10);
		C_KOMatch c_af3 = C_KOMatch.createCompositeKoMatch(this, true, 1, 3);
		
		C_KOMatch c_af2 = C_KOMatch.createCompositeKoMatch(this, false, 1, 2);
		C_KOMatch c_hf1 = C_KOMatch.createCompositeKoMatch(this, false, 2, 13);
		C_KOMatch c_f = new C_Finale(this, SWT.BORDER, 2, 15);
		C_KOMatch c_hf2 = C_KOMatch.createCompositeKoMatch(this, true, 2, 14);
		C_KOMatch c_af4 = C_KOMatch.createCompositeKoMatch(this, true, 1, 4);
		
		C_KOMatch c_af5 = C_KOMatch.createCompositeKoMatch(this, false, 1, 5);
		C_KOMatch c_vf3 = C_KOMatch.createCompositeKoMatch(this, false, 2, 11);
		
		C_KOMatch c_vf4 = C_KOMatch.createCompositeKoMatch(this, true, 2, 12);
		C_KOMatch c_af7 = C_KOMatch.createCompositeKoMatch(this, true, 1, 7);
		
		C_KOMatch c_af6 = C_KOMatch.createCompositeKoMatch(this, false, 1, 6);
		new Label(this, SWT.NONE);
		
		btnAgain = new Button(this, SWT.CENTER);
		btnAgain.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnAgain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.err.println("Again, ok ...");
				C_KOPhase.this.getShell().close();
				FussballWM.main(new String[0]);
			}
		});
		GridData gd_btnAgain = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1);
		gd_btnAgain.widthHint = 100;
		btnAgain.setLayoutData(gd_btnAgain);
		btnAgain.setText("Nochmal!");
		btnAgain.setVisible(true);
		
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af8 = C_KOMatch.createCompositeKoMatch(this, true, 1, 8);
		
		// FIXME Worst hack (why is there one more, stupid iterator )...
		matches = Arrays.asList(c_af1, c_af2, c_af3, c_af4, c_af5, c_af6, c_af7, c_af8, c_vf1, c_vf2, c_vf3, c_vf4,
				c_hf1, c_hf2, c_f);
		
	}
	
	public void refresh() {
		getMatches().forEach(C_KOMatch::refresh);
		if (getMatches().stream().allMatch(m -> m.getMatch().isFinished()))
			btnAgain.setVisible(true);
		
	}
}
