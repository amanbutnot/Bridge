# Day One
---

1. Initialized the project.
2. Created three modules.
    * **bridge-library** → that will contain the library part and connect the other apps to the
      console app.
    * **bridge-console** → the console app where logs and other functionality can be done.
    * **sample-app** → a demo app just to check while making the program.
3. After that created
   the [BridgeService](../../bridge-library/src/main/java/com/github/amanbutnot/bridge_library/service/BridgeService.kt)
   which extends from the service class and implements its lifecycle overrides. For now, it is
   empty.
4. Then in the [bridge-library Manifest](../../bridge-library/src/main/AndroidManifest.xml)
