# ProgettoFinaleSpring

Questo progetto è un'applicazione spring boot collegata ad un Database, in grado di gestire tramite servizi rest,  l'inserimento dell'anagrafica utente all'interno del database e su un file di testo. 

L'applicazione è composta da : 
- un oggetto AnagraficaUtente, all'interno del quale si trovano le variabili che corrispondono ai campi stabiliti del database, 
- un oggetto AnagraficaDB dove risiedono i metodi per l'inserimento dell'anagrafica nel databse, l'inserimento dell'anagrafica su un file di testo e la sua creazione, e la lettura dei dati catturati nel database,
- un controller, AnagraficaController, dove sono esposti i servizi rest.

- Nome Database : crm

- creazione tabella Anagrafica :

CREATE TABLE anagrafica (
    id int AUTO_INCREMENT,
    nome varchar(100),
    cognome varchar(100),
    telefono varchar(50),
    email varchar(50),
    indirizzo varchar(255),
    cf varchar(50),
);


servizi rest disponibili : http://localhost:8080/swagger-ui.html 
