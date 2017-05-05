param (
    [Switch]$Run
)

$OutputDir = "out/Production/Maze"

if ($Run) {
    java -cp $OutputDir Program
} else {
    javac src/Program.java src/maze/*.java src/graphics/*.java src/entities/*.java src/misc/*.java -d $OutputDir
}
