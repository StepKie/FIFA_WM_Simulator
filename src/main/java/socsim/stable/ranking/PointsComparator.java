package socsim.stable.ranking;

import socsim.stable.Table.Row;

public class PointsComparator extends TableRowComparator {
	public PointsComparator() {
		super();
	}

	public PointsComparator(TableRowComparator child, TableRowComparator successor) {
		super(child, successor);
	}

	@Override
	int getComparisonValue(Row row) {
		return -row.getPoints();
	}
}
