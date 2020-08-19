# MCSLoader
Build system for loading all MCProtocolLib versions.

## Purpose

MCSLoader downloads all versions from MCProtocolLib repository and compiles them to .jar files, 
which are used by MCSCore as a MCProtocol implementation.

## Usage

`java -jar mcsloader-2.4.jar OUTPUT MCPROTOCOLLIB_REPOSITORY_URL`

MCProtocolLib versions are loaded from Git repository **MCPROTOCOLLIB_REPOSITORY_URL**.
Build output is saved into **OUTPUT** directory.
