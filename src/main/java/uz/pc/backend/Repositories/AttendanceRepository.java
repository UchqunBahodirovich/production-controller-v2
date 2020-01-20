package uz.pc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uz.pc.backend.Models.WorkdayAttendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public interface AttendanceRepository extends JpaRepository<WorkdayAttendance, Integer> {

    @Override
    List<WorkdayAttendance> findAll();

    WorkdayAttendance findByArrivalTime(LocalTime arrivalTime);
    WorkdayAttendance findByArrivalDate(LocalDate date);
    WorkdayAttendance findById(int id);
    WorkdayAttendance findByDepartTime(LocalTime departTime);
    WorkdayAttendance findByEmpCardIdAndWorkedFalse(String cardId);

}

