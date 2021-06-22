import javax.swing.table.DefaultTableModel;
public class termtm extends DefaultTableModel {
	public termtm (Object Mezonevek[], int rows){
		super(Mezonevek, rows);
	}

	public boolean isCellEditable(int row, int col) {
		if (col==0) return true; else return false;
	}

	public Class<?> getColumnClass(int index){
	    if (index==0) return Boolean.class;
	    else if (index==2) return(String.class);
	    else return(Integer.class);
	}

}
