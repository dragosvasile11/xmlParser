# <p align="center">XML Parser</p>

## About the project
    
    This app is a parser from XML file to multiple XML files.
    It is waiting to receive XML files on the server, to read them and process the data. After the data processing, new XML files are created for different suppliers.
    Add orders##.xml (where ## are digits) file to the input directory and you will receive the processed xml file(s) in the output directory.

## Features

    - WatchService: is a file manager, looking for changes on the input directory.
    - XML Stax Cursor: used to read and write XML files (sent them to output directory) in a memory-constrained environment, where the highest priority is the performance.
    - TransformerFactory: used to preetify the xml file content.
    - Validations: before reading the file, the program is checking if the file name has the correct pattern.
    - Logger: adding log messages for a better understanding of the program for programmers.
    - Junit testing for methods. (working in progress)

## Getting started

    You'll need Java 17 installed.

    In the ConfigFileReader change configPath accordingly to your folder.

    In the config.properties file change the input and output paths accordingly to your folders.

    In order to open the pom.xml file, to start the program, you will need to install Maven.
```
When you are in the root directory of the project run the command:

$ mvn clean install
```
```
Verify that maven is installed:

$ mvn --version
```
```
Build project:

$ mvn package
```
```
You may test the newly compiled and packaged JAR with the following command:

$ java -cp target/my-app-1.0-SNAPSHOT.jar org.example.parser.XmlParser
```

