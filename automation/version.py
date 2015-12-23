#!/usr/bin/python

import lxml.etree
import os
import sys


def main():
    # Check that the POM file exists:
    path = "pom.xml"
    if not os.path.exists(path):
        print("The POM file \"%s\" doesn't exist." % path)
        sys.exit(1)

    # Parse the POM document:
    try:
        document = lxml.etree.parse(path)
    except lxml.etree.XMLSyntaxError:
        print("Can't parse POM file \"%s\"." % path)
        sys.exit(1)

    # Extract the version number:
    namespaces = {
        "pom": "http://maven.apache.org/POM/4.0.0",
    }
    nodes = document.xpath(
        "/pom:project/pom:version",
        namespaces=namespaces
    )
    if not nodes:
        print("Can't find version number inside POM file \"%s\"." % path)
        sys.exit(1)
    node = nodes[0]
    version = node.text

    # Output the data:
    print(version)

    # Done
    sys.exit(0)


if __name__ == "__main__":
    main()
