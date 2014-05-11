$("#altaSP").click(function(env){
	var sparePart = {
			"barCode" : $("#barCode").val(),
			"howMany" : $("#howMany").val(),
			"partNumber" : $("#numeroSerie").val(),
			"provider" : $("#proveedor").val(),
			"purchasePrice" : $("#precio").val()
			};
	$(".form-control").val('');
	ajaxSend("incomingStock.jsp", sparePart);
});

$("#ventaSP").click(function(env){
	var sparePart = {
			"barCode" : $("#barCode").val(),
			"cantidad" : $("#cantidad").val(),
			"numSerie" : $("#numeroSerie").val(),
			"total" : $("#total").val()
			};
	$(".form-control").val('');
	ajaxSend("outcomingStock.jsp", sparePart);
});

function ajaxSend(invoke, data){
	
	$.ajax({	
				type:"POST",
				url: invoke,
	       		data: "sparePart="+JSON.stringify(data),
	       		success: function(msg)    {
	       			alert(msg.trim());
	       			$("span#msg").empty();
	       			$("span#msg").append(msg);
	       		},
	       		error:   function(xml,msg){ 
	       			alert("Error:Favor de Revisar los Valores"); 
	       		}
	 });
}




