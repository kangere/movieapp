<!DOCTYPE html>
<html>
    <head>
        <title>Home page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>

        </style>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.bundle.min.js" integrity="sha384-lZmvU/TzxoIQIOD9yQDEpvxp6wEU32Fy0ckUgOH4EIlMOCdR823rg4+3gWRwnX1M" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container">
            <h1>Movies in collection</h1>
            <a href="/" class="btn btn-secondary">Sort by Title</a>
            <a href="/ratings_sort_descending" class="btn btn-primary" >Sort by Ratings Descending</a>
            <a href="/ratings_sort_ascending" class="btn btn-primary" >Sort by Ratings Ascending</a>
            <a href="/num_votes_descending" class="btn btn-info" >Sort by Number of Votes Descending</a>
            <a href="/num_votes_ascending" class="btn btn-info" >Sort by Number of Votes Ascending</a>


            <#if movies?has_content>

                <#list movies as movie>

                    <div class="card" style="width: 35rem;">
                        <div class="card-body">
                            <h2 class="card-title">${movie["title"]}</h2> <br>
                            <p class="card-text">
                                ID: ${movie["_id"]}<br>
                            Rating: ${movie["imdb_rating"]}  <br>
                            Number of votes: ${movie["num_votes"]}<br>
                            genres: <#list movie["genres"] as genre>${genre}&nbsp;&nbsp;</#list>
                            </p>
                            IMDB URL: <a class="card-link" href=${movie["url"]}> link</a>
                            <br>
                            Directors: <#list movie["directors"] as director>${director}&nbsp;&nbsp;</#list>
                            <br>
                            <#--Insert new Genre:
                            <form method="post" action="/post_genre">
                                <input type="text" id="genre_id" name="genre" value="">
                                <input type="text" name="id"> ${movie["_id"]}</input>
                                <input type="submit" value="submit" class="btn btn-success">

                            </form>-->
                        </div>
                    </div>

                </#list>

            </#if>
        </div>
    </body>

</html>