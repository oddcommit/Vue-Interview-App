#!/bin/bash
source ./helper_tools.sh

scriptDirectory=$(get_relative_path_of_script_directory)
backendFolder=$scriptDirectory/../backend
pomFile=$backendFolder/pom.xml

prepare_pom_for_release() {
    echo "Prepare 'pom.xml' file for production..."
    replaceText "<release.type>SNAPSHOT" "<release.type>RELEASE" "$pomFile" || exit 1
    replaceText "spring.profiles.active=development" "spring.profiles.active=kubernetes" "$pomFile" || exit 1
    echo "'pom.xml' successfully prepared..."
}

prepare_pom_for_release
