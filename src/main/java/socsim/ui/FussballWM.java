package socsim.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.extern.slf4j.Slf4j;
import socsim.io.Fussball_IO;
import socsim.phase.CompetitionPhase;
import socsim.phase.Draw;

/**
 * Main UI hub
 */
@Slf4j
public class FussballWM {
	
	private static final String APPLICATION_ICON = "/FIFA-World-Cup-2018.png";
	public static final int WIDTH = 1450;
	public static final int HEIGHT = 400;
	
	CompetitionPhase currentPhase;
	protected Shell shlFussballWm;
	
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	
	public FussballWM() {
		Fussball_IO.readHistory();
	}
	
	public static void main(String[] args) {
		new FussballWM().open();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		currentPhase = new Draw(shlFussballWm);
		shlFussballWm.open();
		shlFussballWm.layout();
		
		log.info("New Fussball WM session started");
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
		shlFussballWm.setSize(WIDTH, HEIGHT);
		shlFussballWm.setImage(SWTResourceManager.getImage(this.getClass(), APPLICATION_ICON));
		shlFussballWm.setText("Fussball WM");
		
		RowLayout rl_shlFussballWm = new RowLayout(SWT.HORIZONTAL);
		rl_shlFussballWm.fill = true;
		rl_shlFussballWm.justify = true;
		rl_shlFussballWm.center = true;
		shlFussballWm.setLayout(rl_shlFussballWm);
		
		createHistoryMenu();
		
		shlFussballWm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (currentPhase == null) {
					return;
				}
				
				if (e.character == SWT.ESC) {
					currentPhase = currentPhase.jump();
				} else {
					currentPhase.step();
					if (currentPhase.isFinished()) {
						currentPhase = currentPhase.createNextRound();
						
					}
					
				}
			}
		});
		shlFussballWm.addShellListener(new ShellAdapter() {
			
			@Override
			public void shellClosed(ShellEvent e) {
				Fussball_IO.persist();
			}
		});
	}
	
	private void createHistoryMenu() {
		Menu menu = new Menu(shlFussballWm, SWT.BAR);
		shlFussballWm.setMenuBar(menu);
		MenuItem mntmHistory = new MenuItem(menu, SWT.NONE);
		mntmHistory.setText("History");
		mntmHistory.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				new HistoryDialog(shlFussballWm).open();
//				MessageBox messageBox = new MessageBox(shlFussballWm, SWT.ICON_INFORMATION);
//				messageBox.setMessage("HI");
//				messageBox.open();
			}
		});
	}
}
