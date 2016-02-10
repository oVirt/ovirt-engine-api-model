package org.ovirt.api.metamodel.tool;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * This class is responsible for bootstrapping the CDI container, creating the application entry point and running it
 * with the command line arguments.
 */
public class MainEnums {
    public static void main(String[] args) throws Exception {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        EnumGenerationTool tool = container.instance().select(EnumGenerationTool.class).get();
        tool.run(args);
        weld.shutdown();
    }
}
