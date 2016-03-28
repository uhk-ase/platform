package cz.uhk.fim.ase.platform.database;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import cz.uhk.fim.ase.platform.model.Agent;

public class DatabaseNeo4j {
	public void writeNodeToDB(String path, List<Agent> registr) {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		@SuppressWarnings("deprecation")
		GraphDatabaseService db = dbFactory.newEmbeddedDatabase(path);
		
		for (Agent agent : registr) {
			//create node
			try (Transaction tx = db.beginTx()){
				Node newNode = db.createNode(Neo4jLabel.FIRM);
				
				//set propertese
				newNode.setProperty("IdAgent", agent.getId());
				newNode.setProperty("IdPlatform", agent.getPlatform());
				newNode.setProperty("Finance", agent.getFinenc());
				newNode.setProperty("Produkt", agent.getProdukt());
				tx.success();
			} 
		}
	}
	public void writeRelationshipToDB(String path, List<Agent> registr) {
		
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		@SuppressWarnings("deprecation")
		GraphDatabaseService db = dbFactory.newEmbeddedDatabase(path);
		
		try (Transaction tx = db.beginTx()){
			for (Agent agent : registr) {
				//synchronise index from registr and neo4j DB
				int indexZdrojovehoNodu = registr.indexOf(agent);
				Node zdrojovyNode = db.getNodeById(indexZdrojovehoNodu);
				
				//write relationship betwene agent´s.
				for (Agent agentuvPartner : agent.getPartners()) {
					//synchronise index from registr and neo4j DB
					int indexCilovehoNodu = registr.indexOf(agentuvPartner);
					Node cilovyNode = db.getNodeById(indexCilovehoNodu);
					
					//create relationship betwene agent and his partner
					zdrojovyNode.createRelationshipTo(cilovyNode, Neo4jRel.WorkWith);
				}
			}
			tx.success();
		}
	}
}
