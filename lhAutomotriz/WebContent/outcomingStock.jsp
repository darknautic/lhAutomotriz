<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"	%>
<%@page import="java.util.List"		%>

<%@page import="com.lhweb.load.model.*"%>





<%@page	language="java" 
				contentType="text/html; charset=UTF-8"
    		pageEncoding="UTF-8"
    		%>


<%

	String sparePartString = request.getParameter("sparePart").toString();	
	Stock auxStock = new Stock();
	List<String> aux = auxStock.stringFormattedToStringList(sparePartString);
	
	
  HashMap<String,String> keyValue = new HashMap<String,String>();
    
  for(String s : aux){	 
	  
	  List<String> a;
	  a = Arrays.asList(s.split(":"));
	  
	  if(a.size() == 1 ){
		  keyValue.put(a.get(0), "");
	  }
	  else if (a.size() == 2 ){
		  keyValue.put(a.get(0),a.get(1));
	  }
	  	    
  }
  
  Sales sales = new Sales();
  //sales.setBrandNumber(keyValue.get("brandNumber"));
  sales.addItems(keyValue.get("barCode"), Integer.parseInt(keyValue.get("cantidad")));
  sales.setTotal(Double.parseDouble(keyValue.get("total")));
  int responseValue = sales.completeSale();
  
  if(responseValue<0){
		out.println("Se Registro La Venta");
	}else{
		out.println("No Se Registro La Venta");
	}
  
%>
