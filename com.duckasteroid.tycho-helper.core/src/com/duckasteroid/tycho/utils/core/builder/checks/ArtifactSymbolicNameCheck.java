package com.duckasteroid.tycho.utils.core.builder.checks;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.pde.core.plugin.IPluginModelBase;

import com.duckasteroid.tycho.utils.core.builder.ICheck;
import com.duckasteroid.tycho.utils.core.builder.Level;
import com.duckasteroid.tycho.utils.core.builder.Problem;
import com.duckasteroid.tycho.utils.core.builder.Problem.Location;
/**
 * Checks if POM `artifactId` = Manifest `Bundle-SymbolicName`
 * @author chris
 */
public class ArtifactSymbolicNameCheck implements ICheck {

	@Override
	public void doCheck(IPluginModelBase pdeModel, IMavenProjectFacade mavenModel, Collection<Problem> results, IProgressMonitor monitor) {
		String pdeName = pdeModel.getBundleDescription().getSymbolicName();
		String mavenName = "";
		
		if (pdeName != mavenName) {
			Problem problem = new Problem(getClass(), Level.ERROR, "POM Artifact ID ["+mavenName+"] does not match Bundle-SymbolicName ["+pdeName+"]");
			problem.addLocation(getPdeSymbolicNameLocation(pdeModel, pdeName));
			problem.addLocation(getMavenArtifactIdLocation(mavenModel, mavenName));
			results.add(problem);
		}
	}

	private Location getPdeSymbolicNameLocation(IPluginModelBase pdeModel, String pdeName) {
		//TODO return the location of the symbolic name in the PDE manifest
		IResource resource = pdeModel.getUnderlyingResource();
		
		return null;
	}


	private Location getMavenArtifactIdLocation(IMavenProjectFacade mavenModel, String mavenName) {
		// TODO return the location of the artifact ID in the POM
		return null;
	}

	@Override
	public String getDisplayName() {
		return "POM Artifact ID = Bundle-SymbolicName check";
	}

}
