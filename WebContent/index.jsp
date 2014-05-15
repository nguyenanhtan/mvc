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
<!--  <div id="popup">
 	<img src="./img/add-icon.png">
 </div> -->
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
<div>
	<img src="./img/bus-orange-icon.png">
	<input type="text" readonly="readonly" id="latlng-depot" class="opacity-60">
</div>
<div>
	<p class="depot-title">Number vehicle</p>
	<input type="number" id="num-vehicle" value="3" min="0"/>
	<span class="red italic"> vehicles</span>
</div>
<div>
	<p class="depot-title">Capacity</p>
	<input type="number" id="capacity-vehicle" value="10" min="1"/>
	<span class="red italic"> person</span>
</div>
</div>
<!-- <p class="clear"></p> -->

<div id="div-list-request">
<ul id="list-request">
<!-- 	<li>
	<input type="button" class="btn-require" value="1"/>
	<img title="Remove" src="./img/Close-2-icon.png" class="icon-remove">
	</li>	 -->
</ul>
</div>

<div class="clear"></div>

</div>
</div>

<div class="clear"></div>
<div id="div-footer">
	<ul id="footer">
		<li>
			<p class="p-title">Console</p>
			<div id="div-console" class="li-content">
				<h4>Console </h4>
				<span id="clear-console"><img src="./img/broom-icon.png"></span>
				<p id="console"></p>
			</div> 
		
		</li>	
		<li>
			<p class="p-title">Sessions</p>
			<div class="li-content" id="div-session">
				<h4>Sessions</h4>
				<span id="btn-deletesession" title="Delete sessions"><img src="./img/Close-2-icon.png" onclick="deleteSession()"></span>
				<span id="btn-loadsession" title="Load and merge sessions"><img src="./img/Misc-Download-Database-icon.png" onclick="loadSession()"></span>
				<div id="content-session">

				</div>
			</div>
		</li>
		<li>
			
		</li>
	</ul>
	<div id="box-button">
		<input type="button" id="btn-new-session" value="New Session" onclick="newSession()"/>     
		<input type="button" id="Out" value="Out" onclick="showOut()"/>   
		<!-- <input type="button" id="get-matrix-distances" value="Get Matrix Distance" onclick="calculateDistances()"/>   -->
		<input type="button" id="post-data" value="LNS-FFPA" onclick="postData()"/>
		<input type="button" id="btn-save-session" value="Save Session" onclick="saveSession()">
	</div>
</div>
<div id="height-35"></div>
</body>
</html>
