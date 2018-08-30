package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FussballWM {
	
	protected Shell shlFussballWm;
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FussballWM window = new FussballWM();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlFussballWm.open();
		shlFussballWm.layout();
		while (!shlFussballWm.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlFussballWm = new Shell();
		shlFussballWm.setSize(652, 444);
		shlFussballWm.setText("Fussball WM");
		RowLayout rl_shlFussballWm = new RowLayout(SWT.HORIZONTAL);
		rl_shlFussballWm.justify = true;
		shlFussballWm.setLayout(rl_shlFussballWm);
		
		WMGruppe fenster_1 = GruppenFactory.createWMGruppe(shlFussballWm);
		
		WMGruppe fenster = GruppenFactory.createWMGruppe(shlFussballWm);
		fenster.setLayoutData(new RowData(120, 120));
		
		WMGruppe fenster_2 = new WMGruppe(shlFussballWm, SWT.NONE);
		fenster_2.setLayoutData(new RowData(154, 97));
		
	}
}
