package socsim.ui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.Getter;
import socsim.SoccerApplication;
import socsim.stable.Match;

public class C_KOPhase extends Composite {
	
	@Getter List<C_KOMatch> matches;
	
	private Composite finale;
	private CLabel lbl_finale_t2;
	private Label final_result;
	private CLabel lbl_finale_t1;

	private Button btnAgain;
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_KOPhase(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(7, false));
		
		C_KOMatch c_af1 = C_KOMatch.createCompositeKoMatch(this, false, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af3 = C_KOMatch.createCompositeKoMatch(this, true, false);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf1 = C_KOMatch.createCompositeKoMatch(this, false, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf3 = C_KOMatch.createCompositeKoMatch(this, true, false);
		new Label(this, SWT.NONE);
		C_KOMatch c_af2 = C_KOMatch.createCompositeKoMatch(this, false, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af4 = C_KOMatch.createCompositeKoMatch(this, true, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_hf1 = C_KOMatch.createCompositeKoMatch(this, false, false);
		
		finale = createFinaleComp();
		
		C_KOMatch c_hf2 = C_KOMatch.createCompositeKoMatch(this, true, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af5 = C_KOMatch.createCompositeKoMatch(this, false, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		btnAgain = new Button(this, SWT.CENTER);
		btnAgain.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnAgain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.err.println("Again, ok ...");
				C_KOPhase.this.getShell().close();
				SoccerApplication.main(new String[0]);
			}
		});
		GridData gd_btnAgain = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnAgain.widthHint = 100;
		btnAgain.setLayoutData(gd_btnAgain);
		btnAgain.setText("Nochmal!");
		btnAgain.setVisible(false);

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af7 = C_KOMatch.createCompositeKoMatch(this, true, false);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf2 = C_KOMatch.createCompositeKoMatch(this, false, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf4 = C_KOMatch.createCompositeKoMatch(this, true, false);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af6 = C_KOMatch.createCompositeKoMatch(this, false, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af8 = C_KOMatch.createCompositeKoMatch(this, true, false);
		
		matches = Arrays.asList(c_af1, c_af2, c_af3, c_af4, c_af5, c_af6, c_af7, c_af8, c_vf1, c_vf2, c_vf3, c_vf4,
				c_hf1, c_hf2);
	}
	
	private Composite createFinaleComp() {
		Composite composite = new Composite(this, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
		composite.setLayout(new GridLayout(3, false));
		
		lbl_finale_t1 = new CLabel(composite, SWT.CENTER);
		GridData gd_lbl_finale_t1 = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_lbl_finale_t1.widthHint = 150;
		lbl_finale_t1.setLayoutData(gd_lbl_finale_t1);
		lbl_finale_t1.setText("Team 1");
		lbl_finale_t1.setVisible(false);
		
		final_result = new Label(composite, SWT.NONE);
		final_result.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1));
		final_result.setText("4:1");
		final_result.setVisible(false);
		
		lbl_finale_t2 = new CLabel(composite, SWT.RIGHT_TO_LEFT);
		lbl_finale_t2.setAlignment(SWT.CENTER);
		GridData gd_lbl_finale_t2 = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_lbl_finale_t2.widthHint = 150;
		lbl_finale_t2.setLayoutData(gd_lbl_finale_t2);
		lbl_finale_t2.setText("Team 2");
		lbl_finale_t2.setVisible(false);
		
		return composite;
	}
	
	public void showFinale(Match match) {
		
		lbl_finale_t1.setText(match.getHomeTeam().toString());
		lbl_finale_t1.setImage(match.getHomeTeam().getFlag());
		lbl_finale_t2.setText(match.getGuestTeam().toString());
		lbl_finale_t2.setImage(match.getGuestTeam().getFlag());
		final_result.setText(match.getHomeScore() + ":" + match.getGuestScore());
		
		int colorId = SWT.COLOR_DARK_GREEN;
		CLabel winner = match.getWinner().equals(match.getHomeTeam()) ? lbl_finale_t1 : lbl_finale_t2;
		winner.setForeground(Display.getCurrent().getSystemColor(colorId));
		winner.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		
		for (Control c : finale.getChildren())
			c.setVisible(true);
		btnAgain.setVisible(true);
		layout();
	}
}
