package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Triple;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;

public class Ontology {

	// criando ont model
	OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	
	// construtor 
	public Ontology(String owlFile, String type) {
		this.base.read(owlFile, type);
	}

	// URI principal da ontologia
	public String getURI() {
		String URI = base.getNsPrefixURI("");
		return URI;
	}
	
	// inferencia da ontologia
	public OntModel getInferredModel() {
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, base);
		return inf;
	}

	// monta grafo da ontologia
	Graph ont_grafo = base.getGraph();
	
	// lista as classes e subclasses
	public void listClasses() {
		for (ExtendedIterator<OntClass> i = base.listNamedClasses(); i.hasNext();) {
			OntClass classe = (OntClass) i.next();
			System.out.println(classe.getLocalName());
				for (ExtendedIterator<OntClass> j = classe.listSubClasses(); j.hasNext();) {
					OntClass sub = (OntClass) j.next();
					System.out.println("	" + sub.getLocalName());
				}
		}
	}
	
	// retorna nº de triplas
	public int getTriples() {
		int tamanho_grafo = ont_grafo.size();
		return tamanho_grafo;
	}
	
	// lista todas as triplas da ontologia
	public List<Tripla> listTriples() {
		ExtendedIterator<Triple> triplas = ont_grafo.find();
        List<Tripla> trips = new ArrayList(Arrays.asList(new Tripla("teste1", "teste2", "@rdfs:subClassOf" )));
        while(triplas.hasNext()) {
	    	 Triple tripla = triplas.next();
		    	if(tripla.getPredicate().toString().equals("http://www.w3.org/2000/01/rdf-schema#subClassOf") || tripla.getPredicate().toString().equals("http://www.w3.org/2000/01/rdf-schema#type")){
	    		 Tripla t = new Tripla(tripla.getSubject().toString(), tripla.getObject().toString(), "@rdfs:subClassOf" );
		    	 trips.add(t);
	    	 }
	     }
		return trips;	  	     
	}
	
	// lista todas as triplas da ontologia, apenas predicados de subclasse
	public void listTriplesSubclass() {
		ExtendedIterator<Triple> triplas = ont_grafo.find();
			     
	     while(triplas.hasNext()) {
	    	 Triple tripla = triplas.next();
	    	if(tripla.getPredicate().toString().equals("http://www.w3.org/2000/01/rdf-schema#subClassOf") || tripla.getPredicate().toString().equals("http://www.w3.org/2000/01/rdf-schema#type")){
	    		 System.out.println(tripla);
	    	} 
	     }	  	     
	}
	
	// lista todas as triplas da ontologia
	public void listTriplesInf() {
		OntModel inf = getInferredModel();
		Graph grafo = inf.getGraph();
		ExtendedIterator<Triple> triplas = grafo.find();
			     
	     while(triplas.hasNext()) {
	    	 Triple tripla = triplas.next();
	    	 if(tripla.getPredicate().toString() == "@rdfs:subClassOf") {
	    		 System.out.println(tripla);
	    	 }
	    	//  System.out.println(tripla.hashCode());
	     }	  	     
	}
	
	// lista os individuos da ontologia
	public void listIndividuals() {
		for(ExtendedIterator<Individual> i = base.listIndividuals(); i.hasNext();){
			System.out.println(i.next());
		}
	}
	
	// TODO public void getPropertyRange();

}
