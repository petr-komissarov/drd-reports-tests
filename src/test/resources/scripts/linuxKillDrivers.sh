#!/bin/bash

export GRADLE_OPTS=-Dfile.encoding=utf-8
killall -r chromedriver geckodriver iedriverserver msedgedriver operadriver phantomjs 2>/dev/null
exit