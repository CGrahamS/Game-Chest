
<p align="center">
  <img src=treasureicon.png height=300px/>
</p>


### _Epicodus: Android (Weekly Project)_

#### By _[**Caleb Stevenson**](https://github.com/CGrahamS)_

## Description

This is an ongoing practice project where I will be practicing the Android development skills I learn in class.
Game Chest will allow users to search and browse for games. Then they'll be able to save the games they find to their favorites as a list of favorite games or games to be played. User's will also be able to save their favorite video gaming platforms.

## Specs

| BEHAVIOR                                                | INPUT    | OUTPUT   |
|---------------------------------------------------------|----------|----------|
| Program navigates to different activities               |          |          |
| Program passes user input from one activity to another. | Zelda    | Zelda
| Program retrieves best matches to user search query.    | Zelda    | Results for "Zelda" <ol><li>The Legend of Zelda</li><li>Zelda II: The Adventure of Link</li><li>The Legend Of Zelda: The Wind Waker</li></ol>|
| Program saves games to favorites list.                  |          | Results for "Zelda" <ol><li>The Legend of Zelda: A Link To The Past</li><<li>Super Mario Brothers</li></ol>|

## Setup/Installation Requirements

1. Clone this repository to your desktop `git clone https://github.com/CGrahamS/Game-Chest.git`
2. Open repository in Android studio.
3. Confirm Gradle build is successful.
4. Create a new AVD with the 4" WVGA (Nexus S) profile.
5. Select the Marshmallow, API Level: 23, ABI x86
6. Remove '4"' from the AVD name.
7. Now you can Run the app!

## Known Bugs

Scrolling on Favorite Game's activity is unwieldy because the item drag is set on entire list item.

## Support and contact details

Caleb Stevenson: _cgrahamstevenson@gmail.com_

## Technologies Used

_Java,
Android,
ButterKnife_

### License

This webpage is licensed under the GPL license.

Icon made by Freepik from www.flaticon.com

Copyright &copy; 2016 **_Caleb Stevenson_**
