package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManterProfessorTest;
import test.model.ProfessorTest;
import test.persistence.TeacherDAOTest;


@RunWith(Suite.class)
@SuiteClasses({ProfessorTest.class, TeacherDAOTest.class, ManterProfessorTest.class })
public class ProfessorSuite {

}
