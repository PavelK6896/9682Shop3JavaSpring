package app.web.pavelk.shop3.Indicator;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Timed
public class MyController {

    @GetMapping("/api/people")
    @Timed(extraTags = { "region", "us-east-1" })
    @Timed(value = "all.people", longTask = true)
    public List<String> listPeople() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("11");
        objects.add("22");
        return objects;
    }

}
