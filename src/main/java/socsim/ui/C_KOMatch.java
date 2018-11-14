package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import socsim.Match;

public class C_KOMatch extends Composite {
	
	private Match match;
	private CLabel label_team1;
	private Label label_score1;
	private CLabel label_team2;
	private Label label_score2;
	private Label lblNv;
	
	public C_KOMatch(Composite parent, int style) {
		this(parent, style, false, true, 1);
	}
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_KOMatch(Composite parent, int style, boolean reversed, boolean initialVisible, int vertSpan) {
		super(parent, style);
		
		setLayout(new GridLayout(3, false));
		GridData gd_composite_komatch = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, vertSpan);
		gd_composite_komatch.heightHint = 80;
		gd_composite_komatch.widthHint = 150;
		setLayoutData(gd_composite_komatch);
		
		label_team1 = new CLabel(this, reversed ? SWT.RIGHT_TO_LEFT : SWT.NONE);
		label_team1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		label_team1.setText(match != null ? match.getHomeTeam().toString() : "Team 1");
		label_team1.setImage(match != null ? match.getHomeTeam().getFlag() : null);
		
		lblNv = new Label(this, SWT.NONE);
		lblNv.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		lblNv.setVisible(false);
		lblNv.setText("n.V.");
		
		label_score1 = new Label(this, SWT.NONE);
		label_score1.setText("0");
		label_score1.setAlignment(SWT.RIGHT);
		
		label_team2 = new CLabel(this, reversed ? SWT.RIGHT_TO_LEFT : SWT.NONE);
		label_team2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		label_team2.setText(match != null ? match.getGuestTeam().toString() : "Team 2");
		label_team2.setImage(match != null ? match.getGuestTeam().getFlag() : null);
		
		label_score2 = new Label(this, SWT.NONE);
		label_score2.setText("0");
		label_score2.setAlignment(SWT.RIGHT);
		
		label_team1.setVisible(initialVisible);
		label_team2.setVisible(initialVisible);
		label_score1.setVisible(initialVisible);
		label_score2.setVisible(initialVisible);
		
		if (reversed) {
			label_score1.moveAbove(label_team1);
			label_team1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			label_score2.moveAbove(label_team2);
			label_team2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
			lblNv.moveAbove(label_team1);
		}
		
	}
	
	/**
	 * @wbp.factory
	 */
	public static C_KOMatch createCompositeKoMatch(Composite parent, boolean reversed, boolean initialVisible, int vertSpan) {
		return new C_KOMatch(parent, SWT.BORDER, reversed, initialVisible, vertSpan);
	}
	
	public void updateMatch(Match m) {
		label_team1.setText(m.getHomeTeam().toString());
		label_team1.setImage(m.getHomeTeam().getFlag());
		label_team1.setToolTipText(m.getHomeTeam().getTooltip());
		
		label_team2.setText(m.getGuestTeam().toString());
		label_team2.setImage(m.getGuestTeam().getFlag());
		label_team2.setToolTipText(m.getGuestTeam().getTooltip());
		
		label_score1.setText(Integer.toString(m.getHomeScore()));
		label_score2.setText(Integer.toString(m.getGuestScore()));
		
		label_team1.setVisible(true);
		label_team2.setVisible(true);
		label_score1.setVisible(true);
		label_score2.setVisible(true);
		lblNv.setVisible(m.isVerlängerung());
		;
		
		layout();
	}
}
