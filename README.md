# Indominus Rex Taming Minecraft Plugin

Indominus Rex Taming is a Minecraft plugin for Paper 1.21 servers that adds a tamable Indominus Rex, ownership tracking, custom Dino Saddles, and ridable mount behavior. Players can spawn a powerful Ravager-based Indominus Rex, tame it with beef, equip a Dino Saddle, and ride it.

## Install

1. Build the plugin with Java 21 and Maven:

   ```bash
   mvn package
   ```

2. Copy `target/plugin.jar` to the `plugins` folder of a Paper 1.21.11 server.

3. Start or restart the server.

4. Confirm that the plugin has loaded successfully.

## Use

1. Give a permitted player access to the spawn command:

   ```
   /indominus
   ```

   This spawns an Indominus Rex with increased health.

2. Feed the wild Indominus Rex beef by right-clicking it with raw beef or cooked beef. Feeding has a chance to tame it, and unsuccessful attempts consume one piece of beef outside Creative mode.

3. Give a permitted player a custom Dino Saddle:

   ```
   /dinosaddle
   ```

4. Right-click the tamed Indominus Rex with the Dino Saddle to equip it.

5. Right-click the saddled Indominus Rex again to ride it.

6. Only the player who tamed the Indominus Rex can equip its saddle or ride it. Other players are prevented from using it.

Required permissions:

- `indominus.spawn` allows `/indominus`.
- `indominus.saddle` allows `/dinosaddle`.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/962c0271-4f36-4247-9f96-030b42284c27?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=962c0271-4f36-4247-9f96-030b42284c27
