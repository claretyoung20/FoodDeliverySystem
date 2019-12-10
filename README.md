# bytework

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle. This app is scaffold with jhipster

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    npm install

We use npm scripts and [Webpack][] as our build system.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    npm start

To access both front and back end
Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

# STEP TO SET UP THE ENTITIES

1. The app automatically load some data for shipping and delivery purpose
2. Bytework address is default for all users(customer/developers), same with the longitude and latitude

# STEP TO SET UP THE APP

1. Clone the project (https://github.com/claretyoung20/FoodDeliverySystem.git)
2. Create new mySQL database called 'bytework' then under this file path '\FoodDeliverySystem\src\main\resources\config' open application.property file and replace database info
   with your database info (username and password)
3. run 'npm install'
4. run '.mvnw' (on editor CMD just run 'mvnw')
5. run 'npm start'

NOTE: because I use liquid-base there might be problem creating all the entities i have created, u can run 'mvnw liquibase:clearCheckSums' to fix any liquidbase problem

6. Go to ur host browser and localhost:9000
7. login as Admin (login: admin, password: admin) (Under Management tab u can manage users and see API, u can also test the api from there)
   login as User(login: user, password: user
   login as Vendor(login: vendor, password: vendor)
8. After Login as 'vendor' go to entities tab>dropdown>select menu>>create new menu>> fill the info of the menu, under 'user id' select vendor as the owner of the menu
   (I didnt filter the dropdown to show the only the current vendor), then save [Add as many as possible]
9. After login as user,
10. Go to menu to see available food, select food u want and got to cart page to see food in your cart
11. Click on checkout on cart page to go to checkout page
12. On checkout page, choose the delivery and payment type and click on checkout
13. Admin or vendor can see order and change the status.
14. TO CREATE NEW ACCOUNT....
15. Go to application.properties file and configure email to be able to get email for changing/adding password..
16. You cant use (https://www.mailinator.com/) as testing email for new users

NOTE: AT THE MOMENT THE APP ALLOW FOR ONE VENDOR BUT CAN BE IMPROVED TO ALLOW MULTIPLE VENDORS.
