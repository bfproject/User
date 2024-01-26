## Description

Display user list and user details 

Screenshot
| User list  | User details |
| ------------- | ------------- |
| ![User_list_](https://github.com/bfproject/User/assets/13312345/3586f851-9f99-4e8b-ad86-543b0f544dec)  | ![User_detail_view_](https://github.com/bfproject/User/assets/13312345/820c53a9-5d08-47aa-a15f-05e925226a40) |


The data is retrieved from https://randomuser.me/

The project follows an MVI architecture.
A single activity approach is implemented.
Multimodules are used to separate the different layers.


Some of the main libraries used are:
- Jetpack compose to build the UI
- Hilt for dependency injection 
- Navigation to change route view
- Paging to retrieve and display data paginated 
- Retrofit, okhttp and Moshi to get data from the server and map it
- Coil to load images Asynchronously
- Junit, Mockito and Turbine for testing



### IMPORTANT

The API doesn't have an endpoint to retrieve a single user.

So, the data on the user list should also contain all the detail data (not yet implemented).
It is not implemented because get all the data in the user list view is a really bad behaviour as a lot of data needs to be retrieved when it isn't necessary, wasting resources.

Also, the data displayed on the detail view needs to be send from the user list. I opt for send all this data using the navigation arguments.

But sending all the data is consider an anti-pattern.

The proper way would be collect the required data on each view.
Then pass to the detail view only the user id.
That is, the single user view ideally would make a request to the server to get the data of the user clicked, but this endpoint is
not available for now.


Similar thing happens with search. There is no search endpoint.

Another point to improve is paging the data. I create a differten brach for the paging data implementation as I was not sure how to work it together with the search.
[Paging branch](https://github.com/bfproject/User/tree/feat-paginated-data)
