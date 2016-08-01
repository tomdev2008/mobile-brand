package com.xiu.mobile.portal.common.util;

import com.xiu.mobile.portal.model.TopicFilterItemVO;

public class ComparatorFilterItemAsc<T> implements java.util.Comparator<T> {

	@Override
	public int compare(Object arg0, Object arg1) {
		TopicFilterItemVO goodsVo1 = (TopicFilterItemVO) arg0;
		TopicFilterItemVO goodsVo2 = (TopicFilterItemVO) arg1;
		String d1 =goodsVo1.getFilterItemName();
		String d2 =goodsVo2.getFilterItemName();
		if(d1.equals("其他")){
			return 1;
		}
		if(d2.equals("其他")){
			return -1;
		}
		d1=getChangeSize(d1).toUpperCase();
		d2=getChangeSize(d2).toUpperCase();
		String reg="[0-9]+\\.?[0-9]*";
		if(d1.matches(reg)&&d2.matches(reg)){
			Double dd1=Double.valueOf(d1);
			Double dd2=Double.valueOf(d2);
			return dd1.compareTo(dd2);
		}
		
		return (d1).compareTo(d2);
	}

	
	public String getChangeSize(String size){
		if(size.toUpperCase().equals("XXXXS")){
			return "-4";
		}
		else if(size.toUpperCase().equals("XXXS")){
			return "-3";
		}
		else if(size.toUpperCase().equals("XXS")){
			return "-2";
		}
		else if(size.toUpperCase().equals("XS")){
			return "-1";
		}
		else if(size.toUpperCase().equals("S")){
			return "1";
		}
		else if(size.toUpperCase().equals("M")){
			return "2";
		}
		else if(size.toUpperCase().equals("L")){
			return "3";
		}
		else if(size.toUpperCase().equals("XL")){
			return "4";
		}
		else if(size.toUpperCase().equals("XXL")){
			return "5";
		}
		else if(size.toUpperCase().equals("XXXL")){
			return "6";
		}
		else if(size.toUpperCase().equals("XXXXL")){
			return "7";
		}else if(size.toUpperCase().equals("XXXXXL")){
			return "8";
		}else if(size.toUpperCase().indexOf("US")==0){
			return "1"+size.replace("US", "");
		}else if(size.toUpperCase().indexOf("-")>0){
			return size.replace("-", "");
		}
		return size;
	}
	
	public static void main(String[] args) {
//		Integer d1=3;
//		Integer d2=2;
		String d1="1";
		String d2="22";
		String reg="[0-9]+\\.?[0-9]*";
		if(d1.matches(reg)&&d2.matches(reg)){
			Double dd1=Double.valueOf(d1);
			Double dd2=Double.valueOf(d2);
			System.out.println(dd1.compareTo(dd2));
		}
		System.out.println("--");
	}
}
