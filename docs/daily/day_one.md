# Day One

---

## 1. Project Initialization

1. Initialized the project.

2. Created three modules:

   * **bridge-library** → that will contain the library part and connect the other apps to the console app.
   * **bridge-console** → the console app where logs and other functionality can be done.
   * **sample-app** → a demo app just to check while making the program.

## 2. Creating the Service

3. After that created the [BridgeService](../../bridge-library/src/main/java/com/github/amanbutnot/bridge_library/service/BridgeService.kt) which extends from the service class and implements its lifecycle overrides. For now, it is empty.

## 3. Configuring the Library Manifest

4. Then in the [bridge-library Manifest](../../bridge-library/src/main/AndroidManifest.xml) we have to define the service class in the manifest and in this we did three important things:

   * Set `exported = true` meaning other apps can access it but the issue with that is now every app can access it which is a security issue. To fix that we add permission block to make sure that app accessing it must have this permission. You can name the permission anything, what I did was create my own:

   ```xml
   <permission
       android:name="com.github.amanbutnot.bridge.permission.CONNECT"
       android:label="Bridge Connection"
       android:protectionLevel="normal" />
   ```

   In this I named the permission `com.GitHub.amanbutnot.bridge.permission.CONNECT` and label is just the name, the important thing is the protection level, I basically had two options either make it normal or make it signature.
   I chose normal because if an app requests this permission it is granted automatically, while in signature two apps should have the same signing certificate.

5. Then add it to the service to that to access is you need to have this permission:

   ```
   android:permission="com.github.amanbutnot.bridge.permission.CONNECT">
   ```

6. Another main thing is the intent filter in this I used it because we need a way to get that service if we don't use it then we have to manually add the other's app package name which is not useful for my case that's why I added intent filter so that I can look for the service using the predefined name:

   ```xml
   <intent-filter>
       <action android:name="com.github.amanbutnot.bridge_service" />
   </intent-filter>
   ```

## 4. Configuring the Console Manifest

7. Then comes the [bridge_console manifest](../../bridge-console/src/main/AndroidManifest.xml) where we need to do two things:

   * First is to ask for the same permission which we defined in the library part.
   * Second is to ask for queries means specifies the set of external apps that your app intends to interact with and have the intent the same as the library intent name.

---

## End of Day 1
