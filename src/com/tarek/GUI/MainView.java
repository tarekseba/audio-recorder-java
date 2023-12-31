package com.tarek.GUI;

import com.tarek.GUI.annotations.Page;
import java.awt.Component;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainView extends JFrame {
  JTabbedPane tabs;

  public MainView() {
    super();
    build_tabs(find_pages());
    getContentPane().add(tabs);
    setSize(500, 200);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private void build_tabs(List<Class> pages) {
    tabs = new JTabbedPane(JTabbedPane.TOP);
    pages.stream()
        .forEach(
            clazz -> {
              try {
                Component page = (Component) clazz.getConstructor().newInstance();
                Page metadata = (Page) clazz.getAnnotation(Page.class);
                tabs.addTab(metadata != null ? metadata.title() : "Default", page);
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
  }

  private static List<Class> find_pages() {
    List<Optional<Class>> classes = new ArrayList<Optional<Class>>();
    String package_name = "com.tarek";
    String package_path = package_name.replace(".", "/");
    ClassLoader class_loader = Thread.currentThread().getContextClassLoader();
    Enumeration<URL> enumeration;
    try {
      enumeration = class_loader.getResources(package_path);
      while (enumeration.hasMoreElements()) {
        URL url = enumeration.nextElement();
        File dir = new File(url.getFile());
        find_class(dir, package_name, classes);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (List<Class>)
        classes.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
  }

  private static void find_class(File file, String path, List<Optional<Class>> classes) {
    if (file.isDirectory()) {
      Stream.of(file.listFiles()).forEach(f -> find_class(f, path + "." + f.getName(), classes));
    } else if (file.isFile()) {
      classes.add(load_class(path.replace(".class", ""), Page.class));
    }
  }

  private static <T> Optional<Class> load_class(String path, Class<T> annotation) {
    try {
      Class clazz = Class.forName(path);
      if (clazz.<T>getAnnotation(annotation) != null) {
        return Optional.of(clazz);
      }
      return Optional.empty();
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
