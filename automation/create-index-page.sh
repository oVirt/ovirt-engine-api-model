#!/usr/bin/env bash

pushd gh-pages
links=$(
    for name in $(ls -rd */ | sed s./$..) ; do
        if  ! [ -f "${name}/index.html" ] ; then
            continue;
        fi
        timestamp=$(grep -A 1 '<div id="footer-text">' ${name}/index.html | tail -n 1)
        warning=''
        if [ $name == master ] ; then
            warning='<span class="warning">(unstable, development version)</span>'
        fi
        echo "
            <p>
                <a href=\"${name}\">
                    <span class=\"name\">${name}</span>
                </a>
                <span class=\"timestamp\">${timestamp}</span>
                ${warning}
            </p>
        "
    done)
popd
noDocumentationFound='<p>No documentation found.</p>'
replacement=${links:=$noDocumentationFound}
template=$(cat automation/index.template.html)
replaced=${template/=== links ===/$replacement}
echo "${replaced}" > gh-pages/index.html
