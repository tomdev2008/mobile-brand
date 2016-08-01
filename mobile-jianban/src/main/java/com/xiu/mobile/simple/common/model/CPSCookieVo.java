package com.xiu.mobile.simple.common.model;

public class CPSCookieVo {
    public static final String splitStr = "^^";
	
    public static final String equalStr = "=";
	
	public final static String CPS_COOKIE = "kcps";

	public final static String COOKIE_NAME_FROMID = "fromid";// 媒体来源id

	public final static String COOKIE_NAME_CPS = "cps";// 联盟来源

	public final static String COOKIE_NAME_CPSTYPE = "cps_type"; // 表示进入走秀下单的来源是媒体或联盟

	public final static String COOKIE_NAME_CHANNEL_TYPE = "channel_type"; // 秀团和走秀网公用cookie,
	// 在官网用作表示媒体或联盟先进秀团的标识

	public final static String COOKIE_NAME_CHANNEL_NAME = "channel_name"; // 秀团和走秀网公用cookie,
	// 在官网用作表示媒体id或联盟id

	public final static String COOKIE_NAME_CPS_A_ID = "a_id"; // cps所需ID
	public final static String COOKIE_NAME_CPS_U_ID = "u_id"; // cps所需ID
	public final static String COOKIE_NAME_CPS_W_ID = "w_id"; // cps所需ID
	public final static String COOKIE_NAME_CPS_BID = "bid"; // cps所需ID
	public final static String COOKIE_NAME_CPS_UID = "uid"; // cps所需ID
	public final static String COOKIE_NAME_CPS_ABID = "abid"; // cps所需ID
	public final static String COOKIE_NAME_CPS_CID = "cid"; // cps所需ID
	
	public final static String COOKIE_NAME_CPS_L_ID="l_id";  
	public final static String COOKIE_NAME_CPS_L_TYPE1="l_type1";  
	public final static String COOKIE_NAME_CPS_QMAIL="qmail";  
	public final static String COOKIE_NAME_CPS_QNAME="qname";  
	public final static String COOKIE_NAME_CPS_SHOPID="ShopId";   
	public final static String COOKIE_NAME_CPS_UNIONPARAMS="unionParams";  
	public final static String COOKIE_NAME_CPS_USERID="UserId"; 
	public final static String COOKIE_NAME_CPS_VALUEID="valueid"; 
	public final static String COOKIE_NAME_CPS_CHANNELID="channelId"; 
	public final static String COOKIE_NAME_CPS_A_UID="a_uid"; 
	public final static String COOKIE_NAME_CPS_C_ID="c_id";

	String fromid;// 媒体来源id

	String cps;// 联盟来源

	String cps_type; // 表示进入走秀下单的来源是媒体或联盟

	String channel_type; // 秀团和走秀网公用cookie, 在官网用作表示媒体或联盟先进秀团的标识

	String channel_name; // 秀团和走秀网公用cookie, 在官网用作表示媒体id或联盟id

	String a_id; // cps所需ID
	String u_id; // cps所需ID
	String w_id; // cps所需ID
	

	String bid; // cps所需ID
	String uid; // cps所需ID
	String abid; // cps所需ID
	String cid; // cps所需ID
	
	
	String l_id;
	String l_type1;  
	String qmail;  
	String qname;  
	String ShopId;   
	String unionParams;  
	String UserId; 
	String valueid; 
	String channelId; 
	String a_uid; 
	String c_id;

	public String getFromid() {
		return fromid;
	}

	public void setFromid(String fromid) {
		this.fromid = fromid;
	}

	public String getCps() {
		return cps;
	}

	public void setCps(String cps) {
		this.cps = cps;
	}

	public String getCps_type() {
		return cps_type;
	}

	public void setCps_type(String cps_type) {
		this.cps_type = cps_type;
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getA_id() {
		return a_id;
	}

	public void setA_id(String a_id) {
		this.a_id = a_id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getAbid() {
		return abid;
	}

	public void setAbid(String abid) {
		this.abid = abid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getW_id() {
		return w_id;
	}

	public void setW_id(String w_id) {
		this.w_id = w_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getL_id() {
		return l_id;
	}

	public void setL_id(String lId) {
		l_id = lId;
	}

	public String getL_type1() {
		return l_type1;
	}

	public void setL_type1(String lType1) {
		l_type1 = lType1;
	}

	public String getQmail() {
		return qmail;
	}

	public void setQmail(String qmail) {
		this.qmail = qmail;
	}

	public String getQname() {
		return qname;
	}

	public void setQname(String qname) {
		this.qname = qname;
	}

	public String getShopId() {
		return ShopId;
	}

	public void setShopId(String shopId) {
		ShopId = shopId;
	}

	public String getUnionParams() {
		return unionParams;
	}

	public void setUnionParams(String unionParams) {
		this.unionParams = unionParams;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getValueid() {
		return valueid;
	}

	public void setValueid(String valueid) {
		this.valueid = valueid;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getA_uid() {
		return a_uid;
	}

	public void setA_uid(String aUid) {
		a_uid = aUid;
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String cId) {
		c_id = cId;
	}
	
	public String retValues(String param){
		String str="";
		if(param!=null){
		   str=param;
		}
		return str;
	}
	
	public String toString() {
		StringBuffer sb=new StringBuffer("");
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_A_ID).append(equalStr).append(retValues(this.getA_id())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_ABID).append(equalStr).append(retValues(this.getAbid())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_BID).append(equalStr).append(retValues(this.getBid())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CHANNEL_NAME).append(equalStr).append(retValues(this.getChannel_name())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CHANNEL_TYPE).append(equalStr).append(retValues(this.getChannel_type())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_CID).append(equalStr).append(retValues(this.getCid())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS).append(equalStr).append(retValues(this.getCps())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPSTYPE).append(equalStr).append(retValues(this.getCps_type())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_FROMID).append(equalStr).append(retValues(this.getFromid())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_U_ID).append(equalStr).append(retValues(this.getU_id())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_UID).append(equalStr).append(retValues(this.getUid())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_W_ID).append(equalStr).append(retValues(this.getW_id())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_L_ID).append(equalStr).append(retValues(this.getL_id())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_L_TYPE1).append(equalStr).append(retValues(this.getL_type1())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_QMAIL).append(equalStr).append(retValues(this.getQmail())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_QNAME).append(equalStr).append(retValues(this.getQname())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_SHOPID).append(equalStr).append(retValues(this.getShopId())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_UNIONPARAMS).append(equalStr).append(retValues(this.getUnionParams())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_USERID).append(equalStr).append(retValues(this.getUserId())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_VALUEID).append(equalStr).append(retValues(this.getValueid())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_CHANNELID).append(equalStr).append(retValues(this.getChannelId())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_A_UID).append(equalStr).append(retValues(this.getA_uid())).append(splitStr);
		sb.append(CPSCookieVo.COOKIE_NAME_CPS_C_ID).append(equalStr).append(retValues(this.getC_id())).append(splitStr);
        return sb.toString();
    }
}
