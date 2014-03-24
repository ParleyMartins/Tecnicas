/*
Name: EquipamentoDAO
Function: manage the DAO functions of the Equipamento model
*/

package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Equipamento;
import exception.PatrimonioException;

public class EquipamentoDAO {

    // Messages. 
    private static final String EQUIPAMENTO_JA_EXISTENTE = "Equipamento ja cadastrado.";
    private static final String EQUIPAMENTO_NAO_EXISTENTE = "Equipamento nao cadastrado.";
    private static final String EQUIPAMENTO_NULO = "Equipamento esta nulo.";
    private static final String EQUIPAMENTO_EM_USO = "Equipamento esta sendo utilizado em uma reserva.";
    private static final String CODIGO_JA_EXISTENTE = "Equipamento com o mesmo codigo ja cadastrado.";

    // Singleton implementation. 
    private static EquipamentoDAO instance;

    private EquipamentoDAO() {
    	// Blank constructor. 
    }

    public static EquipamentoDAO getInstance() {
    	
        if ( instance == null ) {
            instance = new EquipamentoDAO();
        }
        
        return instance;
    }
    
    
    // Include new Equipamento in the database. 
    public void include(Equipamento equipamento) throws SQLException, 
    		PatrimonioException {
    	
        if ( equipamento == null ) {
            throw new PatrimonioException(EQUIPAMENTO_NULO);
        } else { 
        	if ( this.inDBCodigo(equipamento.getCodigo()) ) {
        		throw new PatrimonioException(CODIGO_JA_EXISTENTE);
        	} else { 
        		if ( !this.inDB(equipamento) ) {
        			this.updateQuery("INSERT INTO " + "equipamento " 
        					+ "(codigo, descricao) VALUES (" 
        					+ "\"" + equipamento.getCodigo() + "\", "
        					+ "\"" + equipamento.getDescricao() + "\");");
        		}
        	}
        }
    }

    // Update Equipamento info in the database.
    public void change(Equipamento old_equipamento, Equipamento new_equipamento) 
    			throws SQLException, PatrimonioException {
    	
        if ( old_equipamento == null ) {
            throw new PatrimonioException(EQUIPAMENTO_NULO);
        }
        if ( new_equipamento == null ) {
            throw new PatrimonioException(EQUIPAMENTO_NULO);
        }

        Connection con = FactoryConnection.getInstance().getConnection();
        PreparedStatement pst;

        if (!this.inDB(old_equipamento) ) {
            throw new PatrimonioException(EQUIPAMENTO_NAO_EXISTENTE);
        } else { 
        	if ( this.inOtherDB(old_equipamento) ) {
        		throw new PatrimonioException(EQUIPAMENTO_EM_USO);
        	} else { 
        		if ( !new_equipamento.getCodigo().equals(old_equipamento.getCodigo() ) 
        					&& this.inDBCodigo(new_equipamento.getCodigo()) ) {
        			throw new PatrimonioException(CODIGO_JA_EXISTENTE);
        		} else { 
        			if ( !this.inDB(new_equipamento) ) {
        				String msg = "UPDATE equipamento SET " 
        						+ "codigo = \"" + new_equipamento.getCodigo() 
        						+ "\", " + "descricao = \"" 
        						+ new_equipamento.getDescricao() 
        						+ "\"" + " WHERE " + "equipamento.codigo = \"" 
        						+ old_equipamento.getCodigo()
        						+ "\" and " + "equipamento.descricao = \"" 
        						+ old_equipamento.getDescricao() + "\";";

        				con.setAutoCommit(false);
        				pst = con.prepareStatement(msg);
        				pst.executeUpdate();
        				con.commit();

        				pst.close();

        			} else {
        				throw new PatrimonioException(EQUIPAMENTO_JA_EXISTENTE);
        			}
        		}
        	}
        }
        
        con.close();
    }
    
    // Remove Equipamento form the database. 
    public void exclude(Equipamento equipamento) throws SQLException, 
    		PatrimonioException {
    	
        if ( equipamento == null ) {
            throw new PatrimonioException(EQUIPAMENTO_NULO);
        } else {
        	if ( this.inOtherDB(equipamento) ) {
        		throw new PatrimonioException(EQUIPAMENTO_EM_USO);
        	}
        }
        
        if ( this.inDB(equipamento) ) {
            this.updateQuery("DELETE FROM equipamento WHERE " 
            		+ "equipamento.codigo = \"" + equipamento.getCodigo() 
            		+ "\" and " + "equipamento.descricao = \"" 
            		+ equipamento.getDescricao() + "\";");
        } else {
            throw new PatrimonioException(EQUIPAMENTO_NAO_EXISTENTE);
        }
    }
    
    // Retrive all Equipamento from the database.
    public Vector<Equipamento> searchAll() throws SQLException, 
    		PatrimonioException {
    	
        return this.search("SELECT * FROM equipamento;");
    }

    // Search an Equipamento by id code.
    public Vector<Equipamento> searchByCode(String valor) throws SQLException, 
    		PatrimonioException {
    	
        return this.search("SELECT * FROM equipamento WHERE codigo = " 
        		+ "\"" + valor + "\";");
    }

    // Search an Equipamento by description.
    public Vector<Equipamento> searchByDescription(String valor) throws 
    		SQLException, PatrimonioException {
    	
        return this.search("SELECT * FROM equipamento WHERE descricao = " 
        		+ "\"" + valor + "\";");
    }

    /*
    Private Methods
    */

    // Retrive Equipamento from the database.
    private Vector<Equipamento> search(String query) throws SQLException, 
    		PatrimonioException {
    	
        Vector<Equipamento> vet = new Vector<Equipamento>();

        Connection con = FactoryConnection.getInstance().getConnection();

        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while ( rs.next() ) {
            vet.add(this.fetchEquipamento(rs));
        }

        pst.close();
        rs.close();
        con.close();
        return vet;
    }

    // Check if there is a database entry by query. 
    private boolean inDBGeneric(String query) throws SQLException {
    	
        Connection con = FactoryConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        if ( !rs.next() ) {
            rs.close();
            pst.close();
            con.close();
            
            return false;
        } else {
            rs.close();
            pst.close();
            con.close();
            
            return true;
        }
    }

    // Check if there is a database entry by Equipamento. 
    private boolean inDB(Equipamento e) throws SQLException, 
    		PatrimonioException {
    	
        return this.inDBGeneric("SELECT * FROM equipamento WHERE " 
        		+ "equipamento.codigo = \"" + e.getCodigo() + "\" and "
                + "equipamento.descricao = \"" + e.getDescricao() + "\";");
    }

    // Check if there is a database entry by code id. 
    private boolean inDBCodigo(String codigo) throws SQLException {
    	
        return this.inDBGeneric("SELECT * FROM equipamento WHERE " 
        		+ "codigo = \"" + codigo + "\";");
    }

    // Check if there is a database entry. 
    private boolean inOtherDB(Equipamento e) throws SQLException {
    	
        return this.inDBGeneric("SELECT * FROM reserva_equipamento WHERE "
                + "id_equipamento = (SELECT id_equipamento FROM equipamento WHERE " 
        		+ "equipamento.codigo = \"" + e.getCodigo()
                + "\" and " + "equipamento.descricao = \"" 
        		+ e.getDescricao() + "\");");
    }

    // Fetch Equipamento usign a result.
    private Equipamento fetchEquipamento(ResultSet rs) throws 
    		PatrimonioException, SQLException {
    	
        return new Equipamento(rs.getString("codigo"), rs.getString("descricao"));
    }

    // Update a query. 
    private void updateQuery(String msg) throws SQLException {
    	
        Connection con = FactoryConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(msg);
        pst.executeUpdate();
        
        pst.close();
        con.close();
    }

}
