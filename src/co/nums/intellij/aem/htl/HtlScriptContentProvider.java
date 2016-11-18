package co.nums.intellij.aem.htl;

import com.intellij.lang.HtmlScriptContentProvider;
import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

public class HtlScriptContentProvider implements HtmlScriptContentProvider {

	@Override
	public IElementType getScriptElementType() {
		return null;
	}

	@Nullable
	@Override
	public Lexer getHighlightingLexer() {
		return null;
	}

}
