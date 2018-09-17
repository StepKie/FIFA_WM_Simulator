package ui.old;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ui.CGruppe;
import ui.GruppenFactory;

public class FussballWM2 {
	
	protected Shell shlFussballWm;
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FussballWM2 window = new FussballWM2();
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
		
		CGruppe fenster_1 = GruppenFactory.createWMGruppe(shlFussballWm, GruppenFactory.getTestGroup());
		
		CGruppe fenster = GruppenFactory.createWMGruppe(shlFussballWm, GruppenFactory.getTestGroup());
		fenster.setLayoutData(new RowData(120, 120));
		
		WMGruppe2 fenster_2 = new WMGruppe2(shlFussballWm, SWT.NONE);
		fenster_2.setLayoutData(new RowData(154, 97));
		
	}
}
