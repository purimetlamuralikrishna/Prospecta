package com.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.models.Data;
import com.test.models.Result;
import com.test.models.Values;

@RestController
public class Controller {
	
	@Autowired
	RestTemplate restTemplate;
	
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		
		return new ResponseEntity<String>("Hello",HttpStatus.ACCEPTED);
	}
		
		
		
		
		@GetMapping("/entries/{category}")
		public ResponseEntity<List<Result> > getEntriesHandler(@PathVariable("category") String category){
			
			
			Data d= restTemplate.getForObject("https://api.publicapis.org/entries", Data.class);
			
			List<Values> values= d.getValues();
			List<Result> list = new ArrayList<>();
			
			for(int i=0;i<values.size();i++) {
				
				if(values.get(i).getCategory().equalsIgnoreCase(category)) {
					Result r = new Result();
					r.setTitle(values.get(i).getApi());
					r.setDescription(values.get(i).getDescription());
					list.add(r);
				}
				
			}
			 
			return new ResponseEntity<List<Result>>(list,HttpStatus.ACCEPTED);
			
		}
		
		@PostMapping("/entries")
		public ResponseEntity<Values> saveEntriesHandler(@RequestBody Values value) {
			
			Data d= restTemplate.getForObject("https://api.publicapis.org/entries", Data.class);
			
			
			List<Values> entries= d.getValues();
			entries.add(value);
			return new ResponseEntity<Values>(value,HttpStatus.ACCEPTED);
			
			
			
		}
		
		
}

