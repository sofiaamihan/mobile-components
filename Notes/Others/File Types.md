#Kotlin
# Class
A blueprint for creating objects, containing properties (variables) and methods (functions).
# File
Container for code.
# Interface
A contract that defines a set of methods that implementing classes must provide.
- It can also contain properties
- Define common behaviours that can be shared across different classes
- Like shared functionalities across components
## Sealed Interface
A special type of interface that restricts which classes can implement it
- Useful for representing a restricted hierarchy of types, often used in state management or when defining a set of related types (like different UI states)
# Data Class
A special class in Kotlin that is primarily used to hold data
- Automatically provides implementations for common methods like equals(), hashCode(), toString()

## Abstract Data Class
A class that cannot be instantiated on its own and may contain abstract members that must be implemented by subclasses.
- Used to define a common structure for a group of related data classes while allowing for some shared implementation

# Enum Class
A special class that represents a fixed set of constants

# Annotation
A special class that is used to attach metadata to code elements (classes, functions, properties, etc)
- @Composable is an annotation

# Kotlin Script
A file with a .kts extension that can be executed as a script - For automation, configuration, or quick prototyping

# Kotlin Worksheet
An interactive environment for writing and executing Kotlin code snippets, It allows for immediate feedback and experimentation

# Object
A singleton instance of a class
- Can contain properties and methods and is defined using the object keywork
- Often used for utility functions, singletons, or to hold state that should be shared across the application