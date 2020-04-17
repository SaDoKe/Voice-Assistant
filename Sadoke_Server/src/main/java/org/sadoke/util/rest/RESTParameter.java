package org.sadoke.util.rest;

public interface RESTParameter {

	String getKey();
	Object getValue();
	RESTParameter getChild();
}
