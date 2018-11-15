package socsim;

import java.util.List;

import socsim.io.Fussball_IO;
import socsim.ui.FussballWM;

public class SoccerApplication {
	
	public static void main(String[] args) {
		var allTeams = Fussball_IO.parseTeams();
		List<Group> groups = new TeamSelector(allTeams).getGroups();
		new FussballWM(groups).open();
	}
}
