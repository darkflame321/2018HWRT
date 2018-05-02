package com.elasticcloudservice.predict;

import java.util.ArrayList;

public class Predict {

	public static String[] predictVm(String[] ecsContent, String[] inputContent) {

		/** =========do your work here========== **/


			PhyServer[] physerver=new PhyServer[Integer.parseInt(inputContent[0])];
			String[] tmp;
			for(int i=0;i<physerver.length;i++) {
				tmp = inputContent[i+1].split(" ");
				physerver[i]=new PhyServer(tmp[0],Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2])*1024); 
			}
			Flavor[] flavor=new Flavor[Integer.parseInt(inputContent[physerver.length+2])];
			for(int i=0;i<flavor.length;i++) {
				tmp = inputContent[i+physerver.length+3].split(" ");
				flavor[i]=new Flavor(tmp[0],Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]));
			}
			
			
			Date startDate=new Date();
			Date endDate=new Date();
			Date firstDate=new Date();
			Date lastDate=new Date();
			tmp = inputContent[physerver.length+flavor.length+4].split(" ");
			startDate.getDate(tmp[0]);
			if(tmp[1].equals("23:59:59"))
				startDate=startDate.add(startDate);
			tmp = inputContent[physerver.length+flavor.length+5].split(" ");
			endDate.getDate(tmp[0]);
			if(tmp[1].equals("23:59:59"))
				endDate=endDate.add(endDate);
			tmp = ecsContent[0].split("\t");
			firstDate.getDate(tmp[2].split(" ")[0]);
			tmp = ecsContent[ecsContent.length-1].split("\t");
			lastDate.getDate(tmp[2].split(" ")[0]);
			
			
			for(int i=0;i<ecsContent.length;i++){ 
				tmp = ecsContent[i].split("\t");
				for(int j=0;j<flavor.length;j++) {
					if(tmp[1].equals(flavor[j].name)) {
						Date tmpdate=new Date();
						tmpdate.getDate(tmp[2].split(" ")[0]);
						flavor[j].addusedate(tmpdate);
						}
				}
			}

			int[] predictflavor=new int[flavor.length];
			
			Train train=new Train(flavor,firstDate,lastDate,startDate,endDate);
			predictflavor=train.getresult();

			for(int i=0;i<predictflavor.length; i++)
				System.out.println(predictflavor[i]);
			
			ArrayList<String> resultlist = new ArrayList<String>();
			
			int allflavor=0;
			
			for(int i=0;i<predictflavor.length; i++)
				allflavor+=predictflavor[i];
			
			System.out.println("allflavor:"+allflavor);	

			
			resultlist.add(allflavor+"");
			for(int i=0;i<flavor.length; i++) {
				resultlist.add(flavor[i].name + " " + predictflavor[i]);
			}
			
			Place place=new Place(predictflavor);

			ArrayList<String> outputserver=place.placePhyserver(physerver,flavor);


			resultlist.add("");
			for(int i=0;i<outputserver.size(); i++) {
				resultlist.add(outputserver.get(i));
				}
			
			String[] results = new String[resultlist.size()];
			for(int i=0;i<results.length;i++) {
				results[i]=resultlist.get(i);
			}
			

		return results;
	}

}
