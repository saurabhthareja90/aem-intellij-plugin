package co.nums.intellij.aem.htl.completion.contributor;

import co.nums.intellij.aem.htl.completion.provider.HtlBlocksCompletionProvider;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.xml.XmlTokenType;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.XmlPatterns.xmlAttribute;

public class HtlBlocksCompletionContributor extends CompletionContributor {

	public HtlBlocksCompletionContributor() {
		extend(
				CompletionType.BASIC,
				psiElement(XmlTokenType.XML_NAME).inside(xmlAttribute()),
				new HtlBlocksCompletionProvider()
		);
	}

}
