package com.duckasteroid.tycho.utils.core.builder.checks;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Finds the character start/end locations of an element - given as a path
 */
public class PathFinder extends DefaultHandler {
	private List<String> path;
	private int start = -1;
	private int end = -1;
	private Locator locator;
	
	private Stack<String> elementContext = new Stack<String>();
	
	private String[] lines;
	
	
	public PathFinder(List<String> path, String[] lines) {
		this.path = path;
		this.lines = lines;
	}
	
	public PathFinder(String path, String[] lines){
		String[] elements = path.split("/");
		this.path = Arrays.asList(elements);
		this.lines = lines;
	}
	
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }
	
	private boolean isInPath() {
		if (elementContext.size() >= path.size()) {
			for(int i=0; i < path.size(); i++) {
				String pathElement = path.get(i);
				String ctxElement = elementContext.get(i);
				if (!pathElement.equals(ctxElement)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private int getLocationOffset() {
		int offset = 0;
		int lines = locator.getLineNumber();
		if (lines >= 1) {
			for(int i =0; i < lines -1; i++) {
				offset += this.lines[i].length();
			}
		}
		offset += locator.getColumnNumber();
		return offset;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		elementContext.push(qName);
		if (isInPath()) {
			if (start < 0) {
				start = getLocationOffset();
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		elementContext.pop();
		if (!isInPath() && start >=0 && end < 0){
			end = getLocationOffset();
		}
	}

	public boolean isFound() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getStart() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getEnd() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
