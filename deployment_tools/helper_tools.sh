#!/bin/bash

get_relative_path_of_script_directory() {
  relDir="$(dirname -- "$0"; )";
  cd "$relDir" || exit 1;
  scriptDirectory="$( pwd; )";
  echo $scriptDirectory
}


replaceText() {
    # Mac got a slightly different SED (FreeBSD sed)
    if [[ "$(uname)" == "Darwin" ]]; then
      sed -i '' "s/$1/$2/g" "$3"
    else
      sed -i "s/$1/$2/g" "$3"
    fi
}
