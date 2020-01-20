package uz.pc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import uz.pc.views.dashboard.DashboardView;
import uz.pc.views.products.ProductsView;
import uz.pc.views.employees.EmployeesView;
import uz.pc.views.production.ProductionView;
import uz.pc.views.attendance.AttendanceView;
import uz.pc.views.salaries.SalariesView;
import uz.pc.views.settings.SettingsView;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "Production Controller v2.0", shortName = "Production Controller v2.0")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@CssImport("./styles/shared-styles.css")
public class MainView extends AppLayout {

    private final Tabs menu;
    boolean a=false;

    public MainView() {
        menu = createMenuTabs();
        final boolean touchOptimized = true;
        DrawerToggle drawerToggle=new DrawerToggle();
        Icon icon= VaadinIcon.MENU.create();
        icon.setColor("white");
//        drawerToggle.setIcon(icon);
        drawerToggle.addThemeName(Lumo.DARK);
        drawerToggle.setClassName("draw-Toggle");
        Label label=new Label();
        HorizontalLayout layout=new HorizontalLayout();
        layout.setWidth("10px");
        label.setText("Production  Controller ");

        drawerToggle.addClickListener(e->{
            if(a){
                label.setText("Production Controller");
                a=false;
            }
            else{
                label.setText("PC");
                a=true;
            }
        });

        label.setClassName("label");
        HorizontalLayout horizontalLayout=new HorizontalLayout(layout,label,drawerToggle);
        horizontalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER,label);
        horizontalLayout.setHeight("100%");
        horizontalLayout.setWidth("100%");
        horizontalLayout.setClassName("layout-background");
        Label label1=new Label();
        label1.setText("MAIN SECTIONS");
        label1.setClassName("label1");
        HorizontalLayout layout1 =new HorizontalLayout();
        layout1.setWidth("100%");
        layout1.setHeight("50px");
        layout1.setAlignItems(FlexComponent.Alignment.CENTER);
        HorizontalLayout layout2=new HorizontalLayout();
        layout2.setWidth("20px");
        layout1.add(layout2,label1);
        layout1.addClassName("draw-layout");
        addToNavbar(touchOptimized,horizontalLayout);
        addToDrawer(layout1,menu);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Dashboard",VaadinIcon.DASHBOARD.create(), DashboardView.class));
        tabs.add(createTab("Products", VaadinIcon.PACKAGE.create(),ProductsView.class));
        tabs.add(createTab("Employees",VaadinIcon.USERS.create(), EmployeesView.class));
        tabs.add(createTab("Production",VaadinIcon.CLIPBOARD_TEXT.create(), ProductionView.class));
        tabs.add(createTab("Attendance",VaadinIcon.CALENDAR_USER.create(), AttendanceView.class));
        tabs.add(createTab("Salaries",VaadinIcon.CASH.create(), SalariesView.class));
        tabs.add(createTab("Settings",VaadinIcon.COG.create(), SettingsView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title,Icon icon,
            Class<? extends Component> viewClass) {
        return createTab(icon,populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Icon icon,Component content) {
        final Tab tab = new Tab();
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.setWidth("40px");
        icon.setSize("25px");
        tab.add(icon,horizontalLayout,content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink
                    && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }
}
