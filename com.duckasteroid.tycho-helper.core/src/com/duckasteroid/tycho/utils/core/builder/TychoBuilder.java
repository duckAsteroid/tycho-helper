package com.duckasteroid.tycho.utils.core.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.PluginModelManager;

import com.duckasteroid.tycho.utils.core.IConstants;

/**
 * This builder loads PDE and maven models and checks they are in sync: 
 * <ul>
 * <li>POM `artifactId` = Manifest `Bundle-SymbolicName`</li> 
 * <li>POM `version` = Manifest`Bundle-Version` </li>
 * <li>POM `version` = `*-SNAPSHOT` then Manifest `Bundle-Version` = `*.qualifier`</li> 
 * <li>(optional) POM `name` = Manifest `Bundle-Name`</li>
 * </ul> 
 * @author chris
 * @see http://my.safaribooksonline.com/book/software-engineering-and-development/ide/9780321574435/builders-markers-and-natures/ch14
 */
public class TychoBuilder extends IncrementalProjectBuilder {

	private List<Check> checks = new ArrayList<Check>();
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		if (shouldAudit(kind)) {
			doAudit(monitor);
		}
		return null;
	}

	/**
	 * Check the build type and the resource delta to determine if an audit is required
	 * @param kind The build kind
	 * @return true if audit required
	 */
	private boolean shouldAudit(int kind) {
		if (kind == FULL_BUILD)
			return true;
		IResourceDelta delta = getDelta(getProject());
		if (delta == null)
			return false;
		IResourceDelta[] children = delta.getAffectedChildren();
		for (int i = 0; i < children.length; i++) {
			IResourceDelta child = children[i];
			String fileName = child.getProjectRelativePath().lastSegment();
			if (fileName.equalsIgnoreCase("pom.xml") || fileName.equalsIgnoreCase("MANIFEST.MF"))
				return true;
		}
		return false;
	}
	
	/**
	 * Actually perform the audit. Load the PDE and Maven models and then perform a series of checks
	 * @param monitor A progress monitor
	 * @throws CoreException If there is a problem performing the checks
	 */
	private void doAudit(IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Checking Maven/PDE metadata consistency", 4);
		// Load up the manifest file (using PDE)
		subMonitor.subTask("Load PDE model");
		PluginModelManager pluginModelManager = PDECore.getDefault().getModelManager();
		IPluginModelBase pdeModel = pluginModelManager.findModel(getProject());
		subMonitor.worked(1);
		// Load up the POM (using M2E?)
		subMonitor.subTask("Load M2E model");
		IMavenProjectRegistry mavenRegistry = MavenPlugin.getMavenProjectRegistry();
		IMavenProjectFacade mavenModel = mavenRegistry.getProject(getProject());
		subMonitor.worked(1);
		
		subMonitor.subTask("Deleting old markers");
		getProject().deleteMarkers(IConstants.MARKER_ID, false, IResource.DEPTH_INFINITE);
		subMonitor.worked(1);
		
		SubMonitor checkMonitor = subMonitor.newChild(1);
		checkMonitor.beginTask("Perform consistency checks", checks.size());
		for(Check check : checks) {
			checkMonitor.subTask(check.getDisplayName());
			check.doCheck(pdeModel, mavenModel, checkMonitor.newChild(1));
		}
	}

}
