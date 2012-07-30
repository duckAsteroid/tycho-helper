package com.duckasteroid.tycho.utils.core.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;

public class Fix implements IMarkerResolution {

	private String label;

	public Fix(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void run(IMarker marker) {
		try {
			// FIXME Actually fix the problem
			marker.delete();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
