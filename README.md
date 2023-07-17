# YBS

I have used Kotlin with Jetpack Compose to test myself with the latest Andorid approach of app creation. I have used ViewMoidels with a Repository to ensure that the logic to get the data from the Flicker is kept seperated away from the rest of the code keping the business logic seperate.

3rd party library imports used and why:

Coroutines
- Used to make async calls to the Flickr API

GSON
- Used to deserialse the FLicr API json repsonse into anm object

Coil
- Used to show the image in the feed in the list/grid view

Navigation Compose
- Used to enable easy navigation around the app

Mockito
- Used to enable the mocking of the Flickr repository
