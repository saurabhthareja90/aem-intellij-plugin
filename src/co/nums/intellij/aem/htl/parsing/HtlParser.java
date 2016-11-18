// This is a generated file. Not intended for manual editing.
package co.nums.intellij.aem.htl.parsing;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static co.nums.intellij.aem.htl.psi.HtlTokenTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class HtlParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ATOM) {
      r = atom(b, 0);
    }
    else if (t == BINARY_OP) {
      r = binaryOp(b, 0);
    }
    else if (t == COMPARISON_OP) {
      r = comparisonOp(b, 0);
    }
    else if (t == COMPARISON_TERM) {
      r = comparisonTerm(b, 0);
    }
    else if (t == EXPR_NODE) {
      r = exprNode(b, 0);
    }
    else if (t == EXPRESSION) {
      r = expression(b, 0);
    }
    else if (t == FACTOR) {
      r = factor(b, 0);
    }
    else if (t == FIELD) {
      r = field(b, 0);
    }
    else if (t == OPERATOR) {
      r = operator(b, 0);
    }
    else if (t == OPTION) {
      r = option(b, 0);
    }
    else if (t == OPTION_LIST) {
      r = optionList(b, 0);
    }
    else if (t == SIMPLE) {
      r = simple(b, 0);
    }
    else if (t == STRING_LITERAL) {
      r = string_literal(b, 0);
    }
    else if (t == TERM) {
      r = term(b, 0);
    }
    else if (t == TEXT_FRAGMENT) {
      r = textFragment(b, 0);
    }
    else if (t == VALUE_LIST) {
      r = valueList(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return interpolation(b, l + 1);
  }

  /* ********************************************************** */
  // IDENTIFIER
  //      | string_literal
  //      | INTEGER_NUMBER
  //      | FLOAT_NUMBER
  //      | boolean_constant
  public static boolean atom(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atom")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ATOM, "<atom>");
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = string_literal(b, l + 1);
    if (!r) r = consumeToken(b, INTEGER_NUMBER);
    if (!r) r = consumeToken(b, FLOAT_NUMBER);
    if (!r) r = boolean_constant(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // comparisonTerm (operator comparisonTerm)*
  public static boolean binaryOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binaryOp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BINARY_OP, "<binary op>");
    r = comparisonTerm(b, l + 1);
    r = r && binaryOp_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (operator comparisonTerm)*
  private static boolean binaryOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binaryOp_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!binaryOp_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "binaryOp_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // operator comparisonTerm
  private static boolean binaryOp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "binaryOp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = operator(b, l + 1);
    r = r && comparisonTerm(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // BOOLEAN_TRUE | BOOLEAN_FALSE
  static boolean boolean_constant(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "boolean_constant")) return false;
    if (!nextTokenIs(b, "", BOOLEAN_FALSE, BOOLEAN_TRUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BOOLEAN_TRUE);
    if (!r) r = consumeToken(b, BOOLEAN_FALSE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '>'
  //                | '<'
  //                | '>='
  //                | '<='
  //                | '=='
  //                | '!='
  public static boolean comparisonOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comparisonOp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPARISON_OP, "<comparison op>");
    r = consumeToken(b, GT);
    if (!r) r = consumeToken(b, LT);
    if (!r) r = consumeToken(b, GEQ);
    if (!r) r = consumeToken(b, LEQ);
    if (!r) r = consumeToken(b, EQ);
    if (!r) r = consumeToken(b, NEQ);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // factor [comparisonOp factor]
  public static boolean comparisonTerm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comparisonTerm")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPARISON_TERM, "<comparison term>");
    r = factor(b, l + 1);
    r = r && comparisonTerm_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [comparisonOp factor]
  private static boolean comparisonTerm_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comparisonTerm_1")) return false;
    comparisonTerm_1_0(b, l + 1);
    return true;
  }

  // comparisonOp factor
  private static boolean comparisonTerm_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comparisonTerm_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = comparisonOp(b, l + 1);
    r = r && factor(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // binaryOp [TERNARY_QUESTION_OP binaryOp TERNARY_BRANCHES_OP binaryOp]
  public static boolean exprNode(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprNode")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPR_NODE, "<expr node>");
    r = binaryOp(b, l + 1);
    r = r && exprNode_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [TERNARY_QUESTION_OP binaryOp TERNARY_BRANCHES_OP binaryOp]
  private static boolean exprNode_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprNode_1")) return false;
    exprNode_1_0(b, l + 1);
    return true;
  }

  // TERNARY_QUESTION_OP binaryOp TERNARY_BRANCHES_OP binaryOp
  private static boolean exprNode_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exprNode_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TERNARY_QUESTION_OP);
    r = r && binaryOp(b, l + 1);
    r = r && consumeToken(b, TERNARY_BRANCHES_OP);
    r = r && binaryOp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '${' exprNode? ['@' optionList] '}'
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    if (!nextTokenIs(b, EXPR_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXPR_START);
    r = r && expression_1(b, l + 1);
    r = r && expression_2(b, l + 1);
    r = r && consumeToken(b, EXPR_END);
    exit_section_(b, m, EXPRESSION, r);
    return r;
  }

  // exprNode?
  private static boolean expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1")) return false;
    exprNode(b, l + 1);
    return true;
  }

  // ['@' optionList]
  private static boolean expression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_2")) return false;
    expression_2_0(b, l + 1);
    return true;
  }

  // '@' optionList
  private static boolean expression_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPTIONS_SEPARATOR);
    r = r && optionList(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // term
  //          | '!' term
  public static boolean factor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FACTOR, "<factor>");
    r = term(b, l + 1);
    if (!r) r = factor_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '!' term
  private static boolean factor_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "factor_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NOT);
    r = r && term(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, FIELD, r);
    return r;
  }

  /* ********************************************************** */
  // (expression | textFragment)*
  static boolean interpolation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interpolation")) return false;
    int c = current_position_(b);
    while (true) {
      if (!interpolation_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "interpolation", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // expression | textFragment
  private static boolean interpolation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interpolation_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    if (!r) r = textFragment(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '&&'
  //            | '||'
  public static boolean operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operator")) return false;
    if (!nextTokenIs(b, "<operator>", AND, OR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPERATOR, "<operator>");
    r = consumeToken(b, AND);
    if (!r) r = consumeToken(b, OR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER ['=' exprNode]
  public static boolean option(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && option_1(b, l + 1);
    exit_section_(b, m, OPTION, r);
    return r;
  }

  // ['=' exprNode]
  private static boolean option_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option_1")) return false;
    option_1_0(b, l + 1);
    return true;
  }

  // '=' exprNode
  private static boolean option_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSIGN);
    r = r && exprNode(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // option (',' option)*
  public static boolean optionList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "optionList")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = option(b, l + 1);
    r = r && optionList_1(b, l + 1);
    exit_section_(b, m, OPTION_LIST, r);
    return r;
  }

  // (',' option)*
  private static boolean optionList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "optionList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!optionList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "optionList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' option
  private static boolean optionList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "optionList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && option(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // atom
  //          | '(' exprNode ')'
  //          | '[' valueList ']'
  //          | '[' ']'
  public static boolean simple(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SIMPLE, "<simple>");
    r = atom(b, l + 1);
    if (!r) r = simple_1(b, l + 1);
    if (!r) r = simple_2(b, l + 1);
    if (!r) r = simple_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '(' exprNode ')'
  private static boolean simple_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_PARENTH);
    r = r && exprNode(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARENTH);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' valueList ']'
  private static boolean simple_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_BRACKET);
    r = r && valueList(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' ']'
  private static boolean simple_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_BRACKET);
    r = r && consumeToken(b, RIGHT_BRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SINGLE_QUOTED_STRING | DOUBLE_QUOTED_STRING
  public static boolean string_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_literal")) return false;
    if (!nextTokenIs(b, "<string literal>", DOUBLE_QUOTED_STRING, SINGLE_QUOTED_STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRING_LITERAL, "<string literal>");
    r = consumeToken(b, SINGLE_QUOTED_STRING);
    if (!r) r = consumeToken(b, DOUBLE_QUOTED_STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // simple ('[' exprNode ']' | '.' field)*
  public static boolean term(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TERM, "<term>");
    r = simple(b, l + 1);
    r = r && term_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('[' exprNode ']' | '.' field)*
  private static boolean term_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!term_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "term_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // '[' exprNode ']' | '.' field
  private static boolean term_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = term_1_0_0(b, l + 1);
    if (!r) r = term_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' exprNode ']'
  private static boolean term_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_BRACKET);
    r = r && exprNode(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // '.' field
  private static boolean term_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "term_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && field(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // HTML_FRAGMENT+ | ESC_EXPR+
  public static boolean textFragment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "textFragment")) return false;
    if (!nextTokenIs(b, "<text fragment>", ESC_EXPR, HTML_FRAGMENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TEXT_FRAGMENT, "<text fragment>");
    r = textFragment_0(b, l + 1);
    if (!r) r = textFragment_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // HTML_FRAGMENT+
  private static boolean textFragment_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "textFragment_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, HTML_FRAGMENT);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, HTML_FRAGMENT)) break;
      if (!empty_element_parsed_guard_(b, "textFragment_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // ESC_EXPR+
  private static boolean textFragment_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "textFragment_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ESC_EXPR);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, ESC_EXPR)) break;
      if (!empty_element_parsed_guard_(b, "textFragment_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // exprNode (',' exprNode)*
  public static boolean valueList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE_LIST, "<value list>");
    r = exprNode(b, l + 1);
    r = r && valueList_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' exprNode)*
  private static boolean valueList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!valueList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "valueList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' exprNode
  private static boolean valueList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && exprNode(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
