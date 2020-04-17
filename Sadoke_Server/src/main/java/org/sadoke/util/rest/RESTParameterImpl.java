package org.sadoke.util.rest;

public class RESTParameterImpl implements RESTParameter {

	private final String key;
	private final Object value;
	private final RESTParameter child;

	public RESTParameterImpl(String key, Object value) {
		this.key = key;
		this.value = value;
		child = null;
	}
	public RESTParameterImpl(String key, Object value, RESTParameter child) {
		this.key = key;
		this.value = value;
		this.child = child;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Object getValue() {
		return value;
	}
	@Override
	public RESTParameter getChild() {

		return child;
	}

}
