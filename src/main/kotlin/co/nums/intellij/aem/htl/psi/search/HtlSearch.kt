package co.nums.intellij.aem.htl.psi.search

import co.nums.intellij.aem.htl.data.blocks.HtlBlockVariable
import co.nums.intellij.aem.htl.definitions.HtlBlock
import co.nums.intellij.aem.htl.extensions.*
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute

object HtlSearch {

    private val htlVariableBlocks = HtlBlock.values().filter { it.identifierType.isVariable() }
    private val htlVariableBlockTypes = htlVariableBlocks.map { it.type }

    fun blockVariables(htmlFile: PsiFile): Collection<HtlBlockVariable> {
        return PsiTreeUtil.findChildrenOfType(htmlFile, XmlAttribute::class.java)
                .flatMap { it.toHtlVariables() }
    }

    private fun XmlAttribute.toHtlVariables(): List<HtlBlockVariable> {
        if (this.isHtlBlock()) {
            val blockType = this.nameElement.text.substringBefore(".").toLowerCase()
            if (blockType in htlVariableBlockTypes) {
                val blockDefinition = htlVariableBlocks.find { it.type == blockType } ?: return emptyList()
                return this.createHtlVariables(blockDefinition)
            }
        }
        return emptyList()
    }

    private fun XmlAttribute.createHtlVariables(blockDefinition: HtlBlock): List<HtlBlockVariable> {
        val identifier = this.getHtlVariableIdentifier() ?: return emptyList()
        val dataType = this.getDataType(blockDefinition)
        val variable = HtlBlockVariable(identifier, blockDefinition.identifierType, dataType, this)
        if (blockDefinition.iterable) {
            val listVariable = createImplicitListVariable(identifier, blockDefinition)
            return listOf(variable, listVariable)
        }
        return listOf(variable)
    }

    private fun XmlAttribute.createImplicitListVariable(identifier: String, blockDefinition: HtlBlock): HtlBlockVariable {
        val listIdentifier = identifier + "List"
        val listDataType = this.getDataType(blockDefinition, true)
        return HtlBlockVariable(listIdentifier, blockDefinition.identifierType, listDataType, this)
    }

    private fun XmlAttribute.getDataType(block: HtlBlock, implicitList: Boolean = false): String {
        return when {
            block.type == HtlBlock.USE.type -> this.getUseObjectType() ?: "Use object"
            block.type == HtlBlock.TEST.type -> "Test result"
            block.iterable -> if (implicitList) "Iterable" else "List element" // TODO: resolve Java type
            else -> ""
        }
    }

}
