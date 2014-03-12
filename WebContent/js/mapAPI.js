var map;
var directionsDisplay;
var markers = new Array();
var icon_pickup = "./img/Map-Marker-Ball-Azure-icon.png";
var icon_deliver = "./img/Map-Marker-Ball-Pink-icon.png";
var icon_marker = "";//icon_pickup;
var btn_pickup = document.getElementById("btn-pickup");
var btn_deliver = document.getElementById("btn-deliver");

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
  	  var marker = new google.maps.Marker({
  	      position: location,
  	      map: map,
  	      draggable:true,
  	      title:"Drag me!",
  	      icon: icon_marker
  	  });
  	  markers.push(marker); 
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
      
$(document).ready(function(){
	$("#btn-pickup").focus(function(){
		icon_marker = icon_pickup;
		
	});
	$("#btn-deliver").focus(function(){
		icon_marker = icon_deliver;
		
	});
});