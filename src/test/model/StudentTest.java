package test.model;

import static org.junit.Assert.*;

import model.Student;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.ClienteException;

public class StudentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	
	@Test
	public void testInstance() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "1234-5678", "Name@email");
		assertTrue("Test instance of Aluno", student instanceof Student);
	}
	
	@Test
	public void testName() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "1234-5678", "Name@email");
		assertTrue("Test name of Aluno", "Name" == student.getName());
	}

	@Test
	public void testCpf() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "1234-5678", "Name@email");
		assertTrue("Test CPF of Aluno", "040.757.021-70" == student.getCpf());
	}
	
	@Test
	public void testEnrollmentNumber() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "1234-5678", "Name@email");
		assertTrue("Test enrollment number of Aluno", "123456" == student.getEnrollmentNumber());
	}
	
	@Test
	public void testPhoneNumber() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "1234-5678", "Name@email");
		assertTrue("Test phone number of Aluno", "1234-5678" == student.getPhoneNumber());
	}
	
	@Test
	public void Testmail() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "1234-5678", "Name@email");
		assertTrue("Test E-mail of Aluno", "Name@email" == student.getEmail());
	}

	
	
	@Test (expected= ClienteException.class)
	public void testNameVazio() throws ClienteException {
		new Student("", "040.757.02170", "123456", "1234-5678", "Name@email");
	}

	@Test (expected= ClienteException.class)
	public void testNameNumero() throws ClienteException {
		new Student("Name12", "040.757.021-70", "123456", "1234-5678", "Name@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNameCaractere() throws ClienteException {
		new Student("Name*", "040.757.021-70", "123456", "1234-5678", "Name@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNullName() throws ClienteException {
		new Student(null, "040.757.021-70", "123456", "1234-5678", "Name@email");
	}

	
	
	@Test (expected= ClienteException.class)
	public void testeNullStringCpf() throws ClienteException {
		new Student("Name", "", "123456", "1234-5678", "Name@email");
	}

	@Test (expected= ClienteException.class)
	public void testCpfWithWords() throws ClienteException {
		new Student("Name", "O40.757.021-7O", "123456", "1234-5678", "Name@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNoDefaultCpf() throws ClienteException {
		new Student("Name", "04075702170", "123456", "1234-5678", "Name@email");
	}
	
	@Test (expected = ClienteException.class)
	public void testInvalidCpf() throws ClienteException {
		new Student("Name", "123.456.789-90", "123456", "1234-5678", "Name@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNullCpf() throws ClienteException {
		new Student("Name", null, "123456", "1234-5678", "Name@email");
	}
	
	
	
	@Test (expected= ClienteException.class)
	public void testNullStringEnrollmentNumber() throws ClienteException {
		new Student("Name", "040.757.021-70", "", "1234-5678", "Name@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNullEnrollmentNumber() throws ClienteException {
		new Student("Name", "040.757.021-70", null, "1234-5678", "Name@email");
	}
	
	
	
	@Test
	public void testNullStringPhoneNumber() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		assertTrue("Test de Telefone Vazio do Aluno", "" == student.getPhoneNumber());
	}
	
	@Test (expected= ClienteException.class)
	public void testNoDefaultPhoneNumber() throws ClienteException {
		new Student("Name", "040.757.021-70", "123456", "6133333333", "Name@email");
	}
	
	@Test (expected= ClienteException.class)
	public void testNullPhoneNumber() throws ClienteException {
		new Student("Name", "040.757.021-70", "123456", null, "Name@email");
	}

	
	
	@Test
	public void testNullStringEmail() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "1234-5678", "");
		assertTrue("Test null e-mail of Aluno", "" == student.getEmail());
	}
	
	@Test (expected= ClienteException.class)
	public void testNullEmail() throws ClienteException {
		new Student("Name", "040.757.021-70", "123456", "9999-9999", null);
	}

	
	
	@Test
	public void testEqualsTrue() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		Student otherStudent = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		assertTrue("Test e-mail of Aluno", student.equals(otherStudent));
	}
	
	@Test
	public void testEqualsFalseName() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		Student otherStudent = new Student("NameDiferente", "040.757.021-70", "12356", "(90) 9999-9999", "otherName@email");
		assertFalse("Test false name of Aluno", student.equals(otherStudent));
	}
	@Test
	public void testEqualsFalseCpf() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		Student otherStudent = new Student("Name", "490.491.781-20", "12356", "(90) 9999-9999", "otherName@email");
		assertFalse("Test false cpf of Aluno", student.equals(otherStudent));
	}
	@Test
	public void testEqualsFalseMatricula() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		Student otherStudent = new Student("Name", "040.757.021-70", "12356", "(90) 9999-9999", "otherName@email");
		assertFalse("Test flase enrollment number of  Aluno", student.equals(otherStudent));
	}
	@Test
	public void testEqualsFalseTelefone() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		Student otherStudent = new Student("Name", "040.757.021-70", "123456", "(90) 9999-9999", "otherName@email");
		assertFalse("Test false phone number of Aluno", student.equals(otherStudent));
	}
	@Test
	public void testEqualsFalseEmail() throws ClienteException {
		Student student = new Student("Name", "040.757.021-70", "123456", "", "Name@email");
		Student otherStudent = new Student("Name", "040.757.021-70", "123456", "", "otherName@email");
		assertFalse("Test false e-mail of Aluno", student.equals(otherStudent));
	}
}
