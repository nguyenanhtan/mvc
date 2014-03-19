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
	  	      animation: google.maps.Animation.BOUNCE,
	  	      icon: icon_marker,
	  	      id:req_counter,
	  	  });
		  google.maps.event.addListener(dmarker, 'mouseover', function() {
			  if(req_active != this.id)
			  {
		  	      this.setAnimation(null); 
			  }
		  });
		  google.maps.event.addListener(dmarker, 'click', function() {
			  if(req_active == this.id)
			  {
				  activeRequest(this.id);
			  }
		  });
		  google.maps.event.addListener(dmarker, 'mouseout', function() {
		  	  if(this.id == req_active)
		  	  {
		  		  this.setAnimation(google.maps.Animation.BOUNCE);
		  	  }
		  });
		  google.maps.event.addListener(dmarker, 'mouseup', function() {
		  	    //alert(dmarker.getPosition());
		  	    pos_deliver.value = dmarker.getPosition();
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
	  	      animation: google.maps.Animation.BOUNCE,
	  	      title:"R"+req_counter,
	  	      icon: icon_marker,
	  	      id:req_counter
	  	  });
		  google.maps.event.addListener(pmarker, 'mouseover', function() {
		  	    
			  if(req_active != this.id)
			  {
		  	      this.setAnimation(null); 
			  }
		  });
		  google.maps.event.addListener(pmarker, 'click', function() {
			  activeRequest(this.id);		  	    
		  });
		  google.maps.event.addListener(pmarker, 'mouseout', function() {
			  if(this.id == req_active)
		  	  {
		  		  this.setAnimation(google.maps.Animation.BOUNCE);
		  	  }
		  });
		  google.maps.event.addListener(pmarker, 'mouseup', function() {
		  	    //alert(pmarker.getPosition());
			  pos_pickup.value = pmarker.getPosition();
		  });
		  //markers.push(pmarker);
		  
	  }
	  else
	  {
		  alert("NO");
	  }
	  if(btn_deliver == ele_focus || btn_pickup == ele_focus)
	  {		
		  ele_focus.blur();
		  ele_focus = null;
		  var infowindow = new google.maps.InfoWindow({
		      content: "Heheheheheheh"
		  });
	  	  google.maps.event.addListener(marker, 'click', function() {
	  	       infowindow.open(map,marker);
	  	  });
	  }	  
    }
  function calcRoute(mStart,mEnd) {
	  if(mStart > markers.length || mEnd > markers.length)
	  {
	  	alert("mStart > markers.length || mEnd > markers.length");
	  }
	  var start = markers[mStart].getPosition();
	  var end = markers[mEnd].getPosition();
	  var request = {
	      origin:start,
	      destination:end,
	      travelMode: google.maps.TravelMode.DRIVING,
	      optimizeWaypoints: true
	  };
	  directionsService.route(request, function(response, status) {
		  //alert("Status: "+status);
	    if (status == google.maps.DirectionsStatus.OK) {
	      directionsDisplay.setDirections(response);
	    }
	  });
	}

 google.maps.event.addDomListener(window, 'load', initialize);  
 
