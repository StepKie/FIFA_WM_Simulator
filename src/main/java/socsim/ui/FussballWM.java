package socsim.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.extern.slf4j.Slf4j;
import socsim.Group;
import socsim.TeamSelector;
import socsim.io.Fussball_IO;
import socsim.phase.CompetitionPhase;
import socsim.phase.Vorrunde;

/**
 * Main UI hub
 */
@Slf4j
public class FussballWM {
	
	private static final String APPLICATION_ICON = "/FIFA-World-Cup-2018.png";
	
	public static final int DEFAULT_WIDTH = 1500;
	public static final int DEFAULT_HEIGHT = 950;
	
	CompetitionPhase currentPhase;
	private Shell shell;
	
	public FussballWM(Shell shell) {
		this.shell = shell;
	}
	
	public static void main(String[] args) {
//		console();
		Fussball_IO.readHistory();
		Shell newShell = new Shell();
		newShell.setSize(new Point(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		new FussballWM(newShell).open();
	}
	
	public static void start(Shell old) {
		Shell newShell = new Shell();
		newShell.setSize(old.getSize());
		newShell.setLocation(old.getLocation());
		old.close();
		new FussballWM(newShell).open();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		configureShell();
		currentPhase = new UI_Draw(shell);
		
		shell.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (currentPhase == null) {
					if (e.character != SWT.ESC) {
						FussballWM.start(shell);
					}
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
		shell.open();
		shell.layout();
		log.info("New Fussball-WM session started");
		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
	
	/**
	 * Create contents of the window.
	 */
	private void configureShell() {
		shell.setImage(SWTResourceManager.getImage(FussballWM.class, APPLICATION_ICON));
		shell.setText("Fussball-WM");
		shell.setLayout(new GridLayout(8, true));
		
		createHistoryMenu(shell);
		
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				Fussball_IO.persist();
			}
		});
	}
	
	private static void createHistoryMenu(Shell shell) {
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		MenuItem mntmHistory = new MenuItem(menu, SWT.NONE);
		mntmHistory.setText("Statistik");
		mntmHistory.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				new HistoryDialog(shell).open();
			}
		});
	}
	
	public static void console() {
		var allTeams = Fussball_IO.parseTeams();
		List<Group> gruppen = new TeamSelector(allTeams).getGroups();
		Vorrunde vr = new Vorrunde(gruppen);
		CompetitionPhase finalRunde = vr.jump();
		finalRunde.jump();
		log.info("WM DONE");
	}
}
