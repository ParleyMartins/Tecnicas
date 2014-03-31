/**
ManterSala
Include the code and description of the room, change, and delete devices.
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/control/ManterSala.java
*/
package control;

import java.sql.SQLException;
import java.util.Vector;

import persistence.SalaDAO;
import exception.PatrimonioException;
import model.Sala;

public class ManterSala {

	private static ManterSala instance;
	
	private Vector <Sala> salas_vet = new Vector <Sala>( );


	private ManterSala ( ) {
		
		// Blank constructor.
	}

	// This constructor provides the singleton implementation.
	public static ManterSala getInstance ( ) {

		if (instance == null)
			instance = new ManterSala( );
		return instance;
	}

	// Gets a vector of room.
	public Vector <Sala> getSalas_vet ( ) throws SQLException,
			PatrimonioException {

		this.salas_vet = SalaDAO.getInstance( ).buscarTodos( );
		return this.salas_vet;
	}
	
	// This method include code and description of the room in the database.
	public void inserir (String codigo, String descricao, String capacidade)
			throws PatrimonioException, SQLException {

		Sala sala = new Sala(codigo, descricao, capacidade);
		SalaDAO.getInstance( ).incluir(sala);
		this.salas_vet.add(sala);
	}

	//  This method Update code and description info in the database.
	public void alterar (String codigo, String descricao, String capacidade,
			Sala sala) throws PatrimonioException, SQLException {

		Sala old_sala = new Sala(sala.getCodigo( ), sala.getDescricao( ),
				sala.getCapacidade( ));
		sala.setCodigo(codigo);
		sala.setDescricao(descricao);
		sala.setCapacidade(capacidade);
		SalaDAO.getInstance( ).alterar(old_sala, sala);
	}

	//This method deletes room form the database.
	public void excluir (Sala sala) throws SQLException, PatrimonioException {

		SalaDAO.getInstance( ).excluir(sala);
		this.salas_vet.remove(sala);
	}

}
