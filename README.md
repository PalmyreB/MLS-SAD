# MLS SAD

Software Architectural Defects (SAD) in Multi-Language Systems (MLS)

This project aims at becoming an extension of [Ptidej](https://github.com/ptidejteam/v5.2) with support for multi-language systems (Java and C/C++ with JNI). Its purpose is to find code smells and anti-patterns linked to the use of multiple languages.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Running

Run `/MLS SAD/src/mlssad/DetectCodeSmellsAndAntiPatterns.java` with the path to a directory or file as the only argument. The program will output a CSV file with the code smells and anti-patterns detected in the input source.

### Customizing

Change the parameters in `/MLS SAD/rsc/config.properties` to adapt to your needs.

## Running the tests

The directory `/MLS SAD Tests` contains tests for each code smell and anti-pattern individually, and two test suites.

## Built With

* [srcML](http://srcml.org/) - A parsing tool to convert code to srcML, a subset of XML
* [Apache Commons Compress](http://commons.apache.org/proper/commons-compress/) - A library for working with archives

## Authors

See the list of [contributors](https://github.com/PalmyreB/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the (upcoming) [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

Inspired by the SAD tool in [Ptidej](https://github.com/ptidejteam/v5.2)

