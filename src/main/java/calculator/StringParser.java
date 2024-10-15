package calculator;

import calculator.customSeparatorExtractor.CustomSeparatorExtractResult;
import calculator.customSeparatorExtractor.CustomSeparatorExtractor;
import calculator.numberExtractor.NumberExtractor;

import java.util.ArrayList;
import java.util.List;

public class StringParser {
	
	private final List<Character> separators = new ArrayList<>();
	private final CustomSeparatorExtractor customSeparatorExtractor;
	private final NumberExtractor numberExtractor;
	
	public StringParser(
			CustomSeparatorExtractor customSeparatorExtractor,
			NumberExtractor numberExtractor
	) {
		this.customSeparatorExtractor = customSeparatorExtractor;
		this.numberExtractor = numberExtractor;
		addDefaultSeparators();
	}
	
	public List<Character> getSeparators() {
		return separators;
	}
	
	public List<Integer> parse(String str) {
		CustomSeparatorExtractResult extractResult = customSeparatorExtractor.extract(str);
		separators.addAll(extractResult.getExtractedSeparators());
		
		return numberExtractor.extract(extractResult.getNumberPart(), separators);
	}
	
	private void addDefaultSeparators() {
		separators.add(',');
		separators.add(':');
	}
}
