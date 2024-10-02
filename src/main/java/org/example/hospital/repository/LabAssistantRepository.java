package org.example.hospital.repository;

import org.example.hospital.entity.LabAssistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabAssistantRepository extends JpaRepository<LabAssistant, Long>
{
}


