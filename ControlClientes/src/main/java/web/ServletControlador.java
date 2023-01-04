package web;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion != null) {
            switch (accion) {
                case "update":
                    this.updateClienteByGet(request, response);
                    break;
                case "delete":
                    this.deleteCliente(request, response);
                    break;
                default:
                    this.defaultAccion(request, response);
            }
        } else {
            this.defaultAccion(request, response);
        }
    }

    public void updateClienteByGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = new ClienteDaoJDBC().findById(new Cliente(idCliente));
        request.setAttribute("cliente", cliente);
        String jspUpdate = "/WEB-INF/paginas/cliente/editarCliente.jsp";
        System.out.println(cliente);
        request.getRequestDispatcher(jspUpdate).forward(request, response);
    }

    private void deleteCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //recuperamos los valores del formulario editarCliente
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));

        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(idCliente);

        //Eliminamos el  objeto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().delete(cliente);
        System.out.println("registrosModificados = " + registrosModificados);

        //Redirigimos hacia accion por default
        this.defaultAccion(request, response);
    }

    private void defaultAccion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> clientes = new ClienteDaoJDBC().findAll();
        HttpSession session = request.getSession();
        session.setAttribute("clientes", clientes);
        session.setAttribute("totalClientes", clientes.size());
        session.setAttribute("saldoTotal", getFullBalance(clientes));
        //request.getRequestDispatcher("clientes.jsp").forward(request, response);
        response.sendRedirect("clientes.jsp");
    }

    private double getFullBalance(List<Cliente> clientes) {
        double fullBalance = 0;
        for (Cliente cliente : clientes) {
            fullBalance += cliente.getSaldo();
        }
        return fullBalance;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion != null) {
            switch (accion) {
                case "insert":
                    this.insertCliente(request, response);
                    break;
                case "update":
                    this.updateClienteByPost(request, response);
                    break;
                default:
                    this.defaultAccion(request, response);
            }
        } else {
            this.defaultAccion(request, response);
        }
    }

    private void insertCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recibimos los datos
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }
        //Creamos el cliente con el modelo definido
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);
        //Insertamos el cliente en la base de datos
        int affectedRows = new ClienteDaoJDBC().insert(cliente);
        System.out.println("Filas afectadas: " + affectedRows);

        //Una vez insertado el registro, redigimos hacia el index con el metodo defaultAccion
        this.defaultAccion(request, response);
    }

    private void updateClienteByPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //recuperamos los valores del formulario editarCliente
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

        //Modificar el  objeto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().update(cliente);
        System.out.println("registrosModificados = " + registrosModificados);

        //Redirigimos hacia accion por default
        this.defaultAccion(request, response);
    }

}
