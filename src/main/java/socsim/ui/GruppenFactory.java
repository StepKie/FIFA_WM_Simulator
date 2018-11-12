package socsim.ui;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

import java.time.Instant;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import lombok.extern.slf4j.Slf4j;
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
		Group group = new Group(getName(id), Ranking.EURO_2012.comparator);
		teams.forEach(group::addTeam);
		// TODO Logic here
		Instant offset = WM_START.plus(id, DAYS);
		group.addMatch(new Match(offset, teams.get(0), teams.get(1), false));
		group.addMatch(new Match(offset.plus(4, HOURS), teams.get(2), teams.get(3), false));
		group.addMatch(new Match(offset.plus(8, DAYS), teams.get(0), teams.get(2), false));
		group.addMatch(new Match(offset.plus(8, DAYS).plus(4, HOURS), teams.get(1), teams.get(3), false));
		group.addMatch(new Match(offset.plus(16, DAYS), teams.get(0), teams.get(3), false));
		group.addMatch(new Match(offset.plus(16, DAYS).plus(4, HOURS), teams.get(1), teams.get(2), false));
		
		return group;
	}
	
	public static final String getName(int i) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i + 1);
	}
	
	public static final int getId(String letter) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(letter);
	}
}