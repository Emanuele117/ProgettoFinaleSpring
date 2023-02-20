package it.logicainformatica.progettoSpring.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.logicainformatica.progettoSpring.bean.AnagraficaUtente;



public class AnagraficaDB {

	
	Database db = new Database();
	
	// INSERISCO UN NUOVO UTENTE
	public void inserisciUtente(AnagraficaUtente a) {

		Connection dbconn = null;
		try {

			dbconn = db.getConnessione();

			PreparedStatement statement = dbconn.prepareStatement(
					"Insert into anagrafica(nome,cognome,email,telefono,CF,indirizzo) values (?,?,?,?,?,?)");

			statement.setString(1, a.getNome());
			statement.setString(2, a.getCognome());
			statement.setString(3, a.getEmail());
			statement.setString(4, a.getTelefono());
			statement.setString(5, a.getCodiceFiscale());
			statement.setString(6, a.getIndirizzo());

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

		// CREO L'OGGETTO CONNECTION
		Connection dbconn = null;

		List<AnagraficaUtente> lista = new ArrayList<AnagraficaUtente>();

		try {

			// VALORIZZO L'OGGETTO CONNECTION
			dbconn = db.getConnessione();

			// PREPARO L' ISTRUZIONE SQL
			PreparedStatement statement = dbconn.prepareStatement("SELECT * FROM anagrafica");

			// LANCIO LA QUERY SUL DATA BASE E I RISULTATI ME LI RESTITUSCIE IN UN OGGETTO
			// DI TIPO RESULTSET
			ResultSet rs = statement.executeQuery();

			// CICLO I VALORI CHE ARRIVANO DAL DB
			while (rs.next()) {

				AnagraficaUtente ana = new AnagraficaUtente();

				// INSERENDO IL NOME DELLA COLONNA E MI PRENDO IL VALORE

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
}
