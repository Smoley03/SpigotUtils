# SpigotUtils
A work in progress library containing code that I find useful when creating plugins. 
SpigotUtils is split into two parts: API and Plugin. The API is standalone and can be 
used independently. The plugin will build upon the API providing features such as auto-reloading
yml configurations

### Motivation
When developing plugins, project setup can take more time than intended. By creating a 
library containing classes/methods I find myself implementing frequently I can expedite
this process tremendously (creating a stub project also helps)

### Current Features
- Location Adapter (Serialize/deserialize location objects)
- Builder classes (ItemStack and PotionEffect)
- AbstractConfig (Easily generate plugin folders/yml configurations and cache data)
- Constants (Enchant, Entity, and Material constants)
- Miscellaneous Utils (File, Hashing, ItemStack, Location, Player, Text, etc.)
- Miscellaneous Objects (HourMinute, Cooldown, Pair, etc.)
- Miscellaneous Collections (WeightedList, etc.)

### Planned Features
- Mob Builder/Custom Mob (API)
- Area class/adapter (API)
- Sort collections of HourMinute objects (API) 
- Inventory Utils (API)
- Map/Set implementations specific to PotionEffectType and Enchantment keys (API)
- AbstractInventoryHolder (API)
- Auto-Reloading YML Configurations (Plugin)
- Custom Item API (Plugin)

### Shading the API into your Jar
If you plan on shading the API into your JAR you should relocate the API to avoid potential version
conflicts with other plugins doing the same. Below is an example of how to do so using gradle
```$xslt
plugins {
    id 'java'
    id 'io.freefair.lombok' version '5.1.1'
    id 'com.github.johnrengelman.shadow' version '4.0.2'
}

group 'com.github.mlefeb01.stubplugin'
version '1.1-SNAPSHOT'

sourceCompatibility = 1.8

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation 'org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT'
    compile ('com.github.mlefeb01.spigotutils-api:SpigotUtils-API:1.1') {
        exclude group: 'org.spigotmc', module: 'spigot-api'
    }
}

shadowJar {
    relocate('com.github.mlefeb01.spigotutils', 'com.github.mlefeb01.changeme.spigotutils')
    configurations = [project.configurations.compile]
    destinationDir = file('build/libs/')
    classifier=null
    minimize()
    dependencies {
        manifest {
            attributes("Main-Class": "")
        }
    }
}
```

### References
- [Comparison between JSON and YAML for data serialization](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.1048.2508&rep=rep1&type=pdf)
- ["Effective Java Second Edition" by Joshua Bloch](https://www.amazon.com/Effective-Java-2nd-Joshua-Bloch/dp/0321356683)