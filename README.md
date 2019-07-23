# MLS SAD

Software Architectural Defects (SAD) in Multi-Language Systems (MLS)

This project aims at becoming an extension of [Ptidej](https://github.com/ptidejteam/v5.2) with support for multi-language systems (Java and C/C++ with JNI). Its purpose is to find code smells and anti-patterns linked to the use of multiple languages.

## Getting Started

### Running

Run `/MLS SAD/src/mlssad/DetectCodeSmellsAndAntiPatterns.java` with the path to a directory or file as the only argument. The program will output a CSV file with the code smells and anti-patterns detected in the input source.

### Customizing

Change the parameters in `/MLS SAD/rsc/config.properties` to adapt to your needs.

## Running the tests

The directory `/MLS SAD Tests` contains tests for each code smell and anti-pattern individually, and two test suites.

## Next steps

- [ ] Tranform `MLSAntiPattern` class: one of its fields can be a list of `MLSCodeSmell`s
- [ ] Make more obvious the relationship between code smells and anti-patterns they characterize
- [ ] Match each native C/C++ function to the correct Java function
- [ ] In the detector for code smell "Local References Abuse Detection", take these JNI functions into account, if relevant:
  - [ ] [EnsureLocalCapacity](https://docs.oracle.com/javase/7/docs/technotes/guides/jni/spec/functions.html#EnsureLocalCapacity)
  - [ ] [PushLocalFrame](https://docs.oracle.com/javase/7/docs/technotes/guides/jni/spec/functions.html#PushLocalFrame)
  - [ ] [PopLocalFrame](https://docs.oracle.com/javase/7/docs/technotes/guides/jni/spec/functions.html#PopLocalFrame)
  - [ ] [NewLocalRef](https://docs.oracle.com/javase/7/docs/technotes/guides/jni/spec/functions.html#NewLocalRef)
- [ ] Test these detectors on C++ files
  - [ ] `AssumingSelfMultiLanguageReturnValue`
  - [ ] `LocalReferencesAbuse`
  - [ ] `MemoryManagementMismatch`
  - [ ] `NotCachingObjectsElements`
  - [ ] `NotHandlingExceptions`
- [ ] Add unit tests based on real systems
- [ ] Complete documentation and limits
- [ ] Integrate into [Ptidej](https://github.com/ptidejteam/v5.2) suite
- [ ] Improve performance (possibly migrate to [VTD-XML](https://vtd-xml.sourceforge.io) by XimpleWare)
- [x] Refactor `CodeToXML`
- [ ] When there is a code smell in an overloaded function or method, count as multiple code smells instead of one
- [ ] When applicable, write `Enum` name instead of an empty class name

## Limits

- Anti-pattern "Excessive Inter-Language Communication": The case of calls in both ways (Java to C and C to Java) is not treated.
- Code smell "Not Using a Relative Path": The path can be a variable, concatenated strings, an absolute path got thanks to a method applied on a relative path…
- Code smell "Unused parameters": The detector does not work for C++ headers with extension `.h`.
- …

## Built With

* [srcML](http://srcml.org/) - A parsing tool to convert code to srcML, a subset of XML
* [Apache Commons Compress](http://commons.apache.org/proper/commons-compress/) - A library for working with archives

## Authors

See the list of [contributors](https://github.com/PalmyreB/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the (upcoming) [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

Loosely inspired by the SAD tool in [Ptidej](https://github.com/ptidejteam/v5.2)

