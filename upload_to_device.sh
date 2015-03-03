#!/bin/bash

# Provide path to .apk file as first argument

echo "Installing to phone"
adb -d install -r $1

