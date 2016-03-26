package cz.uhk.fim.ase.platform.database;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import cz.uhk.fim.ase.platform.model.Agent;

public class DatabaseNeo4j {
	 public static int count = 0;//declaring number of tic

	public void writeToDB(String path, List<Agent> registr) {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		GraphDatabaseService db = dbFactory.newEmbeddedDatabase(path);
		
		
		for (Agent agent : registr) {
			try (Transaction tx = db.beginTx()){
				Node newNode = db.createNode(Neo4jLabel.FIRM);//create node
				
				//set proprtese
				newNode.setProperty("IdAgenta", agent.getId());
				newNode.setProperty("IdPlatformy", agent.getPlatform());
				newNode.setProperty("Finance", agent.getFinenc());
				newNode.setProperty("Produkce", agent.getProdukt());
				
				for (Agent agentInPartners : agent.getPartners()) {
					//zjitit na jakém místì v seznamu je daný agent
					int index = registr.indexOf(agentInPartners);
					Node relNode = db.getNodeById(index);
					newNode.createRelationshipTo(relNode, Neo4jRel.WorkWith);	
				}
			} 
		}
		
		
	}
}
