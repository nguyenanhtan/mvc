function detailVehicle(k)
{
	var waypts = [];
	//Solution = data_response.Solution;	
	Solution = data_response.Solution;	
	timsevice = data_response.timeService;
	out(timsevice);
	pcurrent = Solution[k];
	var m = data_response.numVehicle;
	var n = data_response.numRequest;
	out(m+":"+n);
	while(pcurrent < data_response.numVehicle + 2*data_response.numRequest)
	{
		
		if(pcurrent < (m+n))
		{
			out("pcurrent: "+pcurrent);
			out("m+n = "+(m+n));
			waypts.push({
				require:arr_request[pcurrent - m],
				type: "Pickup",
				position: arr_request[pcurrent - m].pickup.getPosition(),
				timesevice: numberToTime(timsevice[pcurrent])
			});
			//out(arr_request[pcurrent - m].pickup.getPosition());
		}
		else
		{			
			waypts.push({
				require:arr_request[pcurrent - m - n],
				type: "Delivery",
				position: arr_request[pcurrent - m - n].deliver.getPosition(),
				timesevice: numberToTime(timsevice[pcurrent])
			});
			//out(arr_request[pcurrent - m - n].deliver.getPosition());
		}
		
		pcurrent = Solution[pcurrent];
	}
	
	return waypts;
}
function toHtmlDetail(k)
{
	str = "";
	var n = data_response.numVehicle;
	var m = data_response.numRequest;
	var detail = detailVehicle(k);
	if(detail.length == 0)
	{
		str = "<div class='row-detail'>The vehicle "+(k+1)+" is not used</div>"
	}
	else
	{
		str = "<h2>The trip of vehicle "+(k+1)+"</h2>";
		str += "<ol>";
		str += "<li><div class='row-detail'>Left depot at "+numberToTime(data_response.timeService[k])+"</div></li>";
		for(var i = 0;i < detail.length;i++)
		{
			text = detail[i].type +" "+detail[i].require.id+" at "+detail[i].timesevice;
			str+= "<li><div class='row-detail'>"+text+"</div></li>";
		}
		var vcb = k + n +2*m;
		str += "<li><div class='row-detail'> Come back depot at "+numberToTime(data_response.timeService[vcb])+"</div></li>";
		str += "</ol>";
	}
	return str;
}
function detail()
{
	str = "";
	if(data_response != null)
	{
		for(var i = 0;i < data_response.numVehicle;i++)
		{
			str += "<div class='box-detail-vehicle' ondblclick='dbclickBoxDetail("+i+")'>"+toHtmlDetail(i)+"</div>";
		}
		str+= "";
		$("#popup").show(300);

		$("#box-detail").html(str);
		$("#box-detail").show(300);
	}
}
function dbclickBoxDetail(i)
{
	drawRoute(i);
	$("#popup").hide(300);
	$("#box-detail").hide(300);
}