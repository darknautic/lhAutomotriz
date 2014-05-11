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
	
</head>
<script type="text/javascript">
	$(document).ready( function() {
	    $(".menuLH").on("click", function() {
	    	$("#contentLH").attr("src" , $(this).attr("data-url"));
	    	$(".menuLH").removeClass("active");
	    	$(this).addClass("active");
	    });
	});
</script>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
	    <div class="container">
	      <div class="navbar-header">
	        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	        </button>
	        <a class="navbar-brand" href="#">LH</a>
	      </div>
	      <div class="collapse navbar-collapse">
	        <ul class="nav navbar-nav">
	          <li class="menuLH" data-url="cargaArticulo.jsp"><a href="#">Carga Articulos</a></li>
	          <li class="menuLH" data-url="altaProducto.jsp"><a href="#">Entrada Almacen</a></li>
	          <li class="menuLH" data-url="ventaProducto.jsp"><a href="#">Registro de Ventas</a></li>
	          <li class="menuLH" data-url="inventario.jsp"><a href="#">Inventario</a></li>
	        </ul>
	        <ul class="nav navbar-nav navbar-right">
		      <li class="dropdown">
		        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Ayuda <b class="caret"></b></a>
		        <ul class="dropdown-menu">
		          <li><a href="#">Bienvenidos</a></li>
		          <li><a href="#">Quienes Somos</a></li>
		          <li class="divider"></li>
		          <li><a href="#">Hacerca de LH Web</a></li>
		        </ul>
		      </li>
		    </ul>
	      </div><!--/.nav-collapse -->
	    </div>
	  </div>
	<div  id="content" style="margin-top: 5%; margin-left:5%; width: 90%;">
		<iframe id="contentLH" style="position:absolute; top:5%; left:0px; bottom:0px; right:0px; width:100%; height:95%; border:none; margin:0; padding:0;"></iframe>
	</div>		
</body>
</html>