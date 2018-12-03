package socsim.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import socsim.Confederation;
import socsim.Match;
import socsim.Team;
import socsim.TeamSelector;

/**
 * Reads teams, flags etc from file system
 */
@Slf4j
public class Fussball_IO {
	
	public static final String RANKING_FILE = "fifa_elo_new.csv";
	public static final String FLAGS_FOLDER = "/flags-iso/shiny/24/";
	
	public static List<Match> HISTORY = new ArrayList<>();
	/**
	 * File containing the WM stats persisted at the last closing of the application
	 */
	public static final File PERSIST_FILE = new File("stats.wm");
	
	public static void readHistory() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PERSIST_FILE));) {
			HISTORY = (List<Match>) ois.readObject();
			log.info("Loaded {} matches from history ...", HISTORY.size());
		} catch (IOException | ClassNotFoundException e) {
			log.error("Could not read previous session, ignore if first run. Using default settings", e);
		}
	}
	
	public static void saveHistory(List<? extends Match> matches) {
		HISTORY.addAll(matches);
		
	}
	
	public static void persist() {
		try (FileOutputStream fos = new FileOutputStream(Fussball_IO.PERSIST_FILE); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			
			oos.writeObject(HISTORY);
			oos.close();
		} catch (IOException ex) {
			log.error("Error during serialization on exit", ex);
		}
	}
	
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
