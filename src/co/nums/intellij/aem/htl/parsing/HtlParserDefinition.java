package co.nums.intellij.aem.htl.parsing;

import co.nums.intellij.aem.htl.HtlLanguage;
import co.nums.intellij.aem.htl.psi.HtlFile;
import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class HtlParserDefinition implements ParserDefinition {

	public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
	public static final IFileElementType FILE = new IFileElementType(Language.findInstance(HtlLanguage.class));

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		return new HtlLexerAdapter();
	}

	@NotNull
	@Override
	public PsiParser createParser(Project project) {
		return new HtlParser();
	}

	@NotNull
	@Override
	public TokenSet getWhitespaceTokens() {
		return WHITE_SPACES;
	}

	@NotNull
	@Override
	public TokenSet getCommentTokens() {
		return TokenSet.EMPTY;
	}

	@NotNull
	@Override
	public TokenSet getStringLiteralElements() {
		return TokenSet.create(HtlTokenTypes.SINGLE_QUOTED_STRING, HtlTokenTypes.DOUBLE_QUOTED_STRING);
	}

	@Override
	public IFileElementType getFileNodeType() {
		return FILE;
	}

	@Override
	public PsiFile createFile(FileViewProvider viewProvider) {
		return new HtlFile(viewProvider);
	}

	@Override
	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
		return SpaceRequirements.MAY;// TODO: space required in ternary operator
	}

	@NotNull
	@Override
	public PsiElement createElement(ASTNode node) {
		return HtlTokenTypes.Factory.createElement(node);
	}

}
