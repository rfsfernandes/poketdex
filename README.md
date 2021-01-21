
# Android Challenge - PoketDex
A repository to complete the Android Challenge using the [PokeAPI](https://pokeapi.co/).

# Aproach
I developed this app in order to make it look and function like a Pokédex from the game and series Pokémon.

Since Pokémon first gained popularity as a GameBoy game, the app was developed to be fully prepared to work in landscape mode and, since it's a mobile app, it's also suitable for portrait mode.

To develop this app, I used the MVVM pattern as much as I could.

When openned, the app will exibit a list of Pokémon's with their image, name and Pokédex index. This list has pagination, which means only X number of rows are loaded at a time.

When a pokemon from the list is selected while the app is in portrait mode, the user will be redirected to a new page containing the Pokémon description set, such as:
- both default and shiny representative images, its type (both name and color), a small description;
- stats, like HP, ATTACK, DEFENSE, SPEED, WEIGHT, etc;
- available moves for that Pokémon, their effect, short effect and a small description;
- if the user clicks on the type or on the description of the effect, short effect or the small description, a dialog will be shown with more info about the matter he clicked.

The description set can be switched by scrolling horizontally or by clicking on the white arrows on the top.

This functionality is also available on the landscape mode, but the description will appear on the right side of the screen (instead of on a new page) and the Pokémon list will be at the left side.

All the calls to [PokeAPI](https://pokeapi.co/) are made using [Retrofit](https://square.github.io/retrofit/).

To ensure there isn't much load on the [PokeAPI](https://pokeapi.co/), as requested by the API developers,  I implemented a simple cache system using [RoomDB](https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase).

# Postman Collections
To develop the application and keep the endpoints organized, I created a [Postman](https://www.postman.com/) collection containing all the enpoints used.

You can check them out [here](https://documenter.getpostman.com/view/6099206/TVza9Yz2).
