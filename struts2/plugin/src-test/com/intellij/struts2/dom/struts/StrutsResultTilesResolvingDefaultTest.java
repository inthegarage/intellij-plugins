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

package com.intellij.struts2.dom.struts;

import com.intellij.testFramework.builders.WebModuleFixtureBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Tests for {@link com.intellij.struts2.tiles.TilesResultContributor} /
 * {@link com.intellij.struts2.tiles.Struts2TilesModelProvider} with default configuration (only /WEB-INF/tiles.xml).
 *
 * @author Yann C&eacute;bron
 */
public class StrutsResultTilesResolvingDefaultTest extends BasicStrutsHighlightingTestCase<WebModuleFixtureBuilder> {

  @Override
  @NotNull
  protected String getTestDataLocation() {
    return "strutsXml/resultTilesDefault";
  }

  @Override
  protected Class<WebModuleFixtureBuilder> getModuleFixtureBuilderClass() {
    return WebModuleFixtureBuilder.class;
  }


  @Override
  protected void customizeSetup(final WebModuleFixtureBuilder moduleBuilder) {
    moduleBuilder.addSourceRoot(myFixture.getTempDirPath());
    moduleBuilder.setWebXml(myFixture.getTempDirPath() + "/WEB-INF/web.xml");
    moduleBuilder.addWebRoot(getTestDataPath(), "/");

    addLibrary(moduleBuilder, "struts2-tiles-plugin", STRUTS2_TILES_PLUGIN_JAR);
  }

  public void testHighlighting() throws Throwable {
    myFixture.copyFileToProject("/WEB-INF/web.xml");
    performHighlightingTest("struts-tiles.xml");
  }

  public void testCompletion() throws Throwable {
    createStrutsFileSet("struts-tiles-completion.xml");
    myFixture.copyFileToProject("/WEB-INF/web.xml");
    myFixture.testCompletionVariants("struts-tiles-completion.xml",
                                     "definition1",
                                     "definition2");
  }

}