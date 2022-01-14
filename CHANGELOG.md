# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

### Unreleased

##### Security

- Updated many dependencies, including slf4j to latest 1.7.33

### [0.13.3] - 2020-01-03

##### Security

- Updated slf4j dependency to latest 1.7.30

### [0.13.2] - 2019-11-23

##### Security

- Updated slf4j dependency to latest 1.7.29

### [0.13.1] - 2019-08-26

##### Security

- Updated slf4j dependency to latest 1.7.28

### [0.13.0] - 2019-05-31

##### Added

- Log messages can now be provided by a Supplier. E.g.: `logger.info().log(() -> "Log this")`

### [0.12.0] - 2019-03-19

##### Security

- Updated slf4j version

### [0.11.0] - 2019-02-18

##### Fixed

- Missing marker usage by non location aware loggers

### [0.10.0] - 2019-02-18

##### Added

- Support to `Marker`

##### Fixed

- Support to structured logging libraries

### [0.9.0] - 2019-02-18

##### Fixed

- Calling class issue by using `LocationAwareLogger`

### [0.8.0] - 2019-02-11

##### Added

- `Automatic-Module-Name` to `MANIFEST.MF`

### [0.7.0] - 2019-01-22

##### Added

- Javadoc

### [0.6.0] - 2019-01-17

##### Changed

- API readability improvements

### [0.5.0] - 2019-01-11

##### Added

- Support to log every number of calls or every amount of time

### [0.4.0] - 2019-01-09

##### Changed

- Performance improvements

### [0.3.0] - 2019-01-09

##### Fixed

- Possible concurrency issues

### [0.2.0] - 2019-01-08

##### Changed

- Performance improvements

### [0.1.0] - 2019-01-08

- First implementation
