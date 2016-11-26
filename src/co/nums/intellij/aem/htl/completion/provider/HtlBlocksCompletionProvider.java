package co.nums.intellij.aem.htl.completion.provider;

import co.nums.intellij.aem.htl.icons.HtlIcons;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * Provides HTL blocks (HTML data-sly-* attributes).
 */
public class HtlBlocksCompletionProvider extends CompletionProvider<CompletionParameters> {

	@Override
	public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context,
			@NotNull CompletionResultSet resultSet) {
		// make it version aware
		addHtlBlockElement(resultSet, "data-sly-use", HtlSimpleBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-text", HtlExpressionBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-attribute", HtlExpressionBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-element", HtlExpressionBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-test", HtlExpressionBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-list", HtlExpressionBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-repeat", HtlExpressionBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-include", HtlSimpleBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-resource", HtlSimpleBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-template");
		addHtlBlockElement(resultSet, "data-sly-call", HtlExpressionBlockInsertHandler.INSTANCE);
		addHtlBlockElement(resultSet, "data-sly-unwrap");
	}

	private static void addHtlBlockElement(CompletionResultSet resultSet, String name) {
		addHtlBlockElement(resultSet, name, null);
	}

	private static void addHtlBlockElement(CompletionResultSet resultSet, String name,
			InsertHandler<LookupElement> insertHandler) {
		LookupElementBuilder elementBuilder = LookupElementBuilder.create(name)
				.withBoldness(true)
				.withIcon(HtlIcons.HTL_BLOCK)
				.withTypeText("HTL block", true);
		if (insertHandler != null) {
			elementBuilder = elementBuilder.withInsertHandler(insertHandler);
		}
		resultSet.addElement(elementBuilder);
	}

}
