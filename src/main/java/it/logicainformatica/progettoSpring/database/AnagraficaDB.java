package it.logicainformatica.progettoSpring.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

	// INSTANZA OGGETTO DATABASE
	Database db = new Database();

	// METODO PER INSERIRE UN NUOVO UTENTE SUL DB
	public void inseriscoUtenteDB(AnagraficaUtente anag) {

		// CREO L'OGGETTO CONNESSIONE
		Connection dbconn = null;

		try {

			// CONNESSIONE AL DATABASE
			dbconn = db.getConnessione();

			// QUERY OER INSERIRE I CAMPI NELLA TABELLA ANAGRAFICA
			PreparedStatement statement = dbconn.prepareStatement(
					"Insert into anagrafica(nome,cognome,email,telefono,CF,indirizzo) values (?,?,?,?,?,?)");

			// SETTO ALLA QUERY LE VARIABILI DI ANAGRAFICA
			statement.setString(1, anag.getNome());
			statement.setString(2, anag.getCognome());
			statement.setString(3, anag.getEmail());
			statement.setString(4, anag.getTelefono());
			statement.setString(5, anag.getCodiceFiscale());
			statement.setString(6, anag.getIndirizzo());

			// VERIFICO L'INSERIMENTO DELLA QUERY
			boolean f = statement.execute();

			if (f == false) {
				System.out.println("Inserimento eseguito correttamente!");
			}

			else {
				System.out.println("Inserimento non eseguito!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println
			("Errore nel metodo inseriscoUtenteDB di tipo SQL della classe AnagraficaDB : " + e.getMessage());
			
			
		} finally {
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore SQL nella chiusura della connessione al Database del metodo inseriscoUtenteDB" + e.getMessage());
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

			// LANCIO LA QUERY SUL DATABASE E I RISULTATI ME LI RESTITUSCIE IN UN OGGETTO DI
			// TIPO RESULTSET
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
			System.out.println("Errore SQL del metodo getAnagraficaAll : " + e.getMessage() );
			
		} finally {
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore SQL per la chiusura connessione al database del metodo getAnagraficaAll : " + e.getMessage() );
			}
		}

		return lista;

	}

	

	public void scrivoAnagraficaSuFile(AnagraficaUtente anag) throws IOException {
		int contatoreId = 0;

		// LIBRERIA FILE PER CREARE UN NUOVO FILE
		File file = new File("file_posizionale.txt");

		// USO LA LIBRERIA FILEWRITER PER INSERIRE DATI SUL FILE DI TESTO
		FileWriter fwriter = new FileWriter(file, true);

		//LEGGO IL FILE DI TESTO CHE CONTIENE UNA SERIE DI RIGHE DI DATI
		// SEPARATI DA VIRGOLE (",") E TROVO L'ULTIMO ID SCRITTO,
		// DOVE L'ID SI TROVA NELLA PRIMA COLONNA.
		
		// UTILIZZO BUFFEREDREADER PER LEGGERE IL FILE PASSATO COME ARGOMENTO AL COSTRUTTORE
		BufferedReader breader = new BufferedReader(new FileReader(file));
		
		// UTILIZZO LA STRINGA LINE PER LEGGERE IL CONTENUTO DEL FILE RIGA PER RIGA TRAMITE 
		// IL METODO READLINE() DEL BUFFEREDREADER
		String line;
		
		// IL CICLO WHILE SI RIPETE FINCHE TROVA UNA RIGA NEL FILE 
		while ((line = breader.readLine()) != null) {
			
			// AD OGNI ITERAZIONE LA RIGA LETTA VIENE SUDDIVISA IN UNA SERIE DI STRINGHE SEPARATE DA VIRGOLE
			// TRAMITE IL METODO SPLIT(",")
		    String[] values = line.split(",");
		   
		    // E LE STRINGHE RISULTANTI VENGONO MEMORIZZATE IN UN ARRAY DI STRINGHE CHIAMATO VALUES
		    String lastId = values[0].trim();
		    
		    // L'ULTIMO ID SCRITTO VIENE QUINDI ESTRATTO DALLA PRIMA COLONNA DI OGNI RIGA (CIOè LA PRIMA STRINGA NELL'ARRAY VALUES), QUINDI CONVERTITO IN UN INTERPO TRAMITE IL METODO INTEGER.PARSEINT(), E INFINE MEMORIZZATO NELLA VARIABILE CONTATOREID
		    contatoreId = Integer.parseInt(lastId);
		    
		    // CONTATORE ID CONTERRà L'ULTIMO ID INSERITO NEL FILE
		}
		breader.close();

		//incrementa il contatore degli ID
		contatoreId++;

		int lungnome = 100;
		String nome = anag.getNome();
		int diflungnome = lungnome - nome.length();
		String spazio = " ";

		for (int i = 0; i < diflungnome; i++) {
			nome += spazio.replace(" ", "·");
		}

		int lungcognome = 100;
		String cognome = anag.getCognome();
		int diflungcognome = lungcognome - cognome.length();

		for (int i = 0; i < diflungcognome; i++) {
			cognome += spazio.replace(" ", "·");
		}

		int lungtelefono = 50;
		String telefono = anag.getTelefono();
		int diflungtelefono = lungtelefono - telefono.length();

		for (int i = 0; i < diflungtelefono; i++) {
			telefono += spazio.replace(" ", "·");
		}

		int lungindirizzo = 255;
		String indirizzo = anag.getIndirizzo();
		int diflungindirizzo = lungindirizzo - indirizzo.length();

		for (int i = 0; i < diflungindirizzo; i++) {
			indirizzo += spazio.replace(" ", "·");
		}

		int lungcodicefiscale = 50;
		String codicefiscale = anag.getCodiceFiscale();
		int diflungcodicefiscale = lungcodicefiscale - codicefiscale.length();

		for (int i = 0; i < diflungcodicefiscale; i++) {
			codicefiscale += spazio.replace(" ", "·");
		}

		int lungemail = 50;
		String email = anag.getEmail();
		int diflungemail = lungemail - email.length();

		for (int i = 0; i < diflungemail; i++) {
			email += spazio.replace(" ", "·");
		}

		int idLength = 11;
		String id = String.valueOf(contatoreId);
		int difIdLength = idLength - id.length();

		for (int i = 0; i < difIdLength; i++) {
		    id += " ";
		}

		try {

			fwriter.write(id + " ,");
			fwriter.write(nome + ",");
			fwriter.write(cognome + ",");
			fwriter.write(telefono + ",");
			fwriter.write(indirizzo + ",");
			fwriter.write(codicefiscale + ",");
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
