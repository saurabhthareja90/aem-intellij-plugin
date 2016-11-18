package co.nums.intellij.aem.htl.highlighter;

import co.nums.intellij.aem.htl.parsing.HtlLexerAdapter;
import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.KEYWORD;
import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.NUMBER;
import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.STRING;

public class HtlSyntaxHighlighter extends SyntaxHighlighterBase {

	private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<>();

	static {
		fillMap(ATTRIBUTES, KEYWORD, HtlTokenTypes.BOOLEAN_TRUE, HtlTokenTypes.BOOLEAN_FALSE);
		fillMap(ATTRIBUTES, KEYWORD, HtlTokenTypes.EXPR_START, HtlTokenTypes.EXPR_END, HtlTokenTypes.OPTIONS_SEPARATOR);
		fillMap(ATTRIBUTES, STRING, HtlTokenTypes.SINGLE_QUOTED_STRING, HtlTokenTypes.DOUBLE_QUOTED_STRING);
		fillMap(ATTRIBUTES, NUMBER, HtlTokenTypes.INTEGER_NUMBER, HtlTokenTypes.FLOAT_NUMBER);
	}

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new HtlLexerAdapter();
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		return pack(ATTRIBUTES.get(tokenType));
	}

}
