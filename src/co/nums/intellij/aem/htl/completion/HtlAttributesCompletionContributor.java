package co.nums.intellij.aem.htl.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.XmlPatterns.xmlAttribute;

public class HtlAttributesCompletionContributor extends CompletionContributor {

	public HtlAttributesCompletionContributor() {
		extend(
				CompletionType.BASIC,
				psiElement().inside(xmlAttribute()),
				new CompletionProvider<CompletionParameters>() {
					public void addCompletions(CompletionParameters parameters, ProcessingContext context,
							CompletionResultSet resultSet) {
						// TODO: make it dependant on HTL version
						resultSet.addElement(LookupElementBuilder.create("data-sly-use"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-text"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-attribute"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-element"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-test"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-list"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-repeat"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-include"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-resource"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-template"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-call"));
						resultSet.addElement(LookupElementBuilder.create("data-sly-unwrap"));
					}
				});
	}

}
