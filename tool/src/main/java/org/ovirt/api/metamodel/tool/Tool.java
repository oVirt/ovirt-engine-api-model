/*
Copyright (c) 2015 Red Hat, Inc.

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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.ovirt.api.metamodel.analyzer.ModelAnalyzer;
import org.ovirt.api.metamodel.concepts.Model;

@ApplicationScoped
public class Tool {
    // References to the objects that implement the rules to generate names for Java concepts:
    @Inject private JavaPackages javaPackages;

    // Reference to the object used to generate Java names:
    @Inject
    @Style("versioned")
    private VersionedJavaNames versionedJavaNames;

    // References to the objects used to generate code:
    @Inject private XmlDescriptionGenerator xmlDescriptionGenerator;
    @Inject private JsonDescriptionGenerator jsonDescriptionGenerator;
    @Inject private SchemaGenerator schemaGenerator;
    @Inject private JaxrsGenerator jaxrsGenerator;
    @Inject private StructsGenerator structsGenerator;
    @Inject private XmlSupportGenerator xmlSupportGenerator;
    @Inject private JsonSupportGenerator jsonSupportGenerator;
    @Inject private AsciiDocGenerator docGenerator;
    @Inject private DocReportGenerator reportGenerator;

    // Reference to the object used to add built-in types to the model:
    @Inject private BuiltinTypes builtinTypes;

    // The names of the command line options:
    private static final String MODEL_OPTION = "model";
    private static final String IN_SCHEMA_OPTION = "in-schema";
    private static final String OUT_SCHEMA_OPTION = "out-schema";
    private static final String XML_DESCRIPTION_OPTION = "xml-description";
    private static final String JSON_DESCRIPTION_OPTION = "json-description";
    private static final String JAVA_OPTION = "java";
    private static final String JAXRS_OPTION = "jaxrs";
    private static final String VERSION_PREFIX_OPTION = "version-prefix";
    private static final String DOCS_OPTION = "docs";
    private static final String REPORT_OPTION = "report";

    // Names of options for Java package names:
    private static final String JAXRS_PACKAGE_OPTION = "jaxrs-package";
    private static final String XJC_PACKAGE_OPTION = "xjc-package";
    private static final String TYPES_PACKAGE_OPTION = "types-package";
    private static final String CONTAINERS_PACKAGE_OPTION = "containers-package";
    private static final String BUILDERS_PACKAGE_OPTION = "builders-package";
    private static final String JSON_PACKAGE_OPTION = "json-package";
    private static final String XML_PACKAGE_OPTION = "xml-package";

    public void run(String[] args) throws Exception {
        // Create the command line options:
        Options options = new Options();

        // Options for the locations of files and directories:
        options.addOption(Option.builder()
            .longOpt(MODEL_OPTION)
            .desc("The directory or .jar file containing the source model files.")
            .type(File.class)
            .required(true)
            .hasArg(true)
            .argName("DIRECTORY|JAR")
            .build()
        );

        // Options for the location of the generated XML and JSON model representations:
        options.addOption(Option.builder()
            .longOpt(XML_DESCRIPTION_OPTION)
            .desc(
                "The location of the generated XML description of the model. If not specified then the XML " +
                "description isn't generated.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("FILE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(JSON_DESCRIPTION_OPTION)
            .desc(
                "The location of the generated JSON description of the model. If not specified then the JSON " +
                "description isn't generated.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("FILE")
            .build()
        );

        // Options for the location of the input and output XML schemas:
        options.addOption(Option.builder()
            .longOpt(IN_SCHEMA_OPTION)
            .desc("The XML schema input file.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("FILE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(OUT_SCHEMA_OPTION)
            .desc("The XML schema output file.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("FILE")
            .build()
        );

        // Options for the names of generated Java sources:
        options.addOption(Option.builder()
            .longOpt(JAVA_OPTION)
            .desc("The directory where the generated Java source will be created.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("DIRECTORY")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(JAXRS_OPTION)
            .desc("The directory where the generated JAX-RS source will be created.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("DIRECTORY")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(JAXRS_PACKAGE_OPTION)
            .desc("The name of the Java package for JAX-RS interfaces.")
            .required(false)
            .hasArg(true)
            .argName("PACKAGE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(XJC_PACKAGE_OPTION)
            .desc("The name of the Java package for classes generated by the XJC compiler.")
            .required(false)
            .hasArg(true)
            .argName("PACKAGE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(TYPES_PACKAGE_OPTION)
            .desc("The name of the Java package for the generated type interfaces.")
            .required(false)
            .hasArg(true)
            .argName("PACKAGE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(CONTAINERS_PACKAGE_OPTION)
            .desc("The name of the Java package for the generated type containers.")
            .required(false)
            .hasArg(true)
            .argName("PACKAGE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(BUILDERS_PACKAGE_OPTION)
            .desc("The name of the Java package for the generated type builders.")
            .required(false)
            .hasArg(true)
            .argName("PACKAGE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(JSON_PACKAGE_OPTION)
            .desc("The name of the Java package for the generated JSON readers and writers.")
            .required(false)
            .hasArg(true)
            .argName("PACKAGE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(XML_PACKAGE_OPTION)
            .desc("The name of the Java package for the generated XML readers and writers.")
            .required(false)
            .hasArg(true)
            .argName("PACKAGE")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(VERSION_PREFIX_OPTION)
            .desc("The version prefix to add to the generated Java class names, for example V4.")
            .required(false)
            .hasArg(true)
            .argName("PREFIX")
            .build()
        );

        // Options for the generation of documentation:
        options.addOption(Option.builder()
            .longOpt(DOCS_OPTION)
            .desc("The directory where the generated documentation will be created.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("DIRECTORY")
            .build()
        );
        options.addOption(Option.builder()
            .longOpt(REPORT_OPTION)
            .desc("The file where the documentation report be created.")
            .type(File.class)
            .required(false)
            .hasArg(true)
            .argName("FILE")
            .build()
        );

        // Parse the command line:
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        }
        catch (ParseException exception) {
            System.err.println(exception.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.setSyntaxPrefix("Usage: ");
            formatter.printHelp("metamodel-tool [OPTIONS]", options);
            System.exit(1);
        }

        // Extract the locations of files and directories from the command line:
        File modelFile = (File) line.getParsedOptionValue(MODEL_OPTION);
        File xmlFile = (File) line.getParsedOptionValue(XML_DESCRIPTION_OPTION);
        File jsonFile = (File) line.getParsedOptionValue(JSON_DESCRIPTION_OPTION);
        File inSchemaFile = (File) line.getParsedOptionValue(IN_SCHEMA_OPTION);
        File outSchemaFile = (File) line.getParsedOptionValue(OUT_SCHEMA_OPTION);
        File jaxrsDir = (File) line.getParsedOptionValue(JAXRS_OPTION);
        File javaDir = (File) line.getParsedOptionValue(JAVA_OPTION);
        File docsDir = (File) line.getParsedOptionValue(DOCS_OPTION);
        File reportFile = (File) line.getParsedOptionValue(REPORT_OPTION);

        // Analyze the model files:
        Model model = new Model();
        ModelAnalyzer modelAnalyzer = new ModelAnalyzer();
        modelAnalyzer.setModel(model);
        modelAnalyzer.analyzeSource(modelFile);

        // Add the built-in types to the model:
        builtinTypes.addBuiltinTypes(model);

        // Generate the XML representation of the model:
        if (xmlFile != null) {
            File xmlDir = xmlFile.getParentFile();
            FileUtils.forceMkdir(xmlDir);
            xmlDescriptionGenerator.generate(model, xmlFile);
        }

        // Generate the JSON representation of the model:
        if (jsonFile != null) {
            File jsonDir = jsonFile.getParentFile();
            FileUtils.forceMkdir(jsonDir);
            jsonDescriptionGenerator.generate(model, jsonFile);
        }

        // Extract the version prefix from the command line and copy it to the object that manages names:
        String versionPrefix = line.getOptionValue(VERSION_PREFIX_OPTION);
        if (versionPrefix != null) {
            versionedJavaNames.setVersionPrefix(versionPrefix);
        }

        // Extract the names of the Java packages from the command line and copy them to the object that manages them:
        String[] jaxrsPackages = line.getOptionValues(JAXRS_PACKAGE_OPTION);
        if (jaxrsPackages != null) {
            for (String jaxrsPackage : jaxrsPackages) {
                javaPackages.addJaxrsRule(jaxrsPackage);
            }
        }
        String xjcPackage = line.getOptionValue(XJC_PACKAGE_OPTION);
        if (xjcPackage != null) {
            javaPackages.setXjcPackageName(xjcPackage);
        }
        String typesPackage = line.getOptionValue(TYPES_PACKAGE_OPTION);
        if (typesPackage != null) {
            javaPackages.setTypesPackageName(typesPackage);
        }
        String containersPackage = line.getOptionValue(CONTAINERS_PACKAGE_OPTION);
        if (containersPackage != null) {
            javaPackages.setContainersPackageName(containersPackage);
        }
        String buildersPackage = line.getOptionValue(BUILDERS_PACKAGE_OPTION);
        if (buildersPackage != null) {
            javaPackages.setBuildersPackageName(buildersPackage);
        }
        String jsonPackage = line.getOptionValue(JSON_PACKAGE_OPTION);
        if (jsonPackage != null) {
            javaPackages.setJsonPackageName(jsonPackage);
        }
        String xmlPackage = line.getOptionValue(XML_PACKAGE_OPTION);
        if (xmlPackage != null) {
            javaPackages.setXmlPackageName(xmlPackage);
        }

        // Generate the XML schema:
        if (inSchemaFile != null && outSchemaFile != null) {
            schemaGenerator.setInFile(inSchemaFile);
            schemaGenerator.setOutFile(outSchemaFile);
            schemaGenerator.generate(model);
        }

        // Generate the JAX-RS source:
        if (jaxrsDir != null) {
            FileUtils.forceMkdir(jaxrsDir);
            jaxrsGenerator.setOutDir(jaxrsDir);
            jaxrsGenerator.generate(model);
        }

        // Generate the Java source:
        if (javaDir != null) {
            structsGenerator.setOutDir(javaDir);
            structsGenerator.generate(model);

            // Generate JSON support classes:
            jsonSupportGenerator.setOutDir(javaDir);
            jsonSupportGenerator.generate(model);

            // Generate XML support classes:
            xmlSupportGenerator.setOutDir(javaDir);
            xmlSupportGenerator.generate(model);
        }

        // Generate the documentation:
        if (docsDir != null) {
            docGenerator.setOutDir(docsDir);
            docGenerator.generate(model);
        }
        if (reportFile != null) {
            reportGenerator.setOutFile(reportFile);
            reportGenerator.generate(model);
        }
    }
}
