package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.*;

public class ClienteDaoJDBC {

    private static final String SQL_SELECT = "SELECT id_cliente, nombre, apellido, email, telefono, saldo " 
            + "FROM cliente";
    private static final String SQL_SELECT_BY_ID = "SELECT id_cliente, nombre, apellido, email, telefono, saldo "
            + "FROM cliente WHERE id_cliente = ?";
    private static final String SQL_INSERT = "INSERT INTO cliente (nombre, apellido, email, telefono, saldo) "
            + "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE cliente "
            + "SET nombre = ?, apellido = ?, email = ?, telefono = ?, saldo = ? WHERE id_cliente = ?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente = ?";

    public List<Cliente> findAll() {
        Connection conexion = null;
        PreparedStatement pdStatement = null;
        ResultSet resultSet = null;
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            conexion = Conexion.getConnection();
            pdStatement = conexion.prepareStatement(SQL_SELECT);
            resultSet = pdStatement.executeQuery();
            while (resultSet.next()) {
                int idCliente = resultSet.getInt("id_cliente");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");
                double saldo = resultSet.getDouble("saldo");

                cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(pdStatement);
            Conexion.close(conexion);
        }
        return clientes;
    }

    public Cliente findById(Cliente cliente) {
        Connection conexion = null;
        PreparedStatement pdStatement = null;
        ResultSet resultSet = null;
        try {
            conexion = Conexion.getConnection();
            pdStatement = conexion.prepareStatement(SQL_SELECT_BY_ID);
            pdStatement.setInt(1, cliente.getIdCliente());
            resultSet = pdStatement.executeQuery();
            resultSet.next(); //Posicionarse en el primer registro encontrado
            String nombre = resultSet.getString(2);
            String apellido = resultSet.getString(3);
            String email = resultSet.getString(4);
            String telefono = resultSet.getString(5);
            double saldo = resultSet.getDouble(6);

            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setSaldo(saldo);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(pdStatement);
            Conexion.close(conexion);
        }
        return cliente;
    }

    public int insert(Cliente cliente) {
        Connection conexion = null;
        PreparedStatement pdStatement = null;
        int affectedRows = 0;
        try {
            conexion = Conexion.getConnection();
            pdStatement = conexion.prepareStatement(SQL_INSERT);
            pdStatement.setString(1, cliente.getNombre());
            pdStatement.setString(2, cliente.getApellido());
            pdStatement.setString(3, cliente.getEmail());
            pdStatement.setString(4, cliente.getTelefono());
            pdStatement.setDouble(5, cliente.getSaldo());
            affectedRows = pdStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(pdStatement);
            Conexion.close(conexion);
        }
        return affectedRows;
    }

    public int update(Cliente cliente) {
        Connection conexion = null;
        PreparedStatement pdStatement = null;
        int affectedRows = 0;
        try {
            conexion = Conexion.getConnection();
            pdStatement = conexion.prepareStatement(SQL_UPDATE);
            pdStatement.setString(1, cliente.getNombre());
            pdStatement.setString(2, cliente.getApellido());
            pdStatement.setString(3, cliente.getEmail());
            pdStatement.setString(4, cliente.getTelefono());
            pdStatement.setDouble(5, cliente.getSaldo());
            pdStatement.setDouble(6, cliente.getIdCliente());
            affectedRows = pdStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(pdStatement);
            Conexion.close(conexion);
        }
        return affectedRows;
    }

    public int delete(Cliente cliente) {
        Connection conexion = null;
        PreparedStatement pdStatement = null;
        int affectedRows = 0;
        try {
            conexion = Conexion.getConnection();
            pdStatement = conexion.prepareStatement(SQL_DELETE);
            pdStatement.setDouble(1, cliente.getIdCliente());
            affectedRows = pdStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(pdStatement);
            Conexion.close(conexion);
        }
        return affectedRows;
    }
}
