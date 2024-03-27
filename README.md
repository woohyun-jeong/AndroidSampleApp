### AndroidSampleApp
This is a project to create an Android Sample app.

This is a sample project slightly changed from multi-module to lite.
* DroidKnightsApp2023(https://github.com/droidknights/DroidKnightsApp/) - fork repository
* nowinandroid(https://github.com/android/nowinandroid/tree/main) - reference repository

### contributor
<table><tr>         
  <td align="center"><a href="https://github.com/woohyun-jeong"><img src="https://avatars.githubusercontent.com/u/55433351?v=4?s=100" width="100px;" alt=""/>         <br /><sub><b>JungWooHyoen</b><br></sub></a><br /></td>
  <td align="center"><a href="https://github.com/BaeWooRam"><img src="https://avatars.githubusercontent.com/u/41356481?v=4?s=100" width="100px;" alt=""/>         <br /><sub><b>BaeWooRam</b><br></sub></a><br /></td>
</table><br/>

### Used Tech
* [Multi Module](https://developer.android.com/topic/modularization/) - A Project with multiple Gradle modules is known as a multi-module project.
* [Kotlin](https://kotlinlang.org/) - This Project is using Kotlin version 1.8.21.
* [MVVM](https://developer.android.com/jetpack/docs/guide)
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Asynchronous programming 
* [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - Create a UI that automatically responds to lifecycle events.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
* [Navigation](https://developer.android.com/guide/navigation/) - Handle everything needed for in-app navigation.
* [Paging](https://developer.android.com/topic/libraries/architecture/paging/) - Load and display small chunks of data at a time.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks.
* [Dagger 2](https://github.com/google/dagger) - Compile-time framework for dependency injection.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Compile-time framework for dependency injection.
* [Retrofit 2](https://github.com/square/retrofit) - Handle REST api communication.
* [Test](https://developer.android.com/training/testing/) - An Android testing framework for unit and runtime UI tests.
* [ktlint](https://ktlint.github.io/) - Enforce Kotlin coding styles.

### Features
* [Firebase](https://firebase.google.com/docs) - Tools to develop high-quality apps.
  - [Authentication](https://firebase.google.com/docs) - Allows an app to securely save user data in the cloud.
  - [Cloud Firestore](https://firebase.google.com/docs/firestore) - Flexible, scalable NoSQL cloud database to store and sync data.
  - [Cloud Functions](https://firebase.google.com/docs/functions) - Automatically run backend code in response to events triggered by Firebase 
  - [Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) - Notify a client app.
  - [Cloud Storage](https://firebase.google.com/docs/storage) - Store and serve user-generated content.
  - [Remote Config](https://firebase.google.com/docs/remote-config) - Change the settings of app without requiring users to download an app update.
* [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Schedule deferrable, asynchronous tasks even if the app exits or device restarts.
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Access your app's SQLite database with in-app objects and compile-time checks.
* [Glide](https://github.com/bumptech/glide) - Load and cache images by URL.
* [Moshi](https://github.com/square/moshi) - Serialize Kotlin objects and deserialize JSON objects.

### Modules
Using the above modularization strategy, the Android Sample app has the following modules:

<table>
  <tr>
   <td><strong>Name</strong>
   </td>
   <td><strong>Responsibilities</strong>
   </td>
   <td><strong>Key classes and good examples</strong>
   </td>
  </tr>
  <tr>
   <td><code>app</code>
   </td>
   <td>Brings everything together required for the app to function correctly. This includes UI scaffolding and navigation. 
   </td>
   <td><code>SampleApplication, MainActivity</code><br>
   App-level controlled navigation via <code>NavHost is Empty</code>
   </td>
  </tr>
  <tr>
   <td><code>feature:1,</code><br>
   <code>feature:2</code><br>
   ...
   </td>
   <td>Functionality associated with a specific feature or user journey. Typically contains UI components and ViewModels which read data from other modules.<br>
   Examples include:<br>
   <ul>
      <li><a href="https://github.com/woohyun-jeong/AndroidSampleApp/tree/main/feature/main"><code>feature:main</code></a>start main screen.</li>
      <li><a href="https://github.com/woohyun-jeong/AndroidSampleApp/tree/main/feature/pager"><code>feature:pager</code></a>test pager screen.</li>
      <li><a href="https://github.com/woohyun-jeong/AndroidSampleApp/tree/main/feature/sample"><code>feature:sample</code></a>test sample screen.</li>
   </ul>
   </td>
   <td><code>MainActivity, MainViewModel</code><br>
   <code>VerticalPagingActivity, PagingViewModel</code>
   </td>
  </tr>
  <tr>
   <td><code>core:designsystem</code>
   </td>
   <td>Design system which includes Core UI components (many of which are customized Material 3 components), app theme and icons. The design system can be viewed by running the <code>app-nia-catalog</code> run configuration. 
   </td>
   <td>
   <code>BaseComposeView</code>    <code>BottomLogo</code>    <code>Chips</code> 
   </td>
  </tr>
  <tr>
   <td><code>core:ui</code>
   </td>
   <td>Composite UI components and resources used by feature modules, such as the news feed. Unlike the <code>designsystem</code> module, it is dependent on the data layer since it renders models, like news resources. 
   </td>
   <td> <code>NewsFeed</code> <code>NewsResourceCardExpanded</code>
   </td>
  </tr>
  <tr>
   <td><code>core:data</code>
   </td>
   <td>Making network requests and handling responses from a remote data source.
   </td>
   <td><code>ApiModule</code>
   </td>
  </tr>
  <tr>
   <td><code>core:datastore</code>
   </td>
   <td>Storing persistent data using DataStore.
   </td>
   <td><code>SessionPreferencesDataSource</code><br>
   <code>SettingsPreferencesDataSource</code>
   </td>
  </tr>
  <tr>
   <td><code>core:model</code>
   </td>
   <td>Model classes used throughout the app.
   </td>
   <td><code>Topic</code><br>
   <code>Shopping</code><br>
   </td>
  </tr>
</table>

### Test API
- https://developers.naver.com/docs/serviceapi/search/movie/movie.md#%EC%98%81%ED%99%94

