package com.cg.asset.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.asset.dbutil.DbUtil;
import com.cg.asset.dto.Asset;
import com.cg.asset.exception.AssetException;



public  class AssetDaoImpl implements IAssetDao {
	Connection conn = null;
	
	Logger logger = Logger.getRootLogger();
	public AssetDaoImpl()
	{
		PropertyConfigurator.configure("log4j.properties");
	}
	
	/**********************************************************************************************
	 *Function Name     :login(String string,Asset b)
	 *Input Parameters  :string, Asset b
	 *Return Type       :int
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To Validate login Credentials    
	 ********************************************************************************************/
	public int login(String string,Asset b) throws AssetException {
		int a=0;
		try
		{
			conn=DbUtil.getConnections();
			String sql="select * from User_Master";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				String usertype=rs.getString(4);
				String name=rs.getString(2);
				String pass=rs.getString(3);
				
				if(usertype.equalsIgnoreCase(string) && name.equals(b.getUserName())&& pass.equals(b.getPassword()))
				{
					a=1;
					logger.info("Valid user Found");
				}
				
			}
		}
		catch (IOException e)
		{
			logger.error("IO Exception Occured");
			throw new AssetException("INVALID INPUT");
		
		} 
		catch (SQLException e) 
		{
			logger.error("SQL Exception Occured");
			throw new AssetException("Could not retrieve user type");
			
		}
		return a;
	}
	

	/**********************************************************************************************
	 *Function Name     :addDetails(Asset b)
	 *Input Parameters  :Asset b
	 *Return Type       :int
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To Add new Asset details in Asset table. 
	 ********************************************************************************************/
	public int addDetails(Asset b) throws AssetException {

		int n=0;
		try
		{
			int allocQuantity=0;
			conn=DbUtil.getConnections();
			String sql="insert into Asset values(seq_asset_id.nextval,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			
			ps.setString(1, b.getAssetName());
			ps.setString(2,b.getAssetDes() );
			ps.setInt(3, b.getQuantity());
			
			
			 n=ps.executeUpdate();
			
			
		} 
		catch (IOException e) 
		{
			throw new AssetException("INVALID INPUT");
			
		} 
		catch (SQLException e) 
		{
			throw new AssetException("Could not add asset");
		}
		
		return n;
	}
	
	/**********************************************************************************************
	 *Function Name     :validateAssetName(String assetName)
	 *Input Parameters  :string, Asset b
	 *Return Type       :int
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To Validate asset name.    
	 ********************************************************************************************/
	
	@Override
	public int validateAssetName(String assetName) throws AssetException {
		
		ArrayList<String> list=new ArrayList<String>();
		int c=0;
		try
		{
			conn=DbUtil.getConnections();
			String sql1="select assetName from asset";
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs=ps1.executeQuery();
			while(rs.next())
			{
				list.add(rs.getString(1));
			}
			if(list.contains(assetName))
				c=1;
			else
				c=0;
		
	    }
		catch (IOException e)
		{
			throw new AssetException("INVALID INPUT");
			
		}
		catch (SQLException e) 
		{
			throw new AssetException("Could not modify");
		}
		
		return c;
	}
	
	/**********************************************************************************************
	 *Function Name     :modifyDesc(String assetDesc,String assetName)
	 *Input Parameters  :string
	 *Return Type       :int
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To Modify asset description.    
	 ********************************************************************************************/
	
	@Override
	public int modifyDesc(String assetDesc,String assetName) throws AssetException {
	
		int n=0;
		try
		{
			conn=DbUtil.getConnections();
			String sql="update asset set assetDes=? where assetName=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, assetDesc);
			ps.setString(2, assetName);
			 n=ps.executeUpdate();
		} 
		catch (IOException e) 
		{
			throw new AssetException("INVALID INPUT");
		}
		catch (SQLException e)
		{
			throw new AssetException("Could not modify");
		}
		return n;
	}

	/**********************************************************************************************
	 *Function Name     :modifyAssetQuantity(String assetName,int quantity,int flag)
	 *Input Parameters  :string,int
	 *Return Type       :int
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To Modify asset Quantity.    
	 ********************************************************************************************/
	
	
	public int modifyAssetQuantity(String assetName,int quantity,int flag) throws AssetException {
		
		
			int n=0;
				if(flag==1)
				{
				
				try {
					conn=DbUtil.getConnections();
					String sql="update Asset set Quantity=Quantity+? where AssetName=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setInt(1, quantity);
					ps.setString(2, assetName);
					 n=ps.executeUpdate();
				}
				catch (IOException e)
				{
					throw new AssetException("INVALID INPUT");
					
				}
				catch (SQLException e) 
				{
					throw new AssetException("Could not modify");
				}
				
				
			}
				
				if(flag==2)
				{
					try {
						conn=DbUtil.getConnections();
						String sql="update Asset set Quantity=Quantity-? where AssetName=?";
						PreparedStatement ps=conn.prepareStatement(sql);
						ps.setInt(1, quantity);
						ps.setString(2, assetName);
						 n=ps.executeUpdate();
					} 
					catch (IOException e)
					{
						throw new AssetException("INVALID INPUT");
						
					} 
					catch (SQLException e) 
					{
						throw new AssetException("Could not modify");
					}
				}
				return n;
	
	}
	
	public boolean doesAllocationIdExist(int allocId,String userName) throws AssetException
	{
		try {
			ArrayList<Integer> list=new ArrayList<Integer>();
			conn=DbUtil.getConnections();
			int mgr=retrieveEmpNoOfMgr(userName);
			String sql="Select allocationId from asset_allocation where MgrId=?";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1,mgr);
			ResultSet rs= st.executeQuery();
			while(rs.next())
			{
				list.add(rs.getInt(1));
			}
			if(list.contains(allocId))
			{
				logger.info("Allocation Id valid for manager ");
				return true;
			}
			
		}catch( SQLException | IOException e)
		{
			logger.error("Exception Occured");
			throw new AssetException("Could not retrieve");
			
		}
		
		return false;
	}
	
	
	
	
	
	/**********************************************************************************************
	 *Function Name     :viewRequestsByManager(int mgrId)
	 *Input Parameters  :int
	 *Return Type       :ArrayList
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To Display all request by manager.  
	 ********************************************************************************************/
	
	public ArrayList<Asset> viewRequestsByAllocationId(int allocId) throws AssetException
	{

		ArrayList<Asset> list= new ArrayList<Asset>();
		
		try{
			conn=DbUtil.getConnections();
		String sql = "SELECT * FROM Asset s JOIN Asset_Allocation l ON s.Assetid = l.Assetid where allocationId=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, allocId);
		
      
		ResultSet rs = pst.executeQuery();
		
		
	
		
		while(rs.next())
		{
			
			
			int assetId = rs.getInt(1);
			String assetName = rs.getString(2);
			String assetDes = rs.getString(3);
			int quantity = rs.getInt(4);
		
			int allocationId= rs.getInt(5);
			int empNo  = rs.getInt(7);
		
			String status=rs.getString(10);
			int managerId=rs.getInt(11);
			int quantityDemanded=rs.getInt(12);
		
			 
			list.add(new Asset(allocationId,assetId,empNo,assetName,assetDes,quantity,quantityDemanded,status,managerId));
			logger.info("Request Viewed Successfully");
			
		}
		
		}
		catch( SQLException | IOException e)
		{
			logger.error("Exception Occured");
			throw new AssetException("Could not retrieve");
			
		}
		
		
		
		return list ;
	}
	

	
	
	/**********************************************************************************************
	 *Function Name     :insertRequests(Asset bean)
	 *Input Parameters  :Asset bean
	 *Return Type       :int
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To insert Request in Asset_allocation Table after verifying manager.
	 ********************************************************************************************/
	
	public int insertRequests(Asset bean) throws AssetException
	{
	
       int row=0;
       
       int allocationId=-1;
		try 
		{
			
			conn=DbUtil.getConnections();
			
			String sql2="Select quantity From Asset where AssetName=?";
			PreparedStatement st2=conn.prepareStatement(sql2);
			st2.setString(1, bean.getAssetName());
			ResultSet rs2=st2.executeQuery();
			rs2.next();
			int quantity=rs2.getInt(1);
			System.out.println(quantity);
			if(quantity>0)
			{
			String sql1="Select assetId FROM Asset WHERE AssetName = ? ";
			PreparedStatement str1=conn.prepareStatement(sql1);
			str1.setString(1,bean.getAssetName());
	        
			ResultSet rs1=str1.executeQuery();
			while(rs1.next())
			{
				
				bean.setAssetId(rs1.getInt(1));
				
			}	
			String q="SELECT mgr FROM EMPLOYEE where empNo=?";
			PreparedStatement empStr1=conn.prepareStatement(q);		
			empStr1.setInt(1,bean.getEmpNo());
			ResultSet rsc=empStr1.executeQuery();
			rsc.next();
			int mgr=rsc.getInt(1);	

			System.out.println(bean.getUserName());
			int mgrId= retrieveEmpNoOfMgr(bean.getUserName()); 
			if(mgr==mgrId)
			{
				bean.setMgrId(mgr);
		    String status="Pending";

			String query="insert into asset_allocation values(seq_allocId.nextval,?,?,NULL,NULL,?,?,?)";
			
			PreparedStatement str=conn.prepareStatement(query);
			
			str.setInt(1,bean.getAssetId());
			
			str.setInt(2,bean.getEmpNo());
			str.setString(3,status);
			str.setInt(4,bean.getMgrId());
			str.setInt(5,bean.getAllocatedQuantity());
			
		
			
			row= str.executeUpdate();
	
			
	
			logger.info("Request Inserted Successfully");
			
			
			if(row==1)
			{
			String sql="select seq_allocId.currval from dual";
			Statement str2=conn.createStatement();	
			ResultSet rs=str2.executeQuery(sql);
			rs.next();
			
			allocationId=rs.getInt(1);
			}
			}
			else{
				return -1;
			}
			}
			else{
			return -2;
			}
		
		}catch(IOException | SQLException e)
		{
			logger.error("IO Exception Occured");
			throw new AssetException("Could not retrieve");
			
		}	
		
		return allocationId;
			
	}
	
	/**********************************************************************************************
	 *Function Name     :checkAsset(int allocId)
	 *Input Parameters  :int allocId
	 *Return Type       :int
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To check if allocatedQuantity is available.
	 ********************************************************************************************/
	
	

		    public int checkAsset(int allocId) throws AssetException
		    {
		    int checkQuantity;
		    try{
			conn=DbUtil.getConnections();
			
			String q="select quantity-requestedQuantity from asset a,asset_allocation  aa where a.assetid=aa.assetid and allocationId=?";
			PreparedStatement psq=conn.prepareStatement(q);
			psq.setInt(1, allocId);
			ResultSet rs1=psq.executeQuery();
			rs1.next();
			checkQuantity=rs1.getInt(1);
			logger.info("Asset Checked Successfully");
			
		    }catch(IOException | SQLException e)
			{
		    	logger.error("Exception Occured");
				throw new AssetException("Could not update");
				
			}
		    return checkQuantity;
		    }
		    /****************************************************************************************************
			 *Function Name     :approveRequest(int allocationId)
			 *Input Parameters  :int
			 *Return Type       :int
			 *Throws            :AssetException
			 *Author            :Team5
			 *Creation Date     :13/10/2017
			 *Description       :To set allocationDate and ReleaseDate and update quantity and allocatedQuantity    
			 ****************************************************************************************************/
			public int approveRequest(int allocationId) throws AssetException
			{
				int row=0;
				int result = 0;
				try{
				conn=DbUtil.getConnections();
			
				String q="Select status from asset_allocation where allocationId=?";
				PreparedStatement ps1=conn.prepareStatement(q);
				ps1.setInt(1,allocationId);
				ResultSet rs1=ps1.executeQuery();
				rs1.next();
				String statusRec=rs1.getString(1);
				
				String statusReq="Pending";
				
				if(statusRec.equals(statusReq))
				{
				
				String query="update asset_allocation set Allocation_date=sysdate ,release_date=sysdate+10 where allocationId=?";
				PreparedStatement ps=conn.prepareStatement(query);
				ps.setInt(1,allocationId);
				row=ps.executeUpdate();
				logger.info("Request Approved Successfully");
				if(row == 1)
				{
				
					String sql1 = "select requestedQuantity from asset_allocation where allocationid = ?"; 
					PreparedStatement pst1=conn.prepareStatement(sql1);
					pst1.setInt(1, allocationId);
					ResultSet rs= pst1.executeQuery();
					rs.next();
					int quantity=rs.getInt(1);
					
					String sql="update asset set quantity = quantity-?  where assetId=(SELECT assetid from asset_allocation where allocationid = ?)";
					PreparedStatement pst=conn.prepareStatement(sql);
					pst.setInt(1, quantity);
					pst.setInt(2, allocationId);
					result =pst.executeUpdate();
					
				}}else{
					System.out.println("The Requested Was Not Pending");
				}
				
				
			}catch(IOException | SQLException e)
			{
				logger.error(" Exception Occured");
				throw new AssetException("Could not update your request");
				
			}
				return result;
			}
			
		
		
			/**************************************************************************************************
			 *Function Name     :setStatus(int allocId,String status)
			 *Input Parameters  :string, int
			 *Return Type       :int
			 *Throws            :AssetException
			 *Author            :Team5
			 *Creation Date     :13/10/2017
			 *Description       :To set Status as Approved or Rejected for Requests in asset_allocation table
			 **************************************************************************************************/
			
		


			
	public int setStatus(int allocId,String status) throws AssetException
	{int result=0;
		try {
			
			Connection conn = DbUtil.getConnections();
			String updateStatus="update asset_allocation set status=? where AllocationId=?";
			PreparedStatement ps=conn.prepareStatement(updateStatus);
			ps.setString(1, status);
			ps.setInt(2,allocId);
			ps.executeUpdate();
			logger.info("Status Set Successfully");
			}
	
		catch(IOException | SQLException e) {
			throw new AssetException("Could not retrieve");
			
		}
		
		return result;
		
	}
	/**********************************************************************************************
	 *Function Name     :retrieveDetails()
	 *Input Parameters  :
	 *Return Type       :ArrayList<Asset> 
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :13/10/2017
	 *Description       :To retrieve details of all requests    
	 ********************************************************************************************/
	

	public ArrayList<Asset> retrieveDetails() throws AssetException {
			
			ArrayList<Asset> list = new ArrayList<Asset>();
			try {
				Connection conn = DbUtil.getConnections();
				
			
			String sql = "SELECT * FROM Asset s ,Asset_Allocation aa,Employee e,Department d where aa.assetId=s.assetId and e.dept_Id= d.dept_Id and aa.empno= e.empno and status=? order by allocationId";
			String statusPending="Pending";
			PreparedStatement pst = conn.prepareStatement(sql);
	        pst.setString(1,statusPending);
	      
			ResultSet rs = pst.executeQuery();
			
			
			
			while(rs.next())
			{

				int assetId = rs.getInt(1);
				String assetName = rs.getString(2);
				String assetDes = rs.getString(3);
				int quantity = rs.getInt(4);
				int allocationId= rs.getInt(5);
				int empNo  = rs.getInt(7);
				String status=rs.getString(10);
				int mgrId=rs.getInt(11);
				int quantityDemanded=rs.getInt(12);
				String empName=rs.getString(14);
				String deptName=rs.getString(20);

				list.add(new Asset(allocationId,assetId,empNo,assetName,assetDes,quantity,quantityDemanded,status,mgrId,empName,deptName));
				logger.info("Details Retrieved Successfully");
			}
			
			}
			
			catch (IOException | SQLException  e) {
				throw new AssetException("Could not retrieve");
				
			}
			
			return list;
		}
	
	/**********************************************************************************************
	 *Function Name     :doesIdExist(int retMgrId)
	 *Input Parameters  :int retMgrId
	 *Return Type       :boolean
	 *Throws            :AssetException
	 *Author            :Team5
	 *Creation Date     :21/10/2017
	 *Description       :To check if entered Id exists in asset_allocation table 
	 ********************************************************************************************/
	



	@Override
	public int retrieveEmpNoOfMgr(String userName) throws AssetException {
		int mgrId=-1;
		try{
			
		  String q1="SELECT empNo FROM USER_MASTER WHERE UserName=?";
			PreparedStatement empStr2=conn.prepareStatement(q1);		
			empStr2.setString(1,userName);
			ResultSet rsi=empStr2.executeQuery();
			rsi.next();
			 mgrId=rsi.getInt(1);
	}
	catch(SQLException e) {
		logger.error("IO Exception Occured");
		throw new AssetException("Could not retrieve");
		
	}
		
		return mgrId;

	}

	@Override
	public ArrayList<Integer> getAllocationIds(String username) throws AssetException {
		ArrayList<Integer> idList = new ArrayList<Integer>();
		
		try {
			conn = DbUtil.getConnections();
			int empNo = retrieveEmpNoOfMgr(username);
			String idQuery = "Select allocationid from asset_allocation where mgrid=?";
			PreparedStatement pst=conn.prepareStatement(idQuery);
			pst.setInt(1,empNo);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				idList.add(rs.getInt(1));
			}
			
			
			
		} 	
		catch (IOException | SQLException  e) {
			throw new AssetException("Could not retrieve");
			
		}
		
		return idList;
	}

	@Override
	public ArrayList<Asset> getEmployees(String userName) throws AssetException {
		ArrayList<Asset> empList = new ArrayList<Asset>();
		try 
		{
			
			conn = DbUtil.getConnections();
			String empQuery = "Select empno,eName From Employee where mgr=?";
			PreparedStatement ps = conn.prepareStatement(empQuery);
			int mgrId = retrieveEmpNoOfMgr(userName);
			ps.setInt(1,mgrId);
			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				empList.add(new Asset(res.getInt(1),res.getString(2)));
			}
		} 	
		catch (IOException | SQLException  e) {
			throw new AssetException("Could not retrieve");
			
		}
		
		
		
		return empList;
	}
}
