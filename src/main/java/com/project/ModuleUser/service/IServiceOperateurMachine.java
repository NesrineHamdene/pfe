package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.OperateurMachineDTO;

public interface IServiceOperateurMachine {
    OperateurMachineDTO getOperateurByUsername(String username);

    OperateurMachineDTO updateOperateurByUsername(String username, OperateurMachineDTO operateurDTO);


    OperateurMachineDTO createOperateurMachine(OperateurMachineDTO operateurDTO);

//    List<ReclamationDTO> getReclamationsByUsername(String username);
}
