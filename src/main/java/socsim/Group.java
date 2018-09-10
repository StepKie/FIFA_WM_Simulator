package socsim;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Group {
	
	final String name;
	List<Team> teams = new ArrayList<>();
	int number;
	
	public static final Group getTestGroup() {
		Group test = new Group("T");
		test.teams.add(new Team("Argentina", "ARG", 1232, Confederation.CONMEBOL));
		test.teams.add(new Team("Brazil", "BRA", 1232, Confederation.CONMEBOL));
		test.teams.add(new Team("England", "ENG", 1232, Confederation.UEFA));
		test.teams.add(new Team("Germany", "GER", 1232, Confederation.UEFA));
		
		return test;
	}
}
