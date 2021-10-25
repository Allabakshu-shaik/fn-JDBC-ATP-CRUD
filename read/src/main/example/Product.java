package io.fnproject.example;

import java.sql.ResultSet;
import java.util.Objects;

public class Product {
    
    private String product_name;
    private Integer  product_count;
    
    public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Integer getProduct_count() {
		return product_count;
	}

	public void setProduct_count(Integer product_count) {
		this.product_count = product_count;
	}

    public Product(String product_name, Integer product_count) {
        this.product_name = product_name;
        this.product_count = product_count;
    }

    @Override
    public String toString() {
        return "CreateProductInfo{" + "product_name=" + product_name + ", product_count=" + product_count + '}';
    }

    
   
    
}
