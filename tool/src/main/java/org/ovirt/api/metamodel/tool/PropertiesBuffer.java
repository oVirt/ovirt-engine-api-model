/*
Copyright (c) 2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.ovirt.api.metamodel.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 * This class is a buffer intended to simplify generation of Java properties file.
 */
public class PropertiesBuffer {

    // The lines of the body of the properties file:
    private List<String> lines = new ArrayList<>();

    /**
     * Adds a line to the body of the class.
     */
    public void addProperty(String key, String value) {
        addLine(String.format("%1$s=%2$s", key, value));
    }

    /**
     * Adds a line to the body of the class.
     */
    public void addLine(String line) {
        lines.add(line);
    }

    /**
     * Adds an empty line to the body of the properties.
     */
    public void addLine() {
        addLine("");
    }

    /**
     * Generates the complete source code of the class.
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        // License:
        buffer.append("#\n");
        buffer.append("# Copyright (c) 2016 Red Hat, Inc.\n");
        buffer.append("# Licensed under the Apache License, Version 2.0 (the \"License\");\n");
        buffer.append("# you may not use this file except in compliance with the License.\n");
        buffer.append("# You may obtain a copy of the License at\n");
        buffer.append("#\n");
        buffer.append("#   http://www.apache.org/licenses/LICENSE-2.0\n");
        buffer.append("#\n");
        buffer.append("# Unless required by applicable law or agreed to in writing, software\n");
        buffer.append("# distributed under the License is distributed on an \"AS IS\" BASIS,\n");
        buffer.append("# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");
        buffer.append("# See the License for the specific language governing permissions and\n");
        buffer.append("# limitations under the License.\n");
        buffer.append("#\n");

        // Body:
        for (String line : lines) {
            buffer.append(line);
            buffer.append("\n");
        }

        return buffer.toString();
    }

    /**
     * Creates a {@code .properties} file for this properties, and writes the content to that file
     * The required intermediate directories will be created if they don't exist.
     *
     * @param outFile the base directory for the properties files
     * @throws IOException if something fails while creating or writing the file
     */
    public void write(File outFile) throws IOException {
        // Create the package directory and all its parent if needed:
        File packageDir = outFile.getParentFile();
        FileUtils.forceMkdir(packageDir);
        if (!packageDir.exists()) {
            if (!packageDir.mkdirs()) {
                throw new IOException("Can't create directory \"" + packageDir.getAbsolutePath() + "\"");
            }
        }

        // Create the package directory and all its parent if needed:
        System.out.println("Writing properties file \"" + outFile.getAbsolutePath() + "\".");
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(outFile), Charset.forName("UTF-8"))) {
            writer.write(toString());
        }
    }
}
