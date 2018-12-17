/*
 * Copyright 2018 Johan Fredin
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.johanfredin.llama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LlamaExamplesApplication {

    /**
     * Global property for deciding whether or not to start the routes. By default all
     * examples should call this property so that we can easily switch of several routes at once
     * and then manually run the ones we are interested in.
     */
    public static final boolean AUTO_START_ROUTES = true;

    /**
     * SQL examples requires some sort of database connection and are therefore not started by default.
     */
    public static final boolean AUTO_START_SQL_ROUTES = true;

    public static void main(String[] args) {
        SpringApplication.run(LlamaExamplesApplication.class, args);
    }

}
