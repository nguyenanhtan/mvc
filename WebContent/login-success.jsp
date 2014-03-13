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
    <script type="text/javascript" src="./js/Request.js">    
    </script>
  </head>
  <body>
 
<p>You are successfully logged in!</p>

<div id="map-canvas"></div>
<div id="right">

<p id="console"></p>
<input type="button" id="btn-add" class="btn-opt-require" title="New require"/>
<input type="button" id="btn-save" class="btn-opt-require" title="Save require"/>
<input type="button" id="btn-remove" class="btn-opt-require" title="Remove require"/>

<form id="form-opt-require">
<div id="box-require">
	<h3 class="header-box">Input require</h3>
	<div class="row-opt">
		<input type="button" id="btn-pickup" class="btn-opt-require"/>
		<input type="text" value="Position pickup" title="Position pickup" class="opacity-60" id="pos-pickup" readonly="readonly"/>
	</div>
	<div class="row-opt">
		<input type="button" id="btn-deliver" class="btn-opt-require"/>
		<input type="text" value="Position deliver" title="Position deliver" class="opacity-60" id="pos-deliver" readonly="readonly"/>
	
	</div>
	<div class="row-opt">
		<p>Weight:</p><input class="ipt-text" type="number" value=""/>
	</div>
	<div class="row-opt">
		<p>Ep:</p><input class="ipt-text" type="time" value=""/>
	</div>
	<div class="row-opt">
		<p>Lp:</p><input class="ipt-text" type="time" value=""/>
	</div>
	<div class="row-opt">
		<p>Ed:</p><input class="ipt-text" type="time" value=""/>
	</div>
	<div class="row-opt">
		<p>Lp:</p><input class="ipt-text" type="time" value=""/>
	</div>
</div>
</form>
<div>
<input type="button" class="btn-require" value="1"/>
<input type="button" class="btn-require" value="2"/>
<input type="button" class="btn-require" value="3"/>
<input type="button" class="btn-require" value="4"/>
</div>
<input type="button" id="route" value="Route" onclick="calcRoute(1,0)"/>     
</div>
  </body>
</html>