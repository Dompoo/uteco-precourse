package calculator.io;

public class InputParser {
	
	public InputHandleResult parse(String input) {
		InputHandleResult result;
		if (input.startsWith("//")) {
			result = parseInputIncludeSeparatorPart(input);
		} else {
			result = parseInputExcludeSeparatorPart(input);
		}
		return result;
	}
	
	private static InputHandleResult parseInputIncludeSeparatorPart(String input) {
		String numberPart;
		String customSeparatorPart;
		int separatorPartEnd = 0;
		int numberPartStart = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '\\' && i + 1 < input.length() && input.charAt(i + 1) == 'n') {
				separatorPartEnd = i;
				numberPartStart = i + 2;
				break;
			}
		}
		
		customSeparatorPart = input.substring(2, separatorPartEnd);
		numberPart = input.substring(numberPartStart);
		return new InputHandleResult(customSeparatorPart, numberPart);
	}
	
	private static InputHandleResult parseInputExcludeSeparatorPart(String input) {
		return new InputHandleResult("", input);
	}
}
