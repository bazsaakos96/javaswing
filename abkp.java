import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class abkp extends JFrame {
   	private static Connection conn = null;
   	private static String user="system";
   	private static String pswd="retek";
   	private static String pm="Programüzenet:";
	private static termtm ttm;
	private static pantm ptm;
	private static naplotm ntm;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					abkp frame = new abkp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public abkp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 185);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton term = new JButton("Term\u00E9kek");
		term.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connect();
				termekLista();
				disConnect();
				termekPanel tp = new termekPanel(ttm);
				tp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				tp.setVisible(true);
			}
		});
		term.setBounds(10, 11, 108, 23);
		contentPane.add(term);
		
		JButton parancsg = new JButton("Parancs");
		parancsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connect();
				//Command("create or replace trigger term1 before insert on termekek for each row declare ujkod int; begin select max(tkod)+1 into ujkod from termekek; :new.tkod := ujkod; end;");
				/*
				Command("create or replace trigger panasznaplo after insert or update or delete on panasz for each row "+
				"begin if deleting then insert into naplo values(sysdate, 'Panasz törlés, pkod='||:old.pkod); "+
				"elsif inserting then insert into naplo values(sysdate, 'Panasz beszúrás, pkod='||:new.pkod); "+
				"else if :old.pdatum != :new.pdatum then "+
				"insert into naplo values(sysdate, 'Panasz Dátum módosítás, pkod='||:old.pkod||'-'||:old.pdatum||'-'||:new.pdatum); "+
				"elsif :old.pnev != :new.pnev then "+
				"insert into naplo values(sysdate, 'Panasz Személy módosítás, pkod='||:old.pkod||'-'||:old.pnev||'-'||:new.pnev); "+
				"elsif :old.tkod != :new.tkod then "+
				"insert into naplo values(sysdate, 'Panasz Termékkód módosítás, pkod='||:old.pkod||'-'||:old.tkod||'-'||:new.tkod); "+
				"elsif :old.ok != :new.ok then "+
				"insert into naplo values(sysdate, 'Panasz Elintézve módosítás, pkod='||:old.pkod||'-'||:old.ok||'-'||:new.ok); "+
				"end if; end if; end;");
				*/
				//RecDb();
				disConnect();
				
			}
		});
		parancsg.setBounds(10, 108, 108, 23);
		contentPane.add(parancsg);
		
		JButton pan = new JButton("Panaszok");
		pan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connect();
				panaszLista();
				disConnect();
				panaszPanel pp = new panaszPanel(ptm);
				pp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				pp.setVisible(true);
			}
		});
		pan.setBounds(10, 59, 108, 23);
		contentPane.add(pan);
		
		JButton bezar = new JButton("Bez\u00E1r\u00E1s");
		bezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bezar.setBounds(316, 108, 108, 23);
		contentPane.add(bezar);
		
		JButton naplo = new JButton("Napl\u00F3");
		naplo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connect();
				naploLista();
				disConnect();
				naploPanel np = new naploPanel(ntm);
				np.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				np.setVisible(true);
			}
		});
		naplo.setBounds(163, 11, 108, 23);
		contentPane.add(naplo);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBackground(Color.CYAN);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInternalMessageDialog(null, "szia");
			}
		});
		btnNewButton.setBounds(180, 100, 89, 23);
		
		contentPane.add(btnNewButton);
		
		Object cn[] = {"Jel","TKód", "TNév", "Darab", "Ár", "gykod", "kkod"};
    	ttm = new termtm(cn, 0);
		Object pn[] = {"Jel","PKód", "Dátum", "Szöveg", "Személy", "TKód", "OK"};
    	ptm = new pantm(pn, 0);
		Object nn[] = {"Dátum", "Szöveg"};
    	ntm = new naplotm(nn, 0);
	}
	//===========================================================
	public static void Connect(){	
	    try {
		    String url = "jdbc:oracle:thin:@localhost:1521:XE";
		    conn = DriverManager.getConnection(url, user, pswd);
		    //SMD("Kapcsolódás OK!", 1);
		} catch(Exception ex) {SMD("Kapcsolódási hiba:"+ex.getMessage(), 0);}
    }
	public static void disConnect(){
		try {conn.close();
		} catch(Exception ex) {SMD("Kapcsolat lezárási hiba: "+ex.getMessage(),0);}
    }
	public static void termekLista(){
		for (int i=0; i<ttm.getRowCount(); i++) {ttm.removeRow(i); i--;}
		String tnev="";
		int tkod, darab, ar, gykod, kkod;
		String sqlp= "select * from termekek";
		    try {
		    	Statement s = conn.createStatement();
			    s.executeQuery(sqlp);
			    ResultSet rs = s.getResultSet();
		        while(rs.next()) {
			        tkod = rs.getInt("tkod");
			        tnev = rs.getString("tnev");
			        darab = rs.getInt("darab");
			        ar = rs.getInt("ar");
			        gykod = rs.getInt("gykod");
			        kkod = rs.getInt("kkod");
			        ttm.addRow(new Object[]{ new Boolean(false), tkod, tnev, darab, ar, gykod, kkod});
			    }
			    rs.close();
			} catch(Exception e) {SMD("Nem sikerült betölteni az adatokat!"+e.getMessage(), 0);}
    }
	public static void panaszLista(){
		for (int i=0; i<ptm.getRowCount(); i++) {ptm.removeRow(i); i--;}
		String szoveg="", pnev="";
		int pkod, darab, ar, tkod, ok;
		String sqlp= "select * from panasz";
		    try {
		    	Statement s = conn.createStatement();
			    s.executeQuery(sqlp);
			    ResultSet rs = s.getResultSet();
		        while(rs.next()) {
			        pkod = rs.getInt("pkod");
			        java.sql.Date date = rs.getDate("pdatum");
			        szoveg = rs.getString("szoveg");
			        pnev = rs.getString("pnev");
			        tkod = rs.getInt("tkod");
			        ok = rs.getInt("ok");
			        java.util.Date cdate = new java.util.Date(date.getTime());
			        String sdate = sdf.format(cdate).toString();
			        String stkod ="";
			        if (tkod==0) stkod=""; else stkod=""+tkod;
			        ptm.addRow(new Object[]{ new Boolean(false), pkod, sdate, szoveg, pnev, stkod, ok});
			    }
			    rs.close();
			} catch(Exception e) {SMD("Nem sikerült betölteni az adatokat!"+e.getMessage(), 0);}
    }
	public static void naploLista(){
		for (int i=0; i<ntm.getRowCount(); i++) {ntm.removeRow(i); i--;}
		String szoveg="";
		String sqlp= "select * from naplo";
		    try {
		    	Statement s = conn.createStatement();
			    s.executeQuery(sqlp);
			    ResultSet rs = s.getResultSet();
		        while(rs.next()) {
			        java.sql.Date date = rs.getDate("datum");
			        szoveg = rs.getString("szoveg");
			        java.util.Date cdate = new java.util.Date(date.getTime());
			        String sdate = sdf.format(cdate).toString();
			        ntm.addRow(new Object[]{sdate, szoveg});
			    }
			    rs.close();
			} catch(Exception e) {SMD("Nem sikerült betölteni az adatokat!"+e.getMessage(), 0);}
    }
	   public int RecDb(){
			String sqlp=""; int vidb=-10;

		      	sqlp= "select count(*) db from naplo";
		     try {
		    	 Statement s = conn.createStatement();
			     s.executeQuery(sqlp);
			     ResultSet rs = s.getResultSet();
		         while(rs.next()) {
			   vidb = rs.getInt("db");
			 }
			rs.close();
			SMD("Rdb: "+vidb, 1);
			} catch(SQLException e) {SMD("Gond: "+e.getMessage(), 0);}
			return vidb;
			}
	   public void DataInsert(){
		    String sqlp1="insert into gyarto values(1, 'Samsung', 'samsung@samsung.com')";
		    String sqlp2="insert into gyarto values(2, 'LG', 'lg@lg.com')";
		    
			String sqlp3="insert into tkategoria values(1, 'TV')";
			String sqlp4="insert into tkategoria values(2, 'Telefon')";
			
			String sqlp5="insert into termekek values(1, 'Samsung UE43MU6102', 12, 125900, 1, 1)";
			String sqlp6="insert into termekek values(2, 'Samsung UE32M5002', 8, 67900, 1, 1)";
			String sqlp7="insert into termekek values(3, 'Samsung UE40M5002', 5, 95900, 1, 1)";

		     try {
		    	 Statement s = conn.createStatement(); 
		    	 s.executeUpdate(sqlp1); s.executeUpdate(sqlp2);s.executeUpdate(sqlp3);s.executeUpdate(sqlp4);
		    	 s.executeUpdate(sqlp5);s.executeUpdate(sqlp6);s.executeUpdate(sqlp7);
			     SMD("Rekord beszúrva!", 2);
			  } catch(Exception e) {SMD("Hiba:"+e.getMessage(), 0);}
		   }
	   public static void Insert(String sqlp){
		   System.out.println(sqlp);
		     try {
		    	 Statement s = conn.createStatement(); 
		    	 s.executeUpdate(sqlp); 
			     SMD("Rekord beszúrva!", 2);
			  } catch(Exception e) {SMD("Hiba:"+e.getMessage(), 0);}
	   }

	   public static void Command(String sqlp){
		   System.out.println(sqlp);
		     try {
		    	 Statement s = conn.createStatement(); 
		    	 s.executeUpdate(sqlp); 
			     SMD("Sikeres végrehajtás!", 2);
			  } catch(Exception e) {SMD("Hiba:"+e.getMessage(), 0);}
		   }
	public static void SMD(String msg, int icon){
		JOptionPane.showMessageDialog(null, msg, pm, 2);
	}
	public static int StoI(String s){ 
		int x=-55;
		x = Integer.parseInt(s);
		return x;
	}
	public static boolean goodInt(JTextField a) {
		String s = RF(a);
		try {Integer.parseInt(s); return true;} catch (NumberFormatException e){return false;}
	}
	public static boolean filled(JTextField a) {
		String s = RF(a);
		if (s.length() > 0) return true; else return false;
	}
	public static String RF(JTextField a) {
		return a.getText().toString();
	}
	public static void DF(JTextField a) {
		a.setText("");
	}
	public static boolean goodDate(JTextField a) {
		String s = RF(a);
		Date testDate = null;
		try {testDate = sdf.parse(s);} catch (ParseException e){return false;}
		if (sdf.format(testDate).equals(s)) return true; else return false;
	}
}
