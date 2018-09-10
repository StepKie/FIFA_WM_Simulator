package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import socsim.Group;

public class WMGruppe extends Composite {
	private Group gruppe = Group.getTestGroup();
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public WMGruppe(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(4, false));
		new Label(this, SWT.NONE);
		
		Label lblGruppenname = new Label(this, SWT.NONE);
		lblGruppenname.setText("Gruppe " + gruppe.getName());
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		for (int i = 1; i <= 4; i++) {
			Label position = new Label(this, SWT.NONE);
			position.setText(Integer.toString(i));
			Label teamName = new Label(this, SWT.NONE);
			teamName.setText(gruppe.getTeams().get(i - 1).getName());
			
			Label label = new Label(this, SWT.NONE);
			label.setText("0");
			
			Label label_1 = new Label(this, SWT.NONE);
			label_1.setText("0:0");
		}
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
