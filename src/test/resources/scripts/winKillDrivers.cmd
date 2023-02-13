chcp 65001
set GRADLE_OPTS=-Dfile.encoding=utf-8
taskkill /t /f /im chromedriver* /im geckodriver* /im iedriverserver* /im msedgedriver* /im operadriver* /im phantomjs* > nul 2> nul
exit