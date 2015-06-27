# finatraOnSpark
finatraOnSpark does word count in Spark and Word Cloud in the UI


Set/Export SPARK_HOME:
bash-3.2$ echo $SPARK_HOME
/Users/venkatesan3/spark-1.4.0

Unzip the finatraOnSpark.zip

cd to finatraOnSpark

Run Maven 
mvn clean install

Launch Finatra(micro-services):
sh launch.sh

Finatra Admin Page : Use the shutdown link shutdown finatra
http://localhost:7972/admin

NOTE: In the links below, provide an absolute path to a file from your local drive(remove the text in bold/underlined and provide your own file location). Firefox seems to work better with D3.
Word Cloud: Via the UI 
http://localhost:7979/index.html?input.file=/Users/venkatesan3/temp/npm-debug.log

RestFul Response from Finatra Running on Spark:
http://localhost:7979/wordCount?input.file=/Users/venkatesan3/temp/npm-debug.log

