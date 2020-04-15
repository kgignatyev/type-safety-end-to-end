//@file:DependsOn("maven:junit:junit:4.11")

import java.io.File;

val interfaceName = args[0]
println ( "implements: " + interfaceName)
val deps = java.io.File("target/dependencies.txt").readLines()
//[INFO]    com.kgi:geography-interface:jar:1.0.8:compile
val interfaces:List<String> = deps.map {
//    println( "=== $it" )
    val parts = it.split( "[ :]+".toRegex())
    if( parts.size >= 5) {
        "${parts[1]}:${parts.get(2)}:${parts.get(4)}"
    }else {
        "-"
    }
 }.filter {   it.contains("-interface") }

val implements = interfaces.filter {  it.contains( interfaceName) }

val requires = interfaces.filter { println("$it");!it.contains( interfaceName) }

val outFile = File("target/dependencies-info-export")
outFile.writeText( """
export IMPLEMENTS=${implements.joinToString("," )}
export REQUIRES=${requires.joinToString("," )} 
""" )

println("to get implementatiaon and requirements, source from file ${outFile.absolutePath}")

