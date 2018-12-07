package socsim.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import socsim.io.Fussball_IO;

public class C_Finale extends C_KOMatch {
	
	public C_Finale(Composite parent, int style, int vertSpan, int order) {
		super(parent, style, false, vertSpan, order);
		
		setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 2));
		setLayout(new GridLayout(3, false));
		
		GridData gd_lbl_finale_t1 = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_lbl_finale_t1.widthHint = 170;
		gd_lbl_finale_t1.heightHint = 60;
		label_team1.setLayoutData(gd_lbl_finale_t1);
		label_team2.setLayoutData(gd_lbl_finale_t1);
		
		lblNv.moveBelow(label_score2);
		
	}
	
	@Override
	public void refresh() {
		super.refresh();
		if (match == null || !match.isFinished())
			return;
		String resultString = match.getHomeScore() + ":" + match.getGuestScore();
		resultString = resultString.concat(match.isVerlängerung() ? " (n.V.)" : "");
		label_score1.setText(resultString);
		label_score2.setVisible(false);
		lblNv.setVisible(false);
		int colorId = SWT.COLOR_DARK_GREEN;
		CLabel winner = match.getWinner().equals(match.getHomeTeam()) ? label_team1 : label_team2;
		winner.setForeground(Display.getCurrent().getSystemColor(colorId));
		winner.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		
		winner.setImage(Fussball_IO.getLargeFlag(match.getWinner()));
		layout(true);
		
	}
}
