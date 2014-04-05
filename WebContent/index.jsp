<!DOCTYPE html>


<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>Dial A Ride Problem</title>
    <link type="text/css" rel="stylesheet" href="./css/style.css"></link>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDgaadJHrg37wH05ZRMDmpe_oC2DwL8iyg&sensor=true">
    </script>
    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
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
 
<!-- <p>You are successfully logged in!</p> -->
<p id="header">Run best in browser supporting HTML5 and CSS3</p>

<div id="map-canvas"></div>
<div id="bound-right">
<div id="right">


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
		<input type="button" id="btn-pickup" class="btn-opt-require" title="Marker pickup"/>
		<input type="text" value="Position pickup" title="Position pickup" class="opacity-60" id="pos-pickup" readonly="readonly"/>
	</div>
	<div class="row-opt">
		<p>Duration </p>
		<input class="ipt-text" type="number" value="0" min="0" id="ipt-duration-pickup"/>
		<p class="minute-text">(minute)</p>
	</div>
	<div class="line"></div>
	<div class="row-opt">
		<input type="button" id="btn-deliver" class="btn-opt-require" title="Marker deliver"/>
		<input type="text" value="Position deliver" title="Position deliver" class="opacity-60" id="pos-deliver" readonly="readonly"/>	
	</div>
	<div class="row-opt">
		<p>Duration </p>
		<input class="ipt-text" type="number" value="0" min="0" id="ipt-duration-deliver"/>
		<p class="minute-text">(minute)</p>
	</div>
	<div class="line"></div>
	<div class="row-opt">
		<p>Weight:</p>
		<input class="ipt-text" type="number" value="1" min="1" id="ipt-weight"/>
		<p class="red italic"> (num of person)</p>
	</div>
	<div class="row-opt">
		<p>Ep:</p><input class="ipt-text" type="time" value="00:00" id="ipt-Ep"/>
	</div>
	<div class="row-opt">
		<p>Lp:</p><input class="ipt-text" type="time" value="23:59" id="ipt-Lp"/>
	</div>
	<div class="row-opt">
		<p>Ed:</p><input class="ipt-text" type="time" value="00:00" id="ipt-Ed"/>
	</div>
	<div class="row-opt">
		<p>Ld:</p><input class="ipt-text" type="time" value="23:59" id="ipt-Ld"/>
	</div>
</div>
</form>
<div id="depot">
<p>
	<img src="./img/bus-orange-icon.png">
</p>
<p>
	<input type="number" id="num-vehicle" value="3" min="0"/>
</p>
<p class="red italic"> vehicles</p>
</div>
<p class="clear"></p>

<div id="div-list-request">
<ul id="list-request">
<!-- 	<li>
	<input type="button" class="btn-require" value="1"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>	 -->
</ul>
</div>

<div class="clear"></div>
<div id="box-button">
<input type="button" id="route" value="Route" onclick="calcRoute(1,0)"/>     
<input type="button" id="Out" value="Out" onclick="showOut()"/>   
<input type="button" id="get-matrix-distances" value="Get Matrix Distance" onclick="calculateDistances()"/>   
<input type="button" id="post-data" value="Post" onclick="postData()"/>
</div>
<div id="div-console">
<h4>Console <span id="clear-console">clear</span></h4>
<p id="console"></p>
</div>   
</div>
</div>
  </body>
</html>
