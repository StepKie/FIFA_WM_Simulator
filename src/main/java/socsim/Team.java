package socsim;

import lombok.Value;

@Value
public class Team {
	
	String name;
	String country_code;
	
	int elo;
	
	Confederation confed;
	
	// Group, Place
}
