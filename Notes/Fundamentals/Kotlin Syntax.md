# 1. Basics

### Printing
```kotlin
fun main(){
    print("this does not print a new line at the end")
    println("this does, so use this")
}

// alternative
fun main(args: Array<String>) {
    println(args.contentToString())
}
```
- main function is the entry point of a kotlin application

### Comments
```kotlin
// This is an end-of-line comment

/* This is a block comment
   on multiple lines. */
```

### Variables
```kotlin
val a: Int = 1  // immediate assignment
val b = 2   // `Int` type is inferred
val c: Int  // Type required when no initializer is provided
c = 3       // deferred assignment

var thing: String = "this variable is mutable and can be changed, unlike val"
var thing: String? = null // Adding a ? means the variable can either be String or null

// this is when you indent after a variable
set(value){

}
```
- var and val are both storing values

### String Templates: F-string equivalent
```kotlin
val i = 10
println("i = $i") 
// i = 10

// expressions included
val s = "abc"
println("$s.length is ${s.length}") 
// abc.length is 3
```

### Conditional Expressions: If-statements
```kotlin
fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else if (b > a){
        return b
    } else{
        return "huh"
    )
}

// expressions
fun maxOf(a: Int, b: Int) = if (a > b) a else b
```
- when is also considered a conditional expression

### Operations
```kotlin
|| (disjunction)
&& (conjunction)
! (negation)

++ (increment by 1)
-- (decrement by 1)

+, -, *, / , % (basic math operators)
<, >, <=, >= (comparison operators)

+=, -=, *=, /=, %= (augmented assignment operators)

* (spread operator) // passes each element of an array as individual arguents to your chosen function
```
<aside> 💡

Expressions contain values while statements do not. This does not necessarily mean something will appear on the terminal when the line is run. println() are statements

</aside>

## Keywords
```kotlin
break // Exits the current scope
break@outer // Exits all the scopes it's nested in BUT it has to be declared: @outer for (i..10){}
continue // Skips this iteration and continues on to the next one in the SAME scope
```
BUT YOU HAVE TO TEST THIS ONE OUT ^^

# 2. Functions
```kotlin
// syntax
fun name(parameter: datatype, parameter: datatype): datatype(of what you want to return {
    return data
}

// basic example:
fun sum(a: Int, b: Int): Int {
    return a + b
}

// function expressions
fun sum(a: Int, b: Int) = a + b
```
- unit is a return type that can be omitted and means the function will not return any meaningful values
- EVERYTHING has to be inside a function, and every function will automatically run, you don’t have to call it
- the order of the functions do not matter, so you can call a function in lines before it’s made

# 3. Properties
```kotlin
// full syntax for declaring a property
var <propertyName>[: <PropertyType>] [= <property_initializer>]
    [<getter>]
    [<setter>]

// basic example
class Address {
    var name: String = "Holmes, Sherlock"
    var street: String = "Baker"
    var city: String = "London"
    var state: String? = null
    var zip: String = "123456"
}

// to use a property, simply refer to it by its name
fun copyAddress(address: Address): Address {
    val result = Address() // create instance
    result.name = address.name // access instanse properties
    result.street = address.street
    // ...
    return result
}

// using parameters
class Rectangle(val width: Int, val height: Int) {
    val area: Int // property type is optional since it can be inferred from the getter's return type
        get() = this.width * this.height // indent so that "this" means the val area
}
```
- classes are considered object, so i suppose they’re something you can return
- initialiser, getter, and setter are optional (because honestly I don’t know what that means)

# 4. Classes
```kotlin
// define
class Shape

// syntax
class CapitalLetter(var height: Double, var thing: String){
    var property = height + thing
}
```

### Inheritance
```kotlin
open class Shape

class Rectangle(val height: Double, val length: Double): Shape() {
    // The properties are already initialised when included in the header
    val perimeter = (height + length) * 2
}
```

```kotlin
open class Person(open val name: String, open var age:Int) {}
class Student(override val name: String, override var age:Int): Person(name, age){}
```
- it is declared by : so :Shape()
- classes are final by default so to make a class inheritable, you must mark it with open

### Objects
```kotlin
class Person(name: String, age: Int){
    // PROPERTIES
    val name: String
    val age: Int
    
    init{
        this.name = name
        this.age = age
    }
    
    // METHODS
    fun greet(name: String){
        println("Hello $name"}
}
```

# 5. String Literals
### Escaped strings
```kotlin
val s = "Hello, world!\\n"
```
- escaping means including backslash with special functions such as line breaking
- escape characters
    
### Multiline strings
```kotlin
val text = """
    for (c in "foo")
        print(c)
"""

// function trimMargin() removes leading whitespaces from multiline strings
val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()
```
- no escaping allowed

### String formatting
```kotlin
String.format()

// Formats an integer, adding leading zeroes to reach a length of seven characters
val integerNumber = String.format("%07d", 31416)
println(integerNumber)
// 0031416

// Formats a floating-point number to display with a + sign and four decimal places
val floatNumber = String.format("%+.4f", 3.141592)
println(floatNumber)
// +3.1416

// Formats two strings to uppercase, each taking one placeholder
val helloString = String.format("%S %S", "hello", "world")
println(helloString)
// HELLO WORLD

// Formats a negative number to be enclosed in parentheses, then repeats the same number in a different format (without parentheses) using `argument_index$`.
val negativeNumberInParentheses = String.format("%(d means %1\\$d", -31416)
println(negativeNumberInParentheses)
//(31416) means -31416
```
- accepts a format string and one or more arguments
- contains one placeholder (%) for a given argument, followed by format specifiers
- format specifiers (instructions): flags, width, precision, convertion, etc
    - `%d` for integers, `%f` for floating-point numbers, and `%s` for strings
    - you can also use the `argument_index$` syntax to reference the same argument multiple times within the format string in different formats.

# 6. Loops
### For loops
```kotlin
val items = listOf("apple", "banana", "kiwifruit")
for (item in items) {
    println(item)
}

val items = listOf("apple", "banana", "kiwifruit")
for (index in items.indices) {
    println("item at $index is ${items[index]}")
}

var sum = 0
for (i in 1..100){
		sum += i
}
```

### While loops
```kotlin
val items = listOf("apple", "banana", "kiwifruit")
var index = 0
while (index < items.size) {
    println("item at $index is ${items[index]}")
    index++
}
```

### When expressions
```kotlin
fun describe(obj: Any): String =
    when (obj) {
        1          -> "One"
        "Hello"    -> "Greeting"
        is Long    -> "Long"
        !is String -> {
            "Not a string"
            "This is not a string"
        }
        else       -> "Unknown"
    }
```

```jsx
// Advanced when expressions
fun main(args: Array<String>){
		val x = 10
		when (x) {
				5 -> println("x is 5")
				in 1...10 -> println("x is between 1 and 10")
}
```

### Range
```kotlin
val x = 10
val y = 9
if (x in 1..y+1) {
    println("fits in range")
}

val list = listOf("a", "b", "c")

if (-1 !in 0..list.lastIndex) {
    println("-1 is out of range")
}
if (list.size !in list.indices) {
    println("list size is out of valid list indices range, too")
}

// iterating over a range
for (x in 1..5) {
    print(x)
}

// iterating over a progression
for (x in 1..10 step 2) {
    print(x)
}
println()
for (x in 9 downTo 0 step 3) {
    print(x)
}
```

# 7. Type Checks and Automatic Casts
the is operator checks if an expression is an instance of a type
- if an immutable local variable or property is checked for a specific type, there’s no need to cast it explicitly
```kotlin
fun getStringLength(obj: Any): Int? {
    if (obj is String) {
        // `obj` is automatically cast to `String` in this branch
        return obj.length
    }

    // `obj` is still of type `Any` outside of the type-checked branch
    return null
}

// another example
fun getStringLength(obj: Any): Int? {
    if (obj !is String) return null

    // `obj` is automatically cast to `String` in this branch
    return obj.length
}

// another example
fun getStringLength(obj: Any): Int? {
    // `obj` is automatically cast to `String` on the right-hand side of `&&`
    if (obj is String && obj.length > 0) {
        return obj.length
    }

    return null
}
```

# 8. Data Types
### integer types

|Type|Size (bits)|Min value|Max value|
|---|---|---|---|
|`Byte`|8|-128|127|
|`Short`|16|-32768|32767|
|`Int`|32|-2,147,483,648 (-231)|2,147,483,647 (231 - 1)|
|`Long`|64|-9,223,372,036,854,775,808 (-263)|9,223,372,036,854,775,807 (263 - 1)|

```kotlin
val one = 1 // Int
val threeBillion = 3000000000 // Long
val oneLong = 1L // Long
val oneByte: Byte = 1
```
Int? - the ? is used to indicate that the datatype can either be integer or null
### floating-point types

|Type|Size (bits)|Significant bits|Exponent bits|Decimal digits|
|---|---|---|---|---|
|`Float` (single precision)|32|24|8|6-7|
|`Double` (double precision)|64|53|11|15-16|

```kotlin
val pi = 3.14 // Double
// val one: Double = 1 // Error: type mismatch
val oneDouble = 1.0 // Double

// you need to spceify the float at the end
val e = 2.7182818284 // Double (plus e is a value you can use in the system)
val eFloat = 2.7182818284f // Float, actual value is 2.7182817

```

### literal constants for numbers
- Decimals: `123`
- Longs are tagged by a capital `L`: `123L`
- Hexadecimals: `0x0F`
- Binaries: `0b00001011`

```kotlin
// underscores can be used to make constants more readable
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010
```

### booleans
```kotlin
true
false
null
```

### strings (immutable)
```kotlin
val str = "abcd"

// Creates and prints a new String object
println(str.uppercase())
// ABCD

// The original string remains the same
println(str) 
// abcd

// concatenation
val s = "abc" + 1
println(s + "def")
// abc1def  

// Common Functions
val arrayList = arrayListOf("Renee", "Charlotte", "Nex")
println(arrayList.add(1, "Bel")
.remove
```

### arrays
arrays hold a fixed number of values of the same type/subtype. (collections are preferred instead of arrays) (collections are sets or lists)
- inefficient because the only way to add or remove elements from an array is to create a new array each time
- you can’t use the equality operator (`==`) to check arrays
```kotlin
// creating arrays
arrayOf()
arrayOfNulls()
emptyArray()
Array // constructor (acts like a class to create an instance i guesss)

// Creates an array with values [1, 2, 3]
val simpleArray = arrayOf(1, 2, 3)
println(simpleArray.joinToString())
// 1, 2, 3

// Returns the number of elements in an array
val simpleArray = arrayOf(5, 6, 7)
println(simpleArray.size)

// Creates an array with values [null, null, null]
val nullArray: Array<Int?> = arrayOfNulls(3)
println(nullArray.joinToString())
// null, null, null

var exampleArray = emptyArray<String>()

// Creates an Array<Int> that initializes with zeros [0, 0, 0]
val initArray = Array<Int>(3) { 0 }
println(initArray.joinToString())
// 0, 0, 0

// Creates an Array<String> with values ["0", "1", "4", "9", "16"]
val asc = Array(5) { i -> (i * i).toString() }
asc.forEach { print(it) }
// 014916

// Nested arrays
// Creates a two-dimensional array
val twoDArray = Array(2) { Array<Int>(2) { 0 } }
println(twoDArray.contentDeepToString())
// [[0, 0], [0, 0]]

// Creates a three-dimensional array
val threeDArray = Array(3) { Array(3) { Array<Int>(3) { 0 } } }
println(threeDArray.contentDeepToString())
// [[[0, 0, 0], [0, 0, 0], [0, 0, 0]], [[0, 0, 0], [0, 0, 0], [0, 0, 0]], [[0, 0, 0], [0, 0, 0], [0, 0, 0]]]

// Access and modify elements
val simpleArray = arrayOf(1, 2, 3)
val twoDArray = Array(2) { Array<Int>(2) { 0 } }

// Accesses the element and modifies it
simpleArray[0] = 10
twoDArray[0][0] = 2

// Prints the modified element
println(simpleArray[0].toString()) // 10
println(twoDArray[0][0].toString()) // 2

// application
fun main() {
    val lettersArray = arrayOf("c", "d")
    printAllStrings("a", "b", *lettersArray)
    // abcd
}

fun printAllStrings(vararg strings: String) {
    for (string in strings) {
        print(string)
    }
}

// comparing arrays
val simpleArray = arrayOf(1, 2, 3)
val anotherArray = arrayOf(1, 2, 3)

// Compares contents of arrays
println(simpleArray.contentEquals(anotherArray))
// true

// Using infix notation, compares contents of arrays after an element 
// is changed
simpleArray[0] = 10
println(simpleArray contentEquals anotherArray)
// false
```

|Primitive-type array|Equivalent in Java|
|---|---|
|[`BooleanArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean-array/)|`boolean[]`|
|[`ByteArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/)|`byte[]`|
|[`CharArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/)|`char[]`|
|[`DoubleArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/)|`double[]`|
|[`FloatArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float-array/)|`float[]`|
|[`IntArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/)|`int[]`|
|[`LongArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/)|`long[]`|
|[`ShortArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short-array/)|`short[]`|
