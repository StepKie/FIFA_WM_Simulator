package ui;

import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import socsim.Group;

public class FussballWM {
	
	protected Shell shlFussballWm;
	List<Group> gruppen;
	/**
	 * @wbp.nonvisual location=94,219
	 */
	private final GruppenFactory gruppenFactory = new GruppenFactory();
	
	public FussballWM(List<Group> groups) {
		this.gruppen = groups;
	}
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FussballWM window = new FussballWM(Collections.emptyList());
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
		
		for (Group gruppe : gruppen) {
			WMGruppe gruppenComp = GruppenFactory.createWMGruppe(shlFussballWm, gruppe);
			gruppenComp.setLayoutData(new RowData(154, 97));
		}
		
	}
}