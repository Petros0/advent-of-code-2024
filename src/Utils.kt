import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputAndTrim(name: String) = Path("src/$name.txt").readText().trim().lines()

fun readInput(name: String) = Path("src/$name.txt").readText()

fun readAsString(fileName: String, delimiter: String = ""): String =
    readInput(fileName).lines().reduce { a, b -> "$a$delimiter$b" }

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> List<T>.midpoint(): T =
    this[lastIndex / 2]
