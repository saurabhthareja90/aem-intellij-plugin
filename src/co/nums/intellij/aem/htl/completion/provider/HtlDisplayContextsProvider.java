package co.nums.intellij.aem.htl.completion.provider;

import co.nums.intellij.aem.htl.icons.HtlIcons;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class HtlDisplayContextsProvider extends CompletionProvider<CompletionParameters> {

	private static final String[] DISPLAY_CONTEXTS = new String[] {
			"attribute",
			"attributeName",
			"comment",
			"elementName",
			"html",
			"number",
			"scriptComment",
			"scriptRegExp",
			"scriptString",
			"scriptToken",
			"styleComment",
			"styleString",
			"styleToken",
			"text",
			"unsafe",
			"uri"
	};

	private final List<LookupElement> displayContextElements;

	public HtlDisplayContextsProvider() {
		displayContextElements = Stream.of(DISPLAY_CONTEXTS)
				.map(HtlDisplayContextsProvider::displayContextElement)
				.collect(toList());
	}

	private static LookupElement displayContextElement(String displayContextName) {
		return LookupElementBuilder.create(displayContextName)
				.withIcon(HtlIcons.HTL_DISPLAY_CONTEXT)
				.withTypeText("HTL display context", true);
	}

	@Override
	protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context,
			@NotNull CompletionResultSet resultSet) {
		resultSet.addAllElements(displayContextElements);
	}

}
