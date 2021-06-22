import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class panaszPanel extends JDialog {
	private JTable table;
	private pantm ptm;
	private JTextField kod;
	private JTextField datum;
	private JTextField szoveg;
	private JTextField szemely;
	private JTextField tkod;
	private JTextField ok;


	public panaszPanel(pantm betm) {
		ptm=betm;
		setBounds(100, 100, 550, 358);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 514, 124);
		getContentPane().add(scrollPane);
		
		table = new JTable(ptm);
		scrollPane.setViewportView(table);
		
		JButton bezar = new JButton("Bez\u00E1r\u00E1s");
		bezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		bezar.setBounds(435, 284, 89, 23);
		getContentPane().add(bezar);
		
		JLabel lblNv = new JLabel("K\u00F3d:");
		lblNv.setBounds(10, 146, 46, 14);
		getContentPane().add(lblNv);
		
		kod = new JTextField();
		kod.setColumns(10);
		kod.setBounds(75, 143, 58, 20);
		getContentPane().add(kod);
		
		JLabel lblDarab = new JLabel("D\u00E1tum:");
		lblDarab.setBounds(10, 171, 46, 14);
		getContentPane().add(lblDarab);
		
		datum = new JTextField();
		datum.setColumns(10);
		datum.setBounds(75, 168, 86, 20);
		getContentPane().add(datum);
		
		JLabel lblr = new JLabel("Sz\u00F6veg:");
		lblr.setBounds(10, 199, 65, 14);
		getContentPane().add(lblr);
		
		szoveg = new JTextField();
		szoveg.setColumns(10);
		szoveg.setBounds(75, 196, 449, 20);
		getContentPane().add(szoveg);
		
		JLabel lblGyrtkd = new JLabel("Szem\u00E9ly:");
		lblGyrtkd.setBounds(10, 224, 65, 14);
		getContentPane().add(lblGyrtkd);
		
		szemely = new JTextField();
		szemely.setColumns(10);
		szemely.setBounds(75, 222, 201, 20);
		getContentPane().add(szemely);
		
		JLabel lblKategria = new JLabel("Term\u00E9kK\u00F3d:");
		lblKategria.setBounds(10, 256, 78, 14);
		getContentPane().add(lblKategria);
		
		tkod = new JTextField();
		tkod.setColumns(10);
		tkod.setBounds(85, 253, 48, 20);
		getContentPane().add(tkod);
		
		JButton ujadat = new JButton("Felvisz");
		ujadat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!abkp.filled(kod)) abkp.SMD("A Kód mezõ üres!", 0);
				else if (!abkp.filled(datum)) abkp.SMD("A Dátum mezõ üres!", 0);
				else if (!abkp.goodDate(datum)) abkp.SMD("A Dátum mezõben hibás adat van!", 0);
				else if (!abkp.filled(szoveg)) abkp.SMD("A Szöveg mezõ üres!", 0);
				else if (!abkp.filled(szemely)) abkp.SMD("A Személy mezõ üres!", 0);
				else if (abkp.filled(tkod) && !abkp.goodInt(tkod)) abkp.SMD("A Termékkód mezõben hibás adat van!", 0);
				else if (!abkp.filled(ok)) abkp.SMD("Az Elintézve mezõ üres!", 0);
				else if (!abkp.goodInt(ok)) abkp.SMD("Az Elintézve mezõben hibás adat van!", 0);
				else if (abkp.StoI(abkp.RF(ok))<0 || abkp.StoI(abkp.RF(ok))>1) abkp.SMD("Az Elintézve mezõben nem megengedett számérték van!", 0);
				else {
					String tks="null";
					if (abkp.filled(tkod)) tks=abkp.RF(tkod);
					abkp.Connect();
					String sqlp="insert into panasz values("+abkp.RF(kod)+", TO_DATE('"+abkp.RF(datum)+"','YY.MM.DD'), '"+
							abkp.RF(szoveg)+"', '"+abkp.RF(szemely)+"', "+tks+", "+abkp.RF(ok)+")";
					abkp.Command(sqlp);
					abkp.panaszLista();
					abkp.DF(kod); abkp.DF(szoveg); abkp.DF(szemely);abkp.DF(tkod);abkp.DF(ok);
					abkp.disConnect();

				}
			}
		});
		ujadat.setBounds(75, 284, 86, 23);
		getContentPane().add(ujadat);
		
		JButton modosit = new JButton("M\u00F3dos\u00EDt");
		modosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        int db=0, x=0, jel=0;
		        for(x = 0; x < ptm.getRowCount(); x++)
		        if ((Boolean)ptm.getValueAt(x,0)) {db++; jel=x;}
		    	if (db==0) abkp.SMD("Nincs kijelölve egyetlen módosítandó rekord sem!",0);
		    	if (db>1) abkp.SMD("Több módosítandó rekord van kijelölve!",0);
		    	if (db==1){
		    		if (!abkp.filled(datum) && !abkp.filled(szoveg) && !abkp.filled(tkod) && !abkp.filled(szemely) && !abkp.filled(ok)) abkp.SMD("Nincs megadva módosítandó adat!",0);
		    		else if (abkp.filled(datum) && !abkp.goodDate(datum)) abkp.SMD("A Dátum mezõben hibás adat van!", 0);
		    		else if (abkp.filled(tkod) && !abkp.goodInt(tkod)) abkp.SMD("A Termékkód mezõben hibás adat van!", 0);
		    		else if (abkp.filled(ok) && !abkp.goodInt(ok)) abkp.SMD("Az Elintézve mezõben hibás adat van!", 0);
		    		else if (abkp.filled(ok) && abkp.goodInt(ok) && (abkp.StoI(abkp.RF(ok))<0 || abkp.StoI(abkp.RF(ok))>1)) abkp.SMD("Az Elintézve mezõben nem megengedett számérték van!", 0);
		    		else {
		    			String sqlp="";
		    			String sqlp1="update panasz set ";
		    			String sqlp2=" where pkod= "+ptm.getValueAt(jel,1).toString();
		    			abkp.Connect();
		    			if (abkp.filled(datum)) {
		    				sqlp=sqlp1+"pdatum = TO_DATE('"+abkp.RF(datum)+"','YY.MM.DD')"+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(datum);
		    			}
		    			if (abkp.filled(szoveg)) {
		    				sqlp=sqlp1+"szoveg = '"+abkp.RF(szoveg)+"'"+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(szoveg);
		    			}
		    			if (abkp.filled(szemely)) {
		    				sqlp=sqlp1+"pnev = '"+abkp.RF(szemely)+"'"+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(szemely);
		    			}
		    			if (abkp.filled(tkod)) {
		    				sqlp=sqlp1+"tkod = "+abkp.RF(tkod)+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(tkod);
		    			}
		    			if (abkp.filled(ok)) {
		    				sqlp=sqlp1+"ok = "+abkp.RF(ok)+sqlp2;
		    				abkp.Command(sqlp);
		    				abkp.DF(ok);
		    			}
		    			abkp.panaszLista();
		    			abkp.disConnect();
		    		}
		    	}
		    	
			}
		});
		modosit.setBounds(190, 284, 86, 23);
		getContentPane().add(modosit);
		
		JButton torol = new JButton("T\u00F6r\u00F6l");
		torol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int db=0, x=0;
		        for(x = 0; x < ptm.getRowCount(); x++)
		        if ((Boolean)ptm.getValueAt(x,0)) db++;
		    	if (db==0) abkp.SMD("Nincs kijelölve a törlendõ rekord!",0);
		    	else {
					abkp.Connect();					
		    		for (int i=0; i<ptm.getRowCount(); i++)  
		                 if ((Boolean)ptm.getValueAt(i,0)) {
		                	String sqlp="delete from panasz where pkod="+ptm.getValueAt(i,1).toString();
		 					abkp.Command(sqlp);
							abkp.panaszLista();
							abkp.disConnect();
		                 }
		    	}
			}
		});
		torol.setBounds(317, 284, 86, 23);
		getContentPane().add(torol);
		
		JLabel lblElintzve = new JLabel("Elint\u00E9zve:");
		lblElintzve.setBounds(173, 256, 58, 14);
		getContentPane().add(lblElintzve);
		
		ok = new JTextField();
		ok.setColumns(10);
		ok.setBounds(230, 253, 46, 20);
		getContentPane().add(ok);
		
	    TableColumn tc = null;
	    for (int i = 0; i < 6; i++) {
	       tc = table.getColumnModel().getColumn(i);
	       if (i==3) tc.setPreferredWidth(350); 
	       else {tc.setPreferredWidth(100);}
	    }

	}
}
