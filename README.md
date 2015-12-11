# finatraOnSpark
finatraOnSpark runs finatra on Apache Spark. Its a play on the word-count example. Where it does a word count on a file and displays the results in a D3 word-cloud.


#Set Env Variable
Set/Export SPARK_HOME: 

export SPARK_HOME=/Users/venkatesan3/spark-1.4.0

export PATH=$SPARK_HOME/bin:$PATH


#Build the project using Maven
cd to finatraOnSpark

mvn clean install


#Launch Finatra(micro-services):
sh launch.sh


#Notes
Finatra Admin Page : Use the shutdown link shutdown finatra
http://localhost:7972/admin

NOTE: In the links below, provide an absolute path to a file from your local drive. Firefox seems to work better with D3.
Word Cloud: Via the UI 
http://localhost:7979/index.html?input.file=/Users/venkatesan3/temp/npm-debug.log

RestFul Response from Finatra Running on Spark:
http://localhost:7979/wordCount?input.file=/Users/venkatesan3/temp/npm-debug.log

