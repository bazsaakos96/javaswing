import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class naploPanel extends JDialog {
	private JTable table;
	private naplotm ntm;


	public naploPanel(naplotm betm) {
		ntm=betm;
		setBounds(100, 100, 550, 219);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 514, 124);
		getContentPane().add(scrollPane);
		
		table = new JTable(ntm);
		scrollPane.setViewportView(table);
		
		JButton bezar = new JButton("Bez\u00E1r\u00E1s");
		bezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		bezar.setBounds(210, 146, 89, 23);
		getContentPane().add(bezar);
		
	    TableColumn tc = null;
	    for (int i = 0; i < 2; i++) {
	       tc = table.getColumnModel().getColumn(i);
	       if (i==0) tc.setPreferredWidth(50); 
	       else {tc.setPreferredWidth(250);}
	    }

	}
}
