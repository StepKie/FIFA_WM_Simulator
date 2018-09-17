package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import socsim.stable.Group;

public class CGruppe extends Composite {
	private Group gruppe = GruppenFactory.getTestGroup();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CGruppe(Composite parent, int style, Group gruppe) {
		super(parent, style);
		this.gruppe = gruppe;
		setLayout(new GridLayout(4, false));
		new Label(this, SWT.NONE);

		Label lblGruppenname = new Label(this, SWT.NONE);
		lblGruppenname.setText("Gruppe " + gruppe.getName());
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		createRow(0);
		createRow(1);
		createRow(2);
		createRow(3);

	}

	private void createRow(int i) {
		Label position = new Label(this, SWT.NONE);
		position.setText(Integer.toString(i + 1));
		// TODO Do all that stuff by parsing from Table row ...
//		Label teamName = new Label(this, SWT.NONE);
//		teamName.setText(gruppe.getTeams().get(i).getName());
//
//		Label label = new Label(this, SWT.NONE);
//		label.setText("0");
//
//		Label label_1 = new Label(this, SWT.NONE);
//		label_1.setText("0:0");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
