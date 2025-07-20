package com.demetriusdemiurge.bishop_starter.services;

import com.demetriusdemiurge.bishop_starter.data.Command;

public interface CommandService {

    public void defineCommand(Command command);

    public void executeCommand(Command command);

    public void executeCommonCommand();

}
