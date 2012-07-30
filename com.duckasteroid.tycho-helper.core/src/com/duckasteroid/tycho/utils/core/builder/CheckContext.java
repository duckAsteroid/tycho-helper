package com.duckasteroid.tycho.utils.core.builder;

import java.util.List;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.pde.core.plugin.IPluginModelBase;

/**
 * Provides a context for an {@link ICheck} to work in
 */
public class CheckContext {
	private IPluginModelBase pdeModel;
	private IMavenProjectFacade mavenModel;
	private String bundleManifestContent;
	private String mavenPomContent;

	private List<Problem> results;

	public CheckContext(IPluginModelBase pdeModel, IMavenProjectFacade mavenModel, List<Problem> results) {
		super();
		this.pdeModel = pdeModel;
		this.mavenModel = mavenModel;
		this.results = results;
		bundleManifestContent = loadContent(getPdeResource());
		mavenPomContent = loadContent(getMavenResource());
	}
	
	private static String loadContent(IResource file) {
		ITextFileBufferManager bufferManager = FileBuffers.getTextFileBufferManager();
		IPath path = file.getFullPath();
		try {
			bufferManager.connect(path, LocationKind.IFILE, null);
            ITextFileBuffer textFileBuffer = bufferManager.getTextFileBuffer(path, LocationKind.IFILE);
            IDocument document = textFileBuffer.getDocument();
            return document.get();
		}
		catch(CoreException e1) {
			e1.printStackTrace();
		}
		finally {
			try {
				bufferManager.disconnect(path, LocationKind.IFILE, null);
			}
			catch(CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public IPluginModelBase getPdeModel() {
		return pdeModel;
	}
	
	public IResource getPdeResource() {
		return pdeModel.getUnderlyingResource();
	}

	public IMavenProjectFacade getMavenModel() {
		return mavenModel;
	}
	
	public IResource getMavenResource() {
		return mavenModel.getPom();
	}

	public List<Problem> getResults() {
		return results;
	}

	public String getBundleManifestContent() {
		return bundleManifestContent;
	}

	public String getMavenPomContent() {
		return mavenPomContent;
	}
}
