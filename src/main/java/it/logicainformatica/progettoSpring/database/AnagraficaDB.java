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
	
	public void scrivoAnagraficaSuFile(AnagraficaUtente a) throws IOException {
		
		// CREO L'OGGETTO CONNESSIONE
		Connection dbconn = null;
		
		//STRINGA PER IL PATH DEL FILE
		String path = "C:/prova3.txt";
		
		// LIBRERIA FILE PER CREARE UN NUOVO FILE
		File newFile = new File(path);
		
		// USO LA LIBRERIA FILEWRITER PER INSERIRE DATI SUL FILE DI TESTO
		FileWriter writer = new FileWriter("prova3.txt");
		
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
				
				writer.write("Nome: " + ana.getNome() + ", ");
	            writer.write("Cognome: " + ana.getCognome() + ", ");
	            writer.write("ID: " + ana.getId() + ", ");
	            writer.write("Telefono: " + ana.getTelefono() + ", ");
	            writer.write("Indirizzo: " + ana.getIndirizzo() + ", ");
	            writer.write("Codice fiscale: " + ana.getCodiceFiscale() + ", ");
	            writer.write("Email: " + ana.getEmail() + "\n");

	            

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbconn.close();
				writer.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

	}
}
