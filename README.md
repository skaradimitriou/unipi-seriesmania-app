# SeriesMania
<p align="center">
  <img width="100" src="https://github.com/skaradimitriou/unipi-seriesmania-app/assets/64270931/8ad1dee8-0cf3-4197-b085-6b6ae820bca7" alt="seriesmania_logo_round"><br>
  <b>SeriesMania is a series suggestion app</b> developed for my Masters degree dissertation in University of Piraeus.
  <br/>
</p>

[![Demo  Unipi Audio Stories](https://github.com/skaradimitriou/unipi-seriesmania-app/assets/64270931/3a02a7ef-e0e1-426b-a755-f9f060edc051f)](https://drive.google.com/file/d/1PPDuWABBSE_AC4paymiut-hE89u9VaMA/view)

## Features

The user can:
1. Register/Login
2. Browse a list of series 
3. Discover series per genre category
4. Search for series
5. Interact with other users by starting (or replying in) a discussion
6. Edit my profile (add image, bio, username, series preferences)
7. Follow other users
8. Create my own series watchlist
9. Read FaQ (Frequently asked questions) inside the app
10. Visit the app's analytics section

In addition to this app, a backend app has been developed in order to create and store the analytics inside the online db. You can find it [HERE](https://github.com/skaradimitriou/seriesmania-be-app)

## Programming Language 

![Kotlin](https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white) 

## Architecture
Model - View - ViewModel (MVVM) <br>
Multi-Module Approach

## Technologies Used
- Navigation Component <br/>
- Kotlin Coroutines <br/>
- Kotlin Flows <br/>
- Data Binding

### Libraries

- [Hilt]() for dependency Injection <br/>
- [Firebase Authentication]() for the user's authentication <br/>
- [Firebase Firestore]() to store any necessary data in online db <br/>
- [Firebase Storage]() to store any necessary stories media content <br/>
- [Paging3]() to provide seamless experience with fresh data on user's scroll demand <br/>
- [Gson](https://github.com/google/gson) for serialization/deserialization </br>
- [Glide](https://github.com/bumptech/glide) to load images <br/> 
- [Lottie]() for animations <br/>
- [Timber](https://github.com/JakeWharton/timber) for logging across the app <br/>
- Built with [Material Design](https://material.io/)
