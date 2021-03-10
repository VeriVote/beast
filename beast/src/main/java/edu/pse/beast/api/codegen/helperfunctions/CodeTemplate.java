package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

public class CodeTemplate {
	private String code;
	private List<String> placeHolder;
	private List<String> loopBounds;
	
	public CodeTemplate(String code, List<String> placeHolder,
			List<String> loopBounds) {
		this.code = code;
		this.placeHolder = placeHolder;
		this.loopBounds = loopBounds;
	}
	
	
}
