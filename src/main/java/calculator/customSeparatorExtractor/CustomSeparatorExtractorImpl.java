package calculator.customSeparatorExtractor;

import java.util.ArrayList;
import java.util.List;

public class CustomSeparatorExtractorImpl implements CustomSeparatorExtractor {
	
	@Override
	public CustomSeparatorExtractResult extract(String str) {
		List<Character> extractedSeparators = new ArrayList<>();
		
		int numberPartStartIndex = 0;
		if (hasCustomSeparator(str)) {
			for (int i = 2; i < str.length(); i++) {
				char currentChar = str.charAt(i);
				if (currentChar != '\n') {
					extractedSeparators.add(currentChar);
				} else {
					numberPartStartIndex = i + 1;
					break;
				}
			}
		}
		
		return new CustomSeparatorExtractResult(str.substring(numberPartStartIndex), extractedSeparators);
	}
	
	private boolean hasCustomSeparator(String str) {
		return str.startsWith("//");
	}
}
