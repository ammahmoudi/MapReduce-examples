# MapReduce-examples
MapReduce Examples using pure Scala and Then using Spark.

![spark scala](/cover.jpg)

The repository has two folders: scala and spark. The scala folder contains pure Scala implementations of some common MapReduce algorithms, such as word count, inverted index, and matrix multiplication. The spark folder contains Spark implementations of the same algorithms, using Spark's built-in APIs, such as RDDs, DataFrames, and Datasets.

To run the examples, you need to have Scala and Spark installed on your machine. You also need to have SBT, a build tool for Scala projects. You can use the following commands to compile and run the examples:

- To compile the Scala examples, go to the scala folder and run `sbt compile`.
- To run the Scala examples, go to the scala folder and run `sbt run`. You will see a list of options to choose which example to run. Enter the number of the example and press enter.
- To compile the Spark examples, go to the spark folder and run `sbt package`. This will create a JAR file in the target folder.
- To run the Spark examples, go to the spark folder and run `spark-submit --class <class name> target/scala-2.12/mapreduce-examples_2.12-0.1.jar <input file> <output folder>`. Replace `<class name>` with the name of the example class, such as `WordCount` or `InvertedIndex`. Replace `<input file>` with the path to the input file, such as `data/words.txt` or `data/documents.txt`. Replace `<output folder>` with the path to the output folder, such as `output/wordcount` or `output/invertedindex`.

The input and output files are in the data and output folders, respectively. You can also use your own files as input and output. The output files are in text format, with each line containing a key-value pair separated by a tab. You can view the output files using any text editor or command-line tool, such as `cat` or `less`.

The repository also contains some test cases for the examples, written using ScalaTest, a testing framework for Scala. You can run the test cases using `sbt test` in the scala or spark folder. The test cases will compare the output files with the expected files in the test folder and report any differences or errors.