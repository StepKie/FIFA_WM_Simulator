package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import lombok.Getter;
import lombok.Setter;
import socsim.KOMatch;
import socsim.Match;
import socsim.Team;

public class C_KOMatch extends Composite {
	
	@Getter private Match match;
	
	@Getter @Setter private C_KOMatch home_previous;
	@Getter @Setter private C_KOMatch away_previous;
	
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
	
	public void updateMatch(KOMatch m) {
		match = m;
		if (m == null)
			return;
		updateLabel(m.getHomeTeam(), label_team1, label_score1, m.getHomeScore());
		updateLabel(m.getGuestTeam(), label_team2, label_score2, m.getGuestScore());
		label_score1.setText(Integer.toString(m.getHomeScore()));
		
		lblNv.setVisible(m.isVerlängerung());
		;
		
		layout();
	}
	
	private void updateLabel(Team t, CLabel label_team, Label label_score, int score) {
		if (t == null) {
			label_team.setVisible(false);
			label_score.setVisible(false);
			return;
		}
		
		label_team.setText(t.toString());
		label_team.setImage(t.getFlag());
		label_team.setToolTipText(t.getTooltip());
		
		label_score.setText(Integer.toString(score));
		
		label_team.setVisible(true);
		label_score.setVisible(true);
	}
	
	public void updateWinner(Team winner) {
		updateLabel(winner, label_team1, label_score1, 0);
		
	}
}
