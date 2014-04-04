var directionsService = new google.maps.DirectionsService();
function initialize() 
{	
	directionsDisplay = new google.maps.DirectionsRenderer();
    var mapOptions = {
    center: new google.maps.LatLng(21.006738,105.84138),
    zoom: 16
    };
    map = new google.maps.Map(document.getElementById("map-canvas"),mapOptions);       
    directionsDisplay.setMap(map);

    google.maps.event.addListener(map, 'click', function(event) {
    	  placeMarker(event.latLng);
      });
    putDepot(new google.maps.LatLng(21.006738,105.84138));

  }
function putDepot(location)
{
	depot = new google.maps.Marker({
	      position: location,
	      map: map,
	      draggable:true,
	      title:"Depot",
//	      animation: google.maps.Animation.BOUNCE,
	      icon: icon_depot,
	  });
}
  function placeMarker(location) {	 
	  //alert( ele_focus.id+"/"+btn_deliver+"/"+btn_pickup);
	  //alert(req_counter);
	  //pos_deliver.value = "OK";	  
	  if(btn_deliver == ele_focus)
	  {
		  icon_marker = icon_deliver;
		  pos_deliver.value = location;
		  dmarker = new google.maps.Marker({
	  	      position: location,
	  	      map: map,
	  	      draggable:true,
	  	      title:"R "+req_counter,
//	  	      animation: google.maps.Animation.BOUNCE,
	  	      icon: icon_marker,
	  	      id:req_counter,
	  	  });
		  google.maps.event.addListener(dmarker, 'mouseover', function() {	
			  	  //box_console.innerHTML += "mouseover<br/>";
		  	      //this.setAnimation(null); 		  	      
		  });
		  google.maps.event.addListener(dmarker, 'click', function() {
			  //box_console.innerHTML += "click<br/>";
			  if(dmarker != this && dmarker != null)
		      {
				  dmarker.setMap(null);
				  if(pmarker != null)
			      {
					  pmarker.setMap(null);
			      }
		      }
			  
				  activeRequest(this.id);
		  });
		  google.maps.event.addListener(dmarker, 'mouseout', function() {
			  //box_console.innerHTML += "mouseout<br/>";
//		  	  if(this.id == req_active)
//		  	  {
//		  		  this.setAnimation(google.maps.Animation.BOUNCE);
//		  	  }
//		  	  else
//		  	  {
//		  	  }
		  });
		  google.maps.event.addListener(dmarker, 'mouseup', function() {
		  	    //alert(dmarker.getPosition());
			  //box_console.innerHTML += "mouseup<br/>";
			  if(this.id == req_active)
			  {
				  pos_deliver.value = this.getPosition();
			  }
		  });
		  //markers.push(dmarker);
	  }
	  else if(btn_pickup == ele_focus)
	  {
		  icon_marker = icon_pickup;
		  pos_pickup.value = location;
		  pmarker = new google.maps.Marker({
	  	      position: location,
	  	      map: map,
	  	      draggable:true,
//	  	      animation: google.maps.Animation.BOUNCE,
	  	      title:"R"+req_counter,
	  	      icon: icon_marker,
	  	      id:req_counter
	  	  });
		  google.maps.event.addListener(pmarker, 'mouseover', function() {
		  	      //this.setAnimation(null); 
		  });
		  google.maps.event.addListener(pmarker, 'click', function() {
			  
			  if(pmarker != this && pmarker != null)
		      {
				  pmarker.setMap(null);
				  if(dmarker != null)
			      {
					  dmarker.setMap(null);
			      }
		      }
			  activeRequest(this.id);		  	    
		  });
		  google.maps.event.addListener(pmarker, 'mouseout', function() {
//			  if(this.id == req_active)
//		  	  {
//		  		  this.setAnimation(google.maps.Animation.BOUNCE);
//		  	  }
		  });
		  google.maps.event.addListener(pmarker, 'mouseup', function() {
		  	    //alert(pmarker.getPosition());
			  if(this.id == req_active)
			  {
				  pos_pickup.value = this.getPosition();
			  }
		  });
		  //markers.push(pmarker);
		  
	  }
	  else
	  {
		  //alert("NO");
	  }
	  if(btn_deliver == ele_focus || btn_pickup == ele_focus)
	  {		
		  ele_focus.blur();
		  ele_focus = null;
//		  var infowindow = new google.maps.InfoWindow({
//		      content: "Heheheheheheh"
//		  });
//	  	  google.maps.event.addListener(pmarker, 'click', function() {
//	  	       infowindow.open(map,pmarker);
//	  	  });
	  }	  
    }
  function calcRoute() {	
	  out("Calc Route");
	  var start = depot;
	  var end = depot;//arr_request[0].pickup;
	  //out(depot.getPosition());
	  for(var k = 0;k < data_response.numVehicle;k++)
	  {
		  waypts = getRoute(k);
		  //out("way.len = "+waypts.length);
		  if(waypts.length > 0)
		  {
			  var request = {
			      origin:start.getPosition(),
			      destination:end.getPosition(),
			      waypoints: waypts,
			      travelMode: google.maps.TravelMode.DRIVING,
			      optimizeWaypoints: false
			  };
			  //ut("__k___"+k+"____");
			  directionsService.route(request, function(response, status) {
				  out("Status: "+status);
			    if (status == google.maps.DirectionsStatus.OK) {
			      directionsDisplay.setDirections(response);
			    }
			    else
			    {
			    	alert("Error route: "+status);
			    }
			  });
		  }
		  else
		  {
			  out("****NO ROUTE***");
		  }
	  }
	}
function getRoute(i)
{
	var waypts = [];
	Solution = data_response.Solution;
	pcurrent = Solution[i];
	m = data_response.numVehicle;
	n = data_response.numRequest;
	while(pcurrent < data_response.numVehicle + 2*data_response.numRequest)
	{
		if(pcurrent < m+n)
		{
			waypts.push({
				location:arr_request[pcurrent - m].pickup.getPosition(),
				stopover:true
			});
			//out(arr_request[pcurrent - m].pickup.getPosition());
		}
		else
		{			
			waypts.push({
				location:arr_request[pcurrent - m - n].deliver.getPosition(),
				stopover:true
			});
			//out(arr_request[pcurrent - m - n].deliver.getPosition());
		}
		
		pcurrent = Solution[pcurrent];
	}
	
	return waypts;
}
 google.maps.event.addDomListener(window, 'load', initialize);  
 
