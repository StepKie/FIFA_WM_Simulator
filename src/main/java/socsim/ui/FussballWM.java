package socsim.ui;

import java.time.Instant;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import socsim.stable.Group;
import socsim.stable.KORound;
import socsim.stable.Match;

public class FussballWM {
	
	protected Shell shlFussballWm;
	List<Group> gruppen;
	KORound koRunde;
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	private Iterator<C_KOMatch> koSpiele;
	
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
		shlFussballWm.setSize(850, 1100);
		shlFussballWm.setText("Fussball WM");

		RowLayout rl_shlFussballWm = new RowLayout(SWT.HORIZONTAL);
		shlFussballWm.setLayout(rl_shlFussballWm);
		
		for (Group gruppe : gruppen) {
			C_Gruppe gruppenComp = GruppenFactory.createWMGruppe(shlFussballWm, gruppe);
			gruppenComps.add(gruppenComp);
			gruppenComp.setLayoutData(new RowData(180, 280));
		}
		// new C_KOPhase(shlFussballWm, SWT.NONE);
		C_KOPhase koPhase = new C_KOPhase(shlFussballWm, SWT.BORDER);
		koSpiele = koPhase.getMatches().iterator();
		shlFussballWm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				playNextGame();
			}
		});
		
	}
	
	public void playNextGame() {
		Match vorrunde_Next = gruppen.stream().flatMap(g -> g.getMatches().stream()).filter(m -> !m.isFinished()).sorted().findFirst().orElse(null);
		if (vorrunde_Next != null) {
			vorrunde_Next.play();
			gruppenComps.forEach(C_Gruppe::refresh);
		} else {
			if (koRunde == null) {
				Instant date = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
				koRunde = new KORound("Achtelfinale", KORound.getAF(gruppen), date);
			} else if (koRunde.isFinished()) {
				if (koRunde.getMatches().size() > 1) {
					koRunde = koRunde.createNextRound();
				} else {
					System.err.println("WINNER: " + koRunde.getMatches().get(0).getWinner());
					return;
				}
			}
			Match next = koRunde.nextMatch();
			next.play();
			if (koSpiele.hasNext()) {
				koSpiele.next().updateMatch(next);
			}
		}
	}
}
