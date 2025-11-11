<img height="50" alt="icons8-forest-96" src="https://github.com/user-attachments/assets/ef59aafe-3de7-4815-9371-cfb141f8aec4" />

[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/MilanVlaski/forestry/blob/main/LICENSE)
<!-- ![Build](https://img.shields.io/github/actions/workflow/status/USER/REPO/ci.yml?branch=main) -->

A web application for tracking **forest inventory**. Using this to teach myself the [Apache Causeway framework](https://causeway.apache.org/). It follows the [Naked Objects](https://wiki.c2.com/?NakedObjects) philosophy, which involves exposing your classes, objects, or entities, directly to the user interface. 

## Quick Start
To use the app, you must have JDK 21 installed, as well as Maven. We suggest installing both using [SDKMAN](https://sdkman.io/) for Linux, by running `sdk maven install` and `sdk install java`.

1. Run `mvn clean install`
2. Run `PROTOTYPING=true mvn -pl webapp spring-boot:run`. On command prompt, it's `set PROTOTYPING=true`.
3. Browse to http://localhost:8080.
4. Press `Generic UI (Wicket)`.
5. Login using:
    - Either as a regular user:  
        **username**: marco  
        **password**: pass
   - or the superuser:  
       **username**: secman-admin  
       **password**: pass

To generate sample data, you can open up the "Prototyping menu" in the top-right, press "Run fixture script", press OK, and navigate the app to see.

## Use cases

- Track trees, to estimate how much yield a forest will provide. 
- Manage Arborists, how many trees they added, etc.
- Also see [use case docs](docs/Use_cases.md)

Use Cases for Apache Causeway can be found in the [Causeway documentation](https://causeway.apache.org/docs/latest/what-is-apache-causeway/common-use-cases.html).

## Documentation
- [Conventions](docs/Conventions.md) - Personal Causeway conventions
- [Domain model](docs/Domain_model.md) - A rough entity diagram.
- [Glossary](docs/Glossary.md) - Explanation of Forestry terms.
- [Test conventions](docs/Test_conventions.md) - A rough testing plan.
- [To do](docs/To_do.md) - A to do list, which is easier to maintain than GitHub issues, for most small work.
- [Use cases](docs/Use_cases.md) - Rough use cases. 

## Status

- Currently being worked on. See [todo list](docs/To_do.md) for progress. 

## How to contribute

1. In progress

## Project Structure

This project contains two types of Maven subprojects:
- Modules - with `{groupId}.modules.{name}` as the Java package format.
- Other - with `{groupId}.{name}` as the Java package format.

Modules encapsulate shared logic, features, and entities (for example, a forest-inventory module). They may depend only on other modules.
Other subprojects can be customized to provide application specific behavior. They may depend on modules.

| Directory           | Description                                                                       | 
|---------------------|-----------------------------------------------------------------------------------|
| forest-inventory    | Entities and features of forest inventory.                                        |
| module-simple       | Example module. Can be used as a template for new modules.                        |
| module-simple-tests | Example test module.                                                              |
| webapp              | Adds web application features to modules. Currently, only for `forest-inventory`. |
| webapp-tests        | For testing the `webapp`. Good for ensuring that modules are properly connected.  |

## Testing

The application has both unit tests and integration tests.

| Test type  | Report                             | Phase             | Skip using   |
|-------------|------------------------------------|-------------------|--------------|
| Unit test   | `target/surefire-unittest-reports` | `test`            | `-DskipUTs`  |
| Integ test  | `target/surefire-integtest-reports`| `integration-test`| `-DskipITs`  |
