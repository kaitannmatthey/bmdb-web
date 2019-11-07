package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Movie;
import com.bmdb.db.MovieRepository;

@CrossOrigin
@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieRepository movieRepo;
	
	// List - Return all Movies
	@GetMapping("/")
	public JsonResponse listMovies() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(movieRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Get - Return 1 Movie for a given ID
	@GetMapping("/{id}")
	public JsonResponse getMovie(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(movieRepo.findById(id));
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Add - Adds New Movie
	@PostMapping("/")
	public JsonResponse addMovie(@RequestBody Movie m) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(movieRepo.save(m));			
		} 
		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Update - Updates a Movie
	@PutMapping("/")
	public JsonResponse updateMovie(@RequestBody Movie m) {
		JsonResponse jr = null;
		try {
			if (movieRepo.existsById(m.getId())) {
				jr = JsonResponse.getInstance(movieRepo.save(m));
			} else {
				jr = JsonResponse.getInstance("Error updating movie! Movie " + m.getId() + " does not exist!");
			}
		} 
		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Delete - Deletes a Movie
	@DeleteMapping("/{id}")
	public JsonResponse deleteMovie(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			if(movieRepo.existsById(id)) {
				movieRepo.deleteById(id);
				jr = JsonResponse.getInstance("Movie " + id + " successfully deleted!");
			} else {
				jr = JsonResponse.getInstance("Error deleting movie! Movie " + id + " does not exist!");
			}
		} 
		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
}
