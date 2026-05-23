Param(
    [string]$projectRoot = "$PSScriptRoot"
)

$src = Join-Path $projectRoot 'src'
$lib = Join-Path $projectRoot 'lib'
$jar = Join-Path $lib 'mysql-connector-j-8.3.0.jar'
$build = Join-Path $projectRoot 'build\classes'
$sourcesFile = Join-Path $projectRoot 'sources.txt'

New-Item -ItemType Directory -Force -Path $build | Out-Null

Get-ChildItem -Path $src -Recurse -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII $sourcesFile

# Classpath (Windows uses ';')
$cp = "$jar;$src"
Write-Output "Compiling with classpath: $cp"

$javacArgs = @('-cp', $cp, '-d', $build, '-sourcepath', $src, "@$sourcesFile")
& javac @javacArgs

if ($LASTEXITCODE -eq 0) {
    Write-Output "Compilation succeeded. Classes are in: $build"
} else {
    Write-Error "Compilation failed with exit code $LASTEXITCODE"
}
