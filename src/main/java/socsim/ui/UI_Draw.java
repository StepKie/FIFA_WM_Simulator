package socsim.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import lombok.extern.slf4j.Slf4j;
import socsim.phase.Draw;

@Slf4j
public class UI_Draw extends UI_Phase<Draw> {
	
	private int revealed = -1;
	
	private Shell shell;
	
	List<C_Gruppe> gruppenComps = new ArrayList<>();
	
	public UI_Draw(Shell parent) {
		
		super(new Draw(8, 4));
		this.shell = parent;
		delegate.getGruppen().forEach(g -> gruppenComps.add(C_Gruppe.createWMGruppe(shell, g)));
	}
	
	@Override
	public void step() {
		revealed++;
		refresh();
		
	}
	
	@Override
	public boolean isFinished() {
		return revealed == delegate.num_groups * delegate.teamsInGrp - 1;
	}
	
	@Override
	public UI_Vorrunde createNextRound() {
		assert (isFinished());
		log.info("Ziehung vorbei");
		
		return new UI_Vorrunde(delegate.createNextRound(), gruppenComps);
	}
	
	@Override
	public void refresh() {
		int gruppe = revealed % delegate.num_groups;
		int index = revealed / delegate.num_groups;
		gruppenComps.get(gruppe).reveal(index);
		
		return;
	}
}
