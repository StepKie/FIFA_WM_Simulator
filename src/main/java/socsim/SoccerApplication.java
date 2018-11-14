package socsim;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import socsim.io.Fussball_IO;
import socsim.ui.FussballWM;

@Slf4j
public class SoccerApplication {
	
	public static void main(String[] args) {
		var allTeams = Fussball_IO.parseTeams();
		List<Group> groups = new TeamSelector(allTeams).getGroups();
		new FussballWM(groups).open();
		log.info("New Fussball WM session started");
	}
}
