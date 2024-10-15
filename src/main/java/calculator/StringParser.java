package calculator;

import java.util.ArrayList;
import java.util.List;

public class StringParser {
	
	private final List<Character> separators = new ArrayList<>();
	private final NumberExtractor numberExtractor;
	
	public StringParser(NumberExtractor numberExtractor) {
		this.numberExtractor = numberExtractor;
		addDefaultSeparators();
	}
	
	public List<Character> getSeparators() {
		return separators;
	}
	
	public List<Integer> parse(String str) {
		String extractedString = extractCustomSeparator(str);
		
		return numberExtractor.extract(extractedString, separators);
	}
	
	private String extractCustomSeparator(String str) {
		if (hasCustomSeparator(str)) {
			separators.add(str.charAt(2));
			return str.substring(5);
		} else {
			return str;
		}
	}
	
	private boolean hasCustomSeparator(String str) {
		return str.startsWith("//");
	}
	
	private void addDefaultSeparators() {
		separators.add(',');
		separators.add(':');
	}
}
