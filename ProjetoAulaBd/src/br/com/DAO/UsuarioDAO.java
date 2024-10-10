package br.com.DAO;

import br.com.DTO.UsuarioDTO;
import br.com.Views.TelaUsuarios;
import java.sql.*;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    public static UsuarioDAO objUsuarioDAO;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void inserirUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "INSERT INTO tb_usuarios(id_usuario, usuario, login, senha) VALUES(?, ?, ?, ?)";

        conexao = new ConexaoDAO().conector();

        try {
            // Verifica se o ID do usuário é menor que 0
            if (objUsuarioDTO.getId_usuario() < 0) {
                JOptionPane.showMessageDialog(null, "Erro: ID do usuário não pode ser menor que 0.");
                return;
            }

            // Verifica se os campos obrigatórios estão preenchidos
            if (objUsuarioDTO.getNomeUsuario().isEmpty()
                    || objUsuarioDTO.getLoginUsuario().isEmpty()
                    || objUsuarioDTO.getSenhaUsuario().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Erro: Todos os campos são obrigatórios.");
                return;
            }

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            pst.setString(2, objUsuarioDTO.getNomeUsuario());
            pst.setString(3, objUsuarioDTO.getLoginUsuario());
            pst.setString(4, objUsuarioDTO.getSenhaUsuario());

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Usuário já existe ou não foi inserido.");
            }

            pst.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de erro para entrada duplicada
                JOptionPane.showMessageDialog(null, "Erro: Usuário já existe.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir usuário: " + e.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }

    }

    public void editar(UsuarioDTO objUsuarioDTO) {

        String sql = "update tb_usuarios set usuario=?, login=?, senha=? where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(4, objUsuarioDTO.getId_usuario());
            pst.setString(1, objUsuarioDTO.getNomeUsuario());
            pst.setString(2, objUsuarioDTO.getLoginUsuario());
            pst.setString(3, objUsuarioDTO.getSenhaUsuario());

            int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Usuario Editado com sucesso!");
                apagarCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Metodo editar" + e);
            
        }
    }

    public void apagarCampos() {

        TelaUsuarios.txtIDUsuario.setText(null);
        TelaUsuarios.txtNomeUsuario.setText(null);
        TelaUsuarios.txtLoginUsuario.setText(null);
        TelaUsuarios.txtSenhaUsuario.setText(null);
    }


public void apagar(UsuarioDTO objUsuarioDTO){
    String sql = " delete from tb_usuarios where id_usuario = ?";
    conexao = ConexaoDAO.conector();
    
    try{
        pst = conexao.prepareStatement(sql);
        pst.setInt(1, objUsuarioDTO.getId_usuario());
        int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Usuario Excluido com sucesso!");
                apagarCampos();
            }
        
        
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, "Metodo apagar "+ e);
    }
}


public void deletar(UsuarioDTO objUsuarioDTO){
    String sql = "delete from tb_usuarios where id_usuario = ?";
    conexao = ConexaoDAO.conector();
    
    try{
        pst = conexao.prepareStatement(sql);
        pst.setInt(1, objUsuarioDTO.getId_usuario());
        int del = pst.executeUpdate();
        if(del>0){
            JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso!");
           apagarCampos();
            
        }
        
    }catch(Exception e ){
        JOptionPane.showMessageDialog(null, "Metodo deletar "+e);
    }
}

}
