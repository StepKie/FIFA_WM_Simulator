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
import socsim.Match.State;
import socsim.Team;

public class C_KOMatch extends Composite {
	
	@Getter @Setter protected KOMatch match;
	@Getter int order;
	protected CLabel label_team1;
	protected Label label_score1;
	protected CLabel label_team2;
	protected Label label_score2;
	protected Label lblNv;
	
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
		GridData gd_composite_komatch = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, vertSpan);
		gd_composite_komatch.heightHint = 100;
		gd_composite_komatch.widthHint = 170;
		setLayoutData(gd_composite_komatch);
		
		label_team1 = new CLabel(this, reversed ? SWT.RIGHT_TO_LEFT : SWT.NONE);
		label_team1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		
		lblNv = new Label(this, SWT.NONE);
		lblNv.setText("n.V.");
		lblNv.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 2));
		
		label_score1 = new Label(this, SWT.NONE);
		
		label_team2 = new CLabel(this, reversed || order == 15 ? SWT.RIGHT_TO_LEFT : SWT.NONE);
		label_team2.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		label_score2 = new Label(this, SWT.NONE);
		
		if (reversed) {
			// Do not reuse SWT GridData objects! ...
			var gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			var gd2 = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
			label_team1.setLayoutData(gd);
			label_team2.setLayoutData(gd2);
			
			label_score1.moveAbove(label_team1);
			label_score2.moveAbove(label_team2);
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
		updateLabel(match.getHomeTeam(), label_team1, label_score1, match.getHomeScore(), match.getState());
		updateLabel(match.getGuestTeam(), label_team2, label_score2, match.getGuestScore(), match.getState());
		lblNv.setVisible(match.isVerlängerung());
		layout(true);
	}
	
	private void updateLabel(Team t, CLabel label_team, Label label_score, int score, State state) {
		if (t == null) {
			label_team.setVisible(false);
			label_score.setVisible(false);
			return;
		}
		label_team.setText(t.getShortName());
		label_team.setImage(t.getFlag());
		label_team.setToolTipText(t.getTooltip());
		label_team.setVisible(true);
		
		label_score.setText(Integer.toString(score));
		label_score.setVisible(state == State.FINISHED);
	}
}
