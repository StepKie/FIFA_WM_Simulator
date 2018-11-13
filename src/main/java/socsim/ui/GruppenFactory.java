package socsim.ui;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.Instant;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import lombok.extern.slf4j.Slf4j;
import socsim.Confederation;
import socsim.stable.Group;
import socsim.stable.Match;
import socsim.stable.Team;
import socsim.stable.ranking.Ranking;

@Slf4j
public final class GruppenFactory {
	
	public static final Instant WM_START = new GregorianCalendar(2012, 6, 8, 18, 0).toInstant();
	
	/**
	 * @wbp.factory
	 */
	public static C_Gruppe createWMGruppe(Composite parent, Group grp) {
		C_Gruppe gruppe = new C_Gruppe(parent, SWT.BORDER, grp);
		return gruppe;
	}
	
	/**
	 * @param id Group id
	 * @param teams List in the order of the seeding
	 * @return The group with matches set
	 */
	public static Group create_WM_Group(int id, List<Team> teams) {
		log.info("Creating Group {}", id);
		Group group = new Group(getName(id), Ranking.EURO_2012.comparator, teams);
		Instant offset = WM_START.plus(id, DAYS);
		group.createSchedule(offset);
		
		return group;
	}
	
	public static final String getName(int i) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i + 1);
	}
	
	public static final int getId(String letter) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(letter);
	}
	
	public static final Group getTestGroup() {
		Group group = new Group("T", Ranking.EURO_2012.comparator);
		group.addTeam(new Team("CZE", "Tschechien", 1200, Confederation.UEFA, null));
		group.addTeam(new Team("GRE", "Griechenland", 1000, Confederation.UEFA, null));
		group.addTeam(new Team("POL", "Polen", 1000, Confederation.UEFA, null));
		group.addTeam(new Team("RUS", "Russland", 1000, Confederation.UEFA, null));
		
		Instant i = new GregorianCalendar(2012, 6, 8, 18, 0).toInstant();
		group.addMatch(new Match(i, group.getTeam("POL"), group.getTeam("GRE"), 1, 1));
		group.addMatch(new Match(i, group.getTeam("RUS"), group.getTeam("CZE"), 4, 1));
		group.addMatch(new Match(i, group.getTeam("GRE"), group.getTeam("CZE"), 1, 2));
		group.addMatch(new Match(i, group.getTeam("POL"), group.getTeam("RUS"), 1, 1));
		group.addMatch(new Match(i, group.getTeam("GRE"), group.getTeam("RUS"), 1, 0));
		group.addMatch(new Match(i, group.getTeam("CZE"), group.getTeam("POL"), 1, 0));
		
		return group;
	}
}