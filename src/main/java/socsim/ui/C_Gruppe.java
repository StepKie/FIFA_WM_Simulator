package socsim.ui;

import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import socsim.stable.Group;
import socsim.stable.Match;
import socsim.stable.Table.Row;

public class C_Gruppe extends Composite {
	private Group gruppe;
	
	Label[] teamName = new Label[4];
	Label[] teamPoints = new Label[4];
	Label[] teamGoals = new Label[4];
	Label[] pairing = new Label[6];
	Label[] result = new Label[6];
	
	public C_Gruppe(Composite parent, int style) {
		this(parent, style, GruppenFactory.getTestGroup());
	}
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public C_Gruppe(Composite parent, int style, Group gruppe) {
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
		
		refresh(null);
		setInvisible();
	}
	
	private void emptyRow() {
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}
	
	private void createRow(int i) {
		CLabel position = new CLabel(this, SWT.NONE);
		position.setImage(gruppe.getTeam(2).getFlag());
		// Label position = new Label(this, SWT.NONE);
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
			pairing[i] = new Label(this, SWT.NONE);
			String text = m.getHomeTeam().getId() + " - " + m.getGuestTeam().getId();
			pairing[i].setText(text);
			pairing[i].setData(m);
			new Label(this, SWT.NONE);
			result[i] = new Label(this, SWT.NONE);
			result[i].setText("-:-");
		}
	}
	
	public void refresh(Match played) {
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
		
		if (played != null) {
			for (Label matchLabel : pairing) {
				int colorId = matchLabel.getData().equals(played) ? SWT.COLOR_RED : SWT.COLOR_WIDGET_FOREGROUND;
				matchLabel.setForeground(Display.getCurrent().getSystemColor(colorId));
				
			}
		}
		// Without this call to layout, labels are not refreshed correctly
		layout();
	}
	
	private void setInvisible() {
		
		Stream.of(teamName).forEach(l -> l.setVisible(false));
		Stream.of(teamPoints).forEach(l -> l.setVisible(false));
		Stream.of(teamGoals).forEach(l -> l.setVisible(false));
		Stream.of(pairing).forEach(l -> l.setVisible(false));
		Stream.of(result).forEach(l -> l.setVisible(false));
	}
	
	public void reveal(int index) {
		teamName[index].setVisible(true);
		
		if (index == 3) {
			Stream.of(teamPoints).forEach(l -> l.setVisible(true));
			Stream.of(teamGoals).forEach(l -> l.setVisible(true));
			Stream.of(pairing).forEach(l -> l.setVisible(true));
			Stream.of(result).forEach(l -> l.setVisible(true));
		}
	}
}
