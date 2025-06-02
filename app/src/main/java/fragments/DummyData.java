package fragments;

import java.util.ArrayList;
import java.util.List;

import models.MovieDetailModel;

public class DummyData {

    public static List<MovieDetailModel> getDummyMovies() {
        List<MovieDetailModel> movieList = new ArrayList<>();

        movieList.add(new MovieDetailModel(
                "Inception",
                "Christopher Nolan",
                "2010",
                148,
                "A skilled thief is given a chance at redemption if he can successfully perform inception.",
                "https://m.media-amazon.com/images/I/51s+Kc8kX-L._AC_.jpg",
                "https://www.youtube.com/watch?v=YoHD9XEInc0",
                "https://m.media-amazon.com/images/I/81p+xe8cbnL._AC_SL1500_.jpg"
        ));

        movieList.add(new MovieDetailModel(
                "Interstellar",
                "Christopher Nolan",
                "2014",
                169,
                "A team of explorers travel through a wormhole in space to ensure humanity's survival.",
                "https://m.media-amazon.com/images/I/71n58YFQZSL._AC_SL1024_.jpg",
                "https://www.youtube.com/watch?v=zSWdZVtXT7E",
                "https://m.media-amazon.com/images/I/81kz03e1uUL._AC_SL1500_.jpg"
        ));

        movieList.add(new MovieDetailModel(
                "The Matrix",
                "Lana Wachowski, Lilly Wachowski",
                "1999",
                136,
                "A hacker learns about the true nature of reality and his role in the war against its controllers.",
                "https://m.media-amazon.com/images/I/51EG732BV3L._AC_.jpg",
                "https://www.youtube.com/watch?v=vKQi3bBA1y8",
                "https://m.media-amazon.com/images/I/81Z+9V4lJGL._AC_SL1500_.jpg"
        ));

        movieList.add(new MovieDetailModel(
                "The Dark Knight",
                "Christopher Nolan",
                "2008",
                152,
                "Batman faces the Joker, a criminal mastermind who wants to plunge Gotham City into anarchy.",
                "https://m.media-amazon.com/images/I/51k0qa6zY-L._AC_.jpg",
                "https://www.youtube.com/watch?v=EXeTwQWrcwY",
                "https://m.media-amazon.com/images/I/81AJdOIEIhL._AC_SL1500_.jpg"
        ));


        return movieList;
    }
}
