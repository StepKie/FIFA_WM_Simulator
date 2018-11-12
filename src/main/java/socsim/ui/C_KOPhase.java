package socsim.ui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import lombok.Getter;
import socsim.stable.Match;

public class C_KOPhase extends Composite {
	
	@Getter List<C_KOMatch> matches;

	private Composite finale;
	private Label lbl_finale_t2;
	private Label final_result;
	private Label lbl_finale_t1;
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_KOPhase(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(7, false));
		
		C_KOMatch c_af1 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af3 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf1 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf3 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		C_KOMatch c_af2 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af4 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_hf1 = C_KOMatch.createCompositeKoMatch(this, false);
		
		finale = createFinaleComp();
		
		C_KOMatch c_hf2 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af5 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af7 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf2 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_vf4 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af6 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		C_KOMatch c_af8 = C_KOMatch.createCompositeKoMatch(this, true);

		matches = Arrays.asList(c_af1, c_af2, c_af3, c_af4, c_af5, c_af6, c_af7, c_af8, c_vf1, c_vf2, c_vf3, c_vf4, c_hf1, c_hf2);
	}

	private Composite createFinaleComp() {
		Composite composite = new Composite(this, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
		composite.setLayout(new GridLayout(3, false));

		lbl_finale_t1 = new Label(composite, SWT.CENTER);
		GridData gd_lbl_finale_t1 = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_lbl_finale_t1.widthHint = 70;
		lbl_finale_t1.setLayoutData(gd_lbl_finale_t1);
		lbl_finale_t1.setText("Team 1");
		lbl_finale_t1.setVisible(false);

		final_result = new Label(composite, SWT.NONE);
		final_result.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1));
		final_result.setText("4:1");
		final_result.setVisible(false);

		lbl_finale_t2 = new Label(composite, SWT.CENTER);
		GridData gd_lbl_finale_t2 = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_lbl_finale_t2.widthHint = 70;
		lbl_finale_t2.setLayoutData(gd_lbl_finale_t2);
		lbl_finale_t2.setText("Team 2");
		lbl_finale_t2.setVisible(false);

		return composite;
	}

	public void showFinale(Match match) {

		
		lbl_finale_t1.setText(match.getHomeTeam().toString());
		lbl_finale_t2.setText(match.getGuestTeam().toString());
		final_result.setText(match.getHomeScore() + ":" + match.getGuestScore());
		
		int colorId =SWT.COLOR_RED;
		Label winner = match.getWinner().equals(match.getHomeTeam()) ? lbl_finale_t1 : lbl_finale_t2;
		winner.setForeground(Display.getCurrent().getSystemColor(colorId));
	
		for (Control c : finale.getChildren())
			c.setVisible(true);

		layout();
	}
}
