# vk-music-display

It can be useful if you need to display the current track on the stream via OBS, for example.

How it works: the program parses the page and looks for the currently playing track in the status. 
Therefore, for everything to work, you need to include the broadcast of music in the status.
Also, the page must be open for Internet.

# Own deployment:

Endpoint: http://ip:port/{userId}

Run: java -jar vk-music-display-1.0.0-all.jar {port}

# Public application:

https://vk-music-display.herokuapp.com/{userId}
