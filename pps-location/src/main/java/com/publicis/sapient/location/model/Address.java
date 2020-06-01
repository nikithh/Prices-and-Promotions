package com.publicis.sapient.location.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author sursuhas
 * @since March 2020
 * @see Bean for Address
 */

public class Address {

    @NotNull
    private String streetName;
    @NotNull
    private String city;
    @NotNull
    @Min(value = 10000)
    @Max(value = 999999)
    private Integer pin;

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(final int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Address [streetName=" + this.streetName + ", city=" + this.city + ", pin=" + this.pin + "]";
    }

    public Address(final String streetName,
            final String city,
            final int pin) {
        super();
        this.streetName = streetName;
        this.city = city;
        this.pin = pin;
    }

    public Address() {
        super();
    }

}
