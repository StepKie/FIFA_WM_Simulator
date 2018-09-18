package socsim.ui.old;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class WMGruppe2 extends Composite {
	private Label txtArgentina;
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public WMGruppe2(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(4, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("1");
		txtArgentina = new Label(this, SWT.NONE);
		txtArgentina.setText("Argentina");
		
		Label label = new Label(this, SWT.NONE);
		label.setText("7");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("8:3");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setText("2");
		
		Label lblNetherlands = new Label(this, SWT.NONE);
		lblNetherlands.setText("Netherlands");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
