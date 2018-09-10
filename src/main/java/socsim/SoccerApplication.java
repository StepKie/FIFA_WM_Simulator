package socsim;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import ui.FussballWM;

@Slf4j
public class SoccerApplication {
	
	private static Collection<Team> allTeams;
	
	public static void main(String[] args) {
		
		allTeams = parseTeams("fifa_ranking_small.csv");
		log.info("-----------------------------");
		log.info("Participants:");
		List<Team> participants = new ArrayList<>();
		for (Confederation cf : Confederation.values()) {
			participants.addAll(new TeamSelector(allTeams).getParticipantsFrom(cf));
		}
		List<Group> groups = draw(participants);
		
		try {
			FussballWM window = new FussballWM(groups);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<Group> draw(List<Team> participants) {
		int num_groups = 8;
		List<Group> groups = new ArrayList<>();
		for (int i = 1; i <= num_groups; i++) {
			Group grp = new Group(String.valueOf(i));
			for (int j = 1; j <= 4; j++) {
				grp.getTeams().add(participants.remove(0));
			}
			groups.add(grp);
		}
		
		return groups;
	}
	
	private static Collection<Team> parseTeams(String fileName) {
		Collection<Team> teams = new ArrayList<>();
		
		try {
			@Cleanup
			CSVParser parser = CSVParser.parse(ClassLoader.getSystemResource(fileName).openStream(),
					Charset.defaultCharset(), CSVFormat.EXCEL.withFirstRecordAsHeader());
			for (CSVRecord csvRecord : parser.getRecords()) {
				int elo = (int) Math.round(Double.parseDouble(csvRecord.get("total_points")));
				Confederation confed = Confederation.fromString(csvRecord.get("confederation"));
				Team newTeam = new Team(csvRecord.get("country_full"), csvRecord.get("country_abrv"), elo, confed);
				log.info("Parsed: {}", newTeam.toString());
				teams.add(newTeam);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teams;
	}
	
}
