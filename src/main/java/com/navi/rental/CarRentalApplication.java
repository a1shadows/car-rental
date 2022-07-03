package com.navi.rental;

import com.navi.rental.model.Operation;
import com.navi.rental.service.interfaces.IRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.function.Consumer;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@SpringBootApplication
@RequiredArgsConstructor
public class CarRentalApplication implements ApplicationRunner {

    private final IRentalService rentalService;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CarRentalApplication.class);
        Properties properties = new Properties();
        properties.setProperty("spring.main.banner-mode", "off");
        properties.setProperty("logging.pattern.console", "");
        application.setDefaultProperties(properties);
        application.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        Files.lines(Paths.get(args.getSourceArgs()[0])).forEach(inputConsumer());
    }


    private Consumer<String> inputConsumer() {
        return operationString -> {
            if (isEmpty(operationString))
                return;
            Operation operation = new Operation(operationString.split(" "));
            switch (operation.getOperator()) {
                case ADD_BRANCH:
                    rentalService.onboardBranch(
                            operation.getOperand(),
                            operation.getVehicleTypesFromArguments()
                    );
                    break;
                case ADD_VEHICLE:
                    rentalService.addVehicle(
                            operation.getOperand(),
                            operation.getVehicleFromArguments()
                    );
                    break;
                case BOOK:
                    rentalService.book(
                            operation.getOperand(),
                            operation.getArguments().get(0),
                            Integer.valueOf(operation.getArguments().get(1)),
                            Integer.valueOf(operation.getArguments().get(2))
                    );
                    break;
                case DISPLAY_VEHICLES:
                    rentalService.displayVehicles(operation.getOperand(),
                            Integer.valueOf(operation.getArguments().get(0)),
                            Integer.valueOf(operation.getArguments().get(1))
                    );
            }
        };
    }
}
