package org.example.loaders;

import java.util.List;

public interface Loader {
    List<?> load(String path);
}
