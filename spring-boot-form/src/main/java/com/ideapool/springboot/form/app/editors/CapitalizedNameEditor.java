package com.ideapool.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

public class CapitalizedNameEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text.toUpperCase().trim());
	}	
}
