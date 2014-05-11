package com.lhweb.load.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String usuario = request.getParameter("usuario").toString();
		String password = request.getParameter("password").toString();
		
		if(usuario.equals("lh")){
			System.out.println("Entro");
			request.setAttribute("user", usuario);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			//response.sendRedirect("main.jsp");
		}else{
			System.out.println("No Entro");
			request.setAttribute("erroe", "Usuario Incorrecto");
			request.getRequestDispatcher("index.html").forward(request, response);
			//response.sendRedirect("error.html");
		}			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
