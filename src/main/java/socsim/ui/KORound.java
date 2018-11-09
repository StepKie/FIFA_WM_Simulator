package socsim.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import socsim.stable.Match;

/**
 * TODO Stupid copy and paste from CGruppe
 */
public class KORound extends Composite {

	private Label[] result = new Label[8];
	private List<Match> matches;
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public KORound(Composite parent, int style, String roundName, List<Match> matches) {
		super(parent, style);
		this.matches = matches;

		setLayout(new GridLayout(4, false));
		new Label(this, SWT.NONE);

		Label lblGruppenname = new Label(this, SWT.NONE);
		lblGruppenname.setText(roundName);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		emptyRow();

		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			new Label(this, SWT.NONE);
			Label pairing = new Label(this, SWT.NONE);
			String text = m.getHomeTeam().getId() + " - " + m.getGuestTeam().getId();
			pairing.setText(text);
			new Label(this, SWT.NONE);
			result[i] = new Label(this, SWT.NONE);
			result[i].setText("-:-");
		}

		refresh();
	}

	private void emptyRow() {
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}


	public void refresh() {

		for (Match m : matches) {
			if (m.isFinished()) {
				result[matches.indexOf(m)].setText(m.getHomeScore() + ":" + m.getGuestScore());
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
