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
    <script type="text/javascript" src="./js/onbutton.js">    
    </script>
    <script type="text/javascript" src="./js/variable.js">    
    </script>
    <script type="text/javascript" src="./js/init.js">    
    </script>
  </head>
  <body>
 
<p>You are successfully logged in!</p>

<div id="map-canvas"></div>
<div id="bound-right">
<div id="right">

<p id="console"></p>
<input type="button" id="btn-add" class="btn-opt-require" title="New require"/>
<input type="button" id="btn-save" class="btn-opt-require" title="Save require"/>
<!-- <input type="button" id="btn-remove" class="btn-opt-require" title="Remove require"/> -->
<div id="confirm">	
	<p id="message"> sad</p>
	<input type="button" id="cfm-yes" value="Yes"></input>
	<input type="button" id="cfm-no" value="No"></input>	
	<div class="clear"></div>
</div>
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
		<p>Weight:</p><input class="ipt-text" type="number" value="0" id="ipt-weight"/>
	</div>
	<div class="row-opt">
		<p>Ep:</p><input class="ipt-text" type="time" value="0" id="ipt-Ep"/>
	</div>
	<div class="row-opt">
		<p>Lp:</p><input class="ipt-text" type="time" value="9999" id="ipt-Lp"/>
	</div>
	<div class="row-opt">
		<p>Ed:</p><input class="ipt-text" type="time" value="0" id="ipt-Ed"/>
	</div>
	<div class="row-opt">
		<p>Ld:</p><input class="ipt-text" type="time" value="9999" id="ipt-Ld"/>
	</div>
</div>
</form>
<p class="line clear"></p>
<div>
<ul id="list-request">
	<li>
	<input type="button" class="btn-require" value="1"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>
	<li>
	<input type="button" class="btn-require" value="2"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>
	<li>
	<input type="button" class="btn-require" value="3"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>
	<li>
	<input type="button" class="btn-require" value="4"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>
	<li>
	<input type="button" class="btn-require" value="4"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>
	<li>
	<input type="button" class="btn-require" value="4"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>
</ul>
</div>
<div class="clear"></div>
<p class="line clear"></p>
<input type="button" id="route" value="Route" onclick="calcRoute(1,0)"/>     
</div>
</div>
  </body>
</html>