package com.duckasteroid.tycho.utils.core.builder.checks;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.Constants;

import com.duckasteroid.tycho.utils.core.builder.CheckContext;
import com.duckasteroid.tycho.utils.core.builder.ICheck;
import com.duckasteroid.tycho.utils.core.builder.Level;
import com.duckasteroid.tycho.utils.core.builder.Problem;

/**
 * Checks if POM `artifactId` = Manifest `Bundle-SymbolicName`
 * 
 * @author chris
 */
public class ArtifactSymbolicNameCheck extends AbstractCheck implements ICheck {
	
	private static final String ARTIFACT_ID_PATH = "project/artifactId";
	
	@Override
	public void doCheck(CheckContext ctx, IProgressMonitor monitor) {
		String pdeName = ctx.getPdeModel().getBundleDescription().getSymbolicName();
		String mavenName = ctx.getMavenModel().getArtifactKey().getArtifactId();

		if (!pdeName.equals(mavenName)) {
			Problem problem = new Problem(getClass(), Level.ERROR, "POM Artifact ID [" + mavenName + "] does not match Bundle-SymbolicName [" + pdeName + "]");
			addPdeProblem(ctx, problem, Constants.BUNDLE_SYMBOLICNAME, pdeName);
			addMavenProblem(ctx, problem, ARTIFACT_ID_PATH, mavenName);
			ctx.getResults().add(problem);
		}
		monitor.done();
	}

	@Override
	public String getDisplayName() {
		return "POM Artifact ID = Bundle-SymbolicName check";
	}

}
