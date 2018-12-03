package socsim;

import java.io.Serializable;
import java.util.Comparator;

import org.eclipse.swt.graphics.Image;

import lombok.Value;

@Value
public class Team implements Comparable<Team>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int elo;
	private Confederation confed;
	private transient Image flag;
	
	public static Comparator<Team> BY_NO_OF_PARTICIPANTS = Comparator.comparingInt(t -> t.getConfed().noOfParticipants);
	
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
