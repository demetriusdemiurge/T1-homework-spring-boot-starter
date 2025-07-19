package com.demetriusdemiurge.t1_homework_spring_boot_starter.service;

import com.demetriusdemiurge.t1_homework_spring_boot_starter.data.Command;

public interface CommandService {

    public void defineCommand(Command command);

    public void executeCommand(Command command);

    public void executeCommonCommand();

}
