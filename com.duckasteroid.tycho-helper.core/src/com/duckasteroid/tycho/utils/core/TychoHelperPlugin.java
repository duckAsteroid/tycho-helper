package com.duckasteroid.tycho.utils.core;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleActivator;

import com.duckasteroid.tycho.utils.core.builder.ICheck;

public class TychoHelperPlugin extends Plugin implements BundleActivator {
	
	public TychoHelperPlugin() {}
	
	private static final String CHECKS = "checks";
	private static ArrayList<ICheck> checks = null;
	/**
	 * Load IChecks from the checks extension point
	 * @return A list of checks to use
	 */
	public static synchronized List<ICheck> getChecks() {
		if (checks == null) {
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IConfigurationElement[] elements = registry.getConfigurationElementsFor(IConstants.PLUGIN_ID, CHECKS);
			if (elements != null && elements.length > 0) {
				checks = new ArrayList<ICheck>();
			}
			
			for (IConfigurationElement e : elements) {
				try {
					ICheck check = (ICheck) e.createExecutableExtension("class");
					checks.add(check);
				}
				catch(CoreException ex) {
					ex.printStackTrace();
				}
			}
			
		}
		return checks;
	}
}
