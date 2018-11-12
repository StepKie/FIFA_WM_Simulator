package socsim;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import socsim.stable.Team;

@Slf4j
public class TeamSelector {
	
	public static final String ranking_file = "fifa_ranking_small.csv";
	public static final String countryCodes = "country_codes.csv";
	private Collection<? extends Team> allTeams;
	
	public TeamSelector(Collection<Team> teams) {
		allTeams = teams;
	}
	
	public static Collection<Team> parseTeams() {
		Collection<Team> teams = new ArrayList<>();
		
		try {
			@Cleanup
			CSVParser parser = CSVParser.parse(ClassLoader.getSystemResource(ranking_file).openStream(),
					Charset.defaultCharset(), CSVFormat.EXCEL.withFirstRecordAsHeader());
			for (CSVRecord csvRecord : parser.getRecords()) {
				int elo = (int) Math.round(Double.parseDouble(csvRecord.get("total_points")));
				Confederation confed = Confederation.fromString(csvRecord.get("confederation"));
				Image flag = findFlag(csvRecord.get("country_abrv"));
				teams.add(new Team(csvRecord.get("country_abrv"), csvRecord.get("country_full"), elo, confed, flag));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return teams;
	}
	
	private static Image findFlag(String code3) {
		try {
			@Cleanup
			CSVParser parser = CSVParser.parse(ClassLoader.getSystemResource(countryCodes).openStream(),
					Charset.defaultCharset(), CSVFormat.EXCEL.withFirstRecordAsHeader());
			
			Optional<CSVRecord> code3Line = parser.getRecords().stream()
					.filter(r -> r.get("Alpha-3").equalsIgnoreCase(code3)).findFirst();
			if (code3Line.isPresent()) {
				String code2 = code3Line.get().get("Alpha-2");
				System.out.println("Found country code: " + code3 + " to " + code2);
				return SWTResourceManager.getImage("/flags-iso/shiny/24" + code2 + ".png");
			} else {
				System.out.println("No entry for " + code3);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
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
