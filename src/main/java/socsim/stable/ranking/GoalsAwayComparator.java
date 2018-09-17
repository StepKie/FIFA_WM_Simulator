package socsim.stable.ranking;

import socsim.stable.Table.Row;

public class GoalsAwayComparator extends TableRowComparator {
	public GoalsAwayComparator() {
		super();
	}

	public GoalsAwayComparator(TableRowComparator child, TableRowComparator successor) {
		super(child, successor);
	}

	@Override
	int getComparisonValue(Row row) {
		return -row.getGoalsAway();
	}
}
