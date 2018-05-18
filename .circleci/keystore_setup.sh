#!/bin/bash

export KEYSTORE_PROPERTIES=~/repo/keystores/keystore.properties
export STORE_FILE_LOCATION=~/repo/keystores/keyfile.jks

function copyEnvVarsToProperties {
    echo "Keystore file should exist at $STORE_FILE_LOCATION"
    echo "Keystore Properties should exist at $KEYSTORE_PROPERTIES"
    mkdir -p ~/repo/keystores
    touch $KEYSTORE_PROPERTIES
    touch $STORE_FILE_LOCATION
    echo "keyAlias=$KEY_ALIAS" >> ${KEYSTORE_PROPERTIES}
    echo "keyPassword=$KEY_PASSWORD" >> ${KEYSTORE_PROPERTIES}
    echo "storeFile=$STORE_FILE" >> ${KEYSTORE_PROPERTIES}
    echo "storePassword=$STORE_PASSWORD" >> ${KEYSTORE_PROPERTIES}
}

# decode key store from remote location
function decodeKeyStoreFile {
    echo "Decoding KEYSTORE_FILE to $STORE_FILE_LOCATION ..."
    echo $KEYSTORE_FILE | base64 --decode >> $STORE_FILE_LOCATION
    cat $STORE_FILE_LOCATION
}

# execute functions
copyEnvVarsToProperties
decodeKeyStoreFile