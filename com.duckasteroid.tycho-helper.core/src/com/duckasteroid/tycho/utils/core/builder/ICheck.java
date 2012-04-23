package com.duckasteroid.tycho.utils.core.builder;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.pde.core.plugin.IPluginModelBase;

/**
 * A base class for checks that are performed by the builder. Subclasses determine the nature of the check 
 * and the content of any problems detected.
 * @author chris
 */
public interface ICheck {
	/**
	 * Perform the checks required on the model. Creating markers as necessary
	 * 
	 * @param pdeModel
	 *            The PDE model for the project
	 * @param mavenModel
	 *            The maven model for the project
	 * @param results
	 * 			  An initially empty collection for the problems this check finds
	 * @param monitor
	 *            A progress monitor
	 */
	public abstract void doCheck(IPluginModelBase pdeModel, IMavenProjectFacade mavenModel, Collection<Problem> results, IProgressMonitor monitor);

	/**
	 * A name to appear in any UI for the check
	 * 
	 * @return The check instances display name
	 */
	public String getDisplayName();

	
}
