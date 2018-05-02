package com.elasticcloudservice.predict;

import java.util.ArrayList;

public class Flavor {
	public String name;
	public int CPU;
	public int MEM;
	public ArrayList<Date> usedate;
	

	public Flavor(String name,int CPU,int MEM) {
		this.name=name;
		this.CPU=CPU;
		this.MEM=MEM;
		this.usedate=new ArrayList<Date>();
	}
	
	public void addusedate(Date getusedate) {
		this.usedate.add(getusedate);
	}



}
