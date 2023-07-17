# YBS

I have used Kotlin with Jetpack Compose to test myself with the latest Andorid approach of app creation. I have used ViewModels to ensure that the data is kept seperate from the view logic. Finally, have used the Repository pattern with the ViewModel to ensure that the logic to get the data from the Flicker is kept in a dedicated area away from thje rest of the code.

3rd party library imports used and why:

Coroutines
- Used to make async calls to the Flickr API

GSON
- Used to deserialse the Flickr API json repsonse into a class

Coil
- Used to show the image in the feed in the list/grid view

Navigation Compose
- Used to enable easy navigation around the app

Mockito
- Used to enable the mocking of the Flickr repository
