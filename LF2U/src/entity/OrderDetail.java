package entity;

public class OrderDetail {
	private int fspid;
	private double amount;
	private String amount_asStr;
	private String name;
	private double price;
	private String price_asStr;
	private double line_item_total;
	StoreProduct sp;
	ProductCatalog product;
	public OrderDetail(int fspid,double amount, StoreProduct sp)
	{
		this.fspid = fspid;
		this.amount = amount;
		this.sp = sp;
		this.price = sp.getPrice();
	}
	
	public String getAmountAsStr(){
		amount_asStr = amount + sp.getProductUnit();
		return amount_asStr;
	}
	public String getPriceAsStr(){
		price_asStr = price + " per " + sp.getPrice();
		return price_asStr;
	}
	
	public double getLineItemTotal(){
		return amount*price;
		
	}
}
