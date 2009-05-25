package org.gregoire.demo;

import java.io.Serializable;

public class MyBean implements Serializable {

	private static final long serialVersionUID = 42L;

	private String parameter;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
