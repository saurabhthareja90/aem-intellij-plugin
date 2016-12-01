package co.nums.intellij.aem.htl.completion.contributor;

import co.nums.intellij.aem.htl.completion.provider.HtlDisplayContextsProvider;
import co.nums.intellij.aem.htl.completion.provider.HtlExpressionOptionsCompletionProvider;
import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.StandardPatterns.or;

public class HtlExpressionOptionsCompletionContributor extends CompletionContributor {

	public HtlExpressionOptionsCompletionContributor() {
		extend(
				CompletionType.BASIC,
				optionIdentifier(),
				new HtlExpressionOptionsCompletionProvider()
		);
		extend(
				CompletionType.BASIC,
				displayContextOptionValue(),
				new HtlDisplayContextsProvider()
		);
	}

	private static PsiElementPattern.Capture<PsiElement> optionIdentifier() {
		return psiElement(HtlTokenTypes.IDENTIFIER)
				.inside(psiElement(HtlTokenTypes.OPTION))
				.andNot(psiElement().afterLeaf(psiElement(HtlTokenTypes.ASSIGN)));
	}

	private static PsiElementPattern.Capture<PsiElement> displayContextOptionValue() {
		return psiElement()
				.inside(psiElement(HtlTokenTypes.OPTION))
				.afterLeafSkipping(
						or(new PsiElementPattern[] {
								psiElement(HtlTokenTypes.ASSIGN),
								psiElement(TokenType.WHITE_SPACE)
						}),
						psiElement(HtlTokenTypes.IDENTIFIER).withText("context")
				);
	}

}
