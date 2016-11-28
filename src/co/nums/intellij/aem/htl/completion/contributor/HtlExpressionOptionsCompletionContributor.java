package co.nums.intellij.aem.htl.completion.contributor;

import co.nums.intellij.aem.htl.completion.provider.HtlExpressionOptionsCompletionProvider;
import co.nums.intellij.aem.htl.psi.HtlTokenTypes;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class HtlExpressionOptionsCompletionContributor extends CompletionContributor {

	public HtlExpressionOptionsCompletionContributor() {
		extend(
				CompletionType.BASIC,
				psiElement(HtlTokenTypes.IDENTIFIER).inside(psiElement(HtlTokenTypes.OPTION)),
				new HtlExpressionOptionsCompletionProvider()
		);
	}

}
