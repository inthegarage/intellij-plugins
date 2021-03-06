/*
 * Copyright 2011 The authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.lang.ognl.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiType;
import org.jetbrains.annotations.NotNull;

/**
 * {@code condition ? then : else}.
 *
 * @author Yann C&eacute;bron
 */
public class OgnlConditionalExpression extends OgnlExpressionBase {

  public OgnlConditionalExpression(@NotNull final ASTNode node) {
    super(node);
  }

  @Override
  public PsiType getType() {
    return getThen().getType();
  }

  @NotNull
  private OgnlExpression findExpression(final int index) {
    final OgnlExpression[] expression = findChildrenByClass(OgnlExpression.class);
    assert index <= expression.length : "no expression at " + index + " '" + getText() + "'";
    return expression[index];
  }

  @NotNull
  public OgnlExpression getCondition() {
    return findExpression(0);
  }

  @NotNull
  public OgnlExpression getThen() {
    return findExpression(1);
  }

  @NotNull
  public OgnlExpression getElse() {
    return findExpression(2);
  }

}