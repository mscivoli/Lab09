package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	public class EdgeTraversedGraphListener implements TraversalListener<Country, DefaultEdge> {

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> ev) {
			Country sourceVertex = grafo.getEdgeSource(ev.getEdge()) ;
			Country targetVertex = grafo.getEdgeTarget(ev.getEdge()) ;
			
			if( !backVisit.containsKey(targetVertex) && backVisit.containsKey(sourceVertex) ) {
				backVisit.put(targetVertex, sourceVertex) ;
			} else if(!backVisit.containsKey(sourceVertex) && backVisit.containsKey(targetVertex)) {
				backVisit.put(sourceVertex, targetVertex) ;
			}

		}

		@Override
		public void vertexFinished(VertexTraversalEvent<Country> arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<Country> arg0) {
			// TODO Auto-generated method stub

		}

	}

	private Graph<Country, DefaultEdge> grafo;
	private List<Country> continenti;
	private Map<Integer, Country> idMap;
	private List<Border> borders;
	private Map<Country, Country> backVisit;
	private List<Country> ricorsione;

	public Model() {
	
	
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		BordersDAO dao = new BordersDAO();
		
		this.continenti = dao.loadAllCountries();

		// crea idMap
		this.idMap = new HashMap<>();
		for (Country f : this.continenti)
			idMap.put(f.getCodice(), f);
		
		this.borders = new LinkedList<Border>();
		borders.addAll(dao.loadBorder(anno, idMap));
		for(Border btemp : borders) {
			if(!grafo.containsEdge(btemp.getPrimoPaese(), btemp.getSecondoPaese())) {
				Graphs.addEdgeWithVertices(grafo, btemp.getPrimoPaese(), btemp.getSecondoPaese());
				
			}
		}
		
		System.out.println("grafo creato");
		System.out.println("vertici: "+grafo.vertexSet().size());
		System.out.println("edge: "+grafo.edgeSet().size());
		
		for(Country c : grafo.vertexSet()){
			System.out.println(c + " con grado " + Graphs.neighborListOf(grafo, c).size());
		}
		
		System.out.println("Numero di componenti connesse:");
		ConnectivityInspector<Country, DefaultEdge> g = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		System.out.println(g.connectedSets().size());
		
		
	}
	
	public List<Country> percorso(int radice){
		
		Country c = idMap.get(radice);
		
		List<Country> result = new ArrayList<Country>();
		backVisit = new HashMap<>();

		GraphIterator<Country, DefaultEdge> it = new DepthFirstIterator<>(this.grafo, c);
		
		it.addTraversalListener(new Model.EdgeTraversedGraphListener());

		backVisit.put(c, null);

		while (it.hasNext()) {
			result.add(it.next());
		}

		return result;
	}
	
	
	public List<Country> percorsoIterativo(int partenza){
		
		BordersDAO dao = new BordersDAO();
		
		Country c = idMap.get(partenza);
		
		Map<Integer, Country> visitati = new HashMap<Integer, Country>();
		//List<Country> visitati = new LinkedList<Country>();
		List<Country> daVisitare = new LinkedList<Country>();
		
		daVisitare.add(c);
		
		
		while(daVisitare.size() > 0) {
			Country country = daVisitare.get(0);
			daVisitare.remove(0);
			for(Country neighbor : Graphs.neighborListOf(grafo, country)) {
				if(!visitati.containsKey(neighbor.getCodice())) {
					daVisitare.add(neighbor);
				}
			}
			
			visitati.put(country.getCodice(),country);
		}
		
		
		List<Country> lista = new LinkedList<Country>(visitati.values());
		return lista;
		
		
		
	}
	
	
	public List<Country> percorsoRicorsione(int partenza){
		
		Country c = idMap.get(partenza);
		
		List<Country> ltemp = new LinkedList<Country>(Graphs.neighborListOf(grafo, c));
		
		ricorsione.add(c);
		
		for(Country ctemp : ltemp) {
			if(!ricorsione.contains(ctemp)) {
				//ricorsione.add(ctemp);
				this.percorsoRicorsione(ctemp.getCodice());
			}
		}
		
		
		return ltemp;
		
	}
	
	
	
	
	
	
	
	/*public List<Country> nodiRaggiungibili(int source) {
		
		Country c = idMap.get(source);
		
		this.percorso(c);

		List<Country> collegamenti = new LinkedList<>();

		while (c != null) {
			collegamenti.add(c);
			//source = backVisit.get(target);
		}

		return collegamenti ;

	}*/
	

}





























