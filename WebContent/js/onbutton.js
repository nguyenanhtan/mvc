
function checkOpt()
{
	if(!patt_pos.test(pos_pickup.value))
	{
		return false;
	}
	else if(!patt_pos.test(pos_deliver.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_weight.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_Ep.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_Lp.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_Ed.value))
	{
		return false;
	}
	else if(!patt_num.test(ipt_Ld.value))
	{
		return false;
	}
	return true;
}
function confirm(mes)
{
	message.innerHTML = mes;
	$("div#confirm").slideDown(300);
}
function resetOpt()
{
	pos_pickup.value = "Position pickup";
	pos_deliver.value = "Position deliver";
	
	ipt_weight.value = "0";
	ipt_Ep.value = "0";
	ipt_Lp.value = "9999";
	ipt_Ed.value = "0";
	ipt_Ld.value = "9999";
}
function saveRequest()
{
	
}