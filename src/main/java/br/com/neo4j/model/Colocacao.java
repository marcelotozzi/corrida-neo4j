package br.com.neo4j.model;

import org.neo4j.graphdb.Relationship;

public class Colocacao {
	private static final String CHEGADA = "chegada";

	private Relationship relacao;

	public Relationship getRelationship() {
		return this.relacao;
	}

	public void setRelationship(final Relationship rel) {
		this.relacao = rel;
	}

	public String getChegada() {
		return (String) relacao.getProperty(CHEGADA, null);
	}

	public void setChegada(String chegada) {
		relacao.setProperty(CHEGADA, chegada);
	}
}
