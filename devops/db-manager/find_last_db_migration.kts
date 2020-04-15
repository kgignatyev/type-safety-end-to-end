import java.io.File;

val migration_files  = File("src/db_migrations").listFiles()

val file_names = migration_files.map { it.name }.sorted()

val lastMigration = file_names.last()

val version = lastMigration.substring( 0, lastMigration.indexOf('_'))
println( "found last migration:$version"  )

val db_info_file = "target/db_migration_info"
val outFile = File(db_info_file)
outFile.parentFile.mkdirs()
outFile.writeText("export LAST_DB_MIGRATION=${version}")
println("last db migratin info written to ${outFile.absolutePath}")
