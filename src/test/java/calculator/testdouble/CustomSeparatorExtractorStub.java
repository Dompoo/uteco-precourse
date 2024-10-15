package calculator.testdouble;

import calculator.customSeparatorExtractor.CustomSeparatorExtractResult;
import calculator.customSeparatorExtractor.CustomSeparatorExtractor;

import java.util.List;

public class CustomSeparatorExtractorStub implements CustomSeparatorExtractor {
	
	@Override
	public CustomSeparatorExtractResult extract(String str) {
		return new CustomSeparatorExtractResult("1:2:3", List.of('(', ')'));
	}
}
