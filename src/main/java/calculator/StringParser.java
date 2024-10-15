package calculator;

import java.util.ArrayList;
import java.util.List;

public class StringParser {
	
	private final List<Character> separators = new ArrayList<>();
	
	public StringParser() {
		addDefaultSeparators();
	}
	
	public List<Character> getSeparators() {
		return separators;
	}
	
	private void addDefaultSeparators() {
		separators.add(',');
		separators.add(':');
	}
}
