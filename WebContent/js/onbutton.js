
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
	$("#console").html("<div>"+mes+"</div>" + $("#console").html());
	//$("#console").slideDown(500).delay(5000).slideUp(500);	
}
function resetOpt()
{
	pos_pickup.value = "Position pickup";
	pos_deliver.value = "Position deliver";
	
	ipt_weight.value = "1";
	ipt_Ep.value = "00:00";
	ipt_Lp.value = "23:59";
	ipt_Ed.value = "00:00";
	ipt_Ld.value = "23:59";
	ipt_duration_pickup.value = 0;
	ipt_duration_deliver.value = 0;
	
	//req_active = null;
}
function saveReq()
{
	if(req_active == req_counter)
	{
		tmp_request = new Request(req_counter,pmarker,dmarker,ipt_weight.value,ipt_Ep.value,ipt_Lp.value,ipt_Ed.value,ipt_Ld.value,ipt_duration_pickup.value,ipt_duration_deliver.value);
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
				arr_request[i].duration_pickup = ipt_duration_pickup.value;
				arr_request[i].duration_deliver = ipt_duration_deliver.value;
				
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
			arr_request[i].deliver.setZIndex(9999);
			arr_request[i].pickup.setZIndex(9999);
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
	ipt_duration_pickup.value = req.duration_pickup;
	ipt_duration_deliver.value = req.duration_deliver;
	
}
function addReq(req)
{
	treq = '<li><input type="button" onclick="clickReq(this.value)" class="btn-require" value="'+req.id+'"/><img title="Remove" src="./img/Close-2-icon.png" class="icon-remove" onclick="removeReq('+req.id+',this)">	</li>';
	$("#list-request").append(treq);
}
function showOut()
{
	out(arr_request[0].Ep);
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
		case 'duration-pickup':
			drtn[i] = arr_request[i].duration_pickup;
			break;
		case 'duration-deliver':
			drtn[i] = arr_request[i].duration_deliver;
			break;
		default: return null;
		}
	}
	return drtn;
}
// var numrange = 10;
function getRangeMatrix()
{
	var pk = getParameterRequest("pickup-position");
	var dl = getParameterRequest("deliver-position");
	var dp = new Array();
	dp[0] = depot.getPosition();
	//out(dp.concat(pk,dl));
	return dp.concat(pk,dl);
}
// function getRangeMatrixByGroup(group)
// {
// 	//console.log("group: "+group);
// 	return getRangeMatrix().slice(group*numrange,(group+1)*numrange);	
// }
// var valSynch = true;
// var cur_grow = 0;
// var cur_gcol = 0;
// var MAX_GROUP;
// // function calculateDistances2()
// // {
// // 	var rangeMatrix = new Array();
// // 	rangeMatrix = getRangeMatrix();
	
// // 	out(rangeMatrix);
// // 	for(var grow = 0; grow <= MAX_GROUP;grow++)
// // 	{
// // 		for(var gcol = 0; gcol <= MAX_GROUP;gcol++)	
// // 		{		
// // 			valSynch = false;	
// // 			cur_gcol = gcol;
// // 			cur_grow = grow;			
// // 			calculate(grow,gcol);
// // 			setTimeout(function(){},3000);
// // 			// $waitUntil(
// // 			// 	function(){return valSynch;},
// // 			// 	function(){}
// // 			// 	);
// // 			out(grow+":"+gcol+"______________________");
// // 		}
// // 	}
// // 	out("arr_matrix_distances: "+arr_matrix_distances);
// // }
// function calculateDistances()
// {
// 	var rangeMatrix = new Array();
// 	rangeMatrix = getRangeMatrix();
// 	out(rangeMatrix);
// 	MAX_GROUP = rangeMatrix.length/numrange;
// 	cur_grow = 0;
// 	cur_gcol = 0;
// 	for(grow = 0; grow <= MAX_GROUP;grow++)
// 	{
// 		for(gcol = 0; gcol <= MAX_GROUP;gcol++)	
// 		{	
								
// 			$waitUntil(
// 				function(){return fsync(grow,gcol);},
// 				function(){calculate(grow,gcol);},
// 				1000
// 				);
// 		}
// 	}
// 	//out("arr_matrix_distances: "+arr_matrix_distances);
// }
// function fsync(pgrow,pgcol)
// {
// 	//out("fsync: "+grow+"/"+gcol);
// 	out(pgrow+"/"+pgcol+"/");
// 	if(pgcol == cur_gcol && pgrow == cur_grow)
// 	{
// 		out(pgrow+"/"+pgcol+"/true");
// 		return true;
// 	}
// 	return false;
// }
// function calculate(grow,gcol) {

// 	  var service = new google.maps.DistanceMatrixService();
// 	  var rorigins = getRangeMatrixByGroup(grow);
// 	  var rdestinations = getRangeMatrixByGroup(gcol);	  
// 	  service.getDistanceMatrix(
// 	    {
// 	      origins: rorigins,
// 	      destinations: rdestinations,
// 	      travelMode: google.maps.TravelMode.DRIVING,
// 	      unitSystem: google.maps.UnitSystem.METRIC,
// 	      avoidHighways: false,
// 	      avoidTolls: false
// 	    }, callback);
	  
// }
// function nextGroup()
// {
// 	if(cur_gcol<MAX_GROUP)
// 	{
// 		cur_gcol++;
// 	}
// 	else
// 	{
// 		cur_grow++;
// 		cur_gcol = 0;
// 	}
// 	out("nextGroup: "+cur_grow+"/"+cur_gcol);
// }
// function callback(response, status) {
	
// 	  // var cur_grow = getGroup(response.originAddresses);
// 	  // var cur_gcol = getGroup(response.destinationAddresses);	  
// 	  out("in callback: "+cur_grow+"/"+cur_gcol);
// 	  if (status != google.maps.DistanceMatrixStatus.OK) 
// 	  {
// 	    //alert('Error was: ' + status);
// 	    out("Can't get matrix distance");
// 	    //arr_matrix_distances = null;
// 	  } else {
// 		  updateMatrixDisAndDur(cur_grow,cur_gcol,jsonToArrayMatrixDis(response),jsonToArrayMatrixDur(response));		  
// 		  out("Get success!"+cur_grow+"/"+cur_gcol);  
// 	  }	
// 	  nextGroup();	  
// }
// function $waitUntil(check,onComplete,delay,timeout) {
//   // if the check returns true, execute onComplete immediately
//   if (check()) {
//       onComplete();
//       return;
//   }

//   if (!delay) delay=100;

//   var timeoutPointer;
//   var intervalPointer=setInterval(function () {
//     if (!check()) {} // if check didn't return true, means we need another check in the next interval
//   	else
//   	{
// 	      // if the check returned true, means we're done here. clear the interval and the timeout and execute onComplete
// 	      clearInterval(intervalPointer);
// 	      if (timeoutPointer) clearTimeout(timeoutPointer);
// 	      onComplete();
//   	}
//   },delay);
//   // if after timeout milliseconds function doesn't return true, abort
//   if (timeout) timeoutPointer=setTimeout(function () {
//       clearInterval(intervalPointer);
//   },timeout);
// }
// // function getGroup(range)
// // {
// // 	var allRange = getRangeMatrix();
// // 	//alert(range);
// // 	for(var grp = 0;grp < allRange.length/numrange;grp++)
// // 	{
// // 		if(likeHood(range,getRangeMatrixByGroup(grp))>0.5)
// // 		{
// // 			return grp;
// // 		}
// // 	}
// // 	return null;
// // }
// // function likeHood(range1,range2)
// // {
// // 	out("___________________");
// // 	out("range1: "+range1);
// // 	out("range2: "+range2)
// // 	out("___________________");
// // 	if(range2.length != range1.length)
// // 	{
// // 		return 0;
// // 	}
// // 	else
// // 	{
// // 		var dem = 0;
// // 		for(var i = 0; i < range1.length;i++)
// // 		{
// // 			if(range1[i]===range2[i])
// // 			{
// // 				dem++;
// // 			}
// // 		}
// // 	}
// // 	return dem/range2.length;
// // }
// function updateMatrixDisAndDur(irow,icol,arrDis,arrDur)
// {	
// 	for(var i = 0;i < arrDis.length;i++)
// 	{
// 		if(arr_matrix_distances[i+irow])
// 		{
// 			out("arr: "+arr_matrix_distances[i+irow]);
// 		}else
// 		{
// 			out("arr else");
// 		}
// 		arr_matrix_distances[i+irow] = new Array();
// 		arr_matrix_duration[i+irow] = new Array();
// 		for(var j = 0;j < arrDis[i].length;j++)
// 		{
// 			arr_matrix_distances[i+irow][j+icol]=arrDis[i][j];
// 			arr_matrix_duration[i+irow][j+icol]=arrDur[i][j];
			
// 		}
// 	}
// }
// function jsonToArrayMatrixDis(sjson)
// {
// 	jrows = sjson.rows;
// 	rdata = new Array();
// 	//out("Json: "+jrows);
// 	for(var i = 0;i < jrows.length;i++)
// 	{
// 		jrow = jrows[i].elements;
// 		out("jrow: "+jrow);
// 		rdata[i] = new Array();
// 		for(var j = 0;j < jrow.length;j++)
// 		{
// 			rdata[i][j] = jrow[j].distance.value;
// 		}
// 	}
// 	return rdata;
// }
// function jsonToArrayMatrixDur(sjson)
// {
// 	jrows = sjson.rows;
// 	// out("Json: "+jrows);
// 	rdata = new Array();
// 	for(var i = 0;i < jrows.length;i++)
// 	{
// 		jrow = jrows[i].elements;
// 		rdata[i] = new Array();
// 		for(var j = 0;j < jrow.length;j++)
// 		{
// 			rdata[i][j] = jrow[j].duration.value;
// 		}
// 	}
// 	return rdata;
// }
function postData()
{
	$.ajax(
			{
				url:"ControllerServlet",
				type:"POST",
				data:{
					rangeMatrix:JSON.stringify(getRangeMatrix()),
					num_vehicle:JSON.stringify($("#num-vehicle").val()),
					capacity_vehicle:JSON.stringify($("#capacity-vehicle").val()),
					depot: JSON.stringify(depot.getPosition()),
				    pickup: JSON.stringify(getParameterRequest("pickup-position")),
				    deliver: JSON.stringify(getParameterRequest("deliver-position")),
				    weight: JSON.stringify(getParameterRequest("weight")),
				    Ep: JSON.stringify(getParameterRequest("Ep")),
				    Lp: JSON.stringify(getParameterRequest("Lp")),
				    Ed: JSON.stringify(getParameterRequest("Ed")),
				    Ld: JSON.stringify(getParameterRequest("Ld")),
				    duration_pickup: JSON.stringify(getParameterRequest("duration-pickup")),
				    duration_deliver: JSON.stringify(getParameterRequest("duration-deliver")),
				    matrix_distances:JSON.stringify(arr_matrix_distances)				    
				},
				success:function(data)
				{
			    	out("Data receive: " + data);
			    	parseJSON(data);
			    	calcRoute();
			 	},
				error:function(status,stt,err)
				{
					alert("Status: "+stt + "    ERR: "+err);
				}
			}
			);
}
function saveSession()
{
	$.ajax(
			{
				url:"ControllerModel",
				type:"POST",
				data:{
					command: "saveSession",
					num_vehicle:JSON.stringify($("#num-vehicle").val()),
					capacity_vehicle:JSON.stringify($("#capacity-vehicle").val()),
					depot: JSON.stringify(depot.getPosition()),
					rangeMatrix:JSON.stringify(getRangeMatrix()),
				    pickup: JSON.stringify(getParameterRequest("pickup-position")),
				    deliver: JSON.stringify(getParameterRequest("deliver-position")),
				    weight: JSON.stringify(getParameterRequest("weight")),
				    Ep: JSON.stringify(getParameterRequest("Ep")),
				    Lp: JSON.stringify(getParameterRequest("Lp")),
				    Ed: JSON.stringify(getParameterRequest("Ed")),
				    Ld: JSON.stringify(getParameterRequest("Ld")),
				    
				    duration_pickup: JSON.stringify(getParameterRequest("duration-pickup")),
				    duration_deliver: JSON.stringify(getParameterRequest("duration-deliver")),				   			   
				},
				success:function(data)
				{
			    	out("Data model: " + data);
			    	loadIdSession();
			    	//parseJSON(data);			    	
			 	},
				error:function(status,stt,err)
				{
					alert("Status: "+stt + "    ERR: "+err);
				}
			}
			);
}
function loadIdSession()
{
	$.ajax({
		url:"ControllerModel",
		type:"POST",
		data:{
			command:"loadIdSession"			
		},
		success:function(data)
		{
			// out(data);
			$("#content-session").html(toStringSession(data));
		},
		error:function(status,stt,err)
		{
			alert("load ERROR: "+status+"/"+stt+"/"+err);
		}
	});
}
function loadSession()
{
	// alert("loadSession: "+$(".sp-session").children("input")[0].attr("value"));	
	var idsArr = new Array();
	$(".sp-session").children(".a-session").each(function(){
		// out($(this).prop("checked"));		
		if($(this).prop("checked"))
		{
			idsArr.push($(this).attr("value"));
		}
	});
	
	$.ajax({
		url:"ControllerModel",
		type:"POST",
		dataType: "json",
		data:{
			command:"loadSession",
			ids:JSON.stringify(idsArr)
		},
		success:function(data)
		{
			// out(data);	
			// dt = JSON.parse(data);
			newSession();
			session = data.session;
			requests = data.requests;

			ld = JSON.parse(session.depot);

			loc = new google.maps.LatLng(ld.k,ld.A);
			putDepot(loc);
			// for(var i = 0;i < arr_request.length;i++)
			// {
			// 	arr_request[i].destroy();
			// }
			// arr_request = new Array();
			for(var i = 0;i < requests.length;i++)
			{
				latgP = JSON.parse(requests[i].pickup);
				latgD = JSON.parse(requests[i].deliver);
				// alert(requests[i].id+"---"+latgP.k+"---"+latgP.A+"---"+icon_pickup);
				repickup = putMarker(requests[i].id,latgP.k,latgP.A,icon_pickup_nact);
				redelivery = putMarker(requests[i].id,latgD.k,latgD.A,icon_deliver_nact);
				arr_request[i] = new Request(requests[i].id,repickup,redelivery,requests[i].weight,numberToTime(requests[i].Ep),numberToTime(requests[i].Lp),numberToTime(requests[i].Ed),numberToTime(requests[i].Ld),requests[i].duration_pickup,requests[i].duration_deliver);
				
			}
		},
		error:function(status,stt,err)
		{
			alert("load ERROR");
		}
	});
}
function deleteSession()
{
	var idsArr = new Array();
	$(".sp-session").children(".a-session").each(function(){
		// out($(this).prop("checked"));		
		if($(this).prop("checked"))
		{
			idsArr.push($(this).attr("value"));
		}
	});
	$.ajax({
		url:"ControllerModel",
		type:"POST",
		data:{
			command:"deleteSession",
			ids:JSON.stringify(idsArr)
		},
		success:function(data)
		{
			out("succecss");
			$("#content-session").html(toStringSession(data));
		},
		error:function(status,stt,err)
		{
			alert("deleteSession ERROR");
		}
	});
}
function numberToTime(num)
{
	out("-->"+Math.floor(num/60)+":"+num%60);
	h = Math.floor(num/60)+"";
	m = Math.floor(num%60)+"";
	if(h.length==1)
	{
		h = "0"+h;
	}
	if(m.length == 1)
	{
		m = "0"+m;
	}
	return h+":"+m;
}
function toStringSession(data)
{
	out(data);
	obj = JSON.parse(data);
	var html = "";// "<p class='row-session'>";	
	for(var i = 0;i < obj.length;i++)
	{
		html+= "<div class='sp-session'><input type='checkbox' name='box-session' value='"+obj[i]+"' class='a-session'> "+obj[i]+"</div>";
		// if((i+1)%10 == 0)
		// {
		// 	html+= "</p><p class='row-session'>"
		// }
	}
	return html;

}
function parseJSON(data)
{
	obj = JSON.parse(data);
	data_response = obj;	
}
function newSession()
{
	for(var i = 0;i < arr_request.length;i++)
	{
		arr_request[i].destroy();
	}
	arr_request = new Array();
	$("#list-request").html("");
	ele_focus = null;
    req_active = 0;
    req_counter = 0;
	initialize();
	resetOpt();
}
