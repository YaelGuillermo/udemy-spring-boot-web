package com.ideapool.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ideapool.springboot.form.app.models.domain.Country;

@Service
public class CountryServiceImpl implements CountryService {
	
	private List<Country> list;
	
	public CountryServiceImpl() {
		this.list = Arrays.asList(
				new Country(1, "SP", "Spain"), 
				new Country(2, "ME", "Mexico"), 
				new Country(3, "GE", "Germany"), 
				new Country(4, "BR", "Brazil") 
		);
	}

	@Override
	public List<Country> list() {
		return list;
	}

	@Override
	public Country getById(Integer id) {
		Country result = null;
		for (Country country: this.list) {
			if (id == country.getId()) {
				result = country;
				break;
			}
		}
		return result;
	}

}
