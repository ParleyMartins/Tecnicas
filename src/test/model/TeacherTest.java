package test.model;

import static org.junit.Assert.*;

import model.Teacher;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.ClienteException;

public class TeacherTest {
	
	/**
	 *	Os teste da classe Cliente foram feitos aqui, por se tratar de uma classe
	 * abstrata, ela nao eh instaciavel, entao todas as suas funcionalidade foram
	 * testadas a partir das instancias da classe Teacher.
	 */

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	@Test
	public void testInstance() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "1234-5678",
				"Nome@email");
		assertTrue("Teste de Instanciamento do Professor", teacher instanceof Teacher);
	}
	
	@Test
	public void testName() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "1234-5678",
				"Nome@email");
		assertTrue("Teste do Nome do Professor", "Nome" == teacher.getName());
	}

	@Test
	public void testCpf() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		assertTrue("Teste do CPF do Professor", "868.563.327-34" == teacher.getCpf());
	}
	
	@Test
	public void testEnrollmentNumber() throws ClienteException {
		Teacher teacher= new Teacher("Nome", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		assertTrue("Teste da Matricula do Professor", "123456" == teacher.getEnrollmentNumber());
	}
	
	@Test
	public void testTelephone() throws ClienteException {
		Teacher p = new Teacher("Nome", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		assertTrue("Teste de Telefone do Professor", "1234-5678" == p.getPhoneNumber());
	}
	
	@Test
	public void testEmail() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "1234-5678", "Nome@email");
		assertTrue("Teste do E-mail do Professor", "Nome@email" == teacher.getEmail());
	}

	
	
	@Test (expected= ClienteException.class)
	public void testEmptyName() throws ClienteException {
		new Teacher("", "868.563.327-34", "123456", "1234-5678", "Nome@email");
	}

	@Test (expected= ClienteException.class)
	public void testNumberName() throws ClienteException {
		new Teacher("Nome1", "868.563.327-34", "123456", "1234-5678", "Nome@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testCharacterName() throws ClienteException {
		new Teacher("Nome+", "868.563.327-34", "123456", "1234-5678", "Nome@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNomeNulo() throws ClienteException {
		new Teacher(null, "868.563.327-34", "123456", "1234-5678", "Nome@email");
	}

	
	
	@Test (expected= ClienteException.class)
	public void testCpfVazio() throws ClienteException {
		new Teacher("Nome", "", "123456", "1234-5678", "Nome@email");
	}

	@Test (expected= ClienteException.class)
	public void testCpfLetters() throws ClienteException {
		new Teacher("Nome", "868.563.327-3d", "123456", "1234-5678", "Nome@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNonstandardizedCpf() throws ClienteException {
		new Teacher("Nome", "86856332734", "123456", "1234-5678", "Nome@email");
	}
	
	@Test (expected = ClienteException.class)
	public void testIvalidCpf() throws ClienteException {
		new Teacher("Nome", "868.563.327-21", "123456", "1234-5678", "Nome@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNullCpf
	() throws ClienteException {
		new Teacher("Nome", null, "123456", "1234-5678", "Nome@email");
	}
	
	
	
	@Test (expected= ClienteException.class)
	public void testEmptyEnrollmentNumber() throws ClienteException {
		new Teacher("Nome", "868.563.327-34", "", "1234-5678", "Nome@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNullEnrollmentNumber() throws ClienteException {
		new Teacher("Nome", "868.563.327-34", null, "1234-5678", "Nome@email");
	}
	
	
	
	@Test
	public void testEmptyTelephone() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		assertTrue("Teste de Telefone Vazio do Professor", "" == teacher.getPhoneNumber());
	}
	
	@Test (expected= ClienteException.class)
	public void testNonstandardizedTelephone() throws ClienteException {
		new Teacher("Nome", "868.563.327-34", "123456", "(901234-5678", "Nome@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testnullTelephone() throws ClienteException {
		new Teacher("Nome", "868.563.327-34", "123456", null, "Nome@email");
	}

	
	
	@Test
	public void testEmptyEmail() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "1234-5678", "");
		assertTrue("Teste de Email Vazio do Professor", "" == teacher.getEmail());
	}
	
	@Test (expected= ClienteException.class)
	public void testNullEmail() throws ClienteException {
		new Teacher("Nome", "868.563.327-34", "123456", "123456", null);
	}

	
	
	@Test
	public void testEqualsTrue() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		Teacher otherTeacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		assertTrue("Teste do E-mail do Professor", teacher.equals(otherTeacher));
	}
	
	@Test
	public void testEqualsFalseNome() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		Teacher otherTeacher = new Teacher("NomeDiferente", "868.563.327-34", "12356", "(90) 1234-3344", "Nom@email");
		assertFalse("Teste do E-mail do Professor", teacher.equals(otherTeacher));
	}
	@Test
	public void testEqualsFalseCpf() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		Teacher otherTeacher = new Teacher("Nome", "338.688.964-65", "12356", "(90) 1234-3344", "Nom@email");
		assertFalse("Teste do E-mail do Professor", teacher.equals(otherTeacher));
	}
	@Test
	public void testEqualsFalseMatricula() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		Teacher otherTeacher = new Teacher("Nome", "868.563.327-34", "12356", "(90) 1234-3344", "Nom@email");
		assertFalse("Teste do E-mail do Professor", teacher.equals(otherTeacher));
	}
	@Test
	public void testEqualsFalseTelefone() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		Teacher otherTeacher = new Teacher("Nome", "868.563.327-34", "123456", "(90) 1234-3344", "Nom@email");
		assertFalse("Teste do E-mail do Professor", teacher.equals(otherTeacher));
	}
	@Test
	public void testEqualsFalseEmail() throws ClienteException {
		Teacher teacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nome@email");
		Teacher otherTeacher = new Teacher("Nome", "868.563.327-34", "123456", "", "Nom@el");
		assertFalse("Teste do E-mail do Professor", teacher.equals(otherTeacher));
	}
}
