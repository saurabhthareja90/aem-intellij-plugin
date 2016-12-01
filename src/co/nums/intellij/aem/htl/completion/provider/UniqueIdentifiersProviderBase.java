package co.nums.intellij.aem.htl.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.TreeUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public abstract class UniqueIdentifiersProviderBase extends CompletionProvider<CompletionParameters> {

	abstract Set<LookupElement> getCandidateLookupElements();
	abstract IElementType getIdentifiersContainerElementType();
	abstract IElementType getIdentifiedElementType();

	@Override
	public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context,
			@NotNull CompletionResultSet resultSet) {
		Set<String> existingIdentifiers = findExistingIdentifiers(parameters);
		getCandidateLookupElements().stream()
				.filter(element -> !existingIdentifiers.contains(element.getLookupString()))
				.forEach(resultSet::addElement);
	}

	private Set<String> findExistingIdentifiers(@NotNull CompletionParameters parameters) {
		PsiElement currentElement = parameters.getPosition();
		ASTNode currentNode = currentElement.getNode();
		ASTNode identifiersContainer = TreeUtil.findParent(currentNode, getIdentifiersContainerElementType());
		if (identifiersContainer != null) {
			return Stream.of(identifiersContainer.getChildren(TokenSet.create(getIdentifiedElementType())))
					.map(ASTNode::getFirstChildNode)
					.map(ASTNode::getText)
					.collect(toSet());
		}
		return Collections.emptySet();
	}

}
