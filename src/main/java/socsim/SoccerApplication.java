package socsim;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoccerApplication {
	
	private static Collection<Team> allTeams;
	
	public static void main(String[] args) {
		
		allTeams = parseTeams("fifa_ranking_small.csv");
		Collection<Team> participants = new ArrayList<>();
		for (Confederation cf : Confederation.values()) {
			participants.addAll(new TeamSelector(allTeams).getParticipantsFrom(cf));
		}
		
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
