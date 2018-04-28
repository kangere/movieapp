import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.TextSearchOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.text;
import static com.mongodb.client.model.Updates.push;

public class MoviesDAO {

   private final MongoCollection<Document> movieDetails;

   public MoviesDAO(final MongoDatabase movieDB)
   {
        movieDetails = movieDB.getCollection("movieDetails");
   }



    public List<Document> sortDescending(String fieldName)
    {
        return movieDetails.find()
                .sort(Sorts.descending(fieldName))
                .into(new ArrayList<>());
    }

    public List<Document> sortAscending(String fieldName)
    {
        return movieDetails.find()
                .sort(Sorts.ascending(fieldName))
                .into(new ArrayList<>());
    }

    public List<Document> searchByField(String textSearch)
    {
        return movieDetails.find(Filters.text(textSearch)).into(new ArrayList<>());
    }

    public List<Document> searchByGenre(String genre)
    {
        return movieDetails.find(eq("genres",genre)).into(new ArrayList<>());
    }

    public String explainAscendingSortQuery(String fieldName)
    {

        return movieDetails.find()
                .sort(Sorts.ascending(fieldName))
                .modifiers(new Document("$explain",true)).first().toJson();
    }

   public void addGenre(String id, String Genre)
   {
       movieDetails.updateOne(eq("_id",id),push("genres",Genre));
   }
}
