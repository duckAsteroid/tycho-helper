package com.duckasteroid.tycho.utils.core.builder.checks;

import java.util.Arrays;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

import com.duckasteroid.tycho.utils.core.builder.CheckContext;
import com.duckasteroid.tycho.utils.core.builder.ICheck;
import com.duckasteroid.tycho.utils.core.builder.Level;
import com.duckasteroid.tycho.utils.core.builder.Problem;

public class VersionCheck extends AbstractCheck implements ICheck {
	private static final String PATH = "project/version";

	@Override
	public void doCheck(CheckContext ctx, IProgressMonitor monitor) {
		Version pdeVersion = ctx.getPdeModel().getBundleDescription().getVersion();
		String mavenVersion = ctx.getMavenModel().getArtifactKey().getVersion();

		if (!equalVersion(pdeVersion, mavenVersion)) {
			Problem problem = new Problem(getClass(), Level.ERROR, "POM Artifact Version [" + mavenVersion + "] does not match Bundle-SymbolicName [" + pdeVersion.toString() + "]");
			addPdeProblem(ctx, problem, Constants.BUNDLE_VERSION, pdeVersion.toString());
			addMavenProblem(ctx, problem, PATH, mavenVersion);
			ctx.getResults().add(problem);
		}
		monitor.done();
	}
	
	public static boolean equalVersion(Version pdeVersion, String mavenVersion) {
		// TODO Actually compare versions...
		String[] mavenBits = mavenVersion.split("\\.|-");
		String[] osgiBits = pdeVersion.toString().split("\\.");
		if (mavenBits.length == osgiBits.length) {
			for(int i = 0; i <= 2 && i < mavenBits.length; i++) {
				if (!mavenBits[i].equals(osgiBits[i])) {
					return false;
				}
			}
			if (mavenBits.length == 4) {
				if (mavenBits[3].equals("SNAPSHOT") && osgiBits[3].equals("qualifier")) {
					return true;
				}
				else {
					return mavenBits[3].equals(osgiBits[3]);
				}
			}
			return Arrays.equals(mavenBits, osgiBits);
		}

		return false;
		
	}

	@Override
	public String getDisplayName() {
		return "POM Artifact Version = Bundle-Version check";
	}

}
