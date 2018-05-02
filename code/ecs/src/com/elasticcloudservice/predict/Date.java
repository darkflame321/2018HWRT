package com.elasticcloudservice.predict;

public class Date {
	public int year;
	public int month;
	public int day;
	
	public Date() {
	}
	
	public void getDate(String s) {
		String[] tmp=s.split("-");
		this.year=Integer.parseInt(tmp[0]);
		this.month=Integer.parseInt(tmp[1]);
		this.day=Integer.parseInt(tmp[2]);
	}
	
	public void printDate() {
		System.out.println(year+" "+month+" "+day);
	}
	
	public Date add(Date olddate) {
		int year2=olddate.year,month2=olddate.month,day2=olddate.day;
		switch(olddate.month) {
		case 1:
			if(olddate.day==31) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 2:
			if(olddate.year%4==0)
				if(olddate.day==29) {
					day2=0;
					month2=3;
				}
			else if(olddate.day==28) {
				day2=0;
				month2=3;
			}
			else
				day2++;
			break;
		case 3:
			if(olddate.day==31) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 4:
			if(olddate.day==30) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 5:
			if(olddate.day==31) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 6:
			if(olddate.day==30) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 7:
			if(olddate.day==31) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 8:
			if(olddate.day==31) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 9:
			if(olddate.day==30) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 10:
			if(olddate.day==31) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 11:
			if(olddate.day==30) {
				day2=0;
				month2++;
				}
			else
				day2++;
			break;
		case 12:
			if(olddate.day==31) {
				day2=0;
				month2=1;
				year++;
				}
			else
				day2=olddate.day+1;
			break;
		}
		Date date2=new Date();
		date2.day=day2;
		date2.month=month2;
		date2.year=year2;
		return date2;
	}
	
	public boolean equal(Date olddate) {
		if(this.day==olddate.day&&this.month==olddate.month&&this.year==olddate.year)
			return true;
		else 
			return false;
	}
	
	
	
	public int diff(Date oldDate) {
		int diffday=0,day1=0,day2=0;
		
		for(int i=1;i<oldDate.month;i++) {
			switch(i) {
			case 1:
				day1=day1+31;
				break;
			case 2:
				if(oldDate.year%4==0)
					day1=day1+29;
				else
					day1=day1+28;
				break;
			case 3:
				day1=day1+31;
				break;
			case 4:
				day1=day1+30;
				break;
			case 5:
				day1=day1+31;
				break;
			case 6:
				day1=day1+30;
				break;
			case 7:
				day1=day1+31;
				break;
			case 8:
				day1=day1+31;
				break;
			case 9:
				day1=day1+30;
				break;
			case 10:
				day1=day1+31;
				break;
			case 11:
				day1=day1+30;
				break;
			case 12:
				day1=day1+31;
				break;
			}
		}
		
		for(int i=1;i<month;i++) {
			switch(i) {
			case 1:
				day2=day2+31;
				break;
			case 2:
				if(year%4==0)
					day2=day2+29;
				else
					day2=day2+28;
				break;
			case 3:
				day2=day2+31;
				break;
			case 4:
				day2=day2+30;
				break;
			case 5:
				day2=day2+31;
				break;
			case 6:
				day2=day2+30;
				break;
			case 7:
				day2=day2+31;
				break;
			case 8:
				day2=day2+31;
				break;
			case 9:
				day2=day2+30;
				break;
			case 10:
				day2=day2+31;
				break;
			case 11:
				day2=day2+30;
				break;
			case 12:
				day2=day2+31;
				break;
			}
		}
		
		for(int i=oldDate.year;i<year;i++) {
			if(i%4==0)
				diffday=diffday+366;
			else
				diffday=diffday+365;
		}
		
		return diffday+day2-day1-oldDate.day+day;
		
	}
	
//	public boolean holiday(Date date) {
//		boolean flag=false;
//		if(date.month==1&&date.day>=1&&date.day<=3)
//			flag=true;
//		else if(date.month==5&&date.day>=1&&date.day<=3)
//			flag=true;
//		else if(date.month==10&&date.day>=1&&date.day<=7)
//			flag=true;
//		else if(date.year==2015&&date.month==2&&date.day>=18&&date.day<=24)
//			flag=true;
//		else if(date.year==2015&&date.month==4&&date.day>=5&&date.day<=7)
//			flag=true;
//		else if(date.year==2015&&date.month==6&&date.day>=20&&date.day<=22)
//			flag=true;
//		else if(date.year==2015&&date.month==9&&date.day>=27&&date.day<=29)
//			flag=true;
//		else if(date.year==2016&&date.month==2&&date.day>=7&&date.day<=13)
//			flag=true;
//		else if(date.year==2016&&date.month==4&&date.day>=4&&date.day<=6)
//			flag=true;
//		else if(date.year==2016&&date.month==6&&date.day>=9&&date.day<=11)
//			flag=true;
//		else if(date.year==2016&&date.month==9&&date.day>=15&&date.day<=17)
//			flag=true;
//		else if(date.year==2017&&date.month==1&&date.day>=27&&date.day<=31)
//			flag=true;
//		else if(date.year==2017&&date.month==2&&date.day>=1&&date.day<=2)
//			flag=true;
//		else if(date.year==2017&&date.month==4&&date.day>=2&&date.day<=4)
//			flag=true;
//		else if(date.year==2017&&date.month==5&&date.day>=28&&date.day<=30)
//			flag=true;
//		else if(date.year==2017&&date.month==10&&date.day==8)
//			flag=true;
//
//		return flag;
//	}

}
