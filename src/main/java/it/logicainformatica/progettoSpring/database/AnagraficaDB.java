package it.logicainformatica.progettoSpring.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.logicainformatica.progettoSpring.bean.AnagraficaUtente;



public class AnagraficaDB {

	//INSTANZA OGGETTO DATABASE
	Database db = new Database();
	
	// INSERISCO UN NUOVO UTENTE
	public void inseriscoUtenteDB(AnagraficaUtente a) {

		// CREO L'OGGETTO CONNESSIONE
		Connection dbconn = null;
		
		try {

			// CONNESSIONE AL DATABASE
			dbconn = db.getConnessione();

			// QUERY OER INSERIRE I CAMPI NELLA TABELLA ANAGRAFICA
			PreparedStatement statement = dbconn.prepareStatement(
					"Insert into anagrafica(nome,cognome,email,telefono,CF,indirizzo) values (?,?,?,?,?,?)");

			//SETTO ALLA QUERY LE VARIABILI DI ANAGRAFICA
			statement.setString(1, a.getNome());
			statement.setString(2, a.getCognome());
			statement.setString(3, a.getEmail());
			statement.setString(4, a.getTelefono());
			statement.setString(5, a.getCodiceFiscale());
			statement.setString(6, a.getIndirizzo());

			// VERIFICO L'INSERIMENTO DELLA QUERY
			boolean f = statement.execute();

			if (f == false) {
				System.out.println("Inserimento eseguito correttamente!");
			}

			else {
				System.out.println("Inserimento non eseguito!");
			}

			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	// STAMPO L'ANAGRAFICA INTERA
	public List<AnagraficaUtente> getAnagraficaAll() {

		// CREO L'OGGETTO CONNESSIONE
		Connection dbconn = null;

		List<AnagraficaUtente> lista = new ArrayList<AnagraficaUtente>();

		try {

			// VALORIZZO L'OGGETTO CONNESSIONE
			dbconn = db.getConnessione();

			// PREPARO LA QUERY
			PreparedStatement statement = dbconn.prepareStatement("SELECT * FROM anagrafica");

			// LANCIO LA QUERY SUL DATABASE E I RISULTATI ME LI RESTITUSCIE IN UN OGGETTO DI TIPO RESULTSET
			ResultSet rs = statement.executeQuery();

			// CICLO I VALORI CHE ARRIVANO DAL DB
			while (rs.next()) {

				AnagraficaUtente ana = new AnagraficaUtente();

				// INSERISCO IL NOME DELLA COLONNA E MI PRENDO IL VALORE
				ana.setNome(rs.getString("nome"));
				ana.setCognome(rs.getString("cognome"));
				ana.setId(rs.getInt("id"));
				ana.setTelefono(rs.getString("telefono"));
				ana.setIndirizzo(rs.getString("indirizzo"));
				ana.setCodiceFiscale(rs.getString("cf"));
				ana.setEmail(rs.getString("email"));

				lista.add(ana);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lista;

	}
	
	public void scrivoAnagraficaSuFile(AnagraficaUtente anag) throws IOException {
		
		int lungnome = 100;
		String nome = anag.getNome();
		int diflungnome = lungnome - nome.length();
		String spazio = " ";
		
		for (int i = 0; i < diflungnome; i++) {
			nome += spazio.replace(" ", "-");
		}
		
		int lungcognome = 100;
		String cognome = anag.getCognome();
		int diflungcognome = lungcognome - cognome.length();
		
		for (int i = 0; i < diflungcognome; i++) {
			cognome += spazio.replace(" ", "-");
		}
		
		int lungtelefono = 50;
		String telefono = anag.getTelefono();
		int diflungtelefono = lungtelefono - telefono.length();
		
		for (int i = 0; i < diflungtelefono; i++) {
			telefono += spazio.replace(" ", "-");
		}
		
		int lungindirizzo = 255;
		String indirizzo = anag.getIndirizzo();
		int diflungindirizzo = lungindirizzo - indirizzo.length();
		
		for (int i = 0; i < diflungindirizzo; i++) {
			indirizzo += spazio.replace(" ", "-");
		}
		
		int lungcodicefiscale = 50;
		String codicefiscale = anag.getCodiceFiscale();
		int diflungcodicefiscale = lungcodicefiscale - codicefiscale.length();
		
		for (int i = 0; i < diflungcodicefiscale; i++) {
			codicefiscale += spazio.replace(" ", "-");
		}
		
		int lungemail = 50;
		String email = anag.getEmail();
		int diflungemail = lungemail - email.length();
		
		for (int i = 0; i < diflungemail; i++) {
			email += spazio.replace(" ", "-");
		}
		
		// LIBRERIA FILE PER CREARE UN NUOVO FILE
		File file = new File("file_posizionale.txt");
		
		// USO LA LIBRERIA FILEWRITER PER INSERIRE DATI SUL FILE DI TESTO
		FileWriter fwriter = new FileWriter(file, true);
		
		try {

				fwriter.write(anag.getId() + " ,");
	            fwriter.write(nome + " ,");
	            fwriter.write(cognome + " ,");
	            fwriter.write(telefono + " ,");
	            fwriter.write(indirizzo + " ,");
	            fwriter.write(codicefiscale + " ,");
	            fwriter.write(email + "\n");

		} finally {
			try {
				fwriter.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Errore nel metodo scrivoAnagraficaSuFile " + e.getMessage());
			}
		}
		

	}
}
