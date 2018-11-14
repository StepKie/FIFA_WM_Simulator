package socsim.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.extern.slf4j.Slf4j;
import socsim.CompetitionPhase;
import socsim.Group;
import socsim.KORound;
import socsim.Vorrunde;

/**
 * Main UI hub
 */
@Slf4j
public class FussballWM {
	
	CompetitionPhase currentPhase;
	private static final String APPLICATION_ICON = "/FIFA-World-Cup-2018.png";
	protected Shell shlFussballWm;
	List<Group> gruppen;
	
	Vorrunde vorrunde;
	KORound koRunde;
	
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	
	public FussballWM(List<Group> groups) {
		this.gruppen = groups;
	}
	
	/**
	 * @wbp.parser.entryPoint
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
		shlFussballWm.setSize(1700, 400);
		shlFussballWm.setImage(SWTResourceManager.getImage(this.getClass(), APPLICATION_ICON));
		shlFussballWm.setText("Fussball WM");
		
		RowLayout rl_shlFussballWm = new RowLayout(SWT.HORIZONTAL);
		rl_shlFussballWm.fill = true;
		rl_shlFussballWm.justify = true;
		rl_shlFussballWm.center = true;
		shlFussballWm.setLayout(rl_shlFussballWm);
		
		vorrunde = new Vorrunde(gruppen, shlFussballWm);
		currentPhase = vorrunde;
		
		shlFussballWm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.ESC) {
					currentPhase = currentPhase.jump();
				} else {
					if (currentPhase.isFinished()) {
						currentPhase = currentPhase.createNextRound();
					}
					currentPhase.step();
				}
			}
		});
	}
}
