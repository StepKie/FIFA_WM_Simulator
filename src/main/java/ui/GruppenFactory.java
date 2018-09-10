package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;

import socsim.Group;

public final class GruppenFactory {
	/**
	 * @wbp.factory
	 */
	public static WMGruppe createWMGruppe(Composite parent, Group grp) {
		WMGruppe gruppe = new WMGruppe(parent, SWT.NONE);
		gruppe.setLayoutData(new RowData(137, 86));
		return gruppe;
	}
}