package co.nums.intellij.aem.htl.psi.extensions

import co.nums.intellij.aem.htl.HtlBlocks
import co.nums.intellij.aem.htl.service.HtlDefinitions
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlToken

private val htlBlocksNames = HtlDefinitions.blocks.map { it.type }
private val htlVariableBlocksNames = HtlDefinitions.blocks.filter { HtlBlocks.VARIABLE_BLOCKS.contains(it.type) }.map { it.type }

fun XmlAttribute.isHtlBlock() = (this.firstChild as? XmlToken)?.isHtlBlock() ?: false

fun XmlAttribute.isHtlVariableBlock(): Boolean {
    val blockType = getLowercaseTokenText()?.substringBefore(".")
    return htlVariableBlocksNames.contains(blockType)
}

private fun XmlAttribute.getLowercaseTokenText() = (this.firstChild as? XmlToken)?.text?.toLowerCase()

fun XmlToken.isHtlBlock() = htlBlocksNames.contains(this.text.substringBefore(".").toLowerCase())

fun XmlAttribute.isHtlUseIdentifierBlock() = getLowercaseTokenText()?.contains(Regex(HtlBlocks.USE + "\\..+?")) ?: false

