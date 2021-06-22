import javax.swing.table.DefaultTableModel;

public class pantm extends DefaultTableModel {
	public pantm (Object Mezonevek[], int rows){
		super(Mezonevek, rows);
	}

	public boolean isCellEditable(int row, int col) {
		if (col==0) return true; else return false;
	}

	public Class<?> getColumnClass(int index){
	    if (index==0) return Boolean.class;
	    else if (index==2 || index==3 || index==4) return(String.class);
	    else return(Integer.class);
	}

}
