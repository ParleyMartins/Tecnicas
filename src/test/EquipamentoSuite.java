package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.control.ManageEquipmentTest;
import test.model.EquipmentTest;
import test.persistence.EquipamentDAOTest;


@RunWith(Suite.class)
@SuiteClasses({EquipmentTest.class, EquipamentDAOTest.class, ManageEquipmentTest.class })
public class EquipamentoSuite {

}
