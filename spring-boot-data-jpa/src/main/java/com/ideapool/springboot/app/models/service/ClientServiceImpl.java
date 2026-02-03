package com.ideapool.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideapool.springboot.app.models.dao.IClientDao;
import com.ideapool.springboot.app.models.dao.IInvoiceDao;
import com.ideapool.springboot.app.models.dao.IProductDao;
import com.ideapool.springboot.app.models.entity.Client;
import com.ideapool.springboot.app.models.entity.Invoice;
import com.ideapool.springboot.app.models.entity.Product;

@Service("clientServiceJPA")
public class ClientServiceImpl implements IClientService {
	
	@Autowired
	private IClientDao clientDao;
	
	@Autowired
	private IProductDao productDao;

	@Autowired
	private IInvoiceDao invoiceDao;
	@Override
	@Transactional(readOnly=true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Client> findAll(Pageable pegeable) {
		return clientDao.findAll(pegeable);
	}

	@Override
	@Transactional(readOnly=true)
	public Client findOne(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> finProductByName(String term) {
		return productDao.findByNameLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveInvoice(Invoice invoice) {
		invoiceDao.save(invoice);		
	}

	@Override
	@Transactional(readOnly=true)
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice findInvoiceById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteInvoice(Long id) {
		invoiceDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Invoice findInvoiceByIdWithItems(Long id) {
	  return invoiceDao.fetchByIdWithItems(id);
	}
	
}
