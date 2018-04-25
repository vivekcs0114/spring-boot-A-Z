package com.synerzip.demo.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    //URI versioning

    @GetMapping("/v1/persion")
    public PersonV1 personV1() {
        return new PersonV1("vivek tiwari");
    }

    @GetMapping("/v2/persion")
    public PersonV2 personV2() {
        return new PersonV2(new Name("vivek","tiwari"));
    }

    //Param versioning

    @GetMapping(value = "/persion/param", params = "version=1")
    public PersonV1 paramV1() {
        return new PersonV1("vivek tiwari");
    }

    @GetMapping(value = "/persion/param", params = "version=2")
    public PersonV2 paramV2() {
        return new PersonV2(new Name("vivek","tiwari"));
    }

    //Header versioning

    @GetMapping(value = "/persion/header", headers = "X_API_VERSION=1")
    public PersonV1 headerV1() {
        return new PersonV1("vivek tiwari");
    }

    @GetMapping(value = "/persion/header", headers = "X_API_VERSION=2")
    public PersonV2 headerV2() {
        return new PersonV2(new Name("vivek","tiwari"));
    }

    //MIME Type/Produces versioning

    @GetMapping(value = "/persion/produces", produces = "application/com.syn.app-v1+json")
    public PersonV1 producesV1() {
        return new PersonV1("vivek tiwari");
    }

    @GetMapping(value = "/persion/produces", produces = "application/com.syn.app-v2+json")
    public PersonV2 producesV2() {
        return new PersonV2(new Name("vivek","tiwari"));
    }
}
