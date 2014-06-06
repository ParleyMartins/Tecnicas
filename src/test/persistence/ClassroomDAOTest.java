package test.persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Sala;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import persistence.FactoryConnection;
import persistence.ClassroomDAO;
import exception.PatrimonioException;


public class ClassroomDAOTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	
	
	@Test
	public void testInstance() {
		assertTrue("Instanciando SalaDAO", ClassroomDAO.getInstance() instanceof ClassroomDAO);
	}
	@Test
	public void testSingleton() {
		ClassroomDAO inst1 = ClassroomDAO.getInstance();
		ClassroomDAO inst2 = ClassroomDAO.getInstance();
		assertSame("Testando o Padrao Singleton", inst2, inst1);
	}
	

	@Test
	public void testIncluir() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		boolean rs = false;
		
		ClassroomDAO.getInstance().insert(s);
		
		rs = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() + "\" and " +
				"sala.capacidade = " + s.getCapacity() +
				";");
		
		if(rs)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		assertTrue("Testando Inclusao no Banco", rs);
	}
	@Test (expected= PatrimonioException.class)
	public void testIncluirNulo() throws PatrimonioException, SQLException {
		ClassroomDAO.getInstance().insert(null);
	}
	@Test (expected= PatrimonioException.class)
	public void testIncluirCodigoExistente() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		Sala s2 = new Sala("CodigoInc", "Descricao Dois", "200");
		boolean rs = false;
		
		ClassroomDAO.getInstance().insert(s2);
		try{
			ClassroomDAO.getInstance().insert(s);
		} finally {
			rs = this.estaNoBanco("SELECT * FROM sala WHERE " +
					"sala.codigo = \"" + s.getIdCode() + "\" and " +
					"sala.descricao = \"" + s.getDescription() + "\" and " +
					"sala.capacidade = " + s.getCapacity() +
					";");
			if(rs)
				this.executaNoBanco("DELETE FROM sala WHERE " +
						"sala.codigo = \"" + s.getIdCode() + "\" and " +
						"sala.descricao = \"" + s.getDescription() +  "\" and " +
						"sala.capacidade = " + s.getCapacity() + ";");
			this.executaNoBanco("DELETE FROM sala WHERE " +
					"sala.codigo = \"" + s2.getIdCode() + "\" and " +
					"sala.descricao = \"" + s2.getDescription() +  "\" and " +
					"sala.capacidade = " + s2.getCapacity() + ";");
		}
		assertFalse("Teste de Inclusão.", rs);
	}
	
	@Test
	public void testAlerar() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		Sala s2 = new Sala("CodigoAlt", "Descricao Dois", "200");
		boolean rs = true, rs2 = false;
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s.getIdCode() + "\", " +
				"\"" + s.getDescription() + "\", " +
				s.getCapacity() + ");");
		
		ClassroomDAO.getInstance().modify(s, s2);
		
		rs = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() + "\" and " +
				"sala.capacidade = " + s.getCapacity() +
				";");
		
		if(rs)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		
		rs2 = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() + "\" and " +
				"sala.capacidade = " + s2.getCapacity() +
				";");
		if(rs2)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() +  "\" and " +
				"sala.capacidade = " + s2.getCapacity() + ";");
		
		assertTrue("Testando Inclusao no Banco", rs2 && !rs);
	}
	@Test (expected= PatrimonioException.class)
	public void testAletarPrimeiroNulo() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		ClassroomDAO.getInstance().modify(null, s);
	}
	@Test (expected= PatrimonioException.class)
	public void testAletarSegundoNulo() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		ClassroomDAO.getInstance().modify(s, null);
	}
	@Test (expected= PatrimonioException.class)
	public void testAletarNaoExistente() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		Sala s2 = new Sala("CodigoAlt", "Descricao Dois", "200");
		boolean rs2 = true;
		
		try{
			ClassroomDAO.getInstance().modify(s, s2);
		} finally {		
		
		rs2 = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() + "\" and " +
				"sala.capacidade = " + s2.getCapacity() +
				";");
		if(rs2)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() +  "\" and " +
				"sala.capacidade = " + s2.getCapacity() + ";");
		}
		assertTrue("Testando Inclusao no Banco", !rs2);
	}
	@Ignore // (expected= PatrimonioException.class)
	public void testAletarEvolvidoEmReserva() throws PatrimonioException, SQLException {
	}
	@Test (expected= PatrimonioException.class)
	public void testAletarComMesmoCodigo() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		Sala s2 = new Sala("CodigoAlt", "Descricao Dois", "200");
		Sala s3 = new Sala("CodigoInc", "Descricao Dois", "200");
		boolean rs = false, rs2 = false, rs3 = true;
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s.getIdCode() + "\", " +
				"\"" + s.getDescription() + "\", " +
				s.getCapacity() + ");");
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s2.getIdCode() + "\", " +
				"\"" + s2.getDescription() + "\", " +
				s2.getCapacity() + ");");
		
		try{
			ClassroomDAO.getInstance().modify(s, s2);
		} finally {
		
		rs = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() + "\" and " +
				"sala.capacidade = " + s.getCapacity() +
				";");
		
		if(rs)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		
		rs2 = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() + "\" and " +
				"sala.capacidade = " + s2.getCapacity() +
				";");
		if(rs2)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() +  "\" and " +
				"sala.capacidade = " + s2.getCapacity() + ";");
		
		rs3 = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s3.getIdCode() + "\" and " +
				"sala.descricao = \"" + s3.getDescription() + "\" and " +
				"sala.capacidade = " + s3.getCapacity() +
				";");
		if(rs3)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s3.getIdCode() + "\" and " +
				"sala.descricao = \"" + s3.getDescription() +  "\" and " +
				"sala.capacidade = " + s3.getCapacity() + ";");
		}
		assertTrue("Testando Inclusao no Banco", rs && rs2 && !rs3);
	}
	@Test (expected= PatrimonioException.class)
	public void testAletarParaExistente() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoAlt", "Descricao Dois", "200");
		Sala s2 = new Sala("CodigoAlt", "Descricao Dois", "200");
		boolean rs = false, rs2 = true;
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s.getIdCode() + "\", " +
				"\"" + s.getDescription() + "\", " +
				s.getCapacity() + ");");
		
		try{
			ClassroomDAO.getInstance().modify(s, s2);
		} finally {
		
		rs = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() + "\" and " +
				"sala.capacidade = " + s.getCapacity() +
				";");
		
		if(rs)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		rs2 = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() + "\" and " +
				"sala.capacidade = " + s2.getCapacity() +
				";");
		if(rs2)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s2.getIdCode() + "\" and " +
				"sala.descricao = \"" + s2.getDescription() +  "\" and " +
				"sala.capacidade = " + s2.getCapacity() + ";");
		}
		assertTrue("Testando Inclusao no Banco", rs && !rs2);
	}
	
	
	@Test
	public void testExcluir() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		boolean rs = true;
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s.getIdCode() + "\", " +
				"\"" + s.getDescription() + "\", " +
				s.getCapacity() + ");");
		
		ClassroomDAO.getInstance().delete(s);
		
		rs = this.estaNoBanco("SELECT * FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() + "\" and " +
				"sala.capacidade = " + s.getCapacity() +
				";");
		
		if(rs)
			this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		assertTrue("Testando Inclusao no Banco", !rs);
	}
	@Test (expected= PatrimonioException.class)
	public void testExcluirNulo() throws PatrimonioException, SQLException {
		ClassroomDAO.getInstance().delete(null);
	}
	@Ignore // (expected= PatrimonioException.class)
	public void testExcluirEnvolvidoEmReserva() throws PatrimonioException, SQLException {
		
	}
	@Test (expected= PatrimonioException.class)
	public void testExcluirNaoExistente() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		ClassroomDAO.getInstance().delete(s);
	}
	
	
	@Test
	public void testBuscarCodigo() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s.getIdCode() + "\", " +
				"\"" + s.getDescription() + "\", " +
				s.getCapacity() + ");");
		
		Vector<Sala> vet = ClassroomDAO.getInstance().searchByCode("CodigoInc");
		
		this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		assertTrue("Testando Buscar o Vetor de ", vet.size() > 0);
	}
	@Test
	public void testDescricao() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s.getIdCode() + "\", " +
				"\"" + s.getDescription() + "\", " +
				s.getCapacity() + ");");
		
		Vector<Sala> vet = ClassroomDAO.getInstance().searchByDescription("Descricao Da Sala Inclusao");
		
		this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		assertTrue("Testando Buscar o Vetor de ", vet.size() > 0);
	}
	@Test
	public void testCapacidade() throws PatrimonioException, SQLException {
		Sala s = new Sala("CodigoInc", "Descricao Da Sala Inclusao", "123");
		
		this.executaNoBanco("INSERT INTO " +
				"sala (codigo, descricao, capacidade) VALUES (" +
				"\"" + s.getIdCode() + "\", " +
				"\"" + s.getDescription() + "\", " +
				s.getCapacity() + ");");
		
		Vector<Sala> vet = ClassroomDAO.getInstance().searchByCapacity("123");
		
		this.executaNoBanco("DELETE FROM sala WHERE " +
				"sala.codigo = \"" + s.getIdCode() + "\" and " +
				"sala.descricao = \"" + s.getDescription() +  "\" and " +
				"sala.capacidade = " + s.getCapacity() + ";");
		
		assertTrue("Testando Buscar o Vetor de ", vet.size() > 0);
	}

	
	private void executaNoBanco(String msg) throws SQLException{
		Connection con =  FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(msg);
		pst.executeUpdate();
		pst.close();
		con.close();
	}
	private boolean estaNoBanco(String query) throws SQLException{
		Connection con = FactoryConnection.getInstance().getConnection();
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		
		if(!rs.next())
		{
			rs.close();
			pst.close();
			con.close();
			return false;
		}
		else {
			rs.close();
			pst.close();
			con.close();
			return true;
		}
	}
	
}
