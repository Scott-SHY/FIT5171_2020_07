package rockets.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;
import rockets.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.resource.ClassPathResource;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

import static org.apache.logging.log4j.core.util.Closer.closeSilently;
import static spark.Spark.*;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    private static DAO dao;

    public static void setDao(DAO dao) {
        App.dao = dao;
    }

    public static void main(String[] args) throws IOException {
        Properties properties = loadProperties();

        int port = Integer.parseInt(properties.getProperty("spark.port"));
        port(port);

        String dbAddress = properties.getProperty("neo4j.dir");
        if (null == dao) {
            dao = new Neo4jDAO(dbAddress);
        }

//        List<LaunchServiceProvider> lsps = new ArrayList<LaunchServiceProvider>();
//        lsps.add(new LaunchServiceProvider("SpaceX", 2002, "USA"));
//        lsps.add(new LaunchServiceProvider("Sea Launch", 1995, "USA"));
//        dao.createOrUpdate(lsps.get(0));
//        dao.createOrUpdate(lsps.get(1));

        // "/"
        handleGetIndex();

        // "/hello"
        handleGetHello();

        // "/register"
        handleGetRegister();

        // "/register"
        handlePostRegister();

        // "/login"
        handleGetLogin();

        // "/login"
        handlePostLogin();

        // "/logout"
        handleGetLogout();

        // "/user/:id"
        handleGetUserById();

        // "/users"
        handleGetUsers();

        // "/rocket/:id
        handleGetRocketById();

        // "/rocket/create
        handleGetCreateRocket();

        // "/rocket/create
        handlePostCreateRocket();

        // "/rockets"
        handleGetRockets();

        // "/lsps"
        handleGetLsps();

        // "/lsps/create"
        handleGetCreateLsp();

        // "/lsps/create"
        handlePostCreateLsp();

        // "/lsp/:id"
        handleGetLspById();

        // "/launches"
//        handleGetLaunches();

    }

    public static void stop() {
        Spark.stop();
    }

    // show users list in users.html.ftl
    private static void handleGetUsers() {
        get("/users", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            try {
                attributes.put("users", dao.loadAll(User.class));
                return new ModelAndView(attributes, "users.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "users.html.ftl");
            }
        }, new FreeMarkerEngine());

    }

    private static void handleGetIndex() {
        get("/", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            User user = getLoggedInUser(req);
            attributes.put("user", user);
            return new ModelAndView(attributes, "base_page.html.ftl");
            //return handleBaseHelloView(req, res, attributes);
        }, new FreeMarkerEngine());
    }

    // show register.html
    private static void handleGetRegister() {
        get("/register", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("email", "");
            attributes.put("firstName", "");
            attributes.put("lastName", "");

            return new ModelAndView(attributes, "register.html.ftl");
        }, new FreeMarkerEngine());
    }

    // register new user
    // fix the serious bug, check if user exist
    private static void handlePostRegister() {
        post("/register", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");

            attributes.put("email", email);
            attributes.put("firstName", firstName);
            attributes.put("lastName", lastName);

            logger.info("Registering <" + email + ">, " + password);

            User user;
            user = dao.getUserByEmail(email);
            if (user != null){
                attributes.put("errorMsg", "User has exist");
                return new ModelAndView(attributes, "register.html.ftl");
            }

            try {
                user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                dao.createOrUpdate(user);

                res.status(301);
                req.session(true);
                req.session().attribute("user", user);
                res.redirect("/hello");
                return new ModelAndView(attributes, "base_page.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "register.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    // show hello to user
    private static void handleGetHello() {
        get("/hello", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            User user = getLoggedInUser(req);
            if (null != user) {
                attributes.put("user", user);
            }
            return new ModelAndView(attributes, "base_page.html.ftl");
        }, new FreeMarkerEngine());
    }

    // show the login.html
    private static void handleGetLogin() {
        get("/login", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String user_name = req.params("user_name");
            if (null == user_name || user_name.trim().isEmpty()) {
                attributes.put("user_name", "");
            } else {
                attributes.put("user_name", user_name);
            }

            return new ModelAndView(attributes, "login.html.ftl");
        }, new FreeMarkerEngine());
    }

    // login with user
    private static void handlePostLogin() {
        post("/login", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String user_name = req.queryParams("user_name");
            String password = req.queryParams("password");

            logger.info("Logging in <" + user_name + ">, " + password);

            User user = null;
            try {
                user = dao.getUserByEmail(user_name);
            } catch (Exception e) {
                handleException(res, attributes, e, "login.html.ftl");
            }
            if (null != user && user.getPassword().equals(password)) {
                res.status(301);
                req.session(true);
                req.session().attribute("user", user);
                res.redirect("/hello");
                return new ModelAndView(attributes, "base_page.html.ftl");
            } else {
                attributes.put("errorMsg", "Invalid email/password combination.");
                attributes.put("user_name", user_name);
                return new ModelAndView(attributes, "login.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static void handleGetLogout() {
        get("/logout", (req, res) -> {
            User user = getLoggedInUser(req);
            spark.Session session = req.session();
            if (null != session && null != user) {
                session.invalidate();
            }
            res.redirect("/");
            return "";
        });
    }

    private static ModelAndView handleException(Response res, Map<String, Object> attributes, Exception e, String templateName) {
        res.status(500);
        if (e instanceof SQLException && null != e.getCause()) {
            attributes.put("errorMsg", e.getCause().getMessage());
        } else {
            attributes.put("errorMsg", e.getMessage());
        }
        e.printStackTrace();
        return new ModelAndView(attributes, templateName);
    }


    private static User getLoggedInUser(Request req) {
        spark.Session session = req.session();
        User user = null;
        if (null != session) {
            user = session.<User>attribute("user");
        }
        return user;
    }

    private static void handleGetUserById() {
        get("/user/:id", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            User user = getLoggedInUser(req);
            attributes.put("user", user);
            try {
                String id = req.params(":id");
                User person = dao.load(User.class, Long.parseLong(id));
                if (null != person) {
                    attributes.put("user", person);
                } else {
                    attributes.put("errorMsg", "No user with the ID " + id + ".");
                }
                return new ModelAndView(attributes, "user.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "user.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static void handleGetRocketById() {
        get("/rocket/:id", (req, res) ->{
            Map<String, Object> attributes = new HashMap<>();
            Rocket rocket = new Rocket();
            attributes.put("rocket", rocket);
            try {
                String id = req.params(":id");
                Rocket newRocket = dao.load(Rocket.class, Long.parseLong(id));
                if (null != newRocket){
                    attributes.put("rocket", newRocket);
                } else {
                    attributes.put("errorMsg", "No rocket with the ID " + id + ".");
                }
                return new ModelAndView(attributes, "rocket.html.ftl");
            } catch (Exception e){
                return handleException(res, attributes, e, "rocket.html.ftl");
            }
        },new FreeMarkerEngine());
    }

    private static void handlePostCreateRocket() {
        post("/rockets/create", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String name = req.queryParams("name");
            String country = req.queryParams("country");
            int firstYearFlight = Integer.parseInt(req.queryParams("firstYearFlight"));
            String lspName = req.queryParams("manufacturer");
            LaunchServiceProvider manufacturer = dao.getLspByName(lspName);

            attributes.put("name", name);
            attributes.put("country", country);
            attributes.put("firstYearFlight", firstYearFlight);
            attributes.put("manufacturer", manufacturer);

            logger.info("Create Rocket " + name);

            Rocket rocket;
            rocket = dao.getRocketByName(name);
            if (rocket != null){
                attributes.put("errorMsg", "Rocket has exist");
                return new ModelAndView(attributes, "create_rocket.html.ftl");
            }

            try {
                rocket = new Rocket();
                rocket.setName(name);
                rocket.setCountry(country);
                rocket.setFirstYearFlight(firstYearFlight);
                rocket.setManufacturer(manufacturer);
                dao.createOrUpdate(rocket);

                res.status(301);
                req.session(true);
                req.session().attribute("rocket",rocket);
                res.redirect("/");
                return new ModelAndView(attributes, "rockets.html.ftl");
            } catch (Exception e){
                return handleException(res, attributes, e, "create_rocket.html.ftl");
            }

        }, new FreeMarkerEngine());
    }

    private static void handleGetCreateRocket() {
        get("/rockets/create", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "");
            attributes.put("country", "");
            attributes.put("firstYearFlight", "");
            attributes.put("manufacturer", dao.loadAll(LaunchServiceProvider.class));
            return new ModelAndView(attributes, "create_rocket.html.ftl");
        }, new FreeMarkerEngine());
    }

    private static void handleGetRockets() {
        get("/rockets", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            try {
                attributes.put("rockets", dao.loadAll(Rocket.class));
                return new ModelAndView(attributes, "rockets.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "rockets.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static void handleGetLsps() {
        get("/lsps", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            try {
                attributes.put("lsps", dao.loadAll(LaunchServiceProvider.class));
                return new ModelAndView(attributes, "launchserviceproviders.html.ftl");
            } catch (Exception e){
                return handleException(res, attributes, e, "launchserviceproviders.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static void handleGetCreateLsp() {
        get("/lsps/create", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "");
            attributes.put("yearFounded", "");
            attributes.put("country", "");
            attributes.put("headquarters", "");
            return new ModelAndView(attributes, "create_lsp.html.ftl");
        }, new FreeMarkerEngine());
    }

    private static void handlePostCreateLsp(){
        post("/lsps/create", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String name = req.queryParams("name");
            int yearFounded = Integer.parseInt(req.queryParams("yearFounded"));
            String country = req.queryParams("country");
            String headquarters = req.queryParams("headquarters");

            attributes.put("name", name);
            attributes.put("yearFounded", yearFounded);
            attributes.put("country", country);
            attributes.put("headquarters", headquarters);

            logger.info("Create LaunchServiceProvider " + name);

            LaunchServiceProvider lsp;
            lsp = dao.getLspByName(name);
            if (lsp != null){
                attributes.put("errorMsg", "Launch Service Provider has exist");
                return new ModelAndView(attributes, "create_lsp.html.ftl");
            }
            try {
                lsp = new LaunchServiceProvider();
                lsp.setName(name);
                lsp.setCountry(country);
                lsp.setYearFounded(yearFounded);
                lsp.setHeadquarters(headquarters);
                dao.createOrUpdate(lsp);

                res.status(301);
                req.session(true);
                req.session().attribute("lsp",lsp);
                res.redirect("/");
                return new ModelAndView(attributes, "launchserviceproviders.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "create_lsp.html.ftl");
            }

        }, new FreeMarkerEngine());
    }

    private static void handleGetLspById(){
        get("/lsp/:id", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            LaunchServiceProvider lsp = new LaunchServiceProvider();
            attributes.put("lsp", lsp);
            try {
                String id = req.params(":id");
                LaunchServiceProvider newLsp = dao.load(LaunchServiceProvider.class, Long.parseLong(id));
                if (null != newLsp){
                    attributes.put("lsp", newLsp);
                } else {
                    attributes.put("errorMsg", "No launch service provider with the ID " + id + ".");
                }
                return new ModelAndView(attributes, "launchserviceprovider.html.ftl");
            } catch (Exception e){
                return handleException(res, attributes, e, "launchserviceprovider.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static Properties loadProperties() throws IOException {
        ClassPathResource resource = new ClassPathResource("app.properties");
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = resource.getInputStream();
            properties.load(stream);
            return properties;
        } finally {
            closeSilently(stream);
        }
    }
}
