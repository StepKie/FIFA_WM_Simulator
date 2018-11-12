package socsim.ui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import lombok.Getter;

public class C_KOPhase extends Composite {
	
	@Getter List<C_KOMatch> matches;
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
		
		Label lbl_finale = new Label(this, SWT.NONE);
		lbl_finale.setText("Italien      4:1            Kroatien");
		
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
}
