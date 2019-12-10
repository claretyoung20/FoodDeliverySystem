# bytework

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

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
2. Default users is setup already and here is there login details
   a. login: admin, password: admin
   b. login: user, password: user
   c. login: vendor, password: vendor

3. Bytework address is default for all users(customer/developers), same with the longitude and latitude
4. When creating new menu please select 'vendor' or any user with ROLE_VENDOR as the user id associated to the menu
5. To receive email please check application.properties for email configuration
