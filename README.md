# Territoriality MC

A Minecraft plugin to claim territories and fight for them.

## Prepare environment

### Download BuildTool

Go to https://www.spigotmc.org/wiki/buildtools/ and download **BuildTool**.

With the .jar file downloaded, run the next command to download in cwd the spigot libraries and the server:

```bash
java -jar BuildTools.jar --rev 1.20.1
```

In `Spigot/Spigot-API/target` you can find the spigot library for your IDE environment...

### Prepare the spigot server

Create a start script like this:

**start.sh**:
```bash
#!/bin/bash
java -Xms1G -Xmx1G -XX:+UseG1GC -jar spigot-1.20.1.jar nogui
```

Copy the spigot server to another folder, put the start script here too.

> Remember, the plugins should be in the `plugins` folder created by the spigot server.
> 
> (you can use symbolic link lol)

