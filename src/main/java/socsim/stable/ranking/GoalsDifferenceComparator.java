package socsim.stable.ranking;

import socsim.stable.Table.Row;

public class GoalsDifferenceComparator extends TableRowComparator {
	public GoalsDifferenceComparator() {
		super();
	}

	public GoalsDifferenceComparator(TableRowComparator child, TableRowComparator successor) {
		super(child, successor);
	}

	@Override
	int getComparisonValue(Row row) {
		return -row.getGoalsDifference();
	}
}
