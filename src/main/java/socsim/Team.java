package socsim;

import java.util.Comparator;

import org.eclipse.swt.graphics.Image;

import lombok.ToString;
import lombok.Value;

@Value
public class Team implements Comparable<Team> {
	private String id;
	private String name;
	private int elo;
	private Confederation confed;
	@ToString.Exclude private Image flag;
	
	@Override
	public int compareTo(Team other) {
		return Comparator.nullsFirst(Comparator.comparing(Team::getId)).compare(this, other);
	}
	
	public String getTooltip() {
		return toString() + "\nWertungszahl: " + getElo();
	}
}
