
function checkOpt()
{
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
	else if(!patt_num.test(ipt_Ep.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_Lp.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_Ed.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_Ld.value))
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
function resetOpt()
{
	pos_pickup.value = "Position pickup";
	pos_deliver.value = "Position deliver";
	
	ipt_weight.value = "0";
	ipt_Ep.value = "0";
	ipt_Lp.value = "9999";
	ipt_Ed.value = "0";
	ipt_Ld.value = "9999";
	req_active = null;
}
function saveReq()
{
	tmp_request = new Request(req_counter,pmarker,dmarker,ipt_weight.value,ipt_Ep,ipt_Lp,ipt_Ed,ipt_Ld);
	req_counter++;
	arr_request.push(tmp_request);
	//dmarker = null;
	//pmarker = null;
}
function removeReq(id)
{
	for(var i = 0;i < arr_request.length;i++)
	{
		if(arr_request[i].id == id)
		{
			arr_request[i].pickup.setMap(null);
			arr_request[i].deliver.setMap(null);
			arr_request.splice(i, 1);
			alert(arr_request.length);
			req_active = null;
			break;
		}
	}
	resetOpt();
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
	req_active = this.id;
	for(var i = 0;i < arr_request.length;i++)
	{
		arr_request[i].pickup.setAnimation(null);
		arr_request[i].deliver.setAnimation(null);
		arr_request[i].pickup.setDraggable(false);
		arr_request[i].deliver.setDraggable(false);
		
	}
	for(var i = 0;i < arr_request.length;i++)
	{
		if(arr_request[i].id == id)
		{
			arr_request[i].pickup.setAnimation(google.maps.Animation.BOUNCE);
			arr_request[i].deliver.setAnimation(google.maps.Animation.BOUNCE);
			arr_request[i].pickup.setDraggable(true);
			arr_request[i].deliver.setDraggable(true);
			break;
		}
	}
}