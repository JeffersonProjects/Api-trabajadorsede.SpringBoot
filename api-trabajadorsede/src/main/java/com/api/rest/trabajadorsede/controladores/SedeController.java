package com.api.rest.trabajadorsede.controladores;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rest.trabajadorsede.entidades.Sede;
import com.api.rest.trabajadorsede.repositorios.SedeRepository;

@RestController
@RequestMapping("/api/sede")
public class SedeController {

	@Autowired
	private SedeRepository sedeRepository;
	
	//@GetMapping
	//public ResponseEntity<Page<Sede>> listarSedes(Pageable pageable){
	//	return ResponseEntity.ok(sedeRepository.findAll(pageable));
	//}
	
	@GetMapping
	public ResponseEntity<List<Sede>> listarSedes(){
		return ResponseEntity.ok(sedeRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Sede> guardarSede(@Valid @RequestBody Sede sede){
		Sede sedeGuardada = sedeRepository.save(sede);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(sedeGuardada.getId()).toUri();
		return ResponseEntity.created(ubicacion).body(sedeGuardada);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Sede> actualizarSede(@PathVariable Integer id, @Valid @RequestBody Sede sede){
		Optional<Sede> sedeOptional = sedeRepository.findById(id);
		
		if(!sedeOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		sede.setId(sedeOptional.get().getId());
		sedeRepository.save(sede);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Sede> eliminarSede(@PathVariable Integer id){
		Optional<Sede> sedeOptional = sedeRepository.findById(id);
		
		if(!sedeOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
				
		sedeRepository.delete(sedeOptional.get());		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Sede> obtenerSede(@PathVariable Integer id){
		Optional<Sede> sedeOptional = sedeRepository.findById(id);
	
		if(!sedeOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		return ResponseEntity.ok(sedeOptional.get());
	}
	
}
