package socsim;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.Instant;
import java.util.GregorianCalendar;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class GruppenFactory {
	
	public static final Instant WM_START = new GregorianCalendar(2012, 6, 8, 18, 0).toInstant();
	
	/**
	 * @param id    Group id
	 * @param teams List in the order of the seeding
	 * @return The group with matches set
	 */
	public static Group create_WM_Group(int id, List<Team> teams) {
		log.info("Creating Group {}: {}", id, teams);
		Instant offset = WM_START.plus(id, DAYS);
		
		return new Group(getName(id), Table.WM_2018, teams, offset);
	}
	
	public static final String getName(int i) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i + 1);
	}
	
	public static final int getId(String letter) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(letter);
	}
	
	public static final Group getTestGroup() {
		var teams = List.of(new Team("CZE", "Tschechien", 1200, Confederation.UEFA, null),
				new Team("GRE", "Griechenland", 1000, Confederation.UEFA, null), new Team("POL", "Polen", 1000, Confederation.UEFA, null),
				new Team("RUS", "Russland", 1000, Confederation.UEFA, null));
		return new Group("T", Table.WM_2018, teams, WM_START);
	}
}