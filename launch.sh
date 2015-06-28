#!/bin/bash

spark-submit --driver-memory 32g --executor-memory 32g --executor-cores 24 --total-executor-cores 24 --conf spark.tasks.maxFailures=10 --conf spark.shuffle.manager=SORT --conf "spark.executor.extraJavaOptions=-XX:+PrintGCDetails -XX:+PrintGCTimeStamps" --conf spark.default.parallelism=11 --conf spark.io.compression.codec=lzf --class com.ibm.finatra.spark.App target/finatraOnSpark-1.0.0.jar  -com.twitter.finatra.config.port=':7979' -com.twitter.finatra.config.adminPort=':7972' -jars=target/finatraOnSpark-1.0.0.jar -com.twitter.finatra.config.logLevel='DEBUG' -com.twitter.finatra.config.logNode='finatra' -com.twitter.finatra.config.logPath='logs/finatra.log'


exit 0
