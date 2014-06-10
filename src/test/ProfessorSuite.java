package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManageTeacherTest;
import test.model.TeacherTest;
import test.persistence.TeacherDAOTest;


@RunWith(Suite.class)
@SuiteClasses({TeacherTest.class, TeacherDAOTest.class, ManageTeacherTest.class })
public class ProfessorSuite {

}
