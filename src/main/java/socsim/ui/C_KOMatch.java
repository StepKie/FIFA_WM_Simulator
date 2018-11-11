package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class C_KOMatch extends Composite {
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_KOMatch(Composite parent, int style, boolean reversed) {
		super(parent, style);
		setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		setLayout(new GridLayout(2, false));
		
		Label label_team1 = new Label(this, SWT.NONE);
		label_team1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		label_team1.setText("Team 1");
		
		Label label_score1 = new Label(this, SWT.NONE);
		label_score1.setAlignment(SWT.RIGHT);
		label_score1.setText("3");
		
		Label label_team2 = new Label(this, SWT.NONE);
		
		layout();
		label_team2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		label_team2.setText("Team 2");
		
		Label label_score2 = new Label(this, SWT.NONE);
		label_score2.setText("1");
		
		GridData gd_composite_komatch = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_komatch.heightHint = 50;
		gd_composite_komatch.widthHint = 100;
		setLayoutData(gd_composite_komatch);
		
		if (reversed) {
			label_score1.moveAbove(label_team1);
			label_score2.moveAbove(label_team2);
		}
		
	}
	
	/**
	 * @wbp.factory
	 */
	public static Composite createCompositeKoMatch(Composite parent, boolean reversed) {
		return new C_KOMatch(parent, SWT.NONE, reversed);
	}
}
