#!/usr/bin/env bash
#
# Should be executed only by Travis CI which has encrypted keys available.


set -xe


function _load_ssh_key {
    set +x  # hide keys
    openssl aes-256-cbc \
        -K $encrypted_3716b7dfd644_key \
        -iv $encrypted_3716b7dfd644_iv \
        -in automation/travis_rsa.enc \
        -out automation/travis_rsa \
        -d
    set -x
    eval "$(ssh-agent)"
    chmod 0600 automation/travis_rsa
    ssh-add automation/travis_rsa
}


function _init_git_config {
    git config --global user.email "ovirt-engine-api-model@travis-ci.org"
    git config --global user.name "oVirt Engine API Model"
}


function _clone_gh_pages {
    git clone git@github.com:oVirt/ovirt-engine-api-model.git gh-pages
    pushd gh-pages
    git checkout origin/gh-pages -b gh-pages
    popd
}


function _copy_to_master {
    mkdir -p gh-pages/master
    cp target/generated-html/model.html gh-pages/master/index.html
}


function _copy_to_tagged {
    description=$(git describe)
    version=${description:0:3}

    mkdir -p gh-pages/${version}
    cp target/generated-html/model.html gh-pages/${version}/index.html
}


function _push_gh_pages {
    commit=$(git log --format="%H" -n 1)
    description=$(git describe)

    pushd gh-pages
    git add .
    git commit -m "gh-pages ${description} ${commit}"
    git push origin HEAD:gh-pages
    popd
}


_load_ssh_key
_init_git_config
_clone_gh_pages
case "$1" in
    master)
        _copy_to_master
        ;;
    tagged)
        _copy_to_tagged
        ;;
esac
_push_gh_pages
