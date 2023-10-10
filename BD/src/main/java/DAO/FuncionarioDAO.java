package DAO;

import DTO.FuncionarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FuncionarioDAO {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<FuncionarioDTO> lista = new ArrayList<>();
    
    public void cadastrarFuncionario(FuncionarioDTO objfuncionariodto) {
        String sql = "insert into tabela(nome, cpf, email, senha) values(?, ?, ?, md5(?))";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            pstm = conn.prepareCall(sql);
            pstm.setString(1, objfuncionariodto.getNome());
            pstm.setString(2, objfuncionariodto.getCpf());
            pstm.setString(3, objfuncionariodto.getEmail());
            pstm.setString(4, objfuncionariodto.getSenha());
            
            pstm.execute();
            pstm.close();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Classe FuncionarioDAO" + erro);
        }
    }
 
    public ArrayList<FuncionarioDTO> pesquisarFuncionario() {
        String sql = "SELECT * FROM tabela";
        conn = new ConexaoDAO().conectaBD();
        
        try {
            
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()) {                
                FuncionarioDTO objFuncionarioDTO = new FuncionarioDTO(rs.getString("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("email"), rs.getString("senha"));
            
                lista.add(objFuncionarioDTO);
            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO Pesquisar" + erro);
        }
        return lista;
    }
   
    public void alterarFuncionario(FuncionarioDTO objFuncionarioDTO){
        String sql = "UPDATE tabela set nome = ?, cpf = ?, email = ?, senha = ? WHERE id = ?";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objFuncionarioDTO.getNome());
            pstm.setString(2, objFuncionarioDTO.getCpf());
            pstm.setString(3, objFuncionarioDTO.getEmail());
            pstm.setString(4, objFuncionarioDTO.getSenha());
            pstm.setString(5, objFuncionarioDTO.getId());
            
            pstm.execute();
            pstm.close();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Funcionario DAO Alterar" + erro);
        }
    }
    
    public void excluirFuncionario(FuncionarioDTO objFuncionarioDTO){
        String sql = "DELETE FROM tabela WHERE id = ?";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, objFuncionarioDTO.getId());
            
            pstm.execute();
            pstm.close();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "FuncioanrioDAO Excluir" + erro);
        }
    }
}
