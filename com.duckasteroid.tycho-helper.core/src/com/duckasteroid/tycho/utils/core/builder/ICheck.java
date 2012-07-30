package com.duckasteroid.tycho.utils.core.builder;

import org.eclipse.core.runtime.IProgressMonitor;


/**
 * A base class for checks that are performed by the builder. Subclasses determine the nature of the check 
 * and the content of any problems detected.
 * @author chris
 */
public interface ICheck {
	/**
	 * Perform the checks required on the model. Creating markers as necessary
	 * 
	 * @param context A context object for the check to operate in
	 */
	public abstract void doCheck(CheckContext context, IProgressMonitor monitor);

	/**
	 * A name to appear in any UI for the check
	 * 
	 * @return The check instances display name
	 */
	public String getDisplayName();

	
}
