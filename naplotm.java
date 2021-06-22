import javax.swing.table.DefaultTableModel;

public class naplotm extends DefaultTableModel {
	public naplotm (Object Mezonevek[], int rows){
		super(Mezonevek, rows);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Class<?> getColumnClass(int index){
	   return(String.class);
	}

}
