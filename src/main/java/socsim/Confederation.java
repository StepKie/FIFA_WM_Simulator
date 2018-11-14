package socsim;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Confederation {
	
	UEFA("Europe", 14),
	CAF("Africa", 5),
	CONMEBOL("South America", 5),
	CONCACAF("North and Middle America", 3),
	OFC("Australia and Oceania", 1),
	AFC("Asia", 5);
	
	/** Human-readable name */
	public final String name;
	/** Participants from this Federation in the WC */
	public final int noOfParticipants;
	
	/**
	 * Convenience method to find a Confederation of the given name. If no Terrain
	 * of the exact same name exists, <code>null</code> is returned
	 */
	public static Confederation fromString(String name) {
		return Stream.of(Confederation.values()).filter(t -> t.name().equalsIgnoreCase(name)).findAny().orElse(null);
	}
}
