package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManterResSalaAlunoTest;
import test.model.StudentReserveRoomTest;
import test.persistence.ResSalaAlunoDAOTest;

@RunWith(Suite.class)
@SuiteClasses({StudentReserveRoomTest.class, ResSalaAlunoDAOTest.class, ManterResSalaAlunoTest.class})
public class ReservaSalaAlunoSuite {

}
