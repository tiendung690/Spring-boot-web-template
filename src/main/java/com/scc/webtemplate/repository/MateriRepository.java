package com.scc.webtemplate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.scc.webtemplate.domain.Materi;

public interface MateriRepository extends Repository<Materi, String> {

	void delete(Materi deleted);
	
	Page<Materi> findAll(Pageable pageable);
	
	Materi findOne(String id);
	
	Materi save(Materi persisted);
	
}
