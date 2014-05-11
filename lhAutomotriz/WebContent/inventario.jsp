<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <title>LH ..... </title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
        
        <script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type='text/javascript' src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
	<script type='text/javascript' src="js/allJs.js"></script>
        
        <style type="text/css">
            html,
body {
  height: 100%;
}

#wrap {
  min-height: 100%;
  height: 100%;
  /* Negative indent footer by its height */
  margin: 0 auto -82px 0;
  /* Pad bottom by footer height */
  padding: 0 0 80px 0;
}

/* Set the fixed height of the footer here */
#footer {
  height: 80px;
  background-color: #f5f5f5;
}


#wrap > .center-container {
  padding: 50px 0 0 0;
  margin:0;
}

#footer > .container {
  padding-top:5px
  padding-left: 15px;
  padding-right: 15px;
}

.center-container {
  height:100%;
  display: table;
  width:100%;
  margin:0;
}

.center-row {
  height:50%;
  width:100%;
  display: table-row;
}
  
.center-row > div {
  height:100%;
  width:50%;
  display: table-cell;
  border:0 solid #eee;
  color:#fff;
  vertical-align:middle;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	//$('#inventarioTable').dataTable();
	
	$.ajax({	
		type:"POST",
		url: "getInventory",
   		success: function(data)    {
   			console.log(data);
   			/*GENERA ARRAY DE DATOS A GRAFICAR*/
   			var rowsData = new Array();
   			
   			/*GENERA ARRAY DE ENCABEZADOS DE GRAFICA*/
   			try {
   				var rowsHeaders = [ {
   					"sTitle" : "Articulo"
   				}, {
   					"sTitle" : "Marca"
   				}, {
   					"sTitle" : "Numero de Parte"
   				}, {
   					"sTitle" : "Numero de Referencia"
   				}, {
   					"sTitle" : "Stock Minimo"
   				}, {
   					"sTitle" : "Existencia"
   				} ];
   			} catch (err) {
   			};
   			
   			jQuery("#containerTable").empty();	
   			var tableData = '<table class="table table-striped table-bordered" id="inventarioTable">'+data+'</table>';

   			jQuery("#containerTable").append(tableData);
   			
   			var dTable = jQuery("#inventarioTable").dataTable({
   				"aoColumns" : rowsHeaders
   			});

   		},
   		error:   function(xml,msg){ $("span#msg").append(" Error"); }
});
	
});
</script>
    </head>
   
    
    <body>
        
        <!-- Wrap all page content here -->
<div id="wrap">
  
  <!-- Fixed navbar -->
 
     <div class="container">
     	<div class="container col-md-7">

	
				<h7>Inventario</h7><br><br>
		<div id="containerTable" style="width: 98%; border: 1px; background-color: white;"></div>	
					
	
</div>
  	</div>

</div>
<!-- 
<div id="footer">
  <div class="container">
    <p>&nbsp;</p>
    <p class="lead">This Bootstrap layout was made @<a href="">Bootply.com</a></p>
  </div>
</div>-->
        
    </body>   
</html>


    