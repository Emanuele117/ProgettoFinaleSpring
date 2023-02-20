package it.logicainformatica.progettoSpring.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
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
	}

	@GetMapping("/getListaAnagrafica")
	public List<AnagraficaUtente> getListaAnagrafica() {
		List<AnagraficaUtente> lista = a.getAnagraficaAll();
		return lista;
	}
}
