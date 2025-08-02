package com.ideapool.springboot.di.app.models.service;

import org.springframework.stereotype.Component;

//@Component("MyComplexService")
public class MyComplexService implements IService {

	@Override
	public String operation() {
		return "Executing any complex process!";
	}
}
