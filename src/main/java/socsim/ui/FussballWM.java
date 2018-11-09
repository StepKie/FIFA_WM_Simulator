package socsim.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import socsim.stable.Match;
public class FussballWM {

	// Print options
	private static final boolean SHOW_TEAMS = true;
	private static final boolean SHOW_MATCHES = true;
	private static final boolean SHOW_SUBTABLES = false;

	protected Shell shlFussballWm;
	List<Group> gruppen;
	List<CGruppe> gruppenComps = new ArrayList<>();
	
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
		shlFussballWm.setSize(1700, 700);
		shlFussballWm.setText("Fussball WM");
		RowLayout rl_shlFussballWm = new RowLayout(SWT.HORIZONTAL);
		// rl_shlFussballWm.justify = true;
		shlFussballWm.setLayout(rl_shlFussballWm);
		
		
		Iterator<Match> vorrundenSpiele = gruppen.stream().flatMap(g -> g.getMatches().stream()).sorted().iterator();
		for (Group gruppe : gruppen) {
			CGruppe gruppenComp = GruppenFactory.createWMGruppe(shlFussballWm, gruppe);
			gruppenComps.add(gruppenComp);
			gruppenComp.setLayoutData(new RowData(200, 300));
		}


		shlFussballWm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (vorrundenSpiele.hasNext()) {
					vorrundenSpiele.next().play();
					gruppenComps.forEach(CGruppe::refresh);
				} else {
					gruppen.forEach(g -> g.print(System.out, SHOW_TEAMS, SHOW_MATCHES, SHOW_SUBTABLES));
					Calendar date = new GregorianCalendar(2012, 6, 30, 16, 0);
					Match af1 = new Match(date, gruppen.get(0).getTeam(1), gruppen.get(1).getTeam(2), true); // A1-B2
					Match af2 = new Match(date, gruppen.get(2).getTeam(1), gruppen.get(3).getTeam(2), true); // C1-D2
					Match af3 = new Match(date, gruppen.get(1).getTeam(1), gruppen.get(0).getTeam(2), true); // B1-A2
					Match af4 = new Match(date, gruppen.get(3).getTeam(1), gruppen.get(2).getTeam(2), true); // D1-C2
					Match af5 = new Match(date, gruppen.get(4).getTeam(1), gruppen.get(5).getTeam(2), true); // E1-F2
					Match af6 = new Match(date, gruppen.get(6).getTeam(1), gruppen.get(7).getTeam(2), true); // G1-H2
					Match af7 = new Match(date, gruppen.get(5).getTeam(1), gruppen.get(4).getTeam(2), true); // F1-E2
					Match af8 = new Match(date, gruppen.get(7).getTeam(1), gruppen.get(6).getTeam(2), true); // H1-G2
					KORound gruppe = new KORound(shlFussballWm, SWT.BORDER, "Achtelfinale", Arrays.asList(af1, af2, af3, af4, af5, af6, af7, af8));
					shlFussballWm.layout();
					// makeKOPhase(gruppen);
				}

			}
		});

	}

	private static void makeKOPhase(List<Group> groups) {
		Calendar date = new GregorianCalendar(2012, 6, 30, 16, 0);
//		Instant when = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
		Match af1 = new Match(date, groups.get(0).getTeam(1), groups.get(1).getTeam(2), true); // A1-B2
		Match af2 = new Match(date, groups.get(2).getTeam(1), groups.get(3).getTeam(2), true); // C1-D2
		Match af3 = new Match(date, groups.get(1).getTeam(1), groups.get(0).getTeam(2), true); // B1-A2
		Match af4 = new Match(date, groups.get(3).getTeam(1), groups.get(2).getTeam(2), true); // D1-C2
		Match af5 = new Match(date, groups.get(4).getTeam(1), groups.get(5).getTeam(2), true); // E1-F2
		Match af6 = new Match(date, groups.get(6).getTeam(1), groups.get(7).getTeam(2), true); // G1-H2
		Match af7 = new Match(date, groups.get(5).getTeam(1), groups.get(4).getTeam(2), true); // F1-E2
		Match af8 = new Match(date, groups.get(7).getTeam(1), groups.get(6).getTeam(2), true); // H1-G2
		System.out.println("1/8 Final");
		Arrays.asList(af1, af2, af3, af4, af5, af6, af7, af8).stream().forEach(Match::play);
		System.out.println("------------------------------------------------");
		// TODO K-o matches (new subclass?)
		Match vf1 = new Match(date, af1.getWinner(), af2.getWinner(), true);
		Match vf2 = new Match(date, af5.getWinner(), af6.getWinner(), true);
		Match vf3 = new Match(date, af3.getWinner(), af4.getWinner(), true);
		Match vf4 = new Match(date, af7.getWinner(), af8.getWinner(), true);

		System.out.println("1/4 Final");
		Arrays.asList(vf1, vf2, vf3, vf4).stream().forEach(Match::play);
		System.out.println("------------------------------------------------");

		Match hf1 = new Match(date, vf1.getWinner(), vf2.getWinner(), true);
		Match hf2 = new Match(date, vf3.getWinner(), vf4.getWinner(), true);

		System.out.println("1/2 Final");
		Arrays.asList(hf1, hf2).stream().forEach(Match::play);
		System.out.println("------------------------------------------------");

		// Match p3 = ...
		Match f = new Match(date, hf1.getWinner(), hf2.getWinner(), true);
		System.out.println("Final");
		f.play();
		System.out.println("------------------------------------------------");
		System.err.println("World Champion: " + f.getWinner());

	}
}
