package br.com.neo4j.model;

import org.neo4j.graphdb.Node;

public class Corrida {

	private Node node;
	private static final String NOME = "nome";
	private static final String DISTANCIA = "distancia";

	public void setNode(Node node) {
		this.node = node;
	}
	
	public Node getNode() {
		return this.node;
	}

	public void setNome(String nome) {
		this.node.setProperty(NOME, nome);
	}
	
	public String getNome() {
		return (String) this.node.getProperty(NOME);
	}

	public void setDistancia(String distancia) {
		this.node.setProperty(DISTANCIA, distancia);
	}

	public String getDistancia() {
		return (String) this.node.getProperty(DISTANCIA);
	}
}