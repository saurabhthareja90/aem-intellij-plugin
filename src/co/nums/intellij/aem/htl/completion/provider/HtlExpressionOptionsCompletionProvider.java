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
 * Provides HTL built-in expression options.
 */
public class HtlExpressionOptionsCompletionProvider extends CompletionProvider<CompletionParameters> {

	@Override
	public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context,
			@NotNull CompletionResultSet resultSet) {
		// make it HTL version aware
		// TODO: remove already existing references
		addExpressionOptionElement(resultSet, "scheme");
		addExpressionOptionElement(resultSet, "domain");
		addExpressionOptionElement(resultSet, "i18n");
		addExpressionOptionElement(resultSet, "locale");
		addExpressionOptionElement(resultSet, "format", HtlExpressionOptionBracketsInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "context", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "hint", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "join", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "path", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "prependPath", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "appendPath", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "selectors", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "addSelectors", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "removeSelectors", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "extension", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "suffix", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "prependSuffix", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "appendSuffix", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "query", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "addQuery", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "removeQuery", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
		addExpressionOptionElement(resultSet, "fragment", HtlExpressionOptionQuotesInsertHandler.INSTANCE);
	}

	private static void addExpressionOptionElement(CompletionResultSet resultSet, String name) {
		addExpressionOptionElement(resultSet, name, null);
	}

	private static void addExpressionOptionElement(CompletionResultSet resultSet, String name,
			InsertHandler<LookupElement> insertHandler) {
		LookupElementBuilder elementBuilder = LookupElementBuilder.create(name)
				.withIcon(HtlIcons.HTL_EXPRESSION_OPTION)
				.withTypeText("HTL expression option", true);
		if (insertHandler != null) {
			elementBuilder = elementBuilder.withInsertHandler(insertHandler);
		}
		resultSet.addElement(elementBuilder);
	}

}
