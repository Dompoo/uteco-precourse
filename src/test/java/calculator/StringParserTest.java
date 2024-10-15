package calculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class StringParserTest {
	
	@Test
	void StringParser클래스를_생성하면_기본구분자가_저장된다() {
		//when
		StringParser sut = new StringParser();
		
		//then
		List<Character> separators = sut.getSeparators();
		Assertions.assertThat(separators).containsExactly(',', ':');
	}
	
}