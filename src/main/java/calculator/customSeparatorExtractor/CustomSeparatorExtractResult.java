package calculator.customSeparatorExtractor;

import java.util.List;

public class CustomSeparatorExtractResult {
	
	private final String numberPart;
	private final List<Character> extractedSeparators;
	
	public CustomSeparatorExtractResult(String numberPart, List<Character> extractedSeparators) {
		this.numberPart = numberPart;
		this.extractedSeparators = extractedSeparators;
	}
	
	public String getNumberPart() {
		return numberPart;
	}
	
	public List<Character> getExtractedSeparators() {
		return extractedSeparators;
	}
}
