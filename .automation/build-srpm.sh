#!/bin/bash -xe

# When building on GitHub we should use GITHUB_SHA environment variable, otherwise parse has from git
GIT_HASH=$(git rev-parse --short ${GITHUB_SHA:-HEAD})

# Directory, where build artifacts will be stored, should be passed as the 1st parameter
ARTIFACTS_DIR=${1:-exported-artifacts}

# Disabling tests can be passed as the 2nd parameter
SKIP_TESTS=${2:-0}

# Prepare the version string (with support for SNAPSHOT versioning)
VERSION=$(mvn help:evaluate  -q -DforceStdout -Dexpression=project.version)
VERSION=${VERSION/-SNAPSHOT/-0.$(date +%04Y%02m%02d%02H%02M).git${GIT_HASH}}
IFS='-' read -ra VERSION <<< "$VERSION"
RELEASE=${VERSION[1]-1}

# Prepare source archive
[[ -d rpmbuild/SOURCES ]] || mkdir -p rpmbuild/SOURCES
git archive --format=tar HEAD | gzip -9 > rpmbuild/SOURCES/ovirt-engine-api-model-$VERSION.tar.gz

# Set the location of the JDK that will be used for compilation:
export JAVA_HOME="${JAVA_HOME:=/usr/lib/jvm/java-11}"

# Generate AsciiDoc and HTML documentation
mvn package -Pgenerate-adoc-html -Dadoc.linkcss=true
cp target/doc.jar rpmbuild/SOURCES/ovirt-engine-api-model-doc-$VERSION.jar

# Set version and release
sed \
    -e "s|@VERSION@|${VERSION}|g" \
    -e "s|@RELEASE@|${RELEASE}|g" \
    -e "s|@SKIP_TESTS@|${SKIP_TESTS}|g" \
    < ovirt-engine-api-model.spec.in \
    > ovirt-engine-api-model.spec

# Build source package
rpmbuild \
    -D "_topdir rpmbuild" \
    -bs ovirt-engine-api-model.spec
