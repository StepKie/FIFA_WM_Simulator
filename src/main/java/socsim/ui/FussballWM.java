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
import org.eclipse.wb.swt.SWTResourceManager;

import socsim.stable.Group;
import socsim.stable.KORound;
import socsim.stable.Match;

public class FussballWM {
	
	private static final String APPLICATION_ICON = "/FIFA-World-Cup-2018.png";
	protected Shell shlFussballWm;
	List<Group> gruppen;
	
	KORound koRunde;
	private C_KOPhase koPhase;
	
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	private Iterator<C_KOMatch> koSpiele;
	private int revealed;
	
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
		
		for (Group gruppe : gruppen) {
			C_Gruppe gruppenComp = GruppenFactory.createWMGruppe(shlFussballWm, gruppe);
			gruppenComps.add(gruppenComp);
			gruppenComp.setLayoutData(new RowData(SWT.DEFAULT, SWT.DEFAULT));
		}
		
		koPhase = new C_KOPhase(shlFussballWm, SWT.NONE);
		koPhase.setVisible(false);
		koPhase.setLayoutData(new RowData(1400, SWT.DEFAULT));
		koSpiele = koPhase.getMatches().iterator();
		shlFussballWm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Make this for all phases, without duplicated code etc
				if (e.character == SWT.ESC) {
					while (revealed < 32) {
						int gruppe = revealed % 8;
						int index = revealed / 8;
						gruppenComps.get(gruppe).reveal(index);
						
						revealed++;
					}
					gruppenComps.forEach(cgruppe -> cgruppe.refresh(null));
					return;
				}
				if (revealed < 32) {
					int gruppe = revealed % 8;
					int index = revealed / 8;
					gruppenComps.get(gruppe).reveal(index);
					
					revealed++;
				} else {
					playNextGame();
				}
			}
		});
		
	}
	
	public void playNextGame() {
		Match vorrunde_Next = gruppen.stream().flatMap(g -> g.getMatches().stream()).filter(m -> !m.isFinished())
				.sorted().findFirst().orElse(null);
		if (vorrunde_Next != null) {
			vorrunde_Next.play();
			gruppenComps.forEach(cgruppe -> cgruppe.refresh(vorrunde_Next));
		} else {
			gruppenComps.forEach(cgruppe -> cgruppe.refresh(null));
			if (koRunde == null) {
				shlFussballWm.setSize(1700, 800);
				koPhase.setVisible(true);
				
				Instant date = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
				koRunde = new KORound(KORound.getAF(gruppen), date);
			} else if (koRunde.isFinished()) {
				if (koRunde.getMatches().size() > 1) {
					koRunde = koRunde.createNextRound();
				} else {
					koPhase.showFinale(koRunde.getMatches().get(0));
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
