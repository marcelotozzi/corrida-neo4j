package br.com.neo4j.model;

import org.neo4j.graphdb.Node;

public class Corredor {
	private Node node;
	private static final String NOME = "nome";

	public void setNode(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return this.node;
	}
	
	public void setNome(String nome) {
		this.node.setProperty(NOME, nome);
	}

}
