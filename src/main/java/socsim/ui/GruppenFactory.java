package socsim.ui;

import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;

import lombok.extern.slf4j.Slf4j;
import socsim.Confederation;
import socsim.stable.Group;
import socsim.stable.Match;
import socsim.stable.Team;
import socsim.stable.ranking.Ranking;

@Slf4j
public final class GruppenFactory {
	/**
	 * @wbp.factory
	 */
	public static CGruppe createWMGruppe(Composite parent, Group grp) {
		CGruppe gruppe = new CGruppe(parent, SWT.NONE, grp);
		gruppe.setLayoutData(new RowData(137, 86));
		return gruppe;
	}

	public static final Group getTestGroup() {
		Group group = new Group("T", Ranking.EURO_2012.comparator);
		group.addTeam(new Team("CZE", "Tschechien", 1200, Confederation.UEFA));
		group.addTeam(new Team("GRE", "Griechenland", 1000, Confederation.UEFA));
		group.addTeam(new Team("POL", "Polen", 1000, Confederation.UEFA));
		group.addTeam(new Team("RUS", "Russland", 1000, Confederation.UEFA));

		group.addMatch(
				new Match(new GregorianCalendar(2012, 6, 8, 18, 0), group.getTeam("POL"), group.getTeam("GRE"), 1, 1));
		group.addMatch(
				new Match(new GregorianCalendar(2012, 6, 8, 20, 45), group.getTeam("RUS"), group.getTeam("CZE"), 4, 1));
		group.addMatch(
				new Match(new GregorianCalendar(2012, 6, 12, 18, 0), group.getTeam("GRE"), group.getTeam("CZE"), 1, 2));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 12, 20, 45), group.getTeam("POL"), group.getTeam("RUS"),
				1, 1));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 16, 20, 45), group.getTeam("GRE"), group.getTeam("RUS"),
				1, 0));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 16, 20, 45), group.getTeam("CZE"), group.getTeam("POL"),
				1, 0));

		return group;
	}

	/**
	 * @param id    Group id
	 * @param teams List in the order of the seeding
	 * @return The group with matches set
	 */
	public static Group create_WM_Group(int id, List<Team> teams) {
		log.info("Creating Group {}", id);
		Group group = new Group(getName(id), Ranking.EURO_2012.comparator);
		teams.forEach(t -> group.addTeam(t));
		// TODO Logic here
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 8, 18, 0), teams.get(0), teams.get(1), false));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 8, 20, 45), teams.get(2), teams.get(3), false));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 12, 18, 0), teams.get(0), teams.get(2), false));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 12, 20, 45), teams.get(1), teams.get(3), false));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 16, 20, 45), teams.get(0), teams.get(3), false));
		group.addMatch(new Match(new GregorianCalendar(2012, 6, 16, 20, 45), teams.get(1), teams.get(1), false));

		return group;
	}

	public static final String getName(int i) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i + 1);
	}
	
	public static final int getId(String letter) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(letter);
	}

}