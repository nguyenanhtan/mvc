function Request(id, pPos,dPos,weight,Ep,Lp,Ed,Ld,dP,dD)
{
	this.pickup = pPos;
	this.deliver = dPos;
	this.weight = weight;
	this.Ep = Ep;
	this.Lp = Lp;
	this.Ed = Ed;
	this.Ld = Ld;
	this.id = id;
	this.duration_pickup = dP;
	this.duration_deliver = dD;
	this.print = function()
	{
		alert(this.pickup+"///////"+this.deliver+"/"+this.weight+"/"+this.Ed+"/"+this.Ld);
	};
}