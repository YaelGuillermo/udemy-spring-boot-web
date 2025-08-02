package com.ideapool.springboot.di.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.ideapool.springboot.di.app.models.domain.InvoiceItem;
import com.ideapool.springboot.di.app.models.domain.Product;
import com.ideapool.springboot.di.app.models.service.IService;
import com.ideapool.springboot.di.app.models.service.MyComplexService;
import com.ideapool.springboot.di.app.models.service.MyService;

@Configuration
public class AppConfig {

	@Bean("mySimpleService")
	public IService registerMyService() {
		return new MyService();
	}
	
	@Primary
	@Bean("myComplexService")
	public IService registerMyComplexService() {
		return new MyComplexService();
	}
	
	@Bean("invoiceItem")
	public List<InvoiceItem> registerItems() {
		Product product1 = new Product("Chair", 100);
		Product product2 = new Product("TV", 500);
		Product product3 = new Product("Table", 200);

		InvoiceItem line1 = new InvoiceItem(product1, 3);
		InvoiceItem line2 = new InvoiceItem(product2, 1);
		InvoiceItem line3 = new InvoiceItem(product3, 2);
		
		return Arrays.asList(line1, line2, line3);
	}
	
	@Primary
	@Bean("invoiceItemOffice")
	public List<InvoiceItem> registerItemsOffice() {
		Product product1 = new Product("Printer", 300);
		Product product2 = new Product("Desktop", 400);
		Product product3 = new Product("Notebook", 600);
		Product product4 = new Product("Laptop", 800);

		InvoiceItem line1 = new InvoiceItem(product1, 2);
		InvoiceItem line2 = new InvoiceItem(product2, 3);
		InvoiceItem line3 = new InvoiceItem(product3, 3);
		InvoiceItem line4 = new InvoiceItem(product4, 2);
		
		return Arrays.asList(line1, line2, line3, line4);
	}
}
