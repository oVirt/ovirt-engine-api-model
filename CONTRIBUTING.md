# oVirt Engine API Model

## Introduction

This project contains the specification of the oVirt Engine API, also
known as the _model_.

The specification of the API is written using Java as the supporting
language.

Data types are represented by Java interfaces. For example, the `Vm.java`
file contains the specification of the `Vm` entity, which looks like
this:

```java
@Type
public interface Vm extends VmBase {
    String stopReason();
    Date startTime();
    Date stopTime();
    ...
}
```

The methods of these interfaces represent the attributes of the data
types, including their type and name.

Services API are also represented by Java interfaces. For example, the
`VmService.java` file contains the specification of the `Vm` service, and
it has content similar to this:

```java
@Service
public interface VmService extends MeasurableService {
    interface Start {
        @In Boolean pause();
        @In Vm vm();
        @In Boolean useCloudInit();
        @In Boolean useSysprep();
        @In Boolean async();
    }
    ...
}
```

Operations are represented as nested interfaces. The names of these nested
interfaces correspond to the names of the operations, and the methods correspond
to the parameters of the operations.

The Java language supports adding documentation in the code itself, using the
_Javadoc_ comments. These comments start with `/\**` and end with `*/` and can
be added before the definition of any element, like interfaces and methods. These
_Javadoc_ comments are the mechanism that we use to document the specification. For
example, the `Vm` type can be documented modifying the `Vm.java` file like this:

```java
/**
 * Represents a virtual machine.
 */
@Type
public interface Vm extends VmBase {
    ...
}
```

Attributes can be documented in a similar way, just placing the _Javadoc_ comment
before the definition of the method that represents that attribute:

```java
/**
 * Represents a virtual machine.
 */
@Type
public interface Vm extends VmBase {
    /**
     * Contains the reason why this virtual machine was stopped. This reason is
     * provided by the user, via the GUI or via the API.
     */
    String stopReason();
    ...
}
```

Same for services, their operations and their parameters:

```java
/**
 * This service manages a specific virtual machine.
 */
@Service
public interface VmService extends MeasurableService {

    /**
     * This operation will start the virtual machine managed by this
     * service, if it isn't already running.
     */
    interface Start {
        /**
         * Specifies if the virtual machine should be started in pause
         * mode. It is an optional parameter, if not given then the
         * virtual machine will be started normally.
         */
        @In Boolean pause();
        ...
    }
    ...
}
```

These _Javadoc_ comments are processed by tools that are part of the system
and it is used to automatically generate the reference documentation. You
can see an example of the generated documentation
[here](https://jhernand.fedorapeople.org/ovirt-engine-api-model/model.html).

This documentation viewer will eventually be part of the oVirt Engine server
itself.

Regular builds of the documentation are available at project
[Github page](http://ovirt.github.io/ovirt-engine-api-model/).

The _Javadoc_ comments have their own format, but it isn't used by the
our documentation tools. Instead of that the tools expect and support
[AsciiDoc](http://www.methods.co.nz/asciidoc). This means that _Javadoc_
comments can contain rich text and examples. For example, you could write
the following to better describe the `Start` operation of the `Vm` service:

```java
/**
 * Specifies if the virtual machine should be started in pause
 * mode. It is an _optional_ parameter, if not given then the
 * virtual machine will be started normally.
 *
 * To use this parameter with the Python SDK you can use the
 * following code snippet:
 *
 * ```python
 * # Find the virtual machine:
 * vm = api.vms.get(name="myvm")
 *
 * # Start the virtual machine paused:
 * vm.start(
 *   params.Action(
 *     pause=True
 *   )
 * )
 * ```
 */
@In Boolean pause();
```

## Contributing documentation

### Cloning the `ovirt-engine-api-model` repository

The mechanism to contribute documentation is to modify the `.java`
source files. This source code is part of the `ovirt-engine-api-model`
project, which is hosted in
[Github project](http://github.com/ovirt/ovirt-engine-api-model). So
the first step to be able to contribute is to register to that system
and prepare your environment for using `git`.

To summarize, once you have registered and prepared your system to
use `git`, this is the command that you need to execute in order to
clone the `ovirt-engine-api-model` source:

```sh
$ git clone https://github.com/ovirt/ovirt-engine-api-model
```

### Creating a working branch for your changes

Create a working branch off `master` in which to make your changes.

```sh
$ git checkout master
$ git checkout -b my_working_branch
```

Working branches allow you to keep your changes separate from the `master`
branch while they are being reviewed, and work on multiple unrelated
patches at the same time.

### Locating the source file that you want to modify

The model source files are all inside the `src/main/java` directory, so
you will probably want to change to that directory:

```sh
$ cd src/main/java
```

This directory contains two sub-directories: `types` and `services`. The
first is for the specifications of data types and the second for the
specifications of services.

Files are named like the entities, so they should be easy to locate.

### Modifying the source files

You can use your favorite editor to modify the source files. Just make
sure to modify only the _Javadoc_ comments.

You must also add or update in each _Javadoc_ comment the following three
tags, which are used to track updates to the documentation:

. `@author` - The name and email of the person writing or updating
the documentation item.
For example: _@author Juan Hernández <juan.hernandez@redhat.com>_
+
NOTE: Do not overwrite any existing value in this field. Add an additional
`@author` field with your own name on the line below.
+
. `@date` - The date on which the documentation was added or updated.
For example: _@date 06 Jul 2016_

. `@status` - The status of the documentation item. The following
values are accepted:

.. `requires_text` - This status is to be added to items that do not
have any documentation at all.

.. `added` - Added by an engineering team member or other contributor
after they provide initial documentation.

.. `updated_by_docs` - Added by a documentation team member after the
documentation item has been reviewed.

.. `complete` - Added by the project maintainer when both engineering
and documentation agree that the item is complete, and the update
can be merged.

Add these tags below the documentation in the _Javadoc_ comment:

```java
/**
 * Represents a virtual machine.
 * @author Juan Hernández <juan.hernandez@redhat.com>
 * @date 11 Jul 2016
 * @status added
 */
@Type
public interface Vm extends VmBase {
    ...
}
```

### Submitting the changes

Once you are happy with the changes that you made to the documentation
you can prepare and submit a patch. For example, lets assume that you
have modified the `Vm.java` file, this is what you will need to do to
submit the patch:

```sh
$ git add types/Vm.java
$ git commit -s
```

This will open an editor where you can write the commit message. By
default it will probably be `vim`, but you can change it with the
`EDITOR` environment variable:

```sh
$ export EDITOR=my-favorite-editor
$ git add types/Vm.java
$ git commit -s

```

In that editor you will be asked to write a _commit message_. It is
important to write good commit messages, describing the reason for the
change. The first line should be a summary, then a blank line and your
description of the change. For example:

```
Improve the documentation of vm.start

This patch improves the documentation of the "vm.start"
operation, so that it is clear that the default value
of the "pause" parameter is "false".
```

Write the file, and you are ready to submit it:

```sh
$ git push origin HEAD:refs/for/master
```

If this finishes correctly it will give you the URL of the change. Go
there and make sure that there is at least a reviewer for your change.
In case of doubt add [Ori Liel](maito:oliel@redhat.com)
as reviewer.

The reviewer may ask you to do changes to your patch, and will be happy
to assist you with any doubts you have with the tools.

Eventually your patch will be merged and will be part of the reference
documentation distributed with the next release of the software.

### Testing and previewing your changes

If your changes are simple enough there may be no need to test them,
just submit the patch. But if you are making larger changes you may want
to see how they will look like in the generated documentation. To do this
you can generate the `model.html` file containing the description of
the API. To do this you need to use _Maven_. Won't go into the details
of installing and using Maven here, as you can find plenty of resources
online and you will just need to run one simple command:

```sh
$ mvn package
```

This will analyze the model and create the `model.html` inside the
`target/html` directory:

```sh
$ find . -name model.html
target/html/model.html
```

Open it with your browser and check your changes.


## Building

To build this project use the usual Maven command line:

```sh
$ mvn clean install -Pgenerate-adoc-html
```

## Releasing

The project is released to Maven Central via the Sonatype OSSRH
repository.

To perform a release you will need to do the following actions, most of
them automated by the Maven release plugin:

### Prepare the release

This is automated using the Maven release plugin:

```sh
$ mvn release:prepare
```

This will ask you the version numbers to use for the released artifacts
and the version numbers to use after the release. The release version
numbers will be something like 4.0.5, and the version numbers after the
release will be something like 4.0.6-SNAPSHOT. You should use the
defaults unless there is a very good reason to change them.

The result will be two new patches, and a tag added to the local
repository. These patches and tag will *not* be pushed automatically to
the remote repository, so you need to do it manually, first the patches:

```sh
$ git push origin HEAD:refs/for/master
```

This will send the patches for review to [gerrit](https://gerrit.ovirt.org).
Go there, review and merge them. Once the patches are merged the tag can
be pushed:

```sh
$ git push origin 4.0.5
```

### Perform the release

This is also automated using the Maven release plugin. But in this case
it is necessary to sign the artifacts, as both Sonatype OSSRH and Maven
Central require signed artifacts. To sign artifacts the `sign` profile
needs to be activated:

```sh
$ mvn release:perform -Psign
```

NOTE: The artifacts will be signed using your default GPG key, so make
sure you have a valid GPG key available.

This will use the tag to checkout the code from the remote repository,
it will build it, run the tests and, finally, if everything succeeds, it
will upload the signed artifacts to the OSSRH repository.

The rest of the process is manual, using the OSSRH web interface
available [here](https://oss.sonatype.org). Log in with your user name and
password and select the _Staging Repositories_ option. Then use the
search bar in the top right corner to search for `ovirt`. In the result
list you should see you repository, and in the panel below you should
see the details, including the contents of the repository. Inspect
those contents, and when you are satisfied click the _Close_ button.
Wait a bit, maybe clicking the _Refresh_ button a few times, till the
_Release_ button is enabled. Click the _Release_ button, it will ask for
a message, write something like _Release 4.0.5_ and then _OK_. The
release is now ready, and it will be propagated to Maven Central later,
it usually takes around 30 minutes.

## Branding

The build system provides some branding properties that can be used to
customize the generated documentation:

`adoc.separator`:: This indicates the separator that should be used in
the section identifiers generated for the AsciiDoc documentation. By
default the separator is the forward slash, but this isn't compatible
with some publication systems, like [Publican](https://fedorahosted.org/publican),
so this property can be used to change it.

`product.name`:: The value of this property will be used where the
source AsciiDoc files contain the `{product-name}` variable. The default
value is `oVirt`.

`engine.name`:: The value of this property will be used where the
source AsciiDoc files contain the `{engine-name}` variable. The default
value is `oVirt Engine`.

`hypervisor.name`:: The value of this property will be used where the
source AsciiDoc files contain the `{hypervisor-name}` variable. The default
value is `oVirt Node`.

For example, [_Red Hat_](https://www.redhat.com) uses the following
command to build their branded version of the documentation for their
[_RHV_](https://www.redhat.com/en/technologies/virtualization) product:

```sh
$ mvn \
package \
-Dadoc.separator='-' \
-Dproduct.name='Red Hat Virtualization' \
-Dengine.name='Red Hat Virtualization Manager'
```

## Feedback/questions/issues

If you have any question, issue, or feedback please
contact mailto:juan.hernandez@redhat.com[Juan Hernández].
