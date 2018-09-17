package socsim.stable;


import java.util.SortedSet;
import java.util.TreeSet;

import ui.GruppenFactory;

public class Config
{
	private SortedSet<Group> groups = new TreeSet<Group>();

	// Print options
	private static final boolean SHOW_TEAMS = true;
	private static final boolean SHOW_MATCHES = true;
	private static final boolean SHOW_SUBTABLES = true;

	public static void main(String[] args) {
		Config config = new Config();
		config.initialiseGroups();
		config.printGroups();
	}

	private void initialiseGroups() {
		groups.clear();
		for (int i = 0; i < 8; i++) {
			groups.add(GruppenFactory.getTestGroup());
		}
	}

	private void printGroups() {
		for (Group group : groups)
			group.print(System.out, SHOW_TEAMS, SHOW_MATCHES, SHOW_SUBTABLES);
	}
}
