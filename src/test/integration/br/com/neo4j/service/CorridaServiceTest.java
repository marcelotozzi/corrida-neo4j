package br.com.neo4j.service;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import br.com.neo4j.model.Colocacao;
import br.com.neo4j.model.Corrida;
import br.com.neo4j.service.CorridaService;

public class CorridaServiceTest {
	private static final String CORRIDAS_DB = "sample/corrida-db";
	private CorridaService service;
	private GraphDatabaseService graphDatabaseService;
	private Index<Node> index;

	@Before
	public void setUp() throws Exception {
		deletaArquivoOuDiretorio(new File(CORRIDAS_DB));

		this.graphDatabaseService = new EmbeddedGraphDatabase(CORRIDAS_DB);
		this.index = graphDatabaseService.index().forNodes("nodes");
		this.service = new CorridaService(this.graphDatabaseService, this.index);
	}

	@After
	public void tearDown() throws Exception {
		this.graphDatabaseService.shutdown();
	}

	@Test
	public void deveCriarCorridaEInserirOPrimeiroColocado() {
		Corrida corrida = this.service.criaCorrida("S‹o Silvestre", "42 km");

		Colocacao primeiro = this.service.criaColocacaoParaCorredor("S‹o Silvestre", "Paul Tergat", "Primeiro");

		Assert.assertNotNull(corrida.getNode());
		Assert.assertEquals("S‹o Silvestre", corrida.getNode().getProperty("nome"));
		Assert.assertEquals("42 km", corrida.getNode().getProperty("distancia"));

		Assert.assertEquals("S‹o Silvestre", corrida.getNome());
		Assert.assertEquals("42 km", corrida.getDistancia());

		Assert.assertEquals("Primeiro", primeiro.getRelationship().getProperty("chegada"));
		Assert.assertEquals("Primeiro", primeiro.getChegada());
	}

	private void deletaArquivoOuDiretorio(File arquivo) {
		if (!arquivo.exists()) {
			return;
		}

		if (arquivo.isDirectory()) {
			for (File child : arquivo.listFiles()) {
				deletaArquivoOuDiretorio(child);
			}
		} else {
			arquivo.delete();
		}
	}
}