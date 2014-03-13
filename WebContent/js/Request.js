function Request(pPos,dPos,weight,Ep,Lp,Ed,Ld)
{
	this.pickup = pPos;
	this.deliver = dPos;
	this.weight = weight;
	this.Ep = Ep;
	this.Lp = Lp;
	this.Ed = Ed;
	this.Ld = Ld;	
	this.print = function()
	{
		alert(this.pickup+"///////"+this.deliver+"/"+this.weight+"/"+this.Ed+"/"+this.Ld);
	};
}