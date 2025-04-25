package com.example.autoservice.repository;

import com.example.autoservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByEmployeeUserId(Long userId);
    List<Schedule> findByCarOwnerId(Long userId);

    // <-- новый метод для удаления всех записей по carId
    @Modifying
    @Transactional
    @Query("delete from Schedule s where s.car.id = :carId")
    void deleteByCarId(@Param("carId") Long carId);
}
