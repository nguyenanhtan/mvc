window.onload = function(){
	 //Load element
	 btn_pickup = document.getElementById("btn-pickup");
	 btn_deliver = document.getElementById("btn-deliver");
	 pos_pickup = document.getElementById("pos-pickup");
	 pos_deliver = document.getElementById("pos-deliver");
	 ipt_weight = document.getElementById("ipt-weight");
	 ipt_Ep = document.getElementById("ipt-Ep");
	 ipt_Lp = document.getElementById("ipt-Lp");
	 ipt_Ed = document.getElementById("ipt-Ed");
	 ipt_Ld = document.getElementById("ipt-Ld");
	 btn_add = document.getElementById("btn-add");
	 btn_save = document.getElementById("btn-save");
	 btn_remove = document.getElementById("btn-remove");
	 box_confirm = document.getElementById("confirm");
	 message = document.getElementById("message");
	 cfm_yes = document.getElementById("cfm-yes");
	 cfm_no = document.getElementById("cfm-no");
	 box_console = document.getElementById("console");
	 ipt_duration_pickup = document.getElementById("ipt-duration-pickup");
	 ipt_duration_deliver = document.getElementById("ipt-duration-deliver");
	 
	 /*for(var i = 0;i < 59;i++)
		{
			out("cdsgs");
		}*/
	 btn_pickup.onclick = function(){		
		 //alert("click");
		 if(patt_pos.test(pos_pickup.value))
		 {
			 btn_pickup.blur();
		 }				 
	 };
	 btn_deliver.onclick = function(){
		 if(patt_pos.test(pos_deliver.value))
		 {
			 btn_deliver.blur();
		 }		 
	 };
	 
	 btn_add.onclick = function()
	 {	
		
		$("#form-opt-require").slideDown(300);	
		if(req_active == req_counter)
		{
		 	if(checkOpt())
		 	{
		 		confirm("Do you want to save the require?");
		 		cfm_yes.onclick = function()
		 		{
		 			$("div#confirm").slideUp(300);
		 			saveReq();	 		 			
		 			//stopAnimation();
		 			disReq();
		 			req_active = req_counter;
		 			resetOpt();
		 		};
		 		cfm_no.onclick = function()
		 		{	 			
		 			$("div#confirm").slideUp(300);
		 			dmarker.setMap(null);
		 			pmarker.setMap(null);
		 			resetOpt();
		 		};
		 	}
		 	else
		 	{
		 		if(dmarker != null)
		 		{
		 			dmarker.setMap(null);
		 		}
		 		if(pmarker != null)
		 		{
		 			pmarker.setMap(null);
		 		}
		 		//stopAnimation();
		 		req_active = req_counter;
		 		disReq();
			 	resetOpt();
		 	}
		}
	 	else
	 	{
	 		if(dmarker != null)
	 		{
	 			dmarker.setMap(null);
	 		}
	 		if(pmarker != null)
	 		{
	 			pmarker.setMap(null);
	 		}
	 		//stopAnimation();
	 		req_active = req_counter;
	 		disReq();
		 	resetOpt();
	 	}
	 	
	 	
	 	
	 };
	 btn_save.onclick = function(){				 
		 //$("#form-opt-require").slideUp(300);
		 
		saveRequire();
	 };	 	 
	 
 };
function saveRequire()
{
	if(checkOpt())
		 {
			 saveReq();
			 disReq();
	 	     req_active = req_counter;
		 }
		 else
		 {
			 out("invalid");
			 if(dmarker != null)
	 		{
	 			dmarker.setMap(null);
	 		}
	 		if(pmarker != null)
	 		{
	 			pmarker.setMap(null);
	 		}
		 }
		 resetOpt();
}
$(document).ready(function(){	
	//$("#console").hide();
	$("*").keyup(function(evt){
		if(evt.which == 13)
		{
			saveRequire();
		}
	});
	$("*").focus(function(){
		if($(this).is("#btn-pickup") && !patt_pos.test(pos_pickup.value))
		{
			ele_focus = this;
		}
		else if($(this).is("#btn-deliver") && !patt_pos.test(pos_deliver.value))
		{
			ele_focus = this;
		}
		
	});

	$("*").blur(function(){
		//ele_focus = null;
	});	
	$("#popup").click(function(){
		$(this).hide(300);
		$("#box-detail").hide(300);
	});
	$("#clear-console").click(function(){
		$("#console").html("");
	});
	$("div#confirm").hide();
	$("div.li-content").hide();
	$("ul#footer li .p-title").click(function(){		
		$(this).parent().siblings().children(".li-content").slideUp(300);
		$(this).next().slideToggle(300);

	});
	$("ul#footer li .li-content h4").click(function(){
		$(this).parent().slideUp(300);
	});
	loadIdSession();
});
