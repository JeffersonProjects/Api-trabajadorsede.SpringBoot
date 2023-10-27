package com.api.rest.trabajadorsede.controladores;

import java.net.URI;
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
import com.api.rest.trabajadorsede.entidades.Trabajador;
import com.api.rest.trabajadorsede.repositorios.SedeRepository;
import com.api.rest.trabajadorsede.repositorios.TrabajadorRepository;

@RestController
@RequestMapping("/api/trabajador")
public class TrabajadorController {
	
	@Autowired
	private TrabajadorRepository trabajadorRepository;
	
	@Autowired
	private SedeRepository sedeRepository;
	
	@GetMapping
	public ResponseEntity<Page<Trabajador>> listarTrabajador(Pageable pageable){
		return ResponseEntity.ok(trabajadorRepository.findAll(pageable));
	}	
	
	@PostMapping
	public ResponseEntity<Trabajador> guardarTrabajador(@Valid @RequestBody Trabajador trabajador){
		Optional<Sede> sedeOptional = sedeRepository.findById(trabajador.getSede().getId());
		
		if(!sedeOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		trabajador.setSede(sedeOptional.get());
		Trabajador trabajadorGuardado = trabajadorRepository.save(trabajador);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(trabajadorGuardado.getId()).toUri();
		
		return ResponseEntity.created(ubicacion).body(trabajadorGuardado);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Trabajador> actualizarTrabajador(@PathVariable Integer id, @Valid @RequestBody Trabajador trabajador){
		Optional<Sede> sedeOptional = sedeRepository.findById(trabajador.getSede().getId());
		
		if(!sedeOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		Optional<Trabajador> trabajadorOptional = trabajadorRepository.findById(id);
		if(!trabajadorOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		trabajador.setSede(sedeOptional.get());
		trabajador.setId(trabajadorOptional.get().getId());
		trabajadorRepository.save(trabajador);
				
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Trabajador> eliminarTrabajador(@PathVariable Integer id){
		Optional<Trabajador> trabajadorOptional = trabajadorRepository.findById(id);
		
		if(!trabajadorOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		trabajadorRepository.delete(trabajadorOptional.get());
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/{id}")	
	public ResponseEntity<Trabajador> obtenerTrabajadorPorId(@PathVariable Integer id){
		Optional<Trabajador> trabajadorOptional = trabajadorRepository.findById(id);
		
		if(!trabajadorOptional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		return ResponseEntity.ok(trabajadorOptional.get());
	}
	
}
