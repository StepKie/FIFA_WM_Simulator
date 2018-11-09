package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class KOPhase extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public KOPhase(Composite parent, int style) {
		super(parent, style);

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(71, 65, 55, 36);

		Label lblA = new Label(composite, SWT.NONE);
		lblA.setBounds(0, 0, 55, 15);
		lblA.setText("A1");

		Label lblB = new Label(composite, SWT.NONE);
		lblB.setBounds(0, 21, 55, 15);
		lblB.setText("B2");

		Label lblAf = new Label(this, SWT.NONE);
		lblAf.setBounds(10, 65, 55, 15);
		lblAf.setText("AF1");

		Label label = new Label(this, SWT.NONE);
		label.setText("AF1");
		label.setBounds(10, 123, 55, 15);

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("A1");
		label_1.setBounds(71, 123, 55, 15);

		Label label_2 = new Label(this, SWT.NONE);
		label_2.setText("B2");
		label_2.setBounds(71, 144, 55, 15);

		Label label_3 = new Label(this, SWT.NONE);
		label_3.setText("AF1");
		label_3.setBounds(10, 202, 55, 15);

		Label label_4 = new Label(this, SWT.NONE);
		label_4.setText("A1");
		label_4.setBounds(71, 202, 55, 15);

		Label label_5 = new Label(this, SWT.NONE);
		label_5.setText("B2");
		label_5.setBounds(71, 223, 55, 15);

		Label label_6 = new Label(this, SWT.NONE);
		label_6.setText("AF1");
		label_6.setBounds(10, 283, 55, 15);

		Label label_7 = new Label(this, SWT.NONE);
		label_7.setText("A1");
		label_7.setBounds(71, 283, 55, 15);

		Label label_8 = new Label(this, SWT.NONE);
		label_8.setText("B2");
		label_8.setBounds(71, 304, 55, 15);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
