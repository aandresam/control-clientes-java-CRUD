package test;

import datos.ClienteDaoJDBC;
import dominio.Cliente;

public class test {
    public static void main(String[] args) {
        ClienteDaoJDBC clienteDao = new ClienteDaoJDBC();
        Cliente clienteRecuperado = clienteDao.findById(new Cliente(2));
        System.out.println(clienteRecuperado);
    }
}
