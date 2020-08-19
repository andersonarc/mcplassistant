# mcsloader
assistant build system for loading different mcprotocollib versions

## purpose

mcsloader downloads all versions from mcprotocollib repository and compiles them to .jar files
which are used by mcscore as protocol implementation

## usage

`java -jar mcsloader-2.4.jar **OUTPUT MCPROTOCOLLIB_REPOSITORY_URL**`

versions loaded from git repository **MCPROTOCOLLIB_REPOSITORY_URL**
build output is saved into **OUTPUT** directory
