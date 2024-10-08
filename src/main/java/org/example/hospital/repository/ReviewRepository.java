package org.example.hospital.repository;

import jdk.jfr.Registered;
import org.example.hospital.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>
{
}
