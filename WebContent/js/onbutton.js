
function checkOpt()
{
	//alert("ddd");
	//alert("Ed = "+ipt_Ed.value);
	if(!patt_pos.test(pos_pickup.value))
	{
		return false;
	}
	else if(!patt_pos.test(pos_deliver.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_weight.value))
	{
		return false;
	}
	else if(!patt_time.test(ipt_Ep.value))
	{
		return false;
	}
	else if(!patt_time.test(ipt_Lp.value))
	{
		return false;
	}
	else if(!patt_time.test(ipt_Ed.value))
	{
		return false;
	}
	else if(!patt_time.test(ipt_Ld.value))
	{
		return false;
	}
	return true;
}
function confirm(mes)
{
	message.innerHTML = mes;
	$("div#confirm").slideDown(300);
}
function out(mes)
{
	//alert("sasfs");
	//$("#console").hide();
	$("#console").html($("#console").html()+"<div>"+mes+"</div>");
	//$("#console").slideDown(500).delay(5000).slideUp(500);	
}
function resetOpt()
{
	pos_pickup.value = "Position pickup";
	pos_deliver.value = "Position deliver";
	
	ipt_weight.value = "0";
	ipt_Ep.value = "00:00";
	ipt_Lp.value = "23:59";
	ipt_Ed.value = "00:00";
	ipt_Ld.value = "23:59";
	//req_active = null;
}
function saveReq()
{
	if(req_active == req_counter)
	{
		tmp_request = new Request(req_counter,pmarker,dmarker,ipt_weight.value,ipt_Ep.value,ipt_Lp.value,ipt_Ed.value,ipt_Ld.value);
		req_counter++;
		arr_request.push(tmp_request);
		dmarker = new google.maps.Marker({});
		pmarker = new google.maps.Marker({});
		addReq(tmp_request);
		out("Saved!");
	}else
	{
		updateReq();
		out("Updated!");
	}
	//alert("OK");
}
function updateReq()
{
	id = req_active;
	if(checkOpt())
	{
		for(var i = 0;i < arr_request.length;i++)
		{
			if(arr_request[i].id == id)
			{
				arr_request[i].weight = ipt_weight.value;
				arr_request[i].Ep = ipt_Ep.value;
				arr_request[i].Lp = ipt_Lp.value;
				arr_request[i].Ed = ipt_Ed.value;
				arr_request[i].Ld = ipt_Ld.value;
				break;
			}
		}
	}
	else
	{
		out("Đầu vào không hợp lệ");
	}
}
function removeReq(id,obj)
{
	for(var i = 0;i < arr_request.length;i++)
	{
		if(arr_request[i].id == id)
		{
			arr_request[i].pickup.setMap(null);
			arr_request[i].deliver.setMap(null);
			arr_request.splice(i, 1);			
			//req_active = null;
			break;
		}
	}
	//resetOpt();
	if(req_active == id)
	{
		req_active = req_counter;
		resetOpt();
	}
	$(obj).parent().remove();
}
function disReq()
{
	for(var i = 0;i < arr_request.length;i++)
	{
		arr_request[i].pickup.setIcon(icon_pickup_nact);
		arr_request[i].deliver.setIcon(icon_deliver_nact);
	}	
}
function stopAnimation()
{
	for(var i = 0;i < arr_request.length;i++)
	{
		arr_request[i].pickup.setAnimation(null);
		arr_request[i].deliver.setAnimation(null);
	}	
}
function startAnimation(id)
{
	for(var i = 0;i < arr_request.length;i++)
	{
		if(arr_request[i].id == id)
		{
			arr_request[i].pickup.setAnimation(google.maps.Animation.BOUNCE);
			arr_request[i].deliver.setAnimation(google.maps.Animation.BOUNCE);
			break;
		}
	}
}

function activeRequest(id)
{
	req_active = id;
	for(var i = 0;i < arr_request.length;i++)
	{
		arr_request[i].pickup.setIcon(icon_pickup_nact);
		arr_request[i].deliver.setIcon(icon_deliver_nact);
		arr_request[i].pickup.setDraggable(false);
		arr_request[i].deliver.setDraggable(false);
		
	}
	for(var i = 0;i < arr_request.length;i++)
	{
		if(arr_request[i].id == id)
		{
			arr_request[i].pickup.setIcon(icon_pickup);
			arr_request[i].deliver.setIcon(icon_deliver);
			arr_request[i].pickup.setDraggable(true);
			arr_request[i].deliver.setDraggable(true);			
			loadReq(arr_request[i]);
			break;
		}
	}
}
function loadReq(req)
{
	pos_pickup.value = req.pickup.getPosition();
	pos_deliver.value = req.deliver.getPosition();
	ipt_weight.value = req.weight;
	ipt_Ep.value = req.Ep;
	ipt_Lp.value = req.Lp;
	ipt_Ed.value = req.Ed;
	ipt_Ld.value = req.Ld;
}
function addReq(req)
{
	treq = '<li><input type="button" onclick="clickReq(this.value)" class="btn-require" value="'+req.id+'"/><img title="Remove" src="./img/Close-2-icon.png" class="icon-remove" onclick="removeReq('+req.id+',this)">	</li>';
	$("#list-request").append(treq);
}
function showOut()
{
	alert(req_active);
}
function clickReq(id)
{
	//alert("id: "+id);
	//out("clickR");
	if(req_active == req_counter)
	{
		if(pmarker != null)
	      {
			  pmarker.setMap(null);				  
	      }	
		if(dmarker != null)
	      {
			  dmarker.setMap(null);
	      }
	}
	activeRequest(id);
}
function getParameterRequest(prm)
{
	var drtn = new Array();
	for(var i = 0;i < arr_request.length ; i++)
	{
		switch(prm)
		{
		case 'pickup':
			drtn[i] = arr_request[i].pickup;
			break;
		case 'deliver':
			drtn[i] = arr_request[i].deliver;
			break;
		case 'pickup-position':
			drtn[i] = arr_request[i].pickup.getPosition();
			break;
		case 'deliver-position':
			drtn[i] = arr_request[i].deliver.getPosition();
			break;
		case 'weight':
			drtn[i] = arr_request[i].weight;
			break;
		case 'Ep':
			drtn[i] = arr_request[i].Ep;
			break;
		case 'Lp':
			drtn[i] = arr_request[i].Lp;
			break;
		case 'Ed':
			drtn[i] = arr_request[i].Ed;
			break;
		case 'Ld':
			drtn[i] = arr_request[i].Ld;
			break;
		default: return null;
		}
	}
	return drtn;
}
function calculateDistances() {
	  var service = new google.maps.DistanceMatrixService();
	  service.getDistanceMatrix(
	    {
	      origins: getParameterRequest("pickup-position").concat(getParameterRequest("deliver-position")),
	      destinations: getParameterRequest("pickup-position").concat(getParameterRequest("deliver-position")),
	      travelMode: google.maps.TravelMode.DRIVING,
	      unitSystem: google.maps.UnitSystem.METRIC,
	      avoidHighways: false,
	      avoidTolls: false
	    }, callback);
	  //window.setTimeout(postData,4000);
}
function callback(response, status) {
	  if (status != google.maps.DistanceMatrixStatus.OK) 
	  {
	    //alert('Error was: ' + status);
	    out("Can't get matrix distance");
	    arr_matrix_distances = null;
	  } else {
		  arr_matrix_distances = response.rows;
		  out("Get success!");  
	  }	
	  
	  //alert("CALLBACK");
}
function postData()
{
	//calculateDistances();
	//alert(JSON.stringify(getParameterRequest("pickup-position")));
	//alert(JSON.stringify(arr_matrix_distances.rows));
	$.ajax(
			{
				url:"ControllerServlet",
				type:"POST",
				//dataType: "jsonp", 
				//contentType: "application/json",
				data:{			   
				    pickup: JSON.stringify(getParameterRequest("pickup-position")),
				    deliver: JSON.stringify(getParameterRequest("deliver-position")),
				    weight: JSON.stringify(getParameterRequest("weight")),
				    Ep: JSON.stringify(getParameterRequest("Ep")),
				    Lp: JSON.stringify(getParameterRequest("Lp")),
				    Ed: JSON.stringify(getParameterRequest("Ed")),
				    Ld: JSON.stringify(getParameterRequest("Ld")),
				    matrix_distances:JSON.stringify(arr_matrix_distances)
				},
				success:function(data)
				{
			    	alert("Data: " + data + "\nStatus: ");
			 	},
				error:function(status,stt,err)
				{
					alert("Status: "+stt + "    ERR: "+err);
				}
			}
			);
	//);
}