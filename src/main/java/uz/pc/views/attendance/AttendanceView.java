package uz.pc.views.attendance;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import uz.pc.MainView;
import uz.pc.backend.Models.Employee;
import uz.pc.backend.Models.WorkdayAttendance;
import uz.pc.backend.Repositories.AttendanceRepository;
import uz.pc.backend.Repositories.EmployeeRepository;
import uz.pc.backend.Repositories.SettingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Route(value = "attendance", layout = MainView.class)
@PageTitle("Attendance")
//@CssImport("styles/views/attendance/attendance-view.css")
public class AttendanceView extends VerticalLayout implements AfterNavigationObserver {

    @Autowired
    private AttendanceRepository attendanceRepository;
    private EmployeeRepository empRepo;
    private SettingRepository settingsRepo;


    private Grid<WorkdayAttendance> grid;
    private ComboBox<Employee> combo;
    @Autowired
    public AttendanceView(AttendanceRepository attendanceRepository, EmployeeRepository empRepo, SettingRepository settingsRepo) {
        this.attendanceRepository = attendanceRepository;
        this.empRepo = empRepo;
        this.settingsRepo = settingsRepo;
        combo = new ComboBox<>();

        Button arrival = new Button("Arrival", VaadinIcon.ARROW_LEFT.create());
        Button depart = new Button("Depart",VaadinIcon.ARROW_RIGHT.create());
        this.grid = new Grid<>();
        HorizontalLayout layout = new HorizontalLayout(arrival,combo,depart);
        setHorizontalComponentAlignment(Alignment.CENTER,layout);
        List<Employee> departmentList = empRepo.findAll();

        combo.setItemLabelGenerator(Employee::getIdentification);
        combo.setItems(departmentList);

        combo.setItems(departmentList);

        combo.setPlaceholder("Write identification number employee");

        grid.setHeight("650px");
        grid.setWidth("100%");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        setHorizontalComponentAlignment(Alignment.CENTER,grid);
        grid.addColumn(WorkdayAttendance::getEmpCardId).setHeader("#");
        grid.addColumn(WorkdayAttendance::getArrivalTime).setHeader("ArrivalTime");
        grid.addColumn(WorkdayAttendance::getArrivalDate).setHeader("ArrivalDate");
        grid.addColumn(WorkdayAttendance::getArrivalDifference).setHeader("Difference");
        grid.addColumn(WorkdayAttendance::getWeekday).setHeader("Weekday");
        grid.addColumn(WorkdayAttendance::getDepartTime).setHeader("Depart Time");
        grid.addColumn(WorkdayAttendance::getDepartureDifference).setHeader("Difference");
        grid.addColumn(WorkdayAttendance::getWorkHour).setHeader("Work hour");
        VerticalLayout layout3=new VerticalLayout(layout,grid);
        add(layout3);
        depart.addClickListener(e -> registerDeparture());
        arrival.addClickListener(e -> registerArrival());
    }

    private void registerArrival() {
        WorkdayAttendance emp = new WorkdayAttendance();
        emp.setEmpCardId(combo.getValue().getIdentification());
        emp.setArrivalTime(LocalTime.now());
        emp.setArrivalDate(LocalDate.now());
        emp.setArrivalDifference(arrival_diff(LocalTime.now()));
        emp.setWeekday(LocalDate.now().getDayOfWeek().name());

        attendanceRepository.save(emp);
        grid.setItems(attendanceRepository.findAll());
    }

    private void registerDeparture() {
        WorkdayAttendance wa = attendanceRepository.findByEmpCardIdAndWorkedFalse(combo.getValue().getIdentification());

        wa.setWorked(true);
        wa.setDepartTime(LocalTime.now());
        wa.setWorkHour(ChronoUnit.MINUTES.between(
                wa.getArrivalTime(), wa.getDepartTime()
        ));
        wa.setDepartureDifference(depature_diff(LocalTime.now()));

        attendanceRepository.save(wa);
        grid.setItems(attendanceRepository.findAll());
    }

    private String arrival_diff(LocalTime arrivale){
        return time(ChronoUnit.MINUTES.between(
                arrivale,settingsRepo.findById(1).getArrivalTime()
        ));
    }
    private String depature_diff(LocalTime depature){

        return time(ChronoUnit.MINUTES.between(
                settingsRepo.findById(1).getDepartureTime(),depature
        ));
    }
    private  String time(Long aLong){
        String s="";
        Long a,b;
        a=aLong/60;
        b=Math.abs(aLong)-Math.abs(a*60);
        s=s+a.toString()+":"+b.toString();
        return s;
    }
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        grid.setItems(attendanceRepository.findAll());
    }


}

