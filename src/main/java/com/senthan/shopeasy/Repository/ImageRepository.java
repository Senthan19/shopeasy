package com.senthan.shopeasy.Repository;

import com.senthan.shopeasy.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
