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
  	SparePart sparePart = new SparePart();
	sparePart.setBarCode(keyValue.get("barCode"));
	sparePart.setSparePartName(keyValue.get("sparePart"));
	sparePart.setBriefDescription(keyValue.get("briefDescription"));
	sparePart.setBrand(keyValue.get("brand"));
	sparePart.setPartNumber(keyValue.get("partNumber"));
	sparePart.setBrandNumber(keyValue.get("brandNumber"));
	//sparePart.setSystems(keyValue.get("briefDescription"));//arreglo
	//sparePart.setCompatibility(keyValue.get("briefDescription"));//json
	//sparePart.setProviders(keyValue.get("briefDescription"));//arreglo
	sparePart.setStockMin(Integer.parseInt(keyValue.get("stockMin").toString()));
	sparePart.setSalePrice(Double.parseDouble(keyValue.get("salePrice").toString()));
	//sparePart.setSpecialOfferPrice(Double.parseDouble(keyValue.get("")));
	int responseValue = sparePart.storeSparePart();
	
	if(responseValue<0){
		out.println("Se Registro El Articulo");
	}else{
		out.println("El Articulo Ya Existe");
	}
%>
