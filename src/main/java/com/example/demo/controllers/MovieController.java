package com.example.demo.controllers;



import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.Movie;
import com.example.demo.repository.MovieRepository;

@RestController
public class MovieController {
	
	private MovieRepository movieRepository;
	
	@Autowired
	public MovieController(MovieRepository movieRepository){
		this.movieRepository  =  movieRepository;
	}
	
	@RequestMapping(path = "/movies/{movieId}", method = RequestMethod.GET)
	public Movie get(@PathVariable Long movieId){
		return this.movieRepository.findOne(movieId);//give me the movie that has this movieId.
	}
	
	@RequestMapping(path = "/movies", method = RequestMethod.POST)
	public ResponseEntity<?> store(@RequestBody Movie movie){
		Movie savedMovie = this.movieRepository.save(movie);
		if(savedMovie != null){ //if the movie was saved successfully
			return ResponseEntity.created(URI.create("/movies/"+ savedMovie.getId())).build();
		}
		
		return ResponseEntity.unprocessableEntity().build();
	}
	
	@RequestMapping(path = "/movies", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		// what this does is that it retrieves all movies in the datastore.
		return ResponseEntity.ok().body(this.movieRepository.findAll());
	}
	
	@RequestMapping(path = "/movies/{movieId}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Movie movie){
		//we first check if the movie with the id exist.
		if(this.movieRepository.findOne(movie.getId()) != null){
			Movie updatedMovie = this.movieRepository.save(movie);
			return ResponseEntity.ok().body(updatedMovie);
		}
		
		return ResponseEntity.badRequest().body("movie does not exist");
	}
	
	@RequestMapping(path = "/movies/{movieId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMovie(@PathVariable Long movieId){
		this.movieRepository.delete(movieId);
		return ResponseEntity.ok("movie deleted successfully");
	}
	
	
	
	
}
