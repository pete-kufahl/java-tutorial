package com.prk.commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public final class ClassUtils {
    /**
     * finds the names of all the classes in the named package
     * @param packageName name of the package
     * @return a set of all the names of the classes in the package
     * @throws IOException if an i/o exception occurs
     */
    public static Set<String> findClassNames(String packageName) throws IOException {
        try (InputStream in = ClassLoader.getSystemResourceAsStream(packageName.replaceAll("\\.", "/"))) {
            if (in == null) {
                throw new IllegalArgumentException("package not found: " + packageName);
            }
            return new BufferedReader(new InputStreamReader(in)).lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(cname -> packageName + "." + cname.substring(0, cname.length() - 6))
                    .collect(Collectors.toUnmodifiableSet());
        }
    }

    public static <T> T newInstance(Class<? extends T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("failed to instantiate class: " + clazz.getName(), e);
        }
    }
}
