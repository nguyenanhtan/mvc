var map;
var directionsDisplay;
var markers = new Array();
var icon_pickup = "./img/Map-Marker-Ball-Azure-icon.png";
var icon_deliver = "./img/Map-Marker-Ball-Pink-icon.png";
var icon_marker = "";//icon_pickup;
var btn_pickup = document.getElementById("btn-pickup");
var btn_deliver = document.getElementById("btn-deliver");
var pos_pickup;
var pos_deliver;
var ele_focus = null;
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
    
  }
  function placeMarker(location) {	 
	  //alert( ele_focus.id+"/"+btn_deliver+"/"+btn_pickup);
	  //alert(pos_deliver);
	  //pos_deliver.value = "OK";
	  if(btn_deliver == ele_focus)
	  {
		  icon_marker = icon_deliver;
		  pos_deliver.value = location;
	  }
	  else if(btn_pickup == ele_focus)
	  {
		  icon_marker = icon_pickup;
		  pos_pickup.value = location;
	  }
	  if(btn_deliver == ele_focus || btn_pickup == ele_focus)
	  {		 
	  	  var marker = new google.maps.Marker({
	  	      position: location,
	  	      map: map,
	  	      draggable:true,
	  	      title:"Kéo để chuyển vị trí!",
	  	      icon: icon_marker
	  	  });
	  	  
		  var infowindow = new google.maps.InfoWindow({
		      content: "Heheheheheheh"
		  });
	  	  google.maps.event.addListener(marker, 'click', function() {
	  	       infowindow.open(map,marker);
	  	  });

	  	  markers.push(marker);
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
 window.onload = function(){
	 btn_pickup = document.getElementById("btn-pickup");
	 btn_deliver = document.getElementById("btn-deliver");
	 pos_pickup = document.getElementById("pos-pickup");
	 pos_deliver = document.getElementById("pos-deliver");
	 //alert("btn_pickup: "+btn_pickup);
 };
$(document).ready(function(){	
	$("*").focus(function(){ele_focus = this;});
	$("*").blur(function(){ele_focus = null;});
});
