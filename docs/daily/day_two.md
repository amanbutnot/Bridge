# Day Two

---

## Task 1: Discovering the apps that declared my service as their intent

### 1. Adding a category to the intent filter

First I added category to intent filter to category, the reason I applied this later we need to
match the intents in a specific category.

### 2. Creating the discovery class

Then comes the discovery class in which I am actually getting the package name and the service class
name.

* We will be getting two things one is package name of the app and other is the service class name,
  Now I could've skipped the serivce class name because it will be the same for all as I have that
  service in my libary
* But to be extra sure that the service class package name comes from the installed app and matches
  we are getting that
* Then we create the intent with action name of the action name in the manifest

### 3. Using the `PACKAGE_MANAGER` API

Now comes the thing **PACKAGE_MANAGER** API it allowed me to inspect the installed apps and their
components I used that with my intent and the default category to get the packages names based off
of my needs.

### 4. Mapping and returning the results

Then just looping it and sending it back that's all.

### 5. Creating a demo UI

Then in
my [Bridge Console MainActivity](../../bridge-console/src/main/java/com/github/amanbutnot/bridge/MainActivity.kt)
just created a demo ui to get the package names.
