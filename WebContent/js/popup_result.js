function detailVehicle(k)
{
	var waypts = [];
	//Solution = data_response.Solution;	
	Solution = data_response.Solution;	
	pcurrent = Solution[k];
	m = data_response.numVehicle;
	n = data_response.numRequest;
	while(pcurrent < data_response.numVehicle + 2*data_response.numRequest)
	{
		if(pcurrent < m+n)
		{
			waypts.push({
				require:arr_request[pcurrent - m],
				type: "Pickup",
				position: arr_request[pcurrent - m].pickup.getPosition()

			});
			//out(arr_request[pcurrent - m].pickup.getPosition());
		}
		else
		{			
			waypts.push({
				require:arr_request[pcurrent - m - n],
				type: "Delivery",
				position: arr_request[pcurrent - m - n].deliver.getPosition()
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
	var detail = detailVehicle(k);
	if(detail.length == 0)
	{
		str = "<div class='row-detail'>The vehicle "+k+" is not used</div>"
	}
	else
	{
		str = "<h2>The trip of vehicle "+k+"</h2>";
		str += "<ol>";
		for(var i = 0;i < detail.length;i++)
		{
			text = detail[i].type +" "+detail[i].require.id+" at "+detail[i].position;
			str+= "<li><div class='row-detail'>"+text+"</div></li>";
		}
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
			str += "<div class='box-detail-vehicle'>"+toHtmlDetail(i)+"</div>";
		}
		str+= "";
		$("#popup").show(300);

		$("#box-detail").html(str);
		$("#box-detail").show(300);
	}
}
