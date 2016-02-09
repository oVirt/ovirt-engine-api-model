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
import java.util.Formatter;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * This class is a buffer intended to simplify generation of AsciiDoc documentation.
 */
public class AsciiDocBuffer {
    // The name of the document:
    private String name;

    // The lines of the document:
    private List<String> lines = new ArrayList<>();

    /**
     * Sets the name of the document.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Adds a line.
     */
    public void addLine(String line) {
        lines.add(line);
    }

    /**
     * Adds an empty line.
     */
    public void addLine() {
        addLine("");
    }

    /**
     * Adds a formatted line. The given {@code args} are formatted using the* provided {@code format} using
     * the {@link String#format(String, Object...)} method.
     */
    public void addLine(String format, Object... args) {
        StringBuilder buffer = new StringBuilder();
        Formatter formatter = new Formatter(buffer);
        formatter.format(format, args);
        String line = buffer.toString();
        addLine(line);
    }

    /**
     * Generates a section identifier with the given value.
     */
    public void addId(String value) {
        addLine("[id=\"%s\"]", value);
    }

    /**
     * Generates the complete source code of the document.
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (String line : lines) {
            buffer.append(line);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * Creates a {@code .adoc} file for this document.
     *
     * @param outDir the base directory for the source code
     * @throws IOException if something fails while creating or writing the file
     */
    public void write(File outDir) throws IOException {
        // Create the and all its parent if needed:
        FileUtils.forceMkdir(outDir);
        if (!outDir.exists()) {
            if (!outDir.mkdirs()) {
                throw new IOException("Can't create directory \"" + outDir.getAbsolutePath() + "\"");
            }
        }

        // Write the document:
        File docFile = new File(outDir, name + ".adoc");
        System.out.println("Writing doc file \"" + docFile.getAbsolutePath() + "\".");
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(docFile), Charset.forName("UTF-8"))) {
            writer.write(toString());
        }
    }
}
