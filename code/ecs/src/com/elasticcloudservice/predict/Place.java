package com.elasticcloudservice.predict;

import java.util.ArrayList;
import java.util.Random;

public class Place {
	public ArrayList<Integer> flavornumb;
	
	
	
	public Place(int[] predictFlavor) {
	
		this.flavornumb=new ArrayList<Integer>();
		for(int i=0;i<predictFlavor.length;i++) {
			while(predictFlavor[i]!=0) {
				flavornumb.add(i);
				predictFlavor[i]--;
			}
		}

	}
	
	public ArrayList<String> placePhyserver(PhyServer physerver[],Flavor[] flavor) {
		
		ArrayList<String> result=new ArrayList<String>();
		double T=100;
		double Tmin=0.1;
		double r=0.999;
		double bestscore=0;
		ArrayList<Integer> bestinput=new ArrayList<Integer>();	
		ArrayList<int[]> bestoutputserver0=new ArrayList<int[]>();
		ArrayList<int[]> bestoutputserver1=new ArrayList<int[]>();
		ArrayList<int[]> bestoutputserver2=new ArrayList<int[]>();
		bestinput.addAll(flavornumb);
		Random random = new Random();
		
		while(T>Tmin) {
		ArrayList<int[]> outputserver0=new ArrayList<int[]>();
		ArrayList<int[]> outputserver1=new ArrayList<int[]>();
		ArrayList<int[]> outputserver2=new ArrayList<int[]>();
		ArrayList<Integer> newinput=new ArrayList<Integer>();
		newinput.addAll(bestinput);
//		System.out.println(bestinput.size());
		int x1=random.nextInt(newinput.size());
		int x2=random.nextInt(newinput.size());
		int x1_value=newinput.get(x1);
		newinput.set(x1, newinput.get(x2));
		newinput.set(x2, x1_value);
		ArrayList<Integer> input=new ArrayList<Integer>();
		input.addAll(newinput);

		while(true) {
			int[] output0=new int[flavor.length];
			int[] output1=new int[flavor.length];
			int[] output2=new int[flavor.length];
			int[] leftcpu= {physerver[0].allCPU,physerver[1].allCPU,physerver[2].allCPU};
			int[] leftmem= {physerver[0].allMEM,physerver[1].allMEM,physerver[2].allMEM};
			int i=0;
			boolean flag0=false,flag1=false,flag2=false;
			if(input.size()==0)
				break;
			
			while(true) {				
				if(i>=input.size())
					break;
				else {
					int a=input.get(i);
					double judge=(double)flavor[a].MEM/flavor[a].CPU/1024;
				
					if(judge<2.5) {
						if((leftcpu[2]>=flavor[a].CPU)&&(leftmem[2]>=flavor[a].MEM)) {
							leftcpu[2]-=flavor[a].CPU;
							leftmem[2]-=flavor[a].MEM;
							input.remove(i);
							output2[a]++;
							flag2=true;
						}
						else
							i++;
					}
					else if(judge>=2.5){
						if((leftcpu[1]>=flavor[a].CPU)&&(leftmem[1]>=flavor[a].MEM)) {
							leftcpu[1]-=flavor[a].CPU;
							leftmem[1]-=flavor[a].MEM;
							input.remove(i);
							output1[a]++;
							flag1=true;
						}
						else
							i++;
					}
				}
			}
			int input_sumcpu=0,input_summem=0;
			for(int n=0;n<input.size();n++) {
				input_sumcpu+=flavor[input.get(n)].CPU;
				input_summem+=flavor[input.get(n)].MEM;
			}
			if((input_sumcpu<=leftcpu[0])&&(input_summem<=leftmem[0])&&(input_sumcpu!=0)&&(input_summem!=0)) {
				for(int n=0;n<input.size();) {
					output0[input.get(n)]++;
					input.remove(n);
				}
				flag0=true;
			}	
			
			if(flag0)
				outputserver0.add(output0);
			if(flag1)
				outputserver1.add(output1);
			if(flag2)
				outputserver2.add(output2);		
		}
		double score=1;
		for(int j=0;j<outputserver0.size();j++) {
			double score1=0,score2=0;
			for(int i=0;i<flavor.length;i++) {
				score1+=(outputserver0.get(j)[i]*flavor[i].CPU);
				score2+=(outputserver0.get(j)[i]*flavor[i].MEM);
			}
			score=score*((score1/(double)physerver[0].allCPU)+(score2/(double)physerver[0].allMEM))/2;
		}
		for(int j=0;j<outputserver1.size();j++) {
			double score1=0,score2=0;
			for(int i=0;i<flavor.length;i++) {
				score1+=(outputserver1.get(j)[i]*flavor[i].CPU);
				score2+=(outputserver1.get(j)[i]*flavor[i].MEM);
			}
			score=score*((score1/(double)physerver[1].allCPU)+(score2/(double)physerver[1].allMEM))/2;
		}

		for(int j=0;j<outputserver2.size();j++) {
			double score1=0,score2=0;
			for(int i=0;i<flavor.length;i++) {
				score1+=(outputserver2.get(j)[i]*flavor[i].CPU);
				score2+=(outputserver2.get(j)[i]*flavor[i].MEM);
			}
			score=score*((score1/(double)physerver[2].allCPU)+(score2/(double)physerver[2].allMEM))/2;
		}


//		System.out.println("score1:"+score);
		//pan duan tui huo
		if(score>bestscore) {
			bestscore=score;
			bestoutputserver0=new ArrayList<int[]>();
			bestoutputserver1=new ArrayList<int[]>();
			bestoutputserver2=new ArrayList<int[]>();
			bestinput=new ArrayList<Integer>();	
			bestoutputserver0.addAll(outputserver0);
			bestoutputserver1.addAll(outputserver1);
			bestoutputserver2.addAll(outputserver2);
			bestinput.addAll(newinput);
		}
		else if(Math.exp((double)(bestoutputserver0.size()-outputserver0.size()+
				bestoutputserver1.size()-outputserver1.size()+
				bestoutputserver2.size()-outputserver2.size())/T)>random.nextDouble()){
			bestscore=score;
			bestoutputserver0=new ArrayList<int[]>();
			bestoutputserver1=new ArrayList<int[]>();
			bestoutputserver2=new ArrayList<int[]>();
			bestinput=new ArrayList<Integer>();	
			bestoutputserver0.addAll(outputserver0);
			bestoutputserver1.addAll(outputserver1);
			bestoutputserver2.addAll(outputserver2);
			bestinput.addAll(newinput);
		}
//		System.out.println(bestscore);
		T=r*T;
		
		}
		

		String tmpresult=new String();
		int result_start;
		if(bestoutputserver0.size()!=0) {
			result.add(physerver[0].name+" "+bestoutputserver0.size());
		}
		result_start=result.size();
		for(int i=0;i<bestoutputserver0.size();i++) {
			result.add(physerver[0].name+"-"+(i+1));
			for(int j=0;j<flavor.length;j++) {
				if(bestoutputserver0.get(i)[j]!=0) {
					tmpresult=result.get(i+result_start);
					result.set(i+result_start, tmpresult+" "+flavor[j].name+" "+
					bestoutputserver0.get(i)[j]);
				}
			}
		}
		
		if(bestoutputserver1.size()!=0) {
			result.add("");
			result.add(physerver[1].name+" "+bestoutputserver1.size());
		}
		result_start=result.size();
		for(int i=0;i<bestoutputserver1.size();i++) {
			result.add(physerver[1].name+"-"+(i+1));
			for(int j=0;j<flavor.length;j++) {
				if(bestoutputserver1.get(i)[j]!=0) {
					tmpresult=result.get(i+result_start);
					result.set(i+result_start, tmpresult+" "+flavor[j].name+" "+
					bestoutputserver1.get(i)[j]);
				}
			}
		}
		
		if(bestoutputserver2.size()!=0) {
			result.add("");
			result.add(physerver[2].name+" "+bestoutputserver2.size());
		}
		result_start=result.size();
		for(int i=0;i<bestoutputserver2.size();i++) {
			result.add(physerver[2].name+"-"+(i+1));
			for(int j=0;j<flavor.length;j++) {
				if(bestoutputserver2.get(i)[j]!=0) {
					tmpresult=result.get(i+result_start);
					result.set(i+result_start, tmpresult+" "+flavor[j].name+" "+
					bestoutputserver2.get(i)[j]);
				}
			}
		}
			
		return result;
		
	}
}
