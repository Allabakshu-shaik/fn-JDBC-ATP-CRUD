package io.fnproject.example;

public class CreateProductInfo {
    
    private String product_NAME;
    private Integer product_COUNT;
    

    public String getProduct_NAME() {
		return product_NAME;
	}

	public void setProduct_NAME(String product_NAME) {
		this.product_NAME = product_NAME;
	}

	public void setproduct_COUNT(Integer product_COUNT) {
		this.product_COUNT = product_COUNT;
	}

	public CreateProductInfo() {
    }

    public CreateProductInfo(String product_name, Integer product_count) {
        this.product_NAME = product_name;
        this.product_COUNT = product_count;
    }

  
    @Override
    public String toString() {
        return "CreateProductInfo{" + "prodcut_name" + product_NAME + ", product_count=" + product_COUNT +'}';
    }

	public int getProduct_COUNT() {
		// TODO Auto-generated method stub
		return product_COUNT;
	}
   
    
}
