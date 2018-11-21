package socsim;

import java.util.Comparator;

import org.eclipse.swt.graphics.Image;

import lombok.Value;

@Value
public class Team implements Comparable<Team> {
	private String id;
	private String name;
	int elo;
	Confederation confed;
	private Image flag;
	
	@Override
	public int compareTo(Team other) {
		return Comparator.nullsFirst(Comparator.comparing(Team::getId)).compare(this, other);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getTooltip() {
		return toString() + "\nWertungszahl: " + getElo();
	}
}
