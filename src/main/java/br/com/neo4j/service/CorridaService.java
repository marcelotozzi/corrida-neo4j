package br.com.neo4j.service;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import br.com.neo4j.model.Colocacao;
import br.com.neo4j.model.Corredor;
import br.com.neo4j.model.Corrida;
import br.com.neo4j.model.TipoDeRelacionamento;

public class CorridaService {

	private static final String CORRIDA = "corrida";
	private GraphDatabaseService graphDb;
	private Index<Node> index;

	public CorridaService(GraphDatabaseService graphDatabaseService, Index<Node> index) {
		this.graphDb = graphDatabaseService;
		this.index = index;
	}

	public Corrida criaCorrida(String nome, String distancia) {
		Transaction tx = this.graphDb.beginTx();
		try {
			Node nodeReferencia = this.graphDb.getReferenceNode();

			Node novaCorrida = this.graphDb.createNode();

			Corrida corrida = new Corrida();
			corrida.setNode(novaCorrida);
			corrida.setNome(nome);
			corrida.setDistancia(distancia);

			novaCorrida.createRelationshipTo(nodeReferencia, TipoDeRelacionamento.PAI);

			index.add(novaCorrida, CORRIDA, nome);

			tx.success();
			return corrida;
		} finally {
			tx.finish();
		}
	}

	public Colocacao criaColocacaoParaCorredor(String nomeDaCorrida, String nomeDoCorredor, String colocacaoDeChegada) {
		Transaction tx = this.graphDb.beginTx();
		try {
			Node nodeCorrida = this.index.get(CORRIDA, nomeDaCorrida)
					.getSingle();

			Node nodeCorredor = this.graphDb.createNode();

			Corredor corredor = new Corredor();
			corredor.setNode(nodeCorredor);
			corredor.setNome(nomeDoCorredor);

			if (nodeCorrida == null) {
				throw new IllegalArgumentException("Null corrida");
			}
			if (nodeCorredor == null) {
				throw new IllegalArgumentException("Null corredor");
			}

			Relationship relacaoColocacao = nodeCorredor.createRelationshipTo(nodeCorrida, TipoDeRelacionamento.CORREU);
			
			Colocacao colocacao = new Colocacao();
			colocacao.setRelationship(relacaoColocacao);
			
			if (colocacaoDeChegada != null) {
				colocacao.setChegada(colocacaoDeChegada);
			}
			tx.success();
			return colocacao;
		} finally {
			tx.finish();
		}
	}
}