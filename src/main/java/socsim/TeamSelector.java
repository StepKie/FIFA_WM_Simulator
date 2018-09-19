package socsim;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import socsim.stable.Team;

@Slf4j
public class TeamSelector {
	
	private Collection<? extends Team> allTeams;

	public TeamSelector(Collection<Team> teams) {
		allTeams = teams;
	}
	
	public static Collection<Team> parseTeams(String fileName) {
		Collection<Team> teams = new ArrayList<>();

		try {
			@Cleanup
			CSVParser parser = CSVParser.parse(ClassLoader.getSystemResource(fileName).openStream(),
					Charset.defaultCharset(), CSVFormat.EXCEL.withFirstRecordAsHeader());
			for (CSVRecord csvRecord : parser.getRecords()) {
				int elo = (int) Math.round(Double.parseDouble(csvRecord.get("total_points")));
				Confederation confed = Confederation.fromString(csvRecord.get("confederation"));
				teams.add(new Team(csvRecord.get("country_full"), csvRecord.get("country_abrv"), elo, confed));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return teams;
	}
	
	public Collection<Team> getParticipants() {
		List<Team> participants = new ArrayList<>();
		for (Confederation cf : Confederation.values()) {
			participants.addAll(getParticipantsFrom(cf));
		}
		return participants;
	}

	public Collection<? extends Team> getParticipantsFrom(Confederation cf) {
		Collection<Team> fromConf = allTeams.stream().filter(t -> t.getConfed() == cf).collect(Collectors.toList());

		Collection<Team> selected = drawByElo(fromConf, cf.noOfParticipants);
		log.info("Teams from {}: {}", cf.name, selected);
		return selected;
	}
	
	private static Collection<Team> drawByElo(Collection<Team> teams, int number) {
		Collection<Team> drawn = new ArrayList<>();
		List<Team> urn = new ArrayList<>();
		for (Team t : teams) {
			for (int i = 0; i < t.getElo(); i++) {
				urn.add(t);
			}
		}
		
		for (int i = 0; i < number; i++) {
			Collections.shuffle(urn);
			Team drawnFromUrn = urn.get(0);
			urn.removeIf(t -> t.equals(drawnFromUrn));
			drawn.add(drawnFromUrn);
		}
		
		return drawn;
	}
}
