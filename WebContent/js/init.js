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
	 
	 btn_pickup.onclick = function(){		 
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
	 	if(checkOpt())
	 	{
	 		confirm("Do you want to save the require?");
	 		cfm_yes.onclick = function()
	 		{
	 			$("div#confirm").slideUp(300);
	 			saveReq();	 			
	 			resetOpt();
	 			stopAnimation();
	 			req_active = req_counter;
	 			//alert("YES");
	 		};
	 		cfm_no.onclick = function()
	 		{
	 			resetOpt();
	 			$("div#confirm").slideUp(300);
	 			dmarker.setMap(null);
	 			pmarker.setMap(null);
	 			req_active = req_current;

	 			
	 		};
	 	}
	 	else
	 	{
	 		resetOpt();
	 		//confirm("Do you want to save the require?");	 		
	 	}
	 };
//	 btn_remove.onclick = function(){
//		 
//		 if(req_active != null)
//		 {
//			 removeReq(req_active);
//		 }
//		 else
//		 {
//			 alert("Request active is null");
//		 }
//		 
//	 };

	 btn_save.onclick = function(){		 
		 
		 $("#form-opt-require").slideUp(300);
		 if(checkOpt())
		 {
			 saveReq();
		 }
		 //resetOpt();
		 
	 };
	 //alert("btn_pickup: "+btn_pickup);
 };
$(document).ready(function(){	
	$("*").focus(function(){ele_focus = this;});
	$("*").blur(function(){ele_focus = null;});	
	$("div#confirm").hide();
});