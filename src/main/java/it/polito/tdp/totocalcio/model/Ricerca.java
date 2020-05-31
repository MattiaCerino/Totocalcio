package it.polito.tdp.totocalcio.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {
	
	private Pronostico pronostico ;
	private int N ;
	private List<Risultato> soluzione ;

	public List<Risultato> cerca(Pronostico pronostico) {

		this.pronostico = pronostico ;
		this.N = pronostico.size() ;
		
		List<RisultatoPartita> parziale = new ArrayList<>() ;
		int livello = 0 ;
		
		this.soluzione = new ArrayList<Risultato>() ;
		
		ricorsiva(parziale, livello) ;
		
		return this.soluzione ; 
	
	}
	
	private void ricorsiva(List<RisultatoPartita> parziale, int livello) { // E' la funzione che fa la ricorsione 
		
		// CASO TERMINALE? (SONO ARRIVATO AD UNA SOLUZIONE BANALE)
		if(livello == N) {
			// questa soluzione parziale è una soluzione completa
			// System.out.println(parziale); 
			this.soluzione.add(new Risultato(parziale)) ;
		} else {
			// CASO GENERALE
			
			// 				GIA' DECISI					DA DECIDERE ORA 	DA DECIDERE CON UN'ALTRA ITERAZIONE
			// [SOLUZIONE PARZIALE da 0 a LIVELLO-1]	[LIVELLO] 			[LIVELLO+1 in poi]
			PronosticoPartita pp = pronostico.get(livello);
			// pp sono i sotto-problemi da provare
			
			for(RisultatoPartita ris : pp.getRisultati()) {
				// provo a mettere 'ris' nella posizione "livello" della soluzione parziale

				// Costruzione della SOLUZIONE PARZIALE(sottoproblema) 
				parziale.add(ris) ;
				
				// CHIAMATA della FUNZIONE RICORSIVA - da ora in avanti le successive iterazioni dovranno mettere i livelli successivi
				ricorsiva(parziale, livello+1);
				
				// BACKTRACKING - rimetto le cose a posto per far si che all'iterazione successiva del for io possa mettere l'altra opzione
				parziale.remove(parziale.size()-1) ;		
			}
		}	
	}
}

/*
 * LIVELLO della RICORSIONE = numero di partita che sto considerando
 * Le partite da 0 a "livello-1" sono già state decise
 * La partita di indice "livello" la devo decidere io
 * Le partite da "livello+1" in poi le decideranno le procedure ricorsive sottostanti
 * 
 * SOLUZIONE PARZIALE: un elenco / lista / sequenza di RisultatoPartita di lunghezza pari al livello
 * 
 * SOLUZIONE TOTALE: ho N risultati
 * 
 * CONDIZIONE DI TERMINAZIONE: livello == N
 * 
 * GENERAZIONE DELLE SOLUZIONI DI LIVELLO: provando tutte i pronostici definiti per quel livello
 * 
 */
/*
[ "2X", "1", "1X2", "12" ]
		
	2	 ?     X      2    []
		
[ "2X" ] + [ "1", "1X2", "12" ]
		   [ "1", "1X2", "12" ]
		   [ "1" ] + [ "1X2", "12" ]
*/