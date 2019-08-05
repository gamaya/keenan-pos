package com.keenan;

import com.keenan.config.DataConfig;
import com.keenan.config.ViewConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
    DataConfig.class,
    ViewConfig.class
})
public class KeenanPosApplication implements CommandLineRunner {

    @Autowired
    private ViewHandler viewHandler;

    public static void main(String... args) {
        SpringApplication springApp = new SpringApplication(KeenanPosApplication.class);
        springApp.setBannerMode(Banner.Mode.OFF);
        springApp.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        viewHandler.handleViews();
    }

}
