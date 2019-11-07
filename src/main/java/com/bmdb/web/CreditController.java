package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmdb.business.Credit;
import com.bmdb.db.CreditRepository;

@CrossOrigin
@RestController
@RequestMapping("/credits")
public class CreditController {

	@Autowired
	private CreditRepository creditRepo;
	
	// List - Return all credits
	@GetMapping("/")
	public JsonResponse listCredits() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.findAll());
		} catch(Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Get - return 1 credit for a given id
	@GetMapping("/{id}")
	public JsonResponse getCredit(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.findById(id));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// Add - adds a new credit
	@PostMapping("/")
	public JsonResponse addCredit(@RequestBody Credit c) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.save(c));
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
	public JsonResponse updateCredit(@RequestBody Credit c) {
		JsonResponse jr = null;
		try {
			if (creditRepo.existsById(c.getId())) {
				jr = JsonResponse.getInstance(creditRepo.save(c));
			} else {
				jr = JsonResponse.getInstance("Error updating credit! Credit: " + c.getId() + " does not exist!");
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
	
	// Delete - deletes a credit by id
	@DeleteMapping("/{id}")
	public JsonResponse deleteCredit(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			if(creditRepo.existsById(id)) {
				creditRepo.deleteById(id);
				jr = JsonResponse.getInstance("Credit " + id + " successfully deleted");
			} else {
				jr = JsonResponse.getInstance("Error deleting credit! Credit: " + id + " does not exist!");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
}
