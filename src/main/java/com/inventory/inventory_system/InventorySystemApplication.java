package com.inventory.inventory_system;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// for notifictaions
@EnableScheduling
@SpringBootApplication
public class InventorySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorySystemApplication.class, args);
    }

    // this bean code is used to insert the intial data in db
    // @Bean
    // CommandLineRunner runner(AlertRepository alertRepository) {
    //     return args -> {
    //         alertRepository.save(new Alert("Test Alert - System setup working!"));
    //         System.out.println("✅ Test Alert inserted into MongoDB!");
    //     };
    // }
}
