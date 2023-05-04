package com.vettica.home;

import com.vettica.model.Post;
import com.vettica.utils.QuadFunction;
import com.vettica.utils.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class MovieRecommendations {

    static WebDriver driver = SeleniumDriver.getDriver();

    Supplier<Optional<List<WebElement>>> getListOfMovies = ()->{
        driver.get("https://www.imdb.com/?ref_=nv_home");
        WebElement divOfMovies = driver.findElement(
                By.cssSelector("div.ipc-sub-grid.ipc-sub-grid--page-span-3.ipc-sub-grid--nowrap.ipc-shoveler__grid")
        );
        List<WebElement> listOfMovies = driver.findElements(
                By.cssSelector("div.ipc-poster-card.ipc-poster-card--baseAlt.ipc-poster-card--dynamic-width.top-picks-title.ipc-sub-grid-item.ipc-sub-grid-item--span-2")
        );

        return Optional.ofNullable(listOfMovies);
    };

    Function<String, Image> getImageFromURL = (imageURL) -> {
        try {
            URL image = new URL(imageURL);
            return ImageIO.read(image);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid image URL: ", e);
        }
    };

    QuadFunction<String, Double, String, String, Post> generatePost = (imageURL, stars, title, trailer) -> {
        Image movieImage = getImageFromURL.apply(imageURL);
        Post post = new Post(movieImage, stars, title, trailer);
        return post;
    };

    Supplier<Optional<List<Post>>> getMovieHighlights = () ->{
        Optional<List<WebElement>> movies = getListOfMovies.get();
        List<Post> listOfHighlightsMovies = new ArrayList<>();

        movies.ifPresent(
                movieList -> {
                    movieList.forEach(
                            movie -> {
                                WebElement image = movie.findElement(By.cssSelector("div.img div:nth-child(2)"));
                                String src = image.findElement(By.tagName("img")).getAttribute("src");
                                String starGroup = movie.findElement(By.cssSelector("div.ipc-rating-star-group"))
                                        .findElement(By.xpath(".//span")).getText();
                                double stars = Double.parseDouble(starGroup);
                                String title = movie.findElement(By.cssSelector(
                                        "a.ipc-poster-card__title.ipc-poster-card__title--clamp-2.ipc-poster-card__title--clickable"
                                )).getText();
                                WebElement trailerDiv = movie.findElement(By.cssSelector("div.both-card-actions")).
                                        findElement(By.xpath(".//a"));
                                String trailer = trailerDiv.getAttribute("href").toString();
                                Post post = generatePost.apply(src, stars, title, trailer);
                                listOfHighlightsMovies.add(post);
                            }
                    );
                }
        );

        return Optional.ofNullable(listOfHighlightsMovies);
    };

}