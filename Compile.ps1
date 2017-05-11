param (
    [Switch]$Jar,
    [Switch]$Run
)

$OutputDir = "out/Production/Maze"

if ($Run) {
    java -cp $OutputDir Program
} elseif ($Jar) {
    jar cvf pacman.jar -C $OutputDir .
} else {
    javac `
      src/Program.java `
      src/game/*.java `
      src/graphics/*.java `
      src/entities/*.java `
      src/entities/enemies/*.java `
      src/entities/enemies/factories/*.java `
      src/misc/*.java -d $OutputDir
}
