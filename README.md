# Ti.Watchdog


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
  <module version="1.0.0">de.appwerft.wakeholder</module>
</modules>
```

When you run your project, the compiler will combine your module along with its dependencies
and assets into the application.

## Example Usage

This module starts an AlarmManager which "pings" every 10 minutes. You don't need a require. The cronjob and with destroying of app. 