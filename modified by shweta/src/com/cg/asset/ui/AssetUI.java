package com.cg.asset.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.cg.asset.dto.Asset;
import com.cg.asset.exception.AssetException;
import com.cg.asset.service.AssetServiceImpl;
import com.cg.asset.service.IAssetService;

public class AssetUI {
	
	

	public static void main(String args[]) throws AssetException
	{
		
		int choice;

		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		IAssetService service=new AssetServiceImpl();
		Asset bean=new Asset();
		System.out.println("************************************************");
		System.out.println("***********Asset Management System**************");
		System.out.println("************************************************");
		
		do
		{
		System.out.println("Login As\n 1.Manager  2.Admin  3.Exit");
		choice=sc.nextInt();
		
		
		switch(choice)
		{
		case 1:
			sc.nextLine();
			System.out.println("Enter User Name");
			String userName=sc.nextLine();
			System.out.println("Enter User Password");
			String password=sc.next();
		if(service.checkPassword(password)){
			bean.setUserName(userName);
			bean.setPassword(password);
			int n=service.login("USER",bean);
		if(n==1)
			{
		   System.out.println("Login Successfull!!!");
		  
		    	int ch;
				do{
		    		
		    	System.out.println("1: Asset Allocation Request  2.View Status of your AllocationId 3.Logout");
		    	System.out.println("********************************************************************");
		    	 ch = sc.nextInt();
		    	 
		    	 switch(ch)
		    	 {
		    	case 1:
		    		
		    	ArrayList<Asset> empList = new ArrayList<Asset>();	
		     	
		   	   
		   	    empList = service.getEmployees(userName);
		   	    System.out.println("You can raise request for following Employees :");
		    	 for(Asset id:empList)
				{
					System.out.println("Emp id :"+id.getEmpNo()+"   Emp Name:"+id.getEmployeeName());
				}
				
		   	    
		    	System.out.println("Enter Asset Name");
		    	String assetName=sc.next();
		    	System.out.println("Enter Quantity");
		    	int quantity=sc.nextInt();
		    	sc.nextLine();
		    	
		    	System.out.println("Enter Employee Id");
		    	int empId=sc.nextInt();
		    	bean.setAssetName(assetName);
		    	bean.setAllocatedQuantity(quantity);
		    	bean.setEmpNo(empId);
		    
		    	
		    	int allocationId=service.insertRequests(bean);
		    	if(allocationId==-1)
		    	{
		    		System.out.println("Access Denied");
		    	}
		    	else if(allocationId==-2)
		    	{
		    	System.out.println("Asset Unavailable");	    	
		    	}else{
		    		    		
		    	System.out.println("Request Raised For Allocation Request Number: "+allocationId);
		    	}break;
		    	
		    	 
		    	 
		 case 2:
			 ArrayList<Asset> list = new ArrayList<Asset>();
			 ArrayList<Integer> idList =new ArrayList<Integer>();
			 idList = service.getAllocationIds(userName);
		
			System.out.println("Allocation Ids are;");
			for(Integer id:idList)
			{
				System.out.println("Allocation Number :"+id);
			}
			
		    		System.out.println("Enter the allocation Id");
		    		int allocId=sc.nextInt();
		    	
		    	
		    		if(service.doesAllocationIdExist(allocId,userName))
		    		{
		    		list=service.viewRequestsByAllocationId(allocId);
		    		
		    		 for(Asset requests:list)
		    		 {
		    			 
		    			 System.out.println("---------------------------");
		    		     System.out.println("Manger Id :"+requests.getMgrId());
		    			 System.out.println("Allocation Id :"+requests.getAllocationId());
		    			 System.out.println("Asset Id :"+requests.getAssetId());
		    			 System.out.println("Employee No :"+requests.getEmpNo());
		    			 System.out.println("Asset Name :"+requests.getAssetName());
		    			 System.out.println("Asset Description :"+requests.getAssetDes());
		    			 System.out.println("Asset Quantity :"+requests.getQuantity());
		    			 System.out.println("Requested Quantity :"+requests.getAllocatedQuantity());
		    			 System.out.println("Status :"+requests.getStatus());
		    			System.out.println("---------------------------");
		    			
		    			 
		    		 }}else{
		    			 System.out.println("Allocation Id invalid");
		    		 }
		    
		    	break;
		    	
		    	
		    	
		    	 case 3:
		    		 System.out.println("Logged Out !!");
		    		
		    		 break;
		    		 
		    		 
		    	 default:
					 System.out.println("Invalid choice");
		    	 }
		    	 
		    	
		    }while(ch!=3);
			
			}
		    else
		    {
		    		 System.out.println("Invalid userName or Password");
		    	 
		    }
		    
		}
		
		else
		{
			System.out.println("Invalid Credentials !!!");
		}
			
		    break;
		    	
		 case 2:
		    		
			 sc.nextLine();
		 	System.out.println("Enter Admin Name");
		 	String userName1=sc.nextLine();
		 	System.out.println("Enter Admin Password");
		 	String password1=sc.next();
		 	if(service.checkPassword(password1)){
		 		bean.setUserName(userName1);
	 			bean.setPassword(password1);
	 			int n1=service.login("ADMIN",bean);
	 	if(n1==1)
	 			{
	 		System.out.println("Login Successfull!!!");
			    int choice1;
			    
	 			do{
	 			System.out.println("Admin Area:\n 1.Add Assets 2.Modify Assets 3.View Requests 4.Logout");
	 			choice1=sc.nextInt();
	 		
	 			switch(choice1)
	 			{
	 			
	 			case 1:
	 			sc.nextLine();
	 				System.out.println("Enter Asset Name");
	 				String assetName=sc.nextLine();
	 				System.out.println("Enter Asset Description");
	 				String assetDesc=sc.nextLine();
	 				System.out.println("Enter Quantity");
	 				int quantity=sc.nextInt();
	 				
	 			if(service.checkAssetQuantity(quantity)){
	 				bean.setAssetDes(assetDesc);
	 				
	 				bean.setQuantity(quantity);
	 				bean.setAssetName(assetName);
	 				
	 				int rows=service.addDetails(bean);
	 				if(rows==1)
	 				{
	 					System.out.println("Rows inserted");
	 				}
	 			}
	 			else{
	 				System.out.println("Quantity out of range");
	 			}
	 				break;
	 				
	 			case 2:
	 				sc.nextLine();
	 				int quantity1,n2,ch;
	 				System.out.println("Enter the Asset Name you want to Modify");
	 				String assetName1=sc.nextLine();
	 				int n=service.validateAssetName(assetName1);
	 				if(n==1)
	 				{
	 				System.out.println("Enter want you want to modify");
	 				System.out.println(" 1:Asset Description   2:Asset Quantity");
	 				ch=sc.nextInt();
	 				switch(ch)
	 				{
	 				
	 				case 1:
	 					sc.nextLine();
	 					System.out.println("Enter the new asset description");
	 					String assetDesc1=sc.nextLine();
	 					n2=service.modifyDesc(assetDesc1,assetName1);
	 					if(n2!=0)
	 						System.out.println("Asset Updated");
	 					break;
	 					
	 				case 2:	
	 				System.out.println("Enter the Quantity   1.Add  2.Subtract");
	 				int ch1=sc.nextInt();
	 				int flag=0;
	 				switch(ch1)
	 				{
	 				case 1:
	 					flag=1;
	 					System.out.println("Enter the Quantity to be Added");
	 					 quantity=sc.nextInt();
	 				
	 				 n2=service.modifyAssetQuantity(assetName1,quantity,flag);
	 				if(n2!=0)
	 				{
	 				System.out.println("Asset Updated");
	 				}
	 				break;
	 				
	 				case 2:
	 					flag=2;
	 					System.out.println("Enter the quantity to be subtracted");
	 					quantity1=sc.nextInt();
	 					n2=service.modifyAssetQuantity(assetName1,quantity1,flag);
	 					if(n2!=0)
		 				{
		 				System.out.println("Asset Updated");
		 				}
	 					break;
	 				}
	 				break;
	 				}
	 				}
	 				else
	 					System.out.println("Asset not in the list");
	 				break;
	 				 
	 			case 3:
	 				ArrayList<Asset> list=new ArrayList<Asset>();
	 				list=service.retrieveDetails();
	 				for(Asset l:list)
	 				{
	 					 System.out.println("Request No :"+l.getAllocationId());
	 					 System.out.println("---------------------------");
	 					 System.out.println("Allocation Id :"+l.getAllocationId());
		    		     System.out.println("Manger Id :"+l.getMgrId());
		    		     System.out.println("Employee No :"+l.getEmpNo());
		    		     System.out.println("Employee Name :"+l.getEmployeeName());
		    			 System.out.println("Department :"+l.getDepartmentName());
		    			 System.out.println("Asset Id :"+l.getAssetId());
		    			 System.out.println("Asset Name :"+l.getAssetName());
		    			 System.out.println("Asset Description :"+l.getAssetDes());
		    			 System.out.println("Asset Quantity :"+l.getQuantity());
		    			 System.out.println("Requested Quantity :"+l.getAllocatedQuantity());
		    			 System.out.println("Status"+l.getStatus());
		    		
		    			 
		    			System.out.println("---------------------------");
		    		 }
	 				
	 				System.out.println("1.Approve/Reject Request \n 2.Logout\n");
	 				
	 				int adminChoice=sc.nextInt();
	 				switch(adminChoice)
	 				{
	 				
	 				case 1:
	 					System.out.println("Enter the Allocation Id");
	 					int allocId=sc.nextInt();
	 				
	 						int checkQuantity=service.checkAsset(allocId);
	 						if(checkQuantity>0)
	 						{
	 							int allocated=service.approveRequest(allocId);
	 							
	 							if(allocated==1)
	 							{
	 								String allocStatus="Approved";
	 								System.out.println(allocStatus);
	 								service.setStatus(allocId,allocStatus);
	 								
	 								
	 								
	 							}}else{
	 								System.out.println("Asset Quantity not sufficient");
		 							String rejectStatus="Rejected";
			 						service.setStatus(allocId,rejectStatus);
			 						System.out.println(rejectStatus);
	 							}
	 				
	 				break;
	 			case 2:
	 				System.out.println("Enter Your Choice Here");
	 					
	 				break;
	 			default:
	 				System.out.println("Invalid Choice !!!");
	 				break;
	 				
	 			}
	 				break;
	 			
	 			case 4:
	 				System.out.println("Logged Out!!!");
	 				
	 				
	 				break;
	 			default:
					 System.out.println("Invalid choice");
	 		
	 			}
	 			
	 			}while(choice1!=4);
	    		 
	    		
	    	 }
	 			
	 		else
	 			{
	 				 System.out.println("Invalid userName or Password");
	 			}
		 	}
		 	else
		 		{
		 			System.out.println("Invalid Password");
		 		}
		 	break;
		 case 3: 
			 System.out.println("Thank You !!");
			 break;
			 
			 default:
				 System.out.println("Invalid choice");
		}
		}
		while(choice !=3);
	
	}
}
