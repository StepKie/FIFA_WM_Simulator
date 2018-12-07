package socsim;

import java.io.Serializable;
import java.util.Comparator;

import org.eclipse.swt.graphics.Image;

import lombok.Value;
import socsim.io.Fussball_IO;

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
	
	public Image getFlag() {
		return flag != null ? flag : Fussball_IO.getSmallFlag(this);
	}
	
	public String getTooltip() {
		return toString() + "\nWertungszahl: " + getElo();
	}
	
	public String getShortName() {
		return (name.length() > 15) ? name.substring(0, 15).concat(".") : name;
	}
	
}
