package com.google.jstestdriver.idea.execution.generator;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class JstdConfigStructure {

  private List<File> myLoadFiles = Lists.newArrayList();
  private static Set<File> FILE_SYSTEM_ROOTS = Sets.newHashSet(Arrays.asList(File.listRoots()));

  public void addLoadFile(File loadFile) {
    myLoadFiles.add(loadFile);
  }

  @NotNull
  public String asFileContent(File jstdConfigFileDir) {
    StringBuilder builder = new StringBuilder();
    builder.append("# Generated by IDEA\n");
    Pair<String, List<String>> result = generateBasePathAndLoadPaths(jstdConfigFileDir);
    String basePath = result.getFirst();
    if (!basePath.isEmpty()) {
      builder.append("basepath: ").append(basePath).append("\n\n");
    }
    builder.append("load:\n");
    for (String path : result.getSecond()) {
      builder.append("  - ").append(path).append('\n');
    }
    return builder.toString();
  }

  private File findLCA(List<File> files) {
    List<List<File>> allParents = new ArrayList<List<File>>();
    for (File file : files) {
      File dir = file.isDirectory() ? file : file.getParentFile();
      List<File> parents = buildParents(dir);
      allParents.add(parents);
    }
    List<File> common = null;
    for (List<File> next : allParents) {
      if (common == null) {
        common = next;
      } else {
        List<File> newCommon = new ArrayList<File>();
        for (int i = 0; i < Math.min(common.size(), next.size()); i++) {
          if (common.get(i).equals(next.get(i))) {
            newCommon.add(common.get(i));
          } else {
            break;
          }
        }
        common = newCommon;
      }
    }
    if (common == null) {
      throw new RuntimeException();
    }
    return common.get(common.size() - 1);
  }

  @NotNull
  private Pair<String, List<String>> generateBasePathAndLoadPaths(final File jstdConfigDir) {
    File loadFilesLCA = findLCA(myLoadFiles);
    File configLCA = findLCA(Arrays.asList(loadFilesLCA, jstdConfigDir));
    List<String> upPath = path(jstdConfigDir, configLCA);
    List<String> basePathList = path(loadFilesLCA, configLCA);
    basePathList.addAll(Collections.nCopies(upPath.size(), ".."));
    Collections.reverse(basePathList);

    String basePath = StringUtil.join(basePathList, "/");
    List<String> loadFilesPathList = Lists.newArrayList();
    for (File loadFile : myLoadFiles) {
      List<String> loadFilePath = path(loadFile, loadFilesLCA);
      Collections.reverse(loadFilePath);
      loadFilesPathList.add(StringUtil.join(loadFilePath, "/"));
    }
    return Pair.create(basePath, loadFilesPathList);
  }

  private List<String> path(File child, File ancestor) {
    List<String> fileNames = Lists.newArrayList();
    File file = child;
    while (file != null && !file.equals(ancestor)) {
      fileNames.add(file.getName());
      file = file.getParentFile();
    }
    return fileNames;
  }

  private List<File> buildParents(@NotNull final File dir) {
    List<File> parents = Lists.newArrayList();
    File f = dir;
    while (f != null) {
      parents.add(f);
      if (FILE_SYSTEM_ROOTS.contains(f)) {
        break;
      }
      f = f.getParentFile();
    }
    Collections.reverse(parents);
    return parents;
  }

}