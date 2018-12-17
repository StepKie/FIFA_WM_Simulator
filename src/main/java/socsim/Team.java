package socsim;

import java.io.Serializable;
import java.util.Comparator;

import org.eclipse.swt.graphics.Image;

import lombok.Value;
import socsim.io.Fussball_IO;

@Value
public class Team implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String id;
	String name;
	int elo;
	Confederation confed;
	transient Image flag;
	
	public static Comparator<Team> BY_ID = Comparator.comparing(Team::getId);
	public static Comparator<Team> BY_NO_OF_PARTICIPANTS = Comparator.comparingInt(t -> t.getConfed().noOfParticipants);
	
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
