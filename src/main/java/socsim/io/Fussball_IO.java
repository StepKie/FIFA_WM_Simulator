package socsim.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import socsim.Confederation;
import socsim.TeamSelector;
import socsim.stable.Team;

/**
 * Reads teams, flags etc from file system
 */
@Slf4j
public class Fussball_IO {
	
	public static final String RANKING_FILE = "fifa_elo_new.csv";
	public static final String FLAGS_FOLDER = "/flags-iso/shiny/24/";
	
	public static Collection<Team> parseTeams() {
		Collection<Team> teams = new ArrayList<>();
		
		try {
			
			for (CSVRecord csvRecord : getRecords()) {
				int elo = (int) Math.round(Double.parseDouble(csvRecord.get("elo_new")));
				Confederation confed = Confederation.fromString(csvRecord.get("confederation"));
				Image flag = SWTResourceManager.getImage(TeamSelector.class, FLAGS_FOLDER + csvRecord.get("country_code_2") + ".png");
				teams.add(new Team(csvRecord.get("country_code_3"), csvRecord.get("country_full"), elo, confed, flag));
			}
		} catch (IOException e) {
			log.error("Could not read from file", e);
		}
		return teams;
	}
	
	public static Image getLargeFlag(Team t) {
		try {
			CSVRecord csvRecord = getRecords().stream().filter(r -> t.getId().equals(r.get("country_code_3"))).findFirst().orElse(null);
			String code2 = csvRecord.get("country_code_2");
			return SWTResourceManager.getImage(TeamSelector.class, "/flags-iso/shiny/64/" + code2 + ".png");
			
		} catch (IOException e) {
			log.warn("Did not find flag for " + t.getId(), e);
			return null;
		}
		
	}
	
	private static Collection<CSVRecord> getRecords() throws IOException {
		
		@Cleanup
		CSVParser parser = CSVParser.parse(ClassLoader.getSystemResource(RANKING_FILE).openStream(), Charset.defaultCharset(),
				CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader());
		return parser.getRecords();
	}
	
}
