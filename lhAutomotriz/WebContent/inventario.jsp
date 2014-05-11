<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
        <title>LH ..... </title>
        
		<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
		<script src="js/validator.js"></script>
		<script type='text/javascript' src="js/allJs.js"></script>
		
		<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.js"></script>
		<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
        
        <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">                
        <style type="text/css">

/* Set the fixed height of the footer here */

</style>
<script type="text/javascript">

$(document).ready(function(){
	
	$("#cerrarEdi").click(function(env){
		$( "#containerTableI" ).hide( "blind", { to: { width: 200, height: 60 } }, 1000 );
		$( "#containerTableP" ).show( "blind", { to: { width: 200, height: 60 } }, 1000 );
	});
	
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
   				}];
   			} catch (err) {
   			};
   			
   			jQuery("#containerTable").empty();	
   			var tableData = '<table class="table table-striped table-bordered" id="inventarioTable">'+data+'</table>';

   			jQuery("#containerTable").append(tableData);
   			
   			var oTable = jQuery("#inventarioTable").dataTable({
   				"aoColumns" : rowsHeaders
   			});
			
   			$("#inventarioTable").delegate("tbody tr", "click", function () {
   				/*var aPos   = oTable.fnGetPosition(this);
   	            var aData = oTable.fnGetData(aPos[0]); 
   	            alert(aData);*/
   	            
	   	         // get the position of the current data from the node
	   	         var aPos = oTable.fnGetPosition( this );
	
	   	         // get the data array
	   	         var aData = oTable.fnGetData( aPos[0] );
	
	   	         // get departmentID for the row
	   	         var departmentID = aData[aPos][2];
	   	         console.log(departmentID);
	   	      	 alert(departmentID);
   	            
	   	      	 
	   	      	 
	   	      	 
	   	      	 
   				$( "#containerTableP" ).hide( "blind", { to: { width: 200, height: 60 } }, 1000 );
   				$( "#containerTableI" ).show( "blind", { to: { width: 200, height: 60 } }, 1000 );				
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
       <div class="container">
     	<div id="containerTableP" class="container col-md-12">
			<h7>Inventario</h7><br><br>
			<div id="containerTable" style="width: 98%; border: 1px;"></div>	
		</div>
		<div id="containerTableI" class="container col-md-12" style="display: none;">			   
			   <div id="formContainer" style="margin-top: 5%; margin-left:5%; width: 90%;">
			   		<div id="cerrarEdi">Regresar</div>
					<form class="form-horizontal" data-toggle="validator">
					  	<fieldset>
						    <legend>Editar Articulo LH</legend>				    
						    <div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">C&oacute;digo de Barras:</label>	  
							  	<div class="col-xs-8">
							    	<input id="barCode" type="text" class="form-control" placeholder="C&oacute;digo de Barras" data-error="Por Favor, Ingresa un Co&oacute;digo de Barras" required>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">Nombre de Art&iacute;culo:</label>	  
							  	<div class="col-xs-8">
							    	<input id="sparePart" type="text" class="form-control" placeholder="Nombre de Art&iacute;culo" data-error="Por Favor, Ingresa un Nombre de Art&iacute;culo" required>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">Descripci&oacute;n:</label>	  
							  	<div class="col-xs-8">
							    	<input id="briefDescription" type="text" class="form-control" placeholder="Descripci&oacute;n" data-error="Por Favor, Ingresa una Descripci&oacute;n" required>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">Marca:</label>	  
							  	<div class="col-xs-8">
							    	<input id="brand" type="text" class="form-control" placeholder="Marca" list="brands" data-error="Por Favor, Ingresa una Descripci&oacute;n" required/>
						            <datalist id="brands">              
						              <option value="NGK"></option>
						              <option value="Bogue"></option>
						              <option value="KYB"></option>                           
						            </datalist>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">N&uacute;mero de Parte:</label>	  
							  	<div class="col-xs-8">
							    	<input id="brandNumber" type="text" class="form-control" placeholder="N&uacute;mero de Parte" list="brands" data-error="Por Favor, Ingresa un N&uacute;mero de Parte" required/>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">N&uacute;mero de Referencia:</label>	  
							  	<div class="col-xs-8">
							    	<input id="partNumber" type="text" class="form-control" placeholder="N&uacute;mero de Referencia (opcional)">
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">Sistema:</label>	  
							  	<div class="col-xs-8">
							    	<input id="systems" type="text" class="form-control" placeholder="Sistemas" list="systems" data-error="Por Favor, Ingresa un Sistema" required/>
						            <datalist id="systems">              
						              <option value="Afinacion"></option>
						              <option value="Suspension"></option>                                         
						            </datalist>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">Proveedor:</label>	  
							  	<div class="col-xs-8">
							    	<input id="providers" type="text" class="form-control" placeholder="Proveedores" list="providersList" data-error="Por Favor, Ingresa un Proveedor" required/>
						            <datalist id="providersList">              
						              <option value="Sagaji"></option>
						              <option value="Rolecar"></option>
						              <option value="Cordero"></option>
						              <option value="Egarama"></option>
						              <option value="SYD"></option>                                         
						            </datalist>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">M&iacute;nimo en Almac&eacute;n:</label>	  
							  	<div class="col-xs-8">
							    	<input id="stockMin" type="number" min="0" class="form-control" placeholder="M&iacute;nimo en Almac&eacute;n" data-error="Por Favor, Ingresa un M&iacute;nimo en Almac&eacute;n" required>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">Precio de venta:</label>	  
							  	<div class="col-xs-8">
							    	<input id="salePrice" type="number" min="0" class="form-control" placeholder="Precio Venta" data-error="Por Favor, Ingresa un Precio de Venta" required>
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						   		<label for="inputEmail3" class="col-sm-2 control-label">Precio Especial / Promoci&oacute;n:</label>	  
							  	<div class="col-xs-8">
							    	<input id="specialOfferPrice" type="number" min="0" class="form-control" placeholder="Precio Especial / Promoci&oacute;n ">
							    	<div class="help-block with-errors"></div>
							  	</div>
							</div>
							<div class="form-group">
						      <div class="col-lg-10 col-lg-offset-2">
						        <button type="submit" id="registrar" class="btn btn-primary" disabled="disabled">Registrar</button>
						      </div>
							</div>
						</fieldset>
					 </form>					
	  			</div>
			
		</div>
  	</div>

</div>
    </body>   
</html>


    