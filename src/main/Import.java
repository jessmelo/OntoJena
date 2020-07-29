package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Import {	
	public static void main (String[] args) throws IOException {

		String owlFile = "C:\\Users\\Jess\\Documents\\eclipse-workspace\\OntoJena\\src\\main\\wine.owl";
		System.out.println("Arquivo owl: " + owlFile);
				
		System.out.println("Carregando ontologia...");
		
		Ontology ont = new Ontology(owlFile, "RDF/XML");
		
		System.out.println("URI da Ontologia: " + ont.getURI());
		
		ont.listClasses();
		
		System.out.println("Número de triplas: " + ont.getTriples());
		
		System.out.println("Triplas da Ontologia:");
		// ont.listTriplesSubclass();
		
		// escrevendo arquivo csv
		String csvFile = "C:\\\\Users\\\\Jess\\\\Documents\\\\eclipse-workspace\\\\OntoJena\\\\src\\\\main\\triples_wine.csv";
        FileWriter writer = new FileWriter(csvFile);

        List<Tripla> lista_triplas = ont.listTriples();
        //for header
        ExportCsv.writeLine(writer, Arrays.asList("n1", "n2", "predicado"));

        for (Tripla d : lista_triplas) {
            List<String> list = new ArrayList<>();
            list.add(d.getn1());
            list.add(d.getn2());
            list.add(d.getPredicado());

            ExportCsv.writeLine(writer, list);
        }

        writer.flush();
        writer.close();

		// System.out.println("Indivíduos da Ontologia:");
		// ont.listIndividuals();
				
		// System.out.println("Triplas do Modelo Inferido:");		
		// ont.listTriplesInf();
		
		System.out.println("Fim.");

	}

}
