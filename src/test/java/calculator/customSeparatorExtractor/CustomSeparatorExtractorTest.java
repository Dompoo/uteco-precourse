package calculator.customSeparatorExtractor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomSeparatorExtractorTest {
	
	@Test
	void 추출할_커스텀구분자가_없다면_빈_리스트를_반환한다() {
		//given
		CustomSeparatorExtractorImpl sut = new CustomSeparatorExtractorImpl();
		
		//when
		CustomSeparatorExtractResult result = sut.extract("1:2:3");
		
		//then
		Assertions.assertThat(result.getExtractedSeparators()).isEmpty();
	}
	
	@Test
	void 추출할_커스텀구분자가_있다면_추출한다() {
		//given
		CustomSeparatorExtractorImpl sut = new CustomSeparatorExtractorImpl();
		
		//when
		CustomSeparatorExtractResult result = sut.extract("//;\n1:2:3");
		
		//then
		Assertions.assertThat(result.getExtractedSeparators()).containsExactly(';');
	}
	
	@Test
	void 추출할_커스텀구분자가_여러개_있다면_모두_추출한다() {
		//given
		CustomSeparatorExtractorImpl sut = new CustomSeparatorExtractorImpl();
		
		//when
		CustomSeparatorExtractResult result = sut.extract("//;^&\n1:2:3");
		
		//then
		Assertions.assertThat(result.getExtractedSeparators()).containsExactly(';', '^', '&');
	}
	
	@Test
	void 커스텀구분자를_추출하고_남은_숫자파트를_반환한다() {
	    //given
		CustomSeparatorExtractorImpl sut = new CustomSeparatorExtractorImpl();
	    
	    //when
		CustomSeparatorExtractResult result = sut.extract("//;^&\n1:2:3");
	    
	    //then
		Assertions.assertThat(result.getNumberPart()).isEqualTo("1:2:3");
	}
	
	@Test
	void 커스텀구분자가_없는_경우에도_남은_숫자파트를_반환한다() {
		//given
		CustomSeparatorExtractorImpl sut = new CustomSeparatorExtractorImpl();
		
		//when
		CustomSeparatorExtractResult result = sut.extract("1:2:3");
		
		//then
		Assertions.assertThat(result.getNumberPart()).isEqualTo("1:2:3");
	}
}