#!/bin/bash -xe

# When building on GitHub we should use GITHUB_SHA environment variable, otherwise parse hash from git
GIT_HASH=$(git rev-parse --short ${GITHUB_SHA:-HEAD})

# Directory, where build artifacts will be stored, should be passed as the 1st parameter
ARTIFACTS_DIR=${1:-exported-artifacts}

# Disabling tests can be passed as the 2nd parameter
SKIP_TESTS=${2:-0}

# Get version from Maven (strip -SNAPSHOT suffix if present).
# RPM_VERSION env var overrides this (e.g. extracted from a git tag in CI).
RAW_VERSION=$(mvn help:evaluate -q -DforceStdout -Dexpression=project.version)
VERSION=${RPM_VERSION:-${RAW_VERSION%-SNAPSHOT}}

# RPM release - default to 0.master, overridden by env for tagged/release builds
PACKAGE_RPM_RELEASE=${PACKAGE_RPM_RELEASE:-0.master}

# Release suffix - appended to release for snapshot builds, empty for tagged/release builds
RELEASE_SUFFIX=${RELEASE_SUFFIX:-}

# Prepare source archive
[[ -d rpmbuild/SOURCES ]] || mkdir -p rpmbuild/SOURCES
git archive --format=tar HEAD | gzip -9 > rpmbuild/SOURCES/ovirt-engine-api-model-${VERSION}.tar.gz

# Generate AsciiDoc and HTML documentation
mvn package -Pgenerate-adoc-html -Dadoc.linkcss=true
cp target/doc.jar rpmbuild/SOURCES/ovirt-engine-api-model-doc-${VERSION}.jar

# Set version and release in the spec file
sed \
    -e "s|@VERSION@|${VERSION}|g" \
    -e "s|@PACKAGE_RPM_RELEASE@|${PACKAGE_RPM_RELEASE}|g" \
    -e "s|@SKIP_TESTS@|${SKIP_TESTS}|g" \
    < ovirt-engine-api-model.spec.in \
    > ovirt-engine-api-model.spec

# Build source package
rpmbuild \
    -D "_topdir rpmbuild" \
    --define "release_suffix ${RELEASE_SUFFIX}" \
    -bs ovirt-engine-api-model.spec
