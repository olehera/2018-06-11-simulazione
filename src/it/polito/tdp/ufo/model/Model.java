package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.ufo.Anno;
import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private List<Anno> anni;
	private List<String> stati;
	private Graph<String, DefaultEdge> grafo;
	private String stato;
	private List<String> best;
	
	public Model() {
		dao = new SightingsDAO();
		anni = dao.elencoAnni();
	}
	
	public void creaGrafo(int anno) {
		stati = dao.elencoStati(anno);
		
		grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
		Graphs.addAllVertices(grafo, stati);
		
		for (Adiacenza a: dao.ordineTemporale(anno))
			Graphs.addEdgeWithVertices(grafo, a.getPrimo(), a.getSecondo());
		
		System.out.println("#vertici: " + grafo.vertexSet().size()+"\n");
		System.out.println("#archi: " + grafo.edgeSet().size()+"\n");
	}

	public List<Anno> getAnni() {
		return anni;
	}
	
	public List<String> getStati() {
		return stati;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	
	public List<String> successivi(String stato) {
		return Graphs.successorListOf(grafo, stato);
	}
	
	public List<String> precedenti() {
		return Graphs.predecessorListOf(grafo, stato);
	}
	
	public List<String> raggiungibili() {
		List<String> result = new LinkedList<>();
		
		GraphIterator<String, DefaultEdge> it = new DepthFirstIterator<String, DefaultEdge>(grafo, stato);
		
		it.next(); // scarto la sorgente
		
		while ( it.hasNext() ) {
			result.add(it.next());
		}
		
		return result;
	}
	
	public List<String> trovaCammino() {
		best = new ArrayList<>();
		
		List<String> parziale = new ArrayList<String>();
		parziale.add(stato);
		
		cerca(parziale);
		
		return best;
	}
	
	private void cerca(List<String> parziale) {
		
		for (String s: successivi(parziale.get(parziale.size()-1)))
			if (!parziale.contains(s)) {
				parziale.add(s);
				
				cerca(parziale);
				
				parziale.remove(parziale.size()-1);
			}
		
		if (parziale.size() > best.size())
			best = new ArrayList<>(parziale);
	}

}