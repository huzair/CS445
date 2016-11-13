package org.huzair.use_cases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.huzair.boundary_interfaces.FarmerBI;
import org.huzair.entities.Farmer;
import org.huzair.entities.Store;
import org.huzair.entities.StoreProduct;
import org.huzair.report.FarmerReportType;

public class FarmerManager implements FarmerBI {
	
	static AtomicInteger atomicInteger = new AtomicInteger();
	static AtomicInteger atomicInteger_products = new AtomicInteger();
	private static ArrayList<Farmer> farmers = new ArrayList<Farmer>();

	//Create farmer account;
	@Override
	public int createAccount(Farmer f) {
		Farmer farm = f;
		f.setFid(atomicInteger.incrementAndGet());
		farmers.add(farm);
		int fid_as_int = farm.getFid();
		farm.setStore();
		return fid_as_int;
	}
	
	//Update farmer account
	@Override
	public void updateAccount(int fid, Farmer f) {
		Farmer farm = getFarmerById(fid);
		if(farm!=null){
		farm.setFarm(f);
		}
	}
	
	//Get farmer account by fid
	public Farmer getFarmerById(int fid){
		Iterator<Farmer> f = farmers.listIterator();
        while(f.hasNext()) {
            Farmer farm = f.next();
            if(farm.matchesId(fid))
            	return farm;
        }
		return null;
	}
	
	//Get farmer by zipcode
	public Farmer getFarmerZip(Farmer farm, String zip){
		ArrayList<String> farmer_zip = farm.getDeliversTo();
		if(farmer_zip.contains(zip))
			return farm;
		return null;
	}
	
	//View account by fid
	@Override
	public Farmer viewAccount(int fid) {
		Farmer farm = getFarmerById(fid);
		if(farm!=null)
			return farm;
		return null;
	}

	//View all farmers in the zip
	@Override
	public ArrayList<Farmer> viewFarmers(String zip) {
		ArrayList<Farmer> matchzip = new ArrayList<Farmer>();
		Iterator<Farmer> f = farmers.listIterator();
        while(f.hasNext()) {
            Farmer farm = f.next();
            if(getFarmerZip(farm, zip)!=null)
            	matchzip.add(farm);
        }
        return matchzip;
	}

	//View store for farmer by fid
	@Override
	public ArrayList<StoreProduct> viewStore(int fid) {
		ArrayList<StoreProduct> allproducts = new ArrayList<StoreProduct>();
		Farmer farm = getFarmerById(fid);
		if(farm==null)
			return null;
		Store store = farm.getStore();
		allproducts = store.getAllStoreProducts();
        return allproducts;
	}

	//Add product to store
	@Override
	public int addProduct(int fid, StoreProduct sproducts) {
		Farmer farm = getFarmerById(fid);
		Store store = farm.getStore();
		StoreProduct s = sproducts;
		s.setFspid(atomicInteger_products.incrementAndGet());
		store.setStoreProducts(s);
		return s.getFspid();
	}

	//Modify store product
	@Override
	public void modifyProduct(int fid, int fspid, StoreProduct s) {
		StoreProduct sproduct = viewProduct(fid,fspid);
		if(sproduct!=null)
			sproduct.setProduct(s);
	}

	//View delivery charge
	@Override
	public double ViewDelivery(int fid) {
		Farmer farm = getFarmerById(fid);
		return farm.getDeliveryCharge();
	}

	//Update delivery charge
	@Override
	public void UpdateDelivery(int fid,double dc) {
		Farmer farm = getFarmerById(fid);
		farm.setDeliveryCharge(dc);
	}

	@Override
	public StoreProduct viewProduct(int fid, int fspid) {
		ArrayList<StoreProduct> sp = viewStore(fid);
		Iterator<StoreProduct> s = sp.listIterator();
        while(s.hasNext()) {
            StoreProduct sproducts = s.next();
            if(sproducts.matchesId(fid))
            	return sproducts;
        }
		return null;
	}

	@Override
	public ArrayList<FarmerReportType> allReportTypes() {
		FarmerReportType reports = new FarmerReportType();
		ArrayList<FarmerReportType> allReportTypes = reports.getAllTypes();
		return allReportTypes;
	}

	public ArrayList<Farmer> getAllFarmers() {
		return farmers;
	}

	
}
