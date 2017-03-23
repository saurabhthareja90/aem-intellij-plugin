package co.nums.intellij.aem.htl.psi.extensions

import co.nums.intellij.aem.htl.HtlBlocks
import co.nums.intellij.aem.htl.service.HtlDefinitions
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlToken

private val htlBlocksNames = HtlDefinitions.blocks.map { it.type }
private val htlVariableBlocksNames = HtlDefinitions.blocks.filter { HtlBlocks.VARIABLE_BLOCKS.contains(it.type) }.map { it.type }

fun XmlAttribute.isHtlBlock() = (this.firstChild as? XmlToken)?.isHtlBlock() ?: false

fun XmlAttribute.isHtlVariableBlock(): Boolean {
    return htlVariableBlocksNames.contains(getBlockType())
}

private fun XmlAttribute.getBlockType() = (this.firstChild as? XmlToken)?.text?.substringBefore(".")?.toLowerCase()


fun XmlToken.isHtlBlock() = htlBlocksNames.contains(this.text.substringBefore(".").toLowerCase())

fun XmlAttribute.isHtlUseBlock() = HtlBlocks.USE.equals(getBlockType())
