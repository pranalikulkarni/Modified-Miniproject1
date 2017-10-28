package com.cg.asset.dao;

import java.util.ArrayList;

import com.cg.asset.dto.Asset;
import com.cg.asset.exception.AssetException;

public interface IAssetDao {
	
	public int login(String string,Asset b) throws AssetException;
	public int addDetails(Asset b) throws AssetException;
	
	public int insertRequests(Asset bean) throws AssetException;
	
	public int checkAsset(int assetId) throws AssetException;
	public int approveRequest(int allocationId) throws AssetException;
	public ArrayList<Asset> retrieveDetails() throws AssetException;
	public int setStatus(int allocId,String status) throws AssetException;
	public ArrayList<Asset> viewRequestsByAllocationId(int allocId) throws AssetException;
	int modifyDesc(String assetDesc, String assetName) throws AssetException;
	public int modifyAssetQuantity(String assetName,int quantity,int flag) throws AssetException;
	int validateAssetName(String assetName) throws AssetException;
	public int retrieveEmpNoOfMgr(String userName) throws AssetException;
	public boolean doesAllocationIdExist(int allocId, String userName) throws AssetException;
	public ArrayList<Integer> getAllocationIds(String userName) throws AssetException;
	public ArrayList<Asset> getEmployees(String userName) throws AssetException;
	
	

}
