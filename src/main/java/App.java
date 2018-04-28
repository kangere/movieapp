import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteImpl;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static spark.Spark.*;

public class App {

    private MoviesDAO movieDetails;
    private final Configuration cfg;

    public static void main(String[] args) throws IOException {

        //initialising
        if (args.length == 0) {
            new App("mongodb://localhost");
        } else {
            new App(args[0]);
        }
      
    }

    private static List<Document> getSomeDocs() {
        List<String> genres = new ArrayList<>();
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Comedy");
        genres.add("Fantasy");
        genres.add("Scifi");

        List<String> directors = new ArrayList<>();
        directors.add("Taika Waititi");

        LocalDate date = LocalDate.of(2017, 10, 10);

        Document doc = new Document("const", "tt3501632").append("title", "Thor:Ragnarok")
                .append("url", "https://www.imdb.com/title/tt3501632")
                .append("type", "movie")
                .append("imdb_rating", 8)
                .append("runtime", 130)
                .append("year", 2017)
                .append("genres", genres)
                .append("num_votes", 279166)
                .append("release_date", date)
                .append("directors", directors);

        List<Document> docs = new ArrayList<>();

        docs.add(doc);

        return docs;
    }

    public App(String mongoURI) throws IOException {

        final MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURI));
        final MongoDatabase movieDB = mongoClient.getDatabase("movies");

        movieDetails = new MoviesDAO(movieDB);

        cfg = createFreemarkerConfiguration();
        initializeRoutes();
    }

    abstract class FreemarkerBasedRoute extends RouteImpl {
        final Template template;


        protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
            super(path);
            template = cfg.getTemplate(templateName);
        }

        @Override
        public Object handle(Request request, Response response) {
            StringWriter writer = new StringWriter();

            try {
                doHandle(request, response, writer);
            } catch (Exception e) {
                e.printStackTrace();
                response.redirect("/internal_error");
            }
            return writer;
        }

        protected abstract void doHandle(final Request request, final Response response, final StringWriter writer)
                throws IOException, TemplateException;
    }

    private void initializeRoutes() throws IOException {

        get("/", new FreemarkerBasedRoute("/", "main.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {


                SimpleHash params = new SimpleHash();

                List<Document> movies = movieDetails.sortAscending("title");
                params.put("movies", movies);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        post("/post_genre", new FreemarkerBasedRoute("/", "main.ftl") {
            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {

                String genre = request.queryParams("genre");
                String id = request.queryParams("id");


                if (genre != null && id != null) {
                    movieDetails.addGenre(id, genre);
                } else {
                    System.out.println(id + " " + genre + " unable to insert genre");
                }

            }
        });

        get("/filters", new FreemarkerBasedRoute("/", "filter.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {
                String search_phrase = request.queryParams("textSearch");
                SimpleHash params = new SimpleHash();


                List<Document> movies = null;

                if (search_phrase != null) {
                    movies = movieDetails.searchByField(search_phrase);
                } else {
                    System.out.println(search_phrase + " unable to perform such");
                }


                params.put("movies", movies);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        get("/filter_genre", new FreemarkerBasedRoute("/", "filter.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {
                String search_phrase = request.queryParams("genreSearch");
                SimpleHash params = new SimpleHash();


                List<Document> movies = null;

                if (search_phrase != null) {
                    movies = movieDetails.searchByGenre(search_phrase);
                } else {
                    System.out.println(search_phrase + " unable to perform such");
                }


                params.put("movies", movies);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //sort by ratings descending
        get("/ratings_sort_descending", new FreemarkerBasedRoute("/", "main.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {
                SimpleHash params = new SimpleHash();

                List<Document> movies = movieDetails.sortDescending("imdb_rating");
                params.put("movies", movies);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        get("/ratings_sort_ascending", new FreemarkerBasedRoute("/", "main.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {
                SimpleHash params = new SimpleHash();

                List<Document> movies = movieDetails.sortAscending("imdb_rating");
                params.put("movies", movies);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        get("/num_votes_descending", new FreemarkerBasedRoute("/", "main.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {
                SimpleHash params = new SimpleHash();

                List<Document> movies = movieDetails.sortDescending("num_votes");
                params.put("movies", movies);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        get("/num_votes_ascending", new FreemarkerBasedRoute("/", "main.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {
                SimpleHash params = new SimpleHash();

                List<Document> movies = movieDetails.sortAscending("num_votes");
                params.put("movies", movies);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        get("/stats_page", new FreemarkerBasedRoute("/", "stats.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) {
                SimpleHash params = new SimpleHash();

                String explain = movieDetails.explainAscendingSortQuery("title");
                String imdb_explain = movieDetails.explainAscendingSortQuery("imdb");
                params.put("titleSort", explain);
                params.put("imdbSort", imdb_explain);

                try {
                    template.process(params, writer);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });


        // used to process internal errors
        get("/internal_error", new FreemarkerBasedRoute("/internal_error", "error_template.ftl") {

            @Override
            protected void doHandle(Request request, Response response, StringWriter writer) throws IOException, TemplateException {
                SimpleHash root = new SimpleHash();

                root.put("error", "System has encountered an error.");
                template.process(root, writer);
            }
        });


    }


    private Configuration createFreemarkerConfiguration() {
        Configuration retVal = new Configuration();
        retVal.setClassForTemplateLoading(App.class, "/");
        return retVal;
    }
}
