package com.elasticcloudservice.predict;



public class Train {

	
	double[][] x;
//	double[][] sumx_orgin;
	double[][] sumx;
	double[] averx;
	int start;
	int end;
	
	
	
	public Train(Flavor[] flavor,Date firstDate,Date lastDate,Date startDate,Date endDate) {
		
		int last=lastDate.diff(firstDate)+1;
		this.x=new double[flavor.length][last];
		for(int i=0;i<flavor.length;i++) {
			int count=0;
			
			for(int date=0;date<last;date++) {
				for(int j=0;j<flavor[i].usedate.size();j++) {
					if(flavor[i].usedate.get(j).diff(firstDate)==date) {
						count++;
					}
				}
				this.x[i][date]=count;
				count=0;
			}
			
			
		}
		
		this.start=startDate.diff(firstDate);
		this.end=endDate.diff(firstDate);
//		this.sumx_orgin=new double[flavor.length][last];
		this.sumx=new double[flavor.length][last];
		this.averx=new double[flavor.length];
		//biao zhun cha
		double[] sdx=new double[flavor.length];
		
		for(int i=0;i<flavor.length;i++) {
			double sum=0,sum2=0;
			for(int j=0;j<x[0].length;j++) {
				
				sum+=x[i][j];
				sum2+=x[i][j]*x[i][j];
				this.sumx[i][j]=sum;
			}

//			for(int j=0;j<x[0].length;j++) {
//				this.sumx[i][j]/=sumx[i][last-1];
//			}
//			this.averx[i]=sum/x[0].length;
//			sdx[i]=Math.sqrt(sum2/x[0].length-averx[i]*averx[i]);			
//			for(int j=0;j<x[0].length;j++) {
//				if(Math.abs(this.x[i][j]-averx[i])>(4*sdx[i])) {
//					this.x[i][j]=averx[i];
//				}
//			}
//			
//			sum=0;
//			for(int j=0;j<x[0].length;j++) {			
//				sum+=x[i][j];
//				this.sumx[i][j]=sum;
//			}
//			maxsumx[i]=this.sumx[i][last-1];
//			for(int j=0;j<x[0].length;j++) {
//				this.sumx[i][j]/=maxsumx[i];
//			}
			
		}
//		System.out.println("last:"+last+" start:"+start+" end"+end);
	}
	
	
	
	
	public int[] getresult() {
		//yi jie zhi shu
//		double a=0.223;
//		double[][] S=new double[this.x.length][this.end];
//		
//		for(int i=0;i<S.length;i++) {
//			S[i][0]=(this.sumx[i][0]+this.sumx[i][1]+this.sumx[i][2])/3;
//			for(int j=0;j<this.sumx[0].length;j++) {
//				S[i][j+1]=a*sumx[i][j]+(1-a)*S[i][j];
//			}
//			
//		}
		
		//er jie zhi shu
		double a1=0.152;
		double[][] SS1=new double[this.x.length][this.end];
		double[][] SS2=new double[this.x.length][this.end];
		double[] b1=new double[this.x.length];
		for(int i=0;i<SS1.length;i++) {
			SS1[i][0]=(this.sumx[i][0]+this.sumx[i][1]+this.sumx[i][2])/3;
			SS2[i][0]=(this.sumx[i][0]+this.sumx[i][1]+this.sumx[i][2])/3;
			for(int j=0;j<this.sumx[0].length;j++) {
				SS1[i][j+1]=a1*sumx[i][j]+(1-a1)*SS1[i][j];
				SS2[i][j+1]=a1*SS1[i][j+1]+(1-a1)*SS2[i][j];
			}
			b1[i]=a1*(SS1[i][sumx[0].length]-SS2[i][sumx[0].length])/(1-a1);
		}
		
		//san jie zhi shu
		double a3=0.038;
		double[][] S1=new double[this.x.length][this.end];
		double[][] S2=new double[this.x.length][this.end];
		double[][] S3=new double[this.x.length][this.end];
		double[] b3=new double[this.x.length];
		double[] c=new double[this.x.length];
		for(int i=0;i<S1.length;i++) {
			S1[i][0]=(this.sumx[i][0]+this.sumx[i][1]+this.sumx[i][2])/3;
			S2[i][0]=(this.sumx[i][0]+this.sumx[i][1]+this.sumx[i][2])/3;
			S3[i][0]=(this.sumx[i][0]+this.sumx[i][1]+this.sumx[i][2])/3;
			for(int j=0;j<this.sumx[0].length;j++) {
				S1[i][j+1]=a3*sumx[i][j]+(1-a3)*S1[i][j];
				S2[i][j+1]=a3*S1[i][j+1]+(1-a3)*S2[i][j];
				S3[i][j+1]=a3*S2[i][j+1]+(1-a3)*S3[i][j];
			}	
			b3[i]=a3*((6-5*a3)*S1[i][sumx[0].length]-
					(10-8*a3)*S2[i][sumx[0].length]+(4-3*a3)*S3[i][sumx[0].length])/2/(1-a3)/(1-a3);
			c[i]=a3*a3*(S1[i][sumx[0].length]-2*S2[i][sumx[0].length]+S3[i][sumx[0].length])/(1-a3)/(1-a3);
		}
		
		
		
		
		
		
		

//		int n=x[0].length;
//		double[] a1=new double[x.length];
//		double[] a2=new double[x.length];
//		double[] bb=new double[x.length];
//		for(int i=0;i<sumx.length;i++) {
//			double dx=0,dy=0,dxx=0,dxxx=0,dxxxx=0,dxy=0,dxxy=0;
//			double k1,k2,k3,k4,k5,k6;
//			for(int j=0;j<n;j++) {
//				dx+=j;
//				dxx+=j*j;
//				dxxx+=j*j*j;
//				dxxxx+=j*j*j*j;
//				dy+=sumx[i][j];
//				dxy+=sumx[i][j]*j;
//				dxxy+=sumx[i][j]*j*j;
//			}
//			k1=n*dxx-dx*dx;
//			k2=n*dxxx-dxx*dx;
//			k3=n*dxy-dy*dx;
//			k4=n*dxxx-dx*dxx;
//			k5=n*dxxxx-dxx*dxx;
//			k6=n*dxxy-dy*dxx;
//			bb[i]=(n*dxy-dx*dy)/(n*dxx-dx*dx);
//			a2[i]=(k3*k4-k1*k6)/(k2*k4-k1*k5);
//			a1[i]=(k3-a2[i]*k2)/k1;
//		}
		
		
//    ti du xai jiang		
//		int n=(this.end-this.start)*2;
//		int m=this.x[0].length-n;
//		double a=0.0001;
//		double[][] tmpresult=new double[this.x.length][this.end];
//		
//		
//		for(int i=0;i<this.x.length;i++) {
//			double[] y=new double[m];
//			double[][] x2=new double[m][n+1];
//			for(int p=0;p<m;p++) {
//				y[p]=sumx[i][p+n];
//				for(int q=0;q<n;q++) {
//					x2[p][q]=sumx[i][p+q];
//				}
//				x2[p][n]=1;
//			}
//			double[] theta=new double[n+1];
//			for(int p=0;p<(n+1);p++) {
//				theta[p]=0;
//			}
//			double mincost=0;
//			for(int j=0;j<m;j++) {
//				mincost+=y[j]*y[j];
//			}
//			mincost/=2;
//			int flag=0;
//			while(true) {
//				double[] tmp=new double[n+1];
//				for(int j=0;j<(n+1);j++) {
//					tmp[j]=0;
//				}
//				
//				for(int p=0;p<(n+1);p++) {
//					for(int j=0;j<m;j++) {
//						double sum=0;
//						for(int k=0;k<(n+1);k++) {
//							sum+=theta[k]*x2[j][k];
//						}
//						tmp[p]+=((sum-y[j])*x2[j][p]);					
//					}
//				}
//				for(int j=0;j<(n+1);j++) {
//					theta[j]-=(a*tmp[j]);
//				}
////				double cost=0;
////				for(int j=0;j<m;j++) {
////					double tmpvalue=0;
////					for(int k=0;k<(n+1);k++) {
////						tmpvalue+=theta[k]*x2[j][k];
////					}
////					cost+=(tmpvalue-y[j])*(tmpvalue-y[j]);
////				}
////				cost/=2;
////				
////				if(cost<mincost) {
////					mincost=cost;
////					flag=0;
////				}
////				else
//					flag++;
//				
//				if(flag==10000)
//					break;
//				
//			}
//			
//			
//			for(int p=0;p<sumx[0].length;p++) {
//				tmpresult[i][p]=this.sumx[i][p];
//			}
//			for(int p=sumx[0].length;p<this.end;p++) {
//				double sum=0;
//				for(int q=0;q<n;q++) {
//					sum+=theta[q]*tmpresult[i][p-n+q];				
//					}
//				tmpresult[i][p]=sum+theta[n];
//			}
//			
//			
//			System.out.println(i+" :");
//			for(int p=0;p<(n+1);p++) {
//				System.out.print(theta[p]+" ");
//			}
//			System.out.println();
//		}
		

		double k=0.95,k2=0;
		int[] result=new int[this.x.length];
		for(int i=0;i<result.length;i++) {
			double output=0,output1,output2;
//			for(int date=this.start;date<this.end;date++) {	
			output1=(sumx[i][sumx[0].length-1]-sumx[i][sumx[0].length-1-end+start]);
			output2=k2*b1[i]*(end-start)+(1-k2)*(b3[i]*(end-start)+0.5*c[i]*(end*end-start*start));
//			output2=(tmpresult[i][end-1]-tmpresult[i][start-1])*sumx[i][sumx[0].length-1];
			output=(1-k)*output1+k*output2;
//			output=(1-k)*output1+k*((1-k2)*b1[i]*(end-start)+k2*(b3[i]*(end-start)+0.5*c[i]*(end*end-start*start)));
//			output=(sumx[i][sumx[0].length-1]-sumx[i][sumx[0].length-1-end+start]);
//			output=(1-k)*output1+k*output2;
			
			if(output<0)
				result[i]=0;
			else {
				if(output%1<0.5)
					result[i]=(int)output;
				else
					result[i]=(int)output+1;
			}
		}
		
		return result;

			
		}
}
