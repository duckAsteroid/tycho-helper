package com.duckasteroid.tycho.utils.core.builder.checks;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.Constants;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.duckasteroid.tycho.utils.core.builder.CheckContext;
import com.duckasteroid.tycho.utils.core.builder.ICheck;
import com.duckasteroid.tycho.utils.core.builder.Level;
import com.duckasteroid.tycho.utils.core.builder.Problem;
import com.duckasteroid.tycho.utils.core.builder.Problem.Location;

/**
 * Checks if POM `artifactId` = Manifest `Bundle-SymbolicName`
 * 
 * @author chris
 */
public class ArtifactSymbolicNameCheck implements ICheck {
	@Override
	public void doCheck(CheckContext ctx, IProgressMonitor monitor) {
		String pdeName = ctx.getPdeModel().getBundleDescription().getSymbolicName();
		String mavenName = ctx.getMavenModel().getArtifactKey().getArtifactId();

		if (!pdeName.equals(mavenName)) {
			Problem problem = new Problem(getClass(), Level.ERROR, "POM Artifact ID [" + mavenName + "] does not match Bundle-SymbolicName [" + pdeName + "]");
			addPdeNameLocation(ctx, problem, pdeName);
			addMavenArtifactIdLocation(ctx, problem, mavenName);
			ctx.getResults().add(problem);
		}
		monitor.done();
	}

	private void addPdeNameLocation(CheckContext ctx, Problem p, String pdeName) {
		// return the location of the symbolic name in the PDE manifest
		String pdeModel = ctx.getBundleManifestContent();
		int start = pdeModel.indexOf(Constants.BUNDLE_SYMBOLICNAME);
		if (start >= 0) {
			start += Constants.BUNDLE_SYMBOLICNAME.length() + 2;
			int end = start + pdeName.length();
			p.addLocation(ctx.getPdeResource(), start, end);
		}
	}

	static final String ARTIFACT_ID_PATH = "project/artifactId";

	private void addMavenArtifactIdLocation(CheckContext ctx, Problem p, String pdeName) {
		// return the location of the symbolic name in the PDE manifest
		String mavenModel = ctx.getMavenPomContent();
		PathFinder pathFinder = new PathFinder(ARTIFACT_ID_PATH, mavenModel.split("\n"));
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(new InputSource(new StringReader(mavenModel)), pathFinder);
			if (pathFinder.isFound()) {
				p.addLocation(ctx.getMavenResource(), pathFinder.getStart(), pathFinder.getEnd());
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getDisplayName() {
		return "POM Artifact ID = Bundle-SymbolicName check";
	}

}
