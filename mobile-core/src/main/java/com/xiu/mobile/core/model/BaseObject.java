package com.xiu.mobile.core.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Base class for Model objects. Child objects should implement toString().
 * 
 * @author <a href="mailto:david.lin@xiu.com">David Lin</a>
 */

@SuppressWarnings("serial")
public abstract class BaseObject implements Serializable {

    /**
     * Returns a multi-line String with key=value pairs.
     * 
     * @return a String representation of this class.
     */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
