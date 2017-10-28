package com.cg.asset.service;

import java.util.ArrayList;

import com.cg.asset.dao.AssetDaoImpl;
import com.cg.asset.dto.Asset;
import com.cg.asset.exception.AssetException;

public interface IAssetService {

	
	public int login(String string,Asset b) throws AssetException;
	public int addDetails(Asset b) throws AssetException;
	public int modifyAssetQuantity(String assetName,int quantity,int flag) throws AssetException;

	public int insertRequests(Asset bean) throws AssetException;
	public int modifyDesc(String assetDesc,String assetName) throws AssetException ;
	public int checkAsset(int assetId) throws AssetException;
	public int approveRequest(int allocationId) throws AssetException;
	public ArrayList<Asset> retrieveDetails() throws AssetException;
	public ArrayList<Asset> viewRequestsByAllocationId(int allocId) throws AssetException;
	boolean checkPassword(String pass);
	boolean checkAssetQuantity(int quantity);
	public int validateAssetName(String assetName) throws AssetException;
	public int setStatus(int allocId, String rejectStatus) throws AssetException;
	
	public boolean doesAllocationIdExist(int allocId, String userName) throws AssetException;
	public ArrayList<Integer> getAllocationIds(String userName) throws AssetException;
	public ArrayList<Asset> getEmployees(String userName) throws AssetException;
}
