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

package com.intellij.lang.ognl.psi.binaryexpression;

import com.intellij.lang.ognl.psi.OgnlTokenTypes;

/**
 * @author Yann C&eacute;bron
 */
public class BitwiseBooleanBinaryExpressionPsiTest extends BinaryExpressionPsiTestCase {

  public void testBitwiseAnd() {
    assertConstantBinaryExpression("true & false", true, OgnlTokenTypes.AND, false);
  }

  public void testBitwiseAndKeyword() {
    assertConstantBinaryExpression("true band false", true, OgnlTokenTypes.BAND_KEYWORD, false);
  }

  public void testBitwiseOr() {
    assertConstantBinaryExpression("true | false", true, OgnlTokenTypes.OR, false);
  }

  public void testBitwiseOrKeyword() {
    assertConstantBinaryExpression("true bor false", true, OgnlTokenTypes.BOR_KEYWORD, false);
  }

  public void testBitwiseXor() {
    assertConstantBinaryExpression("true ^ false", true, OgnlTokenTypes.XOR, false);
  }

  public void testBitwiseXorKeyword() {
    assertConstantBinaryExpression("true xor false", true, OgnlTokenTypes.XOR_KEYWORD, false);
  }

}