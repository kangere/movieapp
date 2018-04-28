<!DOCTYPE html>
<html>
<head>
    <title>Stats Page</title>
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
        <div class="row">
            <div class="col-">
                <div class="card">
                    <div class="card-title" style="width: 35rem;">
                       <h3> Sort by Title alphabetically</h3>
                    </div>
                    <div class="class-body">
                        <pre id="title_explain"></pre>

                        <script>
                            document.getElementById("title_explain").innerHTML = JSON.stringify(${titleSort},undefined,2);
                        </script>

                    </div>
                </div>
            </div>

            <div class="col-">
                    <div class="card">
                        <div class="card-title" style="width: 35rem;">
                            <h3> Sort by IMDB Rating Ascending</h3>
                        </div>
                        <div class="class-body">
                            <pre id="imdb_explain"></pre>

                            <script>
                                document.getElementById("imdb_explain").innerHTML = JSON.stringify(${imdbSort},undefined,2);
                            </script>

                        </div>
                    </div>
            </div>
        </div>
    </div>
</body>

</html>