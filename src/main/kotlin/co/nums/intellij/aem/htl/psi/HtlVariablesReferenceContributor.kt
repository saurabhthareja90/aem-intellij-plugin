package co.nums.intellij.aem.htl.psi

import co.nums.intellij.aem.htl.HtlLanguage
import co.nums.intellij.aem.htl.definitions.*
import com.intellij.lang.html.HTMLLanguage
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.StandardPatterns.or
import com.intellij.patterns.StandardPatterns.string
import com.intellij.patterns.XmlPatterns.xmlAttribute
import com.intellij.psi.*
import com.intellij.psi.impl.source.xml.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.util.ProcessingContext

class HtlVariablesReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        val pattern = xmlAttribute().withLocalName(or(
                string().equalTo(HtlBlock.LIST.type),
                string().equalTo(HtlBlock.REPEAT.type),
                string().startsWith("${HtlBlock.LIST.type}."),
                string().startsWith("${HtlBlock.REPEAT.type}."),
                string().startsWith("${HtlBlock.USE.type}."),
                string().startsWith("${HtlBlock.TEST.type}."),
                string().startsWith("${HtlBlock.TEMPLATE.type}.")))
        registrar.registerReferenceProvider(pattern, HtlPsiReferenceProvider())
    }

}

class HtlPsiReferenceProvider : PsiReferenceProvider() {

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val attribute = element as? XmlAttribute ?: return emptyArray()
        val variableReferences: MutableList<PsiReference> = htlVariablesDeclaredBy(attribute)
                .mapNotNull { it.reference }
                .toMutableList()
        variableReferences.add(HtlVariableBlockReference(element as XmlAttributeImpl))
        return variableReferences.toTypedArray()
    }

    fun htlVariablesDeclaredBy(attribute: XmlAttribute): Collection<HtlVariable> {
        val htlFile = attribute.containingFile.viewProvider.getPsi(HtlLanguage) ?: return emptyList()
        val variableIdentifier = attribute.getVariableIdentifier()
        val blockName = attribute.getBlockName()
        val htlVariables = htlVariables(htlFile, blockName, variableIdentifier)
        return when {
            isScopedVariableBlock(blockName) -> referencesInScope(attribute, variableIdentifier, htlVariables)
            isGlobalVariableBlock(blockName) -> referencesAfterDeclaration(attribute, variableIdentifier, htlVariables)
            isTemplateVariableBlock(blockName) -> emptyList()
            else -> throw IllegalStateException("Invalid block attribute: $blockName")
        }
    }

    private fun htlVariables(htlFile: PsiFile, blockName: String, variableIdentifier: String): List<HtlVariable> {
        val iterableBlock = isHtlIterableBlock(blockName)
        return PsiTreeUtil.findChildrenOfType(htlFile, HtlVariable::class.java)
                .filter {
                    it.text.equals(variableIdentifier, ignoreCase = true)
                            || (iterableBlock && it.text.equals("${variableIdentifier}List", ignoreCase = true))
                }
    }

    private fun XmlAttribute.getBlockName() =
            if (localName.contains('.')) localName.substringBefore('.') else localName

    private fun XmlAttribute.getVariableIdentifier() =
            if (localName.contains('.')) localName.substringAfter('.') else "item"

    private fun referencesInScope(attribute: XmlAttribute, variableIdentifier: String, htlVariables: List<HtlVariable>): Collection<HtlVariable> {
        val scopeRange = attribute.parent.textRange
        val overridingScopes = PsiTreeUtil.findChildrenOfType(attribute.parent, XmlAttribute::class.java)
                .filter { it.getVariableIdentifier().equals(variableIdentifier, ignoreCase = true) }
                .filter { isScopedVariableBlock(it.getBlockName()) }
                .map { it.parent }
                .filter { it != attribute.parent }
                .map { it.textRange }
        return htlVariables
                .filter { scopeRange.contains(it.textOffset) }
                .filter { isNotOverridden(it, overridingScopes) }
    }

    private fun isNotOverridden(htlVariable: HtlVariable, redeclarationScopes: List<TextRange>) =
            redeclarationScopes.none { it.contains(htlVariable.textOffset) }

    private fun referencesAfterDeclaration(attribute: XmlAttribute, variableIdentifier: String, htlVariables: List<HtlVariable>): Collection<HtlVariable> {
        val overridingScopes = PsiTreeUtil.findChildrenOfType(attribute.containingFile.viewProvider.getPsi(HTMLLanguage.INSTANCE), XmlAttribute::class.java)
                .filter { it.getVariableIdentifier().equals(variableIdentifier, ignoreCase = true) }
                .filter { isScopedVariableBlock(it.getBlockName()) }
                .map { it.parent }
                .map { it.textRange }
        return htlVariables
                .filter { it.textOffset > attribute.textOffset }
                .filter { isNotOverridden(it, overridingScopes) }
    }

}

class HtlVariableBlockReference(private val xmlAttribute: XmlAttributeImpl) : XmlAttributeReference(xmlAttribute) {

    override fun resolve() = xmlAttribute

}
