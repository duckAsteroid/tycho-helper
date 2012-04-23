package com.duckasteroid.tycho.utils.core.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;

/**
 * Represents a single problem detected by a check.
 * @author chris
 */
public class Problem {

	private String description;
	private Level level;
	private Class<? extends ICheck> checkType;
	private List<Location> locations = new ArrayList<Problem.Location>(2);
	
	public Problem(Class<? extends ICheck> checkType, Level level, String description) {
		this.description = description;
		this.level = level;
		this.checkType = checkType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Class<? extends ICheck> getCheckType() {
		return checkType;
	}

	public void setCheckType(Class<? extends ICheck> checkType) {
		this.checkType = checkType;
	}

	public List<Location> getLocations() {
		return locations;
	}
	
	public void addLocation(Location l) {
		locations.add(l);
	}
	
	public void removeLocation(Location l) {
		locations.remove(l);
	}

	
	/**
	 * A location of the problem in a resource
	 */
	public class Location {
		private IResource resource;
		private int startChar, endChar;
		
		public Location(IResource resource, int startChar, int endChar) {
			this.resource = resource;
			this.startChar = startChar;
			this.endChar = endChar;
		}
		
		public IResource getResource() {
			return resource;
		}
		public void setResource(IResource resource) {
			this.resource = resource;
		}
		public int getStartChar() {
			return startChar;
		}
		public void setStartChar(int startChar) {
			this.startChar = startChar;
		}
		public int getEndChar() {
			return endChar;
		}
		public void setEndChar(int endChar) {
			this.endChar = endChar;
		}
		
		public Problem getProblem() {
			return Problem.this;
		}
		
	}
}
