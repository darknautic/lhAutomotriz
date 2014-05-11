<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="US-ASCII">
<title>LH Web ....</title>
	<link href="css/bootstrap.css" type="text/css" rel="stylesheet">
	
	<!-- Latest compiled and minified JavaScript -->
	<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script src="js/validator.js"></script>
	<script type='text/javascript' src="js/allJs.js"></script>
	
</head>
<script type="text/javascript">
$(document).ready( function() {
	$("#registrar").click(function(env){
		var sparePart = {
				"barCode" : $("#barCode").val(),
				"cantidad" : $("#howMany").val(),
				"numSerie" : $("#brandNumber").val(),
				"total" : $("#total").val()
				};
		$(".form-control").val('');
		ajaxSend("outcomingStock.jsp", sparePart);
	});
});
</script>
<body>
	<div id="wrap">
	  <!-- SE GENERA CONTENEDOR DE FORMULARIO -->
		<div id="formContainer" style="margin-top: 5%; margin-left:5%; width: 90%;">
			<form class="form-horizontal" data-toggle="validator">
			  	<fieldset>
				    <legend>Registro de Venta LH</legend>				    
				    <div class="form-group">
				   		<label for="inputEmail3" class="col-sm-2 control-label">C&oacute;digo de Barras:</label>	  
					  	<div class="col-xs-8">
					    	<input id="barCode" type="text" class="form-control" placeholder="C&oacute;digo de Barras" data-error="Por Favor, Ingresa un Co&oacute;digo de Barras" required>
					    	<div class="help-block with-errors"></div>
					  	</div>
					</div>
					<div class="form-group">
				   		<label for="inputEmail3" class="col-sm-2 control-label">Cantidad:</label>	  
					  	<div class="col-xs-8">
					    	<input id="howMany" type="text" class="form-control" placeholder="Cantidad" data-error="Por Favor, Ingresa una Cantidad" required>
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
				   		<label for="inputEmail3" class="col-sm-2 control-label">Total de Venta:</label>	  
					  	<div class="col-xs-8">
					    	<input id="total" type="text" class="form-control" placeholder="Total de Venta" data-error="Por Favor, Ingresa el Total de la Venta" required>
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
</body>
</html>