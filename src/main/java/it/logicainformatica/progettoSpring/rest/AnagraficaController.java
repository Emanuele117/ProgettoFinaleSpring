package it.logicainformatica.progettoSpring.rest;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.logicainformatica.progettoSpring.bean.AnagraficaUtente;
import it.logicainformatica.progettoSpring.database.AnagraficaDB;



@RestController
@RequestMapping("/anagrafica-utente")
public class AnagraficaController {

	AnagraficaDB a = new AnagraficaDB();

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void inserisciAnagrafica(@RequestBody AnagraficaUtente anag) {
		a.inserisciUtente(anag);
		
		try {
			a.scrivoSuFile(anag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/getListaAnagrafica")
	public List<AnagraficaUtente> getListaAnagrafica() {
		List<AnagraficaUtente> lista = a.getAnagraficaAll();
		return lista;
	}
	
	
	
	@GetMapping("/leggi-file")
	public ResponseEntity<String> leggiDati() {
		
	    try {
	    	
	        // CREO UN OGGETTO BUFFEREDREDADER PER LEGGERE IL CONTENUTO DEL FILE
	        BufferedReader reader = new BufferedReader(new FileReader("prova.txt"));

	        // LEGGO IL CONTENUTO DEL FILE E LO METTO IN UNA STRINGA
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	            sb.append("\n");
	        }

	        // CHIUDO L'OGGETTO PER LEGGERE IL FILE
	        reader.close();

	        // RESTITUISCO IL CONTENUTO DEL FILE COME RESPONSE HTTP
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.TEXT_PLAIN);
	        return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);

	    } catch (IOException e) {
	        e.printStackTrace();
	        // IN CASO DI ERRORE RESTITUISCO UN MESSAGGIO DI ERRORE CON UNO STATO HTTP 500
	        return new ResponseEntity<String>("Errore durante la lettura del file", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
}
