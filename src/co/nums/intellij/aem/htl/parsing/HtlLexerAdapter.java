package co.nums.intellij.aem.htl.parsing;

import com.intellij.lexer.FlexAdapter;

public class HtlLexerAdapter extends FlexAdapter {

	public HtlLexerAdapter() {
		super(new HtlLexer(null));
	}

}
