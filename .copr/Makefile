.PHONY: installdeps srpm

installdeps:
	dnf -y install git gzip java-11-openjdk-devel make maven ovirt-engine-api-metamodel rpm-build sed unzip

srpm: installdeps
	./.automation/build-srpm.sh exported-artifacts 1
	cp rpmbuild/SRPMS/*.src.rpm $(outdir)
