package ui;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;

public final class GruppenFactory {
	/**
	 * @wbp.factory
	 */
	public static WMGruppe createWMGruppe(Composite parent) {
		WMGruppe gruppe = new WMGruppe(parent, SWT.NONE);
		gruppe.setLayoutData(new RowData(137, 86));
		return gruppe;
	}
}