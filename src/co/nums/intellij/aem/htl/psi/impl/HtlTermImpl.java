// This is a generated file. Not intended for manual editing.
package co.nums.intellij.aem.htl.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static co.nums.intellij.aem.htl.psi.HtlTokenTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import co.nums.intellij.aem.htl.psi.*;

public class HtlTermImpl extends ASTWrapperPsiElement implements HtlTerm {

  public HtlTermImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull HtlVisitor visitor) {
    visitor.visitTerm(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof HtlVisitor) accept((HtlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<HtlExprNode> getExprNodeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, HtlExprNode.class);
  }

  @Override
  @NotNull
  public List<HtlField> getFieldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, HtlField.class);
  }

  @Override
  @NotNull
  public HtlSimple getSimple() {
    return findNotNullChildByClass(HtlSimple.class);
  }

}
