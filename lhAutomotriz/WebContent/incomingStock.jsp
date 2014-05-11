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
	
	
	/*System.out.println(sparePartString);
	System.out.println(aux);*/
	
	
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
  
  StockLog stockLog = new StockLog();
  
  HashMap<String, Object> entrada = new HashMap<String, Object>();
  /*Fecha de acceso*/
  Calendar eventDate = Calendar.getInstance();
  TimeZone mexicoTime = TimeZone.getTimeZone("America/Mexico_City");
  eventDate.setTimeZone(mexicoTime);
  
  stockLog.setBarCode(keyValue.get("barCode"));  
  stockLog.setEventDate(eventDate.getTime());
  stockLog.setHowMany(Integer.parseInt(keyValue.get("howMany")));
  stockLog.setPartNumber(keyValue.get("partNumber"));
  stockLog.setProvider(keyValue.get("provider"));
  stockLog.setPurchasePrice(Double.parseDouble(keyValue.get("purchasePrice")));
  
  int responseValue = stockLog.incoming();
  
  if(responseValue<0){
		out.println("Se Registro El Articulo");
	}else{
		out.println("El Articulo Ya Existe");
	}
  
%>
