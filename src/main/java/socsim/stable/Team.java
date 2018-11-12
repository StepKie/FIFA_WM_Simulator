package socsim.stable;

import org.eclipse.swt.graphics.Image;

import lombok.Value;
import socsim.Confederation;

@Value
public class Team implements Comparable<Team> {
	private String id;
	private String name;
	int elo;
	Confederation confed;
	private Image flag;
	
	@Override
	public int compareTo(Team other) {
		return id.compareTo(other.id);
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	String getName() {
		return name;
	}
}
