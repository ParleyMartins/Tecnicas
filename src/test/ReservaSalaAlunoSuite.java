package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManageReserveRoomStudentTest;
import test.model.StudentReserveRoomTest;
import test.persistence.ResSalaAlunoDAOTest;

@RunWith(Suite.class)
@SuiteClasses({StudentReserveRoomTest.class, ResSalaAlunoDAOTest.class, ManageReserveRoomStudentTest.class})
public class ReservaSalaAlunoSuite {

}
