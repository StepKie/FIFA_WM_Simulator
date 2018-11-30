package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import socsim.KOMatch;
import socsim.Team;

@Slf4j
public class C_KOMatch extends Composite {
	
	@Getter @Setter private KOMatch match;
	
	@Getter @Setter private C_KOMatch home_previous;
	@Getter @Setter private C_KOMatch away_previous;
	
	@Getter int order;
	private CLabel label_team1;
	private Label label_score1;
	private CLabel label_team2;
	private Label label_score2;
	private Label lblNv;
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_KOMatch(Composite parent, int style, boolean reversed, int vertSpan, int order) {
		super(parent, style);
		
		this.order = order;
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
	public static C_KOMatch createCompositeKoMatch(Composite parent, boolean reversed, int vertSpan, int order) {
		return new C_KOMatch(parent, SWT.BORDER, reversed, vertSpan, order);
	}
	
	public void refresh() {
		if (match == null)
			return;
		log.info("Updating UI of {}", match.toString());
		updateLabel(match.getHomeTeam(), label_team1, label_score1, match.getHomeScore());
		updateLabel(match.getGuestTeam(), label_team2, label_score2, match.getGuestScore());
		label_score1.setText(Integer.toString(match.getHomeScore()));
		
		lblNv.setVisible(match.isVerlängerung());
		;
		
		layout();
	}
	
	public void finalHack() {
		setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 2));
		setLayout(new GridLayout(3, false));
		
		GridData gd_lbl_finale_t1 = new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1);
		gd_lbl_finale_t1.widthHint = 150;
		label_team1.setLayoutData(gd_lbl_finale_t1);
		
		label_team2.setLayoutData(gd_lbl_finale_t1);
		var t2_lo = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		t2_lo.widthHint = 150;
		label_team2.setLayoutData(t2_lo);
//		label_score1.moveAbove(label_team1);
		label_score2.setVisible(false);
		lblNv.moveAbove(label_team1);
		
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
		label_score.setVisible(score >= 0);
	}
	
	public void updateWinner(Team winner) {
		updateLabel(winner, label_team1, label_score1, 0);
		
	}
}
