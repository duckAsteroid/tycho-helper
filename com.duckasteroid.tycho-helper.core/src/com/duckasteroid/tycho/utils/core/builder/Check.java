package com.duckasteroid.tycho.utils.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.pde.core.plugin.IPluginModelBase;

import com.duckasteroid.tycho.utils.core.IConstants;

public abstract class Check {
	private String displayName;

	/**
	 * Perform the checks required on the model. Creating markers as necessary
	 * 
	 * @param pdeModel
	 *            The PDE model for the project
	 * @param mavenModel
	 *            The maven model for the project
	 * @param monitor
	 *            A progress monitor
	 */
	public abstract void doCheck(IPluginModelBase pdeModel, IMavenProjectFacade mavenModel, IProgressMonitor monitor);

	/**
	 * A name to appear in any UI for the check
	 * 
	 * @return The check instances display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Utility method to report a problem in a file as a marker
	 * @param msg The message to include in the marker
	 * @param file The file that has the problem
	 * @param charStart The first char of the problem text
	 * @param charEnd The last char of the problem text
	 * @param isError 
	 */
	protected void reportProblem(String msg, IFile file, int charStart, int charEnd, boolean isError) {
		try {
			IMarker marker = file.createMarker(IConstants.MARKER_ID);
			marker.setAttribute(IMarker.MESSAGE, msg);
			marker.setAttribute(IMarker.CHAR_START, charStart);
			marker.setAttribute(IMarker.CHAR_END, charEnd);
			marker.setAttribute(IMarker.SEVERITY, isError ? IMarker.SEVERITY_ERROR : IMarker.SEVERITY_WARNING);
			// marker.setAttribute(KEY, loc.key);
			// marker.setAttribute(VIOLATION, violation);
		} catch (CoreException e) {
			logError(e);
			return;
		}
	}

	private void logError(Throwable t) {
		// FIXME Use proper plugin logs!
		t.printStackTrace();
	}
}
