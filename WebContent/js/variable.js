var map;
var directionsDisplay;
var markers = new Array();
var arr_request = new Array();
var icon_pickup = "./img/Map-Marker-Ball-Azure-icon.png";
var icon_deliver = "./img/Map-Marker-Ball-Pink-icon.png";
var icon_marker = "";//icon_pickup;
//Element
var btn_pickup = null;
var btn_deliver = null;
var pos_pickup;
var pos_deliver;
var ipt_weight;
var ipt_Ep;
var ipt_Lp;
var ipt_Ed;
var ipt_Ld;
var btn_add;
var btn_save;
var btn_remove;
var box_confirm;
var message;
var cfm_yes;
var cfm_no;

var ele_focus = null;
var req_active = 0;
var req_counter = 0;
var dmarker;
var pmarker;

var directionsService = new google.maps.DirectionsService();
var patt_pos=/^\(-{0,1}\d*\.{0,1}\d+,\s{0,1}-{0,1}\d*\.{0,1}\d+\)$/;	
var patt_num=/^\d+$/;
