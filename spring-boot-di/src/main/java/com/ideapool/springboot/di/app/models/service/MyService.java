package com.ideapool.springboot.di.app.models.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Primary
//@Component("MySimpleService")
public class MyService implements IService {

	@Override
	public String operation() {
		return "Executing any simple process!";
	}
}
