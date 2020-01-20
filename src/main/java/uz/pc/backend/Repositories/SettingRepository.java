package uz.pc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uz.pc.backend.Models.Setting;

import java.util.List;
@Service

public interface SettingRepository extends JpaRepository<Setting,Integer> {
    @Override
    List<Setting> findAll();
    Setting findById(int id);
}
