package co.nums.intellij.aem.htl.inspections

import co.nums.intellij.aem.htl.psi.extensions.XmlElementVisitorImpl
import co.nums.intellij.aem.htl.psi.extensions.isHtlUseIdentifierBlock
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.XmlSuppressableInspectionTool
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag

class HtmlMiddleTemplateBlocksVariablesAnnotator : XmlSuppressableInspectionTool() {

    private val MESSAGE = "Use data-sly-use variable statements only on root elements"

    override fun getDisplayName() = "Middle template blocks variables annotator"

    override fun getShortName() = "HmlMiddleTemplateBlocksVariablesAnnotator"

    override fun getGroupDisplayName() = "Htl inspections"

    override fun getDescriptionContextClass() = super.getDescriptionContextClass()

    override fun getStaticDescription() = "Select non root data-sly-use statements variables"

    override fun isEnabledByDefault() = true

    fun checkAttribute(attribute: XmlAttribute, holder: ProblemsHolder, firstElement: XmlTag?) {
        if (attribute.isHtlUseIdentifierBlock() && (firstElement != null && firstElement != attribute.parent)) {
            holder.registerProblem(attribute, MESSAGE, HtmlMiddleTemplateBlocksVariablesFix(attribute, firstElement))
        }
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : XmlElementVisitorImpl() {
            override fun visitXmlFile(file: XmlFile?) {
                super.visitXmlFile(file)
            }

            override fun visitXmlAttribute(attribute: XmlAttribute) {
                checkAttribute(attribute, holder, getFirstElement())
            }
        }
    }
}