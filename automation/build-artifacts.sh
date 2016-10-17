#!/bin/bash -ex

# Clean and then create the artifacts directory:
artifacts="exported-artifacts"
rm -rf "${artifacts}"
mkdir -p "${artifacts}"

# Create a settings file that uses the our artifactory server as
# proxy for all repositories:
settings="$(pwd)/settings.xml"
cat > "${settings}" <<.
<settings>
  <mirrors>

    <mirror>
      <id>ovirt-artifactory</id>
      <url>http://artifactory.ovirt.org/artifactory/ovirt-mirror</url>
      <mirrorOf>*</mirrorOf>
    </mirror>

    <mirror>
      <id>maven-central</id>
      <url>http://repo.maven.apache.org/maven2</url>
      <mirrorOf>*</mirrorOf>
    </mirror>

  </mirrors>
</settings>
.

# Clean and then create the local repository:
repository="repository"
rm -rf "${repository}"
mkdir -p "${repository}"

# Prepare an alias for the "mvn" command including all
# the required options:
mvn="mvn -s ${settings} -Dmaven.repo.local=${repository}"

# We will need the name of the package in several places:
name="ovirt-engine-api-model"

# Extract the version number from the POM:
version="$(./automation/version.py)"

# If this is a snapshot then the tarball name should contain a suffix
# with the date and the git hash:
if [[ "${version}" =~ -SNAPSHOT$ ]]; then
  version="${version%%-SNAPSHOT}"
  date="$(date --utc +%Y%m%d)"
  commit="$(git log -1 --pretty=format:%h)"
  suffix=".${date}git${commit}"
else
  suffix=""
fi

# Generate the tarball:
tarball="${name}-${version}${suffix}.tar.gz"
git archive \
  --prefix="${name}-${version}/" \
  --output="${tarball}" \
  HEAD

# There may be several versions of Java installed in the build
# enviroment, and we need to make sure that Java 8 is used, as
# it is required by the code generator:
export JAVA_HOME="/usr/lib/jvm/java-1.8.0"

# Move the tarball to the artifacts directory:
mv "${tarball}" "${artifacts}"

# Build the artifacts:
${mvn} package -DskipTests

# Generate the JSON and XML descriptions of the model:
${mvn} validate -Pdescribe
for format in json xml
do
  mv target/model.${format} "${artifacts}"
done

# Generate the documentation:
${mvn} validate -Pdocument,pdf
for format in adoc html pdf csv
do
  mv target/generated-${format}/* "${artifacts}"
done

# Deploy the artifacts to the artifacts directory:
${mvn} deploy -DaltDeploymentRepository="local::default::file://${PWD}/${artifacts}"
