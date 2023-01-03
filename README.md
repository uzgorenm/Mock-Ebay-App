# Mock Ebay App
## Summary
### In this app, I used Socket Programming to handle all of the Client Server Relations, JavaFX and SceneBuilder to serve as the GUI, and Java to handle the backend.

##### Documentation

###### Programmer Side

This project is my final project for my 422C class. In this project, I had to create an eBay auctioning application. I used socket programming to handle all communications between the server and the client.

The first thing that takes place is the server is started, which reads the item data from the txt file and stores them in an ObservableList. Then, the server accepts every client, starting a new client handler and executing the thread.

The client handler starts the timer the first time it receives a message from a client to start the time, then it processes the bid data. After processing the bid data, it sends the data back to every other client.

Then the client is started, which would connect to the server and launch the thread that handles outputs from the server, and call the controller for the login page. The login page works very simply, in that it takes the string stored inside the username and password text fields, and stores them into an observable data type Account. If you do not use guest, but rather use a username, your password will be hashed and encrypted so that your password is set and defined depending on your username.

After the client presses login ( with the correct username and password) or uses guest to log in, The login controller calls the create controller in the client class that initializes and changes the window of the scene to the main auction screen. The main controller then reads initial item data from the server and sets up the table view containing all the items to be auctioned in the JavaFX view. After the main controller sets up and is finished initializing, the first client sends a message to the server saying you can start the timer, after which the timer starts ticking.

The main screen’s table also has a filter option, which filters the data by adding a listener to the narrow search TextField, and sets a predicate by determining if the changed text field is equivalent to the values of the items, if not, removing the item from the new sorted list, and setting the table equivalent to this sorted list.

The main screen also has a bid area, which takes an item's unique id, the bid you want to give, and a button to submit your bid. This will send a message to the server with the important information, that being the bid number, the username, the item's unique id, and an identifier “BID” indicating that this message is a bid.

The main screen has a console area, which outputs messages, or errors to the clients, letting them know what just happened according to the messages sent to the server.

The main screen also has a history area, which has a text field and a button. The text field receives the UniqueID of each item and clicking the button outputs the history of the bids successfully placed on that item.

The main screen finally has a logout and quit button next to the area displaying your username, which terminates the client or goes back to the login screen depending on the button pressed.

The final thing I added was the option to add an item, what happens is that when you press the button, the client sends the data to the server, which creates the item, adds it to the database, and sends it back to the client for it to add to its table.

###### Client Side

This application is a mock eBay auctioning application. The way to get started is to run the server application, then the client application. The first thing you’ll see is the login page, which will give you the option of either logging in with a username or logging in as a guest. If you log in with a username, your password will be hashed, and encrypted and will be either the first three letters of your username in backward order, followed by 1753, or if your username is less than three letters, it will be 1753. If you log in as a guest by clicking the guest login, your username will be guest0. After clicking either guest login or login ( with the correct username and password), you will be taken to the main auction page, in which you can bid on 5 items, seeing the description, the item name, the buy it now price, the current bid, and the last bidder. When you bid, if you bid more than the current bid, you will be the current bidder. If you do not bid more than the current bid, you will get an error on the console page. If you bid on an item that is already sold, you will get an error on the console page. If you bid on an item that has no time left, you will get an error on the console page. At any time, you can enter the unique id of the item you want to see the history of, click the history button, and see the total successful bid history of that item. To narrow down the items, you can also search the item by its name, description, and unique id, and only see the items that pertain to your typed-in text. Furthermore, you can even add an item to the auction by adding all the necessary fields and pressing CreateItem.
 
###### List of References
https://stackoverflow.com/questions/14370183/passing-parameters-to-a-controller-when-loading-an-fxml/14432578#14432578
https://www.youtube.com/watch?v=ZIzoesrHHQo https://www.youtube.com/watch?v=FeTrcNBVWtg
https://stackoverflow.com/questions/41424544/how-to-update-value-of-each-row-in-table-view- of-javafx
https://stackoverflow.com/questions/11065140/javafx-2-1-tableview-refresh-items 
https://stackoverflow.com/questions/48800076/how-to-refresh-a-particular-row-or-cell-in-tableview 
https://stackoverflow.com/questions/17402484/populating-tableview-in-javafx-with-tableview-cr eated-in-fxml-file?rq=1 https://stackoverflow.com/questions/26555828/how-to-populate-tableview-dynamically-with-fx ml-and-javafx?rq=1
             
