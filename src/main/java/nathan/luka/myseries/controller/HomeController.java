package nathan.luka.myseries.controller;

import nathan.luka.myseries.dataprovider.DataProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private DataProvider model = DataProvider.getDataProvider().getInstance();

}
