package it.polito.tdp.borders.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		model.creaGrafo(2000);
		
		System.out.println("JGraphT:");

		List<Country> c = model.percorso(325);
		for(Country ctemp : c) {
			System.out.println(ctemp);
		}
		System.out.println("Iterativo:");

		List<Country> c2 = model.percorsoIterativo(325);
		for(Country ctemp : c2) {
			System.out.println(ctemp);
		}
		
		System.out.println("Ricorsione:");
		List<Country> c3 = model.percorsoIterativo(325);
		for(Country ctemp : c3) {
			System.out.println(ctemp);
		}
		
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
