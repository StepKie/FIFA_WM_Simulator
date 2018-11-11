package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class C_KOPhase extends Composite {
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_KOPhase(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(7, false));
		
		Composite c_af1 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_af5 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		
		Composite c_vf1 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_vf3 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		Composite c_af2 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_af6 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_hf1 = C_KOMatch.createCompositeKoMatch(this, false);
		
		Label lbl_finale = new Label(this, SWT.NONE);
		lbl_finale.setText("Italien      4:1            Kroatien");
		
		Composite c_hf2 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_af3 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_af7 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		
		Composite c_vf2 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_vf4 = C_KOMatch.createCompositeKoMatch(this, true);
		new Label(this, SWT.NONE);
		
		Composite c_af4 = C_KOMatch.createCompositeKoMatch(this, false);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Composite c_af8 = C_KOMatch.createCompositeKoMatch(this, true);
	}
}
