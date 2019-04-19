# Ti.Watchdog

This module tries to solve [this issue](https://jira.appcelerator.org/browse/AC-6187?filter=-2)

## Install

To use your module locally inside an app you can copy the zip file into the app root folder and compile your app.
The file will automatically be extracted and copied into the correct `modules/` folder.

If you want to use your module globally in all your apps you have to do the following:

### macOS

Copy the distribution zip file into the `~/Library/Application Support/Titanium` folder

### Linux

Copy the distribution zip file into the `~/.titanium` folder


## Project Usage

Register your module with your application by editing `tiapp.xml` and adding your module.
Example:

```xml
<modules>
  <module platform="android">de.appwerft.watchdog</module>
</modules>
```

When you run your project, the compiler will combine your module along with its dependencies
and assets into the application.

## Example Usage

```js
import Ping from 'de.appwerft.watchdog';
Ping.start({
	interval : 10*1000*60, // ms
	debug : true
});
// and optional 
Ping.stopp();
```
