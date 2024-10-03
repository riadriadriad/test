package com.technicaltest.entities;

import java.time.LocalDate;

public record Operation(LocalDate operationDate,int amount,int balance ) {
}
