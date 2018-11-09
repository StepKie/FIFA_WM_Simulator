package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import socsim.stable.Group;
import socsim.stable.Match;
import socsim.stable.Table.Row;

public class CGruppe extends Composite {
	private Group gruppe;

	Label[] teamName = new Label[4];
	Label[] teamPoints = new Label[4];
	Label[] teamGoals = new Label[4];
	Label[] result = new Label[6];
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CGruppe(Composite parent, int style, Group gruppe) {
		super(parent, style);
		this.gruppe = gruppe;
		setLayout(new GridLayout(4, false));
		new Label(this, SWT.NONE);

		Label lblGruppenname = new Label(this, SWT.NONE);
		lblGruppenname.setText(gruppe.getName());
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		emptyRow();
		createRow(0);
		createRow(1);
		createRow(2);
		createRow(3);

		emptyRow();

		createMatches();
		refresh();
	}

	private void emptyRow() {
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}

	private void createRow(int i) {
		Label position = new Label(this, SWT.NONE);
		position.setText(Integer.toString(i + 1));
		// TODO Do all that stuff by parsing from Table row ...
		teamName[i] = new Label(this, SWT.NONE);
		teamPoints[i] = new Label(this, SWT.NONE);
		teamGoals[i] = new Label(this, SWT.NONE);
	}

	private void createMatches() {
		for (int i = 0; i < gruppe.getMatches().size(); i++) {
			Match m = gruppe.getMatches().get(i);
			new Label(this, SWT.NONE);
			Label pairing = new Label(this, SWT.NONE);
			String text = m.getHomeTeam().getId() + " - " + m.getGuestTeam().getId();
			pairing.setText(text);
			new Label(this, SWT.NONE);
			result[i] = new Label(this, SWT.NONE);
			result[i].setText("-:-");
		}
	}

	public void refresh() {
		gruppe.getTable().refresh();
		for (int i = 0; i <= 3; i++) {
			Row r = gruppe.getTable().getRows().get(i);
			teamName[i].setText(r.getTeam().toString());
			teamPoints[i].setText(Integer.toString(r.getPoints()));
			teamGoals[i].setText(r.getGoalsFor() + ":" + r.getGoalsAgainst());
		}

		for (int i = 0; i < result.length; i++) {
			Match m = gruppe.getMatches().get(i);
			if (m.isFinished()) {
				result[i].setText(m.getHomeScore() + ":" + m.getGuestScore());
			}
			
		}
		// Without this call to layout, labels are not refreshed correctly
		layout();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
