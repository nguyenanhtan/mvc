<!DOCTYPE html>


<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>Dial A Ride Problem</title>
    <link type="text/css" rel="stylesheet" href="./css/style.css"></link>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDgaadJHrg37wH05ZRMDmpe_oC2DwL8iyg&sensor=true">
    </script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
	</script>
    <script type="text/javascript" src="./js/mapAPI.js">    
    </script>
  </head>
  <body>
 
<p>You are successfully logged in!</p>

<div id="map-canvas"></div>
<div id="right">
<input type="button" id="route" value="Route" onclick="calcRoute(1,2)"/>     
<p id="console"></p>
<input type="button" id="btn-add" class="btn-opt-require"/>
<input type="button" id="btn-remove" class="btn-opt-require"/>

<form id="form-opt-require">
<div id="box-require">
	<div>
		<input type="button" id="btn-pickup" class="btn-opt-require"/>
		<input type="text" value="position pickup" class="opacity-60" id="pos-pickup"/>
	</div>
	<div>
		<input type="button" id="btn-deliver" class="btn-opt-require"/>
		<input type="text" value="position deliver" class="opacity-60" id="pos-deliver"/>
	
	</div>
	<div>
		<p>Weight:</p><input class="ipt-text" type="number" value=""/>
	</div>
	<div>
		<p>Ep:</p><input class="ipt-text" type="time" value=""/>
	</div>
	<div>
		<p>Lp:</p><input class="ipt-text" type="time" value=""/>
	</div>
	<div>
		<p>Ed:</p><input class="ipt-text" type="time" value=""/>
	</div>
	<div>
		<p>Lp:</p><input class="ipt-text" type="time" value=""/>
	</div>
</div>
</form>
</div>
  </body>
</html>