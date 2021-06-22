import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class termekPanel extends JDialog {
	private JTable table;
	private termtm ttm;
	private JTextField nev;
	private JTextField darab;
	private JTextField ar;
	private JTextField gyarto;
	private JTextField kateg;


	public termekPanel(termtm betm) {
		ttm=betm;
		setBounds(100, 100, 550, 346);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 514, 124);
		getContentPane().add(scrollPane);
		
		table = new JTable(ttm);
		scrollPane.setViewportView(table);
		
		JButton bezar = new JButton("Bez\u00E1r\u00E1s");
		bezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		bezar.setBounds(435, 281, 89, 23);
		getContentPane().add(bezar);
		
		JLabel lblNv = new JLabel("N\u00E9v:");
		lblNv.setBounds(10, 146, 46, 14);
		getContentPane().add(lblNv);
		
		nev = new JTextField();
		nev.setColumns(10);
		nev.setBounds(47, 143, 142, 20);
		getContentPane().add(nev);
		
		JLabel lblDarab = new JLabel("Darab:");
		lblDarab.setBounds(10, 171, 46, 14);
		getContentPane().add(lblDarab);
		
		darab = new JTextField();
		darab.setColumns(10);
		darab.setBounds(75, 168, 58, 20);
		getContentPane().add(darab);
		
		JLabel lblr = new JLabel("\u00C1r:");
		lblr.setBounds(10, 199, 38, 14);
		getContentPane().add(lblr);
		
		ar = new JTextField();
		ar.setColumns(10);
		ar.setBounds(47, 196, 86, 20);
		getContentPane().add(ar);
		
		JLabel lblGyrtkd = new JLabel("Gy\u00E1rt\u00F3K\u00F3d:");
		lblGyrtkd.setBounds(10, 224, 77, 14);
		getContentPane().add(lblGyrtkd);
		
		gyarto = new JTextField();
		gyarto.setColumns(10);
		gyarto.setBounds(96, 222, 38, 20);
		getContentPane().add(gyarto);
		
		JLabel lblKategria = new JLabel("Kateg\u00F3riaK\u00F3d:");
		lblKategria.setBounds(10, 256, 89, 14);
		getContentPane().add(lblKategria);
		
		kateg = new JTextField();
		kateg.setColumns(10);
		kateg.setBounds(95, 253, 38, 20);
		getContentPane().add(kateg);
		
		JButton ujadat = new JButton("Felvisz");
		ujadat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!abkp.filled(nev)) abkp.SMD("A Név mezõ üres!", 0);
				else if (!abkp.filled(darab)) abkp.SMD("A Darab mezõ üres!", 0);
				else if (!abkp.goodInt(darab)) abkp.SMD("A Darab mezõben hibás adat van!", 0);
				else if (!abkp.filled(ar)) abkp.SMD("Az Ár mezõ üres!", 0);
				else if (!abkp.goodInt(ar)) abkp.SMD("Az Á mezõben hibás adat van!", 0);
				else if (!abkp.filled(gyarto)) abkp.SMD("A Gyártó mezõ üres!", 0);
				else if (!abkp.goodInt(gyarto)) abkp.SMD("A Gyártó mezõben hibás adat van!", 0);
				else if (!abkp.filled(kateg)) abkp.SMD("A Kategória mezõ üres!", 0);
				else if (!abkp.goodInt(kateg)) abkp.SMD("A Kategória mezõben hibás adat van!", 0);
				else {
					abkp.Connect();
					String sqlp="insert into termekek (tnev,darab,ar,gykod,kkod) values('"+abkp.RF(nev)+"', "+abkp.StoI(abkp.RF(darab))+", "+abkp.StoI(abkp.RF(ar))+
							", "+abkp.StoI(abkp.RF(gyarto))+", "+abkp.StoI(abkp.RF(kateg))+")";
					abkp.Command(sqlp);
					abkp.termekLista();
					abkp.disConnect();

				}
			}
		});
		ujadat.setBounds(33, 281, 86, 23);
		getContentPane().add(ujadat);
		
		JButton modosit = new JButton("M\u00F3dos\u00EDt");
		modosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        int db=0, x=0, jel=0;
		        for(x = 0; x < ttm.getRowCount(); x++)
		        if ((Boolean)ttm.getValueAt(x,0)) {db++; jel=x;}
		    	if (db==0) abkp.SMD("Nincs kijelölve a egyetlen módosítandó rekord sem!",0);
		    	if (db>1) abkp.SMD("Több módosítandó rekord van kijelölve!",0);
		    	if (db==1){
		    		if (!abkp.filled(nev) && !abkp.filled(darab) && !abkp.filled(ar) && !abkp.filled(gyarto) && !abkp.filled(kateg)) abkp.SMD("Nincs megadva módosítandó adat!",0);
		    		else if (abkp.filled(darab) && abkp.goodInt(darab)) abkp.SMD("A Darab mezõben hibás adat van!", 0);
		    		else if (abkp.filled(ar) && abkp.goodInt(ar)) abkp.SMD("Az Ár mezõben hibás adat van!", 0);
		    		else if (abkp.filled(gyarto) && abkp.goodInt(gyarto)) abkp.SMD("A Gyártó mezõben hibás adat van!", 0);
		    		else if (abkp.filled(kateg) && abkp.goodInt(kateg)) abkp.SMD("A Kategória mezõben hibás adat van!", 0);
		    		else {
		    			String sqlp="";
		    			String sqlp1="update termekek set ";
		    			String sqlp2=" where tkod= "+ttm.getValueAt(jel,1).toString();
		    			abkp.Connect();
		    			if (abkp.filled(nev)) {
		    				sqlp=sqlp1+"tnev = '"+abkp.RF(nev)+"'"+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(nev);
		    			}
		    			if (abkp.filled(darab)) {
		    				sqlp=sqlp1+"darab = "+abkp.RF(darab)+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(darab);
		    			}
		    			if (abkp.filled(ar)) {
		    				sqlp=sqlp1+"ar = "+abkp.RF(ar)+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(ar);
		    			}
		    			if (abkp.filled(gyarto)) {
		    				sqlp=sqlp1+"gyarto = "+abkp.RF(gyarto)+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(gyarto);
		    			}
		    			if (abkp.filled(kateg)) {
		    				sqlp=sqlp1+"kateg = "+abkp.RF(kateg)+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(kateg);
		    			}
		    			abkp.termekLista();
		    			abkp.disConnect();
		    		}
		    	}
		    	
			}
		});
		modosit.setBounds(129, 281, 86, 23);
		getContentPane().add(modosit);
		
		JButton torol = new JButton("T\u00F6r\u00F6l");
		torol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int db=0, x=0;
		        for(x = 0; x < ttm.getRowCount(); x++)
		        if ((Boolean)ttm.getValueAt(x,0)) db++;
		    	if (db==0) abkp.SMD("Nincs kijelölve a törlendõ rekord!",0);
		    	else {
					abkp.Connect();					
		    		for (int i=0; i<ttm.getRowCount(); i++)  
		                 if ((Boolean)ttm.getValueAt(i,0)) {
		                	String sqlp="delete from termekek where tkod="+abkp.StoI(ttm.getValueAt(i,1).toString());
		 					abkp.Command(sqlp);
							abkp.termekLista();
							abkp.disConnect();
		                 }
		    	}
			}
		});
		torol.setBounds(248, 281, 86, 23);
		getContentPane().add(torol);
		
	    TableColumn tc = null;
	    for (int i = 0; i < 6; i++) {
	       tc = table.getColumnModel().getColumn(i);
	       if (i==2) tc.setPreferredWidth(350); 
	       else {tc.setPreferredWidth(100);}
	    }

	}
}
