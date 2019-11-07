package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepository;

@CrossOrigin
@RestController
@RequestMapping("/actors")
public class ActorController {

	@Autowired
	private ActorRepository actorRepo;
	
	// List - Return all actors
	@GetMapping("/")
	public JsonResponse listActors() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(actorRepo.findAll());
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Get - return 1 actor for a given id
	@GetMapping("/{id}")
	public JsonResponse getActor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(actorRepo.findById(id));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Add - adds a new actor
	@PostMapping("/")
	public JsonResponse addActor(@RequestBody Actor a) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(actorRepo.save(a));
		} 
		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Update - updates an actor
	@PutMapping("/")
	public JsonResponse updateActor(@RequestBody Actor a) {
		JsonResponse jr = null;
		try {
			if (actorRepo.existsById(a.getId())) {
				jr = JsonResponse.getInstance(actorRepo.save(a));
			} else {
				jr = JsonResponse.getInstance("Error updating actor! Actor: " + a.getId() + " does not exist!");
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
	
	// Delete - deletes an actor by id
	@DeleteMapping("/{id}")
	public JsonResponse deleteActor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			if(actorRepo.existsById(id)) {
				actorRepo.deleteById(id);
				jr = JsonResponse.getInstance("Actor " + id + " successfully deleted");
			} else {
				jr = JsonResponse.getInstance("Error deleting actor! Actor: " + id + " does not exist!");
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
