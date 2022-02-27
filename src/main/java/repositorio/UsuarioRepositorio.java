package repositorio;

import modelo.ConexionBaseDatos;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements Repositorio<Usuario>{

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }
    @Override
    public List<Usuario> listar()  {
        List<Usuario> usuario = new ArrayList<>();

        try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")){
         while (rs.next()) {
             Usuario p = crearUsuario(rs);
             usuario.add(p);

         }
    } catch (SQLException e ){
        e.printStackTrace();
    }
        return usuario;

           }


    @Override
    public Usuario porId(Long id) {
        Usuario usuario = null;
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?")){
            stmt.setLong(1,id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = crearUsuario(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) {
        String sql;
        if (usuario.getId() != null && usuario.getId()>0) {
            sql = "UPDATE usuarios SET username = ?, password= ?, email = ? WHERE id = ?";
        }else{
            sql = "INSERT INTO usuarios(username,password,email) VALUES(?,?,?)";
        }
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setString(1,usuario.getUserName());
            stmt.setString(2,usuario.getPassword());
            stmt.setString(3,usuario.getEmail());
            if (usuario.getId() != null && usuario.getId()>0){
                stmt.setLong(4,usuario.getId());
            }
            stmt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    public void eliminar(Long id) {
        try(PreparedStatement stmt= getConnection().prepareStatement("DELETE  FROM usurarios where id = ?")){
         stmt.setLong(1,id);
         stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    private Usuario crearUsuario(ResultSet rs) throws SQLException {

        Usuario p = new Usuario();
        p.setId( rs.getLong("id"));
        p.setUserName(rs.getString("userName"));
        p.setPassword(rs.getString("password"));
        p.setEmail(rs.getString("email"));

        return p;



    }
}
