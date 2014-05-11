package com.lhweb.load.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhweb.load.model.SparePart;

/**
 * Servlet implementation class getSpare
 */
@WebServlet("/getSpare")
public class getSpare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getSpare() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String brandNumber = request.getParameter("brandNumber").toString();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		SparePart sparePart = new SparePart();
		sparePart.loadSparePartByBrandNumber(brandNumber);
		String result = sparePart.getBarCode();
		result +=","+sparePart.getSparePartName();
		result +=","+sparePart.getBriefDescription();
		result +=","+sparePart.getBrand();
		result +=","+sparePart.getBrandNumber();
		result +=","+sparePart.getPartNumber();
		//result +=","+sparePart.getSystem();
		//result +=","+sparePart.getProviders();
		result +=","+sparePart.getStockMin();
		result +=","+sparePart.getSalePrice();
		result +=","+sparePart.getSpecialOfferPrice();
		System.out.println(result);		
		out.print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
