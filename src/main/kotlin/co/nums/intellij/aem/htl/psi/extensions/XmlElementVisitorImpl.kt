package co.nums.intellij.aem.htl.psi.extensions

import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.XmlElementVisitor
import com.intellij.psi.templateLanguages.OuterLanguageElement
import com.intellij.psi.xml.XmlTag
import com.intellij.psi.xml.XmlToken
import com.intellij.psi.xml.XmlTokenType

open class XmlElementVisitorImpl : XmlElementVisitor() {

    private var firstElement: XmlTag? = null

    override fun visitXmlToken(token: XmlToken?) {
        if (firstElement == null && XmlTokenType.XML_NAME.equals(token?.tokenType)) {
            var element = token?.prevSibling
            while (element is PsiWhiteSpace) element = element.getPrevSibling()

            if (element is XmlToken && (element).tokenType === XmlTokenType.XML_START_TAG_START) {
                val parent = element.getParent()

                if (parent is XmlTag && token?.nextSibling !is OuterLanguageElement) {
                    firstElement = parent
                }
            }

            super.visitXmlToken(token)
        }
    }

    fun getFirstElement(): XmlTag? {
        return firstElement
    }
}