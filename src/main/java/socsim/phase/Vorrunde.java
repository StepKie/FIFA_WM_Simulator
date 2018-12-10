package socsim.phase;

import java.time.Instant;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import socsim.Group;
import socsim.Match;
import socsim.Team;

@Slf4j
@RequiredArgsConstructor
public class Vorrunde implements CompetitionPhase {
	
	@NonNull private List<Group> gruppen;
	
	@Override
	public void step() {
		Match vorrunde_Next = nextMatch();
		vorrunde_Next.play();
		log.info("VORRUNDE: {}", vorrunde_Next.toString());
		
	}
	
	@Override
	public KORound createNextRound() {
		assert (isFinished());
		log.info("Vorrunde vorbei");
		
//		getShell().setSize(FussballWM.WIDTH, FussballWM.HEIGHT * 2);
		
		Instant date = new GregorianCalendar(2012, 6, 30, 16, 0).toInstant();
		var koRound = new KORound(getAF(), date);
		
//		if (updateUI) {
//			C_KOPhase koPhase = new C_KOPhase(getShell(), SWT.NONE, koRound);
//			koRound.setGui(koPhase);
//			gruppenComps.forEach(cgruppe -> cgruppe.refresh(null));
//			koPhase.refresh();
//		}
		
		return koRound;
	}
	
	public List<Team> getAF() {
		return Arrays.asList(gruppen.get(0).getTeam(1), gruppen.get(1).getTeam(2), // A1-B2
				gruppen.get(2).getTeam(1), gruppen.get(3).getTeam(2), // C1-D2
				gruppen.get(1).getTeam(1), gruppen.get(0).getTeam(2), // B1-A2
				gruppen.get(3).getTeam(1), gruppen.get(2).getTeam(2), // D1-C2
				gruppen.get(4).getTeam(1), gruppen.get(5).getTeam(2), // E1-F2
				gruppen.get(6).getTeam(1), gruppen.get(7).getTeam(2), // G1-H2
				gruppen.get(5).getTeam(1), gruppen.get(4).getTeam(2), // F1-E2
				gruppen.get(7).getTeam(1), gruppen.get(6).getTeam(2) // H1-G2
		);
	}
	
	@Override
	public List<Match> matches() {
		return gruppen.stream().flatMap(g -> g.getMatches().stream()).collect(Collectors.toList());
	}
}
