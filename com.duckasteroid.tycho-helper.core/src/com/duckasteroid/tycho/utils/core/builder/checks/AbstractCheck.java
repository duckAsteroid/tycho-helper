package com.duckasteroid.tycho.utils.core.builder.checks;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.duckasteroid.tycho.utils.core.builder.CheckContext;
import com.duckasteroid.tycho.utils.core.builder.ICheck;
import com.duckasteroid.tycho.utils.core.builder.Problem;

public abstract class AbstractCheck implements ICheck {

	/**
	 * Finds the location of a problem in the PDE Manifest
	 */
	protected static void addPdeProblem(CheckContext ctx, Problem p, String pdeEntry, String pdeValue) {
		// return the location of the symbolic name in the PDE manifest
		String pdeModel = ctx.getBundleManifestContent();
		// FIXME This is a hacky way to do this!
		int start = pdeModel.indexOf(pdeEntry);
		if (start >= 0) {
			start += pdeEntry.length() + 2;
			int end = start + pdeValue.length();
			p.addLocation(ctx.getPdeResource(), start, end);
		}
	}

	protected static void addMavenProblem(CheckContext ctx, Problem p, String mavenPath, String mavenValue) {
		// return the location of the symbolic name in the PDE manifest
		String mavenModel = ctx.getMavenPomContent();
		PathFinder pathFinder = new PathFinder(mavenPath, mavenModel.split("\n"));
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
}