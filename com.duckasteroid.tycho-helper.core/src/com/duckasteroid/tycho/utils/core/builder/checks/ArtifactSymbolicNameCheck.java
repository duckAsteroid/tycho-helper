package com.duckasteroid.tycho.utils.core.builder.checks;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.pde.core.plugin.IPluginModelBase;

import com.duckasteroid.tycho.utils.core.builder.Check;
/**
 * Checks if POM `artifactId` = Manifest `Bundle-SymbolicName`
 * @author chris
 */
public class ArtifactSymbolicNameCheck extends Check {

	@Override
	public void doCheck(IPluginModelBase pdeModel, IMavenProjectFacade mavenModel, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

}
