package socsim.ui;

import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import lombok.Getter;
import socsim.Group;
import socsim.GruppenFactory;
import socsim.Match;
import socsim.Table.Row;

public class C_Gruppe extends Composite {
	
	/**
	 * @wbp.factory
	 */
	public static C_Gruppe createWMGruppe(Composite parent, Group grp) {
		C_Gruppe gruppe = new C_Gruppe(parent, SWT.BORDER, grp);
		return gruppe;
	}
	
	@Getter private Group gruppe;
	
	C_Row[] teams = new C_Row[4];
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
		setLayout(new GridLayout(2, false));
		setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		
		Label lblGruppenname = new Label(this, SWT.CENTER);
		lblGruppenname.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 4, 1));
		lblGruppenname.setText(gruppe.getName());
		
		emptyRow();
		
		for (int i = 0; i <= 3; i++) {
			teams[i] = new C_Row(this, SWT.NONE);
		}
		emptyRow();
		
		createMatches();
		
		refresh(null);
		setInvisible();
	}
	
	private void emptyRow() {
		Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
	}
	
	private void createMatches() {
		for (int i = 0; i < gruppe.getMatches().size(); i++) {
			Match m = gruppe.getMatches().get(i);
			pairing[i] = new Label(this, SWT.NONE);
			pairing[i].setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
			
			String text = m.getHomeTeam().getShortName() + " - " + m.getGuestTeam().getShortName();
			pairing[i].setText(text);
			pairing[i].setData(m);
			pairing[i].setVisible(false);
			
			result[i] = new Label(this, SWT.NONE);
			result[i].setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
			result[i].setText("-:-");
			result[i].setVisible(false);
		}
	}
	
	public void refresh(Match played) {
		for (int i = 0; i <= 3; i++) {
			Row r = gruppe.getTable().getRows().get(i);
			
			teams[i].teamName.setText(r.getTeam().getShortName());
			teams[i].teamName.setImage(r.getTeam().getFlag());
			teams[i].teamName.setToolTipText(r.getTeam().getTooltip());
			teams[i].teamPoints.setText(Integer.toString(r.getPoints()));
			teams[i].teamGoals.setText(r.getGoalsFor() + ":" + r.getGoalsAgainst());
		}
		
		for (int i = 0; i < result.length; i++) {
			Match m = gruppe.getMatches().get(i);
			if (m.isFinished()) {
				result[i].setText(m.getHomeScore() + ":" + m.getGuestScore());
			}
			
		}
		
		for (Label matchLabel : pairing) {
			int colorId = matchLabel.getData().equals(played) ? SWT.COLOR_RED : SWT.COLOR_WIDGET_FOREGROUND;
			matchLabel.setForeground(Display.getCurrent().getSystemColor(colorId));
			
		}
		
		// Layout (for example after reappearing position etc.
		layout(true);
	}
	
	private void setInvisible() {
		for (C_Row row : teams) {
			row.position.setVisible(false);
			row.teamGoals.setVisible(false);
			row.teamName.setVisible(false);
			row.teamPoints.setVisible(false);
		}
	}
	
	public void reveal(int index) {
		teams[index].position.setText(Integer.toString(index + 1));
		teams[index].teamName.setVisible(true);
		
		if (index == 3) {
			for (C_Row row : teams) {
				row.teamGoals.setVisible(true);
				row.teamPoints.setVisible(true);
				row.position.setVisible(true);
			}
			
			Stream.of(pairing).forEach(l -> l.setVisible(true));
			Stream.of(result).forEach(l -> l.setVisible(true));
		}
		
		Stream.of(teams).forEach(C_Row::layout);
		layout();
	}
	
	public class C_Row extends Composite {
		Label position;
		CLabel teamName;
		Label teamPoints;
		Label teamGoals;
		
		public C_Row(Composite parent, int style) {
			super(parent, style);
			setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
			GridLayout lo = new GridLayout(4, false);
			lo.verticalSpacing = 0;
			lo.marginHeight = 0;
			lo.marginWidth = 0;
			setLayout(lo);
			
			position = new Label(this, SWT.NONE);
			teamName = new CLabel(this, SWT.NONE);
			teamName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			teamPoints = new Label(this, SWT.NONE);
			teamGoals = new Label(this, SWT.NONE);
		}
	}
	
	/**
	 * @wbp.factory
	 */
	public static C_Gruppe.C_Row createRow(Composite parent, int style) {
		return new C_Gruppe(parent, style).new C_Row(parent, style);
	}
}
