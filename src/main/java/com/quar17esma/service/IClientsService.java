package com.quar17esma.service;

import com.quar17esma.entity.Client;

import java.util.List;

public interface IClientsService {

    void registerClient(Client client);

    Client getClientByEmail(String email);

}
