package co.nums.intellij.aem.htl.psi

import co.nums.intellij.aem.htl.data.blocks.HtlBlockVariable
import co.nums.intellij.aem.htl.definitions.BlockIdentifierType
import co.nums.intellij.aem.htl.psi.search.*
import com.intellij.lang.StdLanguages
import com.intellij.openapi.components.ServiceManager
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.ResolveCache
import java.util.*

class HtlVariableReference(htlVariable: HtlVariable) : PsiReferenceBase<HtlVariable>(htlVariable) {

    override fun resolve() = ResolveCache.getInstance(element.project)
            .resolveWithCaching(this, { reference, _ -> doResolve(reference) }, false, false)

    private fun doResolve(reference: HtlVariableReference): PsiElement? {
        val variableName = reference.element.identifier.text
        val htlJavaSearch = ServiceManager.getService(HtlJavaSearch::class.java)
        return htlJavaSearch?.globalObjectJavaClass(element.project, variableName)
                ?: blockVariableAttribute(variableName)
    }

    private fun blockVariableAttribute(variableName: String?): PsiElement? {
        val htmlFile = element.containingFile.viewProvider.getPsi(StdLanguages.HTML)
        return HtlSearch.blockVariables(htmlFile)
                .filter { it.identifier.equals(variableName, ignoreCase = true) }
                .filter { it.hasMatchingScope() }
                .sortedWith(BestMatchingToElementComparator(element))
                .map { it.declaration }
                .firstOrNull()
    }

    private fun HtlBlockVariable.hasMatchingScope() = when (identifierType) {
        BlockIdentifierType.ELEMENT_SCOPE_VARIABLE -> coversElement()
    // TODO: what about BlockIdentifierType.ELEMENT_CHILDREN_SCOPE_VARIABLE
        BlockIdentifierType.GLOBAL_VARIABLE -> isDeclaredBeforeElement()
        BlockIdentifierType.TEMPLATE_NAME -> true
        else -> false
    }

    private fun HtlBlockVariable.coversElement() = declaration.parent.textRange.contains(element.textRange)

    private fun HtlBlockVariable.isDeclaredBeforeElement() = declaration.textOffset < element.textOffset

    override fun getVariants() = emptyArray<Any>()

    override fun equals(other: Any?) = other is HtlVariableReference && element === other.element

    override fun hashCode() = element.hashCode()

}

private class BestMatchingToElementComparator(val element: HtlVariable) : Comparator<HtlBlockVariable> {

    override fun compare(o1: HtlBlockVariable, o2: HtlBlockVariable): Int {
        val o1Priority = o1.getPriority()
        val o2Priority = o2.getPriority()
        if (o1Priority == o2Priority) {
            val o1DistanceFromElement = element.textOffset - o1.declaration.textOffset
            val o2DistanceFromElement = element.textOffset - o2.declaration.textOffset
            return o1DistanceFromElement - o2DistanceFromElement
        }
        return o1Priority - o2Priority
    }

    private fun HtlBlockVariable.getPriority() = when (identifierType) {
        BlockIdentifierType.ELEMENT_CHILDREN_SCOPE_VARIABLE -> 0
        BlockIdentifierType.ELEMENT_SCOPE_VARIABLE -> 1
        BlockIdentifierType.GLOBAL_VARIABLE -> 2
        BlockIdentifierType.TEMPLATE_NAME -> 3
        else -> 4
    }

}
