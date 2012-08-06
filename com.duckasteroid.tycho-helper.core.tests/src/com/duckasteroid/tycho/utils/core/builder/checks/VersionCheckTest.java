package com.duckasteroid.tycho.utils.core.builder.checks;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osgi.framework.Version;

public class VersionCheckTest {

	@Test
	public void testEqualVersion() {
		Version pde = new Version(1,2,3,"test");
		assertEquals("1.2.3.test", pde.toString());
		String maven = "1.2.3-test";
		assertTrue(VersionCheck.equalVersion(pde, maven));
		
		pde = new Version(1,2,3,"wibble");
		assertFalse(VersionCheck.equalVersion(pde, maven));
		
		pde = new Version(1,2,4,"test");
		assertFalse(VersionCheck.equalVersion(pde, maven));
		
		pde = new Version(1,3,3,"test");
		assertFalse(VersionCheck.equalVersion(pde, maven));
		
		pde = new Version(2,2,3,"test");
		assertFalse(VersionCheck.equalVersion(pde, maven));
		
		maven = "1.0.0-SNAPSHOT";
		pde = new Version(1,0,0,"qualifier");
		assertTrue(VersionCheck.equalVersion(pde, maven));
	}

}
