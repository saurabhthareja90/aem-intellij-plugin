package co.nums.intellij.aem.htl.psi.references

import co.nums.intellij.aem.htl.psi.patterns.HtlPatterns
import com.intellij.psi.*

class HtlVariablesReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(HtlPatterns.htlVariableDeclaration, HtlVariablesReferencesProvider())
    }

}
